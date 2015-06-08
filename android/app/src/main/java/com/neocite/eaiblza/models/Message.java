package com.neocite.eaiblza.models;

/**
 * Created by paulo-silva on 6/1/15.
 */
public class Message {

    private String value;

    private String owner;

    public Message(String value,String owner) {
        this.value = value;
        this.owner = owner;
    }

    public String getValue(){
        return value;
    }

    public String getOwner(){
        return owner;
    }

}
