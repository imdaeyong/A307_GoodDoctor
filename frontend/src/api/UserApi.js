/*
 User API 예시
 */

import http from '@/util/http-common';

const requestLogin = (data,callback,errorCallback) => {
    //백앤드와 로그인 통신하는 부분
    //console.log(callback);
    //console.log(data); //data.email, data.password
    //console.log(http.get(`/login?email=${data.email}&password=${data.password}`));
    // console.log(http.get('/account/login'));
    
    http.get(`/account/login`,{
        params:{
            email: data.email,
            password: data.password
        }
    }).then(result => {
        console.log(result);
        if (result.data.data == "success"){
            callback();
        } else {
            alert("너 틀림");
            console.log(errorCallback);
            errorCallback();
        }
    })
    //callback();
}

const requestJoin = (data,callback,errorCallback) => {
    //백앤드와 로그인 통신하는 부분
    console.log(data);
    http.post(`/account/signup`,data
    ).then(response => {
        console.log(response);
        if (response.data.data == "success"){
            callback();
        } else {
            console.log(errorCallback);
            errorCallback();
        }
    })
    //callback();
}

const UserApi = {
    requestJoin:(data,callback,errorCallback)=>requestJoin(data,callback,errorCallback),
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback)
    
}

export default UserApi