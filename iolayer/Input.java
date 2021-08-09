package iolayer;

import logic.Account;
import logic.DataHandler;
import logic.Mediator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Input {
    static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    public static void getInput() throws SQLException, IOException {
        Output.showWelcomeMessage();
        Mediator.start();

        int option = getInt();
        while(option != 0) {
            handle(option);
            Output.showWelcomeMessage();
            option = getInt();
        }

        System.out.println("Closing Application");
    }

    private static void handle(int option) throws IOException, SQLException {
        switch (option) {
            case 1:
                Output.showNewUser();
                String name = getString();
                long mobileNumber = getLong();
                String address = getString();
                int userId = DataHandler.createNewUser(name, mobileNumber, address);
                System.out.println("Your user id is " + userId);
                break;
            case 2:
                Output.showNewAccount();
                long accountNumber = DataHandler.createNewAccount(getInt(), getString());
                if(accountNumber == -1) {
                    System.out.println("User id does not exist");
                } else {
                    System.out.println("Your account number is " + accountNumber);
                }
                break;
            case 3:
                Output.askUserId();
                long balance = DataHandler.checkBalance(getInt());
                if(balance == -1) {
                    System.out.println("User id does not exist");
                }
                else {
                    System.out.println("Your total balance is " + balance);
                }
                break;
            case 4:
                Output.askUserId();
                List<Account> list = DataHandler.showAccounts(getInt());
                if (list == null) {
                    System.out.println("User id does not exist");
                } else if(list.isEmpty()) {
                    System.out.println("You don't have any accounts");
                } else {
                    for(Account account: list)
                        System.out.println(account);
                }
                break;
            default:
                System.out.println("Enter valid input");
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
