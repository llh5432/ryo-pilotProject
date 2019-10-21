import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginForm from "@/components/LoginForm";
import JoinForm from "@/components/JoinForm";
import SearchAccountForm from "@/components/SearchAccountForm";
import SearchPasswordForm from "@/components/SearchPasswordForm";
import MainSideForm from "@/components/MainSideForm";
import MainHeaderForm from "@/components/MainHeaderForm";
import MainBodyMenuForm from "@/components/MainBodyMenuForm";

Vue.use(VueRouter)
Vue.component('main-header',{
    MainHeaderForm
})
export const router = new VueRouter({
    routes:[
        {
            path:'/',
            component:LoginForm
        },
        {
            path:'/join',
            component:JoinForm
        },
        {
            path:'/searchAccount',
            component:SearchAccountForm
        },
        {
            path:'/searchPassword',
            component:SearchPasswordForm
        },
        {
            path:'/mainSide',
            component:MainSideForm
        },
        {
            path:'/mainHeader',
            component:MainHeaderForm
        }
        ,{
            path:'/mainBody',
            component:MainBodyMenuForm
        }
    ]
})