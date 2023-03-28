package com.jabaprac.webapp.pageconf;

public class BranchConfiguration extends PageConfiguration{
    String name, city, address;

    @Override
    void setDefault() {
        setName(null);
        setCity(null);
        setAddress(null);
    }

    public BranchConfiguration(String name, String city, String address) {
        this.name = name;
        this.city = city;
        this.address = address;
    }

    public BranchConfiguration() {
        setDefault();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String verify() {
        if(getName() == null || getName().equals(""))
            return "Название не должно быть пустым";

        if(getCity() == null || getCity().equals(""))
            return "Город не должен быть пустой строкой";

        if(getAddress() == null || getAddress().equals(""))
            return "Адрес не должен быть пустым";

//        return "Some error";
        return null;
    }
}
