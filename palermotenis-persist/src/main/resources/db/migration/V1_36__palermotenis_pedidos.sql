CREATE TABLE `pedidos` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Pago` int(10) unsigned NOT NULL,
  `Cuotas` int(10) unsigned DEFAULT NULL,
  `Total` double NOT NULL DEFAULT '0',
  `Fecha` datetime NOT NULL,
  `Cliente` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_pedidos_1` (`Pago`),
  KEY `FK_pedidos_clientes` (`Cliente`),
  CONSTRAINT `FK_pedidos_1` FOREIGN KEY (`Pago`) REFERENCES `pagos` (`Codigo`),
  CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`Cliente`) REFERENCES `clientes` (`ID`)
)
