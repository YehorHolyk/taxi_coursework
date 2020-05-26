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
<h2 style="margin-left: 11%; margin-top: 1%;">Account page</h2>
<table class="table table-hover" id="profile" style="width: 50%; margin-left: 10%;">
    <tbody>
    <tr>
        <td>Login</td>
        <td id="login"><c:out value="${user['userLogin']}"/></td>
    </tr>
    <tr>
        <td>Name</td>
        <td id="name"><c:out value="${user['userName']}"/></td>
    </tr>
    <tr>
        <td>E-mail</td>
        <td id="email"><c:out value="${user['userEmail']}"/></td>
    </tr>
    <tr>
        <td>Phone number</td>
        <td id="phone"><c:out value="${user['userTelephoneNumber']}"/></td>
    </tr>
    </tbody>
</table>
<hr>
<h2 style="margin-left: 11%; margin-top: 1%;">My orders</h2>
<table class="table table-hover" id="orders" style="margin-top: 1%;">
    <thead>
    <tr>
        <th scope="col">Order_id</th>
        <th scope="col">Taxi booked</th>
        <th scope="col">License plate</th>
        <th scope="col">Route</th>
        <th scope="col">Order date</th>
        <th scope="col">Order status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${sessionScope.orderViews}" var="entity">
    <tr>
        <th scope="row"><c:out value="${entity['orderId']}"/></th>
        <td><c:out value="${entity['model']}"/></td>
        <td><c:out value="${entity['licensePlate']}"/></td>
        <td><c:out value="${entity['route']}"/></td>
        <td><fmt:formatDate type="date" value="${entity['orderDate']}" /></td>
        <td><c:out value="${entity['orderStatus']}"/></td>
    </tr>
    </c:forEach>
    </tbody>
</table>
</body>
<%@ include file="/WEB-INF/view/jspf/footer.jspf"%>
</html>