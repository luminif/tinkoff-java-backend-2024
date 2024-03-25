create table links (
    link varchar(255) not null,
    id bigint generated always as identity,

    updated_at timestamp with time zone not null,

    primary key (id)
);
