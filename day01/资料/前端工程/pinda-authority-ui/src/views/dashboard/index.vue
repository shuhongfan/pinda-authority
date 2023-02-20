<template>
  <div class="dashboard-container">
    <el-row :gutter="20" style="margin-top:20px;margin-bottom:20px">
      <el-col :xs="24" :sm="12" :span="6">
        <el-card class="box-card" :body-style="{ padding: '5px 20px' , minHeight: '60px' }">
          <div style="float:left;margin-left:-10px">
            <img src="../../assets/wd.png" />
          </div>
          <div style="margin:15px;float:left;">
            <div style="color:#333333">昌平网点</div>
            <div style="color:#999999;font-size:14px;margin-top:5px">北京省/北京市/昌平区/金燕龙</div>
          </div>
          <div style="margin:10px;float:left">
            <div style="color:#3681FF;text-align:center">0</div>
            <div style="color:#999999;font-size:14px;margin-top:5px">下属机构</div>
          </div>
          <div style="margin:10px 15px;float:left;">
            <div style="color:#3681FF;text-align:center">120</div>
            <div style="color:#999999;font-size:14px;margin-top:5px">系统员工</div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :span="6">
        <el-card class="box-card" :body-style="{ padding: '5px 20px' , minHeight: '60px' }">
          <div style="float:left;margin-left:-10px;margin-top:10px">
            <img src="../../assets/dfpzy.png" />
          </div>
          <div style="margin:15px 10px;float:left;">
            <div style="color:#F04B30">65</div>
            <div style="color:#999999;font-size:14px;margin-top:5px">待分配作业范围</div>
          </div>
          <div style="float:left;margin-top:10px">
            <img src="../../assets/qjdb.png" />
          </div>
          <div style="margin:15px;float:left">
            <div style="color:#F04B30;text-align:center">3456</div>
            <div style="color:#999999;font-size:14px;margin-top:5px">取件待办</div>
          </div>
          <div style="float:left;margin-top:10px">
            <img src="../../assets/pjdb.png" />
          </div>
          <div style="margin:15px 20px;float:left;">
            <div style="color:#F04B30;text-align:center">120</div>
            <div style="color:#999999;font-size:14px;margin-top:5px">派件待办</div>
          </div>
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
            <div id="chart_example" />
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12">
        <el-card class="hots">
          <div slot="header" class="header">
            <span>订单分布</span>
          </div>
          <div>
            <div id="chart_example1" />
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- 订单总量 订单分布 /-->
    <!-- 作业信息 -->
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
    </el-card>

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
    const myChart = echarts.init(document.getElementById('chart_example'))
    const option = {
      color: ['#f44'],
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
          name: '每月花费',
          type: 'bar',
          barWidth: '60%',
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
      const myChart = echarts.init(document.getElementById('chart_example1'))
      window.onresize = myChart.resize
      myChart.setOption({
        // 进行相关配置
        backgroundColor: '#fff',
        tooltip: {}, // 鼠标移到图里面的浮动提示框
        dataRange: {
          show: false,
          min: 0,
          max: 1000,
          text: ['High', 'Low'],
          realtime: true,
          calculable: true,
          color: ['orangered', 'yellow', 'lightskyblue']
        },
        geo: {
          // 这个是重点配置区
          map: 'china', // 表示中国地图
          roam: true,
          label: {
            normal: {
              show: true, // 是否显示对应地名
              textStyle: {
                color: 'rgba(0,0,0,0.4)'
              }
            }
          },
          itemStyle: {
            normal: {
              borderColor: 'rgba(0, 0, 0, 0.2)'
            },
            emphasis: {
              areaColor: null,
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              shadowBlur: 20,
              borderWidth: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        series: [
          {
            type: 'scatter',
            coordinateSystem: 'geo' // 对应上方配置
          },
          {
            name: '启动次数', // 浮动框的标题
            type: 'map',
            geoIndex: 0,
            data: [
              {
                name: '北京',
                value: 599
              },
              {
                name: '上海',
                value: 142
              },
              {
                name: '黑龙江',
                value: 44
              },
              {
                name: '深圳',
                value: 92
              },
              {
                name: '湖北',
                value: 810
              },
              {
                name: '四川',
                value: 453
              }
            ]
          }
        ]
      })
    },
    aa() {
      const myChart1 = echarts.init(document.getElementById('chart_example2'))
      window.onresize = myChart1.resize
      myChart1.setOption({
        title: {
          left: 'center'
        },
        // legend: {
        //   left: 'center',
        //   top: 'bottom',
        //   data: [
        //     '待出库',
        //     '未出库',
        //     '已出库',
        //     '已出库1',
        //     '已出库2',
        //     '已出库3',
        //     '已出库4'
        //   ]
        // },

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
#chart_example {
  height: 337px;
  margin: 0 auto;
}
#chart_example1 {
  height: 337px;
  margin: 0 auto;
}
#chart_example2 {
  height: 300px;
  margin: 0 auto;
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
