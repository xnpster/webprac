package com.jabaprac.webapp.dbobjects;

import jakarta.persistence.*;
import org.hibernate.mapping.Set;

import java.sql.Date;

@Entity(name = "History")
@Table(name = "\"History\"")
public class History {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Accounts account;

    @Column(name="sum")
    private int sum;

    @Column(name="date")
    private Date date;

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", account=" + account +
                ", sum=" + sum +
                ", date=" + date +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public History() { }
}
