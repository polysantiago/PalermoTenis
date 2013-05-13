CREATE TABLE `presentaciones` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Cantidad` double NOT NULL,
  `Unidad` varchar(45) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `TipoPresentacion` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_presentaciones_unidades_1` (`TipoPresentacion`),
  CONSTRAINT `FK_presentaciones_unidades_1` FOREIGN KEY (`TipoPresentacion`) REFERENCES `tipo_presentaciones` (`ID`)
)

