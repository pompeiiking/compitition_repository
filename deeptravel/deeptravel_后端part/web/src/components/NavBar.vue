<template>
  <el-menu :default-active="activeIndex" class="el-menu custom-menu" mode="horizontal" @select="handleSelect">
    <el-menu-item index="1">
      <el-icon>
        <Promotion />
      </el-icon>
      计划助手
    </el-menu-item>
    <el-menu-item index="2">
      <el-icon>
        <Comment />
      </el-icon>
      社区反馈
    </el-menu-item>
    <el-menu-item index="4">
      <el-icon>
        <Picture />
      </el-icon>
      生成式打卡
    </el-menu-item>

    <el-menu-item index="3">
      <el-icon>
        <Tools />
      </el-icon>
      个人肖像
    </el-menu-item>
    <div class="right-container">
      <el-icon class="user-icon">
        <UserFilled />
      </el-icon>
    </div>
  </el-menu>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { Promotion, Comment, Tools, UserFilled, Picture} from '@element-plus/icons-vue';
import store from "../store/user"
import {ElMessage} from "element-plus";
const router = useRouter();

const activeIndex = ref('1'); // 默认激活第一个菜单项

const handleSelect = (key) => {
  console.log('Selected item:', key);

  if (!store.state.is_login) {
    ElMessage({
      message: '请先登录',
      type: 'warning'
    });
    return;
  }
  switch (key) {
    case '1':
      router.push('/plangeneration');
      break;
    case '2':
      router.push('/community');
      break;
    case '3':
      router.push('/usersettings');
      break;
    case '4':
      router.push('/picture');
      break;
    case '5':
      router.push('/audio');
      break;
  }
};
</script>


<style scoped>
.el-menu.custom-menu {
  background-color: #fff; /* 暗色背景 */
  border-bottom: 1px solid #444; /* 底部边框 */
}

.el-menu-item {
  color: #fff; /* 菜单项文字颜色 */
  font-size: 14px;
  transition: background-color 0.3s ease;
}

.el-menu-item:hover {
  background-color: #fff; /* 鼠标悬停时的背景色 */
}

.el-menu-item.is-active {
  background-color: #fff; /* 激活菜单项的背景色 */
  color: #121212; /* 激活菜单项的文字颜色 */
}

.el-icon {
  color: #121212; /* 图标颜色 */
}

.right-container {
  margin-left: auto;
  display: flex;
  align-items: center;
}

.user-icon {
  margin-left: 10px;
  color: #00ffff; /* 用户图标颜色 */
}
</style>