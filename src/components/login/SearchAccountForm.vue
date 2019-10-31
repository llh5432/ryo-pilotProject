<template>
  <b-container class="bv-d-md-down-none" id="containerSet">
    <b-row class="justify-content-md-center" id="rowSet">
      <div id="login">
        <b-card id="cardSet">
          <b-form @submit="searchAccount(searchForm.user_email, searchForm.user_name)">
            <label>Account 찾기</label>
            <b-form-input placeholder="Email" required type="text" v-model="searchForm.user_email"/>
            <br>
            <b-form-input placeholder="Name" required type="text" v-model="searchForm.user_name"/>
            <br>
            <b-button-group>
              <b-button to="/" variant="outline-primary">돌아가기</b-button>
              <b-button id="lBtnSet" type="submit" variant="outline-primary">찾 기</b-button>
            </b-button-group>
          </b-form>
        </b-card>
      </div>
    </b-row>
  </b-container>
</template>

<script>
  import axios from 'axios'

  export default {
    name: "SearchAccountForm",
    created() {
      this.pilotApi = axios.create({
        baseURL: "http://localhost:9090",
        headers: {'Content-Type': 'application/json'}
      })

      //this.$router.push('/aaaa')
    },
    data() {
      return {
        searchForm: {
          user_email: '',
          user_name: ''
        }
      }
    },
    methods: {
      searchAccount(user_email, user_name) {
        if (user_email == '' || user_name == '') {
          alert('폼을 다시 입력하세요');
          return false
        }
        // params: {} get방식에서 객체로 서버에 데이터를 보내고 싶을 때 사용
        this.pilotApi.get(`/api/v1/logins/search/account`, {
          params:
              {
                userEmail: user_email,
                userName: user_name
              }
        })
            .then(res => {
              alert("찾으신 계정은 : " + res.data + "입니다.")
              return this.$router.push("/")
            }).catch(err => {
          alert(err.response.data.error_msg)
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

  #jBtnSet {
    text-justify: auto;
  }
</style>