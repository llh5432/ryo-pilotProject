<template>
  <b-container>
    <b-card-header>
      <span><h3>전체회원</h3></span>
    </b-card-header>

    <b-card-body>
      <b-table
          :current-page="currentPage"
          :fields="fields"
          :items="items"
          :per-page="perPage"
          id="my-table"
      >
      </b-table>
    </b-card-body>
    <b-card-footer>
      <b-pagination :per-page="perPage"
                    :total-rows="rows"
                    align="center"
                    aria-controls="my-table"
                    v-model="currentPage"
      ></b-pagination>
    </b-card-footer>
  </b-container>
</template>

<script>
  import axios from 'axios'
  import Cookies from 'js-cookie'
  export default {
    name: "AdminUserListForm",
    created() {
      const pilotApi = axios.create({
        baseURL: "http://localhost:9090",
        headers: {
          'Authorization': `Bearer ${Cookies.get('token')}`,
        }
      });

      pilotApi.get("/api/v1/users")
          .then(response => {
            this.items = response.data
          })
    },
    data() {
      return {
        perPage: 5,
        currentPage: 1,
        fields: [
          {key: 'user_id', variant: 'primary'},
          {key: 'user_account', variant: 'info'},
          'user_email',
          'user_name',
          'user_created_at'
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