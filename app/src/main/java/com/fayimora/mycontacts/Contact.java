package com.fayimora.mycontacts;

import java.io.Serializable;

/**
 * Created by fayimora on 17/12/2015.
 */
public class Contact implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
