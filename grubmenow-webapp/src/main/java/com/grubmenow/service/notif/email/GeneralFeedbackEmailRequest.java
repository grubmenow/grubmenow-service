package com.grubmenow.service.notif.email;


import org.joda.time.DateTime;

import com.grubmenow.service.model.FeedbackType;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * The request object for sending email to the company CS 
 * when the website users submits a feedback.  
 */
@Data
@Builder
public class GeneralFeedbackEmailRequest 
{
    FeedbackType feedbackType;
    String message;
    String customerEmailId;
    DateTime feedbackProvidedTime;
    String zipCode;
    String searchDay;
}
