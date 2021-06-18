<template>
  <div class="main-container"></div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  components: {},

  async created() {
    await this.getAdsAction();
  },

  methods: {
    ...mapActions({
      getAdsAction: "advertisements/getAllAdvertisementsAction",
    }),
  },
};
</script>
