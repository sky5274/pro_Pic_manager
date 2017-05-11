<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resource/css/adminPage.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/echarts.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="http://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/messages_zh.js"></script>
<title>管理员界面</title>
</head>
<body>
	<div class="contain">
		<div class="top">
			<h4>网站管理</h4>
			<div class="right logo">
				<span id="admin"> </span>
				<button id="back">退出</button>

			</div>
		</div>
		<div class="main">
			<div class="left menu">
				<ul class="menu_item">
					<li>
						<ul>
							<a class="menuHead" href="#">图片管理</a>
							<li><a id="getInfoAll" href="#">全局查看</a></li>
							<li><a id="getInfodetail" href="#">细节查看</a></li>
							<li><a id="getInfoTab" href="#">列表查看</a></li>
							<li><a id="setPicInfo" href="#">设置图片</a></li>
						</ul>
					</li>
					<li>
						<ul>
							<a class="menuHead" href="#">在线用户管理</a>
							<li><a id="OnlineUserCheck" href="#">在线用户信息</a></li>
							<li><a id="serviceReq" href="#">服务器访问查看</a></li>
						</ul>
					</li>
					<li>
						<ul>
							<a class="menuHead" href="#">管理员</a>
							<li><a id="manageAction" href="#">管理员设置</a></li>
							<li><a id='AddUserAction' href="#">添加用户</a></li>
						</ul>
					</li>
				</ul>

			</div>
			<div class=" right context">
				<div id="chartpicall" style="height: 450px"></div>
				<div id="chartpicdetail" style="height: 550px"></div>
				<div id="chartpictab" style="height: 550px">
					<div class="selectTip">
						class:<select id="clazz" name="clazz"></select> theme:<select
							id="theme" name="theme"></select>
						<button id="ensure">查询</button>
					</div>
					<div class="pictabMain">
						<div class="picTabList"></div>

						<div class="selectInfolab">
							<div id="selectInfo">
								class:<label id="clazzNum"></label><br> theme:<label
									id="themeNum"></label>
							</div>
							<div id="picinfo" style="height: 200px;"></div>

						</div>
					</div>
				</div>
				<div id="setPicInfoPanel" style="height: 550px">
					<div class="clazzAndTheme">
						<div class="modifyTab">
							class:<select id="clazz-c" name="clazz-c"></select> changedTo:<input
								type="text" name="clazz" />
							<button class="modify">修改</button>
						</div>
						<div class="modifyTab">
							theme:<select id="theme-c" name="theme-c"></select> changedTo:<input
								type="text" name="theme" />
							<button class="modify">修改</button>
						</div>
						<div class="picInfoTab">
							<table>
								<tr>
									<td colspan="2">
										<div>
											id:<select id="id-c" name="id"></select>
											
										</div>
										<div style="float: right;">
											class:<select id="clazz-cd" name="clazz" ></select>
										</div>
									</td>
									<td colspan="2" >
									<div>
											theme:<select id="theme-cd" name="theme"></select>
										</div>
										<div style="float: right;">
											title:<input type="text" id="title" name="title" />
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2"'>
										<div>
											author:<input type="text" id="author" name="author"  style="width: 90px"/>
										</div>
										<div style="float: right;">
											updateDay:<input type="date" id="updateDay" name="updateDay"  style="width: 120px"/>
										</div>
									</td>
									<td colspan="2">
										<div>
											authorurl:<input type="text" id="authorurl" name="authorurl"
												style="width: 300px" />
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div>
											showurl:<input type="text" id="showurl" name="showurl"
												style="width: 300px" />
										</div>
									</td>
									<td colspan="2">
										<div>
											url:<input type="text" id="url" name="url"
												style="width: 300px" />
										</div>
									</td>
								</tr>
								<tr>
									<td></td>
									<td colspan="2" style="text-align: center;" >
										<div class="btn-div">
											<button id="modify3">修改</button>
											<button id="reset" >重置</button>
										</div>
									</td>
									<td></td>
								</tr>
								<tr></tr>
							</table>
						</div>
					</div>
				</div>
				<div id="OnlineUserInfo" style="height: 550px">
					<div class="left" id="ServiceInfo" style="height: 100%; width: 40%"></div>
					<div id="OnlineInfo" class="right" style="height: 80%; width: 55%"></div>
				</div>
				<div id="serverReqInfo" style="height: 550px">
					<div style="width: 60%; height: 40%">
						<table border="1">
						</table>
					</div>
				</div>
				<div id="managerFrame">
					<div style="text-align: center;">
						<h2>管理用户</h2>
					</div>
					<div class="managerPanel">
						<div class="chooseTip">
							检索:<select name="ser_type">
								<option value="0">all</option>
								<option value="1">visitor</option>
								<option value="2">manager</option>
							</select> 用户名：<input type="text" name="ser_data" />
							<button id="search">查询</button>
						</div>
						<div class="showPanel">
							<table border="1" bordercolor="green" cellspacing="0"></table>
						</div>
					</div>
				</div>
				<div id="managerAddUser">
					<div class="registerMain">
						<form
							action="<%=request.getContextPath()%>/update/ManagerRegist.do"
							method="post" id="logo">
							<table>
								<tr>
									<td>昵称：</td>
									<td class="con"><input type="text" name="nkname" /><span></span></td>
								</tr>
								<tr>
									<td>权限等级：</td>
									<td class="con"><select name="level">
											<option value="1">菜鸟</option>
											<option value="2">路人</option>
											<option value="3">游客</option>
											<option value="4">常驻人</option>
											<option value="5" selected="selected")>官吏</option>
											<option value="5">知县</option>
											<option value="6">巡抚</option>
											<option value="7">列侯</option>
											<option value="8">公爵</option>
											<option value="9">君王</option>
									</select></td>
								</tr>
								<tr>
									<td>真实姓名：</td>
									<td class="con"><input type="text" name="name" /></td>
								</tr>
								<tr>
									<td>密码：</td>
									<td class="con"><input type="password" name="psw" id="psw" /></td>
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
									<td class="con"><select name="department">
											<option value="product">product</option>
											<option value="office">office</option>
											<option value="public">public</option>
											<option value="HR">HR</option>
											<option value="CREATE">create</option>
									</select></td>
								</tr>
								<tr>
									<td>工资：</td>
									<td class="con"><input type="text" name="salary" /></td>
								</tr>
								<tr>
									<td style="text-align: end;"><input type="submit"
										style="margin-right: 5%;" value="提交" /></td>
									<td><input type="reset" style="margin-left: 7%;"
										value="重置" /></td>
								</tr>
							</table>
						</form>

					</div>

				</div>

			</div>
		</div>

		<script type="text/javascript">
			$(window).load(
					function() {
						$(".menu_item").children("li").each(
								function(i, data) {
									$(data).click(
											function() {
												$(this).siblings("li").find(
														"li").css("display",
														"none");
												$(this).find("li").css(
														"display", "block")
											})
								});
						$("#back").mousedown(function() { //退出管理界面
							jQuery.ajax({
								type : "POST",
								url : "setAttribute.do",
								async : false, //ajax异步
								data : {
									request : "rm_session_user"
								},
								success : function(msg) {
									console.log(msg)
									window.location.href = "index.jsp";
								}
							});
						})
						jQuery.ajax({ //获取session属性    username
							type : "POST",
							url : "GetSession.do",
							async : false, //是否ajax异步
							data : {
								data : "username"
							},
							success : function(msg) {
								$("#admin").html(msg)
							}
						})
					})
		</script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resource/js/chartEvent.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resource/js/ManegService.js"></script>

	</div>
</body>
</html>