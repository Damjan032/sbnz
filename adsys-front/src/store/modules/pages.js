import axios from "axios";

const state = {
  pages: [],
};

const mutations = {
  setPages(state, pages) {
    state.pages = pages;
  },
};

const actions = {
  async getPagesAction({ commit }) {
    try {
      const { data } = await axios.post("/api/pages");
      commit("setPages", data);
    } catch (e) {
      console.log(e);
    }
  },
};

const getters = {
  getPages: (state) => state.pages,
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
