package com.example.myapplicationsemester2;

public class Product {

    private String name, info;
    private Double price;
    private int qty;

    public Product(String name, Double price,String info, int qty)
    {
        this.info = info;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public Product()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
