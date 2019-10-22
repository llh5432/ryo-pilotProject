<template>
    <b-container id="containerSet" class="bv-d-md-down-none">
        <b-row id="rowSet" class="justify-content-md-center">
            <div id="login">
                <b-card id="cardSet" >
                    <form>
                        <label>Account 찾기</label>
                        <b-form-input type="text" placeholder="Email"/><br>
                        <b-form-input type="text" placeholder="Name"/><br>
                        <b-button-group>
                            <b-button variant="outline-primary" to="/">돌아가기</b-button>
                            <b-button id="lBtnSet" variant="outline-primary" to="/">찾 기</b-button>
                        </b-button-group>
                    </form>
                </b-card>
            </div>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "SearchAccountForm",


    }
</script>

<style scoped>
    #login {
        width: 400px;
        margin-top: auto;
        border: black solid;
    }
    #lBtnSet {
        text-justify: auto;
    }
    #jBtnSet {
        text-justify: auto;
    }
</style>