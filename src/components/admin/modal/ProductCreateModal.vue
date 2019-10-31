<template>
    <b-modal
            id="createModal"
            title="메뉴 등록"
            v-model="show"
            @show="resetModal"
            @hidden="resetModal"
    >

        <form ref="d" @submit="">
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
                    등 록
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