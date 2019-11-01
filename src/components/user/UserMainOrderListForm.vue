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
        :tbody-tr-class="rowClass"
        hover
        id="my-table"
        small></b-table>
    <b-pagination
        :per-page="perPage"
        :total-rows="rows"
        align="center"
        aria-controls="my-table"
        v-model="currentPage"
    ></b-pagination>
<!--    <MainBodyOrderListForm></MainBodyOrderListForm>-->
  </div>
</template>

<script>
  export default {
    data() {
      return {
        perPage: 8,
        currentPage: 1,
        fields: ['first_name', 'last_name', 'age'],
        items: []
      }
    },
    methods: {
      // eslint-disable-next-line no-unused-vars
      rowClass(item, type) {
        if (!item) return
        if (item.status === 'awesome') return 'table-success'
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