/**
 * 
 */
package com.whaty.platform.database.oracle.standard.standard.scorm.operation;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.datamodels.StudentSco;
import com.whaty.platform.standard.scorm.operation.UserData;
import com.whaty.platform.util.SearchProperty;

/**
 * @author Administrator
 *
 */
public class OracleUserData extends UserData {

	public OracleUserData() {
		
	}
	
	public OracleUserData(String user_id,String courseId) {
		
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.UserData#getSelectedCourses()
	 */
	public List getSelectedCourses() throws ScormException {
		OracleScormDataList dataList=new OracleScormDataList();
		List searchproperty=new ArrayList();
		List orderproperty=new ArrayList();
		searchproperty.add(new SearchProperty("student_id",this.getStudent_id()));
		return dataList.searchUserCourses(null,searchproperty,orderproperty);
	}
	
	public StudentSco getSudentSco(String studentId,String courseId) {
		StudentSco studentSco = new StudentSco();
		try{
			StringBuilder sb = new StringBuilder();//查询各sco结点学生的学习情况
			sb.append("select a.title,                      ");
			sb.append("       b.lesson_location,            ");
			sb.append("       b.status,                     ");
			sb.append("       b.credit,                     ");
			sb.append("       a.id,                         ");
			sb.append("       a.sequence,                   ");
			sb.append("       a.thelevel,b.total_time                    ");
			sb.append("  from scorm_course_item a,          ");
			sb.append("       (select b.status,             ");
			sb.append("               b.credit,             ");
			sb.append("               b.total_time,         ");
			sb.append("               b.lesson_location,    ");
			sb.append("               b.system_id,          ");
			sb.append("               b.course_id           ");
			sb.append("          from scorm_stu_sco b       ");
			sb.append("         where b.student_id = '"+studentId+"' and b.course_id = '"+courseId+"') b  ");
			sb.append(" where a.course_id = b.course_id(+)  ");
			sb.append("   and a.id = b.system_id(+) and a.course_id = '"+courseId+"'         ");
			sb.append(" order by a.sequence asc				");//按序列正序排列
			dbpool db = new dbpool();
			MyResultSet rs = db.executeQuery(sb.toString());
			List<StudentSco> parentList = new ArrayList<StudentSco>();//存储包含子结点的sco，按顺序依次存储
			parentList.add(studentSco);
			StudentSco privNode = null;//记录集中的当前sco的前一个sco
			int privLevel = -1;
			//遍历树
			if(rs!=null&&rs.next()) {
				StudentSco first = new StudentSco();
				first.setSid(rs.getString("id"));//sco id
				first.setTitle(rs.getString("title"));//sco 标题
				first.setHref(rs.getString("lesson_location"));//sco 链接
				first.setScore(rs.getString("credit"));//sco 分数
				first.setStatus(rs.getString("status"));//sco 完成状态
				first.setDuration(rs.getString("total_time"));//sco 学习时长
				first.setLevel(rs.getInt("thelevel"));//sco 级别
				privLevel = first.getLevel();
				privNode = first;//把第一个结点设为前结点
				parentList.get(0).getItem().add(first);//父节点最后一个结点添加第一个结点
				while(rs.next()) {//开始遍历剩余的节点
					StudentSco node = new StudentSco();
					node.setSid(rs.getString("id"));
					node.setTitle(checkText(rs.getString("title")));//标题需要做特殊字符处理
					node.setHref(rs.getString("lesson_location"));
					node.setScore(rs.getString("credit"));
					node.setStatus(rs.getString("status"));
					node.setDuration(rs.getString("total_time"));
					node.setLevel(rs.getInt("thelevel"));
					int level = node.getLevel();//当前sco 级别
					if(level>privLevel) {//当当前级别低于前结点界级别时，父节点列表添加前sco结点
						parentList.add(privNode);
					} else if(level<privLevel) {//当当前级别高于于前结点界级别时，移除父节点列表中最后一个结点，因为此结点至少与父节点列表最后一个结点同级别
						parentList.remove(parentList.size()-1);
						for(int i = parentList.size()-1 ; i> -1 ; i--) {//从父节点列表最后一个元素开始比较，一直当列表中结点级别高于当前结点时，否则结点从父节点列表中删除
							int tmpLevel = parentList.get(parentList.size()-1).getLevel();
							if(tmpLevel<level) {
								break;
							} else {
								parentList.remove(parentList.size()-1);
							}
						}
					} else {
						//同级结点不做处理
					}
					parentList.get(parentList.size()-1).getItem().add(node);//添加当前结点到父节点列表最后一个元素
					privNode = node;//把当前结点设为前结点
					privLevel = level;//把当前级别设为前级别
				}
			}
			db.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentSco;
	}
	
	/**
	 * 添加一个check 的方法，用于检查，title中是否有特殊字符；
	 * @param scoData
	 * @return
	 */
	private String checkText(String text){
		text = text.replace("\\", "\\\\");
		text = text.replace("\"", "\\\"");
		text = text.replace("<", "&lt;");
		text = text.replace(">", "&lg;");
		return text; 
	}
}
