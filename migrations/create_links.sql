CREATE TABLE links (
    link varchar(255) not null,
    id bigint not null,

    updated_at timestamp with time zone not null,

    primary key (id)
);
