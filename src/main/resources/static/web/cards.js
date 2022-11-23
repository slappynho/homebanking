
const app = Vue.
    createApp({
        data() {
            return {
                clientes: [],
                cards: [],
                cardNumber: "",
                cardsTrue: [],
            }
        },

        created() {
            this.loadData();
            this.dateNow = new Date(Date.now()).toLocaleDateString();
            this.newDate1();


        },
        methods: {
            newDate(creationDate) {
                return new Date(creationDate).toLocaleDateString("en-US", {
                  month: "2-digit",
                  year: "2-digit",
                });
              },
              newDate1(value) {
                return new Date(value).toLocaleDateString();
              },
            logOut() {
                axios.post('/api/logout')
                    .then(response => window.location.assign("./index.html"))
            },
            deleteCard() {
                axios.patch('/api/clientes/current/cards/state', `number=${this.cardNumber}`)
                .then(() => this.loadData())
            },
            loadData(){
                axios.get('/api/clientes/current')
                    .then((response) => {
                        this.clientes = response.data;
                        this.cards = this.clientes.cards;
                        this.cardsTrue = response.data.cards.filter((card) => card.active == true);
                        this.debit = this.cardsTrue.filter((card) => card.cardType == "DEBIT");
                        this.credit = this.cardsTrue.filter((card) => card.cardType == "CREDIT");
                    })
                    .catch(function (error) {
                        console.log(error);
                    }
                    );
                },
        }
    }).mount('#appp')