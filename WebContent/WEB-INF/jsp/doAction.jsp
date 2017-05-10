<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>welcome to the page of action</title>
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/resource/css/logopage.css" />
<script type="text/javascript"
	src="<%=request.getContextPath() %>/resource/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/resource/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/resource/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/resource/js/messages_zh.js"></script>

</head>
<body>

	<div class="contain">
		<a href="<%=request.getContextPath() %>">首页</a>
		<div class="logoMain">
			<div class="message" style="margin-bottom: 20px">${message}</div>
			<form action="doaction.do" method="post" id="logo">
				<table>
					<tr>
						<td class="thRight"><span>user:</span></td>
						<td colspan="2"><input type="text" name="user" /></td>
					</tr>
					<tr>
						<td class="thRight"><span>email:</span></td>
						<td colspan="2"><input type="text" name="email" /></td>
					</tr>
					
					<tr>
						<td class="thRight" style="text-align: right: ;"><input type="submit" value="登录" /></td>
						<td class="tdcenter" colspan="2"><input type="reset"
							value="重置" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		$(".logoMain").css('left',$("body").width()/4)
			$("#logo").validate({
				rules:{
					user:"required",
					email: {
						required: true,
						email: true
					},
				},
				message:{
					user: {
						required: "请输入用户名",
						minlength: "用户名必需由两个字母组成"
					},
					email: "请输入一个正确的邮箱",
				}
				
			})
			
		</script>

</body>
</html>