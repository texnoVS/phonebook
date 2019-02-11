insert into usr (id, username, password, active, email)
  values (1, 'admin', '$2a$08$g.gZTLScHZ7BMGVHEkiPtOVleyJi5mLagbwvgIsrwtKOkVAg.3Qyq', true, 'some@some.com');

insert into user_role (user_id, roles)
  values (1, 'USER'), (1, 'ADMIN');