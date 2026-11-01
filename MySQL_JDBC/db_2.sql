use stackly;

CREATE TABLE trains (
    train_id INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(50),
    source VARCHAR(50),
    destination VARCHAR(50)
);

INSERT INTO trains (train_name, source, destination) VALUES
('Express1','Chennai','Madurai'),
('Express2','Coimbatore','Salem'),
('Express3','Madurai','Chennai');

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    passenger_name VARCHAR(50),
    fare DECIMAL(10,2),
    status VARCHAR(20),
    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);

INSERT INTO bookings (train_id, passenger_name, fare, status) VALUES
(1,'Janani',500,'Confirmed'),
(1,'Arun',500,'Waiting'),
(2,'Priya',300,'Confirmed'),
(3,'Karthik',450,'Cancelled'),
(2,'Meena',300,'Confirmed');

-- 41
select * from bookings where fare > 400;

-- 42 
select * from bookings where status != 'Confirmed';

-- 43
select * from trains where source = 'Chennai';

-- 44
select * from bookings where fare > 300 and fare < 500;

-- 45
select * from bookings where passenger_name like 'A%';

-- 46
select t.train_name, b.passenger_name from trains t
join bookings b on t.train_id = b.train_id;

-- 47
select t.train_name, count(b.train_id) as bookings from trains t
join bookings b on t.train_id = b.train_id group by t.train_id;

-- 48
select t.train_name, sum(b.fare) as total_fare from trains t
join bookings b on t.train_id = b.train_id where b.status = 'Confirmed' group by t.train_name;

-- 49
select * from bookings where fare = (select max(fare) from bookings);

-- 50;
select t.train_name, count(*) bookings from trains t
join bookings b on t.train_id = b.train_id group by t.train_name having bookings > 1;

