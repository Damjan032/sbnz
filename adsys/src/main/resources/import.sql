insert into user (email, password, first_name, last_name, enabled) values ('superadmin@mail.com', 'admin123', 'Super', 'Admin', true);

insert into advertiser (name) value ('Google');

insert into advertisement (budget, content, date_published, target_url, title, advertiser_id)value (1000.0, 'Ada', NOW(), 'google.com', 'google for u', 1)

insert into event (dtype, occurred_at, advertisement_id, user_id) value ('c', NOW(), 1, 1)
insert into event (dtype, occurred_at, advertisement_id, user_id) value ('v', NOW(), 1, 1)