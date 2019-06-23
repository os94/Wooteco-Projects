SET NAMES utf8 ;

DROP TABLE IF EXISTS `round_tb`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `round_tb` (
  `round` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `money` int(11) NOT NULL,
  PRIMARY KEY (`round`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

LOCK TABLES `round_tb` WRITE;

UNLOCK TABLES;
