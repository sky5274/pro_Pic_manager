<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/searchContext.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resource/css/title_top.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resource/css/main_contain.css" />
<script type="text/javascript">
	$(window).load(function() {
		var ser = new search($(".searchbox"));
		ser._init({
			limit : '20'
		})
	})
</script>
</head>
<body>
	<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/hibernat?characterEncoding=utf8&useSSL=false"
		user="sky_" password="sky5274" />

	<sql:query dataSource="${snapshot}" var="result">
		SELECT DISTINCT clazz from picture ;
	</sql:query>

	<sql:query dataSource="${snapshot}" var="itemMenu">
		SELECT theme,clazz from picture group by theme order by clazz ,theme ;
	</sql:query>

	<div class="contain">
		<div class="title_top">
			<div class="lable">
				<a href="<%=request.getContextPath()%>">首页</a>
			</div>
			<div class="searchbox">
				<form action="commitSearch.do">
					<label>搜索</label> <input type="text" name="search" id="search" />
					<input type="submit" value="确认" />
				</form>

			</div>
			<div id="useractioon"></div>
		</div>
		<div class="main">
			<div class="main_menu">
				<ul>
					<c:forEach var="row" items="${result.rows}">
						<li><a href="<%=request.getContextPath()%>/img-m-<c:out value="${row.clazz}" />-0.do"><c:out
									value="${row.clazz}" /></a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="show_Mian">
				<div class="show_Menu">
					<ul>
						<c:forEach var="rows" items="${itemMenu.rows}">
							<a href="<%=request.getContextPath()%>/img-th-<c:out value="${rows.theme}"/>-0.do" data_con="${rows.clazz}"><c:out
									value="${rows.theme}" /></a>
						</c:forEach>
					</ul>
				</div>
				<div class="pic_Cntain">
					<c:forEach var="item" items="${pic}">
						<div class="imgbox">
							<div class="img_show">
								<div class="hid icon">
									<a href="#" class="btn_collect" data_id="${item.id}">收藏</a> <a
										href="#" class="btn_share" data_id="${item.id}">赞</a>
								</div>
								<a href="${item.showurl}"><img src="${item.url}" /></a>
								<p class="hid box_bottom">${item.title}</p>
							</div>
							<h3>${item.title}</h3>
							<div class="img_info">
								<a href="${item.authorurl }">${item.author}</a> <span>上传于${item.updateday}</span>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="bom_menu">
				<c:forEach var="item" items="${list}">
					<c:forEach var="entry" items="${item}">
						<a ${entry.value }>${entry.key }</a>
					</c:forEach>
				</c:forEach>
			</div>
		</div>

	</div>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resource/js/pageEvent.js"></script>

</body>
</html>