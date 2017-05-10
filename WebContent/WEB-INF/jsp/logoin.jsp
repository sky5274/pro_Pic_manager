<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logoin</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resource/css/logopage.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/messages_zh.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery.cookie.js"></script>

</head>
<body>
	<div class="contain">
		<a href="<%=request.getContextPath()%>">首页</a>
		<div class="logoMain">
			<form action="logoin.do" method="post" id="logo">
				<table>
					<tr>
						<td class="thRight"><span>user:</span></td>
						<td colspan="2"><input type="text" name="uname" /></td>
					</tr>
					<tr>
						<td class="thRight"><span>password:</span></td>
						<td colspan="2"><input type="password" name="psw" /></td>
					</tr>
					<tr>
						<td></td>
						<td class="font1" colspan="2">记住密码：<input type="checkbox"
							name="remUser" /> 自动登录：<input type="checkbox" name="autoLogo" /></td>
					</tr>
					<tr>
						<td></td>
						<td>
						管理员：<input type="radio" name="logoType" value="manaher" />
							<a href="resetPsw.do">修改密码</a>
						</td>
					</tr>
					<tr>
						<td class="thRight"><input type="submit" value="登录" /></td>
						<td class="tdcenter" colspan="2"><input type="reset"
							value="重置" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		$(".logoMain").css('left', $("body").width() / 4);
		$("#logo").validate({
			rules : {
				uname : "required",
				psw : "required"
			},
			message : {
				uname : {
					required : "请输入用户名",
					minlength : "用户名必需由两个字母组成"
				},
				psw : {
					required : "请输入密码",
					minlength : "密码长度不能小于 5 个字母"
				},
			}
		});
		if ($.cookie("remUser") == "on") {
			$("input[name='remUser']").attr("checked", true);
			$("input[name='uname']").attr("value",$.cookie("name"));
			$("input[name='psw']").attr("value",$.cookie("encode"));
		}
		if($.cookie("autoLogo") == "on"){
			$("input[name='autoLogo']").attr("checked", true);
			$("input[name='uname']").attr("value",$.cookie("name"));
			$("input[name='psw']").attr("value",$.cookie("encode"));
		}
		if($("input[name='autoLogo']").attr("checked")){  //自动提交表单
			window.setTimeout(function(){
				$("#logo").submit();
			},1000)
		}
	</script>

</body>
</html>