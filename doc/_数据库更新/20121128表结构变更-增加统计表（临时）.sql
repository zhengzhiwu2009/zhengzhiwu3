-- Create table
create table DATA_STAT
(
  id                 NVARCHAR2(50) not null,
  name               NVARCHAR2(100),
  year               NVARCHAR2(10),
  dept_add           NUMBER,
  dept_total         NUMBER,
  dept_total_growth  NVARCHAR2(10),
  staff_add          NUMBER,
  staff_total        NUMBER,
  staff_total_growth NVARCHAR2(10),
  create_date        DATE,
  modify_date        DATE
)
tablespace HZPH
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table DATA_STAT
  is '统计表，临时解决方案';
-- Add comments to the columns 
comment on column DATA_STAT.id
  is 'ID';
comment on column DATA_STAT.name
  is '名称';
comment on column DATA_STAT.year
  is '年份';
comment on column DATA_STAT.dept_add
  is '注册单位新增';
comment on column DATA_STAT.dept_total
  is '注册单位累计';
comment on column DATA_STAT.dept_total_growth
  is '注册单位累计同比';
comment on column DATA_STAT.staff_add
  is '注册人数新增';
comment on column DATA_STAT.staff_total
  is '注册人数累计';
comment on column DATA_STAT.staff_total_growth
  is '注册人数累计同比';
comment on column DATA_STAT.create_date
  is '创建日期';
comment on column DATA_STAT.modify_date
  is '修改日期';
-- Create/Recreate primary, unique and foreign key constraints 
alter table DATA_STAT
  add constraint DATA_STAT_PK primary key (ID)
  using index 
  tablespace HZPH
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

  
  
  
  
  
  ---添加测试数据
  insert into DATA_STAT (ID, NAME, YEAR, DEPT_ADD, DEPT_TOTAL, DEPT_TOTAL_GROWTH, STAFF_ADD, STAFF_TOTAL, STAFF_TOTAL_GROWTH, CREATE_DATE, MODIFY_DATE)
values ('1', '2012', '2012', 9, 248, '↑3.27%', 53260, 296634, '↑21.88%', to_date('01-01-2012', 'dd-mm-yyyy'), null);

insert into DATA_STAT (ID, NAME, YEAR, DEPT_ADD, DEPT_TOTAL, DEPT_TOTAL_GROWTH, STAFF_ADD, STAFF_TOTAL, STAFF_TOTAL_GROWTH, CREATE_DATE, MODIFY_DATE)
values ('2', '2011', '2011', 18, 275, '↑7.00%', 78728, 243374, '↑47.82%', to_date('01-01-2011', 'dd-mm-yyyy'), null);

insert into DATA_STAT (ID, NAME, YEAR, DEPT_ADD, DEPT_TOTAL, DEPT_TOTAL_GROWTH, STAFF_ADD, STAFF_TOTAL, STAFF_TOTAL_GROWTH, CREATE_DATE, MODIFY_DATE)
values ('3', '2010', '2010', 27, 257, '↑11.74%', 59913, 164646, '↑57.21%', to_date('01-01-2010', 'dd-mm-yyyy'), null);

insert into DATA_STAT (ID, NAME, YEAR, DEPT_ADD, DEPT_TOTAL, DEPT_TOTAL_GROWTH, STAFF_ADD, STAFF_TOTAL, STAFF_TOTAL_GROWTH, CREATE_DATE, MODIFY_DATE)
values ('4', '2009', '2009', 67, 230, '↑41.10%', 32381, 104733, '↑44.75%', to_date('01-01-2009', 'dd-mm-yyyy'), null);

insert into DATA_STAT (ID, NAME, YEAR, DEPT_ADD, DEPT_TOTAL, DEPT_TOTAL_GROWTH, STAFF_ADD, STAFF_TOTAL, STAFF_TOTAL_GROWTH, CREATE_DATE, MODIFY_DATE)
values ('5', '2008', '2008', 163, 163, '--', 72352, 72352, '--', to_date('01-01-2008', 'dd-mm-yyyy'), null);
  