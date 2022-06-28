<template>
  <v-container class="bv-example-row mt-3">
    <v-row>
      <v-col>
        <v-alert show><h1 class="text-center">Q&A 수정</h1></v-alert>
      </v-col>
    </v-row>
    <qna-input-item type="modify" />
  </v-container>
</template>

<script>
import QnaInputItem from "@/components/qna/item/QnaInputItem.vue";

export default {
  name: "QnaModify",
  components: {
    QnaInputItem,
  },
};
</script>

<style></style>
