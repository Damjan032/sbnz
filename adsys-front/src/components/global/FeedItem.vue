<template>
  <v-card class="py-4 px-2" dark>
    <v-row class="pt-6 px-6">
      <v-avatar>
        <!-- src="https://randomuser.me/api/portraits/men/99.jpg" -->
        <img :src="image" alt="John" height="36px" width="36px" />
      </v-avatar>
      <p class="px-5 pt-3">{{ advertiser }}</p>
    </v-row>
    <v-row class="px-5 pt-5">
      <p align="left">
        {{ content }}
      </p>
    </v-row>
    <a href="">
      <v-row class="px-1 pt-10">
        <v-img src="https://picsum.photos/400/500" width="300px" height="500" />
      </v-row>
    </a>
  </v-card>
</template>

<script>
export default {
  props: {
    image: String,
    advertiser: String,
    content: String
  },
};
</script>

<style></style>
