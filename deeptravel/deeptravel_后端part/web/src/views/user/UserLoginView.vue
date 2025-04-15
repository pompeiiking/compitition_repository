<script>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import {useStore} from 'vuex'
import router from "@/router/index.js";



export default {
  methods: {
    router() {
      return router
    }
  },
  setup(){
    const store = useStore();
    let username = ref('');
    let password = ref('');
    let error_message = ref('');
    const jwt_token = localStorage.getItem("jwt_token");
    if (jwt_token) {
      store.commit('updateToken', jwt_token);
      store.dispatch('getinfo',{
        success(){
          router.push("/plangeneration");
        },
      })
    }
    const login = () => {
      error_message.value = "";
      store.dispatch('login',{
        username:username.value,
        password:password.value,
        success(){
          store.dispatch('getinfo',{
            success(){
              router.push("/plangeneration");
              ElMessage.success("登录成功");
              console.log(store.state.user);
            },
            error() {
              error_message.value = "用户名或密码错误";
            }
          })
        },
        error() {
          error_message.value = "用户名或密码错误";
        }
      })

    }
    return {
      username,
      password,
      error_message,
      login
    }
  }
}


// const form = ref({
//   username: '',
//   password: ''
// });

// const onSubmit = () => {
//   if (form.value.username === 'admin' && form.value.password === '123456') {
//     ElMessage.success('登录成功');
//     // 这里可以添加跳转到首页的逻辑
//   } else {
//     ElMessage.error('用户名或密码错误');
//   }
// };

  //   $.ajax({
  //     url:"http://127.0.0.1:8081/user/account/token/",
  //     type:"post",
  //     data:{
  //       username:"test",
  //       password:"123456"
  //     },
  //     success(resp){
  //       console.log(resp);
  //     },
  //     error(resp) {
  //       console.log(resp);
  //     }})
  //
  // $.ajax({
  //   url:"http://127.0.0.1:8081/user/account/info/",
  //   type:"get",
  //   headers:{
  //     Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0OTE4NjExYjJjOWU0NDI4YjE2OTAxZmFmMzFhNzliMiIsInN1YiI6IjUzIiwiaXNzIjoic2ciLCJpYXQiOjE3NDE0MjYwOTYsImV4cCI6MTc0MjYzNTY5Nn0.PD7QdjdKyganakMB7MpjWZTij99VLgWkS9zn1NJOv0c"
  //   },
  //   success(resp){
  //     console.log(resp);
  //   },
  //   error(resp) {
  //     console.log(resp);
  //   }})



</script>

<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="login-title">用户登录</h2>
      <el-form :model="form" label-width="80px" @submit.prevent="login">
        <el-form-item label="用户名">
          <el-input v-model="username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <div class="error-message">{{error_message}}</div>
        <el-form-item>
          <el-button type="primary" native-type="submit">登录</el-button>
          <el-button @click="router().push({name:'register'})">注册</el-button>>
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
