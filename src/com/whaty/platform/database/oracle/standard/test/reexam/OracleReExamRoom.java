package com.whaty.platform.database.oracle.standard.test.reexam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
//import com.whaty.platform.test.exam.ExamRoom;
import com.whaty.platform.test.reexam.ReExamRoom;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleReExamRoom extends ReExamRoom {

	public OracleReExamRoom() {
	}

	public OracleReExamRoom(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select id,name,address,room_no,course_id,status,room_num,teacher from expendtest_examroom_info where id='"
				+ aid + "'";
		Log.setDebug("OracleReExamRoom@Method:OracleReExamRoom()=" + sql);
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setAddress(rs.getString("address"));
				this.setRoomNo(rs.getString("room_no"));
				this.setExamCourse(new OracleReExamCourse(rs
						.getString("course_id")));
				this.setRoomNum(rs.getString("room_num"));
				this.setTeacher(rs.getString("teacher"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleExamRoom(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into expendtest_examroom_info (id,name,address,room_no,course_id) values (to_char(s_test_examroom_info_id.nextval),'"
				+ this.getName()
				+ "','"
				+ this.getAddress()
				+ "','"
				+ this.getRoomNo()
				+ "','"
				+ this.getExamCourse().getId()
				+ "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleReExamRoom.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update expendtest_examroom_info set name='" + this.getName()
				+ "',address='" + this.getAddress() + "'," + "room_no='"
				+ this.getRoomNo() + "'," + "course_id='"
				+ this.getExamCourse().getId() + "'" + " where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleReExamRoom.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from expendtest_examroom_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleReExamRoom.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getExamRoomNo(String course_id, String batchId) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List roomNo = new ArrayList();
		sql = "select a.id as room_no from expendtest_examroom_info a,test_examcourse_info b where a.course_id='"
				+ course_id
				+ "' and a.course_id=b.id and b.test_batch_id='"
				+ batchId + "' order by a.id";
		Log.setDebug("OracleReExamRoom@Method:getExamRoomNo()=" + sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				roomNo.add(rs.getString("room_no"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleReExamRoom(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return roomNo;
	}

	public List getExamRooms(String course_id, String batchId, String siteId) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List roomList = new ArrayList();
		sql = "select room_no,address ,id,course_id,site_id,room_num from expendtest_examroom_info where course_id='"
				+ course_id
				+ "' and site_id = '"
				+ siteId
				+ "' order by to_number(room_no)";
		// Log.setDebug("OracleReExamRoom@Method:getExamRooms()="+sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleReExamRoom examRoom = new OracleReExamRoom();
				examRoom.setAddress(rs.getString("address"));
				examRoom.setId(rs.getString("id"));
				examRoom.setRoomNo(rs.getString("room_no"));
				examRoom.setRoomNum(rs.getString("room_num"));
				examRoom.setExamCourse(new OracleReExamCourse(rs
						.getString("course_id")));
				roomList.add(examRoom);
			}
		} catch (java.sql.SQLException e) {
			
			Log.setError("ȡб" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return roomList;
	}

	public int add(String course_id, String address, String room_num)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		int id = db.getSequenceNextId("s_test_examroom_info_id");
		sql = "insert into expendtest_examroom_info (id,course_id,address,room_num,room_no) select "
				+ id
				+ ",'"
				+ course_id
				+ "','"
				+ address
				+ "','"
				+ room_num
				+ "',nvl(max(nvl(room_no,0)),0)+1 from expendtest_examroom_info where course_id ='"
				+ course_id + "'";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleReExamRoom.add(String course_id, String address, String room_num) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int add(String course_id, String address, String room_num,
			String teacher, String nouse) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		int id = db.getSequenceNextId("s_expendtest_examroom_info_id");
		sql = "insert into expendtest_examroom_info (id,course_id,address,room_num,room_no,teacher) select "
				+ id
				+ ",'"
				+ course_id
				+ "','"
				+ address
				+ "','"
				+ room_num
				+ "',nvl(max(nvl(room_no,0)),0)+1,'"
				+ teacher
				+ "' from expendtest_examroom_info where course_id ='"
				+ course_id
				+ "'";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleReExamRoom.add(String course_id, String address, String room_num,String teacher, String nouse) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int add(String site_id, String course_id, String address,
			String room_num, String teacher, String nouse)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		int id = db.getSequenceNextId("s_test_examroom_info_id");
		sql = "insert into expendtest_examroom_info (id,course_id,address,room_num,room_no,site_id,teacher) select "
				+ id
				+ ",'"
				+ course_id
				+ "','"
				+ address
				+ "','"
				+ room_num
				+ "',nvl(max(nvl(room_no,0)),0)+1,'"
				+ site_id
				+ "','"
				+ teacher
				+ "' from expendtest_examroom_info where course_id ='"
				+ course_id + "'";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleReExamRoom.add(String site_id, String course_id, String address,String room_num, String teacher, String nouse) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getExamRoom(String course_id) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List list = new ArrayList();
		sql = "select id, address,room_num,teacher  from expendtest_examroom_info where course_id='"
				+ course_id + "'";
		// Log.setDebug("OracleReExamRoom@Method:getExamRoom()="+sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleReExamRoom examroom = new OracleReExamRoom();
				examroom.setId(rs.getString("id"));
				examroom.setRoomNum(rs.getString("room_num"));
				examroom.setAddress(rs.getString("address"));
				examroom.setTeacher(rs.getString("teacher"));
				OracleReExamCourse course = new OracleReExamCourse(course_id);
				examroom.setExamCourse(course);
				list.add(examroom);

			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleReExamRoom(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getSiteExamRoom(String site_id, String course_id) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List list = new ArrayList();
		sql = "select id, address,room_num,teacher   from expendtest_examroom_info where site_id = '"
				+ site_id + "' and course_id='" + course_id + "'";
		// Log.setDebug("OracleReExamRoom@Method:getExamRoom()="+sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleReExamRoom examroom = new OracleReExamRoom();
				examroom.setId(rs.getString("id"));
				examroom.setRoomNum(rs.getString("room_num"));
				examroom.setAddress(rs.getString("address"));
				examroom.setTeacher(rs.getString("teacher"));
				OracleReExamCourse course = new OracleReExamCourse(course_id);
				examroom.setExamCourse(course);
				list.add(examroom);

			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleReExamRoom(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int update(String id, String address, String num)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update expendtest_examroom_info set address='" + address
				+ "',room_num='" + num + "' where id='" + id + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleReExamRoom.update(String id, String address, String num) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update(String id, String address, String num, String teacher)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update expendtest_examroom_info set address='" + address
				+ "',room_num='" + num + "',teacher='" + teacher
				+ "' where id='" + id + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleReExamRoom.update(String id, String address, String num,String teacher) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int getTotalExamRoomNumSet(String course_id) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		int cout = 0;
		sql = "select nvl(room_num,0) as room_num  from expendtest_examroom_info where course_id='"
				+ course_id + "'";

		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				cout = cout + Integer.parseInt(rs.getString("room_num"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleReExamRoom(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return cout;
	}

	public int getSiteTotalExamRoomNumSet(String site_id, String course_id) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		MyResultSet rs1 = null;

		sql = "select course_id,total from (select course_id,count(id) as total from "
				+ "test_examuser_course where user_id in (select id from entity_student_info "
				+ "where site_id = '"
				+ site_id
				+ "') group by course_id) where course_id ='" + course_id + "'";
		int total = 0;
		try {
			db = new dbpool();
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				total = rs.getInt("total");
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		db = new dbpool();
		int cout = 0;
		sql = "select nvl(room_num,0) as room_num  from expendtest_examroom_info where site_id = '"
				+ site_id + "' and  course_id='" + course_id + "'";
		try {
			rs1 = db.executeQuery(sql);
			while (rs1 != null && rs1.next()) {
				cout = cout + Integer.parseInt(rs1.getString("room_num"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleReExamRoom(String aid) error" + sql);
		} finally {
			db.close(rs1);
			db = null;
		}

		db = new dbpool();
		int cout1 = 0;
		sql = "select b.id from entity_student_info a, test_examuser_course b where a.site_id = '"
				+ site_id
				+ "' and b.course_id = '"
				+ course_id
				+ "' and a.id = b.user_id and b.room_id is not null";

		cout1 = db.countselect(sql);
		if (total == cout && cout == cout1)
			cout = -1;
		return cout;
	}
	
	/**
	 * @author shu
	 * @param site_id
	 * @param course_id
	 * @param address
	 * @param room_num
	 * @param teacher
	 * @param nouse
	 * @return 俼,Ρ
	 * @throws PlatformException
	 */
		public int addAndStudent(String kaoqu_id,String edu_type_id,String major_id,String grade_id,String shenfen_id,String activeBatchId,List user_id_list_first,String site_id, String course_id, String address,
				String room_num, String teacher, String nouse)
				throws PlatformException {
			dbpool db = new dbpool();//
			MyResultSet rs = null;
			//update test_examuser_course t set t.room_id='1' where t.user_id='164' and t.course_id='148'	
			//update test_examuser_course t set t.room_id='1',desk_no ='1' where t.user_id='164' and t.course_id='148'			
			int id = db.getSequenceNextId("s_expendtest_examroom_info_id");
			room_num = String.valueOf(user_id_list_first.size());
			for(int j=0;j<user_id_list_first.size();j++){
				String user_id = (String)user_id_list_first.get(j);
				int k = j +1;//Ϊ0ʱΪ1.
				String m = "";
				if(k<=9){
					m = "0" +String.valueOf(k);
				}else{
					m = String.valueOf(k);
				}
				String sql1 = "update expendtest_examuser_course t set t.room_id='" + id + "',desk_no='" + m +"' where t.user_id='" + user_id + "' and "
					+" t.course_id='"+course_id +"'";
				int i = db.executeUpdate(sql1);
				UserAddLog.setDebug("OracleReExamRoom.addAndStudent(List user_id_list_first,String site_id, String course_id, String address,String room_num, String teacher, String nouse) SQL=" + sql1 + " COUNT=" + i + " DATE=" + new Date());
			}
	/*		sql = "insert into test_examroom_info (id,course_id,address,room_num,room_no,site_id,teacher) select "
					+ id
					+ ",'"
					+ course_id
					+ "','"
					+ address
					+ "','"
					+ room_num
					+ "',nvl(max(nvl(room_no,0)),0)+1,'"
					+ site_id
					+ "','"
					+ teacher
					+ "' from test_examroom_info where course_id ='"
					+ course_id + "'";
	*/			
			//int room_num_id = db.getSequenceNextId("s_test_examroom_info_id");
	//		String sql2 = "delete  from expendtest_examroom_info  where batch_id ='" + activeBatchId + "'";
	//		int  i1 = db.executeUpdate(sql2);
			String max_room = "select nvl(max(to_number(room_no)),0) as max_room_no from expendtest_examroom_info where batch_id = '"+activeBatchId+"'" ;
			rs = db.executeQuery(max_room);
			int max_room_no = 0;
			try {
				while (rs != null && rs.next()) {
					max_room_no = rs.getInt("max_room_no");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			int max_room_no_num = max_room_no+1; 
			String sql = "insert into expendtest_examroom_info (id,course_id,address,room_num,room_no,site_id,teacher,batch_id,kaoqu_id,edu_type_id,major_id,grade_id,shenfen_id) values("//select "
				+ id
				+ ",'"
				+ course_id
				+ "','"
				+ address
				+ "','"
				+ room_num
				+ "','"+max_room_no_num+"','"
			//	+ "',nvl(max(nvl(room_no,0)),0)+1,"
				+ site_id
				+ "','"
				+ teacher  +"','" + activeBatchId +"','"+kaoqu_id+"','"+edu_type_id+"','"+major_id+"','"+grade_id+"','"+shenfen_id+"')";
				//+ " from test_examroom_info where course_id ='"+ course_id + "'";
			
		//	System.out.println("sqlll--->"+sql);
			int i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleReExamRoom.addAndStudent(String kaoqu_id,String edu_type_id,String major_id,String grade_id,String shenfen_id,String activeBatchId,List user_id_list_first,String site_id, String course_id, String address,String room_num, String teacher, String nouse) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			db.close(rs);
			db = null;
			return i;
		}
		
		
		/**
		 * @author shu
		 * @param site_id
		 * @param course_id
		 * @param address
		 * @param room_num
		 * @param teacher
		 * @param nouse
		 * @return յǰѷõĿ
		 * @throws PlatformException
		 */
			public int cleanroom(String kaoqu_id,String edu_type_id,String major_id,String grade_id,String shenfen_id,String activeBatchId,List user_id_list_first,String site_id, String course_id, String address,
					String room_num, String teacher, String nouse)
					throws PlatformException {
				dbpool db = new dbpool();//
				MyResultSet rs = null;
			
				String sql = "delete  from expendtest_examroom_info  where batch_id ='" + activeBatchId + "'";
				int  i = db.executeUpdate(sql);
		
				UserAddLog.setDebug("OracleReExamRoom.cleanroom(String kaoqu_id,String edu_type_id,String major_id,String grade_id,String shenfen_id,String activeBatchId,List user_id_list_first,String site_id, String course_id, String address,String room_num, String teacher, String nouse) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
				db.close(rs);
				db = null;
				return i;
			}
			
			public int updatePlace(String[] ids,String[] places) throws PlatformException {
				 dbpool db = new dbpool();
					int count=0;
					for(int i=0;i<places.length;i++)
					{
						//if("".equals(places[i].toString())||"null".equals(places[i].toString()))
						//continue;
						String sql="update  expendtest_examroom_info t set t.address='"+places[i].toString()+"' where t.id ='"+ids[i].toString()+"'";	
						count+=db.executeUpdate(sql);
					}
					return count;
			
			}
	
}
