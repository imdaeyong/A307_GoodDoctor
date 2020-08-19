<template>
  <div>
    <NavBar />
    <div v-if="!this.$store.state.isLogin">
      <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop no-close-on-esc>
        <div class="user" id="login">
          <div class="wrapC mt-3">
            <h1>
              <span style="color : #17a2b8">아파서 왔지?</span> 여기맞아
            </h1>
            <div class="input-label">
              <input
                v-model="email"
                v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
                @keyup.enter="onQRWithLogin"
                id="email"
                placeholder="이메일을 입력하세요."
                type="text"
              />
              <label for="email">이메일</label>
              <div class="error-text" v-if="error.email">{{error.email}}</div>
            </div>

            <div class="input-label">
              <input
                v-model="password"
                type="password"
                v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
                id="password"
                @keyup.enter="onQRWithLogin"
                placeholder="비밀번호를 입력하세요."
              />
              <label for="password">비밀번호</label>
              <div class="error-text" v-if="error.password">{{error.password}}</div>
            </div>

            <button
              class="btn-full-center"
              @click="onQRWithLogin"
              :disabled="!isSubmit"
              :class="{disabled : !isSubmit}"
            >로그인</button>

            <div class="sns-login">
              <div class="text">
                <p>SNS 로그인</p>
                <div class="bar"></div>
              </div>
              <kakaoLogin :component="component" />
              <GoogleLogin :component="component" />
            </div>

            <div class="add-option">
              <div class="text">
                <p>혹시</p>
                <div class="bar"></div>
              </div>
              <div class="wrap">
                <p>비밀번호를 잊으셨나요?</p>
                <router-link to="/accounts/findPassword" class="router-a">비밀번호 찾기</router-link>
              </div>
              <div class="wrap">
                <p>아직 회원이 아니신가요?</p>
                <router-link to="/accounts/signup" class="router-a">가입하기</router-link>
              </div>
            </div>
          </div>
        </div>
      </b-modal>
    </div>
  </div>
</template>

<script>
import "../../assets/css/main.scss";
import "../../assets/css/feed.scss";
import "../../assets/css/feedModal.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import KakaoLogin from "../../components/accounts/snsLogin/Kakao.vue";
import GoogleLogin from "../../components/accounts/snsLogin/Google.vue";
import NavBar from "../../components/NavigationBar.vue";
import store from "@/vuex/store.js";
import http from "@/util/http-common";
import router from "../../router";

export default {
  name: "QRLogin",
  components: {
    KakaoLogin,
    GoogleLogin,
    NavBar,
  },
  data: () => {
    return {
      email: "",
      password: "",
      hospitalId: 0,
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false,
      },
      isSubmit: false,
      component: this,
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
  beforeMount() {
    if (store.state.isLogin) {
      this.onQRWithOutLogin();
    }
  },
  mounted() {
    if (!store.state.isLogin) this.$bvModal.show("bv-modal-example");
  },
  watch: {
    email: function (v) {
      this.emailCheckForm();
    },
    password: function (v) {
      this.pwdCheckForm();
    },
  },
  methods: {
    emailCheckForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;
    },
    pwdCheckForm() {
      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onQRWithLogin() {
      this.hospitalId = this.$route.query.hospitalId;
      let { email, password, hospitalId} = this;
      let data = {
        email,
        password,
        hospitalId
      };
      store.dispatch("QRWithlogin", {
        email: this.email,
        password: this.password,
        hospitalId: this.hospitalId,
        userId: -1
      });
      //if(this.$store.state.isLogin) this.$bvModal.hide('bv-modal-example');
    },
    onQRWithOutLogin() {
      this.hospitalId = this.$route.query.hospitalId;
      let user = this.$store.state.userInfo.data;
      store.dispatch("QRWithOutlogin", {
        email: "",
        password: "",
        hospitalId: this.hospitalId,
        userId: user.id,
      });
    },
  },
};
</script>
