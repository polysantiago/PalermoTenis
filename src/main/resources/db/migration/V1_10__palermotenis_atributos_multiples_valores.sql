CREATE TABLE `atributos_multiples_valores` (
  `Producto` int(10) unsigned NOT NULL,
  `ValorPosible` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Producto`,`ValorPosible`),
  KEY `FK_atributos_multiples_valores_2` (`ValorPosible`),
  CONSTRAINT `FK_atributos_multiples_valores_1` FOREIGN KEY (`Producto`) REFERENCES `productos` (`ID`),
  CONSTRAINT `FK_atributos_multiples_valores_2` FOREIGN KEY (`ValorPosible`) REFERENCES `valores_posibles` (`ID`)
)

