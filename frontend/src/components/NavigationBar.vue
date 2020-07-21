<template>
    <div class="nav-bar">
        <div class="header">
            <div class="header-list">
                <button class="btn-home" style="outline:0;">굿 닥터</button>
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
                            <div class ="" v-if="area.seoul">
                                <div><b-icon-geo-alt></b-icon-geo-alt>서울</div>
                                강남구 강서구 송파구
                            </div>
                            <div class ="" v-if="area.gyeonggido">
                                <div><b-icon-geo-alt></b-icon-geo-alt>경기</div>
                                강남구 강서구 송파구
                            </div>
                            <div class ="" v-if="area.incheon">
                                <div><b-icon-geo-alt></b-icon-geo-alt>인천</div>
                                강남구 강서구 송파구
                            </div>
                            <div class ="" v-if="area.busan">
                                <div><b-icon-geo-alt></b-icon-geo-alt>부산</div>
                                강남구 강서구 송파구
                            </div>
                            <div class ="" v-if="area.daegu">
                                <div><b-icon-geo-alt></b-icon-geo-alt>대구</div>
                                강남구 강서구 송파구
                            </div>
                            <div class ="" v-if="area.gwangju">
                                <div><b-icon-geo-alt></b-icon-geo-alt>광주</div>
                                강남구 강서구 송파구
                            </div>
                            <div class ="" v-if="area.ulsan">
                                <div><b-icon-geo-alt></b-icon-geo-alt>울산</div>
                                강남구 강서구 송파구
                            </div>
                            <div class ="" v-if="area.daejeon">
                                <div><b-icon-geo-alt></b-icon-geo-alt>대전</div>
                                강남구 강서구 송파구
                            </div>
                            <div class ="" v-if="area.sejong">
                                <div><b-icon-geo-alt></b-icon-geo-alt>세종</div>
                                강남구 강서구 송파구
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <button class="btn-write-review" style="outline:0;">리뷰쓰기</button>
            <button class="btn-member" style="outline:0;"><b-icon-person-circle></b-icon-person-circle></button>
        </div>
    </div>
</template>

<script>
    export default {
        methods:{
            toggle(city){
                console.log(city);
                console.log(this.area.length);
                for(var i = 0; i < this.area.length; i++){
                    if(city == this.area[i].name && city == "seoul") this.area[i].seoul = !this.area[i].seoul;
                }
                
                city = !city;
                //this.area.seoul = !this.area.seoul;
            }
        },
        data: () => {
            return {
            area: [
                {seoul: false, name: "seoul"},
                {gyeonggido: false, name: "gyeonggido"},
                {incheon: false, name: "incheon"},
                {busan: false, name: "busan"},
                {daegu: false, name: "daegu"},
                {gwangju: false, name: "gwangju"},
                {ulsan: false, name: "ulsan"},
                {daejeon: false, name: "daejeon"},
                {sejong: false, name: "sejong"}
            ]

            };
        }
    }
</script>