package com.jabaprac.webapp;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Resource;


public class App
{


    @Resource(name = "testBean")
    TestBean b;

    public static void main( String[] args ) {
        return;
    }
    public App() {
        System.out.println("Hello, world!");
        if(b == null)
            System.out.println("b is null");
        else
            System.out.println("b is not null");
    }

}