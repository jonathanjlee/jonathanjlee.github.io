/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringOrderDao;
import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonat
 */
public class FlooringOrderDaoStubImpl implements FlooringOrderDao {
    
    public Order onlyOrder;
    
    public FlooringOrderDaoStubImpl() {
        onlyOrder = new Order(1);
        onlyOrder.setCustomerName("John Smith");
        onlyOrder.setState("CA");
        onlyOrder.setProductType("Tile");
        onlyOrder.setArea(new BigDecimal("150"));
    }
    
    public FlooringOrderDaoStubImpl(Order testOrder) {
        this.onlyOrder = testOrder;
    }

    @Override
    public List<Order> getAllOrders(LocalDate orderDate) 
            throws FlooringPersistenceException {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;
    }

    @Override
    public Order addOrder(LocalDate orderDate, Order newOrder) 
            throws FlooringPersistenceException {
        if (newOrder.getOrderNumber() == (onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) 
            throws FlooringPersistenceException {
        if (orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(LocalDate orderDate, Order revisedOrder) 
            throws FlooringPersistenceException {
        if (revisedOrder.getOrderNumber() == (onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(LocalDate orderDate, Order orderToRemove) 
            throws FlooringPersistenceException {
        if (orderToRemove.getOrderNumber() == (onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }
}
