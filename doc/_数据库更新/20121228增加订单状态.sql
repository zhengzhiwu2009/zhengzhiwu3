
update enum_const ec set ec.name='学生选课' where ec.id='40288a7b3980f3a3013980f8d30f0001';

insert into Enum_Const (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('40288a7b39d3008a0139d396020d009g', '选课期选课', '3', 'FlagOrderType', '0', to_date('28-12-2012', 'dd-mm-yyyy'), '订单类型');

insert into Enum_Const (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('40288a7b39d3008a0139d396020d009h', '直接选课', '4', 'FlagOrderType', '0', to_date('28-12-2012', 'dd-mm-yyyy'), '订单类型');

insert into Enum_Const (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('40288a7b39d3008a0139d396020d009i', '报名单选课', '5', 'FlagOrderType', '0', to_date('28-12-2012', 'dd-mm-yyyy'), '订单类型');
