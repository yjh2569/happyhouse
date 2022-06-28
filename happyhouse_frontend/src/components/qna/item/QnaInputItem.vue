<template>
  <v-row class="mv-1">
    <v-spacer></v-spacer>
    <v-col cols="8" style="text-align: left">
      <v-form @submit="onSubmit" @reset="onReset">
        <v-text-field
          id="userid"
          disabled
          label="작성자"
          v-model="article.userid"
          type="text"
          required
          placeholder="작성자 입력..."
        ></v-text-field>

        <v-text-field
          id="title"
          v-model="article.title"
          label="제목"
          type="text"
          required
          placeholder="제목 입력..."
        ></v-text-field>

        <v-textarea
          id="content"
          v-model="article.content"
          placeholder="내용 입력..."
          rows="10"
          max-rows="15"
          label="내용"
          no-resize
        ></v-textarea>
        <div class="text-center">
          <v-dialog
            v-model="registerDialog"
            width="500"
            v-if="this.type === 'register'"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                type="submit"
                color="success"
                class="ma-1"
                v-bind="attrs"
                v-on="on"
                >글작성</v-btn
              >
            </template>
            <v-card>
              <v-card-title class="text-h6 justify-center lighten-2">
                {{ registerMsg }}
              </v-card-title>

              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" text @click="registerAlertClose">
                  close
                </v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-dialog v-model="registerDialog" width="500" v-else>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                type="submit"
                color="success"
                class="ma-1"
                v-bind="attrs"
                v-on="on"
                >글수정</v-btn
              >
            </template>
            <v-card>
              <v-card-title class="text-h6 justify-center lighten-2">
                {{ registerMsg }}
              </v-card-title>

              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" text @click="registerAlertClose">
                  close
                </v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-btn class="m-1" @click="$router.push({ name: 'QnaList' })"
            >뒤로 가기</v-btn
          >
        </div>
      </v-form>
    </v-col>
    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import { writeQna, getQna, modifyQna } from "@/api/qna";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "QnaInputItem",
  data() {
    return {
      article: {
        no: 0,
        userid: "",
        title: "",
        content: "",
      },
      registerMsg: "",
      registerDialog: false,
      isSuccess: false,
    };
  },
  props: {
    type: { type: String },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    if (this.type === "modify") {
      getQna(
        this.$route.params.no,
        ({ data }) => {
          // this.article.articleno = data.article.articleno;
          // this.article.userid = data.article.userid;
          // this.article.subject = data.article.subject;
          // this.article.content = data.article.content;
          this.article = data;
        },
        (error) => {
          console.log(error);
        },
      );
      this.isUserid = true;
    } else {
      this.article.userid = this.userInfo.userid;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.userid &&
        ((msg = "작성자 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err &&
        !this.article.title &&
        ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) {
        this.registerMsg = msg;
        this.registerDialog = true;
      } else this.type === "register" ? this.registQna() : this.modifyQna();
    },
    onReset(event) {
      event.preventDefault();
      this.article.no = 0;
      this.article.title = "";
      this.article.content = "";
      this.$router.push({ name: "QnaList" });
    },
    registQna() {
      writeQna(
        {
          userid: this.article.userid,
          title: this.article.title,
          content: this.article.content,
        },
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
            this.isSuccess = true;
          }
          this.registerMsg = msg;
          this.registerDialog = true;
        },
        (error) => {
          console.log(error);
        },
      );
    },
    modifyQna() {
      modifyQna(
        {
          no: this.article.no,
          userid: this.article.userid,
          title: this.article.title,
          content: this.article.content,
          anscheck: 0,
        },
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
            this.isSuccess = true;
          }
          this.registerMsg = msg;
          this.registerDialog = true;
        },
        (error) => {
          console.log(error);
        },
      );
    },
    moveList() {
      this.$router.push({ name: "QnaList" });
    },
    registerAlertClose() {
      this.registerDialog = false;
      if (this.isSuccess) {
        this.$router.push({ name: "QnaList" });
      }
    },
  },
};
</script>

<style></style>
