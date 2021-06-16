<template>
  <v-list-item class="pl-0 hoverable pr-0">
    <v-avatar class="mr-5" height="35px" width="35px">
      <v-icon dark>
        {{ icon }}
      </v-icon>
    </v-avatar>
    <v-col>
      <v-list-item-title>{{ title }}</v-list-item-title>
    </v-col>
    <template v-if="rightIcon">
      <v-spacer></v-spacer>
      <v-icon large>{{rightIcon}}</v-icon>
    </template>
  </v-list-item>
</template>

<script>
export default {
  props: {
    title: String,
    icon: String,
    rightIcon: String,
  },
};
</script>

<style></style>
