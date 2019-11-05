<template>
  <div class="container">
    <b-card-group>
      <b-form-row class="pre-scrollable">
        <b-card
            v-for="item in items"
            id="pilot"
            class="border-dark"
        >
          <b-card-header>{{item.menu}}</b-card-header>
          <b-card-body>{{item.price}} 원</b-card-body>
          <b-card-footer><b-button @click="postProduct(item)">담 기</b-button></b-card-footer>
        </b-card>
      </b-form-row>
    </b-card-group>
  </div>
</template>

<script>
  import axios from "axios"
  import Cookies from "js-cookie"
  export default {

    props: ['tab']
    ,created(){
      this.pilotApi = axios.create({
        baseURL:'http://localhost:9090',
        headers:{
          'Authorization': `Bearer ${Cookies.get('token')}`,
        }
      });

      let type = this.tab.english

      if (type == "ALL")
      this.pilotApi.get("/api/v1/products/lessThenPrice")
          .then(response => {
            this.items = response.data
          });

      if(type != "ALL")
        this.pilotApi.get(`/api/v1/products/menuType?menuType=${type}`)
          .then(response => {
            this.items = response.data
          });

    },
    data(){
      return{
        items:[],

      }
    },
    methods:{
      postProduct(item){
        this.$emit("product", item)
      }
      //$emit 상위클래스에게 해당 키값의 데이터가 변했다는걸 알려줌 (키, 밸류)
    }
  }
</script>

<style scoped>
  #pilot {
    width: 210px;
    height: 225px;
    margin-block-end: auto;
  }
</style>