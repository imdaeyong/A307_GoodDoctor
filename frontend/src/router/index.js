import Vue from 'vue'
import VueRouter from 'vue-router'

import Signup from '../views/accounts/Signup.vue'
import EmailCheck from '../views/accounts/emailCheck.vue'
import EmailSend from '../views/accounts/emailSend.vue'
import ChangePassword from '../views/accounts/changePassword.vue'
import FindPassword from '../views/accounts/findPassword.vue'
import SignupComplete from '../views/accounts/SignupComplete.vue'
import FeedMain from '../views/feed/IndexFeed.vue'
import ErrorPage from '../components/errorPage.vue'
import PageNotFound from '../components/PageNotFound.vue'
import GoogleLogin from '../components/accounts/snsLogin/Google.vue'

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
        path : '/accounts/Signup',
        name : 'Signup',
        component : Signup
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
        path : '/accounts/emailCheck',
        name : 'EmailCheck',
        component : EmailCheck
    },
    {
        path: '/accounts/emailSend',
        name : 'EmailSend',
        component : EmailSend
    },
    {
        path: '/accounts/SignupComplete',
        name : 'SignupComplete',
        component : SignupComplete
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
        path: '/account/google',
        name : 'GoogleLogin',
        component : GoogleLogin
    },
  ]

  const router = new VueRouter({
      mode: 'history',
      base: process.env.BASE_URL,
      routes
    })
    
  export default router