<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
</head>
<%@ include file="/WEB-INF/view/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/view/jspf/menu.jspf" %>
<form method="POST" action="/login" style="margin-left: 30%; margin-right:30%; margin-top: 3%;">
    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" class="form-control" id="email" name="email">
    </div>
    <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" class="form-control" id="password" name="password">
    </div>

    <button type="submit" class="btn btn-dark" style="width: 100%;">Login</button>
</form>
<div class="col-md-3">
    <div class="form-control-feedback">
        <span class="text-danger align-middle">
            <c:if test="${error_map != null}">
                <c:if test="${error_map['error_email'] !=null}">
                    <i class="fa fa-close"><c:out value="${error_map['error_email']}"/></i>
                </c:if>
                <c:if test="${error_map['error_password'] !=null}">
                    <i class="fa fa-close"><c:out value="${error_map['error_password']}"/></i>
                </c:if>
            </c:if>
        </span>
    </div>
</div>
</body>
<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</html>