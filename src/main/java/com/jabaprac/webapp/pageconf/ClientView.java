package com.jabaprac.webapp.pageconf;

import com.jabaprac.webapp.dbobjects.Clients;

public class ClientView {
    Clients client;
    int totalAccounts, openAccounts;

    public ClientView(Clients client, int totalAccounts, int openAccounts) {
        this.client = client;
        this.totalAccounts = totalAccounts;
        this.openAccounts = openAccounts;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public int getTotalAccounts() {
        return totalAccounts;
    }

    public void setTotalAccounts(int totalAccounts) {
        this.totalAccounts = totalAccounts;
    }

    public int getOpenAccounts() {
        return openAccounts;
    }

    public void setOpenAccounts(int openAccounts) {
        this.openAccounts = openAccounts;
    }
}
