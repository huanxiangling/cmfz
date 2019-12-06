<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<script type="text/javascript">
    $(function () {
        <%--$('#sub1').click(function () {--%>
            <%--$.ajaxFileUpload({--%>
                <%--url:"${pageContext.request.contextPath}/banner/inputPOI",--%>
                <%--datatype:"json",--%>
                <%--type:"post",--%>
                <%--fileElementId:"inputfile",--%>
                <%--success:function (data) {--%>
                    <%--$("#userTable").trigger('reloadGrid');//刷新表格--%>
                <%--}--%>
            <%--});--%>
        <%--});--%>
        //表格初始化
        $("#userTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/user/showAllBanners",
                datatype : "json",
                colNames : [ 'ID', '手机号', '头像', '昵称', '性别','个性签名', '地址','创建时间','最后登录时间','状态' ],
                colModel : [
                    {name : 'id',hidden:true},
                    {name : 'phone',align:"center",editable:true,editrules:{required:true}},
                    {name : 'photo',search:false,align:"center",formatter:function (data) {
                        return "<img style='width: 100%' src='"+data+"'/>"
                    },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}},
                    {name : 'nickName',search:false,align:"center",editable:true,editoptions:{required:true}},
                    {name : 'sex',align:"center",formatter:function (data) {
                            if (data=="1"){
                                return "男";
                            }else return "女";
                        },editable:true,edittype:"select",editoptions:{value:"1:男;2:女"}
                    },
                    {name : 'sign',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'location',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'rigestDate',search:false,align:"center",edittype:"date",formatter:"date"},
                    {name : 'lastLogin',search:false,align:"center",edittype:"date",formatter:"date"},
                    {name : 'status',align:"center",formatter:function (data) {
                        if (data=="1"){
                            return "展示";
                        }else return "冻结";
                    },editable:true,edittype:"select",editoptions:{value:"1:展示;2:冻结"}}
                ],
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#userPage',
                mtype : "post",
                viewrecords : true,
                height : 500,
                styleUI:"Bootstrap",
                autowidth:true,
                multiselect:true,
                editurl:"${pageContext.request.contextPath}/user/change"
            });
        $("#userTable").jqGrid('navGrid', '#userPage',
            {edit : true, add : true, del : true,
                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            // edit add del
            {
                closeAfterEdit: true,
                // frm ---> 表单对象
                beforeShowForm:function (frm) {
                    frm.find('#url').attr("disabled",true)
                },
                afterSubmit:function (response,postData) {
                    var userID = response.responseJSON.userId;
                    console.log(response.responseJSON);
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/upload",
                        datatype:"json",
                        type:"post",
                        data:{userId:userID},
                        // 指定的上传input框的id
                        fileElementId:"photo",
                        success:function (data) {
//                            alert('上传成功');
//                            // 输出上传成功
//                            // jqgrid重新载入
                            $("#userTable").trigger('reloadGrid');//刷新表格
                        }
                    })
                    return postData;
                }
            },{
                closeAfterAdd: true,
                afterSubmit:function (response,postData) {
                    var userID = response.responseJSON.userId;
                    console.log(userID);
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/upload",
                        datatype:"json",
                        type:"post",
                        data:{userId:userID},
                        // 指定的上传input框的id
                        fileElementId:"photo",
                        success:function (data) {
//                            alert('上传成功');
//                            // 输出上传成功
//                            // jqgrid重新载入
                            $("#userTable").trigger('reloadGrid');//刷新表格
                        }
                    })
                    return postData;
                }
            },{
                closeAfterDel: true,
            },
            {
                sopt:['eq','ne','cn']//配置搜索条件如何
            },//对搜索时的配置对象
        );
    });
</script>
<!--中心内容-->
<div id="content">
    <div class="col-md-10 column">
            <div>
                <h1>用户列表</h1>
                <hr>
                <!--创建表格-->
            <table id="userTable"></table>

            <!--分页工具栏-->
            <div id="userPage">
            </div>

            </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">导入轮播图数据</h4>
            </div>
            <form id="for1" onsubmit="return false" method="post" enctype="multipart/form-data">
                <div class="modal-body">
                        <input type="file" name="ff" id="inputfile">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                    <button id="sub1" type="submit" class="btn btn-primary" data-dismiss="modal">提交</button>
                </div>
            </form>
        </div>
    </div>
</div>
