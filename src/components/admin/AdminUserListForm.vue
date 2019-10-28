<template>
    <b-container>
        <b-table
                id="my-table"
                :items="items"
                :fields="fields"
                :per-page="perPage"
                :current-page="currentPage"
        >
        </b-table>
        <b-pagination align="center"
                v-model="currentPage"
                :total-rows="rows"
                :per-page="perPage"
                aria-controls="my-table"
        ></b-pagination>
    </b-container>
</template>

<script>
    import axios from 'axios'

    export default {
        name:"AdminUserListForm",
        created(){
            const pilotApi = axios.create({
                baseURL: "http://localhost:9090",
                headers: {
                    'Authorization': "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwaWxvdC1wcm9qZWN0IiwidXNlciI6InVzZXIifQ.9WkAKFxO35XUG5_evhRqemxj8ce41WtMouJkps6iPfA"
                }
            });

            pilotApi.get("/api/v1/users")
                .then(response => {
                    this.items = response.data
                })
        },
        data() {
            return {
                perPage: 5,
                currentPage: 1,
                fields: [
                    { key: 'user_id', variant: 'primary' },
                    { key: 'user_account', variant: 'info'},
                            'user_email',
                            'user_name',
                            'user_created_at'
                ],
                items: []
            }
        },
        computed: {
            rows(){
                return this.items.length
            }
        }
    }

</script>

<style scoped>

</style>