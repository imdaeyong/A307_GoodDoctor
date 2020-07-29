<template>
    <div class="feed-modal">
        <div class="feed-reply-modal">
            <div class="feed-top-modal">
                <img src= "../../assets/images/profile_default.png" alt="">
                <div class="user-info-modal">닉네임</div>
                <div class="user-hospital-modal">병원이름 <span>2020.07.13</span></div>
            </div>
            <div class="feed-card-modal">
                <img src= "../../assets/images/feed-sample.jpg" alt=""><br>
                <a href="">#새로나온차#미래내차</a><br>
                이번에 현대 자동차에서 새롭게 선보이게 된 자동차인<br>
                제네시스 G80이다.<br>
                <span>더보기...</span>
            </div>
        </div>
        <div class="feed-foot-modal">
            <div class="feed-reply-content">
                <div class="feed-reply">
                    <div>
                        <img src= "../../assets/images/profile_default.png" alt="">
                        <div class="user-info">긍정인</div><br>
                    </div><br>
                    <div class="reply">오 정말 좋은데요?</div>
                    <div class="reply-time"><span>3일전</span></div>
                </div>

                <div class="feed-reply">
                    <div>
                        <img src= "../../assets/images/profile_default.png" alt="">
                        <div class="user-info">불편러</div><br>
                    </div><br>
                    <div class="reply">아니 이 비싼걸 누가사냐</div>
                    <div class="reply-time"><span>4일전</span></div>
                </div>

                <div class="feed-reply">
                    <div>
                        <img src= "../../assets/images/profile_default.png" alt="">
                        <div class="user-info">Eknom</div><br>
                    </div><br>
                    <div class="reply">돈없으면 못사는거지뭐...</div>
                    <div class="reply-time"><span>5일전</span></div>
                </div>
            </div>
            <div class="feed-btn-list">
                <div class ="like"><button><b-icon-heart v-on:click="addLike()"></b-icon-heart></button></div>
                <div class ="reply"><button><b-icon-chat-square v-on:click="openReply()"></b-icon-chat-square></button></div>
                <div class ="share"><button><b-icon-reply v-on:click="addShare()"></b-icon-reply></button></div>
            </div>
            <div class ="reply-list">
                <img src= "../../assets/images/profile_default.png" alt="">
                <input type="text" class="reply-content" placeholder="댓글달기...">
                <button class="reply-submit">게시</button>
            </div>
        </div>
        
    </div>
</template>
<script>
const SERVER_URL="http://localhost:8080/"
import axios from "axios";
import store from '@/vuex/store.js'
export default {
    mounted(){
        axios.get(`${SERVER_URL}comments/${this.feedId}`).then(data => {

    })
  }
}
</script>