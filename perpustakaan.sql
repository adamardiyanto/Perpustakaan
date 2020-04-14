-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 14, 2020 at 06:29 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpustakaan`
--

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `NIS` varchar(9) NOT NULL,
  `Nama` varchar(20) NOT NULL,
  `TTL` date NOT NULL,
  `Jenkel` varchar(10) NOT NULL,
  `Agama` varchar(20) NOT NULL,
  `tgl_daftar` date NOT NULL,
  `tgl_akhir` date NOT NULL,
  `kelas` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`NIS`, `Nama`, `TTL`, `Jenkel`, `Agama`, `tgl_daftar`, `tgl_akhir`, `kelas`) VALUES
('121', 'qwe', '2000-03-04', 'perempuan', 'Islam', '2020-03-04', '2023-03-04', 'B'),
('123', 'adam', '2000-04-03', 'Laki laki', 'Islam', '2020-03-05', '2023-03-05', 'A'),
('124', 'saya', '2001-04-04', 'Laki laki', 'Islam', '2020-02-12', '2023-02-12', 'B');

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `Kode_buku` varchar(3) NOT NULL,
  `Judul` varchar(30) NOT NULL,
  `Pengarang` varchar(20) NOT NULL,
  `Penerbit` varchar(20) NOT NULL,
  `Tahun` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`Kode_buku`, `Judul`, `Pengarang`, `Penerbit`, `Tahun`) VALUES
('001', 'AADC', 'saya', 'kantorku', '2018'),
('002', 'Conan', 'Conan', 'elex', '2008'),
('003', 'naroto', 'masasi kisimoto', 'elex', '2008');

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `NIK` varchar(9) NOT NULL,
  `Nama_k` varchar(20) NOT NULL,
  `Jenkel` varchar(20) NOT NULL,
  `Golongan` int(11) NOT NULL,
  `Jabatan` varchar(20) NOT NULL,
  `Tunjangan` int(11) NOT NULL,
  `Gaji` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`NIK`, `Nama_k`, `Jenkel`, `Golongan`, `Jabatan`, `Tunjangan`, `Gaji`) VALUES
('111', 'paijo', 'Laki laki', 2, 'magang', 0, 500000),
('234', 'ada', 'Laki laki', 2, 'magang', 0, 500000);

-- --------------------------------------------------------

--
-- Table structure for table `peminjaman`
--

CREATE TABLE `peminjaman` (
  `id_pinjam` varchar(3) NOT NULL,
  `NIS_p` varchar(9) NOT NULL,
  `Kode_buku_p` varchar(3) NOT NULL,
  `tgl_pinjam` date NOT NULL,
  `tgl_kembali` date NOT NULL,
  `Lama` bigint(5) NOT NULL,
  `Denda` bigint(11) NOT NULL,
  `NIK_p` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `peminjaman`
--

INSERT INTO `peminjaman` (`id_pinjam`, `NIS_p`, `Kode_buku_p`, `tgl_pinjam`, `tgl_kembali`, `Lama`, `Denda`, `NIK_p`) VALUES
('01', '123', '001', '2020-03-01', '2020-03-14', 1123200000, 1123199993000, '111'),
('02', '124', '002', '2020-04-10', '2020-04-11', 86400000, 86399993000, '234'),
('03', '123', '002', '2020-04-10', '2020-04-12', 172800000, 172799993000, '111');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`NIS`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`Kode_buku`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`NIK`);

--
-- Indexes for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD KEY `fkbuku` (`Kode_buku_p`),
  ADD KEY `fkanggota` (`NIS_p`),
  ADD KEY `fkkaryawan` (`NIK_p`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD CONSTRAINT `fkanggota` FOREIGN KEY (`NIS_p`) REFERENCES `anggota` (`NIS`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkbuku` FOREIGN KEY (`Kode_buku_p`) REFERENCES `buku` (`Kode_buku`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkkaryawan` FOREIGN KEY (`NIK_p`) REFERENCES `karyawan` (`NIK`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
