DELIMITER $$

CREATE PROCEDURE `moverModelo`(IN modeloID INT, IN modelo_orden INT, IN modelo_right_ID INT, IN modelo_right_orden INT) MODIFIES SQL DATA
BEGIN
 	DECLARE max_orden INT;
	IF modelo_right_ID IS NULL THEN
		SELECT MAX(Orden) INTO max_orden FROM modelos;
		UPDATE modelos SET orden = max_orden + 1 WHERE ID = modeloID;
	ELSE
		UPDATE modelos SET orden = modelo_right_orden WHERE ID = modeloID;	
		UPDATE modelos SET orden = orden + 1 WHERE orden > modelo_right_orden;
		UPDATE modelos SET orden = orden + 1 WHERE ID = modelo_right_ID;
		UPDATE modelos SET orden = orden - 1 WHERE orden > modelo_orden;
	END IF;
END$$

CREATE PROCEDURE `moverOferta`(productoID INT, orig_orden INT, rightID INT, right_orden INT) MODIFIES SQL DATA
BEGIN
	DECLARE max_orden INT;
	DECLARE producto_orden INT;
	IF rightID IS NULL THEN		
		SELECT MAX(sq.Orden) INTO max_orden FROM 
			(SELECT 
				(CASE WHEN pp.EnOferta = 0 OR pp.EnOferta IS NULL THEN pu.Orden ELSE pp.Orden END) AS Orden
			FROM productos p
			INNER JOIN stock s ON p.ID = s.Producto
			LEFT JOIN precios_presentaciones pp ON p.ID = pp.Producto
			LEFT JOIN precios_unidad pu ON p.ID = pu.Producto
			WHERE (pp.EnOferta = 1 OR pu.EnOferta = 1) AND s.stock > 0) AS sq;
		UPDATE precios_presentaciones SET orden = max_orden + 1 WHERE Producto = productoID;
		UPDATE precios_unidad SET orden = max_orden + 1 WHERE Producto = productoID;
	ELSE
		UPDATE precios_presentaciones SET orden = right_orden WHERE Producto = productoID;
		UPDATE precios_unidad SET orden = right_orden WHERE Producto = productoID;

		UPDATE precios_presentaciones SET Orden = Orden + 1 WHERE Orden > right_orden AND EnOferta = 1;
		UPDATE precios_unidad SET Orden = Orden + 1 WHERE Orden > right_orden AND EnOferta = 1;

		UPDATE precios_presentaciones SET Orden = Orden + 1 WHERE Producto = rightID;
		UPDATE precios_unidad SET Orden = Orden + 1 WHERE Producto = rightID;

		UPDATE precios_presentaciones SET Orden = Orden - 1 WHERE Orden > orig_orden;
		UPDATE precios_unidad SET Orden = Orden - 1 WHERE Orden > orig_orden;
	END IF;
END$$

DELIMITER ;