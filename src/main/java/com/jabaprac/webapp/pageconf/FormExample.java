package com.jabaprac.webapp.pageconf;

import java.util.ArrayList;

public class FormExample {
    private String s;
    private ArrayList<Integer> checkboxes;

    private boolean defaultCheckbox;

    public ArrayList<Integer> getCheckboxes() {
        return checkboxes;
    }

    public void setCheckboxes(ArrayList<Integer> checkboxes) {
        this.checkboxes = checkboxes;
    }

    public String getStringCheckboxes() {
        StringBuilder res = new StringBuilder();
        if(checkboxes == null || checkboxes.size() == 0)
            return "[null or zero checks]";

        for (Integer v : checkboxes) {
            res.append(v);
            res.append(';');
        }

        res.append("default checkbox is ");
        res.append(defaultCheckbox);

        return res.toString();
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public FormExample() {
        s = "";
    }

    public boolean isDefaultCheckbox() {
        return defaultCheckbox;
    }

    public void setDefaultCheckbox(boolean defaultCheckbox) {
        this.defaultCheckbox = defaultCheckbox;
    }
}
