N+1 Problem:

```sql
2024-May-19 14:31:07 pm [main] DEBUG org.hibernate.SQL - select d1_0.id,d1_0.name from dept d1_0
Hibernate: select d1_0.id,d1_0.name from dept d1_0
2024-May-19 14:31:07 pm [main] DEBUG org.hibernate.stat.internal.StatisticsImpl - HHH000117: HQL: FROM Department, time: 28ms, rows: 2
| 1  | IT       |
Employees:
2024-May-19 14:31:07 pm [main] DEBUG org.hibernate.SQL - select e1_0.dept_id,e1_0.id,e1_0.name from employee e1_0 where e1_0.dept_id=?
Hibernate: select e1_0.dept_id,e1_0.id,e1_0.name from employee e1_0 where e1_0.dept_id=?
| 5  | Employee IT 4|
| 4  | Employee IT 3|
| 3  | Employee IT 2|
| 1  | Employee IT 0|
| 2  | Employee IT 1|
| 2  | Sales    |
Employees:
2024-May-19 14:31:07 pm [main] DEBUG org.hibernate.SQL - select e1_0.dept_id,e1_0.id,e1_0.name from employee e1_0 where e1_0.dept_id=?
Hibernate: select e1_0.dept_id,e1_0.id,e1_0.name from employee e1_0 where e1_0.dept_id=?
| 6  | Employee Sales 0|
| 10 | Employee Sales 4|
| 8  | Employee Sales 2|
| 9  | Employee Sales 3|
| 7  | Employee Sales 1|
```


With fix:
```sql
2024-May-19 14:31:07 pm [main] DEBUG org.hibernate.SQL - select d1_0.id,e1_0.dept_id,e1_0.id,e1_0.name,d1_0.name from dept d1_0 join employee e1_0 on d1_0.id=e1_0.dept_id
Hibernate: select d1_0.id,e1_0.dept_id,e1_0.id,e1_0.name,d1_0.name from dept d1_0 join employee e1_0 on d1_0.id=e1_0.dept_id
2024-May-19 14:31:07 pm [main] DEBUG org.hibernate.stat.internal.StatisticsImpl - HHH000117: HQL: FROM Department d JOIN fetch d.employees, time: 7ms, rows: 2
| 1  | IT       |
Employees:
| 3  | Employee IT 2|
| 5  | Employee IT 4|
| 4  | Employee IT 3|
| 1  | Employee IT 0|
| 2  | Employee IT 1|
| 2  | Sales    |
Employees:
| 6  | Employee Sales 0|
| 8  | Employee Sales 2|
| 10 | Employee Sales 4|
| 7  | Employee Sales 1|
| 9  | Employee Sales 3|
```
