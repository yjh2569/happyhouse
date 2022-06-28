<template>
  <v-container class="container">
    <router-view></router-view>
  </v-container>
</template>

<script>
export default {
  name: "MemberView",
};
</script>

<style scoped>
.container {
  background-image: url("../assets/house.gif");
  width: 100%;
  height: 100%;
  max-width: 100% !important;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: fixed;
}
</style>
