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
    state.authorities = response.authorities;
    state.token = response.accessToken;
    state.user = response.user;
  },

  removeLoggedUser(state) {
    state.user = null;
    state.token = null;
    state.authorities = [];
  },
};

const actions = {
  async login({ commit }, authRequest) {
    try {
      const { data } = await axios.post("/auth/login", {
        email: authRequest.email,
        password: authRequest.password,
      });

      localStorage.setItem("token", JSON.stringify(data.token));
      commit("setLoggedUser", data);
      router.push("/");
    } catch (e) {
      console.log(e);
    }
  },

  async logout({ commit }) {
    localStorage.removeItem("token");
    commit("removeLoggedUser");

    router.push("/auth");
  },
};

const getters = {
  getUser: (state) => state.user,
  isLogged: (state) => state.user === null,
  isAdmin: (state) => state.authorities.some((auth) => auth.name === "ADMIN"),
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
