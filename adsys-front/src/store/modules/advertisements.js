import axios from "axios";

const state = {
  advertisements: [],
};

const mutations = {
  setAdvertisements(state, advertisements) {
    state.advertisements = advertisements;
  },
};

const actions = {
  async getAdvertisementsAction({ commit }, userId) {
    try {
      const { data } = await axios.get(`/api/posts/toBeSeen/${userId}`);
      commit("setAdvertisements", data);
    } catch (e) {
      console.log(e);
    }
  },
};

const getters = {
  getAdvertisements: (state) => state.advertisements,
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
