CREATE TABLE `clientes` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(128) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Direccion` varchar(256) NOT NULL,
  `Ciudad` varchar(256) NOT NULL,
  `Telefono` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_clientes_1` (`Ciudad`)
) DEFAULT CHARSET=latin1;
