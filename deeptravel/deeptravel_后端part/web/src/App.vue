<template>
  <div class="common-layout">
    <el-container style="height: 100vh;">
      <!-- 左侧边栏添加拖拽处理 -->
      <el-aside :style="{ width: asideWidth + 'px', position: 'relative' }" ref="aside">
        <div class="drag-handle" @mousedown="startDragging"></div>
        <ChatFrame />
      </el-aside>

      <el-container>
        <!-- 右侧内容保持不变 -->
        <el-header>
          <NavBar />
        </el-header>
        <el-main style="height: calc(100vh - 60px);">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue'
import ChatFrame from './components/chat/ChatFrame.vue'

export default {
  components: {
    NavBar,
    ChatFrame
  },
  data() {
    return {
      asideWidth: 400, // 初始宽度
      isDragging: false
    }
  },
  methods: {
    startDragging() {
      this.isDragging = true
      document.addEventListener('mousemove', this.handleDrag)
      document.addEventListener('mouseup', this.stopDragging)
      document.body.style.userSelect = 'none' // 防止选中文本
    },
    handleDrag(e) {
      if (!this.isDragging) return

      const containerRect = this.$refs.aside.$el.getBoundingClientRect()
      const newWidth = e.clientX - containerRect.left

      // 限制最小和最大宽度
      if (newWidth > 300 && newWidth < 800) {
        this.asideWidth = newWidth
      }
    },
    stopDragging() {
      this.isDragging = false
      document.removeEventListener('mousemove', this.handleDrag)
      document.removeEventListener('mouseup', this.stopDragging)
      document.body.style.userSelect = ''
    }
  },
  beforeUnmount() {
    // 组件销毁时移除监听器
    document.removeEventListener('mousemove', this.handleDrag)
    document.removeEventListener('mouseup', this.stopDragging)
  }
}
</script>

<style scoped>
.drag-handle {
  position: absolute;
  right: -3px;
  top: 0;
  bottom: 0;
  width: 6px;
  background-color: #ddd;
  cursor: col-resize;
  z-index: 1000;
  transition: background-color 0.2s;
}

.drag-handle:hover,
.drag-handle:active {
  background-color: #409EFF;
}

.el-aside {
  transition: width 0.2s ease;
  position: relative;
  overflow: visible !important;
}
</style>