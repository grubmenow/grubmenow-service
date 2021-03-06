DROP TABLE FOOD_ITEM;
CREATE TABLE FOOD_ITEM (
 FOOD_ITEM_ID VARCHAR(16) NOT NULL,
 FOOD_ITEM_NAME VARCHAR(256) NOT NULL,
 FOOD_ITEM_IMAGE_URL VARCHAR(256) NOT NULL,
 FOOD_ITEM_DESCRIPTION VARCHAR(1024) NOT NULL,
 FOOD_ITEM_DESCRIPTION_TAGS VARCHAR(1024),
 FOOD_ITEM_STATE VARCHAR(32) NOT NULL,
 PRIMARY KEY ( FOOD_ITEM_ID )
);

DROP TABLE PROVIDER;
CREATE TABLE PROVIDER (
 PROVIDER_ID VARCHAR(16) NOT NULL,
 PROVIDER_NAME VARCHAR(128) NOT NULL,
 PROVIDER_EMAIL_ID VARCHAR(64) NOT NULL,
 PROVIDER_ADDRESS_STREET_NUMBER VARCHAR(8) NOT NULL,
 PROVIDER_ADDRESS_STREET VARCHAR(256) NOT NULL,
 PROVIDER_ADDRESS_APARTMENT_NUMBER VARCHAR(16),
 PROVIDER_ADDRESS_ZIP_CODE VARCHAR(16),
 PROVIDER_ADDRESS_STATE VARCHAR(2),
 PROVIDER_ADDRESS_CITY VARCHAR(32),
 PROVIDER_IMAGE_URL VARCHAR(256),
 TOTAL_RATING_POINTS INT NOT NULL,
 NUMBER_OF_RATINGS INT NOT NULL,
 IS_ONLINE_PAYMENT_ACCEPTED BOOLEAN DEFAULT 0,
 IS_CASH_ON_DELIVERY_PAYMENT_ACCEPTED BOOLEAN DEFAULT 0,
 IS_CARD_ON_DELIVERY_PAYMENT_ACCEPTED BOOLEAN DEFAULT 0,
 PROVIDER_STATE VARCHAR(32) NOT NULL,
 PRIMARY KEY ( PROVIDER_ID )
);

DROP TABLE FOOD_ITEM_OFFER;
CREATE TABLE FOOD_ITEM_OFFER (
 FOOD_ITEM_OFFER_ID VARCHAR(16) NOT NULL,
 PROVIDER_ID VARCHAR(16) NOT NULL,
 FOOD_ITEM_ID VARCHAR(16) NOT NULL,
 OFFER_DESCRIPTION VARCHAR(1024) NOT NULL,
 OFFER_DESCRIPTION_TAGS VARCHAR(1024),
 OFFER_UNIT_PRICE  INT NOT NULL,
 OFFER_CURRENCY VARCHAR(3) NOT NULL,
 OFFER_QUANTITY INT NOT NULL,
 OFFER_DAY DATE NOT NULL,
 OFFER_MEAL_TYPE VARCHAR(256) NOT NULL,
 IS_FOOD_DELIVERY_OPTION_AVAILABLE BOOLEAN DEFAULT 0,
 IS_FOOD_PICK_UP_OPTION_AVAILABLE BOOLEAN DEFAULT 0,
 AVAILABLE_QUANTITY INT NOT NULL,
 OFFER_STATE VARCHAR(32) NOT NULL,
 PRIMARY KEY ( PROVIDER_ID )
);

DROP TABLE CUSTOMER;
CREATE TABLE CUSTOMER (
 CUSTOMER_ID VARCHAR(32) NOT NULL,
 CUSTOMER_FIRST_NAME VARCHAR(128) NULL,
 CUSTOMER_LAST_NAME VARCHAR(128) NULL,
 CUSTOMER_EMAIL_ID VARCHAR(64) NOT NULL,
 CUSTOMER_STATE VARCHAR(32) NOT NULL,
 PRIMARY KEY ( CUSTOMER_ID )
);

DROP TABLE CUSTOMER_ORDER;
CREATE TABLE CUSTOMER_ORDER (
 ORDER_ID VARCHAR(36) NOT NULL,
 CUSTOMER_ID VARCHAR(32) NOT NULL,
 PROVIDER_ID VARCHAR(16) NOT NULL,
 ORDER_CREATION_DATE DATE NOT NULL,
 ORDER_AMOUNT INT NOT NULL,
 ORDER_CURRENCY VARCHAR(3) NOT NULL,
 TAX_AMOUNT INT NOT NULL,
 PAYMENT_METHOD VARCHAR(16) NOT NULL,
 DELIVERY_METHOD VARCHAR(16) NOT NULL,
 ORDER_STATE VARCHAR(32) NOT NULL,
 ORDER_PAYMENT_STATE VARCHAR(32) NOT NULL,
 ORDER_CHARGE_TRANSACTION_ID VARCHAR(32),
 ORDER_REFUND_TRANSACTION_ID VARCHAR(32),
 ORDER_STATE_MESSAGE VARCHAR(512) NOT NULL,
 PRIMARY KEY ( ORDER_ID )
);

DROP TABLE CUSTOMER_ORDER_ITEM;
CREATE TABLE CUSTOMER_ORDER_ITEM (
 ORDER_ITEM_ID VARCHAR(36) NOT NULL,
 ORDER_ID VARCHAR(36) NOT NULL,
 CUSTOMER_ID VARCHAR(32) NOT NULL,
 PROVIDER_ID VARCHAR(16) NOT NULL,
 FOOD_ITEM_ID VARCHAR(16) NOT NULL,
 FOOD_ITEM_OFFER_ID VARCHAR(16) NOT NULL,
 QUANTITY INT NOT NULL,
 ORDER_ITEM_AMOUNT INT NOT NULL,
 ORDER_CURRENCY VARCHAR(3) NOT NULL,
 ORDER_CREATION_DATE DATE NOT NULL,
 PRIMARY KEY ( ORDER_ITEM_ID )
);


DROP TABLE ORDER_FEEDBACK;
CREATE TABLE ORDER_FEEDBACK (
 ORDER_ID VARCHAR(36) NOT NULL,
 CUSTOMER_ID VARCHAR(32) NOT NULL,
 PROVIDER_ID VARCHAR(16) NOT NULL,
 RATING INT NOT NULL,
 FEEDBACK VARCHAR(2048) NOT NULL,
 PRIMARY KEY ( ORDER_ID )
);

DROP TABLE SEARCH_SUGGESTION_FEEDBACK;
CREATE TABLE SEARCH_SUGGESTION_FEEDBACK (
    ID INT NOT NULL AUTO_INCREMENT,
    SEARCH_SUGGESTION_FOOD_ITEM_NAME varchar(256) not null,
    ZIP_CODE varchar(10) NULL,
    EMAIL_ID varchar(64) NULL,
    FEEDBACK_PROVIDED_DATE date not null,
    PRIMARY KEY (ID)
);

DROP TABLE GENERAL_FEEDBACK;
CREATE TABLE GENERAL_FEEDBACK (
    ID INT NOT NULL AUTO_INCREMENT,
    FEEDBACK_TYPE varchar(50) not null,
    ZIP_CODE varchar(10) NULL,
    EMAIL_ID varchar(64) NULL,
    MESSAGE varchar(1024) NULL,
    FEEDBACK_PROVIDED_DATE date not null,
    PRIMARY KEY (ID)
);

DROP TABLE INVITATION_REQUEST;
CREATE TABLE INVITATION_REQUEST (
    ID INT NOT NULL AUTO_INCREMENT,
    ZIP_CODE varchar(10) NULL,
    EMAIL_ID varchar(64) NULL,
    INVITATION_REQUEST_DATE date not null,
    PRIMARY KEY ( ID)
);

DROP TABLE SUBSCRIPTION;
CREATE TABLE SUBSCRIPTION (
    ID INT NOT NULL AUTO_INCREMENT,
    SUBSCRIPTION_TYPE varchar(256) NOT NULL,
    SUBSCRIPTION_PARAMETERS varchar(2048) NOT NULL,
    SUBSCRIBER_CUSTOMER_ID varchar(32) null,
    SUBSCRIPTION_NOTIFICATION_TYPE varchar(32) not null,
    SUBSCRIPTION_STATE varchar(32) not null,
    CREATION_TIME date not null,
    PRIMARY KEY ( ID)
);

DROP TABLE ZIP_DATA;
CREATE TABLE `ZIP_DATA` (   `ZIP_CODE` varchar(10) NOT NULL,   
`STATE_ABBREVIATION` varchar(2) NOT NULL,   
`LATTITUDE` float,  
 `LONGITUDE` float NOT NULL,   
`CITY` varchar(256) DEFAULT NULL,   
`STATE` varchar(256) NOT NULL,   
PRIMARY KEY (`ZIP_CODE`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



load data local INFILE "data/zips.csv"
   into table ZIP_DATA
   columns terminated by ', '
   enclosed by '"'
   ignore 1 lines
(zip_code, STATE_ABBREVIATION, LATTITUDE, LONGITUDE, CITY, STATE);
commit;