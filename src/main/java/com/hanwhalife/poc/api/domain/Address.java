package com.hanwhalife.poc.api.domain;

public class Address {

    private String s; //시/군
    private String g; //구
    private String d; //동

    public String getFullAddress() {
        return this.s + " " + this.g + " " + this.d;
    }

}
