package io;

import pojo.Account;
import logic.DataHandler;
import logic.Initiator;
import validator.Validator;
import exception.BankException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

public class Input {

    static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    public static void getInput() {
        int option;
        try {
            Initiator.initiate();
            Display.showWelcomeMessage();
            option = getInt();

            while (option != 0) {
                handle(option);
                Display.showWelcomeMessage();
                option = getInt();
            }

            System.out.println("Closing application");
        } catch (BankException exception) {
            //exception.printStackTrace();
            System.out.println(exception.getMessage());
        }
    }



    private static void handle(int option) {
        try {
            switch (option) {
                case 1:
                    Display.showNewUser();
                    String name = getString();
                    long accountNumber = getLong();
                    String branch = getString();
                    String address = getString();

                    if (Validator.validate(name, branch, address)) {
                        long[] data = DataHandler.createNewUser(name, accountNumber, branch, address);
                        Display.showUserData(data);
                    } else {
                        System.out.println("All fields must be filled !");
                    }
                    break;
                case 2:
                    Display.showNewAccount();
                    int userId = getInt();
                    branch = getString();

                    if (Validator.validate(branch)) {
                        long accountNo = DataHandler.createNewAccount(userId, branch);
                        Display.showAccountNumber(accountNo);
                    } else {
                        System.out.println("All fields must be filled !");
                    }
                    break;
                case 3:
                    System.out.println("Enter user id");
                    userId = getInt();

                    long balance = DataHandler.checkBalance(userId);
                    Display.showBalance(balance);
                    break;
                case 4:
                    System.out.println("Enter user id");
                    userId = getInt();

                    List<Account> list = DataHandler.showAccounts(userId);
                    Display.printAccounts(list);
                    break;
                case 5:
                    System.out.println("Enter user id");
                    System.out.println("Enter amount to deposit");
                    userId = getInt();
                    long amount = getLong();

                    long newBalance = DataHandler.depositMoney(userId, amount);
                    System.out.println("Your new balance is " + newBalance);
                    break;
                case 6:
                    System.out.println("Enter user id");
                    System.out.println("Enter amount to withdraw");
                    userId = getInt();
                    amount = getLong();

                    newBalance = DataHandler.withdrawMoney(userId, amount);
                    System.out.println("Your new balance is " + newBalance);
                    break;
                case 7:
                    System.out.println("Enter user id");
                    userId = getInt();
                    System.out.println("1. Delete account");
                    System.out.println("2. Delete user");
                    option = getInt();

                    if (option == 1) {
                        if(DataHandler.deactivateAccount(userId)) {
                            System.out.println("Account deleted successfully");
                        }
                    } else if (option == 2) {
                        if(DataHandler.deactivateUser(userId)) {
                            System.out.println("User deleted successfully");
                        }
                    } else {
                        System.out.println("Enter valid option !");
                    }
                    break;

                default:
                    System.out.println("Enter valid input");
            }
        } catch (BankException exception) {
            //exception.printStackTrace();
            System.out.println(exception.getMessage());
        }
    }

    public static String getString() throws BankException {
        try {
            return inputReader.readLine();
        } catch (NumberFormatException | IOException exception) {
            throw new BankException("Invalid input!", exception);
        }
    }

    public static long getLong() throws BankException {
        try {
            return Long.parseLong(inputReader.readLine());
        }
        catch (NumberFormatException | IOException exception) {
            throw new BankException("Enter a valid number!", exception);
        }
    }

    public static int getInt() throws BankException {
        try {
            return Integer.parseInt(inputReader.readLine());
        }
        catch (NumberFormatException | IOException exception) {
            throw new BankException("Enter a valid number!", exception);
        }

    }

}