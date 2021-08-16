package io;

import pojo.Account;
import java.util.List;

import static java.lang.System.out;

public class Display {
    public static void showWelcomeMessage() {
        out.println("1. New user ");
        out.println("2. Existing user? Create new account ");
        out.println("3. Check Balance ");
        out.println("4. Show accounts ");
        out.println("5. Deposit Money ");
        out.println("6. Withdraw money ");
        out.println("7. Delete User/Account ");
        out.println("Enter 0 to exit");

    }

    public static void showNewUser()  {
        out.println("Enter your name");
        out.println("Enter your mobile number");
        out.println("Enter address");
        out.println("Enter branch");
    }

    public static void showNewAccount() {
        out.println("Enter your user id");
        out.println("Enter branch");
    }

    public static void askUserId() {
        out.println("Enter user id");
    }

    public static void enterValidInput() {
        out.println("Enter valid input");
    }

    public static void printAccounts(List<Account> list) {
        for (Account account: list) {
            out.println(account);
        }
    }

    public static void closeApplication() {
        out.println("Closing Application");
    }

    public static void showUserData(long[] data) {
        out.println("Your user id is " + data[0] + "\nYour account number is " + data[1]);
    }

    public static void userIdNotFound() {
        out.println("User id does not exist");
    }

    public static void showBalance(long balance) {
        out.println("Your total balance is " + balance);
    }

    public static void showAccountNumber(long accountNumber) {
        out.println("Your account number is " + accountNumber);
    }

}