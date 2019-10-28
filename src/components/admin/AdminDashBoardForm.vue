<template>
    <div>
        <div>
            <b-card-group deck>
                <b-card bg-variant="primary" text-variant="white" header="총 수익" class="text-center">
                    <b-row class="mb-4">
                        <b-col sm="6" class="text-sm-right"><b>수 익 : </b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>{{items.admin_total}}</b></b-col>
                    </b-row>
                </b-card>

                <b-card bg-variant="default" header="우수 회원" class="text-center">
                    <b-row class="mb-3">
                        <b-col sm="6" class="text-sm-right"><b>계 정 : </b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>{{items.admin_top_user.top_user_account}}</b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>구매액 : </b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>{{items.admin_top_user.total}}</b></b-col>
                    </b-row>
                </b-card>

                <b-card bg-variant="success" text-variant="white" header="우수 메뉴" class="text-center">
                    <b-row class="mb-4">
                        <b-col sm="6" class="text-sm-right"><b>우수 메뉴 : </b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>{{items.admin_top_menu.top_menu}}</b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>판매액 : </b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>{{items.admin_top_user.total}}</b></b-col>
                    </b-row>
                </b-card>
            </b-card-group>
        </div>
        <div class="mt-3">
            <b-card-group deck>
                <b-card bg-variant="info" text-variant="white" header="우수 메뉴타입" class="text-center">
                    <b-row class="mb-4">
                        <b-col sm="6" class="text-sm-right"><b>우수 장르 : </b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>{{items.admin_top_type.top_type}}</b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>판매액 : </b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>{{items.admin_top_type.total}}</b></b-col>
                    </b-row>
                </b-card>

                <b-card bg-variant="warning" text-variant="white" header="총 회원 수" class="text-center">
                    <b-row class="mb-4">
                        <b-col sm="6" class="text-sm-right"><b>회 원 : </b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>{{items.admin_user_count}} 명</b></b-col>
                    </b-row>
                </b-card>

                <b-card bg-variant="danger" text-variant="white" header="총 주문 수" class="text-center">
                    <b-row class="mb-4">
                        <b-col sm="6" class="text-sm-right"><b>주 문 : </b></b-col>
                        <b-col sm="6" class="text-sm-right"><b>{{items.admin_order_count}} 건</b></b-col>
                    </b-row>
                </b-card>
            </b-card-group>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    export default {
        name: "AdminDashBoardForm",
        created(){
            const pilotApi = axios.create({
                baseURL: "http://localhost:9090",
                headers: {
                    'Authorization': "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwaWxvdC1wcm9qZWN0IiwidXNlciI6InVzZXIifQ.9WkAKFxO35XUG5_evhRqemxj8ce41WtMouJkps6iPfA"
                }
            });

            pilotApi.get("/api/v1/orders/admin/dashBoard")
                .then(response => {
                    this.items = response.data

                })
        },
        data() {
            return {
                fields: [
                    'admin_total',
                    'admin_top_user',
                    'admin_top_menu',
                    'admin_top_type',
                    'admin_user_count',
                    'admin_order_count'
                ],
                items:[]
            }
        }
    }
</script>

<style scoped>

</style>