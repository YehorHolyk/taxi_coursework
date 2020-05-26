<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
</head>
<%@ include file="/WEB-INF/view/jspf/head.jspf" %>
<body style="margin-bottom: 20%;">
<%@ include file="/WEB-INF/view/jspf/menu.jspf" %>
<h2 style="margin-left: 11%; margin-top: 1%;">Add auto</h2>
<form method="post" action="/addauto" style="margin-left: 15%; margin-right: 50%">
    <div class="form-group">
        <label for="brand">Brand</label>
        <input type="text" class="form-control" id="brand" name="brand"
               placeholder="BMW">
    </div>
    <div class="form-group">
        <label for="model">Model</label>
        <input type="text" class="form-control" id="model" name="model"
               placeholder="BMW M3 GTR">
    </div>
    <div class="form-group">
        <label for="model">Manufacturer</label>
        <input type="text" class="form-control" id="manufacturer" name="manufacturer"
               placeholder="Berlin Motors">
    </div>
    <div class="form-group">
        <label for="license_plate">License plate</label>
        <input type="text" class="form-control" id="license_plate" name="license_plate"
               placeholder="AA0000AA">
    </div>
    <div class="form-group">
        <label for="price">Price</label>
        <input type="text" class="form-control" id="price" name="price"
               placeholder="250">
    </div>
    <div class="form-group">
        <label for="class">Class</label>
        <input type="text" class="form-control" id="class" name = "class"
               placeholder="Economy">
    </div>
    <div class="form-group">
        <label for="seats">Seats</label>
        <input type="text" class="form-control" id="seats" name = "seats"
               placeholder="250">
    </div>
    <div class="form-group">
        <label for="pictureURL">Image URL</label>
        <input type="text" class="form-control" id="pictureURL" name="pictureURL"
               placeholder="">
    </div>
    <button type="submit" class="btn btn-primary">Add</button>
</form>
</body>
<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</html>
