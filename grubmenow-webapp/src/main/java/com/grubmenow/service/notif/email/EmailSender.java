package com.grubmenow.service.notif.email;


import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import lombok.extern.apachecommons.CommonsLog;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.model.Amount;

@CommonsLog
public class EmailSender 
{
	private static final String FROM = "admin@grubmenow.com";  // Replace with your "From" address. This address must be verified.
    private final AmazonSimpleEmailServiceClient sesClient;
    private final VelocityEngine velocityEngine;
    private final Template customerOrderEmailTemplate;
    
    public EmailSender(String awsAccessKey, String awsSecretKey) throws Exception {
    	AWSCredentials creds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        sesClient = new AmazonSimpleEmailServiceClient(creds);
        // TODO: kapila: 2014-10-09 Region Hardcoded for now
        Region REGION = Region.getRegion(Regions.US_WEST_2);
        sesClient.setRegion(REGION);
        
        // initialize velocity engine for email templates 
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("file.resource.loader.path", "src/main/resources/email");
		velocityEngine.init();
		
		// initialize the various templates
		customerOrderEmailTemplate = velocityEngine.getTemplate("OrderEmailForConsumer.vm");
	}

    public void sendConsumerOrderSuccessEmail(ConsumerOrderSuccessEmailRequest request) throws EmailSendException {
    	String toAddress = request.getConsumer().getCustomerEmailId();
    	try
    	{
    		// Construct an object to contain the recipient address.
    		Destination destination = new Destination().withToAddresses(new String[]{toAddress});
    		
    		Content subject = new Content().withData("Thank you for your grubmenow order");
    		Content htmlBody = new Content().withData(generateHtmlBodyForConsumerOrderSuccessEmail(request));
    		Body body = new Body().withHtml(htmlBody);
    		
    		// Create a message with the specified subject and body.
    		Message message = new Message().withSubject(subject).withBody(body);
    		
    		// Assemble the email.
    		SendEmailRequest sendEmailRequest = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);
    		
            log.debug("Attempting to send a an order email to consumer ["+toAddress+"]");
            sesClient.sendEmail(sendEmailRequest);
            log.debug("Email sent!");
        }
        catch (Exception ex) 
        {
            String errMsg = "The email to email Id ["+toAddress+"] was not sent";
            throw new EmailSendException(errMsg, ex);
        }
    }

	private String generateHtmlBodyForConsumerOrderSuccessEmail(ConsumerOrderSuccessEmailRequest request) throws VelocityException, IOException {
		
		VelocityContext context = new VelocityContext();
		context.put("request", request);
		context.put("Formatter", Formatter.class);
		StringWriter writer = new StringWriter();
        customerOrderEmailTemplate.merge(context, writer);
        System.out.println(writer.toString());
        return writer.toString();
	}
	
	public static class Formatter
	{
		private static DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
	     .appendMonthOfYearText()
	     .appendLiteral(' ')
	     .appendDayOfMonth(2)
	     .appendLiteral(' ')
	     .appendYear(4, 4)
	     .toFormatter();
		
		public static String formatDate(DateTime dateTime)
		{
			return dateFormatter.print(dateTime);
		}
		
		public static String formatAmount(Amount amount)
		{
			return amount.getCurrency() + " " + amount.getValue();
		}
		
		public static String formatAddressFirstLine(ProviderDAO provider)
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
			return stringBuilder.substring(0, stringBuilder.length()-1);
		}
		
		public static String formatAddressLastLine(ProviderDAO provider)
		{
			StringBuilder stringBuilder = new StringBuilder();
			if (StringUtils.isNotBlank(provider.getProviderAddressCity()))
			{
				stringBuilder.append(provider.getProviderAddressCity())
					.append(" ");
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
		
	}
}
