import axios from 'axios'
import store from "../store";

const http = axios.create({
    baseURL: `/api`,
    timeout: 1000
});

http.interceptors.request.use(function (config) {
        if (store.getters.isLoggedIn)
            config.headers['Authorization'] = "Bearer " + store.getters.getToken;
        delete config.xsrfCookieName;
        delete config.xsrfHeaderName;
        return config;
    },
    function (error) {
        if (error.response.status === 401) {
            // store.dispatch('logout', {tokenExpired: true}).then(() => {
            //     if (router.history.current.name !== 'Login') {
            //         router.push({
            //             name: 'Login',
            //             params: {
            //                 errors: [
            //                     {
            //                         code: 'token_expired',
            //                         message: i18n.t('login.messages.token_expired')
            //                     }
            //                 ]
            //             }
            //         });
            //     }
            // });
        } else if ([500, 404].indexOf(error.response.status) > -1) {
            // router.push({name: 'Error', params: {code: error.response.status}});
        }
        return Promise.reject(error);
    });

export default {
    hello() {
        return http.get(`/hello`);
    },
    getUser(userId) {
        return http.get(`/user/` + userId);
    },
    createUser(firstName, lastName) {
        return http.post(`/user/` + firstName + '/' + lastName);
    },
    authenticate(username, password) {
        return http.post("/authenticate", {username: username, password: password})
    },
    getSecured(user, password) {
        console.log(store.getters.getToken)
        return http.get(`/secured/`);
    }
}


