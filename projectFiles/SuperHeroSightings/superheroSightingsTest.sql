DROP DATABASE IF EXISTS SuperheroSightingsTest;
CREATE DATABASE SuperheroSightingsTest;

USE SuperheroSightingsTest;

CREATE TABLE Supe(
	SupeId INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(30) NOT NULL,
    `Description` VARCHAR(255)
);

CREATE TABLE Ability(
	AbilityId INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(30) NOT NULL
);

CREATE TABLE PhoneType(
	PhoneTypeId INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(10) NOT NULL
);

CREATE TABLE Phone(
	PhoneId INT PRIMARY KEY AUTO_INCREMENT,
    PhoneNumber CHAR(12) NOT NULL,
    PhoneTypeId INT NOT NULL,
    FOREIGN KEY (PhoneTypeId) REFERENCES PhoneType(PhoneTypeId)
);

CREATE TABLE Address(
	AddressId INT PRIMARY KEY AUTO_INCREMENT,
    Street VARCHAR(50) NOT NULL,
    City VARCHAR(25) NOT NULL,
    State CHAR(2) NOT NULL,
    ZipCode CHAR(10) NOT NULL,
    Coordinates VARCHAR(50) NOT NULL
);

CREATE TABLE Location(
	LocationId INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(30) NOT NULL,
    `Description` VARCHAR(255),
    AddressId INT NOT NULL,
    FOREIGN KEY (AddressId) REFERENCES Address(AddressId)
);

CREATE TABLE Org(
	OrgId INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(50) NOT NULL,
    `Description` VARCHAR(255),
    AddressId INT NOT NULL,
    FOREIGN KEY (AddressId) REFERENCES Address(AddressId),
    PhoneId INT,
    FOREIGN KEY (PhoneId) REFERENCES Phone(PhoneId)
);

CREATE TABLE Sighting(
	SightingId INT PRIMARY KEY AUTO_INCREMENT,
    SupeId INT NOT NULL,
    FOREIGN KEY (SupeId) REFERENCES Supe(SupeId),
    LocationId INT NOT NULL,
    FOREIGN KEY (LocationId) REFERENCES Location(LocationId),
    `Date` DATE NOT NULL
);

CREATE TABLE Supe_Ability(
	SupeId INT NOT NULL,
    AbilityId INT NOT NULL,
    PRIMARY KEY pk_SupeAbility (SupeId, AbilityId),
    FOREIGN KEY FK_Supe_Ability_Supe (SupeId)
		REFERENCES Supe(SupeId),
	FOREIGN KEY FK_Supe_Ability_Ability (AbilityId)
		REFERENCES Ability(AbilityId)
);

CREATE TABLE Supe_Org(
	SupeId INT NOT NULL,
    OrgId INT NOT NULL,
    PRIMARY KEY pk_Supe_Org (SupeId, OrgId),
    FOREIGN KEY FK_Supe_Org_Supe (SupeId)
		REFERENCES Supe(SupeId),
	FOREIGN KEY FK_Supe_Org_Org (OrgId)
		REFERENCES Org(OrgId)
);

INSERT INTO phoneType(name) VALUES 
	('business'),
    ('cell'),
    ('home');