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
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
INSERT INTO `attachment` VALUES (6,'c0861_assignment01.pdf','http://localhost:8080/attachment/paper/1388587299575c0861_assignment01.pdf',9,19,0),(7,'c0861_assignment02.pdf','http://localhost:8080/attachment/paper/1388587364368c0861_assignment02.pdf',9,20,0),(8,'c0861_lecture03.pdf','http://localhost:8080/attachment/paper/1388587435854c0861_lecture03.pdf',9,21,0);
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
INSERT INTO `attribute` VALUES (1,'编辑',1),(2,'ISBN',1),(3,'期刊名',1),(4,'卷',1),(5,'期',1),(6,'地点',1),(7,'DOI',1),(8,'待解决问题',2),(9,'主要思路',2),(10,'实验结果',2),(11,'贡献',2),(12,'改进',2),(13,'简单提及',3),(14,'相关',3),(15,'使用',3),(16,'对比',3),(17,'不确定',3),(18,'导师',1),(19,'属性一',1),(20,'属性二',1),(21,'属性三',1),(22,'属性四',1),(30,'关系三',3);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='根据attribute表，动态的详细评论表';
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literature`
--

LOCK TABLES `literature` WRITE;
/*!40000 ALTER TABLE `literature` DISABLE KEYS */;
INSERT INTO `literature` VALUES (19,9,9,0,1),(20,9,0,0,1),(21,9,0,0,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literature_publisher`
--

LOCK TABLES `literature_publisher` WRITE;
/*!40000 ALTER TABLE `literature_publisher` DISABLE KEYS */;
INSERT INTO `literature_publisher` VALUES (16,19,115),(17,20,115),(18,21,116);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literatureattribute`
--

LOCK TABLES `literatureattribute` WRITE;
/*!40000 ALTER TABLE `literatureattribute` DISABLE KEYS */;
INSERT INTO `literatureattribute` VALUES (1,19,1,'编辑','未知'),(2,19,2,'ISBN','1111'),(3,20,1,'编辑','未知'),(4,20,2,'ISBN','1111'),(5,21,1,'编辑','未知'),(6,21,2,'ISBN','11223');
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
INSERT INTO `literaturemeta` VALUES (19,'傲慢与偏见','挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书挺好的书','william','1997','小说','www','30-32'),(20,'红与黑','很好很好','cancy','1991','小说；图书','www','30'),(21,'包法利夫人','你好啊你好啊','author','1947','小说','www','30');
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
INSERT INTO `literaturetype` VALUES (1,'图书'),(2,'图书章节'),(3,'期刊'),(4,'会议'),(5,'学位论文'),(6,'技术报告'),(7,'在线资源'),(8,'type1'),(9,'type2'),(10,'type3'),(11,'type4');
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
INSERT INTO `literaturetype_attribute` VALUES (1,1,1,1),(2,1,2,1),(3,3,3,1),(4,3,4,1),(5,3,5,1),(6,4,6,1),(7,4,7,1),(8,2,1,1),(9,3,7,1),(10,5,18,1),(11,8,19,1),(12,8,20,0),(15,8,21,1),(16,9,19,1),(17,9,20,1),(18,8,22,0),(27,10,19,1),(28,10,20,1);
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
INSERT INTO `publisher` VALUES (115,'上海文艺出版社'),(116,'南京大学出版社');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `user` VALUES (9,'test','123456'),(10,'admin','admin'),(11,'hello','123456'),(12,'hi','123'),(15,'hihi','123');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-01-01 23:48:57
