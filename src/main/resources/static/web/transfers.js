const app = Vue.
    createApp({
        data() {
            return {
                clients: [],
                accounts: [],
                name: "",
                originAcc: "",
                amount: 0,
                description: "",
                destinataryAcc: "",
                selectedAcc: "",
                options: [
                    { text: "Cuenta propia", value: "ownAcc" },
                    { text: "Cuenta de un tercero", value: "thirdAcc" },
                ],
            }
        },

        created() {
            axios
                .get("/api/clientes/current")
                .then((response) => {
                    this.clients = response.data;
                    this.accounts = this.clients.accounts;
                    this.name = this.clients.firstName + " " + this.clients.lastName;
                })
                .catch(function (error) {
                    console.log(error);
                });


        },

        methods: {
            logOut() {
                axios.post('/api/logout')
                    .then(() => window.location.assign("./index.html"))
            },
            makeTransfer() {
                axios.post("/api/transactions", `amount=${this.amount}&description=${this.description}&accountO=${this.originAcc}&accountD=${this.destinataryAcc}`)
                    .then(() => window.location.assign("./accounts.html"))
                    .catch(function (error) {
                        console.log(error.response.data);
                        let errorData = error.response.data
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: `${errorData}`,
                        })
                    })
                   
            },
            swaling(){
                Swal.fire({
                    title: '¿Estas seguro?',
                    text: "Una vez efectuada la transferencia no se puede revertir",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Aceptar'
                })
                    .then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire(
                                '¡Guardado!',
                                'La transferencia fue exitosa.',
                                'success',
                                this.makeTransfer()
                            )
                        }
                    })
            }




        },
    }).mount('#appp')