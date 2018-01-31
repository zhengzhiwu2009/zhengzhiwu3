package com.whaty.encrypt;

import java.net.*;
import java.util.*;
import javax.servlet.http.*;

public class appbox{
	public appbox(){
	}

	public static String Text2HTML(String text){
    	text=replaceString(text,"\n","<br>");
    	text=replaceString(text," ","&nbsp;");
    	return text;
	}

	public static String getChineseDate(Date myDate){
		String ChineseDate=(myDate.getYear()+1900)+"��"+(myDate.getMonth()+1)+"��"+myDate.getDate()+"��";
		ChineseDate=convertEncoding("ISO8859-1","GBK",ChineseDate);
		return ChineseDate;
	}
	
	public static String getReferer(HttpServletRequest request){
		return request.getHeader("Referer");
	}	

	public static String getRequestPage(HttpServletRequest request){
		String requestPage=request.getRequestURI()+"?"+request.getQueryString();
		return requestPage;
	}

	public static String parseMyXML(String Src,String Tag){
		String TagA="<"+Tag+">";
		String TagB="</"+Tag+">";
		
		int a=Src.indexOf(TagA);
		int b=Src.indexOf(TagB);
		
		if ((a>=0)&&(b>=0)&&(a<b)) 
			return(Src.substring(a+TagA.length(),b));
		else return("");
	}

	public static String setMyXML(String Src,String Tag,String Value){
		String TagA="<"+Tag+">";
		String TagB="</"+Tag+">";
		
		int a=Src.indexOf(TagA);
		int b=Src.indexOf(TagB);
		
		if ((a>=0)&&(b>=0)&&(a<b)){
			String src=Src.substring(0,a+TagA.length())+Value+Src.substring(b,Src.length());
			return src;
		}
		else return(Src+TagA+Value+TagB);
	}

	public static String removeMyXML(String Src,String Tag){
		String TagA="<"+Tag+">";
		String TagB="</"+Tag+">";
		
		int a=Src.indexOf(TagA);
		int b=Src.indexOf(TagB);
		
		if ((a>=0)&&(b>=0)&&(a<b)){
			String src=Src.substring(0,a);
			if (b+TagB.length()<Src.length()) src+=Src.substring(b+TagB.length(),Src.length());
			return src;
		}
		else return(Src);
	}

	public static String convertEncoding(String EncodingFrom,String EncodingTo,String Str){
		try{
			return new String(Str.getBytes(EncodingFrom),EncodingTo);
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		return("");
	}

	public static String convertEncoding(String Str)
	{
		try{
			return new String(Str.getBytes("GBK"), "ISO8859-1");
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		return("");
	}

    public static String replaceString(String Src, String oldToken, String newToken){
        if (Src==null) return "";

        StringBuffer buffer=new StringBuffer("");
        int a=Src.length();
        int b=oldToken.length();
        
        int p=0,q;
        while ((q=Src.indexOf(oldToken,p))>=0) {
            buffer.append(Src.substring(p,q));
            buffer.append(newToken);
            p=q+b;
        }
        if (p<a) buffer.append(Src.substring(p,a));
        return buffer.toString();
    }
	
  	public static String processString(String Str){
    	if (Str==null){
      		return("");
    	}
    	else{
      		return(Str);
    	}  
  	}
	
  	public static String processURL(HttpServletResponse response,String URLStr){
		if (response.getCharacterEncoding().equalsIgnoreCase("GBK")){
  				URLStr=convertEncoding("GBK","ISO8859-1",URLStr);
  		}
		return URLEncoder.encode(URLStr);
	}
	
  	public static String processHTML(String HtmlStr){
    	if (HtmlStr==null){
      		return("");
    	}
    	else{
    		HtmlStr=replaceString(HtmlStr,"&","&amp;");
    		HtmlStr=replaceString(HtmlStr,"<","&lt;");
      		return(HtmlStr);
    	}
	}

  	public static String processSQL(String SQLStr){
  		String[][] token={{"'","''"},
  						  {":)",":)"}};	
  		
    	if (SQLStr==null){
    		return("");
    	}
    	else{
    		for (int i=0;!token[i][0].equals(":)");i++){
    			SQLStr=replaceString(SQLStr,token[i][0],token[i][1]);
    		}	
    		return(SQLStr);
    	}
  	}

  	public static String encryptPassword(String password){
    	if (password==null){
      		return("");
    	}
    	else{
    	  	return(password);
    	}  
  	}
}	