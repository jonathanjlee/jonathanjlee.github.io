/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 *
 * @author jonat
 */
public class Order {

    int orderNumber;
    String customerName;
    String state;
    BigDecimal taxRate;
    String productType;
    BigDecimal area;
    BigDecimal costPerSquareFoot;
    BigDecimal laborCostPerSquareFoot;
    BigDecimal materialCost;
    BigDecimal laborCost;
    BigDecimal tax;
    BigDecimal total;

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }

//    Getters & Setters for orderNumber
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    
//    Getters & Setters for customerName
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

//    Getters & Setters for state
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    Getters & Setters for taxRate
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

//    Getters & Setters for productType
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

//    Getters & Setters for area
    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

//    Getters & Setters for costPerSquareFoot
    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

//    Getters & Setters for laborCostPerSquareFoot
    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

//    Getters & Setters for materialCost
    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

//    Getters & Setters for laborCost
    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

//    Getters & Setters for tax
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

//    Getters & Setters for total
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

//     Calculations
    public BigDecimal calcMaterialCost(BigDecimal area,
            BigDecimal costPerSquareFoot) {
        return area.multiply(costPerSquareFoot).setScale(2, 
                RoundingMode.HALF_UP);
    }

    public BigDecimal calcLaborCost(BigDecimal area,
            BigDecimal laborCostPerSquareFoot) {
        return area.multiply(laborCostPerSquareFoot).setScale(2, 
                RoundingMode.HALF_UP);
    }

    public BigDecimal calcTax(BigDecimal materialCost, BigDecimal laborCost,
            BigDecimal taxRate) {
        BigDecimal totalCost = materialCost.add(laborCost);
        BigDecimal taxRateDecimal = taxRate.divide(
                new BigDecimal("100"), 5, RoundingMode.HALF_UP);
        return totalCost.multiply(taxRateDecimal).setScale(2, 
                RoundingMode.HALF_UP);
        
    }

    public BigDecimal calcTotal(BigDecimal materialCost, BigDecimal laborCost,
            BigDecimal tax) {
        BigDecimal total = materialCost.add(laborCost).add(tax);
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.orderNumber;
        hash = 83 * hash + Objects.hashCode(this.customerName);
        hash = 83 * hash + Objects.hashCode(this.state);
        hash = 83 * hash + Objects.hashCode(this.taxRate);
        hash = 83 * hash + Objects.hashCode(this.productType);
        hash = 83 * hash + Objects.hashCode(this.area);
        hash = 83 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 83 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
        hash = 83 * hash + Objects.hashCode(this.materialCost);
        hash = 83 * hash + Objects.hashCode(this.laborCost);
        hash = 83 * hash + Objects.hashCode(this.tax);
        hash = 83 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSquareFoot, 
                other.laborCostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "orderNumber=" + orderNumber + ", customerName=" 
                + customerName + ", state=" + state + ", taxRate=" + taxRate 
                + ", productType=" + productType + ", area=" + area 
                + ", costPerSquareFoot=" + costPerSquareFoot 
                + ", laborCostPerSquareFoot=" + laborCostPerSquareFoot 
                + ", materialCost=" + materialCost + ", laborCost=" + laborCost 
                + ", tax=" + tax + ", total=" + total + '}';
    }

}
