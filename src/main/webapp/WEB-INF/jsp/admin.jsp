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
<h3 style="margin-left: 10%; margin-top: 2%;">Orders:</h3>
<table class="table table-hover" id="orders" style="margin-top: 1%;">
    <thead>
    <tr>
        <th scope="col">Order_id</th>
        <th scope="col">Taxi booked</th>
        <th scope="col">License plate</th>
        <th scope="col">User id</th>
        <th scope="col">Login</th>
        <th scope="col">User telephone number</th>
        <th scope="col">User email</th>
        <th scope="col">Route</th>
        <th scope="col">Order date</th>
        <th scope="col">Order status</th>
        <th scope="col">Control status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.orders}" var="entity">

        <tr>
            <th scope="row"><c:out value="${entity['orderId']}"/></th>
            <td><c:out value="${entity['model']}"/></td>
            <td><c:out value="${entity['licensePlate']}"/></td>
            <td><c:out value="${entity['userId']}"/></td>
            <td><c:out value="${entity['login']}"/></td>
            <td><c:out value="${entity['telephoneNumber']}"/></td>
            <td><c:out value="${entity['email']}"/></td>
            <td><c:out value="${entity['route']}"/></td>
            <td><fmt:formatDate type="date" value="${entity['orderDate']}"/></td>
            <td><c:out value="${entity['orderStatus']}"/></td>
            <td>
                <div class="dropdown show">
                    <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Change status
                    </a>

                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <%--<a class="dropdown-item" href="/updateorder?orderId=${entity['orderId']}&idStatus=${1}">On
                            hold</a>--%>
                        <c:if test="${entity['orderStatus'] eq 'On hold'}">
                        <a class="dropdown-item" href="/updateorder?orderId=${entity['orderId']}&idStatus=${2}" class="text-primary">In
                            processing</a>
                        </c:if>
                            <c:if test="${entity['orderStatus'] eq 'In processing'}">
                        <a class="dropdown-item"
                           href="/updateorder?orderId=${entity['orderId']}&idStatus=${3}" class="text-primary">Completed</a>
                        <a class="dropdown-item"
                           href="/updateorder?orderId=${entity['orderId']}&idStatus=${4}" class="text-primary">Canceled</a>
                            </c:if>
                        <c:if test="${entity['orderStatus'] eq 'Completed'}">
                            <a class="dropdown-item" href="#" class="text-success">Order completed!</a>
                        </c:if>
                            <c:if test="${entity['orderStatus'] eq 'Canceled'}">
                                <a class="dropdown-item" href="#" class="text-danger">Order canceled!</a>
                            </c:if>
                    </div>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


</body>
<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</html>
