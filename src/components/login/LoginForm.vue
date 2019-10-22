<template>
    <b-container id="containerSet" class="bv-d-md-down-none">
        <b-row id="rowSet" class="justify-content-md-center">
            <div id="login">
                <b-card id="cardSet" >
                    <form>
                        <label>로 그 인</label>
                        <div>
                            <b-form-input type="text" placeholder="Account"/><br>
                            <b-form-input type="password" placeholder="Password"/><br>
                        </div>
                        <b-button-group>
                            <b-button id="jBtnSet" variant="outline-primary" to="/join">회원가입</b-button>
                            <b-button id="lBtnSet" variant="outline-primary" to="/userMain">로그인</b-button>
                        </b-button-group>

                        <div align="end">
                            <router-link to="/searchAccount">
                                <label><a href="#">아이디 찾기</a></label><br>
                            </router-link>
                            <router-link to="/searchPassword">
                                <label><a href="#">패스워드 찾기</a></label>
                            </router-link>
                        </div>

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