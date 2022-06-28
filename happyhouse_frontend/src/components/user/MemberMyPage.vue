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
        <v-card class="px-4">
          <div class="pa-8">
            <h2 class="text-center">회원정보 수정</h2>
          </div>

          <v-card-text>
            <v-form ref="registerForm" v-model="valid" lazy-validation>
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="userid"
                    :rules="[rules.required]"
                    label="ID"
                    readonly
                  ></v-text-field>
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
                  <v-dialog v-model="modifyDialog" width="500">
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        x-large
                        block
                        :disabled="!valid"
                        color="success"
                        @click="userModify"
                        v-bind="attrs"
                        v-on="on"
                        >회원정보 수정</v-btn
                      >
                    </template>
                    <v-card class="text-center">
                      <v-card-title class="text-h6 justify-center lighten-2">
                        {{ modifyMsg }}
                      </v-card-title>

                      <v-divider></v-divider>

                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="primary" text @click="modifyAlertClose">
                          close
                        </v-btn>
                        <v-spacer></v-spacer>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-col>
                <v-col class="d-flex ml-6" cols="12" sm="3" xsm="12">
                  <v-btn
                    x-large
                    block
                    :disabled="!valid"
                    color="secondary"
                    @click="movePage"
                    >뒤로 가기</v-btn
                  >
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
        </v-card>
      </div>
    </v-dialog>
  </v-app>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { modify } from "@/api/member.js";

const memberStore = "memberStore";

export default {
  name: "MemberLogin",
  data() {
    return {
      userid: "",
      userpwd: "",
      name: "",
      email: "",
      address: "",
      dialog: true,
      modifyDialog: false,
      modifyMsg: "",
      tab: 0,
      valid: true,
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
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
    passwordMatch() {
      return () => this.userpwd === this.verify || "Password must match";
    },
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async userModify() {
      let token = sessionStorage.getItem("access-token");
      await modify(
        {
          userid: this.userid,
          userpwd: this.userpwd,
          username: this.name,
          email: this.email,
          address: this.address,
        },
        (response) => {
          if (response.data === "success") {
            this.modifyMsg = "회원정보 수정에 성공했습니다.";
            this.modifyDialog = true;
            this.getUserInfo(token);
          } else {
            this.modifyMsg = "회원정보 수정에 실패했습니다.";
            this.modifyDialog = true;
          }
        },
        () => {},
      );
    },
    movePage() {
      this.$router.push({ name: "home" });
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    modifyAlertClose() {
      this.modifyDialog = false;
      if (this.modifyMsg == "회원정보 수정에 성공했습니다.") {
        this.$router.push({ name: "home" });
      }
    },
  },
  created() {
    this.userid = this.userInfo.userid;
    this.name = this.userInfo.username;
    this.email = this.userInfo.email;
    this.address = this.userInfo.address;
  },
};
</script>

<style scoped></style>
