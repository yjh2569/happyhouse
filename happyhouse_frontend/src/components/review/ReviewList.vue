<template>
  <div class="mx-auto my-auto" cols="12" md="12">
    <v-data-table :headers="headers" :items="reviews" :items-per-page="pp">
      <template v-slot:item="{ item, index }">
        <tr>
          <td>{{ index + 1 }}</td>
          <td>{{ item.content }}</td>
          <td>
            <v-rating
              v-model="item.rating"
              ref="rat"
              background-color="orange lighten-2"
              color="orange"
              small
            ></v-rating>
          </td>
          <td>{{ item.userid }}</td>
          <td>
            <v-dialog v-model="update" persistent max-width="600px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  color="grey"
                  v-bind="attrs"
                  v-on="on"
                  @click="get(item.no)"
                  v-show="userInfo.userid == item.userid"
                >
                  <v-icon>mdi-pencil</v-icon>
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
                        <v-rating
                          v-model="rating"
                          background-color="orange lighten-2"
                          color="orange"
                          small
                        ></v-rating>
                        <v-text-field
                          v-model="content"
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
                  <v-btn color="grey darken-1" text @click="update = false">
                    취소
                  </v-btn>
                  <v-btn color="blue darken-1" text @click="updat(item.no)">
                    수정
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="remove" persistent max-width="290">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  color="grey"
                  v-bind="attrs"
                  v-on="on"
                  @click="get(item.no)"
                  v-show="userInfo.userid == item.userid"
                >
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </template>
              <v-card>
                <v-card-title class="text-h6">
                  정말로 삭제하시겠습니까?
                </v-card-title>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="green darken-1" text @click="remove = false">
                    취소
                  </v-btn>
                  <v-btn color="red darken-1" text @click="del"> 삭제 </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="check" persistent max-width="290">
              <v-card>
                <v-card-title class="text-h6"> 수정되었습니다 </v-card-title>

                <v-card-actions>
                  <v-btn color="green darken-1" text @click="check = false">
                    확인
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </td>
        </tr>
      </template>
    </v-data-table>
  </div>
</template>

<script>
// import HouseListItem from "@/components/house/HouseListItem.vue";
import { mapState, mapActions } from "vuex";
import { apiInstance } from "@/api/index";
// import ReviewUpdate from "./ReviewUpdate.vue";
const houseStore = "houseStore";
const memberStore = "memberStore";
export default {
  components: {},
  name: "ReviewList",

  // components: {},
  data: () => ({
    reviews: [],
    remove: false,
    update: false,
    content: "",
    rating: 0,
    ratings: {},
    no: 0,
    check: false,
    pp: 10,
    headers: [
      {
        text: "번호",
        align: "start",
        value: "no",
      },
      { text: "내용", value: "content" },
      { text: "별점", value: "rating" },
      { text: "작성자", value: "userid" },
      { text: "" },
    ],
  }),
  created() {
    apiInstance()
      .get(`/review/list/${this.house.aptCode}`)
      .then(({ data }) => {
        // console.log(data);
        this.reviews = data;
      })
      .catch((error) => {
        console.log(error);
      });
    for (let i = 0; i <= 5; i++) {
      apiInstance()
        .get(`/review/rcount`, {
          params: {
            aptCode: this.house.aptCode,
            rating: i,
          },
        })
        .then(({ data }) => {
          // console.log(data);
          this.rating[i] = data;
        })
        .catch((error) => {
          console.log(error);
        });
    }
  },
  computed: {
    ...mapState(houseStore, ["house"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  watch: {
    reviews: function () {
      apiInstance()
        .get(`/review/list/${this.house.aptCode}`)
        .then(({ data }) => {
          // console.log(data);
          this.reviews = data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),

    del() {
      // console.log(no);
      apiInstance()
        .delete(`/review/${this.no}`)
        .then(({ data }) => {
          if (data === "success") {
            this.remove = false;
          } else {
            alert("삭제실패!");
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    get(no) {
      console.log(no);
      apiInstance()
        .get(`/review/${no}`)
        .then(({ data }) => {
          this.no = data.no;
          this.content = data.content;
          this.rating = data.rating;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    updat(no) {
      console.log(no);
      apiInstance()
        .put(`/review/${no}`, {
          no: this.no,
          rating: this.rating,
          content: this.content,
        })
        .then(({ data }) => {
          if (data === "success") {
            this.update = false;
            this.check = true;
          } else {
            alert("수정실패");
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped></style>
