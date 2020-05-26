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
<h3 style="margin-left: 10%; margin-top: 2%;"><c:out value="${auto['model']}"/></h3>
<div class="card" style="margin-left: 10%; width:80%; margin-top: 15px; margin-bottom: 15px;">
    <img class="card-img-top" src="<c:out value="${auto['pictureURL']}"/> " style="width: 30%;" alt="Card image cap">
    <div class="card-body">
        <h5 class="card-title"><c:out value="${auto['model']}"/></h5>
    </div>
    <table class="table table-hover" class="auto" style="width: 50%;">
        <tbody>
        <tr>
            <td>Brand</td>
            <td name="brand"><c:out value="${auto['brand']}"/></td>
        </tr>
        <tr>
            <td>Class</td>
            <td name="class"><c:out value="${auto['autoClass']}"/></td>
        </tr>
        <tr>
            <td>Seats</td>
            <td name="seats"><c:out value="${auto['seats']}"/></td>
        </tr>
        <tr>
            <td>Driver</td>
            <td name="driver"><c:out value="${auto['licensePlate']}"/></td>
        </tr>
        <tr>
            <td>Price</td>
            <td class="name" style="color: darkorange"><c:out value="${auto['price']}"/> + 7.75 UAH per kilometer</td>
        </tr>
        </tbody>
    </table>
    <div class="card-body">
        <button type="button" class="btn btn-dark" style="margin-left: 2%;">Add to shopping cart</button>
    </div>
</div>

</body>
<%@ include file="/WEB-INF/view/jspf/footer.jspf"%>
</html>
