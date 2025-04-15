import { createRouter, createWebHistory } from 'vue-router'
import MyCommunity from '@/views/MyCommunity.vue'
import PlanGeneration from '@/views/PlanGeneration.vue'
import UserSettings from '@/views/UserSettings.vue'
import PictureView from '@/views/PictureView.vue'
import Audio from '@/views/AudioView.vue'
import LoginView from '../views/user/UserLoginView.vue'
import UserRegisterView from "@/views/user/UserRegisterView.vue";
import store from "../store/user"

const routes = [
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta:{
      requestAuth: false,
    }
  },
  {
    path:"/register",
    name:"register",
    component:UserRegisterView,
    meta:{
      requestAuth: false,
    }
  },
  {path:'/community', component:MyCommunity,meta:{requestAuth:true}},
  {path:'/plangeneration', component:PlanGeneration,meta:{requestAuth:true}},
  {path:'/usersettings', component:UserSettings,meta:{requestAuth:true}},
  { path: '/plangeneration', component: PlanGeneration ,meta:{requestAuth:true}},
  {path:'/picture',component:PictureView,meta:{requestAuth:true}},
  {path:'/audio',component:Audio,meta:{requestAuth:true}},
  {
    path: '/:pathMatch(.*)*',
    redirect: '/login'
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
router.beforeEach((to, from, next) => {
  if(to.meta.requestAuth && !store.state.is_login){
    next({name:"login"});
  }else{
    next();
  }
})
export default router