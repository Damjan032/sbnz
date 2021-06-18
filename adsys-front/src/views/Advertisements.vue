<template>
  <div class="main-container">
    <v-row justify="center">
      <v-col cols="7" xl="7" lg="7" md="12" sm="12" xs="12" align="center">
        <template v-if="requests.length > 0">
          <ad-request-item
            v-for="item in requests"
            :key="item.id"
            :request="item"
            class="mb-12"
          />
        </template>

        <template v-else>
          <v-card class="status-card lighter-back" dark>
            <v-row class="py-4 px-12" justify="center">
              <v-col class="mx-12">
                <img
                  class="hu5pjgll"
                  height="112"
                  src="https://www.facebook.com/images/comet/empty_states_icons/general/general_dark_mode.svg"
                  width="112"
                  alt=""
                />
                <p class="headline pt-2">No advertisements to show</p>
                <p class="subtitle-1">
                  This may be because of a technical error or the lack of new
                  ads on our site. Please try reloading this page later.
                </p>
                <v-btn class="blue mt-3">Reload Page</v-btn>
              </v-col>
            </v-row>
          </v-card>
        </template>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import AdRequestItem from "../components/admin/AdRequestItem.vue";

export default {
  components: {AdRequestItem},

  async created() {
    await this.getRequests();
    console.log(this.requests)
  },

  computed: {
    ...mapGetters({
      requests: "requests/getRequests",
    }),
  },

  methods: {
    ...mapActions({
      getRequests: "requests/getRequestsAction",
    }),
  },
};
</script>
