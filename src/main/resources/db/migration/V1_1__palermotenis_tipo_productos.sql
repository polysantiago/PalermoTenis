CREATE TABLE `tipo_productos` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Presentable` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
)
