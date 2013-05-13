CREATE TABLE `tipo_atributos` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Clase` varchar(45) NOT NULL,
  `Unidad` int(10) unsigned DEFAULT NULL,
  `TipoProducto` int(10) unsigned NOT NULL,
  `Tipo` char(1) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_tipo_atributos_tipo_productos` FOREIGN KEY (`TipoProducto`) REFERENCES `tipo_productos` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tipo_atributos_unidades` FOREIGN KEY (`Unidad`) REFERENCES `tipo_atributos_unidades` (`ID`) ON DELETE SET NULL ON UPDATE SET NULL
)

