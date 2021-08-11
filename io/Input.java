package io;

import pojo.Account;
import logic.DataHandler;
import logic.Initiator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

public class Input {

    static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    public static void getInput() throws IOException {
        Initiator.initiate();
        Display.showWelcomeMessage();

        int option = getInt();
        while(option != 0) {
            handle(option);
            Display.showWelcomeMessage();
            option = getInt();
        }

        Display.closeApplication();
    }

    private static void handle(int option) throws IOException {
        switch (option) {
            case 1:
                Display.showNewUser();
                long[] data = DataHandler.createNewUser(getString(), getLong(), getString(), getString());
                Display.showUserData(data);
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
            default:
                Display.enterValidInput();
        }
    }


    public static String getString() throws IOException {
        return inputReader.readLine();
    }

    public static long getLong() throws IOException {
        return Long.parseLong(inputReader.readLine());
    }

    public static int getInt() throws IOException {
        return Integer.parseInt(inputReader.readLine());
    }

}