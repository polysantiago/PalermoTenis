-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.45-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema palermotenis
--

CREATE DATABASE IF NOT EXISTS palermotenis;
USE palermotenis;

--
-- Definition of table `modelos`
--

DROP TABLE IF EXISTS `modelos`;
CREATE TABLE `modelos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `tipoProducto` int(10) unsigned NOT NULL,
  `marca` int(10) unsigned NOT NULL,
  `deporte` int(10) unsigned DEFAULT NULL,
  `lft` int(10) unsigned DEFAULT NULL,
  `rgt` int(10) unsigned DEFAULT NULL,
  `padre` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_modelos_1` (`marca`),
  KEY `FK_modelos_2` (`tipoProducto`),
  CONSTRAINT `FK_modelos_1` FOREIGN KEY (`marca`) REFERENCES `marcas` (`ID`),
  CONSTRAINT `FK_modelos_2` FOREIGN KEY (`tipoProducto`) REFERENCES `tipo_productos` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=431 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `modelos`
--

/*!40000 ALTER TABLE `modelos` DISABLE KEYS */;
INSERT INTO `modelos` (`id`,`nombre`,`tipoProducto`,`marca`,`deporte`,`lft`,`rgt`,`padre`) VALUES 
 (1,'Aero Pro',1,1,1,1,6,NULL),
 (2,'Pro Drive',1,1,1,2,3,1),
 (3,'Pro Drive Plus',1,1,1,4,5,1),
 (4,'Pure',1,1,1,7,32,NULL),
 (5,'Drive',1,1,1,8,9,4),
 (6,'Drive Plus',1,1,1,10,11,4),
 (7,'Drive Roddick',1,1,1,12,13,4),
 (8,'Drive Roddick Plus',1,1,1,14,15,4),
 (9,'Drive GT',1,1,1,16,17,4),
 (10,'Drive GT Plus',1,1,1,18,19,4),
 (11,'Drive Roddick GT',1,1,1,20,21,4),
 (12,'Drive Roddick GT Plus',1,1,1,22,23,4),
 (13,'Storm 2008',1,1,1,24,25,4),
 (14,'Storm Tour',1,1,1,26,27,4),
 (15,'Storm Tour Plus',1,1,1,28,29,4),
 (16,'Storm Limited Edition',1,1,1,30,31,4),
 (17,'Aero',1,1,1,33,38,NULL),
 (18,'Storm',1,1,1,34,35,17),
 (19,'Storm Tour',1,1,1,36,37,17),
 (20,'Y 112',1,1,1,39,40,NULL),
 (21,'You Tek',1,2,1,41,60,NULL),
 (22,'YouTek Speed Pro',1,2,1,42,43,21),
 (23,'YouTek Speed MP 18 x 20',1,2,1,44,45,21),
 (24,'YouTek Speed MP 16 x 19',1,2,1,46,47,21),
 (26,'YouTek Speed Lite',1,2,1,48,49,21),
 (27,'Microgel',1,2,1,61,96,NULL),
 (28,'Microgel Radical Midplus',1,2,1,62,63,27),
 (29,'Microgel Radical Oversize',1,2,1,64,65,27),
 (30,'Microgel Radical Pro',1,2,1,66,67,27),
 (31,'Extreme',1,2,1,68,69,27),
 (32,'Microgel Extreme Pro',1,2,1,70,71,27),
 (33,'Microgel Extreme Teflon 2009',1,2,1,72,73,27),
 (34,'Microgel Extreme Pro Teflon 2009',1,2,1,74,75,27),
 (35,'Extreme Team 2009',1,2,1,76,77,27),
 (36,'Extreme Teflon 2009',1,2,1,78,79,27),
 (37,'Prestige Midplus',1,2,1,80,81,27),
 (38,'Prestige Midsize',1,2,1,82,83,27),
 (39,'Microgel Prestige Pro',1,2,1,84,85,27),
 (40,'Microgel Prestige Team',1,2,1,86,87,27),
 (41,'Microgel Mojo',1,2,1,88,89,27),
 (42,'Monster',1,2,1,90,91,27),
 (43,'Microgel Instinct',1,2,1,92,93,27),
 (44,'Airflow',1,2,1,97,100,NULL),
 (45,'Airflow 1',1,2,1,98,99,44),
 (46,'Metallix',1,2,1,101,106,NULL),
 (47,'Metallix 2',1,2,1,102,103,46),
 (48,'Metallix 4',1,2,1,104,105,46),
 (57,'Liquidmetal',1,2,1,107,116,NULL),
 (58,'Rave Pro',1,2,1,108,109,57),
 (59,'Fire Pro',1,2,1,110,111,57),
 (60,'1 Midplus',1,2,1,112,113,57),
 (61,'1 Pro Oversize',1,2,1,114,115,57),
 (62,'Ti',1,2,1,117,124,NULL),
 (63,'Elite',1,2,1,118,119,62),
 (64,'5000',1,2,1,120,121,62),
 (65,'5200',1,2,1,122,123,62),
 (70,'K Factor',1,4,1,125,172,NULL),
 (71,'K Six One Tour 90',1,4,1,126,127,70),
 (72,'K Six One 95 16 x 18',1,4,1,128,129,70),
 (73,'K Six One 95 18x20',1,4,1,130,131,70),
 (74,'KSix-One 95 16x18 Plus',1,4,1,132,133,70),
 (75,'KSix-One Team',1,4,1,134,135,70),
 (76,'K Blade Tour',1,4,1,136,137,70),
 (77,'K Blade 98',1,4,1,138,139,70),
 (78,'K Blade Team',1,4,1,140,141,70),
 (79,'K Pro Tour',1,4,1,142,143,70),
 (80,'K Pro Open',1,4,1,144,145,70),
 (81,'K Tour',1,4,1,146,147,70),
 (82,'K Six Two',1,4,1,148,149,70),
 (83,'K Surge',1,4,1,150,151,70),
 (84,'K Four MP',1,4,1,152,153,70),
 (85,'K Four OS',1,4,1,154,155,70),
 (86,'K Zen',1,4,1,156,157,70),
 (87,'K Five 98 MP',1,4,1,158,159,70),
 (88,'K Five 108 OS',1,4,1,160,161,70),
 (89,'K Three',1,4,1,162,163,70),
 (90,'K One',1,4,1,164,165,70),
 (91,'K Pro Staff 88',1,4,1,166,167,70),
 (92,'K Kobra Tour',1,4,1,168,169,70),
 (93,'N Code',1,4,1,173,186,NULL),
 (94,'N Flash Orange',1,4,1,174,175,93),
 (95,'N Flash Blue',1,4,1,176,177,93),
 (96,'N Six Two Midplus',1,4,1,178,179,93),
 (97,'N Six Two OS',1,4,1,180,181,93),
 (98,'N Fury',1,4,1,182,183,93),
 (99,'N 4 Midplus',1,4,1,184,185,93),
 (100,'Hammer',1,4,1,187,192,NULL),
 (101,'6.3 midplus',1,4,1,188,189,100),
 (102,'6.3 oversize',1,4,1,190,191,100),
 (103,'Nano Carbon',1,4,1,193,200,NULL),
 (104,'Hope',1,4,1,194,195,103),
 (105,'Champion',1,4,1,196,197,103),
 (106,'Tour',1,4,1,198,199,103),
 (107,'Comp',1,4,1,201,206,NULL),
 (108,'Six One Comp',1,4,1,202,203,107),
 (109,'Blade Comp',1,4,1,204,205,107),
 (110,'Junior',1,4,1,207,224,NULL),
 (111,'Federer 25',1,4,1,208,209,110),
 (112,'Federer 23',1,4,1,210,211,110),
 (113,'Federer 21',1,4,1,212,213,110),
 (114,'US Open 25',1,4,1,214,215,110),
 (115,'US Open 23',1,4,1,216,217,110),
 (116,'US Open 21',1,4,1,218,219,110),
 (117,'Nano Carbon 26',1,4,1,220,221,110),
 (118,'Nano Carbon 25',1,4,1,222,223,110),
 (119,'Exo3',1,3,1,225,234,NULL),
 (120,'Graphite 93',1,3,1,226,227,119),
 (121,'Graphite 100',1,3,1,228,229,119),
 (122,'Rebel 95',1,3,1,230,231,119),
 (123,'Rebel Team 95',1,3,1,232,233,119),
 (124,'O3 Speedport',1,3,1,235,254,NULL),
 (125,'Black',1,3,1,236,237,124),
 (126,'White MP',1,3,1,238,239,124),
 (127,'Pro White MP',1,3,1,240,241,124),
 (128,'Black Team',1,3,1,242,243,124),
 (129,'Black Longbody',1,3,1,244,245,124),
 (130,'Gold',1,3,1,246,247,124),
 (131,'Silver',1,3,1,248,249,124),
 (132,'Platinum',1,3,1,250,251,124),
 (133,'Tour',1,3,1,252,253,124),
 (134,'Ozone',1,3,1,255,260,NULL),
 (135,'Pro Tour MP',1,3,1,256,257,134),
 (136,'Tour MP',1,3,1,258,259,134),
 (137,'O3',1,3,1,261,270,NULL),
 (138,'White',1,3,1,262,263,137),
 (139,'Hybrid Tour',1,3,1,264,265,137),
 (140,'Hybrid Hornet',1,3,1,266,267,137),
 (141,'Hybrid Shark',1,3,1,268,269,137),
 (142,'Air-O-Grafito',1,3,1,271,276,NULL),
 (143,'Air-O-Rage',1,3,1,272,273,142),
 (144,'Air-O-Bolt',1,3,1,274,275,142),
 (145,'Classic Grafito',1,3,1,277,284,NULL),
 (146,'Classic Impact Ti',1,3,1,278,279,145),
 (147,'Classic Quake Ti',1,3,1,280,281,145),
 (148,'Classic Lite Ti',1,3,1,282,283,145),
 (149,'Air-O-Composite',1,3,1,285,290,NULL),
 (150,'Air-O-Steam',1,3,1,286,287,149),
 (151,'Air-O-Impact',1,3,1,288,289,149),
 (152,'Power Line',1,3,1,291,294,NULL),
 (153,'Titan',1,3,1,292,293,152),
 (154,'Junior',1,3,1,295,302,NULL),
 (155,'Maria 25',1,3,1,296,297,154),
 (156,'Maria 23',1,3,1,298,299,154),
 (157,'Maria 21',1,3,1,300,301,154),
 (158,'Competition',2,8,1,303,304,NULL),
 (169,'Axelerate Pro',8,9,1,305,306,NULL),
 (170,'Team All Court Roddick',8,1,1,307,308,NULL),
 (171,'Atom',8,2,1,309,310,NULL),
 (172,'Cross Fire II',8,4,1,311,312,NULL),
 (173,'Brasil',8,12,1,313,314,NULL),
 (174,'Championship sello Negro',3,13,1,315,316,NULL),
 (175,'High damp',10,1,1,317,318,NULL),
 (176,'Custom damp (200)',11,1,1,319,320,NULL),
 (177,'Pro Team Tacky',12,1,1,323,324,NULL),
 (178,'Extreme',5,2,1,325,328,NULL),
 (179,'Supercombi',5,2,1,326,327,178),
 (224,'pirulito',12,8,NULL,329,330,NULL),
 (225,'sensation',2,4,NULL,331,332,NULL),
 (227,'Trance Open',8,4,NULL,333,334,NULL),
 (228,'Speed Pro AX',8,9,NULL,335,336,NULL),
 (229,'Speedzone AX',8,9,NULL,337,338,NULL),
 (231,'pro hurricane tour',2,1,NULL,339,340,NULL),
 (232,'Championship',3,4,NULL,341,342,NULL),
 (233,'US Open',3,4,NULL,343,344,NULL),
 (234,'Polylon Confort',2,14,NULL,345,346,NULL),
 (235,'Conquest',2,1,NULL,347,348,NULL),
 (236,'Ballistic Polymono',2,1,NULL,349,350,NULL),
 (237,'Head Logo II Dampener',11,2,NULL,351,352,NULL),
 (238,'Head Smartsorb',11,2,NULL,353,354,NULL),
 (239,'Open',8,4,NULL,355,356,NULL),
 (241,'Babolat Trophy x 3',3,1,NULL,357,358,NULL),
 (242,'Babolat Championship Gold x 3',3,1,NULL,359,360,NULL),
 (243,'Babolat Team x 3',3,1,NULL,361,362,NULL),
 (244,'Head ATP Gold x 3',3,2,NULL,363,364,NULL),
 (245,'Play and Stay',14,3,NULL,365,366,NULL),
 (246,'Superbreak Lisas',5,15,NULL,367,398,NULL),
 (249,'Navy',5,15,NULL,368,369,246),
 (251,'Black',5,15,NULL,370,371,246),
 (252,'White',5,15,NULL,372,373,246),
 (253,'Carbonic Grey',5,15,NULL,374,375,246),
 (254,'Heiny',5,15,NULL,376,377,246),
 (255,'Calipso Blue',5,15,NULL,378,379,246),
 (256,'Racer Yellow',5,15,NULL,380,381,246),
 (257,'Red Tape',5,15,NULL,382,383,246),
 (258,'Guerrilla Green',5,15,NULL,384,385,246),
 (259,'Cuban Brown',5,15,NULL,386,387,246),
 (260,'Newbud Green',5,15,NULL,388,389,246),
 (261,'Plum Violet',5,15,NULL,390,391,246),
 (262,'Yellow Tang',5,15,NULL,392,393,246),
 (263,'Chocolate Chip',5,15,NULL,394,395,246),
 (264,'Classic Tan',5,15,NULL,396,397,246),
 (265,'Microgel Raptor Midplus',1,2,NULL,94,95,27),
 (266,'Speed Star Blanco/Celeste',8,9,NULL,399,400,NULL),
 (267,'Speed Star Blanco/Negro',8,9,NULL,401,402,NULL),
 (268,'Speed Pro III Rojo',8,9,NULL,403,404,NULL),
 (269,'SHT 106 White / Red',8,6,NULL,405,406,NULL),
 (270,'SHT 306 Blue/Navy',8,6,NULL,407,408,NULL),
 (271,'SHT 306 White / Red',8,6,NULL,409,410,NULL),
 (272,'SHT 306 CL Pearl / White',8,6,NULL,411,412,NULL),
 (306,'Iris Rojo',18,17,NULL,413,414,NULL),
 (307,'Iris Verde',18,17,NULL,415,416,NULL),
 (308,'Pro Shield Azul',18,17,NULL,417,418,NULL),
 (309,'Pro Shield Amarillo',18,17,NULL,419,420,NULL),
 (310,'Power Extreme Blanco y Gris',18,17,NULL,421,422,NULL),
 (311,'Power Extreme Gris y Amarillo',18,17,NULL,423,424,NULL),
 (312,'Max  Plus 2.0 Plateado con Verde',18,17,NULL,425,426,NULL),
 (313,'Max Plus 2.0 Amarillo con Verde y Negro',18,17,NULL,427,428,NULL),
 (314,'Max Plus 2.0 Gris con Naranja y Negro',18,17,NULL,429,430,NULL),
 (315,'Max Plus Mujer Blanco con Rosa',18,17,NULL,431,432,NULL),
 (316,'Storm Tech Naranja con Rojo',18,17,NULL,433,434,NULL),
 (317,'Storm Tech Negro con Gris y Azul',18,17,NULL,435,436,NULL),
 (318,'Vidia Gris con Negro y Naranja',18,17,NULL,437,438,NULL),
 (319,'Vidia Blanca con Azul y Verde Fluo',18,17,NULL,439,440,NULL),
 (320,'Vidia Rojo con Gris y Amarillo',18,17,NULL,441,442,NULL),
 (321,'Taura Negro con Blanco y Amarillo',18,17,NULL,443,444,NULL),
 (322,'Taura Gris con Azul y Blanco',18,17,NULL,445,446,NULL),
 (323,'Taura Negro con Blanco y Celeste',18,17,NULL,447,448,NULL),
 (324,'Targa Azul con Gris y Naranja',18,17,NULL,449,450,NULL),
 (325,'Solid Carbon Gris con Amarillo y Blanco',18,17,NULL,451,452,NULL),
 (326,'Xenta Azul Oscuro y Celeste con Blanco',18,17,NULL,453,454,NULL),
 (327,'Xenta Verde y Gris con Naranja',18,17,NULL,455,456,NULL),
 (328,'YouTek Speed Elite',1,2,NULL,50,51,21),
 (329,'Radical Pro',1,2,NULL,52,53,21),
 (330,'Speed Pro III Blanco y Negro',8,9,NULL,457,458,NULL),
 (331,'Sponge',10,4,NULL,459,460,NULL),
 (332,'Perforated',10,4,NULL,461,462,NULL),
 (333,'Contour',10,4,NULL,463,464,NULL),
 (334,'Micro Dry Max',10,4,NULL,465,466,NULL),
 (335,'Hydrosorb Blanco',10,2,NULL,467,468,NULL),
 (336,'Hydrosorb Negro',10,2,NULL,469,470,NULL),
 (337,'Dual Absorbing Blanco',10,2,NULL,471,472,NULL),
 (338,'Dual Absorbing Negro',10,2,NULL,473,474,NULL),
 (339,'Dual Absorving Azul',10,2,NULL,475,476,NULL),
 (340,'Dual Absorving Gris',10,2,NULL,477,478,NULL),
 (341,'Head Leather Tour Grip',10,2,NULL,479,480,NULL),
 (342,'Ergofeel Blanco',10,2,NULL,481,482,NULL),
 (343,'Hydrosorb Gris',10,2,NULL,483,484,NULL),
 (344,'Hydrosorb Azul',10,2,NULL,485,486,NULL),
 (345,'Tournagrip Original XL',12,19,NULL,487,488,NULL),
 (346,'Tourna Tac Rosa',12,19,NULL,489,490,NULL),
 (347,'Ace Blanco',8,9,NULL,491,492,NULL),
 (348,'Ace Negro',8,9,NULL,493,494,NULL),
 (349,'Speed Tech Blanco con Azul',8,9,NULL,495,496,NULL),
 (350,'Speed Fire Blanco / Rojo',8,9,NULL,497,498,NULL),
 (351,'Speed Fire Blanco / Azul / Plateado',8,9,NULL,499,500,NULL),
 (352,'Superbreak Estampadas',5,15,NULL,501,552,NULL),
 (354,'5NN',5,15,NULL,502,503,352),
 (355,'6GN',5,15,NULL,504,505,352),
 (356,'5YM',5,15,NULL,506,507,352),
 (357,'6MR',5,15,NULL,508,509,352),
 (358,'6EL',5,15,NULL,510,511,352),
 (359,'5RT',5,15,NULL,512,513,352),
 (360,'4PQ',5,15,NULL,514,515,352),
 (361,'4SY',5,15,NULL,516,517,352),
 (362,'6HE',5,15,NULL,518,519,352),
 (363,'5YW',5,15,NULL,520,521,352),
 (364,'4NX',5,15,NULL,522,523,352),
 (365,'6QM',5,15,NULL,524,525,352),
 (366,'5YT',5,15,NULL,526,527,352),
 (367,'5YS',5,15,NULL,528,529,352),
 (368,'Demon',18,2,NULL,553,554,NULL),
 (369,'4SZ',5,15,NULL,530,531,352),
 (370,'Fenix Team',18,2,NULL,555,556,NULL),
 (371,'5YN',5,15,NULL,532,533,352),
 (372,'3V2',5,15,NULL,534,535,352),
 (373,'3FP',5,15,NULL,536,537,352),
 (374,'Intruder',18,2,NULL,557,558,NULL),
 (375,'3FQ',5,15,NULL,538,539,352),
 (376,'3T4',5,15,NULL,540,541,352),
 (377,'Nano Ti.Evolution',18,2,NULL,559,560,NULL),
 (378,'2LM',5,15,NULL,542,543,352),
 (379,'3FZ',5,15,NULL,544,545,352),
 (380,'4NU',5,15,NULL,546,547,352),
 (381,'5MT',5,15,NULL,548,549,352),
 (382,'5ZQ',5,15,NULL,550,551,352),
 (383,'YouTek Radical Midplus',1,2,NULL,54,55,21),
 (384,'YouTek Radical Lite',1,2,NULL,56,57,21),
 (385,'YouTek Radical Oversize',1,2,NULL,58,59,21),
 (386,'K Pro Team FX',1,4,NULL,170,171,70),
 (387,'Super Smash',2,8,NULL,561,562,NULL),
 (388,'Basic Poly',2,8,NULL,563,564,NULL),
 (389,'Super Smash Spiky',2,8,NULL,565,566,NULL),
 (390,'Touch Turbo',2,8,NULL,567,568,NULL),
 (391,'Touch Classic',2,8,NULL,569,570,NULL),
 (392,'Spiky Shark',2,8,NULL,571,572,NULL),
 (393,'Pro Line I',2,8,NULL,573,574,NULL),
 (394,'Pro Line II',2,8,NULL,575,576,NULL),
 (395,'P2',2,8,NULL,577,578,NULL),
 (396,'Touch Multifibre',2,8,NULL,579,580,NULL),
 (397,'Basic ProfiTour',2,8,NULL,581,582,NULL),
 (398,'Polylon',2,14,NULL,583,584,NULL),
 (399,'Big Banger Alu Power',2,20,NULL,585,586,NULL),
 (400,'Big Banger Original',2,20,NULL,587,588,NULL),
 (401,'Big Banger Alu Fluoro',2,20,NULL,589,590,NULL),
 (402,'Big Banger Alu Power Rough',2,20,NULL,591,592,NULL),
 (403,'Big Banger Alu Power Spin',2,20,NULL,593,594,NULL),
 (404,'Big BangerOriginal Rough',2,20,NULL,595,596,NULL),
 (405,'Big Banger Ace',2,20,NULL,597,598,NULL),
 (406,'Big Banger TiMO',2,20,NULL,599,600,NULL),
 (407,'Big Banger XP',2,20,NULL,601,602,NULL),
 (408,'Monotec Zolo',2,20,NULL,603,604,NULL),
 (409,'M2 Plus',2,20,NULL,605,606,NULL),
 (410,'M2 Pro',2,20,NULL,607,608,NULL),
 (411,'Toalson Rencon',2,21,NULL,609,610,NULL),
 (412,'Toalson Cyberblade Thermaxe Tour',2,21,NULL,611,612,NULL),
 (413,'Asterisk',2,21,NULL,613,614,NULL),
 (414,'Toalson Synthetic Gut',2,21,NULL,615,616,NULL),
 (415,'Toalson Toa Gold',2,21,NULL,617,618,NULL),
 (416,'Toalson Cyber Spin',2,21,NULL,619,620,NULL),
 (417,'Men 2009 New York Crew Blanco / Verde',19,6,NULL,621,622,NULL),
 (418,'Men 2009 New York Crew Blanco / Azul',19,6,NULL,623,624,NULL),
 (419,'Men 2009 Open Short Black',20,6,NULL,625,626,NULL),
 (420,'Men 2009 Open Short Navy',20,6,NULL,627,628,NULL),
 (421,'Babolat IG Antivibrador',11,1,NULL,629,630,NULL),
 (422,'Babolat RVS',11,1,NULL,321,322,NULL),
 (423,'Wilson Pro Feel Plus Negro',11,4,NULL,629,630,NULL),
 (424,'Wilson Pro Feel Plus Rojo',11,4,NULL,631,632,NULL),
 (425,'Antivibrador',11,8,NULL,633,634,NULL),
 (426,'Antivibrador Blister x 2',11,5,NULL,635,636,NULL),
 (427,'NXG Tour Damp',11,3,NULL,637,638,NULL),
 (428,'Shock Trap',11,4,NULL,639,640,NULL),
 (429,'Vibra Fun x 2',11,4,NULL,641,642,NULL),
 (430,'Bowl Fun x 75 unidades',11,4,NULL,643,644,NULL);
/*!40000 ALTER TABLE `modelos` ENABLE KEYS */;


--
-- Definition of procedure `borrarModelo`
--

DROP PROCEDURE IF EXISTS `borrarModelo`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `borrarModelo`(OUT rowcount INT, IN modeloID INT)
    MODIFIES SQL DATA
BEGIN
        SELECT @myLeft := lft, @myRight := rgt, @myWidth := rgt - lft + 1
        FROM modelos WHERE id = modeloID;


        DELETE FROM modelos WHERE lft BETWEEN @myLeft AND @myRight;


        UPDATE modelos SET rgt = rgt - @myWidth WHERE rgt > @myRight;
        UPDATE modelos SET lft = lft - @myWidth WHERE lft > @myRight;


        SET rowcount = 1;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `insertarModelo`
--

DROP PROCEDURE IF EXISTS `insertarModelo`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarModelo`(IN pmarca INT, IN pnombre VARCHAR(45),IN modeloPadre INT, IN ptipoProducto INT)
    MODIFIES SQL DATA
BEGIN
        IF modeloPadre IS NULL THEN
                SELECT @myRight := MAX(rgt) FROM modelos;

                INSERT INTO modelos(nombre,lft,rgt,padre,tipoProducto,marca) VALUES (pnombre,(@myRight + 1),(@myRight + 2),modeloPadre,ptipoProducto,pmarca);
        ELSE
                SELECT @myRight := rgt FROM modelos WHERE id = modeloPadre;

                UPDATE modelos
                        SET lft = CASE WHEN lft > @myRight THEN lft + 2
                                       ELSE lft END,
                            rgt = CASE WHEN rgt >= @myRight THEN rgt + 2
                                       ELSE rgt END
                WHERE rgt >= @myRight;

                INSERT INTO modelos(nombre,lft,rgt,padre,tipoProducto,marca) VALUES (pnombre,@myRight,(@myRight + 1),modeloPadre,ptipoProducto,pmarca);
        END IF;
        SELECT LAST_INSERT_ID() AS id;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
