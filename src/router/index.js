import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginForm from "@/components/login/LoginForm";
import JoinForm from "@/components/login/JoinForm";
import SearchAccountForm from "@/components/login/SearchAccountForm";
import SearchPasswordForm from "@/components/login/SearchPasswordForm";
import UserMainForm from "@/components/user/UserMainForm";
import UserMainProductForm from "@/components/user/UserMainProductForm";
import AdminMainForm from "@/components/admin/AdminMainForm";
import Cookies from 'js-cookie'
Vue.use(VueRouter)

const checkedToken = () => (to, from, next) => {
  let result = Cookies.get('token');
  if(result != null) {
    return next();
  }
  alert('로그인먼저해주세요')
  next('/');
};

const checkedAdmin = () => (to, from, next) => {
  let token = Cookies.get('token')
  let jwt = require('jsonwebtoken');
  const decoded = jwt.decode(token)
  const tokenUser = decoded.user;

  if (tokenUser === 'admin') {
    return next();
  }
  alert('관리자계정만 사용할 수 있습니다.');
  next('/userMain');
};

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
      component: UserMainForm,
      beforeEnter: checkedToken()
    }
    , {
      path: '/product',
      component: UserMainProductForm
    }
    , {
      path: '/adminMain',
      component: AdminMainForm,
      beforeEnter: checkedAdmin()
    },
  ]
})