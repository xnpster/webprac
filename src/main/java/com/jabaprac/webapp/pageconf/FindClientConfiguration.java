package com.jabaprac.webapp.pageconf;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Set;

public class FindClientConfiguration extends PageConfiguration{
    private int type;
    private boolean allowAccountTypesId;
    private ArrayList<Long> accountTypesId;

    public boolean allowDateRange;
    private Date startDate, endDate;

    public boolean allowNameSubstring;
    private String nameSubstring;

    public boolean allowClientNo;
    private Long clientNo;

    public FindClientConfiguration(int type,
                                   boolean allowAccountTypesId,
                                   ArrayList<Long> accountTypesId,
                                   boolean allowDateRange, Date startDate,
                                   Date endDate,
                                   boolean allowNameSubstring,
                                   String nameSubstring,
                                   boolean allowClientNo,
                                   Long clientNo) {
        this.type = type;
        this.allowAccountTypesId = allowAccountTypesId;
        this.accountTypesId = accountTypesId;
        this.allowDateRange = allowDateRange;
        this.startDate = startDate;
        this.endDate = endDate;
        this.allowNameSubstring = allowNameSubstring;
        this.nameSubstring = nameSubstring;
        this.allowClientNo = allowClientNo;
        this.clientNo = clientNo;
    }

    public FindClientConfiguration() {
        this.setDefault();
    }

    String verify() {
        return null;
    }

    @Override
    void setDefault() {
        type = ClientType.ALL.getValue();
        allowAccountTypesId = false;
        accountTypesId = new ArrayList<>();
        allowDateRange = false;
        startDate = null;
        endDate = null;
        allowNameSubstring = false;
        nameSubstring = null;
        allowClientNo = false;
        clientNo = null;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Long> getAccountTypesId() {
        return accountTypesId;
    }

    public void setAccountTypesId(ArrayList<Long> accountTypesId) {
        this.accountTypesId = accountTypesId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNameSubstring() {
        return nameSubstring;
    }

    public void setNameSubstring(String nameSubstring) {
        this.nameSubstring = nameSubstring;
    }

    public Long getClientNo() {
        return clientNo;
    }

    public void setClientNo(Long clientNo) {
        this.clientNo = clientNo;
    }

    @Override
    public String toString() {
        return "FindClientConfiguration{" +
                "type=" + type +
                ", allowAccountTypesId=" + allowAccountTypesId +
                ", accountTypesId=" + accountTypesId +
                ", allowDateRange=" + allowDateRange +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", allowNameSubstring=" + allowNameSubstring +
                ", nameSubstring='" + nameSubstring + '\'' +
                ", allowClientNo=" + allowClientNo +
                ", clientNo=" + clientNo +
                '}';
    }

    public boolean isAllowAccountTypesId() {
        return allowAccountTypesId;
    }

    public void setAllowAccountTypesId(boolean allowAccountTypesId) {
        this.allowAccountTypesId = allowAccountTypesId;
    }

    public boolean isAllowDateRange() {
        return allowDateRange;
    }

    public void setAllowDateRange(boolean allowDateRange) {
        this.allowDateRange = allowDateRange;
    }

    public boolean isAllowNameSubstring() {
        return allowNameSubstring;
    }

    public void setAllowNameSubstring(boolean allowNameSubstring) {
        this.allowNameSubstring = allowNameSubstring;
    }

    public boolean isAllowClientNo() {
        return allowClientNo;
    }

    public void setAllowClientNo(boolean allowClientNo) {
        this.allowClientNo = allowClientNo;
    }
}
