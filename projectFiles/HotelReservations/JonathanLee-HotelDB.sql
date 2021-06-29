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