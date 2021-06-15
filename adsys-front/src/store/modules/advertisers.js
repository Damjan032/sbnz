import axios from "axios";

const state = {
  advertisers: [],
};

const mutations = {
  setAdvertisers(state, advertisers) {
    state.advertisers = advertisers;
  },
};

const actions = {
  async getAdvertisersAction({ commit }) {
    try {
      const { data } = await axios.post("/api/advertisers");
      commit("setAdvertisers", data);
    } catch (e) {
      console.log(e);
    }
  },
};

const getters = {
  getAdvertisers: (state) => state.advertisers,
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
