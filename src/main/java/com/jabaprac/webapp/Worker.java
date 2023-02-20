package com.jabaprac.webapp;

import org.springframework.context.annotation.Bean;

public class Worker {
    private static int cnt = 0;
    private int val;
    Worker(int a) {
        val = a;
        cnt++;
    }

    void saySomething() {
        System.out.println("Hello " + cnt + " : " + val);
    }
}
