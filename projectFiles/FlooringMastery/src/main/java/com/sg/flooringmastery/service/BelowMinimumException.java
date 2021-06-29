/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

/**
 *
 * @author jonat
 */
public class BelowMinimumException extends Exception {
    
    public BelowMinimumException(String message) {
        super(message);
    }
    
    public BelowMinimumException(String message, Throwable cause) {
        super(message, cause);
    }
}
