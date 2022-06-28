<template>
  <tr>
    <td>
      <a :href="item.link" target="_blank" v-html="item.title"></a>
    </td>
    <td>{{ item.pubDate | formatDate }}</td>
  </tr>
</template>

<script>
import moment from "moment";
export default {
  name: "NewsListItem",
  props: {
    item: Object,
  },
  filters: {
    formatDate(pubDate) {
      return moment(new Date(pubDate)).format("YYYY.MM.DD hh:mm A");
    },
  },
};
</script>

<style></style>
