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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.4.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script>
	var startNo = 0;
	var isEnd = false;
	var isFetching = false;

	var listItemTemplate = new EJS(
			{
				url : "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"
			});

	var listTemplate = new EJS(
			{
				url : "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
			});

	var messageBox = function(title, message, callback) {
		$("#dialog-message p").text(message);
		$("#dialog-message").attr("title", title).dialog({
			modal : true,
			buttons : {
				"확인" : function() {
					$(this).dialog("close");
				}
			},
			close : callback
		});
	}

	var render = function(vo, mode) {
		var html = "<td>" + vo.no + "</td>" + "<td>" + vo.name + "</td>"
				+ "<td>" + vo.postCount + "</td>" + "<td>" + vo.description
				+ "</td>" + "<td></td>";

		if (mode) {
			$("#list-category").prepend(html);
		} else {
			$("#list-category").append(html);
		}
		//	$("#list-guestbook")[mode ? "prepend" : "append"](html);
	}

	var fetchList = function() {
		if (isEnd || isFetching) {
			isFetching = false;
			return;
		}
		isFetching = true;
		$.ajax({
					url : '${pageContext.request.contextPath }/${authUser.id}/api/blog/list',
					async : true,
					type : 'get',
					dataType : 'json',
					data : '',
					success : function(response) {
						if (response.result != "success") {
							console.error(response.message);
							return;
						}
						// response로 보냄
						response.pageContext = "${pageContext.request.contextPath}";
						var html = listTemplate.render(response);
						$("#list-category").append(html);

						startNo = $('#list-category td').last().data('no') || 0;
					},
					error : function(xhr, status, e) {
						console.error(status + ":" + e);
					}
				});
	}
	$(document).on('click','#list-category a', function(event){
		event.preventDefault();
		var deleteno = $(this).data('no');
		$(this).parents('tr').remove();
		$.ajax({
			url: '${pageContext.request.contextPath }/${authUser.id}/api/blog/delete/' + deleteno,
			async: true,
			type: 'delete',
			dataType: 'json',
			data: '',
			success: function(response){
				if(response.result != "success"){
					console.error(response.message);
					return;
				}
			},
			error: function(xhr, status, e){
				console.error(status + ":" + e);
			}
		});
		
	});

	$(function() {
		// 입력폼 submit 이벤트
		$('#add-form')
				.submit(
						function(event) {
							event.preventDefault();

							var vo = {};
							vo.name = $("#input-name").val();
							if (vo.name == '') {
								messageBox("카테고리 추가", "카테고리 이름은 필수 항목 입니다.",
										function() {
											$("#input-name").focus();
										});
								return;
							}

							vo.description = $("#input-description").val();
							if (vo.description == '') {
								messageBox("카테고리 추가", "카테고리 설명은 필수 항목 입니다.",
										function() {
											$("#input-description").focus();
										});
								return;
							}

							$
									.ajax({
										url : '${pageContext.request.contextPath }/${authUser.id}/api/blog/add',
										async : true,
										type : 'post',
										dataType : 'json',
										contentType : 'application/json',
										data : JSON.stringify(vo),
										success : function(response) {
											if (response.result != "success") {
												console.error(response.message);
												return;
											}

											response.data.pageContext = "${pageContext.request.contextPath}";
											var html = listItemTemplate
													.render(response.data);
											$("#list-category").append(html);

											// form reset
											$("#add-form")[0].reset();
										},
										error : function(xhr, status, e) {
											console.error(status + ":" + e);
										}
									});
						});
		fetchList();
	});
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/admin-header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<jsp:include page="/WEB-INF/views/includes/admin-menu.jsp" />
				<table id="list-category" class="admin-cat">
					<tr>
						<th>번호</th>
						<th>카테고리명</th>
						<th>포스트 수</th>
						<th>설명</th>
						<th>삭제</th>
					</tr>
				</table>

				<h4 class="n-c">새로운 카테고리 추가</h4>
				<form id="add-form" action="" method="post">
					<table id="admin-cat-add">
						<tr>
							<td class="t">카테고리명</td>
							<td><input type="text" id="input-name" name="name"></td>
						</tr>
						<tr>
							<td class="t">설명</td>
							<td><input type="text" id="input-description"
								name="description"></td>
						</tr>
						<tr>
							<td class="s">&nbsp;</td>
							<td><input type="submit" value="카테고리 추가"></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/admin-footer.jsp" />
	</div>
</body>
</html>