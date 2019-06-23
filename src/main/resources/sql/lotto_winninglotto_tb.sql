SET NAMES utf8 ;

DROP TABLE IF EXISTS `winninglotto_tb`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `winninglotto_tb` (
  `winninglotto` varchar(32) COLLATE utf8_bin NOT NULL,
  `bonus` int(10) unsigned NOT NULL,
  `fk_winninglotto_round` int(10) unsigned NOT NULL,
  UNIQUE KEY `fk_winninglotto_round_UNIQUE` (`fk_winninglotto_round`),
  CONSTRAINT `fk_winninglotto_round` FOREIGN KEY (`fk_winninglotto_round`) REFERENCES round (`round`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

LOCK TABLES `winninglotto_tb` WRITE;

UNLOCK TABLES;
