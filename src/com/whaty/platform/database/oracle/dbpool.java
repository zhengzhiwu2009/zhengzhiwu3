package com.whaty.platform.database.oracle;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class dbpool {
	private Context env;
	private DataSource source;
	// private HashMap threadConnectionMap;
	public static ConcurrentMap threadConnectionMap = new ConcurrentHashMap();

	/**
	 * 构造方法初始化
	 * 
	 * @param jdbc_str
	 * @roseuid 414D8D3F0263
	 * @author linjie
	 */
	public dbpool(java.lang.String jdbc_str) {
		// threadConnectionMap = new HashMap();
		try {
			env = (Context) new InitialContext().lookup("java:comp/env");
			source = (DataSource) env.lookup(jdbc_str);
		} catch (Exception e) {
			System.out.println("create dbpool error 1!" + e.toString());
		}
	}

	/**
	 * 无参数构造方法
	 * 
	 * @roseuid 414D6EBB0256
	 * @author linjie
	 */
	public dbpool() {
		// threadConnectionMap = new HashMap();
		try {

			// ********resin pool
			env = (Context) new InitialContext().lookup("java:comp/env");
			source = (DataSource) env.lookup("jdbc/hzph");

			// *****weblogic pool config
			// source=(DataSource) (new
			// InitialContext()).lookup("jdbc/whatymanager4");
		} catch (Exception e) {
			System.out.println("create dbpool error!" + e.toString());
		}
	}

	/**
	 * 获取数据库jdbc连接操作
	 * 
	 * @author linjie
	 * @throws SQLException
	 */
	public Connection getConn() throws SQLException {
		Connection conn = null;
		try {
			conn = source.getConnection();
		} catch (SQLException e) {
			System.out.println("public Connection getConn hzph error");
		} catch (NullPointerException e) {
			System.out.println("error hzph jdbc");
		}
		return conn;
	}

	/**
	 * 根据url,用户名，密码进行数据库的连接
	 * 
	 * @param jdbcURLString
	 * @param userName
	 * @param userPassword
	 * @author linjie
	 */
	public static Connection getDirectConn(String jdbcURLString, String userName, String userPassword) {
		Connection conn = null;
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(jdbcURLString, userName, userPassword);
		} catch (Exception ab) {
			System.out.println("error Direct jdbc!");
		}
		return conn;
	}

	/**
	 * 根据执行sql语句返回结果集
	 * 
	 * @param sqlStr
	 * @return MyResultSet
	 * @author linjie
	 */
	public MyResultSet executeQuery(String sqlStr) {
		ResultSet rset = null;
		Statement stmt = null;
		int fail_conn = 0;
		Connection _conn = null;
		MyResultSet Myrset = new MyResultSet();
		try {
			// _conn = source.getConnection();
			_conn = getConn();

			if (_conn != null) {
				stmt = _conn.createStatement();
				rset = stmt.executeQuery(sqlStr);
				if (rset == null) {
					_conn.close();
					return null;
				}
				Myrset.setMyResuleSet(rset);
				// threadConnectionMap.put(rset, _conn);
				threadConnectionMap.put(rset, new dbconn(sqlStr, _conn));
			}
		} catch (Exception e) {
			System.out.println("Error code: " + e + " ");
			System.out.println("on execute :" + sqlStr);
			fail_conn = 1;
		}

		// return Myrset;
		if (fail_conn == 1) {

			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e2) {
				System.out.println("----------------------");
				System.out.println("can't close ");

			}
			return null;

		} else {
			return Myrset;
		}
	}

	/**
	 * 根据执行sql语句返回结果集
	 * 
	 * @param sqlStr
	 * @return MyResultSet
	 * @author linjie
	 */
	public MyResultSet execuQuery(String sqlStr) {
		ResultSet rset = null;
		Statement stmt = null;
		int fail_conn = 0;
		Connection _conn = null;
		MyResultSet Myrset = new MyResultSet();
		try {
			// _conn = source.getConnection();
			_conn = getConn();

			if (_conn != null) {
				stmt = _conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rset = stmt.executeQuery(sqlStr);
				if (rset == null) {
					_conn.close();
					return null;
				}
				Myrset.setMyResuleSet(rset);
				// threadConnectionMap.put(rset, _conn);
				threadConnectionMap.put(rset, new dbconn(sqlStr, _conn));
			}
		} catch (SQLException e) {
			System.out.println("Error code: " + e + " ");
			System.out.println("on execute :" + sqlStr);
			fail_conn = 1;
		}

		// return Myrset;
		if (fail_conn == 1) {

			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e2) {
				System.out.println("----------------------");
				System.out.println("can't close ");

			}
			return null;

		} else {
			return Myrset;
		}
	}

	// public MyResultSet execute(String sqlStr)
	// {
	// return executeQuery(sqlStr);
	// }

	/**
	 * 根据执行sql语句分页返回结果集
	 * 
	 * @param sqlStr
	 * @param pageInt
	 * @param pageSize
	 * @return MyResultSet
	 * @author linjie
	 */
	public MyResultSet execute(String sqlStr, int pageInt, int pageSize) {
		Statement stmt = null;
		ResultSet rset = null;
		MyResultSet Myrset = new MyResultSet();
		Connection _conn = null;
		try {
			// _conn = source.getConnection();
			_conn = getConn();
			if (_conn != null) {
				stmt = _conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rset = stmt.executeQuery(sqlStr);
				rset.absolute((pageInt - 1) * pageSize + 1);
				if (rset == null) {
					_conn.close();
					return null;
				}
				Myrset.setMyResuleSet(rset);
				// Myrset.setMyConnection(conn);
				// threadConnectionMap.put(rset, _conn);
				threadConnectionMap.put(rset, new dbconn(sqlStr, _conn));
			}
		} catch (SQLException e) {
			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e2) {
				System.out.println("----------------------");
				System.out.println("can't close ");

			}

			System.out.println("----------------------");
			System.out.println("Error code: " + e + "");
			System.out.println("on execute :" + sqlStr);
			return null;
		}

		return Myrset;
		// return rset;
	}

	/**
	 * 执行sql语句返回结果集
	 * 
	 * @param sqlStr
	 * @param pageInt
	 * @param pageSize
	 * @return MyResultSet
	 * @author linjie
	 */
	public MyResultSet execute_oracle_page(String sqlStr, int pageInt, int pageSize) {
		Statement stmt = null;
		ResultSet rset = null;
		MyResultSet Myrset = new MyResultSet();
		Connection _conn = null;
		try {
			// get the fields wanted
			// int endfield=sqlStr.indexOf("from");
			// int startfield=sqlStr.indexOf("select")+6;
			// String fields=sqlStr.substring(startfield,endfield);

			// String query="select "+fields+" from (select "+fields+",ROWNUM as
			// R from ("+sqlStr+")) where R >"+(pageInt-1)*pageSize+" and
			// R<="+pageInt*pageSize;

			String query = "select * from ( select row_.*, rownum rownum_ from (" + sqlStr + ") row_ where rownum<=" + pageInt * pageSize + ") where rownum_>" + (pageInt - 1) * pageSize;

			// _conn = source.getConnection();
			_conn = getConn();
			if (_conn != null) {

				stmt = _conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				rset = stmt.executeQuery(query);
				if (rset == null) {
					_conn.close();
					return null;
				}
				// threadConnectionMap.put(rset, _conn);
				threadConnectionMap.put(rset, new dbconn(sqlStr, _conn));
				Myrset.setMyResuleSet(rset);
			}
			// Myrset.setMyConnection(conn);
		} catch (SQLException e) {
			System.out.println("----------------------");
			System.out.println("Error code: " + e + "");
			System.out.println("on execute :" + sqlStr);
			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e2) {
				System.out.println("----------------------");
				System.out.println("can't close ");

			}
			return null;
		}

		return Myrset;
		// return rset;
	}

	/**
	 * 执行查询数量的sql语句
	 * 
	 * @param sqlStr
	 * @return int
	 * @author linjie
	 */
	public int countselect(String sqlStr) {
		int icount = -1;
		Statement stmt = null;
		ResultSet rset = null;
		int fail_conn = 0;
		Connection _conn = null;
		String msql = "select count(*) from (" + sqlStr + ")";
		try {
			_conn = getConn();
			// _conn = source.getConnection();
			if (_conn != null) {
				stmt = _conn.createStatement();
				rset = stmt.executeQuery(msql);
				// wj 0303
				if (rset == null) {
					_conn.close();
					return 0;
				}
				rset.next();
				icount = rset.getInt(1);
				rset.close();
				_conn.close();
			}
		} catch (SQLException e) {
			icount = 0;
			fail_conn = 1;
		}
		if (fail_conn == 1) {

			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e) {
				System.out.println("----------------------");
				System.out.println("can't close ");
				System.out.println("on execute :" + sqlStr);

			}
		}
		return icount;
	}

	/**
	 * 得到序列号
	 * 
	 * @param sequenceName
	 * @return int
	 * @author linjie
	 */
	public int getSequenceCurId(String sequenceName) {
		int icount = -1;
		Statement stmt = null;
		ResultSet rset = null;
		int fail_conn = 0;
		Connection _conn = null;
		String msql = "select " + sequenceName + ".currval from dual";
		try {
			_conn = getConn();
			// _conn = source.getConnection();
			if (_conn != null) {
				stmt = _conn.createStatement();
				rset = stmt.executeQuery(msql);

				// wj 0303
				if (rset == null) {
					_conn.close();
					return 0;
				}
				rset.next();
				icount = rset.getInt(1);
				rset.close();
				_conn.close();
			}
		} catch (SQLException e) {
			icount = 0;
			fail_conn = 1;
		}
		if (fail_conn == 1) {

			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e) {
				System.out.println("----------------------");
				System.out.println("can't close ");
				System.out.println("on execute :getSequenceCurId sequenceName=" + sequenceName);

			}
		}
		return icount;
	}

	/**
	 * 得到下一个序列号
	 * 
	 * @param sequenceName
	 * @return int
	 * @author linjie
	 */
	public int getSequenceNextId(String sequenceName) {
		int icount = -1;
		Statement stmt = null;
		ResultSet rset = null;
		int fail_conn = 0;
		Connection _conn = null;
		String msql = "select " + sequenceName + ".nextval from dual";
		try {
			_conn = getConn();
			if (_conn != null) {
				// _conn = source.getConnection();
				stmt = _conn.createStatement();
				rset = stmt.executeQuery(msql);

				// wj 0303
				if (rset == null) {
					_conn.close();
					return 0;
				}
				rset.next();
				icount = rset.getInt(1);
				rset.close();
				_conn.close();
			}
		} catch (SQLException e) {
			icount = 0;
			fail_conn = 1;
		}
		if (fail_conn == 1) {

			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e) {
				System.out.println("----------------------");
				System.out.println("can't close ");
				System.out.println("on execute :getSequenceCurId sequenceName=" + sequenceName);

			}
		}
		return icount;
	}

	/*
	 * public MyResultSet execute(String sqlStr,int intRow) { ResultSet
	 * rset=null; Statement stmt = null; MyResultSet Myrset=new MyResultSet();
	 * Connection _conn=null; try { _conn=getConn(); //_conn =
	 * source.getConnection(); stmt = _conn.createStatement(); rset =
	 * stmt.executeQuery(sqlStr); // wj 0303 if (rset==null) { _conn.close();
	 * return null; } for(int i=0;i<intRow;i++) { if(!rset.next()) { break; } }
	 * Myrset.setMyResuleSet(rset); //Myrset.setMyConnection(conn);
	 * //conn.close();
	 * 
	 * threadConnectionMap.put(rset,_conn); } catch (SQLException e) {
	 * 
	 * try { _conn.close(); } catch (SQLException e2) {
	 * System.out.println("----------------------"); System.out.println("can't
	 * close "); System.out.println("on execute :"+sqlStr); } return null; }
	 * 
	 * //return rset; return Myrset; }
	 */

	/**
	 * 根据sql语句执行修改
	 * 
	 * @param sqlStr
	 * @return int
	 * @author linjie
	 */
	public int executeUpdate(String sqlStr) {
		int rset = 0;
		Statement stmt = null;
		Connection _conn = null;
		try {
			_conn = getConn();
			// _conn = source.getConnection();
			if (_conn != null) {
				stmt = _conn.createStatement();
				rset = stmt.executeUpdate(sqlStr);
				stmt.close();
				_conn.close();
			}
		} catch (SQLException e) {
			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e2) {
				System.out.println("----------------------");
				System.out.println("can't close ");
				System.out.println("on execute :" + sqlStr);

			}

			rset = 0;
		}
		return rset;
	}

	/**
	 * 根据sql语句和参数执行修改
	 * 
	 * @param sqlStr
	 * @param longString
	 * @return int
	 * @author linjie
	 */
	public int executeUpdate(String sqlStr, String longString) {
		int rset = 0;
		PreparedStatement pstmt;
		StringReader strR;
		Connection _conn = null;
		try {
			_conn = getConn();
			// _conn = source.getConnection();
			if (_conn != null) {

				pstmt = _conn.prepareStatement(sqlStr);
				strR = new StringReader(longString);
				pstmt.setCharacterStream(1, strR, longString.length());
				rset = pstmt.executeUpdate();
				pstmt.close();
				_conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e2) {
				System.out.println("----------------------");
				System.out.println("can't close ");
				System.out.println("on execute :" + sqlStr);

			}

			rset = 0;
		}
		return rset;
	}

	/**
	 * 根据sql语句和参数执行修改
	 * 
	 * @param sqlStr
	 * @param longString1
	 * @param longString2
	 * @return int
	 * @author linjie
	 */
	public int executeUpdate(String sqlStr, String longString1, String longString2) {
		int rset = 0;
		PreparedStatement pstmt;
		StringReader strR1, strR2;
		Connection _conn = null;
		try {
			_conn = getConn();
			// _conn = source.getConnection();
			if (_conn != null) {
				pstmt = _conn.prepareStatement(sqlStr);
				strR1 = new StringReader(longString1);
				strR2 = new StringReader(longString2);
				pstmt.setCharacterStream(1, strR1, longString1.length());
				pstmt.setCharacterStream(1, strR2, longString2.length());
				rset = pstmt.executeUpdate();
				pstmt.close();
				_conn.close();
			}
		} catch (SQLException e) {
			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e2) {
				System.out.println("----------------------");
				System.out.println("can't close ");
				System.out.println("on execute :" + sqlStr);

			}

			rset = 0;
		}
		return rset;
	}

	/**
	 * 根据sql集合（键值对应的hashtable类型）执行修改
	 * 
	 * @param sqlgroup
	 * @return int
	 * @author linjie
	 */
	public int executeUpdateBatch(Hashtable sqlgroup) {
		List sqllist = new ArrayList();
		for (int i = 0; i < sqlgroup.size(); i++) {
			sqllist.add(sqlgroup.get(new Integer(i + 1)));
		}
		return executeUpdateBatch(sqllist);
		/*
		 * int rset=0; Statement stmt = null; Connection _conn=null; int
		 * sqlgroupsize=0; sqlgroupsize=sqlgroup.size(); try { _conn=getConn();
		 * //_conn = source.getConnection(); _conn.setAutoCommit(false); stmt =
		 * _conn.createStatement(); for(int i=1;i<=sqlgroupsize;i++) { String
		 * sql=(String)sqlgroup.get(new Integer(i)); stmt.addBatch(sql); }
		 * 
		 * stmt.executeBatch(); _conn.commit(); rset=1; } catch (SQLException e) {
		 * System.out.println("Error code: " + e + " "); try { _conn.rollback();
		 * rset=0; } catch (SQLException ex) { System.out.println("Error code: " +
		 * ex + " "); } } finally { try { if (_conn != null) { _conn.close(); } }
		 * catch (SQLException e) { System.out.println("Error code: " + e + "
		 * "); }
		 * 
		 * return rset; }
		 */

	}

	/**
	 * 根据sql集合（list类型）执行修改
	 * 
	 * @param sqlgroup
	 * @return int
	 * @author linjie
	 */
	public int executeUpdateBatch(List sqlgroup) {
		int rset = 0;
		Statement stmt = null;
		Connection _conn = null;
		int sqlgroupsize = 0;
		sqlgroupsize = sqlgroup.size();
		try {
			_conn = getConn();
			if (_conn != null) {
				_conn.setAutoCommit(false);
				stmt = _conn.createStatement();
				for (int i = 0; i < sqlgroupsize; i++) {
					String sql = (String) sqlgroup.get(i);
					stmt.addBatch(sql);
				}

				stmt.executeBatch();
				_conn.commit();
				rset = 1;
			}
		} catch (SQLException e) {
			System.out.println("Error code: " + e + " ");
			e.printStackTrace();
			try {
				if (_conn != null) {
					_conn.rollback();
				}
				rset = 0;
			} catch (SQLException ex) {
				System.out.println("Error code: " + ex + " ");
				ex.printStackTrace();
			}
		} finally {
			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error code: " + e + " ");
			}

			return rset;
		}
	}

	/**
	 * 遍历结果集
	 * 
	 * @param myrset
	 * @author linjie
	 */
	// public void close(MyResultSet Myrset) throws java.sql.SQLException
	public void close(MyResultSet myrset) {
		ResultSet rset = null;
		Connection _conn = null;
		try {
			if (myrset != null)
				rset = myrset.getMyrset();
			if (rset != null) {
				if (threadConnectionMap.get(rset) != null) {
					_conn = (Connection) ((dbconn) threadConnectionMap.get(rset)).getConn();
					if (_conn != null) {
						threadConnectionMap.remove(rset);
						rset.close();
						_conn.close();
					}
				} else {
					rset.close();
				}
			}
		} catch (java.sql.SQLException e) {
			System.out.println("dbpool closed fail");
		}
	}

	/**
	 * 使用8859_1编码进行转换
	 * 
	 * @param s
	 * @return String
	 * @author linjie
	 */
	public String unconvertStr(String s) {
		if (s == null)
			return null;
		try {
			byte abyte0[] = s.getBytes("8859_1");
			String s1 = new String(abyte0, "GBK");
			return s1;
		} catch (UnsupportedEncodingException _ex) {
			return null;
		}
	}

	/**
	 * 使用GBK编码进行转换
	 * 
	 * @param s
	 * @return String
	 * @author linjie
	 */
	public String convertStr(String s) {
		if (s == null)
			return null;
		try {
			byte abyte0[] = s.getBytes("GBK");
			String s1 = new String(abyte0, "8859_1");
			return s1;
		} catch (UnsupportedEncodingException _ex) {
			return null;
		}
	}

	/**
	 * 执行存储过程
	 * 
	 * @param procedureName
	 * @param params
	 * @author linjie
	 */
	public void excecuteProcedure(String procedureName, Map params) {
		Connection _conn = null;
		CallableStatement proc = null;
		String procedure = "{ call " + procedureName + "(";
		int paramsSize = params.size();
		String paramStr = "";
		if (paramsSize > 0) {
			for (int i = 0; i < paramsSize; i++)
				paramStr = paramStr + "?,";
			paramStr = paramStr.substring(0, paramStr.length() - 1);
		}
		procedure = procedure + paramStr + ") }";
		try {
			_conn = getConn();
			if (_conn != null) {
				proc = _conn.prepareCall(procedure);
				Iterator iterator = params.keySet().iterator();
				int i = 0;
				while (iterator.hasNext()) {
					i++;
					String key = (String) iterator.next();
					if (key.equalsIgnoreCase("int"))
						proc.setInt(i, Integer.parseInt((String) params.get(key)));
					else if (key.equalsIgnoreCase("String"))
						proc.setString(i, (String) params.get(key));
					else if (key.equalsIgnoreCase("float"))
						proc.setFloat(i, Float.parseFloat((String) params.get(key)));
					else if (key.equalsIgnoreCase("double"))
						proc.setDouble(i, Double.parseDouble((String) params.get(key)));
					else {
						throw new SQLException("error param data type in the " + i + " param!");
					}
				}
				proc.execute();
				proc.close();
			}
		} catch (SQLException ex1) {
			ex1.printStackTrace();
		} catch (Exception ex2) {
			ex2.printStackTrace();
		} finally {
			try {
				if (_conn != null)
					_conn.close();
			} catch (SQLException ex1) {

			}

		}
	}

	/**
	 * 执行分页的查询返回结果集
	 * 
	 * @param sqlStr
	 * @param startIndex
	 * @param pageSize
	 * @return MyResultSet
	 * @author linjie
	 */
	public MyResultSet executeOraclePage(String sqlStr, int startIndex, int pageSize) {
		Statement stmt = null;
		ResultSet rset = null;
		MyResultSet Myrset = new MyResultSet();
		Connection _conn = null;
		try {
			String query = "select * from ( " + "select  a.*, rownum rownum_ from ( " + sqlStr + ") a where rownum <= " + (startIndex + pageSize) + ") b where rownum_ > " + startIndex;
			_conn = getConn();
			if (_conn != null) {

				stmt = _conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rset = stmt.executeQuery(query);
				if (rset == null) {
					_conn.close();
					return null;
				}
				// threadConnectionMap.put(rset, _conn);
				threadConnectionMap.put(rset, new dbconn(sqlStr, _conn));
				Myrset.setMyResuleSet(rset);
			}
		} catch (SQLException e) {
			System.out.println("----------------------");
			System.out.println("Error code: " + e + "");
			System.out.println("on execute :" + sqlStr);
			try {
				if (_conn != null) {
					_conn.close();
				}
			} catch (SQLException e2) {
				System.out.println("----------------------");
				System.out.println("can't close ");

			}
			return null;
		}
		return Myrset;
	}

}
