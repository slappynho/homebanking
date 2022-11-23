package com.mindhub.homebanking.DTO;

public class LoanApplicationDTO {

    private long id;

    private double amount;

    private Integer payments;

    private String accountDestin;

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getAccountDestin() {
        return accountDestin;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
