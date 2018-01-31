---证券综合统计权限sql

insert into pe_pri_category (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001', '综合统计', '11', '11001', '/zhengquan/zhtj.jsp', '1');

insert into pe_priority (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0506', '综合统计_*', '11001', '/entity/statistics', 'prBzzStatistics', '*');

