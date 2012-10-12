<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.swesource.sample.jee.jbossas6.DataRemote" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="com.swesource.sample.jee.jbossas6.SyncSlsb" %>
<html>
<head>
    <title>JSP sync test - send</title>
</head>
<body>
<%!
SyncSlsb syncSlsb;
%>
<%
Context context = new InitialContext();
syncSlsb = (SyncSlsb) context.lookup("sample-jee-jbossas6/SyncSlsb/no-interface");
syncSlsb.addMessage(1);
%>
<h1>Message sent to SyncSlsb (-> SampleQueue -> SyncMDB)</h1>
<a href="syncCheck.jsp">Check</a> if all is ok.
</body>
</html>