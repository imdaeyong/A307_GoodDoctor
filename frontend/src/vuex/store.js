import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'
import axios from 'axios'
import router from "../router"
import createPersistedState from 'vuex-persistedstate';
import http from '@/util/http-common'

Vue.use(Vuex)

const state = {
  isUser: false,
}

export default new Vuex.Store({
  state: {
    userInfo: {},
    isLogin: false,
    authCode: "",
    feed: {},
    hospitalZoom:{},
    // accessToken:"",
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
    mutateAccessToken(state, accessToken){
      state.accessToken = accessToken
    },
    mutateFeedInfo(state, feed){
      state.feed = feed
    },
    addHospitalZoom(state,hospitalZoom){
      state.hospitalZoom=hospitalZoom
    },
  },
  getters: {
    authCode(state) {
      return state.authCode;
    },
    userInfo(state) {
      return state.userInfo;
    },
    hospitalZoom(state){
      return state.hospitalZoom;
    },
    accessToken(state){
      return state.accessToken;
    }
  },
  actions:{
    login(context, {email, password}) {
      http.get(`account/gooddoc?email=${email}&password=${password}`)
      .then(res=>{
        context.commit('mutateIsLogin', true)
        // context.commit('mutateUserInfo', res)
        context.commit('mutateUserInfo', res.object)
        context.commit('mutateAccessToken', res.data)
        console.log(res);
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
      http.put(`pwd`,data)
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
    openReply(context, feed){
      context.commit('mutateFeedInfo', feed)
    }
  },
  plugins: [
    createPersistedState()
  ]
})