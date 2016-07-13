use appstore;
INSERT INTO app_info (appid) value('D1');

ALTER TABLE user MODIFY COLUMN password VARCHAR(60);
update `user` set `password`='$2a$08$uG8nv6uGRytTHtJwnXm0CeoeXxqzpti2ZC3RycOvRPRq6YGC6hnXe' where `userid`='wuqun1';
