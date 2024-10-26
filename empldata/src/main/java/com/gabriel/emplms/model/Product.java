package com.gabriel.emplms.model;
import com.gabriel.emplms.entity.CartData;

import lombok.Data;

@Data

public class Product {
    private int id;
    private String name;
    private String description;
    private double totalPrice;
    private int stock;
    private int quantity;  
    private CartData cartData;  
}
