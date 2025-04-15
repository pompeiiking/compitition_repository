<template>
    <el-card>
        <!-- 大图标 -->
        <div class="chat-icon">
            <img :src="logo" alt="Logo" class="icon-image" />
        </div>
        <!-- 聊天框 -->
        <div class="chat-box" ref="chatBox">
            <div v-for="(msg, index) in messages" :key="index" class="message">
                <div v-if="msg.type === 'user'" class="user-message">
                    <img :src="avatar" alt="User" class="avatar" />
                    <div v-if="msg.content" class="message-content user-message-content">
                        {{ parseThinkContent(msg.content, index) }}
                    </div>
                    <img v-if="msg.image" :src="msg.image" class="message-image" />
                </div>
                <div v-if="msg.type === 'bot'" class="bot-message">
                    <img :src="avatar_bot" alt="Bot" class="avatar" />
                    <div v-if="msg.content" class="message-content bot-message-content">
                        {{ parseThinkContent(msg.content, index) }}
                    </div>
                    <img v-if="msg.image" :src="msg.image" class="message-image" />
                </div>
            </div>
        </div>

        <!-- 输入区域 -->
        <div class="input-area">
            <el-input v-model="input" placeholder="请输入您的问题..." @keyup.enter="sendMessage" class="input-field"
                :style="{ maxWidth: 'calc(100% - 200px)' }"></el-input>
            <el-button type="primary" @click="sendMessage">
                <el-icon><Top /></el-icon>
            </el-button>
            <el-button type="success" @click="startVoiceRecognition">
                <el-icon><Microphone /></el-icon>
            </el-button>
            <el-button type="info" @click="uploadImage">
                <el-icon><Picture /></el-icon>
            </el-button>
            <input type="file" ref="fileInput" accept="image/*" style="display: none;" @change="handleImageUpload" />
        </div>
    </el-card>
</template>

<script>
import { Top, Microphone, Picture } from '@element-plus/icons-vue'; // 引入图标组件

export default {
    components: {
        Top,
        Microphone,
        Picture
    },
    data() {
        return {
            input: '',
            messages: [],
            avatar: require('@/assets/user.png'),
            avatar_bot: require('@/assets/deepseek.png'),
            logo: require('@/assets/logo.png'), // 添加图标路径
            thinkStates: {} // 用于存储每个消息中 <think> 内容的显示状态
        };
    },
    methods: {
        async sendMessage() {
            console.log('消息:', this.input)
            if (this.input.trim() === '') return;

            this.messages.push({ type: 'user', content: this.input });
            const userInput = this.input;
            this.input = '';
            this.scrollToBottom();

            let botMessage = null;
            try {
                botMessage = { type: 'bot', content: '', loading: true };
                this.messages.push(botMessage);

                const response = await fetch(`http://localhost:8081/chat/c2/${encodeURIComponent(userInput)}`);
                if (!response.ok) throw new Error(`HTTP错误 ${response.status}`);

                const reader = response.body.getReader();
                const decoder = new TextDecoder('utf-8');

                let accumulatedContent = '';
                for (; ;) {
                    const { done, value } = await reader.read();
                    if (done) break;

                    const newContent = decoder.decode(value);
                    accumulatedContent += newContent;
                    const index = this.messages.length - 1;
                    this.messages[index] = {
                        ...botMessage,
                        content: accumulatedContent,
                        loading: false
                    };
                    this.scrollToBottom();
                }
            } catch (error) {
                console.error('请求失败：', error);
                if (botMessage) {
                    this.messages.splice(this.messages.length - 1, 1, {
                        ...botMessage,
                        content: `请求失败: ${error.message}`,
                        loading: false
                    });
                }
                this.scrollToBottom();
            }
        },
        scrollToBottom() {
            this.$nextTick(() => {
                this.$refs.chatBox.scrollTop = this.$refs.chatBox.scrollHeight;
            });
        },
        startVoiceRecognition() {
            if (!('SpeechRecognition' in window && 'webkitSpeechRecognition' in window)) {
                alert('您的浏览器不支持语音识别功能');
                return;
            }

            const recognition = new (window.SpeechRecognition || window.webkitSpeechRecognition)();
            recognition.continuous = false;
            recognition.interimResults = false;

            recognition.onresult = (event) => {
                const last = event.results.length - 1;
                const message = event.results[last][0].transcript;
                this.input = message;
                this.sendMessage();
            };

            recognition.onerror = (event) => {
                console.error('语音识别错误:', event.error);
            };

            recognition.start();
        },
        uploadImage() {
            this.$refs.fileInput.click();
        },
        handleImageUpload(event) {
            const file = event.target.files[0];
            if (!file) return;

            const reader = new FileReader();
            reader.onloadend = () => {
                const imageDataUrl = reader.result;
                this.messages.push({ type: 'user', image: imageDataUrl });
                this.scrollToBottom();
            };
            reader.readAsDataURL(file);
        },
        parseThinkContent(content, index) {
            const regex = /<think>(.*?)<\/think>/g;
            let match;
            let result = '';
            while ((match = regex.exec(content)) !== null) {
                const thinkContent = match[1];
                const thinkId = `${index}-${regex.lastIndex}`;
                this.thinkStates[thinkId] = this.thinkStates[thinkId] || false;

                result += content.slice(0, match.index);
                result += `<span>${thinkContent}</span>`;
                result += `<button @click="toggleThink(thinkId)" style="margin-left: 5px;">${this.thinkStates[thinkId] ? '收起' : '展开'}</button>`;
                content = content.slice(regex.lastIndex);
            }
            result += content;
            return result;
        },
        toggleThink(thinkId) {
            this.$set(this.thinkStates, thinkId, !this.thinkStates[thinkId]);
        }
    },
    mounted() {
        // 页面加载时发送欢迎消息
        this.messages.push({
            type: 'bot',
            content: '您好，欢迎来到基于DeepSeek-R1大模型的旅游推荐系统'
        });
        this.scrollToBottom();
    }
};
</script>


<style scoped>
.chat-icon {
  text-align: center;
  margin-bottom: 20px;
}

.icon-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}

.chat-box {
  height: calc(100vh - 220px); /* 动态计算高度，使聊天框占据剩余空间 */
  overflow-y: auto;
  padding: 10px;
  border-bottom: 1px solid #333; /* 使边框更暗 */
  background-color: #fff; /* 使背景更暗 */
  border-radius: 12px;
  color: #121212; /* 字体颜色改为白色 */
}

.input-area {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.message {
  margin: 10px 0;
  display: flex;
  align-items: center;
}

.user-message,
.bot-message {
  margin-top: 2px;
  display: flex;
  align-items: center;
  font-size: 10px;
  color: #121212; /* 字体颜色改为白色 */
}

.avatar {
  border-radius: 50%;
  margin-right: 10px;
  width: 50px;
  height: 50px;
}

.input-field {
  flex: 1;
  margin-right: 10px;
  max-width: calc(100% - 200px);
  background-color: #1e1e1e; /* 使背景更暗 */
  border: 1px solid #333; /* 使边框更暗 */
  color: #fff; /* 字体颜色改为白色 */
  border-radius: 8px;
}

.message-content {
  max-width: 100%;
  background-color: #1e1e1e; /* 使背景更暗 */
  border-radius: 12px;
  padding: 10px;
  overflow-wrap: break-word;
  white-space: normal;
  word-wrap: break-word;
  font-size: 16px;
  min-height: 40px;
  transition: height 0.3s ease;
  color: #fff; /* 字体颜色改为白色 */
}

.user-message-content {
  background-color: #2d2d2d; /* 使背景更暗 */
  color: #fff; /* 字体颜色改为白色 */
}

.bot-message-content {
  background-color: #1e1e1e; /* 使背景更暗 */
  height: auto;
  color: #fff; /* 字体颜色改为白色 */
}

.message-image {
  max-width: 200px;
  max-height: 200px;
  margin-left: 10px;
  border-radius: 8px;
}

.el-card {
  background-color: #F5F7F9;/* 使背景更暗 */
  border: 1px solid #333; /* 使边框更暗 */
  border-radius: 12px;
  color: #fff; /* 字体颜色改为白色 */
}

.el-button {
  border-radius: 50%; /* 将按钮改为圆形 */
  margin-left: 10px;
  color: #fff;
  width: 40px; /* 设置按钮宽度 */
  height: 40px; /* 设置按钮高度 */
  padding: 0; /* 去除内边距，使图标布满按钮 */
  display: flex;
  align-items: center;
  justify-content: center;
}

.el-button .el-icon {
  font-size: 20px; /* 调整图标大小 */
}
</style>
```