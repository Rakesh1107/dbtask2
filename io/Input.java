package io;

import exception.BankException;
import pojo.Account;
import logic.DataHandler;
import logic.Initiator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

public class Input {

    static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    public static void getInput() {
        try {
            Initiator.initiate();
            Display.showWelcomeMessage();

            int option = getInt();
            while(option != 0) {
                handle(option);
                Display.showWelcomeMessage();
                option = getInt();
            }

            Display.closeApplication();
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handle(int option) {
        try {
            switch (option) {
                case 1:
                    Display.showNewUser();
                    long[] data = DataHandler.createNewUser(getString(), getLong(), getString(), getString());
                    if (data == null) {
                        Display.failed("User creation");
                    } else {
                        Display.showUserData(data);
                    }

                    break;
                case 2:
                    Display.showNewAccount();
                    long accountNumber = DataHandler.createNewAccount(getInt(), getString());
                    if(accountNumber == -1) {
                        Display.userIdNotFound();
                    } else {
                        Display.showAccountNumber(accountNumber);
                    }
                    break;
                case 3:
                    Display.askUserId();
                    long balance = DataHandler.checkBalance(getInt());
                    if(balance == -1) {
                        Display.userIdNotFound();
                    }
                    else {
                        Display.showBalance(balance);
                    }
                    break;
                case 4:
                    Display.askUserId();
                    List<Account> list = DataHandler.showAccounts(getInt());
                    if (list == null) {
                        Display.userIdNotFound();
                    }
                    else {
                        Display.printAccounts(list);
                    }
                    break;
                case 5:
                    System.out.println("Enter user id");
                    System.out.println("Enter amount to deposit");
                    long newBalance = DataHandler.depositMoney(getInt(), getLong());
                    if (newBalance != -1) {
                        System.out.println("Your new balance is " + newBalance);
                    }
                    break;
                case 6:
                    System.out.println("Enter user id");
                    System.out.println("Enter amount to withdraw");
                    long newBalance2 = DataHandler.withdrawMoney(getInt(), getLong());
                    if (newBalance2 != -1) {
                        System.out.println("Your new balance is " + newBalance2);
                    }
                    break;
                default:
                    Display.enterValidInput();
            }
        }
        catch (BankException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getString() {
        try {
            return inputReader.readLine();
        }
        catch (IOException e) {
            System.out.println("Invalid input");
        }
        return null;
    }

    public static long getLong() {
        try {
            return Long.parseLong(inputReader.readLine());
        }
        catch (IOException e) {
            System.out.println("Invalid input");
        }
        return -1;
    }

    public static int getInt() {
        try {
            return Integer.parseInt(inputReader.readLine());
        }
        catch (IOException e) {
            System.out.println("Invalid input");
        }
        return -1;

    }

}