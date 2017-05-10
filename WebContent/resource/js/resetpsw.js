jQuery(function(){
	/*
	 * 本案例没有panel后退
	 * 
	 * */
	initPageEvent();   //初始化页面事件
});
function getPasswordSecurityLevel(password){
	//密码长度大于4位
	//密码含有字母
	//密码含有字母和数字
	//密码含有特殊字符v
	//密码长度大于12位
	return 0
	+( password.length > 4)
	+( /[a-z]/.test(password) && /[A-Z]/.test(password) )
	+( /\d/.test(password) && /\D/.test(password) )
	+( /[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/.test(password) && /\w/.test(password) )
	+( password.length > 12 );
}
/**
 * 检测密码安全等级
 * */
function checkPswSER() {
	var level = getPasswordSecurityLevel($("#password").val());
	jQuery("#cp").removeClass().addClass("password"+level) .html("密码安全级别："+level);
}

/**
 * 开启page事件
 * */
function initPageEvent(){
	$(".reset").mousedown(function(){   //重置按钮
		$(this).parents("table").find("input").attr("value","")
	})
	checkUserIsOk();
}

/**
 * 检测user是否正确
 * */
function checkUserIsOk(){
	var self=$(".doAction").find(".active");
	/* 触发JS刷新验证码*/
	refreshImg();
	self.find(".ensure").mousedown(function(){
		if(valiteNameAndEamil()){
			doAjaxValite()
		}else{
			/* 触发JS刷新验证码*/
			self.find("#img").attr("src","authImage.do?date="+Math.random())
		}
	})
}

function refreshImg(){
	var self=$(".doAction").find(".active");
	/* 触发JS刷新验证码*/
	self.find("#refreshImg").mousedown(function(){
		self.find("#img").attr("src","authImage.do?date="+Math.random())
	});
}

/**
 * 验证用户名与邮箱是否符合规则
 * */
function valiteNameAndEamil(){
	var f=true;
	var self=$(".doAction").find(".active");

	self.find("input").each(function(i,data){   //输入框不能为空
		if($(data).attr("value")==""||$(data).attr("value")==null){
			$(data).focus();
			$(data).parents("tr").find(".message").html("不能为空！！")
			f=false;
		}else{
			$(data).parents("tr").find(".message").html("")
		}
	});
	var email=self.find("#email").attr("value");
	f=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(email);
	if(!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(email))){
		self.find("#email").focus();
		self.find("#email").parents("tr").find(".message").html("邮箱格式不正确！！")
	}
	return f;
}

/**
 * 服务器验证姓名与邮箱
 * */
function doAjaxValite(){
	var self=$(".doAction").find(".active");
	var n=self.find("#name").attr("value");
	var e=self.find("#email").attr("value");
	var r=self.find("#checkNum").attr("value");
	jQuery.ajax({
		type : "POST",
		url : "doValiteUser.do",
		async : false, //ajax异步
		data: {name:n,email:e,recode:r},
		success : function(msg) {
			if(msg=="success"){
				alert("验证成功！！！")
				transToPanel(1);
				ModifyPsw();
			}else if(msg=="recode_error"){
				self.find("#checkNum").attr("vlaue","");
				self.find("#ResMessage").html("验证码不正确")
			}else if(msg=="con_error"){
				self.find("input").attr("vlaue","");
				self.find("#ResMessage").html("用户名或邮箱不正确")
			}
		},
		error:function(){
			alert("没有连接到拂去其");
			self.find("input").attr("value","")
		}
	});
}

/**
 * 转向修改密码页面
 * */
function transToPanel(index){
	$("。state").find(".stateInfo").eq(index).addClass("active").removeClass("unchecked");
	$(".doAction").find(".active").removeClass("active");
	$(".doAction").find(".panel").eq(index).addClass("active");
}

function ModifyPsw(){
	var f=false;
	var self=$(".doAction").find(".active");
	self.find(".ensure").attr("disabled",true);
	jQuery("#password").bind('keyup', checkPswSER).bind('blur', checkPswSER);
	self.find("#password").focus(function(){
		$(this).blur(function(){
			var pl=$(this).siblings("#cp").attr("class");
			var level=parseInt(pl.substr(pl.length-1,pl.length));
			console.log(level);
			if(level>0){
				self.find("#repassword").focus(function(){
					if($(this).attr("value")!=""){
						$(this).parents("tr").find(".message").html("")
					}
					$(this).blur(function(){
						if(self.find("#password").attr("value")!=$(this).attr("value")){
							$(this).focus();
							$(this).attr("value","");
							$(this).parents("tr").find(".message").html("要与新密码一致")
						}
					})
					$(this).change(function(){
						if(self.find("#password").attr("value")==$(this).attr("value")){
							f=true;
							self.find(".ensure").attr("disabled",!f);
						}
					})
				})
			}else{
				$(this).parents("tr").find(".message").html("密码长度要大于4")
			}
		})
	})
	self.find(".ensure").mousedown(function(){
		submitPSW();
	});
	self.find("#repassword").focus(function(){
		if(self.find("#password").attr("value")==""){
			self.find("#password").focus();
			self.find("#password").parents("tr").find(".message").html("先设置新密码")
		}
	})
}

/**
 * 验证密码
 * */
function submitPSW(){
	var self=$(".doAction").find(".active");
	var psw=self.find("#password").attr("value");
	jQuery.ajax({
		type : "POST",
		url : "doresPsw.do",
		async : false, //ajax异步
		data: {passwd:psw},
		success : function(msg) {
			
			transToPanel(2);
			transToResult(msg);
		},
		error:function(){
			alert("没有连接到拂去其");
			self.find("input").attr("value","")
		}
	});
}

function transToResult(msg){
	console.log(msg)
	var self=$(".doAction").find(".active");
	if(msg=="sucess"){
		self.find("#message").html("您的密码修改成功，请重新登录")
	}else{
		self.find("#message").html("您的密码修改失败，请重新修改")
	}
	showinfo();
}
function showinfo() {
	var t = 5;
	if (t > 0) {
		var p1 = document
				.getElementById('show');
		p1.innerText = t + "秒后将转到首页"
		t--;
		window.setTimeout("showinfo()", 1000);
	} else {
		window.location.href = "index.jsp"
	}
}