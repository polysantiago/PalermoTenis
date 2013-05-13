CREATE TABLE `pedidos_productos` (
  `Pedido` int(10) unsigned NOT NULL,
  `Stock` int(10) unsigned NOT NULL,
  `Cantidad` int(10) unsigned NOT NULL DEFAULT '1',
  `Subtotal` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`Pedido`,`Stock`),
  KEY `FK_pedidos_productos_2` (`Stock`),
  CONSTRAINT `FK_pedidos_productos_1` FOREIGN KEY (`Pedido`) REFERENCES `pedidos` (`ID`),
  CONSTRAINT `pedidos_productos_ibfk_1` FOREIGN KEY (`Stock`) REFERENCES `stock` (`ID`)
)

