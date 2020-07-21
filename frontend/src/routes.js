import Login from './views/accounts/Login.vue'
import Signin from './views/accounts/Signin.vue'
import EmailCheck from './views/accounts/emailCheck.vue'
import EmailSend from './views/accounts/emailSend.vue'
import ChangePassword from './views/accounts/changePassword.vue'
import SigninComplete from './views/accounts/SigninComplete.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import ErrorPage from './components/errorPage.vue'
import PageNotFound from './components/PageNotFound.vue'

export default [
    {
        path : '/',
        name : 'Login',
        component : Login
    },
    {
        path : '/accounts/Signin',
        name : 'Signin',
        component : Signin
    },
    {
        path : 'accounts/changePassword',
        name : 'ChangePassword',
        component : ChangePassword
    },
    {
        path : 'accounts/emailCheck',
        name : 'EmailCheck',
        component : EmailCheck
    },
    {
        path: 'accounts/emailSend',
        name : 'EmailSend',
        component : EmailSend
    },
    {
        path: 'accounts/SigninComplete',
        name : 'SigninComplete',
        component : SigninComplete
    },
    {
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain
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
    
]
