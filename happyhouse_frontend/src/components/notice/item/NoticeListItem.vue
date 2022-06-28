<template>
  <tr>
    <td>{{ article.articleno }}</td>
    <td>
      <router-link
        :to="{ name: 'noticeDetail', params: { articleno: article.articleno } }"
        >{{ article.subject }}</router-link
      >
    </td>
    <td>{{ article.userid }}</td>
    <td>{{ article.regtime | formatDate }}</td>
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
