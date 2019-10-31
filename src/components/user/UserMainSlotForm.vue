<template>
  <div>
    <b-table :fields="fields" :items="items" foot-clone>
      <!-- A custom formatted data column cell -->
      <template v-slot:cell(name)="data">
        {{ data.value.first }} {{ data.value.last }}
      </template>

      <!-- A custom formatted header cell for field 'name' -->
      <template v-slot:head(name)="data">
        <span class="text-info">{{ data.label.toUpperCase() }}</span>
      </template>

      <!-- A custom formatted footer cell for field 'name' -->
      <template v-slot:foot(name)="data">
        <span class="text-danger">{{ data.label }}</span>
      </template>

      <!-- Default fall-back custom formatted footer cell -->
      <template v-slot:foot()="data">
        <i>{{ }}</i>
      </template>
    </b-table>
    <div class="container align-content-end">
      <b-button align="end">주문하기</b-button>
    </div>

  </div>
</template>

<script>
  export default {
    name: "MainBodyOrderListForm",
    data() {
      return {
        fields: [
          // A column that needs custom formatting
          {key: 'name', label: 'Product Name'},
          // A regular column
          'quantity',
          // A regular column
          'total'
        ],
        items: [
          {name: {first: 'KR', last: '김밥'}, total: 1500, quantity: 2},
          {name: {first: 'KR', last: '김치찌개'}, total: 5000, quantity: 3},
          {name: {first: 'CN', last: '짜장면'}, total: 3000, quantity: 3},
          {name: {first: 'JP', last: '돈코츠라멘'}, total: 5000, quantity: 1}
        ]
      }
    }
  }
</script>