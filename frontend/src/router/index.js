import Vue from 'vue'
import VueRouter from 'vue-router'

import Signup from '../views/accounts/Signup.vue'
import Profile from '../views/accounts/profile.vue'
import ChangePassword from '../views/accounts/changePassword.vue'
import FindPassword from '../views/accounts/findPassword.vue'
import FeedMain from '../views/feed/IndexFeed.vue'
import ErrorPage from '../components/errorPage.vue'
import PageNotFound from '../components/PageNotFound.vue'
import FeedWrite from '../components/feed/FeedWrite.vue'
import Hospital from '../views/hospital/IndexHospital.vue'
import List from '../components/hospital/HospitalItem.vue'
import Map from "../components/hospital/HospitalMap.vue"

Vue.use(VueRouter)

  const routes = [
    {
        path : '/',
        name : 'FeedMain',
        component : FeedMain
    },
    {
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain
    },
    {
        path : '/feed/write',
        name : 'FeedWrite',
        component : FeedWrite
    },
    {
        path : '/accounts/Signup',
        name : 'Signup',
        component : Signup
    },
    {
        path : '/accounts/profile',
        name : 'Profile',
        component : Profile
    },
    {
        path : '/accounts/findPassword',
        name : 'FindPassword',
        component : FindPassword
    },
    {
        path : '/accounts/changePassword',
        name : 'ChangePassword',
        component : ChangePassword
    },
    {
        path : '/errorPage',
        name : 'ErrorPage',
        component : ErrorPage
    },
    {
        path : '/PageNotFound',
        name : 'PageNotFound',
        component : PageNotFound
    },
    {
        path : '/hospital',
        name : 'Hospital',
        component : Hospital,
        children:[
            {
              path: '/list',
              component: List,
            },
            {
                path: '/map',
                component: Map,
            }
          ]
    },


  ]

  const router = new VueRouter({
      mode: 'history',
      base: process.env.BASE_URL,
      routes
    })
    
  export default router