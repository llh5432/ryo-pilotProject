import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginForm from "@/components/login/LoginForm";
import JoinForm from "@/components/login/JoinForm";
import SearchAccountForm from "@/components/login/SearchAccountForm";
import SearchPasswordForm from "@/components/login/SearchPasswordForm";
import UserMainForm from "@/components/user/UserMainForm";
import UserMainProductForm from "@/components/user/UserMainProductForm";
import AdminMainForm from "@/components/admin/AdminMainForm";

Vue.use(VueRouter)
Vue.component('user-main', {
  UserMainForm
})
export const router = new VueRouter({
  routes: [
    {
      path: '/',
      component: LoginForm
    },
    {
      path: '/join',
      component: JoinForm
    },
    {
      path: '/searchAccount',
      component: SearchAccountForm
    },
    {
      path: '/searchPassword',
      component: SearchPasswordForm
    },
    {
      path: '/userMain',
      component: UserMainForm
    }
    , {
      path: '/product',
      component: UserMainProductForm
    }
    , {
      path: '/adminMain',
      component: AdminMainForm
    },
  ]
})