 SET NAMES utf8 ;

DROP TABLE IF EXISTS `turn`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `turn` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `current_team` varchar(10) NOT NULL,
  `round` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `turn_id_uindex` (`id`),
  UNIQUE KEY `turn_round_uindex` (`round`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `turn` WRITE;

UNLOCK TABLES;
