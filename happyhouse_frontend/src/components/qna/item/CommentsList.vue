<template>
  <v-container style="width: 60%">
    <v-row>
      <v-col>
        <h2>Comments</h2>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="9">
        <v-textarea
          solo
          name="input-7-4"
          rows="3"
          label="댓글을 입력하세요..."
          no-resize
        ></v-textarea>
      </v-col>
      <v-col>
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
  </v-container>
</template>

<script>
export default {};
</script>

<style></style>
