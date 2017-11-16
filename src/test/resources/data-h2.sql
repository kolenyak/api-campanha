insert into api.time (id, nome) values (1, 'Palmeiras');
insert into api.time (id, nome) values (2, 'Santos');
insert into api.time (id, nome) values (3, 'Corinthians');
insert into api.time (id, nome) values (4, 'São Paulo');


insert into api.campanha (id, nome, id_time_coracao, data_vigencia_inicio, data_vigencia_fim) values (1, 'Campanha Palmeiras 1', 1, '2017-11-10', '2017-11-13');
insert into api.campanha (id, nome, id_time_coracao, data_vigencia_inicio, data_vigencia_fim) values (2, 'Campanha Palmeiras 2', 1, '2017-11-14', '2017-11-16');
insert into api.campanha (id, nome, id_time_coracao, data_vigencia_inicio, data_vigencia_fim) values (3, 'Campanha Santos', 2, '2017-11-10', '2017-11-13');


insert into api.cliente (id, nome, email, data_nascimento, id_time_coracao) values (1, 'João Palmeirense', 'joao@email.com', '1990-01-01', 1);
insert into api.cliente (id, nome, email, data_nascimento, id_time_coracao) values (2, 'Pedro Santista', 'pedro@email.com', '1990-01-01', 2);

insert into api.campanha_cliente (id_campanha, id_cliente) values (1, 1);
insert into api.campanha_cliente (id_campanha, id_cliente) values (2, 1);
insert into api.campanha_cliente (id_campanha, id_cliente) values (3, 2);