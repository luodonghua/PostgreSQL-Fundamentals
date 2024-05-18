drop table if exists cities;
drop table if exists countries;
drop table if exists course_contents_mapping;
drop table if exists courses;
drop table if exists course_contents;
drop table if exists employees;

create table countries (
    country_id      serial           primary key,
    country_name    varchar(50)      not null
);

create table cities (
    city_id         bigserial        primary key,
    city_name       varchar(30)      not null,
    city_code       varchar(3)       default 'NA'   not null,
    country_id      int,
    constraint fk_city_counrty foreign key (country_id) references countries(country_id)
);

create table courses (
    course_id       int             generated always as identity (increment 1 start 1 minvalue 1),
    course_name     varchar(50),
    primary key (course_id)
);

create table course_contents (
    content_id       int             generated always as identity (increment 1 start 1 minvalue 1),
    content_name     varchar(50),
    primary key (content_id)
);

create table course_contents_mapping (
    course_id       int,
    content_id      int,
    constraint pk_course_contents_mapping primary key  (course_id, content_id),
    constraint fk_course_id foreign key (course_id) references courses(course_id),
    constraint fk_content_id foreign key (content_id) references course_contents(content_id)
);

create table employees (
    employee_id     int                 generated always as identity (increment 1 start 1 minvalue 1),
    employee_name   varchar(100)        not null,
    department      varchar(100)        not null,
    joining_date    date                not null,
    age             smallint            not null,
    address         varchar(250)        not null,
    salary          numeric(7,2)        not null,
    left_date       timestamp with time zone,
    left_job        boolean             not null,
    primary key (employee_id)
);

-- default spring.sql.init.separator is ';', doesn't work well with $$
create or replace function gettotalemployees()
    returns integer
    language 'plpgsql'
    cost 100
    volatile parallel unsafe
as 
'
declare
    totalemployees integer;
begin
    select count(*) from employees into totalemployees;
    return totalemployees;
end;
'
;

create or replace procedure procgetemployeeage(
    p_employee_id int,
    out v_age smallint)
    language plpgsql
as
'
begin
    select age from employees where employee_id = p_employee_id into v_age;
end
'
;


