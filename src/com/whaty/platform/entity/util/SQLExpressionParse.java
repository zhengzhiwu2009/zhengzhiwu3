package com.whaty.platform.entity.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * SQL表达式解析
 * @author zqf
 *
 */
public class SQLExpressionParse implements Serializable {

	private String dataFormat = "yyyy-MM-dd"; 
	
	/**
	 * sql表达式解析
	 * @param variable	搜索条件
	 * @param itemName	字段名称
	 * @return	sql查询条件
	 * @throws Exception
	 */
	public static String ExpressionParse(String variable, String itemName) throws Exception{
		
		String sql_conditon = "";
		
		//首先将传进来的字符按照关系运算符以空格分开
		variable = variable.replace("(", " ( ").replace(")", " ) ")
		.replace(">=", " >= ").replace("<=", " <= ")
		.replace("!=", " != ").replace("==", " = ")
		.replaceAll(">(([^=]){1})", " > $1")
		.replaceAll("<(([^=]){1})", " < $1")
		.replaceAll("^=", " =")
		.replaceAll("([^><]){1}=", "$1 = ");
		if(variable != null && !"".equals(variable)){
			variable = variable.trim();
		}
		String[] items = variable.split("\\s+");
		try {
			sql_conditon = builtSQL(items,itemName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return sql_conditon;
	}
	
	/**
	 * 构造sql语句
	 * @param items
	 * @param itemName
	 * @return sql语句
	 * @throws Exception
	 */
	private static String builtSQL(String[] items, String itemName) throws Exception{
		StringBuffer sql_condition = new StringBuffer();
		sql_condition.append(" and ( ");
		try {
			if(syntaxAnalysis(items)){
				if(items.length == 1){
					//长度为1的时候直接返回
					if(items[0].trim().equals("null")){
						//允许搜索条件为空的记录--yinxu
						return " and (" + itemName + " is null or  " + itemName + " ='')";
					}else {
						String[] dateCheck = isDate(items[0]);
						if(dateCheck[0].equals("true")){
							return " and to_char(" + itemName + ",'"+ dateCheck[1] +"') like '%"+items[0]+"%' " ;
						}else{
							//Lee start 2014年1月7日 判断查询条件为系统编号[regNo]时 不区分大小写
							if("regno".equals(itemName==null?"":itemName.toLowerCase()))
								return " and LOWER(" + itemName + ") like '%"+(items[0]==null?"":items[0].toLowerCase())+"%' " ;
							else//Lee end
								return " and " + itemName + " like '%"+items[0]+"%' " ;
						}
					}
					
				}else{
					for(int i = 0; i < items.length; i++){
						if("(".equals(items[i]) || ")".equals(items[i]) || isLogicalOperator(items[i])){
							//如果为not 并且不在表达式开头，在not前添加and，否则直接添加
							sql_condition.append(" ");
							if(i > 0 && items[i].equals("not")){
								sql_condition.append(" and ");
							}
							sql_condition.append(items[i]);
							sql_condition.append(" ");
						}else if(isRelationOperator(items[i])){
							//关系运算符 添加   (字段名称  关系运算符  值)
							String[] dateCheck = isDate(items[i+1]);
							if(dateCheck[0].equals("true")){
								sql_condition.append(" to_char(");
								sql_condition.append(itemName);
								sql_condition.append(",'"+ dateCheck[1] +"') ");
								sql_condition.append(items[i]);
								sql_condition.append(" '");
								sql_condition.append(items[i+1]);
								sql_condition.append("' ");
								i++;
							}else{
								sql_condition.append(itemName);
								sql_condition.append(" ");
								sql_condition.append(items[i]);
								sql_condition.append(" '");
								sql_condition.append(items[i+1]);
								sql_condition.append("' ");
								i++;
							}
						}
						else{
							sql_condition.append(itemName);
							sql_condition.append(" like '%");
							sql_condition.append(items[i]);
							sql_condition.append("%' ");
						}
					}
				}
				sql_condition.append(" )");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return sql_condition.toString();
	}
	
	/**
	 * 语法分析
	 * a)不能以or、and开头，不能以or、and、not结尾
	 * b)逻辑运算符、关系运算符或是操作数不能连续出现
	 * c)逻辑运算符后不能跟")"，关系运算符后只能跟操作数
	 * c)关系运算符之后必须为操作数
	 * d)左括号和右括号必须匹配
	 * @param items
	 * @return
	 */
	private static boolean syntaxAnalysis(String[] items) throws Exception{
		
		if(items.length == 1){
			return true;
		}
		LinkedList<String> tmpList = new LinkedList();
		
		if("or".equals(items[0]) || "and".equals(items[0])){
			throw new Exception("表达式语法错误，不能已or、and 开头");
		}else if(isLogicalOperator(items[items.length - 1])){
			throw new Exception("表达式语法错误，不能已or、and、not 结尾");
		}
		tmpList.add("#");
		for(int i = 0; i < items.length; i++){
			String currentItem = items[i];
			String stackHead = "";
			if(tmpList.size() == 0){
				stackHead = "#";
			}else{
				stackHead = tmpList.getLast();
			}
			
			if("(".equals(currentItem)){
				if(isLogicalOperator(stackHead) || "#".equals(stackHead)){
					tmpList.removeLast();
				}else if(isRelationOperator(stackHead)){
					throw new Exception("表达式语法错误，关系运算符后不能跟括号");
				}else{
					throw new Exception("表达式语法错误，所要搜索的值后面不能跟左括号“(”");
				}
				tmpList.add(currentItem);
			}else if(")".equals(currentItem)){
				if("(".equals(stackHead)){
					tmpList.removeLast();
				}else if(isLogicalOperator(stackHead)){
					throw new Exception("表达式语法错误，逻辑运算符之后不能直接跟右括号“)”");
				}else if(isRelationOperator(stackHead)){
					throw new Exception("表达式语法错误，关系运算符之后不能直接跟右括号“)”");
				}else if("#".equals(stackHead)){
					throw new Exception("表达式语法错误，右括号“)”不能放在表达式开头");
				}else{
					tmpList.removeLast();
					if(tmpList.size() > 0){
						tmpList.removeLast();
					}
				}
			}else if(isLogicalOperator(currentItem)){
				if(isLogicalOperator(stackHead)){
					throw new Exception("表达式语法错误，不能连续两次出现or、and、not");
				}else if(isRelationOperator(stackHead)){
					throw new Exception("表达式语法错误，关系运算符后面不能跟逻辑运算符");
				}else if("(".equals(stackHead) && !"not".equals(currentItem)){
					throw new Exception("表达式语法错误，左括号“(”后不能直接跟“or或and”");
				}else{
					if(tmpList.size() > 0){
						tmpList.removeLast();
					}
					tmpList.add(currentItem);
				}
			}else if(isRelationOperator(currentItem)){
				if(isRelationOperator(stackHead)){
					throw new Exception("表达式语法错误，关系运算符不能连续出现");
				}else{
					if(!"(".equals(stackHead) && tmpList.size() > 0){
						tmpList.removeLast();
					}
					tmpList.add(currentItem);
				}
			}else{
				if(tmpList.size() == 0 || "(".equals(stackHead) || isLogicalOperator(stackHead) || isRelationOperator(stackHead) || "#".equals(stackHead)){
					if(!"(".equals(stackHead) && tmpList.size() > 0){
						tmpList.removeLast();
					}
					tmpList.add(currentItem);
				}else{
					throw new Exception("表达式语法错误，所要搜索的值不能连续出现");
				}
			}
		}
		return true;
	}
	/**
	 * 判断是否为date类型
	 * @param variable
	 * @author zhangchengli
	 * @return true false
	 */
	private static String[] isDate(String variable){
		String[] dateCheck = new String[]{"false","yyyy-MM-dd"};
		if(variable.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
			dateCheck[0] = "true";
			return dateCheck;
		}
		if(variable.matches("^\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
			dateCheck[0] = "true";
			dateCheck[1] = "yyyy-MM-dd HH:mi:ss";
			return dateCheck;
		}
		return dateCheck;
	}
	
	/**
	 * 判断所传过来的变量是否为逻辑运算符(or,and,not)
	 * @param variable
	 * @author zhangchengli
	 * @return
	 */
	private static boolean isLogicalOperator(String variable){
		if("or".equals(variable) || "and".equals(variable) || "not".equals(variable)){
			return true;
		}
		return false;
	}
	/**
	 * 用于判断所传过来的变量是否为关系运算符(=、==、>、>=、<、<=、!=)
	 * @param variable
	 * @author zhangchengli
	 * @return
	 */
	private static boolean isRelationOperator(String variable){
		if("=".equals(variable) || "==".equals(variable) || ">".equals(variable) || ">=".equals(variable)
				|| "<".equals(variable) || "<=".equals(variable) || "!=".equals(variable)){
			return true;
		}
		return false;
	}
	
	
}
