CREATE TABLE `provincias` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Pais` int(11) NOT NULL,
  `Nombre` varchar(128) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_provincias_1` (`Pais`),
  CONSTRAINT `FK_provincias_1` FOREIGN KEY (`Pais`) REFERENCES `paises` (`ID`)
)

