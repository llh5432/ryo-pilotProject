<template>
    <b-container id="containerSet" class="bv-d-md-down-none">

        <b-row id="rowSet" class="justify-content-md-center">
            <div id="login">
                <b-card id="cardSet" >
                    <form>
                        <label>Your 계정을 입력하라</label>
                            <b-form-input type="text" placeholder="Account"/><br>
                            <b-form-input type="password" placeholder="Password"/><br>
                            <router-link to="/join">
                                <b-button id="jBtnSet" variant="outline-primary">회원가입</b-button>
                            </router-link>
                            <router-link to="/mainSide">
                                <b-button id="lBtnSet" variant="outline-primary">로그인</b-button><br>
                            </router-link>
                            <router-link to="/searchAccount">
                                <label><a href="HelloWorld.vue">아이디 찾기</a></label><br>
                            </router-link>
                            <router-link to="/searchPassword">
                                <label><a href="HelloWorld.vue">패스워드 찾기</a></label>
                            </router-link>
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