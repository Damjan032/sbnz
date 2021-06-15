<template>
  <v-navigation-drawer
    class="left-side-bar"
    permanent
    fixed
    dark
    app
  >
    <template v-slot:prepend>
      <v-list dense>
        <div class="side-card mx-2 my-1">
          <v-list-item class="px-3">
            <v-list-item-avatar>
              <img src="https://randomuser.me/api/portraits/women/81.jpg" />
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>Jane Smith</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </div>

        <div
          v-for="item in items"
          :key="item.title"
          class="side-card mx-2 my-1"
        >
          <v-list-item class="px-3">
            <v-list-item-avatar>
              <img
                class="icon-img"
                :src="item.src"
                style="height: 36px; width: 36px;"
              />
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </div>
      </v-list>
    </template>
  </v-navigation-drawer>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          title: "COVID-19 Information Centre",
          src:
            "https://static.xx.fbcdn.net/rsrc.php/v3/yx/r/5rR6LRpNc5u.png?_nc_eui2=AeHAw0rkRkLBz7aIrFynRDi0FjhaWUpP68wWOFpZSk_rzPUpi7hp9IiLr0h_KvM9HDtsWqxVY-HVOj0dk2PXvQgO",
        },
        {
          title: "Friends",
          src:
            "https://static.xx.fbcdn.net/rsrc.php/v3/y8/r/S0U5ECzYUSu.png?_nc_eui2=AeFFXKDWfQXhvjTxGxWhydOqqfpKmA4GtxSp-kqYDga3FECaKHSW975Jmwyi8PsmsKXBEWKCVdzRvRFUTFHU4K1Y",
        },
        {
          title: "Groups",
          src:
            "https://static.xx.fbcdn.net/rsrc.php/v3/y5/r/PrjLkDYpYbH.png?_nc_eui2=AeF3LPzmPYwVW_qZWuyvp32TZ3xAUM5_bWpnfEBQzn9tahfRNAMozmTLv4icupxz88p4efbzXWPCpmLDCdF4nRQ2",
        },
        {
          title: "Pages",
          src:
            "https://static.xx.fbcdn.net/rsrc.php/v3/yH/r/kyCAf2jbZvF.png?_nc_eui2=AeEPBkFHHN_QRK3G2x0lO5fwtEPFOzh_t-u0Q8U7OH-363ywCQ3Sq8_6pnULwqsBjKnx6BkzGNaQiEwhgzzpBHr0",
        },
        {
          title: "Watch",
          src:
            "https://static.xx.fbcdn.net/rsrc.php/v3/y5/r/duk32h44Y31.png?_nc_eui2=AeFj6zWewLY6c5lF6prPlei72NRDTXGHJ53Y1ENNcYcnnYqFRIc3qcn4api7uusZUH7Vd5xkYHQKE7TrOWPgkQl",
        },
        {
          title: "Events",
          src:
            "https://static.xx.fbcdn.net/rsrc.php/v3/ys/r/8wTx0Eu2vRq.png?_nc_eui2=AeGzRs9JFMCGbGc_MmUGDS21eGQYcQV2gv94ZBhxBXaC_8lvWKd5LN50ghZzIGPWMS1CWoi9JNtjtW3S4anf45RI",
        },
        {
          title: "Memories",
          src:
            "https://static.xx.fbcdn.net/rsrc.php/v3/y6/r/VPndBxotRgH.png?_nc_eui2=AeGqTf27s37EweXIB8VU0a3qFvGCZ9KBlcEW8YJn0oGVwZlvk4pZepdP4WiIqki0s3bqjUtAYkF1AzWRo-T2iHnj",
        },
        {
          title: "Saved",
          src:
            "https://static.xx.fbcdn.net/rsrc.php/v3/yD/r/lVijPkTeN-r.png?_nc_eui2=AeFOUY4GZUjsVeMZLI5-1frE-5kYScafj0T7mRhJxp-PRBjFmNOhfTuMFVfoUyMt2ZtKt7Ulzax7qygX9m8O3MN1",
        },
        {
          title: "Jobs",
          src:
            "https://static.xx.fbcdn.net/rsrc.php/v3/yo/r/DO-SN-shaZL.png?_nc_eui2=AeHa10DH6cihX8eo6RR_R5ReU4NMTXfNRG1Tg0xNd81EbfHRlBYHqOofJKgy-BIlXamtlIogUa1NzcV6RMov4hGG",
        },
      ],
    };
  },
};
</script>

<style lang="scss" scoped>
.left-side-bar {
  margin-top: 64px;
  min-width: 300px !important;
  background-color: #18191a !important;
}

.side-card {
  background-color: transparent;
  border: none !important;
  border-radius: 8px;
}

.side-card:hover {
  cursor: pointer;
  background-color: gray;
}
</style>
