create table tb_user(
    id bigserial primary key,
    username varchar(255),
    email varchar(255),
    password varchar(255),
    role varchar(20),
    created_at timestamp
);