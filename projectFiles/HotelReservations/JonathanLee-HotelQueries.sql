USE HotelSystem;

-- 1. Write a query that returns a list of reservations that end in July 2023, including the name of the guest, the room number(s), and the reservation dates.
SELECT
	CONCAT(g.FirstName, ' ', g.LastName) GuestName,
    rr.RoomId RoomNumber,
    r.StartDate Checkin,
    r.EndDate Checkout
FROM Guest g
INNER JOIN Reservation r ON g.GuestId = r.GuestId
INNER JOIN RoomReservation rr ON r.ReservationId = rr.ReservationId
WHERE r.EndDate BETWEEN '2023-07-01' AND '2023-07-31';

-- 		GuestName		RoomNumber	Checkin		Checkout
-- 		Jonathan Lee	205			2023-06-28	2023-07-02
-- 		Walter Holaway	204			2023-07-13	2023-07-14
-- 		Wilfred Vise	401			2023-07-18	2023-07-21
-- 		Bettyann Seery	303			2023-07-28	2023-07-29

-- 2. Write a query that returns a list of all reservations for rooms with a jacuzzi, displaying the guest's name, the room number, and the dates of the reservation.
SELECT
	CONCAT(g.FirstName, ' ', g.LastName) GuestName,
    rr.RoomId RoomNumber,
    res.StartDate Checkin,
    res.EndDate Checkout
FROM Amenity a
INNER JOIN RoomAmenity ra ON a.AmenityId = ra.AmenityId
INNER JOIN Room rm ON ra.RoomId = rm.RoomId
INNER JOIN RoomReservation rr ON rm.RoomId = rr.RoomId
INNER JOIN Reservation res ON rr.ReservationId = res.ReservationId
INNER JOIN Guest g ON res.GuestId = g.GuestId
WHERE a.`Name` LIKE 'jacuzzi';

-- 		GuestName		RoomNumber	Checkin		Checkout
-- 		Karie Yang		201			2023-03-06	2023-03-07
-- 		Bettyann Seery	203			2023-02-05	2023-02-10
-- 		Karie Yang		203			2023-09-13	2023-09-15
-- 		Jonathan Lee	205			2023-06-28	2023-07-02
-- 		Wilfred Vise	207			2023-04-23	2023-04-24
-- 		Walter Holaway	301			2023-04-09	2023-04-13
-- 		Mack Simmer		301			2023-11-22	2023-11-25
-- 		Bettyann Seery	303			2023-07-28	2023-07-29
-- 		Duane Cullison	305			2023-02-22	2023-02-04
-- 		Bettyann Seery	305			2023-08-30	2023-09-01
-- 		Jonathan Lee	307			2023-03-17	2023-03-20

-- 3. Write a query that returns all the rooms reserved for a specific guest, including the guest's name, the room(s) reserved, the starting date of the reservation,
-- and how many people were included in the reservation. (Choose a guest's name from the existing data.)
SELECT
	CONCAT(g.FirstName, ' ', g.LastName) GuestName,
    rr.RoomId RoomNumber,
    res.StartDate Checkin,
    rr.Adults AdultsInRoom,
    IFNULL(rr.Children, 0) ChildrenInRoom
FROM Guest g
INNER JOIN Reservation res ON g.GuestId = res.GuestId
INNER JOIN RoomReservation rr ON res.ReservationId = rr.ReservationId
WHERE g.LastName LIKE 'Simmer'
ORDER BY res.StartDate ASC;

-- 		GuestName	RoomNumber	Checkin		AdultsInRoom	ChildrenInRoom
-- 		Mack Simmer 308 		2023-02-02 	1				0
-- 		Mack Simmer	208			2023-09-16	2				0
-- 		Mack Simmer	206			2023-11-22	2				0
-- 		Mack Simmer	301			2023-11-22	2				2

-- 4. Write a query that returns a list of rooms, reservation ID, and per-room cost for each reservation. The results should include all rooms, whether or not there 
-- is a reservation associated with the room.
SELECT
	rm.RoomId RoomNumber,
    IFNULL(rr.ReservationId, 'No existing reservations') ReservationId,
    IFNULL(res.TotalCost, 0) TotalCost
FROM Room rm
LEFT OUTER JOIN RoomReservation rr ON rm.RoomId = rr.RoomId
LEFT OUTER JOIN Reservation res ON rr.ReservationId = res.ReservationId
ORDER BY rm.RoomId ASC;

-- 		RoomNumber	ReservationId				TotalCost
-- 		201			4							199.99
-- 		202			7							349.98
-- 		203			2							999.95
-- 		203			20							399.98
-- 		204			15							184.99
-- 		205			14							699.96
-- 		206			12							599.96
-- 		206			22							449.97
-- 		207			10							174.99
-- 		208			12							599.96
-- 		208			19							149.99
-- 		301			9							799.96
-- 		301			22							449.97
-- 		302			6							924.95
-- 		302			23							699.96
-- 		303			17							199.99
-- 		304			13							184.99
-- 		305			3							349.98
-- 		305			18							349.98
-- 		306			No existing reservations	0.00
-- 		307			5							524.97
-- 		308			1							299.98
-- 		401			11							1199.97
-- 		401			16							1259.97
-- 		401			21							1199.97
-- 		402			No existing reservations	0.00

-- 5. Write a query that returns all the rooms accommodating at least three guests and that are reserved on any date in April 2023.
SELECT
	rm.RoomId RoomNumber,
    rt.MaxOcc MaxOccupancy
FROM RoomType rt
INNER JOIN Room rm ON rt.RoomTypeId = rm.RoomTypeId
INNER JOIN RoomReservation rr ON rm.RoomId = rr.RoomId
INNER JOIN Reservation res ON rr.ReservationId = res.ReservationId
WHERE rt.MaxOcc >= 3
	AND res.StartDate > '2023-04-01'
    AND res.EndDate < '2023-05-01'
ORDER BY rm.RoomId ASC;

-- Room 301 has a maximum occupancy of 4 people and is reserved by Walter Holaway from 04.09.2023 to 04.13.2023.

-- 6. Write a query that returns a list of all guest names and the number of reservations per guest, sorted starting with the guest with the most reservations and 
-- then by the guest's last name.
SELECT
	CONCAT(g.FirstName, ' ', g.LastName) GuestName,
    COUNT(grr.RoomReservationId) NumberOfReservations
FROM Guest g
LEFT OUTER JOIN GuestRoomReservation grr ON g.GuestId = grr.GuestId
GROUP BY g.LastName
ORDER BY COUNT(grr.RoomReservationId) DESC, g.LastName ASC;

-- 	GuestName			NumberOfReservations
-- 	Mack Simmer			4
-- 	Bettyann Seery		3
-- 	Duane Cullison		2
-- 	Walter Holaway		2
-- 	Jonathan Lee		2
-- 	Aurore Lipton		2
-- 	Maritza Tilton		2
-- 	Joleen Tison		2
-- 	Wilfred Vise		2
-- 	Karie Yang			2
-- 	Zachery Luechtefeld	1

-- 7. Write a query that displays the name, address, and phone number of a guest based on their phone number. (Choose a phone number from the existing data.)
SELECT
	CONCAT(g.FirstName, ' ', g.LastName) GuestName,
    a.Street,
    c.`Name` City,
    s.`Name` State,
    z.ZipCode
FROM Guest g
INNER JOIN Address a ON g.AddressId = a.AddressId
INNER JOIN City c ON a.CityId = c.CityId
INNER JOIN State s ON a.StateAbbr = s.StateAbbr
INNER JOIN ZipCode z ON a.ZipId = z.ZipId
WHERE g.Phone LIKE '(612) 321-8745';

-- 	GuestName		Street	ALTER					City		State		ZipCode
-- 	Jonathan Lee	3625 East 43 Street, Apt 418	Minneapolis	Minnesota	55406