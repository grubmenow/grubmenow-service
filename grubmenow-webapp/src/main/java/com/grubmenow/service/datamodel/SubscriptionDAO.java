package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "SUBSCRIPTION")
@ToString
@NoArgsConstructor
@Data
public class SubscriptionDAO {
    @Id
    @Column(name = "SUBSCRIPTION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int subscriptionId;

    @NonNull
    @Column(name = "SUBSCRIPTION_TYPE")
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    @NonNull
    @Column(name = "SUBSCRIPTION_PARAMETERS")
    private String subscriptionParameters;

    @Column(name = "SUBSCRIBER_CUSTOMER_ID")
    private String subscriberCustomerId;

    @NonNull
    @Column(name = "SUBSCRIPTION_NOTIFICATION_TYPE")
    @Enumerated(EnumType.STRING)
    private SubscriptionNotificationType subscriptionNotificationType;

    @NonNull
    @Column(name = "SUBSCRIPTION_STATE")
    @Enumerated(EnumType.STRING)
    private SubscriptionState subscriptionState;

    @NonNull
    @Column(name = "CREATION_TIME")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;
}
