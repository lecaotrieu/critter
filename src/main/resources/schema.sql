DROP TABLE IF EXISTS schedule_employee;
DROP TABLE IF EXISTS schedule_pet;
DROP TABLE IF EXISTS schedule_activities;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS employee_daysavailable;
DROP TABLE IF EXISTS employee_skill;
DROP TABLE IF EXISTS pet_daysavailable;
DROP TABLE IF EXISTS pet;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS user;
create table schedule
(
    id   bigint auto_increment
        primary key,
    date date null
);

create table schedule_activities
(
    schedule_id bigint       not null,
    activities  varchar(255) null,
    constraint FKp4gtwmuodj21fo9kjww5ql477
        foreign key (schedule_id) references schedule (id)
);

create table user
(
    id       bigint auto_increment
        primary key,
    name     varchar(255) null,
    password varchar(255) null,
    username varchar(255) null
);

create table customer
(
    notes        varchar(255) null,
    phone_number varchar(255) null,
    id           bigint       not null
        primary key,
    constraint FKg2o3t8h0g17smtr9jgypagdtv
        foreign key (id) references user (id)
);

create table employee
(
    phone_number varchar(255) null,
    id           bigint       not null
        primary key,
    constraint FKd8il4lxw1wi74qh8b7uoy6e0a
        foreign key (id) references user (id)
);

create table employee_daysavailable
(
    employee_id    bigint       not null,
    days_available varchar(255) null,
    constraint FK7094sftiu9srt8hhfeu9mb9ek
        foreign key (employee_id) references employee (id)
);

create table employee_skill
(
    employee_id bigint       not null,
    skills      varchar(255) null,
    constraint FKkd8xx37dlmjryoas0d91hri6c
        foreign key (employee_id) references employee (id)
);

create table pet
(
    id          bigint auto_increment
        primary key,
    birth_date  date         null,
    name        varchar(255) null,
    notes       varchar(255) null,
    type        int          null,
    customer_id bigint       null,
    constraint FKt742r2fu4c3i9sn6a8kv0k746
        foreign key (customer_id) references customer (id)
);

create table pet_daysavailable
(
    pet_id         bigint       not null,
    days_available varchar(255) null,
    constraint FKar4e4vgak86wqrb1c3bnwulkl
        foreign key (pet_id) references pet (id)
);

create table schedule_employee
(
    schedule_id bigint not null,
    employee_id bigint not null,
    constraint FKjtcgvfdq605kdn6m7a41cggtg
        foreign key (employee_id) references employee (id),
    constraint FKph1rlkusvosc5jx4qduxeyldn
        foreign key (schedule_id) references schedule (id)
);

create table schedule_pet
(
    schedule_id bigint not null,
    pet_id      bigint not null,
    constraint FKol27lvj9v1f41nue1y6g8ivdo
        foreign key (schedule_id) references schedule (id),
    constraint FKollomwxdacrhw8nrnvcf6d9mj
        foreign key (pet_id) references pet (id)
);

