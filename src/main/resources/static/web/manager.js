
const app = Vue.createApp({
        data() {
            return {
                firstName:"",
                lastName:"",
                email:"",
                clientes:[],
                id:[],
                
            }
        },
        created() {
            this.loadData();
        },
        methods: {
            loadData(){
                axios.get('/clientes')
                .then(response =>{
                    this.clientes = response.data._embedded.clientes;
                    this.clientesId = this.clientes.map(id => id._links.self.href)
                })
                
            },
            deletClient(clientes){
                axios.delete(clientes)
                .then(()=> this.loadData())
            },
            addClient(){
                if (this.firstName != "" && this.lastName!= "" && this.email != ""){
                    axios.post('/api/clientes', {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    email: this.email
                    }).then(()=>this.loadData())
                    .then(()=> {
                        this.firstName=""
                        this.lastName=""
                        this.email=""
                    })

                }
            },
            mostrarEnInput(cliente){
                this.id=cliente._links.self.href
                this.firstName=cliente.firstName
                this.lastName=cliente.lastName
                this.email=cliente.email
            },
            replace(cliente){
                axios.put(this.id,cliente)
                .then(()=> this.loadData())
            },
            editClient(){
                if (this.firstName != "" && this.lastName!= "" && this.email != ""){
                   let replacing =  {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    email: this.email
                    }
                    this.replace(replacing)
                }
            },
            
        },
        computed: {

        }
    }).mount('#appp')