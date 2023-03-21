package com.jabaprac.webapp.pageconf;

public enum CheckboxValue {
    ON("on"), OFF("off");

    private String val;
    CheckboxValue(String s) {
        val = s;
    }

    public String getVal() {
        return val;
    }

    public static CheckboxValue fromString(String s) {
        if(s.equals(ON.val))
            return ON;
        else
            return OFF;
    }

    public boolean toBool() {
        return this == ON;
    }

    public static boolean checboxToBool(String s) {
        return fromString(s).toBool();
    }
}
