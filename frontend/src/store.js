import Vue from 'vue'
import Vuex from 'vuex'
import api from './components/backend-api'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        loginSuccess: false,
        loginError: false,
        userName: null,
        userPass: null,
        token: null,
    },
    mutations: {
        login_success(state, payload){
            console.log(payload);
            state.loginSuccess = true;
            state.userName = payload.userName;
            state.userPass = payload.userPass;
            state.token = payload.token;
        },
        login_error(state, payload){
            state.loginError = true;
            state.userName = payload.userName;
        }
    },
    actions: {
        login({commit}, {user, password}) {
            return new Promise((resolve, reject) => {
                console.log("Accessing backend with user: '" + user);
                api.authenticate(user,password)
                    .then(response => {
                        console.log("Response: '" + JSON.stringify(response.data) + "' with Statuscode " + response.status);
                        if(response.status === 200) {
                            console.log("Login successful");
                            // place the loginSuccess state into our vuex store
                            commit('login_success', {
                                userName: user,
                                userPass: password,
                                token: response.data.token
                            });
                        }
                        resolve(response)
                    })
                    .catch(error => {
                        console.log("Error: " + error);
                        // place the loginError state into our vuex store
                        commit('login_error', {
                            userName: user
                        });
                        reject("Invalid credentials!")
                    })
            })
        }
    },
    getters: {
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored: state => state.loginError,
        getUserName: state => state.userName,
        getUserPass: state => state.userPass,
        getToken: state => state.token
    }
})