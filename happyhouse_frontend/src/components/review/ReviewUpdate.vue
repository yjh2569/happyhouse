<template>
  <v-dialog v-model="dialog" persistent max-width="600px">
    <template v-slot:activator="{ on, attrs }">
      <v-btn icon color="grey" v-bind="attrs" v-on="on">
        <v-icon>mdi-heart</v-icon>
      </v-btn>
    </template>
    <v-card>
      <v-card-title>
        <span class="text-h6">수정하기</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" md="12">
              <v-text-field
                v-model="content"
                :rules="contentRules"
                :counter="150"
                label="한 줄평"
                required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="grey darken-1" text @click="dialog = false"> 취소 </v-btn>
        <v-btn color="blue darken-1" text @click="check"> 수정 </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { apiInstance } from "@/api/index";
import { mapState, mapActions } from "vuex";
const houseStore = "houseStore";

export default {
  name: "ReviewUpdate",
  components: {},
  data() {
    return {
      content: "",
      rating: 0,
      dialog: true,
      contentRules: [
        (v) => !!v || "입력하세요",
        (v) => v.length <= 150 || "150자 이내로 작성하세요",
      ],
    };
  },
  computed: {
    ...mapState(houseStore, ["house"]),
    ...mapActions(houseStore, ["detailHouse"]),
  },
  created() {
    apiInstance()
      .get(`/review/${this.$route.params.no}`)
      .then(({ data }) => {
        this.content = data.content;
        this.rating = data.rating;
      });
  },
  methods: {
    check() {
      if (this.content > 150) {
        alert("150자 이내로 작성하세요");
        return;
      } else {
        this.updat();
      }
    },
    updat() {
      console.log(this.$route.params.no);
      apiInstance()
        .put(`/review/${this.$route.params.no}`, {
          rating: this.rating,
          content: this.content,
        })
        .then(({ data }) => {
          if (data === "success") {
            alert("수정성공");
          } else {
            alert("수정실패");
          }
        });
    },
  },
};
</script>

<style></style>
