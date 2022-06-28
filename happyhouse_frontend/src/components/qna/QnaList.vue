<template>
  <v-container>
    <div class="text-center">
      <h1 class="ma-8">Q&A</h1>
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
        <v-col cols="1">
          <v-btn @click="movePage" color="success">Q&A 등록</v-btn>
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
              <th class="text-center">해결 여부</th>
            </tr>
          </thead>
          <tbody>
            <qna-list-item
              v-for="article in articles"
              :key="article.no"
              :article="article"
            ></qna-list-item>
          </tbody>
        </template>
      </v-simple-table>
      <qna-pagination-content
        target="qna"
        :searchKey="key"
        :word="word"
        :changed="changed"
        @pagechanged="pagechanged"
      ></qna-pagination-content>
    </div>
    <div v-else class="text-center">검색 결과가 없습니다.</div>
  </v-container>
</template>

<script>
import { listQna } from "@/api/qna.js";
import QnaListItem from "@/components/qna/item/QnaListItem.vue";
import QnaPaginationContent from "@/components/qna/item/QnaPaginationContent.vue";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  components: {
    QnaListItem,
    QnaPaginationContent,
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
        { text: "글 번호", value: "no" },
      ],
    };
  },
  created() {
    let param = {
      pg: 1,
      key: null,
      word: null,
    };
    listQna(
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
      this.$router.push("/qna/write");
    },
    search() {
      this.changed = !this.changed;
      let param = {
        pg: 1,
        key: this.key,
        word: this.word,
      };
      listQna(
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
      listQna(
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
