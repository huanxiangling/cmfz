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
                var myChart = echarts.init(document.getElementById('userMap'));
                // 手动将 字符串类型转换为 Json类型
                if(message.content = "success"){
                    getMap();
                }
            }
        });
    </script>
    <script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('userMap'));
            var option = {
                title: {
                    text: '用户分布图',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['男', '女']
                },
                visualMap: {
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: '男',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        <%--data: "${pageContext.request.contextPath}/user/findMap",--%>
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            getMap();
            });
        function getMap() {
            var myChart = echarts.init(document.getElementById('userMap'));
            $.get("${pageContext.request.contextPath}/user/findMap",function (data) {
//                var result=JSON.parse(data);
                console.log(data)
                myChart.setOption({
                    series:[
                        {
                            data: data,
                        }
                    ]
                })
            },"json")
        }
    </script>
<!--中心内容-->
<div id="content">
    <div class="col-md-10 column">
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="userMap" style="width: 600px;height:400px;"></div>
    </div>
</div>
