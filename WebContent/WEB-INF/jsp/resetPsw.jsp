<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>reset the password</title>
<style type="text/css">
.password0 {
	background: #FF0000;
}

.password1 {
	background: #FF9900;
}

.password2 {
	background: #FFFF00;
}

.password3 {
	background: #CCFF00;
}

.password4 {
	background: #00FF00;
}

.password5 {
	background: #0000FF;
}
</style>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resource/css/logopage.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery-1.8.3.js"></script>
</head>
<body>
	<div class="contain">
		<a href="<%=request.getContextPath()%>">首页</a>
		<div class="actionState">
			<div class="state">
				<ul>
					<li><label class="active stateInfo">填写信息验证</label></li>
					<li><label>>></label></li>
					<li><label class="stateInfo unchecked ">重置密码</label></li>
					<li><label>>></label></li>
					<li><label class="stateInfo unchecked ">密码修改成功</label></li>
				</ul>
			</div>
			<div class="doAction">
				<ul>
					<li>
						<div class="panel active">
							<table>
								<tr>
									<td>用户名：</td>
									<td><input type="text" id="name" /></td>
									<td><span class="message"></span></td>
								</tr>
								<tr>
									<td>邮箱：</td>
									<td><input type="text" id="email" /></td>
									<td><span class="message"></span></td>
								</tr>
								<tr>
									<td>验证码：</td>
									<td><input type="text" id="checkNum" /></td>
									<td><img id="img" src="authImage.do" /> <a href='#'
										id="refreshImg">看不清？</a> <span class="message"></span></td>
								</tr>
								<tr>
									<td><span id="ResMessage"></span></td>
								</tr>
								<tr>
									<td></td>
									<td><button class="ensure">提交</button>
										<button class="reset">重置</button></td>
								</tr>
							</table>
						</div>
					</li>
					<li>
						<div class="panel">
							<table>
								<tr>
									<td>新密码：</td>
									<td><input type="password" id="password" />
									<p id="cp" class="password0"></td>
									<td><span class="message"></span></td>
								</tr>
								<tr>
									<td>确认密码：</td>
									<td><input type="password" id="repassword" /></td>
									<td><span class="message"></span></td>
								</tr>
								<tr>
									<td></td>
									<td><button class="ensure">提交</button>
										<button class="reset">重置</button></td>
								</tr>
							</table>
						</div>
					</li>
					<li>
						<div class="panel">

							<span id="message"> </span>
							<div class="info">
								<p id="show"></p>
							</div>
						</div>

					</li>
				</ul>
			</div>

		</div>
	</div>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resource/js/resetpsw.js"></script>


	<script type="text/javascript">
		
	</script>

</body>
</html>