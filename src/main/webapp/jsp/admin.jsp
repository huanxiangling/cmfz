<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<script>
    $(function () {

        //表格初始化
        $("#adminTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/admin/showAllAdmin",
                datatype: "json",
                colNames: ['ID', '用户名', '密码', '角色', '状态', '操作'],
                colModel: [
                    {name: 'id', hidden: true},
                    {name: 'username', editable: true, align: "center",},
                    {name: 'password', hidden: true},
                    {
                        name: 'roles', align: "center", formatter: function (data) {
                        var a = "";
                        for (var i = 0; i < data.length; i++) {
                            a += " " + data[i].name + " ";
                        }
                        return a;
                    }, editable: true
                    },
                    {
                        name: 'status',
                        align: "center",
                        editable: true,
                        edittype: "select",
                        editoptions: {value: "1:展示;2:冻结"}
                    },
                    {
                        name: 'doing', search: false,
                        align: "center", editable: true, editoptions: {required: true},
                        formatter: function (cellvalue, options, rowObject) {
                            return '<a href="#" data-toggle="modal" data-target="#myAdminModal" onclick=\'editRow("' + rowObject.id + '")\' class="btn btn-lg"> <span class="glyphicon glyphicon-th-list"></span></a>'
                        }
                    },

                ],
                rowNum: 3,
                height: 500,
                rowList: [1, 3, 5, 10],
                pager: '#adminPage',
                mtype: "post",
                viewrecords: true,
                styleUI: "Bootstrap",
                autowidth: true,
                multiselect: true,
                editurl: "${pageContext.request.contextPath}/admin/change"
            });
        $("#adminTable").jqGrid('navGrid', '#adminPage',
            {
                edit: false, add: false, del: true,
                edittext: "编辑", addtext: "添加", deltext: "删除"
            },
            // edit add del
            {}, {
                closeAfterAdd: true,
            }, {
                closeAfterDel: true,
            },
            {
                sopt: ['eq', 'ne', 'cn'],//配置搜索条件如何
                closeAfterSearch: true
            },//对搜索时的配置对象
        );
    });
    //管理员信息回显
    function editRow(id) {
        $.post('${pageContext.request.contextPath}/admin/findOne', {id: id}, (result) => {
            $('#username').val(result.username);
            $('#pwd').val(result.password);
            $.post("${pageContext.request.contextPath}/admin/findRoles", function (data) {
                $("#rolelist").empty();
                data.forEach(function (role) {
                    var option = "<input type='checkbox' id='" + role.name + "' name='roless' value='" + role.id + "'>" + "<span>" + role.name + "</span>" + "&nbsp;&nbsp;";
                    $("#rolelist").append(option);
                });
                var option;
                result.roles.forEach(function (re) {
                    $("#" + re.name).prop("checked", true);
                });
                $("#rolelist").html(option);
            }, "json");
            if (result.status == "正常") {
                $('#status1').prop("selected", true);
            } else {
                $('#status2').prop("selected", true);
            }
            KindEditor.html("#editor_id", result.content);
            $('#formid').val(id);
        }, "json");

    }
    function addAdmin() {
        $('#forAdmin')[0].reset();
        $.post("${pageContext.request.contextPath}/admin/findRoles", function (data) {
            $("#rolelist").empty();
            data.forEach(function (role) {
                var option = "<input type='checkbox' name='roless' value='" + role.id + "'>" + "<span>" + role.name + "</span>" + "&nbsp;&nbsp;";
                $("#rolelist").append(option);
            });

        }, "json");
    }
    function sub() {
        var adminId = $('#formid').val();
        if (adminId == "") {
            $.ajax({
                url: "${pageContext.request.contextPath}/admin/save",
                datatype: "json",
                type: "post",
                data: $("#forAdmin").serialize(),
                success: function (data) {
                    $("#adminTable").trigger('reloadGrid');//刷新表格
                }
            });
        } else {
            $.ajax({
                url: "${pageContext.request.contextPath}/admin/update",
                datatype: "json",
                type: "post",
                data: $("#forAdmin").serialize(),
                success: function (data) {
                    $("#adminTable").trigger('reloadGrid');//刷新表格
                }
            });
        }
//
    }

</script>
<!--中心内容-->
<div id="content">
    <div class="col-md-10 column">
        <div>
            <h1>管理员管理</h1>
            <hr>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="tabbable" id="tabs-109847">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#panel-365123" data-toggle="tab">管理员列表</a>
                            </li>
                            <li>
                                <a href="#" onclick="addAdmin()" data-toggle="modal"
                                   data-target="#myAdminModal">添加管理员</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-365123">
                                <!--创建表格-->
                                <table id="adminTable"></table>

                                <!--分页工具栏-->
                                <div id="adminPage"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 管理员Modal -->
<div class="modal fade" id="myAdminModal" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content" style="width: 750px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">管理员信息</h4>
            </div>
            <form id="forAdmin" onsubmit="return false" method="post" class="form-horizontal">
                <input id="formid" type="hidden" name="id">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">用户名：</label>
                        <div class="col-sm-10">
                            <input type="text" name="username" style="width: 300px" class="form-control" id="username"
                                   placeholder="请输入用户名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pwd" class="col-sm-2 control-label">密码：</label>
                        <div class="col-sm-10">
                            <input type="password" name="password" style="width: 300px" class="form-control" id="pwd"
                                   placeholder="请输入密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="rolelist" class="col-sm-2 control-label">角色：</label>
                        <div id="rolelist">

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="statusList" class="col-sm-2 control-label">状态：</label>
                        <select style="width: 300px" class="form-control" id="statusList" name="status">
                            <option id="status1" value="正常">正常</option>
                            <option id="status2" value="冻结">冻结</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                    <button id="sub2" onclick="sub()" type="submit" class="btn btn-primary" data-dismiss="modal">提交
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
