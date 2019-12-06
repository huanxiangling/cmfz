<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<script type="text/javascript">
        $(function () {
            //表格初始化
            $("#table").jqGrid(
                {
                    url : "${pageContext.request.contextPath}/album/findByCurrentPage",
                    datatype : "json",
                    colNames : [ 'ID', '标题', '封面','评分', '作者', '播音','集数', '简介','状态','出版日期' ],
                    colModel : [
                        {name : 'id',width : 55,hidden : true,align:"center"},
                        {name : 'title',editable:true,editrules:{required:true},align:"center"},
                        {name : 'cover',index : 'invdate',width : 90,align:"center",
                            formatter:function (data) {
                                return "<img style='width: 100%' src='"+data+"'/>"
                            },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}
                        },
                        {name : 'score',editable:true,align:"center"},
                        {name : 'author',editable:true,align:"center"},
                        {name : 'broadcast',editable:true,align:"center"},
                        {name : 'count',align:"center"},
                        {name : 'desc',editable:true,align:"center"},
                        {name : 'status',editable:true,align:"center",
                            formatter:function (data) {
                                if (data=="1"){
                                    return "正常";
                                }else return "损坏";
                            }
                        },
                        {name : 'createDate',editable:true,align:"center",edittype:"date",formatter:"date",formatoptions: {newformat:'Y-m-d'}}
                    ],
                    rowNum : 10,
                    rowList : [ 10, 20, 30 ],
                    pager : '#page',
                    sortname : 'id',
                    height : 500,
                    viewrecords : true,
                    sortorder : "desc",
                    multiselect : true,
                    subGrid : true,
                    editurl :"${pageContext.request.contextPath}/album/change",
                    caption : "专辑章节表",
                    autowidth:true,
                    styleUI:"Bootstrap",
                    subGridRowExpanded : function(subgrid_id, row_id) {
                        addTable(subgrid_id,row_id);
                    },
                    subGridRowColapsed : function(subgrid_id, row_id) {
                    }
                });
            $("#table").jqGrid('navGrid', '#page',
                {edit : true, add : true, del : true,
                    edittext:"编辑",addtext:"添加",deltext:"删除"
                },
                // edit add del
                {
                    closeAfterEdit: true,
                    // frm ---> 表单对象
                    beforeShowForm:function (frm) {
                        frm.find('#cover').attr("disabled",true),
                        frm.find('#createDate').attr("disabled",true)
                    },
                },{
                    closeAfterAdd: true,
                    afterSubmit:function (response,postData) {
                        var albumID = response.responseJSON.albumId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/album/upload",
                            datatype:"json",
                            type:"post",
                            data:{albumId:albumID},
                            // 指定的上传input框的id
                            fileElementId:"cover",
                            success:function (data) {
//                            alert('上传成功');
//                            // 输出上传成功
//                            // jqgrid重新载入
                                $("#table").trigger('reloadGrid');//刷新表格
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
        function addTable (subgrid_id,row_id) {
            var subgridTable = subgrid_id + "table";
            var subgridPage = subgrid_id + "page";
            $("#" + subgrid_id).html(
                "<table id='" + subgridTable+ "' class='scroll'></table>"
                +"<div id='"+ subgridPage + "' class='scroll'></div>");
            $("#" + subgridTable).jqGrid(
                {
                    url : "${pageContext.request.contextPath}/chapter/findByCurrentPage?albumId="+row_id,
                    datatype : "json",
                    colNames : [ 'ID', '标题','大小','时长',"上传时间",'音频',"操作"],
                    colModel : [
                        {name : "id",key : true,hidden : true,align:"center"},
                        {name : "title",editable:true,align:"center"},
                        {name : "size",search:false,align:"center",
//                            formatter:function (data) {
//                                return data+"MB"
//                            }
                        },
                        {name : "time",search:false,align:"center"},
                        {name : "createTime",search:false,editable:true,align:"center",edittype:"date",formatter:"date"},
                        {name : 'url',search:false,align:"center",
                            editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}
                        },
                        {name : "doing",align:"center",search:false,
                            formatter:function (cellvalue, options, rowObject) {
                                return '<a href="javascript:void(0)" onclick=\'player("' + rowObject.url + '")\'  class="btn btn-lg" title="播放"><span class="glyphicon glyphicon-play-circle"></span></a>'
                                        +'<a href="javascript:void(0)" onclick=\'down("'+rowObject.url+'")\' class="btn btn-lg" title="下载"><span class="glyphicon glyphicon-download"></span></a>'
                            }
                        }
                    ],
                    rowNum : 20,
                    pager : "#"+subgridPage,
                    sortname : 'num',
                    sortorder : "asc",
                    height : '100%',
                    styleUI:"Bootstrap",
                    editurl:"${pageContext.request.contextPath}/chapter/change?albumId="+row_id,
                    autowidth:true
                });
            $("#" + subgridTable).jqGrid('navGrid',
                "#" + subgridPage,
                {edit : true, add : true, del : true,
                    edittext:"编辑",addtext:"添加",deltext:"删除"
                },
                // edit add del
                {
                    closeAfterEdit: true,
                    // frm ---> 表单对象
                    beforeShowForm:function (frm) {
                        frm.find('#createTime').attr("disabled",true),
                        frm.find('#url').attr("disabled",true)
                    },
                },{
                    closeAfterAdd: true,
                    afterSubmit:function (response,postData) {
                        var chapterID = response.responseJSON.chapterId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chapter/upload",
                            datatype:"json",
                            type:"post",
                            data:{albumId:row_id,chapterId:chapterID},
                            // 指定的上传input框的id
                            fileElementId:"url",
                            success:function (data) {
//                            alert('上传成功');
//                            // 输出上传成功
//                            // jqgrid重新载入
                                $('#'+subgridTable).trigger('reloadGrid');//刷新表格
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
        }
        function player(a) {
            $('#MModel').modal("show"),
            $('#myaudio').attr("src",a).attr("autoplay",true)
            <%--var id = a;--%>
            <%--$.post("${pageContext.request.contextPath}/chapter/player",{chapterId:id});--%>
        }
        function down(a) {
            location.href = "${pageContext.request.contextPath}/chapter/download?url="+a;
            //var url = a;
            //$.post("${pageContext.request.contextPath}/chapter/download",{url:url});
        }
</script>
<!--中心内容-->
<div id="content">
    <div class="col-md-10 column">
            <div>
                <h1>专辑章节管理</h1>
                <div class="modal fade" id="MModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <audio src="" id="myaudio" controls></audio>
                </div>
                <hr>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <div class="tabbable" id="tabs-109847">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#panel-365123" data-toggle="tab">专辑章节信息</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="panel-365123">
                                        <!--创建表格-->
                                    <table id="table"></table>

                                    <!--分页工具栏-->
                                    <div id="page"></div>
                                </div>
                            </div>
                        </div>
                    </div>
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
