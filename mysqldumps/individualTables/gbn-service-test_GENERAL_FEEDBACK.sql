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
-- Table structure for table `GENERAL_FEEDBACK`
--

DROP TABLE IF EXISTS `GENERAL_FEEDBACK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GENERAL_FEEDBACK` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FEEDBACK_TYPE` varchar(50) NOT NULL,
  `ZIP_CODE` varchar(10) DEFAULT NULL,
  `EMAIL_ID` varchar(64) DEFAULT NULL,
  `MESSAGE` varchar(1024) DEFAULT NULL,
  `FEEDBACK_PROVIDED_DATE` date NOT NULL,
  `SEARCH_DAY` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GENERAL_FEEDBACK`
--

LOCK TABLES `GENERAL_FEEDBACK` WRITE;
/*!40000 ALTER TABLE `GENERAL_FEEDBACK` DISABLE KEYS */;
INSERT INTO `GENERAL_FEEDBACK` VALUES (1,'SEARCH_GENERAL_FEEDBACK','98007','','hello','2014-11-30',NULL),(2,'SEARCH_GENERAL_FEEDBACK','98007','','asd','2014-11-30',NULL),(3,'CONTACT',NULL,NULL,NULL,'2014-11-30',NULL),(4,'CONTACT',NULL,'vj',NULL,'2014-11-30',NULL),(5,'CONTACT','98007','vj@',NULL,'2014-11-30',NULL),(6,'CONTACT','98007','vja@','hello\n\nhi o\n\n-Vikas','2014-11-30',NULL),(7,'CONTACT','98007','pokar.nitin@gmail.com','Hi, This is test','2014-11-30',NULL),(8,'SEARCH_GENERAL_FEEDBACK','98007','','asd','2014-12-19',NULL),(9,'CONTACT','98007','xyz@gmail.com','I Like the website!!','2014-12-20',NULL),(10,'CONTACT','98007','xyz@gmail.com','I Like the website!!','2014-12-20',NULL),(11,'CONTACT','asd','asd','asdasds','2014-12-20',NULL),(12,'CONTACT','98008','kavyashreekp@gmail.com','Test message','2014-12-20',NULL),(13,'SEARCH_GENERAL_FEEDBACK','98007','','the website is fantastic - vikas','2014-12-23',NULL),(14,'NO_RESULTS_FEEDBACK','98033','test@am.com','suggestions','2014-12-28',NULL),(15,'NO_RESULTS_FEEDBACK','98033','asd.','asd','2014-12-28',NULL),(16,'NO_RESULTS_FEEDBACK','98033','asd','asd','2014-12-28',NULL),(17,'NO_RESULTS_FEEDBACK','98033','asd@','asd','2014-12-28',NULL),(18,'NO_RESULTS_FEEDBACK','98033','sad@asd','asd','2014-12-28',NULL),(19,'CONTACT','98007','paparao.ch@gmail.com','Trying to test website:)','2014-12-31',NULL),(20,'NO_RESULTS_FEEDBACK','98032','','','2014-12-31',NULL),(21,'NO_RESULTS_FEEDBACK','98033','','','2014-12-31',NULL),(22,'CONTACT','98033','chetanra@live.com','Please cancel order 660214324688.  Will definitely use the site in future.  Thx.','2015-01-01',NULL),(23,'NO_RESULTS_FEEDBACK','9700o','','','2015-01-01',NULL),(24,'NO_RESULTS_FEEDBACK','98007','mustvicky@gmail.com','resas','2015-01-01',NULL),(25,'NO_RESULTS_FEEDBACK','98102','','','2015-01-01',NULL),(26,'NO_RESULTS_FEEDBACK','90007','','','2015-01-07',NULL),(27,'NO_RESULTS_FEEDBACK','98007','','','2015-01-08',NULL),(28,'NO_RESULTS_FEEDBACK','09787','','','2015-01-09',NULL),(29,'NO_RESULTS_FEEDBACK','98052','','no results :(','2015-01-10',NULL),(30,'NO_RESULTS_FEEDBACK','98007','','Curry','2015-01-14',NULL),(31,'NO_RESULTS_FEEDBACK','98007','','Idli','2015-01-14',NULL),(32,'NO_RESULTS_FEEDBACK','98052',NULL,'','2015-04-18','Today'),(33,'NO_RESULTS_FEEDBACK','98052',NULL,'','2015-04-18','Today'),(34,'NO_RESULTS_FEEDBACK','98052',NULL,'','2015-04-18','Today'),(35,'NO_RESULTS_FEEDBACK','98052',NULL,'','2015-04-18','Today'),(36,'NO_RESULTS_FEEDBACK','98052',NULL,'','2015-04-18','Today'),(37,'NO_RESULTS_FEEDBACK','98052',NULL,'','2015-04-18','Today'),(38,'NO_RESULTS_FEEDBACK','98052','sad@as.com','','2015-04-18','Today'),(39,'NO_RESULTS_FEEDBACK','98052',NULL,'','2015-04-18','Today'),(40,'NO_RESULTS_FEEDBACK','98052',NULL,'','2015-04-18','Today'),(41,'NO_RESULTS_FEEDBACK','98052','asdasd@f.com','','2015-04-18','Today'),(42,'NO_RESULTS_FEEDBACK','98052','asd@asd.sd.com','','2015-04-18','Today'),(43,'NO_RESULTS_FEEDBACK','98052','mustvicky@gmail.com','nice website, but no food today on integ website?','2015-04-18','Today');
/*!40000 ALTER TABLE `GENERAL_FEEDBACK` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-15 15:55:14
