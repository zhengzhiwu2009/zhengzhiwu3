prompt PL/SQL Developer import file
prompt Created on 2013年1月30日 by Administrator
set feedback off
set define off
prompt Dropping STATISTIC_COURSE_YEAR...
drop table STATISTIC_COURSE_YEAR cascade constraints;
prompt Creating STATISTIC_COURSE_YEAR...
create table STATISTIC_COURSE_YEAR
(
  YEAR                          VARCHAR2(4) not null,
  ELECTIVE_PERSON_NUMBER        NUMBER(9),
  START_STUDY_PERSON_NUMBER     NUMBER(9),
  COMPLETED_STUDY_PERSON_NUMBER NUMBER(9),
  START_EXAM_PERSON_NUMBER      NUMBER(9),
  PASS_EXAM_PERSON_NUMBER       NUMBER(9),
  PRE_START_STUDY_NUMBER        NUMBER(9),
  PRE_COMPLETED__STUDY_NUMBER   NUMBER(9),
  PRE_START_EXAM_NUMBER         NUMBER(9),
  PRE_PASS_EXAM_NUMBER          NUMBER(9),
  UPDATE_TIME                   DATE,
  PRE_COURSE_TIMES              NUMBER(9),
  PRE_PASS_TJMES                NUMBER(9),
  PRE_PASS_COM_TEN_HOURS        NUMBER(9),
  PRE_PASS_COM_FIFTEEN_HOURS    NUMBER(9),
  PASS_COM_TEN_HOURS            NUMBER(9),
  PASS_COM_FIFTEEN_HOURS        NUMBER(9),
  PASS_TJMES                    NUMBER(9)
)
tablespace HZPH
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table STATISTIC_COURSE_YEAR
  is '跨年统计';
comment on column STATISTIC_COURSE_YEAR.ELECTIVE_PERSON_NUMBER
  is '选课人数';
comment on column STATISTIC_COURSE_YEAR.START_STUDY_PERSON_NUMBER
  is '开始学习人数';
comment on column STATISTIC_COURSE_YEAR.COMPLETED_STUDY_PERSON_NUMBER
  is '完成学习人数';
comment on column STATISTIC_COURSE_YEAR.START_EXAM_PERSON_NUMBER
  is '开始测验人数
';
comment on column STATISTIC_COURSE_YEAR.PASS_EXAM_PERSON_NUMBER
  is '测验通过人数
';
comment on column STATISTIC_COURSE_YEAR.PRE_START_STUDY_NUMBER
  is '非今年报名学习人数
';
comment on column STATISTIC_COURSE_YEAR.PRE_COMPLETED__STUDY_NUMBER
  is '非今年报名学习完成人数
';
comment on column STATISTIC_COURSE_YEAR.PRE_START_EXAM_NUMBER
  is '非今年报名测验人数
';
comment on column STATISTIC_COURSE_YEAR.PRE_PASS_EXAM_NUMBER
  is '非今年报名测验通过人数
';
comment on column STATISTIC_COURSE_YEAR.UPDATE_TIME
  is '数据更新日期';
comment on column STATISTIC_COURSE_YEAR.PRE_COURSE_TIMES
  is '去年报名，今年考试学时总数';
comment on column STATISTIC_COURSE_YEAR.PRE_PASS_TJMES
  is '去年报名，今年考试通过总学时数';
comment on column STATISTIC_COURSE_YEAR.PRE_PASS_COM_TEN_HOURS
  is '去年报名今年完成10个必修学时人数';
comment on column STATISTIC_COURSE_YEAR.PRE_PASS_COM_FIFTEEN_HOURS
  is '去年报名今年完成15个必修学时人数';
comment on column STATISTIC_COURSE_YEAR.PASS_COM_TEN_HOURS
  is '完成10个必修学时人数';
comment on column STATISTIC_COURSE_YEAR.PASS_COM_FIFTEEN_HOURS
  is '完成15个必修学时人数';
comment on column STATISTIC_COURSE_YEAR.PASS_TJMES
  is '完成学时数';
alter table STATISTIC_COURSE_YEAR
  add constraint STATISTIC_COURSE_YEAR_PK primary key (YEAR)
  using index 
  tablespace HZPH
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for STATISTIC_COURSE_YEAR...
alter table STATISTIC_COURSE_YEAR disable all triggers;
prompt Loading STATISTIC_COURSE_YEAR...
insert into STATISTIC_COURSE_YEAR (YEAR, ELECTIVE_PERSON_NUMBER, START_STUDY_PERSON_NUMBER, COMPLETED_STUDY_PERSON_NUMBER, START_EXAM_PERSON_NUMBER, PASS_EXAM_PERSON_NUMBER, PRE_START_STUDY_NUMBER, PRE_COMPLETED__STUDY_NUMBER, PRE_START_EXAM_NUMBER, PRE_PASS_EXAM_NUMBER, UPDATE_TIME, PRE_COURSE_TIMES, PRE_PASS_TJMES, PRE_PASS_COM_TEN_HOURS, PRE_PASS_COM_FIFTEEN_HOURS, PASS_COM_TEN_HOURS, PASS_COM_FIFTEEN_HOURS, PASS_TJMES)
values ('2012', null, 1719142, 1709665, 1698749, 1691173, 0, 0, 4, 2, null, null, 5, 0, null, 218023, 132442, 30259945);
insert into STATISTIC_COURSE_YEAR (YEAR, ELECTIVE_PERSON_NUMBER, START_STUDY_PERSON_NUMBER, COMPLETED_STUDY_PERSON_NUMBER, START_EXAM_PERSON_NUMBER, PASS_EXAM_PERSON_NUMBER, PRE_START_STUDY_NUMBER, PRE_COMPLETED__STUDY_NUMBER, PRE_START_EXAM_NUMBER, PRE_PASS_EXAM_NUMBER, UPDATE_TIME, PRE_COURSE_TIMES, PRE_PASS_TJMES, PRE_PASS_COM_TEN_HOURS, PRE_PASS_COM_FIFTEEN_HOURS, PASS_COM_TEN_HOURS, PASS_COM_FIFTEEN_HOURS, PASS_TJMES)
values ('2011', null, 1419819, 1410607, 1402630, 1392724, 5446, 4637, 4897, 5296, null, null, 8466, 200, null, 194183, 114895, 2698232);
insert into STATISTIC_COURSE_YEAR (YEAR, ELECTIVE_PERSON_NUMBER, START_STUDY_PERSON_NUMBER, COMPLETED_STUDY_PERSON_NUMBER, START_EXAM_PERSON_NUMBER, PASS_EXAM_PERSON_NUMBER, PRE_START_STUDY_NUMBER, PRE_COMPLETED__STUDY_NUMBER, PRE_START_EXAM_NUMBER, PRE_PASS_EXAM_NUMBER, UPDATE_TIME, PRE_COURSE_TIMES, PRE_PASS_TJMES, PRE_PASS_COM_TEN_HOURS, PRE_PASS_COM_FIFTEEN_HOURS, PASS_COM_TEN_HOURS, PASS_COM_FIFTEEN_HOURS, PASS_TJMES)
values ('2010', null, 1002854, 994714, 988823, 980907, 1203, 1540, 1735, 2055, null, null, 3291, 160, null, 128441, 75791, 1811105);
insert into STATISTIC_COURSE_YEAR (YEAR, ELECTIVE_PERSON_NUMBER, START_STUDY_PERSON_NUMBER, COMPLETED_STUDY_PERSON_NUMBER, START_EXAM_PERSON_NUMBER, PASS_EXAM_PERSON_NUMBER, PRE_START_STUDY_NUMBER, PRE_COMPLETED__STUDY_NUMBER, PRE_START_EXAM_NUMBER, PRE_PASS_EXAM_NUMBER, UPDATE_TIME, PRE_COURSE_TIMES, PRE_PASS_TJMES, PRE_PASS_COM_TEN_HOURS, PRE_PASS_COM_FIFTEEN_HOURS, PASS_COM_TEN_HOURS, PASS_COM_FIFTEEN_HOURS, PASS_TJMES)
values ('2009', null, 638802, 634027, 630537, 626091, 2541, 3833, 3309, 3480, null, null, 6233, 263, null, 88685, 48602, 1208955);
insert into STATISTIC_COURSE_YEAR (YEAR, ELECTIVE_PERSON_NUMBER, START_STUDY_PERSON_NUMBER, COMPLETED_STUDY_PERSON_NUMBER, START_EXAM_PERSON_NUMBER, PASS_EXAM_PERSON_NUMBER, PRE_START_STUDY_NUMBER, PRE_COMPLETED__STUDY_NUMBER, PRE_START_EXAM_NUMBER, PRE_PASS_EXAM_NUMBER, UPDATE_TIME, PRE_COURSE_TIMES, PRE_PASS_TJMES, PRE_PASS_COM_TEN_HOURS, PRE_PASS_COM_FIFTEEN_HOURS, PASS_COM_TEN_HOURS, PASS_COM_FIFTEEN_HOURS, PASS_TJMES)
values ('2008', null, 501382, 497063, 496049, 494546, 0, 0, 0, 0, null, null, null, null, null, 69217, 38360, 928166);
commit;
prompt 5 records loaded
prompt Enabling triggers for STATISTIC_COURSE_YEAR...
alter table STATISTIC_COURSE_YEAR enable all triggers;
set feedback on
set define on
prompt Done.
