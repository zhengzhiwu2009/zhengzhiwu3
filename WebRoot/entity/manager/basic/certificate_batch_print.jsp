<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*,java.io.*,java.util.*"%>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="com.whaty.platform.entity.bean.*" %>
<%@ page isELIgnored="false" %>
<%@ page
	import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
	<%@ page import="java.io.ByteArrayOutputStream,java.io.FileNotFoundException,java.io.FileOutputStream,java.io.IOException"%>
	<%@ page import="com.lowagie.text.Document,com.lowagie.text.DocumentException,com.lowagie.text.Image,com.lowagie.text.pdf.AcroFields,com.lowagie.text.pdf.PdfCopy"%>
	<%@ page import="com.lowagie.text.pdf.PdfImportedPage,com.lowagie.text.pdf.PdfReader,com.lowagie.text.pdf.PdfStamper,com.lowagie.text.pdf.PdfWriter"%>
	<%@ page import="com.lowagie.text.pdf.PdfContentByte"%>
	<%@page import="com.lowagie.text.Rectangle"%>
	
 
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

	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	
	String sql = 


		"select bs.true_name as true_name,\n" +
		"       bs.gender as gender,\n" + 
		"       to_char(bs.birthday, 'yyyy-mm-dd') as birthday,\n" + 
		"       bs.photo as photo,\n" + 
		"       pbc.certificate as certificate,\n" + 
		"       pbc.id as id,\n" + 
		"       pbb.batch_code as batch_code,\n" +
		"       pbb.name as batch_name,\n" + 
		"       pbb.start_time as batch_start_date,\n" + 
		"       pbb.end_time as batch_end_date,\n" + 
		"       pbb.start_time as batch_start_date,\n" + 
		"       pbb.end_time as batch_end_date,\n" + 
		"       bs.fk_enterprise_id,\n" + 
		"       ep.code,\n" + 
		"       eb.id as exambatch_id,\n" + 
		"       bs.reg_no\n" + 
		"  from pe_bzz_examscore   es,\n" + 
		"       pe_bzz_student     bs,\n" + 
		"       pe_enterprise      ep,\n" + 
		"       pe_bzz_exambatch   eb,\n" + 
		"       pe_bzz_certificate pbc,\n" + 
		"       pe_bzz_batch       pbb\n" + 
		" where es.student_id = bs.id\n" + 
		"   and es.exambatch_id = eb.id\n" + 
		"   and bs.fk_enterprise_id = ep.id\n" + 
		"   and pbc.student_id = bs.id\n" + 
		"   and pbb.id = bs.fk_batch_id\n" + 
		"   and eb.selected = '402880a91dadc115011dadfcf26b00aa'\n" + 
		"   and ep.fk_parent_id is null\n" + 
		"union\n" + 
		"select bs.true_name as true_name,\n" + 
		"       bs.gender as gender,\n" + 
		"       to_char(bs.birthday, 'yyyy-mm-dd') as birthday,\n" + 
		"       bs.photo as photo,\n" + 
		"       pbc.certificate as certificate,\n" + 
		"       pbc.id as id,\n" + 
		"       pbb.batch_code as batch_code,\n" + 
		"       pbb.name as batch_name,\n" + 
		"       pbb.start_time as batch_start_date,\n" + 
		"       pbb.end_time as batch_end_date,\n" + 
		"       bs.fk_enterprise_id,\n" + 
		"       epp.code,\n" + 
		"       eb.id as exambatch_id,\n" + 
		"       bs.reg_no\n" + 
		"  from pe_bzz_examscore   es,\n" + 
		"       pe_bzz_student     bs,\n" + 
		"       pe_enterprise      ep,\n" + 
		"       pe_enterprise      epp,\n" + 
		"       pe_bzz_exambatch   eb,\n" + 
		"       pe_bzz_certificate pbc,\n" + 
		"       pe_bzz_batch       pbb\n" + 
		" where es.student_id = bs.id\n" + 
		"   and es.exambatch_id = eb.id\n" + 
		"   and bs.fk_enterprise_id = ep.id\n" + 
		"   and pbc.student_id = bs.id\n" + 
		"   and pbb.id = bs.fk_batch_id\n" + 
		"   and ep.fk_parent_id = epp.id\n" + 
		"   and eb.selected = '402880a91dadc115011dadfcf26b00aa'\n" + 
		" order by reg_no";

%>
<%
       
        int stu_count =db.countselect(sql);
		try {
			int count = stu_count;// 总记录数  
			int pageCount = 1;// 每页记录数
			int index = 1; // 表格序号
			int page1 = 0;// 总共页数
			/** 主要控制总共的页数*/
			if (count >= pageCount && count % pageCount == 0) {
				page1 = count / pageCount;
			} else {
				page1 = count / pageCount + 1;
			}
			
			
			//String TemplatePDF =request.getRealPath("/")+"pdfmodel/certificate_new.pdf";//设置模板路径
			String TemplatePDF ="/www/htdocs/webapps/scetraining/pdfmodel/certificate_new.pdf";//设置模板路径
			//System.out.println("model:"+TemplatePDF);
			File dirFile= new File(request.getRealPath("/")+"UserFiles/certificate/");
			//File dirFile= new File("/www/htdocs/webapps/scetraining/UserFiles/pdfresult/"+student_id);
			
			if(!dirFile.exists())
	    	{
	    		dirFile.mkdir();
	    	}
			//×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
			String filename=(new Date()).getTime()+".pdf";
			//FileOutputStream fos = new FileOutputStream(request.getRealPath("/")+"pdfresult/"+(new Date()).getTime()+".pdf");//需要生成PDF
			FileOutputStream fos = new FileOutputStream("/www/htdocs/webapps/scetraining/UserFiles/certificate/"+filename);//需要生成PDF
			//System.out.println("result:/www/htdocs/webapps/scetraining/UserFiles/pdfresult/"+student_id+"/"+filename);
			//String resule_url="http://"+request.getLocalAddr()+":"+request.getServerPort()+request.getContextPath()+"/pdfresult/"+filename;
			String resule_url=request.getContextPath()+"/UserFiles/certificate/"+filename;
			
			/** 向PDF模板中插入数据 */
			ByteArrayOutputStream baos[] = new ByteArrayOutputStream[page1];//用于存储每页生成PDF流
			
			rs = db.executeQuery(sql);
			int t=0;
			while(rs!=null && rs.next())
			{
				//将打印时间插入到数据库中，当前的系统时间
				String id = fixnull(rs.getString("id"));
				String sql_update="update pe_bzz_certificate t set t.print_date=sysdate where t.id='"+id+"'";
				//针对2010考试批次，设置打印时间为2012年03月15日
				String exambatch_id = fixnull(rs.getString("exambatch_id"));
				if(null != exambatch_id && "ff80808132945bc80132a53212bd39d6".equals(exambatch_id)){
					sql_update="update pe_bzz_certificate t set t.print_date=to_date('2012-03-15','yyyy-mm-dd') where t.id='"+id+"'";
				}
				db.executeUpdate(sql_update);
				
			    ++t;
				String true_name = fixnull(rs.getString("true_name"));
				String gender = fixnull(rs.getString("gender"));
				String birthday = fixnull(rs.getString("birthday"));
				String photo_link=fixnull(rs.getString("photo"));
				String certificate = fixnull(rs.getString("certificate"));
				true_name = true_name.replaceAll(" ","");
				true_name = true_name.replaceAll(" ","");
				true_name = true_name.replaceAll("　","");
				
				Date date=new Date();
				SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日");
	            String nowDateStr = df.format(date);
	            
	            String bir_year = birthday.substring(0,4);
				String bir_month = birthday.substring(5,7);
				String bir_day = birthday.substring(8,10);
				
				String batch_code =  fixnull(rs.getString("batch_code"));
				String code =  fixnull(rs.getString("code"));
				String batch_name = fixnull(rs.getString("batch_name"));
				if("2009秋季".equals(batch_name)){	//学员所在批次为09秋
					TemplatePDF ="/www/htdocs/webapps/scetraining/pdfmodel/certificate_new2009.pdf";//设置模板路径
				}else if("2010春季".equals(batch_name)){	//学员所在批次为10春
					TemplatePDF ="/www/htdocs/webapps/scetraining/pdfmodel/certificate_new2010.pdf";//设置模板路径
				}
				
				String name = true_name+"，"+gender+"，"+"生于"+bir_year+"年"+bir_month+"月"+bir_day+"日";
			    //找到学员照片的地址,PDF需要绝对路径
				if(photo_link!=null && !photo_link.equals("") && !photo_link.equals("null"))
				{
				
					photo_link=request.getRealPath("/")+"incoming/student-photo/"+batch_code+"/"+code+"/"+photo_link;
				}
						else
							photo_link=request.getRealPath("/")+"incoming/student-photo/noImage.jpg";
				File file = new File(photo_link);
				if(!file.exists())
					photo_link=request.getRealPath("/")+"incoming/student-photo/noImage.jpg";
					//获得考生的照片地址,!!!!!!!!!!!!!!!绝对路径
					String photo_url=photo_link;
				
				baos[t-1] = new ByteArrayOutputStream();
				PdfReader reader = new PdfReader(TemplatePDF);
				PdfStamper stamp = new PdfStamper(reader, baos[t-1]);
				AcroFields form = stamp.getAcroFields();
				form.setField("name",name);
				form.setField("certificate",certificate);
				//form.setField("date",nowDateStr);
				//针对2010学期设置打印日期为2012-03-05
				if(null != exambatch_id && "ff80808132945bc80132a53212bd39d6".equals(exambatch_id)){
					form.setField("date","2012年03月15日");
				}else{
					form.setField("date",nowDateStr);
				}
				String batch_start_date = new SimpleDateFormat("yyyy年 MM月 dd日").format(rs.getDate("batch_start_date"));	//获取学期开始时间
				String batch_end_date = new SimpleDateFormat("yyyy年 MM月 dd日").format(rs.getDate("batch_end_date"));	//获取学期结束时间
				form.setField("batch_start_date",batch_start_date);
				form.setField("batch_end_date",batch_end_date);
				//设置相关的照片
				//PdfStamper stamper = new PdfStamper(reader, new
				       float[] fieldPositions =	stamp.getAcroFields().getFieldPositions("photo");
				       float fieldPage = fieldPositions[0];
				       float fieldLlx = fieldPositions[1];
				       float fieldLly = fieldPositions[2];
				       float fieldUrx = fieldPositions[3];
				       float fieldUry = fieldPositions[4]; 
				       Rectangle rec=new Rectangle(fieldLlx, fieldLly,
										fieldUrx, fieldUry);
					   //实例化一个图片对象				
				       Image gif =Image.getInstance(photo_url);
				       gif.scaleToFit(rec.getWidth(), rec.getHeight());
				       
				       //得到图片的绝对位置
				       gif.setAbsolutePosition(fieldLlx+
						(rec.getWidth() - gif.getScaledWidth()) / 2, fieldLly +
						(rec.getHeight() - gif.getScaledHeight()) / 2);
				        PdfContentByte cb =stamp.getOverContent((int)fieldPage);
			 		    cb.addImage(gif);
			       
			 		    
			   //重要的结束代码
			 		    stamp.setFormFlattening(true); // 千万不漏了这句啊
						stamp.close();
						 //if(true) return;
				       
			}
			db.close(rs);
			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, fos);
			doc.open();
			/**取出之前保存的每页内容*/
			for (int i = 0; i < page1; i++) {
				PdfImportedPage impPage = pdfCopy.getImportedPage(new PdfReader(baos[i].toByteArray()), 1);
				pdfCopy.addPage(impPage);
			}
			doc.close();//当文件拷贝  记得关闭doc
 			response.sendRedirect(resule_url);
		 
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}   
		
%>		 

