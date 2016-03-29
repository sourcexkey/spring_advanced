INSERT INTO events VALUES ( default,'event_title1','2015-05-10',50);
INSERT INTO events VALUES ( default,'event_title2','2015-05-11',70);
INSERT INTO events VALUES ( default,'event_title3','2015-05-12',100);

INSERT INTO users VALUES ( default,'admin','admin@mail.com');
INSERT INTO users VALUES ( default,'user','user@mail.com');
INSERT INTO users VALUES ( default,'user_name1','user_email1@mail.com');
INSERT INTO users VALUES ( default,'user_name2','user_email2@mail.com');

INSERT INTO users_details VALUES ( 1,'$2a$10$Gcu83OJctU2ymh.MP0/TdeJ3HbI9uniB8kg2d4GmpSZgoxz08uWGm',TRUE,TRUE,TRUE,TRUE,'ROLE_REGISTERED_USER,ROLE_BOOKING_MANAGER');
INSERT INTO users_details VALUES ( 2,'$2a$10$L5CLVF6Ef0pihV5DNLHOqOTvVv7zZxA82hh9HLFXs3Vq0bZyx3AKK',TRUE,TRUE,TRUE,TRUE,default);

INSERT INTO user_accounts VALUES ( default,1,1000);
INSERT INTO user_accounts VALUES ( default,2,500);
INSERT INTO user_accounts VALUES ( default,3,700);

INSERT INTO tickets VALUES (default, 1,1,'BAR',1);
INSERT INTO tickets VALUES (default, 1,1,'BAR',2);
INSERT INTO tickets VALUES (default, 1,1,'BAR',3);
INSERT INTO tickets VALUES (default, 1,1,'BAR',4);

INSERT INTO tickets VALUES (default, 2,2,'BAR',1);
INSERT INTO tickets VALUES (default, 1,2,'BAR',2);
INSERT INTO tickets VALUES (default, 1,2,'BAR',3);

INSERT INTO tickets VALUES (default, 3,3,'PREMIUM',100);