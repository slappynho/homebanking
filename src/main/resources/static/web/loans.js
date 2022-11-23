
const app = Vue.
createApp({
    data() {
        return {
            clientes: [],
            cards: [],
            selectedLoan : "",
            preLoans : [],
            hipoPayments : [],
            persoPayments : [],
            vehiPayments : [],
            selectedPayments : "",
            originAcc : "",
            idH : "",
            idP : "",
            idV : "",
            amount : "",
            accounts: [],
            totalPay : [],
            
        }
    },

    created(){ 
        axios.get ('/api/clientes/current')
            .then((response) => {
                this.clientes = response.data;
                this.accounts = this.clientes.accounts
            }  )    
            .catch(function (error) {
                console.log(error);
            }
        );
        axios.get ('/api/loans')
            .then((response) => {
                this.preLoans = response.data;
                this.hipoPayments = this.preLoans[0].payments
                this.idH = this.preLoans[0].id
                this.persoPayments = this.preLoans[1].payments
                this.idP = this.preLoans[1].id
                this.vehiPayments = this.preLoans[2].payments
                this.idV = this.preLoans[2].id
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
        appLoan() {
            axios.post('/api/loans', {"id":this.selectedLoan, "amount": this.amount,"payments":this.selectedPayments,"accountDestin":this.originAcc
            })
            .catch(function (error) {
                console.log(error.response.data);
                let errorData = error.response.data
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: `${errorData}`,
                })
            })
            .then(()=> setTimeout(()=> {window.location.assign("./accounts.html")},"1500"))
        },
        intCalcs(){
            if (this.selectedLoan == 11){
                this.totalPay= (this.amount / this.selectedPayments) * 1.2
            }
            if (this.selectedLoan == 12){
                this.totalPay= (this.amount / this.selectedPayments) * 1.15
            }
            if (this.selectedLoan == 13){
                this.totalPay= (this.amount / this.selectedPayments) * 1.1
            }
            return this.totalPay
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
                            this.appLoan()
                        )
                    }
                    else{
                        Swal.fire(
                            '¡Cancelado!',
                            'La transferencia fue cancelada.',
                            'fail',
                            window.location.reload()
                        )
                    }
                })
        },
    },
}).mount('#appp')