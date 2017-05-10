<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
		var socket = null;
		$(function() {
			var websocket;
//考虑各种浏览器兼容问题
            if ('WebSocket' in window) {
                websocket = new WebSocket("ws://localhost:8080/picShow/socket/webSocketServer");
            } else if ('MozWebSocket' in window) {
                websocket = new MozWebSocket("ws://localhost:8080/picShow/socket/webSocketServer");
            } else {
                websocket = new SockJS("http://localhost:8080/picShow/sockjs/webSocketServer");
            }
//和服务端连接成功后的操作 send操作必须要有  服务端才会可能响应          
            websocket.onopen = function (evnt) {
            	var data = {appId:'hx', status:'s'};
            	websocket.send(JSON.stringify(data));//尝试给服务端发送json参数，这也是项目中常见的的
            	$("#showMsg").append("连接成功!<br/>");
            };
//接收到服务端返回的数据后回调
            websocket.onmessage = function (evnt) {
                $("#showMsg").append("(<font color='red'>"+evnt.data+"</font>)<br/>");
            };
//连接失败回调
            websocket.onerror = function (evnt) {
            };
//服务端关闭引发
            websocket.onclose = function (evnt) {
            	$("#showMsg").append("连接关闭!<br/>");
            }
		});
	</script>
</head>
<body>
	<div id="showMsg" style="border: 1px solid; width: 500px; height: 400px; overflow: auto;"></div>
</body>
</html>