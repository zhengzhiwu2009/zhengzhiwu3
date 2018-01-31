/*==============================================================*/
/* DBMS name:      ORACLE Version 9i                            */
/* Created on:     2009-6-5 16:01:20                            */
/*==============================================================*/


alter table HOMEWORK_HISTORY
   drop constraint FK_HOMEWORK_REFERENCE_HOMEWORK;

alter table HOMEWORK_HISTORY
   drop constraint FK_HOMEWORK_REFERENCE_ENUM_C03;

alter table HOMEWORK_HISTORY
   drop constraint FK_HOMEWORK_REFERENCE_ENUM_C04;

alter table HOMEWORK_HISTORY
   drop constraint FK_HOMEWORK_REFERENCE_PE_STUDE;

alter table HOMEWORK_INFO
   drop constraint FK_HOMEWORK_REFERENCE_PE_TEACH;

alter table HOMEWORK_INFO
   drop constraint FK_HOMEWORK_REFERENCE_ENUM_C01;

alter table HOMEWORK_INFO
   drop constraint FK_HOMEWORK_REFERENCE_ENUM_C02;

alter table PE_BULLETIN
   drop constraint FK_BULLETIN_REFERENCE_ENUM_CO1;

alter table PE_BULLETIN
   drop constraint FK_PE_BULLE_FK_BULLET_ENUM_CON;

alter table PE_BULLETIN
   drop constraint FK_PE_BULLE_FK_BULLET_PE_MANAG;

alter table PE_DOCUMENT
   drop constraint FK_DOCUMENT_REFERENCE_ENUM_CO1;

alter table PE_DOCUMENT
   drop constraint FK_DOCUMENT_REFERENCE_ENUM_CO2;

alter table PE_DOCUMENT
   drop constraint FK_PE_DOCUM_FK_DOCUME_SSO_USER;

alter table PE_EXAM_MAINCOURSE_NO
   drop constraint FK_PE_EXAM__FK_PE_EXA_PE_SEM24;

alter table PE_EXAM_MAINCOURSE_ROOM
   drop constraint FK_PE_EXAM__FK_PE_EXA_PE_SIT2;

alter table PE_EXAM_MAINCOURSE_ROOM
   drop constraint FK_PE_EXAM__FK_PE_EXA_PR_EXAM_;

alter table PE_EXAM_NO
   drop constraint FK_PE_EXAM__FK_PE_EXA_PE_SEM21;

alter table PE_EXAM_PATROL
   drop constraint FK_PE_EXAM__REFERENCE_ENUM_CO2;

alter table PE_EXAM_PATROL
   drop constraint FK_PE_EXAM__REFERENCE_ENUM_CO3;

alter table PE_EXAM_PATROL
   drop constraint FK_PE_EXAM__FK_PE_EXA_PE_SITE;

alter table PE_EXAM_PATROL
   drop constraint FK_PE_EXAM__FK_PE_PAT_ENUM_CON;

alter table PE_EXAM_ROOM
   drop constraint FK_PE_EXAM__FK_PE_EXA_PE_EXAM_;

alter table PE_EXAM_ROOM
   drop constraint FK_PE_EXAM__FK_PE_EXA_PE_SIT1;

alter table PE_FEE_BATCH
   drop constraint FK_PE_FEE_B_FK_PE_FEE_PE_SITE;

alter table PE_FEE_BATCH
   drop constraint FK_PE_FEE_B_REFERENCE_PE_SITEM;

alter table PE_FEE_BATCH
   drop constraint FK_PE_FEE_B_REFERENCE_ENUM_CO1;

alter table PE_FEE_BATCH
   drop constraint FK_PE_FEE_B_REFERENCE_ENUM_CO2;

alter table PE_FEE_LEVEL
   drop constraint FK_PE_FEE_L_REFERENCE_ENUM_CO1;

alter table PE_FEE_LEVEL
   drop constraint FK_PE_FEE_L_FK_PE_FEE_ENUM_CON;

alter table PE_INFO_NEWS
   drop constraint FK_PE_INFO__FK_INFO_N_PE_INFO_;

alter table PE_INFO_NEWS
   drop constraint FK_PE_INFO__FK_INFO_N_PE_MANAG;

alter table PE_INFO_NEWS
   drop constraint FK_PE_INFO__REFERENCE_ENUM_CO1;

alter table PE_INFO_NEWS
   drop constraint FK_PE_INFO__FK_PE_INF_ENUM_CON;

alter table PE_MAJOR
   drop constraint FK_PE_MAJOR_FK_PE_MAJ_ENUM_CON;

alter table PE_MAJOR
   drop constraint FK_PE_MAJOR_REFERENCE_ENUM_CON;

alter table PE_MANAGER
   drop constraint FK_PE_MANAG_FK_PE_MAN_ENUM_CON;

alter table PE_MANAGER
   drop constraint FK_PE_MANAG_REFERENCE_SSO_USER;

alter table PE_MANAGER
   drop constraint FK_PE_MANAG_REFERENCE_ENUM_CO2;

alter table PE_PC_EXERCISE
   drop constraint FK_PE_PC_EX_FK_PE_PC__PR_PC_OP;

alter table PE_PC_NEWS
   drop constraint FK_PE_PC_NE_REFERENCE_ENUM_CO1;

alter table PE_PC_NEWS
   drop constraint FK_PE_PC_NE_FK_PE_PC__ENUM_CON;

alter table PE_PC_NEWS
   drop constraint FK_PE_PC_NE_FK_PE_PC__SSO_USER;

alter table PE_PC_NOTE
   drop constraint FK_PE_PC_NO_FK_PE_PC__PE_PC_TE;

alter table PE_PC_NOTE
   drop constraint FK_PE_PC_NO_FK_PE_PC__PR_PC_OP;

alter table PE_PC_OPENCOURSE_COURSEWARE
   drop constraint FK_PE_PC_OP_FK_PE_PC__PR_PC_O1;

alter table PE_PC_OPENCOURSE_RESOURCE
   drop constraint FK_PE_PC_OP_FK_PE_PC__PR_PC_OP;

alter table PE_PC_STUDENT
   drop constraint FK_PE_PC_ST_FK_PE_PC__SSO_USER;

alter table PE_PC_TEACHER
   drop constraint FK_PE_PC_TE_REFERENCE_ENUM_CO1;

alter table PE_PC_TEACHER
   drop constraint FK_PE_PC_TE_FK_PE_PC__ENUM_CON;

alter table PE_PC_TEACHER
   drop constraint FK_PE_PC_TE_FK_PE_PC__SSO_USER;

alter table PE_PRIORITY
   drop constraint FK_PE_PRIOR_REFERENCE_PE_PRI_C;

alter table PE_PRI_CATEGORY
   drop constraint FK_PE_PRI_C_REFERENCE_PE_PRI_C;

alter table PE_PRI_ROLE
   drop constraint FK_PE_PRI_R_REFERENCE_ENUM_CO1;

alter table PE_PRI_ROLE
   drop constraint FK_PE_PRI_R_FK_PE_PRI_ENUM_CON;

alter table PE_RECRUITPLAN
   drop constraint FK_PE_RECRU_REFERENCE_PE_GRADE;

alter table PE_REC_EXAMCOURSE
   drop constraint FK_PE_REC_E_REFERENCE_ENUM_C01;

alter table PE_REC_EXAMCOURSE
   drop constraint FK_PE_REC_E_REFERENCE_ENUM_C02;

alter table PE_REC_ROOM
   drop constraint FK_PE_REC_R_FK_PE_REC_PE_RECRU;

alter table PE_REC_ROOM
   drop constraint FK_PE_REC_R_FK_PE_REC_PE_SITE;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_FK_PE_REC_ENUM_CO1;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_FK_PE_REC_ENUM_CO2;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_FK_PE_REC_ENUM_CO3;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_REFERENCE_ENUM_CO4;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_REFERENCE_ENUM_CO5;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_REFERENCE_ENUM_CO6;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_REFERENCE_ENUM_CO7;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_REFERENCE_ENUM_CO8;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_REFERENCE_ENUM_CO9;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_FK_PE_REC_ENUM_CON;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_FK_PE_REC_PE_REC_R;

alter table PE_REC_STUDENT
   drop constraint FK_PE_REC_S_REFERENCE_PR_REC_P;

alter table PE_SITE
   drop constraint FK_PE_SITE_FK_PE_SIT_ENUM_CO1;

alter table PE_SITE
   drop constraint FK_PE_SITE_FK_PE_SIT_ENUM_CON;

alter table PE_SITE
   drop constraint FK_PE_SITE_FK_PE_SIT_PE_SITE;

alter table PE_SITEMANAGER
   drop constraint FK_PE_SITEM_REFERENCE_ENUM_CO2;

alter table PE_SITEMANAGER
   drop constraint FK_PE_SITEM_FK_PE_SIT_ENUM_CON;

alter table PE_SITEMANAGER
   drop constraint FK_PE_SITEM_REFERENCE_PE_SITE;

alter table PE_SITEMANAGER
   drop constraint FK_PE_SITEM_REFERENCE_SSO_USER;

alter table PE_SMS_INFO
   drop constraint FK_SMS_INFO_REFERENCE_ENUM_CO1;

alter table PE_SMS_INFO
   drop constraint FK_PE_SMS_I_FK_SMS_IN_ENUM_CON;

alter table PE_SMS_INFO
   drop constraint FK_PE_SMS_I_FK_SMS_IN_PE_SITE;

alter table PE_SMS_SYSTEMPOINT
   drop constraint FK_SMS_SYST_REFERENCE_ENUM_CO1;

alter table PE_SMS_SYSTEMPOINT
   drop constraint FK_PE_SMS_S_FK_SMS_SY_ENUM_CON;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_C10;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_C17;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_C18;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_C19;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_CO1;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_CO2;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_CO3;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_CO4;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_CO5;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_CO6;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_ENUM_CO9;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_PE_EDUTY;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_PE_FEE_L;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_PE_GRADE;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_PE_MAJOR;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_PE_SITE;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_FK_PE_STU_PR_STUDE;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_SSO_USER;

alter table PE_STUDENT
   drop constraint FK_PE_STUDE_REFERENCE_PE_REC_S;

alter table PE_TCH_BOOK
   drop constraint FK_PE_TCH_B_REFERENCE_ENUM_CO1;

alter table PE_TCH_BOOK
   drop constraint FK_PE_TCH_B_REFERENCE_ENUM_CO2;

alter table PE_TCH_COURSE
   drop constraint FK_PE_TCH_C_REFERENCE_ENUM_C11;

alter table PE_TCH_COURSE
   drop constraint FK_PE_TCH_C_FK_PE_TCH_ENUM_CON;

alter table PE_TCH_COURSE
   drop constraint FK_PE_TCH_C_FK_PE_TCH_PE_EXAMA;

alter table PE_TCH_COURSE
   drop constraint FK_PE_TCH_C_FK_PE_TCH_PE_EXAMB;

alter table PE_TCH_COURSEWARE
   drop constraint FK_PE_TCH_C_REFERENCE_ENUM_C12;

alter table PE_TCH_COURSEWARE
   drop constraint FK_PE_TCH_C_REFERENCE_PE_TCH_3;

alter table PE_TCH_COURSEWARE
   drop constraint FK_PE_TCH_C_REFERENCE_ENUM_CO2;

alter table PE_TCH_PROGRAM
   drop constraint FK_PE_TCH_P_FK_PE_TCH_ENUM_CON;

alter table PE_TCH_PROGRAM
   drop constraint FK_PE_TCH_P_REFERENCE_ENUM_CO1;

alter table PE_TCH_PROGRAM
   drop constraint FK_PE_TCH_P_REFERENCE_ENUM_CO6;

alter table PE_TCH_PROGRAM
   drop constraint FK_PE_TCH_P_REFERENCE_ENUM_CO7;

alter table PE_TCH_PROGRAM
   drop constraint FK_PE_TCH_P_REFERENCE_PE_EDUTY;

alter table PE_TCH_PROGRAM
   drop constraint FK_PE_TCH_P_FK_PE_TCH_PE_GRADE;

alter table PE_TCH_PROGRAM
   drop constraint FK_PE_TCH_P_REFERENCE_PE_MAJOR;

alter table PE_TCH_PROGRAM_GROUP
   drop constraint FK_PE_TCH_P_REFERENCE_PE_TCH_8;

alter table PE_TCH_PROGRAM_GROUP
   drop constraint FK_PE_TCH_P_REFERENCE_PE_TCH_9;

alter table PE_TCH_REJOIN_ROOM
   drop constraint FK_PE_TCH_R_FK_PE_REJ_PE_SEMES;

alter table PE_TCH_REJOIN_SECTION
   drop constraint FK_PE_REJOI_FK_PE_REJ_PE_SEME1;

alter table PE_TEACHER
   drop constraint FK_PE_TEACH_REFERENCE_ENUM_CO2;

alter table PE_TEACHER
   drop constraint FK_PE_TEACH_REFERENCE_ENUM_CO3;

alter table PE_TEACHER
   drop constraint FK_PE_TEACH_REFERENCE_ENUM_CO4;

alter table PE_TEACHER
   drop constraint FK_PE_TEACH_REFERENCE_ENUM_CO5;

alter table PE_TEACHER
   drop constraint FK_PE_TEACH_FK_PE_TEA_PE_MAJOR;

alter table PE_TEACHER
   drop constraint FK_PE_TEACH_REFERENCE_SSO_USER;

alter table PE_TEACHER
   drop constraint FK_PE_TEACH_REFERENCE_ENUM_001;

alter table PE_TEACHER
   drop constraint FK_PE_TEACH_REFERENCE_ENUM_CON;

alter table PE_VOTE_PAPER
   drop constraint FK_PE_VOTE__REFERENCE_ENUM_CO1;

alter table PE_VOTE_PAPER
   drop constraint FK_PE_VOTE__REFERENCE_ENUM_CO2;

alter table PE_VOTE_PAPER
   drop constraint FK_PE_VOTE__REFERENCE_ENUM_CO3;

alter table PE_VOTE_PAPER
   drop constraint FK_PE_VOTE__REFERENCE_ENUM_CO4;

alter table PE_VOTE_PAPER
   drop constraint FK_PE_VOTE__REFERENCE_ENUM_CO5;

alter table PE_VOTE_PAPER
   drop constraint FK_PE_VOTE__FK_PE_VOT_ENUM_CON;

alter table PR_COURSE_ARRANGE
   drop constraint FK_PR_COURS_REFERENCE_ENUM_CO1;

alter table PR_COURSE_ARRANGE
   drop constraint FK_PR_COURS_FK_PR_COU_ENUM_CON;

alter table PR_COURSE_ARRANGE
   drop constraint FK_PR_COURS_FK_PR_COU_PR_TCH_O;

alter table PR_DOCUMENT
   drop constraint FK_PR_DOCUM_REFERENCE_ENUM_CO1;

alter table PR_DOCUMENT
   drop constraint FK_PR_DOCUM_FK_PR_DOC_ENUM_CON;

alter table PR_DOCUMENT
   drop constraint FK_PR_DOCUM_FK_PR_DOC_PE_DOCUM;

alter table PR_DOCUMENT
   drop constraint FK_PR_DOCUM_FK_PR_DOC_SSO_USER;

alter table PR_EDU_MAJOR_SITE_FEE_LEVEL
   drop constraint FK_PR_EDU_M_FK_PR_EDU_PE_EDUTY;

alter table PR_EDU_MAJOR_SITE_FEE_LEVEL
   drop constraint FK_PR_EDU_M_FK_PR_EDU_PE_FEE_L;

alter table PR_EDU_MAJOR_SITE_FEE_LEVEL
   drop constraint FK_PR_EDU_M_FK_PR_EDU_PE_MAJOR;

alter table PR_EDU_MAJOR_SITE_FEE_LEVEL
   drop constraint FK_PR_EDU_M_FK_PR_EDU_PE_SITE;

alter table PR_EXAM_BOOKING
   drop constraint FK_PR_EXAM__REFERENCE_ENUM_C02;

alter table PR_EXAM_BOOKING
   drop constraint FK_PR_EXAM__REFERENCE_ENUM_C03;

alter table PR_EXAM_BOOKING
   drop constraint FK_PR_EXAM__REFERENCE_ENUM_CO4;

alter table PR_EXAM_BOOKING
   drop constraint FK_PR_EXAM__FK_PR_EXA_PE_EXA19;

alter table PR_EXAM_BOOKING
   drop constraint FK_PR_EXAM__FK_PR_EXA_PE_EXA18;

alter table PR_EXAM_BOOKING
   drop constraint FK_PR_EXAM__FK_PR_EXA_PE_SEM20;

alter table PR_EXAM_BOOKING
   drop constraint FK_PR_EXAM__FK_PR_EXA_PR_TCH_S;

alter table PR_EXAM_OPEN_MAINCOURSE
   drop constraint FK_PR_EXAM__FK_PR_EXA_PE_EXA25;

alter table PR_EXAM_OPEN_MAINCOURSE
   drop constraint FK_PR_EXAM__FK_PR_EXA_PE_TCH_C;

alter table PR_EXAM_PATROL_SETTING
   drop constraint FK_PR_EXAM__FK_PR_EXA_PE_EXA22;

alter table PR_EXAM_PATROL_SETTING
   drop constraint FK_PR_EXAM__FK_PR_EXA_PE_SEM23;

alter table PR_EXAM_PATROL_SETTING
   drop constraint FK_PR_EXAM__FK_PR_EXA_PE_SITE;

alter table PR_EXAM_STU_MAINCOURSE
   drop constraint FK_PR_EXAM__FK_PR_EXA_PE_STUDE;

alter table PR_EXAM_STU_MAINCOURSE
   drop constraint FK_PR_EXAM__FK_PR_EXA_PR_EXAM_;

alter table PR_EXAM_STU_MAINCOURSE
   drop constraint FK_PR_EXAM__REFERENCE_PE_EXAM_;

alter table PR_EXAM_STU_MAINCOURSE
   drop constraint FK_PR_EXAM__REFERENCE_ENUM_C05;

alter table PR_EXAM_STU_MAINCOURSE
   drop constraint FK_PR_EXAM__REFERENCE_ENUM_C06;

alter table PR_FEE_DETAIL
   drop constraint FK_PR_FEE_D_REFERENCE_PE_FEE_B;

alter table PR_FEE_DETAIL
   drop constraint FK_PR_FEE_D_REFERENCE_PE_STUDE;

alter table PR_FEE_DETAIL
   drop constraint FK_PR_FEE_D_REFERENCE_ENUM_CO3;

alter table PR_FEE_DETAIL
   drop constraint FK_PR_FEE_D_REFERENCE_ENUM_CO4;

alter table PR_PC_BOOKING_SEAT
   drop constraint FK_PR_PC_BO_FK_PR_PC__PR_PC_OP;

alter table PR_PC_ELECTIVE
   drop constraint FK_PR_PC_EL_REFERENCE_ENUM_CO1;

alter table PR_PC_ELECTIVE
   drop constraint FK_PR_PC_EL_FK_PR_PC__ENUM_CON;

alter table PR_PC_ELECTIVE
   drop constraint FK_PR_PC_EL_FK_PR_PC__PE_PC_ST;

alter table PR_PC_ELECTIVE
   drop constraint FK_PR_PC_EL_FK_PR_PC__PR_PC_OP;

alter table PR_PC_NOTE_REPLY
   drop constraint FK_PR_PC_NO_FK_PE_PC__PE_PC_NO;

alter table PR_PC_NOTE_REPLY
   drop constraint FK_PR_PC_NO_FK_PE_PC__PR_PC_EL;

alter table PR_PC_OPENCOURSE
   drop constraint FK_PR_PC_OP_FK_PE_PC__PE_PC_CO;

alter table PR_PC_OPENCOURSE
   drop constraint FK_PR_PC_OP_FK_PE_PC__PE_SEMES;

alter table PR_PC_OPENCOURSE_TEACHER
   drop constraint FK_PR_PC_OP_FK_PR_PC__PE_PC_TE;

alter table PR_PC_OPENCOURSE_TEACHER
   drop constraint FK_PR_PC_OP_FK_PR_PC__PR_PC_OP;

alter table PR_PC_STU_BOOKING
   drop constraint FK_PR_PC_ST_FK_PR_PC__PR_PC_BO;

alter table PR_PC_STU_BOOKING
   drop constraint FK_PR_PC_ST_FK_PR_PC__PR_PC_E1;

alter table PR_PC_STU_EXERCISE
   drop constraint FK_PR_PC_ST_FK_PR_PC__PE_PC_EX;

alter table PR_PC_STU_EXERCISE
   drop constraint FK_PR_PC_ST_FK_PR_PC__PR_PC_EL;

alter table PR_PRI_MANAGER_EDUTYPE
   drop constraint FK_PR_PRI_M_REFERENCE_SSO_U101;

alter table PR_PRI_MANAGER_EDUTYPE
   drop constraint FK_PR_PRI_M_REFERENCE_PE_EDUTY;

alter table PR_PRI_MANAGER_GRADE
   drop constraint FK_PR_PRI_M_REFERENCE_PE_GRADE;

alter table PR_PRI_MANAGER_GRADE
   drop constraint FK_PR_PRI_M_REFERENCE_SSO_US99;

alter table PR_PRI_MANAGER_MAJOR
   drop constraint FK_PR_PRI_M_REFERENCE_SSO_U100;

alter table PR_PRI_MANAGER_MAJOR
   drop constraint FK_PR_PRI_M_REFERENCE_PE_MAJOR;

alter table PR_PRI_MANAGER_SITE
   drop constraint FK_PR_PRI_M_REFERENCE_PE_SITE;

alter table PR_PRI_MANAGER_SITE
   drop constraint FK_PR_PRI_M_REFERENCE_SSO_US98;

alter table PR_PRI_ROLE
   drop constraint FK_PR_PRI_R_REFERENCE_PE_PRIOR;

alter table PR_PRI_ROLE
   drop constraint FK_PR_PRI_R_REFERENCE_PE_PRI_R;

alter table PR_REC_COURSE_MAJOR_EDUTYPE
   drop constraint FK_PR_REC_C_REFERENCE_PE_MAJOR;

alter table PR_REC_COURSE_MAJOR_EDUTYPE
   drop constraint FK_PR_REC_C_REFERENCE_PE_EDUTY;

alter table PR_REC_COURSE_MAJOR_EDUTYPE
   drop constraint FK_PR_REC_C_REFERENCE_PE_REC_E;

alter table PR_REC_EXAM_COURSE_TIME
   drop constraint FK_PR_REC_E_FK_PR_REC_PE_RECRU;

alter table PR_REC_EXAM_COURSE_TIME
   drop constraint FK_PR_REC_E_REFERENCE_PE_REC_4;

alter table PR_REC_EXAM_STU_COURSE
   drop constraint FK_PR_REC_E_REFERENCE_PE_REC_7;

alter table PR_REC_EXAM_STU_COURSE
   drop constraint FK_PR_REC_E_FK_PR_REC_PE_REC_S;

alter table PR_REC_PATROL_SETTING
   drop constraint FK_PR_REC_P_FK_PR_REC_PE_EXAM_;

alter table PR_REC_PATROL_SETTING
   drop constraint FK_PR_REC_P_FK_PR_REC_PE_RECRU;

alter table PR_REC_PATROL_SETTING
   drop constraint FK_PR_REC_P_FK_PR_REC_PE_SITE;

alter table PR_REC_PLAN_MAJOR_EDUTYPE
   drop constraint FK_PR_REC_P_REFERENCE_PE_EDUTY;

alter table PR_REC_PLAN_MAJOR_EDUTYPE
   drop constraint FK_PR_REC_P_REFERENCE_PE_MAJOR;

alter table PR_REC_PLAN_MAJOR_EDUTYPE
   drop constraint FK_PR_REC_P_REFERENCE_PE_RECRU;

alter table PR_REC_PLAN_MAJOR_SITE
   drop constraint FK_PR_REC_P_REFERENCE_PE_SITE;

alter table PR_REC_PLAN_MAJOR_SITE
   drop constraint FK_PR_REC_P_REFERENCE_PR_REC_P;

alter table PR_SMS_SEND_STATUS
   drop constraint FK_PR_SMS_S_FK_SMS_NU_PE_SMS_I;

alter table PR_STUDENT_HISTORY
   drop constraint FK_PR_STUDE_FK_PR_STU_PE_STUDE;

alter table PR_STU_CHANGEABLE_MAJOR
   drop constraint FK_PR_STU_C_REFERENCE_PE_MAJO2;

alter table PR_STU_CHANGEABLE_MAJOR
   drop constraint FK_PR_STU_C_REFERENCE_PE_MAJO3;

alter table PR_STU_CHANGE_EDUTYPE
   drop constraint FK_PR_STU_C_REFERENCE_PE_EDUT1;

alter table PR_STU_CHANGE_EDUTYPE
   drop constraint FK_PR_STU_C_FK_PR_STU_PE_EDUTY;

alter table PR_STU_CHANGE_EDUTYPE
   drop constraint FK_PR_STU_C_FK_PR_STU_PE_STU17;

alter table PR_STU_CHANGE_MAJOR
   drop constraint FK_PR_STU_C_REFERENCE_PE_MAJO1;

alter table PR_STU_CHANGE_MAJOR
   drop constraint FK_PR_STU_C_FK_PR_STU_PE_MAJOR;

alter table PR_STU_CHANGE_MAJOR
   drop constraint FK_PR_STU_C_FK_PR_STU_PE_STU16;

alter table PR_STU_CHANGE_SCHOOL
   drop constraint FK_PA_STU_S_REFERENCE_PE_STUDE;

alter table PR_STU_CHANGE_SCHOOL
   drop constraint FK_PR_STU_C_REFERENCE_ENUM_CO1;

alter table PR_STU_CHANGE_SCHOOL
   drop constraint FK_PR_STU_C_FK_PR_STU_ENUM_CON;

alter table PR_STU_CHANGE_SITE
   drop constraint FK_PR_STU_C_REFERENCE_PE_SIT1;

alter table PR_STU_CHANGE_SITE
   drop constraint FK_PR_STU_C_FK_PR_STU_PE_SITE;

alter table PR_STU_CHANGE_SITE
   drop constraint FK_PR_STU_C_FK_PR_STU_PE_STU15;

alter table PR_STU_MULTI_MAJOR
   drop constraint FK_PR_STU_M_FK_PR_STU_PE_MAJOR;

alter table PR_TCH_COURSE_BOOK
   drop constraint FK_PR_TCH_C_FK_PR_TCH_PE_TCH_B;

alter table PR_TCH_COURSE_BOOK
   drop constraint FK_PR_TCH_C_FK_PR_TCH_PE_TCH_C;

alter table PR_TCH_COURSE_TEACHER
   drop constraint FK_PR_TCH_C_REFERENCE_ENUM_CO1;

alter table PR_TCH_COURSE_TEACHER
   drop constraint FK_PR_TCH_C_FK_PR_TCH_ENUM_CON;

alter table PR_TCH_COURSE_TEACHER
   drop constraint FK_PR_TCH_C_REFERENCE_PE_TCH_C;

alter table PR_TCH_COURSE_TEACHER
   drop constraint FK_PR_TCH_C_REFERENCE_PE_TEACH;

alter table PR_TCH_ELECTIVE_BOOK
   drop constraint FK_PR_TCH_E_REFERENCE_PR_TCH_S;

alter table PR_TCH_ELECTIVE_BOOK
   drop constraint FK_PR_TCH_E_REFERENCE_PR_TCH_O;

alter table PR_TCH_OPENCOURSE
   drop constraint FK_PR_TCH_O_REFERENCE_ENUM_CO1;

alter table PR_TCH_OPENCOURSE
   drop constraint FK_PR_TCH_O_FK_PR_TCH_ENUM_CON;

alter table PR_TCH_OPENCOURSE
   drop constraint FK_PR_TCH_O_FK_PR_TCH_PE_SEMES;

alter table PR_TCH_OPENCOURSE
   drop constraint FK_PR_TCH_O_REFERENCE_PE_TCH_1;

alter table PR_TCH_OPENCOURSE_BOOK
   drop constraint FK_PR_TCH_O_REFERENCE_PE_TCH_B;

alter table PR_TCH_OPENCOURSE_BOOK
   drop constraint FK_PR_TCH_O_REFERENCE_PR_TCH_3;

alter table PR_TCH_OPENCOURSE_COURSEWARE
   drop constraint FK_PR_TCH_O_REFERENCE_PE_TCH_6;

alter table PR_TCH_OPENCOURSE_COURSEWARE
   drop constraint FK_PR_TCH_O_REFERENCE_PR_TCH_6;

alter table PR_TCH_OPENCOURSE_TEACHER
   drop constraint FK_PR_TCH_O_REFERENCE_PR_TCH_2;

alter table PR_TCH_OPENCOURSE_TEACHER
   drop constraint FK_PR_TCH_O_REFERENCE_PE_TEAC5;

alter table PR_TCH_PAPER_CONTENT
   drop constraint FK_PR_TCH_P_REFERENCE_ENUM_CO1;

alter table PR_TCH_PAPER_CONTENT
   drop constraint FK_PR_TCH_P_FK_PR_TCH_PR_TCH_S;

alter table PR_TCH_PAPER_CONTENT
   drop constraint FK_PR_TCH_P_FK_PR_TCH_ENUM_CON;

alter table PR_TCH_PAPER_TITLE
   drop constraint FK_PR_TCH_P_REFERENCE_PE_SEMES;

alter table PR_TCH_PAPER_TITLE
   drop constraint FK_PR_TCH_P_REFERENCE_PE_TEACH;

alter table PR_TCH_PROGRAM_COURSE
   drop constraint FK_PE_TCH_P_REFERENCE_PE_TCH_4;

alter table PR_TCH_PROGRAM_COURSE
   drop constraint FK_PE_TCH_P_REFERENCE_PE_TCH_5;

alter table PR_TCH_PROGRAM_COURSE
   drop constraint FK_PR_TCH_P_REFERENCE_ENUM_CO2;

alter table PR_TCH_PROGRAM_COURSE
   drop constraint FK_PR_TCH_P_REFERENCE_ENUM_CO3;

alter table PR_TCH_STU_ELECTIVE
   drop constraint FK_PR_TCH_S_FK_PA_LRN_ENUM_CON;

alter table PR_TCH_STU_ELECTIVE
   drop constraint FK_PR_TCH_S_FK_PA_LRN_ENUM_CO1;

alter table PR_TCH_STU_ELECTIVE
   drop constraint FK_PA_LRN_S_REFERENCE_PE_STUDE;

alter table PR_TCH_STU_ELECTIVE
   drop constraint FK_PR_TCH_S_FK_PA_LRN_SSO_USER;

alter table PR_TCH_STU_ELECTIVE
   drop constraint FK_PR_TCH_S_FK_PR_TCH_ENUM_C13;

alter table PR_TCH_STU_ELECTIVE
   drop constraint FK_PR_TCH_S_FK_PR_TCH_PR_TCH_O;

alter table PR_TCH_STU_ELECTIVE
   drop constraint FK_PR_TCH_S_FK_PR_TCH_PR_TCH_P;

alter table PR_TCH_STU_PAPER
   drop constraint FK_PR_TCH_S_FK_PR_TCH_ENUM_CO1;

alter table PR_TCH_STU_PAPER
   drop constraint FK_PR_TCH_S_FK_PR_TCH_ENUM_C14;

alter table PR_TCH_STU_PAPER
   drop constraint FK_PR_TCH_S_REFERENCE_ENUM_CO3;

alter table PR_TCH_STU_PAPER
   drop constraint FK_PR_TCH_S_REFERENCE_ENUM_CO5;

alter table PR_TCH_STU_PAPER
   drop constraint FK_PR_TCH_S_FK_PR_TCH_ENUM_CON;

alter table PR_TCH_STU_PAPER
   drop constraint FK_PR_TCH_S_FK_PR_TCH_PE_REJO1;

alter table PR_TCH_STU_PAPER
   drop constraint FK_PR_TCH_S_FK_PR_TCH_PE_TCH_R;

alter table PR_TCH_STU_PAPER
   drop constraint FK_PR_TCH_S_REFERENCE_PE_STUDE;

alter table PR_TCH_STU_PAPER
   drop constraint FK_PR_TCH_S_REFERENCE_PR_TCH_3;

alter table PR_VOTE_QUESTION
   drop constraint FK_PR_VOTE__REFERENCE_ENUM_CO2;

alter table PR_VOTE_QUESTION
   drop constraint FK_PR_VOTE__FK_PR_VOT_ENUM_CON;

alter table PR_VOTE_QUESTION
   drop constraint FK_PR_VOTE__FK_PR_VOT_PE_VOTE_;

alter table PR_VOTE_RECORD
   drop constraint FK_PR_VOTE__FK_PR_VOT_PE_VOTE1;

alter table PR_VOTE_SUGGEST
   drop constraint FK_PR_VOTE__REFERENCE_ENUM_CO1;

alter table PR_VOTE_SUGGEST
   drop constraint FK_PR_VOTE__REFERENCE_ENUM_CO3;

alter table PR_VOTE_SUGGEST
   drop constraint FK_PR_VOTE__REFERENCE_PE_VOTE2;

alter table SSO_USER
   drop constraint FK_SSO_USER_REFERENCE_ENUM_CO1;

alter table SSO_USER
   drop constraint FK_SSO_USER_FK_SSO_US_ENUM_CON;

alter table SSO_USER
   drop constraint FK_SSO_USER_REFERENCE_PE_PRI_R;

alter table SYSTEM_APPLY
   drop constraint FK_SYSTEM_A_REFERENCE_ENUM_CO1;

alter table SYSTEM_APPLY
   drop constraint FK_SYSTEM_A_FK_SYSTEM_ENUM_CON;

alter table SYSTEM_APPLY
   drop constraint FK_SYSTEM_A_FK_SYSTEM_PE_STUDE;

alter table SYSTEM_VARIABLES
   drop constraint FK_SYSTEM_V_REFERENCE_ENUM_CO1;

alter table SYSTEM_VARIABLES
   drop constraint FK_SYSTEM_V_FK_SYSTEM_ENUM_CON;

drop index "Index_1";

drop table ENUM_CONST cascade constraints;

drop table HOMEWORK_HISTORY cascade constraints;

drop table HOMEWORK_INFO cascade constraints;

drop table PE_BULLETIN cascade constraints;

drop table PE_DOCUMENT cascade constraints;

drop table PE_EDUTYPE cascade constraints;

drop table PE_EXAM_MAINCOURSE_NO cascade constraints;

drop table PE_EXAM_MAINCOURSE_ROOM cascade constraints;

drop table PE_EXAM_NO cascade constraints;

drop table PE_EXAM_PATROL cascade constraints;

drop table PE_EXAM_ROOM cascade constraints;

drop table PE_EXAM_SCORE_INPUT_USER cascade constraints;

drop table PE_FEE_BATCH cascade constraints;

drop table PE_FEE_LEVEL cascade constraints;

drop table PE_GRADE cascade constraints;

drop table PE_INFO_NEWS cascade constraints;

drop table PE_INFO_NEWS_TYPE cascade constraints;

drop table PE_JIANZHANG cascade constraints;

drop table PE_MAJOR cascade constraints;

drop table PE_MANAGER cascade constraints;

drop table PE_PC_COURSE cascade constraints;

drop table PE_PC_EXERCISE cascade constraints;

drop table PE_PC_NEWS cascade constraints;

drop table PE_PC_NOTE cascade constraints;

drop table PE_PC_OPENCOURSE_COURSEWARE cascade constraints;

drop table PE_PC_OPENCOURSE_RESOURCE cascade constraints;

drop table PE_PC_STUDENT cascade constraints;

drop table PE_PC_TEACHER cascade constraints;

drop table PE_PRIORITY cascade constraints;

drop table PE_PRI_CATEGORY cascade constraints;

drop table PE_PRI_ROLE cascade constraints;

drop table PE_RECRUITPLAN cascade constraints;

drop table PE_REC_EXAMCOURSE cascade constraints;

drop table PE_REC_ROOM cascade constraints;

drop table PE_REC_STUDENT cascade constraints;

drop table PE_SEMESTER cascade constraints;

drop table PE_SITE cascade constraints;

drop table PE_SITEMANAGER cascade constraints;

drop table PE_SMS_INFO cascade constraints;

drop table PE_SMS_SYSTEMPOINT cascade constraints;

drop table PE_STUDENT cascade constraints;

drop table PE_TCH_BOOK cascade constraints;

drop table PE_TCH_COURSE cascade constraints;

drop table PE_TCH_COURSEGROUP cascade constraints;

drop table PE_TCH_COURSEWARE cascade constraints;

drop table PE_TCH_PROGRAM cascade constraints;

drop table PE_TCH_PROGRAM_GROUP cascade constraints;

drop table PE_TCH_REJOIN_ROOM cascade constraints;

drop table PE_TCH_REJOIN_SECTION cascade constraints;

drop table PE_TEACHER cascade constraints;

drop table PE_VOTE_PAPER cascade constraints;

drop table PR_COURSE_ARRANGE cascade constraints;

drop table PR_DOCUMENT cascade constraints;

drop table PR_EDU_MAJOR_SITE_FEE_LEVEL cascade constraints;

drop table PR_EXAM_BOOKING cascade constraints;

drop table PR_EXAM_OPEN_MAINCOURSE cascade constraints;

drop table PR_EXAM_PATROL_SETTING cascade constraints;

drop table PR_EXAM_STU_MAINCOURSE cascade constraints;

drop table PR_FEE_DETAIL cascade constraints;

drop table PR_PC_BOOKING_SEAT cascade constraints;

drop table PR_PC_ELECTIVE cascade constraints;

drop table PR_PC_NOTE_REPLY cascade constraints;

drop table PR_PC_OPENCOURSE cascade constraints;

drop table PR_PC_OPENCOURSE_TEACHER cascade constraints;

drop table PR_PC_STU_BOOKING cascade constraints;

drop table PR_PC_STU_EXERCISE cascade constraints;

drop table PR_PRI_MANAGER_EDUTYPE cascade constraints;

drop table PR_PRI_MANAGER_GRADE cascade constraints;

drop table PR_PRI_MANAGER_MAJOR cascade constraints;

drop table PR_PRI_MANAGER_SITE cascade constraints;

drop table PR_PRI_ROLE cascade constraints;

drop table PR_REC_COURSE_MAJOR_EDUTYPE cascade constraints;

drop table PR_REC_EXAM_COURSE_TIME cascade constraints;

drop table PR_REC_EXAM_STU_COURSE cascade constraints;

drop table PR_REC_PATROL_SETTING cascade constraints;

drop table PR_REC_PLAN_MAJOR_EDUTYPE cascade constraints;

drop table PR_REC_PLAN_MAJOR_SITE cascade constraints;

drop table PR_SMS_SEND_STATUS cascade constraints;

drop table PR_STUDENT_HISTORY cascade constraints;

drop table PR_STUDENT_INFO cascade constraints;

drop table PR_STU_CHANGEABLE_MAJOR cascade constraints;

drop table PR_STU_CHANGE_EDUTYPE cascade constraints;

drop table PR_STU_CHANGE_MAJOR cascade constraints;

drop table PR_STU_CHANGE_SCHOOL cascade constraints;

drop table PR_STU_CHANGE_SITE cascade constraints;

drop table PR_STU_MULTI_MAJOR cascade constraints;

drop table PR_TCH_COURSE_BOOK cascade constraints;

drop table PR_TCH_COURSE_TEACHER cascade constraints;

drop table PR_TCH_ELECTIVE_BOOK cascade constraints;

drop table PR_TCH_OPENCOURSE cascade constraints;

drop table PR_TCH_OPENCOURSE_BOOK cascade constraints;

drop table PR_TCH_OPENCOURSE_COURSEWARE cascade constraints;

drop table PR_TCH_OPENCOURSE_TEACHER cascade constraints;

drop table PR_TCH_PAPER_CONTENT cascade constraints;

drop table PR_TCH_PAPER_TITLE cascade constraints;

drop table PR_TCH_PROGRAM_COURSE cascade constraints;

drop table PR_TCH_STU_ELECTIVE cascade constraints;

drop table PR_TCH_STU_PAPER cascade constraints;

drop table PR_VOTE_QUESTION cascade constraints;

drop table PR_VOTE_RECORD cascade constraints;

drop table PR_VOTE_SUGGEST cascade constraints;

drop table SSO_USER cascade constraints;

drop table SYSTEM_APPLY cascade constraints;

drop table SYSTEM_VARIABLES cascade constraints;

drop table WHATYUSER_LOG4J cascade constraints;

/*==============================================================*/
/* Table: ENUM_CONST                                            */
/*==============================================================*/
create table ENUM_CONST  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50),
   NAMESPACE            VARCHAR2(50)                    not null,
   IS_DEFAULT           char(1)                        default '0',
   CREATE_DATE          DATE                           default sysdate,
   NOTE                 VARCHAR2(50),
   constraint PK_ENUM_CONST primary key (ID),
   constraint AK_KEY_2_ENUM_CON unique (NAME, NAMESPACE)
);

comment on table ENUM_CONST is
'枚举常量';

comment on column ENUM_CONST.ID is
'自动ID';

comment on column ENUM_CONST.NAME is
'名称，供界面显示给用户';

comment on column ENUM_CONST.CODE is
'代码，供程序识别值';

comment on column ENUM_CONST.NAMESPACE is
'名称空间，用于区分不同的值组合';

comment on column ENUM_CONST.IS_DEFAULT is
'是否是默认值：1是/0否';

comment on column ENUM_CONST.CREATE_DATE is
'创建时间';

comment on column ENUM_CONST.NOTE is
'备注信息';

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

/*==============================================================*/
/* Table: PE_BULLETIN                                           */
/*==============================================================*/
create table PE_BULLETIN  (
   ID                   VARCHAR2(50)                    not null,
   FK_MANAGER_ID        VARCHAR2(50),
   FLAG_ISVALID         VARCHAR2(50),
   FLAG_ISTOP           VARCHAR2(50),
   TITLE                VARCHAR2(500)                   not null,
   PUBLISH_DATE         DATE,
   UPDATE_DATE          DATE,
   SCOPE_STRING         VARCHAR2(1000),
   NOTE                 CLOB,
   constraint PK_PE_BULLETIN primary key (ID)
);

comment on table PE_BULLETIN is
'公告';

comment on column PE_BULLETIN.FLAG_ISVALID is
'ENUMCONST 0无效 1有效';

comment on column PE_BULLETIN.FLAG_ISTOP is
'ENUMCONST 0不置顶 1置顶';

comment on column PE_BULLETIN.SCOPE_STRING is
'描述接收范围的字符串';

/*==============================================================*/
/* Table: PE_DOCUMENT                                           */
/*==============================================================*/
create table PE_DOCUMENT  (
   ID                   VARCHAR2(50)                    not null,
   TITLE                VARCHAR2(500)                   not null,
   FK_SSO_ID            VARCHAR2(50),
   FLAG_DEL             VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   SEND_DATE            DATE,
   NOTE                 CLOB,
   constraint PK_PE_DOCUMENT primary key (ID)
);

comment on table PE_DOCUMENT is
'公文';

comment on column PE_DOCUMENT.FK_SSO_ID is
'发件人';

comment on column PE_DOCUMENT.FLAG_DEL is
'对发送者是否可见（假删除） 1已删除';

/*==============================================================*/
/* Table: PE_EDUTYPE                                            */
/*==============================================================*/
create table PE_EDUTYPE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50),
   constraint PK_PE_EDUTYPE primary key (ID),
   constraint AK_KEY_1_PE_EDUTY unique (NAME),
   constraint AK_KEY_3_PE_EDUTY unique (CODE)
);

comment on table PE_EDUTYPE is
'层次表';

comment on column PE_EDUTYPE.CODE is
'编号，唯一性约束';

/*==============================================================*/
/* Table: PE_EXAM_MAINCOURSE_NO                                 */
/*==============================================================*/
create table PE_EXAM_MAINCOURSE_NO  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_SEMESTER_ID       VARCHAR2(50),
   START_DATETIME       DATE,
   END_DATETIME         DATE,
   constraint PK_PE_EXAM_MAINCOURSE_NO primary key (ID),
   constraint AK_KEY_2_PE_EXA04 unique (NAME)
);

comment on table PE_EXAM_MAINCOURSE_NO is
'主干课程考试场次表';

comment on column PE_EXAM_MAINCOURSE_NO.NAME is
'名称：xxx学期第x场';

/*==============================================================*/
/* Table: PE_EXAM_MAINCOURSE_ROOM                               */
/*==============================================================*/
create table PE_EXAM_MAINCOURSE_ROOM  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50),
   CODE                 VARCHAR2(50),
   FK_SITE_ID           VARCHAR2(50),
   FK_EXAM_OPEN_MAINCOURSE_ID VARCHAR2(50),
   EXAM_PLACE           VARCHAR2(50),
   INVIGILATOR_A        VARCHAR2(50),
   INVIGILATOR_B        VARCHAR2(50),
   constraint PK_PE_EXAM_MAINCOURSE_ROOM primary key (ID),
   constraint AK_KEY_2_PE_EXA07 unique (NAME)
);

comment on table PE_EXAM_MAINCOURSE_ROOM is
'学生主干课程考场表';

comment on column PE_EXAM_MAINCOURSE_ROOM.NAME is
'名称（备用）';

comment on column PE_EXAM_MAINCOURSE_ROOM.FK_SITE_ID is
'考试站点';

comment on column PE_EXAM_MAINCOURSE_ROOM.EXAM_PLACE is
'考试地点';

/*==============================================================*/
/* Table: PE_EXAM_NO                                            */
/*==============================================================*/
create table PE_EXAM_NO  (
   ID                   VARCHAR2(50)                    not null,
   FK_SEMESTER_ID       VARCHAR2(50),
   NAME                 VARCHAR2(50)                    not null,
   SEQUENCE             NUMBER(2),
   START_DATETIME       DATE,
   END_DATETIME         DATE,
   PAPER_TYPE           VARCHAR2(50),
   constraint PK_PE_EXAM_NO primary key (ID),
   constraint AK_KEY_2_PE_EXAM_ unique (NAME)
);

comment on table PE_EXAM_NO is
'考试场次表';

comment on column PE_EXAM_NO.NAME is
'名称 （某学期第几场）';

comment on column PE_EXAM_NO.SEQUENCE is
'每学期的场次顺序号';

comment on column PE_EXAM_NO.START_DATETIME is
'考试场次开始时间';

comment on column PE_EXAM_NO.END_DATETIME is
'考试场次结束时间';

comment on column PE_EXAM_NO.PAPER_TYPE is
'试卷类型（ABC）';

/*==============================================================*/
/* Table: PE_EXAM_PATROL                                        */
/*==============================================================*/
create table PE_EXAM_PATROL  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_SITE_ID           VARCHAR2(50),
   FLAG_IS_JIANKAO      VARCHAR2(50)                    not null,
   FLAG_IS_XUNKAO       VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50)                    not null,
   CARD_NUM             VARCHAR2(50)                    not null,
   BIRTHDAY             DATE,
   GENDER               VARCHAR2(50),
   HOMETOWN             VARCHAR2(50),
   WORKPLACE            VARCHAR2(500),
   OCCUPATION           VARCHAR2(50),
   PHOTO_LINK           VARCHAR2(500),
   MOBILE_GUANGZHOU     VARCHAR2(50),
   MOBILE_AWAY          VARCHAR2(50),
   PHONE_OFFICE         VARCHAR2(50),
   PHONE_HOME           VARCHAR2(50),
   constraint PK_PE_EXAM_PATROL primary key (ID),
   constraint AK_KEY_2_PE_EXA01 unique (NAME),
   constraint AK_KEY_3_PE_EXAM_ unique (CARD_NUM),
   constraint AK_KEY_4_PE_EXAM_ unique (CODE)
);

comment on table PE_EXAM_PATROL is
'巡考人员表';

comment on column PE_EXAM_PATROL.NAME is
'姓名';

comment on column PE_EXAM_PATROL.FLAG_IS_JIANKAO is
'是否为监考';

comment on column PE_EXAM_PATROL.FLAG_IS_XUNKAO is
'是否为巡考';

comment on column PE_EXAM_PATROL.CODE is
'上岗证号';

comment on column PE_EXAM_PATROL.CARD_NUM is
'身份证号';

comment on column PE_EXAM_PATROL.BIRTHDAY is
'出生日期';

comment on column PE_EXAM_PATROL.GENDER is
'性别';

comment on column PE_EXAM_PATROL.HOMETOWN is
'籍贯';

comment on column PE_EXAM_PATROL.WORKPLACE is
'工作单位';

comment on column PE_EXAM_PATROL.OCCUPATION is
'职业';

comment on column PE_EXAM_PATROL.PHOTO_LINK is
'照片链接';

comment on column PE_EXAM_PATROL.MOBILE_GUANGZHOU is
'广州手机';

comment on column PE_EXAM_PATROL.MOBILE_AWAY is
'外出手机';

comment on column PE_EXAM_PATROL.PHONE_OFFICE is
'办公电话';

comment on column PE_EXAM_PATROL.PHONE_HOME is
'家庭电话';

/*==============================================================*/
/* Table: PE_EXAM_ROOM                                          */
/*==============================================================*/
create table PE_EXAM_ROOM  (
   ID                   VARCHAR2(50)                    not null,
   FK_EXAM_NO           VARCHAR2(50),
   FK_SITE_ID           VARCHAR2(50),
   NAME                 VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50),
   INVIGILATOR_A        VARCHAR2(50),
   INVIGILATOR_B        VARCHAR2(50),
   CLASSROOM            VARCHAR2(50),
   constraint PK_PE_EXAM_ROOM primary key (ID),
   constraint AK_KEY_2_PE_EXAM1 unique (NAME)
);

comment on table PE_EXAM_ROOM is
'考场表';

comment on column PE_EXAM_ROOM.FK_SITE_ID is
'考试站点';

comment on column PE_EXAM_ROOM.NAME is
'建议格式：学期+考试场次+考场号';

comment on column PE_EXAM_ROOM.CODE is
'考场号';

comment on column PE_EXAM_ROOM.INVIGILATOR_A is
'监考人A姓名';

comment on column PE_EXAM_ROOM.INVIGILATOR_B is
'监考人B姓名';

comment on column PE_EXAM_ROOM.CLASSROOM is
'真实教室';

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

/*==============================================================*/
/* Table: PE_FEE_BATCH                                          */
/*==============================================================*/
create table PE_FEE_BATCH  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_SITE_ID           VARCHAR2(50),
   FK_INPUTTER_ID       VARCHAR2(50),
   FLAG_FEE_CHECK       VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   FEE_AMOUNT_TOTAL     NUMBER(20,2),
   FEE_RECORD_NUM       NUMBER(20),
   INPUT_DATE           DATE,
   NOTE                 VARCHAR2(1000),
   constraint PK_PE_FEE_BATCH primary key (ID),
   constraint AK_KEY_1_PE_FEE_B unique (NAME)
);

comment on table PE_FEE_BATCH is
'学习中心上交费用批次';

comment on column PE_FEE_BATCH.FK_SITE_ID is
'所属学习中心。总站为空';

comment on column PE_FEE_BATCH.FLAG_FEE_CHECK is
'上报审核状态：‘0’未上报，‘1’已上报，‘2’已审核';

comment on column PE_FEE_BATCH.FLAG_BAK is
'备用标志位';

comment on column PE_FEE_BATCH.FEE_AMOUNT_TOTAL is
'批次费用总数';

comment on column PE_FEE_BATCH.FEE_RECORD_NUM is
'明细数量';

comment on column PE_FEE_BATCH.NOTE is
'备注';

/*==============================================================*/
/* Table: PE_FEE_LEVEL                                          */
/*==============================================================*/
create table PE_FEE_LEVEL  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FLAG_DEFAULT         VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   FEE_PERCREDIT        NUMBER(10,2),
   OWE_FEE_LIMIT        NUMBER(10,2),
   NOTE                 VARCHAR2(1000),
   constraint PK_PE_FEE_LEVEL primary key (ID),
   constraint AK_KEY_2_PE_FEE_L unique (NAME)
);

comment on table PE_FEE_LEVEL is
'收费标准';

comment on column PE_FEE_LEVEL.FLAG_DEFAULT is
'是否为默认收费标准';

comment on column PE_FEE_LEVEL.FEE_PERCREDIT is
'每学分费用';

comment on column PE_FEE_LEVEL.OWE_FEE_LIMIT is
'允许欠费额';

comment on column PE_FEE_LEVEL.NOTE is
'备注';

/*==============================================================*/
/* Table: PE_GRADE                                              */
/*==============================================================*/
create table PE_GRADE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50),
   SERIAL_NUMBER        NUMBER(4),
   BEGIN_DATE           DATE,
   constraint PK_PE_GRADE primary key (ID),
   constraint AK_KEY_1_PE_GRADE unique (NAME),
   constraint AK_KEY_3_PE_GRADE unique (CODE),
   constraint AK_KEY_4_PE_GRADE unique (SERIAL_NUMBER)
);

comment on table PE_GRADE is
'年级表';

comment on column PE_GRADE.CODE is
'编号，唯一性约束';

comment on column PE_GRADE.SERIAL_NUMBER is
'序列计数，从2006秋为1，2007春为2，依次递增';

/*==============================================================*/
/* Table: PE_INFO_NEWS                                          */
/*==============================================================*/
create table PE_INFO_NEWS  (
   ID                   VARCHAR2(50)                    not null,
   FK_NEWS_TYPE_ID      VARCHAR2(50),
   FK_MANAGER_ID        VARCHAR2(50),
   TITLE                VARCHAR2(500)                   not null,
   FLAG_NEWS_STATUS     VARCHAR2(50),
   FLAG_ISACTIVE        VARCHAR2(50),
   REPORT_DATE          DATE                           default sysdate,
   SUBMIT_DATE          DATE,
   CONFIRM_MANAGER_ID   VARCHAR2(50),
   READ_COUNT           NUMBER,
   NOTE                 CLOB,
   constraint INFO_NEWS_PRI1 primary key (ID)
);

comment on table PE_INFO_NEWS is
'新闻表';

comment on column PE_INFO_NEWS.FK_MANAGER_ID is
'发布人';

comment on column PE_INFO_NEWS.TITLE is
'标题';

comment on column PE_INFO_NEWS.FLAG_NEWS_STATUS is
'ENUMCONST新闻审核状态 0未审核 1审核通过';

comment on column PE_INFO_NEWS.FLAG_ISACTIVE is
'ENUMCONST新闻是否活动 0不活动 1活动';

comment on column PE_INFO_NEWS.REPORT_DATE is
'报道日期';

comment on column PE_INFO_NEWS.SUBMIT_DATE is
'审核日期';

comment on column PE_INFO_NEWS.CONFIRM_MANAGER_ID is
'审核人';

comment on column PE_INFO_NEWS.READ_COUNT is
'阅读次数';

comment on column PE_INFO_NEWS.NOTE is
'新闻体';

/*==============================================================*/
/* Table: PE_INFO_NEWS_TYPE                                     */
/*==============================================================*/
create table PE_INFO_NEWS_TYPE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(100)                   not null,
   NOTE                 VARCHAR2(1000),
   constraint INFO_NEWS_TYPEPRI1 primary key (ID),
   constraint AK_KEY_2_PE_INFO_ unique (NAME)
);

/*==============================================================*/
/* Table: PE_JIANZHANG                                          */
/*==============================================================*/
create table PE_JIANZHANG  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   CREAT_DATE           DATE                           default SYSDATE,
   FLAG_ACTIVE          CHAR(1)                        default '0',
   JIANZHANG            CLOB,
   constraint PK_PE_JIANZHANG primary key (ID),
   constraint AK_KEY_2_PE_JIANZ unique (NAME)
);

comment on table PE_JIANZHANG is
'招生简章表';

comment on column PE_JIANZHANG.CREAT_DATE is
'创建时间';

comment on column PE_JIANZHANG.FLAG_ACTIVE is
'是否活动 0 非活动 1活动 有且仅有一条活动记录';

comment on column PE_JIANZHANG.JIANZHANG is
'简章内容';

/*==============================================================*/
/* Table: PE_MAJOR                                              */
/*==============================================================*/
create table PE_MAJOR  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50)                    not null,
   ALIAS                VARCHAR2(50)                    not null,
   FLAG_IS_EDUCATION    VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   constraint PK_PE_MAJOR primary key (ID),
   constraint AK_KEY_1_PE_MAJOR unique (NAME),
   constraint AK_KEY_3_PE_MAJOR unique (CODE)
);

comment on table PE_MAJOR is
'专业表';

comment on column PE_MAJOR.CODE is
'编号，唯一性约束';

comment on column PE_MAJOR.ALIAS is
'别名';

comment on column PE_MAJOR.FLAG_IS_EDUCATION is
'是否为教育专业';

/*==============================================================*/
/* Table: PE_MANAGER                                            */
/*==============================================================*/
create table PE_MANAGER  (
   ID                   VARCHAR2(50)                    not null,
   LOGIN_ID             VARCHAR2(50),
   NAME                 VARCHAR2(50)                    not null,
   TRUE_NAME            VARCHAR2(50)                    not null,
   FK_SSO_USER_ID       VARCHAR2(50),
   FLAG_ISVALID         VARCHAR2(50),
   MOBILE_PHONE         VARCHAR2(50),
   PHONE                VARCHAR2(50),
   GENDER               VARCHAR2(50),
   ID_CARD              VARCHAR2(50),
   EMAIL                VARCHAR2(50),
   GRADUATION_INFO      VARCHAR2(200),
   GRADUATION_DATE      DATE,
   ADDRESS              VARCHAR2(300),
   ZHI_CHENG            VARCHAR2(50),
   WORK_TIME            VARCHAR2(50),
   constraint PK_PE_MANAGER primary key (ID),
   constraint AK_KEY_2_PE_MANAG unique (NAME),
   constraint AK_KEY_3_PE_MANAG unique (LOGIN_ID)
);

comment on table PE_MANAGER is
'总站管理员表';

comment on column PE_MANAGER.LOGIN_ID is
'登录名，与SSO_USER.LOGIN_ID同步';

comment on column PE_MANAGER.NAME is
'唯一性名称  用户名/姓名';

comment on column PE_MANAGER.TRUE_NAME is
'姓名';

comment on column PE_MANAGER.FLAG_ISVALID is
'是否有效 0无效，1有效';

comment on column PE_MANAGER.MOBILE_PHONE is
'移动电话';

comment on column PE_MANAGER.PHONE is
'固定电话';

comment on column PE_MANAGER.GENDER is
'性别';

comment on column PE_MANAGER.ID_CARD is
'身份证号';

comment on column PE_MANAGER.EMAIL is
'电子邮箱';

comment on column PE_MANAGER.GRADUATION_INFO is
'毕业院校及专业层次';

comment on column PE_MANAGER.GRADUATION_DATE is
'毕业时间';

comment on column PE_MANAGER.ADDRESS is
'通信地址及邮编';

comment on column PE_MANAGER.ZHI_CHENG is
'职称';

comment on column PE_MANAGER.WORK_TIME is
'从事成人教育工作时间';

/*==============================================================*/
/* Table: PE_PC_COURSE                                          */
/*==============================================================*/
create table PE_PC_COURSE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50),
   CODE                 VARCHAR2(50),
   NOTE                 VARCHAR2(50),
   constraint PK_PE_PC_COURSE primary key (ID),
   constraint AK_KEY_2_PE_PC_CO unique (NAME)
);

comment on table PE_PC_COURSE is
'公选课课程';

comment on column PE_PC_COURSE.NAME is
'课程名';

comment on column PE_PC_COURSE.CODE is
'编号';

comment on column PE_PC_COURSE.NOTE is
'说明';

/*==============================================================*/
/* Table: PE_PC_EXERCISE                                        */
/*==============================================================*/
create table PE_PC_EXERCISE  (
   ID                   VARCHAR2(50)                    not null,
   FK_OPENCOURSE_ID     VARCHAR2(50),
   PUBLISH_DATETIME     DATE,
   UPLOAD_END_DATETIME  DATE,
   NOTE                 CLOB,
   constraint PK_PE_PC_EXERCISE primary key (ID)
);

comment on table PE_PC_EXERCISE is
'作业练习表';

comment on column PE_PC_EXERCISE.PUBLISH_DATETIME is
'布置作业时间';

comment on column PE_PC_EXERCISE.UPLOAD_END_DATETIME is
'提交作业截止时间';

comment on column PE_PC_EXERCISE.NOTE is
'作业内容';

/*==============================================================*/
/* Table: PE_PC_NEWS                                            */
/*==============================================================*/
create table PE_PC_NEWS  (
   ID                   VARCHAR2(50)                    not null,
   TITLE                VARCHAR2(500),
   FK_MANAGER_ID        VARCHAR2(50),
   FLAG_ISSUE           VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   ISSUE_DATETIME       DATE,
   NOTE                 CLOB,
   constraint PK_PE_PC_NEWS primary key (ID)
);

comment on table PE_PC_NEWS is
'公选课新闻通知';

comment on column PE_PC_NEWS.TITLE is
'标题';

comment on column PE_PC_NEWS.FK_MANAGER_ID is
'发布人';

comment on column PE_PC_NEWS.FLAG_ISSUE is
'ENUMCONST是否发布';

comment on column PE_PC_NEWS.ISSUE_DATETIME is
'发布时间';

comment on column PE_PC_NEWS.NOTE is
'内容';

/*==============================================================*/
/* Table: PE_PC_NOTE                                            */
/*==============================================================*/
create table PE_PC_NOTE  (
   ID                   VARCHAR2(50)                    not null,
   FK_OPENCOURSE_ID     VARCHAR2(50),
   FK_TEACHER_ID        VARCHAR2(50),
   PUBLISH_DATETIME     DATE,
   TITLE                VARCHAR2(500),
   NOTE                 CLOB,
   constraint PK_PE_PC_NOTE primary key (ID)
);

comment on table PE_PC_NOTE is
'教师笔记表';

comment on column PE_PC_NOTE.PUBLISH_DATETIME is
'发布时间';

comment on column PE_PC_NOTE.TITLE is
'标题';

/*==============================================================*/
/* Table: PE_PC_OPENCOURSE_COURSEWARE                           */
/*==============================================================*/
create table PE_PC_OPENCOURSE_COURSEWARE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50),
   FK_OPENCOURSE_ID     VARCHAR2(50),
   NOTE                 VARCHAR2(50),
   constraint PK_PE_PC_OPENCOURSE_COURSEWARE primary key (ID)
);

comment on table PE_PC_OPENCOURSE_COURSEWARE is
'课件表';

/*==============================================================*/
/* Table: PE_PC_OPENCOURSE_RESOURCE                             */
/*==============================================================*/
create table PE_PC_OPENCOURSE_RESOURCE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50),
   FK_OPENCOURSE_ID     VARCHAR2(50),
   FK_UPLOAD_TEACHER_ID VARCHAR2(50),
   URL                  VARCHAR2(50),
   UPLOAD_DATE          DATE,
   constraint PK_PE_PC_OPENCOURSE_RESOURCE primary key (ID)
);

comment on table PE_PC_OPENCOURSE_RESOURCE is
'课程资源表（上传下载）';

comment on column PE_PC_OPENCOURSE_RESOURCE.NAME is
'资源名';

comment on column PE_PC_OPENCOURSE_RESOURCE.FK_OPENCOURSE_ID is
'对应课程';

comment on column PE_PC_OPENCOURSE_RESOURCE.FK_UPLOAD_TEACHER_ID is
'上传教师';

comment on column PE_PC_OPENCOURSE_RESOURCE.URL is
'下载地址';

comment on column PE_PC_OPENCOURSE_RESOURCE.UPLOAD_DATE is
'上传时间';

/*==============================================================*/
/* Table: PE_PC_STUDENT                                         */
/*==============================================================*/
create table PE_PC_STUDENT  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50),
   FK_SSO_USER_ID       VARCHAR2(50),
   TRUE_NAME            VARCHAR2(50),
   REG_NO               VARCHAR2(50),
   MAJOR                VARCHAR2(50),
   CLASS                VARCHAR2(50),
   AREA                 VARCHAR2(50),
   MOBILEPHONE          VARCHAR2(50),
   constraint PK_PE_PC_STUDENT primary key (ID),
   constraint AK_KEY_2_PE_PC_ST unique (REG_NO)
);

comment on table PE_PC_STUDENT is
'公选课学生';

comment on column PE_PC_STUDENT.NAME is
'学号/姓名';

comment on column PE_PC_STUDENT.TRUE_NAME is
'姓名';

comment on column PE_PC_STUDENT.REG_NO is
'学号（用户名）';

comment on column PE_PC_STUDENT.MAJOR is
'专业';

comment on column PE_PC_STUDENT.CLASS is
'班别';

comment on column PE_PC_STUDENT.AREA is
'校区';

comment on column PE_PC_STUDENT.MOBILEPHONE is
'手机号';

/*==============================================================*/
/* Table: PE_PC_TEACHER                                         */
/*==============================================================*/
create table PE_PC_TEACHER  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50),
   FK_SSO_USER_ID       VARCHAR2(50),
   FLAG_TEACHER_TYPE    VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   NOTE                 CLOB,
   constraint PK_PE_PC_TEACHER primary key (ID)
);

comment on table PE_PC_TEACHER is
'公选课教师';

comment on column PE_PC_TEACHER.NAME is
'姓名';

comment on column PE_PC_TEACHER.FLAG_TEACHER_TYPE is
'教师类型（主讲、指导）';

comment on column PE_PC_TEACHER.NOTE is
'简介';

/*==============================================================*/
/* Table: PE_PRIORITY                                           */
/*==============================================================*/
create table PE_PRIORITY  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_PRI_CAT_ID        VARCHAR2(50),
   NAMESPACE            VARCHAR2(50)                    not null,
   ACTION               VARCHAR2(50)                    not null,
   METHOD               VARCHAR2(50)                    not null,
   constraint PK_PE_PRIORITY primary key (ID)
);

comment on table PE_PRIORITY is
'权限表';

comment on column PE_PRIORITY.NAMESPACE is
'Struts NAMESPACE';

comment on column PE_PRIORITY.ACTION is
'Struts ACTION';

comment on column PE_PRIORITY.METHOD is
'Struts METHOD';

/*==============================================================*/
/* Table: PE_PRI_CATEGORY                                       */
/*==============================================================*/
create table PE_PRI_CATEGORY  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_PARENT_ID         VARCHAR2(50),
   CODE                 VARCHAR2(50),
   PATH                 VARCHAR2(1000),
   FLAG_LEFT_MENU       CHAR(1),
   constraint PK_PE_PRI_CATETORY primary key (ID),
   constraint AK_KEY_3_PE_PRI_C unique (CODE)
);

comment on table PE_PRI_CATEGORY is
'权限类别表';

comment on column PE_PRI_CATEGORY.FK_PARENT_ID is
'父类别权限分类，为空则表示为根分类';

comment on column PE_PRI_CATEGORY.CODE is
'编号（js使用）';

comment on column PE_PRI_CATEGORY.PATH is
'点击时的目标地址';

comment on column PE_PRI_CATEGORY.FLAG_LEFT_MENU is
'是否是左菜单 1左菜单 0上菜单';

/*==============================================================*/
/* Table: PE_PRI_ROLE                                           */
/*==============================================================*/
create table PE_PRI_ROLE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FLAG_ROLE_TYPE       VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   constraint PK_PE_PRI_ROLE primary key (ID),
   constraint AK_KEY_1_PE_PRI_R unique (NAME)
);

comment on table PE_PRI_ROLE is
'用户角色表';

comment on column PE_PRI_ROLE.FLAG_ROLE_TYPE is
'类型 1总站 2分站 3教师 4学生  总之 要跟ssoUser的USER_TYPEG 一样';

/*==============================================================*/
/* Table: PE_RECRUITPLAN                                        */
/*==============================================================*/
create table PE_RECRUITPLAN  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50)                    not null,
   FK_GRADE_ID          VARCHAR2(50),
   FLAG_ACTIVE          CHAR(1)                        default '0' not null,
   START_DATE           DATE,
   REGISTER_START_DATE  DATE,
   REGISTER_END_DATE    DATE,
   EXAM_START_DATE      DATE,
   EXAM_END_DATE        DATE,
   END_DATE             DATE,
   SCORE_START_DATE     DATE,
   SCORE_END_DATE       DATE,
   MATRICULATE_END_DATE DATE,
   SITE_IMPORT_START_DATE DATE,
   SITE_IMPORT_END_DATE DATE,
   SITE_EDIT_START_DATE DATE,
   SITE_EDIT_END_DATE   DATE,
   SITE_CHECK_START_DATE DATE,
   SITE_CHECK_END_DATE  DATE,
   NOTE                 VARCHAR2(1000),
   constraint PK_PE_RECRUITPLAN primary key (ID),
   constraint AK_KEY_1_PE_RECRU unique (NAME),
   constraint AK_AK_KEY_3_PE_RECRU_PE_RECRU unique (CODE)
);

comment on table PE_RECRUITPLAN is
'招生考试批次表';

comment on column PE_RECRUITPLAN.FK_GRADE_ID is
'唯一性约束';

comment on column PE_RECRUITPLAN.FLAG_ACTIVE is
'当前活动状态，只能有一个为1';

comment on column PE_RECRUITPLAN.START_DATE is
'招生计划开始日期';

comment on column PE_RECRUITPLAN.REGISTER_START_DATE is
'报名开始时间';

comment on column PE_RECRUITPLAN.REGISTER_END_DATE is
'报名结束时间';

comment on column PE_RECRUITPLAN.EXAM_START_DATE is
'入学考试开始时间';

comment on column PE_RECRUITPLAN.EXAM_END_DATE is
'入学考试结束时间';

comment on column PE_RECRUITPLAN.END_DATE is
'招生计划截止日期';

comment on column PE_RECRUITPLAN.SCORE_START_DATE is
'成绩录入开始时间';

comment on column PE_RECRUITPLAN.SCORE_END_DATE is
'成绩录入结束时间';

comment on column PE_RECRUITPLAN.MATRICULATE_END_DATE is
'录取结束时间，录取开始查询时间';

comment on column PE_RECRUITPLAN.SITE_IMPORT_START_DATE is
'分站招生录入开始时间';

comment on column PE_RECRUITPLAN.SITE_IMPORT_END_DATE is
'分站招生录入结束时间';

comment on column PE_RECRUITPLAN.SITE_EDIT_START_DATE is
'分站招生修改删除开始时间';

comment on column PE_RECRUITPLAN.SITE_EDIT_END_DATE is
'分站招生修改删除结束时间';

comment on column PE_RECRUITPLAN.SITE_CHECK_START_DATE is
'分站招生资料审核开始时间';

comment on column PE_RECRUITPLAN.SITE_CHECK_END_DATE is
'分站招生资料审核结束时间';

comment on column PE_RECRUITPLAN.NOTE is
'备注';

/*==============================================================*/
/* Table: PE_REC_EXAMCOURSE                                     */
/*==============================================================*/
create table PE_REC_EXAMCOURSE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FLAG_ARRANGE_ROOM    VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   NOTE                 VARCHAR2(1000),
   constraint PK_PE_REC_EXAMCOURSE primary key (ID),
   constraint AK_KEY_1_PE_REC_2 unique (NAME)
);

comment on table PE_REC_EXAMCOURSE is
'入学考试科目表';

comment on column PE_REC_EXAMCOURSE.FLAG_ARRANGE_ROOM is
'是否作为排考场依据科目（状态是1的科目，并集为全部学生，以这些科目分组安排考场）';

comment on column PE_REC_EXAMCOURSE.NOTE is
'考试科目备注';

/*==============================================================*/
/* Table: PE_REC_ROOM                                           */
/*==============================================================*/
create table PE_REC_ROOM  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50)                    not null,
   FK_SITE_ID           VARCHAR2(50),
   FK_RECRUITPLAN_ID    VARCHAR2(50),
   INVIGILATOR_A        VARCHAR2(50),
   INVIGILATOR_B        VARCHAR2(50),
   CLASSROOM            VARCHAR2(50),
   constraint PK_PE_REC_ROOM primary key (ID),
   constraint AK_KEY_2_PE_REC_R unique (NAME)
);

comment on table PE_REC_ROOM is
'入学考试考场表（学生入学考试地点不变）';

comment on column PE_REC_ROOM.NAME is
'招生批次+站点+场号';

comment on column PE_REC_ROOM.FK_SITE_ID is
'考试站点';

comment on column PE_REC_ROOM.CLASSROOM is
'教室';

/*==============================================================*/
/* Table: PE_REC_STUDENT                                        */
/*==============================================================*/
create table PE_REC_STUDENT  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_REC_MAJOR_SITE_ID VARCHAR2(50)                    not null,
   FLAG_MAJOR_TYPE      VARCHAR2(50),
   FK_REC_ROOM          VARCHAR2(50),
   FLAG_REC_CHANNEL     VARCHAR2(50),
   FLAG_REC_STATUS      VARCHAR2(50),
   FLAG_NOEXAM          VARCHAR2(50)                    not null,
   FLAG_NOEXAM_STATUS   VARCHAR2(50),
   FLAG_MATRICULATE     VARCHAR2(50),
   FLAG_PUBLISH         VARCHAR2(50),
   FLAG_TEACHER         VARCHAR2(50)                    not null,
   FLAG_TEACHER_STATUS  VARCHAR2(50),
   FLAG_XUELI_CHECK     VARCHAR2(50),
   REC_PROVINCE         VARCHAR2(50),
   GENDER               VARCHAR2(50),
   MATRICULATE_NUM      VARCHAR2(50),
   BIRTHDAY             DATE,
   CARD_TYPE            VARCHAR2(50),
   CARD_NO              VARCHAR2(20),
   FOLK                 VARCHAR2(20),
   PROVINCE             VARCHAR2(50),
   CITY                 VARCHAR2(50),
   SECTION              VARCHAR2(50),
   ZZMM                 VARCHAR2(40),
   XUELI                VARCHAR2(50),
   MARRIAGE             VARCHAR2(50),
   EMAIL                VARCHAR2(100),
   OCCUPATION           VARCHAR2(50),
   WORKPLACE            VARCHAR2(500),
   WORKPLACE_ZIP        VARCHAR2(50),
   WORKPLACE_PHONE      VARCHAR2(50),
   ADDRESS              VARCHAR2(500),
   PHONE                VARCHAR2(50),
   ZIP                  VARCHAR2(50),
   REGISTER             VARCHAR2(50),
   PHOTO_LINK           VARCHAR2(100),
   GRADUATE_SCHOOL      VARCHAR2(50),
   MOBILEPHONE          VARCHAR2(50),
   GRADUATE_DATE        VARCHAR2(50),
   GRADUATE_CODE        VARCHAR2(50),
   GRADUATE_MAJOR       VARCHAR2(50),
   WORK_BEGINDATE       VARCHAR2(50),
   SEAT_NUM             VARCHAR2(50),
   EXAM_CARD_NUM        VARCHAR2(50),
   PASSWORD             VARCHAR2(50),
   REC_EXAM_FEE         NUMBER(10,2),
   REC_EXAM_FEE_DATE    DATE,
   REC_EXAM_FEE_INVOICE VARCHAR2(50),
   NOTE                 VARCHAR2(1000),
   constraint PK_PE_REC_STUDENT primary key (ID),
   constraint AK_KEY_3_PE_REC_S unique (FK_REC_MAJOR_SITE_ID, CARD_TYPE, CARD_NO),
   constraint AK_KEY_4_PE_REC_S unique (EXAM_CARD_NUM)
);

comment on table PE_REC_STUDENT is
'招生报名信息表';

comment on column PE_REC_STUDENT.NAME is
'姓名';

comment on column PE_REC_STUDENT.FLAG_MAJOR_TYPE is
'专业备注';

comment on column PE_REC_STUDENT.FK_REC_ROOM is
'考场号';

comment on column PE_REC_STUDENT.FLAG_REC_CHANNEL is
'招生渠道';

comment on column PE_REC_STUDENT.FLAG_REC_STATUS is
'学生状态 0未审核 1分站已审核资料及交费 2分站已提交 3总校已审核 4总校已驳回';

comment on column PE_REC_STUDENT.FLAG_NOEXAM is
'是否为免试生 0考试生 1免试生';

comment on column PE_REC_STUDENT.FLAG_NOEXAM_STATUS is
'免试审核状态 0不通过 1通过 2暂通过 暂定3为考试生的状态';

comment on column PE_REC_STUDENT.FLAG_MATRICULATE is
'是否录取 0 待定 1已录取 2未录取';

comment on column PE_REC_STUDENT.FLAG_PUBLISH is
'录取信息是否已发布。0未发布，1已发布';

comment on column PE_REC_STUDENT.FLAG_TEACHER is
'是否有教师资格（教师教育专业提供）0 无 1有 2其它专业';

comment on column PE_REC_STUDENT.FLAG_TEACHER_STATUS is
'教师资格审核是否通过 0不通过 1通过 2暂通过 3其它不需要审核的';

comment on column PE_REC_STUDENT.FLAG_XUELI_CHECK is
'ENUMCONST学历验证（0未验证，1通过，2暂通过，3不通过）';

comment on column PE_REC_STUDENT.REC_PROVINCE is
'录取省份（暂不知用途）';

comment on column PE_REC_STUDENT.GENDER is
'性别';

comment on column PE_REC_STUDENT.MATRICULATE_NUM is
'录取号';

comment on column PE_REC_STUDENT.BIRTHDAY is
'出生日期';

comment on column PE_REC_STUDENT.CARD_TYPE is
'证件类型';

comment on column PE_REC_STUDENT.CARD_NO is
'证件号';

comment on column PE_REC_STUDENT.FOLK is
'民族';

comment on column PE_REC_STUDENT.PROVINCE is
'省';

comment on column PE_REC_STUDENT.CITY is
'市';

comment on column PE_REC_STUDENT.SECTION is
'区';

comment on column PE_REC_STUDENT.ZZMM is
'政治面貌';

comment on column PE_REC_STUDENT.XUELI is
'文化程度';

comment on column PE_REC_STUDENT.MARRIAGE is
'婚姻状况';

comment on column PE_REC_STUDENT.OCCUPATION is
'职业';

comment on column PE_REC_STUDENT.WORKPLACE is
'工作单位';

comment on column PE_REC_STUDENT.WORKPLACE_ZIP is
'单位邮编';

comment on column PE_REC_STUDENT.WORKPLACE_PHONE is
'单位电话';

comment on column PE_REC_STUDENT.ADDRESS is
'联系地址';

comment on column PE_REC_STUDENT.PHONE is
'联系电话';

comment on column PE_REC_STUDENT.ZIP is
'邮编';

comment on column PE_REC_STUDENT.REGISTER is
'户口';

comment on column PE_REC_STUDENT.PHOTO_LINK is
'照片路径';

comment on column PE_REC_STUDENT.GRADUATE_SCHOOL is
'毕业院校';

comment on column PE_REC_STUDENT.MOBILEPHONE is
'手机号码';

comment on column PE_REC_STUDENT.GRADUATE_DATE is
'毕业时间';

comment on column PE_REC_STUDENT.GRADUATE_CODE is
'毕业证编号';

comment on column PE_REC_STUDENT.GRADUATE_MAJOR is
'就读专业';

comment on column PE_REC_STUDENT.WORK_BEGINDATE is
'参加工作时间';

comment on column PE_REC_STUDENT.SEAT_NUM is
'座位号';

comment on column PE_REC_STUDENT.EXAM_CARD_NUM is
'准考证号';

comment on column PE_REC_STUDENT.PASSWORD is
'密码';

comment on column PE_REC_STUDENT.REC_EXAM_FEE is
'入学考试费';

comment on column PE_REC_STUDENT.REC_EXAM_FEE_DATE is
'导入时间';

comment on column PE_REC_STUDENT.REC_EXAM_FEE_INVOICE is
'发票号';

/*==============================================================*/
/* Table: PE_SEMESTER                                           */
/*==============================================================*/
create table PE_SEMESTER  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   SERIAL_NUMBER        NUMBER(4),
   FLAG_ACTIVE          CHAR(1)                        default '0',
   FLAG_NEXT_ACTIVE     CHAR(1)                        default '0',
   START_DATE           DATE,
   END_DATE             DATE,
   EXAM_BOOKING_START_DATE DATE,
   EXAM_BOOKING_END_DATE DATE,
   FINAL_EXAM_START_DATE DATE,
   FINAL_EXAM_END_DATE  DATE,
   FINAL_EXAM_SCORE_DATE DATE,
   GRADUATE_START_DATE  DATE,
   GRADUATE_END_DATE    DATE,
   ELECTIVE_FRESH_START_DATE DATE,
   ELECTIVE_FRESH_END_DATE DATE,
   ELECTIVE_START_DATE  DATE,
   ELECTIVE_END_DATE    DATE,
   CANCEL_ELECTIVE_START_DATE DATE,
   CANCEL_ELECTIVE_END_DATE DATE,
   FRESHMAN_ELECTIVE_START_DATE DATE,
   FRESHMAN_ELECTIVE_END_DATE DATE,
   PAPER_START_DATE     DATE,
   PAPER_END_DATE       DATE,
   PAPER_TITLE_END_DATE DATE,
   PAPER_SYLLABUS_END_DATE DATE,
   PAPER_DRAFT_END_DATE DATE,
   PAPER_FINAL_END_DATE DATE,
   PAPER_REJOIN_END_DATE DATE,
   SCORE_USUAL_START_DATE DATE,
   SCORE_USUAL_END_DATE DATE,
   MAIN_COURSE_START_DATE DATE,
   MAIN_COURSE_END_DATE DATE,
   constraint PK_PE_TCH_SEMESTER primary key (ID),
   constraint AK_KEY_1_PE_TCH_S unique (NAME),
   constraint AK_KEY_4_PE_SEMES unique (SERIAL_NUMBER)
);

comment on table PE_SEMESTER is
'学期';

comment on column PE_SEMESTER.SERIAL_NUMBER is
'序列计数，从2006秋为1，2007春为2，依次递增';

comment on column PE_SEMESTER.FLAG_ACTIVE is
'当前活动状态，只能有一个为1，0非活动 1活动';

comment on column PE_SEMESTER.FLAG_NEXT_ACTIVE is
'当前活动学期的下学期，只能有一个为1，0非活动 1活动';

comment on column PE_SEMESTER.START_DATE is
'学期开始日期';

comment on column PE_SEMESTER.END_DATE is
'学期结束日期';

comment on column PE_SEMESTER.EXAM_BOOKING_START_DATE is
'期末考试预约开始时间';

comment on column PE_SEMESTER.EXAM_BOOKING_END_DATE is
'期末考试预约结束时间';

comment on column PE_SEMESTER.FINAL_EXAM_START_DATE is
'期末考试开始时间';

comment on column PE_SEMESTER.FINAL_EXAM_END_DATE is
'期末考试结束时间';

comment on column PE_SEMESTER.FINAL_EXAM_SCORE_DATE is
'期末考试成绩查询开始时间';

comment on column PE_SEMESTER.GRADUATE_START_DATE is
'毕业申请开始时间';

comment on column PE_SEMESTER.GRADUATE_END_DATE is
'毕业申请结束时间';

comment on column PE_SEMESTER.ELECTIVE_FRESH_START_DATE is
'新生选课开始时间';

comment on column PE_SEMESTER.ELECTIVE_FRESH_END_DATE is
'新生选课结束时间';

comment on column PE_SEMESTER.ELECTIVE_START_DATE is
'选课开始日期';

comment on column PE_SEMESTER.ELECTIVE_END_DATE is
'选课结束日期';

comment on column PE_SEMESTER.CANCEL_ELECTIVE_START_DATE is
'退课开始时间';

comment on column PE_SEMESTER.CANCEL_ELECTIVE_END_DATE is
'退课结束时间';

comment on column PE_SEMESTER.FRESHMAN_ELECTIVE_START_DATE is
'新生选课开始时间';

comment on column PE_SEMESTER.FRESHMAN_ELECTIVE_END_DATE is
'新生选课结束时间';

comment on column PE_SEMESTER.PAPER_START_DATE is
'论文开始日期';

comment on column PE_SEMESTER.PAPER_END_DATE is
'论文结束日期';

comment on column PE_SEMESTER.PAPER_TITLE_END_DATE is
'论文选题截止日期';

comment on column PE_SEMESTER.PAPER_SYLLABUS_END_DATE is
'论文开题报告截止日期';

comment on column PE_SEMESTER.PAPER_DRAFT_END_DATE is
'论文初稿截止日期';

comment on column PE_SEMESTER.PAPER_FINAL_END_DATE is
'论文终稿截止日期';

comment on column PE_SEMESTER.PAPER_REJOIN_END_DATE is
'答辩截止日期';

comment on column PE_SEMESTER.SCORE_USUAL_START_DATE is
'平时成绩上报开始日期';

comment on column PE_SEMESTER.SCORE_USUAL_END_DATE is
'平时成绩上报截止日期';

comment on column PE_SEMESTER.MAIN_COURSE_START_DATE is
'主干课程考试报名开始时间';

comment on column PE_SEMESTER.MAIN_COURSE_END_DATE is
'主干课程考试报名结束时间';

/*==============================================================*/
/* Table: PE_SITE                                               */
/*==============================================================*/
create table PE_SITE  (
   ID                   VARCHAR2(50)                    not null,
   FK_EXAM_SITE_ID      VARCHAR2(50),
   FLAG_ACTIVE          VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   NAME                 VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50)                    not null,
   PROVINCE             VARCHAR2(50),
   ZIP_CODE             VARCHAR2(50),
   CITY                 VARCHAR2(50),
   LINE                 VARCHAR2(50),
   SEQUENCE             VARCHAR2(50),
   ADDRESS              VARCHAR2(1000),
   MANAGER              VARCHAR2(50),
   MANAGER_PHONE_OFFICE VARCHAR2(50),
   MANAGER_PHONE_HOME   VARCHAR2(50),
   MANAGER_MOBILEPHONE  VARCHAR2(50),
   RECRUIT_PHONE        VARCHAR2(50),
   RECRUIT_FAX          VARCHAR2(50),
   FOUND_DATE           DATE                           default sysdate,
   LAST_NIANSHEN_DATE   DATE                           default sysdate,
   NOTE                 VARCHAR2(1000),
   SCORE_LINE           NUMBER,
   constraint PK_PE_SITE primary key (ID),
   constraint AK_KEY_1_PE_SITE unique (NAME),
   constraint AK_KEY_4_PE_SITE unique (CODE)
);

comment on table PE_SITE is
'学习中心表';

comment on column PE_SITE.FK_EXAM_SITE_ID is
'本站点学生考试站点';

comment on column PE_SITE.FLAG_ACTIVE is
'学习中心是否招生';

comment on column PE_SITE.CODE is
'编号，唯一性约束，试点高校代码+三位顺序号（例：10574001）';

comment on column PE_SITE.PROVINCE is
'省份';

comment on column PE_SITE.ZIP_CODE is
'邮政编码';

comment on column PE_SITE.CITY is
'所属地级市';

comment on column PE_SITE.LINE is
'线路';

comment on column PE_SITE.SEQUENCE is
'顺序号';

comment on column PE_SITE.ADDRESS is
'详细地址';

comment on column PE_SITE.MANAGER is
'负责人姓名';

comment on column PE_SITE.MANAGER_PHONE_OFFICE is
'负责人办公电话';

comment on column PE_SITE.MANAGER_PHONE_HOME is
'负责人家庭电话';

comment on column PE_SITE.MANAGER_MOBILEPHONE is
'负责人手机';

comment on column PE_SITE.RECRUIT_PHONE is
'招生电话';

comment on column PE_SITE.RECRUIT_FAX is
'招生传真';

comment on column PE_SITE.FOUND_DATE is
'审核通过时间、创建时间';

comment on column PE_SITE.LAST_NIANSHEN_DATE is
'年审通过时间';

comment on column PE_SITE.NOTE is
'备注';

comment on column PE_SITE.SCORE_LINE is
'录取总分数线（招生模块后台使用）';

/*==============================================================*/
/* Table: PE_SITEMANAGER                                        */
/*==============================================================*/
create table PE_SITEMANAGER  (
   ID                   VARCHAR2(50)                    not null,
   LOGIN_ID             VARCHAR2(50),
   NAME                 VARCHAR2(50)                    not null,
   TRUE_NAME            VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(3),
   FK_SSO_USER_ID       VARCHAR2(50),
   FK_SITE_ID           VARCHAR2(50),
   FLAG_ISVALID         VARCHAR2(50),
   REGION_NAME          VARCHAR2(50),
   MOBILE_PHONE         VARCHAR2(50),
   PHONE                VARCHAR2(50),
   EMAIL                VARCHAR2(50),
   GENDER               VARCHAR2(50),
   ID_CARD              VARCHAR2(50),
   GRADUATION_INFO      VARCHAR2(200),
   GRADUATION_DATE      DATE,
   ADDRESS              VARCHAR2(300),
   ZHI_CHENG            VARCHAR2(50),
   WORK_TIME            VARCHAR2(50),
   GROUP_ID             VARCHAR2(1),
   NOTE                 VARCHAR2(50),
   constraint PK_PE_SITEMANAGER primary key (ID),
   constraint AK_KEY_2_PE_SITEM unique (NAME),
   constraint AK_KEY_3_PE_SITEM unique (LOGIN_ID)
);

comment on table PE_SITEMANAGER is
'分站管理员表';

comment on column PE_SITEMANAGER.LOGIN_ID is
'登录名，与SSO_USER.LOGIN_ID同步';

comment on column PE_SITEMANAGER.NAME is
'唯一约束名 用户名/姓名';

comment on column PE_SITEMANAGER.TRUE_NAME is
'姓名';

comment on column PE_SITEMANAGER.FLAG_ISVALID is
'是否有效 0无效，1有效';

comment on column PE_SITEMANAGER.REGION_NAME is
'所属地区名，对应考区信息';

comment on column PE_SITEMANAGER.MOBILE_PHONE is
'移动电话';

comment on column PE_SITEMANAGER.PHONE is
'固定电话';

comment on column PE_SITEMANAGER.EMAIL is
'电子邮箱';

comment on column PE_SITEMANAGER.GENDER is
'性别';

comment on column PE_SITEMANAGER.ID_CARD is
'身份证号';

comment on column PE_SITEMANAGER.GRADUATION_INFO is
'毕业院校及专业层次';

comment on column PE_SITEMANAGER.GRADUATION_DATE is
'毕业时间';

comment on column PE_SITEMANAGER.ADDRESS is
'通信地址及邮编';

comment on column PE_SITEMANAGER.ZHI_CHENG is
'职称';

comment on column PE_SITEMANAGER.WORK_TIME is
'从事成人教育工作时间';

comment on column PE_SITEMANAGER.GROUP_ID is
'是否为站长 0不是，1是';

comment on column PE_SITEMANAGER.NOTE is
'备注 人员类型';

/*==============================================================*/
/* Table: PE_SMS_INFO                                           */
/*==============================================================*/
create table PE_SMS_INFO  (
   ID                   VARCHAR2(50)                    not null,
   FK_SITE_ID           VARCHAR2(50),
   FLAG_SMS_TYPE        VARCHAR2(50),
   FLAG_SMS_STATUS      VARCHAR2(50),
   CREAT_DATE           DATE,
   BOOKING_DATE         DATE,
   SEND_DATE            DATE,
   RETURN_REASON        VARCHAR2(100),
   SENDER_NAME          VARCHAR2(50),
   SENDER_LOGIN_IN_ID   VARCHAR2(50),
   NOTE                 VARCHAR2(1000),
   constraint PK_PE_SMS_INFO primary key (ID)
);

comment on table PE_SMS_INFO is
'短信表';

comment on column PE_SMS_INFO.FK_SITE_ID is
'发送站点  空为总站';

comment on column PE_SMS_INFO.FLAG_SMS_TYPE is
'ENUMCONST 短信类型 0系统短信 1一般短信 2定时短信';

comment on column PE_SMS_INFO.FLAG_SMS_STATUS is
'ENUMCONST 短信审核状态 0未审核 1审核通过 2驳回';

comment on column PE_SMS_INFO.CREAT_DATE is
'短信创建日期';

comment on column PE_SMS_INFO.BOOKING_DATE is
'定时短信预约发送时间';

comment on column PE_SMS_INFO.SEND_DATE is
'实际短信发送日期';

comment on column PE_SMS_INFO.RETURN_REASON is
'驳回原因';

comment on column PE_SMS_INFO.SENDER_NAME is
'发送人姓名';

comment on column PE_SMS_INFO.SENDER_LOGIN_IN_ID is
'login_in号';

comment on column PE_SMS_INFO.NOTE is
'短信体';

/*==============================================================*/
/* Table: PE_SMS_SYSTEMPOINT                                    */
/*==============================================================*/
create table PE_SMS_SYSTEMPOINT  (
   ID                   VARCHAR2(50)                    not null,
   CODE                 VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FLAG_SMS_STATUS      VARCHAR2(50),
   FLAG_ISAUTO          VARCHAR2(50),
   SQL_NOTE             VARCHAR2(4000),
   NOTE                 VARCHAR2(4000),
   constraint SMS_SYSTEMPOINT_PK primary key (ID),
   constraint AK_KEY_2_PE_SMS_S unique (NAME),
   constraint AK_KEY_3_PE_SMS_S unique (CODE)
);

comment on table PE_SMS_SYSTEMPOINT is
'系统短信点表';

comment on column PE_SMS_SYSTEMPOINT.FLAG_SMS_STATUS is
'ENUMCONST 审核是否通过 0未审核 1审核通过';

comment on column PE_SMS_SYSTEMPOINT.FLAG_ISAUTO is
'ENUMCONST 是否活动  0手动 1自动';

comment on column PE_SMS_SYSTEMPOINT.SQL_NOTE is
'查询符合条件学生手机号sql';

/*==============================================================*/
/* Table: PE_STUDENT                                            */
/*==============================================================*/
create table PE_STUDENT  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   TRUE_NAME            VARCHAR2(50)                    not null,
   FK_SSO_USER_ID       VARCHAR2(50),
   FK_SITE_ID           VARCHAR2(50),
   FK_MAJOR_ID          VARCHAR2(50),
   FK_GRADE_ID          VARCHAR2(50),
   FK_EDUTYPE_ID        VARCHAR2(50),
   FK_FEE_LEVEL_ID      VARCHAR2(50),
   FK_STUDENT_INFO_ID   VARCHAR2(50),
   FK_REC_STUDENT_ID    VARCHAR2(50),
   REG_NO               VARCHAR2(50),
   KAOSHENGHAO          VARCHAR2(50),
   RECRUIT_DATE         DATE,
   FEE_BALANCE          NUMBER(10,2)                   default 0,
   FEE_INACTIVE         NUMBER(10,2)                   default 0,
   FLAG_MAJOR_TYPE      VARCHAR2(50),
   FLAG_STUDENT_STATUS  VARCHAR2(50),
   FLAG_ADVANCED        VARCHAR2(50),
   FLAG_DISOBEY         VARCHAR2(50),
   DISOBEY_NOTE         VARCHAR2(1000),
   SCORE_UNITE_ENGLISH_A VARCHAR2(50),
   SCORE_UNITE_ENGLISH_B VARCHAR2(50),
   SCORE_UNITE_ENGLISH_C VARCHAR2(50),
   SCORE_UNITE_COMPUTER VARCHAR2(50),
   SCORE_UNITE_YUWEN    VARCHAR2(50)                    not null,
   SCORE_UNITE_SHUXUE   VARCHAR2(50)                    not null,
   SCORE_DEGREE_ENGLISH NUMBER(4,1),
   DEGREE_ENGLISH_TYPE  VARCHAR2(50),
   GRADUATION_CERTIFICATE_NO VARCHAR2(50),
   GRADUATION_DATE      DATE,
   LEAVE_DATE           VARCHAR2(50),
   DEGREE_CERTIFICATE_NO VARCHAR2(50),
   DEGREE_DATE          DATE,
   constraint PK_PE_STUDENT primary key (ID),
   constraint AK_KEY_2_PE_STUDE unique (NAME),
   constraint AK_KEY_3_PE_STUDE unique (FK_STUDENT_INFO_ID),
   constraint AK_KEY_4_PE_STUDE unique (REG_NO)
);

comment on table PE_STUDENT is
'学生表';

comment on column PE_STUDENT.TRUE_NAME is
'真实姓名';

comment on column PE_STUDENT.FK_FEE_LEVEL_ID is
'收费标准';

comment on column PE_STUDENT.FK_REC_STUDENT_ID is
'招生报名信息关联，对应于PE_REC_STUDENT表ID字段';

comment on column PE_STUDENT.REG_NO is
'学号(刚进学籍库时没有学号，财务确认时生成)，登录名，与SSO_USER.LOGIN_ID同步';

comment on column PE_STUDENT.KAOSHENGHAO is
'考生号(报学籍使用)';

comment on column PE_STUDENT.RECRUIT_DATE is
'入学籍时间（预交费财务确认时）';

comment on column PE_STUDENT.FEE_BALANCE is
'帐户余额，包括学费、教材费，可以为负';

comment on column PE_STUDENT.FEE_INACTIVE is
'不可退余额，通过减免费用进帐的总额';

comment on column PE_STUDENT.FLAG_MAJOR_TYPE is
'专业备注（跨专业 非跨专业 专业方向）';

comment on column PE_STUDENT.FLAG_STUDENT_STATUS is
'ENUMCONST 0 已录取未在学籍 1已交费 2已上报 3已确认交费 4已注册在籍 5已毕业 6 开除学籍 7退学';

comment on column PE_STUDENT.FLAG_ADVANCED is
'ENUMCONST 0非优秀生 1优秀生';

comment on column PE_STUDENT.FLAG_DISOBEY is
'ENUMCONST 违纪记录状态 ';

comment on column PE_STUDENT.DISOBEY_NOTE is
'违纪情况备注';

comment on column PE_STUDENT.SCORE_UNITE_ENGLISH_A is
'ENUMCONST统考英语A成绩：0合格 1不合格、2违纪、3作弊、4缺考';

comment on column PE_STUDENT.SCORE_UNITE_ENGLISH_B is
'ENUMCONST统考英语B成绩：0合格、1不合格、2违纪、3作弊、4缺考';

comment on column PE_STUDENT.SCORE_UNITE_ENGLISH_C is
'ENUMCONST统考英语C成绩：0合格、1不合格、2违纪、3作弊、4缺考';

comment on column PE_STUDENT.SCORE_UNITE_COMPUTER is
'ENUMCONST统考计算机成绩：0合格、1不合格、2违纪、3作弊、4缺考';

comment on column PE_STUDENT.SCORE_UNITE_YUWEN is
'ENUMCONST统考语文B成绩：0合格、1不合格、2违纪、3作弊、4缺考';

comment on column PE_STUDENT.SCORE_UNITE_SHUXUE is
'ENUMCONST统考数学B成绩：0合格、1不合格、2违纪、3作弊、4缺考';

comment on column PE_STUDENT.SCORE_DEGREE_ENGLISH is
'学位外语成绩（百分制）';

comment on column PE_STUDENT.DEGREE_ENGLISH_TYPE is
'备注学位外语类型（英语日语）';

comment on column PE_STUDENT.GRADUATION_CERTIFICATE_NO is
'毕业证号';

comment on column PE_STUDENT.GRADUATION_DATE is
'毕业时间';

comment on column PE_STUDENT.LEAVE_DATE is
'离校时间';

comment on column PE_STUDENT.DEGREE_CERTIFICATE_NO is
'学位证号';

comment on column PE_STUDENT.DEGREE_DATE is
'获得学位时间';

/*==============================================================*/
/* Index: "Index_1"                                             */
/*==============================================================*/
create index "Index_1" on PE_STUDENT (
   FK_SITE_ID ASC
);

/*==============================================================*/
/* Table: PE_TCH_BOOK                                           */
/*==============================================================*/
create table PE_TCH_BOOK  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   TRUE_NAME            VARCHAR2(50)                    not null,
   FLAG_ISVALID         VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   CODE                 VARCHAR2(50),
   ISBN                 VARCHAR2(50),
   AUTHOR               VARCHAR2(50),
   PUBLISHER            VARCHAR2(100),
   PRICE                NUMBER(10,2)                   default 0,
   NOTE                 VARCHAR2(1000),
   constraint PK_PE_TCH_BOOK primary key (ID),
   constraint AK_KEY_2_PE_TCH_B unique (CODE)
);

comment on table PE_TCH_BOOK is
'教材表';

comment on column PE_TCH_BOOK.FLAG_ISVALID is
'状态位：是否有效';

comment on column PE_TCH_BOOK.FLAG_BAK is
'备用状态位';

comment on column PE_TCH_BOOK.CODE is
'编号';

/*==============================================================*/
/* Table: PE_TCH_COURSE                                         */
/*==============================================================*/
create table PE_TCH_COURSE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_EXAM_SCORE_INPUT_USERA_ID VARCHAR2(50),
   FK_EXAM_SCORE_INPUT_USERB_ID VARCHAR2(50),
   CODE                 VARCHAR2(50)                    not null,
   FLAG_ISVALID         VARCHAR2(50),
   FLAG_ISPUBLICCOURSE  VARCHAR2(50),
   CREDIT               NUMBER(4,1),
   SCORE_SYSTEM_RATE    NUMBER(4,1),
   SCORE_HOMEWORK_RATE  NUMBER(4,1),
   SCORE_EXAM_RATE      NUMBER(4,1),
   SCORE_USUAL_RATE     NUMBER(4,1),
   NOTE                 CLOB,
   constraint PK_PE_TCH_COURSE primary key (ID),
   constraint AK_KEY_1_PE_TCH_3 unique (NAME),
   constraint AK_KEY_3_PE_TCH_C unique (CODE)
);

comment on table PE_TCH_COURSE is
'课程表';

comment on column PE_TCH_COURSE.FK_EXAM_SCORE_INPUT_USERA_ID is
'成绩录入人AID';

comment on column PE_TCH_COURSE.FK_EXAM_SCORE_INPUT_USERB_ID is
'成绩录入人BID';

comment on column PE_TCH_COURSE.CODE is
'课程编号，唯一性约束';

comment on column PE_TCH_COURSE.FLAG_ISVALID is
'是否有效';

comment on column PE_TCH_COURSE.FLAG_ISPUBLICCOURSE is
'是否为公共选修课';

comment on column PE_TCH_COURSE.CREDIT is
'学分';

comment on column PE_TCH_COURSE.SCORE_SYSTEM_RATE is
'系统成绩所占比例';

comment on column PE_TCH_COURSE.SCORE_HOMEWORK_RATE is
'作业成绩所占比例';

comment on column PE_TCH_COURSE.SCORE_EXAM_RATE is
'考试成绩所占比例';

comment on column PE_TCH_COURSE.SCORE_USUAL_RATE is
'平时成绩所占比例';

comment on column PE_TCH_COURSE.NOTE is
'课程简介';

/*==============================================================*/
/* Table: PE_TCH_COURSEGROUP                                    */
/*==============================================================*/
create table PE_TCH_COURSEGROUP  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   constraint PK_PE_TCH_COURSEGROUP primary key (ID),
   constraint AK_KEY_2_PE_TCH_C unique (NAME)
);

comment on table PE_TCH_COURSEGROUP is
'课程分组表
(些表ID号 固定使用
   _1  _2   _3   _4 
程序使用)';

/*==============================================================*/
/* Table: PE_TCH_COURSEWARE                                     */
/*==============================================================*/
create table PE_TCH_COURSEWARE  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_COURSE_ID         VARCHAR2(50),
   FLAG_ISVALID         VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   CODE                 VARCHAR2(50),
   AUTHOR               VARCHAR2(50),
   PUBLISHER            VARCHAR2(50),
   NOTE                 VARCHAR2(4000),
   LINK                 VARCHAR2(1000),
   constraint PK_PE_TCH_COURSEWARE primary key (ID),
   constraint AK_KEY_1_PE_TCH_5 unique (NAME),
   constraint AK_KEY_4_PE_TCH_C unique (FK_COURSE_ID, LINK)
);

comment on table PE_TCH_COURSEWARE is
'课件表（自AUTHOR向下数据字段对应旧版LRN_COURSEWARE_INFO表 张利斌 20080620）';

comment on column PE_TCH_COURSEWARE.FLAG_ISVALID is
'状态位：是否有效';

comment on column PE_TCH_COURSEWARE.CODE is
'编号';

/*==============================================================*/
/* Table: PE_TCH_PROGRAM                                        */
/*==============================================================*/
create table PE_TCH_PROGRAM  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   FK_MAJOR_ID          VARCHAR2(50),
   FK_EDUTYPE_ID        VARCHAR2(50),
   FK_GRADE_ID          VARCHAR2(50),
   FLAG_DEGREE_CANDISOBEY VARCHAR2(50),
   FLAG_MAJOR_TYPE      VARCHAR2(50),
   FLAG_UNITE_A         VARCHAR2(50),
   FLAG_UNITE_B         VARCHAR2(50),
   GRADUATE_MIN_CREDIT  NUMBER,
   DEGREE_AVG_SCORE     NUMBER(4,1),
   DEGREE_PAPER_SCORE   NUMBER(4,1),
   PAPER_MIN_CREDIT_HOUR NUMBER,
   PAPER_MIN_SEMESER    NUMBER,
   MAX_ELECTIVE         NUMBER,
   MIN_SEMESTER         NUMBER(4,1),
   MAX_SEMESTER         NUMBER(4,1),
   NOTE                 VARCHAR2(1000),
   constraint PK_PE_TCH_PROGRAM primary key (ID),
   constraint AK_KEY_1_PE_TCH_7 unique (NAME),
   constraint AK_KEY_3_PE_TCH_P unique (FK_MAJOR_ID, FK_EDUTYPE_ID, FK_GRADE_ID, FLAG_MAJOR_TYPE)
);

comment on table PE_TCH_PROGRAM is
'教学计划';

comment on column PE_TCH_PROGRAM.FLAG_DEGREE_CANDISOBEY is
'ENUMCONST 取得学位是否可以违纪 0不可以 1可以';

comment on column PE_TCH_PROGRAM.FLAG_MAJOR_TYPE is
'专业备注（跨专业 非跨专业 专业方向）';

comment on column PE_TCH_PROGRAM.FLAG_UNITE_A is
'ENUMCONST统考课程A';

comment on column PE_TCH_PROGRAM.FLAG_UNITE_B is
'ENUMCONST统考课程B';

comment on column PE_TCH_PROGRAM.GRADUATE_MIN_CREDIT is
'毕业最低学分标准（现为80）';

comment on column PE_TCH_PROGRAM.DEGREE_AVG_SCORE is
'取得学位平均分';

comment on column PE_TCH_PROGRAM.DEGREE_PAPER_SCORE is
'取得学位最低论文分数';

comment on column PE_TCH_PROGRAM.PAPER_MIN_CREDIT_HOUR is
'选择毕业课程的最小学分';

comment on column PE_TCH_PROGRAM.PAPER_MIN_SEMESER is
'选择毕业课程的最小学期';

comment on column PE_TCH_PROGRAM.MAX_ELECTIVE is
'最大选课数';

comment on column PE_TCH_PROGRAM.MIN_SEMESTER is
'最小学年（学期？）';

comment on column PE_TCH_PROGRAM.MAX_SEMESTER is
'最大学年（学期？）';

/*==============================================================*/
/* Table: PE_TCH_PROGRAM_GROUP                                  */
/*==============================================================*/
create table PE_TCH_PROGRAM_GROUP  (
   ID                   VARCHAR2(50)                    not null,
   FK_PROGRAM_ID        VARCHAR2(50),
   FK_COURSEGROUP_ID    VARCHAR2(50),
   MIN_CREDIT           NUMBER,
   MAX_CREDIT           NUMBER,
   constraint PK_PE_TCH_PROGRAM_GROUP primary key (ID)
);

comment on column PE_TCH_PROGRAM_GROUP.MIN_CREDIT is
'最低学分要求';

comment on column PE_TCH_PROGRAM_GROUP.MAX_CREDIT is
'最高学分限制';

/*==============================================================*/
/* Table: PE_TCH_REJOIN_ROOM                                    */
/*==============================================================*/
create table PE_TCH_REJOIN_ROOM  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50),
   FK_SEMESTER_ID       VARCHAR2(50),
   TRUE_NAME            VARCHAR2(50),
   CODE                 NUMBER(1),
   constraint PK_PE_TCH_REJOIN_ROOM primary key (ID),
   constraint AK_KEY_2_PE_TCH_R unique (NAME)
);

comment on table PE_TCH_REJOIN_ROOM is
'答辩教室';

comment on column PE_TCH_REJOIN_ROOM.NAME is
'下拉名';

comment on column PE_TCH_REJOIN_ROOM.FK_SEMESTER_ID is
'学期';

comment on column PE_TCH_REJOIN_ROOM.TRUE_NAME is
'教室名';

comment on column PE_TCH_REJOIN_ROOM.CODE is
'教室编号（1-6）';

/*==============================================================*/
/* Table: PE_TCH_REJOIN_SECTION                                 */
/*==============================================================*/
create table PE_TCH_REJOIN_SECTION  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50),
   FK_SEMESTER_ID       VARCHAR2(50),
   REJOIN_DATETIME      DATE,
   SEQUENCE             NUMBER(2),
   constraint PK_PE_TCH_REJOIN_SECTION primary key (ID),
   constraint AK_AK_KEY_2_PE_TCH_R unique (NAME)
);

comment on table PE_TCH_REJOIN_SECTION is
'答辩时间段';

comment on column PE_TCH_REJOIN_SECTION.FK_SEMESTER_ID is
'学期';

comment on column PE_TCH_REJOIN_SECTION.REJOIN_DATETIME is
'时间';

comment on column PE_TCH_REJOIN_SECTION.SEQUENCE is
'序号';

/*==============================================================*/
/* Table: PE_TEACHER                                            */
/*==============================================================*/
create table PE_TEACHER  (
   ID                   VARCHAR2(50)                    not null,
   LOGIN_ID             VARCHAR2(50),
   NAME                 VARCHAR2(50)                    not null,
   TRUE_NAME            VARCHAR2(50)                    not null,
   FK_SSO_USER_ID       VARCHAR2(50),
   FK_MAJOR_ID          VARCHAR2(50),
   FLAG_ACTIVE          VARCHAR2(50)                    not null,
   FLAG_PAPER           VARCHAR2(50)                    not null,
   ID_CARD              VARCHAR2(50)                    not null,
   GENDER               VARCHAR2(50),
   BIRTHDAY             DATE,
   FLAG_MAX_XUEWEI      VARCHAR2(50),
   FLAG_ZHICHENG        VARCHAR2(50)                    not null,
   FLAG_MAX_XUELI       VARCHAR2(50)                    not null,
   GRADUATE_SCHOOL      VARCHAR2(50),
   GRADUATE_MAJOR       VARCHAR2(50),
   PHONE_OFFICE         VARCHAR2(50),
   PHONE_HOME           VARCHAR2(100),
   MOBILEPHONE          VARCHAR2(50)                    not null,
   EMAIL                VARCHAR2(100)                   not null,
   WORKPLACE            VARCHAR2(50),
   STU_COUNT_LIMIT      NUMBER,
   NOTE                 VARCHAR2(1000),
   constraint PK_PE_TEACHER primary key (ID),
   constraint AK_KEY_2_PE_TEACH unique (NAME),
   constraint AK_KEY_3_PE_TEACH unique (LOGIN_ID)
);

comment on table PE_TEACHER is
'教师表';

comment on column PE_TEACHER.LOGIN_ID is
'登录名，与SSO_USER.LOGIN_ID同步';

comment on column PE_TEACHER.TRUE_NAME is
'真实姓名';

comment on column PE_TEACHER.FK_MAJOR_ID is
'所属专业';

comment on column PE_TEACHER.FLAG_ACTIVE is
'是否有效：1有效/0无效';

comment on column PE_TEACHER.FLAG_PAPER is
'是否带毕业论文：1是/0否';

comment on column PE_TEACHER.ID_CARD is
'身份证号';

comment on column PE_TEACHER.GENDER is
'ENUMCONST姓别 0男 1女';

comment on column PE_TEACHER.FLAG_MAX_XUEWEI is
'ENUMCONST最高学位';

comment on column PE_TEACHER.FLAG_ZHICHENG is
'ENUMCONST职称 0教授 1副教授 2讲师 3其它';

comment on column PE_TEACHER.FLAG_MAX_XUELI is
'ENUMCONST最高学历';

comment on column PE_TEACHER.GRADUATE_SCHOOL is
'毕业院校';

comment on column PE_TEACHER.GRADUATE_MAJOR is
'学历专业';

comment on column PE_TEACHER.PHONE_OFFICE is
'单位电话';

comment on column PE_TEACHER.PHONE_HOME is
'家庭';

comment on column PE_TEACHER.MOBILEPHONE is
'手机';

comment on column PE_TEACHER.WORKPLACE is
'工作单位';

/*==============================================================*/
/* Table: PE_VOTE_PAPER                                         */
/*==============================================================*/
create table PE_VOTE_PAPER  (
   ID                   VARCHAR2(50)                    not null,
   TITLE                VARCHAR2(500)                   not null,
   PICTITLE             VARCHAR2(500),
   FLAG_ISVALID         VARCHAR2(50),
   FLAG_CAN_SUGGEST     VARCHAR2(50),
   FLAG_VIEW_SUGGEST    VARCHAR2(50),
   FLAG_LIMIT_DIFFIP    VARCHAR2(50),
   FLAG_LIMIT_DIFFSESSION VARCHAR2(50),
   FLAG_TYPE            VARCHAR2(50),
   FOUND_DATE           DATE,
   START_DATE           DATE,
   END_DATE             DATE,
   KEYWORDS             VARCHAR2(4000),
   NOTE                 CLOB,
   constraint PK_PE_VOTE_PAPER primary key (ID)
);

comment on table PE_VOTE_PAPER is
'投票表';

comment on column PE_VOTE_PAPER.TITLE is
'标题';

comment on column PE_VOTE_PAPER.PICTITLE is
'图片标题（来自M4,暂确定是否使用）';

comment on column PE_VOTE_PAPER.FLAG_ISVALID is
'ENUMCONST是否有效';

comment on column PE_VOTE_PAPER.FLAG_CAN_SUGGEST is
'ENUMCONST是否有建议功能';

comment on column PE_VOTE_PAPER.FLAG_VIEW_SUGGEST is
'ENUMCONST是否可查看建议';

comment on column PE_VOTE_PAPER.FLAG_LIMIT_DIFFIP is
'ENUMCONST是否限制IP';

comment on column PE_VOTE_PAPER.FLAG_LIMIT_DIFFSESSION is
'ENUMCONST是否限制SESSION';

comment on column PE_VOTE_PAPER.FLAG_TYPE is
'ENUMCONST类型（0 普通投票、1 课程投票）';

comment on column PE_VOTE_PAPER.FOUND_DATE is
'创建日期';

comment on column PE_VOTE_PAPER.START_DATE is
'投票开始时间';

comment on column PE_VOTE_PAPER.END_DATE is
'投票结束时间';

comment on column PE_VOTE_PAPER.KEYWORDS is
'关键字';

comment on column PE_VOTE_PAPER.NOTE is
'正文';

/*==============================================================*/
/* Table: PR_COURSE_ARRANGE                                     */
/*==============================================================*/
create table PR_COURSE_ARRANGE  (
   ID                   VARCHAR2(50)                    not null,
   FK_OPENCOURSE_ID     VARCHAR2(50),
   ARRANGE_DATE         DATE,
   FLAG_COURSE_SECTION  VARCHAR2(50),
   CLASSROOM            VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   constraint PK_PR_COURSE_ARRANGE primary key (ID),
   constraint AK_KEY_2_PR_COURS unique (ARRANGE_DATE, FLAG_COURSE_SECTION, CLASSROOM)
);

comment on table PR_COURSE_ARRANGE is
'课程表  实地上课安排';

comment on column PR_COURSE_ARRANGE.FK_OPENCOURSE_ID is
'课程';

comment on column PR_COURSE_ARRANGE.ARRANGE_DATE is
'日期';

comment on column PR_COURSE_ARRANGE.FLAG_COURSE_SECTION is
'时间段';

comment on column PR_COURSE_ARRANGE.CLASSROOM is
'教室';

/*==============================================================*/
/* Table: PR_DOCUMENT                                           */
/*==============================================================*/
create table PR_DOCUMENT  (
   ID                   VARCHAR2(50)                    not null,
   FK_DOCUMENT_ID       VARCHAR2(50),
   FK_SSO_ID            VARCHAR2(50),
   FLAG_READ            VARCHAR2(50),
   FLAG_DEL             VARCHAR2(50),
   READ_DATE            DATE,
   constraint PK_PR_DOCUMENT primary key (ID),
   constraint AK_KEY_2_PR_DOCUM unique (FK_DOCUMENT_ID, FK_SSO_ID)
);

comment on table PR_DOCUMENT is
'公文关系表';

comment on column PR_DOCUMENT.FK_SSO_ID is
'公文接收者';

comment on column PR_DOCUMENT.FLAG_READ is
'ENUMCONST  0未读 1已阅读 ';

comment on column PR_DOCUMENT.FLAG_DEL is
'ENUMCONST 0可见 1收件箱已删除';

comment on column PR_DOCUMENT.READ_DATE is
'阅读时间';

/*==============================================================*/
/* Table: PR_EDU_MAJOR_SITE_FEE_LEVEL                           */
/*==============================================================*/
create table PR_EDU_MAJOR_SITE_FEE_LEVEL  (
   ID                   VARCHAR2(50)                    not null,
   FK_EDUTYPE_ID        VARCHAR2(50)                    not null,
   FK_MAJOR_ID          VARCHAR2(50)                    not null,
   FK_SITE_ID           VARCHAR2(50)                    not null,
   FK_FEE_LEVEL_ID      VARCHAR2(50)                    not null,
   constraint PK_PR_EDU_MAJOR_SITE_FEE_LEVEL primary key (ID),
   constraint AK_KEY_2_PR_EDU_M unique (FK_EDUTYPE_ID, FK_MAJOR_ID, FK_SITE_ID)
);

comment on table PR_EDU_MAJOR_SITE_FEE_LEVEL is
'层次-专业-站点-收费标准关系表';

/*==============================================================*/
/* Table: PR_EXAM_BOOKING                                       */
/*==============================================================*/
create table PR_EXAM_BOOKING  (
   ID                   VARCHAR2(50)                    not null,
   FK_SEMESTER_ID       VARCHAR2(50),
   FK_TCH_STU_ELECTIVE_ID VARCHAR2(50),
   BOOKING_DATE         DATE                           default sysdate,
   FK_EXAM_NO_ID        VARCHAR2(50),
   FK_EXAM_ROOM_ID      VARCHAR2(50),
   SEAT_NO              VARCHAR2(3),
   FLAG_SCORE_STATUSA   VARCHAR2(50),
   SCORE_EXAM_A         NUMBER(4,1),
   FLAG_SCORE_STATUSB   VARCHAR2(50),
   SCORE_EXAM_B         NUMBER(4,1),
   SCORE_EXAM           NUMBER(4,1),
   FLAG_SCORE_STATUS    VARCHAR2(50),
   constraint PK_PR_EXAM_BOOKING primary key (ID),
   constraint AK_KEY_2_PR_EXA17 unique (FK_TCH_STU_ELECTIVE_ID, FK_SEMESTER_ID)
);

comment on table PR_EXAM_BOOKING is
'预约考试表';

comment on column PR_EXAM_BOOKING.FK_SEMESTER_ID is
'考试学期，临时存放信息';

comment on column PR_EXAM_BOOKING.BOOKING_DATE is
'预约日期';

comment on column PR_EXAM_BOOKING.FK_EXAM_NO_ID is
'考试场次，临时存放信息';

comment on column PR_EXAM_BOOKING.FK_EXAM_ROOM_ID is
'考场 ';

comment on column PR_EXAM_BOOKING.SEAT_NO is
'座位号';

comment on column PR_EXAM_BOOKING.FLAG_SCORE_STATUSA is
'A登分人记录';

comment on column PR_EXAM_BOOKING.SCORE_EXAM_A is
'A登分人记录';

comment on column PR_EXAM_BOOKING.FLAG_SCORE_STATUSB is
'B登分人记录';

comment on column PR_EXAM_BOOKING.SCORE_EXAM_B is
'B登分人记录';

comment on column PR_EXAM_BOOKING.SCORE_EXAM is
'最终考试成绩';

comment on column PR_EXAM_BOOKING.FLAG_SCORE_STATUS is
'成绩状态，0末录入，1正常，2缺考，3违纪，4作弊';

/*==============================================================*/
/* Table: PR_EXAM_OPEN_MAINCOURSE                               */
/*==============================================================*/
create table PR_EXAM_OPEN_MAINCOURSE  (
   ID                   VARCHAR2(50)                    not null,
   FK_COURSE_ID         VARCHAR2(50),
   FK_EXAM_MAINCOURSE_NO_ID VARCHAR2(50),
   constraint PK_PR_EXAM_OPEN_MAINCOURSE primary key (ID),
   constraint AK_KEY_2_PR_EXAM_ unique (FK_COURSE_ID, FK_EXAM_MAINCOURSE_NO_ID)
);

/*==============================================================*/
/* Table: PR_EXAM_PATROL_SETTING                                */
/*==============================================================*/
create table PR_EXAM_PATROL_SETTING  (
   ID                   VARCHAR2(50)                    not null,
   FK_SEMESTER_ID       VARCHAR2(50),
   FK_SITE_ID           VARCHAR2(50),
   FK_EXAM_PATROL_ID    VARCHAR2(50),
   constraint PK_PR_EXAM_PATROL_SETTING primary key (ID)
);

comment on table PR_EXAM_PATROL_SETTING is
'巡考人员安排表';

/*==============================================================*/
/* Table: PR_EXAM_STU_MAINCOURSE                                */
/*==============================================================*/
create table PR_EXAM_STU_MAINCOURSE  (
   ID                   VARCHAR2(50)                    not null,
   FK_STUDENT_ID        VARCHAR2(50),
   FK_EXAM_OPEN_MAINCOURSE_ID VARCHAR2(50),
   FK_EXAM_MAINCOURSE_ROOM_ID VARCHAR2(50),
   SEAT_NO              VARCHAR2(3),
   SCORE                NUMBER(4,1),
   FLAG_SCORE_STATUS    VARCHAR2(50),
   FLAG_SCORE_PUB       VARCHAR2(50),
   constraint PK_PR_EXAM_STU_MAINCOURSE primary key (ID),
   constraint AK_KEY_2_PR_EXA26 unique (FK_STUDENT_ID, FK_EXAM_OPEN_MAINCOURSE_ID)
);

comment on table PR_EXAM_STU_MAINCOURSE is
'学生主干课程考试预约表';

comment on column PR_EXAM_STU_MAINCOURSE.SEAT_NO is
'座位号';

comment on column PR_EXAM_STU_MAINCOURSE.SCORE is
'考试成绩';

comment on column PR_EXAM_STU_MAINCOURSE.FLAG_SCORE_STATUS is
'考试成绩状态（未录入、正常、违纪、作弊）';

comment on column PR_EXAM_STU_MAINCOURSE.FLAG_SCORE_PUB is
'成绩是否发布';

/*==============================================================*/
/* Table: PR_FEE_DETAIL                                         */
/*==============================================================*/
create table PR_FEE_DETAIL  (
   ID                   VARCHAR2(50)                    not null,
   FK_STU_ID            VARCHAR2(50),
   FK_FEE_BATCH_ID      VARCHAR2(50),
   FLAG_FEE_TYPE        VARCHAR2(50),
   FLAG_FEE_CHECK       VARCHAR2(50),
   FEE_AMOUNT           NUMBER(10,2),
   CREDIT_AMOUNT        NUMBER(10,2),
   INPUT_DATE           DATE                           default sysdate,
   INVOICE_NO           VARCHAR2(50),
   NOTE                 VARCHAR2(1000),
   constraint PK_PR_FEE_DETAIL primary key (ID)
);

comment on table PR_FEE_DETAIL is
'学生交消费明细';

comment on column PR_FEE_DETAIL.FK_FEE_BATCH_ID is
'对应收费批次ID，消费项不需上报或交费项未上报时没有收费批次，此字段空，上报时建立收费批次，同时关联此字段';

comment on column PR_FEE_DETAIL.FLAG_FEE_TYPE is
'费用类型';

comment on column PR_FEE_DETAIL.FLAG_FEE_CHECK is
'上报审核状态：‘0’未上报，‘1’已上报，‘2’已审核（生效）';

comment on column PR_FEE_DETAIL.FEE_AMOUNT is
'费用额（交费为+，消费为-）';

comment on column PR_FEE_DETAIL.CREDIT_AMOUNT is
'学生帐户余额（费用生效时与学生帐户同步）';

comment on column PR_FEE_DETAIL.INPUT_DATE is
'交费日期';

comment on column PR_FEE_DETAIL.INVOICE_NO is
'发票号';

comment on column PR_FEE_DETAIL.NOTE is
'备注';

/*==============================================================*/
/* Table: PR_PC_BOOKING_SEAT                                    */
/*==============================================================*/
create table PR_PC_BOOKING_SEAT  (
   ID                   VARCHAR2(50)                    not null,
   FK_OPENCOURSE_ID     VARCHAR2(50),
   COURSE_DATETIME      DATE,
   BOOKING_BEGIN_DATETIME DATE,
   BOOKING_END_DATETIME DATE,
   LIMIT_NUM            NUMBER(5),
   constraint PK_PR_PC_BOOKING_SEAT primary key (ID)
);

comment on table PR_PC_BOOKING_SEAT is
'订座信息管理表';

comment on column PR_PC_BOOKING_SEAT.COURSE_DATETIME is
'上课时间';

comment on column PR_PC_BOOKING_SEAT.BOOKING_BEGIN_DATETIME is
'开始预定时间';

comment on column PR_PC_BOOKING_SEAT.BOOKING_END_DATETIME is
'预定结束时间';

comment on column PR_PC_BOOKING_SEAT.LIMIT_NUM is
'限定人数';

/*==============================================================*/
/* Table: PR_PC_ELECTIVE                                        */
/*==============================================================*/
create table PR_PC_ELECTIVE  (
   ID                   VARCHAR2(50)                    not null,
   FK_STU_ID            VARCHAR2(50),
   FK_OPENCOURSE_ID     VARCHAR2(50),
   FLAG_ENROL           VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   ROOM_NUM             VARCHAR2(50),
   SEAT_NUM             VARCHAR2(50),
   SCORE                NUMBER(4,1),
   constraint PK_PR_PC_ELECTIVE primary key (ID)
);

comment on table PR_PC_ELECTIVE is
'学生公选课选课表';

comment on column PR_PC_ELECTIVE.FLAG_ENROL is
'ENUMCONST是否注册';

comment on column PR_PC_ELECTIVE.ROOM_NUM is
'考场号';

comment on column PR_PC_ELECTIVE.SEAT_NUM is
'座位号';

comment on column PR_PC_ELECTIVE.SCORE is
'分数';

/*==============================================================*/
/* Table: PR_PC_NOTE_REPLY                                      */
/*==============================================================*/
create table PR_PC_NOTE_REPLY  (
   ID                   VARCHAR2(50)                    not null,
   FK_NOTE_ID           VARCHAR2(50),
   FK_ELECTIVE_ID       VARCHAR2(50),
   REPLY_DATETIME       DATE,
   NOTE                 VARCHAR2(4000),
   constraint PK_PR_PC_NOTE_REPLY primary key (ID)
);

comment on table PR_PC_NOTE_REPLY is
'教师笔记回复表';

comment on column PR_PC_NOTE_REPLY.REPLY_DATETIME is
'回复时间';

comment on column PR_PC_NOTE_REPLY.NOTE is
'回复内容';

/*==============================================================*/
/* Table: PR_PC_OPENCOURSE                                      */
/*==============================================================*/
create table PR_PC_OPENCOURSE  (
   ID                   VARCHAR2(50)                    not null,
   FK_COURSE_ID         VARCHAR2(50),
   FK_SEMESTER_ID       VARCHAR2(50),
   constraint PK_PR_PC_OPENCOURSE primary key (ID)
);

comment on table PR_PC_OPENCOURSE is
'公选课开课表';

/*==============================================================*/
/* Table: PR_PC_OPENCOURSE_TEACHER                              */
/*==============================================================*/
create table PR_PC_OPENCOURSE_TEACHER  (
   ID                   VARCHAR2(50)                    not null,
   FK_OPENCOURSE_ID     VARCHAR2(50),
   FK_TEACHER_ID        VARCHAR2(50),
   constraint PK_PR_PC_OPENCOURSE_TEACHER primary key (ID)
);

comment on table PR_PC_OPENCOURSE_TEACHER is
'开课教师关系表';

/*==============================================================*/
/* Table: PR_PC_STU_BOOKING                                     */
/*==============================================================*/
create table PR_PC_STU_BOOKING  (
   ID                   VARCHAR2(50)                    not null,
   FK_BOOKING_SEAT_ID   VARCHAR2(50),
   FK_ELECTIVE_ID       VARCHAR2(50),
   constraint PK_PR_PC_STU_BOOKING primary key (ID),
   constraint AK_KEY_2_PR_PC_ST unique (FK_BOOKING_SEAT_ID, FK_ELECTIVE_ID)
);

comment on table PR_PC_STU_BOOKING is
'学生订座表';

/*==============================================================*/
/* Table: PR_PC_STU_EXERCISE                                    */
/*==============================================================*/
create table PR_PC_STU_EXERCISE  (
   ID                   VARCHAR2(50)                    not null,
   FK_EXERCISE_ID       VARCHAR2(50),
   FK_ELECTIVE_ID       VARCHAR2(50),
   NOTE                 CLOB,
   constraint PK_PR_PC_STU_EXERCISE primary key (ID)
);

comment on table PR_PC_STU_EXERCISE is
'学生提交作业';

/*==============================================================*/
/* Table: PR_PRI_MANAGER_EDUTYPE                                */
/*==============================================================*/
create table PR_PRI_MANAGER_EDUTYPE  (
   ID                   VARCHAR2(50)                    not null,
   FK_SSO_USER_ID       VARCHAR2(50),
   FK_EDUTYPE_ID        VARCHAR2(50),
   constraint PK_PR_PRI_MANAGER_EDUTYPE primary key (ID)
);

comment on table PR_PRI_MANAGER_EDUTYPE is
'管理员管理学生权限范围';

comment on column PR_PRI_MANAGER_EDUTYPE.FK_SSO_USER_ID is
'SSO_USER表的ID外键';

comment on column PR_PRI_MANAGER_EDUTYPE.FK_EDUTYPE_ID is
'层次ID，为空表示全部层次';

/*==============================================================*/
/* Table: PR_PRI_MANAGER_GRADE                                  */
/*==============================================================*/
create table PR_PRI_MANAGER_GRADE  (
   ID                   VARCHAR2(50)                    not null,
   FK_SSO_USER_ID       VARCHAR2(50),
   FK_GRADE_ID          VARCHAR2(50),
   constraint PK_PR_PRI_MANAGER_GRADE primary key (ID)
);

comment on table PR_PRI_MANAGER_GRADE is
'管理员管理学生权限范围';

comment on column PR_PRI_MANAGER_GRADE.FK_SSO_USER_ID is
'SSO_USER表的ID外键';

comment on column PR_PRI_MANAGER_GRADE.FK_GRADE_ID is
'年级ID，为空表示全部年级';

/*==============================================================*/
/* Table: PR_PRI_MANAGER_MAJOR                                  */
/*==============================================================*/
create table PR_PRI_MANAGER_MAJOR  (
   ID                   VARCHAR2(50)                    not null,
   FK_SSO_USER_ID       VARCHAR2(50),
   FK_MAJOR_ID          VARCHAR2(50),
   constraint PK_PR_PRI_MANAGER_MAJOR primary key (ID)
);

comment on table PR_PRI_MANAGER_MAJOR is
'管理员管理学生权限范围';

comment on column PR_PRI_MANAGER_MAJOR.FK_SSO_USER_ID is
'SSO_USER表的ID外键';

comment on column PR_PRI_MANAGER_MAJOR.FK_MAJOR_ID is
'专业ID，为空表示全部专业';

/*==============================================================*/
/* Table: PR_PRI_MANAGER_SITE                                   */
/*==============================================================*/
create table PR_PRI_MANAGER_SITE  (
   ID                   VARCHAR2(50)                    not null,
   FK_SSO_USER_ID       VARCHAR2(50),
   FK_SITE_ID           VARCHAR2(50),
   constraint PK_PR_PRI_MANAGER_SITE primary key (ID)
);

comment on table PR_PRI_MANAGER_SITE is
'管理员管理学生权限范围（学习中心）';

comment on column PR_PRI_MANAGER_SITE.FK_SSO_USER_ID is
'SSO_USER表的ID外键';

comment on column PR_PRI_MANAGER_SITE.FK_SITE_ID is
'学习中心ID，与PE_SITEMANAGER表FK_SITE_ID相同，为提高查询效率冗余此字段';

/*==============================================================*/
/* Table: PR_PRI_ROLE                                           */
/*==============================================================*/
create table PR_PRI_ROLE  (
   ID                   VARCHAR2(50)                    not null,
   FK_ROLE_ID           VARCHAR2(50),
   FK_PRIORITY_ID       VARCHAR2(50),
   FLAG_ISVALID         CHAR(1),
   constraint PK_PR_PRI_ROLE primary key (ID)
);

comment on table PR_PRI_ROLE is
'角色-权限 关系';

comment on column PR_PRI_ROLE.FLAG_ISVALID is
'是否有效  表记录不变 是否有权限靠本字段';

/*==============================================================*/
/* Table: PR_REC_COURSE_MAJOR_EDUTYPE                           */
/*==============================================================*/
create table PR_REC_COURSE_MAJOR_EDUTYPE  (
   ID                   VARCHAR2(50)                    not null,
   FK_MAJOR_ID          VARCHAR2(50),
   FK_EDUTYPE_ID        VARCHAR2(50),
   FK_REC_EXAMCOURSE_ID VARCHAR2(50),
   constraint PK_PR_REC_COURSE_MAJOR_EDUTYPE primary key (ID),
   constraint AK_KEY_2_PR_REC_C unique (FK_MAJOR_ID, FK_EDUTYPE_ID, FK_REC_EXAMCOURSE_ID)
);

comment on table PR_REC_COURSE_MAJOR_EDUTYPE is
'入学考试课程与专业、层次默认关系表（排考场操作时，按此表内容生成每个学生的考试记录PR_REC_EXAM_STU_COURSE）';

/*==============================================================*/
/* Table: PR_REC_EXAM_COURSE_TIME                               */
/*==============================================================*/
create table PR_REC_EXAM_COURSE_TIME  (
   ID                   VARCHAR2(50)                    not null,
   FK_RECRUITPLAN_ID    VARCHAR2(50),
   FK_REC_EXAMCOURSE_ID VARCHAR2(50),
   START_DATETIME       DATE,
   END_DATETIME         DATE,
   SCORE_LINE           NUMBER,
   constraint PK_PR_REC_EXAM_COURSE_TIME primary key (ID),
   constraint AK_KEY_2_PR_REC_6 unique (FK_RECRUITPLAN_ID, FK_REC_EXAMCOURSE_ID)
);

comment on table PR_REC_EXAM_COURSE_TIME is
'入学考试课程时间表（创建考试批次后确定每门考试时间）';

comment on column PR_REC_EXAM_COURSE_TIME.FK_RECRUITPLAN_ID is
'考试批次';

comment on column PR_REC_EXAM_COURSE_TIME.FK_REC_EXAMCOURSE_ID is
'考试科目';

comment on column PR_REC_EXAM_COURSE_TIME.START_DATETIME is
'考试开始时间';

comment on column PR_REC_EXAM_COURSE_TIME.END_DATETIME is
'考试结束时间';

comment on column PR_REC_EXAM_COURSE_TIME.SCORE_LINE is
'单科分数线（只用于后台）';

/*==============================================================*/
/* Table: PR_REC_EXAM_STU_COURSE                                */
/*==============================================================*/
create table PR_REC_EXAM_STU_COURSE  (
   ID                   VARCHAR2(50)                    not null,
   FK_REC_STUDENT_ID    VARCHAR2(50),
   FK_REC_EXAM_COURSE_TIME_ID VARCHAR2(50),
   SCORE                NUMBER(4,1)                    default 0,
   constraint PK_PR_REC_EXAM_STU_COURSE primary key (ID)
);

comment on table PR_REC_EXAM_STU_COURSE is
'入学考试学生-课程 关系（排考场操作时，按PR_REC_COURSE_MAJOR_EDUTYPE表记录，按每个学生所属专业、层次生成记录）';

comment on column PR_REC_EXAM_STU_COURSE.FK_REC_STUDENT_ID is
'报名学生';

comment on column PR_REC_EXAM_STU_COURSE.FK_REC_EXAM_COURSE_TIME_ID is
'考试科目时间表ID';

comment on column PR_REC_EXAM_STU_COURSE.SCORE is
'分数';

/*==============================================================*/
/* Table: PR_REC_PATROL_SETTING                                 */
/*==============================================================*/
create table PR_REC_PATROL_SETTING  (
   ID                   VARCHAR2(50)                    not null,
   FK_RECRUITPLAN_ID    VARCHAR2(50),
   FK_SITE_ID           VARCHAR2(50),
   FK_EXAM_PATROL_ID    VARCHAR2(50),
   constraint PK_PR_REC_PATROL_SETTING primary key (ID)
);

comment on table PR_REC_PATROL_SETTING is
'入学考试巡考安排';

/*==============================================================*/
/* Table: PR_REC_PLAN_MAJOR_EDUTYPE                             */
/*==============================================================*/
create table PR_REC_PLAN_MAJOR_EDUTYPE  (
   ID                   VARCHAR2(50)                    not null,
   FK_RECRUITPLAN_ID    VARCHAR2(50),
   FK_MAJOR_ID          VARCHAR2(50),
   FK_EDUTYPE_ID        VARCHAR2(50),
   constraint PK_PR_REC_PLAN_MAJOR_EDU primary key (ID),
   constraint AK_KEY_2_PR_REC_4 unique (FK_RECRUITPLAN_ID, FK_MAJOR_ID, FK_EDUTYPE_ID)
);

comment on table PR_REC_PLAN_MAJOR_EDUTYPE is
'招生计划-专业-层次 关系';

/*==============================================================*/
/* Table: PR_REC_PLAN_MAJOR_SITE                                */
/*==============================================================*/
create table PR_REC_PLAN_MAJOR_SITE  (
   ID                   VARCHAR2(50)                    not null,
   FK_SITE_ID           VARCHAR2(50),
   FK_REC_PLAN_MAJOR_EDUTYPE_ID VARCHAR2(50),
   constraint PK_PR_REC_PLAN_MAJOR_SITE primary key (ID),
   constraint AK_KEY_2_PR_REC_5 unique (FK_SITE_ID, FK_REC_PLAN_MAJOR_EDUTYPE_ID)
);

comment on table PR_REC_PLAN_MAJOR_SITE is
'招生计划-专业-层次-学习中心 人数';

/*==============================================================*/
/* Table: PR_SMS_SEND_STATUS                                    */
/*==============================================================*/
create table PR_SMS_SEND_STATUS  (
   ID                   VARCHAR2(50)                    not null,
   FK_SMS_INFO_ID       VARCHAR2(50),
   MOBILE_PHONE         VARCHAR2(50),
   FLAG_SEND_STATUS     VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   constraint PK_PR_SMS_SEND_STATUS primary key (ID)
);

comment on table PR_SMS_SEND_STATUS is
'短信条 具体到手机号';

comment on column PR_SMS_SEND_STATUS.MOBILE_PHONE is
'手机号';

comment on column PR_SMS_SEND_STATUS.FLAG_SEND_STATUS is
'发送状态1已发送 0未发送';

/*==============================================================*/
/* Table: PR_STUDENT_HISTORY                                    */
/*==============================================================*/
create table PR_STUDENT_HISTORY  (
   ID                   VARCHAR2(50)                    not null,
   FK_STUDENT_ID        VARCHAR2(50),
   DATE_SECTION         VARCHAR2(50),
   PLACE                VARCHAR2(500),
   ZHIWU                VARCHAR2(50),
   constraint PK_PR_STUDENT_HISTORY primary key (ID)
);

comment on table PR_STUDENT_HISTORY is
'学生简历表（学校学籍表中使用）';

comment on column PR_STUDENT_HISTORY.DATE_SECTION is
'起止时间';

comment on column PR_STUDENT_HISTORY.PLACE is
'在何单位（学校）工作（学习）';

comment on column PR_STUDENT_HISTORY.ZHIWU is
'职务或职称';

/*==============================================================*/
/* Table: PR_STUDENT_INFO                                       */
/*==============================================================*/
create table PR_STUDENT_INFO  (
   ID                   VARCHAR2(50)                    not null,
   BIRTHDAY             DATE,
   GENDER               VARCHAR2(4),
   CARD_TYPE            VARCHAR2(50),
   CARD_NO              VARCHAR2(50),
   FORK                 VARCHAR2(50),
   PROVINCE             VARCHAR2(50),
   CITY                 VARCHAR2(50),
   ZZMM                 VARCHAR2(50),
   XUELI                VARCHAR2(50),
   MARRIAGE             VARCHAR2(50),
   EMAIL                VARCHAR2(50),
   OCCUPATION           VARCHAR2(50),
   WORKPLACE            VARCHAR2(500),
   ZIP                  VARCHAR2(50),
   ADDRESS              VARCHAR2(500),
   PHONE                VARCHAR2(50),
   MOBILEPHONE          VARCHAR2(50),
   GRADUATE_SCHOOL      VARCHAR2(50),
   GRADUATE_SCHOOL_CODE VARCHAR2(50),
   GRADUATE_YEAR        VARCHAR2(50),
   GRADUATE_CODE        VARCHAR2(50),
   XUEZHI               VARCHAR2(50),
   PHOTO_LINK           VARCHAR2(100),
   PHOTO_DATE           DATE,
   LAST_INFO_UPDATE_DATE DATE,
   constraint PK_PR_STUDENT_INFO primary key (ID)
);

comment on table PR_STUDENT_INFO is
'学生资料表';

comment on column PR_STUDENT_INFO.BIRTHDAY is
'出生日期';

comment on column PR_STUDENT_INFO.GENDER is
'性别';

comment on column PR_STUDENT_INFO.CARD_TYPE is
'证件类型';

comment on column PR_STUDENT_INFO.CARD_NO is
'证件号';

comment on column PR_STUDENT_INFO.FORK is
'民族';

comment on column PR_STUDENT_INFO.PROVINCE is
'省';

comment on column PR_STUDENT_INFO.CITY is
'市';

comment on column PR_STUDENT_INFO.ZZMM is
'政治面貌';

comment on column PR_STUDENT_INFO.XUELI is
'入学前最高学历';

comment on column PR_STUDENT_INFO.MARRIAGE is
'婚姻情况';

comment on column PR_STUDENT_INFO.EMAIL is
'电子邮件';

comment on column PR_STUDENT_INFO.OCCUPATION is
'职业';

comment on column PR_STUDENT_INFO.WORKPLACE is
'工作单位';

comment on column PR_STUDENT_INFO.ZIP is
'邮政编码';

comment on column PR_STUDENT_INFO.ADDRESS is
'地址';

comment on column PR_STUDENT_INFO.PHONE is
'联系电话';

comment on column PR_STUDENT_INFO.MOBILEPHONE is
'手机号';

comment on column PR_STUDENT_INFO.GRADUATE_SCHOOL is
'毕业学校';

comment on column PR_STUDENT_INFO.GRADUATE_SCHOOL_CODE is
'毕业学校编号';

comment on column PR_STUDENT_INFO.GRADUATE_YEAR is
'毕业年份';

comment on column PR_STUDENT_INFO.GRADUATE_CODE is
'毕业证书号';

comment on column PR_STUDENT_INFO.XUEZHI is
'学制';

comment on column PR_STUDENT_INFO.PHOTO_LINK is
'毕业照片地址';

comment on column PR_STUDENT_INFO.PHOTO_DATE is
'上传日期';

comment on column PR_STUDENT_INFO.LAST_INFO_UPDATE_DATE is
'资料最后更新时间';

/*==============================================================*/
/* Table: PR_STU_CHANGEABLE_MAJOR                               */
/*==============================================================*/
create table PR_STU_CHANGEABLE_MAJOR  (
   ID                   VARCHAR2(50)                    not null,
   FK_OLD_MAJOR_ID      VARCHAR2(50),
   FK_NEW_MAJOR_ID      VARCHAR2(50),
   constraint PK_PR_STU_CHANGEABLE_MAJOR primary key (ID)
);

comment on table PR_STU_CHANGEABLE_MAJOR is
'可转专业表';

comment on column PR_STU_CHANGEABLE_MAJOR.FK_OLD_MAJOR_ID is
'转专业来源';

comment on column PR_STU_CHANGEABLE_MAJOR.FK_NEW_MAJOR_ID is
'转专业去向';

/*==============================================================*/
/* Table: PR_STU_CHANGE_EDUTYPE                                 */
/*==============================================================*/
create table PR_STU_CHANGE_EDUTYPE  (
   ID                   VARCHAR2(50)                    not null,
   FK_STU_ID            VARCHAR2(50),
   FK_OLD_EDUTYPE_ID    VARCHAR2(50),
   FK_NEW_EDUTYPE_ID    VARCHAR2(50),
   C_DATE               DATE,
   NOTE                 CLOB,
   constraint PK_PR_STU_CHANGE_EDUTYPE primary key (ID)
);

comment on table PR_STU_CHANGE_EDUTYPE is
'学生转层次表';

/*==============================================================*/
/* Table: PR_STU_CHANGE_MAJOR                                   */
/*==============================================================*/
create table PR_STU_CHANGE_MAJOR  (
   ID                   VARCHAR2(50)                    not null,
   FK_STU_ID            VARCHAR2(50),
   FK_OLD_MAJOR_ID      VARCHAR2(50),
   FK_NEW_MAJOR_ID      VARCHAR2(50),
   C_DATE               DATE,
   NOTE                 CLOB,
   constraint PK_PR_STU_CHANGE_MAJOR primary key (ID)
);

comment on table PR_STU_CHANGE_MAJOR is
'学生转专业表';

comment on column PR_STU_CHANGE_MAJOR.C_DATE is
'转专业时间';

comment on column PR_STU_CHANGE_MAJOR.NOTE is
'备注';

/*==============================================================*/
/* Table: PR_STU_CHANGE_SCHOOL                                  */
/*==============================================================*/
create table PR_STU_CHANGE_SCHOOL  (
   ID                   VARCHAR2(50)                    not null,
   FK_STU_ID            VARCHAR2(50),
   FLAG_ABORTSCHOOL_TYPE VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   OP_DATE              DATE                           default sysdate,
   NOTE                 VARCHAR2(1000),
   constraint PK_PA_STU_SCHOOL_CHANGE1 primary key (ID)
);

comment on table PR_STU_CHANGE_SCHOOL is
'学籍异动表（开除学籍 退学）';

comment on column PR_STU_CHANGE_SCHOOL.FLAG_ABORTSCHOOL_TYPE is
'ENUMCONST 0退学 1开除学籍';

comment on column PR_STU_CHANGE_SCHOOL.OP_DATE is
'操作日期';

comment on column PR_STU_CHANGE_SCHOOL.NOTE is
'备注';

/*==============================================================*/
/* Table: PR_STU_CHANGE_SITE                                    */
/*==============================================================*/
create table PR_STU_CHANGE_SITE  (
   ID                   VARCHAR2(50)                    not null,
   FK_STU_ID            VARCHAR2(50),
   FK_OLD_SITE_ID       VARCHAR2(50),
   FK_NEW_SITE_ID       VARCHAR2(50),
   C_DATE               DATE,
   NOTE                 CLOB,
   constraint PK_PR_STU_CHANGE_SITE primary key (ID)
);

/*==============================================================*/
/* Table: PR_STU_MULTI_MAJOR                                    */
/*==============================================================*/
create table PR_STU_MULTI_MAJOR  (
   ID                   VARCHAR2(50)                    not null,
   FK_MAJOR_ID          VARCHAR2(50),
   OLD_MAJOR            VARCHAR2(50),
   constraint PK_PR_STU_MULTI_MAJOR primary key (ID),
   constraint AK_KEY_2_PR_STU_M unique (FK_MAJOR_ID, OLD_MAJOR)
);

comment on table PR_STU_MULTI_MAJOR is
'跨专业表（认为两个专业相似）';

comment on column PR_STU_MULTI_MAJOR.OLD_MAJOR is
'原专业';

/*==============================================================*/
/* Table: PR_TCH_COURSE_BOOK                                    */
/*==============================================================*/
create table PR_TCH_COURSE_BOOK  (
   ID                   VARCHAR2(50)                    not null,
   FK_COURSE_ID         VARCHAR2(50),
   FK_BOOK_ID           VARCHAR2(50),
   constraint PK_PR_TCH_COURSE_BOOK primary key (ID),
   constraint AK_AK_KEY_2_PR_TCH_C_PR_TCH_C unique (FK_COURSE_ID, FK_BOOK_ID)
);

/*==============================================================*/
/* Table: PR_TCH_COURSE_TEACHER                                 */
/*==============================================================*/
create table PR_TCH_COURSE_TEACHER  (
   ID                   VARCHAR2(50)                    not null,
   FK_COURSE_ID         VARCHAR2(50),
   FK_TEACHER_ID        VARCHAR2(50),
   FLAG_TEACHER_TYPE    VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   constraint PK_PR_TCH_COURSE_TEACHER primary key (ID),
   constraint AK_KEY_2_PR_TCH_C unique (FK_COURSE_ID, FK_TEACHER_ID, FLAG_TEACHER_TYPE)
);

comment on table PR_TCH_COURSE_TEACHER is
'课程-教师 关系（课程默认授课教师，设置以后开课选教师从此选出）';

comment on column PR_TCH_COURSE_TEACHER.FLAG_TEACHER_TYPE is
'本课程的教师角色（主讲、责任）';

/*==============================================================*/
/* Table: PR_TCH_ELECTIVE_BOOK                                  */
/*==============================================================*/
create table PR_TCH_ELECTIVE_BOOK  (
   ID                   VARCHAR2(50)                    not null,
   FK_ELECTIVE_ID       VARCHAR2(50),
   FK_OPENCOURSE_BOOK_ID VARCHAR2(50),
   constraint PK_PR_TCH_ELECTIVE_BOOK primary key (ID)
);

comment on table PR_TCH_ELECTIVE_BOOK is
'选课选教材表';

/*==============================================================*/
/* Table: PR_TCH_OPENCOURSE                                     */
/*==============================================================*/
create table PR_TCH_OPENCOURSE  (
   ID                   VARCHAR2(50)                    not null,
   FK_COURSE_ID         VARCHAR2(50),
   FK_SEMESTER_ID       VARCHAR2(50),
   ADVICE_EXAM_NO       VARCHAR2(50),
   COURSE_TIME          NUMBER(4),
   FLAG_EXAM_COURSE     VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   constraint PK_PR_TCH_OPENCOURSE primary key (ID),
   constraint AK_KEY_2_PR_TCH_O unique (FK_COURSE_ID, FK_SEMESTER_ID)
);

comment on table PR_TCH_OPENCOURSE is
'开课表 考试场次-课程 关系';

comment on column PR_TCH_OPENCOURSE.FK_COURSE_ID is
'课程';

comment on column PR_TCH_OPENCOURSE.ADVICE_EXAM_NO is
'建议考试场次';

comment on column PR_TCH_OPENCOURSE.COURSE_TIME is
'上课次数';

comment on column PR_TCH_OPENCOURSE.FLAG_EXAM_COURSE is
'是否为考试课程';

/*==============================================================*/
/* Table: PR_TCH_OPENCOURSE_BOOK                                */
/*==============================================================*/
create table PR_TCH_OPENCOURSE_BOOK  (
   ID                   VARCHAR2(50)                    not null,
   FK_OPENCOURSE_ID     VARCHAR2(50),
   FK_BOOK_ID           VARCHAR2(50),
   constraint PK_PR_TCH_OPENCOURSE_BOOK primary key (ID)
);

comment on table PR_TCH_OPENCOURSE_BOOK is
'开课对应教材表';

/*==============================================================*/
/* Table: PR_TCH_OPENCOURSE_COURSEWARE                          */
/*==============================================================*/
create table PR_TCH_OPENCOURSE_COURSEWARE  (
   ID                   VARCHAR2(50)                    not null,
   FK_OPENCOURSE_ID     VARCHAR2(50),
   FK_COURSEWARE_ID     VARCHAR2(50),
   constraint PK_PR_TCH_OPENCOURSE_COURSEWAR primary key (ID)
);

comment on table PR_TCH_OPENCOURSE_COURSEWARE is
'开课对应课件表';

/*==============================================================*/
/* Table: PR_TCH_OPENCOURSE_TEACHER                             */
/*==============================================================*/
create table PR_TCH_OPENCOURSE_TEACHER  (
   ID                   VARCHAR2(50)                    not null,
   FK_OPENCOURSE_ID     VARCHAR2(50),
   FK_TEACHER_ID        VARCHAR2(50),
   constraint PK_PR_TCH_OPENCOURSE_TEACHER primary key (ID)
);

comment on table PR_TCH_OPENCOURSE_TEACHER is
'开课-授课教师 关系（用户从默认关系表PR_TCH_COURSE_TEACHER中选择）';

/*==============================================================*/
/* Table: PR_TCH_PAPER_CONTENT                                  */
/*==============================================================*/
create table PR_TCH_PAPER_CONTENT  (
   ID                   VARCHAR2(50)                    not null,
   FK_TCH_STU_PAPER     VARCHAR2(50)                    not null,
   URL                  VARCHAR2(1000),
   NOTE                 VARCHAR2(1000),
   ACTION_DATE          DATE                           default sysdate not null,
   FLAG_ACTION_USER     VARCHAR2(50)                    not null,
   FLAG_PAPER_SECTION   VARCHAR2(50)                    not null,
   constraint PK_PR_TCH_PAPER_CONTENT primary key (ID)
);

comment on table PR_TCH_PAPER_CONTENT is
'论文内容表';

comment on column PR_TCH_PAPER_CONTENT.FK_TCH_STU_PAPER is
'所属论文';

comment on column PR_TCH_PAPER_CONTENT.URL is
'附件地址链接';

comment on column PR_TCH_PAPER_CONTENT.NOTE is
'留言';

comment on column PR_TCH_PAPER_CONTENT.ACTION_DATE is
'操作日期';

comment on column PR_TCH_PAPER_CONTENT.FLAG_ACTION_USER is
'ENUMCONST提交人 0学生 1教师';

comment on column PR_TCH_PAPER_CONTENT.FLAG_PAPER_SECTION is
'ENUMCONST所属论文阶段 0开题报告 1论文初稿 2论文终稿';

/*==============================================================*/
/* Table: PR_TCH_PAPER_TITLE                                    */
/*==============================================================*/
create table PR_TCH_PAPER_TITLE  (
   ID                   VARCHAR2(50)                    not null,
   FK_SEMESTER          VARCHAR2(50),
   FK_TEACHER           VARCHAR2(50),
   TITLE                VARCHAR2(100)                   not null,
   TITLE_MEMO           VARCHAR2(1000),
   STU_COUNT_LIMIT      NUMBER                         default 10,
   constraint PK_PR_TCH_PAPER_TITLE2 primary key (ID),
   constraint AK_KEY_9_PR_TCH_P unique (FK_SEMESTER, FK_TEACHER, TITLE)
);

comment on table PR_TCH_PAPER_TITLE is
'教师-论文题目表';

comment on column PR_TCH_PAPER_TITLE.TITLE is
'论文题目';

comment on column PR_TCH_PAPER_TITLE.TITLE_MEMO is
'题目备注信息';

comment on column PR_TCH_PAPER_TITLE.STU_COUNT_LIMIT is
'同一题目最多选题人数';

/*==============================================================*/
/* Table: PR_TCH_PROGRAM_COURSE                                 */
/*==============================================================*/
create table PR_TCH_PROGRAM_COURSE  (
   ID                   VARCHAR2(50)                    not null,
   FK_PROGRAMGROUP_ID   VARCHAR2(50),
   FK_COURSE_ID         VARCHAR2(50),
   FLAG_IS_MAIN_COURSE  VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   CREDIT               NUMBER(4,1),
   UNIT                 INTEGER,
   constraint PK_PE_TCH_PROGRAM_COURSE primary key (ID),
   constraint AK_KEY_2_PR_TCH_P unique (FK_PROGRAMGROUP_ID, FK_COURSE_ID)
);

comment on table PR_TCH_PROGRAM_COURSE is
'培养计划-课程 关系';

comment on column PR_TCH_PROGRAM_COURSE.FLAG_IS_MAIN_COURSE is
'ENUMCONST 是否为主干课程 0不是 1是';

comment on column PR_TCH_PROGRAM_COURSE.UNIT is
'教学单元';

/*==============================================================*/
/* Table: PR_TCH_STU_ELECTIVE                                   */
/*==============================================================*/
create table PR_TCH_STU_ELECTIVE  (
   ID                   VARCHAR2(50)                    not null,
   FK_STU_ID            VARCHAR2(50),
   FK_TCH_PROGRAM_COURSE VARCHAR2(50),
   FK_TCH_OPENCOURSE_ID VARCHAR2(50),
   FK_OPERATOR_ID       VARCHAR2(50),
   FLAG_ELECTIVE_ADMISSION VARCHAR2(50),
   FLAG_SCORE_STATUS    VARCHAR2(50),
   FLAG_SCORE_PUB       VARCHAR2(50),
   ELECTIVE_DATE        DATE                            not null,
   PRI                  VARCHAR2(50),
   SCORE_SYSTEM         NUMBER(4,1),
   SCORE_USUAL          NUMBER(4,1),
   SCORE_HOMEWORK       NUMBER(4,1),
   SCORE_EXAM           NUMBER(4,1),
   SCORE_TOTAL          NUMBER(4,1),
   ONLINE_TIME          VARCHAR2(50),
   ENTER_TIMES          NUMBER(8),
   constraint PK_PA_LRN_STU_ELECTIVE primary key (ID),
   constraint AK_KEY_2_PR_TCH_S unique (FK_STU_ID, FK_TCH_OPENCOURSE_ID),
   constraint AK_KEY_3_PR_TCH_S unique (FK_STU_ID, FK_TCH_PROGRAM_COURSE)
);

comment on table PR_TCH_STU_ELECTIVE is
'学生选课表';

comment on column PR_TCH_STU_ELECTIVE.FK_TCH_PROGRAM_COURSE is
'教学计划';

comment on column PR_TCH_STU_ELECTIVE.FK_TCH_OPENCOURSE_ID is
'开课';

comment on column PR_TCH_STU_ELECTIVE.FLAG_ELECTIVE_ADMISSION is
'是否选课确认 （即平台为学生开课）0未开课 1已开课';

comment on column PR_TCH_STU_ELECTIVE.FLAG_SCORE_STATUS is
'成绩状态，0末录入，1正常，2缺考，3违纪，4作弊';

comment on column PR_TCH_STU_ELECTIVE.FLAG_SCORE_PUB is
'成绩是否已发布，0否，1是';

comment on column PR_TCH_STU_ELECTIVE.ELECTIVE_DATE is
'选课时间';

comment on column PR_TCH_STU_ELECTIVE.PRI is
'优先级';

comment on column PR_TCH_STU_ELECTIVE.SCORE_SYSTEM is
'系统成绩';

comment on column PR_TCH_STU_ELECTIVE.SCORE_USUAL is
'平时成绩';

comment on column PR_TCH_STU_ELECTIVE.SCORE_HOMEWORK is
'作业成绩';

comment on column PR_TCH_STU_ELECTIVE.SCORE_EXAM is
'考试成绩';

comment on column PR_TCH_STU_ELECTIVE.SCORE_TOTAL is
'总评成绩';

comment on column PR_TCH_STU_ELECTIVE.ONLINE_TIME is
'在线时间';

comment on column PR_TCH_STU_ELECTIVE.ENTER_TIMES is
'登录交流园地次数';

/*==============================================================*/
/* Table: PR_TCH_STU_PAPER                                      */
/*==============================================================*/
create table PR_TCH_STU_PAPER  (
   ID                   VARCHAR2(50)                    not null,
   FK_STU_ID            VARCHAR2(50),
   FK_PAPER_TITLE_ID    VARCHAR2(50),
   FK_REJOIN_SECTION_ID VARCHAR2(50),
   FK_REJOIN_ROOM_ID    VARCHAR2(50),
   FLAG_TITLE_ADMISSION VARCHAR2(50),
   FLAG_SYLLABUS_LAST_UPDATE VARCHAR2(50),
   FLAG_DRAFT_A_LAST_UPDATE VARCHAR2(50),
   FLAG_FINAL_LAST_UPDATE VARCHAR2(50),
   FLAG_PAPER_REJOIN    VARCHAR2(50),
   TITLE_DATE           DATE,
   FINAL_SCORE          NUMBER(4,1),
   FINAL_SCORE_DATE     DATE,
   REJOIN_SCORE         NUMBER(4,1),
   SCORE_TOTAL          NUMBER(4,1),
   constraint PK_PR_STU_PAPER4 primary key (ID)
);

comment on table PR_TCH_STU_PAPER is
'学生毕业论文表';

comment on column PR_TCH_STU_PAPER.FK_STU_ID is
'学生ID';

comment on column PR_TCH_STU_PAPER.FK_PAPER_TITLE_ID is
'选题';

comment on column PR_TCH_STU_PAPER.FK_REJOIN_SECTION_ID is
'答辩时间';

comment on column PR_TCH_STU_PAPER.FK_REJOIN_ROOM_ID is
'答辩地点';

comment on column PR_TCH_STU_PAPER.FLAG_TITLE_ADMISSION is
'选题确认是否通过 0未审核 1通过 2未通过';

comment on column PR_TCH_STU_PAPER.FLAG_SYLLABUS_LAST_UPDATE is
'开题报告最后操作人：0未提交，1学生新提交，2老师新修改。';

comment on column PR_TCH_STU_PAPER.FLAG_DRAFT_A_LAST_UPDATE is
'初稿最后操作人：0未提交，1学生新提交，2老师新修改。';

comment on column PR_TCH_STU_PAPER.FLAG_FINAL_LAST_UPDATE is
'终稿最后操作人：0未提交，1学生新提交，2老师新修改。';

comment on column PR_TCH_STU_PAPER.FLAG_PAPER_REJOIN is
'ENUMCONST 是否被抽取答辩 0未被抽取 1被抽取';

comment on column PR_TCH_STU_PAPER.TITLE_DATE is
'选题日期';

comment on column PR_TCH_STU_PAPER.FINAL_SCORE is
'终稿分数';

comment on column PR_TCH_STU_PAPER.FINAL_SCORE_DATE is
'终稿分数日期';

comment on column PR_TCH_STU_PAPER.REJOIN_SCORE is
'答辩分数';

comment on column PR_TCH_STU_PAPER.SCORE_TOTAL is
'总评成绩 无答辩成绩按终稿成绩 有答辩成绩 论文30%答辩70% ';

/*==============================================================*/
/* Table: PR_VOTE_QUESTION                                      */
/*==============================================================*/
create table PR_VOTE_QUESTION  (
   ID                   VARCHAR2(50)                    not null,
   FK_VOTE_PAPER_ID     VARCHAR2(50),
   FLAG_QUESTION_TYPE   VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   QUESTION_NOTE        VARCHAR2(1000),
   ITEM_NUM             NUMBER(2),
   ITEM1                VARCHAR2(50),
   ITEM_RESULT1         NUMBER(8),
   ITEM2                VARCHAR2(50),
   ITEM_RESULT2         NUMBER(8),
   ITEM3                VARCHAR2(50),
   ITEM_RESULT3         NUMBER(8),
   ITEM4                VARCHAR2(50),
   ITEM_RESULT4         NUMBER(8),
   ITEM5                VARCHAR2(50),
   ITEM_RESULT5         NUMBER(8),
   ITEM6                VARCHAR2(50),
   ITEM_RESULT6         NUMBER(8),
   ITEM7                VARCHAR2(50),
   ITEM_RESULT7         NUMBER(8),
   ITEM8                VARCHAR2(50),
   ITEM_RESULT8         NUMBER(8),
   ITEM9                VARCHAR2(50),
   ITEM_RESULT9         NUMBER(8),
   ITEM10               VARCHAR2(50),
   ITEM_RESULT10        NUMBER(8),
   ITEM11               VARCHAR2(50),
   ITEM_RESULT11        NUMBER(8),
   ITEM12               VARCHAR2(50),
   ITEM_RESULT12        NUMBER(8),
   ITEM13               VARCHAR2(50),
   ITEM_RESULT13        NUMBER(8),
   ITEM14               VARCHAR2(50),
   ITEM_RESULT14        NUMBER(8),
   ITEM15               VARCHAR2(50),
   ITEM_RESULT15        NUMBER(8),
   constraint PK_PR_VOTE_QUESTION primary key (ID)
);

comment on table PR_VOTE_QUESTION is
'投票问卷题目表';

comment on column PR_VOTE_QUESTION.FLAG_QUESTION_TYPE is
'ENUMCONST类型 1单选 2多选';

comment on column PR_VOTE_QUESTION.QUESTION_NOTE is
'内容';

comment on column PR_VOTE_QUESTION.ITEM_NUM is
'选项数量（来自M4，暂不知是否使用）';

comment on column PR_VOTE_QUESTION.ITEM1 is
'选项';

comment on column PR_VOTE_QUESTION.ITEM_RESULT1 is
'票数';

/*==============================================================*/
/* Table: PR_VOTE_RECORD                                        */
/*==============================================================*/
create table PR_VOTE_RECORD  (
   ID                   VARCHAR2(50)                    not null,
   FK_VOTE_PAPER_ID     VARCHAR2(50),
   IP                   VARCHAR2(50),
   USER_SESSION         VARCHAR2(500),
   VOTE_DATE            DATE,
   constraint PK_PR_VOTE_RECORD primary key (ID)
);

comment on table PR_VOTE_RECORD is
'投票记录表（记录IP、SESSION）';

comment on column PR_VOTE_RECORD.IP is
'IP（用于限制同IP只投一次）';

comment on column PR_VOTE_RECORD.USER_SESSION is
'SESSION号（用于限制同SESSION只投一次）';

comment on column PR_VOTE_RECORD.VOTE_DATE is
'投票时间';

/*==============================================================*/
/* Table: PR_VOTE_SUGGEST                                       */
/*==============================================================*/
create table PR_VOTE_SUGGEST  (
   ID                   VARCHAR2(50)                    not null,
   FK_VOTE_PAPER_ID     VARCHAR2(50),
   FLAG_CHECK           VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   IP                   VARCHAR2(50),
   FOUND_DATE           DATE,
   NOTE                 CLOB,
   constraint PK_PR_VOTE_SUGGEST primary key (ID)
);

comment on table PR_VOTE_SUGGEST is
'投票建议表';

comment on column PR_VOTE_SUGGEST.FLAG_CHECK is
'是否通过审核（不通过将不显示在前台）';

comment on column PR_VOTE_SUGGEST.IP is
'建议人IP';

comment on column PR_VOTE_SUGGEST.FOUND_DATE is
'建议时间';

comment on column PR_VOTE_SUGGEST.NOTE is
'建议内容';

/*==============================================================*/
/* Table: SSO_USER                                              */
/*==============================================================*/
create table SSO_USER  (
   ID                   VARCHAR2(50)                    not null,
   LOGIN_ID             VARCHAR2(50)                    not null,
   PASSWORD             VARCHAR2(50)                    not null,
   FK_ROLE_ID           VARCHAR2(50),
   FLAG_ISVALID         VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   LOGIN_NUM            NUMBER(8)                      default 0,
   LAST_LOGIN_DATE      DATE                           default sysdate,
   LAST_LOGIN_IP        VARCHAR2(100),
   constraint PK_SSO_USER primary key (ID),
   constraint AK_KEY_1_SSO_USER unique (LOGIN_ID)
);

comment on column SSO_USER.FLAG_ISVALID is
'ENUMCONST是否有效';

comment on column SSO_USER.LOGIN_NUM is
'登录次数';

comment on column SSO_USER.LAST_LOGIN_DATE is
'最后登录时间';

comment on column SSO_USER.LAST_LOGIN_IP is
'最后登录IP';

/*==============================================================*/
/* Table: SYSTEM_APPLY                                          */
/*==============================================================*/
create table SYSTEM_APPLY  (
   ID                   VARCHAR2(50)                    not null,
   FK_STUDENT_ID        VARCHAR2(50),
   APPLY_TYPE           VARCHAR2(50),
   FLAG_APPLY_STATUS    VARCHAR2(50),
   APPLY_DATE           DATE,
   CHECK_DATE           DATE,
   APPLY_NOTE           VARCHAR2(1000),
   CHECK_NOTE           VARCHAR2(1000),
   APPLY_INFO           CLOB,
   constraint PK_SYSTEM_APPLY primary key (ID)
);

comment on table SYSTEM_APPLY is
'申请表';

comment on column SYSTEM_APPLY.FK_STUDENT_ID is
'学生ID';

comment on column SYSTEM_APPLY.APPLY_TYPE is
'ENUMCONST 申请类型 平台上有几种就加几种';

comment on column SYSTEM_APPLY.FLAG_APPLY_STATUS is
'ENUMCONST 申请是否通过';

comment on column SYSTEM_APPLY.APPLY_DATE is
'申请时间';

comment on column SYSTEM_APPLY.CHECK_DATE is
'审核时间';

comment on column SYSTEM_APPLY.APPLY_INFO is
'申请原因';

/*==============================================================*/
/* Table: SYSTEM_VARIABLES                                      */
/*==============================================================*/
create table SYSTEM_VARIABLES  (
   ID                   VARCHAR2(50)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   VALUE                VARCHAR2(50)                    not null,
   PATTERN              VARCHAR2(500)                   not null,
   FLAG_PLATFORM_SECTION VARCHAR2(50),
   FLAG_BAK             VARCHAR2(50),
   constraint PK_SYSTEM_VARIABLES primary key (ID),
   constraint AK_KEY_2_SYSTEM_V unique (NAME)
);

comment on table SYSTEM_VARIABLES is
'系统变量表';

comment on column SYSTEM_VARIABLES.PATTERN is
'正则';

comment on column SYSTEM_VARIABLES.FLAG_PLATFORM_SECTION is
'ENUMCONST 所属模块（如果分权限则用到）';

/*==============================================================*/
/* Table: WHATYUSER_LOG4J                                       */
/*==============================================================*/
create table WHATYUSER_LOG4J  (
   ID                   VARCHAR2(50)                    not null,
   USERID               VARCHAR2(50),
   OPERATE_TIME         DATE,
   BEHAVIOR             VARCHAR2(500),
   STATUS               VARCHAR2(10),
   NOTES                VARCHAR2(4000),
   LOGTYPE              VARCHAR2(50),
   PRIORITY             VARCHAR2(50),
   IP                   VARCHAR2(50),
   constraint PK_WHATYUSER_LOG4J primary key (ID)
);

comment on table WHATYUSER_LOG4J is
'日志表';

comment on column WHATYUSER_LOG4J.USERID is
'SSO LOGINID';

comment on column WHATYUSER_LOG4J.OPERATE_TIME is
'操作时间';

comment on column WHATYUSER_LOG4J.BEHAVIOR is
'动作';

comment on column WHATYUSER_LOG4J.STATUS is
'状态 有效无效';

comment on column WHATYUSER_LOG4J.NOTES is
'说明';

comment on column WHATYUSER_LOG4J.LOGTYPE is
'登陆人角色';

comment on column WHATYUSER_LOG4J.PRIORITY is
'来自M4';

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

alter table HOMEWORK_INFO
   add constraint FK_HOMEWORK_REFERENCE_PE_TEACH foreign key (CREATER)
      references PE_TEACHER (ID);

alter table HOMEWORK_INFO
   add constraint FK_HOMEWORK_REFERENCE_ENUM_C01 foreign key (FLAG_ISVALID)
      references ENUM_CONST (ID);

alter table HOMEWORK_INFO
   add constraint FK_HOMEWORK_REFERENCE_ENUM_C02 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_BULLETIN
   add constraint FK_BULLETIN_REFERENCE_ENUM_CO1 foreign key (FLAG_ISTOP)
      references ENUM_CONST (ID);

alter table PE_BULLETIN
   add constraint FK_PE_BULLE_FK_BULLET_ENUM_CON foreign key (FLAG_ISVALID)
      references ENUM_CONST (ID);

alter table PE_BULLETIN
   add constraint FK_PE_BULLE_FK_BULLET_PE_MANAG foreign key (FK_MANAGER_ID)
      references PE_MANAGER (ID);

alter table PE_DOCUMENT
   add constraint FK_DOCUMENT_REFERENCE_ENUM_CO1 foreign key (FLAG_DEL)
      references ENUM_CONST (ID);

alter table PE_DOCUMENT
   add constraint FK_DOCUMENT_REFERENCE_ENUM_CO2 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_DOCUMENT
   add constraint FK_PE_DOCUM_FK_DOCUME_SSO_USER foreign key (FK_SSO_ID)
      references SSO_USER (ID);

alter table PE_EXAM_MAINCOURSE_NO
   add constraint FK_PE_EXAM__FK_PE_EXA_PE_SEM24 foreign key (FK_SEMESTER_ID)
      references PE_SEMESTER (ID);

alter table PE_EXAM_MAINCOURSE_ROOM
   add constraint FK_PE_EXAM__FK_PE_EXA_PE_SIT2 foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PE_EXAM_MAINCOURSE_ROOM
   add constraint FK_PE_EXAM__FK_PE_EXA_PR_EXAM_ foreign key (FK_EXAM_OPEN_MAINCOURSE_ID)
      references PR_EXAM_OPEN_MAINCOURSE (ID);

alter table PE_EXAM_NO
   add constraint FK_PE_EXAM__FK_PE_EXA_PE_SEM21 foreign key (FK_SEMESTER_ID)
      references PE_SEMESTER (ID);

alter table PE_EXAM_PATROL
   add constraint FK_PE_EXAM__REFERENCE_ENUM_CO2 foreign key (FLAG_IS_XUNKAO)
      references ENUM_CONST (ID);

alter table PE_EXAM_PATROL
   add constraint FK_PE_EXAM__REFERENCE_ENUM_CO3 foreign key (GENDER)
      references ENUM_CONST (ID);

alter table PE_EXAM_PATROL
   add constraint FK_PE_EXAM__FK_PE_EXA_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PE_EXAM_PATROL
   add constraint FK_PE_EXAM__FK_PE_PAT_ENUM_CON foreign key (FLAG_IS_JIANKAO)
      references ENUM_CONST (ID);

alter table PE_EXAM_ROOM
   add constraint FK_PE_EXAM__FK_PE_EXA_PE_EXAM_ foreign key (FK_EXAM_NO)
      references PE_EXAM_NO (ID);

alter table PE_EXAM_ROOM
   add constraint FK_PE_EXAM__FK_PE_EXA_PE_SIT1 foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PE_FEE_BATCH
   add constraint FK_PE_FEE_B_FK_PE_FEE_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PE_FEE_BATCH
   add constraint FK_PE_FEE_B_REFERENCE_PE_SITEM foreign key (FK_INPUTTER_ID)
      references PE_SITEMANAGER (ID);

alter table PE_FEE_BATCH
   add constraint FK_PE_FEE_B_REFERENCE_ENUM_CO1 foreign key (FLAG_FEE_CHECK)
      references ENUM_CONST (ID);

alter table PE_FEE_BATCH
   add constraint FK_PE_FEE_B_REFERENCE_ENUM_CO2 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_FEE_LEVEL
   add constraint FK_PE_FEE_L_REFERENCE_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_FEE_LEVEL
   add constraint FK_PE_FEE_L_FK_PE_FEE_ENUM_CON foreign key (FLAG_DEFAULT)
      references ENUM_CONST (ID);

alter table PE_INFO_NEWS
   add constraint FK_PE_INFO__FK_INFO_N_PE_INFO_ foreign key (FK_NEWS_TYPE_ID)
      references PE_INFO_NEWS_TYPE (ID);

alter table PE_INFO_NEWS
   add constraint FK_PE_INFO__FK_INFO_N_PE_MANAG foreign key (FK_MANAGER_ID)
      references PE_MANAGER (ID);

alter table PE_INFO_NEWS
   add constraint FK_PE_INFO__REFERENCE_ENUM_CO1 foreign key (FLAG_NEWS_STATUS)
      references ENUM_CONST (ID);

alter table PE_INFO_NEWS
   add constraint FK_PE_INFO__FK_PE_INF_ENUM_CON foreign key (FLAG_ISACTIVE)
      references ENUM_CONST (ID);

alter table PE_MAJOR
   add constraint FK_PE_MAJOR_FK_PE_MAJ_ENUM_CON foreign key (FLAG_IS_EDUCATION)
      references ENUM_CONST (ID);

alter table PE_MAJOR
   add constraint FK_PE_MAJOR_REFERENCE_ENUM_CON foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_MANAGER
   add constraint FK_PE_MANAG_FK_PE_MAN_ENUM_CON foreign key (FLAG_ISVALID)
      references ENUM_CONST (ID);

alter table PE_MANAGER
   add constraint FK_PE_MANAG_REFERENCE_SSO_USER foreign key (FK_SSO_USER_ID)
      references SSO_USER (ID);

alter table PE_MANAGER
   add constraint FK_PE_MANAG_REFERENCE_ENUM_CO2 foreign key (GENDER)
      references ENUM_CONST (ID);

alter table PE_PC_EXERCISE
   add constraint FK_PE_PC_EX_FK_PE_PC__PR_PC_OP foreign key (FK_OPENCOURSE_ID)
      references PR_PC_OPENCOURSE (ID);

alter table PE_PC_NEWS
   add constraint FK_PE_PC_NE_REFERENCE_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_PC_NEWS
   add constraint FK_PE_PC_NE_FK_PE_PC__ENUM_CON foreign key (FLAG_ISSUE)
      references ENUM_CONST (ID);

alter table PE_PC_NEWS
   add constraint FK_PE_PC_NE_FK_PE_PC__SSO_USER foreign key (FK_MANAGER_ID)
      references SSO_USER (ID);

alter table PE_PC_NOTE
   add constraint FK_PE_PC_NO_FK_PE_PC__PE_PC_TE foreign key (FK_TEACHER_ID)
      references PE_PC_TEACHER (ID);

alter table PE_PC_NOTE
   add constraint FK_PE_PC_NO_FK_PE_PC__PR_PC_OP foreign key (FK_OPENCOURSE_ID)
      references PR_PC_OPENCOURSE (ID);

alter table PE_PC_OPENCOURSE_COURSEWARE
   add constraint FK_PE_PC_OP_FK_PE_PC__PR_PC_O1 foreign key (FK_OPENCOURSE_ID)
      references PR_PC_OPENCOURSE (ID);

alter table PE_PC_OPENCOURSE_RESOURCE
   add constraint FK_PE_PC_OP_FK_PE_PC__PR_PC_OP foreign key (FK_OPENCOURSE_ID)
      references PR_PC_OPENCOURSE (ID);

alter table PE_PC_STUDENT
   add constraint FK_PE_PC_ST_FK_PE_PC__SSO_USER foreign key (FK_SSO_USER_ID)
      references SSO_USER (ID);

alter table PE_PC_TEACHER
   add constraint FK_PE_PC_TE_REFERENCE_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_PC_TEACHER
   add constraint FK_PE_PC_TE_FK_PE_PC__ENUM_CON foreign key (FLAG_TEACHER_TYPE)
      references ENUM_CONST (ID);

alter table PE_PC_TEACHER
   add constraint FK_PE_PC_TE_FK_PE_PC__SSO_USER foreign key (FK_SSO_USER_ID)
      references SSO_USER (ID);

alter table PE_PRIORITY
   add constraint FK_PE_PRIOR_REFERENCE_PE_PRI_C foreign key (FK_PRI_CAT_ID)
      references PE_PRI_CATEGORY (ID);

alter table PE_PRI_CATEGORY
   add constraint FK_PE_PRI_C_REFERENCE_PE_PRI_C foreign key (FK_PARENT_ID)
      references PE_PRI_CATEGORY (ID);

alter table PE_PRI_ROLE
   add constraint FK_PE_PRI_R_REFERENCE_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_PRI_ROLE
   add constraint FK_PE_PRI_R_FK_PE_PRI_ENUM_CON foreign key (FLAG_ROLE_TYPE)
      references ENUM_CONST (ID);

alter table PE_RECRUITPLAN
   add constraint FK_PE_RECRU_REFERENCE_PE_GRADE foreign key (FK_GRADE_ID)
      references PE_GRADE (ID);

alter table PE_REC_EXAMCOURSE
   add constraint FK_PE_REC_E_REFERENCE_ENUM_C01 foreign key (FLAG_ARRANGE_ROOM)
      references ENUM_CONST (ID);

alter table PE_REC_EXAMCOURSE
   add constraint FK_PE_REC_E_REFERENCE_ENUM_C02 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_REC_ROOM
   add constraint FK_PE_REC_R_FK_PE_REC_PE_RECRU foreign key (FK_RECRUITPLAN_ID)
      references PE_RECRUITPLAN (ID);

alter table PE_REC_ROOM
   add constraint FK_PE_REC_R_FK_PE_REC_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_FK_PE_REC_ENUM_CO1 foreign key (FLAG_NOEXAM)
      references ENUM_CONST (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_FK_PE_REC_ENUM_CO2 foreign key (FLAG_NOEXAM_STATUS)
      references ENUM_CONST (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_FK_PE_REC_ENUM_CO3 foreign key (FLAG_PUBLISH)
      references ENUM_CONST (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_REFERENCE_ENUM_CO4 foreign key (FLAG_TEACHER_STATUS)
      references ENUM_CONST (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_REFERENCE_ENUM_CO5 foreign key (FLAG_TEACHER)
      references ENUM_CONST (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_REFERENCE_ENUM_CO6 foreign key (FLAG_MATRICULATE)
      references ENUM_CONST (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_REFERENCE_ENUM_CO7 foreign key (FLAG_REC_CHANNEL)
      references ENUM_CONST (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_REFERENCE_ENUM_CO8 foreign key (FLAG_XUELI_CHECK)
      references ENUM_CONST (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_REFERENCE_ENUM_CO9 foreign key (FLAG_MAJOR_TYPE)
      references ENUM_CONST (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_FK_PE_REC_ENUM_CON foreign key (FLAG_REC_STATUS)
      references ENUM_CONST (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_FK_PE_REC_PE_REC_R foreign key (FK_REC_ROOM)
      references PE_REC_ROOM (ID);

alter table PE_REC_STUDENT
   add constraint FK_PE_REC_S_REFERENCE_PR_REC_P foreign key (FK_REC_MAJOR_SITE_ID)
      references PR_REC_PLAN_MAJOR_SITE (ID);

alter table PE_SITE
   add constraint FK_PE_SITE_FK_PE_SIT_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_SITE
   add constraint FK_PE_SITE_FK_PE_SIT_ENUM_CON foreign key (FLAG_ACTIVE)
      references ENUM_CONST (ID);

alter table PE_SITE
   add constraint FK_PE_SITE_FK_PE_SIT_PE_SITE foreign key (FK_EXAM_SITE_ID)
      references PE_SITE (ID);

alter table PE_SITEMANAGER
   add constraint FK_PE_SITEM_REFERENCE_ENUM_CO2 foreign key (GENDER)
      references ENUM_CONST (ID);

alter table PE_SITEMANAGER
   add constraint FK_PE_SITEM_FK_PE_SIT_ENUM_CON foreign key (FLAG_ISVALID)
      references ENUM_CONST (ID);

alter table PE_SITEMANAGER
   add constraint FK_PE_SITEM_REFERENCE_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PE_SITEMANAGER
   add constraint FK_PE_SITEM_REFERENCE_SSO_USER foreign key (FK_SSO_USER_ID)
      references SSO_USER (ID);

alter table PE_SMS_INFO
   add constraint FK_SMS_INFO_REFERENCE_ENUM_CO1 foreign key (FLAG_SMS_STATUS)
      references ENUM_CONST (ID);

alter table PE_SMS_INFO
   add constraint FK_PE_SMS_I_FK_SMS_IN_ENUM_CON foreign key (FLAG_SMS_TYPE)
      references ENUM_CONST (ID);

alter table PE_SMS_INFO
   add constraint FK_PE_SMS_I_FK_SMS_IN_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PE_SMS_SYSTEMPOINT
   add constraint FK_SMS_SYST_REFERENCE_ENUM_CO1 foreign key (FLAG_ISAUTO)
      references ENUM_CONST (ID);

alter table PE_SMS_SYSTEMPOINT
   add constraint FK_PE_SMS_S_FK_SMS_SY_ENUM_CON foreign key (FLAG_SMS_STATUS)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_C10 foreign key (FLAG_MAJOR_TYPE)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_C17 foreign key (SCORE_UNITE_ENGLISH_C)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_C18 foreign key (SCORE_UNITE_SHUXUE)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_C19 foreign key (SCORE_UNITE_YUWEN)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_CO1 foreign key (FLAG_ADVANCED)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_CO2 foreign key (FLAG_STUDENT_STATUS)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_CO3 foreign key (SCORE_UNITE_COMPUTER)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_CO4 foreign key (SCORE_UNITE_ENGLISH_A)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_CO5 foreign key (FLAG_DISOBEY)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_CO6 foreign key (SCORE_UNITE_ENGLISH_B)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_ENUM_CO9 foreign key (DEGREE_ENGLISH_TYPE)
      references ENUM_CONST (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_PE_EDUTY foreign key (FK_EDUTYPE_ID)
      references PE_EDUTYPE (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_PE_FEE_L foreign key (FK_FEE_LEVEL_ID)
      references PE_FEE_LEVEL (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_PE_GRADE foreign key (FK_GRADE_ID)
      references PE_GRADE (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_PE_MAJOR foreign key (FK_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_FK_PE_STU_PR_STUDE foreign key (FK_STUDENT_INFO_ID)
      references PR_STUDENT_INFO (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_SSO_USER foreign key (FK_SSO_USER_ID)
      references SSO_USER (ID);

alter table PE_STUDENT
   add constraint FK_PE_STUDE_REFERENCE_PE_REC_S foreign key (FK_REC_STUDENT_ID)
      references PE_REC_STUDENT (ID);

alter table PE_TCH_BOOK
   add constraint FK_PE_TCH_B_REFERENCE_ENUM_CO1 foreign key (FLAG_ISVALID)
      references ENUM_CONST (ID);

alter table PE_TCH_BOOK
   add constraint FK_PE_TCH_B_REFERENCE_ENUM_CO2 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_TCH_COURSE
   add constraint FK_PE_TCH_C_REFERENCE_ENUM_C11 foreign key (FLAG_ISVALID)
      references ENUM_CONST (ID);

alter table PE_TCH_COURSE
   add constraint FK_PE_TCH_C_FK_PE_TCH_ENUM_CON foreign key (FLAG_ISPUBLICCOURSE)
      references ENUM_CONST (ID);

alter table PE_TCH_COURSE
   add constraint FK_PE_TCH_C_FK_PE_TCH_PE_EXAMA foreign key (FK_EXAM_SCORE_INPUT_USERA_ID)
      references PE_EXAM_SCORE_INPUT_USER (ID);

alter table PE_TCH_COURSE
   add constraint FK_PE_TCH_C_FK_PE_TCH_PE_EXAMB foreign key (FK_EXAM_SCORE_INPUT_USERB_ID)
      references PE_EXAM_SCORE_INPUT_USER (ID);

alter table PE_TCH_COURSEWARE
   add constraint FK_PE_TCH_C_REFERENCE_ENUM_C12 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PE_TCH_COURSEWARE
   add constraint FK_PE_TCH_C_REFERENCE_PE_TCH_3 foreign key (FK_COURSE_ID)
      references PE_TCH_COURSE (ID);

alter table PE_TCH_COURSEWARE
   add constraint FK_PE_TCH_C_REFERENCE_ENUM_CO2 foreign key (FLAG_ISVALID)
      references ENUM_CONST (ID);

alter table PE_TCH_PROGRAM
   add constraint FK_PE_TCH_P_FK_PE_TCH_ENUM_CON foreign key (FLAG_DEGREE_CANDISOBEY)
      references ENUM_CONST (ID);

alter table PE_TCH_PROGRAM
   add constraint FK_PE_TCH_P_REFERENCE_ENUM_CO1 foreign key (FLAG_MAJOR_TYPE)
      references ENUM_CONST (ID);

alter table PE_TCH_PROGRAM
   add constraint FK_PE_TCH_P_REFERENCE_ENUM_CO6 foreign key (FLAG_UNITE_A)
      references ENUM_CONST (ID);

alter table PE_TCH_PROGRAM
   add constraint FK_PE_TCH_P_REFERENCE_ENUM_CO7 foreign key (FLAG_UNITE_B)
      references ENUM_CONST (ID);

alter table PE_TCH_PROGRAM
   add constraint FK_PE_TCH_P_REFERENCE_PE_EDUTY foreign key (FK_EDUTYPE_ID)
      references PE_EDUTYPE (ID);

alter table PE_TCH_PROGRAM
   add constraint FK_PE_TCH_P_FK_PE_TCH_PE_GRADE foreign key (FK_GRADE_ID)
      references PE_GRADE (ID);

alter table PE_TCH_PROGRAM
   add constraint FK_PE_TCH_P_REFERENCE_PE_MAJOR foreign key (FK_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PE_TCH_PROGRAM_GROUP
   add constraint FK_PE_TCH_P_REFERENCE_PE_TCH_8 foreign key (FK_PROGRAM_ID)
      references PE_TCH_PROGRAM (ID);

alter table PE_TCH_PROGRAM_GROUP
   add constraint FK_PE_TCH_P_REFERENCE_PE_TCH_9 foreign key (FK_COURSEGROUP_ID)
      references PE_TCH_COURSEGROUP (ID);

alter table PE_TCH_REJOIN_ROOM
   add constraint FK_PE_TCH_R_FK_PE_REJ_PE_SEMES foreign key (FK_SEMESTER_ID)
      references PE_SEMESTER (ID);

alter table PE_TCH_REJOIN_SECTION
   add constraint FK_PE_REJOI_FK_PE_REJ_PE_SEME1 foreign key (FK_SEMESTER_ID)
      references PE_SEMESTER (ID);

alter table PE_TEACHER
   add constraint FK_PE_TEACH_REFERENCE_ENUM_CO2 foreign key (FLAG_MAX_XUELI)
      references ENUM_CONST (ID);

alter table PE_TEACHER
   add constraint FK_PE_TEACH_REFERENCE_ENUM_CO3 foreign key (GENDER)
      references ENUM_CONST (ID);

alter table PE_TEACHER
   add constraint FK_PE_TEACH_REFERENCE_ENUM_CO4 foreign key (FLAG_ZHICHENG)
      references ENUM_CONST (ID);

alter table PE_TEACHER
   add constraint FK_PE_TEACH_REFERENCE_ENUM_CO5 foreign key (FLAG_MAX_XUEWEI)
      references ENUM_CONST (ID);

alter table PE_TEACHER
   add constraint FK_PE_TEACH_FK_PE_TEA_PE_MAJOR foreign key (FK_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PE_TEACHER
   add constraint FK_PE_TEACH_REFERENCE_SSO_USER foreign key (FK_SSO_USER_ID)
      references SSO_USER (ID);

alter table PE_TEACHER
   add constraint FK_PE_TEACH_REFERENCE_ENUM_001 foreign key (FLAG_ACTIVE)
      references ENUM_CONST (ID);

alter table PE_TEACHER
   add constraint FK_PE_TEACH_REFERENCE_ENUM_CON foreign key (FLAG_PAPER)
      references ENUM_CONST (ID);

alter table PE_VOTE_PAPER
   add constraint FK_PE_VOTE__REFERENCE_ENUM_CO1 foreign key (FLAG_ISVALID)
      references ENUM_CONST (ID);

alter table PE_VOTE_PAPER
   add constraint FK_PE_VOTE__REFERENCE_ENUM_CO2 foreign key (FLAG_LIMIT_DIFFIP)
      references ENUM_CONST (ID);

alter table PE_VOTE_PAPER
   add constraint FK_PE_VOTE__REFERENCE_ENUM_CO3 foreign key (FLAG_LIMIT_DIFFSESSION)
      references ENUM_CONST (ID);

alter table PE_VOTE_PAPER
   add constraint FK_PE_VOTE__REFERENCE_ENUM_CO4 foreign key (FLAG_TYPE)
      references ENUM_CONST (ID);

alter table PE_VOTE_PAPER
   add constraint FK_PE_VOTE__REFERENCE_ENUM_CO5 foreign key (FLAG_VIEW_SUGGEST)
      references ENUM_CONST (ID);

alter table PE_VOTE_PAPER
   add constraint FK_PE_VOTE__FK_PE_VOT_ENUM_CON foreign key (FLAG_CAN_SUGGEST)
      references ENUM_CONST (ID);

alter table PR_COURSE_ARRANGE
   add constraint FK_PR_COURS_REFERENCE_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PR_COURSE_ARRANGE
   add constraint FK_PR_COURS_FK_PR_COU_ENUM_CON foreign key (FLAG_COURSE_SECTION)
      references ENUM_CONST (ID);

alter table PR_COURSE_ARRANGE
   add constraint FK_PR_COURS_FK_PR_COU_PR_TCH_O foreign key (FK_OPENCOURSE_ID)
      references PR_TCH_OPENCOURSE (ID);

alter table PR_DOCUMENT
   add constraint FK_PR_DOCUM_REFERENCE_ENUM_CO1 foreign key (FLAG_READ)
      references ENUM_CONST (ID);

alter table PR_DOCUMENT
   add constraint FK_PR_DOCUM_FK_PR_DOC_ENUM_CON foreign key (FLAG_DEL)
      references ENUM_CONST (ID);

alter table PR_DOCUMENT
   add constraint FK_PR_DOCUM_FK_PR_DOC_PE_DOCUM foreign key (FK_DOCUMENT_ID)
      references PE_DOCUMENT (ID);

alter table PR_DOCUMENT
   add constraint FK_PR_DOCUM_FK_PR_DOC_SSO_USER foreign key (FK_SSO_ID)
      references SSO_USER (ID);

alter table PR_EDU_MAJOR_SITE_FEE_LEVEL
   add constraint FK_PR_EDU_M_FK_PR_EDU_PE_EDUTY foreign key (FK_EDUTYPE_ID)
      references PE_EDUTYPE (ID);

alter table PR_EDU_MAJOR_SITE_FEE_LEVEL
   add constraint FK_PR_EDU_M_FK_PR_EDU_PE_FEE_L foreign key (FK_FEE_LEVEL_ID)
      references PE_FEE_LEVEL (ID);

alter table PR_EDU_MAJOR_SITE_FEE_LEVEL
   add constraint FK_PR_EDU_M_FK_PR_EDU_PE_MAJOR foreign key (FK_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PR_EDU_MAJOR_SITE_FEE_LEVEL
   add constraint FK_PR_EDU_M_FK_PR_EDU_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PR_EXAM_BOOKING
   add constraint FK_PR_EXAM__REFERENCE_ENUM_C02 foreign key (FLAG_SCORE_STATUS)
      references ENUM_CONST (ID);

alter table PR_EXAM_BOOKING
   add constraint FK_PR_EXAM__REFERENCE_ENUM_C03 foreign key (FLAG_SCORE_STATUSA)
      references ENUM_CONST (ID);

alter table PR_EXAM_BOOKING
   add constraint FK_PR_EXAM__REFERENCE_ENUM_CO4 foreign key (FLAG_SCORE_STATUSB)
      references ENUM_CONST (ID);

alter table PR_EXAM_BOOKING
   add constraint FK_PR_EXAM__FK_PR_EXA_PE_EXA19 foreign key (FK_EXAM_NO_ID)
      references PE_EXAM_NO (ID);

alter table PR_EXAM_BOOKING
   add constraint FK_PR_EXAM__FK_PR_EXA_PE_EXA18 foreign key (FK_EXAM_ROOM_ID)
      references PE_EXAM_ROOM (ID);

alter table PR_EXAM_BOOKING
   add constraint FK_PR_EXAM__FK_PR_EXA_PE_SEM20 foreign key (FK_SEMESTER_ID)
      references PE_SEMESTER (ID);

alter table PR_EXAM_BOOKING
   add constraint FK_PR_EXAM__FK_PR_EXA_PR_TCH_S foreign key (FK_TCH_STU_ELECTIVE_ID)
      references PR_TCH_STU_ELECTIVE (ID);

alter table PR_EXAM_OPEN_MAINCOURSE
   add constraint FK_PR_EXAM__FK_PR_EXA_PE_EXA25 foreign key (FK_EXAM_MAINCOURSE_NO_ID)
      references PE_EXAM_MAINCOURSE_NO (ID);

alter table PR_EXAM_OPEN_MAINCOURSE
   add constraint FK_PR_EXAM__FK_PR_EXA_PE_TCH_C foreign key (FK_COURSE_ID)
      references PE_TCH_COURSE (ID);

alter table PR_EXAM_PATROL_SETTING
   add constraint FK_PR_EXAM__FK_PR_EXA_PE_EXA22 foreign key (FK_EXAM_PATROL_ID)
      references PE_EXAM_PATROL (ID);

alter table PR_EXAM_PATROL_SETTING
   add constraint FK_PR_EXAM__FK_PR_EXA_PE_SEM23 foreign key (FK_SEMESTER_ID)
      references PE_SEMESTER (ID);

alter table PR_EXAM_PATROL_SETTING
   add constraint FK_PR_EXAM__FK_PR_EXA_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PR_EXAM_STU_MAINCOURSE
   add constraint FK_PR_EXAM__FK_PR_EXA_PE_STUDE foreign key (FK_STUDENT_ID)
      references PE_STUDENT (ID);

alter table PR_EXAM_STU_MAINCOURSE
   add constraint FK_PR_EXAM__FK_PR_EXA_PR_EXAM_ foreign key (FK_EXAM_OPEN_MAINCOURSE_ID)
      references PR_EXAM_OPEN_MAINCOURSE (ID);

alter table PR_EXAM_STU_MAINCOURSE
   add constraint FK_PR_EXAM__REFERENCE_PE_EXAM_ foreign key (FK_EXAM_MAINCOURSE_ROOM_ID)
      references PE_EXAM_MAINCOURSE_ROOM (ID);

alter table PR_EXAM_STU_MAINCOURSE
   add constraint FK_PR_EXAM__REFERENCE_ENUM_C05 foreign key (FLAG_SCORE_STATUS)
      references ENUM_CONST (ID);

alter table PR_EXAM_STU_MAINCOURSE
   add constraint FK_PR_EXAM__REFERENCE_ENUM_C06 foreign key (FLAG_SCORE_PUB)
      references ENUM_CONST (ID);

alter table PR_FEE_DETAIL
   add constraint FK_PR_FEE_D_REFERENCE_PE_FEE_B foreign key (FK_FEE_BATCH_ID)
      references PE_FEE_BATCH (ID);

alter table PR_FEE_DETAIL
   add constraint FK_PR_FEE_D_REFERENCE_PE_STUDE foreign key (FK_STU_ID)
      references PE_STUDENT (ID);

alter table PR_FEE_DETAIL
   add constraint FK_PR_FEE_D_REFERENCE_ENUM_CO3 foreign key (FLAG_FEE_TYPE)
      references ENUM_CONST (ID);

alter table PR_FEE_DETAIL
   add constraint FK_PR_FEE_D_REFERENCE_ENUM_CO4 foreign key (FLAG_FEE_CHECK)
      references ENUM_CONST (ID);

alter table PR_PC_BOOKING_SEAT
   add constraint FK_PR_PC_BO_FK_PR_PC__PR_PC_OP foreign key (FK_OPENCOURSE_ID)
      references PR_PC_OPENCOURSE (ID);

alter table PR_PC_ELECTIVE
   add constraint FK_PR_PC_EL_REFERENCE_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PR_PC_ELECTIVE
   add constraint FK_PR_PC_EL_FK_PR_PC__ENUM_CON foreign key (FLAG_ENROL)
      references ENUM_CONST (ID);

alter table PR_PC_ELECTIVE
   add constraint FK_PR_PC_EL_FK_PR_PC__PE_PC_ST foreign key (FK_STU_ID)
      references PE_PC_STUDENT (ID);

alter table PR_PC_ELECTIVE
   add constraint FK_PR_PC_EL_FK_PR_PC__PR_PC_OP foreign key (FK_OPENCOURSE_ID)
      references PR_PC_OPENCOURSE (ID);

alter table PR_PC_NOTE_REPLY
   add constraint FK_PR_PC_NO_FK_PE_PC__PE_PC_NO foreign key (FK_NOTE_ID)
      references PE_PC_NOTE (ID);

alter table PR_PC_NOTE_REPLY
   add constraint FK_PR_PC_NO_FK_PE_PC__PR_PC_EL foreign key (FK_ELECTIVE_ID)
      references PR_PC_ELECTIVE (ID);

alter table PR_PC_OPENCOURSE
   add constraint FK_PR_PC_OP_FK_PE_PC__PE_PC_CO foreign key (FK_COURSE_ID)
      references PE_PC_COURSE (ID);

alter table PR_PC_OPENCOURSE
   add constraint FK_PR_PC_OP_FK_PE_PC__PE_SEMES foreign key (FK_SEMESTER_ID)
      references PE_SEMESTER (ID);

alter table PR_PC_OPENCOURSE_TEACHER
   add constraint FK_PR_PC_OP_FK_PR_PC__PE_PC_TE foreign key (FK_TEACHER_ID)
      references PE_PC_TEACHER (ID);

alter table PR_PC_OPENCOURSE_TEACHER
   add constraint FK_PR_PC_OP_FK_PR_PC__PR_PC_OP foreign key (FK_OPENCOURSE_ID)
      references PR_PC_OPENCOURSE (ID);

alter table PR_PC_STU_BOOKING
   add constraint FK_PR_PC_ST_FK_PR_PC__PR_PC_BO foreign key (FK_BOOKING_SEAT_ID)
      references PR_PC_BOOKING_SEAT (ID);

alter table PR_PC_STU_BOOKING
   add constraint FK_PR_PC_ST_FK_PR_PC__PR_PC_E1 foreign key (FK_ELECTIVE_ID)
      references PR_PC_ELECTIVE (ID);

alter table PR_PC_STU_EXERCISE
   add constraint FK_PR_PC_ST_FK_PR_PC__PE_PC_EX foreign key (FK_EXERCISE_ID)
      references PE_PC_EXERCISE (ID);

alter table PR_PC_STU_EXERCISE
   add constraint FK_PR_PC_ST_FK_PR_PC__PR_PC_EL foreign key (FK_ELECTIVE_ID)
      references PR_PC_ELECTIVE (ID);

alter table PR_PRI_MANAGER_EDUTYPE
   add constraint FK_PR_PRI_M_REFERENCE_SSO_U101 foreign key (FK_SSO_USER_ID)
      references SSO_USER (ID);

alter table PR_PRI_MANAGER_EDUTYPE
   add constraint FK_PR_PRI_M_REFERENCE_PE_EDUTY foreign key (FK_EDUTYPE_ID)
      references PE_EDUTYPE (ID);

alter table PR_PRI_MANAGER_GRADE
   add constraint FK_PR_PRI_M_REFERENCE_PE_GRADE foreign key (FK_GRADE_ID)
      references PE_GRADE (ID);

alter table PR_PRI_MANAGER_GRADE
   add constraint FK_PR_PRI_M_REFERENCE_SSO_US99 foreign key (FK_SSO_USER_ID)
      references SSO_USER (ID);

alter table PR_PRI_MANAGER_MAJOR
   add constraint FK_PR_PRI_M_REFERENCE_SSO_U100 foreign key (FK_SSO_USER_ID)
      references SSO_USER (ID);

alter table PR_PRI_MANAGER_MAJOR
   add constraint FK_PR_PRI_M_REFERENCE_PE_MAJOR foreign key (FK_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PR_PRI_MANAGER_SITE
   add constraint FK_PR_PRI_M_REFERENCE_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PR_PRI_MANAGER_SITE
   add constraint FK_PR_PRI_M_REFERENCE_SSO_US98 foreign key (FK_SSO_USER_ID)
      references SSO_USER (ID);

alter table PR_PRI_ROLE
   add constraint FK_PR_PRI_R_REFERENCE_PE_PRIOR foreign key (FK_PRIORITY_ID)
      references PE_PRIORITY (ID);

alter table PR_PRI_ROLE
   add constraint FK_PR_PRI_R_REFERENCE_PE_PRI_R foreign key (FK_ROLE_ID)
      references PE_PRI_ROLE (ID);

alter table PR_REC_COURSE_MAJOR_EDUTYPE
   add constraint FK_PR_REC_C_REFERENCE_PE_MAJOR foreign key (FK_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PR_REC_COURSE_MAJOR_EDUTYPE
   add constraint FK_PR_REC_C_REFERENCE_PE_EDUTY foreign key (FK_EDUTYPE_ID)
      references PE_EDUTYPE (ID);

alter table PR_REC_COURSE_MAJOR_EDUTYPE
   add constraint FK_PR_REC_C_REFERENCE_PE_REC_E foreign key (FK_REC_EXAMCOURSE_ID)
      references PE_REC_EXAMCOURSE (ID);

alter table PR_REC_EXAM_COURSE_TIME
   add constraint FK_PR_REC_E_FK_PR_REC_PE_RECRU foreign key (FK_RECRUITPLAN_ID)
      references PE_RECRUITPLAN (ID);

alter table PR_REC_EXAM_COURSE_TIME
   add constraint FK_PR_REC_E_REFERENCE_PE_REC_4 foreign key (FK_REC_EXAMCOURSE_ID)
      references PE_REC_EXAMCOURSE (ID);

alter table PR_REC_EXAM_STU_COURSE
   add constraint FK_PR_REC_E_REFERENCE_PE_REC_7 foreign key (FK_REC_EXAM_COURSE_TIME_ID)
      references PR_REC_EXAM_COURSE_TIME (ID);

alter table PR_REC_EXAM_STU_COURSE
   add constraint FK_PR_REC_E_FK_PR_REC_PE_REC_S foreign key (FK_REC_STUDENT_ID)
      references PE_REC_STUDENT (ID);

alter table PR_REC_PATROL_SETTING
   add constraint FK_PR_REC_P_FK_PR_REC_PE_EXAM_ foreign key (FK_EXAM_PATROL_ID)
      references PE_EXAM_PATROL (ID);

alter table PR_REC_PATROL_SETTING
   add constraint FK_PR_REC_P_FK_PR_REC_PE_RECRU foreign key (FK_RECRUITPLAN_ID)
      references PE_RECRUITPLAN (ID);

alter table PR_REC_PATROL_SETTING
   add constraint FK_PR_REC_P_FK_PR_REC_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PR_REC_PLAN_MAJOR_EDUTYPE
   add constraint FK_PR_REC_P_REFERENCE_PE_EDUTY foreign key (FK_EDUTYPE_ID)
      references PE_EDUTYPE (ID);

alter table PR_REC_PLAN_MAJOR_EDUTYPE
   add constraint FK_PR_REC_P_REFERENCE_PE_MAJOR foreign key (FK_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PR_REC_PLAN_MAJOR_EDUTYPE
   add constraint FK_PR_REC_P_REFERENCE_PE_RECRU foreign key (FK_RECRUITPLAN_ID)
      references PE_RECRUITPLAN (ID);

alter table PR_REC_PLAN_MAJOR_SITE
   add constraint FK_PR_REC_P_REFERENCE_PE_SITE foreign key (FK_SITE_ID)
      references PE_SITE (ID);

alter table PR_REC_PLAN_MAJOR_SITE
   add constraint FK_PR_REC_P_REFERENCE_PR_REC_P foreign key (FK_REC_PLAN_MAJOR_EDUTYPE_ID)
      references PR_REC_PLAN_MAJOR_EDUTYPE (ID);

alter table PR_SMS_SEND_STATUS
   add constraint FK_PR_SMS_S_FK_SMS_NU_PE_SMS_I foreign key (FK_SMS_INFO_ID)
      references PE_SMS_INFO (ID);

alter table PR_STUDENT_HISTORY
   add constraint FK_PR_STUDE_FK_PR_STU_PE_STUDE foreign key (FK_STUDENT_ID)
      references PE_STUDENT (ID);

alter table PR_STU_CHANGEABLE_MAJOR
   add constraint FK_PR_STU_C_REFERENCE_PE_MAJO2 foreign key (FK_OLD_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PR_STU_CHANGEABLE_MAJOR
   add constraint FK_PR_STU_C_REFERENCE_PE_MAJO3 foreign key (FK_NEW_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PR_STU_CHANGE_EDUTYPE
   add constraint FK_PR_STU_C_REFERENCE_PE_EDUT1 foreign key (FK_NEW_EDUTYPE_ID)
      references PE_EDUTYPE (ID);

alter table PR_STU_CHANGE_EDUTYPE
   add constraint FK_PR_STU_C_FK_PR_STU_PE_EDUTY foreign key (FK_OLD_EDUTYPE_ID)
      references PE_EDUTYPE (ID);

alter table PR_STU_CHANGE_EDUTYPE
   add constraint FK_PR_STU_C_FK_PR_STU_PE_STU17 foreign key (FK_STU_ID)
      references PE_STUDENT (ID);

alter table PR_STU_CHANGE_MAJOR
   add constraint FK_PR_STU_C_REFERENCE_PE_MAJO1 foreign key (FK_NEW_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PR_STU_CHANGE_MAJOR
   add constraint FK_PR_STU_C_FK_PR_STU_PE_MAJOR foreign key (FK_OLD_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PR_STU_CHANGE_MAJOR
   add constraint FK_PR_STU_C_FK_PR_STU_PE_STU16 foreign key (FK_STU_ID)
      references PE_STUDENT (ID);

alter table PR_STU_CHANGE_SCHOOL
   add constraint FK_PA_STU_S_REFERENCE_PE_STUDE foreign key (FK_STU_ID)
      references PE_STUDENT (ID);

alter table PR_STU_CHANGE_SCHOOL
   add constraint FK_PR_STU_C_REFERENCE_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PR_STU_CHANGE_SCHOOL
   add constraint FK_PR_STU_C_FK_PR_STU_ENUM_CON foreign key (FLAG_ABORTSCHOOL_TYPE)
      references ENUM_CONST (ID);

alter table PR_STU_CHANGE_SITE
   add constraint FK_PR_STU_C_REFERENCE_PE_SIT1 foreign key (FK_NEW_SITE_ID)
      references PE_SITE (ID);

alter table PR_STU_CHANGE_SITE
   add constraint FK_PR_STU_C_FK_PR_STU_PE_SITE foreign key (FK_OLD_SITE_ID)
      references PE_SITE (ID);

alter table PR_STU_CHANGE_SITE
   add constraint FK_PR_STU_C_FK_PR_STU_PE_STU15 foreign key (FK_STU_ID)
      references PE_STUDENT (ID);

alter table PR_STU_MULTI_MAJOR
   add constraint FK_PR_STU_M_FK_PR_STU_PE_MAJOR foreign key (FK_MAJOR_ID)
      references PE_MAJOR (ID);

alter table PR_TCH_COURSE_BOOK
   add constraint FK_PR_TCH_C_FK_PR_TCH_PE_TCH_B foreign key (FK_BOOK_ID)
      references PE_TCH_BOOK (ID);

alter table PR_TCH_COURSE_BOOK
   add constraint FK_PR_TCH_C_FK_PR_TCH_PE_TCH_C foreign key (FK_COURSE_ID)
      references PE_TCH_COURSE (ID);

alter table PR_TCH_COURSE_TEACHER
   add constraint FK_PR_TCH_C_REFERENCE_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PR_TCH_COURSE_TEACHER
   add constraint FK_PR_TCH_C_FK_PR_TCH_ENUM_CON foreign key (FLAG_TEACHER_TYPE)
      references ENUM_CONST (ID);

alter table PR_TCH_COURSE_TEACHER
   add constraint FK_PR_TCH_C_REFERENCE_PE_TCH_C foreign key (FK_COURSE_ID)
      references PE_TCH_COURSE (ID);

alter table PR_TCH_COURSE_TEACHER
   add constraint FK_PR_TCH_C_REFERENCE_PE_TEACH foreign key (FK_TEACHER_ID)
      references PE_TEACHER (ID);

alter table PR_TCH_ELECTIVE_BOOK
   add constraint FK_PR_TCH_E_REFERENCE_PR_TCH_S foreign key (FK_ELECTIVE_ID)
      references PR_TCH_STU_ELECTIVE (ID);

alter table PR_TCH_ELECTIVE_BOOK
   add constraint FK_PR_TCH_E_REFERENCE_PR_TCH_O foreign key (FK_OPENCOURSE_BOOK_ID)
      references PR_TCH_OPENCOURSE_BOOK (ID);

alter table PR_TCH_OPENCOURSE
   add constraint FK_PR_TCH_O_REFERENCE_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PR_TCH_OPENCOURSE
   add constraint FK_PR_TCH_O_FK_PR_TCH_ENUM_CON foreign key (FLAG_EXAM_COURSE)
      references ENUM_CONST (ID);

alter table PR_TCH_OPENCOURSE
   add constraint FK_PR_TCH_O_FK_PR_TCH_PE_SEMES foreign key (FK_SEMESTER_ID)
      references PE_SEMESTER (ID);

alter table PR_TCH_OPENCOURSE
   add constraint FK_PR_TCH_O_REFERENCE_PE_TCH_1 foreign key (FK_COURSE_ID)
      references PE_TCH_COURSE (ID);

alter table PR_TCH_OPENCOURSE_BOOK
   add constraint FK_PR_TCH_O_REFERENCE_PE_TCH_B foreign key (FK_BOOK_ID)
      references PE_TCH_BOOK (ID);

alter table PR_TCH_OPENCOURSE_BOOK
   add constraint FK_PR_TCH_O_REFERENCE_PR_TCH_3 foreign key (FK_OPENCOURSE_ID)
      references PR_TCH_OPENCOURSE (ID);

alter table PR_TCH_OPENCOURSE_COURSEWARE
   add constraint FK_PR_TCH_O_REFERENCE_PE_TCH_6 foreign key (FK_COURSEWARE_ID)
      references PE_TCH_COURSEWARE (ID);

alter table PR_TCH_OPENCOURSE_COURSEWARE
   add constraint FK_PR_TCH_O_REFERENCE_PR_TCH_6 foreign key (FK_OPENCOURSE_ID)
      references PR_TCH_OPENCOURSE (ID);

alter table PR_TCH_OPENCOURSE_TEACHER
   add constraint FK_PR_TCH_O_REFERENCE_PR_TCH_2 foreign key (FK_OPENCOURSE_ID)
      references PR_TCH_OPENCOURSE (ID);

alter table PR_TCH_OPENCOURSE_TEACHER
   add constraint FK_PR_TCH_O_REFERENCE_PE_TEAC5 foreign key (FK_TEACHER_ID)
      references PE_TEACHER (ID);

alter table PR_TCH_PAPER_CONTENT
   add constraint FK_PR_TCH_P_REFERENCE_ENUM_CO1 foreign key (FLAG_PAPER_SECTION)
      references ENUM_CONST (ID);

alter table PR_TCH_PAPER_CONTENT
   add constraint FK_PR_TCH_P_FK_PR_TCH_PR_TCH_S foreign key (FK_TCH_STU_PAPER)
      references PR_TCH_STU_PAPER (ID);

alter table PR_TCH_PAPER_CONTENT
   add constraint FK_PR_TCH_P_FK_PR_TCH_ENUM_CON foreign key (FLAG_ACTION_USER)
      references ENUM_CONST (ID);

alter table PR_TCH_PAPER_TITLE
   add constraint FK_PR_TCH_P_REFERENCE_PE_SEMES foreign key (FK_SEMESTER)
      references PE_SEMESTER (ID);

alter table PR_TCH_PAPER_TITLE
   add constraint FK_PR_TCH_P_REFERENCE_PE_TEACH foreign key (FK_TEACHER)
      references PE_TEACHER (ID);

alter table PR_TCH_PROGRAM_COURSE
   add constraint FK_PE_TCH_P_REFERENCE_PE_TCH_4 foreign key (FK_COURSE_ID)
      references PE_TCH_COURSE (ID);

alter table PR_TCH_PROGRAM_COURSE
   add constraint FK_PE_TCH_P_REFERENCE_PE_TCH_5 foreign key (FK_PROGRAMGROUP_ID)
      references PE_TCH_PROGRAM_GROUP (ID);

alter table PR_TCH_PROGRAM_COURSE
   add constraint FK_PR_TCH_P_REFERENCE_ENUM_CO2 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PR_TCH_PROGRAM_COURSE
   add constraint FK_PR_TCH_P_REFERENCE_ENUM_CO3 foreign key (FLAG_IS_MAIN_COURSE)
      references ENUM_CONST (ID);

alter table PR_TCH_STU_ELECTIVE
   add constraint FK_PR_TCH_S_FK_PA_LRN_ENUM_CON foreign key (FLAG_SCORE_PUB)
      references ENUM_CONST (ID);

alter table PR_TCH_STU_ELECTIVE
   add constraint FK_PR_TCH_S_FK_PA_LRN_ENUM_CO1 foreign key (FLAG_SCORE_STATUS)
      references ENUM_CONST (ID);

alter table PR_TCH_STU_ELECTIVE
   add constraint FK_PA_LRN_S_REFERENCE_PE_STUDE foreign key (FK_STU_ID)
      references PE_STUDENT (ID);

alter table PR_TCH_STU_ELECTIVE
   add constraint FK_PR_TCH_S_FK_PA_LRN_SSO_USER foreign key (FK_OPERATOR_ID)
      references SSO_USER (ID);

alter table PR_TCH_STU_ELECTIVE
   add constraint FK_PR_TCH_S_FK_PR_TCH_ENUM_C13 foreign key (FLAG_ELECTIVE_ADMISSION)
      references ENUM_CONST (ID);

alter table PR_TCH_STU_ELECTIVE
   add constraint FK_PR_TCH_S_FK_PR_TCH_PR_TCH_O foreign key (FK_TCH_OPENCOURSE_ID)
      references PR_TCH_OPENCOURSE (ID);

alter table PR_TCH_STU_ELECTIVE
   add constraint FK_PR_TCH_S_FK_PR_TCH_PR_TCH_P foreign key (FK_TCH_PROGRAM_COURSE)
      references PR_TCH_PROGRAM_COURSE (ID);

alter table PR_TCH_STU_PAPER
   add constraint FK_PR_TCH_S_FK_PR_TCH_ENUM_CO1 foreign key (FLAG_DRAFT_A_LAST_UPDATE)
      references ENUM_CONST (ID);

alter table PR_TCH_STU_PAPER
   add constraint FK_PR_TCH_S_FK_PR_TCH_ENUM_C14 foreign key (FLAG_FINAL_LAST_UPDATE)
      references ENUM_CONST (ID);

alter table PR_TCH_STU_PAPER
   add constraint FK_PR_TCH_S_REFERENCE_ENUM_CO3 foreign key (FLAG_TITLE_ADMISSION)
      references ENUM_CONST (ID);

alter table PR_TCH_STU_PAPER
   add constraint FK_PR_TCH_S_REFERENCE_ENUM_CO5 foreign key (FLAG_PAPER_REJOIN)
      references ENUM_CONST (ID);

alter table PR_TCH_STU_PAPER
   add constraint FK_PR_TCH_S_FK_PR_TCH_ENUM_CON foreign key (FLAG_SYLLABUS_LAST_UPDATE)
      references ENUM_CONST (ID);

alter table PR_TCH_STU_PAPER
   add constraint FK_PR_TCH_S_FK_PR_TCH_PE_REJO1 foreign key (FK_REJOIN_SECTION_ID)
      references PE_TCH_REJOIN_SECTION (ID);

alter table PR_TCH_STU_PAPER
   add constraint FK_PR_TCH_S_FK_PR_TCH_PE_TCH_R foreign key (FK_REJOIN_ROOM_ID)
      references PE_TCH_REJOIN_ROOM (ID);

alter table PR_TCH_STU_PAPER
   add constraint FK_PR_TCH_S_REFERENCE_PE_STUDE foreign key (FK_STU_ID)
      references PE_STUDENT (ID);

alter table PR_TCH_STU_PAPER
   add constraint FK_PR_TCH_S_REFERENCE_PR_TCH_3 foreign key (FK_PAPER_TITLE_ID)
      references PR_TCH_PAPER_TITLE (ID);

alter table PR_VOTE_QUESTION
   add constraint FK_PR_VOTE__REFERENCE_ENUM_CO2 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PR_VOTE_QUESTION
   add constraint FK_PR_VOTE__FK_PR_VOT_ENUM_CON foreign key (FLAG_QUESTION_TYPE)
      references ENUM_CONST (ID);

alter table PR_VOTE_QUESTION
   add constraint FK_PR_VOTE__FK_PR_VOT_PE_VOTE_ foreign key (FK_VOTE_PAPER_ID)
      references PE_VOTE_PAPER (ID);

alter table PR_VOTE_RECORD
   add constraint FK_PR_VOTE__FK_PR_VOT_PE_VOTE1 foreign key (FK_VOTE_PAPER_ID)
      references PE_VOTE_PAPER (ID);

alter table PR_VOTE_SUGGEST
   add constraint FK_PR_VOTE__REFERENCE_ENUM_CO1 foreign key (FLAG_CHECK)
      references ENUM_CONST (ID);

alter table PR_VOTE_SUGGEST
   add constraint FK_PR_VOTE__REFERENCE_ENUM_CO3 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table PR_VOTE_SUGGEST
   add constraint FK_PR_VOTE__REFERENCE_PE_VOTE2 foreign key (FK_VOTE_PAPER_ID)
      references PE_VOTE_PAPER (ID);

alter table SSO_USER
   add constraint FK_SSO_USER_REFERENCE_ENUM_CO1 foreign key (FLAG_ISVALID)
      references ENUM_CONST (ID);

alter table SSO_USER
   add constraint FK_SSO_USER_FK_SSO_US_ENUM_CON foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table SSO_USER
   add constraint FK_SSO_USER_REFERENCE_PE_PRI_R foreign key (FK_ROLE_ID)
      references PE_PRI_ROLE (ID);

alter table SYSTEM_APPLY
   add constraint FK_SYSTEM_A_REFERENCE_ENUM_CO1 foreign key (FLAG_APPLY_STATUS)
      references ENUM_CONST (ID);

alter table SYSTEM_APPLY
   add constraint FK_SYSTEM_A_FK_SYSTEM_ENUM_CON foreign key (APPLY_TYPE)
      references ENUM_CONST (ID);

alter table SYSTEM_APPLY
   add constraint FK_SYSTEM_A_FK_SYSTEM_PE_STUDE foreign key (FK_STUDENT_ID)
      references PE_STUDENT (ID);

alter table SYSTEM_VARIABLES
   add constraint FK_SYSTEM_V_REFERENCE_ENUM_CO1 foreign key (FLAG_BAK)
      references ENUM_CONST (ID);

alter table SYSTEM_VARIABLES
   add constraint FK_SYSTEM_V_FK_SYSTEM_ENUM_CON foreign key (FLAG_PLATFORM_SECTION)
      references ENUM_CONST (ID);

