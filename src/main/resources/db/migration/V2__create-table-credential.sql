create table tb_user(
    id bigserial primary key,
    username varchar(255),
    site varchar(255),
    login varchar(255),
    encryptedPassword varchar(255),
    created_at timestamp,
    updated_at timestamp
);