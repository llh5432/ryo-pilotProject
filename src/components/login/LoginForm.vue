<template>
  <b-container class="bv-d-md-down-none" id="containerSet">
    <b-row class="justify-content-md-center" id="rowSet">
      <div id="login">
        <b-card id="cardSet">
          <b-form @submit="loginCheck(loginForm.user_account, loginForm.user_password)">
            <label>로 그 인</label>
            <div>
              <b-form-input placeholder="Account" required type="text" v-model="loginForm.user_account"/>
              <br>
              <b-form-input placeholder="Password" required type="password" v-model="loginForm.user_password"/>
              <br>
            </div>
            <b-button-group>
              <b-button id="jBtnSet" to="/join" variant="outline-primary">회원가입</b-button>
              <b-button type="submit" variant="primary">로그인</b-button>
            </b-button-group>
          </b-form>
          <div align="end">
            <router-link to="/searchAccount">
              <label><a href="#">아이디 찾기</a></label><br>
            </router-link>
            <router-link to="/searchPassword">
              <label><a href="#">패스워드 찾기</a></label>
            </router-link>
          </div>
        </b-card>
      </div>
    </b-row>
  </b-container>
</template>

<script>
  import axios from 'axios'
  import Cookies from 'js-cookie'

  export default {
    name: "LoginForm",
    created() {
      this.pilotApi = axios.create({
        baseURL: "http://localhost:9090",
      })

    },
    data() {
      return {
        loginForm: {
          user_account: '',
          user_password: ''
        }
      }
    },
    methods: {
      loginCheck(user_account, user_password) {

        this.loginForm.user_account = user_account;
        this.loginForm.user_password = user_password;

        this.pilotApi.post(`/api/v1/logins/check`, this.loginForm)
            .then(response => {

              if (response.data != null){
                alert('로그인하였습니다.');

                // console.log(response)
                Cookies.set('token', response.data);
                this.$router.push("/userMain");


                let token = Cookies.get('token')

                let jwt = require('jsonwebtoken');

                const decoded = jwt.decode(token)
                console.log(decoded)
                const tokenUser = decoded.user;
                console.log(tokenUser)
              }

            }).catch(err => {
              Cookies.remove('token')
              console.log(err);
              alert(err.response.data.error_msg);
              return false;
            })
      }
    }
  }
</script>

<style scoped>
  #login {
    width: 400px;
    margin-top: auto;
    border: black solid;
  }

  #jBtnSet {
    text-justify: auto;
  }
</style>