import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import Axios from "axios";
import VueAxios from "vue-axios";


Vue.use(VueAxios, Axios);
Vue.axios.defaults.baseURL = "http://localhost:8088";

export const bus = new Vue();


Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
