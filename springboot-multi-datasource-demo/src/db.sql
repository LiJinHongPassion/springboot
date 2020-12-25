create database account;
use account;
CREATE TABLE `user` (
                        `id` int(11) NOT NULL,
                        `expired` varchar(255) DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



create database icebreg;
use icebreg;
CREATE TABLE `order` (
                         `id` int(11) NOT NULL,
                         `expired` varchar(255) DEFAULT NULL,
                         `status` varchar(255) DEFAULT NULL,
                         `create_time` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



create database ocean;
use ocean;
CREATE TABLE `product` (
                           `id` int(11) NOT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;