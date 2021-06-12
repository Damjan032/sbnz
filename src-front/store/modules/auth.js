import router from "../../router/index";
import axios from "axios";


const state = {
  user: null,
  token: null,
  authorities: [],
  roles: [],
};

const mutations = {
  setLoggedUser(state, response) {
    state.user = response.user;
    state.authorities = response.authorities;
    state.roles = response.roles;
  },

  removeLoggedUser(state) {
    state.user = null;
    state.authorities = [];
    state.roles = [];
  },
};

const actions = {
  async login({ commit }, authRequest) {
    try {
      const { data } = await axios.post("/auth/login", authRequest);
      console.log(data);
      sessionStorage.setItem("token", JSON.stringify(data.token));
      commit("setLoggedUser", data);


      router.push("/");
    } catch (e) {
      console.log(e);
    }
  },
  async logout({ commit }) {
    sessionStorage.removeItem("token");
    commit("removeLoggedUser");

    router.push("/auth");
  },
};

const getters = {
  getUser: (state) => state.user,
  isLogged: (state) => state.user === null,
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
