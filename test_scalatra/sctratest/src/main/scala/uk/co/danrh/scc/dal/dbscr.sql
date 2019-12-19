
create table shares
(
	company_symbol CHAR(3) not null
		primary key,
	company_name VARCHAR,
	shares_available INT,
	last_update TIMESTAMP,
	currency CHAR(3),
	value FLOAT
);

create table users
(
	user_id VARCHAR not null
		constraint users_pk
			primary key,
	pwhash VARCHAR not null
);

create table usershares
(
	user_id VARCHAR not null
		references users,
	company_symbol CHAR(3)
		references shares,
	quantity INT default 0 not null,
	constraint usershares_pk
		primary key (user_id, company_symbol)
);

