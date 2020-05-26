<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 25.05.2019
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tables</title>
</head>
<body>
<% List<String> list = (List<String>) request.getAttribute("tables");
    for (String s :
            list) { %>
<p><%=s%></p>
<% } %>
</body>
</html>
