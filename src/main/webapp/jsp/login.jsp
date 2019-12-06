<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script>
        function login() {
            $.ajax({
                url:"${pageContext.request.contextPath}/admin/login",
                type:"POST",
                datatype:"JSON",
                data:$("#loginForm").serialize(),
                success:function (data) {
                    if (data!=null&data!=""){
                        $("#msg").html("<span style='color: red;' class='error'>"+data+"</span>")
                    }else {
                        location.href = "${pageContext.request.contextPath}/jsp/main.jsp";
                    }
                }

            })
        }
    </script>
</head>
<body style=" background: url(${pageContext.request.contextPath}/img/009e9dfd5266d016d30938279a2bd40735fa3576.jpg); background-size: 100%;">

<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" onsubmit="return false" >
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <div class="form-group">
                    验证码:
                    <img id="num" src="${pageContext.request.contextPath}/image/img" />
                    <a href="javascript:;" onclick="document.getElementById('num').src = '${pageContext.request.contextPath}/image/img?'+(new Date()).getTime()">换一张(处理点击连接和图片的验证码更换)</a>
                <input type="num" class="form-control" placeholder="验证码" autocomplete="off" name="num">
            </div>
            <span id="msg"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default form-control">注册</button>
            </div>

        </div>
        </form>
    </div>
</div>
</body>
</html>
