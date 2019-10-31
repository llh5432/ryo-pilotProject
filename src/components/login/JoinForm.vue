<template>
  <b-container class="bv-d-md-down-none" id="containerSet">

    <b-row class="justify-content-md-center" id="rowSet">
      <div id="login">
        <b-card id="cardSet">
          <form id="formSet" @submit="createApi()">
            <label>간단한 회원가입</label>
            <div id="formBodySet">
              <b-form-input placeholder="Account" type="text" required v-model="User.user_account"/>
              <br>
              <b-form-input placeholder="Password" type="password" required v-model="User.user_password"/>
              <br>
              <b-form-input placeholder="Email" type="email" required v-model="User.user_email"/>
              <br>
              <b-form-input placeholder="Name" type="text" required v-model="User.user_name"/>
              <br>
            </div>
            <div id="formFootSet">
              <b-button-group>
                <b-button to="/" variant="outline-primary">돌아가기</b-button>
                <b-button type="submit" variant="outline-primary">가입</b-button>
              </b-button-group>
            </div>
          </form>
        </b-card>
      </div>
    </b-row>
  </b-container>
</template>

<script>
  import axios from 'axios'

  export default {
    name: "JoinForm",
    created() {
      this.pilotApi = axios.create({
        baseURL: "http://localhost:9090"
      })

    },
    data() {
      return {
        User: {
          user_account: '',
          user_password:'',
          user_email:'',
          user_name:''
        }
      }
    },
    methods: {
      createApi() {
        console.log(this.User)
        this.pilotApi.post("/api/v1/logins/create", this.User)
            .then(res => {
              if (res != null)
                alert('회원가입을 축하합니다!');
                this.$router.push("/")
            }).catch(err => {
              alert(err.response.data.error_msg);
            }
        )

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

  #formSet {
    text-align: center;
  }
</style>