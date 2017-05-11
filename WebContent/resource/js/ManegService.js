$(window).load(function(){
	$("#OnlineUserCheck").mousedown(function(){
		showOnlineUser();
	})
	$("#serviceReq").mousedown(function(){
		showServerInfo();
	})
	$("#manageAction").mousedown(function(){
		showManagePanel();
	})
	$("#AddUserAction").mousedown(function(){
		showAddUserPanel();
	})
	viliteRegist();
})


/**
 * 显示服务器在线信息
 */
function showOnlineUser(){
	var self=$(".context").find("div#OnlineUserInfo");
	self.show();
	self.siblings("div").css("display","none");

	var option = {
			title : {
				text: '服务器信息',
				subtext: '部分信息查看',
				x:'center'
			},
			tooltip : {
				trigger: 'item',
				formatter: "{a} <br/>{b} : {c} ({d}%)"
			},
			legend: {
				orient : 'vertical',
				x : 'left',
				data:[]
			},
			calculable : true,
			series : [
				{
					name:'网站信息',
					type:'pie',
					radius : '55%',
					center: ['50%', 225],
					data:[]
				}
				]
	};

	var option2 = {
			title : {
				text: '服务器信息柱状图',
				subtext: '部分信息查看',
				x:'center'
			},
			tooltip : {
				trigger: 'axis',
				axisPointer : {
					type: 'shadow'
				}
			},
			legend: {
				data:[]
			},
			toolbox: {
				show : true,
				x: 'right',
				feature : {
					mark : {show: true},
					magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			xAxis : [
				{
					type : 'value'
				}
				],
				yAxis : [
					{
						type : 'category',
						data:[]
					}
					],
					series : [
						{
							name:'服务器数据',
							type:'bar',
							data:[],
						}
						]

	};
	var myChart= echarts.init(document.getElementById('ServiceInfo'));
	var myChart2 = echarts.init(document.getElementById('OnlineInfo'));

	var timer=window.setInterval(function(){
		if(self.css("display")=="none"){
			clearInterval(timer)

		}else{
			$.post("getOnlineInfo.do",function(res){
				updateOnlineInfo(res,option,option2,myChart,myChart2);
			},"json")
		}

	},1000)
}
/**
 * 更新服务器中目标信息
 */
function updateOnlineInfo(res,option,option2,myChart,myChart2){


	$.each(res,function(i,data){
		option.legend.data[i]=data.title;
		option2.legend.data[i]=data.title;
		option.series[0].data[i]={value:data.value,name:data.title};
		option2.yAxis[0].data[i]=data.title;
		option2.series[0].data[i]=data.value;
	})

	myChart2.setOption(option2);
	myChart.setOption(option);
}
/**
 * 查看服务器访问信息
 * */
function showServerInfo(){
	var self=$(".context").find("#serverReqInfo");
	self.show();
	self.siblings("div").css("display","none");
//	var timer=window.setInterval(function(){
//	if(self.css("display")=="none"){
//	clearInterval(timer)
//	}else{
//	$.post("gerServerInfo.do",function(res){
//	updateOnlineServerInfo(res);
//	},"json")
//	}
//	},1000)
	var websocket;
	var url={};
	url.addr="192.168.1.232:8080/picShow";
	url.type="socket";
	url.tar="webSocketPushMessage";

	connectWebSocket(websocket,url,"get_server_info");

	$(".menu_item").find("a").each(function(i,data){
		if($(data).attr("id")!="serviceReq"){
			$(data).mouseup(function(){
				if(websocket!=null){
					websocket.close();
					websocket=null;
				}
			})
		}

	})
}

/**
 * 更新服务器信息
 * */
function updateOnlineServerInfo(res){
	var self=$(".context").find("#serverReqInfo");
	var f="serverInfo>";
	res=res.substring(res.indexOf(f)+f.length)
	res=JSON.parse(res)
	var tab=self.find("table");
	tab.html("")
	$("<tr><th>id</th><th>sessionID</th><th>state</th><th>host</th><th>level</th><th>user</th></tr>").appendTo(tab);
	$.each(res,function(i,data){
		var d=data.obj;
		$("<tr><th>"+d.id+"</th><th>"+d.sessionId+"</th><th>"+d.state+"</th><th>"+d.host+"</th><th>"+d.level+"</th><th>"+d.user+"</th></tr>").appendTo(tab);
	})
}

function connectWebSocket(websocket,url,req){
	//考虑各种浏览器兼容问题
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://"+url.addr+"/"+url.type+"/"+url.tar);
	} else if ('MozWebSocket' in window) {
		websocket = new MozWebSocket("ws://"+url.addr+"/"+url.type+"/"+url.tar);
	} else {
		url.type="sockjs"
			websocket = new SockJS("http://"+url.addr+"/"+url.type+"/"+url.tar);
	}
	//和服务端连接成功后的操作 send操作必须要有  服务端才会可能响应          
	websocket.onopen = function (evnt) {
		console.log("websocket is opened")
		var data = {request:req+new Date()};
		websocket.send(JSON.stringify(data));//尝试给服务端发送json参数，这也是项目中常见的的
	};
	//接收到服务端返回的数据后回调
	websocket.onmessage = function (evnt) {
		console.log("message is resived")
		var str=evnt.data;
		if(str!="error_message"){
			if(str.startWith("serverInfo>")){
				updateOnlineServerInfo(evnt.data)
			}
		}
	};
	//连接失败回调
	websocket.onerror = function (evnt) {
		alert("网路问题，链接失败")
		$(".context").find("#serverReqInfo").css("display","none")
	};
	//服务端关闭引发
	websocket.onclose = function (evnt) {
		console.log("链接关闭")
		$(".context").find("#serverReqInfo").css("display","none")
	}

	String.prototype.startWith=function(str){  //String方法的拓展
		if(str==null||str==""||this.length==0||str.length>this.length)  
			return false;  
		if(this.substr(0,str.length)==str)  
			return true;  
		else  
			return false;  
		return true;  
	}
}
/**
 * 显示管理游客面板
 * */
function showManagePanel(){
	var self=$(".context").find("#managerFrame");
	self.show();
	self.siblings("div").css("display","none");
	$.post("getUserInfo.do",{type:0,con:""},function(res){
		showUserTab(res);
	},"json");
	self.find("#search").mousedown(function(){
		var t=$(this).siblings("select").attr("value");
		var c=$(this).siblings("input").attr("value");
		//console.log(t+"--------"+c)
		$.post("getUserInfo.do",{type:t,con:c},function(res){
			showUserTab(res);
		},"json");
	})	
}
/**
 * 以列表的形式显示user
 * */
function showUserTab(res){
	var self=$(".context").find("#managerFrame");
	var tab=self.find("table");
	tab.html("");
	$("<tr><th>id</th><th>username</th><th>name</th><th>level</th><th>sex</th></tr>").appendTo(tab)
	$.each(res,function(i,data){
		var u=data.u;
		var info=data.l==""?{}:data.l;
		var tr=$("<tr data_u="+JSON.stringify(u)+" data_l="+JSON.stringify(info)+"><td>"+u.id+"</td><td>"+u.username+"</td><td>"+info.username+"</td><td class='mod'>"+u.level+"</td><td>"+info.sex+"</td></tr>")
		tr.appendTo(tab);
		$(tr).mousedown(function(e){
			$("#tabMenu").remove();
			if(e.which==3){
				showChooseMenu($(tr),e)
			}
		})
	})

	tab.hover(
			function(){
				$(document).bind("contextmenu",function(e){     //禁止鼠标右键显示系统菜单 
					return false;   
				});
			},
			function(){
				$(document).bind("contextmenu",function(e){     //开启鼠标右键显示系统菜单 
					return true;   
				});
			})
}

/**
 * 在鼠标位置显示自定义菜单
 * */
function showChooseMenu(tr,e){
	$(".contain").dblclick(function(){
		$("#tabMenu").remove();
	})
	$("#tabMenu").remove();
	var menu=$("<div id='tabMenu'></div>");
	menu.appendTo($("body"));
	menu.css({
		position:"absolute",
		"top":e.pageY,
		"left":e.pageX
	})
	var u=$("<ul></ul>");
	menu.append(u);
	var it1=$("<li><a href='#'>修改权限</a></li>")
	var it2=$("<li><a href='#'>修改全局信息</a></li>")
	u.append(it1);
	u.append(it2);

	u.children("li").hover(function(){
		$(this).addClass("m_choose")
	},function(){
		$(this).removeClass("m_choose")
	})
	var index=0;
	$(document).keydown(function(e){
		if(e.keyCode==38){  //up
			index++; 
			index=index>=u.children().length?0:index
		}else if(e.keyCode==40){   //down
			index--;
			index=index<0?u.children().length-1:index
		}else if(e.keyCode==13){
			showMenuItem(u.children().eq(index),index);
		}
		u.children(".m_choose").removeClass("m_choose")
		u.children("li").eq(index).addClass("m_choose");
	})
	$()
	it1.mousedown(function(e){
		showMenuItemModifLevel(tr);
	})
	it2.mousedown(function(e){
		e.preventDefault()
		showMenuItemModifyALL(tr)
	})
}

/**
 * 键盘事件显示自定义菜单
 * */
function showMenuItem(li,index){
	if(index==0){
		showMenuItemModifLevel(li);
	}else if(index==1){
		showMenuItemModifyALL(li)
	}
}
/**
 * 自定义菜单，修改用户等级
 * */
function showMenuItemModifLevel(tr){
	
	var td=$(tr).find(".mod");
	var level=td.text();
	td.html("");
	var con=$("<input type='number' value='"+level+"'/>");
	con.focus(function(){
		$("body").bind("keydown",function(e){
			if(e.keyCode==13){
				changLevel(con,level,tr)
			}
		});
		$("body").bind("mousedown",function(e){
			changLevel(con,level,tr)
		});
	});
	con.blur(function(){
		$("body").unbind("mousedown")
		$("body").unbind("keydown")
	})
	td.append(con)
	$("#tabMenu").remove();
}
/**
 * 提交修改用户等级
 * */
function changLevel(con,level,tr){
	var maxLevel=8;
	var td=$(tr).find(".mod");
	if(con.attr("value")!=""&&parseInt(con.attr("value"))< maxLevel&&parseInt(con.attr("value"))!=parseInt(level)){
		$.post("updateLevel.do",{id:tr.children("td").eq(0).text(),name:tr.children("td").eq(1).text(),level:parseInt(con.attr("value"))},function(res){
			if(res=="success"){
				td.html(parseInt(con.attr("value")));
			}else{
				td.html(level);
				alert("修改失败")
			}
		})
	}else{
		td.html(level);
	}
}
/**
 * 自定义菜单，修改用户全局信息
 * */
function showMenuItemModifyALL(tr){
	var u=tr.attr("data_u")
//	console.log(u)
	u=JSON.parse(u)
	var l=JSON.parse(tr.attr("data_l"))
	$("#tabMenu").remove();
	closeTheMenu();
	var box=$("<div id='box'></div>");
	box.css({
		"width":$("body").width(),
		"heigth":$("body").height(),
		position:"absolute",
		"border":"1px solid",
		"z-index":3,
		"top":0,
		"left":0
	})

	box.appendTo($("body"));
	var box_c=$("<div></div>");
	var box_x=$("<div></div>");
	box.append(box_c);
	box_c.append(box_x);
	box_c.css({
		"width":"100%",
		"height":"100%",
		position:"relative"
	})
	var tab=$("<table id='modTab'>" +
			"<tr ><td  height='30px'>" +
			"<table>" +
			"<tr><th>userID: </th><td><label name='userid' value='"+u.id+"'>"+u.id+"</label></td><th>userNkname:</th><td><input type='text' name='username' value='"+u.username+"'/></td><th>userLevel:</th><td><input type='text' name='userlevel' value='"+u.level+"'/></td></tr>" +
			"</table>" +
			"</td></tr>" +
			"<tr ><td height='150px'>" +
			"<table>" +
			"<tr><th>姓名：</th><td><input type='text' name='name' value='"+l.username+"'/></td><th>性别：</th><td><input type='text' name='sex' value='"+l.sex+"'/></td></tr>" +
			"<tr><th>邮箱：</th><td><input type='email' name='email' value='"+l.email+"'/></td><th>部门：</th><td><input type='text' name='department' value='"+l.department+"'/></td></tr>" +
			"<tr><th>生日：</th><td><input type='date' name='brithday' value='"+l.brithday+"'/></td><th>入职日期：</th><td><input type='date' name='hiredate' value='"+l.hiredate+"'/></td></tr>" +
			"<tr><th>薪水：</th><td><input type='number' name='salary' value='"+l.salary+"'/></td><th></th><td><input type='hidden' class='hide' name='infoId' value='"+l.id+"'/></td></tr>" +
			"<tr><td></td><td colspan='2'><button id='tabsubmit' class='btn_1'>提交</button><button id='return' class='btn_1'>取消</button></td><td></td></tr>" +
			"</table>" +
			"</td></tr>" +
	"</table>");
	box_x.css({
		"width":"600px",
		"height":"250px",
		position:"absolute",
		"border":"1px solid",
		"background-color":"blue",
		"top":"100px",
		"left":$("body").width()/2-200
	})
	tab.css({
		"width":"100%",
		"font-size":"120%",
		"height":"60%",
	})
	tab.find("td").css("padding","5px 0px")
	tab.appendTo(box_x);
	$("#tabsubmit").bind("mousedown",function(){
		if(confirm("是否确认提交")){
			var req_d="{";
			var f=true;
			$.each(datas,function(i,data){
				if($(data).attr("value")==""){
					f=false;
					$(data).focus();
				}
				req_d+="\""+$(data).attr("name")+"\":\""+$(data).attr("value")+"\","
			})
			req_d=req_d.substring(0,req_d.length-1)+"}";
			req_d=JSON.parse(req_d);
			if(f){
				commitModify(req_d)
				$("#box").remove();
				openMenu();
			}
		}

	})
	$("#return").mousedown(function(){
		$("#box").remove();
		openMenu()
	})
}


function commitModify(datas){
	$.post("update/UserandUserInfo.do",datas,function(res){
		if(res=="success"){
			alert("修改成功");
		}else{
			alert("未修改完成");
		}
		$.post("getUserInfo.do",{type:0,con:""},function(res){  //更新列表
			showUserTab(res);
		},"json")
	})
}

function closeTheMenu(){
	$(".contain").css({
		"opacity":0.5,
		"z-index":-1
	});
	$(".contain").find("a").mousedown(function(){
		return false;
	})
}

function openMenu(){
	$(".contain").css({
		"opacity":1,
		"z-index":0
	});
	$(".contain").find("a").mousedown(function(){
		return true;
	})
}

/**
 * 添加用户界面显示
 * */
function showAddUserPanel(){
	var self=$(".context").find("#managerAddUser");
	self.show();
	self.siblings("div").css("display","none");
	
}

function viliteRegist(){
	var self=$(".context").find("#managerAddUser");
	self.find("#logo").validate({
		submitHandler : function(form) {  //验证通过后的执行方法
			//当前的form通过ajax方式提交（用到jQuery.Form文件）
			$(form).ajaxSubmit({
//				dataType:"json",  //只能接受确定的消息
				success:function( jsondata ){
					console.log(jsondata)
					if( jsondata =="success"){
						alert("success");
						$(form).reset()
					}else{
						alert("fail");
					}
				}
			}); 

		},
		focusInvalid : true,   //验证提示时，鼠标光标指向提示的input
		rules: {
			nkname:{
				required: true,
				minlength: 5,
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
				minlength: "用户名必需由五个字母组成",
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
	valiteName(self);
}
function valiteName(self) {
	var i=0;
	self.find("input[name=nkname]").focus(function(){
		
		$(this).blur(function() {
			var se=$(this);
			i++;
			if(i>=1){
				var val = $(this).attr("value");
				jQuery.ajax({
					type: "POST",
					url: "valiteName.do",
					async: false, //ajax异步
					data: {
						nkname: val
					},
					success: function(msg) {
						if(msg.trim()=="same"){
//							console.log(msg)
							se.eq(0).attr("value","");
							se.siblings("span").html("用户已存在")
						}else{
							se.siblings("span").html("")
						}
					},
					error: function(){
						if(val!=""){
							console.log("网络不好！");
							se.attr("value","")
						}
					}
				});
			}else if(i>3){
				i=0;
			}
		})
	})
}