<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RESGISTER</title>
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/resource/css/registerPage.css" />
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

		<div class="registerMain">
			<form action="<%=request.getContextPath() %>/getRegister.do" method="post" id="logo">
				<table>
					<tr>
						<td>昵称：</td>
						<td class="con"><input type="text" name="nkname" /></td>
					</tr>
					<tr>
						<td>真实姓名：</td>
						<td class="con"><input type="text" name="name" /></td>
					</tr>
					<tr>
						<td>密码：</td>
						<td class="con"><input type="password" name="psw" id="psw"/></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td class="con"><input type="password" name="repsw" /></td>
					</tr>
					<tr>
						<td>邮箱：</td>
						<td class="con"><input type="email" name="email" /></td>
					</tr>
					<tr>
						<td>性别：</td>
						<td class="con">男：<input type="radio" name="sex" value="男" />女：<input
							type="radio" name="sex" value="女" /></td>
					</tr>
					<tr>
						<td>出身年月：</td>
						<td class="con"><input type="date" name="brithday" /></td>
					</tr>
					<tr>
						<td>入职日期：</td>
						<td class="con"><input type="date" name="hiredate" /></td>
					</tr>
					<tr>
						<td>部门：</td>
						<td class="con">
							<select name="department" >
								<option value="product" >product</option>
								<option value="office" >office</option>
								<option value="public" >public</option>
								<option value="HR" >HR</option>
								<option value="CREATE" >create</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>工资：</td>
						<td class="con"><input type="text" name="salary" /></td>
					</tr>
					<tr>
						<td style="text-align: end;"><input type="submit" value="提交" /></td>
						<td style="text-align: center;"><input type="reset"
							value="重置" /></td>
					</tr>
				</table>
			</form>

		</div>

	</div>

	<script type="text/javascript">
			$("#logo").validate({
				rules: {
					nkname:{
						required: true,
						minlength: 5
					},
					name: {
						required: true,
						minlength: 2
					},
					psw: {
						required: true,
						minlength: 5
					},
					repsw: {
						required: true,
						minlength: 5,
						equalTo: "#psw"
					},
					email: {
						required: true,
						email: true
					},
					hiredate: "required",
					salary:{
						required: true,
						number:true
					}
				},
				messages: {
					nkname: {
						required: "请输入用户名",
						minlength: "用户名必需由五个字母组成"
					},
					name: {
						required: "请输入用户名",
						minlength: "用户名必需由2个字母组成"
					},
					psw: {
						required: "请输入密码",
						minlength: "密码长度不能小于 5 个字母"
					},
					psw: {
						required: "请输入密码",
						minlength: "密码长度不能小于 5 个字母",
						equalTo: "两次密码输入不一致"
					},
					email: "请输入一个正确的邮箱",
					salary:  {
						required: "请输入您的工资",
					}
				}

			});
			function valiteName() {
				var i=0;
				$("input[name='nkname']").blur(function() {
					var self=$(this);
					i++;
					if(i>=1){
						var val = $(this).attr("value");
						console.log(val)
						jQuery.ajax({
							type: "POST",
							url: "valiteName.do",
							async: false, //ajax异步
							data: {
								nkname: val
							},
							success: function(msg) {
								console.log(msg)
								if(msg.rim()=="same"){
									self.attr("value","")
								}
							},
							error: function(){
								if(val!=""){
									console.log("网络不好！");
//									self.attr("value","")
								}
							}
						});
					}else if(i>3){
						i=0;
					}
					
				})
			}
			valiteName();
		</script>

</body>

</html>