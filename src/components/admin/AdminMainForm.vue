<template>
    <b-container>
       <b-navbar toggleable="lg" type="dark" variant="dark">
           <b-navbar-nav>
               <b-navbar-brand to="/adminMain">Ryo-Pilot-Admin-Page</b-navbar-brand>

               <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

                 <b-collapse id="nav-collapse" is-nav>
                   <b-navbar-nav>
                       <b-nav-item href="#">Link</b-nav-item>
                       <b-nav-item href="#" disabled>Disabled</b-nav-item>
                   </b-navbar-nav>


                   <b-navbar-nav class="ml-auto">

                       <b-nav-item-dropdown right>
                           <!-- Using 'button-content' slot -->
                           <template v-slot:button-content>
                               <em>Admin</em>
                           </template>
                           <b-dropdown-item to="/userMain">
                               Home Page
                           </b-dropdown-item>
                           <b-dropdown-item to="/">
                               Sign out
                           </b-dropdown-item>
                       </b-nav-item-dropdown>
                   </b-navbar-nav>
               </b-collapse>

           </b-navbar-nav>
       </b-navbar>

        <div class="col-sm-12 jumbotron-fluid">
            <b-card no-body type="">
                <b-tabs pills card vertical>
                    <b-tab title="간단한 통계" active>
                        <b-card-text>
                            <AdminDashBoardForm></AdminDashBoardForm>
                        </b-card-text>
                    </b-tab>
                    <b-tab title="메뉴 등록 | 수정">
                        <b-card-text>
                            <AdminMenuCUForm></AdminMenuCUForm>
                        </b-card-text>
                    </b-tab>
                    <b-tab title="메뉴 삭제">
                        <b-card-text>
                            <AdminMenuDeleteForm></AdminMenuDeleteForm>
                        </b-card-text>
                    </b-tab>
                    <b-tab title="전체회원 내역">
                        <b-card-text>
                            <AdminUserListForm></AdminUserListForm>
                        </b-card-text>
                    </b-tab>
                    <b-tab title="전체주문 내역">
                        <b-card-text>
                            <AdminOrderListForm></AdminOrderListForm>
                        </b-card-text>
                    </b-tab>

                </b-tabs>
            </b-card>
        </div>
    </b-container>

</template>

<script>
    import AdminDashBoardForm from "@/components/admin/AdminDashBoardForm";
    import AdminMenuCUForm from "@/components/admin/AdminMenuCUForm";
    import AdminMenuDeleteForm from "@/components/admin/AdminMenuDeleteForm";
    import AdminUserListForm from "@/components/admin/AdminUserListForm";
    import AdminOrderListForm from "@/components/admin/AdminOrderListForm";


    export default {
        name: "AdminMainForm",
        components: {AdminMenuDeleteForm, AdminMenuCUForm, AdminDashBoardForm, AdminOrderListForm, AdminUserListForm}
    }
</script>

<style scoped>

</style>