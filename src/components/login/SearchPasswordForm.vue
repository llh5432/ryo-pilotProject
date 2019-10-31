<template>
  <b-container class="bv-d-md-down-none" id="containerSet">
    <b-row class="justify-content-md-center" id="rowSet">
      <div id="login">
        <b-card id="cardSet">
          <b-form @submit="searchPassword(userAccount, userEmail)">
            <label>Password 찾기</label>
            <b-form-input placeholder="Account" type="text" v-model="userAccount" required/>
            <br>
            <b-form-input placeholder="Email" type="email" v-model="userEmail" required/>
            <br>
            <b-button-group>
              <b-button to="/" variant="outline-primary">돌아가기</b-button>
              <b-button type="submit">찾 기</b-button>
            </b-button-group>
          </b-form>
        </b-card>
      </div>
    </b-row>
  </b-container>
</template>

<script>
  import axios from "axios"
  export default {
    name: "SearchPasswordForm",
    created(){
      this.pilotApi = axios.create({
        baseURL:'http://localhost:9090',
        headers: {'Content-Type': 'application/json'}
      })

    },
    data() {
      return {

          userAccount:'',
          userEmail:''

      }
    },
    methods: {
      searchPassword(userAccount, userEmail) {

        this.pilotApi.get(`/api/v1/logins/search/password?userAccount=${userAccount}&userEmail=${userEmail}`)
            .then(res => {
              alert('찾으신 패스워드는 : ' + res.data + '입니다.')
              return this.$router.push("/")
            }).catch(err => {
              alert(err.response.data.error_msg);
          return false
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

  #lBtnSet {
    text-justify: auto;
  }
</style>