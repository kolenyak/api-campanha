CREATE SCHEMA IF NOT EXISTS api;

-- DDL para as dados dos times
DROP TABLE IF EXISTS api.time;
CREATE TABLE IF NOT EXISTS api.time
(
	id INTEGER NOT NULL,
	nome CHAR(255),
	PRIMARY KEY (id)
);

-- DDL para as dados de campnhas
DROP TABLE IF EXISTS api.campanha;
CREATE TABLE IF NOT EXISTS api.campanha
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	nome CHAR(255),
	id_time_coracao INTEGER NOT NULL,
	data_vigencia_inicio DATE NOT NULL,
	data_vigencia_fim DATE NOT NULL,
	data_vigencia_alterada DATE,
	PRIMARY KEY (id),
	FOREIGN KEY (id_time_coracao) REFERENCES api.time(id)
);

-- DDL para as dados dos clientes
DROP TABLE IF EXISTS api.cliente;
CREATE TABLE IF NOT EXISTS api.cliente
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	nome CHAR(255) NOT NULL,
	email CHAR(255) NOT NULL UNIQUE,
	data_nascimento DATE NOT NULL,
	id_time_coracao INTEGER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (id_time_coracao) REFERENCES api.time(id)
);


-- DDL para as dados de associação da campanha com o cliente
DROP TABLE IF EXISTS api.campanha_cliente;
CREATE TABLE IF NOT EXISTS api.campanha_cliente
(
	id_campanha INTEGER NOT NULL,
	id_cliente INTEGER NOT NULL,
	PRIMARY KEY (id_campanha, id_cliente),
	FOREIGN KEY (id_campanha) REFERENCES api.campanha(id),
	FOREIGN KEY (id_cliente) REFERENCES api.cliente(id)
);
