<template>
  <v-app-bar class="navigation py-0" flat dark fixed>
    <v-row class="px-2 py-0" align="center">
      <v-col cols="2" class="px-0">
        <div class="fb-img">
          <svg
            viewBox="0 0 36 36"
            class="a8c37x1j ms05siws hwsy1cff b7h9ocf4"
            fill="url(#jsc_s_3)"
            height="40"
            width="40"
          >
            <defs>
              <linearGradient
                x1="50%"
                x2="50%"
                y1="97.0782153%"
                y2="0%"
                id="jsc_s_3"
              >
                <stop
                  offset="0%"
                  stop-color="#0062E0"
                  data-darkreader-inline-stopcolor=""
                  style="--darkreader-inline-stopcolor:#004eb3;"
                ></stop>
                <stop
                  offset="100%"
                  stop-color="#19AFFF"
                  data-darkreader-inline-stopcolor=""
                  style="--darkreader-inline-stopcolor:#007bbd;"
                ></stop>
              </linearGradient>
            </defs>
            <path
              d="M15 35.8C6.5 34.3 0 26.9 0 18 0 8.1 8.1 0 18 0s18 8.1 18 18c0 8.9-6.5 16.3-15 17.8l-1-.8h-4l-1 .8z"
            ></path>
            <path
              class="p361ku9c"
              d="M25 23l.8-5H21v-3.5c0-1.4.5-2.5 2.7-2.5H26V7.4c-1.3-.2-2.7-.4-4-.4-4.1 0-7 2.5-7 7v4h-4.5v5H15v12.7c1 .2 2 .3 3 .3s2-.1 3-.3V23h4z"
            ></path>
          </svg>
        </div>
      </v-col>

      <v-col cols="8" align="center">
        <v-row justify="center">
          <div class="toolbar-btn">
            <v-icon :color="currentPath('/')" large>mdi-home</v-icon>
          </div>

          <div class="toolbar-btn">
            <v-icon :color="currentPath('/pages')" large>mdi-flag</v-icon>
          </div>

          <div class="toolbar-btn">
            <v-icon :color="currentPath('/admin')" large>mdi-map-plus</v-icon>
          </div>
        </v-row>
      </v-col>

      <v-col cols="2" align="end">
        <avatar-menu :menu-active="menuActive"> </avatar-menu>
      </v-col>
    </v-row>
  </v-app-bar>
</template>

<script>
import AvatarMenu from "./Auth/AvatarMenu";

export default {
  name: "NavBar",
  components: { AvatarMenu },
  data: () => ({
    menuActive: false,
  }),

  methods: {
    currentPath(current) {
      if (current === window.location.pathname)
        return 'blue';
      return 'white'
    },
  },
};
</script>

<style lang="scss" scoped>
.navigation {
  height: 64px;
}

.fb-img {
  border-radius: 50%;
}

.toolbar-btn {
  width: 100px;
  border-radius: 8px;
  // margin-right: 20px;
}

.toolbar-btn:hover {
  background-color: gray;
  cursor: pointer;
}
</style>
