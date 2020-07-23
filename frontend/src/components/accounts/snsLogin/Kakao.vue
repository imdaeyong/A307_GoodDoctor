<template>

    <div id="app">
        <KakaoLogin
            api-key="a0edf003b12f290d0a04dd23ec8b921b"
            image="kakao_login_btn_small"
            :on-success="onSuccess"
            :on-failure="onFailure"/>
    </div>

</template>

<script>
    const SERVER_URL = "http://localhost:8080/"
    import axios from 'axios'
    import KakaoLogin from 'vue-kakao-login'

    let onSuccess = (data) => {

        // console.log(data) console.log(data.access_token)

        axios
            .get(`${SERVER_URL}account/kakao?access_token=${data.access_token}`)
            .then((response) => {
                // console.log(response.data);
                
                const userInfo = {
                    email : response.data.email,
                    nickname : response.data.nickname,
                    gender : response.data.gender
                }
                
                console.log(userInfo);
                // console.log("success")
                alert("로그인 성공");
            })
            
    }
    let onFailure = (data) => {
        console.log(data)
        console.log("failure")
    }
    export default {
        name: 'App',
        components: {
            KakaoLogin
        },
        methods: {
            onSuccess,
            onFailure
        },
    }
</script>
