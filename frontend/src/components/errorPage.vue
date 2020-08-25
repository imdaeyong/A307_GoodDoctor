<template>
  <div class="user" id="join">
        <div class="wrapC">
            <h2>Error 발생 다시 시도하시기 바랍니다.</h2><br><br>
            <button class="btn btn--back btn--login" @click="goback">뒤로가기</button>
        </div>
       
    </div>
</template>

<script>
export default {
    name: "errorPage",
    methods : {
      moveLogin() {
        this.$router.push("/");
      },
      goback(){
        this.$router.go(-1)
      }
    }
}
</script>
