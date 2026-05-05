package com.example.network.domains.models;

public class Product {
    public int id;
    public String name;
    public String description;
    public Integer gender;
    public String expenditure;
    public int price;
    public String img;
    public int idUser;
    public Product(String name, String description, Integer gender, String expenditure, int price) {
        this.name = name;
        this.description = description;
        this.gender = gender;
        this.expenditure = expenditure;
        this.price = price;
    }
}
