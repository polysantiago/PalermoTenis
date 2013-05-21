CREATE TABLE `atributos` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Producto` int(10) unsigned NOT NULL,
  `TipoAtributo` int(10) unsigned NOT NULL,
  `Valor` varchar(128) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL,
  `ValorPosible` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_atributos_5` FOREIGN KEY (`ValorPosible`) REFERENCES `valores_posibles` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `FK_atributos_productos` FOREIGN KEY (`Producto`) REFERENCES `productos` (`ID`),
  CONSTRAINT `FK_atributos_tipoatributo` FOREIGN KEY (`TipoAtributo`) REFERENCES `tipo_atributos` (`ID`)
)
