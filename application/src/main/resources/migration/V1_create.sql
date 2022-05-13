create schema if not exists finance authorization finance_adm;

drop table finance.outbox;
create table finance.outbox (
  idt_outbox bigserial not null,
  jsn_metadata text not null,
  jsn_metadata_text text not null,
  jsn_metadata_byte bytea not null,
  ind_integration_status bpchar(1) NULL,
  dat_creation timestamp not null,
  constraint outbox_pk primary key (idt_outbox)
);
