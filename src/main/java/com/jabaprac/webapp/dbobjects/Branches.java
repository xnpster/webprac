package com.jabaprac.webapp.dbobjects;

import jakarta.persistence.*;

import java.util.Objects;

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

    public Branches(Long id, String name, String addr, String city) {
        this.id = id;
        this.name = name;
        this.addr = addr;
        this.city = city;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branches branches = (Branches) o;
        return Objects.equals(id, branches.id) && Objects.equals(name, branches.name) && Objects.equals(addr, branches.addr) && Objects.equals(city, branches.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, addr, city);
    }
}
