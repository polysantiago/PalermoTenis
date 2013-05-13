CREATE TABLE `precios_presentaciones` (
  `Moneda` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Pago` int(10) unsigned NOT NULL,
  `Producto` int(10) unsigned NOT NULL,
  `Presentacion` int(10) unsigned NOT NULL DEFAULT '1',
  `Valor` double DEFAULT NULL,
  `Cuotas` int(10) unsigned NOT NULL DEFAULT '1',
  `EnOferta` tinyint(1) NOT NULL DEFAULT '0',
  `ValorOferta` double DEFAULT NULL,
  `Orden` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`Moneda`,`Pago`,`Producto`,`Presentacion`,`Cuotas`),
  KEY `FK_precios_cantidad_2` (`Pago`),
  KEY `FK_precios_cantidad_3` (`Producto`),
  KEY `FK_precios_presentaciones_4` (`Presentacion`),
  CONSTRAINT `FK_precios_presentaciones_4` FOREIGN KEY (`Presentacion`) REFERENCES `presentaciones` (`ID`)
)

