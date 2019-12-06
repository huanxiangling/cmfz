<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<script type="text/javascript">
    $(function () {
        <%--$('#sub1').click(function () {--%>
        <%--var formData = new FormData($("#for1")[0]);--%>
        <%--$.ajax({--%>
        <%--type: 'post',--%>
        <%--url: '${pageContext.request.contextPath}/banner/upload',--%>
        <%--dataType: 'json',--%>
        <%--data: formData,--%>
        <%--contentType: false,--%>
        <%--processData: false,--%>
        <%--success: function (data) {--%>
        <%--alert(11);--%>
        <%--window.location.reload();--%>
        <%--}--%>
        <%--});--%>
        <%--});--%>
        //表格初始化
        $("#bannerTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/banner/showAllBanners",
                datatype : "json",
                colNames : [ 'ID', '标题', '图片', '超链接', '创建时间','描述', '状态' ],
                colModel : [
                    {name : 'id',hidden:true},
                    {name : 'title',align:"center",editable:true,editrules:{required:true}},
                    {name : 'url',align:"center",formatter:function (data) {
                        return "<img style='width: 100%' src='"+data+"'/>"
                    },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}},
                    {name : 'href',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'createDate',align:"center",edittype:"date",formatter:"date"},
                    {name : 'desc',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'status',align:"center",formatter:function (data) {
                        if (data=="1"){
                            return "展示";
                        }else return "冻结";
                    },editable:true,edittype:"select",editoptions:{value:"1:展示;2:冻结"}}
                ],
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#bannerPage',
                mtype : "post",
                height : 500,
                viewrecords : true,
                styleUI:"Bootstrap",
                autowidth:true,
                multiselect:true,
                editurl:"${pageContext.request.contextPath}/banner/change"
            });
        $("#bannerTable").jqGrid('navGrid', '#bannerPage',
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
            },{
                closeAfterAdd: true,
                afterSubmit:function (response,postData) {
                    var bannerID = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/upload",
                        datatype:"json",
                        type:"post",
                        data:{bannerId:bannerID},
                        // 指定的上传input框的id
                        fileElementId:"url",
                        success:function (data) {
//                            alert('上传成功');
//                            // 输出上传成功
//                            // jqgrid重新载入
                            $("#bannerTable").trigger('reloadGrid');//刷新表格
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
            <h1>轮播图管理</h1>
            <hr>
            <div class="tabbable" id="tabs-109847">

                        <!--创建表格-->
                        <table id="bannerTable"></table>

                        <!--分页工具栏-->
                        <div id="bannerPage"></div>
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
                    <input type="file" name="ff">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                    <button id="sub1" type="submit" class="btn btn-primary" data-dismiss="modal">提交</button>
                </div>
            </form>
        </div>
    </div>
</div>
