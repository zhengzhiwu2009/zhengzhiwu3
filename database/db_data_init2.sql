--prompt PL/SQL Developer import file
--prompt Created on 2009��5��18�� by yingying
--set feedback off
--set define off
--prompt Loading PE_PRI_CATEGORY...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1', 'ƽ̨����Ϣ����', null, '1', null, '1');
commit;
--prompt 1 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce4bc45606', '��ʦ������', null, '10', 'teacher', '0');
commit;
--prompt 2 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1001', '֪ͨ����', '1', '1001', '/entity/information/peBulletinView.action', '1');
commit;
--prompt 3 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1002', '�ҵĹ���', '1', '1002', 'to_top_menu', '1');
commit;
--prompt 4 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('100201', '�ռ���', '1002', '100201', '/entity/information/peDocumentView.action', '0');
commit;
--prompt 5 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('100202', '������', '1002', '100202', '/entity/information/peDocumentOutbox.action', '0');
commit;
--prompt 6 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('100203', '�½�����', '1002', '100203', '/entity/information/peDocumentOutbox_editnew.action', '0');
commit;
--prompt 7 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1003', '��������', '1', '1003', '/entity/information/personalInfo_viewInfo.action', '1');
commit;
--prompt 8 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1004', '�޸�����', '1', '1004', '/entity/information/personalInfo_editPwd.action', '1');
commit;
--prompt 9 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1005', '���Ź���', '1', '1005', '/entity/information/peInfoNews.action', '1');
commit;
--prompt 10 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1006', '�������', '1', '1006', 'to_top_menu', '1');
commit;
--prompt 11 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('100601', '��ӹ���', '1006', '100601', '/entity/information/peBulletin_showAddNotice.action', '0');
commit;
--prompt 12 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('100602', '�����б�', '1006', '100602', '/entity/information/peBulletin.action', '0');
commit;
--prompt 13 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1007', '���Ĺ���', '1', '1007', '/entity/information/peDocument.action', '1');
commit;
--prompt 14 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1008', '���Ź���', '1', '1008', null, '1');
commit;
--prompt 15 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('100801', 'ϵͳ���ŵ����', '1008', '100801', '/entity/information/peSystemSmsPoint.action', '1');
commit;
--prompt 16 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('100802', '���Ͷ���', '1008', '100802', '/entity/manager/information/sms/new_sms.jsp', '1');
commit;
--prompt 17 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('100803', '��˶���', '1008', '100803', '/entity/information/peSmsCheck.action', '1');
commit;
--prompt 18 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('100804', '�����б�', '1008', '100804', '/entity/information/peSms.action', '1');
commit;
--prompt 19 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('100805', '����ͳ��', '1008', '100805', '/entity/information/peSmsStatistic.action', '1');
commit;
--prompt 20 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('1009', '��̳����', '1', '1009', '/sso/forum/forum_forumManage.action', '1');
commit;
--prompt 21 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91e5d1ff2011e5ddd9f000001', 'ͶƱ����', '1', '1010', 'to_top_menu', '1');
commit;
--prompt 22 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91e5d1ff2011e5dee91a00003', '�����ʾ��б�', '402880a91e5d1ff2011e5ddd9f000001', '101001', '/entity/information/peVotePaper.action', '0');
commit;
--prompt 23 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91e5d1ff2011e5dee91a00002', '��ӵ����ʾ�', '402880a91e5d1ff2011e5ddd9f000001', '101002', '/entity/information/peVotePaper_toAddVotePaper.action', '0');
commit;
--prompt 24 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('2', '�������ݹ���', null, '2', null, '1');
commit;
--prompt 25 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('2001', 'ѧϰ���Ĺ���', '2', '2001', '/entity/basic/peSite.action', '1');
commit;
--prompt 26 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('2003', 'רҵ����', '2', '2003', '/entity/basic/peMajor.action', '1');
commit;
--prompt 27 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('2004', '��ι���', '2', '2004', '/entity/basic/peEdutype.action', '1');
commit;
--prompt 28 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('2005', '�꼶����', '2', '2005', '/entity/basic/peGrade.action', '1');
commit;
--prompt 29 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('2006', 'ѧ�ڹ���', '2', '2006', '/entity/basic/peSemester.action', '1');
commit;
--prompt 30 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a9213d9b9601213dec3c5000ea', '����Ա����', '2', '2007', '/entity/basic/siteManager.action', '1');
commit;
--prompt 31 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('3', '��������', null, '3', null, '1');
commit;
--prompt 32 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('3001', '��������', '3', '3001', null, '1');
commit;
--prompt 33 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300101', '�����������ι���', '3001', '300101', '/entity/recruit/peRecruitplan.action', '1');
commit;
--prompt 34 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a9203240940120329807000006', '�����ƻ�����', '3001', '300102', 'to_top_menu', '1');
commit;
--prompt 35 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300102', '�����ƻ�����', '402880a9203240940120329807000006', '30010201', '/entity/recruit/prRecPlanMajorEdutype.action', '0');
commit;
--prompt 36 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a920324094012032db49b00007', '���������ƻ�', '402880a9203240940120329807000006', '30010202', '/entity/manager/recruit/recplan_copy.jsp', '0');
commit;
--prompt 37 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300103', '��������', '3001', '300103', '/entity/recruit/recruitJianzhang.action', '1');
commit;
--prompt 38 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300104', 'ѧ�ֱ�׼����', '3001', '300104', '/entity/recruit/setCreditA.action', '1');
commit;
--prompt 39 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('3002', '������Ϣ����', '3', '3002', null, '1');
commit;
--prompt 40 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300200', '������Ϣ��ѯ', '3002', '300200', '/entity/recruit/recruitStu.action?search=true', '1');
commit;
--prompt 41 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300201', '������Ϣ¼��', '3002', '300201', 'to_top_menu', '1');
commit;
--prompt 42 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('30020101', '����¼��ѧ��', '300201', '30020101', '/entity/recruit/addPeRecStudent_turntostudentsingleadd.action', '0');
commit;
--prompt 43 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('30020102', '����¼��', '300201', '30020102', '/entity/recruit/addPeRecStudent_batch.action', '0');
commit;
--prompt 44 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300202', '������Ϣ���', '3002', '300202', '/entity/recruit/peRecStudentCheck.action', '1');
commit;
--prompt 45 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300203', '��ʦ�ʸ����', '3002', '300203', '/entity/recruit/peRecStudentTeacherCheck.action', '1');
commit;
--prompt 46 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300204', '�����ʸ����', '3002', '300204', '/entity/recruit/peRecStudentNoexamCheck.action', '1');
commit;
--prompt 47 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300205', '��Ƭ����', '3002', '300205', 'to_top_menu', '1');
commit;
--prompt 48 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfcde6d0f0001', '�����ϴ�', '300205', '30020501', '/entity/recruit/recStudentPhoto_photo.action', '0');
commit;
--prompt 49 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfcdf76120002', '�����ϴ�', '300205', '30020502', '/entity/recruit/recStudentPhotos_photos.action', '0');
commit;
--prompt 50 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a92037626d0120379540aa0005', 'ѧ����֤', '3002', '300206', '/entity/recruit/xueliCheck.action', '1');
commit;
--prompt 51 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('3003', '��ѧ���Թ���', '3', '3003', null, '1');
commit;
--prompt 52 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300301', '��ѧ��������', '3003', '300301', 'to_top_menu', '1');
commit;
--prompt 53 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce10b580003', '���Կγ�����', '300301', '30030101', '/entity/recruit/examcourseset.action', '0');
commit;
--prompt 54 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce22f470004', '���רҵ����', '300301', '30030102', '/entity/recruit/eduTypeMajorExamSet.action', '0');
commit;
--prompt 55 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce314960005', '����ʱ������', '300301', '30030103', '/entity/recruit/eduExamTimeSet.action', '0');
commit;
--prompt 56 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300302', '��ѧ���Թ���', '3003', '300302', 'to_top_menu', '1');
commit;
--prompt 57 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce4bc990006', '�����γ���ϸ', '300302', '30030201', '/entity/recruit/examstucourse.action', '0');
commit;
--prompt 58 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce598340007', '�Զ�����γ�', '300302', '30030202', '/entity/manager/recruit/recruit_autoallotcourse.jsp', '0');
commit;
--prompt 59 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce6673c0008', '�Զ����俼��', '300302', '30030203', '/entity/manager/recruit/recruit_autoallotroom.jsp', '0');
commit;
--prompt 60 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce724900009', '��ӡ׼��֤', '300302', '30030204', '/entity/recruit/examStuRoom.action?msg=current', '0');
commit;
--prompt 61 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce7c4b8000a', '�࿼��ѯ', '300302', '30030205', '/entity/recruit/examinvigilator.action', '0');
commit;
--prompt 62 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce8c32d000b', 'Ѳ����ѯ', '300302', '30030206', '/entity/recruit/examInspector.action', '0');
commit;
--prompt 63 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfceaba011dfcef6a9b0003', 'Ѳ����Ա����', '300302', '30030207', '/entity/manager/recruit/recruit_autoallotInvigilator.jsp', '0');
commit;
--prompt 64 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a92064cf480120654e8be7000f', '�࿼��Ա����', '300302', '30030208', '/entity/manager/recruit/recruit_autojiankao.jsp', '0');
commit;
--prompt 65 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfceaba011dfcf031460004', '����ͳ��', '300302', '30030209', '/entity/recruit/examfenpeistat_turntoStat.action', '0');
commit;
--prompt 66 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfceaba011dfcf0fb1e0005', '��������', '300302', '30030210', '/entity/recruit/peRecRoom.action', '0');
commit;
--prompt 67 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfceaba011dfcf1e1290006', '����������', '300302', '30030211', '/entity/recruit/examStuRoom.action', '0');
commit;
--prompt 68 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300303', '��ѧ���Գɼ�����', '3003', '300303', 'to_top_menu', '1');
commit;
--prompt 69 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfceaba011dfcf5c37c0007', '��ѧ���Գɼ�', '300303', '30030301', '/entity/recruit/examachievement.action', '0');
commit;
--prompt 70 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfceaba011dfcf678d20008', '���Գɼ�����', '300303', '30030302', '/entity/recruit/examachievement_batch.action', '0');
commit;
--prompt 71 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfceaba011dfcf729070009', 'ͳ��¼ȡ����', '300303', '30030303', '/entity/manager/recruit/exam_result_calculate.jsp', '0');
commit;
--prompt 72 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('3004', '¼ȡ����', '3', '3004', null, '1');
commit;
--prompt 73 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300402', '¼ȡ', '3004', '300402', 'to_top_menu', '1');
commit;
--prompt 74 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfceaba011dfcf87b1a000a', '������¼ȡ', '300402', '30040201', '/entity/manager/recruit/exam_recruit_calculate.jsp', '0');
commit;
--prompt 75 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfceaba011dfcf91835000b', '������¼ȡ', '300402', '30040202', '/entity/recruit/recruitManageNoExam.action', '0');
commit;
--prompt 76 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfceaba011dfcf87b1a043a', '��������¼ȡ', '300402', '30040203', '/entity/manager/recruit/luqu_conditionSet2.jsp', '0');
commit;
--prompt 77 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300403', '¼ȡ״̬�޸�', '3004', '300403', '/entity/recruit/recruitManage.action?search=true', '1');
commit;
--prompt 78 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300404', '¼ȡ֪ͨ���ӡ', '3004', '300404', '/entity/manager/recruit/letter_of_admission.jsp', '1');
commit;
--prompt 79 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('3005', 'ͳ�Ʋ�ѯ', '3', '3005', null, '1');
commit;
--prompt 80 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300501', '������Ϣ��ѯͳ��', '3005', '300501', '/entity/manager/recruit/baomingstat.jsp', '1');
commit;
--prompt 81 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300502', '¼ȡ״̬����ͳ��', '3005', '300502', '/entity/manager/recruit/recruitStatus.jsp', '1');
commit;
--prompt 82 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300503', '���Բ�ѯͳ��', '3005', '300503', '/entity/recruit/recruitNoExamStat.action', '1');
commit;
--prompt 83 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('300504', '��ѧ���Գɼ�ͳ��', '3005', '300504', 'to_top_menu', '1');
commit;
--prompt 84 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfcfc63680001', '�뿼�ɼ���ѯ', '300504', '30050401', '/entity/recruit/examScoreSearch.action', '0');
commit;
--prompt 85 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfcfd02090002', '�뿼�ɼ�ͳ��', '300504', '30050402', '/entity/manager/recruit/score_stat.jsp', '0');
commit;
--prompt 86 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('7005', '�շѱ�׼����', '3', '3006', null, '1');
commit;
--prompt 87 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('700501', '�շѱ�׼����', '7005', '300601', '/entity/fee/feeStandardManager.action', '1');
commit;
--prompt 88 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('700502', '���˱�׼����', '7005', '300602', '/entity/fee/listStudentForFeeSet.action?search=true', '1');
commit;
--prompt 89 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4', 'ѧ������', null, '4', null, '1');
commit;
--prompt 90 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4001', 'ѧ��ע�����', '4', '4001', null, '1');
commit;
--prompt 91 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400101', 'Ԥ���ѹ���', '4001', '400101', 'to_top_menu', '1');
commit;
--prompt 92 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfcfe0b3c0003', '��¼ȡ�б�', '400101', '40010101', '/entity/studentStatus/prRecPriPay.action?search=true', '0');
commit;
--prompt 93 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028808c1e01beb1011e01c792b50001', 'Ԥ���Ѽ���', '400101', '40010102', '/entity/studentStatus/prRecPriPayApply.action?search=true', '0');
commit;
--prompt 94 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfcfea7ca0004', '��������', '400101', '40010103', '/entity/studentStatus/derateFeeApply_turnto.action', '0');
commit;
--prompt 95 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91fef4fa7011fef5748fa0001', 'Ԥ����ͳ��', '400101', '40010104', '/entity/studentStatus/derateFeeStat_turnToJsp.action', '0');
commit;
--prompt 96 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400102', 'ע�����', '4001', '400102', 'to_top_menu', '1');
commit;
--prompt 97 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfcff8c7d0005', '��ע���б�', '400102', '40010201', '/entity/studentStatus/peRegisterStudent.action?search=true', '0');
commit;
--prompt 98 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd00ad2f0007', 'X��������ע��', '400102', '40010203', '/entity/manager/studentStatus/student_batch_register.jsp', '0');
commit;
--prompt 99 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4002', 'ѧ����Ϣ����', '4', '4002', null, '1');
commit;
--prompt 100 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400201', 'ѧ����Ϣ��ѯ', '4002', '400201', '/entity/studentStatus/peStudentInfo.action', '1');
commit;
--prompt 101 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a921388dae012138d1b2370002', '��ӡѧ��֤', '4002', '400202', '/entity/manager/studentStatus/print_stuCard.jsp', '1');
commit;
--prompt 102 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400203', 'ģ���½', '4002', '400203', '/entity/studentStatus/simulateStudentLogin.action', '1');
commit;
--prompt 103 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a9207e282001207e5baca5000d', '���Ź���', '4002', '400204', 'to_top_menu', '1');
commit;
--prompt 104 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400204', '�����������', '402880a9207e282001207e5baca5000d', '40020401', '/entity/studentStatus/prStudentGoodApply.action', '0');
commit;
--prompt 105 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a9207e282001207e6a3837000e', '���������ӡ', '402880a9207e282001207e5baca5000d', '40020402', '/entity/studentStatus/studentGoodApplyPrint.action', '0');
commit;
--prompt 106 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400205', 'Υ�͹���', '4002', '400205', '/entity/studentStatus/prStudentOffence.action?search=true', '1');
commit;
--prompt 107 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4003', 'ѧ���䶯����', '4', '4003', null, '1');
commit;
--prompt 108 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400300', 'ѧ���䶯����', '4003', '400300', '/entity/studentStatus/peChangeApply.action', '1');
commit;
--prompt 109 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400301', 'ѧ���䶯', '4003', '400301', 'to_top_menu', '1');
commit;
--prompt 110 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd01afda0008', '�䶯ѧϰ����', '400301', '40030101', '/entity/studentStatus/peChangeSite.action', '0');
commit;
--prompt 111 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd0242660009', '�䶯���', '400301', '40030102', '/entity/studentStatus/peChangeType.action', '0');
commit;
--prompt 112 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd02f961000a', '�䶯רҵ', '400301', '40030103', '/entity/studentStatus/peChangeMajor.action', '0');
commit;
--prompt 113 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a9205f34a901205f5444030001', '��תרҵ����', '400301', '40030104', '/entity/studentStatus/prStuChangeableMajor.action', '0');
commit;
--prompt 114 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400302', '���ѧ���䶯', '4003', '400302', '/entity/studentStatus/studentStatus.action?search=true', '1');
commit;
--prompt 115 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd03f9e9000c', '�䶯ѧϰ����', '400302', '40030201', '/entity/studentStatus/peChangeSite_turntoSearch.action', '0');
commit;
--prompt 116 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd048512000d', '�䶯���', '400302', '40030202', '/entity/studentStatus/peChangeType_turntoSearch.action', '0');
commit;
--prompt 117 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd050cc1000e', '�䶯רҵ', '400302', '40030203', '/entity/studentStatus/peChangeMajor_turntoSearch.action', '0');
commit;
--prompt 118 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400303', '��ѧ������ѧ��', '4003', '400303', 'to_top_menu', '1');
commit;
--prompt 119 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd05fe06000f', '��ѧ�����б�', '400303', '40030301', '/entity/studentStatus/peStudentExpel.action', '0');
commit;
--prompt 120 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd0688350010', '������ѧ', '400303', '40030302', '/entity/studentStatus/peStudentStatus_turntoDropoutSearch.action', '0');
commit;
--prompt 121 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd0716d90011', '����ѧ��', '400303', '40030303', '/entity/studentStatus/peStudentStatus_turntoExpelSearch.action', '0');
commit;
--prompt 122 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4004', '��ҵ����', '4', '4004', null, '1');
commit;
--prompt 123 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400401', '��ҵ���ι���', '4004', '400401', '/entity/studentStatus/peGraduate.action', '1');
commit;
--prompt 124 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400402', '��ҵ��������', '4004', '400402', 'to_top_menu', '1');
commit;
--prompt 125 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd084ab50012', '���Ա�ҵ����', '400402', '40040201', '/entity/studentStatus/peCanGraduate.action', '0');
commit;
--prompt 126 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd08d92a0013', '��ҵ���', '400402', '40040202', '/entity/studentStatus/prApplyGraduate.action', '0');
commit;
--prompt 127 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd09562a0014', '��ҵѧ���б�', '400402', '40040203', '/entity/studentStatus/peGraduatedStudent.action', '0');
commit;
--prompt 128 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91fd6ae10011fd6b7e4180001', '��ҵ����ͳ��', '400402', '40040204', '/entity/studentStatus/peGraduateStat_toGraduateStat.action', '0');
commit;
--prompt 129 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400403', '�������', '4004', '400403', 'to_top_menu', '1');
commit;
--prompt 130 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd0a67d90015', '�걨����', '400403', '40040301', '/entity/studentStatus/peYearReport.action', '0');
commit;
--prompt 131 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcfaab011dfd0aeaa50016', 'X��ҵ֤��ӡ', '400403', '40040302', '/test/inprogress.jsp', '0');
commit;
--prompt 132 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400404', '����ͼ��У��', '4004', '400404', '/entity/studentStatus/peStudentPhoto.action', '1');
commit;
--prompt 133 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4005', 'ѧλ����', '4', '4005', null, '1');
commit;
--prompt 134 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400501', '��������ѧλ����', '4005', '400501', '/entity/studentStatus/peCanApplyDegree.action', '1');
commit;
--prompt 135 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400502', 'ѧλ���', '4005', '400502', '/entity/studentStatus/peApplyDegree.action', '1');
commit;
--prompt 136 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400503', 'ѧλ����', '4005', '400503', '/entity/studentStatus/peStudentDegree.action', '1');
commit;
--prompt 137 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4006', '��ѯͳ��', '4', '4006', null, '1');
commit;
--prompt 138 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400601', 'ѧ���ۺ���Ϣ��ѯ', '4006', '400601', '/entity/studentStatus/peStudentStudyCount.action?search=true', '1');
commit;
--prompt 139 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400602', '�ڼ�ѧ��ͳ��', '4006', '400602', '/entity/manager/pub/pre_statistic.jsp', '1');
commit;
--prompt 140 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('400603', '��ҵѧλͳ��', '4006', '400603', '/entity/manager/studentStatus/graduation_stat.jsp', '1');
commit;
--prompt 141 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('5', '��ѧ�������', null, '5', null, '1');
commit;
--prompt 142 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('5001', '��ѧ��������', '5', '5001', null, '1');
commit;
--prompt 143 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500101', '�γ̹���', '5001', '500101', 'to_top_menu', '1');
commit;
--prompt 144 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd0ebb830001', '�γ���Ϣ�б�', '500101', '50010101', '/entity/teaching/peCourseManager.action', '0');
commit;
--prompt 145 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd0f3f980002', '������ӿγ�', '500101', '50010102', '/entity/teaching/peCourseManager_batch.action', '0');
commit;
--prompt 146 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500103', '��ѧ�ƻ�����', '5001', '500103', 'to_top_menu', '1');
commit;
--prompt 147 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1055f90003', '��ѧ�ƻ��б�', '500103', '50010301', '/entity/teaching/peTeaPlan.action', '0');
commit;
--prompt 148 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd10c7320004', '�����ѧ�ƻ�', '500103', '50010302', '/entity/teaching/peTeaPlan_batch.action', '0');
commit;
--prompt 149 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500104', '��ʦ����', '5001', '500104', 'to_top_menu', '1');
commit;
--prompt 150 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd11aa6e0005', '��ʦ�б�', '500104', '50010401', '/entity/teaching/teacher.action', '0');
commit;
--prompt 151 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd123ece0006', '�����ʦ����', '500104', '50010402', '/entity/manager/teaching/teacher_batch.jsp', '0');
commit;
--prompt 152 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500105', '�̲Ĺ���', '5001', '500105', 'to_top_menu', '1');
commit;
--prompt 153 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1303e30007', '�̲���Ϣ�б�', '500105', '50010501', '/entity/teaching/teachingMaterialsManager.action', '0');
commit;
--prompt 154 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd13ac290008', '������ӽ̲�', '500105', '50010502', '/entity/teaching/teachingMaterialsManager_batch.action', '0');
commit;
--prompt 155 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500106', '�μ�����', '5001', '500106', 'to_top_menu', '1');
commit;
--prompt 156 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd14be740009', '�μ��б�', '500106', '50010601', '/entity/teaching/courseware.action', '0');
commit;
--prompt 157 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1578ea000a', '����μ�����', '500106', '50010602', '/entity/manager/teaching/courseware_batch.jsp', '0');
commit;
--prompt 158 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028809720375d4e01203768474d7894', '�γ���ҵ��ʽ', '5001', '500107', '/entity/teaching/courseHomework.action', '1');
commit;
--prompt 159 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('5002', 'ѡ�ι���', '5', '5002', null, '1');
commit;
--prompt 160 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500201', 'ѡ��ʱ������', '5002', '500201', '/entity/teaching/electiveTimeManage.action', '1');
commit;
--prompt 161 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd19aa960011', 'ѧ�ڿ���', '5002', '500202', 'to_top_menu', '1');
commit;
--prompt 162 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd19aa960012', '�����Ͽδ���', '402880a91dfd0d0c011dfd19aa960011', '50020201', '/entity/manager/teaching/openCourse_time_batch.jsp', '0');
commit;
--prompt 163 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd19aa960013', 'ѧ�ڿ��β鿴', '402880a91dfd0d0c011dfd19aa960011', '50020202', '/entity/teaching/prTchOpencourse.action', '0');
commit;
--prompt 164 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288097202190d3012021c659c40001', '�Ͽδ�������', '402880a91dfd0d0c011dfd19aa960011', '50020203', '/entity/teaching/opencourseTimes.action', '0');
commit;
--prompt 165 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91fcc3c73011fcc47aaec0001', '���뽨�鳡��', '402880a91dfd0d0c011dfd19aa960011', '50020204', '/entity/manager/teaching/openCourse_batch.jsp', '0');
commit;
--prompt 166 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500202', 'ѧ��ѡ�ι���', '5002', '500203', 'to_top_menu', '1');
commit;
--prompt 167 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1667ee000b', '��ѧ��ѡ��', '500202', '50020301', '/entity/manager/teaching/electiveForStudentSearch.jsp', '0');
commit;
--prompt 168 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1704ca000c', 'ѧ����ѡ��', '500202', '50020302', '/entity/teaching/electiveInfoManage.action', '0');
commit;
--prompt 169 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd17868d000d', 'ѡ��ͳ��', '500202', '50020303', '/entity/teaching/electiveStat.action', '0');
commit;
--prompt 170 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd182482000e', 'δѡ��ѧ��', '500202', '50020304', '/entity/teaching/unElectiveStuList.action', '0');
commit;
--prompt 171 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd18b5d5000f', '�̲���������', '500202', '50020305', '/entity/teaching/courseOrderStat.action', '0');
commit;
--prompt 172 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500203', 'ѡ�ο��ι���', '5002', '500204', 'to_top_menu', '1');
commit;
--prompt 173 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd19aa960010', '�ֶ�����', '500203', '50020401', '/entity/teaching/manualConfirmCourse.action', '0');
commit;
--prompt 174 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1a56760011', '��������', '500203', '50020402', '/entity/teaching/batchConfirmCourse_result1.action', '0');
commit;
--prompt 175 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1aec8c0012', '����ͳ��', '500203', '50020403', '/entity/teaching/confirmedCourseStat.action', '0');
commit;
--prompt 176 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1c395f0015', '�ѿ�������', '500203', '50020405', '/entity/teaching/confirmedCourseInfo.action?search=true', '0');
commit;
--prompt 177 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('5003', '��ѧ���̹���', '5', '5003', null, '1');
commit;
--prompt 178 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500301', 'X�γ̱�����', '5003', '500301', 'to_top_menu', '1');
commit;
--prompt 179 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1d1e210016', 'X�γ̱���Ϣ', '500301', '50030101', '/test/inprogress.jsp', '0');
commit;
--prompt 180 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1d98a10017', 'X����γ̱�', '500301', '50030102', '/test/inprogress.jsp', '0');
commit;
--prompt 181 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500302', 'X��ʦ��ѧͳ��', '5003', '500302', '/test/inprogress.jsp', '1');
commit;
--prompt 182 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500303', 'X��ҵͳ��', '5003', '500303', '/test/inprogress.jsp', '1');
commit;
--prompt 183 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500304', 'X����γ�', '5003', '500304', 'to_top_menu', '1');
commit;
--prompt 184 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1ea5da0018', 'X����γ�', '500304', '50030401', '/test/inprogress.jsp', '0');
commit;
--prompt 185 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd1f2fac0019', 'Xϵ������', null, '50030402', '/test/inprogress.jsp', '0');
commit;
--prompt 186 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500305', 'X����������ҵ��', '5003', '500305', '/test/inprogress.jsp', '1');
commit;
--prompt 187 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('5004', '�ɼ�����', '5', '5004', 'to_top_menu', '1');
commit;
--prompt 188 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500401', '�γ̳ɼ�����', '5004', '500401', 'to_top_menu', '1');
commit;
--prompt 189 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd204784001a', 'ƽʱ�ɼ�����', '500401', '50040101', '/entity/manager/teaching/usualScoreView_search.jsp', '0');
commit;
--prompt 190 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd20c02f001b', '��ҵ�ɼ�����', '500401', '50040102', '/entity/manager/teaching/homeworkScoreView_search.jsp', '0');
commit;
--prompt 191 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd2161af001d', '���Գɼ��鿴', '500401', '50040103', '/entity/manager/teaching/examScoreView_search.jsp', '0');
commit;
--prompt 192 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd21f4e6001e', '�ɼ��ϳ�����', '500401', '50040104', '/entity/teaching/scorePercentSet.action', '0');
commit;
--prompt 193 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd226b6e001f', '�ɼ��ϳ�', '500401', '50040105', '/entity/manager/teaching/result_make.jsp', '0');
commit;
--prompt 194 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd22e7260021', '���Ƴɼ��ϳ�', '500401', '50040106', '/entity/manager/teaching/scoreCompose_Single.jsp', '0');
commit;
--prompt 195 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd22e7260020', '�ɼ���ѯ', '500401', '50040107', '/entity/manager/teaching/score_search.jsp', '0');
commit;
--prompt 196 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91f491fc7011f493aecb60001', '�ɼ���������', '500401', '50040108', '/entity/manager/teaching/course_score_batch.jsp', '0');
commit;
--prompt 197 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500402', 'ͳ���ɼ�����', '5004', '500402', 'to_top_menu', '1');
commit;
--prompt 198 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd23e3f50021', 'ͳ���ɼ�', '500402', '50040201', '/entity/teaching/unitExamScore.action', '0');
commit;
--prompt 199 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd247a590022', '��������ɼ�', '500402', '50040202', '/entity/manager/teaching/unitExamScore_batch.jsp', '0');
commit;
--prompt 200 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500403', 'ѧλӢ��ɼ�����', '5004', '500403', 'to_top_menu', '1');
commit;
--prompt 201 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd2568df0023', 'ѧλӢ��ɼ�', '500403', '50040301', '/entity/teaching/degreeEnglishScore.action', '0');
commit;
--prompt 202 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd25f61b0024', '��������', '500403', '50040302', '/entity/manager/teaching/degreeEnglishScore_batch.jsp', '0');
commit;
--prompt 203 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfd0d0c011dfd25f61b0064', 'ѧλӢ���������', '5004', '500404', '/entity/teaching/degreeEnglishAudit.action', '1');
commit;
--prompt 204 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91f491fa7011f493aecb60001', '��ӡ�ɼ���', '5004', '500405', '/entity/teaching/printScore.action', '1');
commit;
--prompt 205 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('5005', '��ҵ���Ĺ���', '5', '5005', null, '1');
commit;
--prompt 206 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500501', '��ҵ����ʱ������', '5005', '500501', '/entity/teaching/paperTime.action', '1');
commit;
--prompt 207 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500502', '�鿴������Ŀ', '5005', '500502', '/entity/teaching/teacherTopic.action', '1');
commit;
--prompt 208 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500503', '�鿴ѧ��ѡ��', '5005', '500503', '/entity/teaching/studentTopic.action', '1');
commit;
--prompt 209 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028809720375d4e01203768474d0018', '�鿴ѡ��δѡ��', '5005', '500504', '/entity/teaching/studentNoSelect.action', '1');
commit;
--prompt 210 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500504', '������', '5005', '500505', 'to_top_menu', '1');
commit;
--prompt 211 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8761011dff8af7ac0001', '��ȡ�������', '500504', '50050501', '/entity/teaching/replyListSelect.action', '0');
commit;
--prompt 212 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dff958b500001', '�鿴�������', '500504', '50050502', '/entity/teaching/replyListView.action', '0');
commit;
--prompt 213 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dff96158f0002', '���Ŵ��ʱ��', '500504', '50050503', '/entity/manager/teaching/replyTimeSet.jsp', '0');
commit;
--prompt 214 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dff96c3630003', '���֪ͨ', '500504', '50050504', '/entity/manager/teaching/replyNotify.jsp', '0');
commit;
--prompt 215 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91fa74cbc011fa7509a0f0003', '���ô��ʱ��', '500504', '50050505', '/entity/teaching/peTchRejoinSection.action', '0');
commit;
--prompt 216 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91fa7931b011fa79eee9c0001', '���ô�����', '500504', '50050506', '/entity/teaching/peTchRejoinRoom.action', '0');
commit;
--prompt 217 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500505', '���ĳɼ�����', '5005', '500506', 'to_top_menu', '1');
commit;
--prompt 218 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dff97d5fc0004', '���ĳɼ��鿴', '500505', '50050601', '/entity/teaching/paperScoreSearch.action', '0');
commit;
--prompt 219 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dff985e950005', '���ɼ�¼��', '500505', '50050602', '/entity/teaching/replyScoreSearch.action', '0');
commit;
--prompt 220 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dff98eb920006', '���ĳɼ��ϳ�', '500505', '50050603', '/entity/manager/teaching/finalScore.jsp', '0');
commit;
--prompt 221 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500506', '��������ͳ��', '5005', '500507', '/entity/teaching/paperShenqingStat.action', '1');
commit;
--prompt 222 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('500507', '���ĳɼ�ͳ��', '5005', '500508', 'to_top_menu', '1');
commit;
--prompt 223 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dff99ebeb0007', '���Ľ�չͳ��', '500507', '50050801', '/entity/teaching/paperProcess.action', '0');
commit;
--prompt 224 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dff9a84050008', '���ĳɼ�ͳ��', '500507', '50050802', '/entity/teaching/paperScoreStat_toJSP.action', '0');
commit;
--prompt 225 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dff9a84050079', '��ҵ�����������', '5005', '500509', '/entity/teaching/paperReapplyAudit.action', '1');
commit;
--prompt 226 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('40288097201892ad012018b233390001', '���Ľ�ʦ����', '5005', '500510', '/entity/teaching/paperTeacher.action', '1');
commit;
--prompt 227 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('6', '�������', null, '6', null, '1');
commit;
--prompt 228 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('6001', '��Ѳ����Ա����', '6', '6001', 'to_top_menu', '1');
commit;
--prompt 229 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dff9d4e6f0009', '��Ѳ������', '6001', '600101', '/entity/exam/peExamPatrol.action', '0');
commit;
--prompt 230 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffa550da000a', '�����Ѳ��', '6001', '600102', '/entity/exam/peExamPatrolManage.action', '0');
commit;
--prompt 231 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a9206a116001206aaeecee0014', 'վ����·����', '6001', '600103', '/entity/exam/peSiteLine.action', '0');
commit;
--prompt 232 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('6002', '��ĩ���Թ���', '6', '6002', null, '1');
commit;
--prompt 233 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('600201', '��ĩ����ʱ������', '6002', '600201', '/entity/exam/peFinalExam.action', '1');
commit;
--prompt 234 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('600202', '��ĩ����ԤԼ����', '6002', '600202', 'to_top_menu', '1');
commit;
--prompt 235 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffa64f1f000b', 'ԤԼ����', '600202', '60020201', '/entity/exam/examBooking.action', '0');
commit;
--prompt 236 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffa6e3be000c', 'ԤԼ��ѯɾ��', '600202', '60020202', '/entity/exam/peFinalExamApply.action', '0');
commit;
--prompt 237 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffa7636e000d', 'ԤԼͳ��', '600202', '60020203', '/entity/manager/exam/pre_statistic.jsp', '0');
commit;
--prompt 238 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91fd61dae011fd61fcdd40001', 'δԤԼ�б�', '600202', '60020204', '/entity/exam/finalExamNoBooking.action', '0');
commit;
--prompt 239 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('600203', '��������', '6002', '600203', 'to_top_menu', '1');
commit;
--prompt 240 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffa843db000e', '���Գ��ι���', '600203', '60020301', '/entity/exam/peExamno.action', '0');
commit;
--prompt 241 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffa8ca51000f', '�Զ����ſ���', '600203', '60020302', '/entity/manager/exam/exam_no_auto.jsp', '0');
commit;
--prompt 242 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffa95aaa0010', '��������', '600203', '60020303', '/entity/exam/peExamRoom.action', '0');
commit;
--prompt 243 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffaa107d0011', '�������ŵ���', '600203', '60020304', '/entity/exam/prExamBooking.action?search=true', '0');
commit;
--prompt 244 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffaabae60012', '�������Ų�ѯ', '600203', '60020305', '/entity/exam/examInfoSearch.action?search=true', '0');
commit;
--prompt 245 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffab52630013', '����ǩ����', '600203', '60020306', '/entity/manager/exam/exam_sign_in.jsp', '0');
commit;
--prompt 246 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91fb751f8011fb756c5d20001', '��λ��ǩ', '600203', '60020307', '/entity/manager/exam/exam_seat_no.jsp', '0');
commit;
--prompt 247 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028808c1fca20a9011fca27bd9f0001', '�Ծ�ǩ���', '600203', '60020308', '/entity/manager/exam/exam_drawing_to.jsp', '0');
commit;
--prompt 248 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a920416fe901204171bdb50001', '�Ծ��ͳ��', '600203', '60020309', '/entity/exam/examPaperStat.action', '0');
commit;
--prompt 249 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('600204', '�ɼ��������', '6002', '600204', 'to_top_menu', '1');
commit;
--prompt 250 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffaec3200017', 'ƽʱ�ɼ�����', '600204', '60020401', '/entity/exam/normalScoreReCheck.action', '0');
commit;
--prompt 251 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffaf4e490018', '��ҵ�ɼ�����', '600204', '60020402', '/entity/exam/workScoreReCheck.action', '0');
commit;
--prompt 252 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffaff18e0019', '���Գɼ�����', '600204', '60020403', '/entity/exam/examScoreReCheck.action', '0');
commit;
--prompt 253 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffad594f0016', '�ɼ�����', '6002', '600205', 'to_top_menu', '1');
commit;
--prompt 254 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffc4bd0a002e', '�ɼ�¼��', '402880a91dff8c3a011dffad594f0016', '60020501', '/entity/exam/examScore.action?search=true', '0');
commit;
--prompt 255 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffc53fb7002f', '�ɼ�����¼��', '402880a91dff8c3a011dffad594f0016', '60020502', '/entity/manager/exam/exam_score_upload.jsp', '0');
commit;
--prompt 256 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028809720375d4e01203486774d0018', '�����Ƿ�����', '402880a91dff8c3a011dffad594f0016', '60020503', '/entity/exam/examscoreexportinputdata.action?search=true', '0');
commit;
--prompt 257 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028809720375d4e01203486774d003', '�Ƿ�����Ƿ�', '402880a91dff8c3a011dffad594f0016', '60020504', '/entity/exam/prexamscoreinputuser_gotosetinputstatus.action', '0');
commit;
--prompt 258 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028809720375d4e01203486774d004', '���ɵǷ��ʻ�', '402880a91dff8c3a011dffad594f0016', '60020505', '/entity/exam/prexamscoreinputuser_creatInputUsers.action', '0');
commit;
--prompt 259 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028809720375d4e01203486774d005', '�Ƿ��ʻ�����', '402880a91dff8c3a011dffad594f0016', '60020506', '/entity/exam/prexamscoreinputuser.action?search=true', '0');
commit;
--prompt 260 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028809720375d4e01203486774d006', '�ɼ�¼��ͳ��', '402880a91dff8c3a011dffad594f0016', '60020507', '/entity/exam/examscoreinputstatistic.action', '0');
commit;
--prompt 261 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028809720375d4e01203486774d007', '�ɼ���ѯ�޸�', '402880a91dff8c3a011dffad594f0016', '60020508', '/entity/exam/examscoreinput.action?search=true', '0');
commit;
--prompt 262 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('6003', '���ɿγ̿���', '6', '6003', null, '1');
commit;
--prompt 263 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('600301', '��������', '6003', '600301', 'to_top_menu', '1');
commit;
--prompt 264 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffb2c7b0001a', '����ʱ������', '600301', '60030101', '/entity/exam/peMainCourseTime.action', '0');
commit;
--prompt 265 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffb4a5a8001c', '����ʱ������', '600301', '60030103', '/entity/exam/peExamMaincourseNo.action', '0');
commit;
--prompt 266 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffb527f7001d', '���Կγ̵���', '600301', '60030104', '/entity/exam/prExamOpenMaincourse.action', '0');
commit;
--prompt 267 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880911e106277011e10951f290001', '����ԤԼ����', '6003', '600302', 'to_top_menu', '1');
commit;
--prompt 268 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880911e106277011e10951f290002', 'ԤԼ����', '402880911e106277011e10951f290001', '60030201', '/entity/exam/maincourseExamBooking.action', '0');
commit;
--prompt 269 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880911e106277011e10adce210002', 'ԤԼ��ѯɾ��', '402880911e106277011e10951f290001', '60030202', '/entity/exam/maincourseElectiveManage.action', '0');
commit;
--prompt 270 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('600302', '���԰���', '6003', '600303', 'to_top_menu', '1');
commit;
--prompt 271 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffb61c5a001e', '�Զ����ſ���', '600302', '60030301', '/entity/manager/exam/maincourse_examroom_auto.jsp', '0');
commit;
--prompt 272 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffb6c5b90020', '������Ϣ����', '600302', '60030302', '/entity/exam/peExamMaincourseRoom.action', '0');
commit;
--prompt 273 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffb75b040022', '����������', '600302', '60030303', '/entity/exam/peMainCourseExamRoom.action', '0');
commit;
--prompt 274 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffb888780024', '�Ծ�ͳ���б�', '600302', '60030305', '/entity/exam/maincourseStat.action', '0');
commit;
--prompt 275 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('600303', '�ɼ�¼��', '6003', '600304', '/entity/exam/maincourseScoreManage.action', '1');
commit;
--prompt 276 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('600304', '�ɼ�����', '6003', '600305', '/entity/exam/peMainCourseScoreRecheck.action', '1');
commit;
--prompt 277 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91e874bce011e875a5b650001', 'ͳ���������', '6', '6004', 'to_top_menu', '1');
commit;
--prompt 278 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91e874bce011e875c1e520002', 'ͳ��Ӣ��A', '402880a91e874bce011e875a5b650001', '600401', '/entity/exam/englishAAvoidApply.action', '0');
commit;
--prompt 279 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91e874bce011e875caae30003', 'ͳ��Ӣ��B', '402880a91e874bce011e875a5b650001', '600402', '/entity/exam/englishBAvoidApply.action', '0');
commit;
--prompt 280 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91e874bce011e875d138f0004', 'ͳ�������', '402880a91e874bce011e875a5b650001', '600403', '/entity/exam/computerAvoidApply.action', '0');
commit;
--prompt 281 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91e874bce011e875d138f0005', '�γ��⿼�������', '6', '6005', '/entity/exam/examAvoidApply.action', '1');
commit;
--prompt 282 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('7', '�������', null, '7', null, '1');
commit;
--prompt 283 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('7001', '���Ѳ���', '7', '7001', null, '1');
commit;
--prompt 284 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('700101', '¼�뽻����ϸ', '7001', '700101', 'to_top_menu', '1');
commit;
--prompt 285 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffb989ab0025', '������Ϣ', '700101', '70010101', '/entity/fee/prFeeDetailIn.action?search=true', '0');
commit;
--prompt 286 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffba48290026', '������Ϣ¼��', '700101', '70010102', '/entity/fee/prFeeDetailIn_addOne.action', '0');
commit;
--prompt 287 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffbad18d0027', '������Ϣ¼��', '700101', '70010103', '/entity/fee/prFeeDetailIn_batch.action', '0');
commit;
--prompt 288 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('700102', '�ϱ���������', '7001', '700102', '/entity/fee/prFeeDetailForTakeIn.action', '1');
commit;
--prompt 289 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('700103', '�����������', '7001', '700103', '/entity/fee/peFeeBathCheck.action', '1');
commit;
--prompt 290 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a92011efae012011fc12d40001', '¼�뷢Ʊ��', '7001', '700104', '/entity/fee/peFeeDetailForReciept.action?search=true', '1');
commit;
--prompt 291 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('7002', '������ò���', '7', '7002', 'to_top_menu', '1');
commit;
--prompt 292 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('4028826a1e195595011e1984bbb30002', '�����б�', '7002', '700201', '/entity/fee/prFeeDetailReduce.action', '0');
commit;
--prompt 293 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffbbe28f0029', '������Ϣ¼��', '7002', '700202', '/entity/fee/prFeeDetailReduce_addone.action', '0');
commit;
--prompt 294 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffbc7634002a', '������Ϣ����', '7002', '700203', '/entity/fee/prFeeDetailReduce_batch.action', '0');
commit;
--prompt 295 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a9201209b50120125b1d27001c', '��ʦ�������', '7002', '700204', '/entity/fee/teacherReduce.action', '0');
commit;
--prompt 296 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a9201323450120132590ba0001', '��ʦ�����б�', '7002', '700205', '/entity/fee/teacherReduceDetail.action', '0');
commit;
--prompt 297 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('7003', 'ѧ���˷�', '7', '7003', 'to_top_menu', '1');
commit;
--prompt 298 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffbd3acc002b', 'ѧ���˷Ѳ���', '7003', '700301', '/entity/fee/prFeeDetailReturn.action', '0');
commit;
--prompt 299 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dff8c3a011dffbdd835002c', '������Ϣ����', '7003', '700302', '/entity/fee/prFeeDetailReturn_batch.action', '0');
commit;
--prompt 300 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('7004', '�˹�������ò���', '7', '7004', '/entity/fee/prFeeDetailSpecial.action', '1');
commit;
--prompt 301 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a920416fe90120418f09480006', '���Է��ù���', '7', '7005', '/entity/fee/recExamFee.action?search=true', '1');
commit;
--prompt 302 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('7006', '��������ѯͳ��', '7', '7006', null, '1');
commit;
--prompt 303 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('700601', '�������β�ѯ', '7006', '700601', '/entity/fee/peFeeBathQuery.action?enumConstByFlagFeeCheck.code=2', '1');
commit;
--prompt 304 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('700602', '��������ͳ��', '7006', '700602', '/entity/fee/feeTotalCount_turntoStat.action', '1');
commit;
--prompt 305 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('700603', 'ѧ�������˻���ѯ', '7006', '700603', '/entity/fee/studentAccount.action?search=true', '1');
commit;
--prompt 306 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('700604', 'ѧ�����з�����ϸ', '7006', '700604', '/entity/fee/prFeeDetailByStudent.action?search=true', '1');
commit;
--prompt 307 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('8', 'X��ѡ�ι���', null, '8', null, '1');
commit;
--prompt 308 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('8001', 'X���¹���', '8', '8001', '/entity/publicCourse/article.action', '1');
commit;
--prompt 309 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('8002', 'X�γ̹���', '8', '8002', '/entity/publicCourse/publicCourse.action', '1');
commit;
--prompt 310 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('8003', 'Xѧ����Ϣ����', '8', '8003', 'to_top_menu', '1');
commit;
--prompt 311 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('800301', 'Xѧ��ע�����', '8003', '800301', '/entity/publicCourse/studentManage.action', '0');
commit;
--prompt 312 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('800302', 'Xע��ͳ��', '8003', '800302', '/entity/publicCourse/enrolStat.action', '0');
commit;
--prompt 313 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('800303', 'Xѡ����Ϣ����', '8003', '800303', '/entity/publicCourse/studentManage_turnToUpload.action', '0');
commit;
--prompt 314 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('8004', 'X������Ϣ����', '8', '8004', '/entity/publicCourse/bookingSeat.action', '1');
commit;
--prompt 315 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('8005', 'X��ѧ��Ϣͳ��', '8', '8005', 'to_top_menu', '1');
commit;
--prompt 316 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('800501', 'Xͳ�Ʋ�ѯ1', '8005', '800501', '/test/inprogress.jsp', '0');
commit;
--prompt 317 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('800502', 'Xͳ�Ʋ�ѯ2', '8005', '800502', '/test/inprogress.jsp', '0');
commit;
--prompt 318 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('800503', 'Xͳ�Ʋ�ѯ3', '8005', '800503', '/test/inprogress.jsp', '0');
commit;
--prompt 319 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('8006', 'X�ɼ�����', '8', '8006', 'to_top_menu', '1');
commit;
--prompt 320 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('800601', 'X�ɼ�����', '8006', '800601', '/entity/publicCourse/scoreManage_turnToUpload.action', '0');
commit;
--prompt 321 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('800602', 'X�ɼ�����', '8006', '800602', '/entity/publicCourse/scoreManage.action', '0');
commit;
--prompt 322 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('8007', 'X�������', '8', '8007', 'to_top_menu', '1');
commit;
--prompt 323 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('800701', 'X�Զ��ſ���', '8007', '800701', '/entity/publicCourse/examManage_turnToAutoAllotRoom.action', '0');
commit;
--prompt 324 records committed...
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('800702', 'X�鿴��������', '8007', '800702', '/entity/publicCourse/examManage.action', '0');
commit;
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91dfcd969011dfce4bc456xs', 'ѧ��������', null, '11', 'student', '0');
insert into PE_PRI_CATEGORY (ID, NAME, FK_PARENT_ID, CODE, PATH, FLAG_LEFT_MENU)
values ('402880a91df5d969011dfc4bc458321', '�γ̲̽��б�', '500105', '50010503', '/entity/teaching/prTchCourseBook.action', '0');
--prompt 326 records loaded
--set feedback on
--set define on
--prompt Done.
