<template>
  <v-form v-model="valid" ref="rev">
    <v-row>
      <v-rating
        v-model="rating"
        ref="rat"
        background-color="orange lighten-2"
        color="orange"
        small
      ></v-rating>
      <v-col cols="12" md="12">
        <v-text-field
          v-model="content"
          :rules="contentRules"
          :counter="150"
          label="한 줄평 작성하기"
          required
        ></v-text-field>
        <v-btn tile color="success" @click="check">
          <v-icon left> mdi-pencil </v-icon>
          작성하기
        </v-btn>
        <v-alert type="success" v-if="succ" :value="salert">
          등록완료!
        </v-alert>
        <v-alert type="error" v-if="fail" :value="falert"> 등록실패.. </v-alert>
      </v-col>
    </v-row>
  </v-form>
</template>

<script>
import { apiInstance } from "@/api/index";
import { mapState, mapActions } from "vuex";
const houseStore = "houseStore";
const memberStore = "memberStore";

export default {
  name: "ReviewWrite",
  components: {},
  data() {
    return {
      valid: false,
      content: "",
      aptCode: 0,
      rating: 0,
      userid: "",
      succ: false,
      fail: false,
      contentRules: [
        (v) => !!v || "입력하세요",
        (v) => v.length <= 150 || "150자 이내로 작성하세요",
      ],
    };
  },
  computed: {
    ...mapState(houseStore, ["house"]),
    ...mapState(memberStore, ["userInfo"]),
    ...mapActions(houseStore, ["detailHouse"]),
  },
  methods: {
    check() {
      if (this.content > 150) {
        alert("150자 이내로 작성하세요");
        return;
      } else {
        this.register();
      }
    },
    register() {
      //   console.log(this.house.aptCode);
      apiInstance()
        .post("/review", {
          aptCode: this.house.aptCode,
          rating: this.rating,
          content: this.content,
          userid: this.userInfo.userid,
        })
        .then(({ data }) => {
          if (data === "success") {
            this.$refs.rev.reset();
            this.$refs.rat.reset();
            this.succ = true;
            setTimeout(() => {
              this.succ = false;
            }, 1500);
          } else {
            setTimeout(() => {
              this.fail = false;
            }, 1500);
          }
        });
    },
  },
};
</script>

<style></style>
