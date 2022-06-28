<template>
  <v-container class="bv-example-row mt-3">
    <v-row>
      <v-col>
        <v-alert show><h1 class="text-center">공지사항</h1></v-alert>
      </v-col>
    </v-row>
    <v-row class="mv-1 mx-auto" style="width: 80%">
      <v-col class="text-left">
        <v-btn @click="listNotice">목록</v-btn>
      </v-col>
      <v-col class="text-right" v-if="userInfo.userid == 'admin'">
        <v-btn color="success" size="sm" @click="moveModifyNotice" class="mr-2"
          >글수정</v-btn
        >
        <v-dialog v-model="deleteConfirmDialog" width="500">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="error"
              size="sm"
              @click="deleteConfirmDialog = true"
              v-bind="attrs"
              v-on="on"
              >글삭제</v-btn
            >
          </template>
          <v-card>
            <v-card-title class="text-h6 justify-center lighten-2">
              해당 공지사항을 정말 삭제하시겠습니까?
            </v-card-title>

            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="green darken-1"
                text
                @click="deleteConfirmDialog = false"
              >
                취소
              </v-btn>
              <v-dialog v-model="deleteDialog" width="500">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    color="red darken-1"
                    text
                    @click="deleteNotice"
                    v-bind="attrs"
                    v-on="on"
                  >
                    삭제
                  </v-btn>
                </template>
                <v-card>
                  <v-card-title class="text-h6 justify-center lighten-2">
                    {{ deleteMsg }}
                  </v-card-title>

                  <v-divider></v-divider>

                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" text @click="deleteAlertClose">
                      close
                    </v-btn>
                    <v-spacer></v-spacer>
                  </v-card-actions>
                </v-card>
              </v-dialog>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
    <v-row class="mv-1">
      <v-card style="width: 80%" class="mx-auto" outlined>
        <v-list-item>
          <v-list-item-content class="text-center">
            <v-list-item-title class="text-h5 mb-1">
              {{ article.subject }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-divider></v-divider>
        <v-list-item>
          <v-row>
            <v-spacer></v-spacer>
            <v-col class="text-right">
              {{ article.regtime }}
            </v-col>
          </v-row>
        </v-list-item>
        <v-divider></v-divider>
        <v-list-item style="min-height: 400px">
          <v-card
            class="mx-auto"
            style="width: 90%; min-height: 360px"
            outlined
          >
            <v-list-item>
              {{ article.content }}
            </v-list-item>
          </v-card>
        </v-list-item>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
// import moment from "moment";
import { getNotice, deleteNotice } from "@/api/notice";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "NoticeDetail",
  data() {
    return {
      article: {},
      deleteConfirmDialog: false,
      deleteDialog: false,
      deleteMsg: true,
      deleteSuccess: false,
    };
  },
  computed: {
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    getNotice(
      this.$route.params.articleno,
      (response) => {
        this.article = response.data;
      },
      (error) => {
        console.log("조회 시 에러 발생!!", error);
      },
    );
  },
  methods: {
    listNotice() {
      this.$router.push({ name: "noticeList" });
    },
    moveModifyNotice() {
      this.$router.replace({
        name: "noticeModify",
        params: { articleno: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteNotice() {
      deleteNotice(
        this.article.articleno,
        () => {
          this.deleteMsg = "공지사항 삭제에 성공했습니다.";
          this.deleteSuccess = true;
          this.deleteDialog = true;
        },
        () => {
          this.deleteMsg = "공지사항 삭제에 실패했습니다.";
          this.deleteDialog = true;
        },
      );
    },
    deleteAlertClose() {
      this.deleteDialog = false;
      if (this.deleteSuccess) {
        this.$router.push({ name: "noticeList" });
      }
      this.deleteConfirmDialog = false;
    },
  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style></style>
