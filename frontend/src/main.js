import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import routes from './routes'
import store from './vuex/store'
// bootstrap 등록
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
// 구글로그인 등록
import GAuth from 'vue-google-oauth2'
//session 등록
import VueSession from 'vue-session'

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.use(GAuth, {clientId: '368553140660-aod20dbfaf14omsnvvpbcr8mugkao6uf.apps.googleusercontent.com',
 scope: 'profile email https://www.googleapis.com/auth/plus.login'})


Vue.config.productionTip = false


Vue.use(VueRouter)

const router = new VueRouter({
    routes,
});
var sessionOptions = {
    persist: true
};
new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');


Vue.use(VueSession, sessionOptions);
