package com.mindhub.homebanking.DTO;


import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {


    private long id;

    private long loan_id;

    private double amount;

    private String name;

    private Integer payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loan_id = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.name = clientLoan.getLoan().getName();
        this.payments = clientLoan.getPayments();
    }

    public long getId() {
        return id;
    }

    public long getLoan_id() {
        return loan_id;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public Integer getPayments() {
        return payments;
    }

}
