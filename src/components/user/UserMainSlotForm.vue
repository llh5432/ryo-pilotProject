<template>
  <div>
    <b-table
        :fields="fields"
        :items="slotForm"
        class="border-dark"
    >
    </b-table>
    <div>
      <span>합계 : {{this.sumTotal}}</span>
    </div>
    <div class="container align-content-end">
      <b-button @click="orderProducts" align="end">주문하기</b-button>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import Cookies from 'js-cookie'

  export default {
    created() {
      this.pilotApi = axios.create({
        baseURL: "http://localhost:9090",
        headers: {
          'Authorization': `Bearer ${Cookies.get('token')}`
        }
      });

    },
    name: "UserMainSlotForm",
    props: ['selectedItems', 'sumTotal'],
    data() {
      return {
        fields: [
          {key: 'product_id', label: 'product_id'},
          {key: 'menu', label: '메 뉴'},
          {key: 'price', label: '가 격 '},
          {key: 'menu_type', label: '타 입'},
          {key: 'quantity', label: '수 량'},
        ],
        items: [],
        sum: 0,

      }
    },
    methods: {
      item(items) {
        return {
          product_id: items[0].product_id,
          menu: items[0].menu,
          price: items[0].price,
          menu_type: items[0].menu_type,
          quantity: items.length,
        }
      },
      orderProducts() {

        let token = Cookies.get('token');
        let jwt = require('jsonwebtoken');
        const decoded = jwt.decode(token);
        const userId = decoded.user_id

        let submitData = {
          "order_tuples": []
        } //빈 배열 생성

        console.log(this.selectedItems)
        this.selectedItems.forEach(item => {
          let order_tuple = {
            "product": {},
            "quantity": ""
          };
          const index = submitData["order_tuples"].findIndex(data => data.product.product_id == item.product_id);
          if (index == -1) {
            order_tuple.product.product_id = item.product_id;
            order_tuple.product.menu_type = item.menu_type;
            order_tuple.product.menu = item.menu;
            order_tuple.product.price = item.price;
            order_tuple.quantity = 1;
            submitData["order_tuples"].push(order_tuple)
          } else {
            submitData.order_tuples[index].quantity = submitData.order_tuples[index].quantity  + 1;
          }
        });


        this.pilotApi.post(`/api/v1/orders/${userId}`, submitData)
            .then(response => {
              if (response != null) {
                alert('주문하였습니다. ' + response)
                this.sumTotal = 0;
                this.selectedItems = [];
                return location.reload(true);
              }

            }).catch(err=> {
              alert(err.response.data.error_msg)
              return false
              });
      }
    },
    computed: {
      slotForm() {
        let groups = [];

        if (this.selectedItems != null) // 이게 없으면 에러가 발생 this.selectedItems에 담긴게 처음에 없으니까..
        this.selectedItems.forEach(function (item) {

          if (item.product_id in groups == false) {
            groups[item.product_id] = []
            // groups 안에 item.product_id가 있으면 빈 배열만 생성
          }
            // 푸쉬
          groups[item.product_id].push(item);
        });
        return groups.map(this.item);
      },
    }

  }
</script>