SET NAMES utf8 ;

DROP TABLE IF EXISTS `lottos_tb`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `lottos_tb` (
  `lotto` varchar(32) COLLATE utf8_bin NOT NULL,
  `fk_lottos_round` int(10) unsigned NOT NULL,
  KEY `fk_lottos_round_idx` (`fk_lottos_round`),
  CONSTRAINT `fk_lottos_round` FOREIGN KEY (`fk_lottos_round`) REFERENCES round (`round`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

LOCK TABLES `lottos_tb` WRITE;

UNLOCK TABLES;
