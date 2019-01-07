$(document).ready(function() {
	$("#logout").click(function() {

		$.ajax({
			type : "post",
			url : "/api/logout",
			success : function() {
				location.href = "/web/index";
			}
		});
	});
});