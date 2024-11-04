package com.reamillo.emplms.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "cart_data")
@NoArgsConstructor
public class CartData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO can be used, but IDENTITY is often more efficient for MySQL
    private int id;

    private String name;

    // Represents a list of products associated with this cart
    @Column(name = "product_id")
    private int productId;
    private String username;
    private String description;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal pricePerItem;
    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice;
    private int quantity;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00") //Philippines
    private Date productAdded;
}
