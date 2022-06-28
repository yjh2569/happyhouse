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
          id="subject"
          v-model="article.subject"
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
          <v-dialog v-model="registerDialog" width="500">
            <template
              v-slot:activator="{ on, attrs }"
              v-if="this.type === 'register'"
            >
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
          <v-dialog v-model="registerDialog" width="500">
            <template
              v-slot:activator="{ on, attrs }"
              v-if="this.type === 'modify'"
            >
              <v-btn
                type="submit"
                color="success"
                class="m-1"
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
          <v-btn class="m-1" @click="$router.push({ name: 'noticeList' })"
            >뒤로 가기</v-btn
          >
        </div>
      </v-form>
    </v-col>
    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import { writeNotice, getNotice, modifyNotice } from "@/api/notice";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "NoticeInputItem",
  data() {
    return {
      article: {
        articleno: 0,
        userid: "",
        subject: "",
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
      getNotice(
        this.$route.params.articleno,
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
        !this.article.subject &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) {
        this.registerMsg = msg;
        this.registerDialog = true;
      } else
        this.type === "register" ? this.registNotice() : this.modifyNotice();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.$router.push({ name: "noticeList" });
    },
    registNotice() {
      writeNotice(
        {
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        },
        ({ data }) => {
          let msg = "공지사항 작성 중 문제가 발생했습니다.";
          if (data === "success") {
            msg = "공지사항 작성에 성공했습니다.";
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
    modifyNotice() {
      modifyNotice(
        {
          articleno: this.article.articleno,
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        },
        ({ data }) => {
          let msg = "공지사항 수정 중 문제가 발생했습니다.";
          if (data === "success") {
            msg = "공지사항 수정에 성공했습니다.";
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
      this.$router.push({ name: "noticeList" });
    },
    registerAlertClose() {
      this.registerDialog = false;
      if (this.isSuccess) {
        this.$router.push({ name: "noticeList" });
      }
    },
  },
};
</script>

<style></style>
