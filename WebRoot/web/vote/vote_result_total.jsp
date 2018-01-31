<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
        <s:iterator value="prVoteQuestionList" id="class" status="num">
       	 <s:set name="max" value="itemNum" />  
      <s:if test="enumConstByFlagQuestionType.code!=3">  	       
          <tr>
          <td class="14title">问题<s:property value="#num.index+1"/>：<s:property value="#class.getQuestionNote()" escape="false"/> <s:if test="enumConstByFlagQuestionType.code!=5">   &nbsp;&nbsp;权重：<s:property value="#class.getWeight()" escape="false"/> %&nbsp;&nbsp;平均分：<s:property value="#class.getAvgScore()" escape="false"/></s:if></td>
          </tr>
          <tr>
          <td style="padding-left:40px"><table width="95%" border="0" cellspacing="0" cellpadding="0" class="12content" style="font-size:12px; line-height:24px;" >
           
             <s:if test="#class.getItem1()!=null">
              <tr> 
                <td width="40%" style="padding-right:20px">（1）<s:property value="#class.getItem1()" /> &nbsp;&nbsp;分值：<s:property value="#class.getItemScore1()" /></td>
                <td width="20%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td>
	                      <s:if test="#class.getItemResult1()>0">
	                     	 <img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult1(), #class.totalItemResult))" default="0"/>" height=10>
	                      </s:if>
                      </td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult1()>0"><s:property value="#class.getItemResult1()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult1(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem2()!=null">
              <tr> 
                <td >（2）<s:property value="#class.getItem2()" /> &nbsp;&nbsp;分值：<s:property value="#class.getItemScore2()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0">
                    <tr> 
                      <td><s:if test="#class.getItemResult2()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult2(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult2()>0"><s:property value="#class.getItemResult2()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult2(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
             <s:if test="#class.getItem3()!=null">
              <tr> 
                <td >（3）<s:property value="#class.getItem3()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore3()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td><s:if test="#class.getItemResult3()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult3(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult3()>0"><s:property value="#class.getItemResult3()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult3(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem4()!=null">
              <tr> 
                <td >（4）<s:property value="#class.getItem4()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore4()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult4()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult4(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult4()>0"><s:property value="#class.getItemResult4()" /></s:if><s:else>0</s:else>票
               (<s:property value="votePercent(#class.getItemResult4(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>    
             <s:if test="#class.getItem5()!=null">
              <tr> 
                <td >（5）<s:property value="#class.getItem5()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore5()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td><s:if test="#class.getItemResult5()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult5(), #class.totalItemResult))" default="0"/>" height="10"></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult5()>0"><s:property value="#class.getItemResult5()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult5(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem6()!=null">
              <tr> 
                <td >（6）<s:property value="#class.getItem6()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore6()" /></td>
                <td>    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult6()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult6(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult6()>0"><s:property value="#class.getItemResult6()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult6(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem7()!=null">
              <tr> 
                <td >（7）<s:property value="#class.getItem7()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore7()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult7()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult7(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult7()>0"><s:property value="#class.getItemResult7()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult7(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
             <s:if test="#class.getItem8()!=null">
              <tr> 
                <td >（8）<s:property value="#class.getItem8()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore8()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td><s:if test="#class.getItemResult8()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult8(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult8()>0"><s:property value="#class.getItemResult8()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult8(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem9()!=null">
              <tr> 
                <td >（9）<s:property value="#class.getItem9()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore9()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult9()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult9(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult9()>0"><s:property value="#class.getItemResult9()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult9(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
            <s:if test="#class.getItem10()!=null">
              <tr> 
                <td >（10）<s:property value="#class.getItem10()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore10()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult10()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult10(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult10()>0"><s:property value="#class.getItemResult10()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult10(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
            <s:if test="#class.getItem11()!=null">
              <tr> 
                <td>（11）<s:property value="#class.getItem11()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore11()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult11()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult11(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult11()>0"><s:property value="#class.getItemResult11()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult11(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>               
             <s:if test="#class.getItem12()!=null">
              <tr> 
                <td >（12）<s:property value="#class.getItem12()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore12()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult12()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult12(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult12()>0"><s:property value="#class.getItemResult12()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult12(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
             <s:if test="#class.getItem13()!=null">
              <tr> 
                <td >（13）<s:property value="#class.getItem13()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore13()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td><s:if test="#class.getItemResult13()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult13(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult13()>0"><s:property value="#class.getItemResult13()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult13(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem14()!=null">
              <tr> 
                <td >（14）<s:property value="#class.getItem14()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore14()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult14()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult14(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult14()>0"><s:property value="#class.getItemResult14()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult14(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>    
             <s:if test="#class.getItem15()!=null">
              <tr> 
                <td >（15）<s:property value="#class.getItem15()" />&nbsp;&nbsp;分值：<s:property value="#class.getItemScore15()" /></td>
                <td >    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td><s:if test="#class.getItemResult15()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="1.3*(votePercent(#class.getItemResult15(), #class.totalItemResult))" default="0"/>" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:50px">共计<s:if test="#class.getItemResult15()>0"><s:property value="#class.getItemResult15()" /></s:if><s:else>0</s:else>票
                (<s:property value="votePercent(#class.getItemResult15(), #class.totalItemResult)" default="0"/>%)</td>
              </tr>
              
          
               </s:if>   
                                                  
                </table></td>
              </tr>   
      </s:if> 
                 <s:elseif test="enumConstByFlagQuestionType.code==3"> 
                  <tr>
                    <td class="14title">问题<s:property value="#num.index+1"/>：<s:property value="#class.getQuestionNote()" escape="false"/></td>
                   </tr>
                  <%--<s:iterator value="prVoteSubQuestionList" id="subClass" status="subNum"> 
                  <s:if test="#class.getId()==#subClass.getPrVoteQuestion().getId()">
                 
                   <tr>
                      <td> &nbsp;&nbsp;<s:property value="#subClass.getItemContent()"/></td>
                  </tr>
                  </s:if>
                  </s:iterator>--%>
                  </s:elseif>  
                
                  
        </s:iterator>    	

