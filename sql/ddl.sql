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


-- Chat Room
create table chat_room(
    id bigint not null auto_increment,
    type varchar(15) not null,
    primary key(id)
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_bin comment 'チャットルーム';


-- Chat Room User
create table chat_room_user(
    id bigint not null auto_increment,
    chat_room_id bigint not null,
    user_id bigint not null,
    primary key(id)
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_bin comment 'チャットルームユーザー';


-- Chat Message
create table chat_message(
    id bigint not null auto_increment,
    deleted bool not null,
    chat_room_id bigint not null,
    user_id bigint not null,
    message varchar(255) not null,
    created_at datetime not null,
    updated_at datetime not null,
    primary key(id)
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_bin comment 'チャットメッセージ';

