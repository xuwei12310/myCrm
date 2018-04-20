<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
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
<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统配置 <span class="c-gray en">&gt;</span> 用户管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form action="${pageContext.request.contextPath }/coreUser/listView.jhtml" method="get">
		<input type="hidden" name="${pagerItem.paramPageIndex }" value="${pagerItem.pageIndex }" />
		<input type="hidden" name="${pagerItem.paramPageSize }" value="${pagerItem.pageSize }" />
		<div class="text-c">用户名:
			<input type="text" class="input-text" value="${username }" 
					style="width: 250px;" placeholder="请输入用户名" id="username" name="username" />
			<button type="submit" class="btn btn-success radius" id="" name="" >
				<i class="Hui-iconfont">&#xe665;</i> 搜索 
			</button>
			<button type="button" class="btn btn-success radius" id="clearSearch" name="" >
				<i class="Hui-iconfont">&#xe6e2;</i> 清除 
			</button>
		</div>
	</form>
	<!-- <div class="text-c"> 日期范围：
		<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;">
		-
		<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;">
		<input type="text" class="input-text" style="width:250px" placeholder="输入会员名称、电话、邮箱" id="" name="">
		<button type="submit" class="btn btn-success radius" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 搜用户</button>
	</div> -->
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		 <span class="l"><a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">
		 <i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> 
		 <a href="javascript:;" onclick="member_add('添加用户','${pageContext.request.contextPath }/coreUser/toAdd.jhtml','','470')" 
		 	class="btn btn-primary radius">
		 <i class="Hui-iconfont">&#xe600;</i> 添加用户</a></span> 
		 <span class="r">共有数据：<strong>${pagerItem.rowCount }</strong> 条</span>
    </div>
	<div class="mt-20">
	<table id="datalist" class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value="0"></th>
				<th width="80">用户ID</th>
				<th width="100">用户名</th>
				<th width="100">昵称</th>
				<th width="100">电话管理</th>
				<th width="80">是否管理员</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${DataList}">
				<tr class="text-c">
					<td><input type="checkbox" value="${item.userid }" name="" ></td>
					<td>${item.userid }</td>
					<td>${item.username }</td>
					<td>${item.nickname }</td>
					<td>${item.telno }</td>
					<td class="td-status">
						<c:choose>
							<c:when test="${item.isadmin=='1' }">
								<span class="label label-success radius">是</span>
							</c:when>
							<c:otherwise>
								<span class="label label-fail radius">否</span>
							</c:otherwise>
						</c:choose>
					</td>
					
					<td class="td-manage">
						<a style="text-decoration:none" onclick="member_manager(${item.userid})"
							href="javascript:;" title="设为管理员" ><i class="Hui-iconfont">&#xe61d;</i></a> 
						<a title="编辑" href="javascript:;" 
							onclick="member_edit('编辑【用户：${item.username }】','${pageContext.request.contextPath }/coreUser/toUpdate.jhtml?userid=${item.userid}','4','','450')" class="ml-5" 
							style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
						<a title="查看" href="javascript:;" 
							onclick="member_detail('查看【用户：${item.username }】','${pageContext.request.contextPath }/coreUser/detail.jhtml?userid=${item.userid}','4','','350')" class="ml-5" 
							style="text-decoration:none"><i class="Hui-iconfont">&#xe667;</i></a> 
						<a title="删除" href="javascript:;" onclick="member_del(this,${item.userid})" class="ml-5" 
							style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="__pager.jsp" flush="true" />
	</div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
$(function(){
	//清空处理
	$("#clearSearch").click(function(){
		location.href="${pageContext.request.contextPath }/coreUser/listView.jhtml?pageindex=${pagerItem.pageIndex}&pagesize=${pagerItem.pageSize }";
	});
	
	/* $('.table-sort').dataTable({
		"aaSorting": [[ 1, "desc" ]],//默认第几个排序
		"bStateSave": true,//状态保存
		"aoColumnDefs": [
		  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
		  {"orderable":false,"aTargets":[0,8,9]}// 制定列不参与排序
		]
	}); */
	
});
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*用户-查看*/
function member_show(title,url,id,w,h){
	layer_show(title,url,w,h);
}
//设为管理员
function member_manager(id){
	layer.confirm('确认要设置为管理员吗？',function(id){
	$.ajax({
		type:'POST',
		url:"${pageContext.request.contextPath}/coreUser/manager.jhtml",
		data:{"id":id},
		success: function(data){
			layer.msg('设置成功!',{icon: 1,time:1000});
		},
		error:function(data) {
			console.log(data);
		},
	});
	});
}
/*用户-停用*/
function member_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '',
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="member_start(this,id)" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe6e1;</i></a>');
				$(obj).parents("tr").find(".td-status").html('<span class="label label-defaunt radius">已停用</span>');
				$(obj).remove();
				layer.msg('已停用!',{icon: 5,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
}

/*用户-启用*/
function member_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '',
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="member_stop(this,id)" href="javascript:;" title="停用"><i class="Hui-iconfont">&#xe631;</i></a>');
				$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
				$(obj).remove();
				layer.msg('已启用!',{icon: 6,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});
	});
}
/*用户-编辑*/
function member_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*用户-查看*/
function member_detail(title,url,id,w,h){
	layer_show(title,url,w,h);
}

/*密码-修改*/
function change_password(title,url,id,w,h){
	layer_show(title,url,w,h);	
}
/*用户-删除*/
function member_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '${pageContext.request.contextPath}/coreUser/delete.jhtml',
			data:{"id":id},
			success: function(data){
				if(data=="ok"){
					$(obj).parents("tr").remove();
					layer.msg('已删除!',{icon:1,time:1000});
				}else{
					layer.msg('删除失败!',{icon:1,time:1000});
				}
				
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
}

function datadel(){
	
	layer.confirm('确认要删除选中的数据吗?',function(index){
		var num=0; //记录删除成功的行数
		var total=0; //记录要删除的行数
		var obj=null; //记录当前对象
		$("#datalist input[type=checkbox]:checked").each(function(){
			obj=this;
			id=$(this).val();
			
			if(id!=null && id!="0"){
				//删除单行数据
				//alert(id);
				total++;
				$.ajax({
					type:'POST',
					url:"${pageContext.request.contextPath}/coreUser/delete.jhtml",
					async:false, //要使用同步
					data:{"id":id},
					success:function(data){
						if(data=="ok"){
							$(obj).parents("tr").remove();
							num++;
							//layer.msg('已删除!',{icon:1,time:1000});
						}else{
							//layer.msg('删除失败!',{icon:1,time:1000});
						}
					},
					error:function(data){
						//console.log(data.msg);
					},
				});
			}
		});
		layer.msg('要删除'+total+'行记录,成功删除'+num+'行记录。',{icon:1,time:3000});
	});
	
}

</script> 
</body>
</html>