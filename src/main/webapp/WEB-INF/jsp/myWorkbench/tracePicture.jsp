<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
  request.setAttribute("resourceIdentity", "myWorkbench:order");
%>
<!DOCTYPE html>
<html>
<head>
  <title>跟踪</title>
  <base href="${ctx}">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%@include file="/WEB-INF/jsp/common/css.jsp"%>
    <link href="${ctx}/resources/comp/qtip/jquery.qtip.css?v=1.1.2" type="text/css" rel="stylesheet" />
  <script type="text/javascript">
		var ctx = "${ctx}";
  </script>
  <%@include file="/WEB-INF/jsp/common/js.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/comp/qtip/jquery.qtip.pack.js"></script>
    <script type="text/javascript" src="${ctx}/resources/comp/html/jquery.outerhtml.js" ></script>
	<script type="text/javascript">
        $().ready(function() {
            $.getJSON('${ctx}/sys/activiti/activityInfo.jhtml?processInstanceId=${processInstanceId}', function(v) {
                var positionHtml = "";
                // 生成图片
                var $positionDiv = $('<div/>', {
                    'class': 'activity-attr'
                }).css({
                    position: 'absolute',
                    left: (v.x - 1),
                    top: (v.y - 1),
                    width: (v.width - 2),
                    height: (v.height - 2),
                    backgroundColor: 'black',
                    opacity: 0,
                    zIndex: $.fn.qtip.zindex - 1
                });

                // 节点边框
                var $border = $('<div/>', {
                    'class': 'activity-attr-border'
                }).css({
                    position: 'absolute',
                    left: (v.x - 1),
                    top: (v.y - 1),
                    width: (v.width - 4),
                    height: (v.height - 3),
                    zIndex: $.fn.qtip.zindex - 2
                });
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid red','border-radius' : '15px'
                });
                positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
                $("#processImageBorder").html(positionHtml);
            });
        });
	</script>
</head>
<body>
<div>
	<img src="${ctx}/sys/activiti/resource/read.jhtml?processDefinitionId=${processDefinitionId}&resourceType=image" style='position:absolute; left:0px; top:0px;'/>
	<div id='processImageBorder'>
	</div>
</div>
</body>
</html>