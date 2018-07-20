package com.excelimpex.Model;

public class Products {

    String name;
    String id;
    String image;

    public Products(String name, String id, String image) {
        this.name=name;
        this.id=id;
        this.image=image;

    }

    public String getImage() {
        return image;
    }


    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }
     

     
}