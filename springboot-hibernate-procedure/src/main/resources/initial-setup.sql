--connect as user "hr" or run "set role hr;"

set role hr;

drop table if exists employee cascade;
create table employee (id serial not null, 
    name varchar(30) not null, 
    salary integer not null,    
    primary key (id)
);

create or replace procedure add_new_employee(
    p_name    varchar,
    p_salary  int
) 
language sql
AS
$$
INSERT INTO employee(name,salary) values(p_name,p_salary);
$$
;

create or replace function get_salary_by_name(
    p_name    varchar
)
returns int
language plpgsql
AS
$$
declare
    v_salary int;
begin
    select salary into v_salary from employee where name=p_name limit 1;
    return coalesce(v_salary,0);
end;
$$
;

-- call add_new_employee('Employee-1',3000);
-- call add_new_employee('Employee-2',4000);
-- select get_salary_by_name('Employee-2');
