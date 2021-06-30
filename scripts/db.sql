create table if not exists receipt_tab
(
    id bigint auto_increment
        primary key,
    receipt_no varchar(20) not null,
    book_no varchar(10) not null,
    person varchar(50) not null,
    phone varchar(20) not null,
    payment_type varchar(10) not null,
    payment_no varchar(100) null,
    amount decimal(5,2) not null,
    input_date date not null,
    approval tinyint(1) default 0 not null,
    constraint receipt_tab_receipt_no_uindex
        unique (receipt_no)
);



create table if not exists receipt_item_tab
(
    id bigint auto_increment
        primary key,
    receipt_no varchar(20) not null,
    item_type varchar(20) not null,
    live_name varchar(50) not null,
    family_name varchar(20) null,
    relation varchar(20) null,
    relation_name varchar(50) null,
    yl_family_name varchar(20) null,
    yl_type varchar(20) null,
    printed tinyint(1) default 0 not null
);




