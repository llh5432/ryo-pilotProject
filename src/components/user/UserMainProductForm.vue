<template>
    <div class="container">
        <b-row align-h="start">
            <b-card header="김치찌개" class="text-center" id="bCard">
                <b-card-text> 가격 : 5000원</b-card-text>
                <b-button>담 기</b-button>
            </b-card>
            <b-card header="김치찌개" class="text-center" id="bCard">
                <b-card-text> 가격 : 5000원</b-card-text>
                <b-button>담 기</b-button>
            </b-card>
        </b-row>
        <UserMainSlotForm></UserMainSlotForm>
    </div>
</template>

<script>
    import UserMainSlotForm from "@/components/user/UserMainSlotForm";
    export default {

        components: {UserMainSlotForm}

    }
</script>

<style scoped>
#bCard{
    width:200px ;
    height: 150px;
    margin-block-end: auto;
}
</style>