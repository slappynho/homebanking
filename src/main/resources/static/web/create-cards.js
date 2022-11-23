const app = Vue.
    createApp({
        data() {
            return {
                clients: [],
                cards: [],
                debit: [],
                name: "",
                credit: [],
                selectedColor: "SILVER",
                options: [
                    { text: "SILVER", value: "SILVER" },
                    { text: "TITANIUM", value: "TITANIUM" },
                    { text: "GOLD", value: "GOLD" },
                ],
                selectedType: "DEBIT",
                options: [
                    { value: "DEBIT", text: "DEBIT" },
                    { text: "CREDIT", value: "CREDIT" },
                ],
                cardsTrue: [],
                
            }
        },

        created() {
            axios
                .get("/api/clientes/current")
                .then((response) => {
                    this.clients = response.data;
                    this.cards = this.clients.cards;
                    this.cardsTrue = response.data.cards.filter((card) => card.active == true);
                    this.debit = this.cards.filter((card) => card.cardType == "DEBIT");
                    this.credit = this.cards.filter((card) => card.cardType == "CREDIT");
                    this.name = this.clients.firstName + " " + this.clients.lastName;
                })
                .catch(function (error) {
                    console.log(error);
                });


        },

        methods: {
            newDate(creationDate) {
                return new Date(creationDate).toLocaleDateString('en-US', { month: '2-digit', year: '2-digit' });
            },
            logOut() {
                axios.post('/api/logout')
                    .then(() => window.location.assign("./index.html"))
            },
            createCards() {
                axios.post("/api/clientes/current/cards",`type=${this.selectedType}&color=${this.selectedColor}`)
                    .then(() => window.location.assign("./cards.html"))
                    .catch(Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'No puedes crear mas de 3 tarjetas por tipo!',
                      }))
            },




        },
    }).mount('#appp')