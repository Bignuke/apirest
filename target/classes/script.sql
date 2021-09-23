CREATE TABLE `parametros` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`descricao` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`valor` TEXT NOT NULL COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `cliente` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`tipoPessoa` CHAR(1) NOT NULL COLLATE 'utf8_general_ci',
	`documento` VARCHAR(14) NOT NULL COLLATE 'utf8_general_ci',
	`nome` VARCHAR(100) NOT NULL COLLATE 'utf8_general_ci',
	`razaoSocial` VARCHAR(100) NULL COLLATE 'utf8_general_ci',
	`dataCadastro` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `usuario` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`idCliente` INT NOT NULL,
	`login` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`senha` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`administrador` TINYINT NOT NULL DEFAULT '0',
	`dataCadastro` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_USUARIO_CLIENTE` (`idCliente`) USING BTREE,
	CONSTRAINT `FK_USUARIO_CLIENTE` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `boleto` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`idUsuario` INT NOT NULL,
	`authenticationCode` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`account_branch` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`account_number` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`documentNumber` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`amount_currency` CHAR(3) NOT NULL DEFAULT 'BRL' COLLATE 'utf8_general_ci',
	`amount_value` DOUBLE(10,2) NOT NULL,
	`dueDate` TIMESTAMP NOT NULL,
	`emissionFee` TINYINT NULL,
	`type` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`payer_document` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`payer_name` VARCHAR(100) NULL COLLATE 'utf8_general_ci',
	`payer_tradeName` VARCHAR(100) NULL COLLATE 'utf8_general_ci',
	`payer_address_addressLine` VARCHAR(100) NULL COLLATE 'utf8_general_ci',
	`payer_address_city` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`payer_address_state` CHAR(2) NULL COLLATE 'utf8_general_ci',
	`payer_address_zipCode` VARCHAR(10) NULL COLLATE 'utf8_general_ci',
	`payer_address_neighborhood` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`payer_address_country` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`updatedAt` TIMESTAMP NULL,
	`ourNumber` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`digitable` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`status` VARCHAR(10) NULL COLLATE 'utf8_general_ci',
	`document` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`emissionDate` TIMESTAMP NULL,
	`recipientOrigin_document` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`recipientOrigin_name` VARCHAR(100) NULL COLLATE 'utf8_general_ci',
	`recipientOrigin_tradeName` VARCHAR(100) NULL COLLATE 'utf8_general_ci',
	`recipientOrigin_address_addressLine` VARCHAR(100) NULL COLLATE 'utf8_general_ci',
	`recipientOrigin_address_city` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`recipientOrigin_address_state` CHAR(2) NULL COLLATE 'utf8_general_ci',
	`recipientOrigin_address_zipCode` VARCHAR(10) NULL COLLATE 'utf8_general_ci',
	`recipientOrigin_address_neighborhood` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`recipientOrigin_address_country` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`recipientFinal_document` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`recipientFinal_name` VARCHAR(100) NULL COLLATE 'utf8_general_ci',
	`recipientFinal_tradeName` VARCHAR(100) NULL COLLATE 'utf8_general_ci',
	`recipientFinal_address_addressLine` VARCHAR(100) NULL COLLATE 'utf8_general_ci',
	`recipientFinal_address_city` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`recipientFinal_address_state` CHAR(2) NULL COLLATE 'utf8_general_ci',
	`recipientFinal_address_zipCode` VARCHAR(10) NULL COLLATE 'utf8_general_ci',
	`recipientFinal_address_neighborhood` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`recipientFinal_address_country` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`dataCadastro` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
	`dataAtualizacao` TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_BOLETO_USUARIO` (`idUsuario`) USING BTREE,
	CONSTRAINT `FK_BOLETO_USUARIO` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=6
;


CREATE TABLE `pagamento` (
	`idBoleto` INT NOT NULL,
	`id` VARCHAR(100) NOT NULL COLLATE 'utf8_general_ci',
	`paymentChannel` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`paidOutDate` DATETIME NULL,
	`amount` DOUBLE(10,2) NULL,
	PRIMARY KEY (`idBoleto`, `id`) USING BTREE,
	INDEX `FK_PAYMENT_BOLETO` (`idBoleto`) USING BTREE,
	CONSTRAINT `FK_PAYMENT_BOLETO` FOREIGN KEY (`idBoleto`) REFERENCES `boleto` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `servico` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`nome` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `precificacao` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`id_cliente` INT NOT NULL,
	`id_servico` INT NOT NULL,
	`valor` DOUBLE(10,2) NULL DEFAULT NULL,
	`vigencia` DATE NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_PRECIFICACAO_CLIENTE` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`),
	CONSTRAINT `FK_PRECIFICACAO_SERVICO` FOREIGN KEY (`id_servico`) REFERENCES `servico` (`id`)
)
COLLATE='utf8_general_ci'
;


INSERT INTO cliente VALUES (1,'J','00000000000000','teste','teste ltda',NOW());
INSERT INTO usuario VALUES (1,1,'admin',MD5('admin'),1,NOW());
INSERT INTO servico VALUES (1, 'Emissão de Boletos');
INSERT INTO precificacao VALUES (1, 1, 1, 1.25, '2021-09-01');


INSERT INTO parametros (descricao, valor) VALUES 
('bankly.link_auth', 'https://login.sandbox.bankly.com.br'),
('bankly.link_api', 'https://api.sandbox.bankly.com.br'),
('bankly.grant_type', 'client_credentials'),
('bankly.client_id', '7bb94e5e-95d1-4b17-a4a4-049f788266c8'),
('bankly.client_secret', 'BmQDGuS%23usy%252okA%26Q%24KEA9QgJneC%26bf'),
('token.access_token', ''),
('token.create_token', ''),
('token.expire_token', '');
-- UPDATE parametros SET valor='https://login.bankly.com.br' WHERE id=1;
-- UPDATE parametros SET valor='https://api.bankly.com.br' WHERE id=2;




/*
CREATE TABLE `address` (
	`id_address` INT NOT NULL AUTO_INCREMENT,
	`address_line` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`city` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`state` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`zip_code` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`neighborhood` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`country` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id_address`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `person` (
	`id_person` INT NOT NULL AUTO_INCREMENT,
	`document` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`name` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`trade_name` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`id_address` INT NULL,
	PRIMARY KEY (`id_person`) USING BTREE,
	INDEX `FK_PERSON_ADDRESS` (`id_address`) USING BTREE,
	CONSTRAINT `FK_PERSON_ADDRESS` FOREIGN KEY (`id_address`) REFERENCES `eaglecapital`.`address` (`id_address`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `boleto` (
	`id_boleto` INT NOT NULL AUTO_INCREMENT,
	`id_usuario` INT NOT NULL,
	`data_cadastro` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`account_branch` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`account_number` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`document_number` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`amount_currency` CHAR(3) NOT NULL COLLATE 'utf8_general_ci',
	`amount_value` DOUBLE(10,2) NOT NULL,
	`due_date` DATETIME NOT NULL,
	`authentication_code` VARCHAR(100) NOT NULL COLLATE 'utf8_general_ci',
	`alias` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`emission_fee` TINYINT NULL,
	`type` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`id_payer` INT NULL,
	`updated_at` DATETIME NULL,
	`our_number` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`digitable` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`status` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`document` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`emission_date` DATETIME NULL,
	`id_recipient_final` INT NULL,
	`id_recipient_origin` INT NULL,
	`data_atualizacao` DATETIME NULL,
	PRIMARY KEY (`id_boleto`) USING BTREE,
	INDEX `FK_BOLETO_CLIENTE` (`id_usuario`) USING BTREE,
	CONSTRAINT `FK_BOLETO_USUARIO` FOREIGN KEY (`id_usuario`) REFERENCES `eaglecapital`.`usuario` (`id_usuario`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
*/