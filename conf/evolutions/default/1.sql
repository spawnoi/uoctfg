# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table duration (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_duration primary key (id))
;

create table experience_level (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_experience_level primary key (id))
;

create table job_offer (
  id                        bigint not null,
  title                     varchar(255),
  description               varchar(255),
  num_vacants               integer,
  duration_id               bigint,
  worktype_id               bigint,
  salary                    varchar(255),
  province_id               bigint,
  emplacement               varchar(255),
  benefits                  varchar(255),
  sector_id                 bigint,
  publication_date          timestamp,
  expiration_date           timestamp,
  publisher_id              bigint,
  constraint pk_job_offer primary key (id))
;

create table province (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_province primary key (id))
;

create table sector (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_sector primary key (id))
;

create table studies_level (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_studies_level primary key (id))
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

create table worktype (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_worktype primary key (id))
;


create table job_offer_user_app (
  job_offer_id                   bigint not null,
  user_app_id                    bigint not null,
  constraint pk_job_offer_user_app primary key (job_offer_id, user_app_id))
;
create sequence duration_seq;

create sequence experience_level_seq;

create sequence job_offer_seq;

create sequence province_seq;

create sequence sector_seq;

create sequence studies_level_seq;

create sequence user_app_seq;

create sequence worktype_seq;

alter table job_offer add constraint fk_job_offer_duration_1 foreign key (duration_id) references duration (id) on delete restrict on update restrict;
create index ix_job_offer_duration_1 on job_offer (duration_id);
alter table job_offer add constraint fk_job_offer_worktype_2 foreign key (worktype_id) references worktype (id) on delete restrict on update restrict;
create index ix_job_offer_worktype_2 on job_offer (worktype_id);
alter table job_offer add constraint fk_job_offer_province_3 foreign key (province_id) references province (id) on delete restrict on update restrict;
create index ix_job_offer_province_3 on job_offer (province_id);
alter table job_offer add constraint fk_job_offer_sector_4 foreign key (sector_id) references sector (id) on delete restrict on update restrict;
create index ix_job_offer_sector_4 on job_offer (sector_id);
alter table job_offer add constraint fk_job_offer_publisher_5 foreign key (publisher_id) references user_app (id) on delete restrict on update restrict;
create index ix_job_offer_publisher_5 on job_offer (publisher_id);



alter table job_offer_user_app add constraint fk_job_offer_user_app_job_off_01 foreign key (job_offer_id) references job_offer (id) on delete restrict on update restrict;

alter table job_offer_user_app add constraint fk_job_offer_user_app_user_ap_02 foreign key (user_app_id) references user_app (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists duration;

drop table if exists experience_level;

drop table if exists job_offer;

drop table if exists job_offer_user_app;

drop table if exists province;

drop table if exists sector;

drop table if exists studies_level;

drop table if exists user_app;

drop table if exists worktype;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists duration_seq;

drop sequence if exists experience_level_seq;

drop sequence if exists job_offer_seq;

drop sequence if exists province_seq;

drop sequence if exists sector_seq;

drop sequence if exists studies_level_seq;

drop sequence if exists user_app_seq;

drop sequence if exists worktype_seq;

