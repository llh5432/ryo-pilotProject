<template>

  <div class="container">
    <b-row align-h="end">
      <b-card class="border-secondary">
        주문내역
      </b-card>
    </b-row>
    <!--        <hr>--><br>
    <b-table
        :current-page="currentPage"
        :fields="fields"
        :items="items"
        :per-page="perPage"
        hover
        id="my-table"
        responsive="sm"
        small
        striped>

      <template v-slot:cell(order_details)="row">
        <b-button @click="row.toggleDetails" class="mr-2" size="sm">
          {{ row.detailsShowing ? 'Hide' : 'Show'}} Detail
        </b-button>

      </template>

      <template v-slot:row-details="row">
        <b-card>
          <b-row class="mb-2">
            <b-col class="text-sm-right" sm="3"><b>MenuType : </b></b-col>
            <b-col v-for="orderDetail in row.item.order_details">{{ orderDetail.product.menu_type }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col class="text-sm-right" sm="3"><b>Menu : </b></b-col>
            <b-col v-for="orderDetail in row.item.order_details">{{ orderDetail.product.menu }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col class="text-sm-right" sm="3"><b>Price : </b></b-col>
            <b-col v-for="orderDetail in row.item.order_details">{{ orderDetail.product.price }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col class="text-sm-right" sm="3"><b>Quantity : </b></b-col>
            <b-col v-for="orderDetail in row.item.order_details">{{ orderDetail.quantity }}</b-col>
          </b-row>
        </b-card>
      </template>
    </b-table>

    <b-pagination
        :per-page="perPage"
        :total-rows="rows"
        align="center"
        aria-controls="my-table"
        v-model="currentPage"
    ></b-pagination>

  </div>
</template>

<script>
  import axios from 'axios'
  import Cookies from 'js-cookie'
  export default {
    created() {
      const pilotApi = axios.create({
        baseURL: "http://localhost:9090",
        headers: {
          'Authorization': `Bearer ${Cookies.get('token')}`
        }
      });

      let token = Cookies.get('token');
      let jwt = require('jsonwebtoken');
      const decoded = jwt.decode(token);
      const tokenUserId = decoded.user_id;

      pilotApi.get(`/api/v1/orders/order/${tokenUserId}`)
          .then(response => {
            // console.log(response)
            this.items = response.data
          })


    },
    data() {
      return {
        perPage: 8,
        currentPage: 1,
        fields: [
          {key: 'order_id', label: '주문 아이디'},
          {key: 'user.user_account', label: '유저'},
          {key: 'order_total_price', label: '총합'},
          {key: 'order_total_quantity', label: '수량'},
          {key: 'order_details', label: '상세정보'},
          {key: 'order_created_at', label: '주문일시'}
        ],
        items: []
      }
    },
    computed: {
      rows() {
        return this.items.length
      }
    }
  }
</script>

<style scoped>

</style>