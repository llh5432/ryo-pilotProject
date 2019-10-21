<template>

    <div class="container">
<!--            <b-card no-body>-->
        <b-navbar toggleable="lg" type="dark" variant="info">
            <b-navbar-brand href="#">Ryo-Pilot</b-navbar-brand>

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
                            <em>User</em>
                        </template>
                        <b-dropdown-item href="#">Admin Page</b-dropdown-item>
                        <b-dropdown-item href="#">Sign Out</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>

                <b-tabs pills vertical nav-wrapper id="tabs">
                    <b-tab title="전 체" active>
                        <main-body-menu-form></main-body-menu-form>
                    </b-tab>
                    <b-tab title="한 식">
                        <main-body-menu-form></main-body-menu-form>
                    </b-tab>
                    <b-tab title="일 식">
                        <main-body-menu-form></main-body-menu-form>
                    </b-tab>
                    <b-tab title="중 식">
                        <main-body-menu-form></main-body-menu-form>
                    </b-tab>
                    <b-tab title="양 식">
                        <main-body-menu-form></main-body-menu-form>
                    </b-tab>
                    <b-tab title="분 식">
                        <main-body-menu-form></main-body-menu-form>
                    </b-tab>
                    <b-tab title="검 색">
                        <SearchMenuForm></SearchMenuForm>
                    </b-tab>
                    <b-tab title="주문내역">
                        <OrderTableForm></OrderTableForm>
                    </b-tab>
                </b-tabs>
<!--            </b-card>-->
        </div>
</template>

<script>
    import MainBodyMenuForm from "@/components/MainBodyMenuForm";
    import OrderTableForm from "@/components/OrderTableForm";
    import SearchMenuForm from "@/components/SearchMenuForm";
    export default {
        components: {MainBodyMenuForm, OrderTableForm, SearchMenuForm},

    }
</script>

<style scoped>
#tabs{

}
</style>