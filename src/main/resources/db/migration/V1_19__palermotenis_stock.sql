CREATE TABLE `stock` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CodigoDeBarra` varchar(12) DEFAULT 'XXXXXXXXXXXX',
  `Producto` int(10) unsigned NOT NULL,
  `ValorClasificatorio` int(10) unsigned DEFAULT NULL,
  `Presentacion` int(10) unsigned DEFAULT NULL,
  `Sucursal` int(11) NOT NULL DEFAULT '1',
  `Stock` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_Stock_Productos` (`Producto`),
  KEY `FK_Stock_ValoresPosibles` (`ValorClasificatorio`),
  KEY `FK_stock_Presentacion` (`Presentacion`),
  KEY `FK_stock_Sucursal` (`Sucursal`),
  CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`Producto`) REFERENCES `productos` (`ID`),
  CONSTRAINT `stock_ibfk_2` FOREIGN KEY (`ValorClasificatorio`) REFERENCES `valores_posibles` (`ID`),
  CONSTRAINT `stock_ibfk_3` FOREIGN KEY (`Presentacion`) REFERENCES `presentaciones` (`ID`),
  CONSTRAINT `stock_ibfk_4` FOREIGN KEY (`Sucursal`) REFERENCES `sucursales` (`ID`)
)