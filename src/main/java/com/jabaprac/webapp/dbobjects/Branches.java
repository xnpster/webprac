package com.jabaprac.webapp.dbobjects;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "Branches")
@Table(name = "\"Branches\"")
public class Branches {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "brn-id-gen")
    @SequenceGenerator(
            name = "brn-id-gen",
            sequenceName = "\"Branches_id_seq\"",
            allocationSize = 1
    )
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="address")
    private String addr;

    @Column(name="city")
    private String city;

    public Branches() { }

    public String toString() {
        return "[Отделение '" + name +
                "' with addr '" + addr +
                "' with city '" + city +
                "' id " + id +
                "]";
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
