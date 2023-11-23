<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
<head id="Head1">
    <%@ include file="/web/common/common.jsp" %>
<script type="text/javascript">
var roletype = "<?php echo session('roletype');?>";
$(function () {
    $('#grid1').datagrid({
        title: '类别列表',
        nowrap: false,
        striped: true,
        fit: true,
        url: "<%=__APP__%>/Good!getTypeList",
        idField: 'id',
        pagination: true,
        rownumbers: true,
        pageSize: 10,
        pageNumber: 1,
        singleSelect: true,
        fitColumns: true,
        pageList: [10, 20, 50, 100],
        sortName: 'id',
        sortOrder: 'desc',
        columns: [
            [
                //{field: 'ck', checkbox: true},
                {title: '名称', width: 100, field: 'title', sortable: true}
            ]
        ], toolbar: [
            {
                text: '新增',
                id: "tooladd",
                disabled: false,
                iconCls: 'icon-add',
                handler: function () {
                    $("#action").val("add");
                    $("#managerDialog").dialog('open');
                    managForm.reset();
                }
            },
            '-',
            {
                text: '修改',
                id: 'tooledit',
                disabled: false,
                iconCls: 'icon-edit',
                handler: function () {
                    $("#action").val("edit");
                    var selected = $('#grid1').datagrid('getSelected');
                    if (selected) {
                        edit(selected);
                    } else {
                        $.messager.alert("提示", "请选择一条记录进行操作");
                    }
                }
            },
            '-',
            {
                text: '删除',
                id: 'tooldel',
                disabled: false,
                iconCls: 'icon-remove',
                handler: function () {
                    var rows = $('#grid1').datagrid('getSelections');
                    if (rows.length) {
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            ids += rows[i].id + ",";
                        }
                        ids = ids.substr(0, (ids.length - 1));
                        $.messager.confirm('提示', '确定要删除吗？', function (r) {
                            if (r) {
                                deleteItem(ids);
                            }
                        });
                    } else {
                        $.messager.alert("提示", "请选择一条记录进行操作");
                    }
                }
            }
        ]
    });

    document.onkeydown=function (e){
        e = e ? e : event;
        if(e.keyCode == 13){
            query();
        }
    }

});

function save() {
    $('#managForm').form('submit', {
        url: "<%=__APP__%>/Good!addType",
        onSubmit: function () {
            return inputCheck();
        },
        success: function (data) {
            closeBackGround();
            $.messager.alert("提示", data, "info", function () {
                closeFlush();
            });
        }
    });
}

function edit(obj) {
	var id = obj.id;
    $("#id").val(id);
    $("#title").val(obj.title);
    $("#managerDialog").dialog('open');
}

function deleteItem(uuid) {
    openBackGround();
    $.post("<%=__APP__%>/Good!deleteTypeItem", {id: uuid}, function (data) {
        closeBackGround();
        closeFlush();
    });
}

function cancel() {
    $.messager.confirm('提示', '是否要关闭？', function (r) {
        if (r) {
            closeFlush();
        }
    });
}

function query() {
    $('#grid1').datagrid('load', serializeObject($('#searchForm')));
}


function closeFlush() {
    managForm.reset();
    $("#managerDialog").dialog('close');
    $("#grid1").datagrid("reload");
}

function inputCheck() {
    if (!($("#managForm").form("validate"))) {
        return false;
    }
    openBackGround();
    return true;
}


function setNull(){
    searchForm.reset();
}



</script>
</head>
<body class="easyui-layout">
<div region="north" border="false" style="height:3px;overflow: hidden"></div>
<div region="west" border="false" style="width:3px;"></div>
<div region="east" border="false" style="width:3px;"></div>
<div region="south" border="false" style="height:3px;overflow: hidden"></div>
<div region="center" border="false">
    <div id="main" class="easyui-layout" fit="true" style="width:100%;height:100%;">
        <div region="north" id="" style="height:100%;" class="" title="查询条件">
            <form action="" id="searchForm" name="searchForm" method="post">
                <table cellpadding="5" cellspacing="0" class="tb_search">
                    <tr>
                        <td width="10%">
                            <label for="sgname">名称：</label>
                            <input type="text" id="sgname" name="sgname" width="100%" maxlength="32"/>
                        </td>
                        <td width="10%">
                            <a href="#" onclick="query();" id="querylink" class="easyui-linkbutton"
                               iconCls="icon-search">查询</a>
                            <a href="#" onclick="setNull();" class="easyui-linkbutton" iconCls="icon-redo">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div region="center" border="false" style="padding:3px 0px 0px 0px;overflow:hidden">

            <table id="grid1"></table>

        </div>
    </div>
</div>


<div id="managerDialog" class="easyui-dialog" title="类别管理" style="width:350px;height:150px;" toolbar="#dlg-toolbar"
     buttons="#dlg-buttons2" resizable="true" modal="true" closed='true'>
    <form id="managForm" name="managForm" method="post" enctype="multipart/form-data">
        <input type="hidden" id="action" name="action"/>
        <input type="hidden" id="id" name="id"/>
        <table cellpadding="1" cellspacing="1" class="tb_custom1">
            <tr>
                <th width="30%" align="right"><label>名称：</label></th>
                <td width="30%">
                    <input id="title" name="type.title" class="easyui-validatebox"
                           style="width:200px;word-wrap: break-word;word-break:break-all;" type="text" required="true"
                           validType="length[0,100]"/>
                </td>

            </tr>
        </table>


    </form>
    <div id="dlg-buttons2">
        <a href="#" class="easyui-linkbutton" onclick="save();">保存</a>
        <a href="#" class="easyui-linkbutton" onclick="cancel();">取消</a>
    </div>
</div>

<div id="viewDialog" class="easyui-dialog" title="商品查看" style="width:450px;height:250px;" toolbar="#dlg-toolbar"
     buttons="" resizable="true" modal="true" closed='true'>

    <table cellpadding="1" cellspacing="1" class="tb_custom1">
        <tr>
            <th width="50px;" align="right"><label>编号：</label></th>
            <td width="150px;">
                <span id="id2"></span>
            </td>
            <th align="right"><label>型号：</label></th>
            <td>
                <span id="gmodel2"></span>
            </td>
        </tr>
        <tr>
            <th align="right"><label>名称：</label></th>
            <td colspan="">
                <span id="gname2"></span>
            </td>
            <th align="right"><label>数量：</label></th>
            <td colspan="">
                <span id="gnumber2"></span>
            </td>
        </tr>
        <tr>
            <th align="right"><label>品牌：</label></th>
            <td>
                <span id="gbrand2"></span>
            </td>
            <th align="right"><label>入库日期：</label></th>
            <td>
                <span id="intime2"></span>
            </td>
        </tr>

        <tr>
            <th align="right"><label>颜色：</label></th>
            <td>
                <span id="gcolor2"></span>
            </td>
            <th align="right"><label>价格：</label></th>
            <td>
                <span id="gprice2"></span>
            </td>
        </tr>

        <tr>
            <th align="right"><label>备注：</label></th>
            <td colspan="3">
                <span id="note2"></span>
            </td>
        </tr>
    </table>
</div>

</body>
</html>