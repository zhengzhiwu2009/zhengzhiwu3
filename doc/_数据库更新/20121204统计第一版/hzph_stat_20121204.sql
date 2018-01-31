prompt PL/SQL Developer import file
prompt Created on 2012年12月4日 by Administrator
set feedback off
set define off
prompt Creating STATISTICSUM...
create table STATISTICSUM
(
  id                     NVARCHAR2(50),
  year                   NVARCHAR2(4) default to_char(sysdate,'yyyy'),
  month                  NVARCHAR2(2) default to_char(sysdate,'MM'),
  day                    NVARCHAR2(2) default to_char(sysdate,'dd'),
  fk_regtype_id          NVARCHAR2(50),
  reg_enterprise_num     NUMBER(10),
  reg_user_num           NUMBER(10),
  entry_user_num         NUMBER(10),
  entry_user_times       NUMBER(10),
  entry_courehours       NUMBER(10),
  fee_amount             NUMBER(10,2),
  refund_num             NUMBER(10),
  refund_amount          NUMBER(10,2),
  overyear_refund_num    NUMBER(10),
  overyear_refund_amount NUMBER(10,2),
  study_begin_times      NUMBER(10),
  study_complete_times   NUMBER(10),
  test_begin_times       NUMBER(10),
  test_complete_times    NUMBER(10),
  create_date            DATE default sysdate,
  update_date            DATE,
  reg_enterprise_count   NUMBER(10),
  reg_user_count         NUMBER(10)
)
tablespace HZPH
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table STATISTICSUM
  is '综合统计-按机构类别';
comment on column STATISTICSUM.id
  is 'ID';
comment on column STATISTICSUM.year
  is '年';
comment on column STATISTICSUM.month
  is '月';
comment on column STATISTICSUM.day
  is '日';
comment on column STATISTICSUM.fk_regtype_id
  is '注册类别';
comment on column STATISTICSUM.reg_enterprise_num
  is '注册机构数';
comment on column STATISTICSUM.reg_user_num
  is '注册人数';
comment on column STATISTICSUM.entry_user_num
  is '报名人数';
comment on column STATISTICSUM.entry_user_times
  is '报名人次';
comment on column STATISTICSUM.entry_courehours
  is '报名学时数';
comment on column STATISTICSUM.fee_amount
  is '付费金额';
comment on column STATISTICSUM.refund_num
  is '退费人数';
comment on column STATISTICSUM.refund_amount
  is '退费金额';
comment on column STATISTICSUM.overyear_refund_num
  is '跨年退人数';
comment on column STATISTICSUM.overyear_refund_amount
  is '跨年退金额';
comment on column STATISTICSUM.study_begin_times
  is '开始学习人次';
comment on column STATISTICSUM.study_complete_times
  is '完成学习人次';
comment on column STATISTICSUM.test_begin_times
  is '开始测验人次';
comment on column STATISTICSUM.test_complete_times
  is '完成测验人次';
comment on column STATISTICSUM.reg_enterprise_count
  is '注册机构数累计';
comment on column STATISTICSUM.reg_user_count
  is '注册人数累计';

prompt Creating STATISTIC_COURSE...
create table STATISTIC_COURSE
(
  id                     NVARCHAR2(50),
  year                   NVARCHAR2(4) default to_char(sysdate,'yyyy'),
  month                  NVARCHAR2(2) default to_char(sysdate,'MM'),
  day                    NVARCHAR2(2) default to_char(sysdate,'dd'),
  fk_course_id           NVARCHAR2(50),
  reg_enterprise_num     NUMBER(10),
  reg_user_num           NUMBER(10),
  entry_user_num         NUMBER(10),
  entry_user_times       NUMBER(10),
  entry_courehours       NUMBER(10),
  fee_amount             NUMBER(10,2),
  refund_num             NUMBER(10),
  refund_amount          NUMBER(10,2),
  overyear_refund_num    NUMBER(10),
  overyear_refund_amount NUMBER(10,2),
  study_begin_times      NUMBER(10),
  study_complete_times   NUMBER(10),
  test_begin_times       NUMBER(10),
  test_complete_times    NUMBER(10),
  create_date            DATE default sysdate,
  update_date            DATE
)
tablespace HZPH
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table STATISTIC_COURSE
  is '综合统计-按课程名称';
comment on column STATISTIC_COURSE.id
  is 'ID';
comment on column STATISTIC_COURSE.year
  is '年';
comment on column STATISTIC_COURSE.month
  is '月';
comment on column STATISTIC_COURSE.day
  is '日';
comment on column STATISTIC_COURSE.fk_course_id
  is '注册类别';
comment on column STATISTIC_COURSE.reg_enterprise_num
  is '注册机构数量';
comment on column STATISTIC_COURSE.reg_user_num
  is '注册人数';
comment on column STATISTIC_COURSE.entry_user_num
  is '报名人数';
comment on column STATISTIC_COURSE.entry_user_times
  is '报名人次';
comment on column STATISTIC_COURSE.entry_courehours
  is '报名学时数';
comment on column STATISTIC_COURSE.fee_amount
  is '付费金额';
comment on column STATISTIC_COURSE.refund_num
  is '退费人数';
comment on column STATISTIC_COURSE.refund_amount
  is '退费金额';
comment on column STATISTIC_COURSE.overyear_refund_num
  is '跨年退人数';
comment on column STATISTIC_COURSE.overyear_refund_amount
  is '跨年退金额';
comment on column STATISTIC_COURSE.study_begin_times
  is '开始学习人次';
comment on column STATISTIC_COURSE.study_complete_times
  is '完成学习人次';
comment on column STATISTIC_COURSE.test_begin_times
  is '开始测验人次';
comment on column STATISTIC_COURSE.test_complete_times
  is '完成测验人次';

prompt Creating STATISTIC_ENTERPRISE...
create table STATISTIC_ENTERPRISE
(
  id                     NVARCHAR2(50),
  year                   NVARCHAR2(4) default to_char(sysdate,'yyyy'),
  month                  NVARCHAR2(2) default to_char(sysdate,'MM'),
  day                    NVARCHAR2(2) default to_char(sysdate,'dd'),
  fk_enterprise_id       NVARCHAR2(50),
  reg_enterprise_num     NUMBER(10),
  reg_user_num           NUMBER(10),
  entry_user_num         NUMBER(10),
  entry_user_times       NUMBER(10),
  entry_courehours       NUMBER(10),
  fee_amount             NUMBER(10,2),
  refund_num             NUMBER(10),
  refund_amount          NUMBER(10,2),
  overyear_refund_num    NUMBER(10),
  overyear_refund_amount NUMBER(10,2),
  study_begin_times      NUMBER(10),
  study_complete_times   NUMBER(10),
  test_begin_times       NUMBER(10),
  test_complete_times    NUMBER(10),
  create_date            DATE default sysdate,
  update_date            DATE
)
tablespace HZPH
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table STATISTIC_ENTERPRISE
  is '综合统计-按机构名称';
comment on column STATISTIC_ENTERPRISE.id
  is 'ID';
comment on column STATISTIC_ENTERPRISE.year
  is '年';
comment on column STATISTIC_ENTERPRISE.month
  is '月';
comment on column STATISTIC_ENTERPRISE.day
  is '日';
comment on column STATISTIC_ENTERPRISE.fk_enterprise_id
  is '机构ID';
comment on column STATISTIC_ENTERPRISE.reg_enterprise_num
  is '注册机构数量';
comment on column STATISTIC_ENTERPRISE.reg_user_num
  is '注册人数';
comment on column STATISTIC_ENTERPRISE.entry_user_num
  is '报名人数';
comment on column STATISTIC_ENTERPRISE.entry_user_times
  is '报名人次';
comment on column STATISTIC_ENTERPRISE.entry_courehours
  is '报名学时数';
comment on column STATISTIC_ENTERPRISE.fee_amount
  is '付费金额';
comment on column STATISTIC_ENTERPRISE.refund_num
  is '退费人数';
comment on column STATISTIC_ENTERPRISE.refund_amount
  is '退费金额';
comment on column STATISTIC_ENTERPRISE.overyear_refund_num
  is '跨年退人数';
comment on column STATISTIC_ENTERPRISE.overyear_refund_amount
  is '跨年退金额';
comment on column STATISTIC_ENTERPRISE.study_begin_times
  is '开始学习人次';
comment on column STATISTIC_ENTERPRISE.study_complete_times
  is '完成学习人次';
comment on column STATISTIC_ENTERPRISE.test_begin_times
  is '开始测验人次';
comment on column STATISTIC_ENTERPRISE.test_complete_times
  is '完成测验人次';

prompt Creating STATISTIC_ENTERPRISE_TYPE...
create table STATISTIC_ENTERPRISE_TYPE
(
  id                     NVARCHAR2(50) not null,
  year                   NVARCHAR2(4) default to_char(sysdate,'yyyy'),
  month                  NVARCHAR2(2) default to_char(sysdate,'MM'),
  day                    NVARCHAR2(2) default to_char(sysdate,'dd'),
  fk_enterprisetype_id   NVARCHAR2(50),
  reg_enterprise_num     NUMBER(10),
  reg_user_num           NUMBER(10),
  entry_user_num         NUMBER(10),
  entry_user_times       NUMBER(10),
  entry_courehours       NUMBER(10),
  fee_amount             NUMBER(10,2),
  refund_num             NUMBER(10),
  refund_amount          NUMBER(10,2),
  overyear_refund_num    NUMBER(10),
  overyear_refund_amount NUMBER(10,2),
  study_begin_times      NUMBER(10),
  study_complete_times   NUMBER(10),
  test_begin_times       NUMBER(10),
  test_complete_times    NUMBER(10),
  create_date            DATE default sysdate,
  update_date            DATE,
  reg_enterprise_count   NUMBER(10),
  reg_user_count         NUMBER(10)
)
tablespace HZPH
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table STATISTIC_ENTERPRISE_TYPE
  is '综合统计-按机构类别';
comment on column STATISTIC_ENTERPRISE_TYPE.id
  is 'ID';
comment on column STATISTIC_ENTERPRISE_TYPE.year
  is '年';
comment on column STATISTIC_ENTERPRISE_TYPE.month
  is '月';
comment on column STATISTIC_ENTERPRISE_TYPE.day
  is '日';
comment on column STATISTIC_ENTERPRISE_TYPE.fk_enterprisetype_id
  is '机构类别';
comment on column STATISTIC_ENTERPRISE_TYPE.reg_enterprise_num
  is '注册机构数量';
comment on column STATISTIC_ENTERPRISE_TYPE.reg_user_num
  is '注册人数';
comment on column STATISTIC_ENTERPRISE_TYPE.entry_user_num
  is '报名人数';
comment on column STATISTIC_ENTERPRISE_TYPE.entry_user_times
  is '报名人次';
comment on column STATISTIC_ENTERPRISE_TYPE.entry_courehours
  is '报名学时数';
comment on column STATISTIC_ENTERPRISE_TYPE.fee_amount
  is '付费金额';
comment on column STATISTIC_ENTERPRISE_TYPE.refund_num
  is '退费人数';
comment on column STATISTIC_ENTERPRISE_TYPE.refund_amount
  is '退费金额';
comment on column STATISTIC_ENTERPRISE_TYPE.overyear_refund_num
  is '跨年退人数';
comment on column STATISTIC_ENTERPRISE_TYPE.overyear_refund_amount
  is '跨年退金额';
comment on column STATISTIC_ENTERPRISE_TYPE.study_begin_times
  is '开始学习人次';
comment on column STATISTIC_ENTERPRISE_TYPE.study_complete_times
  is '完成学习人次';
comment on column STATISTIC_ENTERPRISE_TYPE.test_begin_times
  is '开始测验人次';
comment on column STATISTIC_ENTERPRISE_TYPE.test_complete_times
  is '完成测验人次';

prompt Creating STATISTIC_YEAR...
create table STATISTIC_YEAR
(
  id                          NVARCHAR2(50),
  year                        NVARCHAR2(4) default to_char(sysdate,'yyyy'),
  month                       NVARCHAR2(2) default to_char(sysdate,'MM'),
  reg_enterprise_num          NUMBER(10),
  reg_user_num                NUMBER(10),
  reg_enterprise_growth       NVARCHAR2(10),
  reg_user_growth             NVARCHAR2(10),
  entry_user_num              NUMBER(10),
  entry_user_times            NUMBER(10),
  entry_courehours            NUMBER(10),
  fee_amount                  NUMBER(10,2),
  entry_user_growth           NVARCHAR2(10),
  entry_user_times_growth     NVARCHAR2(10),
  entry_courehours_growth     NVARCHAR2(10),
  fee_amount_growth           NVARCHAR2(10),
  refund_num                  NUMBER(10),
  refund_amount               NUMBER(10,2),
  overyear_refund_num         NUMBER(10),
  overyear_refund_amount      NUMBER(10,2),
  study_begin_times           NUMBER(10),
  study_complete_times        NUMBER(10),
  study_begin_rate            NUMBER(10,2),
  study_complete_rate         NUMBER(10,2),
  study_begin_times_growth    NVARCHAR2(10),
  study_complete_times_growth NVARCHAR2(10),
  test_begin_times            NUMBER(10),
  test_complete_times         NUMBER(10),
  test_begin_times_growth     NVARCHAR2(10),
  test_complete_times_growth  NVARCHAR2(10),
  test_complete_rate          NVARCHAR2(10),
  create_date                 DATE,
  update_date                 DATE,
  reg_enterprise_count        NUMBER(10),
  reg_user_count              NUMBER(10)
)
tablespace HZPH
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table STATISTIC_YEAR
  is '综合统计-年度汇总';
comment on column STATISTIC_YEAR.id
  is 'ID';
comment on column STATISTIC_YEAR.year
  is '年';
comment on column STATISTIC_YEAR.month
  is '月';
comment on column STATISTIC_YEAR.reg_enterprise_num
  is '注册机构数';
comment on column STATISTIC_YEAR.reg_user_num
  is '注册用户数';
comment on column STATISTIC_YEAR.reg_enterprise_growth
  is '注册机构数累计同比';
comment on column STATISTIC_YEAR.reg_user_growth
  is '注册用户数累计同比';
comment on column STATISTIC_YEAR.entry_user_num
  is '报名用户数';
comment on column STATISTIC_YEAR.entry_user_times
  is '报名用户人次';
comment on column STATISTIC_YEAR.entry_courehours
  is '报名总学时';
comment on column STATISTIC_YEAR.fee_amount
  is '缴费总额';
comment on column STATISTIC_YEAR.entry_user_growth
  is '报名用户同比';
comment on column STATISTIC_YEAR.entry_user_times_growth
  is '报名人次同比';
comment on column STATISTIC_YEAR.entry_courehours_growth
  is '报名学时同比';
comment on column STATISTIC_YEAR.fee_amount_growth
  is '缴费总额同比';
comment on column STATISTIC_YEAR.refund_num
  is '退费次数';
comment on column STATISTIC_YEAR.refund_amount
  is '退费总额';
comment on column STATISTIC_YEAR.overyear_refund_num
  is '跨年退费数';
comment on column STATISTIC_YEAR.overyear_refund_amount
  is '跨年退费总额';
comment on column STATISTIC_YEAR.study_begin_times
  is '开始学习人次';
comment on column STATISTIC_YEAR.study_complete_times
  is '学习完成人次';
comment on column STATISTIC_YEAR.study_begin_rate
  is '学习开始比率';
comment on column STATISTIC_YEAR.study_complete_rate
  is '学习完成比率';
comment on column STATISTIC_YEAR.study_begin_times_growth
  is '学习开始人次同比';
comment on column STATISTIC_YEAR.study_complete_times_growth
  is '学习完成人次同比';
comment on column STATISTIC_YEAR.test_begin_times
  is '测验开始人次';
comment on column STATISTIC_YEAR.test_complete_times
  is '测验完成人次';
comment on column STATISTIC_YEAR.test_begin_times_growth
  is '测验开始人次同比';
comment on column STATISTIC_YEAR.test_complete_times_growth
  is '测验完成人次同比';
comment on column STATISTIC_YEAR.test_complete_rate
  is '测验完成比率';
comment on column STATISTIC_YEAR.create_date
  is '创建时间';
comment on column STATISTIC_YEAR.update_date
  is '更新时间';
comment on column STATISTIC_YEAR.reg_enterprise_count
  is '注册机构数累计';
comment on column STATISTIC_YEAR.reg_user_count
  is '注册用户数累计';

prompt Disabling triggers for STATISTICSUM...
alter table STATISTICSUM disable all triggers;
prompt Disabling triggers for STATISTIC_COURSE...
alter table STATISTIC_COURSE disable all triggers;
prompt Disabling triggers for STATISTIC_ENTERPRISE...
alter table STATISTIC_ENTERPRISE disable all triggers;
prompt Disabling triggers for STATISTIC_ENTERPRISE_TYPE...
alter table STATISTIC_ENTERPRISE_TYPE disable all triggers;
prompt Disabling triggers for STATISTIC_YEAR...
alter table STATISTIC_YEAR disable all triggers;
prompt Deleting STATISTIC_YEAR...
delete from STATISTIC_YEAR;
commit;
prompt Deleting STATISTIC_ENTERPRISE_TYPE...
delete from STATISTIC_ENTERPRISE_TYPE;
commit;
prompt Deleting STATISTIC_ENTERPRISE...
delete from STATISTIC_ENTERPRISE;
commit;
prompt Deleting STATISTIC_COURSE...
delete from STATISTIC_COURSE;
commit;
prompt Deleting STATISTICSUM...
delete from STATISTICSUM;
commit;
prompt Loading STATISTICSUM...
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('1', '2012', '01', '30', '集体用户', 0, 1558, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:37:41', 'dd-mm-yyyy hh24:mi:ss'), null, 275, 244932);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('2', '2012', '02', '28', '集体用户', 0, 1446, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:37:41', 'dd-mm-yyyy hh24:mi:ss'), null, 275, 246378);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('3', '2012', '03', '30', '集体用户', 2, 2522, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:37:41', 'dd-mm-yyyy hh24:mi:ss'), null, 277, 248900);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('4', '2012', '04', '30', '集体用户', 0, 1570, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:37:41', 'dd-mm-yyyy hh24:mi:ss'), null, 277, 250470);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('5', '2012', '05', '30', '集体用户', 1, 4919, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:37:41', 'dd-mm-yyyy hh24:mi:ss'), null, 278, 255389);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('6', '2012', '06', '30', '集体用户', 1, 5083, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:37:41', 'dd-mm-yyyy hh24:mi:ss'), null, 279, 260472);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('7', '2012', '07', '30', '集体用户', 2, 2154, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:37:41', 'dd-mm-yyyy hh24:mi:ss'), null, 281, 217969);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('8', '2012', '08', '30', '集体用户', 0, 9061, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:37:41', 'dd-mm-yyyy hh24:mi:ss'), null, 281, 283587);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('9', '2012', '09', '30', '集体用户', 1, 6454, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:37:41', 'dd-mm-yyyy hh24:mi:ss'), null, 282, 290041);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('10', '2012', '10', '30', '集体用户', 1, 4253, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:38:07', 'dd-mm-yyyy hh24:mi:ss'), null, 283, 294294);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('11', '2012', '11', '30', '集体用户', 1, 2585, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:38:07', 'dd-mm-yyyy hh24:mi:ss'), null, 284, 296879);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('12', '2012', '12', '30', '集体用户', 0, 70, null, null, null, null, null, null, null, null, null, null, null, null, to_date('03-12-2012 21:38:07', 'dd-mm-yyyy hh24:mi:ss'), null, 284, 296949);
insert into STATISTICSUM (id, year, month, day, fk_regtype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('601', '2012', '06', '28', '个人用户', 0, 11900, null, null, null, null, null, null, null, null, null, null, null, null, to_date('04-12-2012 10:16:07', 'dd-mm-yyyy hh24:mi:ss'), null, 281, 56557);
commit;
prompt 13 records loaded
prompt Loading STATISTIC_COURSE...
prompt Table is empty
prompt Loading STATISTIC_ENTERPRISE...
prompt Table is empty
prompt Loading STATISTIC_ENTERPRISE_TYPE...
insert into STATISTIC_ENTERPRISE_TYPE (id, year, month, day, fk_enterprisetype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('1', '2012', '12', '04', '402880a91dadc115011dadfcf26b1003', 4, 1270, null, null, null, null, null, null, null, null, null, null, null, null, to_date('04-12-2012 11:39:24', 'dd-mm-yyyy hh24:mi:ss'), null, 72, 10429);
insert into STATISTIC_ENTERPRISE_TYPE (id, year, month, day, fk_enterprisetype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('2', '2012', '12', '04', '402880a91dadc115011dadfcf26b2003', 0, 40, null, null, null, null, null, null, null, null, null, null, null, null, to_date('04-12-2012 11:39:24', 'dd-mm-yyyy hh24:mi:ss'), null, 13, 714);
insert into STATISTIC_ENTERPRISE_TYPE (id, year, month, day, fk_enterprisetype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('3', '2012', '12', '04', '402880a91dadc115011dadfcf26b3003', 1, 40246, null, null, null, null, null, null, null, null, null, null, null, null, to_date('04-12-2012 11:39:24', 'dd-mm-yyyy hh24:mi:ss'), null, 114, 227140);
insert into STATISTIC_ENTERPRISE_TYPE (id, year, month, day, fk_enterprisetype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('4', '2012', '12', '04', '402880a91dadc115011dadfcf26b4003', 4, 99, null, null, null, null, null, null, null, null, null, null, null, null, to_date('04-12-2012 11:39:24', 'dd-mm-yyyy hh24:mi:ss'), null, 81, 889);
insert into STATISTIC_ENTERPRISE_TYPE (id, year, month, day, fk_enterprisetype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('5', '2012', '12', '04', '402880a91dadc115011dadfcf26b5003', 0, 7, null, null, null, null, null, null, null, null, null, null, null, null, to_date('04-12-2012 11:39:24', 'dd-mm-yyyy hh24:mi:ss'), null, 3, 23);
insert into STATISTIC_ENTERPRISE_TYPE (id, year, month, day, fk_enterprisetype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('6', '2012', '12', '04', '402880a91dadc115011dadfcf26b6003', 0, 13, null, null, null, null, null, null, null, null, null, null, null, null, to_date('04-12-2012 11:39:24', 'dd-mm-yyyy hh24:mi:ss'), null, 1, 316);
insert into STATISTIC_ENTERPRISE_TYPE (id, year, month, day, fk_enterprisetype_id, reg_enterprise_num, reg_user_num, entry_user_num, entry_user_times, entry_courehours, fee_amount, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, test_begin_times, test_complete_times, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('7', '2012', '12', '04', null, null, 11900, null, null, null, null, null, null, null, null, null, null, null, null, to_date('04-12-2012 11:39:24', 'dd-mm-yyyy hh24:mi:ss'), null, null, 57438);
commit;
prompt 7 records loaded
prompt Loading STATISTIC_YEAR...
insert into STATISTIC_YEAR (id, year, month, reg_enterprise_num, reg_user_num, reg_enterprise_growth, reg_user_growth, entry_user_num, entry_user_times, entry_courehours, fee_amount, entry_user_growth, entry_user_times_growth, entry_courehours_growth, fee_amount_growth, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, study_begin_rate, study_complete_rate, study_begin_times_growth, study_complete_times_growth, test_begin_times, test_complete_times, test_begin_times_growth, test_complete_times_growth, test_complete_rate, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('5', '2012', '12', 9, 53575, '↑3.27%', '↑22.01%', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 284, 296949);
insert into STATISTIC_YEAR (id, year, month, reg_enterprise_num, reg_user_num, reg_enterprise_growth, reg_user_growth, entry_user_num, entry_user_times, entry_courehours, fee_amount, entry_user_growth, entry_user_times_growth, entry_courehours_growth, fee_amount_growth, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, study_begin_rate, study_complete_rate, study_begin_times_growth, study_complete_times_growth, test_begin_times, test_complete_times, test_begin_times_growth, test_complete_times_growth, test_complete_rate, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('4', '2011', '12', 18, 78728, '↑7.00%', '↑47.82%', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 275, 243374);
insert into STATISTIC_YEAR (id, year, month, reg_enterprise_num, reg_user_num, reg_enterprise_growth, reg_user_growth, entry_user_num, entry_user_times, entry_courehours, fee_amount, entry_user_growth, entry_user_times_growth, entry_courehours_growth, fee_amount_growth, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, study_begin_rate, study_complete_rate, study_begin_times_growth, study_complete_times_growth, test_begin_times, test_complete_times, test_begin_times_growth, test_complete_times_growth, test_complete_rate, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('3', '2010', '12', 27, 59913, '↑11.74%', '↑57.21%', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 257, 164646);
insert into STATISTIC_YEAR (id, year, month, reg_enterprise_num, reg_user_num, reg_enterprise_growth, reg_user_growth, entry_user_num, entry_user_times, entry_courehours, fee_amount, entry_user_growth, entry_user_times_growth, entry_courehours_growth, fee_amount_growth, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, study_begin_rate, study_complete_rate, study_begin_times_growth, study_complete_times_growth, test_begin_times, test_complete_times, test_begin_times_growth, test_complete_times_growth, test_complete_rate, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('2', '2009', '12', 67, 32381, '↑41.10%', '↑44.75%', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 230, 104733);
insert into STATISTIC_YEAR (id, year, month, reg_enterprise_num, reg_user_num, reg_enterprise_growth, reg_user_growth, entry_user_num, entry_user_times, entry_courehours, fee_amount, entry_user_growth, entry_user_times_growth, entry_courehours_growth, fee_amount_growth, refund_num, refund_amount, overyear_refund_num, overyear_refund_amount, study_begin_times, study_complete_times, study_begin_rate, study_complete_rate, study_begin_times_growth, study_complete_times_growth, test_begin_times, test_complete_times, test_begin_times_growth, test_complete_times_growth, test_complete_rate, create_date, update_date, reg_enterprise_count, reg_user_count)
values ('1', '2008', '12', 163, 72352, '--', '--', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 163, 72352);
commit;
prompt 5 records loaded
prompt Enabling triggers for STATISTICSUM...
alter table STATISTICSUM enable all triggers;
prompt Enabling triggers for STATISTIC_COURSE...
alter table STATISTIC_COURSE enable all triggers;
prompt Enabling triggers for STATISTIC_ENTERPRISE...
alter table STATISTIC_ENTERPRISE enable all triggers;
prompt Enabling triggers for STATISTIC_ENTERPRISE_TYPE...
alter table STATISTIC_ENTERPRISE_TYPE enable all triggers;
prompt Enabling triggers for STATISTIC_YEAR...
alter table STATISTIC_YEAR enable all triggers;
set feedback on
set define on
prompt Done.
