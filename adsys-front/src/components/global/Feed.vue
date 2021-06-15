<template>
  <div class="main-container">
    <v-row justify="center">
      <v-col cols="7" align="center">
        <v-card class="py-4 px-6 mb-6" dark>
          <v-row class="pt-6" align="start">
            <v-avatar class="mr-5">
              <img
                src="https://randomuser.me/api/portraits/men/99.jpg"
                alt="John"
                height="36"
                width="36px"
              />
            </v-avatar>
            <v-text-field
              class="pt-1"
              placeholder="What's on your mind, Stefan?"
              filled
              rounded
              dense
            ></v-text-field>
          </v-row>
          <v-row class="pb-2">
            <v-col class="status-link" cols="4">
              adf
            </v-col>
            <v-col class="status-link" cols="4">
              adf
            </v-col>
            <v-col class="status-link" cols="4">
              adf
            </v-col>
          </v-row>
        </v-card>

        <v-card class="py-4 px-2" dark>
          <v-row class="pt-6 px-6">
            <v-avatar>
              <img
                src="https://randomuser.me/api/portraits/men/99.jpg"
                alt="John"
                height="36px"
                width="36px"
              />
            </v-avatar>
            <p class="px-5 pt-3">FC Barcelona United</p>
          </v-row>
          <v-row class="px-5 pt-5">
            <p align="left">
              Katalonski TV3 piše kako se trener Marseillea Jorge Sampaoli javio
              nogometašu Barcelone Konradu de la Fuenteu. Argentinac želi
              Amerikanca vidjeti u dresu francuskog prvoligaša iduće sezone.
              Barcelonini ljudi su potvrdili da postoji kontakt s Marseilleom.
              Katalonska uprava obećala je da će 19-godišnjak odradit pripreme s
              Blaugranom, ali sam igrač želi otići iz Kluba i skupiti potrebne
              minute za vlastiti razvoj. Smatrate li kako Amerikanac treba otići
              iz Barcelone?
            </p>
          </v-row>
          <a href="">
            <v-row class="px-1 pt-10">
              <v-img
                src="https://picsum.photos/400/500"
                width="300px"
                height="500"
              />
            </v-row>
          </a>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
.main-container {
  background-color: #18191a;
  margin-top: 40px;
  padding: 50px 100px;
  height: 100%;
  width: 100%;
}

.status-link {
}

.status-link:hover {
  cursor: pointer;
  background-color: gray;
  border-radius: 10px;
}
</style>
