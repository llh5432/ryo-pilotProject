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
            label="Sort"
            label-align-sm="right"
            label-cols-sm="3"
            label-for="sortBySelect"
            label-size="sm"
        >
          <b-input-group size="sm">
            <b-form-select :options="sortOptions" class="w-75" id="sortBySelect" v-model="sortBy">
              <template v-slot:first>
                <option value="">-- none --</option>
              </template>
            </b-form-select>
            <b-form-select :disabled="!sortBy" class="w-25" size="sm" v-model="sortDesc">
              <option :value="false">Asc</option>
              <option :value="true">Desc</option>
            </b-form-select>
          </b-input-group>
        </b-form-group>
      </b-col>

      <b-col class="my-1" lg="6">
        <b-form-group
            class="mb-0"
            label="Initial sort"
            label-align-sm="right"
            label-cols-sm="3"
            label-for="initialSortSelect"
            label-size="sm"
        >
          <b-form-select
              :options="['asc', 'desc', 'last']"
              id="initialSortSelect"
              size="sm"
              v-model="sortDirection"
          ></b-form-select>
        </b-form-group>
      </b-col>

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
            <b-form-checkbox value="name">Name</b-form-checkbox>
            <b-form-checkbox value="age">Age</b-form-checkbox>
            <b-form-checkbox value="isActive">Active</b-form-checkbox>
          </b-form-checkbox-group>
        </b-form-group>
      </b-col>

      <b-col class="my-1" md="6" sm="5">
        <b-form-group
            class="mb-0"
            label="Per page"
            label-align-sm="right"
            label-cols-lg="3"
            label-cols-md="4"
            label-cols-sm="6"
            label-for="perPageSelect"
            label-size="sm"
        >
          <b-form-select
              :options="pageOptions"
              id="perPageSelect"
              size="sm"
              v-model="perPage"
          ></b-form-select>
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
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        :sort-direction="sortDirection"
        @filtered="onFiltered"
        show-empty
        small
        stacked="md"
    >
      <template v-slot:cell(name)="row">
        {{ row.value.first }} {{ row.value.last }}
      </template>

      <template v-slot:cell(actions)="row">
        <b-button @click="info(row.item, row.index, $event.target)" class="mr-1" size="sm">
          Info modal
        </b-button>
        <b-button @click="row.toggleDetails" size="sm">
          {{ row.detailsShowing ? 'Hide' : 'Show' }} Details
        </b-button>
      </template>

      <template v-slot:row-details="row">
        <b-card>
          <ul>
            <li :key="key" v-for="(value, key) in row.item">{{ key }}: {{ value }}</li>
          </ul>
        </b-card>
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
    <b-modal :id="infoModal.id" :title="infoModal.title" @hide="resetInfoModal" ok-only>
      <pre>{{ infoModal.content }}</pre>
    </b-modal>
  </b-container>
</template>

<script>
  import MainBodyOrderListForm from "@/components/user/UserMainSlotForm";

  export default {
    components: {MainBodyOrderListForm},
    data() {
      return {
        items: [
          {isActive: true, age: 40, name: {first: 'Dickerson', last: 'Macdonald'}},
          {isActive: false, age: 21, name: {first: 'Larsen', last: 'Shaw'}},
          {
            isActive: false,
            age: 9,
            name: {first: 'Mini', last: 'Navarro'},
            _rowVariant: 'success'
          },
          {isActive: false, age: 89, name: {first: 'Geneva', last: 'Wilson'}},
          {isActive: true, age: 38, name: {first: 'Jami', last: 'Carney'}},
          {isActive: false, age: 27, name: {first: 'Essie', last: 'Dunlap'}},
          {isActive: true, age: 40, name: {first: 'Thor', last: 'Macdonald'}},
          {
            isActive: true,
            age: 87,
            name: {first: 'Larsen', last: 'Shaw'},
            _cellVariants: {age: 'danger', isActive: 'warning'}
          },
          {isActive: false, age: 26, name: {first: 'Mitzi', last: 'Navarro'}},
          {isActive: false, age: 22, name: {first: 'Genevieve', last: 'Wilson'}},
          {isActive: true, age: 38, name: {first: 'John', last: 'Carney'}},
          {isActive: false, age: 29, name: {first: 'Dick', last: 'Dunlap'}}
        ],
        fields: [
          {key: 'name', label: 'Person Full name', sortable: true, sortDirection: 'desc'},
          {key: 'age', label: 'Person age', sortable: true, class: 'text-center'},
          {
            key: 'isActive',
            label: 'is Active',
            // eslint-disable-next-line no-unused-vars
            formatter: (value, key, item) => {
              return value ? 'Yes' : 'No'
            },
            sortable: true,
            sortByFormatted: true,
            filterByFormatted: true
          },
          {key: 'actions', label: 'Actions'}
        ],
        totalRows: 1,
        currentPage: 1,
        perPage: 5,
        pageOptions: [5, 10, 15],
        sortBy: '',
        sortDesc: false,
        sortDirection: 'asc',
        filter: null,
        filterOn: [],
        infoModal: {
          id: 'info-modal',
          title: '',
          content: ''
        }
      }
    },
    computed: {
      sortOptions() {
        // Create an options list from our fields
        return this.fields
            .filter(f => f.sortable)
            .map(f => {
              return {text: f.label, value: f.key}
            })
      }
    },
    mounted() {
      // Set the initial number of items
      this.totalRows = this.items.length
    },
    methods: {
      info(item, index, button) {
        this.infoModal.title = `Row index: ${index}`
        this.infoModal.content = JSON.stringify(item, null, 2)
        this.$root.$emit('bv::show::modal', this.infoModal.id, button)
      },
      resetInfoModal() {
        this.infoModal.title = ''
        this.infoModal.content = ''
      },
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