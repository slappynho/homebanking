let urlParams = new URLSearchParams(window.location.search);
let urlName = urlParams.get('id');

const app = Vue.
createApp({
    data() {
        return {
            clients: [],
            accounts: [],
            accountsId: [],
            transactions: [],
            
        }
    },

    created(){ 
        axios.get ('/api/clientes/current')
            .then((response) => {
                this.clients = response.data;
                this.accounts = this.clients.accounts;
                this.accountsId = this.accounts.find(account => account.id == urlName);
                this.transactions = this.accountsId.transactions;
              
            }  )    
            .catch(function (error) {
                console.log(error);
            }
        );

            
    },

    methods: {
        logOut() {
            axios.post('/api/logout')
            .then(response => window.location.assign ("./index.html"))
          },

    },
}).mount('#appp')