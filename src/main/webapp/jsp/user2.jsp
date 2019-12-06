<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>用户c</title>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script>
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-0ab7df07318e43d280ba3eee38311c5b", //替换为您的应用appkey
        });
        goEasy.subscribe(
            {
                channel: "c", //替换为你自己的channel
                onMessage: function (message) {
                    console.log(message.content);
                }
            }
            );
        function sub() {
            var msg = $('#msg').val();
            goEasy.publish({
                channel: "cmfz", //替换为你发送用户的channel
                message: msg //替换为您想要发送的消息内容
            });
            $('#msg').val("");
        }
        function sub1() {
            var msg = $('#msg').val();
            goEasy.publish({
                channel: "a", //替换为你发送用户的channel
                message: msg //替换为您想要发送的消息内容
            });
            $('#msg').val("");
        }
    </script>
</head>
<body>
用户c: <input id="msg" type="text" class="form-control">
<button class="form-control" onclick="sub()">群发</button>
<button class="form-control" onclick="sub1()">向用户a发送</button>
</body>
</html>
