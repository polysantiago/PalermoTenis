RENAME TABLE deportes TO categorias, deportes_modelos TO categorias_modelos;

-- Rename column
ALTER TABLE categorias_modelos DROP FOREIGN KEY FK_deportes_modelos_1;
ALTER TABLE categorias_modelos CHANGE COLUMN deporte categoria INT(10) UNSIGNED NOT NULL, 
  ADD CONSTRAINT FK_deportes_modelos_1
  FOREIGN KEY (categoria)
  REFERENCES categorias (ID);
