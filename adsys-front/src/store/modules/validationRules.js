const state = {
  rules: {
    required: (fieldName) => (v) => !!v || `${fieldName} is required`,
    email: (v) =>
      /^(([^<>()[\]\\.,;:\s@']+(\.[^<>()\\[\]\\.,;:\s@']+)*)|('.+'))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
        v
      ) || "E-mail must be valid",
    name: (fieldName) => (v) =>
      /^([a-z\u00C0-\u02AB'´`]+\.?\s?)+$/i.test(v) ||
      `Invalid characters in ${fieldName}`,
    countryCode: (v) => /^[A-Z]{2}$/.test(v) || "Country code not valid",
    positiveInt: (v) =>
      /^[1-9]\d*$/.test(v) || "Need to be positive integer number",
    stdText: (fieldName) => (v) =>
      /^[a-zA-Z][a-zA-z0-9]+$/.test(v) || `${fieldName} not valid`,
  },
};

const mutations = {};

const actions = {};

const getters = {
  getRules: (state) => state.rules,
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
