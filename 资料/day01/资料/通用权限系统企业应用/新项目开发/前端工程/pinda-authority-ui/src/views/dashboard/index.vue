<template>
  <div class="dashboard-container">
    <el-row :gutter="24" style="margin-top:20px;margin-bottom:20px" justify="center">
      <el-col :span="12">
        <el-card class="box-card" :body-style="{ padding: '5px 20px' , minHeight: '60px' }">
          <el-row :span="24" type="flex" class="row-bg" justify="space-around">
            <el-col :span="23">
              <div style="float:left;margin-left:-10px">
                <img src="../../assets/wd.png" />
              </div>
              <div style="margin:15px;float:left;">
                <div style="color:#333333">昌平网点</div>
                <div style="color:#999999;font-size:14px;margin-top:5px">北京省/北京市/昌平区/金燕龙</div>
              </div>
            </el-col>
            <el-col :span="7">
              <div style="margin:10px;float:left">
                <div style="color:#3681FF;text-align:center">0</div>
                <div style="color:#999999;font-size:14px;margin-top:5px">下属机构</div>
              </div>
            </el-col>
            <el-col :span="7">
              <div style="margin:10px 15px;float:left;">
                <div style="color:#3681FF;text-align:center">120</div>
                <div style="color:#999999;font-size:14px;margin-top:5px">系统员工</div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card" :body-style="{ padding: '5px 20px' , minHeight: '60px' }">
          <el-row type="flex" class="row-bg" justify="space-around">
            <el-col :span="11">
              <div style="float:left;margin-left:5px;margin-top:10px">
                <img src="../../assets/dfpzy.png" />
              </div>
              <div style="margin:15px 10px;float:left;">
                <div style="color:#F04B30">65</div>
                <div style="color:#999999;font-size:14px;margin-top:5px">待分配作业范围</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div style="float:left;margin-top:10px">
                <img src="../../assets/qjdb.png" />
              </div>
              <div style="margin:15px;float:left">
                <div style="color:#F04B30;text-align:center">3456</div>
                <div style="color:#999999;font-size:14px;margin-top:5px">取件待办</div>
              </div>
            </el-col>
            <el-col :span="9">
              <div style="float:left;margin:10px 15px">
                <img src="../../assets/pjdb.png" />
              </div>
              <div style="margin:15px 10px;float:left;">
                <div style="color:#F04B30;text-align:center">120</div>
                <div style="color:#999999;font-size:14px;margin-top:5px">派件待办</div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <!-- 订单总量  订单分布-->
    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :xs="24" :sm="12">
        <el-card class="hots">
          <div slot="header" class="header">
            <span>订单总量</span>
          </div>
          <div>
            <div id="order-total-chart" />
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12">
        <el-card class="hots">
          <div slot="header" class="header">
            <span>订单分布</span>
          </div>
          <div>
            <div id="order-fenbu-chart" />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 作业信息 -->
    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :xs="24" :sm="24">
        <el-card class="hots">
          <div slot="header" class="header">
            <span>作业信息</span>
          </div>
          <div>
            <el-row :gutter="20">
              <el-col :span="3">
                <el-card class="box-card" style="width:120px;margin:20px 1px;float: left;">
                  <div>
                    <label style="color: #F04B30;">300</label>
                    <br />取件任务
                  </div>
                  <div style="margin-top:20px;">
                    <label style="color: #F04B30;">300</label>
                    <br />仓库任务
                  </div>
                  <div style="margin-top:20px;">
                    <label style="color: #F04B30;">300</label>
                    <br />派件任务
                  </div>
                </el-card>
              </el-col>
              <el-col :span="3">
                <el-card
                  class="box-card"
                  style="width:120px;margin:20px 5px;float: left;"
                  shadow="hover"
                >
                  <div>
                    <label style="color: #F04B30;">300</label>
                    <br />取件任务
                  </div>
                  <div style="margin-top:20px;">
                    <label style="color: #F04B30;">300</label>
                    <br />仓库任务
                  </div>
                  <div style="margin-top:20px;">
                    <label style="color: #F04B30;">300</label>
                    <br />派件任务
                  </div>
                </el-card>
              </el-col>
              <el-col :span="3">
                <el-card
                  class="box-card"
                  style="width:120px;margin:20px 5px;float: left;"
                  shadow="hover"
                >
                  <div>
                    <label style="color: #F04B30;">300</label>
                    <br />取件任务
                  </div>
                  <div style="margin-top:20px;">
                    <label style="color: #F04B30;">300</label>
                    <br />仓库任务
                  </div>
                  <div style="margin-top:20px;">
                    <label style="color: #F04B30;">300</label>
                    <br />派件任务
                  </div>
                </el-card>
              </el-col>
              <el-col :span="3">
                <el-card
                  class="box-card"
                  style="width:120px;margin:20px 5px;float: left;"
                  shadow="hover"
                >
                  <div>
                    <label style="color: #F04B30;">300</label>
                    <br />取件任务待派遣
                  </div>
                  <div style="margin-top:20px;">
                    <label style="color: #F04B30;">300</label>
                    <br />仓库任务
                  </div>
                  <div style="margin-top:20px;">
                    <label style="color: #F04B30;">300</label>
                    <br />派件任务
                  </div>
                </el-card>
              </el-col>
              <el-col :span="9">
                <div id="chart_example2" />
              </el-col>
              <el-col :span="3">
                <el-card class="box-card" style="width:120px;margin-top:40px">
                  <div>
                    <label style="color: #F04B30;">120,899</label>
                    <br />取件任务
                  </div>
                  <div style="margin-top:20px;">
                    <label style="color: #F04B30;">248</label>
                    <br />派件任务
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- 作业信息
    <el-card class="hots">
      <div slot="header" class="header">
        <span>作业信息</span>
      </div>
      <el-card class="box-card" style="width:120px;margin:20px 1px;float: left;">
        <div>
          <label style="color: #F04B30;">300</label>
          <br />取件任务
        </div>
        <div style="margin-top:20px;">
          <label style="color: #F04B30;">300</label>
          <br />仓库任务
        </div>
        <div style="margin-top:20px;">
          <label style="color: #F04B30;">300</label>
          <br />派件任务
        </div>
      </el-card>
      <el-card class="box-card" style="width:120px;margin:20px 5px;float: left;" shadow="hover">
        <div>
          <label style="color: #F04B30;">300</label>
          <br />取件任务待派遣
        </div>
        <div style="margin-top:20px;">
          <label style="color: #F04B30;">300</label>
          <br />仓库任务
        </div>
        <div style="margin-top:20px;">
          <label style="color: #F04B30;">300</label>
          <br />派件任务
        </div>
      </el-card>
      <el-card class="box-card" style="width:120px;margin:20px 5px;float: left;" shadow="hover">
        <div>
          <label style="color: #F04B30;">300</label>
          <br />取件任务
        </div>
        <div style="margin-top:20px;">
          <label style="color: #F04B30;">300</label>
          <br />仓库任务
        </div>
        <div style="margin-top:20px;">
          <label style="color: #F04B30;">300</label>
          <br />派件任务
        </div>
      </el-card>
      <el-card class="box-card" style="width:120px;margin:20px 5px;float: left;" shadow="hover">
        <div>
          <label style="color: #F04B30;">300</label>
          <br />取件任务
        </div>
        <div style="margin-top:20px;">
          <label style="color: #F04B30;">300</label>
          <br />仓库任务
        </div>
        <div style="margin-top:20px;">
          <label style="color: #F04B30;">300</label>
          <br />派件任务
        </div>
      </el-card>

      <div style="width:420px;margin:20px 5px;float: left;">
        <div id="chart_example2" />
      </div>
    </el-card>-->

    <!-- 作业信息 /-->
  </div>
</template>
<script>
import echarts from 'echarts'
import 'echarts/map/js/china.js'
export default {
  name: 'Dashboard',
  data() {
    return {}
  },
  computed: {},
  mounted: function() {
    this.chinaConfigure()
    this.aa()
    const myChart = echarts.init(document.getElementById('order-total-chart'))
    const option = {
      color: ['#FD827D'],
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      xAxis: [
        {
          type: 'category',
          data: [
            '1月',
            '2月',
            '3月',
            '4月',
            '5月',
            '6月',
            '7月',
            '8月',
            '9月',
            '10月',
            '11月',
            '12月'
          ],
          axisTick: {
            alignWithLabel: true
          }
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: '订单总量',
          type: 'bar',
          itemStyle: {
            //柱形图圆角，鼠标移上去效果，如果只是一个数字则说明四个参数全部设置为那么多
            emphasis: {
              barBorderRadius: 30
            },
            normal: {
              //柱形图圆角，初始化效果
              barBorderRadius: [10, 10, 10, 10]
            }
          },
          barWidth: '30%',
          data: [995, 666, 444, 858, 654, 236, 645, 546, 846, 225, 547, 356]
        }
      ]
    }
    myChart.setOption(option)
    window.addEventListener('resize', function() {
      myChart.resize()
    })
  },
  created() {},
  methods: {
    chinaConfigure() {
      const myChart = echarts.init(document.getElementById('order-fenbu-chart'))
      window.onresize = myChart.resize
      var name_title = '品达物流集团-订单数量分布'
      var subname = ''
      var nameColor = ' rgb(55, 75, 113)'
      var name_fontFamily = '等线'
      var subname_fontSize = 15
      var name_fontSize = 18
      var mapName = 'china'
      var data = [
        { name: '北京', value: 177 },
        { name: '天津', value: 42 },
        { name: '河北', value: 102 },
        { name: '山西', value: 81 },
        { name: '内蒙古', value: 47 },
        { name: '辽宁', value: 67 },
        { name: '吉林', value: 82 },
        { name: '黑龙江', value: 66 },
        { name: '上海', value: 24 },
        { name: '江苏', value: 92 },
        { name: '浙江', value: 114 },
        { name: '安徽', value: 109 },
        { name: '福建', value: 116 },
        { name: '江西', value: 91 },
        { name: '山东', value: 119 },
        { name: '河南', value: 137 },
        { name: '湖北', value: 116 },
        { name: '湖南', value: 114 },
        { name: '重庆', value: 91 },
        { name: '四川', value: 125 },
        { name: '贵州', value: 62 },
        { name: '云南', value: 83 },
        { name: '西藏', value: 9 },
        { name: '陕西', value: 80 },
        { name: '甘肃', value: 56 },
        { name: '青海', value: 10 },
        { name: '宁夏', value: 18 },
        { name: '新疆', value: 67 },
        { name: '广东', value: 123 },
        { name: '广西', value: 59 },
        { name: '海南', value: 14 }
      ]
      var geoCoordMap = {}
      var toolTipData = [
        {
          name: '北京',
          value: [
            { name: '订单数量', value: 95 },
            { name: '货品数量', value: 82 }
          ]
        },
        {
          name: '天津',
          value: [
            { name: '订单数量', value: 22 },
            { name: '货品数量', value: 20 }
          ]
        },
        {
          name: '河北',
          value: [
            { name: '订单数量', value: 60 },
            { name: '货品数量', value: 42 }
          ]
        },
        {
          name: '山西',
          value: [
            { name: '订单数量', value: 40 },
            { name: '货品数量', value: 41 }
          ]
        },
        {
          name: '内蒙古',
          value: [
            { name: '订单数量', value: 23 },
            { name: '货品数量', value: 24 }
          ]
        },
        {
          name: '辽宁',
          value: [
            { name: '订单数量', value: 39 },
            { name: '货品数量', value: 28 }
          ]
        },
        {
          name: '吉林',
          value: [
            { name: '订单数量', value: 41 },
            { name: '货品数量', value: 41 }
          ]
        },
        {
          name: '黑龙江',
          value: [
            { name: '订单数量', value: 35 },
            { name: '货品数量', value: 31 }
          ]
        },
        {
          name: '上海',
          value: [
            { name: '订单数量', value: 12 },
            { name: '货品数量', value: 12 }
          ]
        },
        {
          name: '江苏',
          value: [
            { name: '订单数量', value: 47 },
            { name: '货品数量', value: 45 }
          ]
        },
        {
          name: '浙江',
          value: [
            { name: '订单数量', value: 57 },
            { name: '货品数量', value: 57 }
          ]
        },
        {
          name: '安徽',
          value: [
            { name: '订单数量', value: 57 },
            { name: '货品数量', value: 52 }
          ]
        },
        {
          name: '福建',
          value: [
            { name: '订单数量', value: 59 },
            { name: '货品数量', value: 57 }
          ]
        },
        {
          name: '江西',
          value: [
            { name: '订单数量', value: 49 },
            { name: '货品数量', value: 42 }
          ]
        },
        {
          name: '山东',
          value: [
            { name: '订单数量', value: 67 },
            { name: '货品数量', value: 52 }
          ]
        },
        {
          name: '河南',
          value: [
            { name: '订单数量', value: 69 },
            { name: '货品数量', value: 68 }
          ]
        },
        {
          name: '湖北',
          value: [
            { name: '订单数量', value: 60 },
            { name: '货品数量', value: 56 }
          ]
        },
        {
          name: '湖南',
          value: [
            { name: '订单数量', value: 62 },
            { name: '货品数量', value: 52 }
          ]
        },
        {
          name: '重庆',
          value: [
            { name: '订单数量', value: 47 },
            { name: '货品数量', value: 44 }
          ]
        },
        {
          name: '四川',
          value: [
            { name: '订单数量', value: 65 },
            { name: '货品数量', value: 60 }
          ]
        },
        {
          name: '贵州',
          value: [
            { name: '订单数量', value: 32 },
            { name: '货品数量', value: 30 }
          ]
        },
        {
          name: '云南',
          value: [
            { name: '订单数量', value: 42 },
            { name: '货品数量', value: 41 }
          ]
        },
        {
          name: '西藏',
          value: [
            { name: '订单数量', value: 5 },
            { name: '货品数量', value: 4 }
          ]
        },
        {
          name: '陕西',
          value: [
            { name: '订单数量', value: 38 },
            { name: '货品数量', value: 42 }
          ]
        },
        {
          name: '甘肃',
          value: [
            { name: '订单数量', value: 28 },
            { name: '货品数量', value: 28 }
          ]
        },
        {
          name: '青海',
          value: [
            { name: '订单数量', value: 5 },
            { name: '货品数量', value: 5 }
          ]
        },
        {
          name: '宁夏',
          value: [
            { name: '订单数量', value: 10 },
            { name: '货品数量', value: 8 }
          ]
        },
        {
          name: '新疆',
          value: [
            { name: '订单数量', value: 36 },
            { name: '货品数量', value: 31 }
          ]
        },
        {
          name: '广东',
          value: [
            { name: '订单数量', value: 63 },
            { name: '货品数量', value: 60 }
          ]
        },
        {
          name: '广西',
          value: [
            { name: '订单数量', value: 29 },
            { name: '货品数量', value: 30 }
          ]
        },
        {
          name: '海南',
          value: [
            { name: '订单数量', value: 8 },
            { name: '货品数量', value: 6 }
          ]
        }
      ]

      /*获取地图数据*/
      myChart.showLoading()
      var mapFeatures = echarts.getMap(mapName).geoJson.features
      myChart.hideLoading()
      mapFeatures.forEach(function(v) {
        // 地区名称
        var name = v.properties.name
        // 地区经纬度
        geoCoordMap[name] = v.properties.cp
      })
      var max = 480,
        min = 9 // todo
      var maxSize4Pin = 100,
        minSize4Pin = 20

      var convertData = function(data) {
        var res = []
        for (var i = 0; i < data.length; i++) {
          var geoCoord = geoCoordMap[data[i].name]
          if (geoCoord) {
            res.push({
              name: data[i].name,
              value: geoCoord.concat(data[i].value)
            })
          }
        }
        return res
      }
      const option = {
        title: {
          text: name_title,
          subtext: subname,
          x: 'center',
          textStyle: {
            color: nameColor,
            fontFamily: name_fontFamily,
            fontSize: name_fontSize
          },
          subtextStyle: {
            fontSize: subname_fontSize,
            fontFamily: name_fontFamily
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: function(params) {
            if (typeof params.value[2] == 'undefined') {
              var toolTiphtml = ''
              for (var i = 0; i < toolTipData.length; i++) {
                if (params.name == toolTipData[i].name) {
                  toolTiphtml += toolTipData[i].name + ':<br>'
                  for (var j = 0; j < toolTipData[i].value.length; j++) {
                    toolTiphtml +=
                      toolTipData[i].value[j].name +
                      ':' +
                      toolTipData[i].value[j].value +
                      '<br>'
                  }
                }
              }
              console.log(toolTiphtml)
              // console.log(convertData(data))
              return toolTiphtml
            }
          }
        },
        geo: {
          show: true,
          map: mapName,
          label: {
            normal: {
              show: false
            },
            emphasis: {
              show: false
            }
          },
          roam: true,
          itemStyle: {
            normal: {
              areaColor: '#ffffff',
              borderColor: '#3B5077'
            },
            emphasis: {
              areaColor: '#fff'
            }
          }
        },
        series: [
          {
            name: '散点',
            type: 'scatter',
            coordinateSystem: 'geo',
            data: convertData(data),
            symbolSize: function(val) {
              return val[2] / 10
            },
            label: {
              normal: {
                formatter: '{b}',
                position: 'right',
                show: true
              },
              emphasis: {
                show: true
              }
            },
            itemStyle: {
              normal: {
                color: '#05C3F9'
              }
            }
          },
          {
            type: 'map',
            map: mapName,
            geoIndex: 0,
            aspectScale: 0.75, //长宽比
            showLegendSymbol: false, // 存在legend时显示
            label: {
              normal: {
                show: true
              },
              emphasis: {
                show: false,
                textStyle: {
                  color: '#fff'
                }
              }
            },
            roam: true,
            itemStyle: {
              normal: {
                areaColor: '#ffffff',
                borderColor: '#3B5077'
              },
              emphasis: {
                areaColor: '#ffffff'
              }
            },
            animation: false,
            data: data
          },
          {
            name: '点',
            type: 'scatter',
            coordinateSystem: 'geo',
            symbol: 'pin', //气泡
            symbolSize: function(val) {
              var a = (maxSize4Pin - minSize4Pin) / (max - min)
              var b = minSize4Pin - a * min
              b = maxSize4Pin - a * max
              return a * val[2] + b
            },
            label: {
              normal: {
                show: true,
                textStyle: {
                  color: '#fff',
                  fontSize: 9
                }
              }
            },
            itemStyle: {
              normal: {
                color: '#F62157' //标志颜色
              }
            },
            zlevel: 6,
            data: convertData(data)
          }
        ]
      }

      myChart.setOption(option)
    },
    aa() {
      const myChart1 = echarts.init(document.getElementById('chart_example2'))
      window.onresize = myChart1.resize
      myChart1.setOption({
        title: {
          left: 'center'
        },

        series: [
          {
            name: '半径模式',
            type: 'pie',
            radius: [20, 110],
            center: ['60%', '40%'],
            roseType: 'radius',
            label: {
              show: false
            },
            emphasis: {
              label: {
                show: true
              }
            },
            data: [
              { value: 7, name: '待出库' },
              { value: 6, name: '未出库' },
              { value: 5, name: '已出库' },
              { value: 4, name: '已出库1' },
              { value: 3, name: '已出库2' },
              { value: 2, name: '已出库3' },
              { value: 1, name: '已出库4' }
            ]
          }
        ]
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-container {
  margin: 10px;
}
#order-total-chart {
  height: 337px;
  margin: 0 auto;
}
#order-fenbu-chart {
  height: 337px;
  margin: 0 auto;
}
#chart_example2 {
  height: 300px;
  margin-top: 15px;
}
.box-card {
  padding: 5px 10px;
  .header {
    height: 25px;
    span {
      color: #97a8be;
      font-size: 14px;
    }
    .item {
      color: #97a8be;
      float: right;
      padding: 3px 0;
    }
  }
  // 总销售额
  .total {
    font-size: 30px;
    height: 140px;
    line-height: 100px;
  }
  .trends {
    height: 22px;
    font-size: 14px;
    span {
      display: inline;
      i {
        color: red;
      }
    }
    span:last-child {
      margin-left: 20px;
      display: inline;
      i {
        color: green;
      }
    }
  }
  .chart {
    height: 160px;
  }
  .hr {
    border-top: 1px solid #e8e8e8;
    margin: 0px 0px 10px 0px;
  }
  .footer {
    span {
      font-size: 14px;
      line-height: 22px;
    }
    span:last-child {
      margin-left: 8px;
      color: rgba(0, 0, 0, 0.85);
    }
  }
  // 总销售额 end
}
// 销售额
.sales-card {
  position: relative;
  .header {
    position: absolute;
    right: 20px;
    top: 15px;
    z-index: 1;
  }
  .chart {
    widows: 100%;
    height: 300px;
  }
  .table {
    color: rgba(0, 0, 0, 0.65);
    h4 {
      color: #000;
      font-weight: 500;
    }
    ul {
      list-style: none;
      margin: 0px;
      padding: 0px;
      .row {
        margin-bottom: 10px;
      }
    }
    .circular {
      width: 20px;
      height: 20px;
      background-color: #314659;
      color: #fff;
      text-align: center;
      font-size: 12px;
      line-height: 20px;
      font-weight: 600;
      border-radius: 50%;
    }
    .light {
      background-color: #f5f5f5;
      color: rgba(0, 0, 0, 0.65);
    }
  }
}

// 线上热门搜索
.hots {
  height: 400px;
  position: relative;
  .header {
    color: #97a8be;
    .ops {
      position: absolute;
      right: 20px;
    }
  }
  .pagination {
    position: absolute;
    right: 12px;
    bottom: 5px;
  }
  .chart {
    height: 300px;
  }
}

// 周搜索
.month-card {
  height: 400px;
  .chart {
    height: 300px;
  }
}
</style>
