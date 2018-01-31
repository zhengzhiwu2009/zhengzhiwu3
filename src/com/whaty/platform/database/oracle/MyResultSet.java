package com.whaty.platform.database.oracle;

import java.io.InputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class MyResultSet {
      
	ResultSet rset;
	Connection conn = null;
	public MyResultSet()
	{}

	public void setMyResuleSet(ResultSet Myrset)
	{
		rset=Myrset;
	}
 
  
 	public void setMyConnection(Connection Myconn)
	{
		conn=Myconn;
	}

  	public Connection getMyConnection()
	{
		return conn;
	}
	
	public ResultSet getMyrset()
	{
		return rset;
	}
	
	/**
	 * 根据下标取得结果集的元素
	 * @param i
	 * @return String
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public String getString(int i) throws java.sql.SQLException
 	{
 		if (rset==null)
 		{
 			return("");
 		}
 		else return rset.getString(i);
	}

	/**
	 * 根据字符串取得结果集的元素
	 * @param s
	 * @return String
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public String getString(String s) throws java.sql.SQLException
 	{
 		if (rset==null)
 		{
 			return("");
 		}
 		else return rset.getString(s);
	}

	/**
	 * 取得结果集里面对应的某一个字段值
	 * @param s
	 * @return int
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public int getInt(String s) throws java.sql.SQLException
 	{
 		if (rset==null)
 		{
 			return(-1);
 		}
 		else return rset.getInt(s);
	}
	
	/**
	 * 根据字符串取得结果集里面对应的某一天
	 * @param s
	 * @return Date
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public Date getDate(String s) throws java.sql.SQLException
 	{
 	 	return rset.getDate(s);
	}
	
	/**
	 * 根据下标取得结果集里面对应的某一天
	 * @param s
	 * @return Date
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public Date getDate(int i) throws java.sql.SQLException
 	{
 		return rset.getDate(i);
	}
	
	/**
	 * 根据字符串取得结果集里面对应的时分秒
	 * @param s
	 * @return Time
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public Time getTime(String s) throws java.sql.SQLException
 	{
 		return rset.getTime(s);
	}
	
	/**
	 * 根据下标取得结果集里面对应的时分秒
	 * @param s
	 * @return Time
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public Time getTime(int i) throws java.sql.SQLException
 	{
 		return rset.getTime(i);
	}

	/**
	 * 根据下标取得结果集里面对应的某一个字段
	 * @param s
	 * @return int
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public int getInt(int s) throws java.sql.SQLException
 	{
 		return rset.getInt(s);
	}
	
	/**
	 * 根据字符串取得结果集里面的unicode输入流
	 * @param s
	 * @return InputStream
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public InputStream getUnicodeStream(String s) throws java.sql.SQLException
 	{
 		return rset.getUnicodeStream(s);
	}
	
	/**
	 * 根据下标取得结果集里面的unicode输入流
	 * @param s
	 * @return InputStream
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public InputStream getUnicodeStream(int s) throws java.sql.SQLException
 	{
 		return rset.getUnicodeStream(s);
	}
	
	/**
	 * 根据字符串取得结果集里面的双精度浮点值
	 * @param s
	 * @return double
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
	public double getDouble(String s) throws java.sql.SQLException
 	{
 		return rset.getDouble(s);
	}
	
	/**
	 * 根据下标取得结果集里面的双精度浮点值
	 * @param s
	 * @return double
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
	public double getDouble(int s) throws java.sql.SQLException
 	{
 		return rset.getDouble(s);
	}
	
	/**
	 * 根据字符串取得结果集里面的单精度浮点值
	 * @param s
	 * @return float
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
	public float getFloat(String s) throws java.sql.SQLException
	{
		return rset.getFloat(s);
	}
	
	/**
	 * 根据下标取得结果集里面的单精度浮点值
	 * @param s
	 * @return float
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
	public float getFloat(int s) throws java.sql.SQLException
	{
		return rset.getFloat(s);
	}
	
	/**
	 * 取结果集的下一个元素
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public boolean next() throws java.sql.SQLException
 	{
 		if (rset==null)
 		{
 			return(false);
 		}
 		else 
 			return rset.next();
	}
 
	/**
	 * 取结果集的前一个元素
	 * @throws java.sql.SQLException
	 * @author huguolong
	 */
 	public boolean previous() throws java.sql.SQLException
 	{
 		if (rset==null)
 		{
 			return(false);
 		}
 		else 
 			return rset.previous();
	}

 	/**
 	 * 关闭结果集
 	 * @throws java.sql.SQLException
 	 */
 	public void close() throws java.sql.SQLException
 	{
 		if (rset!=null) rset.close();
	}
 	
 	public Clob getClob(int i) throws SQLException {
 		return rset.getClob(i);
 	}
}
