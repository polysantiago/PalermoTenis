CREATE TABLE `deportes_modelos` (
  `deporte` int(10) unsigned NOT NULL,
  `modelo` int(10) unsigned NOT NULL,
  PRIMARY KEY (`deporte`,`modelo`),
  KEY `FK_deportes_modelos_2` (`modelo`),
  CONSTRAINT `FK_deportes_modelos_1` FOREIGN KEY (`deporte`) REFERENCES `deportes` (`ID`),
  CONSTRAINT `FK_deportes_modelos_2` FOREIGN KEY (`modelo`) REFERENCES `modelos` (`id`)
)

