<template>
  <div class="user" id="join">
    <div class="wrapC">
      <h1>비밀번호 변경</h1>
      <div class="input-with-label">
        <input v-model="oldPassword"
          :type="oldPasswordType"
          id="oldPassword" placeholder="사용하던 비밀번호를 입력해주세요."/>
        <label for="oldPassword">기존 비밀번호</label>
      </div>

      <div class="input-with-label" >
        <input v-model="newPassword"
          :type="newPasswordType"
          id="newPassword" placeholder="변경할 비밀번호를 입력하세요."/>
        <label for="newPassword">변경 비밀번호</label>
        <div class="error-text" v-if="error.newPassword">{{error.newPassword}}</div>
      </div>

      <div class="input-with-label" >
        <input v-model="newPasswordConfirm"
          :type="newPasswordConfirmType"
          id="newPasswordConfirm" placeholder="변경할 비밀번호를 다시 한 번 입력하세요."/>
        <label for="newPasswordConfirm">비밀번호 확인</label>
        <div class="error-text" v-if="error.newPasswordConfirm">{{error.newPasswordConfirm}}</div>
      </div>

      <button
        class="btn btn--back btn--login"
        @click="onChange"
        :disabled="!isSubmit"
        :class="{disabled : !isSubmit}"
      >저장</button>

    </div>   
  </div>
</template>

<script>
import PV from "password-validator";
import UserApi from "../../api/UserApi";
import axios from 'axios';
const SERVER_URL="http://localhost:8080/";
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
    oldPassword: function(v) {
      this.checkForm();
    },
    newPassword: function(v) {
      this.checkForm();
    },
    newPasswordConfirm: function(v) {
      this.checkForm();
    }
  },
  methods : {
    checkForm() {
      if (
        this.newPassword.length >= 0 &&
        !this.passwordSchema.validate(this.newPassword)
      )
        this.error.newPassword = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.newPassword = false;

      if (
        this.newPasswordConfirm != this.newPassword
      )
        this.error.newPasswordConfirm = "새로운 비밀번호와 다릅니다.";
      else this.error.newPasswordConfirm = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onChange(){
      const userInfo = this.$session.get('user');
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
      if (this.isSubmit) {
        let { email, oldPassword, newPassword } = this;
        let data = {
          email : this.$session.get('user').email,
          oldPassword,
          newPassword
        };
        //요청 후에는 버튼 비활성화
        this.isSubmit = false;
        console.log(oldPassword + " " + this.$session.get('user').email + " " + email + " " + newPassword);
        axios.put(`${SERVER_URL}pwd`,data)
          .then(res=>{
            //요청이 끝나면 버튼 활성화
            userInfo.password = data.newPassword
            this.$session.set('user',userInfo);
            this.isSubmit = true;
            alert("비밀번호 변경 완료! 변경된 비밀번호로 로그인해주세요.");
            this.$router.push("/");
                        
          })
          .catch(err=>{
            alert("기존 비밀번호가 틀렸습니다.");
            console.log(err);
            this.isSubmit = true;
            this.$router.push("/errorPage");
          })
      }
    }
  }
}
</script>