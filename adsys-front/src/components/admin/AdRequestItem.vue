<template>
  <v-card class="card-outer py-4 px-0 lighter-back" dark>
    <v-row class="px-3 mx-1">
      <v-col cols="1" align="start">
        <v-avatar>
          <img
            :src="
              `https://randomuser.me/api/portraits/men/${request.advertisement.id}.jpg`
            "
            alt="John"
            height="36px"
            width="36px"
          />
        </v-avatar>
      </v-col>

      <v-col class="px-3" align="start">
        <div>
          {{ request.advertisement.advertiser.name }}
          <i
            class="mx-1"
            data-visualcompletion="css-img"
            aria-label="Verified account"
            role="img"
            style="background-image:url('https://static.xx.fbcdn.net/rsrc.php/v3/ym/r/2VNwseEvSnT.png');background-position:-58px -73px;background-size:auto;width:12px;height:12px;background-repeat:no-repeat;display:inline-block"
          />
        </div>
        <div class="sponsored subtitle-2">Sponsored</div>
      </v-col>

      <v-col cols="1" align="end">
        <v-btn large icon>
          <v-icon>mdi-dots-horizontal</v-icon>
        </v-btn>
      </v-col>
    </v-row>
    <v-row class="px-5 pt-5 mx-1 mb-2">
      <p align="left">
        {{ request.advertisement.content }}
      </p>
    </v-row>

    <v-data-table
      :headers="headers"
      :items="request.candidates"
      class="elevation-1"
    >
      <template v-slot:[`item.score`]="{ item }">
        <v-chip :color="getColor(item.finalScore)" dark>
          {{ item.finalScore }}
        </v-chip>
      </template>

      <template v-slot:[`item.ageGroup`]="{ item }">
        <v-icon v-if="item.ageGroup == true">mdi-check-all</v-icon>
        <v-icon v-else>mdi-close-outline</v-icon>
      </template>

      <template v-slot:[`item.geographyGroup`]="{ item }">
        <v-icon v-if="item.geographyGroup == true">mdi-check-all</v-icon>
        <v-icon v-else>mdi-close-outline</v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
export default {
  props: {
    request: {
      ad: {
        content: String,
        id: Number,
        advertiser: {
          name: String,
        },
      },
      candidates: Array,
    },
  },

  data() {
    return {
      headers: [
        { text: "Name", value: "name", align: "start", sortable: false },
        {
          text: "email",
          value: "user.email",
          align: "center",
          sortable: false,
        },
        {
          text: "Final score",
          value: "score",
          align: "center",
          sortable: false,
        },
        {
          text: "Herd Coefficient",
          value: "herdCoefficient",
          align: "center",
          sortable: false,
        },
        {
          text: "Is Age group",
          value: "ageGroup",
          align: "center",
          sortable: false,
        },
        {
          text: "Geography group",
          value: "geographyGroup",
          align: "center",
          sortable: false,
        },
      ],
    };
  },

  methods: {
    cl(inp) {
      console.log(inp);
    },
    getColor(score) {
      if (score < 0) return "red";
      else if (score < 5) return "orange";
      else return "green";
    },
  },
};
</script>

<style lang="scss" scoped>
.card-outer {
  border-radius: 10px !important;
  padding-bottom: 0px !important;
}

.item-image {
  border-radius: 0px 0px 10px 10px !important;
}

.sponsored {
  color: gray;
}
</style>
