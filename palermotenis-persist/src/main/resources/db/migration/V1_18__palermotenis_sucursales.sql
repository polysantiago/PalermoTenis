CREATE TABLE `sucursales` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Telefono` varchar(45) DEFAULT NULL,
  `Direccion` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
)