<template>
  <tr @click="selectHouse" style="cursor: pointer">
    <td>{{ index + 1 }}</td>
    <td>{{ item.aptName }}</td>
    <td>{{ item.dongName }}</td>
    <td :class="{ textRed: isHigh, textOrange: isMiddle, textGreen: isRow }">
      {{ item.recentPrice | priceFormat }}
    </td>
    <td>{{ item.dist }}km</td>
  </tr>
</template>

<script>
export default {
  name: "HouseListItem",
  data() {
    return {
      isHigh: false,
      isMiddle: false,
      isRow: false,
    };
  },
  props: {
    item: Object,
    index: Number,
  },
  created() {
    if (this.item.recentPrice > 1500000000) {
      this.isHigh = true;
      this.isMiddle = false;
      this.isRow = false;
    } else if (this.item.recentPrice > 700000000) {
      this.isHigh = false;
      this.isMiddle = true;
      this.isRow = false;
    } else {
      this.isHigh = false;
      this.isMiddle = false;
      this.isRow = true;
    }
  },
  filters: {
    priceFormat(value) {
      let price = parseInt(value);
      let result = "";
      if (price / 100000000 > 1) {
        result = Math.floor(price / 100000000) + "억 ";
      }
      if (price % 100000000 > 0)
        result = result + (price % 100000000) / 10000 + "만 ";
      result += "원";
      return result;
    },
  },
  methods: {
    selectHouse() {
      this.$emit("selectHouse", this.item);
    },
  },
};
</script>

<style>
.textRed {
  color: red;
}
.textOrange {
  color: orange;
}
.textGreen {
  color: green;
}
</style>
