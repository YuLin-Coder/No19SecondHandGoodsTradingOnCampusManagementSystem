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
        $(function () {
            $('#grid1').datagrid({
                title: '帖子列表',
                nowrap: false,
                striped: true,
                fit: true,
                url: "<%=__APP__%>/Posts!getList",
                idField: 'uuid',
                pagination: true,
                rownumbers: true,
                pageSize: 10,
                pageNumber: 1,
                singleSelect: true,
                fitColumns: true,
                sortName: 'id',
                sortOrder: 'desc',
                columns: [
                    [
                        {title: 'id', field: 'id', width: 100, hidden: false},
                        {title: '标题', field: 'title', width: 100, sortable: true},
                        {title: '内容', field: 'note', width: 100, sortable: true},
                        {title: '时间', width: 100, field: 'ndate'},
                        {title:'作者',width:100,field:'username'}

                    ]
                ],
                toolbar: [
/*                     {
                        text: '新增',
                        iconCls: 'icon-add',
                        handler: function () {
                            $("#action").val("add");
                            $("#managerDialog").dialog('open');
                            $('.validatebox-tip').hide();
                        }
                    },
                    '-', */
/*                     {
                        text: '修改',
                        id: 'commit',
                        iconCls: 'icon-edit',
                        handler: function () {
                            $("#action").val("edit");
                            var selected = $('#grid1').datagrid('getSelected');
                            if (selected) {
                                edit(selected);
                                var index = $('#grid1').datagrid('getRowIndex', selected);

                            } else {
                                $.messager.alert("提示", "请选择一条记录进行操作");
                            }
                        }
                    },
                    '-', */
                    {
                        text: '删除',
                        id: 'commit',
                        iconCls: 'icon-remove',
                        handler: function () {
                            var rows = $('#grid1').datagrid('getSelected');
                            if (rows) {
                                var rowId = rows.id;
                                $.messager.confirm('提示', '确定要删除吗？', function (r) {
                                    if (r) {
                                        deleteItem(rowId);
                                    }
                                });
                            } else {
                                $.messager.alert("提示", "请选择一条记录进行操作");
                            }
                        }
                    }
                ]

            });
        });

        function save() {
            $('#managForm').form('submit', {
                url: "<%=__APP__%>/Posts!add",
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
            //$.post("controller/userController.php?action=getOne",{id:uuid},function(data){
            var username = obj.username;
            var password = obj.passwd;
            var roletype = obj.roletype;
            $("#username").val(username);
            $("#passwd").val(password);
            $("#roletype").combobox('setValue', roletype);
            $("#id").val(obj.id);
            $("#managerDialog").dialog('open');
            //});
        }

        function deleteItem(uuid) {
            $.post("<%=__APP__%>/Posts!deleteItem", {id: uuid}, function (data) {
                closeFlush();
            });
        }

        function cancel() {
            $.messager.confirm('提示', '是否要关闭？', function (r) {
                if (r) {
                    $("#managerDialog").dialog('close');
                }
            });
        }

        function query() {
            /*
             var username = $("#username").val();
             var creatTime = $("#creatTm").datebox("getValue");
             var obj = new Object();
             obj.username = username;
             obj.createTime = creatTime
             $('#grid1').datagrid('options').queryParams = obj;
             $('#grid1').datagrid("reload");*/
            //$('#grid1').datagrid('loadData',{total:0,rows:[]});
            $('#grid1').datagrid('options').queryParams = serializeObject($('#searchForm'));
            $('#grid1').datagrid("reload");
            //$('#grid1').datagrid('loadData',{total:0,rows:[]});
            //$('#grid1').datagrid('load', serializeObject($('#searchForm')));
        }

        function reset() {
            searchForm.reset();
        }

        function closeFlush() {
            managForm.reset();
            $("#managerDialog").dialog('close');
            $("#grid1").datagrid("reload");
        }

        function inputCheck() {
            if ($("#passwd").val() != $("#password2").val()) {
                $.messager.alert("提示", "两次输入密码不一致!");
                return false;
            } else if (!($("#managForm").form("validate"))) {
                return false;
            }
            openBackGround();
            return true;
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
        <div region="north" id="" style="height:80%;" class="" title="查询条件">
            <form action="" id="searchForm" name="searchForm" method="post">
                <table cellpadding="1" cellspacing="0" class="tb_search">
                    <tr>
                        <td width="10%">
                            <label for="susername">帖子名称：</label>
                            <input type="text" id="susername" name="susername" width="100%" maxlength="32"/>
                        </td>

                        <td width="10%">
                            <a href="#" onclick="query();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                            <a href="#" onclick="reset();" class="easyui-linkbutton" iconCls="icon-redo">重置</a>
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


<div id="managerDialog" class="easyui-dialog" title="用户管理" style="width:500px;height:290px;" toolbar="#dlg-toolbar"
     buttons="#dlg-buttons2" resizable="true" modal="true" closed='true'>
    <form id="managForm" name="managForm" method="post">
        <input type="hidden" id="action" name="action"/>
        <input type="hidden" id="id" name="id"/>
        <table cellpadding="1" cellspacing="1" class="tb_custom1">
            <tr>
                <th width="10%" align="right"><label>用户名：</label></th>
                <td width="30%">
                    <input id="username" name="user.username" class="easyui-validatebox"
                           style="width:300px;word-wrap: break-word;word-break:break-all;" type="text" required="true"
                           validType="length[0,32]"/><font color='red'>*</font></td>
            </tr>
            <tr>
                <th width="10%" align="right"><label>权限：</label></th>
                <td width="30%">
                    <select id="roletype" name="user.roletype" class="easyui-combobox" panelHeight="auto"
                            style="width:300px;word-wrap: break-word;word-break:break-all;" type="text" required="true"
                            validType="length[0,32]">
                        <option value="1" selected="selected">管理员</option>
                        <option value="2">店主</option>
                    </select></td>
            </tr>
            <tr>
                <th width="10%" align="right"><label>密码：</label></th>
                <td width="10%">
                    <input id="passwd" name="user.passwd" class="easyui-validatebox" required="true" validType="length[0,32]"
                           style="width:300px"/>
                </td>
            </tr>
            <tr>
                <th width="10%" align="right"><label>密码确认：</label></th>
                <td width="10%">
                    <input id="password2" name="" class="easyui-validatebox" style="width:300px" required="true"
                           validType="length[0,32]"/>
                </td>
            </tr>

        </table>
    </form>
    <div id="dlg-buttons2">
        <a href="#" class="easyui-linkbutton" onclick="save();">保存</a>
        <a href="#" class="easyui-linkbutton" onclick="cancel();">取消</a>
    </div>
</div>

</body>
</html>