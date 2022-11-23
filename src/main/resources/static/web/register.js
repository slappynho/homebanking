const app = Vue.createApp({
  data() {
    return {
      email: "",
      password: "",
      firstName: "",
      lastName: "",
    };
  },

  created() { },
  methods: {

    login() {
      axios.post("/api/login", `email=${this.email}&password=${this.password}`)
        .then(() => window.location.assign("./accounts.html"))
    },
    registerForm() {
      axios
        .post(
          "/api/clientes",
          `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`)
        .then(() => this.login())
    },
   

  },
}).mount("#appp");