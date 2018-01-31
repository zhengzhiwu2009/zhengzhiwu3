<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*,java.io.*,java.util.*"%>
<%@ page
	import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
	<%@ page import="java.io.ByteArrayOutputStream,java.io.FileNotFoundException,java.io.FileOutputStream,java.io.IOException"%>
	<%@ page import="com.lowagie.text.Document,com.lowagie.text.DocumentException,com.lowagie.text.Image,com.lowagie.text.pdf.AcroFields,com.lowagie.text.pdf.PdfCopy"%>
	<%@ page import="com.lowagie.text.pdf.PdfImportedPage,com.lowagie.text.pdf.PdfReader,com.lowagie.text.pdf.PdfStamper,com.lowagie.text.pdf.PdfWriter"%>
	<%@ page import="com.lowagie.text.pdf.PdfContentByte"%>
	<%@page import="com.lowagie.text.Rectangle"%>
	
 <%
	response.setContentType("application/pdf");
    //response.setHeader("Content-Disposition","attachment;filename=apply.pdf");
%>
 
	<%!String fixnull(String str) { 
		if (str == null || str.equals("null"))
			str = "";
		return str;
	}%>
 
 <%!
	public   static   String   subTitle(String   str,   int  len)   {   
          if   (str   ==   null)   {   
              return   "";   
          }   
          str   =   str.trim();   
          StringBuffer   r   =   new   StringBuffer();   
          int   l   =   str.length();   
          float   count   =   0;   
          for   (int   i   =   0;   i   <   l;   ++i)   {   
              char   c   =   str.charAt(i);   
              if   (c   >   255   ||   c   <   0)   {   
                  ++count;   
                  r.append(c);   
              }   
              else   {   
                  count   +=   0.5;   
                  r.append(c);   
              }   
          }
          if   (count   <=   len)   {   
              	  for(int j = 0;j<len-count;j++)
              	  {
              	  	r.append(".");
              	  }
          }
          return   r.toString();   
      }

%>

<%
		String student_id = fixnull(request.getParameter("student_id"));
		String temp_sql=
			"select t.reg_no       as reg_no,t.true_name as name,\n" +
			"       t.mobile_phone as phone,\n" + 
			"       pe.name        as enterprise_name,\n" + 
			"       pe.name        as group_name\n" + 
			"  from pe_bzz_student t, pe_enterprise pe\n" + 
			" where t.id = '"+student_id+"'\n" + 
			"   and t.fk_enterprise_id = pe.id\n" + 
			"   and pe.fk_parent_id is null\n" + 
			"union\n" + 
			"select t.reg_no       as reg_no,t.true_name as name,\n" + 
			"       t.mobile_phone as phone,\n" + 
			"       pe.name        as enterprise_name,\n" + 
			"       p.name         as group_name\n" + 
			"  from pe_bzz_student t, pe_enterprise pe, pe_enterprise p\n" + 
			" where t.id = '"+student_id+"'\n" + 
			"   and t.fk_enterprise_id = pe.id\n" + 
			"   and pe.fk_parent_id = p.id";
		//System.out.println("print_late_apply:"+temp_sql);
		String reg_no="";
		String enterprise_name="";
		String group_name="";
		String phone="";
		String name = "";
		
		dbpool db = new dbpool();
	    MyResultSet rs = null;
	
		rs = db.executeQuery(temp_sql);
		while (rs != null && rs.next()) {
			reg_no = fixnull(rs.getString("reg_no"));
			phone = fixnull(rs.getString("phone"));
			enterprise_name = fixnull(rs.getString("enterprise_name"));
			group_name = fixnull(rs.getString("group_name"));
			name =  fixnull(rs.getString("name"));
		}
		db.close(rs);

		
		try {
			int count = 1;// 总记录数  
			int pageCount = 1;// 每页记录数
			int index = 1; // 表格序号
			int page1 = 0;// 总共页数
			/** 主要控制总共的页数*/
			if (count >= pageCount && count % pageCount == 0) {
				page1 = count / pageCount;
			} else {
				page1 = count / pageCount + 1;
			}
			
			String TemplatePDF =request.getRealPath("/")+"pdfmodel/late_apply.pdf";//设置模板路径
			TemplatePDF ="/www/htdocs/webapps/scetraining/pdfmodel/late_apply.pdf";//设置模板路径
			//System.out.println("model:"+TemplatePDF);
			//File dirFile= new File(request.getRealPath("/")+"pdfresult/"+student_id);
			File dirFile= new File("/www/htdocs/webapps/scetraining/UserFiles/pdfresult/"+student_id);

			if(!dirFile.exists())
	    	{
	    		//System.out.println("noexist");
	    		//System.out.println("mkdir:"+dirFile.mkdir());
	    		dirFile.mkdir();
	    	}else{
	    		     boolean flag = true;   
			        //删除文件夹下的所有文件(包括子目录)   
			        File[] files = dirFile.listFiles();   
			        for(int i=0;i<files.length;i++){   
			            //删除子文件   
			            if(files[i].isFile()){   
			                flag = files[i].delete();
			                if(!flag){   
			                    break;   
			                }   
			            }   
			             
			        }   
			           
	    	}
			//×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××

			String filename=(new Date()).getTime()+".pdf";
			//FileOutputStream fos = new FileOutputStream(request.getRealPath("/")+"pdfresult/"+student_id+"/"+(new Date()).getTime()+".pdf");//需要生成PDF
			FileOutputStream fos = new FileOutputStream("/www/htdocs/webapps/scetraining/UserFiles/pdfresult/"+student_id+"/"+filename);//需要生成PDF
			//System.out.println("result:/www/htdocs/webapps/scetraining/UserFiles/pdfresult/"+student_id+"/"+filename);
			//String resule_url="http://"+request.getLocalAddr()+":"+request.getServerPort()+request.getContextPath()+"/UserFiles/pdfresult/"+student_id+"/"+filename;
			String resule_url=request.getContextPath()+"/UserFiles/pdfresult/"+student_id+"/"+filename;
			//System.out.println("resule_url:"+resule_url);

			//  if(true) return;  
			ByteArrayOutputStream baos[] = new ByteArrayOutputStream[page1];//用于存储每页生成PDF流
			/** 向PDF模板中插入数据 */
			for (int item = 0; item < page1; item++) {
			
				
				baos[item] = new ByteArrayOutputStream();
				PdfReader reader = new PdfReader(TemplatePDF);
				PdfStamper stamp = new PdfStamper(reader, baos[item]);
				AcroFields form = stamp.getAcroFields();
				
				
				
				form.setField("group_name",group_name);
				form.setField("work_name",enterprise_name);
				form.setField("name",name);
				form.setField("reg_no",reg_no);
				form.setField("phone",phone);
				
			   //重要的结束代码
			 		    stamp.setFormFlattening(true); // 千万不漏了这句啊
						stamp.close();
						 //if(true) return;
				       
			}
			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, fos);
			doc.open();
			/**取出之前保存的每页内容*/
			for (int i = 0; i < page1; i++) {
				PdfImportedPage impPage = pdfCopy.getImportedPage(new PdfReader(baos[i].toByteArray()), 1);
				pdfCopy.addPage(impPage);
			}
			doc.close();//当文件拷贝  记得关闭doc
			//System.out.println("after resule_url:"+resule_url);
 			response.sendRedirect(resule_url);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
%>		 
