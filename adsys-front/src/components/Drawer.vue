<template>
    <v-navigation-drawer
            dark
            class="primary elevation-5"
            color="teal lighten-3"
            :mini-variant="mini"
            permanent
            app
    >
        <v-list>
            <v-tooltip :disabled="!mini" right>
                <template v-slot:activator="{ on }">
                    <v-list-item link v-on="on" @click.stop="$emit('miniVariant')">
                        <v-list-item-icon>
                            <v-icon>mdi-menu</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                            <v-list-item-title>Collapse</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </template>
                <span>Expand</span>
            </v-tooltip>
            <v-list-item-group>
                <v-tooltip
                        v-for="(item, i) in items"
                        :key="i"
                        right
                        :disabled="!mini"
                >
                    <template v-slot:activator="{ on }">
                        <v-list-item :to="item.path" link v-on="on">
                            <v-list-item-icon>
                                <v-icon>{{ item.icon }}</v-icon>
                            </v-list-item-icon>

                            <v-list-item-content>
                                <v-list-item-title>{{ item.label }}</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </template>
                    <span>{{ item.label }}</span>
                </v-tooltip>
            </v-list-item-group>

        </v-list>
    </v-navigation-drawer>
</template>

<script>
    import {SuperAdmin} from "../utils/drawerItems";

    export default {
        name: "Drawer",
        data: () => ({
            items: Array,
            patient: null
        }),
        props: {
            mini: {
                type: Boolean,
                default: true
            },
        },
        computed: {
            //...mapState('auth', ['role', 'user', 'clinic']),

        },
        async mounted() {
          this.items = (new SuperAdmin()).items;
        }
    }
</script>

<style scoped>
</style>
