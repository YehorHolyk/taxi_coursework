<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
</head>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body style="margin-bottom: 20%;">
<%@ include file="/WEB-INF/view/jspf/menu.jspf"%>
<h2 style="margin-left: 11%; margin-top: 1%;">Zapros page</h2>
<table class="table table-hover" id="orders" style="margin-top: 1%;">
    <thead>
    <tr>
        <th scope="col">email</th>
        <th scope="col">total</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${rs}" var="entity">
        <tr>
            <td scope="row"><c:out value="${entity['email']}"/></td>
            <td><c:out value="${entity['sum']}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
<%@ include file="/WEB-INF/view/jspf/footer.jspf"%>
</html>