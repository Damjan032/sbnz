<template>
  <v-app-bar
      absolute
      color="teal lighten-3"
      flat
      app
      dark
  >
    <v-toolbar-title >Super Admin Application</v-toolbar-title>

    <v-spacer></v-spacer>
    <avatar-menu :menu-active="menuActive"></avatar-menu>
  </v-app-bar>
</template>

<script>
import AvatarMenu from "./Auth/AvatarMenu";

export default {
  name: "NavBar",
  components: {AvatarMenu},
  data: () => ({
    menuActive: false
  }),
}
</script>

<style scoped>
</style>
