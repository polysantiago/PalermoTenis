CREATE TABLE `stock_listados` (
  `Listado` varchar(10) NOT NULL,
  `Stock` int(10) unsigned NOT NULL,
  `Cantidad` int(11) NOT NULL DEFAULT '1',
  `PrecioUnitario` double NOT NULL DEFAULT '0',
  `Subtotal` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`Listado`,`Stock`),
  KEY `Stock` (`Stock`),
  CONSTRAINT `stock_listados_ibfk_1` FOREIGN KEY (`Listado`) REFERENCES `listados` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `stock_listados_ibfk_2` FOREIGN KEY (`Stock`) REFERENCES `stock` (`ID`) ON DELETE CASCADE
)

