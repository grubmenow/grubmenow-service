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
-- Table structure for table `FOOD_ITEM`
--

DROP TABLE IF EXISTS `FOOD_ITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FOOD_ITEM` (
  `FOOD_ITEM_ID` varchar(16) NOT NULL,
  `FOOD_ITEM_NAME` varchar(256) NOT NULL,
  `FOOD_ITEM_IMAGE_URL` varchar(256) NOT NULL,
  `FOOD_ITEM_DESCRIPTION` varchar(1024) NOT NULL,
  `FOOD_ITEM_DESCRIPTION_TAGS` varchar(1024) DEFAULT NULL,
  `FOOD_ITEM_STATE` varchar(32) NOT NULL,
  PRIMARY KEY (`FOOD_ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FOOD_ITEM`
--

LOCK TABLES `FOOD_ITEM` WRITE;
/*!40000 ALTER TABLE `FOOD_ITEM` DISABLE KEYS */;
INSERT INTO `FOOD_ITEM` VALUES ('ariunz','Curd','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/curd.jpg','','','ACTIVE'),('asG8yWfL','Akki Roti','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/akkiroti.jpg','',NULL,'ACTIVE'),('bbtpkg','Aloo Fry','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Aloo-Fry.jpg','',NULL,'ACTIVE'),('bdjlze','Rice Kheer','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Rice-Kheer.jpg','',NULL,'ACTIVE'),('bignvr','Gobhi Sabji','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/gobi+sabji.jpg','','','ACTIVE'),('clkcpq','Tofu Burji','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/tofu_burji.jpg','','','ACTIVE'),('czedfk','Poha','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Poha.jpg','',NULL,'ACTIVE'),('dcpnzp','Dal Dhokli','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/dal-dhokli.jpg','','','ACTIVE'),('ecnyyn','Mushroom Curry','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/mushroom_curry.jpg','','','ACTIVE'),('enwfzb','Pani Puri','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/pani-puri.jpg','',NULL,'ACTIVE'),('ezxqxh','Tomato Pachadi','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Tomato-Pachadi.jpg','',NULL,'ACTIVE'),('farkxm','Aloo Paratha','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/aloo-paratha.jpeg','','','ACTIVE'),('fmCvRCQs','Curd Rice','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/curd-rice.jpg','',NULL,'ACTIVE'),('gngnuh','Vegetable Puff','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Vegetable-Puff.jpg','',NULL,'ACTIVE'),('grtumr','Pav Bhaji','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/street-food-pav-bhaji.jpg','',NULL,'ACTIVE'),('hedivn','Daal fry','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Daal-fry.jpg','',NULL,'ACTIVE'),('hgwkbm','Vegetable Pulao','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Vegetable-Pulao.jpg','',NULL,'ACTIVE'),('hnokyc','Lauki Chana Dal','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Lauki-Chana-Dal.jpg','',NULL,'ACTIVE'),('hrcszm','Besan Ladoo','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Besan-Ladoo.jpg','',NULL,'ACTIVE'),('ikbpdx','Moong dal curry','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Moong-dal-curry.jpg','',NULL,'ACTIVE'),('imgrru','Paneer Butter Masala','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Paneer-Butter-Masala.jpg','',NULL,'ACTIVE'),('iriloz','Aloo Gobhi','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Aloo-Gobhi.jpg','',NULL,'ACTIVE'),('IRktHivU','Thali','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/thali.jpg','',NULL,'ACTIVE'),('jayvzv','Gatte','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Gatte.jpg','',NULL,'ACTIVE'),('kikuha','Lachha Paratha','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/laccha-paratha.jpg','','','ACTIVE'),('lfxljt','Kofta Curry','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Kofta-Curry.jpg','',NULL,'ACTIVE'),('mayyws','Masala karela','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Masala-karela.jpg','',NULL,'ACTIVE'),('mboodq','Paneer Paratha',' https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/aloo-paratha.jpeg','','','ACTIVE'),('nfcwgh','Spicy Puffed Rice','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Spicy-Puffed-Rice.jpg','',NULL,'ACTIVE'),('nsbkck','Plain Rice','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/plain-rice.jpeg','','','ACTIVE'),('nvcloi','Mix Veg Curry','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Mix-Veg-Curry.jpg','',NULL,'ACTIVE'),('otunol','Besan Chillah','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Besan-Chillah.jpg','',NULL,'ACTIVE'),('ovrnrm','Gobhi Paratha',' https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/aloo-paratha.jpeg','','','ACTIVE'),('ppozil','Roti','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Rotli.jpg','','','ACTIVE'),('qanicl','Rajma curry','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Rajma-curry.jpg','',NULL,'ACTIVE'),('rawyka','Daal','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/daal.jpg','','','ACTIVE'),('rznfxo','Rava Dhokla','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/rava-dhokla.jpg','','','ACTIVE'),('sbftrt','Gajar Halwa','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Gajar-Halwa.jpg','',NULL,'ACTIVE'),('svvjpa','Ajwain cookies','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Ajwain-cookies.jpg','',NULL,'ACTIVE'),('swqzbj','Pasta','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Pasta.jpg','',NULL,'ACTIVE'),('tgymtm','Kadi Pakoda','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Kadi-Pakoda.jpg','',NULL,'ACTIVE'),('ttjaed','Chole','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Chole.jpg','',NULL,'ACTIVE'),('txldbt','Navratan Korma','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/navaratan-korma.jpg','','','ACTIVE'),('TyRFXb57','Idly','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/idly.jpg','',NULL,'ACTIVE'),('ujvjnk','Palak Paneer','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/palak-paneer.jpg','','','ACTIVE'),('V0JIoy6w','Vegetable Fried Rice','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/vegetable-fried-rice.jpg','',NULL,'ACTIVE'),('varxsz','Corn Palak','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Corn-Palak.jpg','',NULL,'ACTIVE'),('vjiyhq','Mooli Paratha','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Mooli-Paratha.jpg','',NULL,'ACTIVE'),('vuyufe','Palak Poori','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Palak-Poori.jpg','',NULL,'ACTIVE'),('vyuyiq','Aloo Methi','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/aloo+methi.jpg','','','ACTIVE'),('woqmna','Chicken Pepper Fry','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Chicken-Pepper-Fry.jpg','',NULL,'ACTIVE'),('wrdtL0dg','Upma','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/upma.jpg','',NULL,'ACTIVE'),('wxxnfa','Fried Idly','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Fried-Idly.jpg','',NULL,'ACTIVE'),('xmyocy','Rajasthani gatte sabji','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Rajasthani-gatte-sabji.jpg','',NULL,'ACTIVE'),('xscvvw','Set Dosa','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/set+dosa.jpg','','','ACTIVE'),('ydscqi','Methi paratha','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Methi-paratha.jpg','',NULL,'ACTIVE'),('zlllsu','Grated mango pickle','https://s3-us-west-2.amazonaws.com/grubmenow.com/img/items/Grated-mango-pickle.jpg','',NULL,'ACTIVE');
/*!40000 ALTER TABLE `FOOD_ITEM` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-15 15:55:15
