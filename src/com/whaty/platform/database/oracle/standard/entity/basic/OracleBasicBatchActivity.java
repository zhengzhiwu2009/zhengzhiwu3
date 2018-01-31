package com.whaty.platform.database.oracle.standard.entity.basic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.BasicBatchActivity;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.util.log.UserAddLog;

public class OracleBasicBatchActivity implements BasicBatchActivity {

	public void courseAddBatch(List courseList) throws PlatformException {
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		for (int i = 0; i < courseList.size(); i++) {
			Course course = (Course) courseList.get(i);
			String sql = "select id from entity_course_info where id='"
					+ course.getId() + "'";
			if (db.countselect(sql) > 0) {
				exceptionList.add(course.getId() + ",�ÿγ�ID�Ѵ���!");
				continue;
			}
			sql = "insert into entity_course_info (id,name,credit,course_time,major_id,exam_type,course_type,teaching_type,course_status,ref_book,note,standard_fee,drift_fee,text_book,text_book_price,re0,re1,re2,re3,re4) values ('"
					+ course.getId()
					+ "','"
					+ course.getName()
					+ "',"
					+ course.getCredit()
					+ ",'"
					+ course.getCourse_time()
					+ "','"
					+ course.getMajor_id()
					+ "','"
					+ course.getExam_type()
					+ "','"
					+ course.getCourse_type()
					+ "','"
					+ course.getTeaching_type()
					+ "','"
					+ course.getCourse_status()
					+ "','"
					+ course.getRef_book()
					+ "',?,'"
					+ course.getStandard_fee()
					+ "','"
					+ course.getDrift_fee()
					+ "','"
					+ course.getText_book()
					+ "','"
					+ course.getText_book_price()
					+ "','"
					+ course.getRedundance0()
					+ "','"
					+ course.getRedundance1()
					+ "','"
					+ course.getRedundance2()
					+ "','"
					+ course.getRedundance3()
					+ "','"
					+ course.getRedundance4()
					+ "')";
			int suc = db.executeUpdate(sql, course.getNote());
			if ( suc < 1) {
				exceptionList.add("(" + course.getId() + ")" + course.getName()
						+ "�������");
			} else {
				count++;
			}
			UserAddLog.setDebug("OracleBasicBatchActivity.courseAddBatch(List courseList) SQL=" + sql + " COUNT=" + suc + " DATE=" + new Date());		
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤���ٲ���");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�����" + count + "�ſγ�";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�����" + count + "�ſγ�";
			throw new PlatformException(except);
		}
	}

	public void courseAddBatch(List courseList, List major)
			throws PlatformException {
		dbpool db = new dbpool();
		ArrayList exceptionList = new ArrayList();
		String except = "";
		int count = 0;
		for (int i = 0; i < courseList.size(); i++) {
			Course course = (Course) courseList.get(i);
			String sql = "select id from entity_course_info where id='"
					+ course.getId() + "'";
			if (db.countselect(sql) > 0) {
				exceptionList.add(course.getId() + ",�ÿγ�ID�Ѵ���!");
				continue;
			}
			if (!major.contains(course.getMajor_id())) {
				exceptionList.add(course.getMajor_id() + ",��רҵID���ڱ�����Ա����Χ��!");
				continue;
			}
			sql = "insert into entity_course_info (id,name,credit,course_time,major_id,exam_type,course_type,teaching_type,course_status,ref_book,note,standard_fee,drift_fee,text_book,text_book_price,re0,re1,re2,re3,re4) values ('"
					+ course.getId()
					+ "','"
					+ course.getName()
					+ "',"
					+ course.getCredit()
					+ ",'"
					+ course.getCourse_time()
					+ "','"
					+ course.getMajor_id()
					+ "','"
					+ course.getExam_type()
					+ "','"
					+ course.getCourse_type()
					+ "','"
					+ course.getTeaching_type()
					+ "','"
					+ course.getCourse_status()
					+ "','"
					+ course.getRef_book()
					+ "',?,'"
					+ course.getStandard_fee()
					+ "','"
					+ course.getDrift_fee()
					+ "','"
					+ course.getText_book()
					+ "','"
					+ course.getText_book_price()
					+ "','"
					+ course.getRedundance0()
					+ "','"
					+ course.getRedundance1()
					+ "','"
					+ course.getRedundance2()
					+ "','"
					+ course.getRedundance3()
					+ "','"
					+ course.getRedundance4()
					+ "')";
			int suc = db.executeUpdate(sql, course.getNote());
			if (suc < 1) {
				exceptionList.add("(" + course.getId() + ")" + course.getName()
						+ "�������");
			} else
				count++;
			UserAddLog.setDebug("OracleBasicBatchActivity.courseAddBatch(List courseList, List major) SQL=" + sql + " COUNT=" + suc + " DATE=" + new Date());
		}
		db = null;
		if (exceptionList.size() > 0) {
			exceptionList.add("���֤���ٲ���");
			for (int i = 0; i < exceptionList.size(); i++) {
				except += (String) exceptionList.get(i) + "<br>";
			}
			if (count > 0)
				except += "�ɹ�����" + count + "�ſγ�";
			throw new PlatformException(except);
		} else {
			except += "�ɹ�����" + count + "�ſγ�";
			throw new PlatformException(except);
		}
	}

	public void bookAddBatch(String srcFile) {
		List sqlList = new ArrayList();
		try {
			Workbook w = Workbook.getWorkbook(new File(srcFile));
			Sheet sheet = w.getSheet(0);
			int rows = sheet.getRows();

			for (int i = 2; i < rows; i++) {
				String id = sheet.getCell(0, i).getContents().trim();
				String name = sheet.getCell(1, i).getContents().trim();
				String editor = sheet.getCell(2, i).getContents().trim();
				String publisher = sheet.getCell(3, i).getContents().trim();
				String isbn = sheet.getCell(4, i).getContents().trim();
				String publishDate = sheet.getCell(5, i).getContents().trim();
				String price = sheet.getCell(6, i).getContents().trim();
				String note = sheet.getCell(7, i).getContents().trim();
				String sql = "insert into entity_teachbook_info (id,teachbook_name,publishhouse,maineditor,isbn,publish_date,price,note) values ("
						+ "'"
						+ id
						+ "','"
						+ name
						+ "','"
						+ publisher
						+ "','"
						+ editor
						+ "','"
						+ isbn
						+ "','"
						+ publishDate
						+ "','"
						+ price + "','" + note + "')";
				UserAddLog.setDebug("OracleBasicBatchActivity.courseAddBatch(List courseList, List major) SQL=" + sql + "  DATE=" + new Date());
				sqlList.add(sql);
			}
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlList);
		} catch (BiffException e) {
			
		} catch (IOException e) {
			
		}

	}
}
