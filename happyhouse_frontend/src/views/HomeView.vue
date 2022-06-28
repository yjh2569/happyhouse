<template>
  <home-page />
</template>

<script>
import HomePage from "../components/HomePage";

export default {
  name: "HomeView",

  components: {
    HomePage,
  },
};
</script>

<style>
body {
  -ms-overflow-style: none;
}
::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>
