<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-0ab7df07318e43d280ba3eee38311c5b", //替换为您的应用appkey
    });
    goEasy.subscribe({
        channel: "cmfz", //替换为您自己的channel
        onMessage: function (message) {
            var myChart = echarts.init(document.getElementById('main'));
            // 手动将 字符串类型转换为 Json类型
            if(message.content = "success"){
                getecharts();
            }
        }
    });
</script>
<!--中心内容-->
<div id="content">
    <div class="col-md-10 column">
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="main" style="width: 600px;height:400px;"></div>
    </div>
</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '注册用户男女分布图'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["1天","7天","30天","1年"]
        },
        yAxis: {},
        series: [],
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    getecharts();
    // Ajax异步数据回显
    function getecharts() {
        var myChart = echarts.init(document.getElementById('main'));
        $.get("${pageContext.request.contextPath}/user/findPeopleNum",function (data) {
            myChart.setOption({
                series:[
                    {
                        name: '男',
                        type: 'bar',
                        data: data["man"],
                    },{
                        name: '女',
                        type: 'bar',
                        data: data["women"],
                    }
                ]
            })
        },"json")
    }

</script>
