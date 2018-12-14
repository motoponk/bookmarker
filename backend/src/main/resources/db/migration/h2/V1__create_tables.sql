create sequence user_id_seq start with 1 increment by 1;

create table users (
    id bigint default user_id_seq.nextval,
    email varchar(255) not null,
    password varchar(255) not null,
    name varchar(255) not null,
    role varchar(255) not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id),
    UNIQUE KEY user_email_unique (email)
);