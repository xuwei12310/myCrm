var activiti = {

    initCRUDBtn: function () {
        // 添加
        $(app.id.addBtnId).click(function () {
            activiti.add();
        });
        // 修改
        $(app.id.updateBtnId).click(function () {
            activiti.update();
        });
        // 删除
        $(app.id.deleteBtnId).click(function () {
            activiti.mulDelete();
        });
        // 查询
        $(app.id.searchBtnId).click(function () {
            activiti.search();
        });
        // 清空查询条件
        $(app.id.clearBtnId).click(function () {
            $(app.id.searchFormId).form('clear');
        });
    },
    initViewList: function (params) {
        var params = $.extend({
            url: app.url.datagrid(),
            pagination: true,
            fit: true,
            border: false,
            toolbar: app.id.toolbarId,
            ctrlSelect: true,
            rownumbers: true,
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100],
            onDblClickRow: function () {
                $(app.id.updateBtnId).click();
            }
        }, params);
        $(app.id.listId).datagrid(params);
    },
    initEditDialog: function () {
        var params = {
            title: '编辑' + resourceName,
            width: 350,
            height: 250,
            closed: true,
            cache: false,
            modal: true,
            buttons: [{
                text: '保存',
                iconCls: 'icon-save',
                handler: function () {
                    $(app.id.editFormId).submit();
                }
            }, {
                text: '退出',
                iconCls: 'icon-exit',
                handler: function () {
                    $(app.id.editDialogId).dialog('close');
                }
            }],
            onClose: function () {
                $(app.id.editDialogId).form('clear');
            }
        };
        $(app.id.editDialogId).dialog(params);
    },
    initEditForm: function () {
        $(app.id.editFormId).form({
            onSubmit: function () {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    $.messager.progress('close');
                }
                return isValid;
            },
            success: function (data) {
                $.messager.progress('close');
                var data = eval('(' + data + ')');
                if (data.isSuccess) {
                    $.messager.alert('提示', '保存成功', 'info', function () {
                        $(app.id.editDialogId).dialog('close');
                        $(app.id.listId).datagrid('reload');
                    });
                } else {
                    $.messager.alert('提示', data.message, 'error');
                }
            }
        });
    },

    add: function () {
        $(app.id.editDialogId).dialog('open');
        activiti.initEditForm();
        $("#status").combobox("setValue", 1);
        $(app.id.editFormId).attr('action', app.url.create());
        app.yesOrNoByIntCombobox("#isSys");
    },
    update: function () {
        var selectedRow = $(app.id.listId).datagrid('getSelected');
        if (selectedRow == null) {
            $.messager.alert('提示', '请选择数据行', 'warning');
            return;
        }
        if (selectedRow.isSys == 1) {
            $.messager.alert('提示', '系统内置不能修改', 'warning');
            return;
        }
        $(app.id.editDialogId).dialog('open');
        activiti.initEditForm();
        $(app.id.editFormId).form('load', selectedRow);
        $(app.id.editFormId).attr('action', app.url.update());
        app.yesOrNoByIntCombobox("#isSys");
    },
    mulDelete: function () {
        var checkedRows = $(app.id.listId).datagrid('getChecked');
        if (checkedRows.length == 0) {
            $.messager.alert('提示', '请选择删除的数据行', 'warning');
            return;
        }
        $.messager.confirm("提示", "确定要删除选中的记录?", function (t) {
            if (!t)
                return;
            var idArray = [];
            for (var i = 0; i < checkedRows.length; i++) {
                idArray.push(checkedRows[i].deploymentId);
            }
            var ids = idArray.join(CSIT.join);
            var content = {
                ids: ids
            };
            $.messager.progress();
            CSIT.asyncCallService(app.url.mulDelete(), content,
                function (result) {
                    if (result.isSuccess) {
                        $(app.id.listId).datagrid('reload');
                    } else {
                        $.messager.alert('错误', result.message, "error");
                    }
                    $.messager.progress('close');
                });
        });
    },
    search: function () {
        var isValid = $(app.id.searchFormId).form('validate');
        if (!isValid) {
            return false;
        }
        var content = $(app.id.searchFormId).serializeObject();
        $(app.id.listId).datagrid({
            queryParams: content
        });
    },
    initConfigDialog: function () {
        var params = {
            width: 1000,
            height: document.documentElement.clientHeight - 5 < 500 ? document.documentElement.clientHeight - 5 : 500,
            closed: true,
            cache: false,
            modal: true,
        };
        $("#configDialog").dialog(params);
    },
    init: function (params) {
        activiti.initEditDialog();
        activiti.initConfigDialog();
        activiti.initCRUDBtn();
        activiti.initViewList({
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: '流程定义Id', width: 100, align: 'center'},
                {field: 'deploymentId', title: 'DeploymentId', width: 100, align: 'center'},
                {field: 'name', title: '名称', width: 100, align: 'center'},
                {field: 'key', title: 'KEY', width: 100, align: 'center'},
                {field: 'version', title: '版本号', width: 100, align: 'center'},
                {field: 'resourceName', title: 'XML', width: 100, align: 'center',formatter : function(value, row, index) {
                    return "<a target='_blank' href='"+ctx+resourcePath+"/resource/read.jhtml?processDefinitionId="+row["id"]+"&resourceType=xml' style='color: blue'>"+value+"</a>"
                }},
                {field: 'diagramResourceName', title: '图片', width: 100, align: 'center',formatter : function(value, row, index) {
                    return "<a target='_blank' href='"+ctx+resourcePath+"/resource/read.jhtml?processDefinitionId="+row["id"]+"&resourceType=image' style='color: blue'>"+value+"</a>"
                }},
                {field: 'suspended', title: '是否挂起', width: 100, align: 'center',formatter : function(value, row, index) {
                    if (value == "true") {
                        return '是';
                    } else {
                        return '否';
                    }
                }}
                ]]
        });
        app.enableCombobox($('#status', editDialog));
        $("#statu").combobox('clear');
    }

};
