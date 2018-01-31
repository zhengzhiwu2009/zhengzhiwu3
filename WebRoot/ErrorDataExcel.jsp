<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.sql.*"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.DocumentHelper"%>
<%@page import="org.dom4j.Element"%>

<%	response.setHeader("Content-Disposition", "attachment;filename=ErrorDate.xls"); %>

<%!
	//判断字符串为空的话，赋值为""
	public static String getLargerString(java.io.Reader reader) throws Exception { 

			char[] content = new char[1024000]; 
			char[] buffer = new char[1024]; 
			int len = 0; 
			int off = 0; 
			int contentLen = 1024000; 
			
			while(true) 
			{ 
			len = reader.read(buffer); 
			if( len == -1) 
			break; 
			if( off + len > contentLen ) 
			{ 
			char[] tmp = new char[contentLen + 1024000]; 
			System.arraycopy(content, 0, tmp, 0, off); 
			content = tmp; 
			contentLen = contentLen + 1024000; 
			} 
			System.arraycopy(buffer, 0, content, off, len); 
			off = off + len; 
			} 
			
			return new String(content, 0, off);

} 

	
%>

<%
	
	dbpool db = new dbpool();
	//Connection conn = db.getConn();
	
	
		
	String sql = "select a.*,b.test_result as test_result,b.test_date from ( \n"
		+ "  select distinct hy.id,thi.title,tpi.type ,pbs.name,pbs.phone,pbs.mobile_phone \n"
		+ "  from test_homeworkpaper_history hy,test_homeworkpaper_info thi,test_paperquestion_info tpi,pe_bzz_student pbs \n"
		+ "  where hy.testpaper_id=thi.id and tpi.testpaper_id=thi.id and substr(hy.user_id,2,32)=pbs.id \n"
		+ ") a,test_homeworkpaper_history b \n"
		+ "where a.id=b.id";
		
    //System.out.println(sql);
  //Statement stmt =  conn.createStatement();
  MyResultSet rs1 = db.executeQuery(sql);
//  while (rs1.next()) {
  //	System.out.println(rs1.getString("test_result"));
 // }
   
    long count = 0l;
		
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="Book1.files/filelist.xml">
<link rel=Edit-Time-Data href="Book1.files/editdata.mso">
<link rel=OLE-Object-Data href="Book1.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>China User</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-07-05T08:03:07Z</o:Created>
  <o:LastSaved>2010-07-05T09:51:42Z</o:LastSaved>
  <o:Company>11</o:Company>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;}
tr
	{mso-height-source:auto;
	mso-ruby-visibility:none;}
col
	{mso-width-source:auto;
	mso-ruby-visibility:none;}
br
	{mso-data-placement:same-cell;}
.style0
	{mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	white-space:nowrap;
	mso-rotate:0;
	mso-background-source:auto;
	mso-pattern:auto;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:常规;
	mso-style-id:0;}
td
	{mso-style-parent:style0;
	padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	text-align:center;
	border:.5pt solid windowtext;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;
	display:none;}
-->
</style>
<!--[if gte mso 9]><xml>
 <x:ExcelWorkbook>
  <x:ExcelWorksheets>
   <x:ExcelWorksheet>
    <x:Name>Sheet1</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>4</x:ActiveRow>
       <x:ActiveCol>1</x:ActiveCol>
      </x:Pane>
     </x:Panes>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet2</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>11145</x:WindowHeight>
  <x:WindowWidth>21435</x:WindowWidth>
  <x:WindowTopX>0</x:WindowTopX>
  <x:WindowTopY>75</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1285 style='border-collapse:
 collapse;table-layout:fixed;width:964pt'>
 <col width=123 style='mso-width-source:userset;mso-width-alt:3936;width:92pt'>
 <col width=248 style='mso-width-source:userset;mso-width-alt:7936;width:186pt'>
 <col width=160 style='mso-width-source:userset;mso-width-alt:5120;width:120pt'>
 <col width=135 style='mso-width-source:userset;mso-width-alt:4320;width:101pt'>
 <col width=130 style='mso-width-source:userset;mso-width-alt:4160;width:98pt'>
 <col width=237 style='mso-width-source:userset;mso-width-alt:7584;width:178pt'>
 <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>
 <col width=108 style='mso-width-source:userset;mso-width-alt:3456;width:81pt'>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 width=123 style='height:14.25pt;width:92pt'>homework_id</td>
  <td class=xl24 width=248 style='border-left:none;width:186pt'>title</td>
  <td class=xl24 width=160 style='border-left:none;width:120pt'>type</td>
  <td class=xl24 width=135 style='border-left:none;width:101pt'>bad_type</td>
  <td class=xl24 width=130 style='border-left:none;width:98pt'>test_date</td>
  <td class=xl24 width=237 style='border-left:none;width:178pt'>stuName</td>
  <td class=xl24 width=144 style='border-left:none;width:108pt'>phone</td>
  <td class=xl24 width=108 style='border-left:none;width:81pt'>mobile</td>
 </tr>
 <% 
 	 //rs1 = db.executeQuery(sql);
 	while (rs1.next()) {
 	    String result = rs1.getString("test_result");
 		String hyid = rs1.getString("id");
 		String title = rs1.getString("title");
 		String type = rs1.getString("type");
 		String stuName = rs1.getString("name");
 		String phone = rs1.getString("phone");
 		String mobile = rs1.getString("mobile_phone");
 		String testdate = rs1.getString("test_date");
 		
 		
		//java.io.Reader reader = (java.io.Reader)rs1.getCharacterStream("test_result"); 
//String result = (String)getLargerString(reader); 
		
		
		//String result = null;
 		
 		
 				
 		
 		Document doc = DocumentHelper.parseText(result);
 		
 		List itemlist = doc.selectNodes("/answers/item");
 		Iterator it = itemlist.iterator(); 
		if (it.hasNext()) {
			Element answer = (Element) it.next();
			Element typeEle = answer.element("type");
			String eType = typeEle.getTextTrim();
			if(!type.toUpperCase().equals(eType.toUpperCase())) {
				count++;
			
	 %>
			 <tr height=19 style='height:14.25pt'>
			  <td height=19 class=xl24 style='height:14.25pt;border-top:none'><%=hyid %></td>
			  <td class=xl24 style='border-top:none;border-left:none'><%=title %></td>
			  <td class=xl24 style='border-top:none;border-left:none'><%=type %></td>
			  <td class=xl24 style='border-top:none;border-left:none'><%=eType %></td>
			  <td class=xl24 style='border-top:none;border-left:none'><%=testdate %></td>
			  <td class=xl24 style='border-top:none;border-left:none'><%=stuName %></td>
			  <td class=xl24 style='border-top:none;border-left:none'><%=phone %></td>
			  <td class=xl24 style='border-top:none;border-left:none'><%=mobile %></td>
			 </tr>
			 
	 <%		
 			}
 		}
 	} 
 	db.close(rs1);
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 style='height:14.25pt'>共<%=count %>人</td>
  <td colspan=7 style='mso-ignore:colspan'></td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=123 style='width:92pt'></td>
  <td width=248 style='width:186pt'></td>
  <td width=160 style='width:120pt'></td>
  <td width=135 style='width:101pt'></td>
  <td width=130 style='width:98pt'></td>
  <td width=237 style='width:178pt'></td>
  <td width=144 style='width:108pt'></td>
  <td width=108 style='width:81pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
