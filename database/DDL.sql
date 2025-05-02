CREATE SCHEMA TTBS;

CREATE TABLE User(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(50),
    last_name VARCHAR(50),
    birth_date DATE,
    email VARCHAR(50),
    password VARCHAR(50)
);

CREATE TABLE Route (
    route_id INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(100) NOT NULL,
    source_station VARCHAR(100) NOT NULL,
    destination_station VARCHAR(100) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL
);

CREATE TABLE Seat (
    seat_id INT PRIMARY KEY AUTO_INCREMENT,
    seat_number INT NOT NULL UNIQUE
);

CREATE TABLE RouteSeat (
    route_id INT NOT NULL,
    seat_id INT NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    
    PRIMARY KEY (route_id, seat_id),
    FOREIGN KEY (route_id) REFERENCES Route(route_id),
    FOREIGN KEY (seat_id) REFERENCES Seat(seat_id)
);

CREATE TABLE Ticket (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    route_id INT NOT NULL,
    seat_id INT NOT NULL,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (route_id, seat_id) REFERENCES RouteSeat(route_id, seat_id)
);

CREATE TABLE Payment (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    ticket_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50),
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id)
);
