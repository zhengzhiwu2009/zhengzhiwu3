package com.whaty.filemanager.encode;

import java.io.*; 
import javax.naming.*;
import javax.sql.*;

/*
 * URLEncoder: a wrapper of java.net.URLEncoder, with Chinese words encoding problem fixed
 * 
 * @author Dong Jinpeng
 * @version 0.01
 */

public class URLEncoder
{
	public static String encode(String s)
	{
		String result = "";
		try
		{
			result = java.net.URLEncoder.encode(new String(s.getBytes("GBK"), "ISO-8859-1"));
		}
		catch(Exception e)
		{
		}
		return result;
	}
	
	 public static String convertStr(String s)
    {
        if(s == null)
            return null;
        try
        {
            byte abyte0[] = s.getBytes("ISO-8859-1");
            String s1 = new String(abyte0, "GBK");
            return s1;
        }
        catch(UnsupportedEncodingException _ex)
        {
            return null;
        }
    }
    
     public static String convertStr2(String s)
    {
        if(s == null)
            return null;
        try
        {
            byte abyte0[] = s.getBytes("GBK");
            String s1 = new String(abyte0, "ISO-8859-1");
            return s1;
        }
        catch(UnsupportedEncodingException _ex)
        {
            return null;
        }
    }
}
