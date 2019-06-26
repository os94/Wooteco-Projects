 SET NAMES utf8 ;

DROP TABLE IF EXISTS `board`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `board` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `piece` varchar(10) NOT NULL,
  `team` varchar(10) NOT NULL,
  `point` varchar(5) NOT NULL,
  `round` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `board_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `board` WRITE;

UNLOCK TABLES;
