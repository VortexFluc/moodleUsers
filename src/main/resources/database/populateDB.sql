INSERT INTO users VALUES
(1, 1, 1, 1, 123),
(2, 2, 2, 2, 124),
(3, 3, 3, 3, 125),
(4, 4, 4, 4, 126);

INSERT INTO times VALUES
(1, '2021-07-01', 3, '2021-07-26', true, 2, (SELECT id FROM users WHERE id = 1)),
(2, null, 3, null, false, 3, (SELECT id FROM users WHERE id = 2)),
(3, '2021-07-01', 4, '2021-09-08', true, 2, (SELECT id FROM users WHERE id = 1)),
(4, '2021-05-01', 4, null, true, 3, (SELECT id FROM users WHERE id = 2)),
(5, '2021-08-03', 3, '2021-10-01', true, 2, (SELECT id FROM users WHERE id = 3)),
(6, null, 4, null, false, 2, (SELECT id FROM users WHERE id = 3)),
(7, null, 3, null, false, 4, (SELECT id FROM users WHERE id = 4));

INSERT INTO regions VALUES
(1, 'Моcква'),
(2, 'Ленинградская область'),
(3, 'Приморский край'),
(4, 'Уральский федеральный округ'),
(5, 'Московская область');

INSERT INTO city VALUES
(1, 'Москва'),
(2, 'Санкт-Петербург'),
(3, 'Владивосток'),
(4, 'Екатеринбург'),
(5, 'Чехов'),
(6, 'Подольск');

INSERT INTO institutions VALUES
(1, 'Учреждение1'),
(2, 'Учреждение2'),
(3, 'Учреждение3'),
(4, 'Учреждение4');

INSERT INTO courses VALUES
(1, 3, 'Course number 1', 'course1', '2021-06-01'),
(2, 4, 'Course number 2', 'course2', '2021-03-01');

INSERT INTO learning_plan VALUES
(1, 2, 'lp1'),
(2, 3, 'lp2'),
(3, 4, 'lp3');