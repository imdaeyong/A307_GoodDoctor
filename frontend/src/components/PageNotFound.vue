<template>
  <div>
    <h1>Page Not Found</h1>
    <button @click="goToMain">메인으로</button>
  </div>
</template>

<script>
export default {
    name: "pagenotfound",
    methods: {
        goToMain() {
            this.$router.push('/feed/main')
        }
    }
}
</script>

<style>

</style>