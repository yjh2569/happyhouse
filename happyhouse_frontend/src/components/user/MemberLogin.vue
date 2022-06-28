<template>
  <v-app class="container">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="600px"
      min-width="360px"
      @click:outside="$router.push({ name: 'home' })"
    >
      <div>
        <v-tabs
          v-model="tab"
          show-arrows
          background-color="deep-purple accent-4"
          icons-and-text
          dark
          grow
        >
          <v-tabs-slider color="purple darken-4"></v-tabs-slider>
          <v-tab v-for="i in tabs" :key="i.name">
            <v-icon large>{{ i.icon }}</v-icon>
            <div class="caption py-1">{{ i.name }}</div>
          </v-tab>
          <v-tab-item>
            <v-card class="px-4">
              <v-card-text>
                <v-form ref="loginForm" v-model="valid" lazy-validation>
                  <v-row>
                    <v-col cols="12">
                      <v-text-field
                        v-model="loginuserid"
                        label="ID"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                        v-model="loginuserpwd"
                        :rules="[rules.required]"
                        type="password"
                        name="input-10-1"
                        label="Password"
                      ></v-text-field>
                    </v-col>
                    <v-col class="d-flex" cols="12" sm="6" xsm="12"> </v-col>
                    <v-spacer></v-spacer>
                    <v-col class="d-flex" cols="12" sm="3" xsm="12" align-end>
                      <v-dialog v-model="loginDialog" width="500">
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                            x-large
                            block
                            :disabled="!valid"
                            color="success"
                            @click="confirm"
                            v-bind="attrs"
                            v-on="on"
                          >
                            로그인
                          </v-btn>
                        </template>
                        <v-card class="text-center">
                          <v-card-title
                            class="text-h6 justify-center lighten-2"
                          >
                            {{ loginMsg }}
                          </v-card-title>

                          <v-divider></v-divider>

                          <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                              color="primary"
                              text
                              @click="loginDialog = false"
                            >
                              close
                            </v-btn>
                            <v-spacer></v-spacer>
                          </v-card-actions>
                        </v-card>
                      </v-dialog>
                    </v-col>
                  </v-row>
                </v-form>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <v-tab-item>
            <v-card class="px-4">
              <v-card-text>
                <v-form ref="registerForm" v-model="valid" lazy-validation>
                  <v-row>
                    <v-col cols="12" sm="9" md="9">
                      <v-text-field
                        v-model="userid"
                        :rules="[rules.required]"
                        label="ID"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-dialog v-model="idCheckDialog" width="500">
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                            @click.prevent="idValidationCheck"
                            v-bind="attrs"
                            v-on="on"
                            >중복검사</v-btn
                          >
                        </template>
                        <v-card class="text-center">
                          <v-card-title
                            class="text-h6 justify-center lighten-2"
                          >
                            {{ idCheckMsg }}
                          </v-card-title>

                          <v-divider></v-divider>

                          <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                              color="primary"
                              text
                              @click="idCheckDialog = false"
                            >
                              close
                            </v-btn>
                            <v-spacer></v-spacer>
                          </v-card-actions>
                        </v-card>
                      </v-dialog>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                        v-model="userpwd"
                        :rules="[rules.required]"
                        name="input-10-1"
                        type="password"
                        label="Password"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                        block
                        v-model="verify"
                        :rules="[rules.required, passwordMatch]"
                        name="input-10-1"
                        type="password"
                        label="Confirm Password"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                        v-model="name"
                        :rules="[rules.required]"
                        label="Name"
                        maxlength="20"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                        v-model="email"
                        :rules="emailRules"
                        label="E-mail"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                        v-model="address"
                        :rules="[rules.required]"
                        label="Address"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-spacer></v-spacer>
                    <v-col class="d-flex ml-auto" cols="12" sm="3" xsm="12">
                      <v-dialog v-model="registerDialog" width="500">
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                            x-large
                            block
                            :disabled="!valid"
                            color="success"
                            @click="userRegister"
                            v-bind="attrs"
                            v-on="on"
                            >회원가입</v-btn
                          >
                        </template>
                        <v-card>
                          <v-card-title
                            class="text-h6 justify-center lighten-2"
                          >
                            {{ registerMsg }}
                          </v-card-title>

                          <v-divider></v-divider>

                          <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                              color="primary"
                              text
                              @click="registerAlertClose"
                            >
                              close
                            </v-btn>
                            <v-spacer></v-spacer>
                          </v-card-actions>
                        </v-card>
                      </v-dialog>
                    </v-col>
                  </v-row>
                </v-form>
              </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </div>
    </v-dialog>
  </v-app>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { register, idcheck } from "@/api/member.js";

const memberStore = "memberStore";

export default {
  name: "MemberLogin",
  data() {
    return {
      loginuserid: "",
      loginuserpwd: "",
      userid: "",
      userpwd: "",
      name: "",
      email: "",
      address: "",
      dialog: true,
      loginDialog: false,
      loginMsg: "로그인 중...",
      registerDialog: false,
      registerMsg: "",
      registerSuccess: false,
      idCheckDialog: false,
      idCheckMsg: "",
      tab: 0,
      valid: true,
      tabs: [
        { name: "로그인", icon: "mdi-account" },
        { name: "회원가입", icon: "mdi-account-outline" },
      ],
      verify: "",
      emailRules: [
        (v) => !!v || "Required",
        (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
      ],

      show1: false,
      rules: {
        required: (value) => !!value || "Required.",
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
    passwordMatch() {
      return () => this.userpwd === this.verify || "Password must match";
    },
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      this.loginMsg = "로그인 중...";
      await this.userConfirm({
        userid: this.loginuserid,
        userpwd: this.loginuserpwd,
      });
      let token = sessionStorage.getItem("access-token");
      this.loginDialog = false;
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.loginMsg = "로그인이 정상적으로 완료되었습니다.";
        this.loginDialog = true;
        this.$router.push({ name: "home" });
      } else {
        this.loginMsg = "아이디 또는 비밀번호가 일치하지 않습니다.";
        this.loginDialog = true;
      }
    },
    async userRegister() {
      await register(
        {
          userid: this.userid,
          userpwd: this.userpwd,
          username: this.name,
          email: this.email,
          address: this.address,
        },
        (response) => {
          if (response.data === "success") {
            this.registerMsg = "회원가입에 성공했습니다.";
            this.registerSuccess = true;
            this.registerDialog = true;
          } else {
            this.registerMsg = "회원가입에 실패했습니다.";
            this.registerDialog = true;
          }
        },
        () => {},
      );
    },
    async idValidationCheck() {
      await idcheck(
        this.userid,
        (response) => {
          if (response.data === "success") {
            this.idCheckMsg = "이 아이디는 사용 가능합니다.";
            this.idCheckDialog = true;
          } else {
            this.idCheckMsg = "이미 존재하는 아이디입니다.";
            this.idCheckDialog = true;
          }
        },
        () => {},
      );
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    registerAlertClose() {
      this.registerDialog = false;
      if (this.registerSuccess) {
        this.$router.push({ name: "home" });
      }
    },
  },
};
</script>

<style scoped>
#loginTab {
  background-image: radial-gradient(circle, #a1c4fd 0%, #c2e9fb 100%);
}
</style>
