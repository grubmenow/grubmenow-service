package com.grubmenow.service.datamodel;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.grubmenow.service.model.Amount;
import com.grubmenow.service.model.AvailableDay;
import com.grubmenow.service.model.CustomerOrder;
import com.grubmenow.service.model.CustomerOrderItem;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.FoodItemOffer;
import com.grubmenow.service.model.MealType;
import com.grubmenow.service.model.Provider;

public class ObjectPopulator {
	
	private static DateTimeFormatter printableDateTimeFormatter = DateTimeFormat.forPattern("E, MMM dd YYYY");
	
	public static FoodItem toFoodItem(FoodItemDAO foodItemDAO) {
		
		FoodItem foodItem = new FoodItem();
		foodItem.setFoodItemId(foodItemDAO.getFoodItemId());
		foodItem.setFoodItemName(foodItemDAO.getFoodItemName());
		foodItem.setFoodItemDescription(foodItemDAO.getFoodItemDescription());
		foodItem.setFoodItemImageUrl(foodItemDAO.getFoodItemImageUrl());

		return foodItem;
	}
	
	public static Provider toProvider(ProviderDAO providerDAO) {
		Provider provider = new Provider();
		provider.setProviderId(providerDAO.getProviderId());
		provider.setProviderName(providerDAO.getProviderName());
		
		String address = buildAddressSingleLine(providerDAO);
		provider.setProviderAddress(address);
		
		provider.setProviderImageURL(providerDAO.getProviderImageURL());
	
		BigDecimal totalRatingPoints = new BigDecimal(providerDAO.getTotalRatingPoints());
		BigDecimal numberOfRatings = new BigDecimal(providerDAO.getNumberOfRatings());
		
		if(numberOfRatings.compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal rating = totalRatingPoints.divide(numberOfRatings, 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.CEILING);
			provider.setRating(rating);
		} else {
			provider.setRating(BigDecimal.ZERO);
		}
		
		provider.setZipCode(providerDAO.getProviderAddressZipCode());
		provider.setProviderImageURL(providerDAO.getProviderImageURL());
		provider.setOnlinePaymentAccepted(providerDAO.getIsOnlinePaymentAccepted());
		provider.setCashOnDeliverPaymentAccepted(providerDAO.getIsCashOnDeliverPaymentAccepted());
		provider.setCardOnDeliverPaymentAccepted(providerDAO.getIsCardOnDeliverPaymentAccepted());
		provider.setProviderPhoneNumber(providerDAO.getProviderPhoneNumber());
		
		return provider;
	}
	
	public static String buildAddressSingleLine(ProviderDAO provider)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(provider.getProviderAddressApartmentNumber()))
        {
            stringBuilder.append(provider.getProviderAddressApartmentNumber())
                .append(" ");
        }
        if (StringUtils.isNotBlank(provider.getProviderAddressStreetNumber()))
        {
            stringBuilder.append(provider.getProviderAddressStreetNumber())
            .append(" ");
        }
        if (StringUtils.isNotBlank(provider.getProviderAddressStreet()))
        {
            stringBuilder.append(provider.getProviderAddressStreet())
            .append(" ");
        }
        // remove the last space from the string so far
        stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 1));
        // Add the comman to separate city
        stringBuilder.append(", ");
        if (StringUtils.isNotBlank(provider.getProviderAddressCity()))
        {
            stringBuilder.append(provider.getProviderAddressCity())
                .append(", ");
        }
        if (StringUtils.isNotBlank(provider.getProviderAddressState()))
        {
            stringBuilder.append(provider.getProviderAddressState())
            .append(" ");
        }
        if (StringUtils.isNotBlank(provider.getProviderAddressZipCode()))
        {
            stringBuilder.append(provider.getProviderAddressZipCode())
            .append(" ");
        }
        return stringBuilder.substring(0, stringBuilder.length()-1);
    }
    
	
	public static FoodItemOffer toFoodItemOffer(FoodItemOfferDAO foodItemOfferDAO) {
		FoodItemOffer foodItemOffer = new FoodItemOffer();
		foodItemOffer.setFoodItemOfferId(foodItemOfferDAO.getFoodItemOfferId());
		foodItemOffer.setFoodItemId(foodItemOfferDAO.getFoodItemId());
		foodItemOffer.setProviderId(foodItemOfferDAO.getProviderId());
		foodItemOffer.setOfferDescription(foodItemOfferDAO.getOfferDescription());
		foodItemOffer.setAvailableQuantity(foodItemOfferDAO.getAvailableQuantity());
		foodItemOffer.setPrice(new Amount(foodItemOfferDAO.getOfferUnitPrice(), foodItemOfferDAO.getOfferCurrency()));
		foodItemOffer.setOfferDay(foodItemOfferDAO.getOfferDay().toString(printableDateTimeFormatter));
		
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		
		AvailableDay availableDay = null;
		
		if(foodItemOfferDAO.getOfferDay().getDayOfYear() == today.getDayOfYear() && foodItemOfferDAO.getOfferDay().getDayOfYear() == today.getDayOfYear()) {
			availableDay = AvailableDay.Today;
		} else if(foodItemOfferDAO.getOfferDay().getDayOfYear() == tomorrow.getDayOfYear() && foodItemOfferDAO.getOfferDay().getDayOfYear() == tomorrow.getDayOfYear()) {
			availableDay = AvailableDay.Tomorrow;
		} else {
			availableDay = AvailableDay.Later;
		}
		
		foodItemOffer.setAvailableDay(availableDay);
		foodItemOffer.setMealType(MealType.valueOf(foodItemOfferDAO.getOfferMealType().name()));
		
		return foodItemOffer;
	}
	
	public static CustomerOrder toCustomerOrder(CustomerOrderDAO customerOrderDAO) {

		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderId(customerOrderDAO.getOrderId());
		customerOrder.setOrderState(customerOrderDAO.getOrderState());
		customerOrder.setDeliveryMethod(customerOrderDAO.getDeliveryMethod());
		customerOrder.setOrderAmount(new Amount(customerOrderDAO.getOrderAmount(), customerOrderDAO.getOrderCurrency()));
		customerOrder.setTaxAmount(new Amount(customerOrderDAO.getTaxAmount(), customerOrderDAO.getOrderCurrency()));
		customerOrder.setPaymentMethod(customerOrderDAO.getPaymentMethod());
		customerOrder.setOrderCreationDate(customerOrderDAO.getOrderCreationDate().toString(printableDateTimeFormatter));
		if (customerOrderDAO.getOrderFulfilmentDate() != null)
		{
		    customerOrder.setOrderFulfilmentDate(customerOrderDAO.getOrderFulfilmentDate().toString(printableDateTimeFormatter));
		}
		return customerOrder;
	}

	public static CustomerOrderItem toCustomerOrderItem(CustomerOrderItemDAO customerOrderItemDAO) {
		CustomerOrderItem customerOrderItem = new CustomerOrderItem();
		customerOrderItem.setOrderItemId(customerOrderItemDAO.getOrderItemId());
		customerOrderItem.setQuantity(customerOrderItemDAO.getQuantity());
		customerOrderItem.setOrderItemAmount(new Amount(customerOrderItemDAO.getOrderItemAmount(), customerOrderItemDAO.getOrderCurrency()));
		return customerOrderItem;
	}
}
