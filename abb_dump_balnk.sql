CREATE DATABASE  IF NOT EXISTS `abblog` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `abblog`;
-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: abblog
-- ------------------------------------------------------
-- Server version	5.7.24

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
-- Table structure for table `analysislimits`
--

DROP TABLE IF EXISTS `analysislimits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `analysislimits` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(20) NOT NULL,
  `param_name` varchar(30) NOT NULL,
  `center` decimal(12,5) NOT NULL,
  `warning_pct` decimal(12,5) NOT NULL,
  `error_pct` decimal(12,5) NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `variable_limits` tinyint(4) NOT NULL DEFAULT '0',
  `lcl` double NOT NULL DEFAULT '0',
  `ucl` double NOT NULL DEFAULT '0',
  `productcode` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`productcode`),
  KEY `index2` (`productcode`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_actions`
--

DROP TABLE IF EXISTS `app_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_actions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_type` tinyint(4) NOT NULL,
  `test_record_id` bigint(20) DEFAULT NULL,
  `test_result_id` bigint(20) DEFAULT NULL,
  `usl` double DEFAULT NULL,
  `lsl` double DEFAULT NULL,
  `ucl` double DEFAULT NULL,
  `lcl` double DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `failure_reason` varchar(255) DEFAULT NULL,
  `remedy_type` int(11) DEFAULT NULL,
  `remedy_comments` varchar(255) DEFAULT NULL,
  `action_user` int(11) DEFAULT NULL,
  `action_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ao_results_fk_idx` (`test_result_id`),
  KEY `ao_records_fk_idx` (`test_record_id`),
  KEY `ao_remedy_types_fk_idx` (`remedy_type`),
  KEY `ao_action_users_idx` (`action_user`),
  CONSTRAINT `ao_action_users` FOREIGN KEY (`action_user`) REFERENCES `app_users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ao_records_fk` FOREIGN KEY (`test_record_id`) REFERENCES `testrecord` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ao_remedy_types_fk` FOREIGN KEY (`remedy_type`) REFERENCES `app_remedy_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ao_results_fk` FOREIGN KEY (`test_result_id`) REFERENCES `testresult` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3768 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_group_params`
--

DROP TABLE IF EXISTS `app_group_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_group_params` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_group` int(11) NOT NULL,
  `param` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `gp_group_fk_idx` (`app_group`),
  KEY `gp_param_fk_idx` (`param`),
  CONSTRAINT `gp_group_fk` FOREIGN KEY (`app_group`) REFERENCES `app_groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `gp_param_fk` FOREIGN KEY (`param`) REFERENCES `analysislimits` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_groups`
--

DROP TABLE IF EXISTS `app_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_group_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_remedy_types`
--

DROP TABLE IF EXISTS `app_remedy_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_remedy_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remedy_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_user_group`
--

DROP TABLE IF EXISTS `app_user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ug_users_fk_idx` (`user_id`),
  KEY `ug_groups_fk_idx` (`group_id`),
  CONSTRAINT `ug_groups_fk` FOREIGN KEY (`group_id`) REFERENCES `app_groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ug_users_fk` FOREIGN KEY (`user_id`) REFERENCES `app_users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_users`
--

DROP TABLE IF EXISTS `app_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(25) NOT NULL,
  `imei_number` varchar(45) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `acess_level` tinyint(4) NOT NULL DEFAULT '0',
  `user_active` tinyint(4) NOT NULL,
  `mo_no` varchar(15) DEFAULT NULL,
  `email` varchar(199) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `category` varchar(50) NOT NULL,
  `category_group` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chart`
--

DROP TABLE IF EXISTS `chart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `page_id` int(11) NOT NULL,
  `chart_type` int(11) NOT NULL DEFAULT '0',
  `chart_period` varchar(10) NOT NULL DEFAULT 'DAY',
  `grid_row_pos` int(11) NOT NULL DEFAULT '0',
  `grid_col_pos` int(11) NOT NULL DEFAULT '0',
  `grid_rowspan_count` int(11) NOT NULL DEFAULT '1',
  `grid_colspan_count` int(11) NOT NULL DEFAULT '1',
  `chart_title` varchar(80) NOT NULL,
  `refresh_time` int(11) NOT NULL DEFAULT '100',
  `parameter_1` varchar(50) DEFAULT NULL,
  `parameter_2` varchar(50) DEFAULT NULL,
  `parameter_3` varchar(50) DEFAULT NULL,
  `parameter_4` varchar(50) DEFAULT NULL,
  `parameter_5` varchar(50) DEFAULT NULL,
  `parameter_6` varchar(50) DEFAULT NULL,
  `parameter_7` varchar(50) DEFAULT NULL,
  `parameter_8` varchar(50) DEFAULT NULL,
  `parameter_9` varchar(50) DEFAULT NULL,
  `parameter_10` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=353 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chart_paramnames`
--

DROP TABLE IF EXISTS `chart_paramnames`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chart_paramnames` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chart_type_no` int(10) DEFAULT NULL,
  `chart_param_index` varchar(50) DEFAULT NULL,
  `chart_param_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `chart_type_no` (`chart_type_no`),
  CONSTRAINT `chart_paramnames_ibfk_1` FOREIGN KEY (`chart_type_no`) REFERENCES `chart_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chart_type`
--

DROP TABLE IF EXISTS `chart_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chart_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chart_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chart_types`
--

DROP TABLE IF EXISTS `chart_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chart_types` (
  `id` int(11) NOT NULL,
  `chart_name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `controllimits`
--

DROP TABLE IF EXISTS `controllimits`;
/*!50001 DROP VIEW IF EXISTS `controllimits`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `controllimits` AS SELECT 
 1 AS `category`,
 1 AS `param_name`,
 1 AS `center`,
 1 AS `label`,
 1 AS `lsl`,
 1 AS `usl`,
 1 AS `lcl`,
 1 AS `ucl`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `displayparams`
--

DROP TABLE IF EXISTS `displayparams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `displayparams` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_name` varchar(30) NOT NULL,
  `param_value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dutcomponents`
--

DROP TABLE IF EXISTS `dutcomponents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dutcomponents` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `testrecord_id` bigint(20) NOT NULL,
  `order_code` varchar(45) NOT NULL,
  `serial_number` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `component_testrecord_fk_idx` (`testrecord_id`),
  CONSTRAINT `component_testrecord_fk` FOREIGN KEY (`testrecord_id`) REFERENCES `testrecord` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=226792 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `errorclass`
--

DROP TABLE IF EXISTS `errorclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `errorclass` (
  `id` tinyint(4) NOT NULL,
  `errclassname` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `failurecodes`
--

DROP TABLE IF EXISTS `failurecodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `failurecodes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reason_code` int(11) NOT NULL,
  `reason_desc` varchar(255) NOT NULL,
  `category_group` varchar(45) NOT NULL DEFAULT 'ALL',
  `fault_active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `reason_code_UNIQUE` (`reason_code`),
  UNIQUE KEY `UK99q3rq3o8w4evmnbeq61uyhm8` (`reason_code`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `fpy_daily_view`
--

DROP TABLE IF EXISTS `fpy_daily_view`;
/*!50001 DROP VIEW IF EXISTS `fpy_daily_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `fpy_daily_view` AS SELECT 
 1 AS `day`,
 1 AS `category`,
 1 AS `total_results`,
 1 AS `passed_results`,
 1 AS `failed_results`,
 1 AS `percentage`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `fpy_historical_view`
--

DROP TABLE IF EXISTS `fpy_historical_view`;
/*!50001 DROP VIEW IF EXISTS `fpy_historical_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `fpy_historical_view` AS SELECT 
 1 AS `date`,
 1 AS `category`,
 1 AS `total_results`,
 1 AS `passed_results`,
 1 AS `failed_results`,
 1 AS `percentage`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `fpy_hourly_view`
--

DROP TABLE IF EXISTS `fpy_hourly_view`;
/*!50001 DROP VIEW IF EXISTS `fpy_hourly_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `fpy_hourly_view` AS SELECT 
 1 AS `hour`,
 1 AS `category`,
 1 AS `total_results`,
 1 AS `passed_results`,
 1 AS `failed_results`,
 1 AS `percentage`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `fpy_monthly_view`
--

DROP TABLE IF EXISTS `fpy_monthly_view`;
/*!50001 DROP VIEW IF EXISTS `fpy_monthly_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `fpy_monthly_view` AS SELECT 
 1 AS `month`,
 1 AS `category`,
 1 AS `total_results`,
 1 AS `passed_results`,
 1 AS `percentage`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `overload_parameter`
--

DROP TABLE IF EXISTS `overload_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `overload_parameter` (
  `varientid` int(11) NOT NULL,
  `parameter_name` varchar(45) DEFAULT NULL,
  `center` double DEFAULT NULL,
  `warning_pct` double DEFAULT NULL,
  `error_pct` varchar(45) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `fk_overload_parameter_1_idx` (`varientid`),
  CONSTRAINT `fk_overload_parameter_1` FOREIGN KEY (`varientid`) REFERENCES `varient` (`varient_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `page`
--

DROP TABLE IF EXISTS `page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time_to_display` int(11) NOT NULL DEFAULT '1000',
  `page_title` varchar(100) DEFAULT NULL,
  `bgcolor` varchar(50) NOT NULL,
  `page_priority` int(11) NOT NULL DEFAULT '0',
  `total_gridrows` int(11) DEFAULT NULL,
  `total_gridcols` int(11) DEFAULT NULL,
  `page_style` varchar(50) DEFAULT NULL,
  `page_active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `productcode` varchar(30) NOT NULL,
  `productname` varchar(45) DEFAULT NULL,
  `product_cat_code` varchar(45) DEFAULT NULL,
  `ordercode` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`productcode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_varient`
--

DROP TABLE IF EXISTS `product_varient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_varient` (
  `product_code` varchar(60) DEFAULT NULL,
  `varient_code` varchar(45) DEFAULT NULL,
  `startchar` int(11) DEFAULT NULL,
  `endchar` int(11) DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `fk_product_varient_1_idx` (`product_code`),
  CONSTRAINT `fk_product_varient_1` FOREIGN KEY (`product_code`) REFERENCES `product` (`productcode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `testitems`
--

DROP TABLE IF EXISTS `testitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testitems` (
  `id` int(11) NOT NULL,
  `category` int(11) NOT NULL,
  `barcode` varchar(45) NOT NULL,
  `itemname` varchar(45) NOT NULL,
  `latest` varchar(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `testitem_categories_fk_idx` (`category`),
  CONSTRAINT `testitem_categories_fk` FOREIGN KEY (`category`) REFERENCES `categories` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `testrecord`
--

DROP TABLE IF EXISTS `testrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testrecord` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` varchar(20) NOT NULL,
  `test_station` varchar(20) NOT NULL,
  `dut` varchar(30) NOT NULL,
  `order_code` varchar(255) NOT NULL DEFAULT '',
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `result` tinyint(4) DEFAULT '0',
  `result_code` int(11) DEFAULT '-1',
  `user_desc` varchar(255) DEFAULT NULL,
  `report_result` tinyint(4) NOT NULL DEFAULT '0',
  `error_category` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=277413 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `testresult`
--

DROP TABLE IF EXISTS `testresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testresult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `test_record_id` bigint(20) NOT NULL,
  `param_name` varchar(30) NOT NULL,
  `param_value` decimal(12,5) NOT NULL,
  `result` tinyint(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `testresult_index` (`test_record_id`,`param_name`),
  KEY `result_record_fk_idx` (`test_record_id`),
  CONSTRAINT `result_record_fk` FOREIGN KEY (`test_record_id`) REFERENCES `testrecord` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2504945 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `user_pwd` varchar(45) NOT NULL,
  `user_level` tinyint(5) DEFAULT NULL,
  `user_active` tinyint(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `varient`
--

DROP TABLE IF EXISTS `varient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `varient` (
  `varient_id` int(11) NOT NULL AUTO_INCREMENT,
  `barcode_part` varchar(45) DEFAULT NULL,
  `product_var_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`varient_id`),
  KEY `fk_varient_1_idx` (`product_var_id`),
  CONSTRAINT `fk_varient_1` FOREIGN KEY (`product_var_id`) REFERENCES `product_varient` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `versions`
--

DROP TABLE IF EXISTS `versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `versions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `versioned` varchar(45) NOT NULL,
  `version_number` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `controllimits`
--

/*!50001 DROP VIEW IF EXISTS `controllimits`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`suyosys`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `controllimits` AS (select `analysislimits`.`category` AS `category`,`analysislimits`.`param_name` AS `param_name`,`analysislimits`.`center` AS `center`,`analysislimits`.`label` AS `label`,(`analysislimits`.`center` - ((abs(`analysislimits`.`center`) * `analysislimits`.`error_pct`) / 100)) AS `lsl`,(`analysislimits`.`center` + ((abs(`analysislimits`.`center`) * `analysislimits`.`error_pct`) / 100)) AS `usl`,if((`analysislimits`.`variable_limits` = 0),(`analysislimits`.`center` - ((abs(`analysislimits`.`center`) * `analysislimits`.`warning_pct`) / 100)),`analysislimits`.`lcl`) AS `lcl`,if((`analysislimits`.`variable_limits` = 0),(`analysislimits`.`center` + ((abs(`analysislimits`.`center`) * `analysislimits`.`warning_pct`) / 100)),`analysislimits`.`ucl`) AS `ucl` from `analysislimits`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `fpy_daily_view`
--

/*!50001 DROP VIEW IF EXISTS `fpy_daily_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`suyosys`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `fpy_daily_view` AS select substr(`fpy_historical_view`.`date`,1,8) AS `day`,`fpy_historical_view`.`category` AS `category`,sum(`fpy_historical_view`.`total_results`) AS `total_results`,sum(`fpy_historical_view`.`passed_results`) AS `passed_results`,`fpy_historical_view`.`failed_results` AS `failed_results`,((sum(`fpy_historical_view`.`passed_results`) / sum(`fpy_historical_view`.`total_results`)) * 100) AS `percentage` from `fpy_historical_view` group by substr(`fpy_historical_view`.`date`,1,8),`fpy_historical_view`.`category` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `fpy_historical_view`
--

/*!50001 DROP VIEW IF EXISTS `fpy_historical_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`suyosys`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `fpy_historical_view` AS select concat(year(`testrecord`.`start_time`),lpad(month(`testrecord`.`start_time`),2,0),lpad(dayofmonth(`testrecord`.`start_time`),2,0),lpad(hour(`testrecord`.`start_time`),2,0)) AS `date`,`testrecord`.`category` AS `category`,count(0) AS `total_results`,sum(`testrecord`.`result`) AS `passed_results`,sum((`testrecord`.`result` = 0)) AS `failed_results`,((sum(`testrecord`.`result`) / count(`testrecord`.`result`)) * 100) AS `percentage` from `testrecord` group by year(`testrecord`.`start_time`),month(`testrecord`.`start_time`),dayofmonth(`testrecord`.`start_time`),hour(`testrecord`.`start_time`),`testrecord`.`category` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `fpy_hourly_view`
--

/*!50001 DROP VIEW IF EXISTS `fpy_hourly_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`suyosys`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `fpy_hourly_view` AS select substr(`fpy_historical_view`.`date`,1,10) AS `hour`,`fpy_historical_view`.`category` AS `category`,sum(`fpy_historical_view`.`total_results`) AS `total_results`,sum(`fpy_historical_view`.`passed_results`) AS `passed_results`,`fpy_historical_view`.`failed_results` AS `failed_results`,((sum(`fpy_historical_view`.`passed_results`) / sum(`fpy_historical_view`.`total_results`)) * 100) AS `percentage` from `fpy_historical_view` group by substr(`fpy_historical_view`.`date`,1,10),`fpy_historical_view`.`category` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `fpy_monthly_view`
--

/*!50001 DROP VIEW IF EXISTS `fpy_monthly_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`suyosys`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `fpy_monthly_view` AS select substr(`fpy_historical_view`.`date`,1,6) AS `month`,`fpy_historical_view`.`category` AS `category`,sum(`fpy_historical_view`.`total_results`) AS `total_results`,sum(`fpy_historical_view`.`passed_results`) AS `passed_results`,((sum(`fpy_historical_view`.`passed_results`) / sum(`fpy_historical_view`.`total_results`)) * 100) AS `percentage` from `fpy_historical_view` group by substr(`fpy_historical_view`.`date`,1,6),`fpy_historical_view`.`category` */;
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

-- Dump completed on 2019-01-12 12:34:09
