create table chat_links (
    link_id bigint not null,
    chat_id bigint not null,

    primary key (link_id, chat_id)
);
