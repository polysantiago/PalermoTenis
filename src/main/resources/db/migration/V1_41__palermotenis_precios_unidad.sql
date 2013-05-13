CREATE TABLE `precios_unidad` (
  `Moneda` int(10) unsigned NOT NULL,
  `Pago` int(10) unsigned NOT NULL,
  `Producto` int(10) unsigned NOT NULL,
  `Valor` double DEFAULT NULL,
  `Cuotas` int(10) unsigned NOT NULL DEFAULT '1',
  `EnOferta` tinyint(1) NOT NULL DEFAULT '0',
  `ValorOferta` double DEFAULT NULL,
  `Orden` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`Moneda`,`Pago`,`Producto`,`Cuotas`),
  KEY `FK_precios_pago` (`Pago`),
  KEY `FK_precios_producto` (`Producto`),
  CONSTRAINT `FK_precios_moneda` FOREIGN KEY (`Moneda`) REFERENCES `monedas` (`ID`),
  CONSTRAINT `FK_precios_pago` FOREIGN KEY (`Pago`) REFERENCES `pagos` (`Codigo`),
  CONSTRAINT `FK_precios_producto` FOREIGN KEY (`Producto`) REFERENCES `productos` (`ID`)
)

