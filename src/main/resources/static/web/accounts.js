const app = Vue.createApp({
    data() {
        return {
            clientes:[],
            firstName:"",
            lastName:"",
            balance:[],
            creationDate:"",
            accounts:"",
            
        }
    },
    created() {
        this.loadData();
    },
    methods: {
        sortArrays(accounts) {
            return accounts.sort((a, b) => a.id - b.id ).reverse()
        },
        loadData(){
            axios.get('/api/clientes/current')
            .then(response =>{
                this.clientes= response.data;
                this.accounts=this.clientes.accounts;
            })
            
        },
        logOut(){
            return axios.post('/api/logout')
            .then(() => window.location.assign("./index.html"))
        },
        createAcc(){
            axios.post("/api/clientes/current/accounts")
            .then(() => this.loadData())
            .catch((error) => Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'No puedes crear mas de 3 cuentas!',
              }))
        },
        
    },
    computed: {

    }
}).mount('#appp')