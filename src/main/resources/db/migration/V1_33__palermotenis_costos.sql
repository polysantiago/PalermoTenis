CREATE TABLE `costos` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Producto` int(10) unsigned NOT NULL,
  `Presentacion` int(10) unsigned DEFAULT NULL,
  `Proveedor` int(10) unsigned NOT NULL,
  `Moneda` int(10) unsigned NOT NULL,
  `Costo` double DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_costos_1` (`Producto`),
  KEY `FK_costos_2` (`Proveedor`),
  KEY `FK_costos_3` (`Moneda`),
  CONSTRAINT `FK_costos_1` FOREIGN KEY (`Producto`) REFERENCES `productos` (`ID`),
  CONSTRAINT `FK_costos_2` FOREIGN KEY (`Proveedor`) REFERENCES `proveedores` (`ID`),
  CONSTRAINT `FK_costos_3` FOREIGN KEY (`Moneda`) REFERENCES `monedas` (`ID`)
)

