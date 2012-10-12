<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.swesource.sample.jee.jbossas6.DataRemote" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="com.swesource.sample.jee.jbossas6.SyncSlsb" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<html>
<head>
    <title>JSP sync test - check</title>
</head>
<body>
<%!
SyncSlsb syncSlsb;
%>
<%
Context context = new InitialContext();
syncSlsb = (SyncSlsb) context.lookup("sample-jee-jbossas6/SyncSlsb/no-interface");
Set<String> ids = syncSlsb.listMessageIds();
if(!ids.isEmpty()) {
%>
<h1>List of unprocessed JMSMessageCorrelationIDs</h1>
<%
    Iterator iterator = ids.iterator();
    while(iterator.hasNext()) {
        String id = (String)iterator.next();

%>
<%= id %>
<br/>
<%
    }
}
else {
%>
<h1>All JMSMessages has been processed!</h1>
<%
}
%>
</body>
</html>