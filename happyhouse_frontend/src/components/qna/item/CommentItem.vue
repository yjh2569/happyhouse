<template>
  <v-container>
    <v-card class="mx-auto" outlined>
      <v-row>
        <v-list-item class="pt-4 mx-auto">
          <v-row class="mx-9">
            <v-col cols="3"> 작성자 : {{ comment.userid }} </v-col>
            <v-spacer></v-spacer>
            <v-col class="text-right" cols="6">
              작성시간 : {{ comment.regtime }}
            </v-col>
            <v-col cols="1">
              <v-menu left bottom v-if="comment.userid == userInfo.userid">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon v-bind="attrs" v-on="on">
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>

                <v-list>
                  <v-list-item @click="changeModifyMode">
                    <v-list-item-title>수정</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="deleteComment">
                    <v-list-item-title>삭제</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-col>
          </v-row>
        </v-list-item>
        <v-list-item style="min-height: 180px">
          <v-textarea
            solo
            name="input-7-4"
            label="Solo textarea"
            :value="comment.content"
            readonly
            no-resize
          ></v-textarea>
        </v-list-item>
        <v-expand-transition>
          <v-list-item v-show="modifyMode">
            <v-textarea
              solo
              name="input-7-4"
              label="Solo textarea"
              v-model="commentContent"
              no-resize
            ></v-textarea>
          </v-list-item>
        </v-expand-transition>
      </v-row>
      <v-row v-if="comment.userid == userInfo.userid" class="mb-4 mr-4">
        <v-col class="text-right" v-if="modifyMode">
          <v-btn color="success" @click="modifyComment" class="mr-2"
            >수정 완료</v-btn
          >
          <v-btn color="secondary" @click="changeModifyMode">취소</v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  props: {
    comment: Object,
  },
  data() {
    return {
      modifyMode: false,
      commentContent: "",
    };
  },
  created() {
    this.commentContent = this.comment.content;
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    changeModifyMode() {
      this.modifyMode = !this.modifyMode;
    },
    deleteComment() {
      this.$emit("removeComment", this.comment.commentid);
    },
    modifyComment() {
      this.$emit("updateComment", {
        commentid: this.comment.commentid,
        qnano: this.comment.qnano,
        userid: this.comment.userid,
        content: this.commentContent,
      });
      this.modifyMode = !this.modifyMode;
    },
  },
};
</script>

<style></style>
