<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>login</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/resources/css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/resources/css/demo2.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/resources/css/jquery.toast.css" />
	<!--必要样式-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/resources/css/component.css" />
</head>
<body style="margin:0;padding:0">
		<div class="container demo-1">
			<div class="content">
				<div id="large-header" class="large-header">
					<canvas id="demo-canvas"></canvas>
					<div class="logo_box">
						<h3>CRM系统</h3>
						<form action="${pageContext.request.contextPath }/login/login.jhtml" name="f" id="loginForm" method="post">
							<div class="input_outer">
								<span class="u_user"></span>
								<input id="username" name="username" value="${username }" class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入账户">
							</div>
							<div class="input_outer">
								<span class="us_uer"></span>
								<input id="password" name="password" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
							</div>
							<div class="mb2"><a class="act-but submit" href="javascript:void(0);" id="loginBtn" style="color: #FFFFFF">登录</a></div>
						</form>
					</div>
				</div>
			</div>
		</div><!-- /container -->
		<script src="${pageContext.request.contextPath }/static/resources/js/jquery-2.1.0.min.js"></script>
		<script src="${pageContext.request.contextPath }/static/resources/js/TweenLite.min.js"></script>
		<script src="${pageContext.request.contextPath }/static/resources/js/EasePack.min.js"></script>
		<script src="${pageContext.request.contextPath }/static/resources/js/demo-1.js"></script>
		<script src="${pageContext.request.contextPath }/static/resources/js/jquery.toast.js"></script>
		<script type="text/javascript">
		$(function(){
			document.onkeypress=function(e){
				if(e.keyCode==13){
					var username=$("#username").val();
					var password=$("#password").val();
					if(username==null || password==null){
						$.toast({ 
							  heading:"错误",   //标题
							  text : "用户名或密码不能为空", 
							  showHideTransition : 'slide',  // It can be plain, fade or slide
							  loaderBg: '#6c734f',
							  bgColor : 'rgba(35,53,73,1)',              // Background color for toast
							  icon:'warning',
							  textColor : '#eee',            // text color
							  allowToastClose : true,       // Show the close button or not
							  hideAfter : 3000,              // `false` to make it sticky or time in miliseconds to hide after
							  stack : 5,                     // `fakse` to show one stack at a time count showing the number of toasts that can be shown at once
							  textAlign : 'left',            // Alignment of text i.e. left, right, center
							  position : 'bottom-center'       // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values to position the toast on page
							})
							return;
					}
					$("#loginForm").submit();
				}
			}
			$("#loginBtn").click(function(){
				var username=$("#username").val();
				var password=$("#password").val();
				if(username==null || password==null){
					$.toast({ 
						  heading:"错误",   //标题
						  text : "用户名或密码不能为空", 
						  showHideTransition : 'slide',  // It can be plain, fade or slide
						  loaderBg: '#6c734f',
						  bgColor : 'rgba(35,53,73,1)',              // Background color for toast
						  icon:'warning',
						  textColor : '#eee',            // text color
						  allowToastClose : true,       // Show the close button or not
						  hideAfter : 3000,              // `false` to make it sticky or time in miliseconds to hide after
						  stack : 5,                     // `fakse` to show one stack at a time count showing the number of toasts that can be shown at once
						  textAlign : 'left',            // Alignment of text i.e. left, right, center
						  position : 'bottom-center'       // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values to position the toast on page
						})
						return;
				}
				$("#loginForm").submit();
			});
			var msg="${errorMsg}";
			if(msg!=null && msg!=""){
				$.toast({ 
				  heading:"错误",   //标题
				  text : msg, 
				  showHideTransition : 'slide',  // It can be plain, fade or slide
				  loaderBg: '#6c734f',
				  bgColor : 'rgba(35,53,73,1)',              // Background color for toast
				  icon:'warning',
				  textColor : '#eee',            // text color
				  allowToastClose : true,       // Show the close button or not
				  hideAfter : 3000,              // `false` to make it sticky or time in miliseconds to hide after
				  stack : 5,                     // `fakse` to show one stack at a time count showing the number of toasts that can be shown at once
				  textAlign : 'left',            // Alignment of text i.e. left, right, center
				  position : 'bottom-center'       // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values to position the toast on page
				})
			};
		});
		</script>
	</body>
</html>