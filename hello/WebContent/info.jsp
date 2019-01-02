<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script
  	src="https://code.jquery.com/jquery-3.3.1.min.js"
  	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  	crossorigin="anonymous">
  	</script>
  	
  	<script>
  	//id와 name 값을 모두 읽은 후에 실행 됨
  		$(document).ready(function(){
  			
  			// ADD 클릭시 작동
  			$("#insert").click(function(){
  				var jval = $("#job").val();
  				console.log(jval);
 
  				$.ajax({
  					type : 'POST',
  					url: '/api/insert',
  					data: { job : jval},
  					dataType: 'text',
  					success:function(data){
  						var obj = JSON.parse(data);
  						console.log(obj);
  						if(obj.result == "OK"){
  							var li = '<li><input type="radio" name="job" value="'+obj.id+'"/>'+jval+'+"false"+</li>';
  							$("#list").append(li);
  							$("#job").val("");
  						}else{
  						
  						}
  					}
  				
  				}); //ajax
  			}); //insert click
  			
  			$("#update").click(function(){
					var selValue = $('input[name=job]:checked').val();
					alert(selValue);
					$.ajax({
	  					type : 'POST',
	  					url: '/api/update',
	  					data: { id : selValue},
	  					dataType: 'text',
	  					success:function(data){
	  						var obj = JSON.parse(data);
	  						if(obj.result == "OK"){
	  							alert("Updated");
	  							var divid = "#"+selValue;
	  							$(divid).text("true");
	  						}else{
	  						
	  						}
	  					}
					});	
			}); // update click
			
  		}); //document
  	</script>
</head>
<body>
<h1>info</h1> <br/>

<%-- <c:forEach var="row" items="${rs}"> --%>
<%--     Id: ${row.id}<br/> --%>
<%--     Job: ${row.job}<br/> --%>
<%--     Done: ${row.done}<br/> --%>
<%-- </c:forEach> --%>
<input type="text" id="job"/>
<button id="insert">ADD</button>

<button id="update">UPDATE</button>
<ul id="list">
<c:forEach var="row" items="${rs}">
	<li><input type="radio" name="job" value="${row.id}"/>
    	${row.job}, <div id="${row.id}"></div>, ${row.done}
	</li>
</c:forEach>
</ul>
</body>
</html>