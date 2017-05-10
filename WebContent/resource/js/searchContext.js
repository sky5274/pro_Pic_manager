;
(function(win, doc, $) {
	var search = function(contain) {
		this.contain = contain;
		this.context = contain.find("input").eq(0);
		this.commit = contain.find("input:submit").eq(0);
		this.temp;
		this.settings = {
				method: "like", //模糊查询 like,精确查询  
				areas: "theme" //要查询的区域，class、theme、title、id、author
		}
	};
	search.prototype = {
			_init: function(pro) {
				var self = this;
				if(pro != undefined) {
					self._initProtory(pro);
				}
				self.initEvent();
			},
			_initProtory: function(pro) {
				var self = this;
				$.extend(true, self.settings, pro);
			},
			initEvent: function() {
				var self = this;
				$(self.context).focus(function() {
					$(this).bind("keyup", function(event) {
						var str=$(this).attr("value").trim();
						if(str!=""&&str!=self.temp){
//							window.setTimeout(function(){
							self.searchHint(str);
							self.temp=str;
//							},1)
						}
					})
					$("#promot").show();
				});
				$(self.context).blur(function() {
					self.temp="";
					$(this).unbind("keyup");
					$("body").unbind("keydown");
					window.setTimeout(function(){
//						$("#promot").html("");
						$("#promot").hide();
					},400)
				})
				var promot=$("<div id='promot'></div>");
				promot.css({
					position:"absolute",
					"left":$(self.context).offset().left,
					"top":$(self.context).offset().top+20,
					"width":self.context.width(),
					"display":"none",
					"border":"1px solid lightblue",
					"background-color": "gray",
					"font-size": 15+"px",
					"color":"red",
					"z-index":2,
					"opacity": 0.8
				})
				$("body").append(promot);
			},
			addinfoLayout: function(data) {
				var self=this;
				$("#promot").show();
				$("#promot").html("");
				$.each(data,function(i,d){
					var li=$("<div>"+d.hint+"</div>");
					li.hover(
							function(){
								$(this).css({
									"background-color": "darksalmon",
									"opacity": 1
								})
							},
							function(){
								$(this).css({
									"background-color": "gray",
									"opacity": 0.4
								})
							}
					);
					$("#promot").append(li);
				});
				$("#promot").children().each(function(i,data){
//					console.log(data)
					$(data).click(function(){
						$(self.context).attr("value",$(this).text())
					});
				})
				var i=-1;
				$("body").bind("keydown",function(e){
					if(e.keyCode==38){
						i--;
					}else if(e.keyCode==40){
						i++;
					}
					if(i<0){
						i=$("#promot").children().size()-1;
					}
					if(i>$("#promot").children().size()-1){
						i=0;
					}
					$("#promot").children().css({
						"background-color": "gray",
						"opacity": 0.4
					});
					$("#promot").children().eq(i).css({
						"background-color": "darksalmon",
						"opacity": 1
					});
					if(i>=0&& e.keyCode==13){
						if($("#promot").children("div").size()>0){
							$(self.context).attr("value",$("#promot").children().eq(i).text())
						}
					}
				})
			},
			nopage:function(){
				$("#promot").show();
				$("#promot").html("抱歉您要找的图片没有，请重新输入");
			},
			searchHint: function(value) {   //寻找搜索提示
				var self=this;
				$.ajax({
					type: "POST",
					url: "searchInfo.do",
					dataType: "json",
					async: true, //是否ajax同步
					data: {context:value,method:"like",areas:'title'},
					success: function(data) {
						if(data!=null){
							self.addinfoLayout(data);
						}else{
							self.nopage();
						}
					}
				});
			},

	};

	window['search'] = search;
})(window, document, jQuery)