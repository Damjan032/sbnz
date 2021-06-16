<template>
  <v-dialog
    v-model="show"
    width="600px"
    @click:outside="close"
    :retain-focus="false"
    dark
  >
    <v-card class="pa-3">
      <v-card-title>New Advertisement Request</v-card-title>
      <v-card-text>
        <v-form ref="form" class="mt-2 mr-5" lazy-validation v-model="valid">
          <v-card-title class="px-0 ">Advertisement data</v-card-title>
          <v-select
            :dark="true"
            v-model="request.advertiser"
            :items="advertisers"
            item-text="name"
            label="Company"
            return-object
          />

          <v-text-field
            v-model="request.advertisement.content"
            label="Content"
          />

          <v-combobox
            dark
            v-model="request.advertisement.tags"
            :items="tags"
            item-text="value"
            label="Tags"
            multiple
            chips
            deletable-chips
          ></v-combobox>

          <v-text-field
            v-model="request.advertisement.targetUrl"
            label="Target url"
          />

          <v-card-title class="px-0">Metadata</v-card-title>

          <v-row>
            <v-col>
              <v-text-field
                v-model="request.minAge"
                type="number"
                label="Minimum candidate age"
              />
            </v-col>

            <v-col>
              <v-text-field
                v-model="request.maxAge"
                type="number"
                label="Maximum candidate age"
              />
            </v-col>

            <v-col>
              <v-text-field
                v-model="request.budget"
                type="number"
                label="Budget"
              />
            </v-col>
          </v-row>

          <v-text-field
            v-model="request.geographicLocation"
            label="Geographical location"
          />
        </v-form>
      </v-card-text>
      <v-card-actions class="mr-3">
        <v-spacer></v-spacer>
        <v-btn @click="close" text>Cancel</v-btn>
        <v-btn color="primary" @click="add" text>Add</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  components: {},

  props: {
    value: Boolean,
  },

  data() {
    return {
      dialog: true,
      valid: false,
      rule: [(v) => !!v || "Requred field"],
      request: {
        id: 0,
        minAge: 0,
        maxAge: 99,
        geographicLocation: "Serbia",
        advertiser: null,
        budget: 20,
        advertisement: {
          title: "",
          content: "",
          targetUrl: "www.google.rs",
          advertiser: null,
        },
      },
      // advertisers: [{ name: "Google" }, { name: "Facebook" }],
      // tags: [
      //   "sport",
      //   "weather",
      //   "media",
      //   "technology",
      //   "western",
      //   "movie",
      //   "etc",
      // ],
    };
  },

  computed: {
    ...mapGetters({
      advertisers: "advertisers/getAdvertisers",
      tags: "tags/getTags",
    }),

    show: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit("input", value);
      },
    },
  },

  methods: {
    // ...mapActions({
    //   addPaintingAction: "paintings/addPaintingAction",
    //   geocodeForward: "geocoder/geocodeForward",
    //   updatePaintingAction: "paintings/updatePaintingAction",
    // }),

    // ...mapMutations({
    //   setContext: "artists/setContext",
    // }),

    async add() {
      // await this.addPaintingAction(this.painting);
      // this.reset();
      // this.$refs.form.resetValidation();
      console.log(this.request);
    },

    // async update() {
    //   this.close();
    //   this.$store.dispatch(
    //     "snackbar/showSuccess",
    //     "Painting was successfully updated"
    //   );
    // },

    close() {
      // if (this.$refs.form) this.$refs.form.resetValidation();
      this.show = false;
      this.reset();
    },

    reset() {
      this.request = {
        id: 0,
        minAge: 0,
        maxAge: 99,
        geographicLocation: "Serbia",
        advertiser: null,
        budget: 20,
        advertisement: {
          title: "",
          content: "",
          targetUrl: "www.google.rs",
          advertiser: null,
        },
      };
    },
  },
};
</script>

<style></style>
