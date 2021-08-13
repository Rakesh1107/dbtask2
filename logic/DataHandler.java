package logic;

import cache.Cache;
import exception.BankException;
import io.Input;
import pojo.Account;
import pojo.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHandler {

    public static long[] createNewUser(String name, long mobileNumber, String address, String branch) throws BankException {
        Pattern pattern = Pattern.compile("[A-Za-z]+");
        Matcher matcher = pattern.matcher(name);
        boolean match = matcher.find();
        int len = String.valueOf(mobileNumber).length();

        if (match && len == 10) {
            Customer customer = new Customer();
            customer.setName(name);
            customer.setMobileNumber(mobileNumber);
            customer.setAddress(address);
            int userId = Mediator.insertCustomer(customer);
            if (userId != -1) {
                customer.setUserId(userId);

                Account account = new Account();
                account.setUserId(userId);
                account.setBranch(branch);
                long accountNumber = Mediator.insertAccount(account);

                if (accountNumber != -1) {
                    account.setAccountNumber(accountNumber);
                    Cache.getUsers().put(userId, customer);
                    Cache.getCache().put(userId, new HashMap<>());
                    Cache.getCache().get(userId).put(accountNumber, account);
                    //Cache.getAccounts().add(accountNumber);

                    return new long[]{userId, accountNumber};
                }
                System.out.println("Account creation failed");
                return null;
            }
            System.out.println("user id creation failed");
            return null;
        }
        System.out.println("Name/Number format wrong");

        return null;
    }

    public static long createNewAccount(int userId, String branch) throws BankException {

        if (!Cache.getCache().containsKey(userId)) {
            return -1;
        }

        Account account;
        account = new Account();
        account.setUserId(userId);
        account.setBranch(branch);

        long accountNumber = Mediator.insertAccount(account);

        if (accountNumber != -1) {
            account.setAccountNumber(accountNumber);
            Cache.getCache().get(userId).put(accountNumber, account);
            //Cache.getAccounts().add(accountNumber);
            return accountNumber;
        }

        return -1;

    }

    public static long checkBalance(int userId) {

        if (!Cache.getCache().containsKey(userId)) {
            return -1;
        }
        long balance = 0;
        for (Account account : Cache.getCache().get(userId).values()) {
            balance += account.getBalance();
        }
        return balance;
    }

    public static long withdrawMoney(int userId, long amount) throws BankException {
        if (Cache.getCache().containsKey(userId)) {
            List<Account> userAccounts = new ArrayList<>(Cache.getCache().get(userId).values());
            int i = 0;
            System.out.println("Select account");

            for (Account account: userAccounts) {
                System.out.println(++i + ". " + account.getAccountNumber());
            }

            int option = Input.getInt();

            if(option > 0 && option <= userAccounts.size()) {
                long oldBalance = userAccounts.get(option-1).getBalance();
                System.out.println(oldBalance);
                if(oldBalance >= amount) {
                    long accountToUpdate = userAccounts.get(option-1).getAccountNumber();
                    long newBalance = Mediator.updateBalance(1, accountToUpdate, amount);
                    userAccounts.get(option-1).setBalance(oldBalance-amount);
                    return newBalance;
                } else {
                    System.out.println("Insufficient funds");
                }
            } else {
                System.out.println("No such account");
            }
        }
        else {
            System.out.println("User id not found");
        }
        return -1;
    }

    public static long depositMoney(int userId, long amount) throws BankException {
        if (Cache.getCache().containsKey(userId)) {
            List<Account> userAccounts = new ArrayList<>(Cache.getCache().get(userId).values());
            int i = 0;
            System.out.println("Select account");

            for (Account account: userAccounts) {
                System.out.println(++i + ". " + account.getAccountNumber());
            }
            int option = Input.getInt();

            if(option > 0 && option <= userAccounts.size()) {
                long accountToUpdate = userAccounts.get(option-1).getAccountNumber();
                long oldBalance = userAccounts.get(option-1).getBalance();
                long newBalance = Mediator.updateBalance(2, accountToUpdate, amount);
                userAccounts.get(option-1).setBalance(oldBalance+amount); // updating in cache
                return newBalance;
            } else {
                System.out.println("No such account");
            }
        }
        else {
            System.out.println("User id not found");
        }
        return -1;
    }

    public static List<Account> showAccounts(int userId) {

//        if (!Cache.getUsers().containsKey(userId)) {
//            return null;
//        }

        if (!Cache.getCache().containsKey(userId)) {
            return null;
        }

        return new ArrayList<>(Cache.getCache().get(userId).values());
    }
}
