import Vue from 'vue'
// import App from './App.vue'
import LoginForm from "@/components/LoginForm";
import BootstrapVue from "bootstrap-vue";
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false
Vue.use(BootstrapVue)

new Vue({
  render: h => h(LoginForm),
}).$mount('#app')

