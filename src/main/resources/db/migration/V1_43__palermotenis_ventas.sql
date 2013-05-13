CREATE TABLE `ventas` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Cliente` int(10) unsigned NOT NULL,
  `Usuario` varchar(50) NOT NULL,
  `Total` double NOT NULL DEFAULT '0',
  `Cuotas` int(11) NOT NULL DEFAULT '0',
  `Pago` int(10) unsigned NOT NULL,
  `Fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `FK_ventas_1` (`Pago`),
  KEY `FK_ventas_2` (`Cliente`),
  KEY `Usuario` (`Usuario`),
  CONSTRAINT `FK_ventas_1` FOREIGN KEY (`Pago`) REFERENCES `pagos` (`Codigo`),
  CONSTRAINT `FK_ventas_2` FOREIGN KEY (`Cliente`) REFERENCES `clientes` (`ID`),
  CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`Usuario`) REFERENCES `usuarios` (`usuario`)
)

