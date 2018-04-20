<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script> %>script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>我的桌面</title>
</head>
<body>
<div class="page-container" style="height:700px;;background:url(../static/resources/images/office.jpg);background-size:100% 100%;background-attachment: fixed;background-repeat:no-repeat ; background-size: cover;">
	<p class="f-20 text-success" align="center">欢迎使用CRM <span class="f-14"></span>客户关系管理系统！</p>
	<!-- <p>登录次数：18 </p>
	<p>上次登录IP：222.35.131.79.1  上次登录时间：2014-6-14 11:19:55</p> -->
	<!-- <p align="center" style="font-size: 15px;font-weight: 700">公司2017-2018年销量走势图</p> -->

	<!-- <div id="chart" style="width:800px;height:400px;margin-left: 200px" align="center"></div> -->
</div>
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/common/echarts.js"></script>
<script type="text/javascript">
var myChart=echarts.init(document.getElementById("chart"))

option = {
    color: ['#3398DB'],
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data :/*  ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'], */(function(){
            	var url="/myCrm/product/getChartX.jhtml";
            	var arr =new Array();
            	$.ajax({
            		url:url,
            		type:'post',
            		 async : false,
            		dataType:'json',
            		success:function(data){
            			for(var i=0;i<data.length;i++){
            				arr[i]=data[i]
            			}
            		}
            	});
            	return arr;
            })(),
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'销量（台）',
            type:'bar',
            barWidth: '60%',
            data:(function(){
            	var url="/myCrm/product/getChartY.jhtml";
            	var arr =new Array();
            	$.ajax({
            		url:url,
            		type:'post',
            		 async : false,
            		dataType:'json',
            		success:function(data){
            			for(var i=0;i<data.length;i++){
            				arr[i]=data[i]
            			}
            		}
            	});
            	return arr;
            })()
        }
    ]
};
myChart.setOption(option)
</script>
</body>
</html>