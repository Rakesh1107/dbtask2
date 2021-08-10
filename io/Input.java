package io;

import logic.Account;
import logic.DataHandler;
import logic.Starter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Input {
    static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    public static void getInput() throws SQLException, IOException {
        Output.showWelcomeMessage();
        Starter.start();

        int option = getInt();
        while(option != 0) {
            handle(option);
            Output.showWelcomeMessage();
            option = getInt();
        }

        Output.closeApplication();
    }

    private static void handle(int option) throws IOException, SQLException {
        switch (option) {
            case 1:
                Output.showNewUser();
                int userId = DataHandler.createNewUser(getString(), getLong(), getString());
                Output.showUserId(userId);
                break;
            case 2:
                Output.showNewAccount();
                long accountNumber = DataHandler.createNewAccount(getInt(), getString());
                if(accountNumber == -1) {
                    Output.userIdNotFound();
                } else {
                    Output.showAccountNumber(accountNumber);
                }
                break;
            case 3:
                Output.askUserId();
                long balance = DataHandler.checkBalance(getInt());
                if(balance == -1) {
                    Output.userIdNotFound();
                }
                else if(balance == -2) {
                    Output.noAccounts();
                }
                else {
                    Output.showBalance(balance);
                }
                break;
            case 4:
                Output.askUserId();
                List<Account> list = DataHandler.showAccounts(getInt());
                if (list == null) {
                    Output.userIdNotFound();
                } else if(list.isEmpty()) {
                    Output.noAccounts();
                } else {
                    Output.printAccounts(list);
                }
                break;
            default:
                Output.enterValidInput();
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