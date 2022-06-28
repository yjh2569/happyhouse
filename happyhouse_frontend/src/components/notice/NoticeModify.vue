<template>
  <v-container class="bv-example-row mt-3">
    <v-row>
      <v-col>
        <v-alert show><h1 class="text-center">공지사항 수정</h1></v-alert>
      </v-col>
    </v-row>
    <notice-input-item type="modify" />
  </v-container>
</template>

<script>
import NoticeInputItem from "@/components/notice/item/NoticeInputItem.vue";

export default {
  name: "NoticeModify",
  components: {
    NoticeInputItem,
  },
};
</script>

<style></style>
