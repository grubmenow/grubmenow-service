package com.grubmenow.service.subscription;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.Validate;

import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.SubscriptionDAO;
import com.grubmenow.service.datamodel.SubscriptionNotificationType;
import com.grubmenow.service.notif.email.EmailSendException;
import com.grubmenow.service.notif.email.EmailSender;
import com.grubmenow.service.notif.email.FoodAvailabilityInSearchAreaRequest;
import com.grubmenow.service.persist.PersistenceHandler;

/**
 * Sends notifications based on subscriptions
 */
@CommonsLog
public class SubscribtionSender {

    private PersistenceHandler persistenceHandler;
    private EmailSender emailSender;

    public SubscribtionSender(PersistenceHandler persistenceHandler, EmailSender emailSender) {
        this.persistenceHandler = persistenceHandler;
        this.emailSender = emailSender;
    }

    /**
     * Sends notifications to the customers who have the zipCode in their search
     * criteria
     *
     * @param zipCode
     */
    public void sendLocationSearchMatchNotification(String zipCode) {
        Validate.notEmpty(zipCode, "Zip code cannnot be null or empty");
        /**
         * Get all active subscriptions of type: ZIP_CODE_SUBSCRIPTION and
         * contains the zipCode in the subscriptionParameters
         */
        List<SubscriptionDAO> subscriptionDAOs = persistenceHandler.getLocationSearchActiveSubscriptions(zipCode);
        if (subscriptionDAOs == null) {
            return;
        }

        Set<String> emailSubscriberIds = new HashSet<String>();
        for (SubscriptionDAO dao : subscriptionDAOs) {
            if (dao.getSubscriptionNotificationType() == SubscriptionNotificationType.EMAIL) {
                emailSubscriberIds.add(dao.getSubscriberCustomerId());
            }
        }

        if (emailSubscriberIds.isEmpty()) {
            return;
        }

        for (String subscriberCustId : emailSubscriberIds) {
            CustomerDAO customerDAO = persistenceHandler.getCustomerById(subscriberCustId);
            FoodAvailabilityInSearchAreaRequest emailRequest = new FoodAvailabilityInSearchAreaRequest();
            emailRequest.setCustomer(customerDAO);
            try {
                emailSender.sendFoodAvailabilityInSearchAreaEmail(emailRequest);
            } catch (EmailSendException ex) {
                log.error("Error sending email to customer Id = " + subscriberCustId, ex);
            }
        }
    }
}
