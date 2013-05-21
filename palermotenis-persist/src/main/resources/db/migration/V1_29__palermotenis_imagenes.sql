CREATE TABLE `imagenes` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `HashKey` varchar(32) DEFAULT NULL,
  `Tipo` varchar(45) NOT NULL,
  `Tamanio` bigint(20) unsigned NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Categoria` varchar(45) DEFAULT 'Grande',
  `Modelo` int(10) unsigned NOT NULL,
  `Alto` int(10) unsigned NOT NULL,
  `Ancho` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_imagenes_1` (`Modelo`),
  CONSTRAINT `FK_imagenes_1` FOREIGN KEY (`Modelo`) REFERENCES `modelos` (`id`)
)

