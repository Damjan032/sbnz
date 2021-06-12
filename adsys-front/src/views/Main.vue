<template>
  <v-app>
    <drawer v-if="isSuperAdmin" :mini="miniDrawer" @miniVariant="miniDrawer = !miniDrawer"/>
    <nav-bar />
    <v-main class="content">
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import NavBar from "../components/NavBar";
import Drawer from "../components/Drawer";

export default {
  name: "Main",
  components: {Drawer, NavBar},
  data: () => ({
    miniDrawer: true,
  }),
  methods: {
    isSuperAdmin : function() {
      console.log(this.$store.state.auth.authorities)
      return false
    }
  }
}
</script>

<style scoped>
.content {
  background-color: #f0f0f0;
}
</style>
