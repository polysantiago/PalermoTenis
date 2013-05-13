CREATE TABLE `imagenes_escaladas` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ContentType` varchar(45) NOT NULL,
  `Imagen` longblob NOT NULL,
  `Tamanio` bigint(20) unsigned NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Tipo` char(1) NOT NULL,
  `ImagenOriginal` int(10) unsigned NOT NULL,
  `Alto` int(10) unsigned NOT NULL,
  `Ancho` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_imagenes_escaladas_1` (`ImagenOriginal`),
  KEY `FK_imagenes_escaladas_2` (`Tipo`),
  CONSTRAINT `FK_imagenes_escaladas_1` FOREIGN KEY (`ImagenOriginal`) REFERENCES `imagenes` (`ID`),
  CONSTRAINT `FK_imagenes_escaladas_2` FOREIGN KEY (`Tipo`) REFERENCES `tipo_imagenes` (`Tipo`)
)

