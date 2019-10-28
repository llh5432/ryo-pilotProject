<template>
    <div class="overflow-auto">

<!--        <p class="mt-3">Current Page: {{ currentPage }}</p>-->
        <b-table
                id="my-table"
                :items="items"
                :per-page="perPage"
                :current-page="currentPage"
                small
        ></b-table>
        <div align="center">
            <b-button v-b-modal.modal-create-menu>등 록</b-button>

            <b-pagination align="center"
                          v-model="currentPage"
                          :total-rows="rows"
                          :per-page="perPage"
                          aria-controls="my-table"
            ></b-pagination>
        </div>

        <b-modal
                id="modal-create-menu"
                ref="modal"
                title="메뉴 등록"
                @ok="createProduct"

        >
            <form @submit="createProduct">
                <b-form-group
                        label="Type"
                        label-for="type-input"
                >
                    <b-form-select
                            id="type-input"
                            v-model="product.menu_type"
                            required
                    >
                        <option >Please select an option</option>
                        <option value="KR">KR</option>
                        <option value="JP">JP</option>
                        <option value="JP">CN</option>
                        <option value="JP">PA</option>
                        <option value="JP">BU</option>
                    </b-form-select>

                </b-form-group>

                <b-form-group
                        label="Menu"
                        label-for="name-input"
                >
                    <b-form-input
                            id="name-input"
                            v-model="product.menu"
                            required
                    ></b-form-input>

                </b-form-group>

                <b-form-group
                        label="Price"
                        label-for="price-input"
                >
                    <b-form-input
                            id="price-input"
                            v-model="product.price"
                            required
                    ></b-form-input>

                </b-form-group>
            </form>
        </b-modal>

    </div>
</template>

<script>
    import axios from 'axios'
    export default {
        name:"AdminMenuCUForm",
        created(){
            this.pilotApi = axios.create({
                baseURL: "http://localhost:9090",
                headers: {
                    'Authorization': "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwaWxvdC1wcm9qZWN0IiwidXNlciI6InVzZXIifQ.9WkAKFxO35XUG5_evhRqemxj8ce41WtMouJkps6iPfA"
                }
            });

            this.pilotApi.get("/api/v1/products")
                .then(response => {
                    this.items = response.data
                })

        },
        data() {
            return {
                perPage: 5,
                currentPage: 1,
                fields:[
                    'product_id',
                    'menu_type',
                    'menu',
                    'price',
                    'created_at',
                    'updated_at'
                ],
                items: [],
                product:{
                    menu_type:'KR',
                    menu:'',
                    price:0
                },
                pilotApi: null
            }
        },
        computed: {
            rows() {
                return this.items.length
            }
        },
        methods :{
            createProduct() {
                console.log('들어오는건가?')
                return this.pilotApi.post('/api/v1/products/create', this.product);

            }

        }

    }
</script>