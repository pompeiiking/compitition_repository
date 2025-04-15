
<script>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import $ from 'jquery';
// import {useStore} from 'vuex'
import router from "@/router/index.js";



export default {
  methods: {
    router() {
      return router
    }
  },
  setup(){
    // const store = useStore();
    let username = ref('');
    let password = ref('');
    let confirmedPassword = ref('');
    let gender = ref('');
    let incomeLevel = ref('');
    let travelModePreference = ref('');
    let activityTypePreference = ref('');
    let travelRestriction = ref('');
    let rests = ref('');
    let error_message = ref('');

    const register = () => {
      $.ajax({
        url: "http://127.0.0.1:8081/user/account/register/",
        type: "POST",
        data: {
          username: username.value,
          password: password.value,
          confirmedPassword: confirmedPassword.value,
          gender: gender.value,
          incomeLevel: incomeLevel.value,
          travelModePreference: travelModePreference.value,
          activityTypePreference: activityTypePreference.value,
          travelRestriction: travelRestriction.value,
          rests: rests.value
        },
        success(resp){
          if(resp.error_message == "success") {
            ElMessage.success("注册成功");
            router.push({name:"login"});
          } else {
            error_message.value = resp.error_message;
          }
        }
      });
    }

    return {
      username,
      password,
      confirmedPassword,
      gender,
      incomeLevel,
      travelModePreference,
      activityTypePreference,
      travelRestriction,
      rests,
      register
    }
  }

};



</script>

<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="login-title">用户注册</h2>
      <el-form :model="form" label-width="100px" @submit.prevent="register">
        <!-- 基础信息 -->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="username" placeholder="4-20位字母数字组合" clearable />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input v-model="password" type="password" placeholder="至少包含大小写字母和数字" show-password />
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmedPassword">
          <el-input v-model="confirmedPassword" type="password" placeholder="请再次输入密码" show-password />
        </el-form-item>

        <!-- 用户画像字段 -->
        <el-form-item label="性别">
          <el-radio-group v-model="gender">
            <el-radio label="male">男</el-radio>
            <el-radio label="female">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="收入水平">
          <el-select v-model="incomeLevel" placeholder="请选择">
            <el-option label="5k以下" value="low" />
            <el-option label="5k-1w" value="medium" />
            <el-option label="1w以上" value="high" />
          </el-select>
        </el-form-item>

        <el-form-item label="出行偏好">
          <el-select
              v-model="travelModePreference"
              multiple
              placeholder="请选择出行方式"    style="width: 100%"
          >
            <el-option label="自驾" value="drive" />
            <el-option label="公共交通" value="public" />
            <el-option label="骑行" value="bike" />
            <el-option label="步行" value="walk" />
          </el-select>
        </el-form-item>

        <el-form-item label="活动偏好">
          <el-input
              v-model="activityTypePreference"
              placeholder="例如：登山、露营、博物馆等"
              clearable
          />
        </el-form-item>

        <el-form-item label="旅行限制">
          <el-input
              v-model="travelRestriction"
              type="textarea"
              :rows="2"
              placeholder="例如：过敏源、身体限制等"
          />
        </el-form-item>

        <el-form-item label="其他需求">
          <el-input
              v-model="rests"
              type="textarea"
              :rows="2"
              placeholder="特殊需求说明"
          />
        </el-form-item>

        <!-- 错误提示 -->
        <div class="error-message">{{error_message}}</div>

        <!-- 操作按钮 -->
        <el-form-item>
          <el-button type="primary" native-type="submit" style="width: 120px">立即注册</el-button>
          <el-button @click="router().push({name:'login'})">返回登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>


<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-card {
  width: 400px;
  padding: 20px;
}

.login-title {
  text-align: center;
  margin-bottom: 20px;
}
div.error-message {
  color: red;
}
</style>



