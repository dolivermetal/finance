begin;

drop table if exists finance."statement";
drop table if exists finance."transaction";
drop table if exists finance.account;
drop table if exists finance.credit_card;
drop table if exists finance.person;
drop table if exists finance.outbox;

create schema if not exists finance authorization finance_adm;

create table finance.outbox (
  idt_outbox bigserial not null,
  cod_outbox varchar(40) not null,
  nam_topic varchar not null,
  jsn_metadata text not null,
  ind_integration_status bpchar(1) null,
  dat_creation timestamp not null,
  dat_update timestamp not null,
  constraint outbox_pk primary key (idt_outbox),
  constraint outbox_uk01 unique (cod_outbox)
);

create table finance.person (
	idt_person bigserial not null,
	cod_person varchar(40) not null,
	nam_person varchar(50) not null,
	dat_creation timestamp not null,
	constraint person_pk primary key (idt_person),
	constraint person_uk unique (cod_person)
);

create table finance.account (
	idt_account bigserial not null,
	cod_account varchar(40) not null,
	nam_alias varchar(50) not null,
	idt_person bigint not null,
	dat_creation timestamp not null,
	constraint account_pk primary key (idt_account),
	constraint account_uk01 unique (cod_account),
	constraint account_fk01 foreign key (idt_person) references finance.person(idt_person)
);

create table finance.credit_card (
	idt_credit_card bigserial not null,
	cod_credit_card varchar(40) not null,
	nam_alias varchar(50) not null,
	nam_brand varchar(30) null,
	idt_person bigint not null,
	dat_creation timestamp not null,
	constraint credit_card_pk primary key (idt_credit_card),
	constraint credit_card_uk01 unique (cod_credit_card),
	constraint credit_card_fk01 foreign key (idt_person) references finance.person(idt_person)
);

create table finance."transaction" (
	idt_transaction bigserial not null,
	cod_transaction varchar(40) not null,
	dat_reference timestamp not null,
	num_amount numeric(19, 2) not null,
	ind_category varchar(50) not null,
	des_transaction varchar(100) not null,
	dat_creation timestamp not null,
	dat_update timestamp not null,
	constraint transaction_pk primary key (idt_transaction),
	constraint transaction_uk01 unique (cod_transaction)
);

create table finance."statement" (
	idt_statement bigserial not null,
	cod_statement varchar(40) not null,
	idt_account bigint null,
	idt_credit_card bigint null,
	idt_transaction bigint not null,
	num_balance numeric(19, 2) not null,
	dat_reference timestamp not null,
	dat_creation timestamp not null,
	constraint statement_pk primary key (idt_statement),
	constraint statement_uk01 unique (cod_statement),
	constraint statement_fk01 foreign key (idt_account) references finance.account(idt_account),
	constraint statement_fk02 foreign key (idt_transaction) references finance."transaction"(idt_transaction),
	constraint statement_fk03 foreign key (idt_credit_card) references finance.credit_card(idt_credit_card)
);


commit;
