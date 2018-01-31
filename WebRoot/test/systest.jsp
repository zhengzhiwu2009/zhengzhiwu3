<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Runtime r = Runtime.getRuntime();
Properties props = System.getProperties();
//InetAddress addr;
//addr = InetAddress.getLocalHost();
//String ip = addr.getHostAddress();
Map<String, String> map = System.getenv();
String userName = map.get("USERNAME");// 获取用户名
String computerName = map.get("COMPUTERNAME");// 获取计算机名
String userDomain = map.get("USERDOMAIN");// 获取计算机域名
out.println("<br/>用户名:    " + userName);
out.println("<br/>计算机名:    " + computerName);
out.println("<br/>计算机域名:    " + userDomain);
//out.println("<br/>本地ip地址:    " + ip);
//out.println("<br/>本地主机名:    " + addr.getHostName());
out.println("<br/>JVM可以使用的总内存:    " + r.totalMemory());
out.println("<br/>JVM可以使用的剩余内存:    " + r.freeMemory());
out.println("<br/>JVM可以使用的处理器个数:    " + r.availableProcessors());
out.println("<br/>Java的运行环境版本：    " + props.getProperty("java.version"));
out.println("<br/>Java的运行环境供应商：    " + props.getProperty("java.vendor"));
out.println("<br/>Java供应商的URL：    " + props.getProperty("java.vendor.url"));
out.println("<br/>Java的安装路径：    " + props.getProperty("java.home"));
out.println("<br/>Java的虚拟机规范版本：    " + props.getProperty("java.vm.specification.version"));
out.println("<br/>Java的虚拟机规范供应商：    " + props.getProperty("java.vm.specification.vendor"));
out.println("<br/>Java的虚拟机规范名称：    " + props.getProperty("java.vm.specification.name"));
out.println("<br/>Java的虚拟机实现版本：    " + props.getProperty("java.vm.version"));
out.println("<br/>Java的虚拟机实现供应商：    " + props.getProperty("java.vm.vendor"));
out.println("<br/>Java的虚拟机实现名称：    " + props.getProperty("java.vm.name"));
out.println("<br/>Java运行时环境规范版本：    " + props.getProperty("java.specification.version"));
out.println("<br/>Java运行时环境规范供应商：    " + props.getProperty("java.specification.vender"));
out.println("<br/>Java运行时环境规范名称：    " + props.getProperty("java.specification.name"));
out.println("<br/>Java的类格式版本号：    " + props.getProperty("java.class.version"));
out.println("<br/>Java的类路径：    " + props.getProperty("java.class.path"));
out.println("<br/>加载库时搜索的路径列表：    " + props.getProperty("java.library.path"));
out.println("<br/>默认的临时文件路径：    " + props.getProperty("java.io.tmpdir"));
out.println("<br/>一个或多个扩展目录的路径：    " + props.getProperty("java.ext.dirs"));
out.println("<br/>操作系统的名称：    " + props.getProperty("os.name"));
out.println("<br/>操作系统的构架：    " + props.getProperty("os.arch"));
out.println("<br/>操作系统的版本：    " + props.getProperty("os.version"));
out.println("<br/>文件分隔符：    " + props.getProperty("file.separator"));
out.println("<br/>路径分隔符：    " + props.getProperty("path.separator"));
out.println("<br/>行分隔符：    " + props.getProperty("line.separator"));
out.println("<br/>用户的账户名称：    " + props.getProperty("user.name"));
out.println("<br/>用户的主目录：    " + props.getProperty("user.home"));
out.println("<br/>用户的当前工作目录：    " + props.getProperty("user.dir"));
out.println("<br/>用户的当前工作目录：    " + props.getProperty("user.dir")+"<br/><br/>");

Set set = props.keySet();
Iterator it = set.iterator();
while(it.hasNext()){
	out.println(it.next());
}
out.println(props.entrySet());

//Context env = (Context) new InitialContext().lookup("java:comp/env");
//String jvmRoute= (String)env.lookup("jvmRoute");
//out.println("<br/>jvmRoute："+jvmRoute);

%>


