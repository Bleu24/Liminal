package com.gabriel.emplms.model;
import java.util.List;

import com.gabriel.emplms.entity.ProductData;

import lombok.Data;

@Data

public class Cart {
    private int id;
    private String name;
    private List<ProductData> products;
    private String description;
    private double totalPrice;
    private int quantity;  
}