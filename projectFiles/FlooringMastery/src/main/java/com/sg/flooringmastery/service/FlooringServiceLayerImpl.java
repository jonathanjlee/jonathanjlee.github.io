/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringOrderDao;
import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dao.FlooringProductDao;
import com.sg.flooringmastery.dao.FlooringTaxDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

/**
 *
 * @author jonat
 */
public class FlooringServiceLayerImpl implements FlooringServiceLayer {

    FlooringOrderDao orderDao;
    FlooringProductDao productDao;
    FlooringTaxDao taxDao;

    public FlooringServiceLayerImpl(FlooringOrderDao orderDao,
            FlooringProductDao productDao, FlooringTaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public List<Order> displayOrders(LocalDate orderDate)
            throws FlooringPersistenceException {
        return orderDao.getAllOrders(orderDate);
    }

    @Override
    public File createFileFolder(LocalDate orderDate)
            throws FlooringPersistenceException {
        // Create file name
        File dailyOrders = new File("orders/Orders_"
                + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"))
                + ".txt");
        // Create a new file folder if it does not exist
        try {
            dailyOrders.createNewFile();
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not create new file "
                    + "folder.", e);
        }
        return dailyOrders;
    }

    @Override
    public int createOrderNumber(LocalDate orderDate)
            throws FlooringPersistenceException {
        int orderNumber = 0;
        List<Order> orders = orderDao.getAllOrders(orderDate);
        OptionalInt maxOrder = orders.stream()
                .mapToInt((o) -> o.getOrderNumber())
                .max();
        if (maxOrder.isEmpty()) {
            orderNumber = 1;
        } else {
            orderNumber = maxOrder.getAsInt() + 1;
        }
        return orderNumber;
    }

    @Override
    public Order calculateOrderDetails(Order order)
            throws FlooringPersistenceException, InvalidStateTaxException,
            InvalidProductException {
        validateStateTax(order.getState());
        validateProductType(order.getProductType());
        BigDecimal taxRate = taxDao.getTaxRate(order.getState());
        BigDecimal costPerSquareFoot
                = productDao.getCostPerSquareFoot(order.getProductType());
        BigDecimal laborCostPerSquareFoot
                = productDao.getLaborCostPerSquareFoot(order.getProductType());
        BigDecimal materialCost = order.calcMaterialCost(order.getArea(),
                costPerSquareFoot);
        BigDecimal laborCost = order.calcLaborCost(order.getArea(),
                laborCostPerSquareFoot);
        BigDecimal tax = order.calcTax(materialCost, laborCost, taxRate);
        BigDecimal total = order.calcTotal(materialCost, laborCost, tax);
        order.setTaxRate(taxRate);
        order.setCostPerSquareFoot(costPerSquareFoot);
        order.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(tax);
        order.setTotal(total);
        return order;
    }

    @Override
    public void addOrder(LocalDate orderDate, Order newOrder)
            throws FlooringPersistenceException, BelowMinimumException, 
            InvalidOrderException, InvalidProductException,
            InvalidStateTaxException {
        // Add the order to the DAO
        orderDao.addOrder(orderDate, newOrder);
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber)
            throws FlooringPersistenceException, InvalidOrderException {
        return orderDao.getOrder(orderDate, orderNumber);
    }

    @Override
    public Order editOrder(LocalDate orderDate, Order revisedOrder)
            throws FlooringPersistenceException {
        return orderDao.editOrder(orderDate, revisedOrder);
    }

    @Override
    public Order removeOrder(LocalDate orderDate, Order orderToRemove)
            throws FlooringPersistenceException {
        return orderDao.removeOrder(orderDate, orderToRemove);
    }

    @Override
    public void validateOrderNumber(LocalDate orderDate, int orderNumber) 
            throws FlooringPersistenceException, InvalidOrderException {
        Order requestedOrder = null;
        requestedOrder = orderDao.getOrder(orderDate, orderNumber);
        if (requestedOrder == null) {
            throw new InvalidOrderException("That order number does not "
                    + "exist.");
        }
    }
    
    @Override
    public void validateStateTax(String state)
            throws InvalidStateTaxException, FlooringPersistenceException {
        Map<String, Tax> taxInfo = taxDao.getAllTaxInfo();
        if (!taxInfo.containsKey(state)) {
            throw new InvalidStateTaxException("We cannot sell in "
                    + state + " at this time.");
        }
    }
    
    @Override
    public void validateProductType(String productType) 
            throws InvalidProductException, FlooringPersistenceException {
        Map<String, Product> productInfo = productDao.getAllProductInfo();
        if (!productInfo.containsKey(productType)) {
            throw new InvalidProductException("We do not sell " + productType 
                    + " at this time.");
        }
    }
    
    @Override
    public void validateArea(BigDecimal area) 
            throws BelowMinimumException, FlooringPersistenceException {
        if (area.compareTo(new BigDecimal("100")) < 0) {
            throw new BelowMinimumException("Our mimimum order size is for "
                    + "a 100 ft^2 area.");
        }
    }
}
