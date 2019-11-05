<template>

  <div class="container">


    <b-navbar toggleable="lg" type="dark" variant="success">
      <b-navbar-brand href="#">Ryo-Pilot-User-Page</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>

        <b-navbar-nav class="ml-auto">

          <b-nav-item-dropdown right>

            <template v-slot:button-content>
              <em>User</em>
            </template>

            <template v-if="adminPage === 'admin'">
            <b-dropdown-item to="/adminMain">
              Admin Page
            </b-dropdown-item>
            </template>

            <b-dropdown-item @click="logout">
              Sign out
            </b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>

    <div class="col-sm-12 jumbotron-fluid">
      <b-card no-body>
        <b-tabs card id="tabs" nav-wrapper pills vertical>

          <b-tab v-for="tab in tabs" v-bind:title="tab.korean + `(`+ tab.english + `)`" >
            <b-row align-h="end">
              <b-card class="border-dark">
                <span>{{tab.korean}}</span>
              </b-card>
            </b-row>
            <hr>
            <UserMainProductForm :tab="tab" @product="addSlot"></UserMainProductForm>
            <UserMainSlotForm :selected-items="selectedItems" :sum-total="sumTotal"></UserMainSlotForm>
          </b-tab>

          <b-tab title="검  색(Search)">
            <b-row align-h="end">
              <b-card class="border-secondary">
                검 색
              </b-card>
            </b-row>
            <hr>
            <UserMainSearchMenuForm></UserMainSearchMenuForm>
          </b-tab>

          <b-tab title="주문내역(Order)">
            <UserMainOrderListForm></UserMainOrderListForm>
          </b-tab>

        </b-tabs>
      </b-card>
    </div>
  </div>
</template>

<script>
  import UserMainProductForm from "@/components/user/UserMainProductForm";
  import UserMainSearchMenuForm from "@/components/user/UserMainSearchMenuForm";
  import UserMainOrderListForm from "@/components/user/UserMainOrderListForm";
  import UserMainSlotForm from "@/components/user/UserMainSlotForm";
  import Cookies from 'js-cookie'

  export default {
    name: "UserMainForm",
    components: {UserMainOrderListForm, UserMainSearchMenuForm, UserMainProductForm, UserMainSlotForm},
    data: function () {
      return {
        tabs : [
          {
            korean:'전체', english:'ALL'
          },
          {
            korean:'한식', english:'KR'
          },
          {
            korean:'일식', english:'JP'
          },
          {
            korean:'중식', english:'CN'
          },
          {
            korean:'양식', english:'PA'
          },
          {
            korean:'분식', english:'BU'
          },
        ],
        selectedItems:[],
        sumTotal: 0,
      }
    },
    methods:{
      addSlot(item){
        this.selectedItems.push(item)
        return this.sumTotal = this.selectedItems.reduce((acc, item) => acc + item.price, 0)
      },
      logout() {
        Cookies.remove('token');
        alert('로그아웃하셨습니다.');
        this.$router.push("/");
      },
    },
    computed:{
      adminPage() {
        let token = Cookies.get('token')

        let jwt = require('jsonwebtoken');

        const decoded = jwt.decode(token)
        console.log(decoded)
        const tokenUser = decoded.user;
        console.log(tokenUser)
        return tokenUser
      }
    }

  }
</script>

<style scoped>
</style>