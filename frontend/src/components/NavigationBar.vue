<template>
    <div class="nav-bar">
        <div class="header">
            <div class="header-list">
                <button class="btn-home" style="outline:0;"><b-icon-heart-half></b-icon-heart-half>굿 닥터</button>
                <input type="text" class="input-search ml-4">
                <button class="btn-search" style="outline:0;"><b-icon-search></b-icon-search></button>
            </div>
            <div class="dropdown-list">
                <div class="btn-hospital-list">
                    <button class="btn-search-hospital" style="outline:0;">병원찾기<b-icon-caret-down-fill></b-icon-caret-down-fill></button>
                    <div class="dropdown-content">
                        <div class="drop-platform">
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital1.png" alt=""><br>치과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital2.png" alt=""><br>피부과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital3.png" alt=""><br>성형외과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital4.png" alt=""><br>안과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital5.png" alt=""><br>산부인과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital6.png" alt=""><br>비뇨기과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital7.png" alt=""><br>정형외과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital8.png" alt=""><br>마취통의학과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital9.png" alt=""><br>신경외과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital10.png" alt=""><br>재활의학과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital11.png" alt=""><br>영상의학과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital12.png" alt=""><br>외과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital13.png" alt=""><br>신경과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital14.png" alt=""><br>소아과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital15.png" alt=""><br>내과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital16.png" alt=""><br>이비인후과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital17.png" alt=""><br>가정의학과</button>
                            <button class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital18.png" alt=""><br>한의원</button>
                        </div>
                    </div>
                </div>
                <div class="btn-area-list">
                    <button class="btn-search-area" style="outline:0;">지역별찾기<b-icon-caret-down-fill></b-icon-caret-down-fill></button>
                    <div class="dropdown-content">
                        <div class="drop-platform">
                            <button class="dropdown-sub-area" v-on:click="toggle('seoul')"><b-icon-geo-alt></b-icon-geo-alt>서울</button>
                            <button class="dropdown-sub-area" v-on:click="toggle('gyeonggido')"><b-icon-geo-alt></b-icon-geo-alt>경기</button>
                            <button class="dropdown-sub-area" v-on:click="toggle('incheon')"><b-icon-geo-alt></b-icon-geo-alt>인천</button>
                            <button class="dropdown-sub-area" v-on:click="toggle('busan')"><b-icon-geo-alt></b-icon-geo-alt>부산</button>
                            <button class="dropdown-sub-area" v-on:click="toggle('daegu')"><b-icon-geo-alt></b-icon-geo-alt>대구</button><br>
                            <button class="dropdown-sub-area" v-on:click="toggle('gwangju')"><b-icon-geo-alt></b-icon-geo-alt>광주</button>
                            <button class="dropdown-sub-area" v-on:click="toggle('ulsan')"><b-icon-geo-alt></b-icon-geo-alt>울산</button>
                            <button class="dropdown-sub-area" v-on:click="toggle('daejeon')"><b-icon-geo-alt></b-icon-geo-alt>대전</button>
                            <button class="dropdown-sub-area" v-on:click="toggle('sejong')"><b-icon-geo-alt></b-icon-geo-alt>세종</button>
                        </div>
                        <div>
                            <div class ="dropdown-sub-gu" v-if="area[0].display">
                                <div class ="area"><b-icon-geo-alt></b-icon-geo-alt>서울</div>
                                <div class ="gu-list">
                                    <div><a href="">강남구</a></div><div><a href="">강서구</a></div>
                                    <div><a href="">중구</a></div><div><a href="">서초구</a></div><div><a href="">서초구</a></div><br>
                                    <div><a href="">양천구</a></div><div><a href="">종로구</a></div>
                                    <div><a href="">송파구</a></div><div><a href="">마포구</a></div><div><a href="">용산구</a></div><br>
                                    <div><a href="">강동구</a></div><div><a href="">서대문구</a></div>
                                    <div><a href="">성동구</a><br></div><div><a href="">관악구</a></div><div><a href="">은평구</a></div><br>
                                    <div><a href="">동대문구</a></div><div><a href="">동작구</a></div>
                                    <div><a href="">노원구</a></div><div><a href="">도봉구</a></div><div><a href="">영등포구</a></div><br>
                                    <div><a href="">중랑구</a></div><div><a href="">강북구</a></div>
                                    <div><a href="">금천구</a></div><div><a href="">광진구</a></div><div><a href="">성북구</a></div><br>
                                    <div><a href="">구로구</a></div>
                                </div>
                                
                            </div>
                            <div class ="dropdown-sub-gu" v-if="area[1].display">
                                <div class ="area"><b-icon-geo-alt></b-icon-geo-alt>경기</div>
                                <div class ="gu-list">
                                    <div><a href="">수원</a></div><div><a href="">김포</a></div>
                                    <div><a href="">고양</a></div><div><a href="">영통</a></div><div><a href="">용인</a></div><br>
                                    <div><a href="">일산</a></div><div><a href="">성남</a></div>
                                    <div><a href="">기흥</a></div><div><a href="">부천</a></div><div><a href="">분당</a></div><br>
                                    <div><a href="">안성</a></div><div><a href="">남양주</a></div>
                                    <div><a href="">과천</a></div><div><a href="">오산</a></div><div><a href="">동두천</a></div><br>
                                    <div><a href="">광명</a></div><div><a href="">의왕</a></div>
                                    <div><a href="">양주</a></div><div><a href="">안양</a></div><div><a href="">이천</a></div><br>
                                    <div><a href="">의정부</a></div><div><a href="">시흥</a></div>
                                    <div><a href="">평택</a></div><div><a href="">파주</a></div><div><a href="">안산</a></div><br>
                                    <div><a href="">하남</a></div><div><a href="">포천</a></div>
                                    <div><a href="">광주</a></div><div><a href="">화성</a></div><div><a href="">연천</a></div><br>
                                    <div><a href="">군포</a></div><div><a href="">여주</a></div>
                                    <div><a href="">가평</a></div><div><a href="">구리</a></div><div><a href="">양평</a></div>
                                </div>
                            </div>
                            <div class ="dropdown-sub-gu" v-if="area[2].display">
                                <div class ="area"><b-icon-geo-alt></b-icon-geo-alt>인천</div>
                                <div class ="gu-list">
                                    <div><a href="">송도</a></div><div><a href="">구월동</a></div>
                                    <div><a href="">청라</a></div><div><a href="">연수구</a></div><div><a href="">남동구</a></div><br>
                                    <div><a href="">서구</a></div><div><a href="">부평</a></div>
                                    <div><a href="">미추홀구</a></div><div><a href="">계양구</a></div><div><a href="">중구</a></div><br>
                                    <div><a href="">강화군</a></div><div><a href="">옹진군</a></div><div><a href="">동구</a></div>
                                </div>
                            </div>
                            <div class ="dropdown-sub-gu" v-if="area[3].display">
                                <div class ="area"><b-icon-geo-alt></b-icon-geo-alt>부산</div>
                                <div class ="gu-list">
                                    <div><a href="">서면</a></div><div><a href="">남포동</a></div>
                                    <div><a href="">광안리</a></div><div><a href="">해운대</a></div><div><a href="">센텀시티</a></div><br>
                                    <div><a href="">송도</a></div><div><a href="">중구</a></div>
                                    <div><a href="">남구</a></div><div><a href="">강서구</a></div><div><a href="">서구</a></div><br>
                                    <div><a href="">북구</a></div><div><a href="">연제구</a></div>
                                    <div><a href="">동구</a></div><div><a href="">동래구</a></div><div><a href="">수영구</a></div><br>
                                    <div><a href="">영도구</a></div><div><a href="">사하구</a></div>
                                    <div><a href="">사상구</a></div><div><a href="">부산진구</a></div><div><a href="">금정구</a></div><br>
                                    <div><a href="">기장군</a></div>
                                </div>
                            </div>
                            <div class ="dropdown-sub-gu" v-if="area[4].display">
                                <div class ="area"><b-icon-geo-alt></b-icon-geo-alt>대구</div>
                                <div class ="gu-list">
                                    <div><a href="">동성로</a></div><div><a href="">달서구</a></div>
                                    <div><a href="">수성구</a></div><div><a href="">중구</a></div><div><a href="">남구</a></div><br>
                                    <div><a href="">서구</a></div><div><a href="">동구</a></div>
                                    <div><a href="">북구</a></div><div><a href="">달성군</a></div>
                                </div>
                            </div>
                            <div class ="dropdown-sub-gu" v-if="area[5].display">
                                <div class ="area"><b-icon-geo-alt></b-icon-geo-alt>광주</div>
                                <div class ="gu-list">
                                    <div><a href="">충장로</a></div><div><a href="">상무지구</a></div>
                                    <div><a href="">동구</a></div><div><a href="">서구</a></div><div><a href="">북구</a></div><br>
                                    <div><a href="">광산구</a></div><div><a href="">남구</a></div>
                                </div>
                            </div>
                            <div class ="dropdown-sub-gu" v-if="area[6].display">
                                <div class ="area"><b-icon-geo-alt></b-icon-geo-alt>울산</div>
                                <div class ="gu-list">
                                    <div><a href="">삼산동</a></div><div><a href="">성남동</a></div>
                                    <div><a href="">공업탑</a></div><div><a href="">중구</a></div><div><a href="">동구</a></div><br>
                                    <div><a href="">울주군</a></div><div><a href="">남구</a></div>
                                    <div><a href="">북구</a></div>
                                </div>
                            </div>
                            <div class ="dropdown-sub-gu" v-if="area[7].display">
                                <div class ="area"><b-icon-geo-alt></b-icon-geo-alt>대전</div>
                                <div class ="gu-list">
                                    <div><a href="">둔산동</a></div><div><a href="">은행동</a></div>
                                    <div><a href="">대덕구</a></div><div><a href="">동구</a></div><div><a href="">서구</a></div><br>
                                    <div><a href="">유성구</a></div><div><a href="">중구</a></div>
                                </div>
                            </div>
                            <div class ="dropdown-sub-gu" v-if="area[8].display">
                                <div class ="area"><b-icon-geo-alt></b-icon-geo-alt>세종</div>
                                <div class ="gu-list">
                                    <div><a href="">도담동</a></div><div><a href="">나성동</a></div>
                                    <div><a href="">새롬동</a></div><div><a href="">다정동</a></div><div><a href="">한솔동</a></div><br>
                                    <div><a href="">보람동</a></div><div><a href="">가람동</a></div>
                                    <div><a href="">어진동</a></div><div><a href="">소담동</a></div><div><a href="">반곡동</a></div><br>
                                    <div><a href="">종촌동</a></div><div><a href="">대평동</a></div>
                                    <div><a href="">아름동</a></div><div><a href="">고운동</a></div><div><a href="">조치원읍</a></div><br>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="btn-review-list">
                    <button class="btn-write-review" style="outline:0;"><router-link to="/feed/write" style="text-decoration : none; color : white;">리뷰쓰기</router-link></button>
                </div>
                <div class="btn-member-list">
                    <button class="btn-member" style="outline:0;"><b-icon-person-circle></b-icon-person-circle>
                    <span v-if="this.$store.state.isLogin"> {{this.$store.state.userInfo.data.nickname}}</span> 
                    <span v-else> 닉네임</span>
                    </button>
                    <div class="member-dropdown-content">
                        <div>
                            <div><button class="member-dropdown-btn"><router-link to="/accounts/profile" style="text-decoration : none; color : black;">회원정보</router-link></button></div>
                            <div><button class="member-dropdown-btn" v-on:click="logout()">로그아웃</button></div>
                        </div> 
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import store from '@/vuex/store.js'
    export default {
        methods:{
            toggle(city){
                for(var i = 0; i < this.area.length; i++){
                    if(city == this.area[i].name) this.area[i].display = !this.area[i].display;
                    else this.area[i].display = false;
                }
                city = !city;
            },
            logout(){
                store.dispatch('logout');
                this.$session.destroy();
                this.$bvModal.show('bv-modal-example');
                this.$router.go(0);
            }
        },
        data: () => {
            return {
            area: [
                {display : false, name : "seoul"},
                {display : false, name : "gyeonggido"},
                {display : false, name : "incheon"},
                {display : false, name : "busan"},
                {display : false, name : "daegu"},
                {display : false, name : "gwangju"},
                {display : false, name : "ulsan"},
                {display : false, name : "daejeon"},
                {display : false, name : "sejong"}    
            ]
            };
        }
    }
</script>