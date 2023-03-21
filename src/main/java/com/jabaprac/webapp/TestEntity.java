package com.jabaprac.webapp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//@Entity
public class TestEntity {
//    @Id
//    @GeneratedValue
    private Long id;

    private String text;

    public TestEntity() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    TestEntity(String s) {
        text = s;
    }

    public String toString() {
        return "[String '" + text + "' with id " + id + "]";
    }
}
