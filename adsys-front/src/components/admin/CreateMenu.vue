<template>
  <v-menu offset-y >
    <template v-slot:activator="{ on, attrs }">
      <v-btn class="menu-btn mx-6" fab dark small v-bind="attrs" v-on="on">
        <v-icon dark>
          mdi-plus
        </v-icon>
      </v-btn>
    </template>
    <v-list class="pa-4" dark>
      <p class="px-3 py-2 title">Create</p>

      <div v-for="item in items" :key="item.title">
        <v-list-item class="pl-0 hoverable" @click.stop="item.onClick">
          <v-avatar class="mr-5" height="35px" width="35px">
            <v-icon dark>
              {{ item.icon }}
            </v-icon>
          </v-avatar>
          <v-col>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
            <v-list-item-subtitle>{{ item.subtitle }}</v-list-item-subtitle>
          </v-col>
        </v-list-item>
        <v-divider v-if="item.dividerAfter" class="my-4"></v-divider>
      </div>
    </v-list>
    <new-ad-dialog v-model="showRequestDialog" />
  </v-menu>
</template>

<script>
import NewAdDialog from "./NewAdDialog.vue";

export default {
  components: {
    NewAdDialog,
  },

  data() {
    return {
      showRequestDialog: false,
      items: [
        {
          title: "Advertisement Request",
          subtitle: "Share a new advertisement on News Feed.",
          icon: "mdi-image-edit",
          onClick: () => (this.showRequestDialog = true),
          dividerAfter: true,
        },
        {
          title: "Page",
          subtitle: "Connect with peole who share your interests.",
          icon: "mdi-book-open-page-variant-outline",
          onClick: () => {},
        },
        {
          title: "Company",
          subtitle: "Connect and share with customers and fans.",
          icon: "mdi-domain",
          onClick: () => {},
        },
      ],
    };
  },

  methods: {},
};
</script>

<style lang="scss" scoped>
.menu-btn {
  background-color: rgba(255, 255, 255, 0.1) !important;
}
</style>
