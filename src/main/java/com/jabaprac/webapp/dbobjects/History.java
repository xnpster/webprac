package com.jabaprac.webapp.dbobjects;

import jakarta.persistence.*;
import org.hibernate.mapping.Set;

import java.sql.Date;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "History")
@Table(name = "\"History\"")
public class History {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "hst-id-gen")
    @SequenceGenerator(
            name = "hst-id-gen",
            sequenceName = "\"History_id_seq\"",
            allocationSize = 1
    )
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Accounts account;

    @Column(name="sum")
    private Long sum;

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

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public History() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return Objects.equals(id, history.id) && Objects.equals(account, history.account) && Objects.equals(sum, history.sum) && Objects.equals(date, history.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, sum, date);
    }
}
