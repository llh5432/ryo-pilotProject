<template>
  <div>
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
          {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
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

    <b-pagination :per-page="perPage"
                  :total-rows="rows"
                  align="center"
                  aria-controls="my-table"
                  v-model="currentPage"
    ></b-pagination>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: "AdminOrderListForm",
    created() {
      const pilotApi = axios.create({
        baseURL: "http://localhost:9090",
        headers: {
          'Authorization': "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwaWxvdC1wcm9qZWN0IiwidXNlciI6InVzZXIifQ.9WkAKFxO35XUG5_evhRqemxj8ce41WtMouJkps6iPfA"
        }
      });

      pilotApi.get("/api/v1/orders")
          .then(response => {
            this.items = response.data
          })
    },
    data() {
      return {
        perPage: 3,
        currentPage: 1,
        fields: [
          {key: 'order_id', label: 'Order_Id'},
          {key: 'user.user_account', label: 'User'},
          {key: 'order_total_price', label: 'Total'},
          {key: 'order_total_quantity', label: 'Quantity'},
          {key: 'order_details', label: 'Order_Detail'},
          {key: 'order_created_at', label: 'Order_Created_At'}
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