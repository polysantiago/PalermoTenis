CREATE TABLE `listados` (
  `ID` varchar(10) NOT NULL,
  `Cliente` int(10) unsigned NOT NULL,
  `Pago` int(10) unsigned NOT NULL,
  `Cuotas` int(11) NOT NULL,
  `Total` double NOT NULL,
  `Cod_Autorizacion` varchar(10) NOT NULL,
  `Autorizado` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Cliente` (`Cliente`),
  KEY `Pago` (`Pago`),
  CONSTRAINT `listados_ibfk_1` FOREIGN KEY (`Cliente`) REFERENCES `clientes` (`ID`),
  CONSTRAINT `listados_ibfk_2` FOREIGN KEY (`Pago`) REFERENCES `pagos` (`Codigo`)
)

