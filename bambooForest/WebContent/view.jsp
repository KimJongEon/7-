<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script>
//링크방식으로는 사용못함
$(document).ready(function() {
	$("#addComment").click(function(){
		var query={comment: $("#comment").val(),
				   memberid: "${member.memberid}",
				   postid: "${post.postid}"
		};
		$.ajax({
			type : "post",
			url : "/api/addComment",
			data: query,
			dataType : "text",
			success : function(data) {
				console.log(data);
				var Obj = JSON.parse(data);
				var li1 = '<li class="list-group-item"><p>';
				var comment = "";
				if(Obj.memberid == "${post.memberid}")
					comment = comment + "(글쓴이)";
					comment = comment + Obj.comment + "-" + Obj.created;
				
				var li2= '</p></li>';
				var li = li1 + comment + li2;
				$("#replyList").append(li);
				$("#comment").val("");
				
				
			}
		});
	});
});
</script>

</head>
<body>
	<!-- 게시글 제목 및 내용 -->
	<h2>게시글</h2>
	
	<h3>제목 : ${post.title}</h3>
		내용 : ${post.content}, ${post.created}
		${post.postid}
		
	
<br/>

<textarea rows="5" cols="30" name="comment" id="comment"></textarea>
<button id="addComment">Add</button>

────────────────────────────────────────────────
<br/>
	<!-- 댓글 리스트 -->
	<h2>댓글</h2>
	
	<ul class="list-group" id="replyList">
		<c:forEach var="item" items="${requestScope.replyList}">
			<li class="list-group-item"><p>
					<c:if test="${post.memberid == item.memberid }">(글쓴이)</c:if>
					${item.comment } - ${item.created }
				</p></li>

		</c:forEach>
	</ul>
</body>
</html>