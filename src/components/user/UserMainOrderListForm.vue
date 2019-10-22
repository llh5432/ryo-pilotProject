<template>

    <div class="container">
        <b-row align-h="end">
            <b-card class="border-secondary">
                주문내역
           </b-card>
        </b-row>
<!--        <hr>--><br>
        <b-table
                id="my-table"
                :per-page="perPage"
                :current-page="currentPage"
                small
                hover
                :items="items"
                :fields="fields"
                :tbody-tr-class="rowClass"></b-table>
        <b-pagination
                align="center"
                v-model="currentPage"
                :total-rows="rows"
                :per-page="perPage"
                aria-controls="my-table"
        ></b-pagination>
    </div>

</template>

<script>
    export default {
        data() {
            return {
                perPage: 4,
                currentPage: 1,
                fields: ['first_name', 'last_name', 'age'],
                items: [
                    { age: 40, first_name: 'Dickerson', last_name: 'Macdonald', status: 'awesome' },
                    { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
                    { age: 89, first_name: 'Geneva', last_name: 'Wilson' },
                    { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
                    { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
                    { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
                    { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
                    { age: 21, first_name: 'Larsen', last_name: 'Shaw' }
                ]
            }
        },
        methods: {
            // eslint-disable-next-line no-unused-vars
            rowClass(item, type) {
                if (!item) return
                if (item.status === 'awesome') return 'table-success'
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