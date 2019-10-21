<template>
    <b-container id="containerSet" class="bv-d-md-down-none">

        <b-row id="rowSet" class="justify-content-md-center">
            <div id="login">
                <b-card id="cardSet" >
                    <form id="formSet">
                        <label>회원가입하세요.</label>
                        <div id="formBodySet">
                            <b-form-input type="text" placeholder="Account"/><br>
                            <b-form-input type="password" placeholder="Password"/><br>
                            <b-form-input type="password" placeholder="PasswordDup"/><br>
                            <b-form-input type="email" placeholder="Email"/><br>
                            <b-form-input type="text" placeholder="text"/>
                        </div>
                        <div id="formFootSet">
                            <router-link to="/">
                                <b-button variant="outline-primary">돌아가기</b-button>
                            </router-link>
                            <b-button variant="outline-primary">가입</b-button><br>
                        </div>
                    </form>
                </b-card>
            </div>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "JoinForm",
    }
</script>

<style scoped>
    #login {
        width: 400px;
        margin-top: auto;
        border: black solid;
    }
    #formSet {
        text-align: center;
    }
</style>