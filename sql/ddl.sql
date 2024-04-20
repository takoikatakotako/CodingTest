set
    character_set_client = utf8mb4;
set
    character_set_connection = utf8mb4;

-- User
create table user(
    id bigint not null auto_increment,
    name varchar(15) not null,
    primary key(id)
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_bin comment 'ユーザー';


-- Chat
create table chat(
    id bigint not null auto_increment,
    status varchar(15) not null,
    primary key(id)
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_bin comment 'チャット';
