insert into countries values (1, 'India');
insert into countries values (2, 'USA');
insert into countries values (3, 'China');

insert into cities (city_name,city_code,country_id) values('Delhi', 'DEL',1);
insert into cities (city_name,city_code,country_id) values('New York','NYC',2);


insert  into employees overriding system value values(10001,'Larry','IT','1980-01-01',70,'1047 Round Table Drive',80012.50,null,false);
insert  into employees overriding system value values(10002,'Tom','Sales','2021-09-11',30,'82 Long Table Road',10000,'2024-02-14T08:00:00+08:00',true);