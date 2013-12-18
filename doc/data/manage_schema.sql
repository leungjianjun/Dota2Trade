-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2013 年 12 月 18 日 13:39
-- 服务器版本: 5.5.24-log
-- PHP 版本: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `manage_schema`
--

-- --------------------------------------------------------

--
-- 表的结构 `attachment`
--

CREATE TABLE IF NOT EXISTS `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `link` varchar(500) NOT NULL,
  `creatorid` int(11) NOT NULL,
  `literatureid` int(11) NOT NULL,
  `type` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `cited`
--

CREATE TABLE IF NOT EXISTS `cited` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `literatureid` int(11) NOT NULL,
  `citedbyid` int(11) NOT NULL,
  `citedtypeid` int(3) NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `literature`
--

CREATE TABLE IF NOT EXISTS `literature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorid` int(11) NOT NULL DEFAULT '0',
  `updaterid` int(11) NOT NULL DEFAULT '0',
  `status` tinyint(1) NOT NULL,
  `literaturetypeid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- 转存表中的数据 `literature`
--

INSERT INTO `literature` (`id`, `creatorid`, `updaterid`, `status`, `literaturetypeid`) VALUES
(4, 7, 0, 0, 1),
(5, 7, 0, 0, 2),
(6, 7, 0, 0, 4),
(7, 7, 0, 0, 7),
(8, 7, 0, 0, 2),
(9, 7, 0, 0, 2),
(10, 7, 0, 0, 1),
(11, 7, 0, 0, 2),
(12, 7, 0, 0, 1),
(13, 7, 0, 0, 1);

-- --------------------------------------------------------

--
-- 表的结构 `literaturemeta`
--

CREATE TABLE IF NOT EXISTS `literaturemeta` (
  `literatureid` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `literature_abstract` varchar(1000) NOT NULL,
  `author` varchar(300) NOT NULL,
  `published_year` date NOT NULL,
  `key_words` varchar(300) NOT NULL,
  `link` varchar(1024) NOT NULL,
  `pages` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`literatureid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `literaturemeta`
--

INSERT INTO `literaturemeta` (`literatureid`, `title`, `literature_abstract`, `author`, `published_year`, `key_words`, `link`, `pages`) VALUES
(4, 'Re: 日本数千人夹道欢迎新任美驻日大使肯尼迪', '高等', 'liyancode', '2013-12-03', '呵呵', 'www', '11'),
(5, 'Re: 江泽民在国立中央大学读书', '划分', 'liyancode', '2013-12-03', '呵呵', 'www.ddd', '11'),
(6, 'Re: 专硕真的很受伤', 'thdf', 'jjkk', '2013-12-09', 'fa', 'www.ddd.ff', '115'),
(7, '江泽民在国立中央大学读书  gfgfg', 'gfds', 'liyancode', '2013-12-03', '呵呵fds', 'www.ddd.ff', '113'),
(9, '美驻日大使肯尼迪', '收费多少多少收电费', 'jjkk', '2013-12-03', '规范的施工', 'http://docs.spring.io/spring/docs/3.1.4.RELEASE/javadoc-api/org/springframework/dao/DuplicateKeyException.html', '113'),
(10, 'Re: 江泽民在国立中央大学读书反反复复', 'vbddfgh', 'jjkk', '2013-12-03', '规范的施工', 'http://docs.spring.io/spring/docs/3.1.4.RELEASE/javadoc-api/org/springframework/dao/DuplicateKeyException.html', '11'),
(11, '迎新任美驻日大使肯尼迪', 'sgdgsdfgadgadfgadfgagagasdg', 'fgdsfg', '2013-12-04', 'fgasg收到法国大叔', 'wwwsdfsdaf', '113'),
(12, 'è¿æ°ä»»ç¾é©»', 'hgfds', 'liyancode', '2013-12-03', 'sdg', 'www', '11'),
(13, 'å®ä¹ ', 'gdg', '', '2013-12-04', 'åµåµ', 'wwwsdfsdaf', '113');

-- --------------------------------------------------------

--
-- 表的结构 `literaturetype`
--

CREATE TABLE IF NOT EXISTS `literaturetype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `literature_publisher`
--

CREATE TABLE IF NOT EXISTS `literature_publisher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `literatureid` int(11) NOT NULL,
  `publisherid` int(11) NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- 转存表中的数据 `literature_publisher`
--

INSERT INTO `literature_publisher` (`id`, `literatureid`, `publisherid`) VALUES
(3, 4, 106),
(6, 7, 106),
(7, 9, 108),
(8, 10, 109),
(9, 11, 110),
(10, 12, 111),
(11, 13, 112);

-- --------------------------------------------------------

--
-- 表的结构 `publisher`
--

CREATE TABLE IF NOT EXISTS `publisher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=113 ;

--
-- 转存表中的数据 `publisher`
--

INSERT INTO `publisher` (`id`, `name`) VALUES
(106, '南京大学出版社'),
(108, '人民出版社'),
(109, '南京大学出版社dd'),
(110, '规范'),
(111, '啊啊啊'),
(112, '噢噢噢噢');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `account`, `password`) VALUES
(7, 'liyancode', '123456');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
