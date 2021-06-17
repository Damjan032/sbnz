<template>
  <v-card class="card-outer py-4 px-2" :mouseover="adSeen()" dark>
    <v-row class="px-3">
      <v-col cols="1" align="start">
        <v-avatar>
          <img
            :src="`https://randomuser.me/api/portraits/men/${ad.id}.jpg`"
            alt="John"
            height="36px"
            width="36px"
          />
        </v-avatar>
      </v-col>

      <v-col align="start">
        <div>
          {{ ad.advertiser.name }}
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
        <v-btn icon>
          <v-icon>mdi-dots-horizontal</v-icon>
        </v-btn>
      </v-col>
    </v-row>
    <v-row class="px-5 pt-5">
      <p align="left">
        {{ ad.content }}
      </p>
    </v-row>
    <a :click="adClicked()" :href="ad.targetUrl" target="__blank">
      <v-row class="px-1 pt-5">
        <v-img
          class="item-image"
          src="https://picsum.photos/400/500"
          width="300px"
          height="500"
        />
      </v-row>
    </a>
  </v-card>
</template>

<script>
export default {
  props: {
    ad: {
      content: String,
      id: Number,
      advertiser: {
        name: String,
      },
    },
  },

  methods: {
    adSeen() {
      alert('clicked');
    },

    adClicked() {
      alert('clicked');
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
