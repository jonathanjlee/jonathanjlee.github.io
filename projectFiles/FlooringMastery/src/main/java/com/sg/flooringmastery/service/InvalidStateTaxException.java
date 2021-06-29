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
public class InvalidStateTaxException extends Exception {
    
    public InvalidStateTaxException(String message) {
        super(message);
    }
    
    public InvalidStateTaxException(String message, Throwable cause) {
        super(message, cause);
    }
}
