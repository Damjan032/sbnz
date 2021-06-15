import Vue from "vue";
import Vuex from "vuex";
import VuexPersist from 'vuex-persist';
import advertisers from "./modules/advertisers";
import auth from "./modules/auth";
import pages from "./modules/pages";
import tags from "./modules/tags";

Vue.use(Vuex);

const vuexPersist = new VuexPersist({
  key: 'my-app',
  storage: window.localStorage,
});

export default new Vuex.Store({
  plugins: [vuexPersist.plugin],
  namespaced: true,
  actions: {},
  modules: {
    auth: auth,
    pages: pages,
    advertisers: advertisers,
    tags: tags,
  },
});
