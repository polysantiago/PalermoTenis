CREATE TABLE `usuarios` (
  `usuario` varchar(50) NOT NULL,
  `cliente` int(10) unsigned DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  `activo` tinyint(1) NOT NULL,
  PRIMARY KEY (`usuario`),
  UNIQUE KEY `cliente` (`cliente`),
  CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
)

