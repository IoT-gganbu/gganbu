drop database if exists gganbu;
create database gganbu default character set utf8 collate utf8_general_ci;
USE `gganbu`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gganbu
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
                           `patient_id` bigint NOT NULL AUTO_INCREMENT,
                           `age` int NOT NULL,
                           `gender` int NOT NULL,
                           `is_checkup` tinyint(1) NOT NULL DEFAULT '0',
                           `name` varchar(255) NOT NULL,
                           `resident_no` varchar(255) NOT NULL,
                           `tel` varchar(255) NOT NULL,
                           PRIMARY KEY (`patient_id`),
                           UNIQUE KEY `UK_myf6id8q6ktu4barwljm0q3p2` (`resident_no`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,27,0,0,'김철수1','960611-1111111','010-2729-6604'),(2,27,0,0,'김철수2','960611-1111112','010-2729-6604'),(3,27,0,0,'김철수3','960611-1111113','010-2729-6604'),(4,27,0,0,'김철수4','960611-1111114','010-2729-6604'),(5,27,0,0,'김철수5','960611-1111115','010-2729-6604'),(6,27,0,0,'김철수6','960611-1111116','010-2729-6604'),(7,27,0,0,'김철수7','960611-1111117','010-2729-6604'),(8,27,0,0,'김철수8','960611-1111118','010-2729-6604'),(9,27,0,0,'김철수9','960611-1111119','010-2729-6604');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_progress_history`
--

DROP TABLE IF EXISTS `patient_progress_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_progress_history` (
                                            `pph_id` bigint NOT NULL AUTO_INCREMENT,
                                            `patient_status` int NOT NULL DEFAULT '0',
                                            `patient_id` bigint DEFAULT NULL,
                                            `tc_id` bigint DEFAULT NULL,
                                            PRIMARY KEY (`pph_id`),
                                            KEY `FKf2qv6g17uxp8q0rrqd3iv9mv0` (`patient_id`),
                                            KEY `FK7uikfr6hdgb6n5iieahxv4ds5` (`tc_id`),
                                            CONSTRAINT `FK7uikfr6hdgb6n5iieahxv4ds5` FOREIGN KEY (`tc_id`) REFERENCES `task_checktitle` (`tc_id`),
                                            CONSTRAINT `FKf2qv6g17uxp8q0rrqd3iv9mv0` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_progress_history`
--

LOCK TABLES `patient_progress_history` WRITE;
/*!40000 ALTER TABLE `patient_progress_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient_progress_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
                         `staff_id` bigint NOT NULL AUTO_INCREMENT,
                         `id` varchar(255) NOT NULL,
                         `name` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `task` int NOT NULL,
                         PRIMARY KEY (`staff_id`),
                         UNIQUE KEY `UK_hpcj18w6eotqjkomeuvuatx4o` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'ssafy1','김싸피1','ssafy1',1),(2,'ssafy2','김싸피2','ssafy2',2),(3,'ssafy3','김싸피3','ssafy3',3),(4,'ssafy4','김싸피4','ssafy4',4),(5,'ssafy5','김싸피5','ssafy5',5),(6,'ssafy6','김싸피6','ssafy6',6),(7,'ssafy7','김싸피7','ssafy7',7),(8,'ssafy8','김싸피8','ssafy8',8),(9,'ssafy9','김싸피9','ssafy9',9),(10,'ssafy10','김싸피10','ssafy10',10);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_checktitle`
--

DROP TABLE IF EXISTS `task_checktitle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_checktitle` (
                                   `tc_id` bigint NOT NULL AUTO_INCREMENT,
                                   `check_title` varchar(255) NOT NULL,
                                   PRIMARY KEY (`tc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_checktitle`
--

LOCK TABLES `task_checktitle` WRITE;
/*!40000 ALTER TABLE `task_checktitle` DISABLE KEYS */;
INSERT INTO `task_checktitle` VALUES (1,'접수'),(2,'기초검사, 신체계측'),(3,'채혈, 소변검사'),(4,'흉부 방사선 검사'),(5,'진찰 및 상담'),(6,'자궁경부암 검사'),(7,'유방암 검사'),(8,'위암 검사'),(9,'대장암 검사'),(10,'폐암 검사');
/*!40000 ALTER TABLE `task_checktitle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-09 15:36:03