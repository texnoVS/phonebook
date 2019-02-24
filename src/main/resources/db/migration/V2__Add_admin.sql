insert into usr (id, username, password, active, email)
  values (1, 'admin', '$2a$08$aQAsaiA1Oue8gLuulSN8U.ZszpW8S.mpivKYnD6f1HsoUX.dHbUXO', true, 'some@some.com');
# login: admin
# pass: 12

insert into user_role (user_id, roles)
  values (1, 'USER'), (1, 'ADMIN');