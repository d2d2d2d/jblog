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
				<form action="${pageContext.servletContext.contextPath }/${authUser.id }/admin/write" method="post">
					<table class="admin-cat-write">
						<tr>
							<td class="t">제목</td>
							<td><input type="text" size="60" name="title"> <select
								name="categoryNo">
									<c:forEach items="${list }" var="vo">
										<option value="${vo.no }">${vo.name }</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td class="t">내용</td>
							<td><textarea name="contents"></textarea></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td class="s"><input type="submit" value="포스트하기"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/admin-footer.jsp" />
	</div>
</body>
</html>