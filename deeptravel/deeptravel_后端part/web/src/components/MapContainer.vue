<script setup>
import { onMounted, ref } from "vue";
import * as echarts from "echarts";
import china from "@/assets/China.json"; // 修改为与磁盘文件名一致的大小写
import { ElDialog, ElForm, ElFormItem, ElDatePicker, ElInput } from 'element-plus';

const mapData = china;
const dialogVisible = ref(false);
const form = ref({
  departure: '',
  startDate: '',
  duration: ''
});

onMounted(() => {
  const chartDom = document.getElementById('map-chart');
  const myChart = echarts.init(chartDom);

  echarts.registerMap('china', mapData);

  const option = {
    backgroundColor: "#0E2152",
    geo: {
      map: "china",
      label: {
        normal: {
          show: true,
          textStyle: {
            color: "#fff",
          },
        },
        emphasis: {
          textStyle: {
            color: "#fff",
          },
        },
      },
      itemStyle: {
        normal: {
          borderColor: "#5089EC",
          borderWidth: 1,
          areaColor: {
            type: "radial",
            x: 0.5,
            y: 0.5,
            r: 0.8,
            colorStops: [
              {
                offset: 0,
                color: "rgba(0, 102, 154, 0)",
              },
              {
                offset: 1,
                color: "rgba(0, 102, 154, .4)",
              },
            ],
          },
        },
        emphasis: {
          areaColor: "#2386AD",
          borderWidth: 0,
        },
      },
    },
    series: [
      {
        type: "map",
        map: "china",
        roam: true,
        label: {
          show: true,
        },
        emphasis: {
          label: {
            show: true,
          },
        },
      },
    ],
  };

  myChart.setOption(option);

  myChart.on('click', function (params) {
    console.log('点击的省份:', params.name);
    form.value.departure = params.name; // 默认出发地为点击的省份
    dialogVisible.value = true;
  });
});

const handleSubmit = () => {
  console.log('提交的表单数据:', form.value);
  dialogVisible.value = false;
};
</script>

<template>
  <div id="map-chart" style="width: 100%; height: 800px;"></div>
  <ElDialog v-model="dialogVisible" title="旅游信息" width="30%">
    <ElForm :model="form" label-width="80px">
      <ElFormItem label="出发地">
        <ElInput v-model="form.departure" />
      </ElFormItem>
      <ElFormItem label="出发时间">
        <ElDatePicker v-model="form.startDate" type="date" placeholder="选择出发时间" />
      </ElFormItem>
      <ElFormItem label="旅游时间">
        <ElInput v-model="form.duration" placeholder="请输入旅游时间（天）" />
      </ElFormItem>
    </ElForm>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleSubmit">确定</el-button>
    </template>
  </ElDialog>
</template>

<style scoped>
#map-chart {
    width: 100%;
    height: 800px;
    border-radius: 12px;
    overflow: hidden;
}

.province-buttons {
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
}

.province-button {
    margin: 5px;
    border-radius: 8px;
}

.el-dialog {
    background-color: #1e1e1e;
    border: 1px solid #444;
    border-radius: 12px;
    color: #fff;
}

.el-form-item__label {
    color: #fff;
}

.el-input {
    background-color: #2d2d2d;
    border: 1px solid #444;
    color: #fff;
    border-radius: 8px;
}

.el-date-picker {
    background-color: #2d2d2d;
    border: 1px solid #444;
    color: #fff;
    border-radius: 8px;
}
</style>