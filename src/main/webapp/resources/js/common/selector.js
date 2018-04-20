/**
 * Created by dzy on 2017/7/10.
 * 弹出式选择器
 */
var selector = {
    /**
     * 初始化树
     * @param tree
     * @param url
     * @param table
     */
    initTree: function (tree,urls,table) {
        var params = $.extend({
            animate: true,
            lines: true,
            dnd: true,
            parentField: "pId",
            url: urls.tree.treeRoot,
            onBeforeExpand: function (node, param) {
                tree.tree('options').url = urls.tree.treeChildren + '?parentId=' + node.id;

            },
            onClick: function (node) {
                tree.tree('expand', node.target);
                var url = urls.list + '?parentId=' + node.id;
                table.datagrid({
                    url: url
                });
            },
            onLoadSuccess: function (node, data) {
                if (node == null) {
                    var root = tree.tree('getRoot');
                    tree.tree('select', root.target);
                    tree.tree('expand', root.target);
                }
            },
            onBeforeDrop: function (target, source, point) {
                var root = tree.tree('getRoot');
                var targetId = tree.tree('getNode', target).id;
                if (root.id == targetId) {
                    $.messager.alert("提示", '不能与根节点同级', 'warning');
                    return false;
                }
                return true;
            }
        }, params);
        tree.tree(params);
    },
    /**
     * 初始化选择器
     * @param dialog 弹出层 传入例：$("#myDialog")
     * @param table  数据表格 传入例：$("#myList")
     * @param dialogParam 弹出窗口 参数
     * @param urls
     * @param columns 表格列
     * @param selectOk 选择完成回调函数 接收参数为选中的对象
     */
    initTable: function (dialog,table,url,columns,selectOk) {
        table.datagrid({
            border: true,
            ctrlSelect: true,
            url: url.list,
            nowrap: true,
            striped: true,
            singleSelect: true,
            checkOnSelect: true,
            collapsible: true,
            pagination: true,
            rownumbers: true,
            fit: true,
            toolbar: [{
                text: '选择',
                iconCls: 'icon-ok',
                handler: function () {
                    var selectRow = table.datagrid('getSelected');
                    if (selectRow == null) {
                        $.messager.alert('提示', '请选择', 'warning');
                        return;
                    }
                    selectOk(selectRow);
                    dialog.dialog('close');
                }
            },
                {
                    text: '退出',
                    iconCls: 'icon-back',
                    handler: function () {
                        dialog.dialog('close');
                    }
                }],
            columns: columns
        });
        /**
         * 查询按钮
         */
        dialog.find("#searchBtn").click(function () {
            var content = dialog.find("form").serializeObject();
            table.datagrid({
                queryParams: content
            });
        });
        /**
         * 清除按钮
         */
        dialog.find("#clearBtn").click(function () {
            dialog.find("form").form('clear');
        });

    }
    ,
    initSelector: function (dialog, dialogParam, urls, columns, selectOk) {
        var table = dialog.find(app.id.listId);
        var tree = dialog.find(app.id.treeId);
        var params = $.extend({
            title: "请选择",
            width: $(document).width() - 70,
            height: $(document).height() - 70,
            top: 5,
            closed: true,
            cache: false,
            modal: true,
            closable: true,
            resizable: true,
        }, dialogParam);
        //选择弹出层
        dialog.dialog(params);

        //树初始化
        if (urls.tree) {
            selector.initTree(tree,urls,table);
        }
        //表格初始化
        selector.initTable(dialog,table,urls,columns,selectOk);
        dialog.dialog('open');
    },
};