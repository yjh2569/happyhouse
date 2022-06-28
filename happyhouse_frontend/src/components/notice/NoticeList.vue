<template>
  <v-container>
    <div class="text-center">
      <h1 class="ma-8">공지 사항</h1>
    </div>
    <div>
      <v-row>
        <v-col cols="2">
          <v-select v-model="key" :items="items"> </v-select>
        </v-col>
        <v-col cols="3">
          <v-text-field
            type="text"
            class="ml-1 form-control"
            v-model="word"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
            @keyup.enter="search"
          />
        </v-col>

        <v-spacer></v-spacer>
        <v-col cols="1" v-if="userInfo.userid == 'admin'">
          <v-btn @click="movePage" color="success">글 등록</v-btn>
        </v-col>
      </v-row>
    </div>
    <div v-if="articles.length">
      <v-simple-table class="text-center">
        <template v-slot:default>
          <thead>
            <tr class="text-center">
              <th class="text-center">글번호</th>
              <th class="text-center">제목</th>
              <th class="text-center">작성자</th>
              <th class="text-center">작성일</th>
            </tr>
          </thead>
          <tbody>
            <notice-list-item
              v-for="article in articles"
              :key="article.articleno"
              :article="article"
            ></notice-list-item>
          </tbody>
        </template>
      </v-simple-table>
      <notice-pagination-content
        target="notice"
        :searchKey="key"
        :word="word"
        :changed="changed"
        @pagechanged="pagechanged"
      ></notice-pagination-content>
    </div>
    <div v-else class="text-center">검색 결과가 없습니다.</div>
  </v-container>
</template>

<script>
import { listNotice } from "@/api/notice.js";
import NoticeListItem from "@/components/notice/item/NoticeListItem.vue";
import NoticePaginationContent from "@/components/notice/item/NoticePaginationContent.vue";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  components: {
    NoticeListItem,
    NoticePaginationContent,
  },
  data() {
    return {
      pg: 1,
      key: "",
      word: "",
      changed: false,
      articles: [],
      items: [
        { text: "선택", value: "" },
        { text: "아이디", value: "userid" },
        { text: "제목", value: "title" },
        { text: "글 번호", value: "articleno" },
      ],
    };
  },
  created() {
    let param = {
      pg: 1,
      key: null,
      word: null,
    };
    listNotice(
      param,
      (response) => {
        this.articles = response.data;
      },
      (error) => {
        console.log(error);
      },
    );
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    movePage() {
      this.$router.push("/notice/write");
    },
    search() {
      this.changed = !this.changed;
      let param = {
        pg: 1,
        key: this.key,
        word: this.word,
      };
      listNotice(
        param,
        (response) => {
          this.articles = response.data;
        },
        (error) => {
          console.log(error);
        },
      );
    },
    pagechanged(page) {
      let param = {
        pg: page,
        key: this.key,
        word: this.word,
      };
      listNotice(
        param,
        (response) => {
          this.articles = response.data;
        },
        (error) => {
          console.log(error);
        },
      );
    },
  },
};
</script>

<style scoped>
.btnsearch {
  width: 70px;
}
</style>
