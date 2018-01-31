<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*,java.io.*,java.util.*"%>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.*,com.lowagie.text.pdf.*"%>
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
	List peBzzCertificates = (List)session.getAttribute("peBzzCertificates");
%>
<%
       
        int stu_count = peBzzCertificates.size();
        
		
		
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
			
			String realPath = request.getSession().getServletContext().getRealPath("/");
			//String TemplatePDF =request.getRealPath("/")+"pdfmodel/certificate_new.pdf";//设置模板路径
			String TemplatePDF = realPath + "pdfmodel"+File.separator+"certificate_new.pdf";
			//String TemplatePDF ="/www/htdocs/webapps/scetraining/pdfmodel/certificate_new.pdf";//设置模板路径
			//System.out.println("model:"+TemplatePDF);
			File dirFile= new File(request.getRealPath("/")+"UserFiles" + "pdfresult" + "certificate/");
			//File dirFile= new File(request.getRealPath("/")+"pdfresult/");
			//File dirFile= new File("/www/htdocs/webapps/scetraining/UserFiles/pdfresult/"+student_id);
			
			if(!dirFile.exists())
	    	{
	    		dirFile.mkdir();
	    	}
			//×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
			String filename=(new Date()).getTime()+".pdf";
			//FileOutputStream fos = new FileOutputStream(request.getRealPath("/")+"pdfresult/"+(new Date()).getTime()+".pdf");//需要生成PDF
			FileOutputStream fos = new FileOutputStream(realPath + "UserFiles"+File.separator+"certificate"+File.separator+filename);//需要生成PDF
			//FileOutputStream fos = new FileOutputStream("/www/htdocs/webapps/scetraining/UserFiles/certificate/"+filename);//需要生成PDF
			//System.out.println("result:/www/htdocs/webapps/scetraining/UserFiles/pdfresult/"+student_id+"/"+filename);
			//String resule_url="http://"+request.getLocalAddr()+":"+request.getServerPort()+request.getContextPath()+"/pdfresult/"+filename;
			String resule_url=request.getContextPath()+"/UserFiles/certificate/"+filename;
			
			/** 向PDF模板中插入数据 */
			ByteArrayOutputStream baos[] = new ByteArrayOutputStream[page1];//用于存储每页生成PDF流
			
			dbpool db = new dbpool();
			MyResultSet rs = null;
			//BaseFont font = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			//BaseFont font = BaseFont.createFont(request.getRealPath("/WEB-INF/FZYT_GBK.ttf"), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			for (int item = 0; item < peBzzCertificates.size(); item++) {
				
				String true_name = fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getTrueName());
				true_name = true_name.replaceAll(" ","");
				true_name = true_name.replaceAll(" ","");
				true_name = true_name.replaceAll("　","");
				String gender = fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getGender());
				Date birth = ((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getBirthdayDate();
				String photo_link=fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getPhoto());
				String certificate = fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getCertificate());
				//学员所在考试批次，针对09的学员有补证书的情况，把原来学员的打印时间统一设置为：2011年5月23日
				String examBatchId = fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzExamBatch().getId());
				//获取学员所在批次信息进行处理2012-02-29mhy
				PeBzzBatch peBzzBatch = ((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getPeBzzBatch();	//企业信息
				int batchcode = peBzzBatch.getBatchCode();	//获取企业编号
				if("2009秋季".equals(peBzzBatch.getName())){	//学员所在批次为09秋
					TemplatePDF = realPath + "pdfmodel"+File.separator+"certificate_new2009.pdf";
				}else if("2010春季".equals(peBzzBatch.getName())){	//学员所在批次为10春
					TemplatePDF = realPath + "pdfmodel"+File.separator+"certificate_new2010.pdf";
				}
				String batch_start_date = new SimpleDateFormat("yyyy年 MM月 dd日").format(peBzzBatch.getStartDate());	//获取学期开始时间
				String batch_end_date = new SimpleDateFormat("yyyy年 MM月 dd日").format(peBzzBatch.getEndDate());	//获取学期结束时间
				//System.out.println("batch_start_year" + batch_start_year);
				//int batchcode = ((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getPeBzzBatch().getBatchCode();
				PeEnterprise enterprise = ((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getPeEnterprise().getPeEnterprise();
				String enterprise_code=fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getPeEnterprise().getCode());
				if(enterprise!=null)
				{
					String sql_enter = "select code from pe_enterprise t where t.id='"+enterprise.getId()+"'";
					rs = db.executeQuery(sql_enter);
					while(rs!=null && rs.next())
					{
						enterprise_code=fixnull(rs.getString("code"));
					}
					db.close(rs);
				}
				
				//将打印时间插入到数据库中，当前的系统时间
				String sql="update pe_bzz_certificate t set t.print_date=sysdate where t.id='"+((PeBzzCertificate)peBzzCertificates.get(item)).getId()+"'";
				
				//针对2010考试批次的学员，打印证书的时间为2012年03月15日
				String sql_temp = "select * from pe_bzz_examscore pbe where pbe.student_id='"+((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getId()+"' and pbe.exambatch_id='ff80808132945bc80132a53212bd39d6'"; 
				int count_temp = db.countselect(sql_temp);
				if(0 < count_temp){
					sql="update pe_bzz_certificate t set t.print_date=to_date('2012-03-15','yyyy-mm-dd') where t.id='"+((PeBzzCertificate)peBzzCertificates.get(item)).getId()+"'";
				}
				db.executeUpdate(sql);
				
				Date date=new Date();
				SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日");
	            String nowDateStr = df.format(date);
				String birthday = df.format(birth);
				birthday = birthday.substring(0,10);
	            String bir_year = birthday.substring(0,4);
				String bir_month = birthday.substring(5,7);
				String bir_day = birthday.substring(8,10);
				
				String name = true_name+"，"+gender+"，"+"生于"+bir_year+"年"+bir_month+"月"+bir_day+"日";
				
			    //找到学员照片的地址,PDF需要绝对路径
				if(photo_link!=null && !photo_link.equals("") && !photo_link.equals("null"))
				{   
					photo_link=request.getRealPath("/")+"incoming/student-photo/"+batchcode+"/"+enterprise_code+"/"+photo_link;
				}
				else
				{
					photo_link=request.getRealPath("/")+"incoming/student-photo/noImage.jpg";
				}
							
				File file = new File(photo_link);
				
				if(!file.exists())
					photo_link=request.getRealPath("/")+"incoming/student-photo/noImage.jpg";
					//获得考生的照片地址,!!!!!!!!!!!!!!!绝对路径
					String photo_url=photo_link;
				
				baos[item] = new ByteArrayOutputStream();
				PdfReader reader = new PdfReader(TemplatePDF);
				PdfStamper stamp = new PdfStamper(reader, baos[item]);
				AcroFields form = stamp.getAcroFields();
				//form.setFieldProperty("name","textfont",font,null);

				//form.set
				//stamp.
				form.setField("name",name);
				form.setField("certificate",certificate);
				//form.setField("date",nowDateStr);
				//针对2010学期设置打印日期为2012-03-05
				if(0 < count_temp){
					form.setField("date","2012年03月15日");
				}else{
					if("ff8080812c3ecbb4012c3ee59c440199".equals(examBatchId)){	//2009考试批次补证书的学员
						form.setField("date","2011年5月23日");
					}else{
						form.setField("date",nowDateStr);
					}
				}
				//设置学期相关信息2012-02-29mhy
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

