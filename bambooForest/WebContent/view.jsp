<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 게시글 제목 및 내용 -->
	<h2>게시글</h2>
	
	<h3>제목 : ${post.title}</h3>
		내용 : ${post.content}
	
<br/>
────────────────────────────────────────────────
<br/>
	<!-- 댓글 리스트 -->
	<h2>댓글</h2>
	<c:forEach var="re" items="${replyList}">
					<li>
						<c:if test="${post.memberid == re.memberid}">(글쓴이)</c:if>
						순번 : ${re.replyid} <br/>
						내용 : ${re.comment} <br/>
						작성일자 : ${re.created} <br/><br/>
					</li>
	</c:forEach>
</body>
</html>