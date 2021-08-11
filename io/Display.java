package io;

import pojo.Account;

import java.util.List;

public class Display {
    public static void showWelcomeMessage() {
        System.out.println("1. New user ");
        System.out.println("2. Existing user? Create new account ");
        System.out.println("3. Check Balance ");
        System.out.println("4. Show accounts ");
        System.out.println("Enter 0 to exit");
    }

    public static void showNewUser()  {
        System.out.println("Enter your name");
        System.out.println("Enter your mobile number");
        System.out.println("Enter address");
        System.out.println("Enter branch");
    }

    public static void showNewAccount() {
        System.out.println("Enter your user id");
        System.out.println("Enter branch");
    }

    public static void askUserId() {
        System.out.println("Enter user id");
    }

    public static void enterValidInput() {
        System.out.println("Enter valid input");
    }

    public static void printAccounts(List<Account> list) {
        for (Account account: list) {
            System.out.println(account);
        }
    }

    public static void closeApplication() {
        System.out.println("Closing Application");
    }

    public static void showUserData(long[] data) {
        System.out.println("Your user id is " + data[0] + "\nYour account number is " + data[1]);
    }

    public static void userIdNotFound() {
        System.out.println("User id does not exist");
    }

    public static void showBalance(long balance) {
        System.out.println("Your total balance is " + balance);
    }

    public static void showAccountNumber(long accountNumber) {
        System.out.println("Your account number is " + accountNumber);
    }

    public static void failed(String message) {
        System.out.println(message + " failed");
    }
}