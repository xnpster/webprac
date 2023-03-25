package com.jabaprac.webapp.pageconf;

import com.jabaprac.webapp.banksystem.BankSystem;
import com.jabaprac.webapp.dbobjects.Account_types;
import com.jabaprac.webapp.dbobjects.Clients;

public class AccountConfiguration extends PageConfiguration {
    Long clientNo;

    Long depNo;

    Long accountType;

    Long startSum;

    public AccountConfiguration(Long clientNo, Long depNo, Long accountType, Long startSum) {
        this.clientNo = clientNo;
        this.depNo = depNo;
        this.accountType = accountType;
        this.startSum = startSum;
    }

    public AccountConfiguration() {
        this.setDefault();
    }

    @Override
    void setDefault() {
        setClientNo(null);
        setDepNo(null);
        setAccountType(null);
        setStartSum(null);
    }

    public String verify(BankSystem bank) {
        if(getClientNo() == null)
            return "Не указан id клиента";

        if(bank.clientById(getClientNo()) == null)
            return "Не существует клиента с указанным id";

        if(getDepNo() == null)
            return "Не указан id отделения";

        if(bank.branchById(getDepNo()) == null)
            return "Не существует отделения с указанным id";

        if(getAccountType() == null)
            return  "Не указан тип счёта";

        Account_types accountType = bank.accountTypeById(getAccountType());
        if(accountType == null)
            return "Не существует типа счёта с указанным id";

        if(getStartSum() == null)
            return "Не указана начальная сумма";

        if(getStartSum() < accountType.getCredit_limit())
            return "недопустимая сумма";

        return null;
    }

    public Long getClientNo() {
        return clientNo;
    }

    public void setClientNo(Long clientNo) {
        this.clientNo = clientNo;
    }

    public Long getDepNo() {
        return depNo;
    }

    public void setDepNo(Long depNo) {
        this.depNo = depNo;
    }

    public Long getAccountType() {
        return accountType;
    }

    public void setAccountType(Long accountType) {
        this.accountType = accountType;
    }

    public Long getStartSum() {
        return startSum;
    }

    public void setStartSum(Long startSum) {
        this.startSum = startSum;
    }

    @Override
    public String toString() {
        return "AccountConfiguration{" +
                "clientNo=" + clientNo +
                ", depNo=" + depNo +
                ", accountType=" + accountType +
                ", startSum=" + startSum +
                '}';
    }
}

