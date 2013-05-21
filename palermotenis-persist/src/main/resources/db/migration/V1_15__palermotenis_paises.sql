CREATE TABLE `paises` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Codigo` varchar(2) NOT NULL,
  `Nombre` varchar(65) NOT NULL,
  `Idioma` int(10) unsigned NOT NULL,
  `Moneda` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_paises_moneda` (`Moneda`),
  KEY `FK_paises_idioma` (`Idioma`),
  CONSTRAINT `FK_paises_idioma` FOREIGN KEY (`Idioma`) REFERENCES `idiomas` (`ID`),
  CONSTRAINT `FK_paises_moneda` FOREIGN KEY (`Moneda`) REFERENCES `monedas` (`ID`)
)
