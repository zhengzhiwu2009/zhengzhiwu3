insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('17003001', '≤πøº–≈œ¢', '17', '17003001', '/entity/exam/peBzzExamAgain.action', '1');

insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('17003001001', '≤πøº–≈œ¢', '17003001', '/entity/exam', 'peBzzExamAgain', 

'*');


insert into PR_PRI_ROLE (ID, FK_ROLE_ID, FK_PRIORITY_ID, FLAG_ISVALID)
values ('402880f3224ad8ff0154as54dfsd8ee8f0011', '3', '17003001001', '1');


insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('aa2880a91dadc133441aadfcf26b0002', 'Œ¥…Û∫À', '0', 'FlagExamAgainStatus', '1', sysdate, '≤πøº…Í«Î◊¥Ã¨');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('abb2880a91dad133441bbadfcf26b0002', '“—≥ı…Û', '1', 'FlagExamAgainStatus', '0', sysdate, '≤πøº…Í«Î◊¥Ã¨');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('ccb2880a91dad133441ccadfcf26b0002', '“—÷’…Û', '2', 'FlagExamAgainStatus', '0', sysdate, '≤πøº…Í«Î◊¥Ã¨');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('1qb2880a91dad133441ddadfcf26b0002', '≤µªÿ', '3', 'FlagExamAgainStatus', '0', sysdate, '≤πøº…Í«Î◊¥Ã¨');



-- Create table
create table PE_BZZ_EXAMAGAIN
(
  ID           VARCHAR2(50) not null,
  STUDENT_ID   VARCHAR2(50),
  STATUS       VARCHAR2(50),
  APPLY_DATE   DATE default sysdate,
  FIRST_DATE   DATE default sysdate,
  FINAL_DATE   DATE default sysdate,
  NOTE         VARCHAR2(100),
  EXAMBATCH_ID VARCHAR2(50)
)
tablespace SCETRAINING
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column PE_BZZ_EXAMLATE.ID
  is 'ID';
comment on column PE_BZZ_EXAMLATE.STUDENT_ID
  is '…Í«Î≤πøº—ß…˙ID';
comment on column PE_BZZ_EXAMLATE.STATUS
  is '…Í«Î≤πøº≈˙◊º◊¥Ã¨';
comment on column PE_BZZ_EXAMLATE.APPLY_DATE
  is '…Í«Î ±º‰';
comment on column PE_BZZ_EXAMLATE.FIRST_DATE
  is '≥ı…Û ±º‰';
comment on column PE_BZZ_EXAMLATE.FINAL_DATE
  is '÷’…Û ±º‰';
comment on column PE_BZZ_EXAMLATE.NOTE
  is '±∏◊¢';
comment on column PE_BZZ_EXAMLATE.EXAMBATCH_ID
  is 'øº ‘≈˙¥Œ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_BZZ_EXAMAGAIN
  add constraint PE_BZZ_EXAMAGAIN primary key (ID)
  using index 
  tablespace SCETRAINING
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table PE_BZZ_EXAMAGAIN
  add constraint PE_again_BZZ_EXAMBATCH_LID foreign key (EXAMBATCH_ID)
  references PE_BZZ_EXAMBATCH (ID)
  deferrable initially deferred;
alter table PE_BZZ_EXAMAGAIN
  add constraint PE_again_BZZ_STUDENT_LID foreign key (STUDENT_ID)
  references PE_BZZ_STUDENT (ID)
  deferrable initially deferred;
