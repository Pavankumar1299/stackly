use stackly;

CREATE TABLE Employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(50),
    department VARCHAR(30),
    salary DECIMAL(10,2),
    city VARCHAR(30),
    joining_date DATE
);

INSERT INTO Employee VALUES
(101,'John','IT',60000,'Chennai','2022-01-15'),
(102,'David','HR',45000,'Bangalore','2021-03-10'),
(103,'Smith','IT',70000,'Chennai','2020-07-12'),
(104,'Mary','Finance',55000,'Mumbai','2023-01-20'),
(105,'James','HR',48000,'Delhi','2022-05-05'),
(106,'Linda','Finance',65000,'Mumbai','2021-08-18');

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    city VARCHAR(50)
);

INSERT INTO customers (name, city) VALUES
('Janani','Chennai'),
('Arun','Coimbatore'),
('Priya','Madurai'),
('Karthik','Salem');

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    amount DECIMAL(10,2),
    order_date DATE,
    FOREIGN KEY(customer_id) REFERENCES Customers(customer_id)
);

insert into Orders values
(1, 1, 1500, '2026-11-25'),
(2, 3, 1500, '2026-11-25'),
(3, 4, 599, '2026-10-25'),
(4, 2, 25000, '2026-11-02'),
(5, 1, 1000, '2026-09-13'),
(6, 2, 799, '2026-11-25');

CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(50),
    department VARCHAR(30),
    marks INT
);

insert into Students values
(1, 'Pavan', 'MCA', 74),
(2, 'Ravi', 'BCA', 67),
(3, 'Sudeep', 'CS', 89),
(4, 'Priya', 'Civil', 75),
(5, 'Ravan', 'MCA', 78),
(6, 'Govind', 'BBA', 67);

-- Employee
-- 1
select department, count(*) as total from Employee group by department;

-- 2
select department, avg(salary) as avg_salary from Employee group by department;

-- 3
select department, count(*) as total from Employee group by department having total > 1;

-- 4
select department, max(salary) as max from Employee group by department;

-- 5
select department, min(salary) as min from Employee group by department;

-- 6
select department, avg(salary) as avg from Employee group by department having avg > 50000;

-- 7
select department, sum(salary) as expenditure from Employee group by department;

-- 8
select * from employee order by salary desc;

-- 9
select * from employee order by department, salary desc;

-- 10
select city, count(*) as total_emp from employee group by city having total_emp > 1;

-- 11
select city, sum(salary) as total_paid from employee group by city;

-- 12
select department, sum(salary) as total_paid from employee group by department order by total_paid desc;

-- 14
select department, max(salary) as max, min(salary) as min from employee group by department;

-- 15
select * from employee order by salary desc limit 3;


-- Customers & Orders
-- 16
select c.name, sum(o.amount) as total_amount from customers c
join orders o on c.customer_id = o.customer_id group by c.customer_id, c.name;

-- 17
select c.name, count(o.order_id) as total_orders from customers c
join orders o on c.customer_id = o.customer_id group by c.customer_id, c.name having count(o.order_id) > 3;

-- 18
select c.name, avg(o.amount) as average_amount from customers c
join orders o on c.customer_id = o.customer_id group by c.customer_id, c.name;

-- 19
select c.name, max(o.amount) as highest_amount from customers c
join orders o on c.customer_id = o.customer_id group by c.customer_id, c.name;

-- 20
select c.name, sum(o.amount) as total_purchase from customers c
join orders o on c.customer_id = o.customer_id group by c.customer_id, c.name order by total_purchase desc;

-- 21
select c.name, sum(o.amount) as total_purchase from customers c
join orders o on c.customer_id = o.customer_id group by c.customer_id, c.name having sum(o.amount) > 10000;

-- 22
select c.name, count(o.order_id) as total_orders from customers c
left join orders o on c.customer_id = o.customer_id group by c.customer_id, c.name;

-- 23
select c.name, sum(o.amount) as total_purchase from customers c
join orders o on c.customer_id = o.customer_id
group by c.customer_id, c.name order by total_purchase desc limit 1;

-- 24
select c.name, count(o.order_id) as total_orders from customers c
join orders o on c.customer_id = o.customer_id
group by c.customer_id, c.name order by total_orders desc limit 1;

-- 25
select c.name, avg(o.amount) as average_amount from customers c
join orders o on c.customer_id = o.customer_id
group by c.customer_id, c.name having avg(o.amount) > 2000;

-- 26
select c.name, sum(o.amount) as total_purchase from customers c
join orders o on c.customer_id = o.customer_id
group by c.customer_id, c.name order by total_purchase desc limit 5;

-- 27
select c.name, min(o.amount) as minimum_amount from customers c
join orders o on c.customer_id = o.customer_id group by c.customer_id, c.name;

-- 28
select c.name, sum(o.amount) as total_purchase from customers c
join orders o on c.customer_id = o.customer_id
group by c.customer_id, c.name having sum(o.amount) > 5000;

-- 29
select c.name, count(o.order_id) as total_orders, coalesce(sum(o.amount), 0) as total_purchase
from customers c left join orders o on c.customer_id = o.customer_id group by c.customer_id, c.name;

-- 30
select c.name, count(o.order_id) as total_orders, sum(o.amount) as total_purchase from customers c
join orders o on c.customer_id = o.customer_id
group by c.customer_id, c.name having count(o.order_id) > 2 and sum(o.amount) > 8000;
 

-- Student
-- 30
select department, avg(marks) as average_marks from students group by department;

-- 31
select department, avg(marks) as average_marks
from students group by department having avg(marks) > 75;

-- 32
select department, max(marks) as highest_marks from students group by department;

-- 33
select department, count(*) as total_students from students group by department;

-- 34
select department, count(*) as total_students from students group by department having count(*) > 5;

-- 35
select department, avg(marks) as average_marks from students 
group by department order by average_marks desc;

-- 36
select department, avg(marks) as average_marks from students
group by department order by average_marks desc limit 3;

-- 37
select department, avg(marks) as average_marks from students
group by department having avg(marks) between 70 and 90;

-- 38
select department, sum(marks) as total_marks from students group by department;

-- 39
select department, count(*) as total_students from students
group by department order by total_students desc;

-- 40
select department, min(marks) as lowest_marks from students group by department;

-- 41
select department, max(marks) as highest_marks from students
group by department having max(marks) > 90;

-- 42
select department, count(*) as students_above_80 from students where marks > 80 group by department;

-- 43
select department, count(*) as students_above_75 from students 
where marks > 75 group by department having count(*) > 3;

-- 44
select department, max(marks) as highest_marks from students
group by department order by highest_marks desc; 










