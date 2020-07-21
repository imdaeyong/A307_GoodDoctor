import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'

Vue.use(Vuex)

const state = {
    isUser: false,
}

export default new Vuex.Store({
    state: {
        userInfo: {},
        authCode: "",
    },
    mutations: {
        mutateUserInfo(state, userInfo) {
            state.userInfo = userInfo
        },
    },
    getters: {
        authCode(state) {
            return state.authCode;
        },
        userInfo(state) {
            return state.userInfo;
        },
    },
    actions
})