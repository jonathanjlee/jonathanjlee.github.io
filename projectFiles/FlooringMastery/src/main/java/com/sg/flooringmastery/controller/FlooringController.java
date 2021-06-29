/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.service.InvalidOrderException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.BelowMinimumException;
import com.sg.flooringmastery.service.FlooringServiceLayer;
import com.sg.flooringmastery.service.InvalidProductException;
import com.sg.flooringmastery.service.InvalidStateTaxException;
import com.sg.flooringmastery.ui.FlooringView;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author jonat
 */
public class FlooringController {

    FlooringView view;
    FlooringServiceLayer service;

    public FlooringController(FlooringServiceLayer service, FlooringView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            try {
                menuSelection = displayMenuAndGetSelection();
                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addAnOrder();
                        break;
                    case 3:
                        getAnOrder();
                        break;
                    case 4:
                        editAnOrder();
                        break;
                    case 5:
                        removeAnOrder();
                        break;
                    case 6:
                        exitProgram();
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            } catch (FlooringPersistenceException | InvalidStateTaxException
                    | InvalidProductException
                    | InvalidOrderException
                    | BelowMinimumException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
    }

    private int displayMenuAndGetSelection() {
        view.displayMenu();
        return view.getMenuSelection();
    }

    private void displayOrders()
            throws FlooringPersistenceException, FlooringPersistenceException {
        LocalDate orderDate = view.getOrderDate();
        view.displayAllOrdersBanner(orderDate);
        List<Order> orders = service.displayOrders(orderDate);
        view.displayOrderList(orders);
    }

    private void addAnOrder()
            throws FlooringPersistenceException, InvalidStateTaxException,
            InvalidProductException, BelowMinimumException,
            InvalidOrderException {
        view.displayAddOrderBanner();
        LocalDate orderDate = view.getDateForNewOrder();
        // Create file folder if needed
//        Figure out how to get the DAO to manipulate the File
        File dailyOrders = service.createFileFolder(orderDate);
        // Create orderNumber
        int orderNumber = service.createOrderNumber(orderDate);
        String customerName = view.askCustomerName();
        String state = "";
        do {
            state = askState();
        } while (state.isBlank());
        String productType = "";
        do {
            productType = askProduct();
        } while (productType.isBlank());
        BigDecimal area = BigDecimal.ZERO;
        do {
            area = askArea();
        } while (area.compareTo(BigDecimal.ZERO) == 0);
        // Create Order object
        Order baseInfo = new Order(orderNumber);
        baseInfo.setCustomerName(customerName);
        baseInfo.setState(state);
        baseInfo.setProductType(productType);
        baseInfo.setArea(area);
        // Calculate the rest of the order
        Order newOrder = service.calculateOrderDetails(baseInfo);
        // Verify order with user
        String userResponse = "";
        while (userResponse.isBlank()) {
            userResponse = view.verifyOrder(newOrder).toLowerCase();
            switch (userResponse) {
                case ("yes"):
                    service.addOrder(orderDate, newOrder);
                    view.displaySuccessfulAdd();
                    break;
                case ("no"):
                    List<Order> allOrders = service.displayOrders(orderDate);
                    if (allOrders.isEmpty()) {
                        dailyOrders.delete();
                    }
                    break;
                default:
                    userResponse = "";
                    continue;
            }
        }
    }

    private Order getAnOrder()
            throws FlooringPersistenceException, InvalidOrderException {
        view.displayGetOrderBanner();
        LocalDate orderDate = view.getOrderDate();
        int orderNumber = view.getOrderNumber();
        // Verify that there is an orderNumber in the appropriate folder
        service.validateOrderNumber(orderDate, orderNumber);
        Order retrievedOrder = service.getOrder(orderDate, orderNumber);
        view.displayOrderDetails(retrievedOrder);
        return retrievedOrder;
    }

    private void editAnOrder()
            throws FlooringPersistenceException, InvalidStateTaxException,
            InvalidProductException, InvalidOrderException,
            BelowMinimumException {
        view.displayEditOrderBanner();
        LocalDate orderDate = view.getOrderDate();
        int orderNumber = view.getOrderNumber();
        service.validateOrderNumber(orderDate, orderNumber);
        // Assign the original order information to a variable for comparison
        Order originalOrder = service.getOrder(orderDate, orderNumber);
        String originalState = originalOrder.getState();
        String originalProductType = originalOrder.getProductType();
        BigDecimal originalArea = originalOrder.getArea();
        // Get the new information for the order
        Order revisedOrder = new Order(orderNumber);
        revisedOrder.setCustomerName(
                view.getRevisedCustomerName(originalOrder));
        revisedOrder.setState(view.getRevisedState(originalOrder));
        service.validateStateTax(revisedOrder.getState());
        revisedOrder.setProductType(view.getRevisedProductType(originalOrder));
        service.validateProductType(revisedOrder.getProductType());
        revisedOrder.setArea(view.getRevisedArea(originalOrder));
        service.validateArea(revisedOrder.getArea());
        // If any information (except name) changed, recalculate the math
        if (!revisedOrder.getState().equals(originalState)
                || !revisedOrder.getProductType().equals(originalProductType)
                || !revisedOrder.getArea().equals(originalArea)) {
            revisedOrder = service.calculateOrderDetails(revisedOrder);
        }
        service.editOrder(orderDate, revisedOrder);
        view.displaySuccessfulEdit();
    }

    private void removeAnOrder()
            throws FlooringPersistenceException, InvalidOrderException {
        view.displayRemoveOrderBanner();
        LocalDate orderDate = view.getOrderDate();
        int orderNumber = view.getOrderNumber();
        service.validateOrderNumber(orderDate, orderNumber);
        Order orderToRemove = service.getOrder(orderDate, orderNumber);
        String userResponse = view.verifyOrder(orderToRemove);
        if (userResponse.equalsIgnoreCase("yes")) {
            service.removeOrder(orderDate, orderToRemove);
            view.displaySuccessfulRemove();
        }
    }

    private void exitProgram() {
        view.displayExitMessage();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private String askState()
            throws FlooringPersistenceException, InvalidStateTaxException {
        String state = view.askState();
        try {
            service.validateStateTax(state);
        } catch (InvalidStateTaxException e) {
            view.printMessage("We do not currently sell to " + state + " at "
                    + "this time.");
            state = "";
        }
        return state;
    }

    private String askProduct()
            throws FlooringPersistenceException, InvalidProductException {
        String product = view.askProductType();
        try {
            service.validateProductType(product);
        } catch (InvalidProductException e) {
            view.printMessage("We do not sell " + product + " at this time.");
            product = "";
        }
        return product;
    }
    
    private BigDecimal askArea() 
            throws FlooringPersistenceException, BelowMinimumException {
        BigDecimal area = view.askArea();
        try {
            service.validateArea(area);
        } catch (BelowMinimumException e) {
            view.printMessage("Our minimum order is 100 ft^2.");
            area = BigDecimal.ZERO;
        }
        return area;
    }
}
