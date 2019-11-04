-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- 主机： 10.53.228.19:16381
-- 生成日期： 2019-11-04 10:25:12
-- 服务器版本： 5.7.18-20170830-log
-- PHP 版本： 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `spring_mvc_dubbo_demo`
--

-- --------------------------------------------------------

--
-- 表的结构 `demo1`
--

CREATE TABLE `demo1` (
  `id` int(11) NOT NULL,
  `message1` varchar(99) DEFAULT NULL,
  `message2` varchar(99) DEFAULT NULL,
  `message3` varchar(90) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `demo1`
--

INSERT INTO `demo1` (`id`, `message1`, `message2`, `message3`) VALUES
(1, 'Hello', 'World', '!');

--
-- 转储表的索引
--

--
-- 表的索引 `demo1`
--
ALTER TABLE `demo1`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `demo1`
--
ALTER TABLE `demo1`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
