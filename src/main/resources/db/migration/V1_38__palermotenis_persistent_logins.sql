CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` date NOT NULL,
  PRIMARY KEY (`series`),
  KEY `FK_persisten_logins_clientes` (`username`),
  CONSTRAINT `persistent_logins_ibfk_1` FOREIGN KEY (`username`) REFERENCES `usuarios` (`usuario`) ON DELETE CASCADE ON UPDATE CASCADE
)

