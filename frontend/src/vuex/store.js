import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'
import axios from 'axios'
import router from "../router"
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

const state = {
    isUser: false,
}
const SERVER_URL="http://localhost:8080/"

export default new Vuex.Store({
    state: {
        userInfo: {},
        isLogin: false,
        authCode: "",
        feedInfo: ""
    },
    mutations: {
        addUserInfo(state, userInfo) {
            state.userInfo = userInfo
        },
        mutateIsLogin(state, isLogin){
            state.isLogin = isLogin
        },
        mutateUserInfo(state, userInfo){
            state.userInfo = userInfo
        },
        mutateFeedInfo(state, feedInfo){
            state.feedInfo = feedInfo
        }
    },
    getters: {
        authCode(state) {
            return state.authCode;
        },
        userInfo(state) {
            return state.userInfo;
        },
    },
    actions:{
        login(context, {email, password}) {
             axios.get(`${SERVER_URL}account/gooddoc?email=${email}&password=${password}`)
            .then(res=>{
                context.commit('mutateIsLogin', true)
                context.commit('mutateUserInfo', res)
                
                router.go(0);
                alert("로그인 성공");
            })
            .catch(err=>{
                alert("아이디 또는 비밀번호 실패입니다.");
                router.push("/errorPage");
            })

        },
        logout(context) {
            context.commit('mutateIsLogin', false)
            context.commit('mutateUserInfo', {})
        },
        changePassword(context, data){
            axios.put(`${SERVER_URL}pwd`,data)
          .then(res=>{
            //요청이 끝나면 버튼 활성화
            context.commit('mutateIsLogin', false)
            context.commit('mutateUserInfo', {})
            alert("비밀번호 변경 완료! 변경된 비밀번호로 로그인해주세요.");
            router.push("/");         
          })
          .catch(err=>{
            alert("기존 비밀번호가 틀렸습니다.");
            console.log(err);
            router.push("/errorPage");
          })
        },
        openReply(context, feedInfo){
            context.commit('mutateFeedInfo', feedInfo)
        }

    },
    plugins: [
        createPersistedState()
    ]
})