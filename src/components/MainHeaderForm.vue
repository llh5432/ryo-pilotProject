<template>
    <b-container class="">
        <b-row align-h="center">
                <b-col cols="4">
                    <b-card class="border-secondary">
                        <p>전체</p>
                    </b-card>
                </b-col>
                <b-col cols="5">
                    <form>
                        <b-form-input type="text"/> <b-form-input type="text"/>
                        <b-button variant="primary">검 색</b-button>
                    </form>
                </b-col>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "MainHeaderForm"
    }
</script>

<style scoped>
#bb {
    border: solid black;
}
</style>