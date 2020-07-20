

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import ChangePassword from './views/user/changePassword.vue'
import ErrorPage from './components/errorPage.vue'
import PageNotFound from './components/PageNotFound.vue'
import EmailCheck from './views/user/emailCheck.vue'
import EmailSend from './views/user/emailSend.vue'
import JoinComplete from './views/user/JoinComplete.vue'

export default [


    {
        path : '/',
        name : 'Login',
        component : Login
    },
    {
        path : '/user/join',
        name : 'Join',
        component : Join
    },
    {
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain
    },
    {
        path : '/components',
        name : 'Components',
        component : Components
    },
    {
        path : '/changePassword',
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
        path : '/emailCheck',
        name : 'EmailCheck',
        component : EmailCheck
    },
    {
        path: '/emailSend',
        name : 'EmailSend',
        component : EmailSend
    },
    {
        path: '/joinComplete',
        name : 'JoinComplete',
        component : JoinComplete
    }
]
