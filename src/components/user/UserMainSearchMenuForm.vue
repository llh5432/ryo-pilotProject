<template>
  <b-container>
    <!-- User Interface controls -->
    <b-row align-h="end">
      <b-card class="border-secondary">
        검 색
      </b-card>
    </b-row>
    <hr>
    <b-row>

      <b-col class="my-1" lg="6">
        <b-form-group
            class="mb-0"
            label="Filter"
            label-align-sm="right"
            label-cols-sm="3"
            label-for="filterInput"
            label-size="sm"
        >
          <b-input-group size="sm">
            <b-form-input
                id="filterInput"
                placeholder="Type to Search"
                type="search"
                v-model="filter"
            ></b-form-input>
            <b-input-group-append>
              <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-form-group>
      </b-col>

      <b-col class="my-1" lg="6">
        <b-form-group
            class="mb-0"
            description="Leave all unchecked to filter on all data"
            label="Filter On"
            label-align-sm="right"
            label-cols-sm="3"
            label-size="sm">
          <b-form-checkbox-group class="mt-1" v-model="filterOn">
            <b-form-checkbox value="menu">Menu</b-form-checkbox>
            <b-form-checkbox value="price">price</b-form-checkbox>
            <b-form-checkbox value="menu_type">MenuType</b-form-checkbox>
          </b-form-checkbox-group>
        </b-form-group>
      </b-col>

      <b-col class="my-1" md="6" sm="7">

      </b-col>
    </b-row>

    <!-- Main table element -->
    <b-table
        :current-page="currentPage"
        :fields="fields"
        :filter="filter"
        :filterIncludedFields="filterOn"
        :items="items"
        :per-page="perPage"
        @filtered="onFiltered"
        show-empty
        small
        stacked="md"
        :sort-by.sync="sortBy"
    >
      <template v-slot:cell(select)="items">
        <b-button @click="">담 기</b-button>
      </template>

    </b-table>

    <b-pagination
        :per-page="perPage"
        :total-rows="totalRows"
        align="center"
        class="my-0"
        v-model="currentPage"
    ></b-pagination>

    <MainBodyOrderListForm></MainBodyOrderListForm>


    <!-- Info modal -->
<!--    <b-modal :id="infoModal.id" :title="infoModal.title" @hide="resetInfoModal" ok-only>-->
<!--      <pre>{{ infoModal.content }}</pre>-->
<!--    </b-modal>-->
  </b-container>
</template>

<script>
  import MainBodyOrderListForm from "@/components/user/UserMainSlotForm";
  import axios from "axios"

  export default {
    components: {MainBodyOrderListForm},
    created(){
      this.pilotApi = axios.create({
        baseURL:'http://localhost:9090',
        headers:{
          'Authorization': "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwaWxvdC1wcm9qZWN0IiwidXNlciI6InVzZXIifQ.9WkAKFxO35XUG5_evhRqemxj8ce41WtMouJkps6iPfA"
        }
      });

      this.pilotApi.get("/api/v1/products")
          .then(response => {
            this.items = response.data
            this.totalRows = this.items.length
          })
    },
    data() {
      return {
        items: [],
        fields: [
          'menu_type',
          'menu',
          {key:'price', sortable:true},
          'select'
        ],
        selected: [],
        totalRows: 1,
        currentPage: 1,
        perPage: 5,
        filter: null,
        filterOn: [],
        sortBy: 'price'
      }
    },
    computed: {
      totalRows() {
        return this.items.length
      }
    },
    mounted() {
      // Set the initial number of items
      this.totalRows = this.items.length
    },
    methods: {

      onFiltered(filteredItems) {
        // Trigger pagination to update the number of buttons/pages due to filtering
        this.totalRows = filteredItems.length
        this.currentPage = 1
      }

    }
  }
</script>

<style scoped>

</style>