<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/html5shiv.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>客户关系管理系统</title>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="/aboutHui.shtml">CRM</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml">H-ui</a> 
			<span class="logo navbar-slogan f-l mr-10 hidden-xs">v3.1</span> 
			<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
			<nav class="nav navbar-nav">
				<ul class="cl">
					<li class="dropDown dropDown_hover"><a href="javascript:;" class="dropDown_A"><i class="Hui-iconfont">&#xe600;</i> 新增 <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" onclick="article_add('添加资讯','article-add.html')"><i class="Hui-iconfont">&#xe616;</i> 资讯</a></li>
							<li><a href="javascript:;" onclick="picture_add('添加资讯','picture-add.html')"><i class="Hui-iconfont">&#xe613;</i> 图片</a></li>
							<li><a href="javascript:;" onclick="product_add('添加资讯','product-add.html')"><i class="Hui-iconfont">&#xe620;</i> 产品</a></li>
							<li><a href="javascript:;" onclick="member_add('添加用户','member-add.html','','510')"><i class="Hui-iconfont">&#xe60d;</i> 用户</a></li>
					</ul>
				</li>
			</ul>
		</nav>
		<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
			<ul class="cl">
				<li>${user.username}</li>
				<li class="dropDown dropDown_hover">
					<a href="#" class="dropDown_A">${sessionScope.loginuser.userName } <i class="Hui-iconfont">&#xe6d5;</i></a>
					<ul class="dropDown-menu menu radius box-shadow">
						<li><a href="javascript:;" onClick="myselfinfo()">个人信息</a></li>
						<li><a href="#">切换账户</a></li>
						<li><a href="${pageContext.request.contextPath }/login/logout.jhtml">退出</a></li>
				</ul>
			</li>
				<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
				<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
					<ul class="dropDown-menu menu radius box-shadow">
						<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
						<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
						<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
						<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
						<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
						<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</div>
</div>
</header>
<aside class="Hui-aside">
	<%-- <div class="menu_dropdown bk_2">
		<dl id="menu-syscfg">
			<dt><i class="Hui-iconfont">&#xe616;</i>系统配置
				<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
			</dt>
			<dd>
				<ul>
					<li><a data-href="${pageContext.request.contextPath }/coreUser/listView.jhtml" data-title="用户管理" 
							href="javascript:void(0)">用户管理</a></li>
					<li><a data-href="${pageContext.request.contextPath }/organization/main.jhtml" data-title="部门管理" 
					href="javascript:void(0)">部门管理</a></li>
				</ul>
			</dd>
		</dl>
	</div>
	<div class="menu_dropdown bk_2">
		<dl id="menu-syscfg">
			<dt><i class="Hui-iconfont">&#xe616;</i>基础字典
				<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
			</dt>
			<dd>
				<ul>
					<li><a data-href="${pageContext.request.contextPath }/customerStatus/main.jhtml" data-title="客户状态" 
							href="javascript:void(0)">客户状态</a></li>
					<li><a data-href="${pageContext.request.contextPath }/payType/main.jhtml" data-title="支付类型" 
					href="javascript:void(0)">支付类型</a></li>
					<li><a data-href="${pageContext.request.contextPath }/followType/main.jhtml" data-title="跟进方式" 
					href="javascript:void(0)">跟进方式</a></li>
					<li><a data-href="${pageContext.request.contextPath }/followStage/main.jhtml" data-title="跟进阶段" 
					href="javascript:void(0)">跟进阶段</a></li>
				</ul>
			</dd>
		</dl>
	</div>
	<div class="menu_dropdown bk_2">
		<dl id="menu-basecfg">
			<dt><i class="Hui-iconfont">&#xe616;</i>客户档案
				<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
			</dt>
			<dd>
				<ul>
					<li><a data-href="${pageContext.request.contextPath }/customer/main.jhtml" data-title="客户" 
							href="javascript:void(0)">客户</a>
					</li>
					<li><a data-href="${pageContext.request.contextPath }/follow/main.jhtml" data-title="客户跟进" 
							href="javascript:void(0)">客户跟进</a>
					</li>
				</ul>
			</dd>
		</dl>
	</div>
	<div class="menu_dropdown bk_2">
		<dl id="menu-rent">
			<dt><i class="Hui-iconfont">&#xe616;</i>产品管理
				<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
			</dt>
			<dd>
				<ul>
					<li><a data-href="${pageContext.request.contextPath }/product/main.jhtml" data-title="产品库" 
							href="javascript:void(0)">产品库</a>
					</li>
				</ul>
			</dd>
		</dl>
	</div>
	<div class="menu_dropdown bk_2">
		<dl id="menu-rent2">
			<dt><i class="Hui-iconfont">&#xe616;</i>订单管理
				<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
			</dt>
			<dd>
				<ul>
					<li><a data-href="${pageContext.request.contextPath }/order/main.jhtml" data-title="订单合同" 
							href="javascript:void(0)">订单合同</a>
					</li>
					<!-- <li><a data-href="article-list.html" data-title="求租客户管理" 
							href="javascript:void(0)">求租客户管理</a>
					</li> -->
				</ul>
			</dd>
		</dl>
	</div> --%>
	<c:forEach items="${resourcesList}" var="resources">
		<div class="menu_dropdown bk_2">
		<dl id="menu-rent">
			<dt class="rootNode" id="${resources.id }"><i class="Hui-iconfont">&#xe616;</i>${resources.name}
				<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
			</dt>
			<dd>
				<ul></ul>
			</dd>
		</dl>
	</div>
	</c:forEach>
	
		
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)" style="opacity:0.5"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active">
					<span title="我的桌面" data-href="welcome.html">我的桌面</span>
					<em></em></li>
		</ul>
	</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="../main/welcome.jhtml"></iframe>
	</div>
</div>
</section>

<div class="contextMenu" id="Huiadminmenu">
	<ul>
		<li id="closethis">关闭当前 </li>
		<li id="closeall">关闭全部 </li>
</ul>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">

$(".rootNode").click(function(){
	//判断点击节点是否已经展开，若未展开则取出子节点
	if(!$(this).hasClass("selected")){
	var pid=$(this).attr("id");
	var node=$(this);
	$.ajax({
		url:"${pageContext.request.contextPath}/main/menuTree.jhtml",
		data:{"id":pid},
		success:function(data){
			var content="";
			for(var i=0;i<data.length;i++){
				content+="<li><a data-href='${pageContext.request.contextPath }/"+data[i].url+"' data-title='"+data[i].name+"' href='javascript:void(0)'>"+data[i].name+"</a></li>";
			}
			node.next().find("ul").html(content);
			
		}
	})
	}
})
/*个人信息*/
function myselfinfo(){
	layer.open({
		type: 1,
		area: ['300px','200px'],
		fix: false, //不固定
		maxmin: true,
		shade:0.4,
		title: '查看信息',
		content: '<div>管理员信息</div>'
	});
}

/*资讯-添加*/
function article_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*图片-添加*/
function picture_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*产品-添加*/
function product_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}

</script> 

</body>
</html>