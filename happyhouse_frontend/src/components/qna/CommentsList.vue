<template>
  <v-container style="width: 60%">
    <v-row>
      <v-col>
        <h2>Comments</h2>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="11">
        <v-textarea
          solo
          name="input-7-4"
          rows="3"
          label="댓글을 입력하세요..."
          v-model="content"
          no-resize
        ></v-textarea>
      </v-col>
      <v-col cols="1">
        <v-btn
          height="100"
          width="100"
          color="success"
          style="font-size: 20px"
          @click="registerComment"
          >등록</v-btn
        >
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <comment-item
      v-for="comment in comments"
      :comment="comment"
      @updateComment="updateComment"
      @removeComment="removeComment"
      :key="comment.commentid"
    ></comment-item>
  </v-container>
</template>

<script>
import {
  listComment,
  writeComment,
  modifyComment,
  deleteComment,
} from "@/api/comment";
import { mapState } from "vuex";
import CommentItem from "@/components/qna/item/CommentItem.vue";

const memberStore = "memberStore";

export default {
  name: "commentsList",
  components: {
    CommentItem,
  },
  data() {
    return {
      comments: [],
      content: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    this.commentsList();
  },
  methods: {
    registerComment() {
      writeComment(
        {
          userid: this.userInfo.userid,
          content: this.content,
          qnano: this.$route.params.no,
        },
        ({ data }) => {
          console.log("comment write " + data);
          this.content = "";
          this.commentsList();
        },
        (error) => {
          console.log(error);
        },
      );
    },
    updateComment(comment) {
      modifyComment(
        comment,
        ({ data }) => {
          console.log("comment modify " + data);
          this.commentsList();
        },
        (error) => {
          console.log(error);
        },
      );
    },
    removeComment(commentid) {
      deleteComment(commentid, () => {
        this.commentsList();
      });
    },
    commentsList() {
      listComment(
        this.$route.params.no,
        (response) => {
          this.comments = response.data;
        },
        (error) => {
          console.log("조회 시 에러 발생!!", error);
        },
      );
    },
    // deleteQna() {
    //   if (confirm("정말로 삭제하시겠습니까?")) {
    //     deleteQna(this.article.no, () => {
    //       this.$router.push({ name: "QnaList" });
    //     });
    //   }
    // },
  },
};
</script>

<style></style>
