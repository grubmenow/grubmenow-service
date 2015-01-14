package com.grubmenow.service.api;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemState;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.datamodel.ObjectPopulator;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.exception.ServiceFaultException;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceHandler;

@Controller
@RequestMapping(value = "/api/foodItem")
@CommonsLog
public class FoodItemService extends AbstractRemoteService
{
    @Autowired
    PersistenceHandler persistenceHandler;
    private static final int IMAGE_WIDTH = 300;
    private static final int IMAGE_HEIGHT = 200;
    
    private static final String S3_BUCKET_NAME = "grubmenow.com";
    private static final String S3_KEYNAME_PREFIX = "img/items/";
    private static final String S3_ENDPOINT = "https://s3-us-west-2.amazonaws.com/";
    
    @RequestMapping (value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public FoodItem get(String id)
    {
        return null;
    }
    
    @RequestMapping (method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseStatus (HttpStatus.CREATED)
    @ResponseBody
    public FoodItem create(@RequestBody FoodItem foodItem)
    {
        validateCreateRequest(foodItem);
        /* 
         * Make sure that the food item name is unique
         */
        List<FoodItemDAO> foodItemDAOs = persistenceHandler.getFoodItemByName(foodItem.getFoodItemName());
        if (foodItemDAOs != null && foodItemDAOs.size() > 0)
        {
            throw new ValidationException("Food item name : " + foodItem.getFoodItemName() + " already exists");
        }
        
        try
        {
            File targetFile = File.createTempFile("targetFoodItemImage", ".jpg");
            BufferedImage imageSrc = getImageFromUrl(foodItem.getFoodItemImageUrl());
            validateInputImageSize(imageSrc);
            BufferedImage imageTarget = resizeImage(imageSrc);
            boolean written = ImageIO.write(imageTarget, "jpg", targetFile);
            if (!written)
            {
                throw new ServiceFaultException("Not able to save the file to jpg format");
            }
            
            // upload to s3
            String keyNameSuffix = foodItem.getFoodItemName().replaceAll(" ", "-") + ".jpg";
            String foodItemImageKey = uploadFoodItemImageToS3(keyNameSuffix, targetFile);
            // cleanup
            targetFile.delete();
            
            String foodItemImageUrl = S3_ENDPOINT+S3_BUCKET_NAME+"/"+foodItemImageKey;
            FoodItemDAO foodItemDAO = createFoodItemInDatabase(foodItem.getFoodItemName(), foodItemImageUrl, foodItem.getFoodItemDescription());
            log.info("Created food item: [" + foodItem.getFoodItemName() + "] with URL = [" + foodItemImageUrl + "]"); 
            return ObjectPopulator.toFoodItem(foodItemDAO);
        }
        catch (ValidationException ex)
        {
            throw ex;
        }
        catch (Exception e)
        {
            log.error("Exception while doing image manipulation. ", e);
            throw new ServiceFaultException("Internal service error while doing image manipulation.", e.getMessage());
        }
    }

    private BufferedImage resizeImage(BufferedImage imageSrc)
    {
        BufferedImage imageTarget = Scalr.resize(imageSrc, IMAGE_WIDTH);
        return imageTarget;
    }

    private FoodItemDAO createFoodItemInDatabase(String foodItemName, String foodItemImageUrl, String foodItemDescription)
    {
        FoodItemDAO foodItemDAO = new FoodItemDAO();
        foodItemDAO.setFoodItemId(IDGenerator.generateFoodItemId());
        foodItemDAO.setFoodItemName(foodItemName);
        if (foodItemDescription != null)
        {
            foodItemDAO.setFoodItemDescription(foodItemDescription);
        }
        else
        {
            foodItemDAO.setFoodItemDescription("");
        }
        foodItemDAO.setFoodItemImageUrl(foodItemImageUrl);
        foodItemDAO.setFoodItemState(FoodItemState.ACTIVE);
        persistenceHandler.createFoodItem(foodItemDAO);
        return foodItemDAO;
    }

    /**
     * Returns the complete key name
     */
    private String uploadFoodItemImageToS3(String keySuffix, File foodItemImageToUpload)
    {
        String keyName = S3_KEYNAME_PREFIX + keySuffix;
        AWSCredentials credentials = new BasicAWSCredentials("AKIAJY3UBUCCSC4HKUHQ", "4CdlLU95GbwKZlB98IxhJcY3IdrRaA9bzph987dh");
        AmazonS3 s3client = new AmazonS3Client(credentials);
        s3client.setEndpoint(S3_ENDPOINT);
        PutObjectRequest request = new PutObjectRequest(S3_BUCKET_NAME, keyName, foodItemImageToUpload);
        
        // make the object public-ally available
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        s3client.putObject(request.withAccessControlList(acl));
        return keyName;
    }

    private void validateInputImageSize(BufferedImage imageSrc)
    {
        if (imageSrc.getWidth() < IMAGE_WIDTH)
        {
            throw new ValidationException("The image width should atleast be " + IMAGE_WIDTH + " pixels");
        }
        
        if (imageSrc.getHeight() < IMAGE_HEIGHT)
        {
            throw new ValidationException("The image height should atleast be " + IMAGE_WIDTH + " pixels");
        }
    }

    private BufferedImage getImageFromUrl(String foodItemImageUrl) throws Exception
    {
        URL imageUrl = new URL(foodItemImageUrl);
        
        RenderedImage javaIoImg = ImageIO.read(imageUrl);
        if (javaIoImg instanceof BufferedImage)
        {
            return (BufferedImage) javaIoImg;
        }
        
        throw new Exception("The JavaIO read image is not of the form BufferedImage");
    }

    private void validateCreateRequest(FoodItem foodItem)
    {
        Validator.notNull(foodItem, "foodItem cannot be null");
        Validator.isNull(foodItem.getFoodItemId(), "foodItemId cannot be present for a create request");
        Validator.notBlank(foodItem.getFoodItemName(), "foodItem name cannot be empty");
        Validator.notBlank(foodItem.getFoodItemImageUrl(), "foodItem image url cannot be empty");
    }
}
