<template>
    <div class="container">
       <b-row align-h="end">
            <b-card class="border-dark">
                메뉴타입
            </b-card>
       </b-row>
        <hr>
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
    </div>
</template>

<script>
    export default {
        name: "MainBodyMenuForm"
    }
</script>

<style scoped>
#bCard{
    width:200px ;
    height: 150px;
    margin-block-end: auto;
}
</style>