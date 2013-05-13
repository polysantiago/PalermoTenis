ALTER TABLE categorias_modelos DROP FOREIGN KEY FK_deportes_modelos_1;
ALTER TABLE categorias_modelos 
  ADD CONSTRAINT FK_deportes_modelos_1
  FOREIGN KEY (categoria)
  REFERENCES categorias (ID)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE categorias_modelos DROP FOREIGN KEY FK_deportes_modelos_2;
ALTER TABLE categorias_modelos 
  ADD CONSTRAINT FK_deportes_modelos_2
  FOREIGN KEY (modelo)
  REFERENCES modelos (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  
