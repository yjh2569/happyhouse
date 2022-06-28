<template>
  <v-container class="bv-example-row mt-3">
    <v-row>
      <v-col>
        <v-alert show><h1 class="text-center">Q&A</h1></v-alert>
      </v-col>
    </v-row>
    <v-row class="mv-1 mx-auto" style="width: 80%">
      <v-col class="text-left">
        <v-btn @click="listQna">목록</v-btn>
      </v-col>
      <v-col class="text-right" v-if="userInfo.userid == article.userid">
        <v-btn color="success" size="sm" @click="moveModifyQna" class="mr-2"
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
              class="mr-2"
              >글삭제</v-btn
            >
          </template>
          <v-card>
            <v-card-title class="text-h6 justify-center lighten-2">
              해당 Q&A를 정말 삭제하시겠습니까?
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
                    @click="deleteQna"
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
        <v-dialog v-model="answeredDialog" width="500">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="blue"
              size="sm"
              @click="qnaAnswered"
              v-bind="attrs"
              v-on="on"
              class="white--text"
              >해결 완료</v-btn
            >
          </template>
          <v-card>
            <v-card-title class="text-h6 justify-center lighten-2">
              {{ answeredMsg }}
            </v-card-title>

            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" text @click="answerAlertClose">
                close
              </v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
    <v-row class="ma-5">
      <v-card style="width: 80%" class="mx-auto" outlined>
        <v-list-item>
          <v-list-item-content class="text-center">
            <v-list-item-title class="text-h5 mb-1">
              {{ article.title }}
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
    <v-divider></v-divider>
    <comments-list></comments-list>
  </v-container>
</template>

<script>
// import moment from "moment";
import { getQna, deleteQna, modifyQna } from "@/api/qna";
import { mapState } from "vuex";
import CommentsList from "@/components/qna/CommentsList.vue";

const memberStore = "memberStore";

export default {
  name: "QnaDetail",
  components: {
    CommentsList,
  },
  data() {
    return {
      article: {},
      answeredDialog: false,
      answeredMsg: "",
      deleteConfirmDialog: false,
      deleteDialog: false,
      deleteMsg: "",
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
    getQna(
      this.$route.params.no,
      (response) => {
        this.article = response.data;
      },
      (error) => {
        console.log("조회 시 에러 발생!!", error);
      },
    );
  },
  methods: {
    listQna() {
      this.$router.push({ name: "QnaList" });
    },
    moveModifyQna() {
      this.$router.replace({
        name: "QnaModify",
        params: { no: this.article.no },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteQna() {
      deleteQna(
        this.article.no,
        () => {
          this.deleteMsg = "Q&A 삭제에 성공했습니다.";
          this.deleteSuccess = true;
          this.deleteDialog = true;
        },
        () => {
          this.deleteMsg = "Q&A 삭제에 실패했습니다.";
          this.deleteDialog = true;
        },
      );
    },
    deleteAlertClose() {
      this.deleteDialog = false;
      if (this.deleteSuccess) {
        this.$router.push({ name: "QnaList" });
      }
      this.deleteConfirmDialog = false;
    },
    qnaAnswered() {
      modifyQna(
        {
          no: this.article.no,
          userid: this.article.userid,
          title: this.article.title,
          content: this.article.content,
          anscheck: 1,
        },
        ({ data }) => {
          let msg = "수정 시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          this.answeredMsg = msg;
          this.answeredDialog = true;
        },
        (error) => {
          console.log(error);
        },
      );
    },
    answerAlertClose() {
      this.$router.push({ name: "QnaList" });
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
