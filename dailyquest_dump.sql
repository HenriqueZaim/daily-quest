
DROP TABLE IF EXISTS `grupo`;

CREATE TABLE `grupo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_atualizacao` datetime(6) DEFAULT NULL,
  `data_hora_criacao` datetime(6) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `grupo` WRITE;
INSERT INTO `grupo` VALUES (1,NULL,'2020-12-29 21:09:59.762778','grupo1','desc grupo 1'),(2,NULL,'2020-12-29 21:09:59.763096','grupo2','desc grupo 2'),(3,NULL,'2020-12-29 21:09:59.763181','grupo3','desc grupo 3');
UNLOCK TABLES;

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_atualizacao` datetime(6) DEFAULT NULL,
  `data_hora_criacao` datetime(6) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5171l57faosmj8myawaucatdw` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `usuario` WRITE;
INSERT INTO `usuario` VALUES (1,NULL,NULL,NULL,'user1@mail.com','$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG'),(2,NULL,NULL,NULL,'user2@mail.com','$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG'),(3,NULL,NULL,NULL,'user3@mail.com','$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG'),(4,NULL,NULL,NULL,'user4@mail.com','$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG'),(5,NULL,NULL,NULL,'user5@mail.com','$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG'),(6,NULL,NULL,NULL,'user6@mail.com','$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG'),(7,NULL,NULL,NULL,'user7@mail.com','$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG'),(8,NULL,NULL,NULL,'user8@mail.com','$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG'),(9,NULL,NULL,NULL,'user9@mail.com','$2a$10$IEQa4Gb7e2Hm.vPLO15SrupIE3LQSY/n7V1kXH2nNQgyCnHPY8RdG');
UNLOCK TABLES;

DROP TABLE IF EXISTS `participante`;

CREATE TABLE `participante` (
  `autoridade` varchar(255) DEFAULT NULL,
  `usuario_id` int NOT NULL,
  `grupo_id` int NOT NULL,
  PRIMARY KEY (`grupo_id`,`usuario_id`),
  KEY `FKgiggm4rtsjxnt34ojxv7by7l1` (`usuario_id`),
  CONSTRAINT `FKgiggm4rtsjxnt34ojxv7by7l1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKk89h09428j04bf7frt88oc9cj` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `participante` WRITE;
INSERT INTO `participante` VALUES ('ADMIN',1,1),('PARTICIPANTE',2,1),('PARTICIPANTE',3,1),('ADMIN',4,2),('PARTICIPANTE',5,2),('PARTICIPANTE',6,2),('ADMIN',7,3),('PARTICIPANTE',8,3),('PARTICIPANTE',9,3);
UNLOCK TABLES;

DROP TABLE IF EXISTS `periodo`;

CREATE TABLE `periodo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_atualizacao` datetime(6) DEFAULT NULL,
  `data_hora_criacao` datetime(6) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `data_hora_fim` datetime(6) DEFAULT NULL,
  `data_hora_inicio` datetime(6) DEFAULT NULL,
  `status_periodo` varchar(255) DEFAULT NULL,
  `tipo_periodo` varchar(255) DEFAULT NULL,
  `grupo_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpqp53mifk7o3oeyxfswjbjg5d` (`grupo_id`),
  CONSTRAINT `FKpqp53mifk7o3oeyxfswjbjg5d` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `periodo` WRITE;
INSERT INTO `periodo` VALUES (1,NULL,NULL,NULL,'2020-12-29 21:09:59.763254','2020-12-29 21:09:59.763237','INATIVO','MENSAL',1),(2,NULL,NULL,NULL,'2020-12-29 21:09:59.763295','2020-12-29 21:09:59.763281','ATIVO','MENSAL',1),(3,NULL,NULL,NULL,'2020-12-29 21:09:59.763320','2020-12-29 21:09:59.763309','INATIVO','MENSAL',2),(4,NULL,NULL,NULL,'2020-12-29 21:09:59.763343','2020-12-29 21:09:59.763333','ATIVO','MENSAL',2),(5,NULL,NULL,NULL,'2020-12-29 21:09:59.763364','2020-12-29 21:09:59.763354','INATIVO','MENSAL',3),(6,NULL,NULL,NULL,'2020-12-29 21:09:59.763386','2020-12-29 21:09:59.763375','ATIVO','MENSAL',3);
UNLOCK TABLES;


DROP TABLE IF EXISTS `relatorio`;

CREATE TABLE `relatorio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_hora_atualizacao` datetime(6) DEFAULT NULL,
  `data_hora_criacao` datetime(6) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `assunto` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `periodo_id` int DEFAULT NULL,
  `usuario_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK77dcyp783uefqwn2x3w5u40g` (`periodo_id`),
  KEY `FK7l4hqfh06mg31swmv6u3tklg1` (`usuario_id`),
  CONSTRAINT `FK77dcyp783uefqwn2x3w5u40g` FOREIGN KEY (`periodo_id`) REFERENCES `periodo` (`id`),
  CONSTRAINT `FK7l4hqfh06mg31swmv6u3tklg1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `relatorio` WRITE;
INSERT INTO `relatorio` VALUES 
(1,NULL,NULL,NULL,'Assunto','relatorio',1,1),
(2,NULL,NULL,NULL,'Assunto','relatorio',1,1),
(3,NULL,NULL,NULL,'Assunto','relatorio',2,1),
(4,NULL,NULL,NULL,'Assunto','relatorio',1,2),
(5,NULL,NULL,NULL,'Assunto','relatorio',1,2),
(6,NULL,NULL,NULL,'Assunto','relatorio',2,2),
(7,NULL,NULL,NULL,'Assunto','relatorio',1,3),
(8,NULL,NULL,NULL,'Assunto','relatorio',1,3),
(9,NULL,NULL,NULL,'Assunto','relatorio',2,3),
(10,NULL,NULL,NULL,'Assunto','relatorio',3,4),
(11,NULL,NULL,NULL,'Assunto','relatorio',3,4),
(12,NULL,NULL,NULL,'Assunto','relatorio',4,4),
(13,NULL,NULL,NULL,'Assunto','relatorio',3,5),
(14,NULL,NULL,NULL,'Assunto','relatorio',3,5),
(15,NULL,NULL,NULL,'Assunto','relatorio',4,5),
(16,NULL,NULL,NULL,'Assunto','relatorio',3,6),
(17,NULL,NULL,NULL,'Assunto','relatorio',3,6),
(18,NULL,NULL,NULL,'Assunto','relatorio',4,6),
(19,NULL,NULL,NULL,'Assunto','relatorio',5,7),
(20,NULL,NULL,NULL,'Assunto','relatorio',5,7),
(21,NULL,NULL,NULL,'Assunto','relatorio',6,7),
(22,NULL,NULL,NULL,'Assunto','relatorio',5,8),
(23,NULL,NULL,NULL,'Assunto','relatorio',5,8),
(24,NULL,NULL,NULL,'Assunto','relatorio',6,8),
(25,NULL,NULL,NULL,'Assunto','relatorio',5,9),
(26,NULL,NULL,NULL,'Assunto','relatorio',5,9),
(27,NULL,NULL,NULL,'Assunto','relatorio',6,9);
UNLOCK TABLES;

DROP TABLE IF EXISTS `usuario_tipo_usuario`;

CREATE TABLE `usuario_tipo_usuario` (
  `usuario_id` int NOT NULL,
  `tipo_usuario` varchar(255) DEFAULT NULL,
  KEY `FK6f68bpwavj3mmn73ho5g3cjbo` (`usuario_id`),
  CONSTRAINT `FK6f68bpwavj3mmn73ho5g3cjbo` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `usuario_tipo_usuario` WRITE;
INSERT INTO `usuario_tipo_usuario` VALUES (1,'ROLE_USUARIO'),(2,'ROLE_ADMIN'),(3,'ROLE_USUARIO'),(4,'ROLE_USUARIO'),(5,'ROLE_USUARIO'),(6,'ROLE_USUARIO'),(7,'ROLE_USUARIO'),(8,'ROLE_USUARIO'),(9,'ROLE_USUARIO');
UNLOCK TABLES;
