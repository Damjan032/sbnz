<template>
  <v-app>
    <left-sidebar class="hidden-md-and-down" />
    <!-- <right-sidebar class="hidden-lg-and-down" /> -->
    <nav-bar />
    <v-main class="content">
      <v-container fluid class="outer-cont pa-0">
        <router-view />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import NavBar from "../components/NavBar";
// import RightSidebar from "../components/global/RightSidebar.vue";
import LeftSidebar from "../components/global/LeftSidebar.vue";

export default {
  name: "Main",
  components: { LeftSidebar, NavBar },
};
</script>

<style>
.content {
  background-color: #f0f0f0;
}

.lighter-back {
  background-color: #202324!important;
}

.hoverable:hover {
  background-color: #2B2C2D;
  cursor: pointer;
  border-radius: 7px;
}

.outer-cont {
  height: 100%;
}

html,body { height:100%; }
</style>
