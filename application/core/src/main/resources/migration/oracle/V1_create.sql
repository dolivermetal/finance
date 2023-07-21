/*########################################################################################################*/
-- +-----------------------------------------------------------------------------------------------------+--
--							realiza criação da tabela outbox
-- +-----------------------------------------------------------------------------------------------------+--
create sequence finance_adm.sq_outbox_idt
    maxvalue     999999999999999999
    minvalue     1
    start with   1
    increment by 1
    cache        50
    noorder;

create public synonym sq_outbox_idt for finance_adm.sq_outbox_idt;

create table finance_adm.outbox (
    idt_outbox                     number(18)      default finance_adm.sq_outbox_idt.nextval not null,
    cod_outbox                     varchar2(40)    not null,
    nam_topic                      varchar2(40)    not null,
    jsn_metadata                   clob            not null,
    ind_integration_status         varchar2(20)    not null,
    dat_creation                   timestamp       default systimestamp not null,
    dat_update                     timestamp       default systimestamp not null
  ) tablespace tsdfinance01 initrans 4;

create public synonym outbox for finance_adm.outbox;

alter table finance_adm.outbox
    add constraint outbox_pk primary key (idt_outbox) using index initrans 8 tablespace tsifinance01;

create unique index finance_adm.outbox_uk01 on finance_adm.outbox (cod_outbox) initrans 8 tablespace tsifinance01 online;
alter table finance_adm.outbox  add constraint outbox_uk01 unique (cod_outbox) using index finance_adm.outbox_uk01;


/*########################################################################################################*/
-- +-----------------------------------------------------------------------------------------------------+--
--							realiza criação da tabela person
-- +-----------------------------------------------------------------------------------------------------+--
create sequence finance_adm.sq_person_idt
    maxvalue     999999999999999999
    minvalue     1
    start with   1
    increment by 1
    cache        50
    noorder;

create public synonym sq_person_idt for finance_adm.sq_person_idt;

create table finance_adm.person (
    idt_person                     number(18)      default finance_adm.sq_person_idt.nextval not null,
    cod_person                     varchar2(40)    not null,
    nam_person                     varchar2(50)    not null,
    dat_creation                   timestamp       default systimestamp not null
  ) tablespace tsdfinance01 initrans 4;

create public synonym person for finance_adm.person;

alter table finance_adm.person
    add constraint person_pk primary key (idt_person) using index initrans 8 tablespace tsifinance01;

create unique index finance_adm.person_uk01 on finance_adm.person (cod_person) initrans 8 tablespace tsifinance01 online;
alter table finance_adm.person  add constraint person_uk01 unique (cod_person) using index finance_adm.person_uk01;


/*########################################################################################################*/
-- +-----------------------------------------------------------------------------------------------------+--
--							realiza criação da tabela account
-- +-----------------------------------------------------------------------------------------------------+--
create sequence finance_adm.sq_account_idt
    maxvalue     999999999999999999
    minvalue     1
    start with   1
    increment by 1
    cache        50
    noorder;

create public synonym sq_account_idt for finance_adm.sq_account_idt;

create table finance_adm.account (
    idt_account                    number(18)      default finance_adm.sq_account_idt.nextval not null,
    cod_account                    varchar2(40)    not null,
    nam_alias                      varchar2(50)    not null,
    idt_person                     number(18)      not null,
    dat_creation                   timestamp       default systimestamp not null
  ) tablespace tsdfinance01 initrans 4;

create public synonym account for finance_adm.account;

alter table finance_adm.account
    add constraint account_pk primary key (idt_account) using index initrans 8 tablespace tsifinance01;

alter table finance_adm.account
    add constraint person_account_fk foreign key (idt_person)
    references finance_adm.person (idt_person) enable novalidate;

create unique index finance_adm.account_uk01 on finance_adm.account (cod_account) initrans 8 tablespace tsifinance01 online;
alter table finance_adm.account  add constraint account_uk01 unique (cod_account) using index finance_adm.account_uk01;


/*########################################################################################################*/
-- +-----------------------------------------------------------------------------------------------------+--
--							realiza criação da tabela credit card
-- +-----------------------------------------------------------------------------------------------------+--
create sequence finance_adm.sq_credit_card_idt
    maxvalue     999999999999999999
    minvalue     1
    start with   1
    increment by 1
    cache        50
    noorder;

create public synonym sq_credit_card_idt for finance_adm.sq_credit_card_idt;

create table finance_adm.credit_card (
    idt_credit_card                number(18)      default finance_adm.sq_credit_card_idt.nextval not null,
    cod_credit_card                varchar2(40)    not null,
    nam_alias                      varchar2(50)    not null,
    nam_brand                      varchar2(30)    not null,
    idt_person                     number(18)      not null,
    dat_creation                   timestamp       default systimestamp not null
  ) tablespace tsdfinance01 initrans 4;

create public synonym credit_card for finance_adm.credit_card;

alter table finance_adm.credit_card
    add constraint credit_card_pk primary key (idt_credit_card) using index initrans 8 tablespace tsifinance01;

alter table finance_adm.credit_card
    add constraint person_credit_card_fk foreign key (idt_person)
    references finance_adm.person (idt_person) enable novalidate;

create unique index finance_adm.credit_card_uk01 on finance_adm.credit_card (cod_credit_card) initrans 8 tablespace tsifinance01 online;
alter table finance_adm.credit_card  add constraint credit_card_uk01 unique (cod_credit_card) using index finance_adm.credit_card_uk01;


/*########################################################################################################*/
-- +-----------------------------------------------------------------------------------------------------+--
--							realiza criação da tabela transaction
-- +-----------------------------------------------------------------------------------------------------+--
create sequence finance_adm.sq_transaction_idt
    maxvalue     999999999999999999
    minvalue     1
    start with   1
    increment by 1
    cache        50
    noorder;

create public synonym sq_transaction_idt for finance_adm.sq_transaction_idt;

create table finance_adm."TRANSACTION" (
    idt_transaction                number(18)      default finance_adm.sq_transaction_idt.nextval not null,
    cod_transaction                varchar2(40)    not null,
    dat_reference                  timestamp       not null,
    num_amount                     number(19,2)    not null,
    ind_category                   varchar2(50)    not null,
    des_transaction                varchar2(100)   not null,
    dat_creation                   timestamp       default systimestamp not null,
    dat_update                     timestamp       default systimestamp not null
  ) tablespace tsdfinance01 initrans 4;

create public synonym "TRANSACTION" for finance_adm."TRANSACTION";

alter table finance_adm."TRANSACTION"
    add constraint transaction_pk primary key (idt_transaction) using index initrans 8 tablespace tsifinance01;

create unique index finance_adm.transaction_uk01 on finance_adm.transaction (cod_transaction) initrans 8 tablespace tsifinance01 online;
alter table finance_adm.transaction  add constraint transaction_uk01 unique (cod_transaction) using index finance_adm.transaction_uk01;


/*########################################################################################################*/
-- +-----------------------------------------------------------------------------------------------------+--
--							realiza criação da tabela transaction
-- +-----------------------------------------------------------------------------------------------------+--
create sequence finance_adm.sq_statement_idt
    maxvalue     999999999999999999
    minvalue     1
    start with   1
    increment by 1
    cache        50
    noorder;

create public synonym sq_statement_idt for finance_adm.sq_statement_idt;

create table finance_adm."STATEMENT" (
    idt_statement                  number(18)      default finance_adm.sq_statement_idt.nextval not null,
    cod_statement                  varchar2(40)    not null,
    idt_account                    number(18)      null,
    idt_credit_card                number(18)      null,
    idt_transaction                number(18)      not null,
    num_balance                    number(19,2)    not null,
    dat_reference                  timestamp       not null,
    dat_creation                   timestamp       default systimestamp not null,
    dat_update                     timestamp       default systimestamp not null
  ) tablespace tsdfinance01 initrans 4;

create public synonym "STATEMENT" for finance_adm."STATEMENT";

alter table finance_adm."STATEMENT"
    add constraint statement_pk primary key (idt_statement) using index initrans 8 tablespace tsifinance01;

alter table finance_adm."STATEMENT"
    add constraint account_statement_fk foreign key (idt_account)
    references finance_adm.account (idt_account) enable novalidate;

alter table finance_adm."STATEMENT"
    add constraint transaction_statement_fk foreign key (idt_transaction)
    references finance_adm."TRANSACTION" (idt_transaction) enable novalidate;

alter table finance_adm."STATEMENT"
    add constraint credit_card_statement_fk foreign key (idt_credit_card)
    references finance_adm.credit_card (idt_credit_card) enable novalidate;

create unique index finance_adm.statement_uk01 on finance_adm.statement (cod_statement) initrans 8 tablespace tsifinance01 online;
alter table finance_adm.statement  add constraint statement_uk01 unique (cod_statement) using index finance_adm.statement_uk01;
