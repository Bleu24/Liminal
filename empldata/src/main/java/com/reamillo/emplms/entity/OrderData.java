package com.reamillo.emplms.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "order_data")
public class OrderData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String username;
    @Column(name = "order_id")
    private int productId;
    private int quantity;
    private BigDecimal pricePerItem;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
}
