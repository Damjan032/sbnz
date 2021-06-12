INSERT INTO authority (name) VALUES ('ADMIN');
INSERT INTO authority (name) VALUES ('USER');
--
insert into user (id, email, password, first_name, last_name, enabled) values (1,'superadmin@mail.com', '$2a$10$sfOM0tcraqVA6/3zfgS9HuiX7j41RYNBF/Wsm96xnhDFICeM1CR2O', 'Super', 'Admin', true);
insert into user_authority (user_id, authority_id) values (1, 1);

insert into advertiser (name) value ('Google');

insert into advertisement (budget, content, date_published, target_url, title, advertiser_id)value (1000.0, 'Ada', NOW(), 'google.com', 'google for u', 1)

insert into event (dtype, occurred_at, advertisement_id, user_id) value ('c', NOW(), 1, 1)
insert into event (dtype, occurred_at, advertisement_id, user_id) value ('v', NOW(), 1, 1)