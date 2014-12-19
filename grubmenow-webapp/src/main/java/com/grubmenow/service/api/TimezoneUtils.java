package com.grubmenow.service.api;

import org.joda.time.DateTime;

import com.grubmenow.service.model.AvailableDay;

public class TimezoneUtils
{
    public static final int DINNER_ORDER_CUT_OFF_HOUR = 16;

    private TimezoneUtils() {}

    public static DateTime calculateOrderCutOffTimeWrtClient(AvailableDay availableDay, int requestTimezoneOffsetMins)
    {
        DateTime orderCutoffTimeWrtClient = DateTime.now();
        if (availableDay == AvailableDay.Tomorrow)
        {
            orderCutoffTimeWrtClient = orderCutoffTimeWrtClient.plusDays(1);
        }
        orderCutoffTimeWrtClient = orderCutoffTimeWrtClient.minusMinutes(requestTimezoneOffsetMins)
                              .withTimeAtStartOfDay();
        orderCutoffTimeWrtClient = orderCutoffTimeWrtClient.plusHours(DINNER_ORDER_CUT_OFF_HOUR);
        return orderCutoffTimeWrtClient;
    }

    public static DateTime getCurrentTimeWrtClient(int requestTimezoneOffsetMins) 
    {
        DateTime forDate = DateTime.now();
        forDate = forDate.minusMinutes(requestTimezoneOffsetMins);
        return forDate;
    }
}
