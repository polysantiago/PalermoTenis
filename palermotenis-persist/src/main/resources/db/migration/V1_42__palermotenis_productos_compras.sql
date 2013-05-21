CREATE TABLE `productos_compras` (
  `ID` int(11) NOT NULL,
  `Compra` int(11) NOT NULL,
  `Producto` varchar(128) NOT NULL,
  `Costo` double NOT NULL,
  `Proveedor` int(11) DEFAULT NULL,
  `Stock` int(11) NOT NULL,
  `Cantidad` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
)

