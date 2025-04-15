
<template>
  <div class="dashboard-container">
    <el-card class="main-card">
      <div class="header-section">
        <el-icon class="illustration" :size="200" color="#ffffff">
          <Location />
        </el-icon>
        <h1 class="title">智能旅行规划</h1>
      </div>

      <el-form class="smart-form" :model="form">
        <transition-group name="el-zoom-in-top">
          <el-form-item key="destination" label="📍 目的地" style="margin-bottom: 20px;">
            <el-input 
              v-model="form.destination" 
              placeholder="请输入城市/景点名称"
              class="enlarged-input"
            />
          </el-form-item>

          <el-form-item key="date" label="📅 出行日期" style="margin-bottom: 20px;">
            <el-date-picker
              v-model="form.startDate"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              class="enlarged-input"
            />
          </el-form-item>

          <el-form-item key="peopleCount" label="👥 多少人出行" style="margin-bottom: 20px;">
            <el-input 
              v-model="form.peopleCount" 
              placeholder="请输入出行人数"
              class="enlarged-input"
            />
          </el-form-item>

          <el-form-item key="preferences" label="❤️ 旅行偏好" style="margin-bottom: 20px;">
            <el-checkbox-group v-model="form.preferences">
              <el-checkbox v-for="item in preferenceOptions" :key="item.value" :label="item.label" border></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </transition-group>

        <el-button 
          type="primary" 
          class="generate-btn"
          @click="handleSubmit"
        >
          ✨ 生成智能计划
        </el-button>
      </el-form>
    </el-card>

    <!-- 历史计划侧边栏 -->
    <history-panel class="history-panel" @select="loadHistoryPlan"/>
    
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
/* ... existing code ... */

.smart-form .el-form-item {
  margin-bottom: 20px;
}

.el-checkbox-group {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.el-checkbox {
  font-size: 1rem;
  height: 40px;
  line-height: 40px;
}

.generate-btn {
  margin-top: 30px;
}
</style>