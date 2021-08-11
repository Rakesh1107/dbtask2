package logic;

import db.Connector;

import java.util.List;

public class Mediator {

    public static void extract()  {
        List<Account> accounts = Connector.getAccounts();
        DataStorage.addData(accounts);

        List<Customer> customers = Connector.getCustomers();
        DataStorage.addCustomer(customers);
    }

    public static void insertCustomer(Customer customer) {
        Connector.insertIntoCustomers(customer);
    }

    public static void insertAccount(Account account) {
        Connector.insertIntoAccounts(account);
    }

    public static void insertCustomers(List<Customer> customers) {
        Connector.insertIntoCustomers(customers);
    }

    public static void insertAccounts(List<Account> accounts) {
        Connector.insertIntoAccounts(accounts);
    }
}
