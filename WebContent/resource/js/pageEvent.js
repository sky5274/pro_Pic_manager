$(window).load(function(){
	var username = getSessionAttr("username")
	initLogoEvent(username);  //登录初始化，检查用户会话属性
	initLayoutItemMenu();   //初始化附表菜单
	loadItemMenu();
	waterfall(); 
	var i=0;
	var context=$(".bom_menu").children("a").eq(0).attr("href");
	var con=context.substring(0,context.length-1);
//	$.each($(".bom_menu").children("a"),function(index,data){
//	if($(data).attr("href")==null){
//	i=index;
//	}
//	})
//	loadLead($(".bom_menu"));
	$(window).scroll(function(){	//通过滚动条加载图片
		if(CHECKSCROLLsilder()){
//			console.log(con+(++i));
			if(i<10){
				getNextContext(con,++i);
			}
		}
	})
	$(window).resize(function(){
		waterfall();
	})
})

//登录初始化，检查用户会话属性
function initLogoEvent(username){
	//console.log(username)
	var action = $(".title_top").find("#useractioon");
	action.html("")
	if (username == null||username=="") {
		var logo = $("<a id='logo' href='logo.do'>登录</a>");
		var regist = $("<a id='regist' href='view/register.jsp'>注册</a>")
		action.append(logo);
		action.append(regist);
	} else {
		var user = $("<a><img src='' alt='" + username + "'/></a>")
		var reback = $("<a href='#'>退出</a>")
		reback.click(function(){
			console.log(1)
			removeSession();   //删除会话中的username  属性
		})
		action.append(user)
		action.append(reback)
	}
}

//重新布局副菜单
function initLayoutItemMenu(){
	var temp=$(".show_Menu").find("a").eq(0).attr("data_con");
	var list=$("<li></li>");
	var con=$("<ul class='item'></ul>");
	con.append("<p>"+temp+"</p>").appendTo(list);
	$(".show_Menu").append(list)
	//移动标签
	$(".show_Menu").find("a").each(function(x, data) {
		if($(data).attr("data_con") == temp) {
			var li=$("<li></li>");
			li.append(data).appendTo(con);
		} else {
			var lists=$("<li></li>");
			temp=$(data).attr("data_con")
			con=$("<ul class='item'></ul>");
			con.append("<p>"+temp+"</p>").appendTo(lists);
			$(".show_Menu").append(lists)
			var li=$("<li></li>");
			li.append(data).appendTo(con);
		}
	})
}

//瀑布流的实现  
function waterfall(){
	var $box=$('.pic_Cntain>div');   //获得。maindemo下的一级div
	var w=$box.eq(0).outerWidth()+10;
//	console.log($(".pic_Cntain").width())
	var cols=Math.floor($(".pic_Cntain").width()/w);
	$('.pic_Cntain').width(cols*w).css('margin','0 auto');//设置。maindemo的宽度与居中
	var c_heights=[];  //存放每列的高度
	$box.each(function(index,value){
//		console.log(value)
		var h=$box.eq(index).height()+10;    //控件本身高度加margin
		if(index<cols){
			c_heights.push(h);
		}else{
			var minH=Math.min.apply(null,c_heights);	//获得数组的最小值
			var minINDEX=$.inArray(minH,c_heights);  //获得最小值的索引
			//value是dom控件需要转换
//			console.log(value)
			$(value).css({			//通过css来控制布局
				'position':'absolute',
				'top':minH+'px',
				'left':minINDEX*w+'px'
			});
			c_heights[minINDEX]+=$box.eq(index).outerHeight()+10;			//加载高度
		}
	});
	var maxH=Math.max.apply(null,c_heights);
	$('.pic_Cntain').height(maxH);   //改变容器高度
}

//检查滑块的位置
function CHECKSCROLLsilder(){
	var $lastbox=$('.pic_Cntain>div').last();
	var lase_dis=$lastbox.offset().top+Math.floor($lastbox.outerHeight()/2);
	var sc_dis=$(document).scrollTop();	//滑块滑动的距离
	var documetH=$(window).height();   //浏览器可视高度
	if(lase_dis<sc_dis+documetH){
		return true;
	}else{
		return false;
	}
}

//加载次一级菜单
function loadItemMenu(){
	var cont=$("<div id='item_menu'></div>")
	cont.css({
		"z-index":2
	})
	var list=$("<ul></ul>");
	$($(".main_menu").find("a"),function(i,data){
		console.log(data)
	})
}

function removeSession(){

	jQuery.ajax({
		type : "POST",
		url : "setAttribute.do",
		async : false, //ajax异步
		data: {request:"rm_session_user"},
		success : function(msg) {
			console.log(msg)
		}
	});
	
	var action = $(".title_top").find("#useractioon");
	action.html("");
	var logo = $("<a id='logo' href='logo.do'>登录</a>");
	var regist = $("<a id='regist' href='view/register.jsp'>注册</a>")
	action.append(logo);
	action.append(regist);
}

//加载导航
function loadLead(btm){
	var context=$(btm).children("div").text();
	var prefix=context.substring(0,context.length-1);
	var index=parseInt(context.substring(context.length-1,context.length));
	$(btm).html("");
	var FRI=$("<a href='"+prefix+0+"'>首页</a>");
	$(btm).append(FRI);
	if(index>0){
		var pre=$("<a href='"+prefix+(index-1)+"'><<</a>");
		$(btm).append(pre);
	}
	if(index<7){
		for(var i=0;i<14;i++){
			var li;
			if(i==index){
				li =$("<a>"+i+"</a>");
			}else{
				li =$("<a href='"+prefix+i+"'>"+i+"</a>");
			}
			$(btm).append(li);
		}
	}else{
		for(var i=index-7;i<index+7;i++){
			var li;
			if(i==index){
				li =$("<a>"+i+"</a>");
			}else{
				li =$("<a href='"+prefix+i+"'>"+i+"</a>");
			}
			$(btm).append(li);
		}
	}
	var next=$("<a href='"+prefix+(index+1)+"'>>></a>");
	$(btm).append(next);
}
var temp=null;
function setLayout(con){
	if(con!=null && temp!=con){
		$.each(con,function(i,data){
//			console.log(data)
			var con=$("<div class='imgbox'>" +
					"<div class='img_show'>" +
					"<div class='hid icon>" +
					"<a href='#' class='btn_collect'  data_id='"+data.id+"'>收藏</a>" +
					"<a href='#' class='btn_share' data_id='"+data.id+"'>赞</a>" +
					"</div>" +
					"<a href='"+data.showurl+"'><img src='"+data.url+"'/></a>" +
					"<p class='hid box_bottom'>"+data.title+"</p>" +
					"</div>" +
					"<h3>"+data.title+"</h3>" +
					"<div class='img_info'>" +
					"<a href='"+data.authorurl+"'>"+data.author+"</a>" +
					"<span>上传于"+data.updateday+"</span>" +
					"</div>" +
			"</div>");
			$(".pic_Cntain").append(con);
		});
		waterfall();
		temp=con;
	}
}

function getNextContext(con,offs){
	console.log(con+":"+offs)
	jQuery.ajax({
		type : "get",
		url : "getNextContext.do",
		dataType: "json",
		data: {data:con,offset:offs},
		async : true, //是否ajax异步
		success : function(msg) {
			setLayout(msg);
		}
	});
}
//获取session属性
function getSessionAttr(value) {
	var temp = null;
	jQuery.ajax({
		type : "POST",
		url : "GetSession.do",
		async : false, //是否ajax异步
		data: {data:value},
		success : function(msg) {
			temp = msg;
		}
	});
	return temp;
}