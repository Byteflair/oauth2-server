
DROP TABLE IF EXISTS rolesperuser;
DROP TABLE IF EXISTS rolespergroup;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS persistenlogins;
DROP TABLE IF EXISTS oauth_refresh_token;
DROP TABLE IF EXISTS oauth_code;
DROP TABLE IF EXISTS oauth_client_token;
DROP TABLE IF EXISTS oauth_client_details;
DROP TABLE IF EXISTS oauth_approvals;
DROP TABLE IF EXISTS oauth_access_token;
DROP TABLE IF EXISTS groupmembers;
DROP TABLE IF EXISTS userdetails;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS userstates;
DROP TABLE IF EXISTS systems;
DROP TABLE IF EXISTS groups;

DROP SEQUENCE IF EXISTS groups_seq;
DROP SEQUENCE IF EXISTS users_seq;
DROP SEQUENCE IF EXISTS userdetails_seq;
DROP SEQUENCE IF EXISTS roles_seq;

CREATE SEQUENCE groups_seq;

CREATE TABLE groups (
  idgroup int NOT NULL DEFAULT NEXTVAL ('groups_seq'),
  groupname varchar(45) NOT NULL,
  PRIMARY KEY (idgroup)
)  ;

ALTER SEQUENCE groups_seq RESTART WITH 2;

CREATE TABLE systems (
  idsystem int NOT NULL,
  name varchar(45) NOT NULL,
  PRIMARY KEY (idsystem)
) ;

CREATE TABLE userstates (
  iduserstate int NOT NULL,
  description varchar(45) NOT NULL,
  PRIMARY KEY (iduserstate)
) ;

CREATE SEQUENCE users_seq;

CREATE TABLE users (
  iduser int NOT NULL DEFAULT NEXTVAL ('users_seq'),
  username varchar(45) NOT NULL,
  password varchar(100) NOT NULL,
  idsystem int NOT NULL,
  name varchar(100) NOT NULL,
  phone varchar(45) DEFAULT NULL,
  phone1 varchar(45) DEFAULT NULL,
  phone2 varchar(45) DEFAULT NULL,
  email varchar(100) NOT NULL,
  postaladdress varchar(512) DEFAULT NULL,
  iduserstate int NOT NULL,
  PRIMARY KEY (iduser),
  CONSTRAINT login_UNIQUE UNIQUE  (username),
  CONSTRAINT email_UNIQUE UNIQUE  (email)
 ,
  CONSTRAINT systemstousers FOREIGN KEY (idsystem) REFERENCES systems (idsystem) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT userstatestousers FOREIGN KEY (iduserstate) REFERENCES userstates (iduserstate) ON DELETE NO ACTION ON UPDATE NO ACTION
)  ;

ALTER SEQUENCE users_seq RESTART WITH 2;

CREATE INDEX systemstousers_idx ON users (idsystem);
CREATE INDEX userstatestousers_idx ON users (iduserstate);

CREATE SEQUENCE userdetails_seq;

CREATE TABLE userdetails (
  iduserdetail int NOT NULL DEFAULT NEXTVAL ('userdetails_seq'),
  iduser int NOT NULL,
  detailkey varchar(30) NOT NULL,
  detailvalue varchar(100) NULL,
  PRIMARY KEY (iduserdetail),
  CONSTRAINT userstousersdetail FOREIGN KEY (iduser) REFERENCES users (iduser) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;

CREATE TABLE groupmembers (
  iduser int NOT NULL,
  idgroup int NOT NULL,
  PRIMARY KEY (iduser,idgroup)
 ,
  CONSTRAINT groupstogroupmembers FOREIGN KEY (idgroup) REFERENCES groups (idgroup) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT userstogroupmembers FOREIGN KEY (iduser) REFERENCES users (iduser) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;

CREATE INDEX groupstogroupmembers_idx ON groupmembers (idgroup);

CREATE TABLE oauth_access_token (
  token_id varchar(255) DEFAULT NULL,
  token BYTEA,
  authentication_id varchar(255) NOT NULL,
  user_name varchar(255) DEFAULT NULL,
  client_id varchar(255) DEFAULT NULL,
  authentication BYTEA,
  refresh_token varchar(255) DEFAULT NULL,
  PRIMARY KEY (authentication_id)
) ;

CREATE TABLE oauth_approvals (
  userId varchar(255) DEFAULT NULL,
  clientId varchar(255) DEFAULT NULL,
  scope varchar(255) DEFAULT NULL,
  status varchar(10) DEFAULT NULL,
  expiresAt timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  lastModifiedAt timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP
) ;

CREATE TABLE oauth_client_details (
  client_id varchar(255) NOT NULL,
  resource_ids varchar(255) DEFAULT NULL,
  client_secret varchar(255) DEFAULT NULL,
  scope varchar(255) DEFAULT NULL,
  authorized_grant_types varchar(255) DEFAULT NULL,
  web_server_redirect_uri varchar(255) DEFAULT NULL,
  authorities varchar(255) DEFAULT NULL,
  access_token_validity int DEFAULT NULL,
  refresh_token_validity int DEFAULT NULL,
  additional_information varchar(4096) DEFAULT NULL,
  autoapprove varchar(255) DEFAULT NULL,
  PRIMARY KEY (client_id)
) ;

CREATE TABLE oauth_client_token (
  token_id varchar(255) DEFAULT NULL,
  token BYTEA,
  authentication_id varchar(255) NOT NULL,
  user_name varchar(255) DEFAULT NULL,
  client_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (authentication_id)
) ;

CREATE TABLE oauth_code (
  code varchar(255) DEFAULT NULL,
  authentication BYTEA
) ;

CREATE TABLE oauth_refresh_token (
  token_id varchar(255) DEFAULT NULL,
  token BYTEA,
  authentication BYTEA
) ;

CREATE TABLE persistenlogins (
  username varchar(64) NOT NULL,
  series varchar(64) NOT NULL,
  token varchar(64) NOT NULL,
  lastused timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (series)
) ;

CREATE SEQUENCE roles_seq;

CREATE TABLE roles (
  idrole int NOT NULL DEFAULT NEXTVAL ('roles_seq'),
  rolename varchar(45) NOT NULL,
  PRIMARY KEY (idrole)
)  ;

ALTER SEQUENCE roles_seq RESTART WITH 2;

CREATE TABLE rolespergroup (
  idgroup int NOT NULL,
  idrole int NOT NULL,
  PRIMARY KEY (idgroup,idrole)
 ,
  CONSTRAINT groupstorolespergroup FOREIGN KEY (idgroup) REFERENCES groups (idgroup) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT rolestorolespergroup FOREIGN KEY (idrole) REFERENCES roles (idrole) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;

CREATE INDEX rolestorolespergroup_idx ON rolespergroup (idrole);

CREATE TABLE rolesperuser (
  iduser int NOT NULL,
  idrole int NOT NULL,
  PRIMARY KEY (iduser,idrole)
 ,
  CONSTRAINT rolestorolesperuser FOREIGN KEY (idrole) REFERENCES roles (idrole) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT userstorolesperuser FOREIGN KEY (iduser) REFERENCES users (iduser) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;

CREATE INDEX rolestorolesperuser_idx ON rolesperuser (idrole);