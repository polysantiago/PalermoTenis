CREATE TABLE `valores_posibles` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Valor` varchar(128) NOT NULL,
  `TipoAtributo` int(10) unsigned DEFAULT NULL,
  `Orden` int(10) unsigned DEFAULT '1',
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_valores_posibles_4` FOREIGN KEY (`TipoAtributo`) REFERENCES `tipo_atributos` (`ID`)
)

