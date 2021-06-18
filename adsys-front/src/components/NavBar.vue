<template>
  <v-app-bar class="navigation py-0 lighter-back" flat dark fixed>
    <v-row class="px-2 py-0" align="center">
      <v-col cols="4" class="px-0">
        <div class="fb-img mx-3">
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

      <v-col cols="4" align="center">
        <v-row justify="center">
          <div class="toolbar-btn hoverable">
            <v-icon :color="currentPath('/')" large>mdi-home</v-icon>
          </div>

          <div class="toolbar-btn hoverable">
            <v-icon :color="currentPath('/pages')" large>mdi-flag</v-icon>
          </div>

          <div v-if="isAdmin" class="toolbar-btn hoverable">
            <v-icon :color="currentPath('/admin')" large>mdi-map-plus</v-icon>
          </div>
        </v-row>
      </v-col>

      <v-col cols="4" align="end">
        <span class="mx-2 py-2 pr-4 pl-1 hoverable profile-hov">
          <v-avatar class="mr-2" dark height="30px" width="30px">
            <img :src="`https://randomuser.me/api/portraits/men/${user.id}.jpg`" />
          </v-avatar>
          <span class="body 2">{{ user.firstName }}</span>
        </span>
        <create-menu v-if="isAdmin" />
        <dropdown-menu />
      </v-col>
    </v-row>
  </v-app-bar>
</template>

<script>
import CreateMenu from "./admin/CreateMenu.vue";
import DropdownMenu from "./global/DropdownMenu.vue";

import { mapGetters } from "vuex";

export default {
  name: "NavBar",
  components: { CreateMenu, DropdownMenu },
  data: () => ({
    menuActive: false,
  }),

  computed: {
    ...mapGetters({
      user: "auth/getUser",
      isAdmin: 'auth/isAdmin'
    }),
  },

  methods: {
    currentPath(current) {
      if (current === window.location.pathname) return "blue";
      return "white";
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

.hoverable:hover {
  cursor: pointer;
  background-color: #393a3b;
  border-radius: 7px;
}

.profile-hov {
  border-radius: 20px !important;
}
</style>
