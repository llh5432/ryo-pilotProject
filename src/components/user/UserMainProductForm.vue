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
          <b-card-footer><b-button @click="soss(item)">담 기</b-button></b-card-footer>
        </b-card>
      </b-form-row>
    </b-card-group>
    <UserMainSlotForm :selected-items="selectedItems"></UserMainSlotForm>
  </div>
</template>

<script>
  import UserMainSlotForm from "@/components/user/UserMainSlotForm";
  import axios from "axios"
  export default {

    components: {UserMainSlotForm}
    ,created(){
      this.pilotApi = axios.create({
        baseURL:'http://localhost:9090',
        headers:{'Authorization': "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwaWxvdC1wcm9qZWN0IiwidXNlciI6InVzZXIifQ.9WkAKFxO35XUG5_evhRqemxj8ce41WtMouJkps6iPfA"
        }
      });

      this.pilotApi.get("/api/v1/products/lessThenPrice")
          .then(response => {
            this.items = response.data
          })
    },
    data(){
      return{
        items:[],
        selectedItems:[]
      }
    },
    methods:{
      soss(item){
        this.selectedItems.push(item)

      }
    },




  }
</script>

<style scoped>
  #pilot {
    width: 210px;
    height: 225px;
    margin-block-end: auto;
  }
</style>