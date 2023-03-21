package com.jabaprac.webapp.pageconf;

import com.jabaprac.webapp.dbobjects.Clients;

public class ClientConfiguration extends PageConfiguration {
    private int type;

    String name, phone, email, address;

    public ClientConfiguration(int type, String name, String phone, String email, String address) {
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public ClientConfiguration(Clients client) {
        this(client.isIs_phy() ? ClientType.ONLY_PHYS.getValue() : ClientType.ONLY_UR.getValue(),
                client.getName(), client.getPhone(), client.getEmail(), client.getAddr());
    }

    public ClientConfiguration() {
        this.setDefault();
    }

    @Override
    void setDefault() {
        type = ClientType.ONLY_PHYS.getValue();
        name = "Пупкин Василий Алексеевич";
        phone = "9007003030";
        email = "pup.kin@mail.ru";
        address = "Lomonosov st., Moscow";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AddClientConfiguration{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public void updateClient(Clients client) {
        client.setIs_phy(type == ClientType.ONLY_PHYS.getValue());
        client.setName(name);
        client.setPhone(phone);
        client.setEmail(email);
        client.setAddr(address);
    }

    public String verify() {
        if(getName() == null)
            return "Пустое имя";

        if(getType() != ClientType.ONLY_UR.getValue() && getType() != ClientType.ONLY_PHYS.getValue())
            return "Недопустимый тип клиента";

        if(getPhone() == null)
            return "Пустой номер телефона";

        if(getAddress() == null)
            setAddress("");

        if(getEmail() == null)
            setEmail("");

        char[] phoneChars = getPhone().toCharArray();
        if(phoneChars.length != 11)
            return "В номере телефона должно быть ровно 11 цифр";

        if(phoneChars[0] != '7' && phoneChars[0] != '8')
            return "Номер телефона должен начинаться на 7 или 8";

        for(int i = 1; i < 11; i++) {
            if(!Character.isDigit(phoneChars[i]))
                return "Номер телефона должен содержать только цифры";
        }

        return null;
    }
}
