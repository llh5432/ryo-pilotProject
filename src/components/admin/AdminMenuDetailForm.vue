<template>
    <AdminMainForm>
        <b-card header="김치찌개" class="text-center" id="bCard">
            <form>
                <b-form-input type="text" value="KR"></b-form-input>
                <b-form-input type="num" value="5000"></b-form-input>
                <b-form-input type="date" value="2019/10/22/16:07"></b-form-input>
                <b-button type="submit">수 정</b-button>
            </form>
        </b-card>
    </AdminMainForm>
</template>

<script>
    import AdminMainForm from "@/components/admin/AdminMainForm";
    export default {
        components: {AdminMainForm}
    }
</script>

<style scoped>

</style>