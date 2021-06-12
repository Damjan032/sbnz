<template>
  <v-app>
    <v-app-bar
        app
        color="primary"
        dark
    >
    </v-app-bar>

    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: "Login",
}
</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: all .5s
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>
