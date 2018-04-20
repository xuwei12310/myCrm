<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%-- <shiro:hasPermission name = "${resourceIdentity}:create"> --%>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addBtn">添加</a>
<%-- </shiro:hasPermission> --%>
<%-- <shiro:hasPermission name = "${resourceIdentity}:update"> --%>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="updateBtn">修改</a>
<%-- </shiro:hasPermission> --%>
<%-- <shiro:hasPermission name = "${resourceIdentity}:delete"> --%>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="deleteBtn">删除</a>
<%-- </shiro:hasPermission> --%>