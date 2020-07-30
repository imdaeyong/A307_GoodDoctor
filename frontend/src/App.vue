<template>
  <div id="app">
    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop no-close-on-esc>   
      <User/>
    </b-modal>
    <router-view/>
  </div>
</template>

<script>
import store from '../src/vuex/store.js'
import User from "../src/views/accounts/Login.vue";
import FeedModal from "../src/components/feed/FeedModal.vue";

export default {
  name: "app",
  components: {
    User
  },
  mounted(){
    if(!store.state.isLogin) this.$bvModal.show('bv-modal-example');
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
