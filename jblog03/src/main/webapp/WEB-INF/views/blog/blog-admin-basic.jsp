<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.4.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script>
var imageTemplate = new EJS(
		{
			url : "${pageContext.request.contextPath }/assets/js/ejs/image-template.ejs"
		});
	$(function() {
		// 입력폼 submit 이벤트
		$('#add-form')
				.submit(
						function(event) {
							event.preventDefault();

							var vo = {};
							vo.name = $("#image_container").val();
							$
									.ajax({
										//url : '${pageContext.request.contextPath }/${authUser.id}/api/blog/uploadImage',
										url : "",
										type : 'get',
										dataType : 'json',
										success : function(response) {
											if (response.result != "success") {
												console.error(response.message);
												return;
											};
											response.data.pageContext = "${pageContext.request.contextPath}${blogVo.logo }";
											var html = imageTemplate
													.render(response.data);
											$("#image_container").append(html);
										},
										error : function(xhr, status, e) {
											console.error(status + ":" + e);
										}
									});
						});

	});
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/admin-header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<jsp:include page="/WEB-INF/views/includes/admin-menu.jsp" />
				<form id="add-form"
					action="${pageContext.request.contextPath }/${authUser.id}/upload"
					method="post" enctype="multipart/form-data">
					<table class="admin-config">
						<tr>
							<td class="t">블로그 제목</td>
							<td><input type="text" size="40" name="title"
								value="${blogVo.title }"></td>
						</tr>
						<tr>
							<td class="t">로고이미지</td>
							<td><img
								src="${pageContext.request.contextPath}${blogVo.logo } id="image_container"></td>
						</tr>
						<tr>
							<td class="t">&nbsp;</td>
							<td><input type="file" name="logo-file"></td>
						</tr>
						<tr>
							<td class="t">&nbsp;</td>
							<td class="s"><input type="submit" value="기본설정 변경"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/admin-footer.jsp" />
	</div>
</body>
</html>