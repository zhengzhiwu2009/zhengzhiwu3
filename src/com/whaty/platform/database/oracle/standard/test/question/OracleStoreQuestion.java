package com.whaty.platform.database.oracle.standard.test.question;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.logmanage.OracleLogManage;
import com.whaty.platform.entity.dao.hibernate.GeneralHibernateDao;
import com.whaty.platform.test.question.StoreQuestion;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleStoreQuestion extends StoreQuestion {
	private static final Log log = LogFactory.getLog(GeneralHibernateDao.class);
	public OracleStoreQuestion() {

	}
	
	public OracleStoreQuestion(String id) {
		String sql = "select id,title,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate,diff,keyword,questioncore,lore,"
				+ "cognizetype,purpose,referencescore,referencetime,studentnote,teachernote,type ,remark from "
				+ "test_storequestion_info where id='" + id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setCreatDate(rs.getString("creatdate"));
				this.setCreatUser(rs.getString("creatuser"));
				this.setDiff(rs.getString("diff"));
				this.setQuestionCore(rs.getString("questioncore"));
				this.setKeyWord(rs.getString("keyword"));
				this.setLore(rs.getString("lore"));
				this.setCognizeType(rs.getString("cognizetype"));
				this.setPurpose(rs.getString("purpose"));
				this.setReferenceScore(rs.getString("referencescore"));
				this.setReferenceTime(rs.getString("referencetime"));
				this.setStudentNote(rs.getString("studentnote"));
				this.setTeacherNote(rs.getString("teachernote"));
				this.setType(rs.getString("type"));
				this.setRemark(rs.getString("remark"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	public int add() throws PlatformException {
		String sql = "insert into test_storequestion_info(id,title,creatuser,creatdate,diff,keyword,lore,"
				+ "cognizetype,purpose,referencescore,referencetime,teachernote,studentnote,questioncore,type,remark) values (to_char(s_test_storequestion_info.nextval), '"
				+ this.getTitle()
				+ "', '"
				+ this.getCreatUser()
				+ "', to_date('"
				+ this.getCreatDate()
				+ "','yyyy-mm-dd'), '"
				+ this.getDiff()
				+ "', '"
				+ this.getKeyWord()
				+ "', '"
				+ this.getLore()
				+ "', '"
				+ this.getCognizeType()
				+ "', '"
				+ this.getPurpose()
				+ "', '"
				+ this.getReferenceScore()
				+ "', '"
				+ this.getReferenceTime()
				+ "', '"
				+ this.getTeacherNote()
				+ "', '"
				+ this.getStudentNote()
				+ "',? "
				+ ", '"
				+ this.getType()
				+ "','"+this.getRemark()+"' )";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql,this.getQuestionCore());
		UserAddLog.setDebug("OracleStoreQuestion.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		String sql = "update test_storequestion_info set title='"
				+ this.getTitle() + "', creatuser='" + this.getCreatUser()
				+ "', creatdate=to_date('" + this.getCreatDate()
				+ "','yyyy-mm-dd'), diff='" + this.getDiff() + "', keyword='"
				+ this.getKeyWord() + "', questioncore=?,lore='" + this.getLore()
				+ "',cognizetype='" + this.getCognizeType() + "',"
				+ "purpose='" + this.getPurpose() + "',referencescore='"
				+ this.getReferenceScore() + "'," + "referencetime='"
				+ this.getReferenceTime() + "',studentnote='"
				+ this.getStudentNote() + "' " + ",teachernote='"
				+ this.getTeacherNote() + "',type='" + this.getType()
				+ "',remark ='"+this.getRemark()+"' where id='" + this.getId() + "'";
		//System.out.println("UpdateSQL:" + sql);
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql,this.getQuestionCore());
		UserUpdateLog.setDebug("OracleStoreQuestion.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());      	
		return i;
	}

	public int delete() throws PlatformException {
		String sql = "delete from test_storequestion_info where id='"
				+ this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleStoreQuestion.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		OracleLogManage logs =new OracleLogManage();
		logs.insertLog("OracleStoreQuestion.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date(), "删除题库题目");
		//log.debug("删除题目操作：");
		return i;
	}
	
	public int deleteAll(String loreDirId) throws PlatformException {
		String sql = "delete from test_storequestion_info tsi "
				+ "where tsi.lore in (select tli.id "
				+ "from test_lore_info tli "
				+ "join test_lore_dir tld on tli.loredir = tld.id "
				+ "where tld.id = '"
				+ loreDirId + "')";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleStoreQuestion.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		OracleLogManage logs =new OracleLogManage();
		logs.insertLog("OracleStoreQuestion.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date(), "删除题库所有题目");
		//log.debug("删除题目操作：");
		return i;
	}
	
	public int batchDownload(String[] ids) throws PlatformException {
		int n = 0;
		String[] col_name = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
		String sql = "select case when tsi.type = 'DANXUAN' then '单选' when tsi.type = 'DUOXUAN' then '多选' else '判断' end type, " +
				"tsi.purpose,tsi.title,tsi.referencescore,tsi.questioncore,tli.name " +
				"from test_storequestion_info tsi join test_lore_info tli on tsi.lore = tli.id " +
				"join test_lore_dir tld on tli.loredir = tld.id where tsi.id in ('";
		for(int i = 0; i < ids.length; i++) {
			sql += ids[i] + "','";
		}
		sql += "')";
		dbpool db = new dbpool();
		MyResultSet mrs = db.execuQuery(sql);
		ResultSet rs = mrs.getMyrset();
		StringReader sr = null;
		InputSource is = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc = null;
		int count = 0;
		try {
			while(rs.next()) {
				String str = rs.getString("questioncore");
				sr = new StringReader(str);
				is = new InputSource(sr);
				builder = factory.newDocumentBuilder();
				doc = builder.parse(is);
				NodeList nodes = doc.getElementsByTagName("index");
				if (nodes.getLength() > count) {
					count = nodes.getLength();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		try {
			HSSFSheet sheet = wb.createSheet("题库问题列表");
			HSSFRow subRow = sheet.createRow((int) 0);
			HSSFCell subCell = subRow.createCell((short) 0);
			subCell.setCellValue("题目类型");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 1);
			subCell.setCellValue("题目用途");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 2);
			subCell.setCellValue("题目名称");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 3);
			subCell.setCellValue("建议题目分值");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 4);
			subCell.setCellValue("题目内容");
			subCell.setCellStyle(style);
			for(int i = 0; i < count; i++) {
				subCell = subRow.createCell((short) (5 + i));
				subCell.setCellValue("选项" + col_name[i]);
				subCell.setCellStyle(style);
			}
			subCell = subRow.createCell((short) (5 + count));
			subCell.setCellValue("答案");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) (6 + count));
			subCell.setCellValue("所属知识点");
			subCell.setCellStyle(style);
			rs.beforeFirst();
			
			int temp = 0;
			while(rs.next()) {
				subRow = sheet.createRow((int) temp + 1);
				subRow.createCell((short) 0).setCellValue(rs.getString("type"));
				subRow.createCell((short) 1).setCellValue(rs.getString("purpose").replace("KAOSHI", "课后测验").replace("ZUOYE", "随堂练习"));
				subRow.createCell((short) 2).setCellValue(rs.getString("title"));
				subRow.createCell((short) 3).setCellValue(rs.getString("referencescore"));
				String str = rs.getString("questioncore");
				sr = new StringReader(str);
				is = new InputSource(sr);
				NodeList nodes = null;
				try {
					builder = factory.newDocumentBuilder();
					doc = builder.parse(is);
					nodes = doc.getElementsByTagName("body");
					Node node = nodes.item(0);
					subRow.createCell((short) 4).setCellValue(node.getTextContent());
					nodes = null;
					nodes = doc.getElementsByTagName("index");
					for(int i = 0; i < nodes.getLength(); i++) {
						node = nodes.item(i);
						subRow.createCell((short) (5 + i)).setCellValue(node.getTextContent());
					}
					nodes = null;
					nodes = doc.getElementsByTagName("answer");
					node = nodes.item(0);
					subRow.createCell((short) (5 + count)).setCellValue(node.getTextContent());
					subRow.createCell((short) (6 + count)).setCellValue(rs.getString("name"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				temp ++;
			}
			autoWidth(sheet, 8);
			
			Calendar c = Calendar.getInstance();
			String tm = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日" + c.get(Calendar.HOUR_OF_DAY) + "时" + c.get(Calendar.MINUTE) + "分"
					+ c.get(Calendar.SECOND) + "秒" + "";
			String excelName = "课程题库(" + tm + ").xls";
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + toUtf8String(excelName));
			ServletOutputStream out = response.getOutputStream();
			wb.write(out);
			if (out != null) {
				out.flush();
				out.close();
			}
			n = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(mrs);
		}
		return n;
	}
	
	public int downloadAll(String loreDirId) throws PlatformException {
		int n = 0;
		String[] col_name = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
		String sql = "select case when tsi.type = 'DANXUAN' then '单选' when tsi.type = 'DUOXUAN' then '多选' else '判断' end type, " +
				"tsi.purpose,tsi.title,tsi.referencescore,tsi.questioncore,tli.name ,tsi.remark " +
				"from test_storequestion_info tsi join test_lore_info tli on tsi.lore = tli.id " +
				"join test_lore_dir tld on tli.loredir = tld.id where tld.id = '" + loreDirId + "'";
		dbpool db = new dbpool();
		MyResultSet mrs = db.execuQuery(sql);
		ResultSet rs = mrs.getMyrset();
		StringReader sr = null;
		InputSource is = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc = null;
		int count = 0;
		try {
			while(rs.next()) {
				String str = rs.getString("questioncore");
				sr = new StringReader(str);
				is = new InputSource(sr);
				builder = factory.newDocumentBuilder();
				doc = builder.parse(is);
				NodeList nodes = doc.getElementsByTagName("index");
				if (nodes.getLength() > count) {
					count = nodes.getLength();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		try {
			HSSFSheet sheet = wb.createSheet("题库问题列表");
			HSSFRow subRow = sheet.createRow((int) 0);
			HSSFCell subCell = subRow.createCell((short) 0);
			subCell.setCellValue("题目类型");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 1);
			subCell.setCellValue("题目用途");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 2);
			subCell.setCellValue("题目名称");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 3);
			subCell.setCellValue("建议题目分值");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 4);
			subCell.setCellValue("题目内容");
			subCell.setCellStyle(style);
			for(int i = 0; i < count; i++) {
				subCell = subRow.createCell((short) (5 + i));
				subCell.setCellValue("选项" + col_name[i]);
				subCell.setCellStyle(style);
			}
			subCell = subRow.createCell((short) (5 + count));
			subCell.setCellValue("答案");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) (6 + count));
			subCell.setCellValue("所属知识点");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) (7 + count));
			subCell.setCellValue("题目描述");
			subCell.setCellStyle(style);
			rs.beforeFirst();
			
			int temp = 0;
			while(rs.next()) {
				subRow = sheet.createRow((int) temp + 1);
				subRow.createCell((short) 0).setCellValue(rs.getString("type"));
				subRow.createCell((short) 1).setCellValue(rs.getString("purpose").replace("KAOSHI", "课后测验").replace("ZUOYE", "随堂练习"));
				subRow.createCell((short) 2).setCellValue(rs.getString("title"));
				subRow.createCell((short) 3).setCellValue(rs.getString("referencescore"));
				String str = rs.getString("questioncore");
				sr = new StringReader(str);
				is = new InputSource(sr);
				NodeList nodes = null;
				NodeList context = null;
				try {
					builder = factory.newDocumentBuilder();
					doc = builder.parse(is);
					nodes = doc.getElementsByTagName("body");
					Node node = nodes.item(0);
					subRow.createCell((short) 4).setCellValue(node.getTextContent());
					nodes = null;
					nodes = doc.getElementsByTagName("index");
					context= doc.getElementsByTagName("content");
					for(int i = 0; i < nodes.getLength(); i++) {
						node = nodes.item(i);
						subRow.createCell((short) (5 + i)).setCellValue(context.item(i).getTextContent());
					}
					nodes = null;
					nodes = doc.getElementsByTagName("answer");
					node = nodes.item(0);
					subRow.createCell((short) (5 + count)).setCellValue(node.getTextContent());
					subRow.createCell((short) (6 + count)).setCellValue(rs.getString("name"));
					subRow.createCell((short) (7 + count)).setCellValue(rs.getString("remark"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				temp ++;
			}
			autoWidth(sheet, 8);
			
			Calendar c = Calendar.getInstance();
			String tm = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日" + c.get(Calendar.HOUR_OF_DAY) + "时" + c.get(Calendar.MINUTE) + "分"
					+ c.get(Calendar.SECOND) + "秒" + "";
			String excelName = "课程题库(" + tm + ").xls";
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + toUtf8String(excelName));
			ServletOutputStream out = response.getOutputStream();
			wb.write(out);
			if (out != null) {
				out.flush();
				out.close();
			}
			n = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(mrs);
		}
		return n;
	}
	
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 设置sheet列宽
	 * 
	 * @param sheet
	 * @param maxColNum
	 */
	public void autoWidth(HSSFSheet sheet, int maxColNum) {
		for (int columnNum = 0; columnNum < maxColNum; columnNum++) {
			sheet.autoSizeColumn((short) columnNum);
		}

	}
}
