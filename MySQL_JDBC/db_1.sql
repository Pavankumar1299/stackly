create database stackly;

use stackly;

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

CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    account_type VARCHAR(20),
    balance DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

INSERT INTO accounts (customer_id, account_type, balance) VALUES
(1,'Savings',50000),
(1,'Current',20000),
(2,'Savings',30000),
(3,'Savings',15000),
(4,'Current',40000);

-- select * from customers;
-- select * from accounts;

-- 1
select * from accounts where balance > 20000;

-- 2
select * from customers where city = 'chennai';

-- 3
select * from accounts where balance between 20000 and 50000; 

-- 4
select name from customers where name like 'J%';

-- 5
select * from accounts where account_type in ('Savings', 'Current');

-- 6
select * from accounts where account_type != 'Savings';

-- 7
select name from customers where name like '%a%';

-- 8
select * from accounts where balance <= 30000;

-- 9
select * from customers where city != 'Madurai';

-- 10
select * from accounts where balance not between 10000 and 40000;

-- 11
select name from customers where name like '%i';

-- 12
select * from accounts where balance = 50000;

-- 13
select * from customers where city not in ('Chennai', 'Salem');

-- 14
select * from accounts where balance > 10000 and balance < 40000;

-- 15
select * from accounts where account_type != 'Current';

-- 16
select * from accounts order by balance desc;

-- 17
select * from customers order by name;

-- 18
select * from accounts order by account_type, balance desc;

-- 19
select sum(balance) as total from accounts;

-- 20
select avg(balance) as average from accounts;

-- 21
select max(balance) as max from accounts;

-- 22
select min(balance) as min from accounts;

-- 23
select count(*) as total_count from accounts;

-- 24
select account_type, sum(balance) as total_balance from accounts group by account_type;

-- 25
select account_type, avg(balance) as avg_balance from accounts group by account_type;

-- 26
select account_type, avg(balance) as avg_balance from accounts  group by account_type having avg_balance > 20000;

-- 27
select account_type, count(*) as total_acc from accounts group by account_type;

-- 28
select customer_id, count(customer_id) as count from accounts group by customer_id having count > 1;

-- 29
select c.name, a.account_type, a.balance from customers c
join accounts a on c.customer_id = a.customer_id;

 -- 30
select c.name, a.account_type from customers c
join accounts a on c.customer_id = a.customer_id;

-- 31
select a.account_id, c.*, a.account_type from customers c
join accounts a on c.customer_id = a.customer_id;

-- 32
select c.name, a.account_type, a.balance from customers c
join accounts a on c.customer_id = a.customer_id where a.balance > 20000;

-- 33
select c.name, a.balance as total_balance from customers c
join accounts a on c.customer_id = a.customer_id;

-- 34
select c.name, a.balance as total_balance from customers c
join accounts a on c.customer_id = a.customer_id order by a.balance;

-- 35
select c.city, count(*) as total_account from customers c
join accounts a on c.customer_id = a.customer_id group by c.city;

-- 36
select * from accounts where balance > (select avg(balance) from accounts);

-- 37
select * from customers where customer_id in (select customer_id from accounts);

-- 38
select * from customers where customer_id not in (select customer_id from accounts);

-- 39
select * from accounts where balance = (select max(balance) from accounts);

-- 40
select * from customers where customer_id in (select customer_id from accounts where balance > 40000);



 






