create table article(
    id identity not null primary key,
    title varchar(50),
    content clob
);

create table tag(
    article_id bigint not null,
    tag varchar(20),
    PRIMARY KEY(article_id, tag),
    foreign key(article_id) references article(id)
);

