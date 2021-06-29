/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author jonat
 */
public class FlooringView {

    UserIO io;

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public void printMessage(String message) {
        io.print(message);
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("**********ERROR**********");
        io.print(errorMsg);
    }

    public void displayMenu() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>                          *");
        io.print("* 1. Display Orders                             *");
        io.print("* 2. Add an Order                               *");
        io.print("* 3. Get an Order                               *");
        io.print("* 4. Edit an Order                              *");
        io.print("* 5. Remove an Order                            *");
        io.print("* 6. Quit                                       *");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public int getMenuSelection() {
        return io.readInt("Please select an option above.", 1, 6);
    }

    public LocalDate getOrderDate() {
        String referencedDate = io.readString("What date are you trying to "
                + "reference? (MM/dd/yyyy)");
        boolean validDate = io.validDate(referencedDate);
        while (validDate == false) {
            referencedDate = io.readString("Please enter the date in the "
                    + "proper format to continue. (MM/dd/yyyy)");
            validDate = io.validDate(referencedDate);
        }
        return LocalDate.parse(referencedDate,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public void displayAllOrdersBanner(LocalDate orderDate) {
        io.print("-----ORDERS FOR "
                + orderDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                + "-----");
    }

    public void displayOrderList(List<Order> orderList) {
        if (orderList.isEmpty()) {
            io.readString("No orders for this day. Hit enter to continue.");
        } else {
            for (Order currentOrder : orderList) {
                displayOrderDetails(currentOrder);
            }
        }
    }

    public void displayOrderDetails(Order order) {
        io.print("-------------------------------------------------------");
        io.print("ORDER #: " + order.getOrderNumber()
                + "   CUSTOMER NAME: " + order.getCustomerName()
                + "   STATE: " + order.getState());
        io.print("     TAX RATE: " + order.getTaxRate()
                + "%   PRODUCT TYPE: " + order.getProductType()
                + "   AREA: " + order.getArea() + " ft^2");
        io.print("     $/FT^2: $" + order.getCostPerSquareFoot()
                + "   LABOR $/FT^2: $"
                + order.getLaborCostPerSquareFoot());
        io.print("     MATERIAL COST: $" + order.getMaterialCost()
                + "   LABOR COST: $" + order.getLaborCost()
                + "   TAX: $" + order.getTax()
                + "   TOTAL: $" + order.getTotal());
        io.print("");
    }

    public void displayAddOrderBanner() {
        io.print("-----ADD A NEW ORDER-----");
    }

    public LocalDate getDateForNewOrder() {
        LocalDate futureOrder = null;
        String futureOrderString = "";
        do {
            futureOrderString = io.readString("Please enter a date after "
                    + "today's date (" + LocalDate.now().format(
                            DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    + ") for the order.");
            boolean validDate = io.validDate(futureOrderString);
            while (validDate == false) {
                futureOrderString = io.readString("Please enter the date in "
                        + "the proper format to continue. (MM/dd/yyyy)");
                validDate = io.validDate(futureOrderString);
            }
            futureOrder = LocalDate.parse(futureOrderString,
                    DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } while (futureOrder.isBefore(LocalDate.now().plusDays(1)));
        return LocalDate.parse(futureOrderString,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public String askCustomerName() {
        String customerName = "";
        while (customerName.isBlank()) {
            customerName = io.readString("What is the customer's name?");
        }
        return customerName;
    }

    public String askState() {
        String state = "";
        while (state.isBlank()) {
            state = io.readString("What state are we shipping this to?")
                    .toUpperCase();
        }
        return state;
    }

    public String askProductType() {
        String productType = "";
        while (productType.isBlank()) {
            productType = io.readString("What product would they like?");
            productType = io.capitalizeString(productType);
        }
        return productType;
    }

    public BigDecimal askArea() {
        String areaString = "";
        do {
            areaString = io.readString("What is the area of the room? "
                    + "(100 ft^2 minimum)");
            while (!areaString.matches("[0-9]*\\.?[0-9]{0,}")) {
                areaString = io.readString("Please enter a number greater than "
                        + "100 to continue.");
            }
        } while (areaString.isBlank());
        return new BigDecimal(areaString).setScale(2, RoundingMode.HALF_UP);
    }

    public String verifyOrder(Order newOrder) {
        io.print("Please review the order details below:");
        displayOrderDetails(newOrder);
        return io.readString("Type 'yes' to confirm, or 'no' to cancel and "
                + "return to the main menu.");
    }

    public void displaySuccessfulAdd() {
        io.readString("Your order was successfully added. Please hit enter "
                + "to continue.");
    }

    public void displayGetOrderBanner() {
        io.print("-----GET AN ORDER-----");
    }

    public int getOrderNumber() {
        return io.readInt("Which order number would you like to "
                + "view/edit/remove?");
    }

    public void displayEditOrderBanner() {
        io.print("-----EDIT AN ORDER-----");
    }

    public String getRevisedCustomerName(Order orderToEdit) {
        String newCustomerName = io.readString("Enter customer name ("
                + orderToEdit.getCustomerName() + "):");
        if (!newCustomerName.isBlank()) {
            orderToEdit.setCustomerName(newCustomerName);
        } else {
            newCustomerName = orderToEdit.getCustomerName();
        }
        return newCustomerName;
    }

    public String getRevisedState(Order orderToEdit) {
        String newState = io.readString("Enter state ("
                + orderToEdit.getState() + "):").toUpperCase();
        if (!newState.isBlank()) {
            orderToEdit.setState(newState);
        } else {
            newState = orderToEdit.getState();
        }
        return newState;
    }

    public String getRevisedProductType(Order orderToEdit) {
        String newProductType = io.readString("Enter product type ("
                + orderToEdit.getProductType() + "):");
        newProductType = io.capitalizeString(newProductType);
        if (!newProductType.isBlank()) {
            orderToEdit.setProductType(newProductType);
        } else {
            newProductType = orderToEdit.getProductType();
        }
        return newProductType;
    }

    public BigDecimal getRevisedArea(Order orderToEdit) {
        BigDecimal newArea = BigDecimal.ZERO;
        String newAreaString = io.readString("Enter area ("
                + orderToEdit.getArea() + "):");
        if (!newAreaString.isBlank()) {
            newArea = new BigDecimal(newAreaString);
            orderToEdit.setArea(newArea);
        } else {
            newArea = orderToEdit.getArea();
        }
        return newArea;
    }

    public void displaySuccessfulEdit() {
        io.readString("Your file has been successfully changed. Please hit "
                + "enter to continue.");
    }

    public void displayRemoveOrderBanner() {
        io.print("-----REMOVE AN ORDER-----");
    }

    public void displaySuccessfulRemove() {
        io.readString("Your order was successfully removed. Please hit enter "
                + "to continue.");
    }

    public void displayExitMessage() {
        io.print("Thank you for your business. Goodbye!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown command.");
    }
}
