DROP DATABASE IF EXISTS HotelSystem;

CREATE DATABASE HotelSystem;

USE HotelSystem;

CREATE TABLE Amenity (
	AmenityId INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(50) NOT NULL
);

CREATE TABLE RoomType (
	RoomTypeId INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(20) NOT NULL,
    StdOcc INT NOT NULL,
    MaxOcc INT NOT NULL,
    IsADACompliant BOOL NOT NULL DEFAULT 0
);

CREATE TABLE Price (
	PriceId INT PRIMARY KEY AUTO_INCREMENT,
    BasePrice DECIMAL(12, 2) NOT NULL,
    Upcharge DECIMAL(12,2) NULL
);

CREATE TABLE Guest (
	GuestId INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(30) NOT NULL,
    LastName VARCHAR(30) NOT NULL,
    AddressId INT NOT NULL,
    Phone VARCHAR(15) NOT NULL
);

CREATE TABLE City (
	CityId INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(50)
);

CREATE TABLE State (
	StateAbbr VARCHAR(2) PRIMARY KEY,
    `Name` VARCHAR(15)
);

CREATE TABLE ZipCode (
	ZipId INT PRIMARY KEY,
    ZipCode INT
);

CREATE TABLE Room (
	RoomId INT PRIMARY KEY,
    RoomTypeId INT NOT NULL,
    FOREIGN KEY fk_Room_RoomType (RoomTypeId)
		REFERENCES RoomType(RoomTypeId),
	PriceId INT NOT NULL,
	FOREIGN KEY fk_Room_Price (PriceId)
		REFERENCES Price(PriceId)
);

CREATE TABLE Reservation (
	ReservationId INT PRIMARY KEY AUTO_INCREMENT,
    GuestId INT NOT NULL,
    FOREIGN KEY fk_Reservation_Guest (GuestId)
		REFERENCES Guest(GuestId),
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    TotalCost DECIMAL(12, 2) NOT NULL
);

CREATE TABLE Address (
	AddressId INT PRIMARY KEY AUTO_INCREMENT,
    Street VARCHAR(50) NOT NULL,
    CityId INT NOT NULL,
    FOREIGN KEY fk_Address_City (CityId)
		REFERENCES City(CityId),
	StateAbbr VARCHAR(2) NOT NULL,
    FOREIGN KEY fk_Address_State (StateAbbr)
		REFERENCES State(StateAbbr),
	ZipId INT NOT NULL,
    FOREIGN KEY fk_Address_ZipCode (ZipId)
		REFERENCES ZipCode(ZipId)
);

CREATE TABLE RoomAmenity (
	RoomId INT NOT NULL,
    AmenityId INT NOT NULL,
    PRIMARY KEY pk_RoomAmenity (RoomId, AmenityId),
    FOREIGN KEY fk_RoomAmenity_Room (RoomId)
		REFERENCES Room(RoomId),
	FOREIGN KEY fk_RoomAmenity_Amenity (AmenityId)
		REFERENCES Amenity(AmenityId)
);

CREATE TABLE RoomReservation (
	RoomReservationId INT NOT NULL AUTO_INCREMENT,
	ReservationId INT NOT NULL,
    RoomId INT NOT NULL,
    PRIMARY KEY pk_ReservationRoom (RoomReservationId, ReservationId, RoomId),
    FOREIGN KEY fk_ReservationRoom_Reservation (ReservationId)
		REFERENCES Reservation(ReservationId),
	FOREIGN KEY fk_ReservationRoom_Room (RoomId)
		REFERENCES Room(RoomId),
	Adults TINYINT NOT NULL,
    Children TINYINT NULL
);

CREATE TABLE GuestRoomReservation (
	GuestId INT NOT NULL,
    RoomReservationId INT NOT NULL,
    PRIMARY KEY pk_GuestRoomReservation (GuestId, RoomReservationId),
    FOREIGN KEY fk_GuestroomReservation_Guest (GuestId)
		REFERENCES Guest(GuestId),
	FOREIGN KEY	fk_GuestRoomReservation_RoomReservation (RoomReservationId)
		REFERENCES RoomReservation(RoomReservationId)
);
-- Delete all data from various tables
-- Delete in the opposite order that I inserted
INSERT INTO Amenity (`Name`) VALUES
	('Microwave'),
    ('Refrigerator'),
    ('Jacuzzi'),
    ('Oven');
    
INSERT INTO RoomType (`Name`, StdOcc, MaxOcc, IsADACompliant) VALUES
	('Single', 2, 2, 0),
    ('Single', 2, 2, 1),
    ('Double', 2, 4, 0),
    ('Double', 2, 4, 1),
    ('Suite', 3, 8, 0),
    ('Suite', 3, 8, 1);

INSERT INTO Price (BasePrice, Upcharge) VALUES
	(149.99, NULL),
    (174.99, NULL),
    (174.99, 10),
    (199.99, 10),
    (399.99, 20);

INSERT INTO Room (RoomId, RoomTypeId, PriceId) VALUES
	(201, 3, 4),
    (202, 4, 3),
    (203, 3, 4),
    (204, 4, 3),
    (205, 1, 2),
    (206, 2, 1),
    (207, 1, 2),
    (208, 2, 1),
    (301, 3, 4),
    (302, 4, 3),
    (303, 3, 4),
    (304, 4, 3),
    (305, 1, 2),
    (306, 2, 1),
    (307, 1, 2),
    (308, 2, 1),
    (401, 6, 5),
    (402, 6, 5);

INSERT INTO RoomAmenity (RoomId, AmenityId) VALUES
	(201, 1),
    (201, 3),
    (202, 2),
    (203, 1),
    (203, 3),
    (204, 2),
    (205, 1),
    (205, 2),
    (205, 3),
    (206, 1),
    (206, 2),
    (207, 1),
    (207, 2),
    (207, 3),
    (208, 1),
    (208, 2),
    (301, 1),
    (301, 3),
    (302, 2),
    (303, 1),
    (303, 3),
    (304, 2),
    (305, 1),
    (305, 2),
    (305, 3),
    (306, 1),
    (306, 2),
    (307, 1),
    (307, 2),
    (307, 3),
    (308, 1),
    (308, 2),
    (401, 1),
    (401, 2),
    (401, 4),
    (402, 1),
    (402, 2),
    (402, 4);
    
INSERT INTO City (`Name`) VALUES
	('Minneapolis'),
    ('Council Bluffs'),
    ('Wasilla'),
    ('Harlingen'),
    ('West Deptford'),
    ('Saginaw'),
    ('Arvada'),
    ('Zion'),
    ('Cumberland'),
    ('Oswego'),
    ('Burke'),
    ('Drexel Hill');

INSERT INTO State (StateAbbr, `Name`) VALUES
	('MN', 'Minnesota'),
    ('IA', 'Iowa'),
    ('AK', 'Alaska'),
    ('TX', 'Texas'),
    ('NJ', 'New Jersey'),
    ('MI', 'Michigan'),
    ('CO', 'Colorado'),
    ('IL', 'Illinois'),
    ('RI', 'Rhode Island'),
    ('NY', 'New York'),
    ('VA', 'Virginia'),
    ('PA', 'Pennsylvania');

INSERT INTO ZipCode (ZipId, ZipCode) VALUES
	(55406, '55406'),
    (51501, '51501'),
    (99654, '99654'),
    (78552, '78552'),
    (08096, '08096'),
    (48601, '48601'),
    (80003, '80003'),
    (60099, '60099'),
    (02864, '02864'),
    (13126, '13126'),
    (22015, '22015'),
    (19026, '19026');

INSERT INTO Address (Street, CityId, StateAbbr, ZipId) VALUES
	('3625 East 43 Street, Apt 418', 1, 'MN', 55406),
    ('379 Old Shore Street', 2, 'IA', 51501),
    ('750 Wintergreen Dr.', 3, 'AK', 99654),
    ('9662 Foxrun Lane', 4, 'TX', 78552),
    ('9378 W. Augusta Ave.', 5, 'NJ', 08096),
    ('762 Wild Rose Street', 6, 'MI', 48601),
    ('7 Poplar Dr.', 7, 'CO', 80003),
    ('70 Oakwood St.', 8, 'IL', 60099),
    ('7556 Arrowhead St.', 9, 'RI', 02864),
    ('77 West Surrey Street', 10, 'NY', 13126),
    ('939 Linda Rd.', 11, 'VA', 22015),
    ('87 Queen St.', 12, 'PA', 19026);

INSERT INTO Guest (FirstName, LastName, AddressId, Phone) VALUES
	('Jonathan', 'Lee', 1, '(612) 321-8745'),
    ('Mack', 'Simmer', 2, '(291) 553-0508'),
    ('Bettyann', 'Seery', 3, '(478) 277-9632'),
    ('Duane', 'Cullison', 4, '(308) 494-0198'),
    ('Karie', 'Yang', 5, '(214) 730-0298'),
    ('Aurore', 'Lipton', 6, '(377) 507-0974'),
    ('Zachery', 'Luechtefeld', 7, '(814) 485-2615'),
    ('Jeremiah', 'Pendergrass', 8, '(279) 491-0960'),
    ('Walter', 'Holaway', 9, '(446) 396-6785'),
    ('Wilfred', 'Vise', 10, '(834) 727-1001'),
    ('Maritza', 'Tilton', 11, '(446) 351-6860'),
    ('Joleen', 'Tison', 12, '(231) 893-2755');

INSERT INTO Reservation (GuestId, StartDate, EndDate, TotalCost) VALUES
	(2, '2023-02-02', '2023-02-04', 299.98),
    (3, '2023-02-05', '2023-02-10', 999.95),
    (4, '2023-02-22', '2023-02-04', 349.98),
    (5, '2023-03-06', '2023-03-07', 199.99),
    (1, '2023-03-17', '2023-03-20', 524.97),
    (6, '2023-03-18', '2023-03-23', 924.95),
    (7, '2023-03-29', '2023-03-31', 349.98),
    (8, '2023-03-31', '2023-04-05', 874.95),
    (9, '2023-04-09', '2023-04-13', 799.96),
    (10, '2023-04-23', '2023-04-24', 174.99),
    (11, '2023-05-30', '2023-06-02', 1199.97),
    (12, '2023-06-10', '2023-06-14', 599.96),
    (6, '2023-06-17', '2023-06-18', 184.99),
    (1, '2023-06-28', '2023-07-02', 699.96),
    (9, '2023-07-13', '2023-07-14', 184.99),
    (10, '2023-07-18', '2023-07-21', 1259.97),
    (3, '2023-07-28', '2023-07-29', 199.99),
    (3, '2023-08-30', '2023-09-01', 349.98),
    (2, '2023-09-16', '2023-09-17', 149.99),
    (5, '2023-09-13', '2023-09-15', 399.98),
    (4, '2023-11-22', '2023-11-25', 1199.97),
    (2, '2023-11-22', '2023-11-25', 449.97),
    (11, '2023-12-24', '2023-12-28', 699.96);

INSERT INTO RoomReservation (ReservationId, RoomId, Adults, Children) VALUES
	(1, 308, 1, NULL),
    (2, 203, 2, 1),
    (3, 305, 2, NULL),
    (4, 201, 2, 2),
    (5, 307, 1, 1),
    (6, 302, 3, NULL),
    (7, 202, 2, 2),
    (8, 304, 2, NULL),
    (9, 301, 1, NULL),
    (10, 207, 1, 1),
    (11, 401, 2, 4),
    (12, 206, 2, NULL),
    (12, 208, 1, NULL),
    (13, 304, 3, NULL),
    (14, 205, 2, NULL),
    (15, 204, 3, 1),
    (16, 401, 4, 2),
    (17, 303, 2, 1),
    (18, 305, 1, NULL),
    (19, 208, 2, NULL),
    (20, 203, 2, 2),
    (21, 401, 2, 2),
    (22, 206, 2, NULL),
    (22, 301, 2, 2),
    (23, 302, 2, NULL);
    
INSERT INTO GuestRoomReservation (GuestId, RoomReservationId) VALUES
	(1, 5),
    (1, 15),
    (2, 1),
    (2, 20),
    (2, 23),
    (2, 24),
    (3, 2),
    (3, 18),
    (3, 19),
    (4, 3),
    (4, 22),
    (5, 4),
    (5, 21),
    (6, 6),
    (6, 14),
    (7, 7),
    (8, 8),
    (9, 9),
    (9, 16),
    (10, 10),
    (10, 17),
    (11, 11),
    (11, 25),
    (12, 12),
    (12, 13);

-- Delete Jeremiah Pendergrass
DELETE FROM	GuestRoomReservation
WHERE RoomReservationId = 8;
DELETE FROM RoomReservation
WHERE RoomReservationId = 8;
DELETE FROM Reservation
WHERE GuestId = 8;
DELETE FROM Address
WHERE AddressId = 8;
DELETE FROM Guest
WHERE GuestId = 8;
