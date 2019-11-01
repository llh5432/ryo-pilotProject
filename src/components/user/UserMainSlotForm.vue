<template>
  <div>
    <b-table
        :fields="fields"
        :items="slotForm"
        class="border-dark">

    </b-table>
    <div class="container align-content-end">
      <b-button align="end" @click="">주문하기</b-button>
    </div>

  </div>
</template>

<script>
  export default {
    name: "UserMainSlotForm",
    props: ['selectedItems'],
    data() {
      return {
        fields: [
          {key: 'product_id', label: 'product_id'},
          {key: 'menu', label: '메 뉴'},
          {key: 'price', label: '가 격 '},
          {key: 'menu_type', label: '타 입'},
          {key: 'quantity', label: '수 량'},
          {key: 'total', label: '총합'},
        ],
        items: [
        ],
      }
    },
    methods:{
      item(items) {
        return {
          product_id: items[0].product_id,
          menu: items[0].menu,
          price: items[0].price,
          menu_type: items[0].menu_type,
          quantity: items.length,
          total: items.reduce((acc, item) => acc + item.price, 0)
        }
      }
    },
    computed:{
      slotForm() {
        let groups = [];
        this.selectedItems.forEach(function (item) {
          if (item.product_id in groups == false) {
            groups[item.product_id] = []
          }

          groups[item.product_id].push(item);
        });

        return groups.map(this.item);
      }
    }

  }
</script>