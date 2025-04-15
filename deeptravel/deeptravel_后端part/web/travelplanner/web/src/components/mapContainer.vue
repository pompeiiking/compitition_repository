<!-- ... existing code ... -->
<template>
  <div class="dashboard-container">
    <!-- 添加顶部插图 -->
    <img src="@/assets/travel-illustration.svg" alt="Travel Illustration" class="travel-illustration">

    <el-card class="main-card">
      <div class="header-section">
        <h1 class="title">智能旅行规划</h1>
      </div>

      <el-tabs v-model="activeTab" class="smart-tabs">
        <el-tab-pane label="目的地推荐" name="destinationRecommendation">
          <el-form class="smart-form" :model="form">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="旅客类型？">
                  <el-select v-model="form.travelerType" placeholder="请选择">
                    <el-option label="Any" value="any"></el-option>
                    <!-- 更多选项... -->
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="预算？">
                  <el-select v-model="form.budget" placeholder="请选择">
                    <el-option label="Any" value="any"></el-option>
                    <!-- 更多选项... -->
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="时间？">
                  <el-date-picker v-model="form.when" type="month" placeholder="选择月份"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="地区偏好？">
                  <el-select v-model="form.regionPreference" placeholder="请选择">
                    <el-option label="Any" value="any"></el-option>
                    <!-- 更多选项... -->
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="出发城市？">
                  <el-select v-model="form.departureCity" placeholder="请选择">
                    <el-option label="Melbourne - Australia" value="melbourne"></el-option>
                    <!-- 更多选项... -->
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="活动水平？">
                  <el-select v-model="form.activityLevel" placeholder="请选择">
                    <el-option label="Any" value="any"></el-option>
                    <!-- 更多选项... -->
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="你在寻找什么？（按优先顺序选择）">
              <el-checkbox-group v-model="form.preferences">
                <el-checkbox label="Beach" border>
                  <img src="@/assets/beach-icon.svg" alt="Beach" class="preference-icon">
                </el-checkbox>
                <el-checkbox label="Nature" border>
                  <img src="@/assets/nature-icon.svg" alt="Nature" class="preference-icon">
                </el-checkbox>
                <el-checkbox label="Nightlife" border>
                  <img src="@/assets/nightlife-icon.svg" alt="Nightlife" class="preference-icon">
                </el-checkbox>
                <el-checkbox label="Culture" border>
                  <img src="@/assets/culture-icon.svg" alt="Culture" class="preference-icon">
                </el-checkbox>
                <el-checkbox label="Fun Activities" border>
                  <img src="@/assets/fun-activities-icon.svg" alt="Fun Activities" class="preference-icon">
                </el-checkbox>
                <el-checkbox label="Shopping" border>
                  <img src="@/assets/shopping-icon.svg" alt="Shopping" class="preference-icon">
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="form.popularDestinations">感兴趣热门目的地</el-checkbox>
              <el-checkbox v-model="form.ecoFriendlyDestinations">生态友好目的地</el-checkbox>
            </el-form-item>
            <el-button type="primary" @click="handleSubmit">搜索</el-button>
          </el-form>
        </el-tab-pane>
        <!-- 其他标签页内容... -->
      </el-tabs>
    </el-card>
  </div>
</template>

<style scoped>
.dashboard-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
}

.travel-illustration {
  width: 100%;
  max-width: 800px;
  margin-bottom: 2rem;
}

.smart-tabs {
  width: 100%;
  max-width: 800px;
}

.smart-form {
  padding: 2rem;
}

.preference-icon {
  width: 30px;
  height: 30px;
  margin-right: 0.5rem;
}
</style>