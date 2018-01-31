-- Add/modify columns 
alter table PR_EXAM_BOOKING add SCORE_EXAM_A NUMBER(4,1);
alter table PR_EXAM_BOOKING add SCORE_EXAM_B NUMBER(4,1);
-- Add comments to the columns 
comment on column PR_EXAM_BOOKING.SCORE_EXAM
  is '最终考试成绩';
comment on column PR_EXAM_BOOKING.SCORE_EXAM_A
  is 'A登分人记录';
comment on column PR_EXAM_BOOKING.SCORE_EXAM_B
  is 'B登分人记录';
  
-- Add/modify columns 
alter table PE_TCH_COURSE add FK_EXAM_SCORE_INPUT_USERA_ID VARCHAR2(50);
alter table PE_TCH_COURSE add FK_EXAM_SCORE_INPUT_USERB_ID VARCHAR2(50);
-- Add comments to the columns 
comment on column PE_TCH_COURSE.FK_EXAM_SCORE_INPUT_USERA_ID
  is '成绩录入人AID';
comment on column PE_TCH_COURSE.FK_EXAM_SCORE_INPUT_USERB_ID
  is '成绩录入人BID';
  
/*==============================================================*/
/* Table: PE_EXAM_SCORE_INPUT_USER                              */
/*==============================================================*/
create table PE_EXAM_SCORE_INPUT_USER  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   PASSWORD             VARCHAR2(50)                    not null,
   constraint PK_PE_EXAM_SCORE_INPUT_USER primary key (ID)
);

comment on table PE_EXAM_SCORE_INPUT_USER is
'等分人帐号';

comment on column PE_EXAM_SCORE_INPUT_USER.NAME is
'账户名';

comment on column PE_EXAM_SCORE_INPUT_USER.PASSWORD is
'密码';


alter table PE_TCH_COURSE
   add constraint FK_PE_TCH_C_FK_PE_TCH_PE_EXAMA foreign key (FK_EXAM_SCORE_INPUT_USERA_ID)
      references PE_EXAM_SCORE_INPUT_USER (ID);

alter table PE_TCH_COURSE
   add constraint FK_PE_TCH_C_FK_PE_TCH_PE_EXAMB foreign key (FK_EXAM_SCORE_INPUT_USERB_ID)
      references PE_EXAM_SCORE_INPUT_USER (ID);
      
      
alter table HOMEWORK_HISTORY
   drop constraint FK_HOMEWORK_REFERENCE_HOMEWORK;

alter table HOMEWORK_INFO
   drop constraint FK_HOMEWORK_REFERENCE_PE_TEACH;

alter table HOMEWORK_INFO
   drop constraint FK_HOMEWORK_REFERENCE_ENUM_C01;

alter table HOMEWORK_INFO
   drop constraint FK_HOMEWORK_REFERENCE_ENUM_C02;

drop table HOMEWORK_INFO cascade constraints;

/*==============================================================*/
/* Table: HOMEWORK_INFO                                         */
/*==============================================================*/
create table HOMEWORK_INFO  (
   ID                   VARCHAR2(50)                    not null,
   TITLE                VARCHAR2(50),
   CREATER              VARCHAR2(50),
   CREATDATE            DATE,
   FLAG_ISVALID         VARCHAR2(50),
   NOTE                 CLOB,
   GROUP_ID             VARCHAR2(50),
   STARTDATE            DATE,
   ENDDATE              DATE,
   FLAG_BAK             VARCHAR2(50),
   constraint PK_HOMEWORK_INFO primary key (ID)
);

comment on table HOMEWORK_INFO is
'上传作业信息表';

alter table HOMEWORK_INFO
   add constraint FK_HOMEWORK_REFERENCE_PE_TEACH foreign key (CREATER)
      references PE_TEACHER (ID);

alter table HOMEWORK_INFO
   add constraint FK_HOMEWORK_REFERENCE_ENUM_C01 foreign key (FLAG_ISVALID)
      references ENUM_CONST (ID);

alter table HOMEWORK_INFO
   add constraint FK_HOMEWORK_REFERENCE_ENUM_C02 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);
alter table HOMEWORK_HISTORY
   drop constraint FK_HOMEWORK_REFERENCE_HOMEWORK;

alter table HOMEWORK_HISTORY
   drop constraint FK_HOMEWORK_REFERENCE_ENUM_C03;

alter table HOMEWORK_HISTORY
   drop constraint FK_HOMEWORK_REFERENCE_ENUM_C04;

alter table HOMEWORK_HISTORY
   drop constraint FK_HOMEWORK_REFERENCE_PE_STUDE;

drop table HOMEWORK_HISTORY cascade constraints;

/*==============================================================*/
/* Table: HOMEWORK_HISTORY                                      */
/*==============================================================*/
create table HOMEWORK_HISTORY  (
   ID                   VARCHAR2(50)                    not null,
   USER_ID              VARCHAR2(50),
   TESTDATE             DATE,
   NOTE                 CLOB,
   HOMEWORK_ID          VARCHAR2(50),
   SCORE                VARCHAR2(50),
   FLAG_ISCHECK         VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   constraint PK_HOMEWORK_HISTORY primary key (ID)
);

comment on table HOMEWORK_HISTORY is
'上传作业历史表';

alter table HOMEWORK_HISTORY
   add constraint FK_HOMEWORK_REFERENCE_HOMEWORK foreign key (HOMEWORK_ID)
      references HOMEWORK_INFO (ID);

alter table HOMEWORK_HISTORY
   add constraint FK_HOMEWORK_REFERENCE_ENUM_C03 foreign key (FLAG_ISCHECK)
      references ENUM_CONST (ID);

alter table HOMEWORK_HISTORY
   add constraint FK_HOMEWORK_REFERENCE_ENUM_C04 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table HOMEWORK_HISTORY
   add constraint FK_HOMEWORK_REFERENCE_PE_STUDE foreign key (USER_ID)
      references PE_STUDENT (ID);


	
alter table PR_EXAM_BOOKING
   drop constraint FK_PR_EXAM__REFERENCE_ENUM_C02;

alter table PR_EXAM_BOOKING
   drop constraint FK_PR_EXAM__REFERENCE_ENUM_C03;	

alter table PR_EXAM_BOOKING drop column FLAG_BAK;
alter table PR_EXAM_BOOKING add FLAG_SCORE_STATUSA VARCHAR2(50);
alter table PR_EXAM_BOOKING add FLAG_SCORE_STATUSB VARCHAR2(50);

comment on column PR_EXAM_BOOKING.SCORE_EXAM_B
  is 'B登分人记录 成绩状态，0末录入，1正常，2缺考，3违纪，4作弊';
comment on column PR_EXAM_BOOKING.FLAG_SCORE_STATUSA
  is 'A登分人记录';
comment on column PR_EXAM_BOOKING.FLAG_SCORE_STATUSB
  is 'B登分人记录 成绩状态，0末录入，1正常，2缺考，3违纪，4作弊';

alter table PR_EXAM_BOOKING
   add constraint FK_PR_EXAM__REFERENCE_ENUM_C02 foreign key (FLAG_SCORE_STATUS)
      references ENUM_CONST (ID);

alter table PR_EXAM_BOOKING
   add constraint FK_PR_EXAM__REFERENCE_ENUM_C03 foreign key (FLAG_SCORE_STATUSA)
      references ENUM_CONST (ID);

alter table PR_EXAM_BOOKING
   add constraint FK_PR_EXAM__REFERENCE_ENUM_CO4 foreign key (FLAG_SCORE_STATUSB)
      references ENUM_CONST (ID);