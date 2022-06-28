<template>
  <!-- <ul class="pagination justify-content-center mt-4">
    <li class="page-item" v-if="prev">
      <router-link
        :to="{
          path: `/${target}/list`,
          params: {
            pg: startPageIndex - 1,
            key: searchKey,
            word: word,
          },
        }"
        class="page-link"
        aria-label="Previous"
        @click.native="movePage(startPageIndex - 1)"
      >
        <span aria-hidden="true">&laquo;</span>
      </router-link>
    </li>

    <li
      v-for="index in endPageIndex - startPageIndex + 1"
      :key="index"
      class="page-item"
      :class="{ active: startPageIndex + index - 1 == currentPageIndex }"
    >
      <router-link
        :to="{
          path: `/${target}/list`,
          params: {
            pg: startPageIndex + index - 1,
            key: searchKey,
            word: word,
          },
        }"
        class="page-link"
        @click.native="movePage(startPageIndex + index - 1)"
        >{{ startPageIndex + index - 1 }}</router-link
      >
    </li>

    <li class="page-item" v-if="next">
      <router-link
        :to="{
          path: `/${target}/list`,
          params: { pg: endPageIndex + 1, key: searchKey, word: word },
        }"
        class="page-link"
        aria-label="Next"
        @click.native="movePage(endPageIndex + 1)"
      >
        <span aria-hidden="true">&raquo;</span>
      </router-link>
    </li>
  </ul> -->
  <div class="text-center">
    <v-pagination
      v-model="currentPageIndex"
      :length="endPageIndex - startPageIndex + 1"
      circle
      @input="movePage"
    ></v-pagination>
  </div>
</template>

<script>
import { getTotalCount } from "@/api/qna.js";

export default {
  name: "paginationContent",
  props: {
    target: String,
    searchKey: String,
    word: String,
    changed: Boolean,
  },
  data() {
    return {
      totalListItemCount: 0,
      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,

      pageCount: 0,
      startPageIndex: 0,
      endPageIndex: 0,
      prev: false,
      next: false,
    };
  },
  methods: {
    movePage(param) {
      console.log(param);
      this.currentPageIndex = param;
      this.$emit("pagechanged", param);
      this.initComponent();
    },

    initComponent() {
      // console.log(`/board/count?key=${this.searchKey}&word=${this.word}`);
      let param = {
        key: this.searchKey,
        word: this.word,
      };
      getTotalCount(
        param,
        ({ data }) => {
          this.totalListItemCount = data;
          this.initUI();
        },
        () => {
          alert("에러가 발생했습니다.");
        },
      );
    },
    initUI() {
      this.pageCount = Math.ceil(this.totalListItemCount / this.listRowCount);

      if (this.currentPageIndex % this.pageLinkCount == 0) {
        this.startPageIndex =
          (this.currentPageIndex / this.pageLinkCount - 1) *
            this.pageLinkCount +
          1;
      } else {
        this.startPageIndex =
          Math.floor(this.currentPageIndex / this.pageLinkCount) *
            this.pageLinkCount +
          1;
      }

      if (this.currentPageIndex % this.pageLinkCount == 0) {
        //10, 20...맨마지막
        this.endPageIndex =
          (this.currentPageIndex / this.pageLinkCount - 1) *
            this.pageLinkCount +
          this.pageLinkCount;
      } else {
        this.endPageIndex =
          Math.floor(this.currentPageIndex / this.pageLinkCount) *
            this.pageLinkCount +
          this.pageLinkCount;
      }

      if (this.endPageIndex > this.pageCount) {
        this.endPageIndex = this.pageCount;
      }

      if (this.currentPageIndex <= this.pageLinkCount) {
        this.prev = false;
      } else {
        this.prev = true;
      }

      if (this.endPageIndex >= this.pageCount) {
        this.endPageIndex = this.pageCount;
        this.next = false;
      } else {
        this.next = true;
      }
    },
  },
  watch: {
    currentPageIndex: function () {
      this.initUI();
    },
    changed: function () {
      this.initComponent();
    },
  },
  created() {
    this.initComponent();
  },
};
</script>
