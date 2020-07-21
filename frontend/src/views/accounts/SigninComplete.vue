<template>
    <div class="user" id="join">
        <div class="wrapC">
            <h2>회원가입이 완료 되었습니다~!!</h2>
            <img src="../../assets/images/welcome.png" alt="">
             <button class="btn btn--back btn--login" @click="moveLogin">Home</button>
        </div>
    </div>
</template>

<script>
import http from '@/util/http-common';

export default {
    name: "JoinComplete",

    methods : {
        moveLogin() {
            this.$router.push("/");
        }

    }
}
</script>

<style scoped>
</style>