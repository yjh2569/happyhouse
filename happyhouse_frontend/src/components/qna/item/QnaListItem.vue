<template>
  <tr>
    <td>{{ article.no }}</td>
    <td>
      <router-link :to="{ name: 'QnaDetail', params: { no: article.no } }">{{
        article.title
      }}</router-link>
    </td>
    <td>{{ article.userid }}</td>
    <td>{{ article.regtime | formatDate }}</td>
    <td v-if="article.anscheck === 0">
      <v-chip class="ma-2" color="red" text-color="white">미해결 </v-chip>
    </td>
    <td v-else>
      <v-chip class="ma-2" color="green" text-color="white">해결완료 </v-chip>
    </td>
  </tr>
</template>

<script>
import moment from "moment";
export default {
  name: "NoticeListItem",
  props: {
    article: Object,
  },
  filters: {
    formatDate(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
};
</script>

<style></style>
