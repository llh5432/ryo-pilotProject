<template>
    <b-modal
            id="updateModal"
            title="메뉴 수정"
            v-model="show"
    >

        <form ref="d">
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
                        v-model="product.price"
                        placeholder="가격을 입력해주세요."
                        required
                ></b-form-input>

            </b-form-group>
        </form>

        <template v-slot:modal-footer>
            <div class="w-100">
                <b-button
                        type="submit"
                        variant="primary"
                        class="float-right"
                        @click="createProduct()"
                >
                    수 정
                </b-button>
            </div>
        </template>
    </b-modal>
</template>

<script>
    export default {
        name: "ProductUpdateModal"
    }
</script>

<style scoped>

</style>