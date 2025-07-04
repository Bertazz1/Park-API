insert into USERS (id, username, password, role) values (100, 'joao@gmail.com', '$2a$12$d97TTYzpqnqslpMRRzAEGOI4JGy.fMR2jTVY4a/BD.FFZ8Ant.pX2', 'ROLE_ADMIN');
insert into USERS (id, username, password, role) values (101, 'maria@gmail.com', '$2a$12$d97TTYzpqnqslpMRRzAEGOI4JGy.fMR2jTVY4a/BD.FFZ8Ant.pX2', 'ROLE_CLIENT');
insert into USERS (id, username, password, role) values (102, 'pedro@gmail.com', '$2a$12$d97TTYzpqnqslpMRRzAEGOI4JGy.fMR2jTVY4a/BD.FFZ8Ant.pX2', 'ROLE_CLIENT');
insert into USERS (id, username, password, role) values (103, 'toby@gmail.com', '$2a$12$d97TTYzpqnqslpMRRzAEGOI4JGy.fMR2jTVY4a/BD.FFZ8Ant.pX2', 'ROLE_CLIENT');


insert into CLIENTS (id, name, cpf, user_id) values (10, 'João Silva', '87953162059', 100);
insert into CLIENTS (id, name, cpf, user_id) values (11, 'Maria Souza', '71375952005', 101);
insert into CLIENTS (id, name, cpf, user_id) values (12, 'Pedro Oliveira', '33374678068', 102);