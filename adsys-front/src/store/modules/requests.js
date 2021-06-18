import axios from "axios";

const state = {
  requests: [],
};

const mutations = {
  setRequests(state, requests) {
    state.requests = requests;
  },

  addRequest(state, request) {
    state.request.push(request);
  },
};

const actions = {
  async getRequestsAction({ commit }) {
    try {
      const { data } = await axios.get("/api/requests");
      commit("setRequests", data);
    } catch (e) {
      console.log(e);
    }
  },

  async submitRequestAction({ commit }, payload) {
    try {
      const { data } = await axios.post("/api/requests", payload);
      commit("addRequest", data);
    } catch (e) {
      console.log(e);
    }
  },
};

const getters = {
  getRequests: (state) => state.requests,
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
