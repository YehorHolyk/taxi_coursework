<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
</head>
<%@ include file="/WEB-INF/view/jspf/head.jspf" %>
<body style="margin-bottom: 15%;">
<%@ include file="/WEB-INF/view/jspf/menu.jspf" %>
<h3 style="margin-left: 10%; margin-top: 2%;">Taxi catalogue</h3>
<form method="POST" action="/search" style="margin-left: 30%; margin-right:30%; margin-top: 3%;">
    <div class="form-group">
        <label for="classAuto">Class</label>
        <input  class="form-control" id="classAuto" name="classAuto">
    </div>
    <div class="form-group">
        <label for="minPrice">Min price</label>
        <input  class="form-control" id="minPrice" name="minPrice">
    </div>
    <div class="form-group">
        <label for="maxPrice">Max price</label>
        <input  class="form-control" id="maxPrice" name="maxPrice">
    </div>
    <button type="submit" class="btn btn-dark" style="width: 100%;">Search</button>
</form>
<c:forEach var="item" items="${autos}">
    <c:if test="${!shoppingCart.containsKey(item['licensePlate'])}">
        <div class="card" style="margin-left: 10%; width:80%; margin-top: 15px; margin-bottom: 15px;">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-7">
                        <table class="table table-hover" class="auto" style="width: 50%;">
                            <tbody>
                            <tr>
                                <td>Brand</td>
                                <td><c:out value="${item['brand']}"/></td>
                            </tr>
                            <tr>
                                <td>Class</td>
                                <td><c:out value="${item['autoClass']}"/></td>
                            </tr>
                            <tr>
                                <td>Driver</td>
                                <td><c:out value="${item['licensePlate']}"/></td>
                            </tr>
                            <tr>
                                <td>Price</td>
                                <td style="color: darkorange"><c:out value="${item['price']}"/> + 7.75 UAH per
                                    kilometer
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-5">
                        <img class="card-img-top"
                             src="<c:out value="${item['pictureURL']}"/>" alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title"><c:out value="${item['model']}"/></h5>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-2">
                        <div class="card-body">
                            <form action="addToShoppingCart" method="POST">
                                <input name="license_plate" value="${item['licensePlate']}" type="hidden">
                                <button type="submit" href="/addToShoppingCart?license_plate=${item['licensePlate']}"
                                        class="btn btn-dark" style="margin-left: 2%;">Add to shopping cart
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="col-2" style="text-align: center">
                        <p style="margin-top: 20px">
                            <a href="AutoInfo?license_plate=${item['licensePlate']}" class="card-link">See
                                details</a>
                        </p>

                    </div>
                    <c:set var="status" value="ADMIN" scope="session"/>
                    <c:if test="${sessionScope.user['userStatus'] == status }">
                        <div class="col-2">
                            <div class="card-body">
                                <form action="DeleteAuto" method="POST">
                                    <input name="license_plate" value="${item['licensePlate']}" type="hidden">
                                    <button type="submit" href="/DeleteAuto?license_plate=${item['licensePlate']}"
                                            class="btn btn-warning" style="margin-left: 2%;">DELETE
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:if>
</c:forEach>


</body>
<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</html>
