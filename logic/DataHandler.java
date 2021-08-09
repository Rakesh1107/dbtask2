package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataHandler {

    public static int createNewUser(String name, long mobileNumber, String address) throws SQLException {
        int userId = Generator.generateUserId();

        Customer customer;
        customer = new Customer();
        customer.setUserId(userId);
        customer.setName(name);
        customer.setMobileNumber(mobileNumber);
        customer.setAddress(address);

        Mediator.insertCustomer(customer);

        DataStorage.data.put(userId, new HashMap<>());
        return userId;
    }

    public static long createNewAccount(int userId, String branch) throws SQLException {

        if(!DataStorage.getData().containsKey(userId)) {
            return -1;
        }

        long accountNumber = Generator.generateAccountNumber();

        Account account;
        account = new Account();
        account.setUserId(userId);
        account.setAccountNumber(accountNumber);
        account.setBranch(branch);

        Mediator.insertAccount(account);

        DataStorage.getAccounts().add(accountNumber);
        DataStorage.getData().get(userId).put(accountNumber, account);

        return accountNumber;
    }

    public static long checkBalance(int userId) {

        if(DataStorage.getData().containsKey(userId)) {
            long balance = 0;
            for(Account account: DataStorage.getData().get(userId).values()) {
                balance+=account.getBalance();
            }
            return balance;
        }

        return -1;
    }

    public static List<Account> showAccounts(int userId) {
        if (DataStorage.getData().containsKey(userId)) {
            return new ArrayList<>(DataStorage.getData().get(userId).values());
        }
        return null;
    }
}
