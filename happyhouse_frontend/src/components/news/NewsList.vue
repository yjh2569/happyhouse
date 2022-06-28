<template>
  <v-container>
    <div class="text-center">
      <h1 class="ma-8">뉴스</h1>
    </div>
    <div>
      <v-row>
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
      </v-row>
    </div>
    <div v-if="items.length">
      <v-simple-table class="text-center">
        <template v-slot:default>
          <thead>
            <tr class="text-center">
              <th class="text-center">제목</th>
              <th class="text-center">작성 시간</th>
            </tr>
          </thead>
          <tbody>
            <news-list-item
              v-for="(item, index) in items"
              :key="index"
              :item="item"
            ></news-list-item>
          </tbody>
        </template>
      </v-simple-table>
      <news-pagination-content
        :word="word"
        @pagechanged="pagechanged"
      ></news-pagination-content>
    </div>
    <div v-else class="text-center">{{ noItemsMsg }}</div>
  </v-container>
</template>

<script>
import { newsList } from "@/api/news.js";
import NewsListItem from "@/components/news/item/NewsListItem.vue";
import NewsPaginationContent from "@/components/news/item/NewsPaginationContent.vue";

export default {
  components: {
    NewsListItem,
    NewsPaginationContent,
  },
  data() {
    return {
      pg: 1,
      word: "",
      items: [],
      noItemsMsg: "뉴스를 가져오는 중...",
    };
  },
  created() {
    this.word = "부동산";
    let param = {
      query: this.word,
      start: 1,
    };
    if (this.word == "") {
      return;
    }
    newsList(
      param,
      (response) => {
        // console.log(response);
        this.items = response.data;
        if (this.items.length == 0) {
          this.noItemsMsg = "오류로 인해 뉴스를 가져오지 못했습니다.";
        }
      },
      (error) => {
        console.log(error);
        this.noItemsMsg = "오류로 인해 뉴스를 가져오지 못했습니다.";
      },
    );
  },
  methods: {
    search() {
      let param = {
        query: this.word,
        start: 1,
      };
      if (this.word == "") {
        return;
      }
      newsList(
        param,
        (response) => {
          // console.log(response);
          this.items = response.data;
          if (this.items.length == 0) {
            this.noItemsMsg = "검색 결과가 없습니다.";
          }
        },
        (error) => {
          console.log(error);
          this.noItemsMsg = "오류로 인해 뉴스를 가져오지 못했습니다.";
        },
      );
    },
    pagechanged(page) {
      let param = {
        start: (page - 1) * 10 + 1,
        query: this.word,
      };
      if (this.word == "") {
        return;
      }
      newsList(
        param,
        (response) => {
          // console.log(response);
          this.items = response.data;
        },
        (error) => {
          console.log(error);
          this.noItemsMsg = "오류로 인해 뉴스를 가져오지 못했습니다.";
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
