package com.jabaprac.webapp.dbobjects;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "Clients")
@Table(name = "\"Clients\"")
public class Clients {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "cli-id-gen")
    @SequenceGenerator(
            name = "cli-id-gen",
            sequenceName = "\"Clients_id_seq\"",
            allocationSize = 1
    )
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="address")
    private String addr;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="is_phy")
    private boolean is_phy;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIs_phy() {
        return is_phy;
    }

    public void setIs_phy(boolean is_phy) {
        this.is_phy = is_phy;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", is_phy=" + is_phy +
                '}';
    }

    public Clients() { }

    public Clients(String name, String addr, String email, String phone, boolean is_phy) {
        this.name = name;
        this.addr = addr;
        this.email = email;
        this.phone = phone;
        this.is_phy = is_phy;
    }
}
