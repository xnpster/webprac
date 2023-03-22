package com.jabaprac.webapp.pageconf;

import java.sql.Date;
import java.util.ArrayList;
public class FindAccountConfiguration extends PageConfiguration {
    private boolean allowAccountTypesId;
    private ArrayList<Long> accountTypesId;

    public boolean allowDepositDateRange;
    private Date depositStartDate, depositEndDate;

    public boolean allowWithdrawDateRange;
    private Date withdrawStartDate, withdrawEndDate;

    public boolean allowClientNo;
    private Long clientNo;

    public boolean allowAccountNo;
    private Long accountNo;

    public FindAccountConfiguration(boolean allowAccountTypesId, ArrayList<Long> accountTypesId, boolean allowDepositeDateRange, Date depositStartDate, Date depositEndDate, boolean allowWithdrawDateRange, Date withdrawStartDate, Date withdrawEndDate, boolean allowClientNo, Long clientNo, boolean allowAccountNo, Long accountNo) {
        this.allowAccountTypesId = allowAccountTypesId;
        this.accountTypesId = accountTypesId;
        this.allowDepositDateRange = allowDepositeDateRange;
        this.depositStartDate = depositStartDate;
        this.depositEndDate = depositEndDate;
        this.allowWithdrawDateRange = allowWithdrawDateRange;
        this.withdrawStartDate = withdrawStartDate;
        this.withdrawEndDate = withdrawEndDate;
        this.allowClientNo = allowClientNo;
        this.clientNo = clientNo;
        this.allowAccountNo = allowAccountNo;
        this.accountNo = accountNo;
    }

    public FindAccountConfiguration() {
        setDefault();
    }

    @Override
    void setDefault() {
        setAllowAccountTypesId(false);
        setAccountTypesId(new ArrayList<>());

        setAllowDepositDateRange(false);
        setDepositStartDate(null);
        setDepositEndDate(null);

        setAllowWithdrawDateRange(false);
        setWithdrawStartDate(null);
        setWithdrawEndDate(null);

        setAllowClientNo(false);
        setClientNo(null);

        setAllowAccountNo(false);
        setAccountNo(null);
    }

    @Override
    public String toString() {
        return "FindAccountConfiguration{" +
                "allowAccountTypesId=" + allowAccountTypesId +
                ", accountTypesId=" + accountTypesId +
                ", allowDepositeDateRange=" + allowDepositDateRange +
                ", depositeStartDate=" + depositStartDate +
                ", depositeEndDate=" + depositEndDate +
                ", allowWithdrawDateRange=" + allowWithdrawDateRange +
                ", withdrawStartDate=" + withdrawStartDate +
                ", withdrawEndDate=" + withdrawEndDate +
                ", allowClientNo=" + allowClientNo +
                ", clientNo=" + clientNo +
                ", allowAccountNo=" + allowAccountNo +
                ", accountNo=" + accountNo +
                '}';
    }

    String verify() {
        return null;
    }

    public boolean isAllowAccountTypesId() {
        return allowAccountTypesId;
    }

    public void setAllowAccountTypesId(boolean allowAccountTypesId) {
        this.allowAccountTypesId = allowAccountTypesId;
    }

    public ArrayList<Long> getAccountTypesId() {
        return accountTypesId;
    }

    public void setAccountTypesId(ArrayList<Long> accountTypesId) {
        this.accountTypesId = accountTypesId;
    }

    public boolean isAllowDepositDateRange() {
        return allowDepositDateRange;
    }

    public void setAllowDepositDateRange(boolean allowDepositDateRange) {
        this.allowDepositDateRange = allowDepositDateRange;
    }

    public Date getDepositStartDate() {
        return depositStartDate;
    }

    public void setDepositStartDate(Date depositStartDate) {
        this.depositStartDate = depositStartDate;
    }

    public Date getDepositEndDate() {
        return depositEndDate;
    }

    public void setDepositEndDate(Date depositEndDate) {
        this.depositEndDate = depositEndDate;
    }

    public boolean isAllowWithdrawDateRange() {
        return allowWithdrawDateRange;
    }

    public void setAllowWithdrawDateRange(boolean allowWithdrawDateRange) {
        this.allowWithdrawDateRange = allowWithdrawDateRange;
    }

    public Date getWithdrawStartDate() {
        return withdrawStartDate;
    }

    public void setWithdrawStartDate(Date withdrawStartDate) {
        this.withdrawStartDate = withdrawStartDate;
    }

    public Date getWithdrawEndDate() {
        return withdrawEndDate;
    }

    public void setWithdrawEndDate(Date withdrawEndDate) {
        this.withdrawEndDate = withdrawEndDate;
    }

    public boolean isAllowClientNo() {
        return allowClientNo;
    }

    public void setAllowClientNo(boolean allowClientNo) {
        this.allowClientNo = allowClientNo;
    }

    public Long getClientNo() {
        return clientNo;
    }

    public void setClientNo(Long clientNo) {
        this.clientNo = clientNo;
    }

    public boolean isAllowAccountNo() {
        return allowAccountNo;
    }

    public void setAllowAccountNo(boolean allowAccountNo) {
        this.allowAccountNo = allowAccountNo;
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }
}
