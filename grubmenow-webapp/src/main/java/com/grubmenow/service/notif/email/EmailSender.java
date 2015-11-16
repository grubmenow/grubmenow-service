package com.grubmenow.service.notif.email;


import java.io.IOException;
import java.io.StringWriter;
import java.text.NumberFormat;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

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
import com.grubmenow.service.model.PaymentMethod;

@CommonsLog
public class EmailSender
{
    private static final String FROM = "'MealLocal' <admin@meallocal.com>";
    private static final String BCC = FROM;
    private final AmazonSimpleEmailServiceClient sesClient;
    private final VelocityEngine velocityEngine;
    private final Template customerOrderEmailTemplate;
    private final Template providerOrderEmailTemplate;
    private final Template generalFeedbackEmailTemplate;
    private final Template foodAvailableInSearchAreaTemplate;
    private final boolean isProductionEmail;

    public EmailSender(String awsAccessKey, String awsSecretKey, boolean isProductionEmail) throws Exception {
        this.isProductionEmail = isProductionEmail;
        AWSCredentials creds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        sesClient = new AmazonSimpleEmailServiceClient(creds);
        // TODO: kapila: 2014-10-09 Region Hardcoded for now
        Region REGION = Region.getRegion(Regions.US_WEST_2);
        sesClient.setRegion(REGION);

        // initialize velocity engine for email templates
        velocityEngine = new VelocityEngine();

        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        velocityEngine.setProperty("runtime.log.logsystem.log4j.category", "velocity");
        velocityEngine.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
        velocityEngine.init();

        // initialize the various templates
        customerOrderEmailTemplate = velocityEngine.getTemplate("email/OrderEmailForConsumer.vm");
        providerOrderEmailTemplate = velocityEngine.getTemplate("email/OrderEmailForProvider.vm");
        generalFeedbackEmailTemplate = velocityEngine.getTemplate("email/GeneralFeedbackForGBN.vm");
        foodAvailableInSearchAreaTemplate = velocityEngine.getTemplate("email/FoodAvailableInSearchArea.vm");
    }

    public void sendConsumerOrderSuccessEmail(OrderSuccessEmailRequest request) throws EmailSendException {

        Validate.notNull(request.getConsumer(), "Consumer cannot be null");
        Validate.notNull(request.getCustomerOrder(), "Customer order cannot be null");
        Validate.notNull(request.getOrderAmountTotal(), "Customer order amount cannot be null");
        Validate.notNull(request.getOrderFulfillmentDate(), "Order fulfillment date cannot be null");
        Validate.notNull(request.getProvider(), "Provider cannot be null");
        Validate.notEmpty(request.getOrderItems(), "Order items cannot be empty");

        String toAddress = request.getConsumer().getCustomerEmailId();
        try
        {
            // Construct an object to contain the recipient address.
            Destination destination = new Destination()
            .withToAddresses(new String[]{toAddress})
            .withBccAddresses(BCC);

            Content subject = new Content().withData(getSubjectIncludingPrefix("Thank you for your MealLocal order. Order Id: " + request.getCustomerOrder().getOrderId()));
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

    /**
     * Creates a customer subject based on the test email or not.
     * @return the new subject.
     */
    private String getSubjectIncludingPrefix(String originalSubject)
    {
        if (isProductionEmail)
        {
            return originalSubject;
        }
        return "Test: " + originalSubject;
    }

    public void sendProviderOrderSuccessEmail(OrderSuccessEmailRequest request) throws EmailSendException {

        Validate.notNull(request.getConsumer(), "Consumer cannot be null");
        Validate.notNull(request.getCustomerOrder(), "Customer order cannot be null");
        Validate.notNull(request.getOrderAmountTotal(), "Customer order amount cannot be null");
        Validate.notNull(request.getOrderFulfillmentDate(), "Order fulfillment date cannot be null");
        Validate.notNull(request.getProvider(), "Provider cannot be null");
        Validate.notEmpty(request.getOrderItems(), "Order items cannot be empty");

        String toAddress = request.getProvider().getProviderEmailId();

        try
        {
            // Construct an object to contain the recipient address.
            Destination destination = new Destination()
            .withToAddresses(new String[] { toAddress })
            .withBccAddresses(BCC);

            Content subject = new Content().withData(getSubjectIncludingPrefix("You received an order to fulfill. Order Id: " + request.getCustomerOrder().getOrderId()));
            Content htmlBody = new Content().withData(generateHtmlBodyForProviderOrderSuccessEmail(request));
            Body body = new Body().withHtml(htmlBody);

            // Create a message with the specified subject and body.
            Message message = new Message().withSubject(subject).withBody(body);

            // Assemble the email.
            SendEmailRequest sendEmailRequest = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);

            log.debug("Attempting to send a an order email to provider ["+toAddress+"]");
            sesClient.sendEmail(sendEmailRequest);
            log.debug("Email sent!");
        }
        catch (Exception ex)
        {
            String errMsg = "The email to email Id ["+toAddress+"] was not sent";
            throw new EmailSendException(errMsg, ex);
        }
    }

    public void sendGeneralFeedbackEmail(GeneralFeedbackEmailRequest request) throws EmailSendException
    {
        String toAddress = "admin@meallocal.com";
        try
        {
            // Construct an object to contain the recipient address.
            Destination destination = new Destination().withToAddresses(new String[] { toAddress });

            Content subject = new Content().withData(getSubjectIncludingPrefix("Feedback recieved, type: " + request.getFeedbackType()));
            Content htmlBody = new Content().withData(generateHtmlBodyForGeneralFeedbackEmail(request));
            Body body = new Body().withHtml(htmlBody);

            // Create a message with the specified subject and body.
            Message message = new Message().withSubject(subject).withBody(body);

            // Assemble the email.
            SendEmailRequest sendEmailRequest = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);

            log.debug("Attempting to send feedback email to ["+toAddress+"]");
            sesClient.sendEmail(sendEmailRequest);
            log.debug("Email sent!");
        }
        catch (Exception ex)
        {
            String errMsg = "The email to email Id ["+toAddress+"] was not sent";
            throw new EmailSendException(errMsg, ex);
        }
    }

    public void sendFoodAvailabilityInSearchAreaEmail(FoodAvailabilityInSearchAreaRequest request)
            throws EmailSendException {
        Validate.notNull(request.getCustomer(), "Customer cannot be null");
        Validate.notNull(request.getCustomer().getCustomerFirstName(), "Customer's first name cannot be null");
        Validate.notNull(request.getCustomer().getCustomerEmailId(), "Customer's email cannot be null");

        String toAddress = request.getCustomer().getCustomerEmailId();
        try
        {
            // Construct an object to contain the recipient address.
            Destination destination = new Destination()
            .withToAddresses(new String[]{toAddress})
            .withBccAddresses(BCC);

            Content subject = new Content()
            .withData(getSubjectIncludingPrefix("Home cooked food available in your area"));
            Content htmlBody = new Content().withData(generateHtmlBodyForFoodAvailabilityEmail(request));
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

    private String generateHtmlBodyForFoodAvailabilityEmail(FoodAvailabilityInSearchAreaRequest request)
            throws VelocityException, IOException {

        VelocityContext context = new VelocityContext();
        context.put("request", request);
        context.put("Formatter", Formatter.class);
        StringWriter writer = new StringWriter();
        customerOrderEmailTemplate.merge(context, writer);
        return writer.toString();
    }

    private String generateHtmlBodyForConsumerOrderSuccessEmail(OrderSuccessEmailRequest request) throws VelocityException, IOException {

        VelocityContext context = new VelocityContext();
        context.put("request", request);
        context.put("Formatter", Formatter.class);
        StringWriter writer = new StringWriter();
        customerOrderEmailTemplate.merge(context, writer);
        return writer.toString();
    }

    private String generateHtmlBodyForProviderOrderSuccessEmail(OrderSuccessEmailRequest request) throws VelocityException, IOException {

        VelocityContext context = new VelocityContext();
        context.put("request", request);
        context.put("Formatter", Formatter.class);
        StringWriter writer = new StringWriter();
        providerOrderEmailTemplate.merge(context, writer);
        return writer.toString();
    }

    private String generateHtmlBodyForGeneralFeedbackEmail(GeneralFeedbackEmailRequest request) throws VelocityException, IOException {

        VelocityContext context = new VelocityContext();
        context.put("request", request);
        context.put("Formatter", Formatter.class);
        StringWriter writer = new StringWriter();
        generalFeedbackEmailTemplate.merge(context, writer);
        return writer.toString();
    }

    public static class Formatter
    {
        private static DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
        .appendDayOfWeekText()
        .appendLiteral(", ")
        .appendMonthOfYearText()
        .appendLiteral(' ')
        .appendDayOfMonth(2)
        .appendLiteral(' ')
        .appendYear(4, 4)
        .toFormatter();

        public static String formatDate(DateTime dateTime)
        {
            if (dateTime == null)
            {
                throw new IllegalStateException("The dateTime object cannot be null while formatting date");
            }
            return dateFormatter.print(dateTime);
        }

        public static String formatAmount(Amount amount)
        {
            return NumberFormat.getCurrencyInstance().format(amount.getValue()/100f);
        }

        public static String formatAddressFirstLine(ProviderDAO provider)
        {
            StringBuilder stringBuilder = new StringBuilder();
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
            if (StringUtils.isNotBlank(provider.getProviderAddressApartmentNumber()))
            {
                stringBuilder.append(provider.getProviderAddressApartmentNumber())
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

        /**
         * Formats the enum's constants. E.g. it has _, replace those with space.
         * @param enumString the enum value whose value needs to be formatted
         */
        public static String formatEnum(PaymentMethod paymentMethod)
        {
            if (paymentMethod == null)
            {
                return "";
            }

            return paymentMethod.toString().replaceAll("_", " ");
        }
    }
}
