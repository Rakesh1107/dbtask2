package logic;

import db.Connector;
import pojo.Account;
import pojo.Customer;
import java.util.List;

public class Mediator {

    public static void extract() {
        List<Account> accounts = Connector.getAccounts();
        DataStorage.addData(accounts);

        List<Customer> customers = Connector.getCustomers();
        DataStorage.addCustomer(customers);
    }

    public static int insertCustomer(String name, long mobileNumber, String address) {
        return Connector.insertIntoCustomers(name, mobileNumber, address);
    }

    public static long insertAccount(int userId, String branch) {
        return Connector.insertIntoAccounts(userId, branch);
    }

    public static boolean insertCustomers(List<Customer> customers) {
        return Connector.insertIntoCustomers(customers);
    }

    public static boolean insertAccounts(List<Account> accounts) {
        return Connector.insertIntoAccounts(accounts);
    }
}
