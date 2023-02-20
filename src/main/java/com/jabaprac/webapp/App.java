package com.jabaprac.webapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App
{
    public static void main( String[] args ) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                MainConfig.class
        );

        Worker wrk = context.getBean("workerBean", Worker.class);
        Worker wrk2 = context.getBean("workerBean", Worker.class);

        wrk.saySomething();
        wrk2.saySomething();
        System.out.println("Hello World!");

        context.close();
    }
}