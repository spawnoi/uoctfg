# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table job_offer (
  id                        bigint not null,
  description               varchar(255),
  expiration_date           timestamp,
  publisher_id              bigint,
  constraint pk_job_offer primary key (id))
;

create table user (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  name                      varchar(255),
  sec_name                  varchar(255),
  birth_date                timestamp,
  constraint pk_user primary key (id))
;

create sequence job_offer_seq;

create sequence user_seq;

alter table job_offer add constraint fk_job_offer_publisher_1 foreign key (publisher_id) references user (id) on delete restrict on update restrict;
create index ix_job_offer_publisher_1 on job_offer (publisher_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists job_offer;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists job_offer_seq;

drop sequence if exists user_seq;

