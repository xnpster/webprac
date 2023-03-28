package com.jabaprac.webapp.pageconf;

import java.sql.Date;
import java.util.ArrayList;
import java.util.TreeSet;

public class FindDepartureConfiguration extends PageConfiguration{
    private boolean allowCities, allowNum, allowClient, allowAccount;

    private TreeSet<String> cities;

    private Long num, client, account;

    public FindDepartureConfiguration(boolean allowCities, boolean allowNum, boolean allowClient, boolean allowAccount, TreeSet<String> cities, Long num, Long client, Long account) {
        this.allowCities = allowCities;
        this.allowNum = allowNum;
        this.allowClient = allowClient;
        this.allowAccount = allowAccount;
        this.cities = cities;
        this.num = num;
        this.client = client;
        this.account = account;
    }

    @Override
    void setDefault() {
        setAllowNum(false);
        setAllowClient(false);
        setAllowAccount(false);
        setAllowCities(false);
        cities = new TreeSet<>();
    }

    public FindDepartureConfiguration() {
        setDefault();
    }

    public boolean isAllowCities() {
        return allowCities;
    }

    public void setAllowCities(boolean allowCities) {
        this.allowCities = allowCities;
    }

    public boolean isAllowNum() {
        return allowNum;
    }

    public void setAllowNum(boolean allowNum) {
        this.allowNum = allowNum;
    }

    public boolean isAllowClient() {
        return allowClient;
    }

    public void setAllowClient(boolean allowClient) {
        this.allowClient = allowClient;
    }

    public boolean isAllowAccount() {
        return allowAccount;
    }

    public void setAllowAccount(boolean allowAccount) {
        this.allowAccount = allowAccount;
    }

    public TreeSet<String> getCities() {
        return cities;
    }

    public void setCities(TreeSet<String> cities) {
        this.cities = cities;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "FindDepartureConfiguration{" +
                "allowCities=" + allowCities +
                ", allowNum=" + allowNum +
                ", allowClient=" + allowClient +
                ", allowAccount=" + allowAccount +
                ", cities=" + cities +
                ", num=" + num +
                ", client=" + client +
                ", account=" + account +
                '}';
    }
}
