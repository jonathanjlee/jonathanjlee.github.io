/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author jonat
 */
public class UserIOConsoleImpl implements UserIO {

    double userDouble = 0;
    float userFloat = 0;
    int userInt = 0;
    long userLong = 0;

    Scanner userInput = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        return Double.parseDouble(userInput.nextLine());
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        do {
            System.out.println(prompt);
            userDouble = Double.parseDouble(userInput.nextLine());
            if (userDouble < min || userDouble > max) {
                System.out.println("Please choose a number between " + min
                        + " and " + max + ".");
            }
        } while (userDouble < min || userDouble > max);
        return userDouble;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        return Float.parseFloat(userInput.nextLine());
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        do {
            System.out.println(prompt);
            userFloat = Float.parseFloat(userInput.nextLine());
            if (userFloat < min || userFloat > max) {
                System.out.println("Please choose a number between " + min
                        + " and " + max + ".");
            }
        } while (userFloat < min || userFloat > max);
        return userFloat;
    }

    @Override
    public int readInt(String prompt) {
        do {
            try {
                System.out.println(prompt);
                userInt = Integer.parseInt(userInput.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a positive integer.");
            }
        } while (userInt <= 0);
        return userInt;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        do {
            try {
                System.out.println(prompt);
                userInt = Integer.parseInt(userInput.nextLine());
                if (userInt < min || userInt > max) {
                    System.out.println("Please choose a number between " + min
                            + " and " + max + ".");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please choose a number between " + min
                        + " and " + max + ".");
            }
        } while (userInt < min || userInt > max);
        return userInt;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        return Long.parseLong(userInput.nextLine());
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        do {
            System.out.println(prompt);
            userLong = Long.parseLong(userInput.nextLine());
            if (userLong < min || userLong > max) {
                System.out.println("Please choose a number between " + min
                        + " and " + max + ".");
            }
        } while (userLong < min || userLong > max);
        return userLong;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return userInput.nextLine();
    }

    @Override
    public boolean validDate(String stringDate) {
        try {
            LocalDate.parse(stringDate,
                    DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i])
                    || chars[i] == '.') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}
