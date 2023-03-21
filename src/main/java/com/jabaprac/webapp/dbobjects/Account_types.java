package com.jabaprac.webapp.dbobjects;

import jakarta.persistence.*;

@Entity(name = "Account_types")
@Table(name = "\"Account_types\"")
public class Account_types {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="credit_limit")
    private int credit_limit;

    @Column(name="allow_refill")
    private boolean allow_refill;

    @Column(name="allow_write-off")
    private boolean allow_write_off;

    @Override
    public String toString() {
        return "Account_types{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credit_limit=" + credit_limit +
                ", allow_refill=" + allow_refill +
                ", allow_write_off=" + allow_write_off +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(int credit_limit) {
        this.credit_limit = credit_limit;
    }

    public boolean isAllow_refill() {
        return allow_refill;
    }

    public void setAllow_refill(boolean allow_refill) {
        this.allow_refill = allow_refill;
    }

    public boolean isAllow_write_off() {
        return allow_write_off;
    }

    public void setAllow_write_off(boolean allow_write_off) {
        this.allow_write_off = allow_write_off;
    }

    public Account_types() { }
}
