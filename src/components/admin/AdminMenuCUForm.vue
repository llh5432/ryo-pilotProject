<template>
    <div class="overflow-auto">
    <b-card-header>
        <div>
            <h3>메뉴 관리</h3>
        </div>
    </b-card-header>
<!--        <p class="mt-3">Current Page: {{ currentPage }}</p>-->
        <b-card-body>
            <b-table
                    id="my-table"
                    :items="items"
                    :fields="fields"
                    :per-page="perPage"
                    :current-page="currentPage"
                    small
                    hover
            >
            <template v-slot:cell(menu)="items">
                <div sm="5">{{ items.item.menu }} <b-button class="small alert-danger">수 정</b-button></div>
            </template>

            </b-table>
            <b-button-group>
                <b-button class="btn-danger">삭 제</b-button>
                <b-button v-b-modal.modal-create class="alert-info">등 록</b-button>
            </b-button-group>
        </b-card-body>

        <footer>
            <b-pagination align="center"
                          v-model="currentPage"
                          :total-rows="rows"
                          :per-page="perPage"
                          aria-controls="my-table"
            ></b-pagination>
        </footer>
        <b-modal
                id="modal-create"
                title="메뉴 등록"
                v-model="show"
                @show="resetModal"
                @hidden="resetModal"
        >

            <b-form>
                <b-form-group
                        label="Type"
                        label-for="type-input"
                >
                    <b-form-select
                            id="type-input"
                            v-model="product.menu_type"
                            required
                    >
                        <option value="KR">KR</option>
                        <option value="JP">JP</option>
                        <option value="CN">CN</option>
                        <option value="PA">PA</option>
                        <option value="BU">BU</option>
                    </b-form-select>

                </b-form-group>

                <b-form-group
                        label="Menu"
                        label-for="name-input"
                >
                    <b-form-input
                            id="name-input"
                            ref="name"
                            v-model="product.menu"
                            required
                            placeholder="메뉴이름을 입력해주세요."
                    ></b-form-input>

                </b-form-group>

                <b-form-group
                        label="Price"
                        label-for="price-input"
                >
                    <b-form-input
                            id="price-input"
                            ref="price"
                            v-model="product.price"
                            placeholder="가격을 입력해주세요."
                            required
                    ></b-form-input>

                </b-form-group>

            </b-form>
            <template v-slot:modal-footer>
                <div class="w-100">
                    <b-button
                            @click="createProduct()"
                            variant="primary"
                            class="float-right"
                    >
                        등 록
                    </b-button>

                </div>
            </template>

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
                perPage: 8,
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
                    price:''
                },
                pilotApi: null
                ,show:false
            }
        },
        computed: {
            rows() {
                return this.items.length
            }
        },
        methods :{
            createProduct() {
                if(this.validateProduct()) {

                    this.pilotApi.post('/api/v1/products/create', this.product)
                        .then(res=> {
                            if (res.data.error_msg != null) {
                                alert(res.data.error_msg)
                                return false
                            }

                            console.log(res.data.product_id)
                            if (res.data.product_id != null){
                                alert('메뉴가 등록되었습니다.')

                            this.pilotApi.get('/api/v1/products')
                                .then(response => {

                                    this.items = response.data

                                })
                            }
                        })
                        .catch(err=>{
                            alert(err.response.data.error_msg)
                        })

                    this.show=false
                }

            },
            validateProduct(){
                if (this.product.menu == "") {
                    alert("메뉴가 비어있습니다!")
                    this.$refs.name.focus()
                    return false
                }
                if (this.product.price <= 0) {
                    alert('가격을 설정해주세요!')
                    this.$refs.price.focus()
                    return false
                }
                return true

            },
            resetModal() {
                this.product.menu_type = 'KR'
                this.product.menu = ''
                this.product.price = ''
            }

        }

    }
</script>