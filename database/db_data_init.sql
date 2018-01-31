--  Create sequence 招生身份证号重复数据使用序列生成唯一值
create sequence HNSD_REC_STUDENT_CARD_NO
minvalue 100000
maxvalue 900000
start with 100000
increment by 1
cache 20;
-- 预置数据库数据
-- Create sequence 报名号使用
create sequence archieve_id
minvalue 1
maxvalue 9999
start with 1
increment by 1
cache 10
cycle;

-- 初始化课程分组
insert into PE_TCH_COURSEGROUP (ID, NAME) values ('_1', '公共必修课');
insert into PE_TCH_COURSEGROUP (ID, NAME) values ('_2', '专业必修课');
insert into PE_TCH_COURSEGROUP (ID, NAME) values ('_3', '专业选修课');
insert into PE_TCH_COURSEGROUP (ID, NAME) values ('_4', '公共选修课');

-- 初始化EnumConst表数据

insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('1', '在籍', '4', 'FlagStudentStatus', '0', to_date('31-10-2008', 'dd-mm-yyyy'), '学生学籍状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('2', '是', '1', 'FlagIsvalid', '0', to_date('02-11-2008 14:04:53', 'dd-mm-yyyy hh24:mi:ss'), '是否有效');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('3', '否', '0', 'FlagIsvalid', '1', to_date('02-11-2008 14:04:53', 'dd-mm-yyyy hh24:mi:ss'), '是否有效');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4', '是', '1', 'FlagIspubliccourse', '1', to_date('02-11-2008 14:06:42', 'dd-mm-yyyy hh24:mi:ss'), '是否公选课');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1db430e5011db45574420001', '预交费减免申请', '0', 'ApplyType', '0', to_date('19-11-2008', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1db87ddd011db88f9bd00001', '待审核', '0', 'FlagApplyStatus', '1', to_date('20-11-2008', 'dd-mm-yyyy'), '申请状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1db87ddd011db89195cb0002', '申请通过', '1', 'FlagApplyStatus', '0', to_date('20-11-2008', 'dd-mm-yyyy'), '申请状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1dbe60fe011dbe64d08e0001', '已录取', '0', 'FlagStudentStatus', '1', to_date('21-11-2008', 'dd-mm-yyyy'), '学生学籍状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1dbe60fe011dbe7399870002', '已交费', '1', 'FlagStudentStatus', '0', to_date('21-11-2008', 'dd-mm-yyyy'), '学生学籍状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1dbe60fe011dbe7866590003', '已上报', '2', 'FlagStudentStatus', '0', to_date('21-11-2008', 'dd-mm-yyyy'), '学生学籍状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1dd7ff98011dd8025b450001', '学习积极分子', '1', 'ApplyType', '0', to_date('26-11-2008', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1dd80dc7011dd81da8a60001', '申请不通过', '2', 'FlagApplyStatus', '0', to_date('26-11-2008', 'dd-mm-yyyy'), '申请状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0001', '警告', '1', 'FlagDisobey', '0', to_date('29-11-2008', 'dd-mm-yyyy'), '违纪记录状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0002', '合格', '0', 'ScoreUniteComputer', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考计算机成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0003', '不合格', '1', 'ScoreUniteComputer', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考计算机成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0004', '违纪', '2', 'ScoreUniteComputer', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考计算机成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0005', '作弊', '3', 'ScoreUniteComputer', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考计算机成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0006', '缺考', '4', 'ScoreUniteComputer', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考计算机成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0012', '合格', '0', 'ScoreUniteEnglishA', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考英语A成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0013', '不合格', '1', 'ScoreUniteEnglishA', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考英语A成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0014', '违纪', '2', 'ScoreUniteEnglishA', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考英语A成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0015', '作弊', '3', 'ScoreUniteEnglishA', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考英语A成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0016', '缺考', '4', 'ScoreUniteEnglishA', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考英语A成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0022', '合格', '0', 'ScoreUniteEnglishB', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考英语B成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0023', '不合格', '1', 'ScoreUniteEnglishB', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考英语B成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0024', '违纪', '2', 'ScoreUniteEnglishB', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考英语B成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0025', '作弊', '3', 'ScoreUniteEnglishB', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考英语B成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d00ff0026', '缺考', '4', 'ScoreUniteEnglishB', '0', to_date('11-12-2008', 'dd-mm-yyyy'), '统考英语B成绩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75d94560002', '记过', '2', 'FlagDisobey', '0', to_date('29-11-2008', 'dd-mm-yyyy'), '违纪记录状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7580f011de75e1d3d0003', '留校查看', '3', 'FlagDisobey', '0', to_date('29-11-2008', 'dd-mm-yyyy'), '违纪记录状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7f370011de7f7e2a70001', '预交费费用', '0', 'FlagFeeType', '0', to_date('29-11-2008', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7fe7c011de80055500001', '未上报', '0', 'FlagFeeCheck', '0', to_date('29-11-2008', 'dd-mm-yyyy'), '费用上报审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7fe7c011de800f6c00002', '已上报', '1', 'FlagFeeCheck', '0', to_date('29-11-2008', 'dd-mm-yyyy'), '费用上报审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1de7fe7c011de8019fc20003', '已审核', '2', 'FlagFeeCheck', '0', to_date('29-11-2008', 'dd-mm-yyyy'), '费用上报审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e0602f4011e0605d6540001', '正常', '0', 'FlagDisobey', '1', to_date('05-12-2008', 'dd-mm-yyyy'), '违纪记录状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e14a844011e1513c7850001', '已毕业', '5', 'FlagStudentStatus', '0', to_date('08-12-2008', 'dd-mm-yyyy'), '学生学籍状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e14a844011e15147cac0002', '开除学籍', '6', 'FlagStudentStatus', '0', to_date('08-12-2008', 'dd-mm-yyyy'), '学生学籍状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e14a844011e1514ff1a0003', '退学', '7', 'FlagStudentStatus', '0', to_date('08-12-2008', 'dd-mm-yyyy'), '学生学籍状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e14a844011e15172fe50004', '开除学籍', '0', 'FlagAbortschoolType', '0', to_date('08-12-2008', 'dd-mm-yyyy'), '学籍异动（开除学籍|退学）');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e14a844011e1518329f0005', '退学', '1', 'FlagAbortschoolType', '1', to_date('08-12-2008', 'dd-mm-yyyy'), '学籍异动（开除学籍|退学）');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e19528b011e1955fa280001', '教师减免费用', '7', 'FlagFeeType', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e19528b011e1956b8c50002', '超学分减免费用', '8', 'FlagFeeType', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e19528b011e19574d250003', '其他减免费用', '9', 'FlagFeeType', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e19528b011e195faf910004', '退费', '6', 'FlagFeeType', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e19528b011e1960b6920005', '开课扣费', '3', 'FlagFeeType', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e19528b011e1961a20b0006', '开课后退课', '4', 'FlagFeeType', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e19c0c7011e19c2e16e0001', '未审核', '3', 'FlagFeeCheck', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '减免费用审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e19c0c7011e19c4fb260002', '审核不通过', '4', 'FlagFeeCheck', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '减免费用审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e1bc016011e1bc1f29e0001', '特殊费用', '2', 'FlagFeeType', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e1f9f08011e1fa750cc0001', '毕业申请', '8', 'ApplyType', '0', to_date('10-12-2008', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e1f9f08011e1fa7c7540002', '学位申请', '9', 'ApplyType', '0', to_date('10-12-2008', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e1fad90011e1fb12eec0001', '毕业延期申请', '7', 'ApplyType', '0', to_date('10-12-2008', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e43b2a2011e43b771b80001', '已删除', '1', 'FlagDel', '0', to_date('17-12-2008', 'dd-mm-yyyy'), '是否删除');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e43b2a2011e43b7ccb90002', '未删除', '0', 'FlagDel', '1', to_date('17-12-2008', 'dd-mm-yyyy'), '是否删除');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e447e65011e449334f00003', '未读', '0', 'FlagRead', '1', to_date('17-12-2008', 'dd-mm-yyyy'), '是否阅读');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c1e447e65011e4493bbd30004', '已读', '1', 'FlagRead', '0', to_date('17-12-2008', 'dd-mm-yyyy'), '是否阅读');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c2de7580f011de75d94560002', '原专业', '0', 'FlagMajorType', '1', to_date('28-04-2009', 'dd-mm-yyyy'), '专业备注（跨专业 非跨专业 专业方向）');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c3de7580f011de75d94560002', '跨专业', '1', 'FlagMajorType', '0', to_date('28-04-2009', 'dd-mm-yyyy'), '专业备注（跨专业 非跨专业 专业方向）');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c4de7580f011de75d94560002', '广告策划', '2', 'FlagMajorType', '0', to_date('28-04-2009', 'dd-mm-yyyy'), '专业备注（跨专业 非跨专业 专业方向）');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c5de7580f011de75d94560002', '环境艺术设计', '3', 'FlagMajorType', '0', to_date('28-04-2009', 'dd-mm-yyyy'), '专业备注（跨专业 非跨专业 专业方向）');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c6de7580f011de75d94560002', '平面设计', '4', 'FlagMajorType', '0', to_date('28-04-2009', 'dd-mm-yyyy'), '专业备注（跨专业 非跨专业 专业方向）');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028808c7de7580f011de75d94560002', '装饰工程管理', '5', 'FlagMajorType', '0', to_date('28-04-2009', 'dd-mm-yyyy'), '专业备注（跨专业 非跨专业 专业方向）');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da463db011da46baaac0002', '是', '1', 'FlagIsJiankao', '0', to_date('16-11-2008', 'dd-mm-yyyy'), '是否为监考人员');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da463db011da46c2cad0003', '否', '0', 'FlagIsJiankao', '1', to_date('16-11-2008', 'dd-mm-yyyy'), '是否为监考人员');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da463db011da46cc2840004', '是', '1', 'FlagIsXunkao', '0', to_date('16-11-2008', 'dd-mm-yyyy'), '是否为巡考人员');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da463db011da46d2c5a0005', '否', '0', 'FlagIsXunkao', '1', to_date('16-11-2008', 'dd-mm-yyyy'), '是否为巡考人员');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da481e0011da4963df60004', '男', '1', 'Gender', '1', to_date('16-11-2008', 'dd-mm-yyyy'), '性别');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da481e0011da49697130005', '女', '0', 'Gender', '0', to_date('16-11-2008', 'dd-mm-yyyy'), '性别');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da8c3b2011da8d19ab90001', '未开课', '0', 'FlagElectiveAdmission', '1', to_date('17-11-2008', 'dd-mm-yyyy'), '是否选课确认');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da8c3b2011da8d2362e0002', '已开课', '1', 'FlagElectiveAdmission', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '是否选课确认');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da8c3b2011da8d320dc0003', '未录入', '0', 'FlagScoreStatus', '1', to_date('17-11-2008', 'dd-mm-yyyy'), '成绩状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da8c3b2011da8d38ae00004', '正常', '1', 'FlagScoreStatus', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '成绩状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da8c3b2011da8d407250005', '缺考', '2', 'FlagScoreStatus', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '成绩状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da8c3b2011da8d4802d0006', '违纪', '3', 'FlagScoreStatus', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '成绩状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da8c3b2011da8d4dff10007', '作弊', '4', 'FlagScoreStatus', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '成绩状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da8c3b2011da8d590b30008', '否', '0', 'FlagScorePub', '1', to_date('17-11-2008', 'dd-mm-yyyy'), '成绩是否已发布');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911da8c3b2011da8d5f4100009', '是', '1', 'FlagScorePub', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '成绩是否已发布');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911dec22a0011dec23ba260001', '是', '1', 'FlagExamCourse', '1', to_date('30-11-2008', 'dd-mm-yyyy'), '是否考试课程');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911dec22a0011dec2423210002', '否', '0', 'FlagExamCourse', '0', to_date('30-11-2008', 'dd-mm-yyyy'), '是否考试课程');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880911df6e727011df6ec41470001', '期末考试成绩复查申请', '3', 'ApplyType', '0', to_date('02-12-2008', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880931fc6719e011fc685d83d0001', '未发布', '0', 'FlagIssue', '1', to_date('02-03-2009', 'dd-mm-yyyy'), '公选课新闻是否发布');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880931fc6719e011fc6866a5b0002', '已发布', '1', 'FlagIssue', '0', to_date('02-03-2009', 'dd-mm-yyyy'), '公选课新闻是否发布');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad22bd011dad3edbcc0001', '学生', '0', 'UserType', '0', to_date('18-11-2008', 'dd-mm-yyyy'), 'sso类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad22bd011dad3f441b0002', '教师', '1', 'UserType', '0', to_date('18-11-2008', 'dd-mm-yyyy'), 'sso类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad22bd011dad3fe84a0003', '分站管理员', '2', 'UserType', '0', to_date('18-11-2008', 'dd-mm-yyyy'), 'sso类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad22bd011dad4061ef0004', '总站管理员', '3', 'UserType', '0', to_date('18-11-2008', 'dd-mm-yyyy'), 'sso类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad22bd011dad5d21fa0005', '教师', '1', 'FlagRoleType', '0', to_date('18-11-2008', 'dd-mm-yyyy'), '角色类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad76ed011dad7ccfd70001', '教授', '0', 'FlagZhicheng', '0', to_date('18-11-2008', 'dd-mm-yyyy'), '教师职称');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad76ed011dad7d47590002', '副教授', '1', 'FlagZhicheng', '0', to_date('18-11-2008', 'dd-mm-yyyy'), '教师职称');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad76ed011dad7dc7c40003', '讲师', '2', 'FlagZhicheng', '0', to_date('18-11-2008', 'dd-mm-yyyy'), '教师职称');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad76ed011dad7e337e0004', '其他', '3', 'FlagZhicheng', '0', to_date('18-11-2008', 'dd-mm-yyyy'), '教师职称');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad76ed011dad7fb1060006', '主讲', '1', 'FlagTeacherType', '0', to_date('18-11-2008', 'dd-mm-yyyy'), '教师类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad76ed011dad801fae0007', '责任', '2', 'FlagTeacherType', '0', to_date('18-11-2008', 'dd-mm-yyyy'), '教师类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad76ed011dad8dc9e90008', '是', '1', 'FlagPaper', '0', to_date('18-11-2008', 'dd-mm-yyyy'), '教师是否代论文');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dad76ed011dad8e3b410009', '否', '0', 'FlagPaper', '1', to_date('18-11-2008', 'dd-mm-yyyy'), '教师是否代论文');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dc234f0011dc2411c760002', '参加答辩', '1', 'FlagPaperRejoin', '0', to_date('22-11-2008', 'dd-mm-yyyy'), '是否参加答辩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dc234f0011dc241b9430003', '不参加答辩', '0', 'FlagPaperRejoin', '1', to_date('22-11-2008', 'dd-mm-yyyy'), '是否参加答辩');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dc2bcef011dc2beeb7e0001', '上午8：30', '0', 'FlagRejoinSection', '0', to_date('22-11-2008', 'dd-mm-yyyy'), '答辩时间段');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dc2bcef011dc2bf6c080002', '下午2：00', '1', 'FlagRejoinSection', '0', to_date('22-11-2008', 'dd-mm-yyyy'), '答辩时间段');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dc2bcef011dc2bfcab30003', '晚上7：00', '2', 'FlagRejoinSection', '0', to_date('22-11-2008', 'dd-mm-yyyy'), '答辩时间段');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dc50cd7011dc51094ca0001', '教学管理', '0', 'FlagPlantformSection', '0', to_date('23-11-2008', 'dd-mm-yyyy'), '系统变量所属模块');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dec343b011dec37a95b0001', '本科', '0', 'FlagMaxXueli', '0', to_date('30-11-2008', 'dd-mm-yyyy'), '最大学历');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dec343b011dec381fe30002', '硕士', '1', 'FlagMaxXueli', '0', to_date('30-11-2008', 'dd-mm-yyyy'), '最大学历');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dec343b011dec389bba0003', '博士', '2', 'FlagMaxXueli', '0', to_date('30-11-2008', 'dd-mm-yyyy'), '最大学历');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dec343b011dec39a3370004', '博士后', '4', 'FlagMaxXueli', '0', to_date('30-11-2008', 'dd-mm-yyyy'), '最大学历');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dec343b011dec3a3cd80005', '本科', '0', 'FlagMaxXuewei', '0', to_date('30-11-2008', 'dd-mm-yyyy'), '最大学位');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dec343b011dec3ab41b0006', '硕士', '1', 'FlagMaxXuewei', '0', to_date('30-11-2008', 'dd-mm-yyyy'), '最大学位');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951df7720e011df78e5bbb0001', 'test', 'test', 'FlagPlatformSection', '0', to_date('02-12-2008', 'dd-mm-yyyy'), '123');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dfc7288011dfca6ddf90003', '未审核', '0', 'FlagTitleAdmission', '0', to_date('03-12-2008', 'dd-mm-yyyy'), '选题确认审核');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dfc7288011dfca77e210004', '通过', '1', 'FlagTitleAdmission', '0', to_date('03-12-2008', 'dd-mm-yyyy'), '选题确认审核');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951dfc7288011dfca8069b0005', '未通过', '2', 'FlagTitleAdmission', '0', to_date('03-12-2008', 'dd-mm-yyyy'), '选题确认审核');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e5ca987011e5cade0860001', '未提交', '0', 'FlagSyllabusLastUpdate', '0', to_date('22-12-2008', 'dd-mm-yyyy'), '开题报告最后操作人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e5ca987011e5cae6dd20002', '学生新提交', '1', 'FlagSyllabusLastUpdate', '0', to_date('22-12-2008', 'dd-mm-yyyy'), '开题报告最后操作人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e5ca987011e5caef8bc0003', '教师新批改', '2', 'FlagSyllabusLastUpdate', '0', to_date('22-12-2008', 'dd-mm-yyyy'), '开题报告最后操作人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e5d51ed011e5d53ca380001', '开题报告', '0', 'FlagPaperSection', '0', to_date('22-12-2008', 'dd-mm-yyyy'), '所属论文阶段');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e5d51ed011e5d5433b00002', '论文初稿', '1', 'FlagPaperSection', '0', to_date('22-12-2008', 'dd-mm-yyyy'), '所属论文阶段');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e5d51ed011e5d54a1cb0003', '论文终稿', '2', 'FlagPaperSection', '0', to_date('22-12-2008', 'dd-mm-yyyy'), '所属论文阶段');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e5d51ed011e5d5566050004', '学生', '0', 'FlagActionUser', '0', to_date('22-12-2008', 'dd-mm-yyyy'), '论文提交人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e5d51ed011e5d55b60a0005', '教师', '1', 'FlagActionUser', '0', to_date('22-12-2008', 'dd-mm-yyyy'), '论文提交人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e61aae3011e61ae928e0001', '未提交', '0', 'FlagDraftALastUpdate', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '初稿最后操作人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e61aae3011e61aefed50002', '学生新提交', '1', 'FlagDraftALastUpdate', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '初稿最后操作人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e61aae3011e61af72020003', '教师新批改', '2', 'FlagDraftALastUpdate', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '初稿最后操作人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e61aae3011e61b0649f0004', '未提交', '0', 'FlagFinalLastUpdate', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '终稿最后操作人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e61aae3011e61b0d3f30005', '学生新提交', '1', 'FlagFinalLastUpdate', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '终稿最后操作人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e61aae3011e61b13b580006', '教师新批改', '2', 'FlagFinalLastUpdate', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '终稿最后操作人');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e8a9658011e8ab1099c0001', '毕业论文重修申请', '10', 'ApplyType', '0', to_date('31-12-2008', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e90a70b011e90a9348c0001', '学位英语报名申请', '11', 'ApplyType', '0', to_date('01-01-2009', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e91f92a011e91fb68720001', '转学习中心申请', '12', 'ApplyType', '0', to_date('01-01-2009', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e91f92a011e91fbe62d0002', '转层次申请', '13', 'ApplyType', '0', to_date('01-01-2009', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880951e91f92a011e91fc490e0003', '转专业申请', '14', 'ApplyType', '0', to_date('01-01-2009', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880952114d7aa012114dc14600010', '未批改', '0', 'FlagIscheck', '0', to_date('06-05-2009', 'dd-mm-yyyy'), '交流园地上传作业是否批改');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880952114d7aa012114dc8af70011', '已批改', '1', 'FlagIscheck', '0', to_date('06-05-2009', 'dd-mm-yyyy'), '交流园地上传作业是否批改');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880971e15c3c5011e15d2c5700001', '不活动', '0', 'FlagIsActive', '0', to_date('08-12-2008', 'dd-mm-yyyy'), '是否活动状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880971e15c3c5011e15d361a10002', '活动', '1', 'FlagIsActive', '1', to_date('08-12-2008', 'dd-mm-yyyy'), '是否活动状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880971e19b68d011e19ba562c0001', '普通短信', '0', 'FlagSmsType', '1', to_date('09-12-2008', 'dd-mm-yyyy'), '短信类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880971e19b68d011e19bb24f60002', '定时短信', '1', 'FlagSmsType', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '短信类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880971e19e170011e1a5d46c20001', '未审核', '0', 'FlagSmsStatus', '1', to_date('09-12-2008', 'dd-mm-yyyy'), '短信状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880971e19e170011e1a5de89f0002', '已审核', '1', 'FlagSmsStatus', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '短信状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880971e5d95d6011e5d99ef200001', '驳回', '2', 'FlagSmsStatus', '0', to_date('22-12-2008', 'dd-mm-yyyy'), '短信状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('40288097205651880120567e0e660009', '日语', '1', 'DegreeEnglishType', '0', to_date('30-03-2009', 'dd-mm-yyyy'), '学位外语类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('40288097205651880120567eb574000a', '英语', '0', 'DegreeEnglishType', '1', to_date('30-03-2009', 'dd-mm-yyyy'), '学位外语类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028809c1d625bcf011d65d80e870002', '是', '1', 'FlagNoexam', '0', to_date('04-11-2008', 'dd-mm-yyyy'), '是否免试生');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028809c1d625bcf011d65d865800003', '否', '0', 'FlagNoexam', '1', to_date('04-11-2008', 'dd-mm-yyyy'), '是否免试生');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028809c1d625bcf011d65d949490004', '未发布', '0', 'FlagPublish', '1', to_date('04-11-2008', 'dd-mm-yyyy'), '录取信息是否发布');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028809c1d625bcf011d65d9c7df0005', '已发布', '1', 'FlagPublish', '0', to_date('04-11-2008', 'dd-mm-yyyy'), '录取信息是否发布');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028809c1d625bcf011d66fd0dda0006', '是', '1', 'FlagActive', '0', to_date('04-11-2008', 'dd-mm-yyyy'), '是否有效');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028809c1d625bcf011d66fdb39f0007', '否', '0', 'FlagActive', '1', to_date('04-11-2008', 'dd-mm-yyyy'), '是否有效');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91d9a6507011d9a675b0c0001', '无', '0', 'FlagTeacher', '1', to_date('14-11-2008', 'dd-mm-yyyy'), '是否有教师资格');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91d9a6507011d9a67e1820002', '有', '1', 'FlagTeacher', '0', to_date('14-11-2008', 'dd-mm-yyyy'), '是否有教师资格');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91d9a6507011d9a68fb3f0003', '其他专业', '2', 'FlagTeacher', '0', to_date('14-11-2008', 'dd-mm-yyyy'), '是否有教师资格');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91da9032f011da905da070001', '不通过', '0', 'FlagTeacherStatus', '1', to_date('17-11-2008', 'dd-mm-yyyy'), '教师资格审核是否通过');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91da9032f011da9068d4a0002', '通过', '1', 'FlagTeacherStatus', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '教师资格审核是否通过');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91da9032f011da9073f150003', '未审核', '2', 'FlagTeacherStatus', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '教师资格审核是否通过');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91da9032f011da9082ca20004', '其他不需要审核的', '3', 'FlagTeacherStatus', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '教师资格审核是否通过');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91da9aced011da9daf4d60002', '不通过', '0', 'FlagNoexamStatus', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '免试审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91da9aced011da9dc1d580003', '通过', '1', 'FlagNoexamStatus', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '免试审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91da9aced011da9dcb5a10004', '未审核', '2', 'FlagNoexamStatus', '1', to_date('17-11-2008', 'dd-mm-yyyy'), '免试审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91da9aced011da9dd93bc0005', '不需审核', '3', 'FlagNoexamStatus', '0', to_date('17-11-2008', 'dd-mm-yyyy'), '免试审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91dadc115011dadfcf26b0001', '未审核', '0', 'FlagRecStatus', '1', to_date('18-11-2008', 'dd-mm-yyyy'), '学生状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91dadc115011dadff536d0002', '已交费', '1', 'FlagRecStatus', '0', to_date('18-11-2008', 'dd-mm-yyyy'), '学生状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91dd1528e011dd158f6750001', '等待', '0', 'FlagMatriculate', '1', to_date('25-11-2008', 'dd-mm-yyyy'), '是否录取');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91dd1528e011dd15965c90002', '已录取', '1', 'FlagMatriculate', '0', to_date('25-11-2008', 'dd-mm-yyyy'), '是否录取');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91dd1528e011dd15b080f0003', '未录取', '2', 'FlagMatriculate', '0', to_date('25-11-2008', 'dd-mm-yyyy'), '是否录取');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91de68561011de6ab5da70006', '是', '1', 'FlagDefault', '0', to_date('29-11-2008', 'dd-mm-yyyy'), '是否为默认收费标准');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91de68561011de6ac39130007', '否', '0', 'FlagDefault', '1', to_date('29-11-2008', 'dd-mm-yyyy'), '是否为默认收费标准');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91de7e3a5011de7e733890001', '已审核', '3', 'FlagRecStatus', '0', to_date('29-11-2008', 'dd-mm-yyyy'), '学生状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91de7e3a5011de7e81f700002', '审核不通过', '4', 'FlagRecStatus', '0', to_date('29-11-2008', 'dd-mm-yyyy'), '学生状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91dfc6616011dfc6774930001', '社会招生', '0', 'FlagRecChannel', '1', to_date('03-12-2008', 'dd-mm-yyyy'), '招生渠道');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e06db5f011e06e047880001', '否', '0', 'FlagArrangeRoom', '1', to_date('05-12-2008', 'dd-mm-yyyy'), '是否作为安排入学考试考场的科目');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e06db5f011e06e0bf480002', '是', '1', 'FlagArrangeRoom', '0', to_date('05-12-2008', 'dd-mm-yyyy'), '是否作为安排入学考试考场的科目');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e4f1248011e4f19bdc70001', '不活动', '0', 'FlagIsactive', '1', to_date('19-12-2008', 'dd-mm-yyyy'), '新闻是否活动');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e4f1248011e4f1a2b360002', '活动', '1', 'FlagIsactive', '0', to_date('19-12-2008', 'dd-mm-yyyy'), '新闻是否活动');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e4f1248011e4f1b9e5e0003', '不通过', '0', 'FlagNewsStatus', '1', to_date('19-12-2008', 'dd-mm-yyyy'), '新闻审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e4f1248011e4f1c0ab40004', '通过', '1', 'FlagNewsStatus', '0', to_date('19-12-2008', 'dd-mm-yyyy'), '新闻审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e5ec11a011e5ec319950001', '普通投票', '0', 'FlagType', '1', to_date('22-12-2008', 'dd-mm-yyyy'), '调查问卷类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e5ec11a011e5ec372060002', '课程投票', '1', 'FlagType', '0', to_date('22-12-2008', 'dd-mm-yyyy'), '调查问卷类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e61bac6011e61bcbcd60001', '否', '0', 'FlagCanSuggest', '1', to_date('23-12-2008', 'dd-mm-yyyy'), '是否可以添加建议');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e61bac6011e61bd0f3c0002', '是', '1', 'FlagCanSuggest', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '是否可以添加建议');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e61bac6011e61be18ad0003', '否', '0', 'FlagLimitDiffip', '1', to_date('23-12-2008', 'dd-mm-yyyy'), '是否限制IP');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e61bac6011e61be6a770004', '是', '1', 'FlagLimitDiffip', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '是否限制IP');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e61bac6011e61bf07820005', '否', '0', 'FlagLimitDiffsession', '1', to_date('23-12-2008', 'dd-mm-yyyy'), '是否限制session');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e61bac6011e61bf60020006', '是', '1', 'FlagLimitDiffsession', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '是否限制session');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e61bac6011e61c006b20007', '否', '0', 'FlagViewSuggest', '1', to_date('23-12-2008', 'dd-mm-yyyy'), '是否可查看建议');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e61bac6011e61c05ec50008', '是', '1', 'FlagViewSuggest', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '是否可查看建议');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e635aa0011e635fcd2c0001', '单选题', '1', 'FlagQuestionType', '1', to_date('23-12-2008', 'dd-mm-yyyy'), '调查问卷问题类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e635aa0011e636032100002', '多选题', '2', 'FlagQuestionType', '0', to_date('23-12-2008', 'dd-mm-yyyy'), '调查问卷问题类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e6c256d011e6c3073130001', '未通过', '0', 'FlagCheck', '1', to_date('25-12-2008', 'dd-mm-yyyy'), '建议是否通过审核');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e6c256d011e6c30bd5b0002', '通过', '1', 'FlagCheck', '0', to_date('25-12-2008', 'dd-mm-yyyy'), '建议是否通过审核');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e72ceda011e72d836780001', '非优秀学生', '0', 'FlagAdvanced', '1', to_date('26-12-2008', 'dd-mm-yyyy'), '是否是优秀学生');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e72ceda011e72d8a9750002', '优秀学生', '1', 'FlagAdvanced', '0', to_date('26-12-2008', 'dd-mm-yyyy'), '是否是优秀学生');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e870af4011e870d9cf80001', '统考英语A免试申请', '4', 'ApplyType', '0', to_date('30-12-2008', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e870af4011e870e08350002', '统考英语B免试申请', '5', 'ApplyType', '0', to_date('30-12-2008', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e870af4011e870f2ed30003', '统考计算机免试申请', '6', 'ApplyType', '0', to_date('30-12-2008', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e9f2e2b011e9f4655100001', '免修免考申请', '15', 'ApplyType', '0', to_date('04-01-2009', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91e9fa272011e9fbe05ef0001', '免考', '5', 'FlagScoreStatus', '0', to_date('04-01-2009', 'dd-mm-yyyy'), '成绩状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91ecade03011ecae12bcd0001', '手动', '0', 'FlagIsauto', '1', to_date('12-01-2009', 'dd-mm-yyyy'), '系统短信点是否自动');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91ecade03011ecae19eab0002', '自动', '1', 'FlagIsauto', '0', to_date('12-01-2009', 'dd-mm-yyyy'), '系统短信点是否自动');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91f8745c3011f881fac850045', '平时成绩复查申请', '16', 'ApplyType', '0', to_date('18-02-2009', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91f8745c3011f882029850046', '作业成绩复查申请', '17', 'ApplyType', '0', to_date('18-02-2009', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91f8745c3011f8820a0c80047', '考试成绩复查申请', '18', 'ApplyType', '0', to_date('18-02-2009', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a91f8745c3011f88211ada0048', '主干课复查申请', '19', 'ApplyType', '0', to_date('18-02-2009', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a9201323450120136b71a40021', '分站不通过', '4', 'FlagNoexamStatus', '0', to_date('17-03-2009', 'dd-mm-yyyy'), '免试审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a9201323450120136c8e110022', '分站通过', '5', 'FlagNoexamStatus', '0', to_date('17-03-2009', 'dd-mm-yyyy'), '免试审核状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a92014074e0120140a7ece0001', '免修扣费', '10', 'FlagFeeType', '0', to_date('17-03-2009', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a92014074e0120140b478d0002', '取消免修退费', '11', 'FlagFeeType', '0', to_date('17-03-2009', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a92037626d012037822b3b0001', '未审核', '0', 'FlagXueliCheck', '1', to_date('24-03-2009', 'dd-mm-yyyy'), '学历验证状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a92037626d012037830d4e0002', '未通过', '1', 'FlagXueliCheck', '0', to_date('24-03-2009', 'dd-mm-yyyy'), '学历验证状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a92037626d0120378372700003', '暂通过', '2', 'FlagXueliCheck', '0', to_date('24-03-2009', 'dd-mm-yyyy'), '学历验证状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a92037626d012037844e3a0004', '通过', '3', 'FlagXueliCheck', '0', to_date('24-03-2009', 'dd-mm-yyyy'), '学历验证状态');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a9206b1e3201206b25a19a0001', '优秀学生干部', '2', 'ApplyType', '0', to_date('03-04-2009', 'dd-mm-yyyy'), '申请类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a920c66fed0120c697fa100006', '工商系统', '1', 'FlagRecChannel', '0', to_date('21-04-2009', 'dd-mm-yyyy'), '招生渠道');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a920c66fed0120c6986ec30007', '岭南集团', '2', 'FlagRecChannel', '0', to_date('21-04-2009', 'dd-mm-yyyy'), '招生渠道');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a920c66fed0120c698dec30008', '台企招生', '3', 'FlagRecChannel', '0', to_date('21-04-2009', 'dd-mm-yyyy'), '招生渠道');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a920dc57f00120dc5cd08c0001', '主持', '3', 'FlagTeacherType', '0', to_date('25-04-2009', 'dd-mm-yyyy'), '教师类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028821b1de3be9c011de3bfeaea0001', '是', '1', 'FlagIsEducation', '0', to_date('28-11-2008', 'dd-mm-yyyy'), '是否教育专业');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028821b1de3be9c011de3c068f30002', '否', '0', 'FlagIsEducation', '1', to_date('28-11-2008', 'dd-mm-yyyy'), '是否教育专业');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028821b1df6f6c7011df6fedf060001', '学费', '1', 'FlagFeeType', '1', to_date('02-12-2008', 'dd-mm-yyyy'), '费用类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028826a1db7bb01011db7e52d130001', '学生', '0', 'FlagRoleType', '0', to_date('20-11-2008', 'dd-mm-yyyy'), '角色类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028826a1db7bb01011db7e5b0da0002', '分站管理员', '2', 'FlagRoleType', '0', to_date('20-11-2008', 'dd-mm-yyyy'), '角色类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028826a1db7bb01011db7e623d70003', '总站管理员', '3', 'FlagRoleType', '0', to_date('20-11-2008', 'dd-mm-yyyy'), '角色类型');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028826a1e1bcbd0011e1bd8ab6b0004', '否', '0', 'FlagIstop', '1', to_date('09-12-2008', 'dd-mm-yyyy'), '是否置顶');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('4028826a1e1bcbd0011e1bd94f3d0005', '是', '1', 'FlagIstop', '0', to_date('09-12-2008', 'dd-mm-yyyy'), '是否置顶');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('5', '否', '0', 'FlagIspubliccourse', '0', to_date('02-11-2008 14:06:42', 'dd-mm-yyyy hh24:mi:ss'), '是否公选课');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('6', '是', '1', 'FlagIsMainCourse', '1', to_date('12-11-2008 14:15:06', 'dd-mm-yyyy hh24:mi:ss'), '是否主干课');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('7', '否', '0', 'FlagIsMainCourse', '0', to_date('12-11-2008 14:15:06', 'dd-mm-yyyy hh24:mi:ss'), '是否主干课');
insert into ENUM_CONST (ID, NAME, CODE, NAMESPACE, IS_DEFAULT, CREATE_DATE, NOTE)
values ('402880a9215235510121523d90790001', '业余', '6', 'FlagMajorType', '0', to_date('18-05-2009', 'dd-mm-yyyy'), '专业备注（跨专业 非跨专业 专业方向）');

commit;

--新闻类型
insert into PE_INFO_NEWS_TYPE (ID, NAME, NOTE)
values ('_xygg', '学院公告', '添加网院新闻');
insert into PE_INFO_NEWS_TYPE (ID, NAME, NOTE)
values ('_wydt', '网院动态', null);
insert into PE_INFO_NEWS_TYPE (ID, NAME, NOTE)
values ('_wjdt', '网教动态', null);
insert into PE_INFO_NEWS_TYPE (ID, NAME, NOTE)
values ('_zcfg', '政策法规', null);
commit;

--系统短信
insert into PE_SMS_SYSTEMPOINT (ID, CODE, NAME, FLAG_SMS_STATUS, FLAG_ISAUTO, NOTE, SQL_NOTE)
values ('001', '001', '生日祝福短信', '402880971e19e170011e1a5de89f0002', '402880a91ecade03011ecae19eab0002', '生日快乐!', 'select pr_stu.mobilephone mobilephone,pe_stu.reg_no login_id,pr_stu.email from pr_student_info pr_stu, pe_student pe_stu where  pe_stu.fk_student_info_id = pr_stu.id and to_char(pr_stu.birthday,''mm-dd'') = to_char(sysdate,''mm-dd'')');
insert into PE_SMS_SYSTEMPOINT (ID, CODE, NAME, FLAG_SMS_STATUS, FLAG_ISAUTO, NOTE, SQL_NOTE)
values ('002', '002', '通知未选课学生选课', '402880971e19e170011e1a5de89f0002', '402880a91ecade03011ecae12bcd0001', '您在当前学期还没有选下学期的课程，请在选课期内选课,谢谢。', 'select prStudentInfo.mobilephone mobilephone,' || chr(10) || '       peStudent.reg_no login_id,' || chr(10) || '       prStudentInfo.email' || chr(10) || '  from pe_student peStudent,' || chr(10) || '       pr_student_info prStudentInfo,' || chr(10) || '       enum_const enum' || chr(10) || ' where peStudent.Fk_Student_Info_Id = prStudentInfo.Id' || chr(10) || '       and' || chr(10) || '       peStudent.Flag_Student_Status = enum.id' || chr(10) || '       and' || chr(10) || '       enum.code = ''4''' || chr(10) || '       and' || chr(10) || '       peStudent.id in' || chr(10) || '       ((select peStudent1.Id from pe_student peStudent1) minus' || chr(10) || '        (select distinct elective.fk_stu_id' || chr(10) || '           from pr_tch_stu_elective elective' || chr(10) || '          where elective.fk_tch_opencourse_id in' || chr(10) || '                (select opencourse.id' || chr(10) || '                   from pr_tch_opencourse opencourse' || chr(10) || '                  where opencourse.fk_semester_id =' || chr(10) || '                        (select peSemester.Id' || chr(10) || '                           from pe_semester peSemester' || chr(10) || '                          where peSemester.Flag_Next_Active = ''1''))))');
commit;


--系统变量
insert into SYSTEM_VARIABLES (ID, NAME, VALUE, PATTERN, FLAG_PLATFORM_SECTION, FLAG_BAK)
values ('402880a91fa0dd1a011fa1c15a840043', 'paperRejoinNober', '1000', '1234', '402880951df7720e011df78e5bbb0001', null);
insert into SYSTEM_VARIABLES (ID, NAME, VALUE, PATTERN, FLAG_PLATFORM_SECTION, FLAG_BAK)
values ('402880951df7720e011df78f66550002', 'paperReplyRate', '0.7', '12345', '402880951df7720e011df78e5bbb0001', null);
insert into SYSTEM_VARIABLES (ID, NAME, VALUE, PATTERN, FLAG_PLATFORM_SECTION, FLAG_BAK)
values ('40348856h95jkdf7720e011df78f66550002', 'examAvoidScore', '75.0', '12345', '402880951df7720e011df78e5bbb0001', null);
insert into SYSTEM_VARIABLES (ID, NAME, VALUE, PATTERN, FLAG_PLATFORM_SECTION, FLAG_BAK)
values ('402880951ec85f3f011ec8a2ab140001', 'creditMustScore', '60', '12345', '402880951df7720e011df78e5bbb0001', null);
insert into SYSTEM_VARIABLES (ID, NAME, VALUE, PATTERN, FLAG_PLATFORM_SECTION, FLAG_BAK)
values ('402880a92014074e012014120e6a0003', 'examAvoidFee', '0.5', '123', '402880951df7720e011df78e5bbb0001', null);
commit;

--权限角色
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('3', '总站管理员', '4028826a1db7bb01011db7e623d70003', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('2', '分站管理员', '4028826a1db7bb01011db7e5b0da0002', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('1', '教师', '402880951dad22bd011dad3f441b0002', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('0', '学生', '402880951dad22bd011dad3edbcc0001', null);
insert into PE_PRI_ROLE (ID, NAME, FLAG_ROLE_TYPE, FLAG_BAK)
values ('402880a92137be1c012137db62100006', '分站职工', '4028826a1db7bb01011db7e5b0da0002', null);
commit;

--默认管理员
insert into SSO_USER (ID, LOGIN_ID, PASSWORD, FK_ROLE_ID, FLAG_ISVALID, FLAG_BAK, LOGIN_NUM)
values ('402880a920f4be700120f4c3d87a0001', 'whaty123', '1111', '3', null, null, 0);
insert into PE_MANAGER (ID, LOGIN_ID, NAME, TRUE_NAME, FK_SSO_USER_ID, FLAG_ISVALID, MOBILE_PHONE, PHONE, GENDER, ID_CARD, EMAIL, GRADUATION_INFO, GRADUATION_DATE, ADDRESS, ZHI_CHENG, WORK_TIME)
values ('402880a920f4be700120f4c3d8a90002', 'whaty123', 'whaty123/whaty', 'whaty', '402880a920f4be700120f4c3d87a0001', '2', '11111111111', '11111111', '402880911da481e0011da4963df60004', '111111111111111', '11111', '1', to_date('30-04-2009', 'dd-mm-yyyy'), '1', '1', '1');
commit;