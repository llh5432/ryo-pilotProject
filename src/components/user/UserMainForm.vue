<template>

  <div class="container">
    <!--            <b-card no-body>-->

    <b-navbar toggleable="lg" type="dark" variant="success">
      <b-navbar-brand href="#">Ryo-Pilot-User-Page</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#">Link</b-nav-item>
          <b-nav-item disabled href="#">Disabled</b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">

          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template v-slot:button-content>
              <em>User</em>
            </template>
            <b-dropdown-item to="/adminMain">
              Admin Page
            </b-dropdown-item>
            <b-dropdown-item to="/">
              Sign out
            </b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>

    <div class="col-sm-12 jumbotron-fluid">
      <b-card no-body>
        <b-tabs card id="tabs" nav-wrapper pills vertical>
          <b-tab active title="전 체(All)">
            <b-row align-h="end">
              <b-card class="border-dark">
                <span>전 체</span>
              </b-card>
            </b-row>
            <hr>
            <UserMainProductForm></UserMainProductForm>
          </b-tab>
          <b-tab title="한 식(KR)">
            <b-row align-h="end">
              <b-card class="border-dark">
                <span>한 식</span>
              </b-card>
            </b-row>
            <hr>
            <UserMainProductForm></UserMainProductForm>
          </b-tab>
          <b-tab title="일 식(JP)">
            <b-row align-h="end">
              <b-card class="border-dark">
                <span>일 식</span>
              </b-card>
            </b-row>
            <hr>
            <UserMainProductForm></UserMainProductForm>
          </b-tab>
          <b-tab title="중 식(CN)">
            <b-row align-h="end">
              <b-card class="border-dark">
                <span>중 식</span>
              </b-card>
            </b-row>
            <hr>
            <UserMainProductForm></UserMainProductForm>
          </b-tab>
          <b-tab title="양 식(PA)">
            <b-row align-h="end">
              <b-card class="border-dark">
                <span>양 식</span>
              </b-card>
            </b-row>
            <hr>
            <UserMainProductForm></UserMainProductForm>
          </b-tab>
          <b-tab title="분 식(BU)">
            <b-row align-h="end">
              <b-card class="border-dark">
                <span>분 식</span>
              </b-card>
            </b-row>
            <hr>
            <UserMainProductForm></UserMainProductForm>
          </b-tab>
          <b-tab title="검  색(Search)">
            <UserMainSearchMenuForm></UserMainSearchMenuForm>

          </b-tab>
          <b-tab title="주문내역(Order)">
            <UserMainOrderListForm></UserMainOrderListForm>
          </b-tab>
        </b-tabs>
        <!--            </b-card>-->
      </b-card>
    </div>
  </div>
</template>

<script>
  import UserMainProductForm from "@/components/user/UserMainProductForm";
  import UserMainSearchMenuForm from "@/components/user/UserMainSearchMenuForm";
  import UserMainOrderListForm from "@/components/user/UserMainOrderListForm";

  export default {
    name: "UserMainForm",
    components: {UserMainOrderListForm, UserMainSearchMenuForm, UserMainProductForm},
  }
</script>

<style scoped>
  #tabs {

  }
</style>