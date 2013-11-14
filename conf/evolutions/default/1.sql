# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table job_offer (
  id                        bigint not null,
  title                     varchar(255),
  description               varchar(255),
  num_vacants               integer,
  duration                  integer,
  work_type                 integer,
  salary                    varchar(255),
  emplacement               varchar(255),
  benefits                  varchar(255),
  expiration_date           timestamp,
  publisher_id              bigint,
  constraint ck_job_offer_duration check (duration in (0,1,2)),
  constraint ck_job_offer_work_type check (work_type in (0,1)),
  constraint pk_job_offer primary key (id))
;

create table user_app (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  name                      varchar(255),
  sec_name                  varchar(255),
  doc_id                    varchar(255),
  birth_date                timestamp,
  type                      integer,
  education                 varchar(255),
  experience                varchar(255),
  languages                 varchar(255),
  projects                  varchar(255),
  sector                    varchar(255),
  web                       varchar(255),
  description               varchar(255),
  constraint ck_user_app_type check (type in (0,1,2)),
  constraint pk_user_app primary key (id))
;

create sequence job_offer_seq;

create sequence user_app_seq;

alter table job_offer add constraint fk_job_offer_publisher_1 foreign key (publisher_id) references user_app (id) on delete restrict on update restrict;
create index ix_job_offer_publisher_1 on job_offer (publisher_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists job_offer;

drop table if exists user_app;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists job_offer_seq;

drop sequence if exists user_app_seq;

