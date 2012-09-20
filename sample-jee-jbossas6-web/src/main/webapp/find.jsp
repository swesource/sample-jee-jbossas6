<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.swesource.sample.jee.jbossas6.DataRemote" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<html>
<head>
    <title>JSP find test</title>
</head>
<body>
<%!
DataRemote dataBean;
%>
<%
try {
Context context = new InitialContext();
dataBean = (DataRemote) context.lookup("sample-jee-jbossas6/DataBean/remote");
    for(int i = 0 ; i < 10 ; i++) {
%>
Loop #<%= i %>
<%= dataBean.find(1L).getData() %>
<br/>
<%
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e) {
            System.out.println("Loop #" + i);
            e.printStackTrace();
        }
    }
}
catch(Exception e) {
// exception code here
}
%>
</body>
</html>