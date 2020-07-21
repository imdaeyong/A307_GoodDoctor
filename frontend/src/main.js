import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import routes from './routes'
import store from './vuex/store'
// bootstrap 등록
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
//session 등록
import VueSession from 'vue-session'

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
