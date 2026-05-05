-- Insert clubs
INSERT INTO clubs (name, manager, location) VALUES
('Lions', 'John Doe', 'New York'),
('Tigers', 'Jane Smith', 'Los Angeles'),
('Eagles', 'Robert Brown', 'Chicago'),
('Sharks', 'Emily Davis', 'Miami'),
('Bears', 'Michael Wilson', 'Denver');

-- Insert branches
INSERT INTO branches (club_id, name) VALUES
(1, 'Downtown'), (1, 'Uptown'),
(2, 'Westside'), (2, 'Eastside'), (2, 'Northside'),
(3, 'Central'),
(4, 'Beachfront'), (4, 'Harbor'),
(5, 'Mountain View'), (5, 'Valley'), (5, 'Riverside');

-- Insert members
INSERT INTO members (id, club_id, name, phone, number_of_children) VALUES
(101, 1, 'Alice Johnson', '555-0101', 2),
(102, 1, 'Bob Smith', '555-0102', 0),
(103, 1, 'Charlie Brown', '555-0103', 3),
(201, 2, 'Diana Prince', '555-0201', 1),
(202, 2, 'Evan Wright', '555-0202', 2),
(301, 3, 'Frank Miller', '555-0301', 0),
(302, 3, 'Grace Lee', '555-0302', 1),
(303, 3, 'Henry Davis', '555-0303', 4),
(304, 3, 'Ivy Wilson', '555-0304', 2),
(401, 4, 'Jack Taylor', '555-0401', 1),
(501, 5, 'Karen White', '555-0501', 2),
(502, 5, 'Leo Martin', '555-0502', 3);

-- Insert sports
INSERT INTO sports (id, name, number_of_teams) VALUES
(1, 'Basketball', 8),
(2, 'Football', 12),
(3, 'Swimming', 6),
(4, 'Tennis', 4),
(5, 'Volleyball', 6),
(6, 'Baseball', 10),
(7, 'Soccer', 11);

-- Associate clubs with sports
INSERT INTO club_sports (club_id, sport_id) VALUES
(1, 1), (1, 3), (1, 4),      -- Lions: Basketball, Swimming, Tennis
(2, 1), (2, 2), (2, 7),      -- Tigers: Basketball, Football, Soccer
(3, 3), (3, 4), (3, 5),      -- Eagles: Swimming, Tennis, Volleyball
(4, 3), (4, 6),              -- Sharks: Swimming, Baseball
(5, 2), (5, 6), (5, 7);      -- Bears: Football, Baseball, Soccer