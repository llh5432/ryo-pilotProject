<template>
    <div>
        <div>
            <b-card-group deck>
                <b-card bg-variant="primary" text-variant="white" header="총 수익" class="text-center">
                    <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</b-card-text>
                </b-card>

                <b-card header="우수 회원" class="text-center">
                    <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</b-card-text>
                </b-card>

                <b-card bg-variant="success" text-variant="white" header="우수 메뉴" class="text-center">
                    <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</b-card-text>
                </b-card>
            </b-card-group>
        </div>
        <div class="mt-3">
            <b-card-group deck>
                <b-card bg-variant="info" text-variant="white" header="우수 메뉴타입" class="text-center">
                    <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</b-card-text>
                </b-card>

                <b-card bg-variant="warning" text-variant="white" header="총 회원 수" class="text-center">
                    <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</b-card-text>
                </b-card>

                <b-card bg-variant="danger" text-variant="white" header="총 주문 수" class="text-center">
                    <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</b-card-text>
                </b-card>
            </b-card-group>
        </div>
    </div>
</template>

<script>
    export default {
        name: "AdminDashBoardForm"
    }
</script>

<style scoped>

</style>