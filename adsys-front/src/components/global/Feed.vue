<template>
  <div class="main-container">
    <v-row justify="center">
      <v-col cols="7" xl="7" lg="7" md="12" sm="12" xs="12" align="center">
        <v-card class="status-card lighter-back pb-4 pt-2 px-6 mb-12" dark>
          <v-row class="pt-6" align="start">
            <v-avatar class="mr-5">
              <img
                :src="`https://randomuser.me/api/portraits/men/${user.id}.jpg`"
                alt="John"
                height="36"
                width="36px"
              />
            </v-avatar>
            <v-text-field
              class="status-update pt-1"
              :placeholder="`What's on your mind, ${user.firstName}?`"
              filled
              rounded
              dense
            ></v-text-field>
          </v-row>

          <v-row class="pb-2">
            <v-col
              class="status-link hoverable"
              v-for="item in statusActions"
              :key="item.name"
            >
              <v-icon class="mr-2" :color="item.color">{{ item.icon }}</v-icon>
              {{ item.name }}
            </v-col>
          </v-row>
        </v-card>

        <template v-if="advertisements.length > 0">
          <feed-item
            v-for="item in advertisements"
            @clicked="onAdClick"
            @viewed="onAdView"
            :key="item.id"
            :ad="item"
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
                <v-btn @click="reload" class="blue mt-3">Reload Page</v-btn>
              </v-col>
            </v-row>
          </v-card>
        </template>
        
      </v-col>
    </v-row>
  </div>
</template>

<script>
import FeedItem from "./FeedItem.vue";
import { mapActions, mapGetters } from "vuex";

export default {
  components: { FeedItem },

  async created() {
    await this.getAdvertisersAction();
    await this.getTagsAction();
    await this.getAdsAction(this.user.id);
  },

  data() {
    return {
      statusActions: [
        { name: "Live video", icon: "mdi-camera", color: "red" },
        {
          name: "Photo/Video",
          icon: "mdi-image-multiple-outline",
          color: "green",
        },
        {
          name: "Feeling/Activity",
          icon: "mdi-emoticon-excited-outline",
          color: "yellow",
        },
      ],
      seenAds: {},
    };
  },

  computed: {
    ...mapGetters({
      user: "auth/getUser",
      advertisements: "advertisements/getAdvertisements",
    }),
  },

  methods: {
    ...mapActions({
      getAdvertisersAction: "advertisers/getAdvertisersAction",
      getTagsAction: "tags/getTagsAction",
      getAdsAction: "advertisements/getAdvertisementsToBeSeenAction",
      adSeenAction: "advertisements/advertisementSeenAction",
      adClickedAcion: "advertisements/advertisementClickedAction",
    }),

    async onAdView(id) {
      if (id in this.seenAds) return;
      this.seenAds[id] = true;

      await this.adSeenAction({ userId: this.user.id, advertisementId: id });
    },

    onAdClick(id) {
      this.adClickedAcion({ userId: this.user.id, advertisementId: id });
    },

    reload() {
      this.$router.go();
    }
  },
};
</script>

<style lang="scss">
.main-container {
  background-color: #18191a;
  margin-top: 40px;
  padding: 50px 100px;
  height: 100%;
  min-width: 800px;
}

.status-update {
  input {
    background-color: #181a1b !important;
    color: white !important;
  }
}

.status-card {
  border-radius: 10px;
}

.status-link {
}

.status-link:hover {
  border-radius: 10px;
}
</style>
