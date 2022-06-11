<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 08/06/2022
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    
    
    <meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">
	
	
    <title>File Upload Page</title>
</head>

<body>
<h2>File Upload</h2>

<form   action ="import" method = "POST" enctype = "multipart/form-data">
    <br /> <br />
    Please select a file to upload :
    <input type = "file" name = "file" value = "Browse File" /> <br /> <br />
    Press here to upload the file :
    <input type = "submit" value = "upload" /> <br /> <br />

    <h4 style="color: green">${message}</h4> <br />

  <script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/popper.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
  
</form>
</body>
</html>