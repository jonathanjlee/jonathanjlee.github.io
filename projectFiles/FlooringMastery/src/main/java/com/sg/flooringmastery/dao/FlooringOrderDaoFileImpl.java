/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author jonat
 */
public class FlooringOrderDaoFileImpl implements FlooringOrderDao {

    String fileDir = "orders/Orders_";
    String fileName = "";
    private String orderFile;
    public static final String DELIMITER = ",";

    public FlooringOrderDaoFileImpl() {
        orderFile = fileDir;
    }
    
    public FlooringOrderDaoFileImpl(String orderTextFile) {
        orderFile = orderTextFile;
    }
    
    private Map<Integer, Order> orders = new HashMap<>();

    private void readFromFile(LocalDate orderDate)
            throws FlooringPersistenceException {
        // Create file name to insert into FileReader
        fileName = orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"))
                + ".txt";
        Scanner scanner;

        // Reset map after each call
        orders = new HashMap<>();

        // read .txt file and move each entry into map
        try {
            scanner = new Scanner(new BufferedReader(
                    new FileReader(orderFile + fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not find that folder "
                    + "in our records.", e);
        }
        String currentLine;
        String[] currentTokens;
        if (scanner.hasNext()) {
            scanner.nextLine();
        }
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Order currentOrder = new Order(Integer.parseInt(currentTokens[0]));
            currentOrder.setCustomerName(currentTokens[1]);
            currentOrder.setState(currentTokens[2]);
            currentOrder.setTaxRate(new BigDecimal(currentTokens[3]));
            currentOrder.setProductType(currentTokens[4]);
            currentOrder.setArea(new BigDecimal(currentTokens[5]));
            currentOrder.setCostPerSquareFoot(new BigDecimal(currentTokens[6]));
            currentOrder.setLaborCostPerSquareFoot(
                    new BigDecimal(currentTokens[7]));
            currentOrder.setMaterialCost(new BigDecimal(currentTokens[8]));
            currentOrder.setLaborCost(new BigDecimal(currentTokens[9]));
            currentOrder.setTax(new BigDecimal(currentTokens[10]));
            currentOrder.setTotal(new BigDecimal(currentTokens[11]));

            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        scanner.close();
    }

    private void writeToFile(LocalDate orderDate)
            throws FlooringPersistenceException {
        // Create file name to insert into FileReader
        fileName = orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"))
                + ".txt";
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(orderFile + fileName));
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not save new order.",
                    e);
        }
        // Print header row
        out.println("OrderNumber" + DELIMITER + "CustomerName" + DELIMITER
                + "State" + DELIMITER + "TaxRate" + DELIMITER
                + "ProductType" + DELIMITER + "Area" + DELIMITER
                + "CostPerSquareFoot" + DELIMITER + "LaborCostPerSquareFoot"
                + DELIMITER + "MaterialCost" + DELIMITER + "LaborCost"
                + DELIMITER + "Tax" + DELIMITER + "Total");
        out.flush();
        List<Order> orderList = new ArrayList<>(orders.values());
        for (Order currentOrder : orderList) {
            out.println(currentOrder.getOrderNumber() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getState() + DELIMITER
                    + currentOrder.getTaxRate() + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getArea() + DELIMITER
                    + currentOrder.getCostPerSquareFoot() + DELIMITER
                    + currentOrder.getLaborCostPerSquareFoot() + DELIMITER
                    + currentOrder.getMaterialCost() + DELIMITER
                    + currentOrder.getLaborCost() + DELIMITER
                    + currentOrder.getTax() + DELIMITER
                    + currentOrder.getTotal());
            out.flush();
        }
        out.close();
    }

    @Override
    public List<Order> getAllOrders(LocalDate orderDate)
            throws FlooringPersistenceException {
        readFromFile(orderDate);
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order addOrder(LocalDate orderDate, Order order)
            throws FlooringPersistenceException {
        Order newOrder = orders.put(order.getOrderNumber(), order);
        writeToFile(orderDate);
        return newOrder;
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber)
            throws FlooringPersistenceException {
        readFromFile(orderDate);
        return orders.get(orderNumber);
    }

    @Override
    public Order editOrder(LocalDate orderDate, Order revisedOrder) 
            throws FlooringPersistenceException {
        Order newOrder = orders.replace(revisedOrder.getOrderNumber(), 
                revisedOrder);
        writeToFile(orderDate);
        return newOrder;
    }

    @Override
    public Order removeOrder(LocalDate orderDate, Order orderToRemove)
            throws FlooringPersistenceException {
        Order removedOrder = orders.remove(orderToRemove.getOrderNumber());
        writeToFile(orderDate);
        if (orders.isEmpty()) {
            File emptyFolder = new File("orders/Orders_"
                + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"))
                + ".txt");
            emptyFolder.delete();
        }
        return removedOrder;
    }
}
