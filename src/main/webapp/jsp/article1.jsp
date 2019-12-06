<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<script type="text/javascript">
    $(function () {

        //表格初始化
        $("#acticleTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/article/showAllArticles",
                datatype : "json",
                colNames : [ 'ID','上师', '标题','封面','内容','状态','上传时间', '发布时间','操作'],
                colModel : [
                    {name : 'id',hidden:true},
                    {name : 'guruId',hidden:true},
                    {name : 'title',align:"center",editable:true,editrules:{required:true}},
                    {name : 'img',align:"center",formatter:function (data) {
                        return "<img style='width: 100%' src='"+data+"'/>"
                    },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}},
                    {name : 'content',hidden:true,search:false,align:"center",editable:true,editrules:{required:true}},
                    {name : 'status',align:"center",formatter:function (data) {
                        if (data=="1"){
                            return "展示";
                        }else  return "冻结";
                    },editable:true,edittype:"select",editoptions:{value:"1:展示;2:冻结"}},
                    {name : 'createDate',search:false,align:"center",editable:true,editoptions:{required:true}},
                    {name : 'publishDate',search:false,align:"center",edittype:"date",formatter:"date"},
                    {name : 'doing',search:false,
                        align:"center",editable:true,editoptions:{required:true},
                        formatter:function (cellvalue, options, rowObject) {
                            return '<a href="#" data-toggle="modal" data-target="#myModal" onclick=\'editRow("'+rowObject.id+'")\' class="btn btn-lg"> <span class="glyphicon glyphicon-th-list"></span></a>'
                        }
                    },

                ],
                rowNum : 10,
                height : 500,
                rowList : [ 10, 20, 30 ],
                pager : '#bannerPage',
                mtype : "post",
                viewrecords : true,
                styleUI:"Bootstrap",
                autowidth:true,
                multiselect:true,
                editurl:"${pageContext.request.contextPath}/article/change"
            });
        $("#acticleTable").jqGrid('navGrid', '#bannerPage',
            {edit : false, add : false, del : true,
                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            // edit add del
            {
            },{
                closeAfterAdd: true,
            },{
                closeAfterDel: true,
            },
            {
                sopt:['eq','ne','cn'],//配置搜索条件如何
                closeAfterSearch: true
            },//对搜索时的配置对象
        );
    });
    //上师信息回显
    function editRow(id) {
        $.post('${pageContext.request.contextPath}/article/findOne',{id:id},(result)=>{
            $('#title').val(result.title);
            $.post("${pageContext.request.contextPath}/guru/showGuruList",function (data) {
                $("#guruList").empty();
                var option = "<option value='0'>通用文章</option>";
                data.forEach(function (guru) {
                    if(result.guruId==guru.id){
                        option += "<option selected value='"+guru.id+"'>"+guru.nickName+"</option>"
                    }else{
                        option += "<option value='"+guru.id+"'>"+guru.nickName+"</option>"
                    }
                });
                $("#guruList").html(option);
            },"json");
            if(result.status==1){
                $('#opt1').attr("selected",true);
            }else{
                $('#opt2').attr("selected",true);
            }
            KindEditor.html("#editor_id",result.content);
            $('#formid').val(id);
        },"json");

    }
    function addArticle() {
        $('#for')[0].reset();
        KindEditor.html("#editor_id","");
        $.post("${pageContext.request.contextPath}/guru/showGuruList",function (data) {
            $("#guruList").empty();
            var option = "<option value='0'>通用文章</option>";
            data.forEach(function (guru) {
                option += "<option value='"+guru.id+"'>"+guru.nickName+"</option>"
            });
            $("#guruList").html(option);
        },"json");
    }
    function sub() {
//        alert($("#editor_id").val());
        $.ajaxFileUpload({
            url:"${pageContext.request.contextPath}/article/insertArticle",
            datatype:"json",
            type:"post",
            fileElementId:"inputfile",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
            data:{id:$("#formid").val(),status:$('#input4').val(),title:$("#title").val(),guruId:$("#guruList").val(),content:$("#editor_id").val()},
            success:function (data) {
                $("#acticleTable").trigger('reloadGrid');//刷新表格
            }
        });
    }
</script>
<!--中心内容-->
<div id="content">
    <div class="col-md-10 column">
        <div>
            <h1>文章管理</h1>
            <hr>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="tabbable" id="tabs-109847">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#panel-365123" data-toggle="tab">文章列表</a>
                            </li>
                            <li>
                                <a href="#" onclick="addArticle()" data-toggle="modal" data-target="#myModal" >添加文章</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-365123">
                                <!--创建表格-->
                                <table id="acticleTable"></table>

                                <!--分页工具栏-->
                                <div id="bannerPage"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
