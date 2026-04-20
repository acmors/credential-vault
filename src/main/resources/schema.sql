create table if not exists tb_user(
    id numeric unique primary key,
    username varchar(250) not null,
    email varchar(250) not null,
    password varchar(250) not null,
    role varchar(250) not null
);

insert into tb_user(id, username, email, password, role) values (1 ,'marcos', 'marcos@gmail.com', '12345', 'ADMIN');
insert into tb_user(id, username, email, password, role) values (2, 'lucas', 'lucas@gmail.com', '12345', 'USER');