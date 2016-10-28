
CREATE DATABASE IF NOT EXISTS `oauth_db`;

DROP TABLE IF EXISTS `rolesperuser`;
DROP TABLE IF EXISTS `rolespergroup`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `persistenlogins`;
DROP TABLE IF EXISTS `oauth_refresh_token`;
DROP TABLE IF EXISTS `oauth_code`;
DROP TABLE IF EXISTS `oauth_client_token`;
DROP TABLE IF EXISTS `oauth_client_details`;
DROP TABLE IF EXISTS `oauth_approvals`;
DROP TABLE IF EXISTS `oauth_access_token`;
DROP TABLE IF EXISTS `groupmembers`;
DROP TABLE IF EXISTS `userdetails`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `userstates`;
DROP TABLE IF EXISTS `systems`;
DROP TABLE IF EXISTS `groups`;

CREATE TABLE `groups` (
  `idgroup` int(11) NOT NULL AUTO_INCREMENT,
  `groupname` varchar(45) NOT NULL,
  PRIMARY KEY (`idgroup`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `systems` (
  `idsystem` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idsystem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `userstates` (
  `iduserstate` int(11) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`iduserstate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `idsystem` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `phone1` varchar(45) DEFAULT NULL,
  `phone2` varchar(45) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `postaladdress` varchar(512) DEFAULT NULL,
  `iduserstate` int(11) NOT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `login_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `systemstousers_idx` (`idsystem`),
  KEY `userstatestousers_idx` (`iduserstate`),
  CONSTRAINT `systemstousers` FOREIGN KEY (`idsystem`) REFERENCES `systems` (`idsystem`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userstatestousers` FOREIGN KEY (`iduserstate`) REFERENCES `userstates` (`iduserstate`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `userdetails` (
  `iduserdetail` int(11) NOT NULL AUTO_INCREMENT,
  `iduser` int(11) NOT NULL,
  `detailkey` varchar(30) NOT NULL,
  `detailvalue` varchar(100) NULL,
  PRIMARY KEY (`iduserdetail`),
  CONSTRAINT `userstousersdetail` FOREIGN KEY (`iduser`) REFERENCES `users` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `groupmembers` (
  `iduser` int(11) NOT NULL,
  `idgroup` int(11) NOT NULL,
  PRIMARY KEY (`iduser`,`idgroup`),
  KEY `groupstogroupmembers_idx` (`idgroup`),
  CONSTRAINT `groupstogroupmembers` FOREIGN KEY (`idgroup`) REFERENCES `groups` (`idgroup`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userstogroupmembers` FOREIGN KEY (`iduser`) REFERENCES `users` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` mediumblob,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `authentication` mediumblob,
  `refresh_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_approvals` (
  `userId` varchar(255) DEFAULT NULL,
  `clientId` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastModifiedAt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` mediumblob,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_code` (
  `code` varchar(255) DEFAULT NULL,
  `authentication` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` mediumblob,
  `authentication` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `persistenlogins` (
  `username` varchar(64) CHARACTER SET latin1 NOT NULL,
  `series` varchar(64) CHARACTER SET latin1 NOT NULL,
  `token` varchar(64) CHARACTER SET latin1 NOT NULL,
  `lastused` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `roles` (
  `idrole` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(45) NOT NULL,
  PRIMARY KEY (`idrole`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `rolespergroup` (
  `idgroup` int(11) NOT NULL,
  `idrole` int(11) NOT NULL,
  PRIMARY KEY (`idgroup`,`idrole`),
  KEY `rolestorolespergroup_idx` (`idrole`),
  CONSTRAINT `groupstorolespergroup` FOREIGN KEY (`idgroup`) REFERENCES `groups` (`idgroup`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `rolestorolespergroup` FOREIGN KEY (`idrole`) REFERENCES `roles` (`idrole`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rolesperuser` (
  `iduser` int(11) NOT NULL,
  `idrole` int(11) NOT NULL,
  PRIMARY KEY (`iduser`,`idrole`),
  KEY `rolestorolesperuser_idx` (`idrole`),
  CONSTRAINT `rolestorolesperuser` FOREIGN KEY (`idrole`) REFERENCES `roles` (`idrole`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userstorolesperuser` FOREIGN KEY (`iduser`) REFERENCES `users` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;