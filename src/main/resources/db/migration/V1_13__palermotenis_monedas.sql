CREATE TABLE `monedas` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Simbolo` varchar(45) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Contrario` int(10) unsigned DEFAULT NULL,
  `Codigo` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_monedas_monedas` (`Contrario`),
  CONSTRAINT `FK_monedas_monedas` FOREIGN KEY (`Contrario`) REFERENCES `monedas` (`ID`)
)

