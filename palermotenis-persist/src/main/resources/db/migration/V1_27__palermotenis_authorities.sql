CREATE TABLE `authorities` (
  `usuario` varchar(50) NOT NULL,
  `rol` int(11) NOT NULL,
  PRIMARY KEY (`usuario`,`rol`),
  KEY `rol` (`rol`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `usuarios` (`usuario`) ON DELETE CASCADE,
  CONSTRAINT `authorities_ibfk_2` FOREIGN KEY (`rol`) REFERENCES `roles` (`ID`) ON DELETE CASCADE
)

