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

                <!-- ref="이름" 밑에서 받아 쓸 수 있음               -->
                <template slot="product_id" slot-scope="data">
                    <span @click="onRowSelected(data.items.product_id)">test</span>
                </template>

                <template v-slot:cell(update&delete)="items">
                    <b-button-group>
                        <b-button class="small alert-danger" @click="test(items.item)">수 정</b-button>
                        <b-button class="small btn-danger" @click="deleteApi(items.item.product_id)">삭 제</b-button>
                    </b-button-group>
                </template>

            </b-table>

            <b-button v-b-modal.modal-create class="alert-info">등 록</b-button>
        </b-card-body>

        <b-card-footer>
            <b-pagination align="center"
                          v-model="currentPage"
                          :total-rows="rows"
                          :per-page="perPage"
                          aria-controls="my-table"
            ></b-pagination>
        </b-card-footer>

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

        <b-modal
                id="updateModal"
                title="메뉴 수정"
                v-model="modalShow"
        >

            <form>
                <b-form-group
                        label="Type"
                        label-for="updateSelect"
                >
                    <b-form-select
                            id="updateSelect"
                            v-model="productForm.menu_type"
                    >
                        <option>{{productForm.menu_type}}</option>
                        <option value="KR">KR</option>
                        <option value="JP">JP</option>
                        <option value="CN">CN</option>
                        <option value="PA">PA</option>
                        <option value="BU">BU</option>
                    </b-form-select>
                </b-form-group>

                <b-form-group
                        label="Menu"
                        label-for="updateName"
                >
                    <b-form-input
                            id="updateName"
                            v-model="productForm.menu"
                            placeholder="메뉴를 입력해주세요."
                    ></b-form-input>
                </b-form-group>

                <b-form-group
                        label="Price"
                        lable-for="updatePrice"
                >
                    <b-form-input
                            id="updatePrice"
                            v-model="productForm.price"
                            placeholder="가격을 정해주세요."
                    ></b-form-input>
                </b-form-group>

            </form>

            <template v-slot:modal-footer="productForm">
                <div class="w-100">
                    <b-button
                            @click="updateProduct(productForm)"
                            variant="primary"
                            class="float-right"
                    >
                        수 정
                    </b-button>

                </div>
            </template>
        </b-modal>

    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "AdminMenuCUForm",
        created() {
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
                fields: [
                    'product_id',
                    'menu_type',
                    'menu',
                    'price',
                    'created_at',
                    'updated_at',
                    'update&delete'
                ],
                items: [],
                updateProductId:'',
                productForm:{
                    menu:'',
                    menu_type: '',
                    price:'',
                },
                product: {
                    menu_type: 'KR',
                    menu: '',
                    price: ''
                },
                pilotApi: null,
                show: false,
                selected: [],
                modalShow: false
            }
        },
        computed: {
            rows() {
                return this.items.length
            }
        },
        methods: {
            createProduct() {
                if (this.validateProduct()) {

                    this.pilotApi.post('/api/v1/products/create', this.product)
                        .then(res => {
                            if (res.data.error_msg != null) {
                                alert(res.data.error_msg)
                                return false
                            }

                            console.log(res.data.product_id)
                            if (res.data.product_id != null) {
                                alert('메뉴가 등록되었습니다.')
                                this.loadCurrentPage()
                            }
                        })
                        .catch(err => {
                            alert(err.response.data.error_msg)
                        })

                    this.show = false
                }

            },
            updateProduct(res) {
                if (res == null) {
                    alert('널일수가없지')
                } else {
                    console.log(this.updateProductId, this.productForm)
                    this.pilotApi.put(`/api/v1/products/update/${this.updateProductId}`,this.productForm)
                        .then(response => {

                            if (response != null) {
                                console.log(response)
                                alert('수정하였습니다.')
                                this.loadCurrentPage()
                            }
                        })
                        .catch(err => {
                            alert(err.response.data.error_msg)
                        })
                    this.modalShow = false
                }
            },
            deleteApi(res) {
                console.log(res)
                if (confirm("정말 삭제할겁니까? ") == true){
                    this.pilotApi.delete(`/api/v1/products/delete/${res}`)
                        .then(response => {
                            if (response != null) {
                                this.loadCurrentPage()
                                alert("해당 데이터를 삭제했습니다.");
                            }
                        })
                } else {
                    return false
                }
            },
            validateProduct() {
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
            },
            loadCurrentPage(){

                this.pilotApi.get('/api/v1/products')
                    .then(response => {
                        this.items = response.data

                    })
            },
            test(res){
                console.log(res)
                this.modalShow = true
                this.updateProductId= res.product_id
                this.productForm.menu_type = res.menu_type
                this.productForm.menu = res.menu
                this.productForm.price = res.price
            },

        }

    }
</script>