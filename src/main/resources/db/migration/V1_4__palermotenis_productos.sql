CREATE TABLE `productos` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `TipoProducto` int(10) unsigned NOT NULL,
  `Modelo` int(10) unsigned NOT NULL,
  `Descripcion` text,
  `Activo` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FKC14E5B1323517D44` (`TipoProducto`),
  KEY `FK_productos_modelo` (`Modelo`),
  CONSTRAINT `FK_productos_modelo` FOREIGN KEY (`Modelo`) REFERENCES `modelos` (`id`),
  CONSTRAINT `FK_productos_tipoproductos` FOREIGN KEY (`TipoProducto`) REFERENCES `tipo_productos` (`ID`)
)