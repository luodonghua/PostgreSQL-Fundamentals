set role hr;

drop table if exists employee cascade;
create table employee (
    id serial not null, 
    name varchar(30) not null, 
    salary integer not null,    
    primary key (id)
);
