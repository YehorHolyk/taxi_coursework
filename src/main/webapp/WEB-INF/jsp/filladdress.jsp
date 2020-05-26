<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf" %>
<body style="margin-bottom: 20%;">
<%@ include file="/WEB-INF/view/jspf/menu.jspf" %>


<h2 style="margin-left: 11%; margin-top: 1%;">Fill the taxi's route</h2>
<c:if test="${addresses.size() eq 0}">
    <h4 style="margin-left: 11%;">From:</h4>

    <form method="post" action="filladdress">
        <div class="row" style="margin-left: 10%;width: 50%; margin-top: 1%;">
            <div class="col">
                <label class="col col-form-label" style="margin-left: -15px;">Start address</label>
            </div>
            <div class="col">
                <input type="text" class="form-control" name="district" placeholder="District" required>
            </div>
            <div class="col">
                <input type="text" class="form-control" name="street" placeholder="Street" required>
            </div>
            <div class="col">
                <input type="text" class="form-control" name="homeNumber" placeholder="Home_number" required>
            </div>
            <div class="col">
                <input type="submit" class="form-control" value="Send">
            </div>
        </div>
    </form>
</c:if>
<c:if test="${addresses.size() > 0}">
    <h4 style="margin-left: 11%;">From:</h4>
    <table class="table table-hover" id="profile" style="width: 50%; margin-left: 12%;">
        <thead>
        <tr>
            <th scope="col">Address</th>
            <th scope="col">District</th>
            <th scope="col">Street</th>
            <th scope="col">Home number</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="type" items="${addresses}">
            <c:if test="${type.key == 1}">
            <tr>
                <td>${type.key}</td>
                <td>${type.value['district']}</td>
                <td>${type.value['street']}</td>
                <td>${type.value['homeNumber']}</td>
            </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<h4 style="margin-left: 11%;">To:</h4>
<c:if test="${addresses.size() > 0}">
    <table class="table table-hover" id="profile" style="width: 50%; margin-left: 12%;">
        <thead>
        <tr>
            <th scope="col">Address</th>
            <th scope="col">District</th>
            <th scope="col">Street</th>
            <th scope="col">Home number</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="type" items="${addresses}">
            <c:if test="${type.key != 1}">
                <tr>
                    <td>${type.key}</td>
                    <td>${type.value['district']}</td>
                    <td>${type.value['street']}</td>
                    <td>${type.value['homeNumber']}</td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<form method="post" action="filladdress">
    <div class="row" style="margin-left: 11%;width: 50%; margin-top: 1%;">
        <div class="col">
            <label class="col col-form-label" style="margin-left: -20px;">Destination address</label>
        </div>
        <div class="col">
            <input type="text" class="form-control" name="district" placeholder="District" required>
        </div>
        <div class="col">
            <input type="text" class="form-control" name="street" placeholder="Streer" required>
        </div>
        <div class="col">
            <input type="text" class="form-control" name="homeNumber" placeholder="Home_number" required>
        </div>
        <c:if test="${addresses.size() != 0}">
            <div class="col">
                <input type="submit" class="form-control" value="Send">
            </div>
        </c:if>
    </div>
</form>


<h4 style="margin-left: 10%; margin-top: 2%;">Ordered cars</h4>
<table class="table table-hover" id="orders" style="width: 50%; margin-left: 10%; margin-top: 1%;">
    <thead>
    <tr>
        <th scope="col">Taxi</th>
        <th scope="col">Class</th>
        <th scope="col">Seats</th>
        <th scope="col">Price</th>
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
        </tr>
        <c:set var = "salary" scope = "session" value = "${salary+entity.value['price']}"/>
    </c:forEach>
    </tbody>
</table>
<c:set var="date" scope="session" value="<%=new java.util.Date()%>" />
<table class="table table-hover" id="orders" style="width: 50%; margin-left: 10%; margin-top: 1%;">
    <tbody>
    <tr>
        <td>Total price</td>
        <td id="total"><c:out value="${salary}"/> UAH + 7.75 UAH per kilometer</td>
    </tr>
    <tr>
        <td>Date</td>
        <td><fmt:formatDate type="date" value="${date}" />  </td>
    </tr>
    </tbody>
</table>
<c:set var="summ" scope="session" value="${salary}"/>

<h4 style="margin-left: 10%; margin-top: 2%;">Customer Information</h4>
<table class="table table-hover" id="profile" style="width: 50%; margin-left: 10%;">
    <tbody>
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
<form action="/order" method="POST">
    <button type="submit" class="btn btn-dark" style="width: 50%; margin-left: 10%;">Confirm order</button>
</form>

</body>
<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</html>
