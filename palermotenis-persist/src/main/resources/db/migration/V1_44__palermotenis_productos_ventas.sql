CREATE TABLE `productos_ventas` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Venta` int(10) unsigned NOT NULL,
  `Stock` int(11) DEFAULT NULL,
  `Producto` varchar(128) NOT NULL,
  `PrecioUnitario` double NOT NULL,
  `Subtotal` double NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_productos_ventas_1` (`Venta`),
  CONSTRAINT `productos_ventas_ibfk_1` FOREIGN KEY (`Venta`) REFERENCES `ventas` (`ID`) ON DELETE CASCADE
)

