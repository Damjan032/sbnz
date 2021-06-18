import axios from "axios";

function round(number) {
  return Math.round(number * 1000) / 1000;
}

function addFirstName(candidate) {
  candidate.name = candidate.user.firstName + " " + candidate.user.lastName;
  candidate.finalScore = round(candidate.finalScore);
  candidate.herdCoefficient = round(candidate.herdCoefficient);
}

const state = {
  requests: [],
};

const mutations = {
  setRequests(state, requests) {
    requests.forEach((req) => {
      req.candidates.forEach((candidate) => addFirstName(candidate));
    });
    console.log(requests);
    state.requests = requests;
  },

  addRequest(state, request) {
    request.candidates.forEach((candidate) => addFirstName(candidate));
    state.requests.unshift(request);
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
