start transaction;


create database `Acme-Food`;

use `Acme-Food`

create user 'acme-user'@'%' 
	identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' 
	identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';


grant select, insert, update, delete 
	on `Acme-Food`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-Food`.* to 'acme-manager'@'%';
-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Food
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surnames` varchar(255) DEFAULT NULL,
  `vat_number` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_awymvli3olnnumqow6wf060pa` (`email`),
  KEY `FK_i7xei45auwq1f6vu25985riuh` (`user_account`),
  CONSTRAINT `FK_i7xei45auwq1f6vu25985riuh` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surnames` varchar(255) DEFAULT NULL,
  `vat_number` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jj3mmcc0vjobqibj67dvuwihk` (`email`),
  KEY `FK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (6,0,'Sevilla','cristian@hotmail.com','Cristian','654654654','https://www.imagen.com.mx/assets/img/imagen_share.png','Lorca','ES12345678S',5);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cash_order`
--

DROP TABLE IF EXISTS `cash_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cash_order` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `choice` int(11) NOT NULL,
  `draft_mode` int(11) NOT NULL,
  `minutes` double DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `sender_moment` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `customer` int(11) NOT NULL,
  `dealer` int(11) DEFAULT NULL,
  `restaurant` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1092231oi4v570pube87w7mkf` (`ticker`),
  KEY `FK_s5lplhrjcrt26e3616xj4a9i4` (`customer`),
  KEY `FK_67r55li3stmbc8cgr5dlovgc0` (`dealer`),
  KEY `FK_bsp8nrrvvkklyodt0oorht6o6` (`restaurant`),
  CONSTRAINT `FK_bsp8nrrvvkklyodt0oorht6o6` FOREIGN KEY (`restaurant`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `FK_67r55li3stmbc8cgr5dlovgc0` FOREIGN KEY (`dealer`) REFERENCES `dealer` (`id`),
  CONSTRAINT `FK_s5lplhrjcrt26e3616xj4a9i4` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cash_order`
--

LOCK TABLES `cash_order` WRITE;
/*!40000 ALTER TABLE `cash_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `cash_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cash_order_complains`
--

DROP TABLE IF EXISTS `cash_order_complains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cash_order_complains` (
  `cash_order` int(11) NOT NULL,
  `complains` int(11) NOT NULL,
  UNIQUE KEY `UK_asr7r7u06ms3wxx0umsjq6xyh` (`complains`),
  KEY `FK_iiwcib6ja02pj4h67e4astksk` (`cash_order`),
  CONSTRAINT `FK_iiwcib6ja02pj4h67e4astksk` FOREIGN KEY (`cash_order`) REFERENCES `cash_order` (`id`),
  CONSTRAINT `FK_asr7r7u06ms3wxx0umsjq6xyh` FOREIGN KEY (`complains`) REFERENCES `complain` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cash_order_complains`
--

LOCK TABLES `cash_order_complains` WRITE;
/*!40000 ALTER TABLE `cash_order_complains` DISABLE KEYS */;
/*!40000 ALTER TABLE `cash_order_complains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cash_order_food_disheses`
--

DROP TABLE IF EXISTS `cash_order_food_disheses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cash_order_food_disheses` (
  `cash_order` int(11) NOT NULL,
  `food_disheses` int(11) NOT NULL,
  KEY `FK_e7r86ve0krra8ufsea4evowpn` (`food_disheses`),
  KEY `FK_oxsvrrwdg4k4sm09vej6ny232` (`cash_order`),
  CONSTRAINT `FK_oxsvrrwdg4k4sm09vej6ny232` FOREIGN KEY (`cash_order`) REFERENCES `cash_order` (`id`),
  CONSTRAINT `FK_e7r86ve0krra8ufsea4evowpn` FOREIGN KEY (`food_disheses`) REFERENCES `food_dishes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cash_order_food_disheses`
--

LOCK TABLES `cash_order_food_disheses` WRITE;
/*!40000 ALTER TABLE `cash_order_food_disheses` DISABLE KEYS */;
/*!40000 ALTER TABLE `cash_order_food_disheses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cash_order_offers`
--

DROP TABLE IF EXISTS `cash_order_offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cash_order_offers` (
  `cash_order` int(11) NOT NULL,
  `offers` int(11) NOT NULL,
  KEY `FK_auke83un0a1vucx0tjwg22jnu` (`offers`),
  KEY `FK_self6g6806c6y7jybcw7p8tqr` (`cash_order`),
  CONSTRAINT `FK_self6g6806c6y7jybcw7p8tqr` FOREIGN KEY (`cash_order`) REFERENCES `cash_order` (`id`),
  CONSTRAINT `FK_auke83un0a1vucx0tjwg22jnu` FOREIGN KEY (`offers`) REFERENCES `offer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cash_order_offers`
--

LOCK TABLES `cash_order_offers` WRITE;
/*!40000 ALTER TABLE `cash_order_offers` DISABLE KEYS */;
/*!40000 ALTER TABLE `cash_order_offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complain`
--

DROP TABLE IF EXISTS `complain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complain` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `cash_order` int(11) NOT NULL,
  `customer` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gx1tlfq0lnbtx0imqynf23w8h` (`cash_order`),
  KEY `FK_nbyd2novyqn75ajqsmohumj7v` (`customer`),
  CONSTRAINT `FK_nbyd2novyqn75ajqsmohumj7v` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_gx1tlfq0lnbtx0imqynf23w8h` FOREIGN KEY (`cash_order`) REFERENCES `cash_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complain`
--

LOCK TABLES `complain` WRITE;
/*!40000 ALTER TABLE `complain` DISABLE KEYS */;
/*!40000 ALTER TABLE `complain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit_card` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cw` int(11) NOT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `expiration_month` int(11) NOT NULL,
  `expiration_year` int(11) NOT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_card`
--

LOCK TABLES `credit_card` WRITE;
/*!40000 ALTER TABLE `credit_card` DISABLE KEYS */;
INSERT INTO `credit_card` VALUES (7,0,101,'VISA',3,2019,'nombre1','5325065299416578');
/*!40000 ALTER TABLE `credit_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surnames` varchar(255) DEFAULT NULL,
  `vat_number` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  `credit_card` int(11) NOT NULL,
  `finder` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_b35c50lmdyeoi3v4t5nuqq7bn` (`credit_card`),
  UNIQUE KEY `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email`),
  KEY `FK_6dlj2jxtd2fmnpihb13abpd1x` (`finder`),
  KEY `FK_mbvdes9ypo1yu76so76owiyqx` (`user_account`),
  CONSTRAINT `FK_mbvdes9ypo1yu76so76owiyqx` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_6dlj2jxtd2fmnpihb13abpd1x` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_b35c50lmdyeoi3v4t5nuqq7bn` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_complains`
--

DROP TABLE IF EXISTS `customer_complains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_complains` (
  `customer` int(11) NOT NULL,
  `complains` int(11) NOT NULL,
  UNIQUE KEY `UK_3cvl4c9c2cuu56c2d02wtwk0w` (`complains`),
  KEY `FK_e9c8yqopmhbfq0q82hacl1mqq` (`customer`),
  CONSTRAINT `FK_e9c8yqopmhbfq0q82hacl1mqq` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_3cvl4c9c2cuu56c2d02wtwk0w` FOREIGN KEY (`complains`) REFERENCES `complain` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_complains`
--

LOCK TABLES `customer_complains` WRITE;
/*!40000 ALTER TABLE `customer_complains` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_complains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_ratings`
--

DROP TABLE IF EXISTS `customer_ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_ratings` (
  `customer` int(11) NOT NULL,
  `ratings` int(11) NOT NULL,
  UNIQUE KEY `UK_8r6ueyo0x2p4iued0xvdnwyha` (`ratings`),
  KEY `FK_jke5wt12w6ya96xwqmrd1w8tx` (`customer`),
  CONSTRAINT `FK_jke5wt12w6ya96xwqmrd1w8tx` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_8r6ueyo0x2p4iued0xvdnwyha` FOREIGN KEY (`ratings`) REFERENCES `rating` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_ratings`
--

LOCK TABLES `customer_ratings` WRITE;
/*!40000 ALTER TABLE `customer_ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customizable_system`
--

DROP TABLE IF EXISTS `customizable_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customizable_system` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `max_results` int(11) NOT NULL,
  `message_welcome_page` varchar(255) DEFAULT NULL,
  `name_system` varchar(255) DEFAULT NULL,
  `spanish_message_welcome_page` varchar(255) DEFAULT NULL,
  `telephone_code` varchar(255) DEFAULT NULL,
  `time_cache` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customizable_system`
--

LOCK TABLES `customizable_system` WRITE;
/*!40000 ALTER TABLE `customizable_system` DISABLE KEYS */;
INSERT INTO `customizable_system` VALUES (8,0,'https://www.tu-app.net/blog/wp-content/uploads/2016/09/APPS-restaurantes-1.jpg',10,'Welcome to Acme Food! We\'re the favourite food app!','Acme Food','¡Bienvenidos a Acme Food!  ¡Somos la app favorita de comida!','+34',1);
/*!40000 ALTER TABLE `customizable_system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dealer`
--

DROP TABLE IF EXISTS `dealer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dealer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surnames` varchar(255) DEFAULT NULL,
  `vat_number` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  `restaurant` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_spk2tlclabo5py2qxt4yc32io` (`email`),
  KEY `FK_ef636253ttrr8630tt8qfwvu2` (`restaurant`),
  KEY `FK_4tq588ccy138knp24hjr2y1d1` (`user_account`),
  CONSTRAINT `FK_4tq588ccy138knp24hjr2y1d1` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_ef636253ttrr8630tt8qfwvu2` FOREIGN KEY (`restaurant`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dealer`
--

LOCK TABLES `dealer` WRITE;
/*!40000 ALTER TABLE `dealer` DISABLE KEYS */;
/*!40000 ALTER TABLE `dealer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `key_word` varchar(255) DEFAULT NULL,
  `max_score` int(11) DEFAULT NULL,
  `min_score` int(11) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_restaurants`
--

DROP TABLE IF EXISTS `finder_restaurants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_restaurants` (
  `finder` int(11) NOT NULL,
  `restaurants` int(11) NOT NULL,
  KEY `FK_cljtxt4hg62pxntvuadgpjnpv` (`restaurants`),
  KEY `FK_derw6x9sj0mpl67unmnipmnkj` (`finder`),
  CONSTRAINT `FK_derw6x9sj0mpl67unmnipmnkj` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_cljtxt4hg62pxntvuadgpjnpv` FOREIGN KEY (`restaurants`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_restaurants`
--

LOCK TABLES `finder_restaurants` WRITE;
/*!40000 ALTER TABLE `finder_restaurants` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_restaurants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_dishes`
--

DROP TABLE IF EXISTS `food_dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `food_dishes` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `type` int(11) NOT NULL,
  `restaurant` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8wglnulat71gdyimc829xglg0` (`restaurant`),
  CONSTRAINT `FK_8wglnulat71gdyimc829xglg0` FOREIGN KEY (`restaurant`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_dishes`
--

LOCK TABLES `food_dishes` WRITE;
/*!40000 ALTER TABLE `food_dishes` DISABLE KEYS */;
/*!40000 ALTER TABLE `food_dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_dishes_ingredients`
--

DROP TABLE IF EXISTS `food_dishes_ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `food_dishes_ingredients` (
  `food_dishes` int(11) NOT NULL,
  `ingredients` varchar(255) DEFAULT NULL,
  KEY `FK_ej7ff7tn4nq962xcjfw23bhf8` (`food_dishes`),
  CONSTRAINT `FK_ej7ff7tn4nq962xcjfw23bhf8` FOREIGN KEY (`food_dishes`) REFERENCES `food_dishes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_dishes_ingredients`
--

LOCK TABLES `food_dishes_ingredients` WRITE;
/*!40000 ALTER TABLE `food_dishes_ingredients` DISABLE KEYS */;
/*!40000 ALTER TABLE `food_dishes_ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer`
--

DROP TABLE IF EXISTS `offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `restaurant` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_isn452oho6m8ej5s7h51p04qm` (`restaurant`),
  CONSTRAINT `FK_isn452oho6m8ej5s7h51p04qm` FOREIGN KEY (`restaurant`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer`
--

LOCK TABLES `offer` WRITE;
/*!40000 ALTER TABLE `offer` DISABLE KEYS */;
/*!40000 ALTER TABLE `offer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer_food_disheses`
--

DROP TABLE IF EXISTS `offer_food_disheses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offer_food_disheses` (
  `offer` int(11) NOT NULL,
  `food_disheses` int(11) NOT NULL,
  KEY `FK_i34dy7h96pymk3729vrpaq3kx` (`food_disheses`),
  KEY `FK_eeon202r43nk1iac7irmheddd` (`offer`),
  CONSTRAINT `FK_eeon202r43nk1iac7irmheddd` FOREIGN KEY (`offer`) REFERENCES `offer` (`id`),
  CONSTRAINT `FK_i34dy7h96pymk3729vrpaq3kx` FOREIGN KEY (`food_disheses`) REFERENCES `food_dishes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer_food_disheses`
--

LOCK TABLES `offer_food_disheses` WRITE;
/*!40000 ALTER TABLE `offer_food_disheses` DISABLE KEYS */;
/*!40000 ALTER TABLE `offer_food_disheses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `valoration` int(11) NOT NULL,
  `customer` int(11) NOT NULL,
  `restaurant` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_b9o1acdie8y2b6dbcginbq4dj` (`customer`,`restaurant`),
  KEY `FK_qm31eunx96kjxkd4cxnej3drq` (`restaurant`),
  CONSTRAINT `FK_qm31eunx96kjxkd4cxnej3drq` FOREIGN KEY (`restaurant`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `FK_gelbeote6apc45p1hks81lp0s` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurant` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surnames` varchar(255) DEFAULT NULL,
  `vat_number` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  `comercial_name` varchar(255) DEFAULT NULL,
  `is_banned` int(11) NOT NULL,
  `medium_score` int(11) DEFAULT NULL,
  `order_time` int(11) DEFAULT NULL,
  `speciality` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_979xvypjc2lwr1ia4kq77cko0` (`email`),
  KEY `FK_j7flfgax9kbxmb33twepe4361` (`user_account`),
  CONSTRAINT `FK_j7flfgax9kbxmb33twepe4361` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_ratings`
--

DROP TABLE IF EXISTS `restaurant_ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurant_ratings` (
  `restaurant` int(11) NOT NULL,
  `ratings` int(11) NOT NULL,
  UNIQUE KEY `UK_hmkv3qfyco4fnqpgjpwh37e2f` (`ratings`),
  KEY `FK_npyqqqe3ifnx7m29qfcvyipla` (`restaurant`),
  CONSTRAINT `FK_npyqqqe3ifnx7m29qfcvyipla` FOREIGN KEY (`restaurant`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `FK_hmkv3qfyco4fnqpgjpwh37e2f` FOREIGN KEY (`ratings`) REFERENCES `rating` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_ratings`
--

LOCK TABLES `restaurant_ratings` WRITE;
/*!40000 ALTER TABLE `restaurant_ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `restaurant_ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_profile`
--

DROP TABLE IF EXISTS `social_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_profile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name_social_network` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `restaurant` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_c59jaa0gke87ah450gt43wom9` (`restaurant`),
  CONSTRAINT `FK_c59jaa0gke87ah450gt43wom9` FOREIGN KEY (`restaurant`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_profile`
--

LOCK TABLES `social_profile` WRITE;
/*!40000 ALTER TABLE `social_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (5,0,'21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (5,'ADMIN');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-03 15:07:22
