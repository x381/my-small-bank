drop table if exists account CASCADE;
drop table if exists holder CASCADE;

create table account (
	uid varchar(255) not null,
	type varchar(255),
	balance real,
	holder_id varchar(255) not null,
	primary key (uid)
);
	
create table holder (
	id varchar(255) not null,
	birth_date date,
	city varchar(255),
	country varchar(255),
	first_name varchar(255),
	last_name varchar(255),
	street varchar(255),
	zip_code varchar(255),
	primary key (id)
);

alter table account
add constraint FK_holder
foreign key (holder_id)
references holder;


drop table if exists transfer CASCADE;

create table transfer (
	id varchar(255) not null,
	account_from varchar(255) not null,
	account_to varchar(255) not null,
	amount real,
	execution_date timestamp,
	primary key(id)
);

alter table transfer
add constraint FK_account_from
foreign key (account_from)
references account;

alter table transfer
add constraint FK_account_to
foreign key (account_to)
references account;