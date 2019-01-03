<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
	table { border-collapse:collapse; }
	table td, th {border:1px solid;}
	
</style>
</head>
<body>
<h3>게시글 목록</h3>
	<table>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>등록일</th>
			</tr>
		</thead>
		
		<tbody>
				<c:forEach var="post" items="${postList}">
					<tr>
						<td>${post.postid}</td>
						<td><a href="/web/view?postid=${post.postid}">${post.title}</a></td>
						<td>${post.created}</td>
					</tr>
				</c:forEach>
		</tbody>
	</table>
</body>
</html>