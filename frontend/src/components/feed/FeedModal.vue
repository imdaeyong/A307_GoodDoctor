<template>
    <div class="feed-modal">
        <div class="feed-reply-modal">
            <div class="feed-top-modal">
                <img src= "../../assets/images/profile_default.png" alt="">
                <div class="user-info-modal">닉네임</div>
                <div class="user-hospital-modal">병원이름 <span>2020.07.13</span></div>
            </div>
            <div class="feed-card-modal">
                <img src= "../../assets/images/feed/1.png" alt=""><br>
                <a href="">#진료잘봄#호감</a><br>
                이번에 현대 자동차에서 새롭게 선보이게 된 자동차인<br>
                제네시스 G80이다.<br>
                <span>더보기...</span>
            </div>
        </div>
        <div class="feed-foot-modal">
            <div class="feed-reply-content">
                <div v-for="reply in replys" v-bind:key="reply.id">
                    <div class="feed-reply">
                        <div>
                            <img src= "../../assets/images/profile_default.png" alt="">
                            <div class="user-info">긍정인</div><br>
                        </div><br>
                        <div class="reply">{{reply.content}}</div>
                        <div class="reply-time"><span>3일전</span></div>
                    </div>
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
import axios from "axios";
import store from '@/vuex/store.js'
import http from '@/util/http-common'
export default {
    data: () => {
        return {
        replys : []
        }
    },
    mounted(){
        http.get(`comments/${store.state.feedInfo}`).then(data => {
            console.log(data.data);
            this.replys = data.data; //해당 댓글 정보들을 가져온다.
        })
    }
  
}
</script>