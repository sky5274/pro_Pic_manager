$(window).load(function(){
	$("#getInfoAll").mousedown(function(){
		getPicInfoByClass()
	})
	$("#getInfodetail").mousedown(function(){
		getInfoByTheme();
	})
	$("#getInfoTab").mousedown(function(){
		getInfoForTab();
	})
	$("#setPicInfo").mousedown(function(){
//		$("#setPicInfoPanel").find(".btn-div").css("margin-left",($("#setPicInfoPanel").width()-$(".btn-div").width())/2)
		setPicInfo();
	})
})


/**
 * 显示图片以clazz分类的信息*/
function getPicInfoByClass(){
	$("#chartpicall").show();
	$("#chartpicall").siblings("div").css("display","none")
	var mychart = echarts.init(document.getElementById("chartpicall"));
	var option = {
			title : {
				text: '数据库中图片信息',
				subtext: '纯属虚构'
			},
			tooltip : {
				trigger: 'axis'
			},
			legend: {
				data:['clazz']
			},
			toolbox: {
				show : true,
				feature : {
					mark : {show: true},
					dataView : {show: true, readOnly: false},
					magicType : {show: true, type: ['line', 'bar']},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			xAxis : [
				{
					type : 'category',
					data : []
				}
				],
				yAxis : [
					{
						type : 'value'
					}
					],
					series : [
						{
							name:'clazz',
							type:'bar',
							data:[],
							markPoint : {
								data : [
									{type : 'max', name: '最大值'},
									{type : 'min', name: '最小值'}
									]
							},
							markLine : {
								data : [
									{type : 'average', name: '平均值'}
									]
							}
						},

						]
	};
	$.post("getPicByClass.do",function(res){
		var title=[];
		var con=[];
		$.each(res,function(i,data){
			title[i]=data.clazz;
			con[i]=data.sum;
		})
		option.xAxis[0].data=title;
		option.series[0].data=con
		mychart.setOption(option)
	},"json")
}

/**
 * 显示以clazz分类theme的具体信息*/
function getInfoByTheme(){
	var self=$("#chartpicdetail");
	self.show();
	self.siblings("div").css("display","none")
	var mychart = echarts.init(document.getElementById("chartpicdetail"));
	var option1 = {
			title : {
				text: '数据库图表详细分布',
				subtext: '纯属虚构',
				x:'center'
			},
			tooltip : {
				trigger: 'item',
				formatter: "{a} <br/>{b} : {c} ({d}%)"
			},
			legend: {
				x : 'center',
				y : 'bottom',
				data:[]
			},
			toolbox: {
				show : true,
				feature : {
					mark : {show: true},
					dataView : {show: true, readOnly: false},
					magicType : {
						show: true, 
						type: ['pie', 'funnel']
					},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			series : []
	};
	$.post("getPicByTheme.do",function(res){

//		console.log(res)
//		console.log(res.length)
		var con=[];
		var h=Math.round(res.length/5);
		var heigth=self.height();
		var ht=heigth*0.8/h;
		var width=self.width();
		$.each(res,function(i,data){
			var item;
			var index=i%5;
			var x=(index/5)*width+100;
			var y=((i-index)/5)*ht+120;
			if(i%2==0){
				item= {
						name:data.title.clazz,
						type:'pie',
						radius : [10, 50],
						center : [x, y],
						roseType : 'radius',
						width: '20%',       // for funnel
						hegith:90/h+'%',
						max: 30,            // for funnel
						itemStyle : {
							normal : {
								label : {
									show : false
								},
								labelLine : {
									show : false
								}
							},
							emphasis : {
								label : {
									show : true
								},
								labelLine : {
									show : true
								}
							}
						},
						data:[]
				}
				var d=[];
				$.each(data.value,function(j,it){
					d[j]={value:it.sum,name:it.theme}
				})
				item.data=d;
			}else{
				item= {
						name:data.title.clazz,
						type:'pie',
						radius : [15, 50],
						center : [x, y],
						roseType : 'area',
						x: '50%',               // for funnel
						max: 30,                // for funnel
						sort : 'ascending',     // for funnel
						data:[]
				}
				var d=[];
				$.each(data.value,function(j,it){
					d[j]={value:it.sum,name:it.theme}
				})
				item.data=d;
			}
			con[i]=item;
		})
		option1.series=con
//		console.log(option1)
		mychart.setOption(option1)
	},"json")                 
}

/**
 * 以列表的形式显示信息*/
function getInfoForTab(){
	var self=$("#chartpictab");
	self.show();
	self.siblings("div").hide();

	$.post("getPicByTheme.do",function(res){
		addInfoToSelect(res);
	},"json")
}

/**
 * 设置图片信息
 * */
function setPicInfo(){
	var self=$("#setPicInfoPanel");
	self.show();
	self.siblings("div").css("display","none");


	$.post("getPicByTheme.do",function(res){
		addInfoToSelectInModify(res);
	},"json")
}
/**
 * 添加select的内容与事件
 * */
function addInfoToSelect(res){
	var self=$("#chartpictab");
	self.find("#clazz").html("");
	self.find("#theme").html("");
	$.each(res,function(i,data){
		$("<option value='"+data.title.clazz+"' data_d='"+data.title.sum+"'>"+data.title.clazz+"</option>").appendTo(self.find("#clazz"));
		$.each(data.value,function(j,item){
			$("<option value='"+item.theme+"' data_d='"+item.sum+"' data_c='"+item.clazz+"'>"+item.theme+"</option>").appendTo(self.find("#theme"))
		})
	});
	var mychart = echarts.init(document.getElementById("picinfo"));
	var labelTop = {
			normal : {
				label : {
					show : true,
					position : 'center',
					formatter : '{b}',
					textStyle: {
						baseline : 'bottom'
					}
				},
				labelLine : {
					show : false
				}
			}
	};
	var labelFromatter = {
			normal : {
				label : {
					formatter : function (params){
						return 100 - params.value + '%'
					},
					textStyle: {
						baseline : 'top'
					}
				}
			},
	}
	var labelBottom = {
			normal : {
				color: '#ccc',
				label : {
					show : true,
					position : 'center'
				},
				labelLine : {
					show : false
				}
			},
			emphasis: {
				color: 'rgba(0,0,0,0)'
			}
	};
	var radius = [40, 55];
	var option = {
			legend: {
				orient : 'vertical',
				x : 'left',
				data:[]
			},
			title : {
				subtext: '',
				x: 'center'
			},
			toolbox: {
				show : true,
				feature : {
					dataView : {show: true, readOnly: false},
					magicType : {
						show: true, 
						type: ['pie', 'funnel'],
						option: {
							funnel: {
								width: '20%',
								height: '30%',
								itemStyle : {
									normal : {
										label : {
											formatter : function (params){
												return 'other\n' + params.value + '%\n'
											},
											textStyle: {
												baseline : 'middle'
											}
										}
									},
								} 
							}
						}
					},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			series : [
				{
					type : 'pie',
					center : ['50%', '50%'],
					radius : radius,
					x: '0%', // for funnel
					itemStyle : labelFromatter,
					data : [
						{name:'other', value:46, itemStyle : labelBottom},
						{name:'', value:54,itemStyle : labelTop}
						]
				}
				]
	};

	var clazz=self.find("#clazz");
	var val=clazz.val();
	var theme=clazz.siblings("#theme").eq(0);
	showThemeInfo(theme,val);
	changePie(clazz,theme,option,mychart);
	/*clazz  select的值改变事件*/
	clazz.change(function(){
		val=$(this).val().trim();
		$(this).attr("value",val)
		showThemeInfo(theme,val);
		changePie(clazz,theme,option,mychart);
	})
	/*#theme  select的值改变事件*/
	theme.change(function(){
		changePie(clazz,theme,option,mychart);
	})
	/*button点击事件*/
	var con="img-th-";
	$(".picTabList").css("width",self.width()-250);
	$(self).find("#ensure").mousedown(function(){
		$(".picTabList").html(" ");
		var f=false;
		$.post("getNextContext.do",{data:con+theme.val(),offset:0},function(res){
			if(!f){
				f=true;
				addPicTab(res);
			}
		},"json");
	})
}
/**
 *设置theme  select的显示内容 
 * */
function showThemeInfo(theme,val){
	var f=false;
	$(theme).children("option").each(function(i,data){
		var cl=$(data).attr("data_c").trim();
		if(val==cl){
			$(data).css("display","block");
			if(!f){
				$(theme).attr('value',$(data).attr("value"))
			}
		}else{
			$(data).css("display","none");
		}
	})
}

/**
 * 改变图表的内容
 * */
function changePie(clazz,theme,option,mychart){
	var sum=$(getOption(clazz,$(clazz).val())).attr("data_d");
	var x=$(getOption(theme,$(theme).val())).attr("data_d");
	var lab=$("#selectInfo");
	lab.find("#clazzNum").html(sum);
	lab.find("#themeNum").html(x);
	var prx=Math.round(x*100/sum);
	option.legend.data[0]=theme.val()
	option.title.subtext='the pic of '+theme.val() +' in the ' +clazz.val();
	option.series[0].data[0].value=100-prx;
	option.series[0].data[1].name=theme.val().trim();
	option.series[0].data[1].value=prx;
	mychart.setOption(option)
}

/**
 * 获取select  的子节点option的选中对象
 * */
function getOption(select,val){
	var obj;
	$(select).children("option").each(function(i,data){
		if($(data).attr("value")==val){
			obj=data;
		}
	});
	return obj
}

/**
 * 添加图片table
 * */
function addPicTab(res){
	var self=$(".picTabList");
	self.html(" ");
	self.append($("<div class='ser-title'> 当前位置："+$("#clazz").val()+">"+$("#theme").val()+"</div>"));

	addInfoInToTab(res);
	/*图片经过事件*/
	$(".imgtd").mouseenter(function(){
		var img_c=$("<div id='img_box'></div>");
		img_c.append($(this).children("img").eq(0).clone()).appendTo($("body"));
		img_c.css({
			position:"absolute",
			"top":$(this).offset().top+$(this).height(),
			"left":$(this).offset().left+$(this).width()
		})
	});
	$(".imgtd").mouseout(function(){
		$("#img_box").remove();
	});
	/*添加分页按钮与事件*/
	addPageLead();

}
/**
 * 添加tab信息*/
function addInfoInToTab(res){
	var self=$(".picTabList");
	var tab=$("<table><table>");
	var head=$("<tr>" +
			"<th>id</th>" +
			"<th>title</th>" +
			"<th>author</th>" +
			"<th>theme</th>" +
			"<th>clazz</th>" +
			"<th>updateday</th>" +
			"<th>picture</th>" +
			"<th>作者图片原址</th>" +
			"<th>图片原址</th>" +
	"</tr>");
	tab.append(head)
	$.each(res,function(i,data){
		var row=$("<tr>" +
				"<td>"+data.id+"</td>" +
				"<td>"+data.title+"</td>" +
				"<td>"+data.author+"</td>" +
				"<td>"+data.theme+"</td>" +
				"<td>"+data.class+"</td>" +
				"<td>"+data.updateday+"</td>" +
				"<td class='imgtd'><img src='"+data.url+"' alt='"+data.title+"'></img></td>" +
				"<td><a href='"+data.authorurl+"'>作者原址</a></td> " +
				"<td><a href='"+data.showurl+"'>图片原址</a></td>" +
		"</tr>");
		tab.append(row);		
	})
	self.append(tab)
	self.find("table").eq(1).remove()
}

function ChangeInfoInToTab(res){
	var self=$(".picTabList");
	var tab=self.find("table").eq(0);
	tab.html("")
	var head=$("<tr>" +
			"<th>id</th>" +
			"<th>title</th>" +
			"<th>author</th>" +
			"<th>theme</th>" +
			"<th>clazz</th>" +
			"<th>updateday</th>" +
			"<th>picture</th>" +
			"<th>作者图片原址</th>" +
			"<th>图片原址</th>" +
	"</tr>");
	tab.append(head)
	$.each(res,function(i,data){
		var row=$("<tr>" +
				"<td>"+data.id+"</td>" +
				"<td>"+data.title+"</td>" +
				"<td>"+data.author+"</td>" +
				"<td>"+data.theme+"</td>" +
				"<td>"+data.class+"</td>" +
				"<td>"+data.updateday+"</td>" +
				"<td class='imgtd'><img src='"+data.url+"' alt='"+data.title+"'></img></td>" +
				"<td><a class='newopen' href='#' data_d='"+data.authorurl+"'>作者原址</a></td> " +
				"<td><a class='newopen' href='#' data_d='"+data.showurl+"'>图片原址</a></td>" +
		"</tr>");
		tab.append(row);		
	})
	self.find(".newopen").each(function(i,data){
		$(data).mousedown(function(){
			window.open($(this).attr("data_d"))
		})
	})
	self.find("table").eq(1).remove()
}
/**
 * 添加分页按钮与事件*/
function addPageLead(){
	var self=$(".picTabList");
	var data=$(getOption($(theme),$(theme).val())).attr("data_d")
	var sum=Math.ceil(parseInt(data)/10);
	var pageLead=$("<div class='page-lead'></div>")
	pageLead.appendTo(self);
	var u=$("<ul></ul>");
	var l_pre=$("<li><a class='pre' href='#'>上一页</a></li>")
	l_pre.css("display","none")
	var l_next=$("<li><a class='next' href='#'>下一页</a></li>")
	l_pre.mousedown(function(){
		var index=$(".page-active").find("a").text();
		index=parseInt(index)
		var con=$(".page-active").find("a").attr("data_c");
//		console.log("pre"+index);
		index--;
		if(index==0){
			index=0;
			$(this).css("display","none")
		}else{
			l_next.css("display","block")
		}
		$(".page-active").removeClass("page-active")
		u.find("li").eq(index+1).addClass("page-active");
		ajaxNextContxt(con,index)
	})
	l_next.mousedown(function(){
		var index=$(".page-active").find("a").text();
		index=parseInt(index)
		var con=$(".page-active").find("a").attr("data_c");
//		console.log("next:"+index);
		index++;
		if(index==(sum-1)){
			index=(sum-1);
			$(this).css("display","none")
		}else{
			l_pre.css("display","block")
		}
		$(".page-active").removeClass("page-active")
		u.find("li").eq(index+1).addClass("page-active");
		ajaxNextContxt(con,index)
	})
	u.append(l_pre);
	for(var i=0;i<sum;i++){
		var li=$("<li><a data_c='img-th-"+$("#theme").val()+"' href='#'>"+i+"</a></li>")
		if(i==0){
			li.addClass("page-active")
		}
		u.append(li);
		li.mousedown(function(){  //li鼠标事件
			var offs=$(this).find("a").text();
			if(offs==0){
				l_pre.css("display","none")
			}else if(offs==(sum-1)){
				l_next.css("display","none")
			}else{
				l_pre.css("display","block")
				l_next.css("display","block")
			}
			var con=$(this).find("a").attr("data_c");
			$(".page-active").removeClass("page-active");
			$(this).addClass("page-active")
			ajaxNextContxt(con,offs)
		})
	}
	var u_w=u.width();
	u.append(l_next);
	pageLead.append(u);
//	console.log("ul_width:"+u_w)
	u.css("margin-left",(self.find("table").eq(0).width()-u_w)/3+"px")
}

function ajaxNextContxt(con,offs){
	var f=false;
	$.post("getNextContext.do",{data:con,offset:offs},function(res){
		if(!f){
			f=true;
			ChangeInfoInToTab(res)
		}
	},"json");
}
/**
 * setPicInfoPanel div页面信息添加
 * */
function addInfoToSelectInModify(res){
	var self=$("#setPicInfoPanel");
	picInfoPanelInit(res);
	showPicInfoByID()           // picInfoTab中数据的添加
	var clazz=self.find("#clazz-c");
	var val=clazz.val();
	var theme=self.find("#theme-c").eq(0);
	/*clazz  select的值改变事件*/
	clazz.change(function(){
		val=$(this).val().trim();
		$(this).attr("value",val)
		showThemeInfo(theme,val);
		showPicInfoByID()
	})
	/*#theme  select的值改变事件*/
	theme.change(function(){
		showPicInfoByID()
	})
	modifyEvent();   //修改按钮事件
}

/**
 * setPicInfoPanel  数据初始化
 * */
function picInfoPanelInit(res){
	var self=$("#setPicInfoPanel");
	self.find("#clazz-c").html("");
	self.find("#theme-c").html("");
	$.each(res,function(i,data){
		$("<option value='"+data.title.clazz+"' data_d='"+data.title.sum+"'>"+data.title.clazz+"</option>").appendTo(self.find("#clazz-c"));
		$.each(data.value,function(j,item){
			$("<option value='"+item.theme+"' data_d='"+item.sum+"' data_c='"+item.clazz+"'>"+item.theme+"</option>").appendTo(self.find("#theme-c"))
		})
	});
	var clazz=self.find("#clazz-c");
	var val=clazz.val();
	var theme=self.find("#theme-c").eq(0);
	showThemeInfo(theme,val);   //隐藏theme中不匹配的内容
	
}
/**
 * setPicInfoPanel 修改后 数据初始化根据theme和clazz
 * */
function picInfoPanelInit1(res,data,oth){
	picInfoPanelInit(res);
	var self=$("#setPicInfoPanel");
	self.find("#"+data.name+"-c").attr("value",data.val);
	self.find("#"+oth.name+"-c").attr("value",oth.val);
	showPicInfoByID()           // picInfoTab中数据的添加
}
/**
 * setPicInfoPanel 修改后 数据初始化根据修改的图片信息
 * */
function picInfoPanelInit2(res,data){
	picInfoPanelInit(res);
	var self=$("#setPicInfoPanel");
	self.find("#clazz-c").attr("value",data.clazz);
	self.find("#theme-c").attr("value",data.theme);
	showPicInfoByID()           // picInfoTab中数据的添加
} 
/**
 * 修改按钮事件
 * */
function modifyEvent(){
	var self=$("#setPicInfoPanel");
	self.find(".modify").mousedown(function(){
		var tar=$(this).siblings("input").eq(0);
		var other=$(this).parent().siblings(".modifyTab").find("input");
		var o={}
		o.val=other.attr("value");
		o.name=other.attr("name")
		var data={};
		data.name=tar.attr("name");
		data.val=tar.attr("value");
		data.tar=$(this).siblings("select").eq(0).val()
		//console.log(data.name+":"+data.val+"----"+data.tar)
		if(confirm("\t是否要将   "+data.name+"   的值:\n由      \'"+data.tar+"\'  修改成    \'"+data.val+"\'")){
			$.ajax({
				type : "POST",
				url : "updataThOrCl.do",
				async : false, //ajax异步
				data: data,
				success : function(res) {
					res=res.trim();
				//	console.log(res+"----"+(res=="success"))
					if(res=="success"){
						$.post("getPicByTheme.do",function(res){
							picInfoPanelInit1(res,data,o) //setPicInfoPanel  数据初始化
						},"json")
						alert(data.name+"修改成功")
					}else{
						alert(data.name+"修改失败")
					}
				},
				error:function(msg){
					console.log("message:"+msg)
					alert("update is erro")
				}
			});
		}
		
	})
	self.find("#modify3").mousedown(function(){
		var contain=$(this).parent();
		var data="{";
		contain.children().each(function(i,d){
			var item=$(d).children().eq(0)
			var name=item.attr("name");
			
			if(name!=null){
				var val=item.val()
				data+="\""+name+"\":\""+val+"\","
			}
		})
		data=data.substr(0,data.length-1)
		data+="}";
		if(confirm("picture 将要修改成:\n"+data)){
			data=JSON.parse(data)
			$.ajax({
				type : "POST",
				url : "updataPicture.do",
				async : false, //ajax异步
				data: data,
				success : function(res) {
					res=res.trim();
					//console.log(res+"----"+(res=="success"))
					if(res=="success"){
						$.post("getPicByTheme.do",function(res){
							picInfoPanelInit2(res,data);  //setPicInfoPanel  数据初始化根据修改的图片信息
						},"json")
						alert(data.name+"修改成功")
					}else{
						alert(data.name+"修改失败")
					}
				},
				error:function(msg){
					console.log("message:"+msg)
					alert("update is erro")
				}
			});
		}
	})
	self.find("#reset").mousedown(function(){
		 addPicInfoDetial()
	})
}
/**
 * setPicInfoPanel 页面下显示picture的具体信息
 * */
function showPicInfoByID(){
	var self=$("#setPicInfoPanel");
	var theme=self.find("#theme-c").eq(0);
	var clazz=self.find("#clazz-c");
	clazz.siblings("input").eq(0).attr("value",clazz.val())
	theme.siblings("input").eq(0).attr("value",theme.val())
	var f=false;
	$.post("queryPicByTheme.do",{theme:theme.val()},function(res){
		if(!f){
			f=true;
			addPicInfo(res);
		}
	},"json");
}
/**
 * setPicInfoPanel picInfoTab 的id中添加信息
 * */
function addPicInfo(res){
	var self=$("#setPicInfoPanel").find(".picInfoTab").eq(0);
	self.find("#id-c").html("")
	$.each(res,function(i,data){
		$("<option value='"+data.id+"'  data=\'{\"author\":\""+data.author+"\",\"title\":\""+data.title+"\",\"url\":\""+data.url+"\", \"updateday\":\""+data.updateday+"\", \"authorurl\":\""+data.authorurl+"\", \"showurl\":\""+data.showurl+"\",\"class\":\""+data.class+"\",\"theme\":\""+data.theme+"\"}\' >"+
				data.id+
		"</option>").appendTo(self.find("#id-c"));
	})
	showPicInfoDetial()
}
/**
 * setPicInfoPanel 中显示图片的具体信息
 * */
function showPicInfoDetial(){
	var self=$("#setPicInfoPanel").find(".picInfoTab").eq(0);
	var select=self.find("#id-c");
	self.find("#clazz-cd").eq(0).html($("#setPicInfoPanel").find("#clazz-c").children().clone());
	self.find("#clazz-cd").attr("value",$("#setPicInfoPanel").find("#clazz-c").val())
	self.find("#theme-cd").eq(0).html($("#setPicInfoPanel").find("#theme-c").children().clone());
	self.find("#theme-cd").attr("value",$("#setPicInfoPanel").find("#theme-c").val())
	addPicInfoDetial()
	select.change(function(){
		addPicInfoDetial()
	})
}

/**
 * setPicInfoPanel 中添加图片的具体信息
 * */
function addPicInfoDetial(){
	var self=$("#setPicInfoPanel").find(".picInfoTab").eq(0);
	var select=self.find("#id-c");
	var item=getOption(select,select.val());
	var data=$(item).attr("data");
	var d=JSON.parse(data)   //将字符串转化成对象
	self.find("#title").eq(0).attr("value",d.title)
	self.find("#author").attr("value",d.author)
	self.find("#authorurl").attr("value",d.authorurl)
	self.find("#showurl").attr("value",d.showurl)
	self.find("#url").attr("value",d.url)
	self.find("#updateDay").attr("value",d.updateday)
}


