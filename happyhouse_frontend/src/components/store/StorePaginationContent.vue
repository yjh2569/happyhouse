<template>
  <div class="text-center">
    <v-pagination
      v-model="currentPageIndex"
      :length="pageCount"
      circle
      @input="movePage"
      class="ma-3"
    ></v-pagination>
  </div>
</template>

<script>
import { sgetTotalCount } from "@/api/house.js";

export default {
  name: "paginationContent",
  props: {
    searchKey: String,
    word: String,
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
      sgetTotalCount(
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
    word: function () {
      this.currentPageIndex = 1;
      this.initComponent();
    },
  },
};
</script>
