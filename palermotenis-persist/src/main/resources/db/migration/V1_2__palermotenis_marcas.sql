CREATE TABLE `marcas` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Imagen` longblob,
  `Tamanio` bigint(20) unsigned DEFAULT NULL,
  `ContentType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
)

