<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>message</title>
</head>
<body>
<div>
	${message}  管理员：${user}
	</div>
	<div class="info">
			<p id="show"></p>
		</div>
		<script type="text/javascript">
			var t = 5;
			function showinfo() {
				if(t > 0) {
					var p1= document.getElementById('show');
					p1.innerText= t + "秒后将转到首页"
					t--;
					window.setTimeout("showinfo()",1000);
				}else{
					window.location.href="admin.jsp"
				}
			}
			showinfo();
		</script>
</body>
</html>