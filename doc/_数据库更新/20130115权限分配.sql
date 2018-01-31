prompt PL/SQL Developer import file
prompt Created on 2013��1��15�� by Administrator
set feedback off
set define off
prompt Dropping PE_PRI_CATEGORY...
drop table PE_PRI_CATEGORY cascade constraints;
prompt Dropping PE_PRIORITY...
drop table PE_PRIORITY cascade constraints;
prompt Dropping PE_PRI_ROLE...
drop table PE_PRI_ROLE cascade constraints;
prompt Dropping PR_PRI_ROLE...
drop table PR_PRI_ROLE cascade constraints;
prompt Creating PE_PRI_CATEGORY...
create table PE_PRI_CATEGORY
(
  ID             VARCHAR2(50) not null,
  NAME           VARCHAR2(50) not null,
  FK_PARENT_ID   VARCHAR2(50),
  CODE           VARCHAR2(50),
  PATH           VARCHAR2(1000),
  FLAG_LEFT_MENU CHAR(1)
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
comment on table PE_PRI_CATEGORY
  is '?????';
comment on column PE_PRI_CATEGORY.FK_PARENT_ID
  is '?????????????????';
comment on column PE_PRI_CATEGORY.CODE
  is '???js???';
comment on column PE_PRI_CATEGORY.PATH
  is '????????';
comment on column PE_PRI_CATEGORY.FLAG_LEFT_MENU
  is '?????? 1??? 0???';
alter table PE_PRI_CATEGORY
  add constraint PK_PE_PRI_CATETORY primary key (ID)
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
alter table PE_PRI_CATEGORY
  add constraint AK_KEY_3_PE_PRI_C unique (CODE)
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

prompt Creating PE_PRIORITY...
create table PE_PRIORITY
(
  ID            VARCHAR2(50) not null,
  NAME          VARCHAR2(50) not null,
  FK_PRI_CAT_ID VARCHAR2(50),
  NAMESPACE     VARCHAR2(50) not null,
  ACTION        VARCHAR2(50) not null,
  METHOD        VARCHAR2(50) not null
)
tablespace HZPH
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 192K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table PE_PRIORITY
  is '???';
comment on column PE_PRIORITY.NAMESPACE
  is 'Struts NAMESPACE';
comment on column PE_PRIORITY.ACTION
  is 'Struts ACTION';
comment on column PE_PRIORITY.METHOD
  is 'Struts METHOD';
alter table PE_PRIORITY
  add constraint PK_PE_PRIORITY primary key (ID)
  using index 
  tablespace HZPH
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 128K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table PE_PRIORITY
  add constraint FK_PE_PRIOR_REFERENCE_PE_PRI_C foreign key (FK_PRI_CAT_ID)
  references PE_PRI_CATEGORY (ID);

prompt Creating PE_PRI_ROLE...
create table PE_PRI_ROLE
(
  ID             VARCHAR2(50) not null,
  NAME           VARCHAR2(50) not null,
  FLAG_ROLE_TYPE VARCHAR2(50),
  FLAG_BAK       VARCHAR2(50)
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
comment on table PE_PRI_ROLE
  is '?????';
comment on column PE_PRI_ROLE.FLAG_ROLE_TYPE
  is '?? 1?? 2?? 3?? 4??  ?? ??ssoUser?USER_TYPEG ??';
alter table PE_PRI_ROLE
  add constraint PK_PE_PRI_ROLE primary key (ID)
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
alter table PE_PRI_ROLE
  add constraint AK_KEY_1_PE_PRI_R unique (NAME)
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

prompt Creating PR_PRI_ROLE...
create table PR_PRI_ROLE
(
  ID             VARCHAR2(50) not null,
  FK_ROLE_ID     VARCHAR2(50),
  FK_PRIORITY_ID VARCHAR2(50),
  FLAG_ISVALID   CHAR(1)
)
tablespace HZPH
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 192K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table PR_PRI_ROLE
  is '??-?? ??';
comment on column PR_PRI_ROLE.FLAG_ISVALID
  is '????  ????? ?????????';
alter table PR_PRI_ROLE
  add constraint PK_PR_PRI_ROLE primary key (ID)
  using index 
  tablespace HZPH
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 192K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for PE_PRI_CATEGORY...
alter table PE_PRI_CATEGORY disable all triggers;
prompt Disabling triggers for PE_PRIORITY...
alter table PE_PRIORITY disable all triggers;
prompt Disabling triggers for PE_PRI_ROLE...
alter table PE_PRI_ROLE disable all triggers;
prompt Disabling triggers for PR_PRI_ROLE...
alter table PR_PRI_ROLE disable all triggers;
prompt Disabling foreign key constraints for PE_PRIORITY...
alter table PE_PRIORITY disable constraint FK_PE_PRIOR_REFERENCE_PE_PRI_C;
prompt Loading PE_PRI_CATEGORY...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1000201', '�����ʾ��б�', '10002', '1000201', '/entity/information/peVotePaper.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1000202', '��ӵ����ʾ�', '10002', '1000202', '/entity/information/peVotePaper_toAddVotePaper.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('0900102', '��ӹ���', '09001', '0900102', '/entity/information/peBulletin_showAddNotice.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('0900101', '�����б�', '09001', '0900101', '/entity/information/peBulletin.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028937b38ea06c00138ea5ae6e00004', '��������', '02', '06005', '/entity/basic/peSubEnterprise.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('08', '���屨���븶��', null, '08', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('01', 'ϵͳ����', null, '01', '/entity/information/peBulletinView.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('03', 'ѧԱ����', null, '03', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('07', '�������', null, '07', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('09', '֪ͨ�������', null, '09', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11', 'ͳ�Ʒ���', null, '11', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('10', '���ߵ���', null, '10', '/entity/information/peVotePaperList.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('05', '���Թ���', null, '05', '/entity/exam/peExamOnline.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('04', '�γ̹���', null, '04', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('0400301', 'ר����ѵ�γ�', '04003', '0400301', '/entity/basic/peBzzBatch.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('02', '�˻�����', null, '02', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('06', '��ѵ��ѯ', null, '06', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('03001', 'ѧԱ��Ϣ�б�', '03', '03001', '/entity/basic/peBzzStudent.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('04003', 'ר����ѵ�γ̹���', '04', '04003', 'to_top_menu', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('04002', '������', '04', '04002', '/entity/teaching/peBzzExamLibManager.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('04001', '�γ̷�������', '04', '04001', '/entity/teaching/peBzzCourseManager.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('04004', '����ȵ������', '04', '04004', 'to_top_menu', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('05001', '���߿��Թ���', '05', '05001', '/entity/exam/peExamOnline.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('05002', '���߿���������', '05', '05002', '/entity/exam/peExamOnline.action?flag=examLib', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('07001', '�ɷѹ���', '07', '07001', '/entity/basic/paymentManage.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('07002', '�˷ѹ���', '07', '07002', '/entity/basic/refoundManage.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('07003', '��Ʊ����', '07', '07003', '/entity/basic/invoiceManage.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('07004', 'Ԥ�����˻���ѯ', '07', '07004', '/entity/basic/prepaidAccountManage.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('10001', '�����ʾ�', '10', '10001', '/entity/information/peVotePaperList.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('10002', '�����ʾ����', '10', '10002', 'to_top_menu', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('08001', 'ѡ���ڷ�ʽ', '08', '08001', '/entity/basic/peElectiveCoursePeriod.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('08003', '����������ʷ��ѯ', '08', '08003', 'to_top_menu', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('08002', 'ֱ��ѡ�η�ʽ', '08', '08002', '/entity/basic/collectiveElective_toEelectiveBatchUpload.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('08004', 'Ԥ�����˻�����', '08', '08004', '/entity/basic/prepaidAccountManage_toAccountManage.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('09001', '�������', '09', '09001', 'to_top_menu', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('09002', 'վ������', '09', '09002', 'to_top_menu', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('09003', '�����б�', '09', '09003', '/entity/information/peBulletin.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('02001', '�˻�������Ϣ', '02', '02001', '/entity/information/personalInfo_viewInfo.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('02002', '�����û��˻�', '02', '02002', '/entity/basic/enterpriseManager.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003', '�γ�ͳ��', '11', '11003', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11002', 'ѧԱͳ��', '11', '11002', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001', '�ۺ�ͳ��', '11', '11001', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11004', '��������', '11', '11004', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('06004', 'ȫϵͳѧԱ��ѯ', '06', '06004', '/entity/teacher/search_any_student.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('06003', 'ѧϰ�����ѯ', '06', '06003', 'to_top_menu', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('06001', 'ר����ѵ', '06', '06001', '/entity/teaching/prBzzTchOpenCourse.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('06002', '��֯����', '06', '06002', '/entity/basic/peEnterprise.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('04004001', '�����б����', '04004', '04004001', '/entity/teaching/satisfactionPaperManager.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288a213bc1aa14013bc1c7ccd7000d', '�������б�', '40288a213bc0df4a013bc1a10d1a0013', '0800601', '/entity/basic/signUpOnline.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('0900201', '�ռ���', '09002', '0900201', '/siteEmail/recipientEmail.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288af0397a96db01397ac671eb0009', '������', '09002', '0900202', '/siteEmail/sendEmail.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288acf39aef4d00139af07cf970002', 'ѧԱ', '08003', '0800302', '/entity/basic/collectiveStudentQuery.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288acf39b430650139b44563610003', 'ѧԱ���γ�', '06003', '0600301', '/entity/teaching/studentCourse_toStudentCourseSearch.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288acf39b430650139b4462a790004', '�γ̡�ѧԱ', '06003', '0600302', '/entity/teaching/courseStudent_toCourseStudentSearch.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288acf39aef4d00139af07203d0001', '����', '08003', '0800301', '/entity/basic/collectiveOrderQuery.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288af0397bb32a01397c1c7cab0001', 'д��', '09002', '0900203', '/siteEmail/editEmail_index.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288ccf3acf22eb013acf7293f80019', '������ѧԱ��Ϣ�б�', '03', '03002', '/entity/basic/peBzzStudent.action?flag=self', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288ccf3aaf4b0c013ab00af90c0013', '�Ǵ�ҵע�����', '02', '02004', '/sso/regist.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('ff8080813b0ff864013b10ead3b90039', 'ר��֧��', '08', '08005', '/entity/basic/paymentBatch.action', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288a213bc1aa14013bc1c8b0de000e', '���������', '40288a213bc0df4a013bc1a10d1a0013', '0800602', '/entity/manager/signUpOnline/signUpOnline.jsp', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003002004', '�γ���ϸ��ѯ', '11003002', '11003002004', '/birt/zqyxh/REPORT_REL_DETAIL.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288a213bc0df4a013bc1a10d1a0013', '���߱�����', '08', '08006', 'to_top_menu', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288a7b3b613c5b013b614e3d150009', '�������', '09001', '09001001', '*', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288acf3bd4ccd5013bd5304fce0010', '���֧Ʊ����', '08003', '0800303', '/entity/basic/orderQueryZhiDian.action', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288a7b3b613c5b013b61435b690004', '���Բ˵�', null, '100', '*', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288a7b3b613c5b013b6150dcc1000d', '�������2', '40288a7b3b613c5b013b614e3d150009', '09001001001', '*', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001002002', '��ע�����', '11001002', '11001002002', '/birt/zqyxh/REPORT_REG_TYPE.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001002', 'ע��ͳ��', '11001', '11001002', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001002003', '���¶�ͳ��', '11001002', '11001002003', '/birt/zqyxh/REPORT_REG_MOUNTH.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001002004', '����������', '11001002', '11001002004', '/birt/zqyxh/REPORT_REG_INS.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001002001', 'ע��ͳ�Ƹ���', '11001002', '11001002001', '/birt/zqyxh/REPORT_REG.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001001', '�ۺ�ͳ�Ƹ���', '11001', '11001001', '/birt/zqyxh/REPORT_GENERAL.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001003', '�����븶��ͳ��', '11001', '11001003', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001004', 'ѧϰͳ��', '11001', '11001004', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001003005', '���γ�����', '11001003', '11001003005', '/birt/zqyxh/REPORT_ENT_COURSE.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001003004', '����������', '11001003', '11001003004', '/birt/zqyxh/REPORT_ENT_INS.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001003003', '���¶�ͳ��', '11001003', '11001003003', '/birt/zqyxh/REPORT_ENT_MONTH.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001003002', '��ע�����', '11001003', '11001003002', '/birt/zqyxh/REPORT_ENT_TYPE.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001003001', '�����븶��ͳ�Ƹ���', '11001003', '11001003001', '/birt/zqyxh/REPORT_ENT.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001004002', '��ע�����', '11001004', '11001004002', '/birt/zqyxh/REPORT_LEN_TYPE.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001004001', 'ѧϰͳ�Ƹ���', '11001004', '11001004001', '/birt/zqyxh/REPORT_LEN.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001004005', '���γ�����', '11001004', '11001004005', '/birt/zqyxh/REPORT_LEN_COURSE.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001004004', '����������', '11001004', '11001004004', '/birt/zqyxh/REPORT_LEN_INS.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001004003', '���¶�ͳ��', '11001004', '11001004003', '/birt/zqyxh/REPORT_LEN_MONTH.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001006', 'ѧʱͳ��', '11001', '11001006', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001005', '����ͳ��', '11001', '11001005', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001005004', '����������', '11001005', '11001005004', '/birt/zqyxh/REPORT_TES_INS.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001005003', '���¶�ͳ��', '11001005', '11001005003', '/birt/zqyxh/REPORT_TES_MONTH.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001005002', '��ע�����', '11001005', '11001005002', '/birt/zqyxh/REPORT_TES_TYPE.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001005001', '����ͳ�Ƹ���', '11001005', '11001005001', '/birt/zqyxh/REPORT_TES.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001005005', '���γ�����', '11001005', '11001005005', '/birt/zqyxh/REPORT_TES_COURSE.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003002', '�γ̷���', '11003', '11003002', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001006004', '����������', '11001006', '11001006004', '/birt/zqyxh/REPORT_TIM_INS.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001006003', '���¶�ͳ��', '11001006', '11001006003', '/birt/zqyxh/REPORT_TIM_MONTH.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001006002', '��ע�����', '11001006', '11001006002', '/birt/zqyxh/REPORT_TIM_TYPE.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11001006001', 'ѧʱͳ�Ƹ���', '11001006', '11001006001', '/birt/zqyxh/REPORT_TIM.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003002001', '�γ̷�������', '11003002', '11003002001', '/birt/zqyxh/REPORT_REL.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003001', '�γ�ͳ�Ƹ���', '11003', '11003001', '/birt/zqyxh/REPORT_COURSE.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003003001', 'ѧϰ���������', '11003003', '11003003001', '/birt/zqyxh/REPORT_LNT.jsp', '1');
commit;
prompt 100 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003002002001', '�����ʷ���', '11003002002', '11003002002001', '/birt/zqyxh/REPORT_REL_NEW_TYP.jsp', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003002002002', '��ѧʱ����', '11003002002', '11003002002002', '/birt/zqyxh/REPORT_REL_NEW_TIM.jsp', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003002002', '����·��γ�', '11003002', '11003002002', '/birt/zqyxh/REPORT_REL_NEW_TYP.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003003', 'ѧϰ������', '11003', '11003003', null, '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003003002', 'ѧϰ��������ϸ', '11003003', '11003003002', '/birt/zqyxh/REPORT_LNT_YEAR.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('11003003003', '���������ȱȽ�', '11003003', '11003003003', '/birt/zqyxh/REPORT_LNT_ENT.jsp', '1');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288acf3c1d3df0013c1d5cdd6c0004', '������߿γ�', '11003002', '11003002003', '/birt/zqyxh/REPORT_REL_ONL_BUS.jsp', '1');
commit;
prompt 106 records loaded
prompt Loading PE_PRIORITY...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c8c0661', '�����б�_abstractList', '09003', '/entity/information', 'peBulletin', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b38ea06c00138ea5c32420005', '������֯����_*', '4028937b38ea06c00138ea5ae6e00004', '/entity/basic', 'peSubEnterprise', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b38ea06c00138ea5d27900006', '������֯����_abstractList', '4028937b38ea06c00138ea5ae6e00004', '/entity/basic', 'peSubEnterprise', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b392d020501392d1150b00001', '�γ�����ȵ������_*', '04001', '/entity/teaching', 'addSatisfactionPaper', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b3946df47013946e46d300002', '��Ʊ����_abstractList', '07003', '/entity/basic', 'invoiceManage', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ccf394d393801394d4280d80005', '����ѧʱ_*', '08004', '/entity/basic', 'buyClassHour', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ccf390fbbea01390fdd5eac0001', 'ר����ѵ�γ��б�_*', '04003', '/entity/teaching', 'prBzzTchOpenCourseDetail', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ccf390fbbea01390fe019670003', 'ר����ѵ�γ��б�_abstractList', '04003', '/entity/teaching', 'prBzzTchOpenCourseDetail', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b39433ae20139433f019c0002', '�˷ѹ���_abstractList', '07002', '/entity/basic', 'refoundManage', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b3938a277013938b3394a0002', 'ר����ѵ�γ̣���ѵ��ѯ��', '06001', '/entity/teaching', 'prBzzTchOpenCourseDetail', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b394207de0139421ac19f000b', '�ɷѹ���_*', '07001', '/entity/basic', 'paymentManage', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b39437e2a013943fed13e0002', 'Ԥ�����˻�_*', '07004', '/entity/basic', 'prepaidAccountManage', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b39437e2a0139440146aa0005', 'Ԥ�����˻�����_*', '08004', '/entity/basic', 'prepaidAccountManage', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b3946df47013946e3e3330001', '��Ʊ����_*', '07003', '/entity/basic', 'invoiceManage', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b3947c48b013948a52f460001', '����ѧʱ_*', '08004', '/entity/basic', 'assignStudyHour', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b392e14a601392e284a1b0002', '����ȵ��������Ŀ_*', '04004', '/entity/teaching', 'addQuestionToSatisfy', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b394207de0139421bb582000c', '�ɷѹ���_abstractList', '07001', '/entity/basic', 'paymentManage', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288af0397bb32a01397c1fb92b0002', 'д��', '40288af0397bb32a01397c1c7cab0001', '/siteEmail', 'editEmail', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288acf39b430650139b44a367c0005', 'һ��γ̲�ѯ��ѧԱ���γ̣�', '40288acf39b430650139b44563610003', '/entity/teaching', 'studentCourse', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288acf39b430650139b44aedf50006', 'һ��γ̲�ѯ���γ̵�ѧԱ��', '40288acf39b430650139b4462a790004', '/entity/teaching', 'courseStudent', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288acf395d087301395d0a47380001', '�鿴ѧʱ�����¼_*', '08004', '/entity/basic', 'checkClassHourRecord', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288af039aa11e90139aa14f0ed0001', '�ظ�', '40288af0397a96db01397ac671eb0009', '/siteEmail', 'editEmail', 'recipient');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288af039adb9090139adbee022001a', '�ռ��䣨�鿴������Ϣ��', '0900201', '/siteEmail', 'recipientEmail', 'EmailInfo');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288acf39c2489b0139c2712d8d0004', 'ȫϵͳѧԱ��ѯ_*', '06', '/entity/teaching', 'searchAnyStudent', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288af03975262401397563760e0001', '�ռ���', '0900201', '/siteEmail', 'recipientEmail', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b398a26e301398ac95bda0018', '��ֵ��¼_*', '07004', '/entity/basic', 'buyClassHourRecord', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b398a26e301398acb16e20019', '���Ѽ�¼_*', '07004', '/entity/basic', 'checkClassHourRecord', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288af0397a96db01397ac80ae6000a', '������', '40288af0397a96db01397ac671eb0009', '/siteEmail', 'sendEmail', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b392d020501392d1226330002', '�γ�����ȵ������_abstractList', '04001', '/entity/teaching', 'addSatisfactionPaper', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b392e14a601392e29246a0003', '����ȵ��������Ŀ_abstractList', '04004', '/entity/teaching', 'addQuestionToSatisfy', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b39343903013937c310050002', 'ѡ����ѧ������_abstractList', '08001', '/entity/basic/', 'addStudentToPeriod', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b39433ae20139433e59e70001', '�˷ѹ���_*', '07002', '/entity/basic', 'refoundManage', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b3913cfdc0139140f34a90001', '����ȵ����б����_*', '04004001', '/entity/teaching', 'satisfactionPaperManager', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b3913cfdc01391420e7330007', '����ȵ����б����_abstractList', '04004001', '/entity/teaching', 'satisfactionPaperManager', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b39437e2a01394400b10f0004', 'Ԥ�����˻�����_abstractList', '08004', '/entity/basic', 'prepaidAccountManage', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ccf394d5d0e01394d5f12910001', 'ѧʱ�����¼_*', '08004', '/entity/basic', 'buyClassHourRecord', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b395387ca0139538c53740001', '����ѡ��_*', '08002', '/entity/basic', 'collectiveElective', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b37dff33c0137dffeea490001', '�����û��ʻ�_*', '02002', '/entity/basic', 'enterpriseManager', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b37dff33c0137dffeea490002', '�����û��ʻ�_abstractList', '02002', '/entity/basic', 'enterpriseManager', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b37b79a350137ba3674d60036', '�ʻ�������Ϣ_*', '02001', '/entity/information', 'personalInfo', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61532232', 'ѧԱ��Ϣ�б�_*', '03001', '/entity/basic', 'peBzzStudent', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61532233', 'ѧԱ��Ϣ�б�_abstractList', '03001', '/entity/basic', 'peBzzStudent', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b37b79a350137ba5674d60036', 'ѧԱ��ϸ��Ϣ', '03001', '/entity/basic', 'peDetail', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790001', '�γ̷�������_*', '04001', '/entity/teaching', 'peBzzCourseManager', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790002', '�γ̷�������_abstractList', '04001', '/entity/teaching', 'peBzzCourseManager', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880f321ec15250121ed22ea681128', '������_*', '04002', '/entity/teaching', 'peBzzExamLibManager', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880f321ec15250121ed22ea681129', '������_abstractList', '04002', '/entity/teaching', 'peBzzExamLibManager', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b39343903013937c1f3c60001', 'ѡ����ѧ������_*', '08001', '/entity/basic', 'addStudentToPeriod', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b39437e2a013943fff26a0003', 'Ԥ�����˻�_abstractList', '07004', '/entity/basic', 'prepaidAccountManage', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e615e70512', 'ר����ѵ����_*', '0400301', '/entity/basic', 'peBzzBatch', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e615e70513', 'ר����ѵ����_abstractList', '0400301', '/entity/basic', 'peBzzBatch', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40284a8136cd5bee0136cdccf8fb0004', 'ѧϰ�����ѯ_*', '06003', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b37e4d1a30137e4f7e0e90001', 'ר����ѵ_*', '06001', '/entity/teaching', 'prBzzTchOpenCourse', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b37e4d1a30137e4f7e0e90002', 'ר����ѵ_abstractList', '06001', '/entity/teaching', 'prBzzTchOpenCourse', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880f321ec15250121edbf41a90035', '��֯�����б�_*', '06002', '/entity/basic', 'peEnterprise', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880f321ec15250121edc5444c0036', '��֯�����б�_abstractList', '06002', '/entity/basic', 'peEnterprise', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880462214f1cf012214f6a8650001', '������֯�����б�_*', '06002', '/entity/basic', 'peSubEnterprise', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880462214f1cf012214f94fb70002', '������֯�����б�_abstractList', '06002', '/entity/basic', 'peSubEnterprise', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40284a8136cd5bee0136cdccf8fb0007', '�ɷѹ���_*', '07001', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40284a8136cd5bee0136cdccf8fb0008', '�˷ѹ���_*', '07002', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40284a8136cd5bee0136cdccf8fb0009', '��Ʊ����_*', '07003', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40284a8136cd5bee0136cdccf8fb0010', 'Ԥ�����˻���������_*', '07004', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40284a8136cd5bee0136cdccf8fb0011', '���߿��Թ���_*', '05001', '/entity/exam', 'peExamOnline', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40284a8136cd5bee0136cdccf8fb0012', '���߿��Թ���_abstractList', '05001', '/entity/exam', 'peExamOnline', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40284a8136cd5bee0136cdccf8fb0013', '���߿���������_*', '05002', '/entity/exam', 'peExamOnline', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40284a8136cd5bee0136cdccf8fb0014', '���߿���������_abstractList', '05002', '/entity/exam', 'peExamOnline', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00003', '�����ʾ��б�_*', '1000201', '/entity/information', 'peVotePaper', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00004', '�����ʾ��б�_abstractList', '1000201', '/entity/information', 'peVotePaper', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00008', '�ʾ���_*', '1000201', '/entity/information', 'prVoteSuggest', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00006', '�ʾ���Ŀ����_*', '1000201', '/entity/information', 'prVoteQuestion', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00009', '�ʾ���_abstractList', '1000201', '/entity/information', 'prVoteSuggest', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00007', '�ʾ���Ŀ����_abstractList', '1000201', '/entity/information', 'prVoteQuestion', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00010', '��ӵ����ʾ�_*', '1000202', '/entity/information', 'peVotePaper', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00011', '��ӵ����ʾ�_abstractList', '1000202', '/entity/information', 'peVotePaper', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c8c0648', '������Ϣ�б�_*', '0900101', '/entity/information', 'peBulletin', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c8c0649', '������Ϣ�б�_abstractList', '0900101', '/entity/information', 'peBulletin', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c8c0650', '��ӹ���_*', '0900102', '/entity/information', 'peBulletin', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c8c0651', '��ӹ���_abstractList', '0900102', '/entity/information', 'peBulletin', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0502', '֪ͨ������ϸ��Ϣ_*', '0900101', '/entity/information', 'peBulletinView', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0503', '֪ͨ������ϸ��Ϣ_abstractList', '0900101', '/entity/information', 'peBulletinView', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0504', 'վ������_*', '09002', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0505', 'վ������_abstractList', '09002', '*', '*', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0506', '�ۺ�ͳ��_*', '11001', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0507', '�γ�ͳ��_*', '11003', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0508', 'ѧԱͳ��_*', '11002', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0509', '��������_*', '11004', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0401', 'ϵͳ����_*', '01', '/entity/information', 'peBulletinView', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0402', 'ϵͳ����_abstractList', '01', '/entity/information', 'peBulletinView', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0510', 'ȫϵͳѧԱ��ѯ_*', '06004', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00012', '�����ʾ�_*', '10001', '/entity/information', 'peVotePaperList', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00013', '�����ʾ�_abstractList', '10001', '/entity/information', 'peVotePaperList', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0511', '����ѡ��_*', '08002', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0512', '����ѡ��_abstractList', '08002', '*', '*', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0513', '���屨����ʷ��ѯѧԱ_*', '40288acf39aef4d00139af07cf970002', '/entity/basic', 'collectiveStudentQuery', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0514', '���屨����ʷ��ѯ����_*', '40288acf39aef4d00139af07203d0001', '/entity/basic', 'collectiveOrderQuery', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0515', 'ѡ����_*', '08001', '/entity/basic', 'peElectiveCoursePeriod', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0516', 'ѡ����_abstractList', '08001', '/entity/basic', 'peElectiveCoursePeriod', 'abstractList');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0517', 'Ԥ�����˻��������屨���븶�ѣ�_*', '08004', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c8c0660', '�����б�_*', '09003', '/entity/information', 'peBulletin', '*');
commit;
prompt 100 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288acf3aa215a2013aa217f4640001', 'ѧϰ�����ѯѧԱ���γ�_*', '06003', '/entity/teaching', 'studentCourseViewCourse', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ccf3aaf4b0c013ab00c2b430014', '�Ǵ�ҵע�����_*', '40288ccf3aaf4b0c013ab00af90c0013', '/sso', 'regist', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288ccf3acf22eb013acf80b6af001c', '������ѧԱ��Ϣ_*', '40288ccf3acf22eb013acf7293f80019', '/entity/basic', 'peBzzStudent', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288acf3aa1c2fd013aa1c6eea10001', 'ѧϰ�����ѯ�γ̵�ѧԱ�б�_', '06003', '/entity/teaching', 'courseStudentViewStudent', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('ff8080813b0ff864013b1901c5e80095', 'ר��֧��_*', 'ff8080813b0ff864013b10ead3b90039', '/entity/basic', 'paymentBatch', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('ff8080813aeeac87013af3b43c3e01b5', '������ѧԱ��ϸ��Ϣ', '40288ccf3acf22eb013acf7293f80019', '/entity/basic', 'peDetail', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0512x', '�����븶��_*', '11001003', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05121', '�����븶�Ѱ�ע�����_*', '11001003002', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05123', '�����븶�Ѱ��������_*', '11001003004', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05122', '�����븶�Ѱ��¶�ͳ��_*', '11001003003', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0512y', '�����븶��ͳ�Ƹ���_*', '11001003001', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0514x', 'ѧϰͳ��_*', '11001004', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05141', 'ѧϰͳ�ư�ע�����_*', '11001004002', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05143', 'ѧϰͳ�ư��������_*', '11001004004', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05142', 'ѧϰͳ�ư��¶�ͳ��_*', '11001004003', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0514y', 'ѧϰͳ�Ƹ���_*', '11001004001', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0512z', '�����븶�Ѱ��γ�����_*', '11001003005', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0514z', 'ѧϰͳ�ư��γ�����_*', '11001004005', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0515x', '����ͳ��_*', '11001005', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05151', '����ͳ�ư�ע�����_*', '11001005002', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05153', '����ͳ�ư��������_*', '11001005004', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05152', '����ͳ�ư��¶�ͳ��_*', '11001005003', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0515y', '����ͳ�Ƹ���_*', '11001005001', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0515z', '����ͳ�ư��γ�����_*', '11001005005', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0516x', 'ѧʱͳ��_*', '11001006', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05161', 'ѧʱͳ�ư�ע�����_*', '11001006002', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05163', 'ѧʱͳ�ư��������_*', '11001006004', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05162', 'ѧʱͳ�ư��¶�ͳ��_*', '11001006003', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0516y', 'ѧʱͳ�Ƹ���_*', '11001006001', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790400', '�γ̷�������_��ӵ�ר��', '04001', '/entity/teaching', 'peBzzCourseManager', 'addToSpecial');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790100', '�γ̷�������_���', '04001', '/entity/teaching', 'peBzzCourseManager', 'add');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790200', '�γ̷�������_ɾ��', '04001', '/entity/teaching', 'peBzzCourseManager', 'delete');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790300', '�γ̷�������_�޸�', '04001', '/entity/teaching', 'peBzzCourseManager', 'update');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790600', '�γ̷�������_����μ�', '04001', '/entity/teaching', 'peBzzCourseManager', 'impCourse');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790700', '�γ̷�������_Ԥ��', '04001', '/entity/teaching', 'peBzzCourseManager', 'review');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790800', '�γ̷�������_����μ�', '04001', '/entity/teaching', 'peBzzCourseManager', 'manageCourse');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790900', '�γ̷�������_����ѧϰ��Դ', '04001', '/entity/teaching', 'peBzzCourseManager', 'manageSource');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790a00', '�γ̷�������_��������', '04001', '/entity/teaching', 'peBzzCourseManager', 'satified');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790b00', '�γ̷�������_�޸Ŀμ���ҳ', '04001', '/entity/teaching', 'peBzzCourseManager', 'updateIndex');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790c00', '�γ̷�������_�鿴��Ϣ', '04001', '/entity/teaching', 'peBzzCourseManager', 'view');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b3b613c5b013b615134b2000e', '���Բ��Բ���', '40288a7b3b613c5b013b6150dcc1000d', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a213bc1aa14013bc1cfb9960015', '���߱�����_abstractList', '40288a213bc0df4a013bc1a10d1a0013', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790005', '�γ̷�������_*', '11003002001', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce79000d', 'ѧϰ������_*', '11003003', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790007', '�γ�ͳ�Ƹ���_*', '11003001', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790006', '�γ̷���_*', '11003002', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce79000c', 'ѧϰ���������_*', '11003003001', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce79000e', 'ѧϰ��������ϸ_*', '11003003002', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790008', '����·��γ�_*', '11003002002', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790010', '���������ȱȽ�_*', '11003003003', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a333c3d4471013c3d646273000c', 'ר����ѵ�γ̲鿴ѧԱ', '04003', '/entity/basic', 'peDetail', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288acf3c1d3df0013c1d6b5efc0017', '�γ���ϸ��ѯ_*', '11003002004', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b3b613c5b013b6144c5ff0006', '���Թ���*_', '40288a7b3b613c5b013b61435b690004', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a7b3b613c5b013b614ea735000a', '����', '40288a7b3b613c5b013b614e3d150009', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a213bc0df4a013bc1a3203a0014', '���߱�����_*', '40288a213bc0df4a013bc1a10d1a0013', '*', '*', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a213bc1aa14013bc1b70dea0006', '���߱������б�_*', '40288a213bc1aa14013bc1c7ccd7000d', '/entity/basic', 'signUpOnline', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a213bc1aa14013bc1b8e9ce0007', '���߱��������_*', '40288a213bc1aa14013bc1c8b0de000e', '/entity/basic', 'signUpOnline', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288acf3bd4ccd5013bd5353eaf0011', '���֧Ʊ����_*', '40288acf3bd4ccd5013bd5304fce0010', '/entity/basic', 'orderQueryZhiDian', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a213be6d699013be6df5d170002', '���߱����鿴ѧԱ_*', '40288a213bc1aa14013bc1c7ccd7000d', '/entity/basic', 'prSignUpOrderStudent', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288a213be6d699013be6e0dadd0003', '���߱����鿴�γ�_*', '40288a213bc1aa14013bc1c7ccd7000d', '/entity/basic', 'prSignUpOrderCourse', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790500', '�γ̷�������_ȡ��ר��', '04001', '/entity/teaching', 'peBzzCourseManager', 'cancelSpecial');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790d00', '�γ̷�������_�鿴������', '04001', '/entity/teaching', 'peBzzCourseManager', 'viewResult');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0511x', 'ע��ͳ��_*', '11001002', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05111', '��ע�����_*', '11001002002', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05113', '���������_*', '11001002004', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd05112', '���¶�ͳ��_*', '11001002003', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0511y', 'ע��ͳ�Ƹ���_*', '11001002001', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0511xx', '�ۺ�ͳ�Ƹ���_*', '11001001', '/entity/statistics', 'prBzzStatistics', '*');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790003', '�γ̷�������_fabu', '04001', '/entity/teaching', 'peBzzCourseManager', 'fabu');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028937b384132ff01384142ce790004', '�γ̷�������_tingyong', '04001', '/entity/teaching', 'peBzzCourseManager', 'tingyong');
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288acf3c1d3df0013c1d6b5efc0007', '������߿γ�_*', '40288acf3c1d3df0013c1d5cdd6c0004', '/entity/statistics', 'prBzzStatistics', '*');
commit;
prompt 170 records loaded
prompt Loading PE_PRI_ROLE...
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R001', 'Э�����Ա_�ɷ�', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R002', 'Э�����Ա_��Ʊ', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R003', 'Э�����Ա_�˷�', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R004', 'Э�����Ա_�γ̹���', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R005', 'Э�����Ա_���', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R006', 'Э�����Ա_ר��', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R007', 'Э�����Ա_�γ̲鿴', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R008', 'Э�����Ա_����', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R009', 'Э�����Ա_ѧԱ�鿴', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R010', 'Э�����Ա_ѧԱ����', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R011', 'Э�����Ա_ͳ��', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R012', 'Э�����Ա_��ѵ��ѯ', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R013', 'Э�����Ա_���ߵ���', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('R014', 'Э�����Ա_֪ͨ����', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('ff8080813bd023d7013bd26b5b300135', 'Э�����Ա_����', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('ff8080813bd023d7013bd26c831f0170', 'Э�����Ա_ͣ��', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('1', '�����������Ա', '4028826a1db7bb01011db7e623d70004', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('3', 'Э�����Ա', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('2', 'һ���������Ա', '4028826a1db7bb01011db7e5b0da0002', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('0', 'ѧ��', '402880951dad22bd011dad3edbcc0001', null);
commit;
prompt 20 records loaded
prompt Loading PR_PRI_ROLE...
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf390fbbea01390fe1b2b50004', '3', '40288ccf390fbbea01390fdd5eac0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b392e14a601392e2987a10004', '3', '40288a7b392e14a601392e284a1b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b39343903013937c3aabc0003', '2', '40288a7b39343903013937c1f3c60001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b39437e2a0139440198ac0006', '3', '40288a7b39437e2a013943fed13e0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf394d5d0e01394d5fbc000002', '2', '40288ccf394d5d0e01394d5f12910001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b395387ca0139538cacf30002', '2', '40288a7b395387ca0139538c53740001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b33a8c490006', '2', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b33d80fe0007', '3', '40288a7b37b79a350137ba5674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3aa21d90012', '3', '40284a8136cd5bee0136cdccf8fb0007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3aa21ee0015', '3', '40284a8136cd5bee0136cdccf8fb0010', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3dcd0cb002b', '3', '402880a92113a9b0012113e614fd0401', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3286b000001', '3', '40288a7b37dff33c0137dffeea490001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b33684580003', '3', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b33a3cfd0004', '3', '402880a92113a9b0012113e61532232', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b33a71eb0005', '2', '402880a92113a9b0012113e61532232', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b33da56d0008', '2', '40288a7b37b79a350137ba5674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b340869f000a', '3', '4028937b384132ff01384142ce790001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3477e7c000b', '3', '402880f321ec15250121ed22ea681128', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b34a8560000c', '3', '402880f321ec15250121ed22ea681130', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b390fc26000d', '3', '402880a92113a9b0012113e615e70512', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b39d7f32000e', '3', '40284a8136cd5bee0136cdccf8fb0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b39d7f38000f', '3', '40288a7b37e4d1a30137e4f7e0e90001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3a267ad0010', '3', '402880f321ec15250121edbf41a90035', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3aa21e00013', '3', '40284a8136cd5bee0136cdccf8fb0008', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3aa21ea0014', '3', '40284a8136cd5bee0136cdccf8fb0009', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3bfa86c0018', '3', '402880a91e5d1ff2011e5dee91a00003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3bfa8750019', '3', '402880a91e5d1ff2011e5dee91a00006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3bfa87a001a', '3', '402880a91e5d1ff2011e5dee91a00008', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3c1e7b4001b', '3', '402880a91e5d1ff2011e5dee91a00010', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3ca094a0020', '3', '402880a92113a9b0012113e62c8c0648', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3ca09510021', '3', '402880a92113a9b0012113e62c8c0650', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3e45aae002f', '2', '40284a8136cd5bee0136cdccf8fb0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3e45ab60030', '2', '40288a7b37e4d1a30137e4f7e0e90001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3e646f90031', '2', '402880a92113a9b0012113e614fd0510', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3f0f7790035', '2', '402880a92113a9b0012113e614fd0511', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3f0f78e0037', '2', '402880a92113a9b0012113e614fd0515', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288af039aa0c8d0139aa1163370001', '2', '40288af039a9f7170139aa0046200001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3d8d2c00027', '3', '402880a92113a9b0012113e614fd0506', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3d8d2d7002a', '3', '402880a92113a9b0012113e614fd0509', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3dd846b002c', '2', '402880a92113a9b0012113e614fd0401', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3eae0300032', '2', '402880a91e5d1ff2011e5dee91a00012', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3ec00550034', '2', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3f0f7930038', '2', '402880a92113a9b0012113e614fd0517', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38ea06c00138ea5d7c1f0007', '2', '4028937b38ea06c00138ea5c32420005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('4028937b38b319870138b3d2d7a00024', '3', '402880a92113a9b0012113e614fd0502', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b3947c48b013948a5fb400002', '2', '40288a7b3947c48b013948a52f460001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf394d393801394d431ae60006', '2', '40288ccf394d393801394d4280d80005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b3913cfdc01391412f2320005', '3', '40288a7b3913cfdc0139140f34a90001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b39433ae20139433f58630003', '3', '40288a7b39433ae20139433e59e70001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b3938a277013938b398f50003', '2', '40288a7b3938a277013938b3394a0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b394207de0139421bf8ac000d', '3', '40288a7b394207de0139421ac19f000b', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b39437e2a01394401ddd30007', '2', '40288a7b39437e2a0139440146aa0005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b3946df47013946e5946f0003', '3', '40288a7b3946df47013946e3e3330001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b392d020501392d128bc30003', '3', '40288a7b392d020501392d1150b00001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288af039752624013975641f240002', '3', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b398a26e301398acbc938001a', '3', '40288a7b398a26e301398acb16e20019', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b398a26e301398acc0a10001b', '3', '40288a7b398a26e301398ac95bda0018', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288af039a5ec390139a9a200380002', '2', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288af039aa11e90139aa15501d0002', '2', '40288af039aa11e90139aa14f0ed0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf39b430650139b44ca9030007', '2', '40288acf39b430650139b44a367c0005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf39b430650139b44caa6a0008', '2', '40288acf39b430650139b44aedf50006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf39c3652e0139c36804f50002', '3', '40288acf39b430650139b44a367c0005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf395d087301395d0b44350002', '2', '40288acf395d087301395d0a47380001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288af039752624013975660f970003', '3', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288af039752624013975660f9a0004', '3', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288af0397a96db01397ac91d06000b', '3', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288af0397bb32a01397c21ff820003', '3', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288af039a5ec390139a9a2285e0003', '2', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf39aec3a20139aedaac6a0007', '2', '40288acf39aec3a20139aed9b4100006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf39aee1870139aef2f5d30001', '2', '402880a92113a9b0012113e614fd0514', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf39c2489b0139c271a7220005', '2', '40288acf39c2489b0139c2712d8d0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf39c3652e0139c36805140003', '3', '40288acf39b430650139b44aedf50006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288af039adb9090139adbf5519001b', '0', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf3aa1c2fd013aa1c8103e0002', '3', '40288acf3aa1c2fd013aa1c6eea10001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf3aa1d617013aa1d974160002', '2', '40288acf3aa1c2fd013aa1c6eea10001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3aaf4b0c013ab00d2eba0016', '2', '40288ccf3aaf4b0c013ab00c2b430014', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551240032', '1', '40288ccf394d393801394d4280d80005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace855162003f', '1', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551a10049', '1', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bcb0220013bcc4531d60064', '3', '4028937b384132ff01384142ce790003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551b0004c', '1', '40288a7b39343903013937c310050002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bcb0220013bcc4531d60065', '3', '4028937b384132ff01384142ce790004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a213be6d699013be6e154240004', '2', '40288a213be6d699013be6df5d170002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a213be6d699013be6e154430005', '2', '40288a213be6d699013be6e0dadd0003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38fa8d0600e6', 'R002', '40288a7b3946df47013946e46d300002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551c00051', '1', '40288ccf394d5d0e01394d5f12910001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551c00052', '1', '40288a7b395387ca0139538c53740001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551c00053', '1', '40288a7b37dff33c0137dffeea490001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551c00054', '1', '40288a7b37dff33c0137dffeea490002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551c00055', '1', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('ff8080813aeeac87013af7679b090266', '3', '40288acf39c2489b0139c2712d8d0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('ff8080813aeeac87013af790ce5f035d', '2', 'ff8080813aeeac87013af78f6568035c', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('ff8080813b0d2e87013b0e0944420007', '2', 'ff8080813b0d2e87013b0e08a8510006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38fc010800e7', 'R003', '40284a8136cd5bee0136cdccf8fb0008', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38fc010800e8', 'R003', '40288a7b39433ae20139433e59e70001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38fc011800e9', 'R003', '40288a7b39433ae20139433f019c0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390985ba00ea', 'R005', '402880f321ec15250121ed22ea681128', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390985c900eb', 'R005', '402880f321ec15250121ed22ea681129', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390a02aa00ec', 'R004', '4028937b384132ff01384142ce790001', '1');
commit;
prompt 100 records committed...
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390a02aa00ed', 'R004', '4028937b384132ff01384142ce790002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390a02aa00ee', 'R004', '4028937b384132ff01384142ce790003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390a02ba00ef', 'R004', '4028937b384132ff01384142ce790004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85525c0080', '1', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390a2a7600f0', 'R004', '40288a7b392d020501392d1150b00001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390a2a7600f1', 'R004', '40288a7b392d020501392d1226330002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390a5cf000f2', 'R004', '40288a7b3913cfdc0139140f34a90001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390a5dbb00f3', 'R004', '40288a7b3913cfdc01391420e7330007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85526c0086', '1', '402880a92113a9b0012113e614fd0401', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85526c0087', '1', '402880a92113a9b0012113e614fd0402', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85526c0088', '1', '402880a92113a9b0012113e614fd0510', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85526c0089', '1', '402880a91e5d1ff2011e5dee91a00012', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390a5dbb00f4', 'R004', '40288a7b392e14a601392e284a1b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85527b008b', '1', '402880a92113a9b0012113e614fd0511', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85527b008c', '1', '402880a92113a9b0012113e614fd0512', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85527b008d', '1', '402880a92113a9b0012113e614fd0513', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85529b008f', '1', '402880a92113a9b0012113e614fd0515', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85529b0090', '1', '402880a92113a9b0012113e614fd0516', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8552aa0091', '1', '402880a92113a9b0012113e614fd0517', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390a5dbb00f5', 'R004', '40288a7b392e14a601392e29246a0003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8552aa0093', '1', '40288acf3aa215a2013aa217f4640001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8552aa0094', '1', '40288acf3aa1c2fd013aa1c6eea10001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c095a12bb001c', '3', '4028937b384132ff01384142ce790010', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf3aa215a2013aa218416a0002', '3', '40288acf3aa215a2013aa217f4640001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf3aa215a2013aa2188eb00003', '2', '40288acf3aa215a2013aa217f4640001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390b866c00f6', 'R007', '4028937b384132ff01384142ce790002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390d7e6300f7', 'R012', '4028937b384132ff01384142ce790002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551430037', '1', '40288a7b3938a277013938b3394a0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c095a801b001d', '3', '4028937b384132ff01384142ce79000c', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390dce6800f8', 'R012', '402880a92113a9b0012113e614fd0510', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace855143003a', '1', '40288a7b39437e2a0139440146aa0005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c095a802b001e', '3', '4028937b384132ff01384142ce79000e', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace855152003c', '1', '40288a7b3947c48b013948a52f460001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390dd01d00f9', 'R012', '40288acf39c2489b0139c2712d8d0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390f81e400fa', 'R012', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551620040', '1', '40288acf39b430650139b44a367c0005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551620041', '1', '40288acf39b430650139b44aedf50006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551620042', '1', '40288acf395d087301395d0a47380001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551720043', '1', '40288af039aa11e90139aa14f0ed0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551720044', '1', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551720045', '1', '40288acf39c2489b0139c2712d8d0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551720046', '1', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390fb49d00fb', 'R012', '402880f321ec15250121edbf41a90035', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390fb49d00fc', 'R012', '402880f321ec15250121edc5444c0036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c390fb4ac00fd', 'R012', '40288ccf390fbbea01390fe019670003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3910756c00fe', 'R012', '40284a8136cd5bee0136cdccf8fb0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3910756c00ff', 'R012', '40288acf3aa1c2fd013aa1c6eea10001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3910759b0100', 'R012', '40288acf3aa215a2013aa217f4640001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551ef005f', '1', '40288a7b39343903013937c1f3c60001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3910a8d00101', 'R012', '40288a7b37e4d1a30137e4f7e0e90002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39122aec0102', 'R009', '40288a7b37b79a350137ba5674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c391241520103', 'R009', '402880a92113a9b0012113e61532233', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace8551fe0063', '1', '40284a8136cd5bee0136cdccf8fb0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c391338f10104', 'R010', '402880a92113a9b0012113e61532232', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39133a1a0105', 'R010', '40288a7b37b79a350137ba5674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c391362240106', 'R010', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39142f090107', 'R014', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c391476ff0108', 'R013', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3914ef9a0109', 'R011', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39158031010a', 'R009', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3915cb54010b', 'R007', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39164873010c', 'R006', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3917175c010d', 'R005', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39175d2f010e', 'R004', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39179f97010f', 'R003', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c391810230110', 'R002', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3918335b0111', 'R002', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c391880420112', 'R001', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c391a61b40113', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('ff8080813b0ff864013b10ebb80f003b', '2', 'ff8080813b0ff864013b10eb8608003a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('ff8080813b1908e6013b191201aa003a', '2', 'ff8080813b0ff864013b1901c5e80095', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c391aa7870114', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93b6455a6013b64b5425b0044', '3', '402880a92113a9b0012113e614fd0511x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0002', '402883183bbd7a83013bbd9cd98f0001', '40288a7b392d020501392d1150b00001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0003', '402883183bbd7a83013bbd9cd98f0001', '40288ccf390fbbea01390fdd5eac0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0004', '402883183bbd7a83013bbd9cd98f0001', '40288a7b394207de0139421ac19f000b', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0005', '402883183bbd7a83013bbd9cd98f0001', '40288a7b39437e2a013943fed13e0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0006', '402883183bbd7a83013bbd9cd98f0001', '40288a7b3946df47013946e3e3330001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0007', '402883183bbd7a83013bbd9cd98f0001', '40288a7b392e14a601392e284a1b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0008', '402883183bbd7a83013bbd9cd98f0001', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0009', '402883183bbd7a83013bbd9cd98f0001', '40288acf39b430650139b44a367c0005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae000a', '402883183bbd7a83013bbd9cd98f0001', '40288acf39b430650139b44aedf50006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae000b', '402883183bbd7a83013bbd9cd98f0001', '40288acf39c2489b0139c2712d8d0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae000c', '402883183bbd7a83013bbd9cd98f0001', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae000d', '402883183bbd7a83013bbd9cd98f0001', '40288a7b398a26e301398ac95bda0018', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae000e', '402883183bbd7a83013bbd9cd98f0001', '40288a7b398a26e301398acb16e20019', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae000f', '402883183bbd7a83013bbd9cd98f0001', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0010', '402883183bbd7a83013bbd9cd98f0001', '40288a7b39433ae20139433e59e70001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0011', '402883183bbd7a83013bbd9cd98f0001', '40288a7b3913cfdc0139140f34a90001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0012', '402883183bbd7a83013bbd9cd98f0001', '40288a7b37dff33c0137dffeea490001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0013', '402883183bbd7a83013bbd9cd98f0001', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0014', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e61532232', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0015', '402883183bbd7a83013bbd9cd98f0001', '40288a7b37b79a350137ba5674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0016', '402883183bbd7a83013bbd9cd98f0001', '4028937b384132ff01384142ce790001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0017', '402883183bbd7a83013bbd9cd98f0001', '402880f321ec15250121ed22ea681128', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0018', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e615e70512', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0019', '402883183bbd7a83013bbd9cd98f0001', '40284a8136cd5bee0136cdccf8fb0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae001a', '402883183bbd7a83013bbd9cd98f0001', '40288a7b37e4d1a30137e4f7e0e90001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae001b', '402883183bbd7a83013bbd9cd98f0001', '402880f321ec15250121edbf41a90035', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae001c', '402883183bbd7a83013bbd9cd98f0001', '402880462214f1cf012214f6a8650001', '1');
commit;
prompt 200 records committed...
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae001d', '402883183bbd7a83013bbd9cd98f0001', '40284a8136cd5bee0136cdccf8fb0007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae001e', '402883183bbd7a83013bbd9cd98f0001', '40284a8136cd5bee0136cdccf8fb0008', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae001f', '402883183bbd7a83013bbd9cd98f0001', '40284a8136cd5bee0136cdccf8fb0009', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0020', '402883183bbd7a83013bbd9cd98f0001', '40284a8136cd5bee0136cdccf8fb0010', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0021', '402883183bbd7a83013bbd9cd98f0001', '40284a8136cd5bee0136cdccf8fb0011', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0022', '402883183bbd7a83013bbd9cd98f0001', '40284a8136cd5bee0136cdccf8fb0013', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0023', '402883183bbd7a83013bbd9cd98f0001', '402880a91e5d1ff2011e5dee91a00003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0024', '402883183bbd7a83013bbd9cd98f0001', '402880a91e5d1ff2011e5dee91a00008', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0025', '402883183bbd7a83013bbd9cd98f0001', '402880a91e5d1ff2011e5dee91a00006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0026', '402883183bbd7a83013bbd9cd98f0001', '402880a91e5d1ff2011e5dee91a00010', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0027', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e62c8c0648', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0028', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e62c8c0650', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0029', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd0502', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae002a', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae002b', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae002c', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd0506', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae002d', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd0509', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae002e', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd0401', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae002f', '402883183bbd7a83013bbd9cd98f0001', '40288acf3aa215a2013aa217f4640001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0030', '402883183bbd7a83013bbd9cd98f0001', '40288ccf3aaf4b0c013ab00c2b430014', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0031', '402883183bbd7a83013bbd9cd98f0001', '40288acf3aa1c2fd013aa1c6eea10001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0032', '402883183bbd7a83013bbd9cd98f0001', '40288a7b3b613c5b013b615134b2000e', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0033', '402883183bbd7a83013bbd9cd98f0001', '40288a7b3b613c5b013b614ea735000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0034', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd0511x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0035', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd05111', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0036', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd05113', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0037', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd05112', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0038', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd0511y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9cd9ae0039', '402883183bbd7a83013bbd9cd98f0001', '402880a92113a9b0012113e614fd0511xx', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4003b', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b392d020501392d1150b00001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4003c', '402883183bbd7a83013bbd9d2ea4003a', '40288ccf390fbbea01390fdd5eac0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4003d', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b394207de0139421ac19f000b', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4003e', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b39437e2a013943fed13e0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4003f', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b3946df47013946e3e3330001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40040', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b392e14a601392e284a1b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40041', '402883183bbd7a83013bbd9d2ea4003a', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40042', '402883183bbd7a83013bbd9d2ea4003a', '40288acf39b430650139b44a367c0005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40043', '402883183bbd7a83013bbd9d2ea4003a', '40288acf39b430650139b44aedf50006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40044', '402883183bbd7a83013bbd9d2ea4003a', '40288acf39c2489b0139c2712d8d0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40045', '402883183bbd7a83013bbd9d2ea4003a', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40046', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b398a26e301398ac95bda0018', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a7b3b613c5b013b614f0662000b', '3', '40288a7b3b613c5b013b614ea735000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93b6455a6013b6486f8850010', '3', '402880a92113a9b0012113e614fd05113', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93b6455a6013b64871fc70011', '3', '402880a92113a9b0012113e614fd05111', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93b6455a6013b64b542630045', '3', '402880a92113a9b0012113e614fd0511xx', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93b6455a6013b64b5426b0046', '3', '402880a92113a9b0012113e614fd0511y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183b8cb019013b8da8383d0084', '2', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40047', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b398a26e301398acb16e20019', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40048', '402883183bbd7a83013bbd9d2ea4003a', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40049', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b39433ae20139433e59e70001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4004a', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b3913cfdc0139140f34a90001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4004b', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b37dff33c0137dffeea490001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4004c', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4004d', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e61532232', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4004e', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b37b79a350137ba5674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4004f', '402883183bbd7a83013bbd9d2ea4003a', '4028937b384132ff01384142ce790001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40050', '402883183bbd7a83013bbd9d2ea4003a', '402880f321ec15250121ed22ea681128', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40051', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e615e70512', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40052', '402883183bbd7a83013bbd9d2ea4003a', '40284a8136cd5bee0136cdccf8fb0004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40053', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b37e4d1a30137e4f7e0e90001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40054', '402883183bbd7a83013bbd9d2ea4003a', '402880f321ec15250121edbf41a90035', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40055', '402883183bbd7a83013bbd9d2ea4003a', '402880462214f1cf012214f6a8650001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40056', '402883183bbd7a83013bbd9d2ea4003a', '40284a8136cd5bee0136cdccf8fb0007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40057', '402883183bbd7a83013bbd9d2ea4003a', '40284a8136cd5bee0136cdccf8fb0008', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40058', '402883183bbd7a83013bbd9d2ea4003a', '40284a8136cd5bee0136cdccf8fb0009', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40059', '402883183bbd7a83013bbd9d2ea4003a', '40284a8136cd5bee0136cdccf8fb0010', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4005a', '402883183bbd7a83013bbd9d2ea4003a', '40284a8136cd5bee0136cdccf8fb0011', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4005b', '402883183bbd7a83013bbd9d2ea4003a', '40284a8136cd5bee0136cdccf8fb0013', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4005c', '402883183bbd7a83013bbd9d2ea4003a', '402880a91e5d1ff2011e5dee91a00003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4005d', '402883183bbd7a83013bbd9d2ea4003a', '402880a91e5d1ff2011e5dee91a00008', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4005e', '402883183bbd7a83013bbd9d2ea4003a', '402880a91e5d1ff2011e5dee91a00006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4005f', '402883183bbd7a83013bbd9d2ea4003a', '402880a91e5d1ff2011e5dee91a00010', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40060', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e62c8c0648', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40061', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e62c8c0650', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40062', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd0502', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40063', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40064', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40065', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd0506', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40066', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd0509', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40067', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd0401', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40068', '402883183bbd7a83013bbd9d2ea4003a', '40288acf3aa215a2013aa217f4640001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40069', '402883183bbd7a83013bbd9d2ea4003a', '40288ccf3aaf4b0c013ab00c2b430014', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4006a', '402883183bbd7a83013bbd9d2ea4003a', '40288acf3aa1c2fd013aa1c6eea10001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4006b', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b3b613c5b013b615134b2000e', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4006c', '402883183bbd7a83013bbd9d2ea4003a', '40288a7b3b613c5b013b614ea735000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4006d', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd0511x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4006e', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd05111', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea4006f', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd05113', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40070', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd05112', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40071', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd0511y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9d2ea40072', '402883183bbd7a83013bbd9d2ea4003a', '402880a92113a9b0012113e614fd0511xx', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a213bc0df4a013bc1a614b30015', '2', '40288a213bc0df4a013bc1a3203a0014', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf3bd4ccd5013bd53577240012', '2', '40288acf3bd4ccd5013bd5353eaf0011', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa8ab6c0005', '3', '402880a92113a9b0012113e614fd05121', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa8ab870006', '3', '402880a92113a9b0012113e614fd05141', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa8ab8c0007', '3', '402880a92113a9b0012113e614fd05151', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa8ab900008', '3', '402880a92113a9b0012113e614fd05161', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa8f8d00009', '3', '402880a92113a9b0012113e614fd05162', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa8f8d4000a', '3', '402880a92113a9b0012113e614fd05163', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa8f8d8000b', '3', '402880a92113a9b0012113e614fd0516x', '1');
commit;
prompt 300 records committed...
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa8f8dc000c', '3', '402880a92113a9b0012113e614fd0516y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa9262e000d', '3', '402880a92113a9b0012113e614fd05152', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa92633000e', '3', '402880a92113a9b0012113e614fd05153', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa92638000f', '3', '402880a92113a9b0012113e614fd0515x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa9263c0010', '3', '402880a92113a9b0012113e614fd0515y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa926410011', '3', '402880a92113a9b0012113e614fd0515z', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa9515b0012', '3', '402880a92113a9b0012113e614fd05142', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa9515f0013', '3', '402880a92113a9b0012113e614fd05143', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa951630014', '3', '402880a92113a9b0012113e614fd0514x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa951670015', '3', '402880a92113a9b0012113e614fd0514y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa9516c0016', '3', '402880a92113a9b0012113e614fd0514z', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa98ca40017', '3', '402880a92113a9b0012113e614fd05122', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa98ca80018', '3', '402880a92113a9b0012113e614fd05123', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa98cad0019', '3', '402880a92113a9b0012113e614fd0512x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa98cb2001a', '3', '402880a92113a9b0012113e614fd0512y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93bda6179013bdaa98cb6001b', '3', '402880a92113a9b0012113e614fd0512z', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85525c0081', '1', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ccf3ace423b013ace85529b008e', '1', '402880a92113a9b0012113e614fd0514', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93b6455a6013b6486c8c7000f', '3', '402880a92113a9b0012113e614fd05112', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333b94c35e013b997956b7012e', '2', '40288a7b37dff33c0137dffeea490002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333b94c35e013b997def1c0131', '1', '4028937b38ea06c00138ea5c32420005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333b94c35e013b997956a8012d', '2', '40288a7b37dff33c0137dffeea490001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c385644df0067', '1', '40288a213bc1aa14013bc1b70dea0006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9e3e010073', '402883183bbd7a83013bbd9cd98f0001', '4028937b384132ff01384142ce790003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402883183bbd7a83013bbd9e7ba60074', '402883183bbd7a83013bbd9d2ea4003a', '4028937b384132ff01384142ce790004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93be53d86013be54e4239004d', '1', 'ff8080813b0ff864013b1901c5e80095', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288ae93be53d86013be55435a60055', '1', '40288a7b37e4d1a30137e4f7e0e90002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c088ad4a70006', '3', '402880a92113a9b0012113e614fd0507', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c088ad4b70007', '3', '4028937b384132ff01384142ce790005', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c088ad4b70008', '3', '4028937b384132ff01384142ce790006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c088ad4b70009', '3', '4028937b384132ff01384142ce790007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288acf3c1d3df0013c1d6cf7bd0008', '3', '40288acf3c1d3df0013c1d6b5efc0007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c08a1c94d000c', '3', '4028937b384132ff01384142ce79000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c08a1f4a3000d', '3', '4028937b384132ff01384142ce790009', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c08c8dd93001a', '3', '4028937b384132ff01384142ce790008', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c0505fa013c095abeda001f', '3', '4028937b384132ff01384142ce79000d', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c1f794a013c2369ae500003', '3', '40288acf3c1d3df0013c1d6b5efc0017', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c385644c00066', '1', '40288a213bc0df4a013bc1a3203a0014', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c385644df0068', '1', '40288a213bc1aa14013bc1b8e9ce0007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c385644df0069', '1', '40288a213bc1aa14013bc1cfb9960015', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c385644df006a', '1', '40288a213be6d699013be6df5d170002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c385644df006b', '1', '40288a213be6d699013be6e0dadd0003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f3160300a6', 'R001', '40284a8136cd5bee0136cdccf8fb0007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f3161300a7', 'R001', '40288a7b394207de0139421ac19f000b', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f3161300a8', 'R001', '40288a7b394207de0139421bb582000c', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f4fe5b00a9', 'R014', '402880a92113a9b0012113e614fd0401', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f4fe5b00aa', 'R014', '402880a92113a9b0012113e614fd0402', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f4fe5b00ab', 'R014', '402880a92113a9b0012113e614fd0502', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f4fe6a00ac', 'R014', '402880a92113a9b0012113e614fd0503', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f4fe6a00ad', 'R014', '402880a92113a9b0012113e62c8c0648', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f4fe6a00ae', 'R014', '402880a92113a9b0012113e62c8c0649', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f4fe6a00af', 'R014', '402880a92113a9b0012113e62c8c0650', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f4fe6a00b0', 'R014', '402880a92113a9b0012113e62c8c0651', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f4fe6a00b1', 'R014', '402880a92113a9b0012113e62c8c0660', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f4fe8a00b2', 'R014', '402880a92113a9b0012113e62c8c0661', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f598e500b3', 'R008', '40284a8136cd5bee0136cdccf8fb0011', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f598e500b4', 'R008', '40284a8136cd5bee0136cdccf8fb0012', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f598e500b5', 'R008', '40284a8136cd5bee0136cdccf8fb0013', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f598e500b6', 'R008', '40284a8136cd5bee0136cdccf8fb0014', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f6060700b7', 'R013', '402880a91e5d1ff2011e5dee91a00003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f6060700b8', 'R013', '402880a91e5d1ff2011e5dee91a00004', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f6060700b9', 'R013', '402880a91e5d1ff2011e5dee91a00010', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a213bc1aa14013bc1bcbbc5000a', '2', '40288a213bc1aa14013bc1b70dea0006', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a213bc1aa14013bc1bcbbd5000b', '2', '40288a213bc1aa14013bc1b8e9ce0007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a213bdc8c29013bdca3fcbc0009', '2', '40288a213bc1aa14013bc1cfb9960015', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f6060700ba', 'R013', '402880a91e5d1ff2011e5dee91a00011', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f6060700bb', 'R013', '402880a91e5d1ff2011e5dee91a00012', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f6061600bc', 'R013', '402880a91e5d1ff2011e5dee91a00013', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b6300bd', 'R011', '402880a92113a9b0012113e614fd05111', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b6300be', 'R011', '402880a92113a9b0012113e614fd05112', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b6300bf', 'R011', '402880a92113a9b0012113e614fd05113', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b7300c0', 'R011', '402880a92113a9b0012113e614fd05121', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b7300c1', 'R011', '402880a92113a9b0012113e614fd05122', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b7300c2', 'R011', '402880a92113a9b0012113e614fd05123', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b7300c3', 'R011', '402880a92113a9b0012113e614fd0512x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b7300c4', 'R011', '402880a92113a9b0012113e614fd0512y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b7300c5', 'R011', '402880a92113a9b0012113e614fd0512z', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b7300c6', 'R011', '402880a92113a9b0012113e614fd05152', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b8200c7', 'R011', '402880a92113a9b0012113e614fd05153', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b8200c8', 'R011', '402880a92113a9b0012113e614fd0515z', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f76b8200c9', 'R011', '4028937b384132ff01384142ce790010', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f7b25f00ca', 'R011', '402880a92113a9b0012113e614fd05151', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f7b25f00cb', 'R011', '402880a92113a9b0012113e614fd0515x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f7b25f00cc', 'R011', '402880a92113a9b0012113e614fd0515y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8044800cd', 'R011', '402880a92113a9b0012113e614fd0507', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8044800ce', 'R011', '40288acf3c1d3df0013c1d6b5efc0007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8045700cf', 'R011', '40288acf3c1d3df0013c1d6b5efc0017', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8045700d0', 'R011', '4028937b384132ff01384142ce790007', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8045700d1', 'R011', '4028937b384132ff01384142ce790008', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f83a8a00d2', 'R011', '402880a92113a9b0012113e614fd0516x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a11400d3', 'R011', '402880a92113a9b0012113e614fd05141', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a11400d4', 'R011', '402880a92113a9b0012113e614fd05142', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a11400d5', 'R011', '402880a92113a9b0012113e614fd05143', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a11400d6', 'R011', '402880a92113a9b0012113e614fd0514x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a11400d7', 'R011', '402880a92113a9b0012113e614fd0514y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a12400d8', 'R011', '402880a92113a9b0012113e614fd0514z', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a12400d9', 'R011', '402880a92113a9b0012113e614fd05161', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a12400da', 'R011', '402880a92113a9b0012113e614fd05162', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a12400db', 'R011', '402880a92113a9b0012113e614fd0516y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a13400dc', 'R011', '4028937b384132ff01384142ce79000c', '1');
commit;
prompt 400 records committed...
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a13400dd', 'R011', '4028937b384132ff01384142ce79000d', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8a13400de', 'R011', '4028937b384132ff01384142ce79000e', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8e89d00df', 'R011', '402880a92113a9b0012113e614fd0506', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8e89d00e0', 'R011', '402880a92113a9b0012113e614fd0511x', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8e89d00e1', 'R011', '402880a92113a9b0012113e614fd0511xx', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f8e89d00e2', 'R011', '402880a92113a9b0012113e614fd0511y', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38f946da00e3', 'R011', '402880a92113a9b0012113e614fd05163', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38fa8d0600e4', 'R002', '40284a8136cd5bee0136cdccf8fb0009', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c38fa8d0600e5', 'R002', '40288a7b3946df47013946e3e3330001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704e16001a', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790900', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280e3013b', '3', '4028937b384132ff01384142ce790200', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280e3013c', '3', '4028937b384132ff01384142ce790300', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280e3013d', '3', '4028937b384132ff01384142ce790400', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280e3013e', '3', '4028937b384132ff01384142ce790500', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280e3013f', '3', '4028937b384132ff01384142ce790600', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280e30140', '3', '4028937b384132ff01384142ce790700', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280f20141', '3', '4028937b384132ff01384142ce790800', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280f20142', '3', '4028937b384132ff01384142ce790900', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280f20143', '3', '4028937b384132ff01384142ce790a00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280f20144', '3', '4028937b384132ff01384142ce790b00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280f20145', '3', '4028937b384132ff01384142ce790c00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf98c1c014b', 'R006', '40288af039aa11e90139aa14f0ed0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf9bf90014c', 'R006', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf9bf90014d', 'R006', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf9d79c014e', 'R006', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cfa4e52014f', 'R006', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cfa4e520150', 'R006', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704e16001d', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790c00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704df70015', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790200', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704e16001e', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790500', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704e16001f', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790d00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d70fc280025', 'ff8080813bd023d7013bd26c831f0170', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7128e60028', 'ff8080813bd023d7013bd26c831f0170', '402880a92113a9b0012113e614fd0402', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7203670029', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d720367002c', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790100', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d720367002d', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790200', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d720377002e', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790300', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7203770030', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790700', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7203860032', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790900', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7203860033', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790a00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7203860034', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790b00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7203860035', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790c00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7203a60036', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790500', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7203a60037', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790d00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7247f20038', 'ff8080813bd023d7013bd26b5b300135', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7247f20039', 'ff8080813bd023d7013bd26b5b300135', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7247f2003a', 'ff8080813bd023d7013bd26b5b300135', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7247f2003b', 'ff8080813bd023d7013bd26b5b300135', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d726b87003d', 'ff8080813bd023d7013bd26b5b300135', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d72b988003e', 'ff8080813bd023d7013bd26b5b300135', '402880a92113a9b0012113e614fd0401', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d72b988003f', 'ff8080813bd023d7013bd26b5b300135', '402880a92113a9b0012113e614fd0402', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7e46de0069', 'R007', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7e46de006a', 'R007', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7e46de006b', 'R007', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7e46de006c', 'R007', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d8008a30075', 'R004', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d8008a30076', 'R004', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d80fd15007b', 'R004', '4028937b384132ff01384142ce790600', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3c3809f6011b', 'R006', '4028937b384132ff01384142ce790400', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704de70012', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704de70013', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790400', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704de70011', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704de70014', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790100', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704df70016', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790300', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704df70017', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790600', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704df70018', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790700', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704e060019', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790800', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704e16001b', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790a00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d704e16001c', 'ff8080813bd023d7013bd26c831f0170', '4028937b384132ff01384142ce790b00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d70d95e0020', 'ff8080813bd023d7013bd26c831f0170', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d70d95e0021', 'ff8080813bd023d7013bd26c831f0170', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d70d95e0022', 'ff8080813bd023d7013bd26c831f0170', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d70d95e0023', 'ff8080813bd023d7013bd26c831f0170', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d70d95e0024', 'ff8080813bd023d7013bd26c831f0170', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d70fc280026', 'ff8080813bd023d7013bd26c831f0170', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7128e60027', 'ff8080813bd023d7013bd26c831f0170', '402880a92113a9b0012113e614fd0401', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d720367002a', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d720367002b', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790400', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d720377002f', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790600', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7203770031', 'ff8080813bd023d7013bd26b5b300135', '4028937b384132ff01384142ce790800', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d726b87003c', 'ff8080813bd023d7013bd26b5b300135', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7574c00040', 'ff8080813bd023d7013bd26b5b300135', '40288a7b37b79a350137ba3674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d77dc690042', 'R014', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d77dc690043', 'R014', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d77dc690044', 'R014', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d77dc690045', 'R014', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d78150d0047', 'R014', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7881160048', 'R013', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7881160049', 'R013', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d788116004a', 'R013', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d788116004b', 'R013', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d78a15f004c', 'R013', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d78a16f004d', 'R013', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7921ab004e', 'R012', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d794f240052', 'R012', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d79ccdf0057', 'R011', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d79ff3a0058', 'R011', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7aa993005b', 'R010', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7aa993005c', 'R010', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7aa993005d', 'R010', '40288af0397bb32a01397c1fb92b0002', '1');
commit;
prompt 500 records committed...
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7acb92005f', 'R010', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7b36fe0061', 'R009', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7b36fe0063', 'R009', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7d35fb0067', 'R007', '4028937b384132ff01384142ce790700', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7d35fb0068', 'R007', '4028937b384132ff01384142ce790c00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7e693b006d', 'R007', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7e693b006e', 'R007', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7ede7b006f', 'R005', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7ede7b0070', 'R005', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7ede7b0071', 'R005', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7ede8a0072', 'R005', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7f04230073', 'R005', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7f04230074', 'R005', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d8008a30077', 'R004', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d8008a30078', 'R004', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d80291b0079', 'R004', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d80291b007a', 'R004', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d80fd25007d', 'R004', '4028937b384132ff01384142ce790800', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d80fd34007f', 'R004', '4028937b384132ff01384142ce790a00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d820b680089', 'R002', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d82a314008d', 'R001', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d82c62d0091', 'R001', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d82c62d0092', 'R001', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d8317a80093', '3', '4028937b384132ff01384142ce790d00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280d30138', '3', '40288a7b392d020501392d1226330002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280d30139', '3', '4028937b384132ff01384142ce790002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf280d3013a', '3', '4028937b384132ff01384142ce790100', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39371b670115', 'R006', '402880a92113a9b0012113e615e70512', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39371b670116', 'R006', '402880a92113a9b0012113e615e70513', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39371b670117', 'R006', '40288ccf390fbbea01390fdd5eac0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c39371b670118', 'R006', '40288ccf390fbbea01390fe019670003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3c47a12c011f', 'R006', '4028937b384132ff01384142ce790002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d8409c80096', '1', '40288a7b37b79a350137ba5674d60036', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d8409b90094', '1', '402880a92113a9b0012113e61532232', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d8409b90095', '1', '402880a92113a9b0012113e61532233', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cefa4b50136', 'R006', '4028937b384132ff01384142ce790003', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cf96c3f014a', 'R006', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3c4f9cb10122', 'R006', '402880a92113a9b0012113e614fd0401', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3c4f9cb10123', 'R006', '402880a92113a9b0012113e614fd0402', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d78150d0046', 'R014', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7921ab004f', 'R012', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7921ab0050', 'R012', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7921ab0051', 'R012', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d794f240053', 'R012', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d79ccd00054', 'R011', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d79ccd00055', 'R011', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d79ccd00056', 'R011', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d79ff490059', 'R011', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7a82e1005a', 'R010', '402880a92113a9b0012113e61532233', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7aa993005e', 'R010', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7acba20060', 'R010', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7b36fe0062', 'R009', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7b36fe0064', 'R009', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7b53500065', 'R009', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d7b53600066', 'R009', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d80fd15007c', 'R004', '4028937b384132ff01384142ce790700', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d80fd25007e', 'R004', '4028937b384132ff01384142ce790900', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d80fd340080', 'R004', '4028937b384132ff01384142ce790b00', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d818d5e0081', 'R003', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d818d5e0082', 'R003', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d818d5e0083', 'R003', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d818d5e0084', 'R003', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d81aaca0085', 'R003', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d81aaca0086', 'R003', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d820b680087', 'R002', '402880a92113a9b0012113e614fd0504', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d820b680088', 'R002', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d820b68008a', 'R002', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d822b35008b', 'R002', '40288af03975262401397563760e0001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d822b44008c', 'R002', '40288af0397a96db01397ac80ae6000a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d82a314008e', 'R001', '402880a92113a9b0012113e614fd0505', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d82a314008f', 'R001', '40288af0397bb32a01397c1fb92b0002', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d82a3140090', 'R001', '40288af039adb9090139adbee022001a', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3d3c72d10168', 'R006', '4028937b384132ff01384142ce790001', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c37d0c4013c3cee40430132', 'R006', '4028937b384132ff01384142ce790500', '1');
insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('40288a333c3d4471013c3d655f61000d', 'R006', '40288a333c3d4471013c3d646273000c', '1');
commit;
prompt 574 records loaded
prompt Enabling foreign key constraints for PE_PRIORITY...
alter table PE_PRIORITY enable constraint FK_PE_PRIOR_REFERENCE_PE_PRI_C;
prompt Enabling triggers for PE_PRI_CATEGORY...
alter table PE_PRI_CATEGORY enable all triggers;
prompt Enabling triggers for PE_PRIORITY...
alter table PE_PRIORITY enable all triggers;
prompt Enabling triggers for PE_PRI_ROLE...
alter table PE_PRI_ROLE enable all triggers;
prompt Enabling triggers for PR_PRI_ROLE...
alter table PR_PRI_ROLE enable all triggers;
set feedback on
set define on
prompt Done.
