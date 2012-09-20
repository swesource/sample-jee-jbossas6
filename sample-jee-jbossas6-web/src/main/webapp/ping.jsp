<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.swesource.sample.jee.jbossas6.DataRemote" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<html>
<head>
    <title>JSP ping test</title>
</head>
<body>
<%!
DataRemote dataBean;
%>
<%
try {
Context context = new InitialContext();
dataBean = (DataRemote) context.lookup("sample-jee-jbossas6/DataBean/remote");
}
catch(Exception e) {
// exception code here
}
%>
What's your name bean?<br/>
<%= dataBean.ping() %>
</body>
</html>