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
-- Table structure for table `SEARCH_SUGGESTION_FEEDBACK`
--

DROP TABLE IF EXISTS `SEARCH_SUGGESTION_FEEDBACK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEARCH_SUGGESTION_FEEDBACK` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SEARCH_SUGGESTION_FOOD_ITEM_NAME` varchar(256) NOT NULL,
  `ZIP_CODE` varchar(10) DEFAULT NULL,
  `EMAIL_ID` varchar(64) DEFAULT NULL,
  `FEEDBACK_PROVIDED_DATE` date NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEARCH_SUGGESTION_FEEDBACK`
--

LOCK TABLES `SEARCH_SUGGESTION_FEEDBACK` WRITE;
/*!40000 ALTER TABLE `SEARCH_SUGGESTION_FEEDBACK` DISABLE KEYS */;
INSERT INTO `SEARCH_SUGGESTION_FEEDBACK` VALUES (1,'rice','98007','coo@c.com','2014-10-29'),(2,'rice','98007','a@as.com','2014-10-29'),(3,' papad','98007','a@as.com','2014-10-29'),(4,' podina','98007','a@as.com','2014-10-29'),(5,' roti stack','98007','a@as.com','2014-10-29'),(6,'asd','98007','asdads','2014-10-29'),(7,'vada pav','98007','yashwanthcp@gmail.com','2014-11-02'),(8,'abc','98007','abc@gmail.com','2014-11-02'),(9,' def','98007','abc@gmail.com','2014-11-02'),(10,'pasta','98007','yashwanthcp@gmail.com','2014-11-03'),(11,'roti stack','98007','mye','2014-11-09'),(12,'sad','98007','asd','2014-11-09'),(13,'Nothing in particular','98007','yashwanthcp@gmail.com','2014-11-29'),(14,'roti sabzi','98109','xyz@am.com','2014-12-19'),(15,'a','98109','as','2014-12-19'),(16,'as','98109','as','2014-12-19'),(17,'as','98109','as@gma','2014-12-19'),(18,'asd','98007','asd','2014-12-19'),(19,'as','98109','','2014-12-19'),(20,'as','98','asd','2014-12-19'),(21,'adsasd','98007','ads','2014-12-19'),(22,'aalu paratha','98007','','2014-12-27'),(23,'food items - test comment','98033','myemail@gmai;.cpom','2014-12-28'),(24,'fooditems','98033','asd@gmail.com','2014-12-28');
/*!40000 ALTER TABLE `SEARCH_SUGGESTION_FEEDBACK` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-15 15:55:29
