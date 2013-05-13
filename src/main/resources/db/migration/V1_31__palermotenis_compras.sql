CREATE TABLE `compras` (
  `ID` int(11) NOT NULL,
  `Fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Usuario` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
)

