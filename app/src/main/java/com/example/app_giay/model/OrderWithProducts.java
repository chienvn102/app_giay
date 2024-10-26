package com.example.app_giay.model;

import java.util.ArrayList;

public class OrderWithProducts {
    private String dhMa;
    private String dhNoiGiao;
    private ArrayList<donhang> products;

    public OrderWithProducts(String dhMa, String dhNoiGiao) {
        this.dhMa = dhMa;
        this.dhNoiGiao = dhNoiGiao;
        this.products = new ArrayList<>();
    }

    public String getDhMa() {
        return dhMa;
    }

    public String getDhNoiGiao() {
        return dhNoiGiao;
    }

    public ArrayList<donhang> getProducts() {
        return products;
    }

    public void addProduct(donhang product) {
        products.add(product);
    }
}

