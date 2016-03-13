package com.fayimora.mycontacts;

import java.util.ArrayList;

/**
 * Created by fayimora on 13/03/2016.
 */
public class ContactList extends ArrayList<Contact> {
    private static ContactList instance = null;

    // make sure no one can instantiate it
    private ContactList(){}

    public static ContactList getInstance(){
        if(instance == null){
            instance = new ContactList();
        }
        return instance;
    }
}
