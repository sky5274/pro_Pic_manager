$(window).load(function(){
	waterfall();
	var dataInt=
	$(window).scroll(function(){	//通过滚动条加载图片
		if(CHECKSCROLLsilder()){
			$.each(dataInt.data, function(key,value) {
				var box=$('<div></div>').addClass('box').appendTo($('.maindemo'));
				var pic=$('<div></div>').addClass('pic').appendTo(box);
				$('<img></img>').attr('src',value.src).appendTo(pic);
			});
			waterfall();   //重新使用瀑布流
		}
	})
});
//瀑布流的实现  
function waterfall(){
	var $box=$('.pic_Cntain>div');   //获得。maindemo下的一级div
	var w=$box.eq(0).outerWidth();
	var cols=Math.floor($(window).width()/w);
	$('.pic_Cntain').width(cols*w).css('margin','0 auto');//设置。maindemo的宽度与居中
	var c_heights=[];  //存放每列的高度
	$box.each(function(index,value){
		var h=$box.eq(index).height()+20;    //控件本身高度加margin
		if(index<cols){
			c_heights.push(h);
		}else{
			var minH=Math.min.apply(null,c_heights);	//获得数组的
			var minINDEX=$.inArray(minH,c_heights);  //获得最小值的索引
			//value是dom控件需要转换
//			console.log(value)
			$(value).css({			//通过css来控制布局
				'position':'absolute',
				'top':minH+'px',
				'left':minINDEX*w+'px'
			});
			c_heights[minINDEX]+=$box.eq(index).outerHeight();			//加载高度
		}
	});
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
var i=0;
function getNextContext(){
	
}
