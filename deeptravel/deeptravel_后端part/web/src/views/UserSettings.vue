<template>
    <div class="user-settings">
        <h2>用户设置</h2>
        <el-form label-position="top" label-width="100px" :model="form">
            <el-form-item label="性别">
                <el-radio-group v-model="form.gender">
                    <el-radio label="male">男</el-radio>
                    <el-radio label="female">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="年龄">
                <el-input-number v-model="form.age" :min="18" :max="100"></el-input-number>
            </el-form-item>
            <el-form-item label="收入情况">
                <el-select v-model="form.income" placeholder="请选择">
                    <el-option label="低" value="low"></el-option>
                    <el-option label="中" value="medium"></el-option>
                    <el-option label="高" value="high"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="性格">
                <el-input v-model="form.personality" placeholder="请输入性格描述"></el-input>
            </el-form-item>
            <el-form-item label="旅游偏好">
                <el-checkbox-group v-model="form.travelPreferences">
                    <el-checkbox label="自然风光"></el-checkbox>
                    <el-checkbox label="历史文化"></el-checkbox>
                    <el-checkbox label="美食体验"></el-checkbox>
                    <el-checkbox label="户外活动"></el-checkbox>
                    <el-checkbox label="城市探索"></el-checkbox>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item label="其他信息">
                <el-input type="textarea" v-model="form.otherInfo" placeholder="请输入其他对旅游推荐有帮助的信息"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveSettings">保存设置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            form: {
                gender: 'male',
                age: 25,
                income: 'medium',
                personality: '',
                travelPreferences: [],
                otherInfo: ''
            }
        };
    },
    methods: {
        saveSettings() {
            // 这里可以添加保存设置的逻辑，例如发送请求到后端
            console.log('保存的设置:', this.form);
        }
    }
};
</script>

<style scoped>
.user-settings {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
}

h2 {
    text-align: center;
    margin-bottom: 20px;
}
</style>