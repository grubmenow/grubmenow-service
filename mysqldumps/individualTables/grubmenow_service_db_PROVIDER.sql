-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: grubmenow-db.ccaypnjyvorr.us-west-2.rds.amazonaws.com    Database: grubmenow_service_db
-- ------------------------------------------------------
-- Server version	5.6.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `PROVIDER`
--

DROP TABLE IF EXISTS `PROVIDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROVIDER` (
  `PROVIDER_ID` varchar(16) NOT NULL,
  `PROVIDER_NAME` varchar(128) NOT NULL,
  `PROVIDER_EMAIL_ID` varchar(64) NOT NULL,
  `PROVIDER_ADDRESS_STREET_NUMBER` varchar(8) NOT NULL,
  `PROVIDER_ADDRESS_STREET` varchar(256) NOT NULL,
  `PROVIDER_ADDRESS_APARTMENT_NUMBER` varchar(16) DEFAULT NULL,
  `PROVIDER_ADDRESS_ZIP_CODE` varchar(16) DEFAULT NULL,
  `PROVIDER_ADDRESS_STATE` varchar(2) DEFAULT NULL,
  `PROVIDER_ADDRESS_CITY` varchar(32) DEFAULT NULL,
  `PROVIDER_PHONE_NUMBER` varchar(128) DEFAULT NULL,
  `PROVIDER_IMAGE_URL` varchar(256) DEFAULT NULL,
  `TOTAL_RATING_POINTS` int(11) NOT NULL,
  `NUMBER_OF_RATINGS` int(11) NOT NULL,
  `IS_ONLINE_PAYMENT_ACCEPTED` tinyint(1) DEFAULT '0',
  `IS_CASH_ON_DELIVERY_PAYMENT_ACCEPTED` tinyint(1) DEFAULT '0',
  `IS_CARD_ON_DELIVERY_PAYMENT_ACCEPTED` tinyint(1) DEFAULT '0',
  `PROVIDER_STATE` varchar(32) NOT NULL,
  PRIMARY KEY (`PROVIDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROVIDER`
--

LOCK TABLES `PROVIDER` WRITE;
/*!40000 ALTER TABLE `PROVIDER` DISABLE KEYS */;
INSERT INTO `PROVIDER` VALUES ('dsrulpnr','Avika\'s Kitchen','amrita.uki@gmail.com','4755','148th Ave NE','R102','98007','WA','Bellevue','+1-425-247-8623',NULL,5,1,1,1,0,'ACTIVE'),('egttgyqd','Shilpa\'s Kitchen','sheelpa.reddy@gmail.com','18710','NE 59th CT','G 2053','98052','WA','Redmond','4258949026',NULL,0,0,1,1,0,'ACTIVE'),('envdsgys','Padmavathi\'s Kitchen','pchinthagunti@gmail.com','4715','148th Ave NE','','98007','WA','Bellevue','+1425-406-0544',NULL,0,0,1,1,0,'ACTIVE'),('fdcsaroi','Ruchi\'s Kitchen','ruchi_viky@hotmail.com','23939','SE 7th lane','','98074','WA','Sammamish','+1-425-615-9790',NULL,0,0,1,1,0,'ACTIVE'),('ffthkfgl','Reet\'s Rasoi','aakankshasuhane@gmail.com','5029','148th Ave NE','K102','98007','WA','Bellevue','4252197262',NULL,5,1,1,1,0,'ACTIVE'),('fvhkzqpb','Mumbai Masala','kinjalnpokar@gmail.com','4723','148th AVE NE','DD 201','98007','WA','Bellevue','+1 206-915-9047',NULL,0,0,1,1,0,'ACTIVE'),('gfscdnkz','Amma\'s Kichen','kavyashreekp@gmail.com','4631','148th Ave NE','QQ103','98007','WA','Bellevue','2068836876',NULL,0,0,1,1,0,'ACTIVE'),('gikzfilc','Kuvam\'zz Rasoi','kuvamsidhu@gmail.com','15205','NE, 16th PL','11','98007','WA','bellevue','4254297748',NULL,0,0,1,1,0,'ACTIVE'),('gwgsjsmz','Neha\'s Kitchen','jain.rich144@gmail.com','15216','NE 16th place','','98007','WA','Bellevue','+1 425-638-3552',NULL,5,1,1,1,0,'ACTIVE'),('jjkpiirq','Gori\'s Kitchen','abhijeet-pugalia@hotmail.com','4727','148th Ave NE','CC303','98007','WA','Bellevue','+1-425-429-7762',NULL,15,3,1,1,0,'ACTIVE'),('lnatuwjp','Sonali\'s Kitchen','Sonalitandon87@yahoo.com','4513 ','148th ave NE','MM-201','98007','WA','Bellevue','4253268059',NULL,0,0,1,1,0,'ACTIVE'),('mkqhswry','Andhra Vantalu','redrose.daffodil@gmail.com','4515','148th Ave NE','JJ104','98007','WA','Bellevue','+1-425-247-4046',NULL,0,0,1,1,0,'ACTIVE'),('mvckvswm','Leena\'s Kitchen','leena2923@gmail.com','6012','188th lane ne','103','98052','WA','Redmond','+1 425-922-6901',NULL,5,1,1,1,0,'ACTIVE'),('N3WJ4bns','Kapila\'s Kitchen','kapila.jain86@gmail.com','4631','148th AVE NE','QQ 202','98007','WA','Bellevue','+1 206-218-4126',NULL,5,1,1,1,0,'ACTIVE'),('pikzwffc','Nisha\'s Kitchen','sohelahuja@gmail.com','7741','152nd Ave NE','','98052','WA','Redmond','3233089103',NULL,0,0,1,1,0,'ACTIVE'),('wkhwulkt','Punjabi Tadka','nancy.vatsa@gmail.com','4723','148TH AVE NE','DD 101','98007','WA','Bellevue','4259745242',NULL,0,0,1,1,0,'ACTIVE'),('xbwmdbdc','Susmitha\'s Kitchen','susmithapatturi@gmail.com','14625','Ne 34th St','G#13','98007','WA','Bellevue','+1 425-996-8298 / +1 425-503-9681',NULL,5,1,1,1,0,'ACTIVE'),('zvpZa8ub','Kavya\'s Kitchen','kavyashreekp@gmail.com','4275','148th AVE NE','F 102','98007','WA','Bellevue','+1 206-883-6875',NULL,30,7,1,1,0,'ACTIVE');
/*!40000 ALTER TABLE `PROVIDER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-15 15:55:28
