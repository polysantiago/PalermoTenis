CREATE TABLE `tipo_presentaciones` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `TipoProducto` int(10) unsigned NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_presentaciones_1` (`TipoProducto`),
  CONSTRAINT `FK_presentaciones_1` FOREIGN KEY (`TipoProducto`) REFERENCES `tipo_productos` (`ID`)
)

