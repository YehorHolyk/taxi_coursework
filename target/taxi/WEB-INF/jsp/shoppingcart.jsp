<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
</head>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body style="height: 1000px;">
<%@ include file="/WEB-INF/view/jspf/menu.jspf"%>
<h2 style="margin-left: 11%; margin-top: 1%;">My shopping cart</h2>
<table class="table table-hover" id="orders" style="width: 50%; margin-left: 10%; margin-top: 1%;">
    <thead>
    <tr>
        <th scope="col">Taxi</th>
        <th scope="col">Class</th>
        <th scope="col">Seats</th>
        <th scope="col">Price</th>
        <th scope="col">_____</th>
    </tr>
    </thead>
    <tbody>
    <c:set var = "salary" scope = "session" value = "${0}"/>
    <c:forEach items="${shoppingCart}" var="entity">
    <tr>
        <td ><c:out value="${entity.value['model']}"/></td>
        <td ><c:out value="${entity.value['autoClass']}"/></td>
        <td ><c:out value="${entity.value['seats']}"/></td>
        <td ><c:out value="${entity.value['price']}"/></td>
        <td><a href="/removeFromShoppingCart?license_plate=${entity.value['licensePlate']}">Remove</a></td>
    </tr>
        <c:set var = "salary" scope = "session" value = "${salary+entity.value['price']}"/>
    </c:forEach>
    </tbody>
</table>
<table class="table table-hover" id="orders" style="width: 50%; margin-left: 10%; margin-top: 1%;">
    <tbody>
    <tr>
        <td>Total price</td>
        <td id="total"><c:out value="${salary}"/> UAH + 7.75 UAH per kilometer</td>
    </tr>
    </tbody>
</table>
<form action="/filladdress" method="GET">
    <c:choose>
        <c:when test="${sessionScope.shoppingCart.size() eq 0}">
            <p>Choose something=)</p>
        </c:when>
        <c:otherwise>
            <button type="submit" class="btn btn-dark" style="width: 50%; margin-left: 10%;">Order</button>
        </c:otherwise>
    </c:choose>
    <%--<button type="submit" class="btn btn-dark" style="width: 50%; margin-left: 10%;" ${sessionScope.shoppingCart.size<0 ? 'disabled="disabled"' : ''}>Order</button>--%>
</form>



</body>
<%@ include file="/WEB-INF/view/jspf/footer.jspf"%>
</html>
