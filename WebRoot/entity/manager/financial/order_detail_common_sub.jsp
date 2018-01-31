<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml"> 
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>tableType_2</title>
		<link href="/entity/manager/images/css.css" rel="stylesheet" type="text/css">
	</head>
<Script>
/* 
* 默认转换实现函数，如果需要其他功能，需自行扩展
* 参数：
* tableID : HTML中Table对象id属性值
* 详细用法参见以下 TableToExcel 对象定义 
*/
function saveAsExcel(tableID) {
    var tb = new TableToExcel("tableid");
    tb.setFontStyle("Courier New");
    tb.setFontSize(10);
    tb.setTableBorder(2);
    tb.setColumnWidth(7);
    tb.isLineWrap(true);
    tb.getExcelFile();
    
    var tb = new TableToExcel("exptable");
    tb.setFontStyle("Courier New");
    tb.setFontSize(10);
    tb.setTableBorder(2);
    tb.setColumnWidth(7);
    tb.isLineWrap(true);
    tb.getExcelFile();
}

/*
* 功能：HTML中Table对象转换为Excel通用对象.
* 参数：tableID HTML中Table对象的ID属性值
* 说明：
* 能适应复杂的HTML中Table对象的自动转换，能够自动根据行列扩展信息
* 合并Excel中的单元格，客户端需要安装有Excel
* 详细的属性、方法引用说明参见：Excel的Microsoft Excel Visual Basic参考
* 示范：
* var tb = new TableToExcel('demoTable');
* tb.setFontStyle("Courier New");
* tb.setFontSize(10); //推荐取值10
* tb.setFontColor(6); //一般情况不用设置
* tb.setBackGround(4); //一般情况不用设置
* tb.setTableBorder(2); //推荐取值2
* tb.setColumnWidth(10); //推荐取值10
* tb.isLineWrap(false);
* tb.isAutoFit(true);
* 	
* tb.getExcelFile();
* 如果设置了单元格自适应，则设置单元格宽度无效
* 版本：1.0
* BUG提交：QQ:18234348 或者 http://jeva.bokee.com
*/
function TableToExcel(tableID) {
    this.tableBorder = -1; //边框类型，-1没有边框 可取1/2/3/4
    this.backGround = 0; //背景颜色：白色 可取调色板中的颜色编号 1/2/3/4....
    this.fontColor = 1; //字体颜色：黑色
    this.fontSize = 10; //字体大小
    this.fontStyle = "宋体"; //字体类型
    this.rowHeight = -1; //行高
    this.columnWidth = -1; //列宽
    this.lineWrap = true; //是否自动换行
    this.textAlign = -4108; //内容对齐方式 默认为居中
    this.autoFit = false; //是否自适应宽度
    this.tableID = tableID;
}

TableToExcel.prototype.setTableBorder = function (excelBorder) {
    this.tableBorder = excelBorder;
};

TableToExcel.prototype.setBackGround = function (excelColor) {
    this.backGround = excelColor;
};

TableToExcel.prototype.setFontColor = function (excelColor) {
    this.fontColor = excelColor;
};

TableToExcel.prototype.setFontSize = function (excelFontSize) {
    this.fontSize = excelFontSize;
};

TableToExcel.prototype.setFontStyle = function (excelFont) {
    this.fontStyle = excelFont;
};

TableToExcel.prototype.setRowHeight = function (excelRowHeight) {
    this.rowHeight = excelRowHeight;
};

TableToExcel.prototype.setColumnWidth = function (excelColumnWidth) {
    this.columnWidth = excelColumnWidth;
};

TableToExcel.prototype.isLineWrap = function (lineWrap) {
    if (lineWrap == false || lineWrap == true) {
        this.lineWrap = lineWrap;
    }
};

TableToExcel.prototype.setTextAlign = function (textAlign) {
    this.textAlign = textAlign;
};

TableToExcel.prototype.isAutoFit = function (autoFit) {
    if (autoFit == true || autoFit == false)
        this.autoFit = autoFit;
}
//文件转换主函数
TableToExcel.prototype.getExcelFile = function () {
    var jXls, myWorkbook, myWorksheet, myHTMLTableCell, myExcelCell, myExcelCell2;
    var myCellColSpan, myCellRowSpan;

    try {
        jXls = new ActiveXObject('Excel.Application');
    }
    catch (e) {
        alert("无法启动Excel!\n\n" + e.message +
"\n\n如果您确信您的电脑中已经安装了Excel，" +
"那么请调整IE的安全级别。\n\n具体操作：\n\n" +
"工具 → Internet选项 → 安全 → 自定义级别 → 对没有标记为安全的ActiveX进行初始化和脚本运行 → 启用");
        return false;
    }

    jXls.Visible = true;
    myWorkbook = jXls.Workbooks.Add();
    jXls.DisplayAlerts = false;
    myWorkbook.Worksheets(3).Delete();
    myWorkbook.Worksheets(2).Delete();
    jXls.DisplayAlerts = true;
    myWorksheet = myWorkbook.ActiveSheet;

    var readRow = 0, readCol = 0;
    var totalRow = 0, totalCol = 0;
    var tabNum = 0;

    //设置行高、列宽
    if (this.columnWidth != -1)
        myWorksheet.Columns.ColumnWidth = this.columnWidth;
    else
        myWorksheet.Columns.ColumnWidth = 7;
    if (this.rowHeight != -1)
        myWorksheet.Rows.RowHeight = this.rowHeight;

    //搜索需要转换的Table对象，获取对应行、列数
    var obj = document.all.tags("table");
    for (x = 0; x < obj.length; x++) {
        if (obj[x].id == this.tableID) {
            tabNum = x;
            totalRow = obj[x].rows.length;
            for (i = 0; i < obj[x].rows[0].cells.length; i++) {
                myHTMLTableCell = obj[x].rows(0).cells(i);
                myCellColSpan = myHTMLTableCell.colSpan;
                totalCol = totalCol + myCellColSpan;
            }
        }
    }

    //开始构件模拟表格
    var excelTable = new Array();
    for (i = 0; i <= totalRow; i++) {
        excelTable[i] = new Array();
        for (t = 0; t <= totalCol; t++) {
            excelTable[i][t] = false;
        }
    }

    //开始转换表格 
    for (z = 0; z < obj[tabNum].rows.length; z++) {
        readRow = z + 1;
        readCol = 0;
        for (c = 0; c < obj[tabNum].rows(z).cells.length; c++) {
            myHTMLTableCell = obj[tabNum].rows(z).cells(c);
            myCellColSpan = myHTMLTableCell.colSpan;
            myCellRowSpan = myHTMLTableCell.rowSpan;
            for (y = 1; y <= totalCol; y++) {
                if (excelTable[readRow][y] == false) {
                    readCol = y;
                    break;
                }
            }
            if (myCellColSpan * myCellRowSpan > 1) {
                myExcelCell = myWorksheet.Cells(readRow, readCol);
                myExcelCell2 = myWorksheet.Cells(readRow + myCellRowSpan - 1, readCol + myCellColSpan - 1);
                myWorksheet.Range(myExcelCell, myExcelCell2).Merge();
                myExcelCell.HorizontalAlignment = this.textAlign;
                myExcelCell.Font.Size = this.fontSize;
                myExcelCell.Font.Name = this.fontStyle;
                myExcelCell.wrapText = this.lineWrap;
                myExcelCell.Interior.ColorIndex = this.backGround;
                myExcelCell.Font.ColorIndex = this.fontColor;
                if (this.tableBorder != -1) {
                    myWorksheet.Range(myExcelCell, myExcelCell2).Borders(1).Weight = this.tableBorder;
                    myWorksheet.Range(myExcelCell, myExcelCell2).Borders(2).Weight = this.tableBorder;
                    myWorksheet.Range(myExcelCell, myExcelCell2).Borders(3).Weight = this.tableBorder;
                    myWorksheet.Range(myExcelCell, myExcelCell2).Borders(4).Weight = this.tableBorder;
                }

                myExcelCell.Value = myHTMLTableCell.innerText;
                for (row = readRow; row <= myCellRowSpan + readRow - 1; row++) {
                    for (col = readCol; col <= myCellColSpan + readCol - 1; col++) {
                        excelTable[row][col] = true;
                    }
                }

                readCol = readCol + myCellColSpan;
            } else {
                myExcelCell = myWorksheet.Cells(readRow, readCol);
                myExcelCell.Value = myHTMLTableCell.innerText;
                myExcelCell.HorizontalAlignment = this.textAlign;
                myExcelCell.Font.Size = this.fontSize;
                myExcelCell.Font.Name = this.fontStyle;
                myExcelCell.wrapText = this.lineWrap;
                myExcelCell.Interior.ColorIndex = this.backGround;
                myExcelCell.Font.ColorIndex = this.fontColor;
                if (this.tableBorder != -1) {
                    myExcelCell.Borders(1).Weight = this.tableBorder;
                    myExcelCell.Borders(2).Weight = this.tableBorder;
                    myExcelCell.Borders(3).Weight = this.tableBorder;
                    myExcelCell.Borders(4).Weight = this.tableBorder;
                }
                excelTable[readRow][readCol] = true;
                readCol = readCol + 1;
            }
        }
    }
    if (this.autoFit == true)
        myWorksheet.Columns.AutoFit;

    jXls.UserControl = true;
    jXls = null;
    myWorkbook = null;
    myWorksheet = null;
};
 
function AllAreaExcel(){
	var curTbl = document.getElementById("exptable");
	var oXL = new ActiveXObject("Excel.Application");//创建AX对象excel
	var oWB = oXL.Workbooks.Add();//获取workbook对象
	var oSheet = oWB.ActiveSheet;//激活当前sheet
	var sel = document.body.createTextRange();
	sel.moveToElementText(curTbl);//把表格中的内容移到TextRange中
	sel.select();//全选TextRange中内容
	sel.execCommand("Copy");//复制TextRange中内容
	oSheet.Paste();//粘贴到活动的EXCEL中
	oXL.Visible = true;//设置excel可见属性
}	
	</Script>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
	<div id="main_content">
		<div class="cntent_k">
				<div class="k_cc">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="28">
						<div class="content_title" id="zlb_title_start">
							订单详情
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr>
					<td colspan="2" valign="top" class="pageBodyBorder" style="background: none;">
						<div class="content">
							<table id ="tableid" cellspacing="0" cellpadding="0">
								<tr align="bottom" class="postFormBox">
									<th>
										订单号：
									</th>
									<td width="">
										<font color="red"><s:property value="#request.peBzzOrderInfo.seq"/></font>
									</td>
									<th>
										备注：
									</th>
									<td>
										<font color="red"><s:property value="peBzzOrderInfo.cname"/></font>
									</td>
								</tr>
								<tr align="bottom" class="postFormBox">
									<th>
										到账状态：
									</th>
									<td width="">
										<font color="red"><s:property value="peBzzOrderInfo.enumConstByFlagPaymentState.name" /></font>
									</td>
									<th>
										总金额：
									</th>
									<td width="">
										<font color="red"><s:property value="peBzzOrderInfo.amount"/>&nbsp;元</font>
									</td>
								</tr>
								<tr class="postFormBox">
									<th>
										支付类型：
									</th>
									<td width="">
										<font color="red"><s:property value="peBzzOrderInfo.enumConstByFlagPaymentType.name"/></font>
									</td>
									<th>
										支付方式：
									</th>
									<td width="">
										<font color="red"><s:property value="peBzzOrderInfo.enumConstByFlagPaymentMethod.name"/></font>
									</td>
								</tr>
								<tr class="postFormBox">
									<th>
										下单日期：
									</th>
									<td width="">
										<font color="red"><s:date name="peBzzOrderInfo.createDate" format="yyyy-MM-dd HH:mm:ss"/></font>
									</td>
									<th>
										到账确认时间：
									</th>
									<td width="">
										<font color="red">
										<s:if test="peBzzOrderInfo.paymentDate!=null">
											<s:date name="peBzzOrderInfo.paymentDate" format="yyyy-MM-dd HH:mm:ss"/>
										</s:if>
										<s:else>
										    --
										</s:else>
										</font>
									</td>
								</tr>
								<!-- 学员信息 -->
								<!-- 学员信息 -->
								<tr class="postFormBox">
									<th>
										<s:if test="#request.peBzzStudent!=null">系统编号：</s:if>
										<s:else>机构代码：</s:else>
									</th>
									<td>
										<s:property value="#request.peBzzStudent.regNo"/>
										<s:property value="#request.peEnterpriseManager.peEnterprise.code"/>
									</td>
									<th>
										姓名：
									</th>
									<td>
										<s:property value="#request.peBzzStudent.trueName"/>
										<s:property value="#request.peEnterpriseManager.name"/>
									</td>
								</tr>
								<s:if test="#request.peBzzStudent!=null">
								<tr class="postFormBox">
									<th>
										国籍：
									</th>
									<td>
										<s:property value="#request.peBzzStudent.cardType" default="--"/>
									</td>
									<th>
										证件编号：
									</th>
									<td>
										<s:property value="#request.peBzzStudent.cardNo"  default="--"/>
									</td>
								</tr>
								<tr class="postFormBox">
									<th>
										学历：
									</th>
									<td>
										<s:property value="#request.peBzzStudent.education"  default="--"/>
									</td>
									<th>
										工作部门：
									</th>
									<td>
										<s:property value="#request.peBzzStudent.department"  default="--"/>
									</td>
								</tr>
								
								<tr class="postFormBox">
									<th>
										工作所在地：
									</th>
									<td>
										<s:property value="#request.peBzzStudent.address"  default="--"/>
									</td>
									<th>
										职务：
									</th>
									<td>
										<s:property value="#request.peBzzStudent.position"  default="--"/>
									</td>
								</tr>
								</s:if>
								<tr class="postFormBox">
									<th>
										电子邮件：
									</th>
									<td>
										<s:if test="#request.peBzzStudent!=null">													
											<s:property value="#request.peBzzStudent.email" default="--"/>
										</s:if>
										<s:else>
											<s:property value="#request.peEnterpriseManager.email" default="--"/>												
										</s:else>
									</td>
									<th>
										手机：
									</th>
									<td>
										<s:if test="#request.peBzzStudent!=null">	
											<s:property value="#request.peBzzStudent.mobilePhone" default="--"/>
										</s:if>
										<s:else>												
											<s:property value="#request.peEnterpriseManager.mobilePhone"  default="--"/>
										</s:else>
									</td>
								</tr>
								 <tr class="postFormBox">
									<th>
										所在机构：
									</th>
									<td>
										<s:if test="#request.peBzzStudent!=null">	
											<s:property value="#request.peBzzStudent.peEnterprise.name" default="--"/>
										</s:if>
										<s:else>												
											<s:property value="#request.peEnterpriseManager.peEnterprise.name" default="--"/>
										</s:else>
									</td>
									<s:if test="#request.peEnterpriseManager==null">
										<th>
											所在机构代码：
										</th>
										<td>
											<s:if test="#request.peBzzStudent!=null">	
												<s:property value="#request.peBzzStudent.peEnterprise.code" default="--"/>
											</s:if>
											<s:else>
												<s:property value="#request.peEnterpriseManager.peEnterprise.code" default="--"/>												
											</s:else>
										</td>
									</s:if>
									<s:else>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
									</s:else>
								</tr> 
								<s:if test="peBzzInvoiceInfo != null">
								<tr class="postFormBox">
									<td colspan="2">
										<table width="100%" id="invoice" border="0" cellpadding="0" cellspacing="0" style="display:none;">
											<tr class="postFormBox">
												<th>
													付款单位（个人）：
												</th>
												<td>
													<input name="peBzzInvoiceInfo.title" type="text" size="15"
														maxlength="100">
												</td>
												<th>
													邮寄地址：
												</th>
												<td>
													<input name="peBzzInvoiceInfo.address" type="text" size="15"
														maxlength="100">
												</td>
											</tr>
											<tr class="postFormBox">
												<th>
													邮政编码：
												</th>
												<td>
													<input name="peBzzInvoiceInfo.zipCode" type="text" size="15"
														maxlength="15">
												</td>
												<th>
													收件人：
												</th>
												<td>
													<input name="peBzzInvoiceInfo.addressee" type="text" size="15"
														maxlength="15">
												</td>
											</tr>
											<tr class="postFormBox">
												<th>
													联系电话：
												</th>
												<td>
													<input name="peBzzInvoiceInfo.phone" type="text" size="15"
														maxlength="15">
												</td>
												<td>
													&nbsp;
												</td>
												<td>
													&nbsp;
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</s:if>
						</table>
							<s:if test="electiveList != null && electiveList.size() >0">
								<table id="exptable" width='98%' cellpadding='1' cellspacing='0' class='list'>
									<tr style="text-align:center;">
										<th width='10%'>
											系统编号
										</th>
										<th width='10%'>
											学员姓名
										</th>
										<th width='10%'>
											课程编号
										</th>
										<th width='30%'>
											课程名称
										</th>
										<th width='10%'>
											课程性质
										</th>
										<th width='10%'>
											课程金额(元)
										</th>
											<th width='10%'>
											课程学时
										</th>
										<th width='10%'>
											课程状态
										</th>
									</tr>
									<s:iterator value="electiveList" id="elective">
										<tr style="text-align:center;">
										<td>
											<s:property value="#elective[0]"/>
										</td>
										<td>
											<s:property value="#elective[1]"/>
										</td>
										<td>
											<s:property value="#elective[2]"/>
										</td>
										<td>
											<s:property value="#elective[3]"/>
										</td>
										<td>
											<s:property value="#elective[8]"/>
										</td>
										<td>
											<s:property value="#elective[4]"/>
										</td>
										<td>
											<s:property value="#elective[5]"/>
										</td>
										<td><!--李文强 已删除课程不显示已删除状态 原本if判断条件取值有误,已修改
										  -->
											
											<s:if test="#elective[9]!=null && #elective[9]==0"><font title="关闭订单、重新购买会造成课程删除" color="red" style="cursor: pointer;">已点击重新购买</font></s:if>
											<s:elseif test="#elective[6]=='INCOMPLETE'">未完成</s:elseif>
											<s:elseif test="#elective[6]=='COMPLETED'">已完成</s:elseif>
											<s:elseif test="#elective[6]=='UNCOMPLETE'">未学习</s:elseif>
											<s:else>未学习</s:else>
										</td>
									</tr>
									</s:iterator>
								</table>
							</s:if>
						</div>
					</td>
				</tr>
                <tr>
                	<td colspan="10" align="center">
                		<span style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');">
							<a href="#" onclick="window.history.back(-1);">关闭</a>
						</span>&nbsp;&nbsp;&nbsp;&nbsp;
                		<span style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');">
						<a href="#" onclick="saveAsExcel()">下载</a>
						</span>
					</td>
               </tr>
			</table>
	  </div>
    </div>
</div>
	<div class="clear"></div>
</body>
</html>