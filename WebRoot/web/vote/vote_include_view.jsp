<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link href="/entity/manager/css/admincss.css" rel="stylesheet"
	type="text/css">

<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	int i = 1;
%>
<s:iterator value="prVoteQuestionList" status="aa">
	<tr name="titleTr">
		<td class="14title">
			<input type="hidden" name="questionRequired"
				value='<s:property value="isRequirde"/>' />
			<input type="hidden" name="questionType"
				value='<s:property value="enumConstByFlagQuestionType.code"/>' />

			<s:if test="enumConstByFlagQuestionType.code!=5">
				<%=i++%> 、</s:if>
			<s:property value="questionNote" escape="false" />
			<s:if test="isRequirde ==1">（必填） </s:if>
		</td>
		<td align="right"width ="10%"><s:if test="enumConstByFlagQuestionType.code !=5 && weight !=null" >权重：<s:property value ="weight"></s:property>% </s:if></td>
	</tr>
	<tr  name="answer">
		<td colspan="2" bgcolor="#F9F9F9">
			<s:if test="enumConstByFlagQuestionType.code==1">
				<s:if test="item1 != null">
					<input type="radio" id="<s:property value="id"/>@1"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@1">
					<s:property value="item1" />
					<br/>
				</s:if>
				<s:if test="item2 != null">
					<input type="radio" id="<s:property value="id"/>@2"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@2">
					<s:property value="item2" />
					<br/>
				</s:if>
				<s:if test="item3 != null">
					<input type="radio" id="<s:property value="id"/>@3"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@3">
					<s:property value="item3" />
					<br/>
				</s:if>
				<s:if test="item4 != null">
					<input type="radio" id="<s:property value="id"/>@4"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@4">
					<s:property value="item4" />
					<br/>
				</s:if>
				<s:if test="item5 != null">
					<input type="radio" id="<s:property value="id"/>@5"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@5">
					<s:property value="item5" />
					<br/>
				</s:if>
				<s:if test="item6 != null">
					<input type="radio" id="<s:property value="id"/>@6"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@6">
					<s:property value="item6" />
					<br/>
				</s:if>
				<s:if test="item7 != null">
					<input type="radio" id="<s:property value="id"/>@7"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@7">
					<s:property value="item7" />
					<br/>
				</s:if>
				<s:if test="item8 != null">
					<input type="radio" id="<s:property value="id"/>@8"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@8">
					<s:property value="item8" />
					<br/>
				</s:if>
				<s:if test="item9 != null">
					<input type="radio" id="<s:property value="id"/>@9"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@9">
					<s:property value="item9" />
					<br/>
				</s:if>
				<s:if test="item10 != null">
					<input type="radio" id="<s:property value="id"/>@10"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@10">
					<s:property value="item10" />
					<br/>
				</s:if>
				<s:if test="item11 != null">
					<input type="radio" id="<s:property value="id"/>@11"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@11">
					<s:property value="item11" />
					<br/>
				</s:if>
				<s:if test="item12 != null">
					<input type="radio" id="<s:property value="id"/>@12"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@12">
					<s:property value="item12" />
					<br/>
				</s:if>
				<s:if test="item13 != null">
					<input type="radio" id="<s:property value="id"/>@13"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@13">
					<s:property value="item13" />
					<br/>
				</s:if>
				<s:if test="item14 != null">
					<input type="radio" id="<s:property value="id"/>@14"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@14">
					<s:property value="item14" />
					<br/>
				</s:if>
				<s:if test="item15 != null">
					<input type="radio" id="<s:property value="id"/>@15"
						name="radio<s:property value="#aa.index+1"/>"
						value="<s:property value="id"/>@15">
					<s:property value="item15" />
					<br/>
				</s:if>
				<s:if test="isCustom ==1">
					<font size="2">自定义回答：</font>
					<input type="text" class ="custom_<s:property value="id"/>"  name="questionNV"
						value="">
				</s:if>
				<script type="text/javascript">
                     var rt = '<s:property value="result"/>';
                     var s= rt.split(",");
                     for(var i=0;i<s.length;i++){        
                     if(s[i]){
                      document.getElementById('<s:property value="id"/>'+'@'+s[i]).checked=true;
                     }
                     }
                     </script>
			</s:if>
			<s:elseif test="enumConstByFlagQuestionType.code==2">
				<s:if test="item1 != null">
					<input type="checkbox" id="<s:property value="id"/>@1"
						name="checkboxQuestion" value="<s:property value="id"/>@1">
					<s:property value="item1" />
					<br/>
				</s:if>
				<s:if test="item2 != null">
					<input type="checkbox" id="<s:property value="id"/>@2"
						name="checkboxQuestion" value="<s:property value="id"/>@2">
					<s:property value="item2" />
					<br/>
				</s:if>
				<s:if test="item3 != null">
					<input type="checkbox" id="<s:property value="id"/>@3"
						name="checkboxQuestion" value="<s:property value="id"/>@3">
					<s:property value="item3" />
					<br/>
				</s:if>
				<s:if test="item4 != null">
					<input type="checkbox" id="<s:property value="id"/>@4"
						name="checkboxQuestion" value="<s:property value="id"/>@4">
					<s:property value="item4" />
					<br/>
				</s:if>
				<s:if test="item5 != null">
					<input type="checkbox" id="<s:property value="id"/>@5"
						name="checkboxQuestion" value="<s:property value="id"/>@5">
					<s:property value="item5" />
					<br/>
				</s:if>
				<s:if test="item6 != null">
					<input type="checkbox" id="<s:property value="id"/>@6"
						name="checkboxQuestion" value="<s:property value="id"/>@6">
					<s:property value="item6" />
					<br/>
				</s:if>
				<s:if test="item7 != null">
					<input type="checkbox" id="<s:property value="id"/>@7"
						name="checkboxQuestion" value="<s:property value="id"/>@7">
					<s:property value="item7" />
					<br/>
				</s:if>
				<s:if test="item8 != null">
					<input type="checkbox" id="<s:property value="id"/>@8"
						name="checkboxQuestion" value="<s:property value="id"/>@8">
					<s:property value="item8" />
					<br/>
				</s:if>
				<s:if test="item9 != null">
					<input type="checkbox" id="<s:property value="id"/>@9"
						name="checkboxQuestion" value="<s:property value="id"/>@9">
					<s:property value="item9" />
					<br/>
				</s:if>
				<s:if test="item10 != null">
					<input type="checkbox" id="<s:property value="id"/>@10"
						name="checkboxQuestion" value="<s:property value="id"/>@10">
					<s:property value="item10" />
					<br/>
				</s:if>
				<s:if test="item11 != null">
					<input type="checkbox" id="<s:property value="id"/>@11"
						name="checkboxQuestion" value="<s:property value="id"/>@11">
					<s:property value="item11" />
					<br/>
				</s:if>
				<s:if test="item12 != null">
					<input type="checkbox" id="<s:property value="id"/>@12"
						name="checkboxQuestion" value="<s:property value="id"/>@12">
					<s:property value="item12" />
					<br/>
				</s:if>
				<s:if test="item13 != null">
					<input type="checkbox" id="<s:property value="id"/>@13"
						name="checkboxQuestion" value="<s:property value="id"/>@13">
					<s:property value="item13" />
					<br/>
				</s:if>
				<s:if test="item14 != null">
					<input type="checkbox" id="<s:property value="id"/>@14"
						name="checkboxQuestion" value="<s:property value="id"/>@14">
					<s:property value="item14" />
					<br/>
				</s:if>
				<s:if test="item15 != null">
					<input type="checkbox" id="<s:property value="id"/>@15"
						name="checkboxQuestion" value="<s:property value="id"/>@15">
					<s:property value="item15" />
					<br/>
				</s:if>
				<s:if test="isCustom ==1">
					<font size="2">自定义回答：</font>
					<input  type="text" class ="custom_<s:property value="id"/>"  name="questionNV"
						value="">
				</s:if>
				<script type="text/javascript">
                     var rt = '<s:property value="result"/>';
                     var s= rt.split(",");
                     for(var i=0;i<s.length;i++){        
                     if(s[i]){
                      document.getElementById('<s:property value="id"/>'+'@'+s[i]).checked=true;
                      }
                     }
                     </script>
			</s:elseif>
			<s:elseif test="enumConstByFlagQuestionType.code==3">
					<textarea class="<s:property value="id"/>" name="questionNV"
						cols="70" rows="12">
						<s:property value="result" />
					</textarea><br/>
					<s:if test="isCustom ==1">
						<font size="2">自定义回答：</font>
						<input  type="text" class ="custom_<s:property value="id"/>"  name="questionNV"
							value="">
					</s:if>
			</s:elseif>
			<s:elseif test="enumConstByFlagQuestionType.code==4">
				<input onchange="if(!/(^0$)|(^100$)|(^\d{1,2}$)/.test(value)){value='';alert('输入格式不正确!');}" 
					class="<s:property value="id"/>" name="questionNV"
					value="<s:property value="result"/>"> (只能输入0-100内的正整数) <br/>
            	 		 <s:if test="isCustom ==1">
					<font size="2">自定义回答：</font>
					<input  type="text" class ="custom_<s:property value="id"/>"  name="questionNV"
						value="">
				</s:if>
			</s:elseif>
		</td>
	</tr>

</s:iterator>

