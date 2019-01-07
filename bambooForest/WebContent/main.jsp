<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
  	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  	crossorigin="anonymous">
</script>
<script src='/js/main.js'></script>
<title>main.jsp</title>
</head>
<body>
	<h1>Bamboo Forest</h1>
	<c:if test="${!empty sessionScope.member}">
		<div>
			<p>${member.memberid}님  <button id="logout">Logout</button></p>
		</div>
	</c:if>
	<div>
		<jsp:include page="${content}" flush="false"/>
	</div>
</body>
</html>