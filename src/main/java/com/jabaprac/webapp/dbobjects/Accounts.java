package com.jabaprac.webapp.dbobjects;

import jakarta.persistence.*;

import java.sql.Date;

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
}
