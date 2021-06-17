<template>
  <v-container class="blue lighten-1 fill-height" fluid>
    <v-progress-linear
      :active="loading"
      :indeterminate="loading"
      absolute
      top
      height="3px"
      color="primary"
    ></v-progress-linear>
    <v-row justify="center">
      <v-col cols="6">
        <v-card outlined>
          <v-row align="center" class="mt-9" justify="center">
            <v-col cols="12" md="8" class="text-center py-0"
              ><span class="display-1">Sign in</span></v-col
            >
          </v-row>
          <v-row align="center" justify="center">
            <v-col cols="12" md="8" class="text-center py-0"
              ><span class="subtitle-1"></span
            ></v-col>
          </v-row>
          <v-form ref="form" v-model="valid">
            <v-container>
              <v-text-field
                v-model="formElements.email"
                :rules="[rules.required]"
                label="Korisnicko ime"
                required
              ></v-text-field>

              <v-text-field
                :type="'password'"
                v-model="formElements.password"
                :rules="[rules.required]"
                label="Lozinka"
                required
              ></v-text-field>
              <v-btn
                :disabled="!valid"
                color="success"
                class="mr-4"
                @click="postLogin"
              >
                Login
              </v-btn>
            </v-container>
          </v-form>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

import Vue from "vue";

export default {
  name: "LoginView",
  data: () => ({
    valid: false,
    email: "",
    password: "",
    loginStep: 1,
    loading: false,
    userNameError: {
      isError: false,
      errorMessage: "",
    },
    loginError: {
      isError: false,
      errorMessage: "",
    },
    authRequest: {
      email: null,
      password: null,
    },
    formElements: {
      userName: null,
      password: null,
    },
    name: null,
    rules: {
      required: (value) => !!value || "Required.",
    },
  }),
  computed: {
    ...mapGetters([
      {
        isLogged: "auth/isLogged",
      },
    ]),
  },
  watch: {
    loginStep(val) {
      console.log(val);
    },
    loggedIn() {
      this.loading = false;

      if (this.isLogged) {
        this.$router.push("/");
      } else {
        this.loginError.isError = true;
        this.loginError.errorMessage = "Incorrect password";
      }
    },
  },
  methods: {
    ...mapActions("auth", ["login"]),
    postLogin() {
      this.authRequest.password = this.formElements.password;
      this.authRequest.email = this.formElements.email;
      this.login(this.authRequest);
    },
    checkEmail() {
      this.userNameError.isError = false;
      this.userNameError.errorMessage = "";
      let valid = this.$refs.userNameForm.validate();
      if (valid) {
        this.loading = true;
        this.exists();
      }
    },
    async exists() {
      try {
        this.name = (
          await Vue.prototype.$axios.get(
            `/api/auth/exists/${this.formElements.userName}`
          )
        ).data;
        this.authRequest.userName = this.formElements.userName;
        this.loginStep = 2;
      } catch (err) {
        this.userNameError.isError = true;
        this.userNameError.errorMessage = `Couldn't find your account`;
      } finally {
        this.loading = false;
      }
    },
    returnToEmail() {
      this.$refs.userNameForm.reset();

      this.loginStep = 1;
      this.$nextTick(() => {
        this.$refs.passwordForm.reset();
      });
    },
    goBack(event) {
      alert("GOBACK");
      if (this.loginStep === 2) {
        event.stop();
        this.returnToEmail();
      }
    },
  },
  mounted() {
    document.addEventListener("backbutton", this.goBack, false);
  },
  destroyed() {
    document.removeEventListener("backbutton", this.goBack);
  },
};
</script>

<style scoped></style>
