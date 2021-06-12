import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import Axios from "axios";
import VueAxios from "vue-axios";
import VueToast from "vue-toast-notification";
import "vue-toast-notification/dist/theme-sugar.css";

Vue.use(VueToast);
Vue.use(VueAxios, Axios, VueToast);
Vue.axios.defaults.baseURL = "http://localhost:8080";

Vue.axios.defaults.headers.common["Access-Control-Allow-Origin"] = "*";

Vue.axios.interceptors.request.use(
  (config) => {
    let token = JSON.parse(localStorage.getItem("token"));

    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }

    return config;
  },

  (error) => {
    return Promise.reject(error);
  }
);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
