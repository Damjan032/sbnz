import {configPath, requestsPath, usersPath} from "./paths";

const {homePath} = require("./paths");


const patientsItem = {
    icon: 'mdi-file-document-edit-outline',
    label: 'Certificates Requests',
    path: requestsPath,
};


const homeItem = {
    icon: 'mdi-file-document-outline',
    label: 'Certificates',
    path: homePath,
};

const userItem = {
    icon: 'mdi-account-multiple',
    label: 'Users',
    path: usersPath
};
const configItem = {
    icon: 'mdi-cogs',
    label: 'Configuration',
    path: configPath
}

class SuperAdmin {
    static name = 'SuperAdmin';
    static code = 'superAdmin';
    constructor() {
        this.items = [
            homeItem,
            patientsItem,
            userItem,
            configItem
        ]
    }
}

export {SuperAdmin};
