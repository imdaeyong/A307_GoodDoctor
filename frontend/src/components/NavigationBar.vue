<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="info" fixed="top">
      <!-- 네브바 왼쪽 여백 -->
      <div style="margin-left: 5em;"></div>

      <router-link to="/" style="text-decoration: none; color: white; font-size: 22px">
        <b-icon-heart-half style="margin-right: 0.4em; margin-top: 0.2em;"></b-icon-heart-half>굿 닥터
      </router-link>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <!-- <nav-form> -->
        <select v-model="target" class="btn-front" for="navSearch">
          <option value="hospital">병원 검색</option>
          <option value="feed">피드 검색</option>
        </select>
        <input
          class="input-search"
          type="text"
          v-model="word"
          @keyup.enter="search"
          placeholder
          id="navSearch"
        />
        <button @click="search" class="btn-search" style="outline:0;">
          <b-icon-search></b-icon-search>
        </button>
        <!-- </nav-form> -->

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item href="/feed/write" right style="margin-right: .3em;">리뷰쓰기</b-nav-item>

          <!-- 병원으로 찾기 -->
          <b-nav-item-dropdown text="병원찾기" right>
            <div class="drop-down-hospital">
              <button @click="selectSubject('치과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital1.png" alt />
                <br />치과
              </button>
              <button @click="selectSubject('피부과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital2.png" alt />
                <br />피부과
              </button>
              <button @click="selectSubject('성형외과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital3.png" alt />
                <br />성형외과
              </button>
              <button @click="selectSubject('안과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital4.png" alt />
                <br />안과
              </button>
              <button @click="selectSubject('산부인과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital5.png" alt />
                <br />산부인과
              </button>
              <button @click="selectSubject('비뇨기과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital6.png" alt />
                <br />비뇨기과
              </button>
              <button @click="selectSubject('정형외과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital7.png" alt />
                <br />정형외과
              </button>
              <button @click="selectSubject('마취통증의학과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital8.png" alt />
                <br />마취통증의학과
              </button>
              <button @click="selectSubject('재활의학과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital10.png" alt />
                <br />재활의학과
              </button>
              <button @click="selectSubject('영상의학과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital11.png" alt />
                <br />영상의학과
              </button>
              <button @click="selectSubject('외과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital12.png" alt />
                <br />외과
              </button>
              <button @click="selectSubject('신경과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital13.png" alt />
                <br />신경과
              </button>
              <button @click="selectSubject('소아과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital14.png" alt />
                <br />소아과
              </button>
              <button @click="selectSubject('내과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital15.png" alt />
                <br />내과
              </button>
              <button @click="selectSubject('이비인후과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital16.png" alt />
                <br />이비인후과
              </button>
              <button @click="selectSubject('가정의학과')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital17.png" alt />
                <br />가정의학과
              </button>
              <button @click="selectSubject('한의원')" class="dropdown-sub-hospital">
                <img src="../assets/images/hospital/hospital18.png" alt />
                <br />한의원
              </button>
            </div>
          </b-nav-item-dropdown>

          <!-- 지역으로 찾기 -->
          <b-nav-item-dropdown text="지역별찾기" right>
            <div class="ml-2" style="width: 360px">
              <div>
                <button class="dropdown-sub-area" v-on:click="selectCity('서울')" value="서울">
                  <b-icon-geo-alt></b-icon-geo-alt>서울
                </button>
                <button class="dropdown-sub-area" v-on:click="selectCity('경기')" value="경기">
                  <b-icon-geo-alt></b-icon-geo-alt>경기
                </button>
                <button class="dropdown-sub-area" v-on:click="selectCity('부산')" value="부산">
                  <b-icon-geo-alt></b-icon-geo-alt>부산
                </button>
                <button class="dropdown-sub-area" v-on:click="selectCity('경남')" value="경남">
                  <b-icon-geo-alt></b-icon-geo-alt>경남
                </button>
                <button class="dropdown-sub-area" v-on:click="selectCity('인천')" value="인천">
                  <b-icon-geo-alt></b-icon-geo-alt>인천
                </button>
                <br />
                <button class="dropdown-sub-area" v-on:click="selectCity('대구')" value="대구">
                  <b-icon-geo-alt></b-icon-geo-alt>대구
                </button>
                <button class="dropdown-sub-area" v-on:click="selectCity('경북')" value="경북">
                  <b-icon-geo-alt></b-icon-geo-alt>경북
                </button>
                <button class="dropdown-sub-area" v-on:click="selectCity('대전')" value="대전">
                  <b-icon-geo-alt></b-icon-geo-alt>대전
                </button>
                <button class="dropdown-sub-area" v-on:click="selectCity('전북')" value="전북">
                  <b-icon-geo-alt></b-icon-geo-alt>전북
                </button>
                <button class="dropdown-sub-area" v-on:click="selectCity('충남')" value="충남">
                  <b-icon-geo-alt></b-icon-geo-alt>충남
                </button>
              </div>
              <div class="dropdown-sub-gu mx-2" v-if="area[0].display" style="z-index: 2;">
                <div class="area">
                  <b-icon-geo-alt></b-icon-geo-alt>서울
                </div>
                <div class="gu-list">
                  <div>
                    <a @click="selectGu('강남구')" href="#">강남구</a>
                  </div>
                  <div>
                    <a @click="selectGu('송파구')" href="#">송파구</a>
                  </div>
                  <div>
                    <a @click="selectGu('서초구')" href="#">서초구</a>
                  </div>
                  <div>
                    <a @click="selectGu('마포구')" href="#">마포구</a>
                  </div>
                  <div>
                    <a @click="selectGu('강서구')" href="#">강서구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('영등포구')" href="#">영등포구</a>
                  </div>
                  <div>
                    <a @click="selectGu('강동구')" href="#">강동구</a>
                  </div>
                  <div>
                    <a @click="selectGu('성북구')" href="#">성북구</a>
                  </div>
                  <div>
                    <a @click="selectGu('은평구')" href="#">은평구</a>
                  </div>
                  <div>
                    <a @click="selectGu('관악구')" href="#">관악구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('구로구')" href="#">구로구</a>
                  </div>
                  <div>
                    <a @click="selectGu('노원구')" href="#">노원구</a>
                  </div>
                  <div>
                    <a @click="selectGu('동대문구')" href="#">동대문구</a>
                  </div>
                  <div>
                    <a @click="selectGu('동작구')" href="#">동작구</a>
                  </div>
                  <div>
                    <a @click="selectGu('양천구')" href="#">양천구</a>
                  </div>
                  <div>
                    <a @click="selectGu('중랑구')" href="#">중랑구</a>
                  </div>
                  <div>
                    <a @click="selectGu('광진구')" href="#">광진구</a>
                  </div>
                  <div>
                    <a @click="selectGu('강북구')" href="#">강북구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('중구')" href="#">중구</a>
                  </div>
                  <div>
                    <a @click="selectGu('종로구')" href="#">종로구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('성동구')" href="#">성동구</a>
                    <br />
                  </div>
                  <div>
                    <a @click="selectGu('서대문구')" href="#">서대문구</a>
                  </div>
                  <div>
                    <a @click="selectGu('용산구')" href="#">용산구</a>
                  </div>
                  <div>
                    <a @click="selectGu('금천구')" href="#">금천구</a>
                  </div>
                </div>
              </div>
              <div class="dropdown-sub-gu mx-2" v-if="area[1].display">
                <div class="area">
                  <b-icon-geo-alt></b-icon-geo-alt>경기
                </div>
                <div class="gu-list">
                  <div>
                    <a @click="selectGu('부천')" href="#">부천</a>
                  </div>
                  <div>
                    <a @click="selectGu('화성')" href="#">화성</a>
                  </div>
                  <div>
                    <a @click="selectGu('일산')" href="#">일산</a>
                  </div>
                  <div>
                    <a @click="selectGu('성남분당')" href="#">성남분당</a>
                  </div>
                  <div>
                    <a @click="selectGu('남양주')" href="#">남양주</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('김포')" href="#">김포</a>
                  </div>
                  <div>
                    <a @click="selectGu('고양덕양')" href="#">고양덕양</a>
                  </div>
                  <div>
                    <a @click="selectGu('시흥')" href="#">시흥</a>
                  </div>
                  <div>
                    <a @click="selectGu('의정부')" href="#">의정부</a>
                  </div>
                  <div>
                    <a @click="selectGu('평택')" href="#">평택</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('광명')" href="#">광명</a>
                  </div>
                </div>
              </div>
              <div class="dropdown-sub-gu mx-2" v-if="area[2].display">
                <div class="area">
                  <b-icon-geo-alt></b-icon-geo-alt>부산
                </div>
                <div class="gu-list">
                  <div>
                    <a @click="selectGu('진구')" href="#">진구</a>
                  </div>
                  <div>
                    <a @click="selectGu('해운대구')" href="#">해운대구</a>
                  </div>
                  <div>
                    <a @click="selectGu('동래구')" href="#">동래구</a>
                  </div>
                  <div>
                    <a @click="selectGu('금정구')" href="#">금정구</a>
                  </div>
                  <div>
                    <a @click="selectGu('사하구')" href="#">사하구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('남구')" href="#">남구</a>
                  </div>
                  <div>
                    <a @click="selectGu('연제구')" href="#">연제구</a>
                  </div>
                  <div>
                    <a @click="selectGu('북구')" href="#">북구</a>
                  </div>
                  <div>
                    <a @click="selectGu('사상구')" href="#">사상구</a>
                  </div>
                  <div>
                    <a @click="selectGu('동구')" href="#">동구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('수영구')" href="#">수영구</a>
                  </div>
                  <div>
                    <a @click="selectGu('기장군')" href="#">기장군</a>
                  </div>
                  <div>
                    <a @click="selectGu('광안리')" href="#">광안리</a>
                  </div>
                  <div>
                    <a @click="selectGu('영도구')" href="#">영도구</a>
                  </div>
                  <div>
                    <a @click="selectGu('송도')" href="#">송도</a>
                  </div>
                  <div>
                    <a @click="selectGu('강서구')" href="#">강서구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('중구')" href="#">중구</a>
                  </div>
                </div>
              </div>
              <div class="dropdown-sub-gu mx-2" v-if="area[3].display">
                <div class="area">
                  <b-icon-geo-alt></b-icon-geo-alt>경남
                </div>
                <div class="gu-list">
                  <div>
                    <a @click="selectGu('김해시')" href="#">김해시</a>
                  </div>
                  <div>
                    <a @click="selectGu('양산시')" href="#">양산시</a>
                  </div>
                  <div>
                    <a @click="selectGu('진주시')" href="#">진주시</a>
                  </div>
                  <div>
                    <a @click="selectGu('성산구')" href="#">성산구</a>
                  </div>
                  <div>
                    <a @click="selectGu('의창구')" href="#">의창구</a>
                  </div>
                  <div>
                    <a @click="selectGu('합포구')" href="#">합포구</a>
                  </div>
                  <div>
                    <a @click="selectGu('회원구')" href="#">회원구</a>
                  </div>
                  <div>
                    <a @click="selectGu('거제시')" href="#">거제시</a>
                  </div>
                  <div>
                    <a @click="selectGu('통영시')" href="#">통영시</a>
                  </div>
                  <div>
                    <a @click="selectGu('진해구')" href="#">진해구</a>
                  </div>
                </div>
              </div>
              <div class="dropdown-sub-gu mx-2" v-if="area[4].display">
                <div class="area">
                  <b-icon-geo-alt></b-icon-geo-alt>인천
                </div>
                <div class="gu-list">
                  <div>
                    <a @click="selectGu('남동구')" href="#">남동구</a>
                  </div>
                  <div>
                    <a @click="selectGu('부평구')" href="#">부평구</a>
                  </div>
                  <div>
                    <a @click="selectGu('미추홀구')" href="#">미추홀구</a>
                  </div>
                  <div>
                    <a @click="selectGu('서구')" href="#">서구</a>
                  </div>
                  <div>
                    <a @click="selectGu('계양구')" href="#">계양구</a>
                  </div>
                  <div>
                    <a @click="selectGu('연수구')" href="#">연수구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('동구')" href="#">동구</a>
                  </div>
                  <div>
                    <a @click="selectGu('남구')" href="#">남구</a>
                  </div>
                  <div>
                    <a @click="selectGu('중구')" href="#">중구</a>
                  </div>
                  <div>
                    <a @click="selectGu('강화군')" href="#">강화군</a>
                  </div>
                  <div>
                    <a @click="selectGu('옹진군')" href="#">옹진군</a>
                  </div>
                </div>
              </div>
              <div class="dropdown-sub-gu mx-2" v-if="area[5].display">
                <div class="area">
                  <b-icon-geo-alt></b-icon-geo-alt>대구
                </div>
                <div class="gu-list">
                  <div>
                    <a @click="selectGu('달서구')" href="#">달서구</a>
                  </div>
                  <div>
                    <a @click="selectGu('수성구')" href="#">수성구</a>
                  </div>
                  <div>
                    <a @click="selectGu('중구')" href="#">중구</a>
                  </div>
                  <div>
                    <a @click="selectGu('남구')" href="#">남구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('서구')" href="#">서구</a>
                  </div>
                  <div>
                    <a @click="selectGu('동구')" href="#">동구</a>
                  </div>
                  <div>
                    <a @click="selectGu('북구')" href="#">북구</a>
                  </div>
                  <div>
                    <a @click="selectGu('달성군')" href="#">달성군</a>
                  </div>
                </div>
              </div>
              <div class="dropdown-sub-gu mx-2" v-if="area[6].display">
                <div class="area">
                  <b-icon-geo-alt></b-icon-geo-alt>경북
                </div>
                <div class="gu-list">
                  <div>
                    <a @click="selectGu('구미시')" href="#">구미시</a>
                  </div>
                  <div>
                    <a @click="selectGu('경산시')" href="#">경산시</a>
                  </div>
                  <div>
                    <a @click="selectGu('포항남구')" href="#">포항남구</a>
                  </div>
                  <div>
                    <a @click="selectGu('경주시')" href="#">경주시</a>
                  </div>
                  <div>
                    <a @click="selectGu('포항북구')" href="#">포항북구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('영주시')" href="#">영주시</a>
                  </div>
                  <div>
                    <a @click="selectGu('안동시')" href="#">안동시</a>
                  </div>
                  <div>
                    <a @click="selectGu('김천시')" href="#">김천시</a>
                  </div>
                </div>
              </div>
              <div class="dropdown-sub-gu mx-2" v-if="area[7].display">
                <div class="area">
                  <b-icon-geo-alt></b-icon-geo-alt>대전
                </div>
                <div class="gu-list">
                  <div>
                    <a @click="selectGu('대덕구')" href="#">대덕구</a>
                  </div>
                  <div>
                    <a @click="selectGu('동구')" href="#">동구</a>
                  </div>
                  <div>
                    <a @click="selectGu('서구')" href="#">서구</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('유성구')" href="#">유성구</a>
                  </div>
                  <div>
                    <a @click="selectGu('중구')" href="#">중구</a>
                  </div>
                </div>
              </div>
              <div class="dropdown-sub-gu mx-2" v-if="area[8].display">
                <div class="area">
                  <b-icon-geo-alt></b-icon-geo-alt>전북
                </div>
                <div class="gu-list">
                  <div>
                    <a @click="selectGu('완산구')" href="#">완산구</a>
                  </div>
                  <div>
                    <a @click="selectGu('덕진구')" href="#">덕진구</a>
                  </div>
                  <div>
                    <a @click="selectGu('익산시')" href="#">익산시</a>
                  </div>
                  <div>
                    <a @click="selectGu('군산시')" href="#">군산시</a>
                  </div>
                  <div>
                    <a @click="selectGu('정읍시')" href="#">정읍시</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('김제시')" href="#">김제시</a>
                  </div>
                  <div>
                    <a @click="selectGu('남원시')" href="#">남원시</a>
                  </div>
                  <div>
                    <a @click="selectGu('완주군')" href="#">완주군</a>
                  </div>
                  <div>
                    <a @click="selectGu('고창군')" href="#">고창군</a>
                  </div>
                  <div>
                    <a @click="selectGu('부안군')" href="#">부안군</a>
                  </div>
                  <br />
                </div>
              </div>
              <div class="dropdown-sub-gu mx-2" v-if="area[9].display">
                <div class="area">
                  <b-icon-geo-alt></b-icon-geo-alt>충남
                </div>
                <div class="gu-list">
                  <div>
                    <a @click="selectGu('아산시')" href="#">아산시</a>
                  </div>
                  <div>
                    <a @click="selectGu('서북구')" href="#">서북구</a>
                  </div>
                  <div>
                    <a @click="selectGu('동남구')" href="#">동남구</a>
                  </div>
                  <div>
                    <a @click="selectGu('서산시')" href="#">서산시</a>
                  </div>
                  <div>
                    <a @click="selectGu('당진시')" href="#">당진시</a>
                  </div>
                  <br />
                  <div>
                    <a @click="selectGu('논산시')" href="#">논산시</a>
                  </div>
                  <div>
                    <a @click="selectGu('부여군')" href="#">부여군</a>
                  </div>
                  <div>
                    <a @click="selectGu('보령시')" href="#">보령시</a>
                  </div>
                  <div>
                    <a @click="selectGu('홍성군')" href="#">홍성군</a>
                  </div>
                  <div>
                    <a @click="selectGu('공주시')" href="#">공주시</a>
                  </div>
                  <br />
                </div>
              </div>
            </div>
          </b-nav-item-dropdown>

          <!-- 회원관련 기능 -->
          <b-nav-item-dropdown
            v-if="this.$store.state.isLogin"
            :text="this.$store.state.userInfo.data.nickname"
            right
          >
            <div style="text-align: center">
              <b-dropdown-item
                href="/accounts/profile"
                style="text-decoration : none; color : black;"
              >회원정보</b-dropdown-item>
              <b-dropdown-item v-on:click="logout()">로그아웃</b-dropdown-item>
            </div>
          </b-nav-item-dropdown>

          <b-nav-item-dropdown v-else text="닉네임" right></b-nav-item-dropdown>

          <!-- 네브바 오른쪽 여백 -->
          <div style="margin-right: 5em;"></div>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>


<script>
import store from "@/vuex/store.js";
import http from "@/util/http-common";

export default {
  name: "NavigationBar",
  methods: {
    logout() {
      store.dispatch("logout");
      this.$bvModal.show("bv-modal-example");
    },
    selectSubject(subject) {
      this.subject = subject;
      this.$router.push(`../hospital?no=10&subject=${subject}`);
      this.$router.go(0);
    },
    selectGu(gu) {
      if (this.sido == "부산") this.gugun = "부산" + gu;
      else this.gugun = gu;

      this.$router.push(
        `../hospital?no=10&subject=&sido=${this.sido}&gu=${this.gugun}`
      );
      this.$router.go(0);
    },
    selectCity(city) {
      this.sido = city;
      for (var i = 0; i < this.area.length; i++) {
        if (city == this.area[i].name)
          this.area[i].display = !this.area[i].display;
        else this.area[i].display = false;
      }
      city = !city;
    },
    search() {
      if (this.target == "hospital") {
        this.$router.push(
          `../search?no=10&subject=&sido=&gu=&word=${this.word}`
        );
        this.$router.go(0);
      } else if (this.target == "feed") {
        this.$router.push(`../search?word=${this.word}`);
        this.$router.go(0);
      }
    },
  },
  data: () => {
    return {
      sido: "",
      gugun: "",
      subject: "",
      word: "",
      target: "hospital",
      area: [
        { display: false, name: "서울" },
        { display: false, name: "경기" },
        { display: false, name: "부산" },
        { display: false, name: "경남" },
        { display: false, name: "인천" },
        { display: false, name: "대구" },
        { display: false, name: "경북" },
        { display: false, name: "대전" },
        { display: false, name: "전북" },
        { display: false, name: "충남" },
      ],
    };
  },
};
</script>