-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.40 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping data for table java_library.app_user: ~3 rows (approximately)
INSERT INTO `app_user` (`username`, `password`) VALUES
	('admin', '$2a$10$a/oP/XQ/dqDrZ004umFtqOS5Mj7l26yTMeaR9WsGlzqZ6UbJcQMtK'),
	('Ana', '$2a$10$7BP2Mxgm9ZOfLZBQVVXyYuqyk0uHeyCDlvoWfDY7r1AwJeREdOHV2'),
	('lib', '$2a$10$i42Di/uhr4D2AzqqM18vP.8Cs/9kBn0NoGcf6fEc8dXFZYxbrAR7C');

-- Dumping data for table java_library.app_user_roles: ~6 rows (approximately)
INSERT INTO `app_user_roles` (`user_id`, `role_id`) VALUES
	('admin', 1),
	('admin', 2),
	('lib', 2),
	('admin', 3),
	('Ana', 3),
	('lib', 3);

-- Dumping data for table java_library.author: ~1 rows (approximately)
INSERT INTO `author` (`id`, `first_name`, `last_name`) VALUES
	(1, 'Graham', ' Norton ');

-- Dumping data for table java_library.book: ~2 rows (approximately)
INSERT INTO `book` (`isbn`, `availability`, `available_quantity`, `release_date`, `title`, `total_quantity`, `author_id`, `category_id`) VALUES
	('0063436477', b'1', 14, '2025-01-14', 'Frankie: A Novel', 14, 1, 1),
	('11111111111', b'1', 1, '2023-06-08', 'hello', 1, 1, 2);

-- Dumping data for table java_library.book_loan: ~2 rows (approximately)
INSERT INTO `book_loan` (`id`, `borrowed`, `due_date`, `returned`, `status`, `book_isbn`, `user_username`) VALUES
	(1, '2025-01-21 10:55:38.664414', '2025-01-20 10:55:38.664000', '2025-01-21 11:01:02.764916', 'RETURNED', '11111111111', 'admin'),
	(2, '2025-01-21 11:03:36.261934', '2025-02-20 11:03:36.261934', NULL, 'BORROWED', '0063436477', 'admin');

-- Dumping data for table java_library.category: ~2 rows (approximately)
INSERT INTO `category` (`id`, `category_name`) VALUES
	(1, 'Fiction'),
	(2, 'Mystery');

-- Dumping data for table java_library.notification: ~2 rows (approximately)
INSERT INTO `notification` (`id`, `created_at`, `message`, `user_username`) VALUES
	(1, '2025-01-21', 'Your loan for \'hello\' expires tomorrow!', 'admin'),
	(2, '2025-01-21', 'OVERDUE: Book \'hello\' is overdue', 'admin');

-- Dumping data for table java_library.role: ~3 rows (approximately)
INSERT INTO `role` (`id`, `role_name`) VALUES
	(1, 'ADMIN'),
	(2, 'LIBRARIAN'),
	(3, 'USER');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
