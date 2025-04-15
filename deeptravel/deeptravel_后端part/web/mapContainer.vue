<script setup>
import { onMounted, onUnmounted } from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";

let map = null;

const provinces = [
  "北京市", "天津市", "河北省", "山西省", "内蒙古自治区", "辽宁省", "吉林省", "黑龙江省",
  "上海市", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省", "河南省", "湖北省",
  "湖南省", "广东省", "广西壮族自治区", "海南省", "重庆市", "四川省", "贵州省", "云南省",
  "西藏自治区", "陕西省", "甘肃省", "青海省", "宁夏回族自治区", "新疆维吾尔自治区", "台湾省",
  "香港特别行政区", "澳门特别行政区"
];

const provinceColors = {
  "北京市": "#FFCCCC",
  "天津市": "#CCFFCC",
  "河北省": "#CCCCFF",
  "山西省": "#FFCC99",
  "内蒙古自治区": "#99CCFF",
  "辽宁省": "#FF99CC",
  "吉林省": "#CCFF99",
  "黑龙江省": "#99FFCC",
  "上海市": "#FF9999",
  "江苏省": "#99FF99",
  "浙江省": "#9999FF",
  "安徽省": "#FF9966",
  "福建省": "#66FF99",
  "江西省": "#9966FF",
  "山东省": "#FF6666",
  "河南省": "#66FF66",
  "湖北省": "#6666FF",
  "湖南省": "#FF6633",
  "广东省": "#33FF66",
  "广西壮族自治区": "#6633FF",
  "海南省": "#FF3333",
  "重庆市": "#33FF33",
  "四川省": "#3333FF",
  "贵州省": "#FF3300",
  "云南省": "#00FF33",
  "西藏自治区": "#3300FF",
  "陕西省": "#FF0000",
  "甘肃省": "#00FF00",
  "青海省": "#0000FF",
  "宁夏回族自治区": "#FF00FF",
  "新疆维吾尔自治区": "#00FFFF",
  "台湾省": "#FFFF00",
  "香港特别行政区": "#FF00FF",
  "澳门特别行政区": "#00FFFF"
};

onMounted(() => {
  window._AMapSecurityConfig = {
    securityJsCode: "9bba3153901547f511861fb51b2cc403",
  };
  AMapLoader.load({
    key: "7d424e7f23dbe9481bb7d3b0179e655a",
    version: "2.0",
    plugins: ["AMap.Scale", "AMap.DistrictLayer"],
  })
    .then((AMap) => {
      map = new AMap.Map("container", {
        viewMode: "3D",
        zoom: 4,
        center: [104.195397, 35.86166],
      });

      const districtLayer = new AMap.DistrictLayer.Country({
        zIndex: 10,
        SOC: 'CHN',
        depth: 2,
        styles: {
          'nation-stroke': '#22ffff',
          'coastline-stroke': [0.85, 0.63, 0.94, 1],
          'province-stroke': 'white',
          'city-stroke': 'rgba(255,255,255,0.5)',
          'fill': function(properties) {
            const provinceName = properties.name;
            return provinceColors[provinceName] || '#A6D8D4';
          }
        }
      });

      map.add(districtLayer);

      map.on('click', function(e) {
        console.log('点击的经纬度:', e.lnglat);
      });
    })
    .catch((e) => {
      console.log(e);
    });
});

onUnmounted(() => {
  map?.destroy();
});

const handleProvinceClick = (province) => {
  console.log('点击的省份:', province);
};
</script>

<template>
  <div id="container"></div>
  <div class="province-buttons">
    <el-button
      v-for="province in provinces"
      :key="province"
      @click="handleProvinceClick(province)"
      class="province-button"
    >
      {{ province }}
    </el-button>
  </div>
</template>

<style scoped>
#container {
  width: 100%;
  height: 800px;
}

.province-buttons {
  display: flex;
  flex-wrap: wrap;
  margin-top: 10px;
}

.province-button {
  margin: 5px;
}
</style>