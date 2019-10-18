<template>
    <b-container id="containerSet" class="bv-d-md-down-none">
        <b-row id="rowSet" class="justify-content-md-center">
            <div id="login">
                <b-card id="cardSet" >
                    <form>
                        <label>Your 계정을 입력하라</label>
                        <b-form-input type="text" placeholder="Account"/><br>
                        <b-form-input type="password" placeholder="Password"/><br>
                            <b-button id="btnSet" variant="outline-primary">로그인</b-button><br>
                            <label><a href="HelloWorld.vue">아이디 찾기</a></label><br>
                            <label><a href="HelloWorld.vue">패스워드 찾기</a></label>
                    </form>
                </b-card>
            </div>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "LoginForm",
    }
</script>

<style scoped>
    #login {
        width: 400px;
        margin-top: 25%;
        border: black solid;
    }
    #btnSet {
        text-justify: auto;
    }
</style>