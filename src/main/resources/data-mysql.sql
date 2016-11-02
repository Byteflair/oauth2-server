INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `scope`, `authorized_grant_types`, `authorities`, `access_token_validity`, `refresh_token_validity`, `autoapprove`) VALUES ('mercury','$2a$12$6bEkR5.PZfut4t8azXk9fOqrBQzC8eu/iavS.iQGZrWKXIOYQ.ADW','read,write,trust','authorization_code,password,refresh_token,implicit,client_credentials','ROLE_TRUSTED_CLIENT',900,43200,'true');
INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `scope`, `authorized_grant_types`, `authorities`, `access_token_validity`, `refresh_token_validity`, `autoapprove`) VALUES ('pbx.gpsauriga.com','$2a$12$6bEkR5.PZfut4t8azXk9fOqrBQzC8eu/iavS.iQGZrWKXIOYQ.ADW','read,write,trust','authorization_code,password,refresh_token,implicit,client_credentials','ROLE_TRUSTED_CLIENT',900,43200,'true');

INSERT INTO `roles` (`idrole`, `rolename`) VALUES (1,'ROLE_ADMIN');
INSERT INTO `roles` (`idrole`, `rolename`) VALUES (2,'ROLE_MANAGER');
INSERT INTO `roles` (`idrole`, `rolename`) VALUES (3,'ROLE_OPERATOR');
INSERT INTO `roles` (`idrole`, `rolename`) VALUES (4,'ROLE_DRIVER');
INSERT INTO `roles` (`idrole`, `rolename`) VALUES (5,'ROLE_OWNER');

INSERT INTO `systems` VALUES (13,'Mercury Test');

INSERT INTO `userstates` (`iduserstate`, `description`) VALUES (0,'INACTIVE');
INSERT INTO `userstates` (`iduserstate`, `description`) VALUES (1,'ACTIVE');
INSERT INTO `userstates` (`iduserstate`, `description`) VALUES (2,'LOCKED');

INSERT INTO `users` (`iduser`, `username`, `password`, `idsystem`, `name`, `email`, `iduserstate`) VALUES (1,'admin','$2a$12$6bEkR5.PZfut4t8azXk9fOqrBQzC8eu/iavS.iQGZrWKXIOYQ.ADW',13,'Administrador', 'admin@aurigasystems.es',1);
INSERT INTO `users` (`iduser`, `username`, `password`, `idsystem`, `name`, `email`, `iduserstate`) VALUES (2,'asignador','$2a$12$6bEkR5.PZfut4t8azXk9fOqrBQzC8eu/iavS.iQGZrWKXIOYQ.ADW',13,'Asignador', 'asignador@aurigasystems.es',1);
INSERT INTO `users` (`iduser`, `username`, `password`, `idsystem`, `name`, `email`, `iduserstate`) VALUES (3,'operador','$2a$12$6bEkR5.PZfut4t8azXk9fOqrBQzC8eu/iavS.iQGZrWKXIOYQ.ADW',13,'Operador', 'operador@aurigasystems.es',1);
INSERT INTO `users` (`iduser`, `username`, `password`, `idsystem`, `name`, `email`, `iduserstate`) VALUES (4,'conductor','$2a$12$6bEkR5.PZfut4t8azXk9fOqrBQzC8eu/iavS.iQGZrWKXIOYQ.ADW',13,'Conductor', 'conductor@aurigasystems.es',1);

INSERT INTO `userdetails` (`detailkey`, `detailvalue`, `iduser`) VALUES ('key1', 'valor1', 1);
INSERT INTO `userdetails` (`detailkey`, `detailvalue`, `iduser`) VALUES ('key2', 'valor1', 1);
INSERT INTO `userdetails` (`detailkey`, `detailvalue`, `iduser`) VALUES ('key3', 'valor1', 1);
INSERT INTO `userdetails` (`detailkey`, `detailvalue`, `iduser`) VALUES ('key1', 'valor2', 2);
INSERT INTO `userdetails` (`detailkey`, `detailvalue`, `iduser`) VALUES ('key2', 'valor2', 2);
INSERT INTO `userdetails` (`detailkey`, `detailvalue`, `iduser`) VALUES ('key3', 'valor2', 2);
INSERT INTO `userdetails` (`detailkey`, `detailvalue`, `iduser`) VALUES ('key1', 'valor3', 3);
INSERT INTO `userdetails` (`detailkey`, `detailvalue`, `iduser`) VALUES ('key2', 'valor3', 3);
INSERT INTO `userdetails` (`detailkey`, `detailvalue`, `iduser`) VALUES ('key3', 'valor3', 3);

INSERT INTO `rolesperuser`  (`iduser`, `idrole`) VALUES (1,1);
INSERT INTO `rolesperuser`  (`iduser`, `idrole`) VALUES (1,2);
INSERT INTO `rolesperuser`  (`iduser`, `idrole`) VALUES (1,3);
INSERT INTO `rolesperuser`  (`iduser`, `idrole`) VALUES (2,2);
INSERT INTO `rolesperuser`  (`iduser`, `idrole`) VALUES (2,3);
INSERT INTO `rolesperuser`  (`iduser`, `idrole`) VALUES (3,3);
INSERT INTO `rolesperuser`  (`iduser`, `idrole`) VALUES (4,4);
