package com.jabaprac.webapp.pageconf;

public enum ClientType {
    ALL(0), ONLY_PHYS(1), ONLY_UR(2);

    private final int x;
    ClientType(int x) {
        this.x = x;
    }

    public int getValue() {
        return x;
    }

    public static ClientType get(int x) {
        if(x == ALL.x)
            return ALL;
        else if(x == ONLY_PHYS.x)
            return ONLY_PHYS;
        else
            return ONLY_UR;
    }
}