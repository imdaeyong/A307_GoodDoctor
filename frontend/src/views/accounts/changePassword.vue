<template>
  <div class="user" id="join">
    <div class="wrapC mt-5">
      <h1>비밀번호 변경</h1>
      <div class="input-label">
        <input v-model="oldPassword"
          :type="oldPasswordType"
          id="oldPassword" placeholder="사용하던 비밀번호를 입력해주세요."/>
        <label for="oldPassword">기존 비밀번호</label>
      </div>

      <div class="input-label" >
        <input v-model="newPassword"
          :type="newPasswordType"
          :class="{error : error.newPassword, complete:!error.newPassword&&newPassword.length!==0}"
          id="newPassword" placeholder="변경할 비밀번호를 입력하세요."/>
        <label for="newPassword">변경 비밀번호</label>
        <div class="error-text" v-if="error.newPassword">{{error.newPassword}}</div>
      </div>

      <div class="input-label" >
        <input v-model="newPasswordConfirm"
          :type="newPasswordConfirmType"
          :class="{error : error.newPasswordConfirm, complete:!error.newPasswordConfirm&&newPasswordConfirm.length!==0}"
          id="newPasswordConfirm" placeholder="변경할 비밀번호를 다시 한 번 입력하세요."/>
        <label for="newPasswordConfirm">비밀번호 확인</label>
        <div class="error-text" v-if="error.newPasswordConfirm">{{error.newPasswordConfirm}}</div>
      </div>

      <button
        class="btn-full-center mt-2"
        :disabled="!isSubmit"
        :class="{disabled : !isSubmit}"
        @click="passwordChange"
      >저장</button>

    </div>   
  </div>
</template>

<script>
import '../../assets/css/style.scss'
import '../../assets/css/user.scss'
import PV from "password-validator"
import store from '@/vuex/store.js'

export default {
  name: "ChangePassword",
  data: () => {
    return {
      email: "",
      oldPassword: "",
      newPassword: "",
      newPasswordConfirm: "",
      passwordSchema: new PV(),
      isTerm: false,
      isLoading: false,
      error: {
        email: "",
        oldPassword: "",
        newPassword: "",
        newPasswordConfirm: "",
        term: false
      },
      isSubmit: false,
      oldPasswordType: "password",
      newPasswordType: "password",
      newPasswordConfirmType: "password",
      termPopup: false
    };
  },
  created() {
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    newPassword: function(v) {
      this.newPasswordCheckForm();
    },
    newPasswordConfirm: function(v) {
      this.newPasswordConfirmCheckForm();
    }
  },
  methods : {
    newPasswordCheckForm() {
      if (
        this.newPassword.length >= 0 &&
        !this.passwordSchema.validate(this.newPassword)
      )
        this.error.newPassword = "영문, 숫자 포함 8 자리이상이어야 합니다.";
      else this.error.newPassword = false;
    },
    newPasswordConfirmCheckForm() {
      if (this.newPassword !== this.newPasswordConfirm) {
        this.error.newPasswordConfirm = "비밀번호가 일치하지 않습니다."
      }
      else this.error.newPasswordConfirm = false;
    
      let isSubmit = true;
        Object.values(this.error).map(v => {
          if (v) isSubmit = false;
        });
        this.isSubmit = isSubmit;    
    },
    passwordChange(){
      const userInfo = store.state.userInfo.data;
      
      if (this.isSubmit) {
        let { email, oldPassword, newPassword } = this;
        let data = {
          email : store.state.userInfo.data.email,
          oldPassword,
          newPassword
        };
        this.isSubmit = false;
        store.dispatch('changePassword', data);
      }
    }
  }
}
</script>