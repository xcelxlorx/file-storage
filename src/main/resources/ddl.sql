create table files_tb (
                          id bigint,
                          original_file_name varchar(255),
                          save_file_name varchar(255),
                          name varchar(45) not null,
                          size bigint,
                          folder_id bigint,
                          user_id bigint,
                          primary key (id)
);

create table folder_tb (
                           id bigint,
                           name varchar(45) not null,
                           parent_id bigint,
                           user_id bigint,
                           primary key (id)
);

create table user_tb (
                         id bigint,
                         email varchar(100) not null,
                         password varchar(256) not null,
                         role varchar(45) not null,
                         usage bigint,
                         username varchar(45) not null,
                         primary key (id)
);