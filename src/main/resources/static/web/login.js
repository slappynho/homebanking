const app = Vue.createApp({
    data() {
      return {
        email: "",
        password: "",
      };
    },
  
    created() {},
    methods: {
      login() {
        axios.post("/api/login", `email=${this.email}&password=${this.password}`)
          .then(response => window.location.assign ("./accounts.html"))
      },
      

    },
  }).mount("#appp");