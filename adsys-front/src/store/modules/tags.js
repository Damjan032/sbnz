import axios from "axios";

const state = {
  tags: [],
};

const mutations = {
  setTags(state, tags) {
    state.tags = tags;
  },
};

const actions = {
  async getTagsAction({ commit }) {
    try {
      const { data } = await axios.post("/api/tags");
      commit("setTags", data);
    } catch (e) {
      console.log(e);
    }
  },
};

const getters = {
  getTags: (state) => state.tags,
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
