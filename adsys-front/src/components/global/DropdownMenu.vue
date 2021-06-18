<template>
  <v-menu offset-y>
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        class="menu-btn"
        fab
        dark
        small
        v-bind="attrs"
        v-on="on"
      >
        <v-icon dark>
          mdi-menu-down
        </v-icon>
      </v-btn>
    </template>
    <v-list class="pa-4 lighter-back" dark>
      <v-list-item class="pl-0 hoverable">
        <v-avatar class="mr-5" height="50px" width="50px">
          <v-icon dark>
            mdi-image-edit
          </v-icon>
        </v-avatar>
        <v-col>
          <v-list-item-title>Placeholder placeholder</v-list-item-title>
          <v-list-item-subtitle
            >Lorem ipsum dolores amaret lorem orem</v-list-item-subtitle
          >
        </v-col>
      </v-list-item>

      <div v-for="item in items" :key="item.name">
        <v-divider v-if="item.dividerBefore" class="my-4"></v-divider>
        <dropdown-item
          :title="item.name"
          :icon="item.icon"
          :rightIcon="item.rightIcon"
          @click.native="item.onClick"
        />
        <v-divider v-if="item.dividerAfter" class="my-4"></v-divider>
      </div>
    </v-list>
  </v-menu>
</template>

<script>
import DropdownItem from "./DropdownItem.vue";
import { mapActions } from "vuex";

export default {
  components: { DropdownItem },

  data() {
    return {
      items: [
        {
          name: "Give Feedback",
          icon: "mdi-message-plus",
          rightIcon: "",
          onClick: () => {},
          dividerAfter: true,
          dividerBefore: true,
        },
        {
          name: "Settings & privacy",
          icon: "mdi-cog",
          rightIcon: "mdi-chevron-right",
          onClick: () => {},
        },
        {
          name: "Help & support",
          icon: "mdi-help-circle",
          rightIcon: "mdi-chevron-right",
          onClick: () => {},
        },
        {
          name: "Display & accessibility",
          icon: "mdi-moon-waning-crescent",
          rightIcon: "mdi-chevron-right",
          onClick: () => {},
        },
        { name: "Log Out", icon: "mdi-logout", onClick: () => this.logout() },
      ],
    };
  },

  methods: {
    ...mapActions({
      logoutAction: "auth/logout",
    }),

    async logout() {
      await this.logoutAction();
    },
  },
};
</script>

<style lang="scss">
.menu-btn {
  background-color: rgba(255, 255, 255, 0.1) !important;
}
</style>
