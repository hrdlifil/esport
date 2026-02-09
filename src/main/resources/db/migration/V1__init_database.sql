create table proband(
                        id bigint primary key,
                        first_name varchar(255) not null,
                        last_name varchar(255) not null,
                        phone_number varchar(255) not null,
                        email varchar(255) not null unique,
                        ring_size integer,
                        date_of_birth date not null
);


create sequence student_sequence start with 1 increment by 1;
