<template>
  <div class="main-container">
    <v-row justify="center">
      <v-col cols="7" xl="7" lg="7" md="12" sm="12" xs="12" align="center">
        <v-card class="py-4 px-6 mb-12" dark>
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
              class="pt-1"
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

        <feed-item
          v-for="item in advertisements"
          :key="item.id"
          :ad="item"
          class="mb-12"
        />
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
      items: [
        {
          advertiser: "FC Barcelona United",
          image: "https://randomuser.me/api/portraits/men/99.jpg",
          content:
            "Katalonski TV3 piše kako se trener Marseillea Jorge Sampaoli javio nogometašu Barcelone Konradu de la Fuenteu. Argentinac želi Amerikanca vidjeti u dresu francuskog prvoligaša iduće sezone. iz Barcelone?",
        },
      ],
    };
  },

  computed: {
    ...mapGetters({
      user: "auth/getUser",
      advertisements: "advertisements/getAdvertisements"
    }),
  },

  methods: {
    ...mapActions({
      getAdvertisersAction: "advertisers/getAdvertisersAction",
      getTagsAction: "tags/getTagsAction",
      getAdsAction: "advertisements/getAdvertisementsAction"
    }),
  },
};
</script>

<style lang="scss" scoped>
.main-container {
  background-color: #18191a;
  margin-top: 40px;
  padding: 50px 100px;
  height: 100%;
  min-width: 800px;
}

.status-link {
}

.status-link:hover {
  border-radius: 10px;
}
</style>
