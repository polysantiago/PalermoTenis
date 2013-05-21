CREATE TABLE `suscriptores` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Email` varchar(45) NOT NULL,
  `RandomStr` varchar(9) NOT NULL,
  `Activo` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
)

