-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: grubmenow-db.ccaypnjyvorr.us-west-2.rds.amazonaws.com    Database: gbn-service-test
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
-- Table structure for table `CUSTOMER`
--

DROP TABLE IF EXISTS `CUSTOMER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CUSTOMER` (
  `CUSTOMER_ID` varchar(32) NOT NULL,
  `CUSTOMER_FIRST_NAME` varchar(128) DEFAULT NULL,
  `CUSTOMER_LAST_NAME` varchar(128) DEFAULT NULL,
  `CUSTOMER_EMAIL_ID` varchar(64) NOT NULL,
  `CUSTOMER_PHONE_NUMBER` varchar(20) DEFAULT NULL,
  `CUSTOMER_STATE` varchar(32) NOT NULL,
  PRIMARY KEY (`CUSTOMER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CUSTOMER`
--

LOCK TABLES `CUSTOMER` WRITE;
/*!40000 ALTER TABLE `CUSTOMER` DISABLE KEYS */;
INSERT INTO `CUSTOMER` VALUES ('10152357727027016','yashwanth','p','yashwanthcp@gmail.com','2068836875','ACTIVE'),('10152629274705698','Ravi','S.Math','ravismath@gmail.com',NULL,'ACTIVE'),('10152763424501327','Vikas','Jain','mustvicky@gmail.com','+1 206-218-2329','ACTIVE'),('10152767095066327','Vikas','Jain','mustvicky+gbn@gmail.com','+1 206 218 2328','ACTIVE'),('10152843609422975','Nitin','Pokar','pokar.nitin@gmail.com',NULL,'ACTIVE'),('10152863048180589','Chetan','Raghavendra','chetanra@live.com',NULL,'ACTIVE'),('10152891832357975','Nitin','Pokar','pokar.nitin@gmail.com','2069159046','ACTIVE'),('10152954099022975','Nitin','Pokar','pokar.nitin@gmail.com','2069159046','ACTIVE'),('10154781149580271','Sohel','Ahuja','sohelahuja@gmail.com',NULL,'ACTIVE'),('10154788988205271','Sohel','Ahuja','sohelahuja@gmail.com',NULL,'ACTIVE'),('10154866700335391','Jeetu','Mirchandani','jeetum@gmail.com',NULL,'ACTIVE'),('10155084137480296','Vikash','Kumar','jainvikashraj@gmail.com','','ACTIVE'),('10200093409013705','Kavya','Yash','kavyashreekp@gmail.com','2068836875','ACTIVE'),('10204194280290816','Kapila','Jain','kapila.jain86@gmail.com',NULL,'ACTIVE'),('874970270','Sohel','Ahuja','sohelahuja@gmail.com',NULL,'ACTIVE'),('965011763528223','Abhijeet','Pugalia','abhijeet15oct@gmail.com',NULL,'ACTIVE'),('971712746183559','Vimalanandan','Thangamani','vimal.psgit@gmail.com','4845152776','ACTIVE');
/*!40000 ALTER TABLE `CUSTOMER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-15 15:55:12
