<template>
  <div>
    <b-card-header>
      <h3>전체 통계</h3>
    </b-card-header>
    <b-card-body>
      <div>
        <b-card-group deck>
          <b-card bg-variant="primary" class="text-center" header="총 수익" text-variant="white">
            <b-row class="mb-4 align-content-center">
              <b-col class="text-sm-right" sm="5"><b>수 익</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>{{dashBoard.admin_total}}</b></b-col>
            </b-row>
          </b-card>

          <b-card bg-variant="warning" class="text-center" header="총 회원 수" text-variant="white">
            <b-row class="mb-4">
              <b-col class="text-sm-right" sm="5"><b>회 원</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>{{dashBoard.admin_user_count}} 명</b></b-col>
            </b-row>
          </b-card>

          <b-card bg-variant="info" class="text-center" header="우수 메뉴타입" text-variant="white">
            <b-row class="mb-4">
              <b-col class="text-sm-right" sm="5"><b>우수 장르</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>{{dashBoard.admin_top_type.top_type}}</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>판매액</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>{{dashBoard.admin_top_type.total}}</b></b-col>
            </b-row>
          </b-card>

        </b-card-group>
      </div>
      <div class="mt-3">
        <b-card-group deck>

          <b-card bg-variant="danger" class="text-center" header="총 주문 수" text-variant="white">
            <b-row class="mb-4">
              <b-col class="text-sm-right" sm="5"><b>주 문</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>{{dashBoard.admin_order_count}} 건</b></b-col>
            </b-row>
          </b-card>

          <b-card bg-variant="default" class="text-center" header="우수 회원">
            <b-row class="mb-3">
              <b-col class="text-sm-right" sm="5"><b>계 정</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>{{ dashBoard.admin_top_user.top_user_account }}</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>구매액</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>{{dashBoard.admin_top_user.total}}</b></b-col>
            </b-row>
          </b-card>

          <b-card bg-variant="success" class="text-center" header="우수 메뉴" text-variant="white">
            <b-row class="mb-4">
              <b-col class="text-sm-right" sm="5"><b>우수 메뉴</b></b-col>
              <b-col class="text-sm-right" sm="6"><b>{{dashBoard.admin_top_menu.top_menu}}</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>판매액</b></b-col>
              <b-col class="text-sm-right" sm="5"><b>{{dashBoard.admin_top_menu.total}}</b></b-col>
            </b-row>
          </b-card>

        </b-card-group>
      </div>
    </b-card-body>
  </div>
</template>

<script>
  import axios from 'axios'
  import Cookies from 'js-cookie'
  export default {
    name: "AdminDashBoardForm",
    created() {
      const pilotApi = axios.create({
        baseURL: "http://localhost:9090",
        headers: {
          'Authorization': `Bearer ${Cookies.get('token')}`,
        }
      });

      pilotApi.get("/api/v1/orders/admin/dashBoard")
          .then(response => {
            this.dashBoard = response.data
            console.log(this.dashBoard)
            console.log(this.dashBoard.admin_top_user)
          })
    },
    data() {
      return {
        dashBoard: {
          admin_total: '',
          admin_top_user: '',
          admin_top_menu: '',
          admin_top_type: '',
          admin_user_count: '',
          admin_order_count: ''
        },
      }
    }
  }
</script>

<style scoped>
</style>