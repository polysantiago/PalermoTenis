CREATE TABLE `ciudades` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Provincia` int(11) NOT NULL,
  `Nombre` varchar(40) NOT NULL,
  `CodigoPostal` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ciudades_1` (`Provincia`),
  CONSTRAINT `FK_ciudades_1` FOREIGN KEY (`Provincia`) REFERENCES `provincias` (`ID`)
)

