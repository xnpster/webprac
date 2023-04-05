package com.jabaprac.webapp.dbobjects;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "Accounts")
@Table(name = "\"Accounts\"")
public class Accounts {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "acc-id-gen")
    @SequenceGenerator(
            name = "acc-id-gen",
            sequenceName = "\"Accounts_id_seq\"",
            allocationSize = 1
    )
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="branch_id")
    private Branches branch;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Clients client;

    @ManyToOne
    @JoinColumn(name="type_id")
    private Account_types type;

    @Column(name="balance")
    private Long balance;

    @Column(name="open_date")
    private Date open_date;

    @Column(name="close_date")
    private Date close_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Branches getBranch() {
        return branch;
    }

    public void setBranch(Branches branch) {
        this.branch = branch;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public Account_types getType() {
        return type;
    }

    public void setType(Account_types type) {
        this.type = type;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Date getOpen_date() {
        return open_date;
    }

    public void setOpen_date(Date open_date) {
        this.open_date = open_date;
    }

    public Date getClose_date() {
        return close_date;
    }

    public void setClose_date(Date close_date) {
        this.close_date = close_date;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "id=" + id +
                ", branch=" + branch +
                ", client=" + client +
                ", type=" + type +
                ", balance=" + balance +
                ", open_date=" + open_date +
                ", close_date=" + close_date +
                '}';
    }

    public Accounts() { }

    public Accounts(Long id, Branches branch, Clients client, Account_types type, Long balance, Date open_date, Date close_date) {
        this.id = id;
        this.branch = branch;
        this.client = client;
        this.type = type;
        this.balance = balance;
        this.open_date = open_date;
        this.close_date = close_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accounts accounts = (Accounts) o;
        return Objects.equals(id, accounts.id) && Objects.equals(branch, accounts.branch) && Objects.equals(client, accounts.client) && Objects.equals(type, accounts.type) && Objects.equals(balance, accounts.balance) && Objects.equals(open_date, accounts.open_date) && Objects.equals(close_date, accounts.close_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branch, client, type, balance, open_date, close_date);
    }
}
