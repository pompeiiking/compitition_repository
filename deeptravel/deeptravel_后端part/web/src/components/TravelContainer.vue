<script setup>
import { ref } from "vue";
import { ElDialog, ElForm, ElFormItem, ElDatePicker, ElInput, ElMessage, ElCard, ElSelect, ElOption } from 'element-plus';

// 初始化表单对话框可见性
const dialogVisible = ref(false);
// 初始化表单数据
const form = ref({
  destination: '',
  startDate: '',
  duration: '',
  preferences: [],
  numberOfPeople: '', // 添加人数信息
  otherInfo: '' // 新增其他信息
});

// 初始化结果对话框可见性
const resultDialogVisible = ref(false);
// 初始化结果内容
const resultContent = ref('');
// 初始化偏好选项
const preferenceOptions = ref([
  { value: 'food', label: '美食探索' },
  { value: 'culture', label: '文化体验' },
  { value: 'nature', label: '自然风光' },
  { value: 'shopping', label: '购物中心' }
]);

// 提交表单处理函数
const handleSubmit = async () => {
  console.log('提交的表单数据:', form.value);
  dialogVisible.value = false; // 收回表单对话框

  const userInput = `"目的地"${form.value.destination}"出发时间"${form.value.startDate}"结束时间"${form.value.duration}"旅行偏好"${form.value.preferences}"其他信息"${form.value.otherInfo}`;

  try {
    resultContent.value = ''; // 清空之前的结果
    resultDialogVisible.value = true; // 打开结果对话框

    const response = await fetch(`http://localhost:8081/plan/p/${encodeURIComponent(userInput)}`);
    if (!response.ok) throw new Error(`HTTP错误 ${response.status}`);

    const reader = response.body.getReader();
    const decoder = new TextDecoder('utf-8');

    for (; ;) {
      const { done, value } = await reader.read();
      if (done) break;

      const newContent = decoder.decode(value);
      resultContent.value += newContent; // 流式更新结果内容
    }
  } catch (error) {
    console.error('请求失败：', error);
    resultContent.value = `请求失败: ${error.message}`; // 显示错误信息
  }
};

// 复制结果到剪贴板
const copyToClipboard = () => {
  navigator.clipboard.writeText(resultContent.value)
    .then(() => {
      ElMessage.success('计划已复制到剪贴板');
    })
    .catch((error) => {
      ElMessage.error('复制失败，请手动复制');
      console.error('复制失败:', error);
    });
};
</script>

<template>
  <div class="dashboard-container">
    <el-card class="main-card">
      <div class="header-section">
        <el-icon class="illustration" :size="200" color="#ffffff">
          <Location />
        </el-icon>
        <h1 class="title">智能旅行规划</h1>
      </div>
      <img src="../assets/image.png" alt="">
      <el-form class="smart-form" :model="form">
        <transition-group name="el-zoom-in-top">
          <el-form-item key="destination" label="📍 目的地">
            <el-input v-model="form.destination" placeholder="请输入城市/景点名称"  />
          </el-form-item>

          <el-form-item key="date" label="📅 出行日期">
            <el-date-picker v-model="form.startDate" type="daterange" range-separator="至" start-placeholder="开始日期"
              end-placeholder="结束日期"  />
          </el-form-item>

          <el-form-item key="preferences" label="❤️ 旅行偏好">
            <el-select v-model="form.preferences" multiple placeholder="选择偏好（可多选）">
              <el-option v-for="item in preferenceOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>

          <el-form-item key="numberOfPeople" label="👥 人数">
            <el-input v-model="form.numberOfPeople" placeholder="请输入人数"  type="number" />
          </el-form-item>

          <el-form-item key="otherInfo" label="📝 其他信息">
            <el-input v-model="form.otherInfo" placeholder="请输入其他信息"  />
          </el-form-item>
        </transition-group>

        <el-button type="primary" class="generate-btn" @click="handleSubmit">
          ✨ 生成智能计划
        </el-button>
      </el-form>
    </el-card>

    <!-- 历史计划侧边栏 -->
    <history-panel class="history-panel" @select="loadHistoryPlan" />

    <!-- 结果对话框（保留原有功能） -->
    <ElDialog v-model="resultDialogVisible" title="旅游计划" width="50%">
      <p>{{ resultContent }}</p>
      <template #footer>
        <el-button @click="copyToClipboard">复制计划</el-button>
      </template>
    </ElDialog>
  </div>
</template>

<style scoped>
.dashboard-container {
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: 2rem;
  padding: 2rem;
  height: 100vh;
  background: linear-gradient(135deg, #e6f4ff 0%, #b3e0ff 100%);
}

.main-card {
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.1);
  overflow: visible;
}

.header-section {
  text-align: center;
  padding: 2rem 0;
  background: linear-gradient(45deg, #0077b6, #00b4d8);
  border-radius: 20px 20px 0 0;
  margin: -20px -20px 2rem;
}

.illustration {
  width: 200px;
  height: auto;
  margin-bottom: 1rem;
}

.title {
  color: white;
  font-size: 2.5rem;
  text-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.smart-form {
  padding: 0 2rem;
}

.generate-btn {
  width: 100%;
  height: 60px;
  font-size: 1.3rem;
  letter-spacing: 1px;
  margin-top: 2rem;
  border-radius: 12px;
  transition: all 0.3s;
  background-color: #0077b6;
  border-color: #0077b6;
}

.enlarged-input {
  font-size: 1.1rem;
  height: 50px;
  width: 100%;
}

.history-panel {
  box-shadow: 5px 0 15px rgba(0,0,0,0.05);
  border-radius: 20px;
}

.el-zoom-in-top-enter-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>