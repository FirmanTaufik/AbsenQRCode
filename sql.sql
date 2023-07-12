-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table db_absen_qrcode.tb_dosen
DROP TABLE IF EXISTS `tb_dosen`;
CREATE TABLE IF NOT EXISTS `tb_dosen` (
  `id_dosen` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL DEFAULT '0',
  `Password` varchar(50) NOT NULL DEFAULT '0',
  `NIDN` int NOT NULL DEFAULT '0',
  `Nama_dosen` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_dosen`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table db_absen_qrcode.tb_dosen: ~2 rows (approximately)
DELETE FROM `tb_dosen`;
INSERT INTO `tb_dosen` (`id_dosen`, `Username`, `Password`, `NIDN`, `Nama_dosen`) VALUES
	(1, 'dosen', 'ce28eed1511f631af6b2a7bb0a85d636', 1, 'dosen'),
	(2, 'dosen2', 'ac41c4e0e6ef7ac51f0c8f3895f82ce5', 1, 'dosen2');

-- Dumping structure for table db_absen_qrcode.tb_kelas
DROP TABLE IF EXISTS `tb_kelas`;
CREATE TABLE IF NOT EXISTS `tb_kelas` (
  `id_kelas` int NOT NULL AUTO_INCREMENT,
  `Nama_Kelas` varchar(50) NOT NULL DEFAULT '0',
  `Tahun_ajaran` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_kelas`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table db_absen_qrcode.tb_kelas: ~2 rows (approximately)
DELETE FROM `tb_kelas`;
INSERT INTO `tb_kelas` (`id_kelas`, `Nama_Kelas`, `Tahun_ajaran`) VALUES
	(1, '1B', '2023'),
	(2, '2B', '2023');

-- Dumping structure for table db_absen_qrcode.tb_mhs
DROP TABLE IF EXISTS `tb_mhs`;
CREATE TABLE IF NOT EXISTS `tb_mhs` (
  `id_mhs` int NOT NULL AUTO_INCREMENT,
  `NIM` int DEFAULT '0',
  `Nama` varchar(50) DEFAULT NULL,
  `Tempat_Tgl_Lhr` date DEFAULT NULL,
  `Jurusan` varchar(50) DEFAULT NULL,
  `Foto` text,
  `id_kelas` int DEFAULT NULL,
  PRIMARY KEY (`id_mhs`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table db_absen_qrcode.tb_mhs: ~3 rows (approximately)
DELETE FROM `tb_mhs`;
INSERT INTO `tb_mhs` (`id_mhs`, `NIM`, `Nama`, `Tempat_Tgl_Lhr`, `Jurusan`, `Foto`, `id_kelas`) VALUES
	(1, 111, 'Dadang', '2001-06-14', 'Teknik Mesin', '1.jpg', 1),
	(2, 222, 'Nisa', '2001-05-17', 'Teknik Mesin', NULL, 1),
	(3, 333, 'Heri', '2001-07-18', 'Hukum', NULL, 2);

-- Dumping structure for table db_absen_qrcode.tb_mk
DROP TABLE IF EXISTS `tb_mk`;
CREATE TABLE IF NOT EXISTS `tb_mk` (
  `id_mk` int NOT NULL AUTO_INCREMENT,
  `Nama_mk` varchar(50) NOT NULL DEFAULT '',
  `Kode_mk` varchar(50) DEFAULT NULL,
  `id_dosen` int DEFAULT NULL,
  PRIMARY KEY (`id_mk`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table db_absen_qrcode.tb_mk: ~3 rows (approximately)
DELETE FROM `tb_mk`;
INSERT INTO `tb_mk` (`id_mk`, `Nama_mk`, `Kode_mk`, `id_dosen`) VALUES
	(1, 'Bahasa Inggris', '1', 1),
	(2, 'Kalkulus', '222314414', 1),
	(3, 'Ilmu Komputer', '121434425', 2);

-- Dumping structure for table db_absen_qrcode.tb_presensi
DROP TABLE IF EXISTS `tb_presensi`;
CREATE TABLE IF NOT EXISTS `tb_presensi` (
  `id_presensi` int NOT NULL AUTO_INCREMENT,
  `Pertemuan` int DEFAULT NULL,
  `Tanggal` date DEFAULT NULL,
  `Id_mk` int DEFAULT NULL,
  `Id_mhs` int DEFAULT NULL,
  `Id_kelas` int DEFAULT NULL,
  PRIMARY KEY (`id_presensi`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table db_absen_qrcode.tb_presensi: ~3 rows (approximately)
DELETE FROM `tb_presensi`;
INSERT INTO `tb_presensi` (`id_presensi`, `Pertemuan`, `Tanggal`, `Id_mk`, `Id_mhs`, `Id_kelas`) VALUES
	(25, 1, '2023-07-12', 2, 3, 2),
	(27, 1, '2023-07-12', 1, 1, 1),
	(28, 1, '2023-07-12', 1, 2, 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
