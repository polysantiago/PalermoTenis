CREATE TABLE `modelos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `tipoProducto` int(10) unsigned NOT NULL,
  `marca` int(10) unsigned NOT NULL,
  `lft` int(10) unsigned DEFAULT NULL,
  `rgt` int(10) unsigned DEFAULT NULL,
  `padre` int(10) unsigned DEFAULT NULL,
  `orden` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_modelos_1` (`marca`),
  KEY `FK_modelos_2` (`tipoProducto`),
  CONSTRAINT `FK_modelos_1` FOREIGN KEY (`marca`) REFERENCES `marcas` (`ID`),
  CONSTRAINT `FK_modelos_2` FOREIGN KEY (`tipoProducto`) REFERENCES `tipo_productos` (`ID`)
)