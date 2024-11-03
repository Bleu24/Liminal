package com.gabriel.emplms.service;

import java.util.List;

import com.gabriel.emplms.entity.OrderData;

public interface OrderService {
    OrderData addToOrder(String username, int productId, int quantity);

    List<OrderData> getAllOrders(String username);

    OrderData updateOrderItem(String username, int productId, int quantity);

    void removeOrderItem(String username, int id);

    void saveOrder(String username);

}