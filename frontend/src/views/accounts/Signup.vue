<template>
  <div class="user" id="login">
    <div class="wrapCC mt-5">
      <h1>가입하기</h1>
      <!-- <div class="profile-image">
        <div>
          <img :src="preview">
        </div>
        <input type="file" v-on:change="upload" class="review-img-upload"/>
      </div> -->
      <div class="form-wrap">
        <div class="input-label">
          <input
            v-bind:class="{error : error.nickName, complete:nickName.length!==0}"
            v-model="nickName" 
            id="nickName" 
            placeholder="닉네임을 입력하세요." 
            type="text" 
          />
          <label for="nickName">닉네임</label>
        </div>

        <div class="input-label">
          <input 
            v-model="email" 
            v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
            id="email" 
            placeholder="이메일을 입력하세요." 
            type="text"/>
          <label for="email">이메일</label>
          <label for="email" @click="emailAuthStart" class="right">인증</label>
          <div class="error-text" v-if="error.email &&email.length!=0">{{error.email}}</div>
          
        </div>
        <div class="three-quarter" v-if="emailAuthinput">
          <div class="auth-input-label">
            <input 
              type="text"
              id ="inputAuth"
              ref="inputAuth"
              v-model="inputAuth"
              v-bind:class="{complete:inputAuth.length==6}"
            />
            <label for="inputAuth">인증번호</label>
            <label for="inputAuth" @click="emailAuthCheck" class="authRight"><button><span style="font-weight: bold;">확인</span></button>
              </label>
          </div>
        </div>

        <div class="input-label">
          <input 
            v-model="password"
            v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
            id="password" 
            :type="passwordType" 
            placeholder="비밀번호를 입력하세요." />
          <label for="password">비밀번호</label>
          <div class="error-text" v-if="error.password && password.length!=0">{{error.password}}</div>
        </div>

        <div class="input-label">
          <input
            v-model="passwordConfirm"
            :type="passwordConfirmType"
            v-bind:class="{error : error.passwordConfirm, complete:!error.passwordConfirm&&passwordConfirm.length!==0}"
            id="password-confirm"
            placeholder="비밀번호를 다시한번 입력하세요."
          />
          <label for="password-confirm">비밀번호 확인</label>
          <div class="error-text" v-if="error.passwordConfirm && passwordConfirm.length!=0">{{error.passwordConfirm}}</div>
        </div>
      </div>

      <button class="btn-full-center mt-4"
      :disabled="!isSubmit"
      :class="{disabled : !isSubmit}"
      @click="onjoin"
      >가입하기</button>
    </div>
  </div>
</template>

<script>
import * as EmailValidator from "email-validator"
import PV from "password-validator"
import '../../assets/css/style.scss'
import '../../assets/css/user.scss'
import http from '@/util/http-common'
import store from '@/vuex/store.js'

export default {
  name: "Singup",
  data: () => {
    return {
      preview: "",
      file: "",
      nickName: "",
      email: "",
      password: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
      passwordType: "password",
      passwordConfirmType: "password",
      error: {
        email: true,
        password: true,
        passwordConfirm: true,
        authEmail: true,
      },
      isSubmit: false,
      termPopup: false,
      //인증 관련 변수
      authCode:"",
      inputAuth:"",
      emailAuthinput: false,
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
    email: function() {
      this.emailCheckForm();
    },
    password: function() {
      this.passwordCheckForm();
    },
    passwordConfirm: function() {
      this.passwordConfirmCheckForm()
    },
    error: function() {
      this.signupCheck()
    },
    // emailAuthinput: function() {
    //   this.signupCheck()
    // },
  },
  methods: {
    emailAuthCheck() {
      if(this.$store.state.authCode!=this.inputAuth){
        alert("인증번호를 다시 확인해주세요!");
      }else{
        this.error.authEmail = false
        alert("인증 되었습니다.")
        this.emailAuthinput = false
      }
    },
    emailAuthStart() {
      this.emailAuthinput = true
      http
        .post("/email", {email: this.email})
        .then(({ data }) => {
          alert('입력하신 이메일로 인증번호가 발송되었습니다.')
          this.$store.state.authCode=data.object
        })
        .catch(() => {
        });
    },
    emailCheckForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;
    },
    passwordCheckForm() {
      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문, 숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;
      this.signupCheck()
    },
    passwordConfirmCheckForm() {
      if (this.password !== this.passwordConfirm) {
        this.error.passwordConfirm = "비밀번호가 일치하지 않습니다."
      }
      else this.error.passwordConfirm = false; 
      this.signupCheck()     
    },
    signupCheck() {
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;    
    },
    onjoin(){
      let formData = new FormData();
      // formData.append('file', this.file);
      formData.append('imageUrl', this.preview);
      formData.append('email', this.email);
      formData.append('nickname', this.nickName);
      formData.append('password', this.password);
      http.post(`account`,formData,{
        headers:{'Content-Type':'multipart/form-data'}
      })
      .then(res=>{
        alert("회원 가입되었습니다. 로그인 해주세요");
        this.$router.push("/feed/main")
      })
      .catch(err=>{
        if(err.response.data.data == "nickname_fail") alert("이미 존재하는 닉네임입니다.");
        else if(err.response.data.data == "email_fail") alert("이미 존재하는 이메일입니다.");
      })
    },
    upload(e){
      let file = e.target.files[0];
      this.file = file;

      this.preview = URL.createObjectURL(file);

      let reader = new FileReader();

      reader.readAsDataURL(file);
      reader.onload = e => {
        this.preview = e.target.result;
      }
    }
  }
};
</script>