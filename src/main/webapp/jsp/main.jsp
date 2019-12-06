<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/back.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/jquery-ui.css">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script charset="UTF-8" src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/echarts/china.js" charset="UTF-8"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',{
                width:'600px',
                // 1. 指定图片上传路径
                uploadJson:"${pageContext.request.contextPath}/article/uploadImg",
                allowFileManager:true,
                fileManagerJson:"${pageContext.request.contextPath}/article/showAllImgs",
                afterBlur:function () {
                    this.sync();
                },
                afterCreate:function () {
                    this.sync();
                },
                afterChange:function () {
                    this.sync();
                }
            });
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row clearfix">
        <!--导航栏-->
        <nav class="navbar navbar-default" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> <a class="navbar-brand" href="#">持名法舟后台管理系统</a>
            </div>
            <div>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#">欢迎${sessionScope.admin}</a>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">退出登录<strong class="caret"></strong></a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <div class="row clearfix">
        <div class="col-md-2 column">
            <div class="panel-group" id="panel-494242" >
                <div class="panel panel-default">
                    <div class="panel-heading" style="height: 60px">
                        <a class="panel-title" data-toggle="collapse" data-parent="#panel-494242" href="#panel-element-294894"><h4>用户管理</h4></a>
                    </div>
                    <div id="panel-element-294894" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${pageContext.request.contextPath}/jsp/userlist.jsp')" class="list-group-item" style="border: 0;color: #0000FF">用户列表</a>
                            <a href="javascript:$('#content').load('${pageContext.request.contextPath}/jsp/echarts.jsp')" class="list-group-item" style="border: 0;color: #0000FF">注册趋势图</a>
                            <a href="javascript:$('#content').load('${pageContext.request.contextPath}/jsp/map.jsp')" class="list-group-item" style="border: 0;color: #0000FF">地理分布表</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-494242" href="#panel-element-372799">上师管理</a>
                    </div>
                    <div id="panel-element-372799" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="#" class="list-group-item" style="border: 0;color: #0000FF">上师列表</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-494242" href="#panel-element-400001">文章管理</a>
                    </div>
                    <div id="panel-element-400001" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${pageContext.request.contextPath}/jsp/article1.jsp')" class="list-group-item" style="border: 0;color: #0000FF">文章列表</a>
                            <a href="javascript:$('#content').load('${pageContext.request.contextPath}/jsp/article2.jsp')" class="list-group-item" style="border: 0;color: #0000FF">文章搜索</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-494242" href="#panel-element-400002">专辑管理</a>
                    </div>
                    <div id="panel-element-400002" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${pageContext.request.contextPath}/jsp/album.jsp')" class="list-group-item" style="border: 0;color: #0000FF">专辑列表</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-494242" href="#panel-element-400003">轮播图管理</a>
                    </div>
                    <div id="panel-element-400003" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${pageContext.request.contextPath}/jsp/banner.jsp')" class="list-group-item" style="border: 0;color: #0000FF">轮播图列表</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--中心内容-->
        <div id="content">
            <div class="col-md-10 column">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <div class="alert" style=" background-color: #EEEEEE">
                            <h2>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎使用持名法舟后台管理系统
                            </h2>
                        </div>
                    </div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <div class="carousel slide" id="carousel-251315">
                            <ol class="carousel-indicators">
                                <li class="active" data-slide-to="0" data-target="#carousel-251315">
                                </li>
                                <li data-slide-to="1" data-target="#carousel-251315">
                                </li>
                                <li data-slide-to="2" data-target="#carousel-251315">
                                </li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="item active">
                                    <img alt="" src="../img/3e4d03381f30e924eebbff0d40086e061d95f7b0.jpg" />
                                    <div class="carousel-caption">
                                        <h4>
                                            First Thumbnail label
                                        </h4>
                                        <p>
                                            Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
                                        </p>
                                    </div>
                                </div>
                                <div class="item">
                                    <img alt="" src="../img/009e9dfd5266d016d30938279a2bd40735fa3576.jpg" />
                                    <div class="carousel-caption">
                                        <h4>
                                            Second Thumbnail label
                                        </h4>
                                        <p>
                                            Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
                                        </p>
                                    </div>
                                </div>
                                <div class="item">
                                    <img alt="" src="../img/980186345982b2b7bcce9fcb3cadcbef76099b35.jpg" />
                                    <div class="carousel-caption">
                                        <h4>
                                            Third Thumbnail label
                                        </h4>
                                        <p>
                                            Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
                                        </p>
                                    </div>
                                </div>
                            </div> <a class="left carousel-control" href="#carousel-251315" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a> <a class="right carousel-control" href="#carousel-251315" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel-footer" style="height:50px;
                    width:100%;
                    clear:both;
                    margin-top:-50px;">
        <div class="panel-title">
            <h5 style="text-align: center">@百知教育 baizhi@zparkhr.com.cn</h5>
        </div>
    </div>
</div>

</body>
</html>
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content" style="width: 750px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" >文章信息</h4>
            </div>
            <form id="for" onsubmit="return false" method="post" class="form-horizontal">
                <input id="formid" type="hidden" name="id">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="title" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" style="width: 300px" class="form-control" id="title" placeholder="请输入标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputfile" class="col-sm-2 control-label">封面上传</label>
                        <input type="file" style="width: 300px" class="form-control" name="articleImg" id="inputfile">
                    </div>
                    <div class="form-group">
                        <label for="guruList" class="col-sm-2 control-label">所属上师</label>
                        <select style="width: 300px" class="form-control" id="guruList" name="guruId" >

                        </select>
                    </div>

                    <div class="form-group">
                        <label for="input4" class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-10">
                            <select name="status" style="width: 300px" class="form-control" id="input4">
                                <option id="opt1" value="1">展示</option>
                                <option id="opt2" value="2">冻结</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <label for="editor_id">内容</label>
                            <textarea id="editor_id" name="content" style="width:700px;height:300px;">
                            </textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                    <button id="sub2" onclick="sub()" type="submit" class="btn btn-primary" data-dismiss="modal">提交</button>
                </div>
            </form>
        </div>
    </div>
</div>