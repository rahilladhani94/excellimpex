package com.excelimpex.Model;

public class Distributor {
 
    String name;
    String id;
    String city;

    public Distributor(String name, String id, String city) {
        this.name=name;
        this.id=id;
        this.city=city;

    }

    public String getCity() {
        return city;
    }


    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }
     

     
}