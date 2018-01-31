--prompt PL/SQL Developer import file
--prompt Created on 2009��5��18�� by yingying
--set feedback off
--set define off
--prompt Loading PE_PRIORITY...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028809720375d4e0120376c86750019', '�鿴ѡ��δѡ��_*', '4028809720375d4e01203768474d0018', '/entity/teaching', 'StudentNoSel', '*');
commit;
--prompt 1 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40279d0a91dff8c3a011dffc4bd0a0021', '�ɼ��������_*', '600204', 'namespace', 'action', '*');
commit;
--prompt 2 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a920416fe901204171bdb50011', '�Ծ��ͳ��_*', '402880a920416fe901204171bdb50001', '/entity/exam', 'examPaperStat', '*');
commit;
--prompt 3 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a920416fe90120418f09480076', '���Է��ù���_*', '402880a920416fe90120418f09480006', '/entity/fee', 'recExamFee', '*');
commit;
--prompt 4 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd0ebb831232', '�鿴��ʷ�μ�_*', '402880a91dfd0d0c011dfd0ebb830001', '/entity/teaching', 'courseCoursewareHis', '*');
commit;
--prompt 5 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92064cf480120654e8be7130f', '�࿼��Ա����_autoInspector', '402880a92064cf480120654e8be7000f', '/entity/recruit', 'examInspector', 'autoInspector');
commit;
--prompt 6 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd0ebb831234', '���ÿγ̽�ʦ_*', '402880a91dfd0d0c011dfd0ebb830001', '/entity/teaching', 'prTchCourseTeacher', '*');
commit;
--prompt 7 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd0ebb831231', '�鿴��ʷ�̲�_*', '402880a91dfd0d0c011dfd0ebb830001', '/entity/teaching', 'courseBookHis', '*');
commit;
--prompt 8 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a9206a110001206aaeecee0014', 'վ����·����_*', '402880a9206a116001206aaeecee0014', '/entity/exam', 'peSiteLine', '*');
commit;
--prompt 9 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a9207e282001207e6a3837120e', '���������ӡ_*', '402880a9207e282001207e6a3837000e', '/entity/studentStatus', 'studentGoodApplyPrint', '*');
commit;
--prompt 10 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288097201892ad012018b894382302', '������Ľ�ʦ_*', '40288097201892ad012018b233390001', '/entity/teaching', 'paperTeacherAdd', '*');
commit;
--prompt 11 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62ad70631', '¼ȡ״̬����������ѯͳ�ƽ��_abstractList', '300502', '/entity/recruit', 'recruitStatus', 'abstractList');
commit;
--prompt 12 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62ae60633', '����ͳ�Ʋ�ѯ�鿴��ϸ_abstractList', '300501', '/entity/recruit', 'baomingStu', 'abstractList');
commit;
--prompt 13 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62b440637', '�ϱ����������޸�ѧ��������ϸ_abstractList', '700102', '/entity/fee', 'prFeeDetailPici', 'abstractList');
commit;
--prompt 14 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62b540639', 'Ϊѧ�����ø����շѱ�׼_abstractList', '700502', '/entity/fee', 'stuFeeStandardSet', 'abstractList');
commit;
--prompt 15 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62bb1063d', '��ѧ���Գɼ�������ͳ��_abstractList', '300504', '/entity/recruit', 'recruitScoreStat', 'abstractList');
commit;
--prompt 16 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62bc1063f', 'ԤԼͳ��_abstractList', '402880a91dff8c3a011dffa7636e000d', '/entity/exam', 'prFinalExamApplyCount', 'abstractList');
commit;
--prompt 17 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c1f0643', '�Զ��ſ���_abstractList', '800701', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 18 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c2e0645', 'ѧ�ڿ��β鿴_abstractList', '402880a91dfd0d0c011dfd19aa960013', '/entity/teaching', 'prTchOpencourse', 'abstractList');
commit;
--prompt 19 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62cea064d', '����γ�_abstractList', '402880a91dfd0d0c011dfd1ea5da0018', '/entity/teaching', 'countcoursereward', 'abstractList');
commit;
--prompt 20 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62d38064f', 'ע��ͳ��_abstractList', '800302', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 21 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62d770651', '�ɼ�����_abstractList', '800602', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 22 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62db50653', '�ɼ�����_abstractList', '800601', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 23 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62e320659', 'ͳ�Ʋ�ѯ1_abstractList', '800501', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 24 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62e9f065f', 'ѧ��ע�����_abstractList', '800301', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 25 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62f0d0665', '���ɵǷ��ʻ�_abstractList', '4028809720375d4e01203486774d004', '/entity/exam', 'prexamscoreinputuser', 'abstractList');
commit;
--prompt 26 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62f7a066b', '���ô��ʱ��_abstractList', '402880a91fa74cbc011fa7509a0f0003', '/entity/teaching', 'peTchRejoinSection', 'abstractList');
commit;
--prompt 27 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62fe80671', '��ӡ��λ��ǩ_abstractList', '402880a91fb751f8011fb756c5d20001', 'namespace', 'action', 'abstractList');
commit;
--prompt 28 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e630550677', '��ҵ����ͳ��_abstractList', '402880a91fd6ae10011fd6b7e4180001', '/entity/studentStatus', 'peGraduateStat', 'abstractList');
commit;
--prompt 29 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e630c2067d', '�ʾ���Ŀ����_abstractList', '402880a91e5d1ff2011e5dee91a00003', '/entity/information', 'prVoteQuestion', 'abstractList');
commit;
--prompt 30 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e631300683', '��ӵ����ʾ�_abstractList', '402880a91e5d1ff2011e5dee91a00002', '/entity/information', 'peVotePaper', 'abstractList');
commit;
--prompt 31 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6319d0689', 'ͳ��Ӣ��A_abstractList', '402880a91e874bce011e875c1e520002', '/entity/exam', 'englishAAvoidApply', 'abstractList');
commit;
--prompt 32 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e631ad068b', 'ѧ����֤_abstractList', '402880a92037626d0120379540aa0005', '/entity/recruit', 'xueliCheck', 'abstractList');
commit;
--prompt 33 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e631bc068d', '�����ƻ�����_abstractList', '402880a9203240940120329807000006', 'namespace', 'action', 'abstractList');
commit;
--prompt 34 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6321a0691', '�Ƿ��ʻ�����_abstractList', '4028809720375d4e01203486774d005', '/entity/exam', 'prexamscoreinputuser', 'abstractList');
commit;
--prompt 35 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6322a0693', '�ɼ�¼��ͳ��_abstractList', '4028809720375d4e01203486774d006', '/entity/exam', 'examscoreinputstatistic', 'abstractList');
commit;
--prompt 36 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e632880697', '���������ƻ�_abstractList', '402880a920324094012032db49b00007', 'namespace', 'action', 'abstractList');
commit;
--prompt 37 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e632970699', '��תרҵ����_abstractList', '402880a9205f34a901205f5444030001', '/entity/studentStatus', 'prStuChangeableMajor', 'abstractList');
commit;
--prompt 38 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e632f5069d', '�鿴��ѧ�ƻ����пγ�_abstractList', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'prTchProgramCourse', 'abstractList');
commit;
--prompt 39 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e63305069f', '�鿴��ѧ�ƻ������¿γ�_abstractList', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'teachPlanInfoCourse', 'abstractList');
commit;
--prompt 40 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6336206a3', '��ѧ��ѡ��ѡ��ѧ��_abstractList', '402880a91dfd0d0c011dfd1667ee000b', '/entity/teaching', 'elelctiveManage', 'abstractList');
commit;
--prompt 41 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6337206a5', '��ѧ��ѡ��_abstractList', '402880a91dfd0d0c011dfd1667ee000b', '/entity/teaching', 'peStuElective', 'abstractList');
commit;
--prompt 42 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10105', '�鿴��վ����Ա�绰�б�_*', '100802', '/entity/information', 'siteManagerPhoneView', '*');
commit;
--prompt 43 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10104', '�鿴����Ա�绰�б�_*', '100802', '/entity/information', 'managerPhoneView', '*');
commit;
--prompt 44 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10103', '�鿴��ʦ�绰�б�_*', '100802', '/entity/information', 'teacherPhoneView', '*');
commit;
--prompt 45 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028826a1e195595011d1985b790003', '����޸�ѧ��Υ�ͼ�¼_*', '400205', '/entity/studentStatus', 'prStudentOffenceAdd', '*');
commit;
--prompt 46 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91f491fc7011f493f0eb20002', '�γ̳ɼ���������_*', '402880a91f491fc7011f493aecb60001', 'namespace', 'action', '*');
commit;
--prompt 47 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a11007', '�˷���ϸ_*', '7003', '/entity/fee', 'feeRefund', '*');
commit;
--prompt 48 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402800991dfcfaab011dfd0716d90011', '����ѧ�ڿ���_*', '402880a91fcc3c73011fcc47aaec0001', 'namespace', 'action', '*');
commit;
--prompt 49 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4r28808c1e01beb1011e01c8f5a10002', 'δԤԼ�����б�_*', '402880a91fd61dae011fd61fcdd40001', '/entity/exam', 'finalExamNoBooking', '*');
commit;
--prompt 50 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91fef4fa7011fef5748fa0021', 'Ԥ����ͳ��_*', '402880a91fef4fa7011fef5748fa0001', '/entity/studentStatus', 'derateFeeStat', '*');
commit;
--prompt 51 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a9201209b50120125b1d27011c', '��ʦ�������_*', '402880a9201209b50120125b1d27001c', '/entity/fee', 'teacherReduce', '*');
commit;
--prompt 52 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4008126a1e195595011e19857b790003', '���ѧ���䶯_*', '400302', '/entity/studentStatus', 'studentStatus', '*');
commit;
--prompt 53 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288097201892ad012018b894380002', '���Ľ�ʦ����_*', '40288097201892ad012018b233390001', '/entity/teaching', 'paperTeacher', '*');
commit;
--prompt 54 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('40288097202190d3012021c9a41d0002', '�Ͽδ�������_*', '40288097202190d3012021c659c40001', '/entity/teaching', 'opencourseTimes', '*');
commit;
--prompt 55 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfceaba011dfcf91835000b', '������¼ȡ_*', '402880a91dfceaba011dfcf91835000b', '/entity/recruit', 'recruitManageNoExam', '*');
commit;
--prompt 56 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfcfd02090002', '�뿼�ɼ�ͳ��_*', '402880a91dfcfaab011dfcfd02090002', 'namespace', 'action', '*');
commit;
--prompt 57 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff9d4e6f0009', '��Ѳ������_*', '402880a91dff8c3a011dff9d4e6f0009', '/entity/exam', 'peExamPatrol', '*');
commit;
--prompt 58 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffa550da000a', '�����Ѳ��_*', '402880a91dff8c3a011dffa550da000a', '/entity/exam', 'peExamPatrolManage', '*');
commit;
--prompt 59 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffa64f1f000b', 'ԤԼ����_*', '402880a91dff8c3a011dffa64f1f000b', '/entity/exam', 'examBooking', '*');
commit;
--prompt 60 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffa95aaa0010', '��������_*', '402880a91dff8c3a011dffa95aaa0010', '/entity/exam', 'peExamRoom', '*');
commit;
--prompt 61 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028809720375d4e0120376c8675004', '�Ƿ�����Ƿ�_gotosetinputstatus', '4028809720375d4e01203486774d003', '/entity/exam', 'prexamscoreinputuser', 'gotosetinputstatus');
commit;
--prompt 62 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028809720375d4e0120376c86750765', '��ѧ�ƻ����пγ�_*', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'prTchProgramCourse', '*');
commit;
--prompt 63 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffb2c7b0001a', '����ʱ������_*', '402880a91dff8c3a011dffb2c7b0001a', '/entity/exam', 'peMainCourseTime', '*');
commit;
--prompt 64 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028809720375d4e0120376c26750765', '��ѧ�ƻ��γ�������Ϣ_*', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'teachPlanInfo', '*');
commit;
--prompt 65 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffb4a5a8001c', '����ʱ������_*', '402880a91dff8c3a011dffb4a5a8001c', '/entity/exam', 'peExamMaincourseNo', '*');
commit;
--prompt 66 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffb527f7001d', '���Կγ̵���_*', '402880a91dff8c3a011dffb527f7001d', '/entity/exam', 'prExamOpenMaincourse', '*');
commit;
--prompt 67 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffb61c5a001e', '�Զ����ſ���_*', '402880a91dff8c3a011dffb61c5a001e', 'namespace', 'action', '*');
commit;
--prompt 68 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffb6c5b90020', '������Ϣ����_*', '402880a91dff8c3a011dffb6c5b90020', '/entity/exam', 'peExamMaincourseRoom', '*');
commit;
--prompt 69 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffb75b040022', '����������_*', '402880a91dff8c3a011dffb75b040022', '/entity/exam', 'peMainCourseExamRoom', '*');
commit;
--prompt 70 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028804520375d4e0120376c26750765', '�鿴ѧ��ѡ��_*', '500503', '/entity/teaching', 'studentNoSelect', '*');
commit;
--prompt 71 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffb888780024', '�Ծ�ͳ���б�_*', '402880a91dff8c3a011dffb888780024', '/entity/exam', 'peMainCoursePaperBag', '*');
commit;
--prompt 72 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffbbe28f0029', '������Ϣ¼��_*', '402880a91dff8c3a011dffbbe28f0029', '/entity/fee', 'prFeeDetailReduce', '*');
commit;
--prompt 73 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffbd3acc002b', '������Ϣ¼��_*', '402880a91dff8c3a011dffbd3acc002b', '/entity/fee', 'prFeeDetailReturn', '*');
commit;
--prompt 74 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a914f8c3a011dff99ebeb0007', '���Ľ�չͳ�ƽ�ʦ_*', '402880a91dff8c3a011dff99ebeb0007', '/entity/teaching', 'paperProcess', '*');
commit;
--prompt 75 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfcde6d0f0001', '�����ϴ�_*', '402880a91dfcd969011dfcde6d0f0001', '/entity/recruit', 'recStudentPhoto', '*');
commit;
--prompt 76 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce4bc990006', '�����γ���ϸ_*', '402880a91dfcd969011dfce4bc990006', '/entity/recruit', 'examstucourse', '*');
commit;
--prompt 77 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce598340007', '�Զ�����γ�_*', '402880a91dfcd969011dfce598340007', 'namespace', 'action', '*');
commit;
--prompt 78 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfceaba011dfcef6a9b0003', 'Ѳ����Ա����_autoInspector', '402880a91dfceaba011dfcef6a9b0003', '/entity/recruit', 'examInspector', 'autoInspector');
commit;
--prompt 79 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfceaba011dfcf0fb1e0005', '��������_*', '402880a91dfceaba011dfcf0fb1e0005', '/entity/recruit', 'peRecRoom', '*');
commit;
--prompt 80 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfceaba011dfcf1e1290006', '����������_*', '402880a91dfceaba011dfcf1e1290006', '/entity/recruit', 'examStuRoom', '*');
commit;
--prompt 81 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd0242660009', '�䶯���_*', '402880a91dfcfaab011dfd0242660009', '/entity/studentStatus', 'peChangeType', '*');
commit;
--prompt 82 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd02f961000a', '�䶯רҵ_*', '402880a91dfcfaab011dfd02f961000a', '/entity/studentStatus', 'peChangeMajor', '*');
commit;
--prompt 83 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd03f9e9000c', '�䶯ѧϰ����_*', '402880a91dfcfaab011dfd03f9e9000c', '/entity/studentStatus', 'peChangeSite', '*');
commit;
--prompt 84 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd048512000d', '�䶯���_*', '402880a91dfcfaab011dfd048512000d', '/entity/studentStatus', 'peChangeType', '*');
commit;
--prompt 85 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd05fe06000f', '��ѧ�����б�_*', '402880a91dfcfaab011dfd05fe06000f', '/entity/studentStatus', 'peStudentExpel', '*');
commit;
--prompt 86 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd0688350010', '������ѧ_*', '402880a91dfcfaab011dfd0688350010', '/entity/studentStatus', 'peStudentStatus', '*');
commit;
--prompt 87 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd084ab50012', '���Ա�ҵ����_*', '402880a91dfcfaab011dfd084ab50012', '/entity/studentStatus', 'peCanGraduate', '*');
commit;
--prompt 88 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd08d92a0013', '��ҵ���_*', '402880a91dfcfaab011dfd08d92a0013', '/entity/studentStatus', 'prApplyGraduate', '*');
commit;
--prompt 89 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd09562a0014', '��ҵѧ���б�_*', '402880a91dfcfaab011dfd09562a0014', '/entity/studentStatus', 'peGraduatedStudent', '*');
commit;
--prompt 90 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd0a67d90015', '�걨����_*', '402880a91dfcfaab011dfd0a67d90015', '/entity/studentStatus', 'peYearReport', '*');
commit;
--prompt 91 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd0aeaa50016', '��ҵ֤��ӡ_*', '402880a91dfcfaab011dfd0aeaa50016', 'namespace', 'action', '*');
commit;
--prompt 92 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1667ee000b', '��ѧ��ѡ��_*', '402880a91dfd0d0c011dfd1667ee000b', 'namespace', 'action', '*');
commit;
--prompt 93 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1704ca000c', 'ѧ����ѡ��_*', '402880a91dfd0d0c011dfd1704ca000c', '/entity/teaching', 'electiveInfoManage', '*');
commit;
--prompt 94 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd17868d000d', 'ѡ��ͳ��_*', '402880a91dfd0d0c011dfd17868d000d', '/entity/teaching', 'electiveStat', '*');
commit;
--prompt 95 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd182482000e', 'δѡ��ѧ��_*', '402880a91dfd0d0c011dfd182482000e', '/entity/teaching', 'unElectiveStuList', '*');
commit;
--prompt 96 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd18b5d5000f', '�̲���������_*', '402880a91dfd0d0c011dfd18b5d5000f', '/entity/teaching', 'courseOrderStat', '*');
commit;
--prompt 97 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1a56760011', '��������_*', '402880a91dfd0d0c011dfd1a56760011', '/entity/teaching', 'batchConfirmCourse', '*');
commit;
--prompt 98 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880971e70cf1e011e70ec275d000a', '���Ͷ��Ų鿴ѧ��_*', '100802', '/entity/information', 'studentPhoneView', '*');
commit;
--prompt 99 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd226b6e001f', '�ɼ��ϳ�_*', '402880a91dfd0d0c011dfd226b6e001f', 'namespace', 'action', '*');
commit;
--prompt 100 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd22e7260020', '�ɼ���ѯ_*', '402880a91dfd0d0c011dfd22e7260020', 'namespace', 'action', '*');
commit;
--prompt 101 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('700102', '�ϱ���������_*', '700102', '/entity/fee', 'prFeeDetailForTakeIn', '*');
commit;
--prompt 102 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('1001', '֪ͨ����_*', '1001', '/entity/information', 'peBulletinView', '*');
commit;
--prompt 103 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff9a84050079', '��ҵ�����������_*', '402880a91dff8c3a011dff9a84050079', '/entity/teaching', 'paperReapplyAudit', '*');
commit;
--prompt 104 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('1004', '�޸�����_*', '1004', 'namespace', 'action', '*');
commit;
--prompt 105 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('1005', '���Ź���_*', '1005', '/entity/information', 'peInfoNews', '*');
commit;
--prompt 106 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('1007', '���Ĺ���_*', '1007', '/entity/information', 'peDocument', '*');
commit;
--prompt 107 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('1009', '��̳����_*', '1009', '/sso/forum', 'forum', '*');
commit;
--prompt 108 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('2001', 'ѧϰ���Ĺ���_*', '2001', '/entity/basic', 'peSite', '*');
commit;
--prompt 109 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1c02beb1011e01c8f5a10002', '�����б�鿴���Ͷ���_*', '100804', '/entity/information', 'prSmsSendStatus', '*');
commit;
--prompt 110 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('2003', 'רҵ����_*', '2003', '/entity/basic', 'peMajor', '*');
commit;
--prompt 111 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('2004', '��ι���_*', '2004', '/entity/basic', 'peEdutype', '*');
commit;
--prompt 112 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('2005', '�꼶����_*', '2005', '/entity/basic', 'peGrade', '*');
commit;
--prompt 113 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('7004', '�˹�������ò���_*', '7004', '/entity/fee', 'prFeeDetailSpecial', '*');
commit;
--prompt 114 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('100802', '���Ͷ���_*', '100802', '/entity/information', 'studentPhoneView', '*');
commit;
--prompt 115 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('100803', '��˶���_*', '100803', '/entity/information', 'peSmsCheck', '*');
commit;
--prompt 116 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('100804', '�����б�_*', '100804', '/entity/information', 'peSms', '*');
commit;
--prompt 117 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('100805', '����ͳ��_*', '100805', '/entity/information', 'peSmsStatistic', '*');
commit;
--prompt 118 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300101', '�����������ι���_*', '300101', '/entity/recruit', 'peRecruitplan', '*');
commit;
--prompt 119 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300104', 'ѧ�ֱ�׼����_*', '300104', '/entity/recruit', 'setCreditA', '*');
commit;
--prompt 120 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300200', '������Ϣ��ѯ_*', '300200', '/entity/recruit', 'recruitStu', '*');
commit;
--prompt 121 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1c02beb1011e01c8f5a10005', '�������ɿο��Կγ�_*', '402880a91dff8c3a011dffb4a5a8001c', '/entity/exam', 'examMaincourseSet', '*');
commit;
--prompt 122 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a20003', '���뷢Ʊ��_*', '402880a92011efae012011fc12d40001', '/entity/fee', 'peFeeDetailForReciept', '*');
commit;
--prompt 123 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300204', '�����ʸ����_*', '300204', '/entity/recruit', 'peRecStudentNoexamCheck', '*');
commit;
--prompt 124 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300403', '¼ȡ״̬�޸�_*', '300403', '/entity/recruit', 'recruitManage', '*');
commit;
--prompt 125 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300404', '¼ȡ֪ͨ���ӡ_*', '300404', '/entity/recruit', 'recruitNotification', '*');
commit;
--prompt 126 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300501', '������Ϣ��ѯͳ��_*', '300501', 'namespace', 'action', '*');
commit;
--prompt 127 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300502', '¼ȡ״̬����������ѯͳ��_*', '300502', 'namespace', 'action', '*');
commit;
--prompt 128 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300503', '���Բ�ѯͳ��_*', '300503', '/entity/recruit', 'recruitNoExamStat', '*');
commit;
--prompt 129 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400201', 'ѧ����Ϣ��ѯ_*', '400201', '/entity/studentStatus', 'peStudentInfo', '*');
commit;
--prompt 130 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffc4bd0a003f', 'ԤԼ����B_*', '402880911e106277011e10951f290002', '/entity/exam', 'maincourseElectivecourse', '*');
commit;
--prompt 131 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400203', 'ģ���½_*', '400203', '/entity/studentStatus', 'simulateStudentLogin', '*');
commit;
--prompt 132 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400204', '���Ź���_*', '400204', '/entity/studentStatus', 'prStudentGoodApply', '*');
commit;
--prompt 133 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400205', 'Υ�͹���_*', '400205', '/entity/studentStatus', 'prStudentOffence', '*');
commit;
--prompt 134 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400404', '����ͼ��У��_*', '400404', '/entity/studentStatus', 'peStudentPhoto', '*');
commit;
--prompt 135 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400501', '��������ѧλ����_*', '400501', '/entity/studentStatus', 'peCanApplyDegree', '*');
commit;
--prompt 136 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400502', 'ѧλ���_*', '400502', '/entity/studentStatus', 'peApplyDegree', '*');
commit;
--prompt 137 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400503', 'ѧλ����_*', '400503', '/entity/studentStatus', 'peStudentDegree', '*');
commit;
--prompt 138 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400601', 'ѧ���ۺ���Ϣ��ѯ_*', '400601', '/entity/studentStatus', 'peStudentStudyCount', '*');
commit;
--prompt 139 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400602', '�ڼ�ѧ��ͳ��_*', '400602', 'namespace', 'action', '*');
commit;
--prompt 140 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400603', '��ҵѧλͳ��_*', '400603', '/entity/studentStatus', 'prStudentDegree', '*');
commit;
--prompt 141 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('500201', 'ѡ��ʱ������_*', '500201', '/entity/teaching', 'electiveTimeManage', '*');
commit;
--prompt 142 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('500302', '��ʦ��ѧͳ��_*', '500302', 'namespace', 'action', '*');
commit;
--prompt 143 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('500305', '����������ҵ��_*', '500305', '/entity/teaching', 'countcheckofworkfee', '*');
commit;
--prompt 144 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('500501', '��ҵ����ʱ������_*', '500501', '/entity/teaching', 'paperTime', '*');
commit;
--prompt 145 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('500502', '�鿴������Ŀ_*', '500502', '/entity/teaching', 'teacherTopic', '*');
commit;
--prompt 146 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('500506', '��������ͳ��_*', '500506', '/entity/teaching', 'paperShenqingStat', '*');
commit;
--prompt 147 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('600201', '��ĩ����ʱ������_*', '600201', '/entity/exam', 'peFinalExam', '*');
commit;
--prompt 148 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('600303', '�ɼ�¼��_*', '600303', '/entity/exam', 'maincourseScoreManage', '*');
commit;
--prompt 149 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('30020102', '����¼��_*', '30020102', '/entity/recruit', 'addPeRecStudent', '*');
commit;
--prompt 150 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('700501', '�շѱ�׼����_*', '700501', '/entity/fee', 'feeStandardManager', '*');
commit;
--prompt 151 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('700502', '���˱�׼����_*', '700502', '/entity/fee', 'listStudentForFeeSet', '*');
commit;
--prompt 152 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('700601', '�������β�ѯ_*', '700601', '/entity/fee', 'peFeeBathQuery', '*');
commit;
--prompt 153 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('700602', '��������ͳ��_*', '700602', '/entity/fee', 'feeTotalCount', '*');
commit;
--prompt 154 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('30020101', '����¼��ѧ��_*', '30020101', '/entity/recruit', 'addPeRecStudent', '*');
commit;
--prompt 155 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce10b580003', '���Կγ�����_*', '402880a91dfcd969011dfce10b580003', '/entity/recruit', 'examcourseset', '*');
commit;
--prompt 156 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce314960005', '����ʱ������_*', '402880a91dfcd969011dfce314960005', '/entity/recruit', 'eduExamTimeSet', '*');
commit;
--prompt 157 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce6673c0008', '�Զ����俼��_*', '402880a91dfcd969011dfce6673c0008', 'namespace', 'action', '*');
commit;
--prompt 158 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce724900009', '��ӡ׼��֤_*', '402880a91dfcd969011dfce724900009', '/entity/recruit', 'examstuticket', '*');
commit;
--prompt 159 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce7c4b8000a', '�࿼��ѯ_*', '402880a91dfcd969011dfce7c4b8000a', '/entity/recruit', 'examinvigilator', '*');
commit;
--prompt 160 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce8c32d000b', 'Ѳ����ѯ_*', '402880a91dfcd969011dfce8c32d000b', '/entity/recruit', 'examInspector', '*');
commit;
--prompt 161 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfceaba011dfcf5c37c0007', '��ѧ���Գɼ�_*', '402880a91dfceaba011dfcf5c37c0007', '/entity/recruit', 'examachievement', '*');
commit;
--prompt 162 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfceaba011dfcf729070009', 'ͳ��¼ȡ����_*', '402880a91dfceaba011dfcf729070009', 'namespace', 'action', '*');
commit;
--prompt 163 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfceaba011dfcf87b1a000a', '������¼ȡ_*', '402880a91dfceaba011dfcf87b1a000a', 'namespace', 'action', '*');
commit;
--prompt 164 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfcfc63680001', '�뿼�ɼ���ѯ_*', '402880a91dfcfaab011dfcfc63680001', '/entity/recruit', 'examScoreSearch', '*');
commit;
--prompt 165 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfcfe0b3c0003', '��¼ȡ�б�_*', '402880a91dfcfaab011dfcfe0b3c0003', '/entity/studentStatus', 'prRecPriPay', '*');
commit;
--prompt 166 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfcfea7ca0004', '��������_*', '402880a91dfcfaab011dfcfea7ca0004', '/entity/studentStatus', 'derateFeeApply', '*');
commit;
--prompt 167 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd01afda0008', '�䶯ѧϰ����_*', '402880a91dfcfaab011dfd01afda0008', '/entity/studentStatus', 'peChangeSite', '*');
commit;
--prompt 168 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd0ebb830001', '�γ���Ϣ�б�_*', '402880a91dfd0d0c011dfd0ebb830001', '/entity/teaching', 'peCourseManager', '*');
commit;
--prompt 169 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1055f90003', '��ѧ�ƻ��б�_*', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'peTeaPlan', '*');
commit;
--prompt 170 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd10c7320004', '�����ѧ�ƻ�_*', '402880a91dfd0d0c011dfd10c7320004', '/entity/teaching', 'peTeaPlan', '*');
commit;
--prompt 171 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd11aa6e0005', '��ʦ�б�_*', '402880a91dfd0d0c011dfd11aa6e0005', '/entity/teaching', 'teacher', '*');
commit;
--prompt 172 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd123ece0006', '�����ʦ����_*', '402880a91dfd0d0c011dfd123ece0006', 'namespace', 'action', '*');
commit;
--prompt 173 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1303e30007', '�̲���Ϣ�б�_*', '402880a91dfd0d0c011dfd1303e30007', '/entity/teaching', 'teachingMaterialsManager', '*');
commit;
--prompt 174 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd13ac290008', '������ӽ̲�_*', '402880a91dfd0d0c011dfd13ac290008', '/entity/teaching', 'teachingMaterialsManager', '*');
commit;
--prompt 175 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd14be740009', '�μ��б�_*', '402880a91dfd0d0c011dfd14be740009', '/entity/teaching', 'courseware', '*');
commit;
--prompt 176 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1578ea000a', '����μ�����_*', '402880a91dfd0d0c011dfd1578ea000a', 'namespace', 'action', '*');
commit;
--prompt 177 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1aec8c0012', '����ͳ��_*', '402880a91dfd0d0c011dfd1aec8c0012', '/entity/teaching', 'confirmedCourseStat', '*');
commit;
--prompt 178 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1d1e210016', '�γ̱���Ϣ_*', '402880a91dfd0d0c011dfd1d1e210016', '/entity/teaching', 'syllabus', '*');
commit;
--prompt 179 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1d98a10017', '����γ̱�_*', '402880a91dfd0d0c011dfd1d98a10017', '/entity/teaching', 'syllabus', '*');
commit;
--prompt 180 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1f2fac0019', 'ϵ������_*', '402880a91dfd0d0c011dfd1f2fac0019', '/entity/teaching', 'countcoursecoefficient', '*');
commit;
--prompt 181 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd204784001a', 'ƽʱ�ɼ�����_*', '402880a91dfd0d0c011dfd204784001a', 'namespace', 'action', '*');
commit;
--prompt 182 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd20c02f001b', '�鿴��ҵ�ɼ�_*', '402880a91dfd0d0c011dfd20c02f001b', 'namespace', 'action', '*');
commit;
--prompt 183 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd2161af001d', '���Գɼ��鿴_*', '402880a91dfd0d0c011dfd2161af001d', 'namespace', 'action', '*');
commit;
--prompt 184 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd21f4e6001e', '�ɼ��ϳ�����_*', '402880a91dfd0d0c011dfd21f4e6001e', '/entity/teaching', 'scorePercentSet', '*');
commit;
--prompt 185 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd23e3f50021', 'ͳ���ɼ�_*', '402880a91dfd0d0c011dfd23e3f50021', '/entity/teaching', 'unitExamScore', '*');
commit;
--prompt 186 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd247a590022', '��������ɼ�_*', '402880a91dfd0d0c011dfd247a590022', 'namespace', 'action', '*');
commit;
--prompt 187 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd25f61b0024', '��������_*', '402880a91dfd0d0c011dfd25f61b0024', 'namespace', 'action', '*');
commit;
--prompt 188 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8761011dff8af7ac0001', '��ȡ�������_*', '402880a91dff8761011dff8af7ac0001', '/entity/teaching', 'replyListSelect', '*');
commit;
--prompt 189 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff958b500001', '�鿴�������_*', '402880a91dff8c3a011dff958b500001', '/entity/teaching', 'replyListView', '*');
commit;
--prompt 190 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff96158f0002', '���ʱ������_*', '402880a91dff8c3a011dff96158f0002', 'namespace', 'action', '*');
commit;
--prompt 191 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff9a84050008', '���ĳɼ�ͳ��_*', '402880a91dff8c3a011dff9a84050008', '/entity/teaching', 'paperScoreStat', '*');
commit;
--prompt 192 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd25f61b0064', 'ѧλӢ���������_*', '402880a91dfd0d0c011dfd25f61b0064', '/entity/teaching', 'degreeEnglishAudit', '*');
commit;
--prompt 193 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400300', 'ѧ���䶯����_*', '400300', '/entity/studentStatus', 'peChangeApply', '*');
commit;
--prompt 194 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10015', '�鿴վ�����Ա_*', '2001', '/entity/basic', 'peSiteManager', '*');
commit;
--prompt 195 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10016', '���������ƻ�վ��_*', '300102', '/entity/recruit', 'prExistSite', '*');
commit;
--prompt 196 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10017', 'Ϊ��ѡ��ѧϰ��������ѧ�ֱ�׼_*', '300104', '/entity/recruit', 'setCreditB', '*');
commit;
--prompt 197 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10002', 'Ԥ���Ѽ���_*', '4028808c1e01beb1011e01c792b50001', '/entity/studentStatus', 'prRecPriPayApply', '*');
commit;
--prompt 198 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028826a1e195595011e19857b790003', '�����б�_*', '4028826a1e195595011e1984bbb30002', '/entity/fee', 'prFeeDetailReduce', '*');
commit;
--prompt 199 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd22e7260021', '���Ƴɼ��ϳ�_*', '402880a91dfd0d0c011dfd22e7260021', 'namespace', 'action', '*');
commit;
--prompt 200 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('100203', '�½�����_*', '100203', '/entity/information', 'peDocumentOutbox', '*');
commit;
--prompt 201 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('100202', '������_*', '100202', '/entity/information', 'peDocumentOutbox', '*');
commit;
--prompt 202 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('100201', '�ռ���_*', '100201', '/entity/information', 'peDocumentView', '*');
commit;
--prompt 203 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffc4bd0a003e', 'ԤԼ����_*', '402880911e106277011e10951f290002', '/entity/exam', 'maincourseExamBooking', '*');
commit;
--prompt 204 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffc4bd0a004e', 'ԤԼ��ѯɾ��_*', '402880911e106277011e10adce210002', '/entity/exam', 'maincourseElectiveManage', '*');
commit;
--prompt 205 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd0716d90011', '����ѧ��_*', '402880a91dfcfaab011dfd0716d90011', '/entity/studentStatus', 'peStudentStatus', '*');
commit;
--prompt 206 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd19aa960010', '�ֶ�����_*', '402880a91dfd0d0c011dfd19aa960010', '/entity/teaching', 'manualConfirmCourse', '*');
commit;
--prompt 207 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('100601', '��ӹ���_*', '100601', '/entity/information', 'peBulletin', '*');
commit;
--prompt 208 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('2006', 'ѧ�ڹ���_*', '2006', '/entity/basic', 'peSemester', '*');
commit;
--prompt 209 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('100801', 'ϵͳ���ŵ����_*', '100801', '/entity/information', 'peSystemSmsPoint', '*');
commit;
--prompt 210 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300102', '�����ƻ�����_*', '300102', '/entity/recruit', 'prRecPlanMajorEdutype', '*');
commit;
--prompt 211 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300202', '������Ϣ���_*', '300202', '/entity/recruit', 'peRecStudentCheck', '*');
commit;
--prompt 212 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffc4bd0a002e', '�ɼ�¼��_*', '402880a91dff8c3a011dffc4bd0a002e', '/entity/exam', 'examScore', '*');
commit;
--prompt 213 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffc53fb7002f', '�ɼ�����¼��_*', '402880a91dff8c3a011dffc53fb7002f', 'namespace', 'action', '*');
commit;
--prompt 214 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff96c3630003', '���֪ͨ_*', '402880a91dff8c3a011dff96c3630003', 'namespace', 'action', '*');
commit;
--prompt 215 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff97d5fc0004', '���ĳɼ��鿴_*', '402880a91dff8c3a011dff97d5fc0004', '/entity/teaching', 'paperScoreSearch', '*');
commit;
--prompt 216 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff985e950005', '���ɼ�¼��_*', '402880a91dff8c3a011dff985e950005', '/entity/teaching', 'replyScoreSearch', '*');
commit;
--prompt 217 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff98eb920006', '���ĳɼ��ϳ�_*', '402880a91dff8c3a011dff98eb920006', 'namespace', 'action', '*');
commit;
--prompt 218 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff99ebeb0007', '���Ľ�չͳ��_*', '402880a91dff8c3a011dff99ebeb0007', '/entity/teaching', 'paperProcess', '*');
commit;
--prompt 219 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffa6e3be000c', 'ԤԼ��ѯɾ��_*', '402880a91dff8c3a011dffa6e3be000c', '/entity/exam', 'peFinalExamApply', '*');
commit;
--prompt 220 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffa7636e000d', 'ԤԼͳ��_*', '402880a91dff8c3a011dffa7636e000d', 'namespace', 'action', '*');
commit;
--prompt 221 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffa843db000e', '���Գ��ι���_*', '402880a91dff8c3a011dffa843db000e', '/entity/exam', 'peExamno', '*');
commit;
--prompt 222 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffa8ca51000f', '�Զ����ſ���_*', '402880a91dff8c3a011dffa8ca51000f', 'namespace', 'action', '*');
commit;
--prompt 223 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffaa107d0011', '�������ŵ���_*', '402880a91dff8c3a011dffaa107d0011', '/entity/exam', 'prExamBooking', '*');
commit;
--prompt 224 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffaabae60012', '�������Ų�ѯ_*', '402880a91dff8c3a011dffaabae60012', '/entity/exam', 'examInfoSearch', '*');
commit;
--prompt 225 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffab52630013', '�Ծ��ɷ���_*', '402880a91dff8c3a011dffab52630013', '/entity/exam', 'prExamPaperCount', '*');
commit;
--prompt 226 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffaec3200017', 'ƽʱ�ɼ�����_*', '402880a91dff8c3a011dffaec3200017', '/entity/exam', 'normalScoreReCheck', '*');
commit;
--prompt 227 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffb989ab0025', '������Ϣ_*', '402880a91dff8c3a011dffb989ab0025', '/entity/fee', 'prFeeDetailIn', '*');
commit;
--prompt 228 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffba48290026', '������Ϣ¼��_*', '402880a91dff8c3a011dffba48290026', '/entity/fee', 'prFeeDetailIn', '*');
commit;
--prompt 229 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffbad18d0027', '������Ϣ¼��_*', '402880a91dff8c3a011dffbad18d0027', '/entity/fee', 'prFeeDetailIn', '*');
commit;
--prompt 230 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('500503', '�鿴ѧ��ѡ��_*', '500503', '/entity/teaching', 'studentTopic', '*');
commit;
--prompt 231 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('600304', '�ɼ�����_*', '600304', '/entity/exam', 'peMainCourseScoreRecheck', '*');
commit;
--prompt 232 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce22f470004', '���רҵ����_*', '402880a91dfcd969011dfce22f470004', '/entity/recruit', 'eduTypeMajorExamSet', '*');
commit;
--prompt 233 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('700604', 'ѧ�����з�����ϸ_*', '700604', '/entity/fee', 'prFeeDetailByStudent', '*');
commit;
--prompt 234 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfcdf76120002', '�����ϴ�_*', '402880a91dfcd969011dfcdf76120002', '/entity/recruit', 'recStudentPhoto', '*');
commit;
--prompt 235 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfceaba011dfcf678d20008', '���Գɼ�����_*', '402880a91dfceaba011dfcf678d20008', '/entity/recruit', 'examachievement', '*');
commit;
--prompt 236 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd0f3f980002', '������ӿγ�_*', '402880a91dfd0d0c011dfd0f3f980002', '/entity/teaching', 'peCourseManager', '*');
commit;
--prompt 237 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1c395f0015', '�ѿ�������_*', '402880a91dfd0d0c011dfd1c395f0015', '/entity/teaching', 'confirmedCourseInfo', '*');
commit;
--prompt 238 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd2568df0023', 'ѧλӢ��ɼ�_*', '402880a91dfd0d0c011dfd2568df0023', '/entity/teaching', 'degreeEnglishScore', '*');
commit;
--prompt 239 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfcff8c7d0005', '��ע���б�_*', '402880a91dfcfaab011dfcff8c7d0005', '/entity/studentStatus', 'peRegisterStudent', '*');
commit;
--prompt 240 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffbc7634002a', '������Ϣ����_*', '402880a91dff8c3a011dffbc7634002a', '/entity/fee', 'prFeeDetailReduce', '*');
commit;
--prompt 241 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcfaab011dfd050cc1000e', '�䶯רҵ_*', '402880a91dfcfaab011dfd050cc1000e', '/entity/studentStatus', 'peChangeMajor', '*');
commit;
--prompt 242 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('1003', '��������_*', '1003', '/entity/information', 'personalInfo', '*');
commit;
--prompt 243 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300103', '��������_*', '300103', '/entity/recruit', 'recruitJianzhang', '*');
commit;
--prompt 244 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('300203', '��ʦ�ʸ����_*', '300203', '/entity/recruit', 'peRecStudentTeacherCheck', '*');
commit;
--prompt 245 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('400401', '��ҵ���ι���_*', '400401', '/entity/studentStatus', 'peGraduate', '*');
commit;
--prompt 246 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('500303', '��ҵͳ��_*', '500303', '/entity/teaching', 'homeWorkStat', '*');
commit;
--prompt 247 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('700103', '�����������_*', '700103', '/entity/fee', 'peFeeBathCheck', '*');
commit;
--prompt 248 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('700603', 'ѧ�������˻���ѯ_*', '700603', '/entity/fee', 'studentAccount', '*');
commit;
--prompt 249 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10018', 'ͳ��¼ȡ��������¼ȡ������_*', '402880a91dfceaba011dfcf729070009', '/entity/recruit', 'examresultcalculate', '*');
commit;
--prompt 250 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10019', '������¼ȡ����¼ȡ������_*', '402880a91dfceaba011dfcf87b1a000a', '/entity/recruit', 'recruitManageExam', '*');
commit;
--prompt 251 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10020', '������Ϣ��ѯͳ�ƽ��_*', '300501', '/entity/recruit', 'baomingStat', '*');
commit;
--prompt 252 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10021', '¼ȡ״̬����������ѯͳ�ƽ��_*', '300502', '/entity/recruit', 'recruitStatus', '*');
commit;
--prompt 253 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10022', '����ͳ�Ʋ�ѯ�鿴��ϸ_*', '300501', '/entity/recruit', 'baomingStu', '*');
commit;
--prompt 254 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10023', '�ڼ�ѧ��ͳ�ƽ��_*', '400602', '/entity/studentStatus', 'prStudentOnStatus', '*');
commit;
--prompt 255 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10024', '�ϱ����������޸�ѧ��������ϸ_*', '700102', '/entity/fee', 'prFeeDetailPici', '*');
commit;
--prompt 256 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10025', 'Ϊѧ�����ø����շѱ�׼_*', '700502', '/entity/fee', 'stuFeeStandardSet', '*');
commit;
--prompt 257 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10026', '�������β�ѯѧ��������ϸ_*', '700601', '/entity/fee', 'peFeePiCi', '*');
commit;
--prompt 258 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01d8f5a10002', '��ѧ���Գɼ�������ͳ��_*', '300504', '/entity/recruit', 'recruitScoreStat', '*');
commit;
--prompt 259 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e11c8f5a10002', 'ԤԼͳ��_*', '402880a91dff8c3a011dffa7636e000d', '/entity/exam', 'prFinalExamApplyCount', '*');
commit;
--prompt 260 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('800702', '�鿴��������_*', '800702', '/entity/publicCourse', 'action', '*');
commit;
--prompt 261 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('800701', '�Զ��ſ���_*', '800701', '/entity/publicCourse', 'action', '*');
commit;
--prompt 262 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10005', 'ѧ�ڿ��β鿴_*', '402880a91dfd0d0c011dfd19aa960013', '/entity/teaching', 'prTchOpencourse', '*');
commit;
--prompt 263 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01beb1011e01c8f5a10003', 'ѧ�ڿ��β���_*', '402880a91dfd0d0c011dfd19aa960012', 'namespace', 'action', '*');
commit;
--prompt 264 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('100602', '�����б�_*', '100602', '/entity/information', 'peBulletin', '*');
commit;
--prompt 265 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfceaba011dfcf031460004', '����ͳ��_*', '402880a91dfceaba011dfcf031460004', '/entity/recruit', 'examfenpeistat', '*');
commit;
--prompt 266 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1ea5da0018', '����γ�_*', '402880a91dfd0d0c011dfd1ea5da0018', '/entity/teaching', 'countcoursereward', '*');
commit;
--prompt 267 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('800302', 'ע��ͳ��_*', '800302', '/entity/publicCourse', 'action', '*');
commit;
--prompt 268 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('800602', '�ɼ�����_*', '800602', '/entity/publicCourse', 'action', '*');
commit;
--prompt 269 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('800601', '�ɼ�����_*', '800601', '/entity/publicCourse', 'action', '*');
commit;
--prompt 270 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('800503', 'ͳ�Ʋ�ѯ3_*', '800503', '/entity/publicCourse', 'action', '*');
commit;
--prompt 271 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('800502', 'ͳ�Ʋ�ѯ2_*', '800502', '/entity/publicCourse', 'action', '*');
commit;
--prompt 272 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('800501', 'ͳ�Ʋ�ѯ1_*', '800501', '/entity/publicCourse', 'action', '*');
commit;
--prompt 273 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('8004', '������Ϣ����_*', '8004', '/entity/publicCourse', 'action', '*');
commit;
--prompt 274 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('800303', 'ѡ����Ϣ����_*', '800303', '/entity/publicCourse', 'action', '*');
commit;
--prompt 275 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('800301', 'ѧ��ע�����_*', '800301', '/entity/publicCourse', 'action', '*');
commit;
--prompt 276 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('8002', '�γ̹���_*', '8002', '/entity/publicCourse', 'action', '*');
commit;
--prompt 277 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('8001', '���¹���_*', '8001', '/entity/publicCourse', 'action', '*');
commit;
--prompt 278 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028809720375d4e0130276c86750018', '���ɵǷ��ʻ�_*', '4028809720375d4e01203486774d004', '/entity/exam', 'prexamscoreinputuser', '*');
commit;
--prompt 279 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('403880a92dff8c3a011dffc4bd0a003e', '��ҵ�ɼ�����_*', '402880a91dff8c3a011dffaf4e490018', '/entity/exam', 'workScoreReCheck', '*');
commit;
--prompt 280 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('403880a92dff8c3a011dffc4bd0a0123e', '���Գɼ�����_*', '402880a91dff8c3a011dffaff18e0019', '/entity/exam', 'examScoreReCheck', '*');
commit;
--prompt 281 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808d0e01bea1011e01c8f5a10002', '���ô��ʱ��_*', '402880a91fa74cbc011fa7509a0f0003', '/entity/teaching', 'peTchRejoinSection', '*');
commit;
--prompt 282 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028100d0e01bea1011e01c8f5a10002', '���ô�����_*', '402880a91fa7931b011fa79eee9c0001', '/entity/teaching', 'peTchRejoinRoom', '*');
commit;
--prompt 283 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a93dff8c3a011dffc4bd0a004e2', '��ĩ���Դ�ӡ_*', '402880a91dff8c3a011dffab52630013', '/entity/exam', 'finalExamPrint', '*');
commit;
--prompt 284 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91d40d0c0a5dfd22e7260021', '��ӡ��λ��ǩ_*', '402880a91fb751f8011fb756c5d20001', 'namespace', 'action', '*');
commit;
--prompt 285 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c324dfd22e7260021', '�Ծ�ͳ���б�_*', '402880a91dff8c3a011dffb888780024', '/entity/exam', 'maincourseStat', '*');
commit;
--prompt 286 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dffc4bd0a0021', '�Ծ�ǩ���_*', '4028808c1fca20a9011fca27bd9f0001', 'namespace', 'action', '*');
commit;
--prompt 287 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('410080a91dfd0d0c011dfd22e7260021', '��ҵ����ͳ��_*', '402880a91fd6ae10011fd6b7e4180001', '/entity/studentStatus', 'peGraduateStat', '*');
commit;
--prompt 288 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a9201323454120132590ba0001', '��ʦ�����б�_*', '402880a9201323450120132590ba0001', '/entity/fee', 'teacherReduceDetail', '*');
commit;
--prompt 289 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00004', '�ʾ���_*', '402880a91e5d1ff2011e5dee91a00003', '/entity/information', 'prVoteSuggest', '*');
commit;
--prompt 290 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00005', '�ʾ���Ŀ����_*', '402880a91e5d1ff2011e5dee91a00003', '/entity/information', 'prVoteQuestion', '*');
commit;
--prompt 291 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e874bce011e875d138f0005', '�γ��⿼�������_*', '402880a91e874bce011e875d138f0005', '/entity/exam', 'examAvoidApply', '*');
commit;
--prompt 292 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00003', '�����ʾ��б�_*', '402880a91e5d1ff2011e5dee91a00003', '/entity/information', 'peVotePaper', '*');
commit;
--prompt 293 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e5d1ff2011e5dee91a00002', '��ӵ����ʾ�_*', '402880a91e5d1ff2011e5dee91a00002', '/entity/information', 'peVotePaper', '*');
commit;
--prompt 294 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e874bce011e875d138f0004', 'ͳ�������_*', '402880a91e874bce011e875d138f0004', '/entity/exam', 'computerAvoidApply', '*');
commit;
--prompt 295 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e874bce011e875caae30003', 'ͳ��Ӣ��B_*', '402880a91e874bce011e875caae30003', '/entity/exam', 'englishBAvoidApply', '*');
commit;
--prompt 296 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91e874bce011e875c1e520002', 'ͳ��Ӣ��A_*', '402880a91e874bce011e875c1e520002', '/entity/exam', 'englishAAvoidApply', '*');
commit;
--prompt 297 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92037626d0120379540aa0015', 'ѧ����֤_*', '402880a92037626d0120379540aa0005', '/entity/recruit', 'xueliCheck', '*');
commit;
--prompt 298 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a9203240940120329807300106', '�����ƻ�����_*', '402880a9203240940120329807000006', 'namespace', 'action', '*');
commit;
--prompt 299 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028809720375d4e0130276c86750019', '�����Ƿ�����_*', '4028809720375d4e01203486774d0018', '/entity/exam', 'examscoreexportinputdata', '*');
commit;
--prompt 300 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028809720375d4e0130276c86750017', '�Ƿ��ʻ�����_*', '4028809720375d4e01203486774d005', '/entity/exam', 'prexamscoreinputuser', '*');
commit;
--prompt 301 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028809720375d4e0130276c86750016', '�ɼ�¼��ͳ��_*', '4028809720375d4e01203486774d006', '/entity/exam', 'examscoreinputstatistic', '*');
commit;
--prompt 302 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028809720375d4e0130276c86750015', '�ɼ���ѯ�޸�_*', '4028809720375d4e01203486774d007', '/entity/exam', 'examscoreinput', '*');
commit;
--prompt 303 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a920324094012032db49b12317', '���������ƻ�_*', '402880a920324094012032db49b00007', 'namespace', 'action', '*');
commit;
--prompt 304 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92325f34a901205f5444030001', '��תרҵ����_*', '402880a9205f34a901205f5444030001', '/entity/studentStatus', 'prStuChangeableMajor', '*');
commit;
--prompt 305 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1055f91231', '�鿴�γ�������Ϣ_*', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'teachPlanInfo', '*');
commit;
--prompt 306 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1055f91232', '�鿴��ѧ�ƻ����пγ�_*', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'prTchProgramCourse', '*');
commit;
--prompt 307 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1055f91233', '�鿴��ѧ�ƻ������¿γ�_*', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'teachPlanInfoCourse', '*');
commit;
--prompt 308 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('500104', '�鿴��ʦ�γ�_*', '500104', '/entity/teaching', 'teacherCourse', '*');
commit;
--prompt 309 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1667ee1231', '��ѧ��ѡ��ѡ��ѧ��_*', '402880a91dfd0d0c011dfd1667ee000b', '/entity/teaching', 'elelctiveManage', '*');
commit;
--prompt 310 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1667ee1232', '��ѧ��ѡ��_*', '402880a91dfd0d0c011dfd1667ee000b', '/entity/teaching', 'peStuElective', '*');
commit;
--prompt 311 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd1704ca1231', '�鿴ѧ����ѡ�γ�_*', '402880a91dfd0d0c011dfd1704ca000c', '/entity/teaching', 'peStuElectiveCourses', '*');
commit;
--prompt 312 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd2047841231', '�鿴ѧ��ƽʱ�ɼ�_*', '402880a91dfd0d0c011dfd204784001a', '/entity/teaching', 'usualScoreView', '*');
commit;
--prompt 313 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd20c02f1231', '�鿴ѧ����ҵ�ɼ�_*', '402880a91dfd0d0c011dfd20c02f001b', '/entity/teaching', 'homeworkScoreView', '*');
commit;
--prompt 314 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd2161af1231', '�鿴ѧ�����Գɼ�_*', '402880a91dfd0d0c011dfd2161af001d', '/entity/teaching', 'examScoreView', '*');
commit;
--prompt 315 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfd0d0c011dfd22e7261231', '��ѯѧ���γ̿��Գɼ�_*', '402880a91dfd0d0c011dfd22e7260020', '/entity/teaching', 'scoreSearch', '*');
commit;
--prompt 316 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff96158f01231', '��ѯ����ѧ�����ʱ��_*', '402880a91dff8c3a011dff96158f0002', '/entity/teaching', 'replyTimeSet', '*');
commit;
--prompt 317 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff98eb9201231', '���ĳɼ��ϳ�_*', '402880a91dff8c3a011dff98eb920006', '/entity/teaching', 'finalScore', '*');
commit;
--prompt 318 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dff8c3a011dff98eb9201232', '��ӡ�ɼ���_*', '402880a91f491fa7011f493aecb60001', '/entity/teaching', 'printScore', '*');
commit;
--prompt 319 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60bb50487', '�鿴��ʷ�̲�_abstractList', '402880a91dfd0d0c011dfd0ebb830001', '/entity/teaching', 'courseBookHis', 'abstractList');
commit;
--prompt 320 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60ca00493', '�鿴��ʦ�绰�б�_abstractList', '100802', '/entity/information', 'teacherPhoneView', 'abstractList');
commit;
--prompt 321 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60cfe0495', '����޸�ѧ��Υ�ͼ�¼_abstractList', '400205', '/entity/studentStatus', 'prStudentOffenceAdd', 'abstractList');
commit;
--prompt 322 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60d0d0497', '�γ̳ɼ���������_abstractList', '402880a91f491fc7011f493aecb60001', 'namespace', 'action', 'abstractList');
commit;
--prompt 323 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60d6b0499', '�˷���ϸ_abstractList', '7003', '/entity/fee', 'feeRefund', 'abstractList');
commit;
--prompt 324 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60d6b049b', '����ѧ�ڿ���_abstractList', '402880a91fcc3c73011fcc47aaec0001', 'namespace', 'action', 'abstractList');
commit;
--prompt 325 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60d7b049d', 'δԤԼ�����б�_abstractList', '402880a91fd61dae011fd61fcdd40001', '/entity/exam', 'finalExamNoBooking', 'abstractList');
commit;
--prompt 326 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60de8049f', 'Ԥ����ͳ��_abstractList', '402880a91fef4fa7011fef5748fa0001', '/entity/studentStatus', 'derateFeeStat', 'abstractList');
commit;
--prompt 327 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60e5504a7', '�Ͽδ�������_abstractList', '40288097202190d3012021c659c40001', '/entity/teaching', 'opencourseTimes', 'abstractList');
commit;
--prompt 328 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60eb304a9', '������¼ȡ_abstractList', '402880a91dfceaba011dfcf91835000b', '/entity/recruit', 'recruitManageNoExam', 'abstractList');
commit;
--prompt 329 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60ec304ab', '�뿼�ɼ�ͳ��_abstractList', '402880a91dfcfaab011dfcfd02090002', 'namespace', 'action', 'abstractList');
commit;
--prompt 330 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60f2004ad', '��Ѳ������_abstractList', '402880a91dff8c3a011dff9d4e6f0009', '/entity/exam', 'peExamPatrol', 'abstractList');
commit;
--prompt 331 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60f2004af', '�����Ѳ��_abstractList', '402880a91dff8c3a011dffa550da000a', '/entity/exam', 'peExamPatrolManage', 'abstractList');
commit;
--prompt 332 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60ffb04b7', '����ʱ������_abstractList', '402880a91dff8c3a011dffb2c7b0001a', '/entity/exam', 'peMainCourseTime', 'abstractList');
commit;
--prompt 333 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60ffb04b9', '��ѧ�ƻ��γ�������Ϣ_abstractList', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'teachPlanInfo', 'abstractList');
commit;
--prompt 334 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6100b04bb', '����ʱ������_abstractList', '402880a91dff8c3a011dffb4a5a8001c', '/entity/exam', 'peExamMaincourseNo', 'abstractList');
commit;
--prompt 335 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6101a04bd', '���Կγ̵���_abstractList', '402880a91dff8c3a011dffb527f7001d', '/entity/exam', 'prExamOpenMaincourse', 'abstractList');
commit;
--prompt 336 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6106904bf', '�Զ����ſ���_abstractList', '402880a91dff8c3a011dffb61c5a001e', 'namespace', 'action', 'abstractList');
commit;
--prompt 337 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e610e604c5', '�鿴ѧ��ѡ��_abstractList', '500503', '/entity/teaching', 'studentNoSelect', 'abstractList');
commit;
--prompt 338 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e610e604c7', '�Ծ�ͳ���б�_abstractList', '402880a91dff8c3a011dffb888780024', '/entity/exam', 'peMainCoursePaperBag', 'abstractList');
commit;
--prompt 339 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e610f504c9', '������Ϣ¼��_abstractList', '402880a91dff8c3a011dffbbe28f0029', '/entity/fee', 'prFeeDetailReduce', 'abstractList');
commit;
--prompt 340 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6110504cb', '������Ϣ¼��_abstractList', '402880a91dff8c3a011dffbd3acc002b', '/entity/fee', 'prFeeDetailReturn', 'abstractList');
commit;
--prompt 341 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6116304cd', '���Ľ�չͳ�ƽ�ʦ_abstractList', '402880a91dff8c3a011dff99ebeb0007', '/entity/teaching', 'paperProcess', 'abstractList');
commit;
--prompt 342 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e611d004d3', '�Զ�����γ�_abstractList', '402880a91dfcd969011dfce598340007', 'namespace', 'action', 'abstractList');
commit;
--prompt 343 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e611d004d5', '��������_abstractList', '402880a91dfceaba011dfcf0fb1e0005', '/entity/recruit', 'peRecRoom', 'abstractList');
commit;
--prompt 344 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e611e004d7', '����������_abstractList', '402880a91dfceaba011dfcf1e1290006', '/entity/recruit', 'examStuRoom', 'abstractList');
commit;
--prompt 345 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e611ef04d9', '�䶯���_abstractList', '402880a91dfcfaab011dfd0242660009', '/entity/studentStatus', 'peChangeType', 'abstractList');
commit;
--prompt 346 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6123d04db', '�䶯רҵ_abstractList', '402880a91dfcfaab011dfd02f961000a', '/entity/studentStatus', 'peChangeMajor', 'abstractList');
commit;
--prompt 347 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e612ab04e1', '��ѧ�����б�_abstractList', '402880a91dfcfaab011dfd05fe06000f', '/entity/studentStatus', 'peStudentExpel', 'abstractList');
commit;
--prompt 348 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e612ab04e3', '������ѧ_abstractList', '402880a91dfcfaab011dfd0688350010', '/entity/studentStatus', 'peStudentStatus', 'abstractList');
commit;
--prompt 349 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e612ba04e5', '���Ա�ҵ����_abstractList', '402880a91dfcfaab011dfd084ab50012', '/entity/studentStatus', 'peCanGraduate', 'abstractList');
commit;
--prompt 350 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e612ca04e7', '��ҵ���_abstractList', '402880a91dfcfaab011dfd08d92a0013', '/entity/studentStatus', 'prApplyGraduate', 'abstractList');
commit;
--prompt 351 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6131804e9', '��ҵѧ���б�_abstractList', '402880a91dfcfaab011dfd09562a0014', '/entity/studentStatus', 'peGraduatedStudent', 'abstractList');
commit;
--prompt 352 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6139504ef', '��ѧ��ѡ��_abstractList', '402880a91dfd0d0c011dfd1667ee000b', 'namespace', 'action', 'abstractList');
commit;
--prompt 353 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6142204f9', '��������_abstractList', '402880a91dfd0d0c011dfd1a56760011', '/entity/teaching', 'batchConfirmCourse', 'abstractList');
commit;
--prompt 354 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6143104fb', '���Ͷ��Ų鿴ѧ��_abstractList', '100802', '/entity/information', 'studentPhoneView', 'abstractList');
commit;
--prompt 355 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6148f04ff', '�ɼ���ѯ_abstractList', '402880a91dfd0d0c011dfd22e7260020', 'namespace', 'action', 'abstractList');
commit;
--prompt 356 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6149f0501', '�ϱ���������_abstractList', '700102', '/entity/fee', 'prFeeDetailForTakeIn', 'abstractList');
commit;
--prompt 357 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0505', '��ҵ�����������_abstractList', '402880a91dff8c3a011dff9a84050079', '/entity/teaching', 'paperReapplyAudit', 'abstractList');
commit;
--prompt 358 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6150c0507', '�޸�����_abstractList', '1004', 'namespace', 'action', 'abstractList');
commit;
--prompt 359 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e615e70513', 'רҵ����_abstractList', '2003', '/entity/basic', 'peMajor', 'abstractList');
commit;
--prompt 360 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e615e70515', '��ι���_abstractList', '2004', '/entity/basic', 'peEdutype', 'abstractList');
commit;
--prompt 361 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e616540519', '�˹�������ò���_abstractList', '7004', '/entity/fee', 'prFeeDetailSpecial', 'abstractList');
commit;
--prompt 362 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e616b2051f', '��˶���_abstractList', '100803', '/entity/information', 'peSmsCheck', 'abstractList');
commit;
--prompt 363 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e616c20521', '�����б�_abstractList', '100804', '/entity/information', 'peSms', 'abstractList');
commit;
--prompt 364 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6171f0525', '�����������ι���_abstractList', '300101', '/entity/recruit', 'peRecruitplan', 'abstractList');
commit;
--prompt 365 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6172f0527', 'ѧ�ֱ�׼����_abstractList', '300104', '/entity/recruit', 'setCreditA', 'abstractList');
commit;
--prompt 366 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e617fa052f', '�����ʸ����_abstractList', '300204', '/entity/recruit', 'peRecStudentNoexamCheck', 'abstractList');
commit;
--prompt 367 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e618680535', '������Ϣ��ѯͳ��_abstractList', '300501', 'namespace', 'action', 'abstractList');
commit;
--prompt 368 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e618d5053b', 'ѧ����Ϣ��ѯ_abstractList', '400201', '/entity/studentStatus', 'peStudentInfo', 'abstractList');
commit;
--prompt 369 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e619420541', '���Ź���_abstractList', '400204', '/entity/studentStatus', 'prStudentGoodApply', 'abstractList');
commit;
--prompt 370 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e619b00547', '��������ѧλ����_abstractList', '400501', '/entity/studentStatus', 'peCanApplyDegree', 'abstractList');
commit;
--prompt 371 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61a1d054d', 'ѧ���ۺ���Ϣ��ѯ_abstractList', '400601', '/entity/studentStatus', 'peStudentStudyCount', 'abstractList');
commit;
--prompt 372 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61a8b0553', 'ѡ��ʱ������_abstractList', '500201', '/entity/teaching', 'electiveTimeManage', 'abstractList');
commit;
--prompt 373 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61af80559', '��ҵ����ʱ������_abstractList', '500501', '/entity/teaching', 'paperTime', 'abstractList');
commit;
--prompt 374 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61b65055f', '��ĩ����ʱ������_abstractList', '600201', '/entity/exam', 'peFinalExam', 'abstractList');
commit;
--prompt 375 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61b750561', '�ɼ�¼��_abstractList', '600303', '/entity/exam', 'maincourseScoreManage', 'abstractList');
commit;
--prompt 376 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61b750563', '����¼��_abstractList', '30020102', '/entity/recruit', 'addPeRecStudent', 'abstractList');
commit;
--prompt 377 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61bd30567', '���˱�׼����_abstractList', '700502', '/entity/fee', 'listStudentForFeeSet', 'abstractList');
commit;
--prompt 378 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61be20569', '�������β�ѯ_abstractList', '700601', '/entity/fee', 'peFeeBathQuery', 'abstractList');
commit;
--prompt 379 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61c50056d', '����¼��ѧ��_abstractList', '30020101', '/entity/recruit', 'addPeRecStudent', 'abstractList');
commit;
--prompt 380 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61c50056f', '���Կγ�����_abstractList', '402880a91dfcd969011dfce10b580003', '/entity/recruit', 'examcourseset', 'abstractList');
commit;
--prompt 381 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61d1b0577', '�࿼��ѯ_abstractList', '402880a91dfcd969011dfce7c4b8000a', '/entity/recruit', 'examinvigilator', 'abstractList');
commit;
--prompt 382 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61d88057d', 'ͳ��¼ȡ����_abstractList', '402880a91dfceaba011dfcf729070009', 'namespace', 'action', 'abstractList');
commit;
--prompt 383 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61df60583', '��¼ȡ�б�_abstractList', '402880a91dfcfaab011dfcfe0b3c0003', '/entity/studentStatus', 'prRecPriPay', 'abstractList');
commit;
--prompt 384 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61e630589', '�γ���Ϣ�б�_abstractList', '402880a91dfd0d0c011dfd0ebb830001', '/entity/teaching', 'peCourseManager', 'abstractList');
commit;
--prompt 385 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61e73058b', '��ѧ�ƻ��б�_abstractList', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'peTeaPlan', 'abstractList');
commit;
--prompt 386 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61e73058d', '�����ѧ�ƻ�_abstractList', '402880a91dfd0d0c011dfd10c7320004', '/entity/teaching', 'peTeaPlan', 'abstractList');
commit;
--prompt 387 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61ee00591', '�����ʦ����_abstractList', '402880a91dfd0d0c011dfd123ece0006', 'namespace', 'action', 'abstractList');
commit;
--prompt 388 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61ee00593', '�̲���Ϣ�б�_abstractList', '402880a91dfd0d0c011dfd1303e30007', '/entity/teaching', 'teachingMaterialsManager', 'abstractList');
commit;
--prompt 389 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61f4d0597', '�μ��б�_abstractList', '402880a91dfd0d0c011dfd14be740009', '/entity/teaching', 'courseware', 'abstractList');
commit;
--prompt 390 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61f4d0599', '����μ�����_abstractList', '402880a91dfd0d0c011dfd1578ea000a', 'namespace', 'action', 'abstractList');
commit;
--prompt 391 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61fbb059d', '�γ̱���Ϣ_abstractList', '402880a91dfd0d0c011dfd1d1e210016', '/entity/teaching', 'syllabus', 'abstractList');
commit;
--prompt 392 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61fbb059f', '����γ̱�_abstractList', '402880a91dfd0d0c011dfd1d98a10017', '/entity/teaching', 'syllabus', 'abstractList');
commit;
--prompt 393 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6208605a7', '���Գɼ��鿴_abstractList', '402880a91dfd0d0c011dfd2161af001d', 'namespace', 'action', 'abstractList');
commit;
--prompt 394 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e620f305ad', '��������ɼ�_abstractList', '402880a91dfd0d0c011dfd247a590022', 'namespace', 'action', 'abstractList');
commit;
--prompt 395 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e621ce05b5', '���ʱ������_abstractList', '402880a91dff8c3a011dff96158f0002', 'namespace', 'action', 'abstractList');
commit;
--prompt 396 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e622a905bd', '�鿴վ�����Ա_abstractList', '2001', '/entity/basic', 'peSiteManager', 'abstractList');
commit;
--prompt 397 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6231605c3', 'Ԥ���Ѽ���_abstractList', '4028808c1e01beb1011e01c792b50001', '/entity/studentStatus', 'prRecPriPayApply', 'abstractList');
commit;
--prompt 398 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6238405c9', '�½�����_abstractList', '100203', '/entity/information', 'peDocumentOutbox', 'abstractList');
commit;
--prompt 399 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e623f105cf', 'ԤԼ����_abstractList', '402880911e106277011e10951f290002', '/entity/exam', 'maincourseExamBooking', 'abstractList');
commit;
--prompt 400 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6245e05d5', '�ֶ�����_abstractList', '402880a91dfd0d0c011dfd19aa960010', '/entity/teaching', 'manualConfirmCourse', 'abstractList');
commit;
--prompt 401 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e624cc05db', 'ϵͳ���ŵ����_abstractList', '100801', '/entity/information', 'peSystemSmsPoint', 'abstractList');
commit;
--prompt 402 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e624db05dd', '�����ƻ�����_abstractList', '300102', '/entity/recruit', 'prRecPlanMajorEdutype', 'abstractList');
commit;
--prompt 403 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e624eb05df', '������Ϣ���_abstractList', '300202', '/entity/recruit', 'peRecStudentCheck', 'abstractList');
commit;
--prompt 404 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6254905e3', '�ɼ�����¼��_abstractList', '402880a91dff8c3a011dffc53fb7002f', 'namespace', 'action', 'abstractList');
commit;
--prompt 405 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6255805e5', '���֪ͨ_abstractList', '402880a91dff8c3a011dff96c3630003', 'namespace', 'action', 'abstractList');
commit;
--prompt 406 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e625b605e9', '���ɼ�¼��_abstractList', '402880a91dff8c3a011dff985e950005', '/entity/teaching', 'replyScoreSearch', 'abstractList');
commit;
--prompt 407 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e625c605eb', '���ĳɼ��ϳ�_abstractList', '402880a91dff8c3a011dff98eb920006', 'namespace', 'action', 'abstractList');
commit;
--prompt 408 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6262305ef', 'ԤԼ��ѯɾ��_abstractList', '402880a91dff8c3a011dffa6e3be000c', '/entity/exam', 'peFinalExamApply', 'abstractList');
commit;
--prompt 409 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6263305f1', 'ԤԼͳ��_abstractList', '402880a91dff8c3a011dffa7636e000d', 'namespace', 'action', 'abstractList');
commit;
--prompt 410 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6269105f5', '�Զ����ſ���_abstractList', '402880a91dff8c3a011dffa8ca51000f', 'namespace', 'action', 'abstractList');
commit;
--prompt 411 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e626a005f7', '�������ŵ���_abstractList', '402880a91dff8c3a011dffaa107d0011', '/entity/exam', 'prExamBooking', 'abstractList');
commit;
--prompt 412 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e626fe05fb', '�Ծ��ɷ���_abstractList', '402880a91dff8c3a011dffab52630013', '/entity/exam', 'prExamPaperCount', 'abstractList');
commit;
--prompt 413 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6270e05fd', 'ƽʱ�ɼ�����_abstractList', '402880a91dff8c3a011dffaec3200017', '/entity/exam', 'normalScoreReCheck', 'abstractList');
commit;
--prompt 414 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6276c0601', '������Ϣ¼��_abstractList', '402880a91dff8c3a011dffba48290026', '/entity/fee', 'prFeeDetailIn', 'abstractList');
commit;
--prompt 415 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6277b0603', '������Ϣ¼��_abstractList', '402880a91dff8c3a011dffbad18d0027', '/entity/fee', 'prFeeDetailIn', 'abstractList');
commit;
--prompt 416 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e627d90607', '�ɼ�����_abstractList', '600304', '/entity/exam', 'peMainCourseScoreRecheck', 'abstractList');
commit;
--prompt 417 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e627e90609', '���רҵ����_abstractList', '402880a91dfcd969011dfce22f470004', '/entity/recruit', 'eduTypeMajorExamSet', 'abstractList');
commit;
--prompt 418 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62846060d', '�����ϴ�_abstractList', '402880a91dfcd969011dfcdf76120002', '/entity/recruit', 'recStudentPhoto', 'abstractList');
commit;
--prompt 419 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62856060f', '���Գɼ�����_abstractList', '402880a91dfceaba011dfcf678d20008', '/entity/recruit', 'examachievement', 'abstractList');
commit;
--prompt 420 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e628b40613', '�ѿ�������_abstractList', '402880a91dfd0d0c011dfd1c395f0015', '/entity/teaching', 'confirmedCourseInfo', 'abstractList');
commit;
--prompt 421 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e629110617', '��ע���б�_abstractList', '402880a91dfcfaab011dfcff8c7d0005', '/entity/studentStatus', 'peRegisterStudent', 'abstractList');
commit;
--prompt 422 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6297f061d', '��������_abstractList', '1003', '/entity/information', 'personalInfo', 'abstractList');
commit;
--prompt 423 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e629ec0623', '��ҵ���ι���_abstractList', '400401', '/entity/studentStatus', 'peGraduate', 'abstractList');
commit;
--prompt 424 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62a5a0629', 'ѧ�������˻���ѯ_abstractList', '700603', '/entity/fee', 'studentAccount', 'abstractList');
commit;
--prompt 425 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62ac7062f', '������Ϣ��ѯͳ�ƽ��_abstractList', '300501', '/entity/recruit', 'baomingStat', 'abstractList');
commit;
--prompt 426 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62b340635', '�ڼ�ѧ��ͳ�ƽ��_abstractList', '400602', '/entity/studentStatus', 'prStudentOnStatus', 'abstractList');
commit;
--prompt 427 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62ba2063b', '�������β�ѯѧ��������ϸ_abstractList', '700601', '/entity/fee', 'peFeePiCi', 'abstractList');
commit;
--prompt 428 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c0f0641', '�鿴��������_abstractList', '800702', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 429 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c7d0647', 'ѧ�ڿ��β���_abstractList', '402880a91dfd0d0c011dfd19aa960012', 'namespace', 'action', 'abstractList');
commit;
--prompt 430 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c8c0649', '�����б�_abstractList', '100602', '/entity/information', 'peBulletin', 'abstractList');
commit;
--prompt 431 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62c9c064b', '����ͳ��_abstractList', '402880a91dfceaba011dfcf031460004', '/entity/recruit', 'examfenpeistat', 'abstractList');
commit;
--prompt 432 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62dc50655', 'ͳ�Ʋ�ѯ3_abstractList', '800503', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 433 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62dd40657', 'ͳ�Ʋ�ѯ2_abstractList', '800502', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 434 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62e42065b', '������Ϣ����_abstractList', '8004', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 435 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62e51065d', 'ѡ����Ϣ����_abstractList', '800303', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 436 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62eaf0661', '�γ̹���_abstractList', '8002', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 437 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62ebf0663', '���¹���_abstractList', '8001', '/entity/publicCourse', 'action', 'abstractList');
commit;
--prompt 438 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62f1c0667', '��ҵ�ɼ�����_abstractList', '402880a91dff8c3a011dffaf4e490018', '/entity/exam', 'workScoreReCheck', 'abstractList');
commit;
--prompt 439 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62f2c0669', '���Գɼ�����_abstractList', '402880a91dff8c3a011dffaff18e0019', '/entity/exam', 'examScoreReCheck', 'abstractList');
commit;
--prompt 440 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62f8a066d', '���ô�����_abstractList', '402880a91fa7931b011fa79eee9c0001', '/entity/teaching', 'peTchRejoinRoom', 'abstractList');
commit;
--prompt 441 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62f99066f', '��ĩ���Դ�ӡ_abstractList', '402880a91dff8c3a011dffab52630013', '/entity/exam', 'finalExamPrint', 'abstractList');
commit;
--prompt 442 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62ff70673', '�Ծ�ͳ���б�_abstractList', '402880a91dff8c3a011dffb888780024', '/entity/exam', 'maincourseStat', 'abstractList');
commit;
--prompt 443 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e630070675', '�Ծ�ǩ���_abstractList', '4028808c1fca20a9011fca27bd9f0001', 'namespace', 'action', 'abstractList');
commit;
--prompt 444 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e630650679', '��ʦ�����б�_abstractList', '402880a9201323450120132590ba0001', '/entity/fee', 'teacherReduceDetail', 'abstractList');
commit;
--prompt 445 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e63074067b', '�ʾ���_abstractList', '402880a91e5d1ff2011e5dee91a00003', '/entity/information', 'prVoteSuggest', 'abstractList');
commit;
--prompt 446 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e630d2067f', '�γ��⿼�������_abstractList', '402880a91e874bce011e875d138f0005', '/entity/exam', 'examAvoidApply', 'abstractList');
commit;
--prompt 447 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e630e20681', '�����ʾ��б�_abstractList', '402880a91e5d1ff2011e5dee91a00003', '/entity/information', 'peVotePaper', 'abstractList');
commit;
--prompt 448 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6313f0685', 'ͳ�������_abstractList', '402880a91e874bce011e875d138f0004', '/entity/exam', 'computerAvoidApply', 'abstractList');
commit;
--prompt 449 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6314f0687', 'ͳ��Ӣ��B_abstractList', '402880a91e874bce011e875caae30003', '/entity/exam', 'englishBAvoidApply', 'abstractList');
commit;
--prompt 450 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6320b068f', '�����Ƿ�����_abstractList', '4028809720375d4e01203486774d0018', '/entity/exam', 'examscoreexportinputdata', 'abstractList');
commit;
--prompt 451 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e632780695', '�ɼ���ѯ�޸�_abstractList', '4028809720375d4e01203486774d007', '/entity/exam', 'examscoreinput', 'abstractList');
commit;
--prompt 452 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e632e5069b', '�鿴�γ�������Ϣ_abstractList', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'teachPlanInfo', 'abstractList');
commit;
--prompt 453 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6335306a1', '�鿴��ʦ�γ�_abstractList', '500104', '/entity/teaching', 'teacherCourse', 'abstractList');
commit;
--prompt 454 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e633c006a7', '�鿴ѧ����ѡ�γ�_abstractList', '402880a91dfd0d0c011dfd1704ca000c', '/entity/teaching', 'peStuElectiveCourses', 'abstractList');
commit;
--prompt 455 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e633d006a9', '�鿴ѧ��ƽʱ�ɼ�_abstractList', '402880a91dfd0d0c011dfd204784001a', '/entity/teaching', 'usualScoreView', 'abstractList');
commit;
--prompt 456 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e633df06ab', '�鿴ѧ����ҵ�ɼ�_abstractList', '402880a91dfd0d0c011dfd20c02f001b', '/entity/teaching', 'homeworkScoreView', 'abstractList');
commit;
--prompt 457 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6342d06ad', '�鿴ѧ�����Գɼ�_abstractList', '402880a91dfd0d0c011dfd2161af001d', '/entity/teaching', 'examScoreView', 'abstractList');
commit;
--prompt 458 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6344d06af', '��ѯѧ���γ̿��Գɼ�_abstractList', '402880a91dfd0d0c011dfd22e7260020', '/entity/teaching', 'scoreSearch', 'abstractList');
commit;
--prompt 459 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6345c06b1', '��ѯ����ѧ�����ʱ��_abstractList', '402880a91dff8c3a011dff96158f0002', '/entity/teaching', 'replyTimeSet', 'abstractList');
commit;
--prompt 460 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6349b06b3', '���ĳɼ��ϳ�_abstractList', '402880a91dff8c3a011dff98eb920006', '/entity/teaching', 'finalScore', 'abstractList');
commit;
--prompt 461 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e634aa06b5', '��ӡ�ɼ���_abstractList', '402880a91f491fa7011f493aecb60001', '/entity/teaching', 'printScore', 'abstractList');
commit;
--prompt 462 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028809720375d4e01203768474d7894', '�γ���ҵ����_*', '4028809720375d4e01203768474d7894', '/entity/teaching', 'courseHomework', '*');
commit;
--prompt 463 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfceaba011dfcf87b1a043a', '��������¼ȡ_*', '402880a91dfceaba011dfcf87b1a043a', '/entity/recruit', 'recruitManageExam', '*');
commit;
--prompt 464 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a9213d9b9601213dec3c5000ea', '����Ա����_*', '402880a9213d9b9601213dec3c5000ea', '/entity/basic', 'siteManager', '*');
commit;
--prompt 465 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60adb047b', '�鿴ѡ��δѡ��_abstractList', '4028809720375d4e01203768474d0018', '/entity/teaching', 'StudentNoSel', 'abstractList');
commit;
--prompt 466 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60aea047d', '�ɼ��������_abstractList', '600204', 'namespace', 'action', 'abstractList');
commit;
--prompt 467 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60afa047f', '�Ծ��ͳ��_abstractList', '402880a920416fe901204171bdb50001', '/entity/exam', 'examPaperStat', 'abstractList');
commit;
--prompt 468 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60b480481', '���Է��ù���_abstractList', '402880a920416fe90120418f09480006', '/entity/fee', 'recExamFee', 'abstractList');
commit;
--prompt 469 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60b580483', '�鿴��ʷ�μ�_abstractList', '402880a91dfd0d0c011dfd0ebb830001', '/entity/teaching', 'courseCoursewareHis', 'abstractList');
commit;
--prompt 470 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60bb50485', '���ÿγ̽�ʦ_abstractList', '402880a91dfd0d0c011dfd0ebb830001', '/entity/teaching', 'prTchCourseTeacher', 'abstractList');
commit;
--prompt 471 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60bc50489', 'վ����·����_abstractList', '402880a9206a116001206aaeecee0014', '/entity/exam', 'peSiteLine', 'abstractList');
commit;
--prompt 472 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60c23048b', '���������ӡ_abstractList', '402880a9207e282001207e6a3837000e', '/entity/studentStatus', 'studentGoodApplyPrint', 'abstractList');
commit;
--prompt 473 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60c32048d', '������Ľ�ʦ_abstractList', '40288097201892ad012018b233390001', '/entity/teaching', 'paperTeacherAdd', 'abstractList');
commit;
--prompt 474 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60c90048f', '�鿴��վ����Ա�绰�б�_abstractList', '100802', '/entity/information', 'siteManagerPhoneView', 'abstractList');
commit;
--prompt 475 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60c900491', '�鿴����Ա�绰�б�_abstractList', '100802', '/entity/information', 'managerPhoneView', 'abstractList');
commit;
--prompt 476 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60df804a1', '��ʦ�������_abstractList', '402880a9201209b50120125b1d27001c', '/entity/fee', 'teacherReduce', 'abstractList');
commit;
--prompt 477 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60e4604a3', '���ѧ���䶯_abstractList', '400302', '/entity/studentStatus', 'studentStatus', 'abstractList');
commit;
--prompt 478 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60e4604a5', '���Ľ�ʦ����_abstractList', '40288097201892ad012018b233390001', '/entity/teaching', 'paperTeacher', 'abstractList');
commit;
--prompt 479 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60f3004b1', 'ԤԼ����_abstractList', '402880a91dff8c3a011dffa64f1f000b', '/entity/exam', 'examBooking', 'abstractList');
commit;
--prompt 480 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60f8e04b3', '��������_abstractList', '402880a91dff8c3a011dffa95aaa0010', '/entity/exam', 'peExamRoom', 'abstractList');
commit;
--prompt 481 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e60f9d04b5', '��ѧ�ƻ����пγ�_abstractList', '402880a91dfd0d0c011dfd1055f90003', '/entity/teaching', 'prTchProgramCourse', 'abstractList');
commit;
--prompt 482 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6107804c1', '������Ϣ����_abstractList', '402880a91dff8c3a011dffb6c5b90020', '/entity/exam', 'peExamMaincourseRoom', 'abstractList');
commit;
--prompt 483 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6107804c3', '����������_abstractList', '402880a91dff8c3a011dffb75b040022', '/entity/exam', 'peMainCourseExamRoom', 'abstractList');
commit;
--prompt 484 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6117204cf', '�����ϴ�_abstractList', '402880a91dfcd969011dfcde6d0f0001', '/entity/recruit', 'recStudentPhoto', 'abstractList');
commit;
--prompt 485 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6117204d1', '�����γ���ϸ_abstractList', '402880a91dfcd969011dfce4bc990006', '/entity/recruit', 'examstucourse', 'abstractList');
commit;
--prompt 486 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6124d04dd', '�䶯ѧϰ����_abstractList', '402880a91dfcfaab011dfd03f9e9000c', '/entity/studentStatus', 'peChangeSite', 'abstractList');
commit;
--prompt 487 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6124d04df', '�䶯���_abstractList', '402880a91dfcfaab011dfd048512000d', '/entity/studentStatus', 'peChangeType', 'abstractList');
commit;
--prompt 488 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6132804eb', '�걨����_abstractList', '402880a91dfcfaab011dfd0a67d90015', '/entity/studentStatus', 'peYearReport', 'abstractList');
commit;
--prompt 489 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6132804ed', '��ҵ֤��ӡ_abstractList', '402880a91dfcfaab011dfd0aeaa50016', 'namespace', 'action', 'abstractList');
commit;
--prompt 490 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e613a504f1', 'ѧ����ѡ��_abstractList', '402880a91dfd0d0c011dfd1704ca000c', '/entity/teaching', 'electiveInfoManage', 'abstractList');
commit;
--prompt 491 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e613a504f3', 'ѡ��ͳ��_abstractList', '402880a91dfd0d0c011dfd17868d000d', '/entity/teaching', 'electiveStat', 'abstractList');
commit;
--prompt 492 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e613b404f5', 'δѡ��ѧ��_abstractList', '402880a91dfd0d0c011dfd182482000e', '/entity/teaching', 'unElectiveStuList', 'abstractList');
commit;
--prompt 493 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6141204f7', '�̲���������_abstractList', '402880a91dfd0d0c011dfd18b5d5000f', '/entity/teaching', 'courseOrderStat', 'abstractList');
commit;
--prompt 494 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6148f04fd', '�ɼ��ϳ�_abstractList', '402880a91dfd0d0c011dfd226b6e001f', 'namespace', 'action', 'abstractList');
commit;
--prompt 495 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e614fd0503', '֪ͨ����_abstractList', '1001', '/entity/information', 'peBulletinView', 'abstractList');
commit;
--prompt 496 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6156a0509', '���Ź���_abstractList', '1005', '/entity/information', 'peInfoNews', 'abstractList');
commit;
--prompt 497 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6156a050b', '���Ĺ���_abstractList', '1007', '/entity/information', 'peDocument', 'abstractList');
commit;
--prompt 498 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6157a050d', '��̳����_abstractList', '1009', '/sso/forum', 'forum', 'abstractList');
commit;
--prompt 499 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61589050f', 'ѧϰ���Ĺ���_abstractList', '2001', '/entity/basic', 'peSite', 'abstractList');
commit;
--prompt 500 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e615d70511', '�����б�鿴���Ͷ���_abstractList', '100804', '/entity/information', 'prSmsSendStatus', 'abstractList');
commit;
--prompt 501 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e616450517', '�꼶����_abstractList', '2005', '/entity/basic', 'peGrade', 'abstractList');
commit;
--prompt 502 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e616b2051d', '���Ͷ���_abstractList', '100802', '/entity/information', 'studentPhoneView', 'abstractList');
commit;
--prompt 503 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6171f0523', '����ͳ��_abstractList', '100805', '/entity/information', 'peSmsStatistic', 'abstractList');
commit;
--prompt 504 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6178d0529', '������Ϣ��ѯ_abstractList', '300200', '/entity/recruit', 'recruitStu', 'abstractList');
commit;
--prompt 505 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6178d052b', '�������ɿο��Կγ�_abstractList', '402880a91dff8c3a011dffb4a5a8001c', '/entity/exam', 'examMaincourseSet', 'abstractList');
commit;
--prompt 506 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6179c052d', '���뷢Ʊ��_abstractList', '402880a92011efae012011fc12d40001', '/entity/fee', 'peFeeDetailForReciept', 'abstractList');
commit;
--prompt 507 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6180a0531', '¼ȡ״̬�޸�_abstractList', '300403', '/entity/recruit', 'recruitManage', 'abstractList');
commit;
--prompt 508 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6180a0533', '¼ȡ֪ͨ���ӡ_abstractList', '300404', '/entity/recruit', 'recruitNotification', 'abstractList');
commit;
--prompt 509 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e618680537', '¼ȡ״̬����������ѯͳ��_abstractList', '300502', 'namespace', 'action', 'abstractList');
commit;
--prompt 510 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e618770539', '���Բ�ѯͳ��_abstractList', '300503', '/entity/recruit', 'recruitNoExamStat', 'abstractList');
commit;
--prompt 511 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e618d5053d', 'ԤԼ����B_abstractList', '402880911e106277011e10951f290002', '/entity/exam', 'maincourseElectivecourse', 'abstractList');
commit;
--prompt 512 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e618e5053f', 'ģ���½_abstractList', '400203', '/entity/studentStatus', 'simulateStudentLogin', 'abstractList');
commit;
--prompt 513 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e619420543', 'Υ�͹���_abstractList', '400205', '/entity/studentStatus', 'prStudentOffence', 'abstractList');
commit;
--prompt 514 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e619520545', '����ͼ��У��_abstractList', '400404', '/entity/studentStatus', 'peStudentPhoto', 'abstractList');
commit;
--prompt 515 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e619bf0549', 'ѧλ���_abstractList', '400502', '/entity/studentStatus', 'peApplyDegree', 'abstractList');
commit;
--prompt 516 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e619bf054b', 'ѧλ����_abstractList', '400503', '/entity/studentStatus', 'peStudentDegree', 'abstractList');
commit;
--prompt 517 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61a1d054f', '�ڼ�ѧ��ͳ��_abstractList', '400602', 'namespace', 'action', 'abstractList');
commit;
--prompt 518 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61a2d0551', '��ҵѧλͳ��_abstractList', '400603', '/entity/studentStatus', 'prStudentDegree', 'abstractList');
commit;
--prompt 519 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61a8b0555', '��ʦ��ѧͳ��_abstractList', '500302', 'namespace', 'action', 'abstractList');
commit;
--prompt 520 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61a9a0557', '����������ҵ��_abstractList', '500305', '/entity/teaching', 'countcheckofworkfee', 'abstractList');
commit;
--prompt 521 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61b08055b', '�鿴������Ŀ_abstractList', '500502', '/entity/teaching', 'teacherTopic', 'abstractList');
commit;
--prompt 522 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61b08055d', '��������ͳ��_abstractList', '500506', '/entity/teaching', 'paperShenqingStat', 'abstractList');
commit;
--prompt 523 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61bd30565', '�շѱ�׼����_abstractList', '700501', '/entity/fee', 'feeStandardManager', 'abstractList');
commit;
--prompt 524 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61c40056b', '��������ͳ��_abstractList', '700602', '/entity/fee', 'feeTotalCount', 'abstractList');
commit;
--prompt 525 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61cad0571', '����ʱ������_abstractList', '402880a91dfcd969011dfce314960005', '/entity/recruit', 'eduExamTimeSet', 'abstractList');
commit;
--prompt 526 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61cbd0573', '�Զ����俼��_abstractList', '402880a91dfcd969011dfce6673c0008', 'namespace', 'action', 'abstractList');
commit;
--prompt 527 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61cbd0575', '��ӡ׼��֤_abstractList', '402880a91dfcd969011dfce724900009', '/entity/recruit', 'examstuticket', 'abstractList');
commit;
--prompt 528 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61d2a0579', 'Ѳ����ѯ_abstractList', '402880a91dfcd969011dfce8c32d000b', '/entity/recruit', 'examInspector', 'abstractList');
commit;
--prompt 529 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61d2a057b', '��ѧ���Գɼ�_abstractList', '402880a91dfceaba011dfcf5c37c0007', '/entity/recruit', 'examachievement', 'abstractList');
commit;
--prompt 530 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61d98057f', '������¼ȡ_abstractList', '402880a91dfceaba011dfcf87b1a000a', 'namespace', 'action', 'abstractList');
commit;
--prompt 531 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61d980581', '�뿼�ɼ���ѯ_abstractList', '402880a91dfcfaab011dfcfc63680001', '/entity/recruit', 'examScoreSearch', 'abstractList');
commit;
--prompt 532 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61e050585', '��������_abstractList', '402880a91dfcfaab011dfcfea7ca0004', '/entity/studentStatus', 'derateFeeApply', 'abstractList');
commit;
--prompt 533 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61e050587', '�䶯ѧϰ����_abstractList', '402880a91dfcfaab011dfd01afda0008', '/entity/studentStatus', 'peChangeSite', 'abstractList');
commit;
--prompt 534 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61ed0058f', '��ʦ�б�_abstractList', '402880a91dfd0d0c011dfd11aa6e0005', '/entity/teaching', 'teacher', 'abstractList');
commit;
--prompt 535 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61f3e0595', '������ӽ̲�_abstractList', '402880a91dfd0d0c011dfd13ac290008', '/entity/teaching', 'teachingMaterialsManager', 'abstractList');
commit;
--prompt 536 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e61fab059b', '����ͳ��_abstractList', '402880a91dfd0d0c011dfd1aec8c0012', '/entity/teaching', 'confirmedCourseStat', 'abstractList');
commit;
--prompt 537 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6201805a1', 'ϵ������_abstractList', '402880a91dfd0d0c011dfd1f2fac0019', '/entity/teaching', 'countcoursecoefficient', 'abstractList');
commit;
--prompt 538 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6202805a3', 'ƽʱ�ɼ�����_abstractList', '402880a91dfd0d0c011dfd204784001a', 'namespace', 'action', 'abstractList');
commit;
--prompt 539 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6202805a5', '�鿴��ҵ�ɼ�_abstractList', '402880a91dfd0d0c011dfd20c02f001b', 'namespace', 'action', 'abstractList');
commit;
--prompt 540 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6209505a9', '�ɼ��ϳ�����_abstractList', '402880a91dfd0d0c011dfd21f4e6001e', '/entity/teaching', 'scorePercentSet', 'abstractList');
commit;
--prompt 541 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6209505ab', 'ͳ���ɼ�_abstractList', '402880a91dfd0d0c011dfd23e3f50021', '/entity/teaching', 'unitExamScore', 'abstractList');
commit;
--prompt 542 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6210305af', '��������_abstractList', '402880a91dfd0d0c011dfd25f61b0024', 'namespace', 'action', 'abstractList');
commit;
--prompt 543 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6211205b1', '��ȡ�������_abstractList', '402880a91dff8761011dff8af7ac0001', '/entity/teaching', 'replyListSelect', 'abstractList');
commit;
--prompt 544 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6216105b3', '�鿴�������_abstractList', '402880a91dff8c3a011dff958b500001', '/entity/teaching', 'replyListView', 'abstractList');
commit;
--prompt 545 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6222c05b7', '���ĳɼ�ͳ��_abstractList', '402880a91dff8c3a011dff9a84050008', '/entity/teaching', 'paperScoreStat', 'abstractList');
commit;
--prompt 546 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6224b05b9', 'ѧλӢ���������_abstractList', '402880a91dfd0d0c011dfd25f61b0064', '/entity/teaching', 'degreeEnglishAudit', 'abstractList');
commit;
--prompt 547 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6225b05bb', 'ѧ���䶯����_abstractList', '400300', '/entity/studentStatus', 'peChangeApply', 'abstractList');
commit;
--prompt 548 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e622b805bf', '���������ƻ�վ��_abstractList', '300102', '/entity/recruit', 'prExistSite', 'abstractList');
commit;
--prompt 549 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e622b805c1', 'Ϊ��ѡ��ѧϰ��������ѧ�ֱ�׼_abstractList', '300104', '/entity/recruit', 'setCreditB', 'abstractList');
commit;
--prompt 550 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6232605c5', '�����б�_abstractList', '4028826a1e195595011e1984bbb30002', '/entity/fee', 'prFeeDetailReduce', 'abstractList');
commit;
--prompt 551 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6233505c7', '���Ƴɼ��ϳ�_abstractList', '402880a91dfd0d0c011dfd22e7260021', 'namespace', 'action', 'abstractList');
commit;
--prompt 552 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6239305cb', '������_abstractList', '100202', '/entity/information', 'peDocumentOutbox', 'abstractList');
commit;
--prompt 553 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e623a305cd', '�ռ���_abstractList', '100201', '/entity/information', 'peDocumentView', 'abstractList');
commit;
--prompt 554 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6240105d1', 'ԤԼ��ѯɾ��_abstractList', '402880911e106277011e10adce210002', '/entity/exam', 'maincourseElectiveManage', 'abstractList');
commit;
--prompt 555 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6241005d3', '����ѧ��_abstractList', '402880a91dfcfaab011dfd0716d90011', '/entity/studentStatus', 'peStudentStatus', 'abstractList');
commit;
--prompt 556 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6246e05d7', '��ӹ���_abstractList', '100601', '/entity/information', 'peBulletin', 'abstractList');
commit;
--prompt 557 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6247e05d9', 'ѧ�ڹ���_abstractList', '2006', '/entity/basic', 'peSemester', 'abstractList');
commit;
--prompt 558 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6253905e1', '�ɼ�¼��_abstractList', '402880a91dff8c3a011dffc4bd0a002e', '/entity/exam', 'examScore', 'abstractList');
commit;
--prompt 559 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e625a605e7', '���ĳɼ��鿴_abstractList', '402880a91dff8c3a011dff97d5fc0004', '/entity/teaching', 'paperScoreSearch', 'abstractList');
commit;
--prompt 560 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6261405ed', '���Ľ�չͳ��_abstractList', '402880a91dff8c3a011dff99ebeb0007', '/entity/teaching', 'paperProcess', 'abstractList');
commit;
--prompt 561 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6268105f3', '���Գ��ι���_abstractList', '402880a91dff8c3a011dffa843db000e', '/entity/exam', 'peExamno', 'abstractList');
commit;
--prompt 562 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e626ef05f9', '�������Ų�ѯ_abstractList', '402880a91dff8c3a011dffaabae60012', '/entity/exam', 'examInfoSearch', 'abstractList');
commit;
--prompt 563 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6275c05ff', '������Ϣ_abstractList', '402880a91dff8c3a011dffb989ab0025', '/entity/fee', 'prFeeDetailIn', 'abstractList');
commit;
--prompt 564 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e627c90605', '�鿴ѧ��ѡ��_abstractList', '500503', '/entity/teaching', 'studentTopic', 'abstractList');
commit;
--prompt 565 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62837060b', 'ѧ�����з�����ϸ_abstractList', '700604', '/entity/fee', 'prFeeDetailByStudent', 'abstractList');
commit;
--prompt 566 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e628a40611', '������ӿγ�_abstractList', '402880a91dfd0d0c011dfd0f3f980002', '/entity/teaching', 'peCourseManager', 'abstractList');
commit;
--prompt 567 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e628c30615', 'ѧλӢ��ɼ�_abstractList', '402880a91dfd0d0c011dfd2568df0023', '/entity/teaching', 'degreeEnglishScore', 'abstractList');
commit;
--prompt 568 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e629210619', '������Ϣ����_abstractList', '402880a91dff8c3a011dffbc7634002a', '/entity/fee', 'prFeeDetailReduce', 'abstractList');
commit;
--prompt 569 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62931061b', '�䶯רҵ_abstractList', '402880a91dfcfaab011dfd050cc1000e', '/entity/studentStatus', 'peChangeMajor', 'abstractList');
commit;
--prompt 570 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6298f061f', '��������_abstractList', '300103', '/entity/recruit', 'recruitJianzhang', 'abstractList');
commit;
--prompt 571 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6299e0621', '��ʦ�ʸ����_abstractList', '300203', '/entity/recruit', 'peRecStudentTeacherCheck', 'abstractList');
commit;
--prompt 572 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e629fc0625', '��ҵͳ��_abstractList', '500303', '/entity/teaching', 'homeWorkStat', 'abstractList');
commit;
--prompt 573 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62a0c0627', '�����������_abstractList', '700103', '/entity/fee', 'peFeeBathCheck', 'abstractList');
commit;
--prompt 574 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62a69062b', 'ͳ��¼ȡ��������¼ȡ������_abstractList', '402880a91dfceaba011dfcf729070009', '/entity/recruit', 'examresultcalculate', 'abstractList');
commit;
--prompt 575 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e62a79062d', '������¼ȡ����¼ȡ������_abstractList', '402880a91dfceaba011dfcf87b1a000a', '/entity/recruit', 'recruitManageExam', 'abstractList');
commit;
--prompt 576 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a921388dae012138d1b2370002', '��ӡѧ��֤_printStuCard', '402880a921388dae012138d1b2370002', '/entity/studentStatus', 'peStudentInfo', 'printStuCard');
commit;
--prompt 577 records committed...
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce4bc45606', '����������Ŀ_*', '402880a91dfcd969011dfce4bc45606', '/entity/workspaceTeacher', 'prTchPaperTitle', '*');
commit;
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a91dfcd969011dfce4bc456xs', '����ѡ��_*', '402880a91dfcd969011dfce4bc456xs', '/entity/workspaceStudent', 'prTchStuPaperTitleSelect', '*');
commit;
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01bebbm01e01c8f5a10020', '������Ϣ���_abstractUpdateColumn', '300202', '/entity/recruit', 'peRecStudentCheck', 'abstractUpdateColumn');
commit;
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('4028808c1e01be65bm01e01c8f5a10020', '�γ̲̽��б�_*', '402880a91df5d969011dfc4bc458321', '/entity/teaching', 'prTchCourseBook', '*');
commit;
insert into PE_PRIORITY (ID, NAME, FK_PRI_CAT_ID, NAMESPACE, ACTION, METHOD)
values ('402880a92113a9b0012113e6176y752f', '�����ʸ����_updateColumn', '300204', '/entity/recruit', 'peRecStudentNoexamCheck', 'abstractUpdateColumn');
commit;
--prompt 580 records loaded
--set feedback on
--set define on
--prompt Done.
