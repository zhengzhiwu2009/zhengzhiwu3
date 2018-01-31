<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.HashMap,java.util.Iterator,java.util.Map,java.io.*,com.whaty.util.IP"%>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.regex.Pattern"%>
<%!
	public static boolean isNumeric(String str){
		if(str==null || str.equals(""))
			return false;
	     Pattern pattern = Pattern.compile("[0-9]*");
	     return pattern.matcher(str).matches();   
	}
	
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

	public String getServerFromIp(HashMap hm,String ip,String path)
	{
		String result="";
		String type="";
		String tmp_ip="";
		//ip="61.134.135.128";
		if(hm!=null && ip!=null && !ip.equals(""))
		{
			String[] ipnums=ip.split("\\.");
			Iterator iterator = hm.entrySet().iterator();
			//System.out.println("aa111:"+hm.size());
			while(iterator.hasNext())
			{
				java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
				String key= (String)entry.getKey(); //返回与此项对应的键
				String r_key=compareIp(ip,key);
				
				if(r_key!=null && !r_key.equals(""))
				{
					//System.out.println("r_key:"+r_key);
					type=(String)hm.get(r_key);
					break;
				}
				// entry.getValue(); //返回与此项对应的值
				//System.out.println("value:"+entry.getValue());
				//System.out.println("key:"+entry.getKey());
			}
		}
		//System.out.println("type:"+type);
		//此处将没在列表中的客户端ip地址记录在newip.txt文件中
		if(type==null || type.equals(""))
		{
			type="other";
			dbpool db = new dbpool();
			String sql="";
			sql="select * from ip_new where ip='"+ip+"'";
			if(db.countselect(sql)<=0)
			{
				sql="insert into ip_new(id,ip) values(to_char(s_ip_new_id.nextval),'"+ip+"')";
				db.executeUpdate(sql);
			}
		}
		result=getSeverFromType(type,path);
		//System.out.println("result:"+result);
		return result;
	}
	public String getSeverFromType(String type,String path)
	{
		String result="";
		if(type==null || type.equals(""))
		{
			return result;
		}
		else
		{
			String filepath=path+type+".txt";
			//System.out.println("filepath:"+filepath);
		   
		   int minValue=0;
		   try {
		   FileReader read = new FileReader(filepath);
		  // System.out.println("FileReader:"+read);
		   BufferedReader br = new BufferedReader(read);
		  // System.out.println("br:"+br);
		   String row;
		   boolean bFirst=true;
		 //  System.out.println("BufferedReader:"+br.toString());
		   while((row = br.readLine())!=null){
		 //  System.out.println("row:"+row);
			   if(row!=null && !row.equals(""))
			   {
				   String[] ipArray=row.split("\\ ");
				 //  System.out.println("ipArray.length:"+ipArray.length);
				   if(ipArray.length>1)
				   {
				   		String tmp_str=ipArray[1];
				   		if(!isNumeric(tmp_str))
				   		{
				   			continue;
				   		}
				   		else
				   		{
					   		int temp_num=Integer.parseInt(tmp_str);
					   		//获取最小流量对应流媒体服务器
					   		if(bFirst)
					   		{
					   			minValue=temp_num;
					   			result=ipArray[0];
					   			bFirst=false;
					   			//System.out.println("11:"+ipArray[0]);
					   			//System.out.println("22:"+ipArray[1]);
					   		}
					   		if(temp_num<minValue)
					   		{
					   			minValue=temp_num;
					   			result=ipArray[0];
					   			//System.out.println("1:"+ipArray[0]);
					   			//System.out.println("2:"+ipArray[1]);
					   		}
				   		}
				   }
			   }
		   	}
		   }
		   catch (FileNotFoundException e) {
			   e.printStackTrace();
			   System.out.println("Error:"+e.toString());
			  } catch (IOException e){
			   e.printStackTrace();
			    System.out.println("Error1:"+e.toString());
			  }
		   //	if(result.equals(""))
		   //		result="course3.thbzzpx.org";
			return result;
		}
	}
	
	public String compareIp(String client_ip,String standard_ip)
	{
		String[] ipArrays=standard_ip.split("\\/");
		if(ipArrays.length>1)
		{
			String s_ip=ipArrays[0];
			String mask_ip=getMask(Integer.parseInt(ipArrays[1]));
			IP o_ip=new IP();
			long p0=o_ip.ipToLong(client_ip);
			long p1=o_ip.ipToLong(mask_ip);
			long p2=o_ip.ipToLong(s_ip);
			//System.out.println("11111:"+);
			//System.out.println("22222:");
			if((p0&p1)==(p1&p2))
				return standard_ip;
			//System.out.println("p0:"+Long.toBinaryString(p0));
			//System.out.println("p1:"+Long.toBinaryString(p1));
			//System.out.println("p2:"+Long.toBinaryString(p2));
			//System.out.println("p0&p1:"+(Long.toBinaryString(p0)&Long.toBinaryString(p1)));
			
		}
		return "";
	}
	 public String getMask(int maskBit) {
        if(maskBit == 1)
            return "128.0.0.0";
        else if(maskBit == 2)
            return "192.0.0.0";
        else if(maskBit == 3)
            return "224.0.0.0";
        else if(maskBit == 4)
            return "240.0.0.0";
        else if(maskBit == 5)
            return "248.0.0.0";
        else if(maskBit == 6) 
            return "252.0.0.0";
        else if(maskBit == 7)
            return "254.0.0.0";
        else if(maskBit == 8)
            return "255.0.0.0";
        else if(maskBit ==9)
            return "255.128.0.0";
        else if(maskBit == 10)
            return "255.192.0.0";
        else if(maskBit == 11)
            return "255.224.0.0";
        else if(maskBit == 12)
            return "255.240.0.0";
        else if(maskBit == 13)
            return "255.248.0.0";
        else if(maskBit == 14)
            return "255.252.0.0";
        else if(maskBit == 15)
            return "255.254.0.0";
        else if(maskBit == 16)
            return "255.255.0.0";
        else if(maskBit == 17)
            return "255.255.128.0";
        else if(maskBit == 18)
            return "255.255.192.0";
        else if(maskBit == 19)
            return "255.255.224.0";
        else if(maskBit == 20)
            return "255.255.240.0";
        else if(maskBit == 21)
            return "255.255.248.0";
        else if(maskBit == 22)
            return "255.255.252.0";
        else if(maskBit == 23)
            return "255.255.254.0";
        else if(maskBit == 24)
            return "255.255.255.0";
        else if(maskBit == 25)
            return "255.255.255.128";
        else if(maskBit == 26)
            return "255.255.255.192";
        else if(maskBit == 27)
            return "255.255.255.224";
        else if(maskBit == 28)
            return "255.255.255.240";
        else if(maskBit == 29)
            return "255.255.255.248";
        else if(maskBit == 30)
            return "255.255.255.252";
        else if(maskBit == 31)
            return "255.255.255.254";
        else if(maskBit == 32)
            return "255.255.255.255";
        return "";
    }
%>
<% 
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
	
	String client_ip=getIpAddr(request);
	HashMap ipReferList=(HashMap)application.getAttribute("ipReferList");
	String path=request.getRealPath("/");
	path+="courseserver/";
	String courseSever=getServerFromIp(ipReferList,client_ip,path);
	
	if(courseSever==null || courseSever.equals(""))
	{
		courseSever="1.tel.webtrn.cn";
	}
	
	String url="http://"+courseSever+":8080/zhibo";
	//String url="mms://210.76.109.66/zhibo";
	out.println(url);
%>