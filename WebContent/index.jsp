<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/">

    <meta charset="UTF-8">
    <title>追溯链</title>

    <script src="resources/js/jquery-3.2.1.min.js"></script>
    <script src="resources/js/echarts.js"></script>
    <script>
        /**
         *  ajax封装函数
         * @param {type} url 请求的路径
         * @param {type} successFun 请求成功时的回调函数
         * @param {type} reqType 请求的数据类型
         * @param {type} reqData 请求的数据
         * @param {type} async 是否异步
         * @returns {undefined}
         */
        function getSomethingByAjax(url, successFun, async, reqType, reqData) {
            reqType = reqType || "GET";
            reqData = reqData || {};
            async = async && true;
            $.ajax({
                url: url,
                type: reqType,
                data: reqData,
                async: async,
                success: successFun,
                error: function (req, status, error) {
                    console.log(error);
                }
            });
        }

    </script>
</head>

<body>
<div id="main" style="width: 1536px;height: 900px;"></div>

</body>
</html>
<script>
    var url = "getRelationship";
    getSomethingByAjax(url, parseData);

    var dataArr = new Array();
    var linksArr = new Array();
    var dataSet_infections = new Set();
    var dataSet_no_infections = new Set();

    function parseData(data) {
        data.forEach(function (value) {
            if (value.flag == 0) {
                dataSet_infections.add(value.originMac);
                dataSet_infections.add(value.targetMac);
                linksArr.push({
                    source: value.originMac,//源节点
                    target: value.targetMac,//目标节点
                    name: '感染',//关系
                    des: ''
                })
            } else {
                dataSet_infections.add(value.originMac);
                //dataSet_no_infections.add(value.originMac);
                dataSet_no_infections.add(value.targetMac);
                linksArr.push({
                    source: value.originMac,//源节点
                    target: value.targetMac,//目标节点
                    name: '接触',//关系
                    des: ''
                })
            }
        });

        for (var x of dataSet_infections) { // 遍历Map
            dataArr.push({
                name: x,
                des: x,
                symbolSize: 70,//节点大小
                category: 0,//设置节点所属类别
            });

        }
        for (var x of dataSet_no_infections) {
            if (dataSet_infections.size > 0) {
                if (!dataSet_infections.has(x)) {
                    dataArr.push({
                        name: x,
                        des: x,
                        symbolSize: 70,//节点大小
                        category: 1,//设置节点所属类别
                    });
                }
            } else {
                dataArr.push({
                    name: x,
                    des: x,
                    symbolSize: 70,//节点大小
                    category: 1,//设置节点所属类别
                });
            }
        }
        myChart.setOption(option);
    }

    var myChart = echarts.init(document.getElementById('main'));
    var categories = [{name: "感染"}, {name: "未感染"}];
    var timmerHandle = null;
    var isClick = false;
    var isDrag = false;
    myChart.on("click", function (param) {
        setTimeout(function () {
            if (isClick && param.dataType == "node") {
                //console.log(param.data.des)
                var url = "gotoRelationshipDetail/" + param.data.des
                window.open(url, '_blank')
                //console.log("点击了节点");
            }
        }, 500);
    });
    myChart.on("mouseup", function (param) {
        if (!isDrag) {
            //先把doMouseDownTimmer清除，不然200毫秒后setGragTrue方法还是会被调用的
            clearTimeout(timmerHandle);
            console.log("mouse up.");
            isClick = true;
        } else {
            isDrag = false;
            console.log("draging over.");
            isClick = false;
        }
    });

    option = {
        // 图的标题
        title: {
            text: '病毒传播链'
        },
        // 提示框的配置
        tooltip: {
            formatter: function (x) {
                return x.data.des;
            }
        },
        // 工具箱
        toolbox: {
            // 显示工具箱
            show: true,
            feature: {
                mark: {
                    show: true
                },
                // 还原
                restore: {
                    show: true
                },
                // 保存为图片
                saveAsImage: {
                    show: true
                }
            }
        },
        legend: [{
            // selectedMode: 'single',
            //设置可以根据类别显示or隐藏节点
            data: categories.map(function (a) {
                return a.name;
            })
        }],
        series: [{
            type: 'graph', // 类型:关系图
            layout: 'force', //图的布局，类型为力导图
            symbolSize: 40, // 调整节点的大小
            roam: true, // 是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移,可以设置成 'scale' 或者 'move'。设置成 true 为都开启
            edgeSymbol: ['circle', 'arrow'],
            edgeSymbolSize: [2, 10],
            edgeLabel: {
                normal: {
                    textStyle: {
                        fontSize: 20
                    }
                }
            },
            force: {
                repulsion: 2500,
                edgeLength: [10, 50]
            },
            draggable: true,
            lineStyle: {
                normal: {
                    width: 2,
                    color: '#4b565b',
                }
            },
            edgeLabel: {
                normal: {
                    show: true,
                    formatter: function (x) {
                        return x.data.name;
                    }
                }
            },
            label: {
                normal: {
                    show: true,
                    textStyle: {}
                }
            },

            // 数据
            data: dataArr,

            //     [{
            //     name: '刘备',
            //     des: '刘备',
            //     symbolSize: 70,//节点大小
            //     category: 0,//设置节点所属类别
            // }, {
            //     name: '刘备1',
            //     des: '刘备1',
            //     symbolSize: 70,//节点大小
            //     category: 0,//设置节点所属类别
            // }, {
            //     name: '刘备2',
            //     des: '刘备2',
            //     symbolSize: 70,//节点大小
            //     category: 0,//设置节点所属类别
            // }, {
            //     name: '关羽',
            //     des: '关羽',
            //     symbolSize: 70,//节点大小
            //     category: 0,//设置节点所属类别
            // }],//...后续数据省略
            links: linksArr,
            //     [{
            //     source: '关羽',//源节点
            //     target: '刘备',//目标节点
            //     name: '感染',//关系
            //     des: ''
            // }], //定义关系，后续省略
            categories: categories,//给类别赋值
        }]
    };
</script>