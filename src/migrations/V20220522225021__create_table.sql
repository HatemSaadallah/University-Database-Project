-- Write Migration
-- Version: 20220522225021

USE `university_project`;

CREATE TABLE `users` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `created_at` datetime NOT NULL,
    `created_by` int(11),
    `updated_at` datetime,
    `updated_by` int(11),
    `deleted_at` datetime,
    `deleted_by` int(11),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;