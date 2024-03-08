insert into USERS (username, password, enabled)
    values ('user', 'password', true);
insert into USERS (username, password, enabled)
    values ('admin', 'password', true);

insert into AUTHORITIES(username, authority)
    values ('user', 'USER');
insert into AUTHORITIES(username, authority)
values ('admin', 'ADMIN');
