import axios from 'axios';

export default axios.create({
  baseURL: 'http://localhost:8080/'
  // 업로드 시 아래 주소 사용
  //baseURL:'https://i3a307.p.ssafy.io:8080/'
})