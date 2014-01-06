-- MySQL dump 10.13  Distrib 5.5.15, for Win32 (x86)
--
-- Host: localhost    Database: manage_schema
-- ------------------------------------------------------
-- Server version	5.5.15

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
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `link` varchar(500) NOT NULL,
  `creatorid` int(11) NOT NULL,
  `literatureid` int(11) NOT NULL,
  `type` int(3) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '属性名',
  `type` int(2) NOT NULL COMMENT '属性的类别：引用属性、信息属性、评价属性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES (1,'编辑',1),(2,'ISBN',1),(3,'期刊名',1),(4,'卷',1),(5,'期',1),(6,'地点',1),(7,'DOI',1),(8,'待解决问题',2),(9,'主要思路',2),(10,'实验结果',2),(11,'贡献',2),(12,'改进',2),(13,'简单提及',3),(14,'相关',3),(15,'使用',3),(16,'对比',3),(17,'不确定',3);
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cited`
--

DROP TABLE IF EXISTS `cited`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cited` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `literatureid` int(11) NOT NULL,
  `citedbyid` int(11) NOT NULL,
  `citedtypeid` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cited`
--

LOCK TABLES `cited` WRITE;
/*!40000 ALTER TABLE `cited` DISABLE KEYS */;
/*!40000 ALTER TABLE `cited` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commenterid` int(11) NOT NULL,
  `literatureid` int(11) NOT NULL,
  `short_content` varchar(1000) NOT NULL,
  `score` int(11) DEFAULT '0',
  `status` int(11) NOT NULL,
  `commenttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commentattribute`
--

DROP TABLE IF EXISTS `commentattribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commentattribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `literatureid` int(11) NOT NULL,
  `attributeid` int(11) NOT NULL,
  `attributename` varchar(20) NOT NULL,
  `value` varchar(1024) NOT NULL,
  `commenterid` int(11) NOT NULL,
  `commenttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='根据attribute表，动态的详细评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commentattribute`
--

LOCK TABLES `commentattribute` WRITE;
/*!40000 ALTER TABLE `commentattribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `commentattribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `label`
--

DROP TABLE IF EXISTS `label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `createid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `label`
--

LOCK TABLES `label` WRITE;
/*!40000 ALTER TABLE `label` DISABLE KEYS */;
/*!40000 ALTER TABLE `label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `label_literature`
--

DROP TABLE IF EXISTS `label_literature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `label_literature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `literatureid` int(11) NOT NULL,
  `labelid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `label_literature`
--

LOCK TABLES `label_literature` WRITE;
/*!40000 ALTER TABLE `label_literature` DISABLE KEYS */;
/*!40000 ALTER TABLE `label_literature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `literature`
--

DROP TABLE IF EXISTS `literature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `literature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorid` int(11) NOT NULL DEFAULT '0',
  `updaterid` int(11) NOT NULL DEFAULT '0',
  `status` tinyint(1) NOT NULL,
  `literaturetypeid` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literature`
--

LOCK TABLES `literature` WRITE;
/*!40000 ALTER TABLE `literature` DISABLE KEYS */;
/*!40000 ALTER TABLE `literature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `literature_publisher`
--

DROP TABLE IF EXISTS `literature_publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `literature_publisher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `literatureid` int(11) NOT NULL,
  `publisherid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literature_publisher`
--

LOCK TABLES `literature_publisher` WRITE;
/*!40000 ALTER TABLE `literature_publisher` DISABLE KEYS */;
/*!40000 ALTER TABLE `literature_publisher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `literatureattribute`
--

DROP TABLE IF EXISTS `literatureattribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `literatureattribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `literatureid` int(11) NOT NULL,
  `attributeid` int(11) NOT NULL,
  `attributename` varchar(20) NOT NULL,
  `value` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literatureattribute`
--

LOCK TABLES `literatureattribute` WRITE;
/*!40000 ALTER TABLE `literatureattribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `literatureattribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `literaturemeta`
--

DROP TABLE IF EXISTS `literaturemeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `literaturemeta` (
  `literatureid` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `literature_abstract` varchar(1000) NOT NULL,
  `author` varchar(300) NOT NULL,
  `published_year` varchar(100) NOT NULL,
  `key_words` varchar(300) NOT NULL,
  `link` varchar(1024) NOT NULL,
  `pages` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`literatureid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literaturemeta`
--

LOCK TABLES `literaturemeta` WRITE;
/*!40000 ALTER TABLE `literaturemeta` DISABLE KEYS */;
/*!40000 ALTER TABLE `literaturemeta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `literaturetype`
--

DROP TABLE IF EXISTS `literaturetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `literaturetype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literaturetype`
--

LOCK TABLES `literaturetype` WRITE;
/*!40000 ALTER TABLE `literaturetype` DISABLE KEYS */;
INSERT INTO `literaturetype` VALUES (1,'图书'),(2,'图书章节'),(3,'期刊'),(4,'会议'),(5,'学位论文'),(6,'技术报告'),(7,'在线资源');
/*!40000 ALTER TABLE `literaturetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `literaturetype_attribute`
--

DROP TABLE IF EXISTS `literaturetype_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `literaturetype_attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `literaturetypeid` int(11) NOT NULL,
  `attributeid` int(11) NOT NULL,
  `ismust` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literaturetype_attribute`
--

LOCK TABLES `literaturetype_attribute` WRITE;
/*!40000 ALTER TABLE `literaturetype_attribute` DISABLE KEYS */;
INSERT INTO `literaturetype_attribute` VALUES (1,1,1,1),(2,1,2,1),(3,3,3,1),(4,3,4,1),(5,3,5,1),(6,4,6,1),(7,4,7,1),(8,2,1,1),(9,3,7,1);
/*!40000 ALTER TABLE `literaturetype_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publisher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` VALUES (1,'上海文艺出版社'),(2,'南京大学出版社');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `statistics`
--

DROP TABLE IF EXISTS `statistics`;
/*!50001 DROP VIEW IF EXISTS `statistics`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `statistics` (
  `Userid` int(10) unsigned,
  `Account` varchar(32),
  `Name` varchar(45),
  `Cliterature` bigint(21),
  `CAttachment` bigint(21),
  `CSimple` bigint(21),
  `CComplex` bigint(21),
  `W_Cliterature` bigint(21),
  `W_CAttachment` bigint(21),
  `W_CSimple` bigint(21),
  `W_CComplex` bigint(21),
  `M_Cliterature` bigint(21),
  `M_CAttachment` bigint(21),
  `M_CSimple` bigint(21),
  `M_CComplex` bigint(21),
  `H_Cliterature` bigint(21),
  `H_CAttachment` bigint(21),
  `H_CSimple` bigint(21),
  `H_CComplex` bigint(21),
  `Y_Cliterature` bigint(21),
  `Y_CAttachment` bigint(21),
  `Y_CSimple` bigint(21),
  `Y_CComplex` bigint(21)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin'),(2,'test','123456');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `account` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `major` varchar(45) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,2,'test','测试','软件工程','mf1332000@software.nju.edu.cn');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `statistics`
--

/*!50001 DROP TABLE IF EXISTS `statistics`*/;
/*!50001 DROP VIEW IF EXISTS `statistics`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `statistics` AS (select `u`.`id` AS `Userid`,`u`.`account` AS `Account`,(select `us`.`name` from `user_info` `us` where (`us`.`userid` = `u`.`id`)) AS `Name`,(select count(0) from `literature` where (`literature`.`creatorid` = `u`.`id`)) AS `Cliterature`,(select count(0) from `attachment` where (`attachment`.`creatorid` = `u`.`id`)) AS `CAttachment`,(select count(0) from `comment` where (`comment`.`commenterid` = `u`.`id`)) AS `CSimple`,(select count(distinct `commentattribute`.`commenttime`) from `commentattribute` where (`commentattribute`.`commenterid` = `u`.`id`)) AS `CComplex`,(select count(0) from `literature` where ((`literature`.`creatorid` = `u`.`id`) and (`literature`.`createtime` >= (now() - interval 7 day)))) AS `W_Cliterature`,(select count(0) from `attachment` where ((`attachment`.`creatorid` = `u`.`id`) and (`attachment`.`createtime` >= (now() - interval 7 day)))) AS `W_CAttachment`,(select count(0) from `comment` where ((`comment`.`commenterid` = `u`.`id`) and (`comment`.`commenttime` >= (now() - interval 7 day)))) AS `W_CSimple`,(select count(distinct `commentattribute`.`commenttime`) from `commentattribute` where ((`commentattribute`.`commenterid` = `u`.`id`) and (`commentattribute`.`commenttime` >= (now() - interval 7 day)))) AS `W_CComplex`,(select count(0) from `literature` where ((`literature`.`creatorid` = `u`.`id`) and (`literature`.`createtime` >= (now() - interval 1 month)))) AS `M_Cliterature`,(select count(0) from `attachment` where ((`attachment`.`creatorid` = `u`.`id`) and (`attachment`.`createtime` >= (now() - interval 1 month)))) AS `M_CAttachment`,(select count(0) from `comment` where ((`comment`.`commenterid` = `u`.`id`) and (`comment`.`commenttime` >= (now() - interval 1 month)))) AS `M_CSimple`,(select count(distinct `commentattribute`.`commenttime`) from `commentattribute` where ((`commentattribute`.`commenterid` = `u`.`id`) and (`commentattribute`.`commenttime` >= (now() - interval 1 month)))) AS `M_CComplex`,(select count(0) from `literature` where ((`literature`.`creatorid` = `u`.`id`) and (`literature`.`createtime` >= (now() - interval 0.5 year)))) AS `H_Cliterature`,(select count(0) from `attachment` where ((`attachment`.`creatorid` = `u`.`id`) and (`attachment`.`createtime` >= (now() - interval 0.5 year)))) AS `H_CAttachment`,(select count(0) from `comment` where ((`comment`.`commenterid` = `u`.`id`) and (`comment`.`commenttime` >= (now() - interval 0.5 year)))) AS `H_CSimple`,(select count(distinct `commentattribute`.`commenttime`) from `commentattribute` where ((`commentattribute`.`commenterid` = `u`.`id`) and (`commentattribute`.`commenttime` >= (now() - interval 0.5 year)))) AS `H_CComplex`,(select count(0) from `literature` where ((`literature`.`creatorid` = `u`.`id`) and (`literature`.`createtime` >= (now() - interval 1 year)))) AS `Y_Cliterature`,(select count(0) from `attachment` where ((`attachment`.`creatorid` = `u`.`id`) and (`attachment`.`createtime` >= (now() - interval 1 year)))) AS `Y_CAttachment`,(select count(0) from `comment` where ((`comment`.`commenterid` = `u`.`id`) and (`comment`.`commenttime` >= (now() - interval 1 year)))) AS `Y_CSimple`,(select count(distinct `commentattribute`.`commenttime`) from `commentattribute` where ((`commentattribute`.`commenterid` = `u`.`id`) and (`commentattribute`.`commenttime` >= (now() - interval 1 year)))) AS `Y_CComplex` from `user` `u`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-01-05 22:34:03
