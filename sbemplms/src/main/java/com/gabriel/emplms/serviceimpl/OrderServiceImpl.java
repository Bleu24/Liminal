package com.gabriel.emplms.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.emplms.entity.OrderData;
import com.gabriel.emplms.repository.OrderDataRepository;
import com.gabriel.emplms.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDataRepository orderRepository;

    @Override
    @Transactional
    public OrderData addToOrder(String username, int productId, int quantity) {
        OrderData order = new OrderData();
        order.setUsername(username);
        order.setProductId(productId);
        order.setQuantity(quantity);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public List<OrderData> getAllOrders(String username) {
        return orderRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public OrderData updateOrderItem(String username, int productId, int quantity) {
        OrderData order = orderRepository.findByUsernameAndProductId(username, productId);
        if (order != null) {
            order.setQuantity(quantity);
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    @Transactional
    public void removeOrderItem(String username, int id) {
        OrderData order = orderRepository.findById(id).orElse(null);
        if (order != null && order.getUsername().equals(username)) {
            orderRepository.delete(order);
        }
    }

    @Override
    public void saveOrder(String username) {
        List<OrderData> orders = orderRepository.findByUsername(username);
        for (OrderData order : orders) {
            if (order.getQuantity() > 0) {
                orderRepository.save(order);
            }
        }
    }
}
