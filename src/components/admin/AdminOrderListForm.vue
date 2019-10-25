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
            <template v-slot:cell(test)="data">
                <span>{{data.item.user.user_account + ',' + data.item.user.user_email}}</span>
            </template>


            <template v-slot:cell(order_details)="row">
                <b-button size="sm" @click="row.toggleDetails" class="mr-2">
                    {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
                </b-button>

                <!-- As `row.showDetails` is one-way, we call the toggleDetails function on @change -->
<!--                <b-form-checkbox v-model="row.detailsShowing" @change="row.toggleDetails">-->
<!--                    Details via check-->
<!--                </b-form-checkbox>-->
            </template>

            <template v-slot:row-details="row">
                <b-card>
                    <b-row class="mb-2">
                        <b-col v-for="orderDetail in row.item.order_details">{{ orderDetail.product.menu_type }}</b-col>
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
    import axios from 'axios'

    export default {
        name:"AdminOrderListForm",
        created(){
            const pilotApi = axios.create({
                baseURL: "http://localhost:9090",
                headers: {
                    'Authorization': "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwaWxvdC1wcm9qZWN0IiwidXNlciI6InVzZXIifQ.9WkAKFxO35XUG5_evhRqemxj8ce41WtMouJkps6iPfA"
                }
            });

            pilotApi.get("/api/v1/orders")
                .then(response => {
                    this.items = response.data
                    console.log(response.data)
                })
        },
        data() {
            return {
                perPage: 3,
                currentPage: 1,
                fields: [
                    { key: 'test', label: 'test1123123' },
                    'order_id',
                    { key: 'user.user_account', label: 'user'},
                    'order_total_price',
                    'order_total_quantity',
                    'order_details',
                    'order_created_at'
                ],
                items: []
            }
        },
        computed: {
            rows() {

                return this.items.length
            },
            test(){
                for(let i in this.items){
                    console.log(this.items[i])
                }
            }
        }
    }
</script>

<style scoped>

</style>