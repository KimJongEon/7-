<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--글쓰기 입력 폼 -->
	<form action="/web/addPost" method="POST">
	 제목 : <input type="text" name="title" id="title"><br/>
	<p>내용</p><textarea rows="5" cols="30" name="content" id="content"></textarea>
	<input type="submit" value="AddPost"/>
	</form>
</body>
</html>