create table notification_task(
    id bigserial primary key,
    chatId bigint,
    message varchar(250),
    time timestamp
);