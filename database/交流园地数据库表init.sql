
create table ENTITY_COURSE_ITEM
(
  ID      VARCHAR2(50),
  DAYI    VARCHAR2(10),
  GONGGAO VARCHAR2(10),
  TAOLUN  VARCHAR2(10),
  KAOSHI  VARCHAR2(10),
  ZUOYE   VARCHAR2(10),
  ZIYUAN  VARCHAR2(10),
  ZXDAYI  VARCHAR2(10),
  SMZUOYE VARCHAR2(10),
  ZICE    VARCHAR2(10),
  PINGJIA VARCHAR2(10),
  DAOHANG VARCHAR2(10),
  DAOXUE  VARCHAR2(10),
  SHIYAN  VARCHAR2(10),
  ZFX     VARCHAR2(10),
  BOKE    VARCHAR2(10)
)
;

create table INTERACTION_ANNOUNCE_INFO
(
  ID             VARCHAR2(50) not null,
  TITLE          VARCHAR2(200) not null,
  BODY           LONG not null,
  PUBLISHER_ID   VARCHAR2(50) not null,
  PUBLISHER_NAME VARCHAR2(100),
  PUBLISHER_TYPE VARCHAR2(50) not null,
  PUBLISH_DATE   DATE default sysdate not null,
  COURSE_ID      VARCHAR2(50) not null
);
alter table INTERACTION_ANNOUNCE_INFO
  add constraint INTERACTION_ANNOUNCE_INFO_PK primary key (ID);
  
create table INTERACTION_ANSWER_INFO
(
  ID           VARCHAR2(50) not null,
  QUESTION_ID  VARCHAR2(50) not null,
  BODY         LONG not null,
  PUBLISH_DATE DATE default sysdate not null,
  REUSER_ID    VARCHAR2(100) not null,
  REUSER_NAME  VARCHAR2(50) not null,
  REUSER_TYPE  VARCHAR2(50) not null
);
alter table INTERACTION_ANSWER_INFO
  add constraint INTERACTION_ANSWER_INFO_PK primary key (ID);
  
create table INTERACTION_QUESTION_INFO
(
  ID              VARCHAR2(50) not null,
  TITLE           VARCHAR2(400) not null,
  BODY            LONG not null,
  KEY             VARCHAR2(400) not null,
  PUBLISH_DATE    DATE default sysdate not null,
  COURSE_ID       VARCHAR2(50) not null,
  SUBMITUSER_ID   VARCHAR2(100) not null,
  SUBMITUSER_NAME VARCHAR2(100) not null,
  SUBMITUSER_TYPE VARCHAR2(50),
  RE_STATUS       VARCHAR2(50) not null
);
alter table INTERACTION_QUESTION_INFO
  add constraint INTERACTION_QUESTION_INFO_PK primary key (ID);

create table INTERACTION_TEACHCLASS_INFO
(
  ID            VARCHAR2(50) not null,
  TEACHCLASS_ID VARCHAR2(50) not null,
  TITLE         VARCHAR2(1000),
  CONTENT       LONG,
  STATUS        VARCHAR2(10) default '1',
  TYPE          VARCHAR2(10),
  PUBLISH_DATE  DATE default sysdate
);
alter table INTERACTION_TEACHCLASS_INFO
  add constraint INTERACTION_TEACHCLASS_PK primary key (ID);
  
create table ONLINEEXAM_COURSE_INFO
(
  ID             VARCHAR2(20) not null,
  GROUP_ID       VARCHAR2(50),
  BATCH_ID       VARCHAR2(20),
  NOTE           VARCHAR2(2000) not null,
  START_DATE     VARCHAR2(40) not null,
  END_DATE       VARCHAR2(40) not null,
  ISAUTOCHECK    VARCHAR2(20) not null,
  ISHIDDENANSWER VARCHAR2(20) not null,
  TITLE          VARCHAR2(400),
  STATUS         VARCHAR2(20),
  CREATUSER      VARCHAR2(100),
  CREATDATE      DATE default sysdate
);
alter table ONLINEEXAM_COURSE_INFO
  add constraint ONLINEEXAM_COURSE_INFO_PK primary key (ID);
  
create table ONLINEEXAM_COURSE_PAPER
(
  ID            VARCHAR2(20) not null,
  PAPER_ID      VARCHAR2(20),
  TESTCOURSE_ID VARCHAR2(20)
);
alter table ONLINEEXAM_COURSE_PAPER
  add constraint ONLINEEXAM_COURSE_PAPER_PK primary key (ID);
  
create table ONLINETEST_COURSE_INFO
(
  ID             VARCHAR2(50) not null,
  GROUP_ID       VARCHAR2(50),
  BATCH_ID       VARCHAR2(50),
  NOTE           VARCHAR2(2000) not null,
  START_DATE     VARCHAR2(40) not null,
  END_DATE       VARCHAR2(40) not null,
  ISAUTOCHECK    VARCHAR2(20) not null,
  ISHIDDENANSWER VARCHAR2(20) not null,
  TITLE          VARCHAR2(400),
  STATUS         VARCHAR2(20),
  CREATUSER      VARCHAR2(100),
  CREATDATE      DATE default sysdate
);
alter table ONLINETEST_COURSE_INFO
  add constraint ONLINETEST_COURSE_INFO_PK primary key (ID);
  
create table ONLINETEST_COURSE_PAPER
(
  ID            VARCHAR2(20) not null,
  PAPER_ID      VARCHAR2(20) not null,
  TESTCOURSE_ID VARCHAR2(20) not null
);
alter table ONLINETEST_COURSE_PAPER
  add constraint ONLINETEST_COURSE_PAPER_PK primary key (ID);
  
create table TEST_EXAMPAPER_HISTORY
(
  ID           VARCHAR2(50) not null,
  USER_ID      VARCHAR2(100) not null,
  TEST_DATE    DATE not null,
  TEST_RESULT  LONG,
  TESTPAPER_ID VARCHAR2(20) not null,
  SCORE        VARCHAR2(20) not null,
  ISCHECK      VARCHAR2(20) default '0' not null
);
alter table TEST_EXAMPAPER_HISTORY
  add constraint EXAMPAPER_HISTORY_PK primary key (ID);
  
create table TEST_EXAMPAPER_INFO
(
  ID        VARCHAR2(50) not null,
  TITLE     VARCHAR2(200) not null,
  CREATUSER VARCHAR2(50) not null,
  CREATDATE DATE not null,
  STATUS    VARCHAR2(10) not null,
  NOTE      VARCHAR2(2000) not null,
  TIME      VARCHAR2(20) not null,
  TYPE      VARCHAR2(20) not null,
  GROUP_ID  VARCHAR2(50) not null
);
alter table TEST_EXAMPAPER_INFO
  add primary key (ID);
  
create table TEST_EXPERIMENTPAPER_HISTORY
(
  ID           VARCHAR2(50) not null,
  USER_ID      VARCHAR2(100) not null,
  TEST_DATE    DATE not null,
  TEST_RESULT  LONG,
  TESTPAPER_ID VARCHAR2(50) not null,
  SCORE        VARCHAR2(20) not null,
  ISCHECK      VARCHAR2(20) default '0'
);
alter table TEST_EXPERIMENTPAPER_HISTORY
  add constraint EXPERIMENTPAPER_HISTORY_PK primary key (ID);
  
create table TEST_EXPERIMENTPAPER_INFO
(
  ID        VARCHAR2(50) not null,
  TITLE     VARCHAR2(200) not null,
  CREATUSER VARCHAR2(50) not null,
  CREATDATE DATE not null,
  STATUS    VARCHAR2(10) not null,
  NOTE      VARCHAR2(2000),
  COMMENTS  VARCHAR2(2000),
  STARTDATE VARCHAR2(40) not null,
  ENDDATE   VARCHAR2(40) not null,
  GROUP_ID  VARCHAR2(50) not null,
  TYPE      VARCHAR2(20)
);
alter table TEST_EXPERIMENTPAPER_INFO
  add primary key (ID);
  
create table TEST_HOMEWORKPAPER_HISTORY
(
  ID           VARCHAR2(50) not null,
  USER_ID      VARCHAR2(100) not null,
  TEST_DATE    DATE default sysdate not null,
  TEST_RESULT  LONG,
  TESTPAPER_ID VARCHAR2(50) not null,
  SCORE        VARCHAR2(20) default '0' not null,
  ISCHECK      VARCHAR2(20) default '0'
);
alter table TEST_HOMEWORKPAPER_HISTORY
  add constraint HOMEWORKPAPER_HISTORY_PK primary key (ID);
  
create table TEST_HOMEWORKPAPER_INFO
(
  ID        VARCHAR2(50) not null,
  TITLE     VARCHAR2(200) not null,
  CREATUSER VARCHAR2(50) not null,
  CREATDATE DATE not null,
  STATUS    VARCHAR2(10) not null,
  NOTE      VARCHAR2(2000),
  COMMENTS  VARCHAR2(2000),
  STARTDATE VARCHAR2(40) not null,
  ENDDATE   VARCHAR2(40) not null,
  GROUP_ID  VARCHAR2(50) not null,
  TYPE      VARCHAR2(20)
);
alter table TEST_HOMEWORKPAPER_INFO
  add primary key (ID);
  
create table TEST_LORE_DIR
(
  ID        VARCHAR2(50),
  NAME      VARCHAR2(100),
  NOTE      LONG,
  FATHERDIR VARCHAR2(50) not null,
  CREATDATE DATE,
  GROUP_ID  VARCHAR2(50)
);

create table TEST_LORE_INFO
(
  ID        VARCHAR2(10) not null,
  NAME      VARCHAR2(100),
  CREATDATE DATE,
  CONTENT   LONG,
  LOREDIR   VARCHAR2(10),
  CREATERID VARCHAR2(10),
  ACTIVE    VARCHAR2(1) not null
);

create table TEST_PAPERPOLICY_INFO
(
  ID         VARCHAR2(50) not null,
  TITLE      VARCHAR2(200) not null,
  POLICYCORE LONG not null,
  CREATUSER  VARCHAR2(50) not null,
  CREATDATE  DATE not null,
  STATUS     VARCHAR2(10) not null,
  NOTE       VARCHAR2(2000),
  TESTTIME   VARCHAR2(20),
  GROUP_ID   VARCHAR2(50),
  TYPE       VARCHAR2(50)
);
alter table TEST_PAPERPOLICY_INFO
  add constraint PAPERPOLICY_INFO primary key (ID);
  
create table TEST_PAPERQUESTION_INFO
(
  ID             VARCHAR2(20) not null,
  CREATUSER      VARCHAR2(20) not null,
  CREATDATE      DATE not null,
  DIFF           VARCHAR2(10) not null,
  QUESTIONCORE   LONG not null,
  TITLE          VARCHAR2(1000) not null,
  SERIAL         VARCHAR2(20) not null,
  SCORE          VARCHAR2(20) not null,
  LORE           VARCHAR2(20) not null,
  COGNIZETYPE    VARCHAR2(100) not null,
  PURPOSE        VARCHAR2(100) not null,
  REFERENCESCORE VARCHAR2(40) not null,
  REFERENCETIME  VARCHAR2(40) not null,
  STUDENTNOTE    VARCHAR2(2000),
  TEACHERNOTE    VARCHAR2(2000),
  TESTPAPER_ID   VARCHAR2(20) not null,
  TYPE           VARCHAR2(20)
);
alter table TEST_PAPERQUESTION_INFO
  add primary key (ID);
  
create table TEST_STOREQUESTION_INFO
(
  ID             VARCHAR2(20) not null,
  CREATUSER      VARCHAR2(20) not null,
  CREATDATE      DATE not null,
  DIFF           VARCHAR2(10) not null,
  QUESTIONCORE   LONG not null,
  TITLE          VARCHAR2(1000) not null,
  KEYWORD        VARCHAR2(200) not null,
  LORE           VARCHAR2(20) not null,
  COGNIZETYPE    VARCHAR2(100) not null,
  PURPOSE        VARCHAR2(100) not null,
  REFERENCESCORE VARCHAR2(40) not null,
  REFERENCETIME  VARCHAR2(40) not null,
  STUDENTNOTE    VARCHAR2(2000),
  TEACHERNOTE    VARCHAR2(2000),
  TYPE           VARCHAR2(20)
);
alter table TEST_STOREQUESTION_INFO
  add primary key (ID);
  
create table TEST_TESTPAPER_HISTORY
(
  ID           VARCHAR2(50) not null,
  USER_ID      VARCHAR2(100) not null,
  TEST_DATE    DATE not null,
  TEST_RESULT  LONG,
  TESTPAPER_ID VARCHAR2(50) not null,
  SCORE        VARCHAR2(20) not null,
  ISCHECK      VARCHAR2(20) default '0' not null
);
alter table TEST_TESTPAPER_HISTORY
  add constraint TESTPAPER_HISTORY_PK primary key (ID);
  
create table TEST_TESTPAPER_INFO
(
  ID        VARCHAR2(50) not null,
  TITLE     VARCHAR2(200) not null,
  CREATUSER VARCHAR2(50) not null,
  CREATDATE DATE not null,
  STATUS    VARCHAR2(10) not null,
  NOTE      VARCHAR2(2000) not null,
  TIME      VARCHAR2(20) not null,
  TYPE      VARCHAR2(20) not null,
  GROUP_ID  VARCHAR2(50) not null
);
alter table TEST_TESTPAPER_INFO
  add primary key (ID);
  
create table RESOURCE_DIR
(
  ID        VARCHAR2(10) not null,
  NAME      VARCHAR2(50),
  PARENT    VARCHAR2(10),
  NOTE      VARCHAR2(200),
  STATUS    VARCHAR2(10),
  ISINHERIT VARCHAR2(10),
  KEYID     VARCHAR2(100)
);
alter table RESOURCE_DIR
  add constraint RESOURCE_DIR primary key (ID);
  
create table RESOURCE_INFO
(
  ID          VARCHAR2(10) not null,
  TITLE       VARCHAR2(50),
  LANGUAGE    VARCHAR2(50),
  DESCRIPTION VARCHAR2(200),
  KEYWORDS    VARCHAR2(50),
  CREATUSER   VARCHAR2(100),
  TYPE_ID     VARCHAR2(10),
  DIR_ID      VARCHAR2(10),
  CONTENT     LONG,
  DETAIL      VARCHAR2(2000),
  STATUS      VARCHAR2(1) default '1'
);
alter table RESOURCE_INFO
  add constraint RESOURCE_INFO_PK primary key (ID);
  
create table RESOURCE_RIGHT
(
  ID      VARCHAR2(50) not null,
  USER_ID VARCHAR2(100),
  DIR_ID  VARCHAR2(50)
);
alter table RESOURCE_RIGHT
  add constraint RESOURCE_RIGHT_PK primary key (ID);
  
create table RESOURCE_TYPE
(
  ID     VARCHAR2(10) not null,
  NAME   VARCHAR2(50),
  NOTE   VARCHAR2(200),
  STATUS VARCHAR2(20),
  XML    LONG
);
alter table RESOURCE_TYPE
  add constraint RESOURCE_TYPE_PK primary key (ID);
commit;


-- Create sequence 
create sequence S_ANNOUNCE_INFO_ID
minvalue 1
maxvalue 999999999999999999999
start with 1
increment by 1
cache 20;

-- Create sequence 
create sequence S_QUESTION_INFO_ID
minvalue 1
maxvalue 999999999999999999999
start with 1
increment by 1
cache 20;



-- Create sequence 
create sequence s_answer_info_id
minvalue 1
maxvalue 999999999999999999999
start with 1
increment by 1
cache 20;


-- Create sequence 
create sequence S_TEST_HWPAPER_HISTORY_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_TEST_LORE_DIR_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_TEST_LORE_INFO
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_TEST_LORE_INFO_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_TEST_PAPERPOLICY_INFO
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_TEST_PAPERQUESTION_INFO
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_TEST_PAPER_INFO
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_TEST_STOREQUESTION_INFO
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_TEST_TESTPAPER_HISTORY_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;


-- Create sequence 
create sequence S_INTERACTION_TEACHCLASS_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_ONLINEEXAM_COURSE_INFO_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_ONLINEEXAM_COURSE_PAPER_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_ONLINETEST_COURSE_INFO_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_ONLINETEST_COURSE_PAPER_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

-- Create sequence 
create sequence S_TEST_EMPAPER_HISTORY_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 1
increment by 1
nocache;

create sequence S_RESOURCE_INFO_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 29
increment by 1
nocache;

create sequence S_RESOURCE_DIR_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 12
increment by 1
nocache;

create sequence S_RESOURCE_TYPE_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 22
increment by 1
cache 20;

-- Create sequence 
create sequence S_RESOURCE_RIGHT_ID
minvalue 0
maxvalue 9999999999999999999999999
start with 20
increment by 1
nocache;                