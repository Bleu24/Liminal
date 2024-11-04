package com.reamillo.emplms.model;
import java.math.BigDecimal;

import lombok.Data;

@Data

public class Product {
    private int id;
    private String username;
    private String name;
    private String description;
    private BigDecimal pricePerItem;
    private int stock;
}
