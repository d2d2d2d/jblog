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
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/admin-header.jsp" />
		
		<div id="wrapper">
			<div id="content" class="full-screen">
				<jsp:include page="/WEB-INF/views/includes/admin-menu.jsp" />
				<form
					action="${pageContext.request.contextPath }/${authUser.id}/upload"
					method="post" enctype="multipart/form-data">
					<table class="admin-config">
						<tr>
							<td class="t">블로그 제목</td>
							<td><input type="text" size="40" name="title" value="a${BlogVo.title }"></td>
						</tr>
						<tr>
							<td class="t">로고이미지</td>
							<td><img
								src="${pageContext.request.contextPath}${BlogVo.logo }"></td>
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