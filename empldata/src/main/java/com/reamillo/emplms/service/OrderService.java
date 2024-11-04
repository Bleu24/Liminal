package com.reamillo.emplms.service;

import java.util.List;

import com.reamillo.emplms.entity.OrderData;

public interface OrderService {
    OrderData addToOrder(String username, int productId, int quantity);

    List<OrderData> getAllOrders(String username);


    void removeOrderItem(String username, int id);

    void saveOrder(String username);

}
