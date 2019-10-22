<template>
    <div>
        <b-table
                id="my-table"
                :items="items"
                :per-page="perPage"
                :current-page="currentPage"
                :fields="fields"
                striped
                small
                hover
                responsive="sm">
            <template v-slot:cell(show_details)="row">
                <b-button size="sm" @click="row.toggleDetails" class="mr-2">
                    {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
                </b-button>

                <!-- As `row.showDetails` is one-way, we call the toggleDetails function on @change -->
                <b-form-checkbox v-model="row.detailsShowing" @change="row.toggleDetails">
                    Details via check
                </b-form-checkbox>
            </template>

            <template v-slot:row-details="row">
                <b-card>
                    <b-row class="mb-2">
                        <b-col sm="3" class="text-sm-right"><b>Age:</b></b-col>
                        <b-col>{{ row.item.age }}</b-col>
                    </b-row>

                    <b-row class="mb-2">
                        <b-col sm="3" class="text-sm-right"><b>Is Active:</b></b-col>
                        <b-col>{{ row.item.isActive }}</b-col>
                    </b-row>

                    <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>
                </b-card>
            </template>
        </b-table>
        <b-pagination align="center"
                      v-model="currentPage"
                      :total-rows="rows"
                      :per-page="perPage"
                      aria-controls="my-table"
        ></b-pagination>
    </div>
</template>

<script>
    export default {
        name:"AdminOrderListForm",
        data() {
            return {
                perPage: 3,
                currentPage: 1,
                fields: ['order_id', 'first_name', 'last_name', 'show_details'],
                items: [
                    {isActive: true, age: 40, first_name: 'Dickerson', last_name: 'Macdonald'},
                    {isActive: false, age: 21, first_name: 'Larsen', last_name: 'Shaw'},
                    {
                        isActive: false,
                        age: 89,
                        first_name: 'Geneva',
                        last_name: 'Wilson',
                        // _showDetails: true
                    },
                    {isActive: true, age: 38, first_name: 'Jami', last_name: 'Carney'}
                ]
            }
        },
        computed: {
            rows() {
                return this.items.length
            }
        }
    }
</script>