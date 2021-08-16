package logic;

import cache.Cache;
import exception.BankException;
import io.Input;
import pojo.Account;
import pojo.Customer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataHandler {

    public static void main(String[] args) {
        DateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("India"));
        formatter.setTimeZone(TimeZone.getDefault());
        System.out.println(TimeZone.getDefault().getID());
        Calendar calendar = Calendar.getInstance();
        String[] arr = TimeZone.getAvailableIDs();
        for (String s: arr) {
            System.out.println(s);
        }
        calendar.setTimeInMillis(System.currentTimeMillis());
        System.out.println(formatter.format(calendar.getTime()));
    }

    private static boolean validate(String... fields) {
        for (String field: fields) {
            if (field == null || field.length() == 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateMobileNumber(long mobileNumber) {
        String mobile = String.valueOf(mobileNumber);

        if (mobile.length() == 10) {
            char firstNumber = mobile.charAt(0);
            return firstNumber == '6' || firstNumber == '7' || firstNumber == '8' || firstNumber == '9';
        } else {
            return false;
        }
    }

    public static long[] createNewUser(String name, long mobileNumber, String address, String branch) throws BankException {
    /*    Pattern pattern = Pattern.compile("[A-Za-z]+");
          Matcher matcher = pattern.matcher(name);
          boolean match = matcher.find(); */

        if (validate(name, branch, address, String.valueOf(mobileNumber))) {
            if(validateMobileNumber(mobileNumber)) {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setMobileNumber(mobileNumber);
                customer.setAddress(address);
                //customer.setTime(System.currentTimeMillis());

                int userId = Mediator.insertCustomer(customer);
                if (userId != -1) {
                    customer.setUserId(userId);

                    Account account = new Account();
                    account.setUserId(userId);
                    account.setBranch(branch);
                    //account.setTime(System.currentTimeMillis());
                    long accountNumber = Mediator.insertAccount(account);

                    if (accountNumber != -1) {
                        account.setAccountNumber(accountNumber);
                        Cache.getUsers().put(userId, customer);
                        Cache.getCache().put(userId, new HashMap<>());
                        Cache.getCache().get(userId).put(accountNumber, account);
                        //Cache.getAccounts().add(accountNumber);

                        return new long[]{userId, accountNumber};
                    }
                    throw new BankException("Account creation failed");
                }
                throw new BankException("User creation failed");
            }
            throw new BankException("Invalid mobile number");
        }
        throw new BankException("Name, Branch or Address can not empty");

    }

    public static long createNewAccount(int userId, String branch) throws BankException {

        if (validate(branch, String.valueOf(userId))) {
            if (!Cache.getCache().containsKey(userId)) {
                throw new BankException("User id does not exist");
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
            } else {
                throw new BankException("Account creation failed");
            }
        }
        else {
            throw new BankException("Branch can not be empty");
        }
    }

    public static long checkBalance(int userId) throws BankException {
        if (validate(String.valueOf(userId))) {
            if (!Cache.getCache().containsKey(userId)) {
                throw new BankException("User id not found");
            }
            long balance = 0;
            for (Account account : Cache.getCache().get(userId).values()) {
                balance += account.getBalance();
            }
            return balance;
        }
        else {
            throw new BankException("User id field can not be empty");
        }
    }

    public static long withdrawMoney(int userId, long amount) throws BankException {

        if (validate(String.valueOf(userId), String.valueOf(amount))) {
            if (amount > 0) {
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
                            throw new BankException("Insufficient funds");
                        }
                    } else {
                        throw new BankException("Account does not exist");
                    }
                }
                else {
                    throw new BankException("User id does not exist");
                }
            } else {
                throw new BankException("Amount can not be 0 or negative");
            }
        }
        throw new BankException("User id field can not be empty");

    }

    public static long depositMoney(int userId, long amount) throws BankException {
        if (validate(String.valueOf(userId), String.valueOf(amount))) {
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
                    throw new BankException("Account number does not exist");
                }
            }
            else {
                throw new BankException("User id not found");
            }
        }
        else {
            throw new BankException("User id field can not be null");
        }

    }

    public static List<Account> showAccounts(int userId) throws BankException {

        if (validate(String.valueOf(userId))) {
            if (!Cache.getCache().containsKey(userId)) {
                throw new BankException("User id does not exist");
            }
            else
                return new ArrayList<>(Cache.getCache().get(userId).values());
        }
        throw new BankException("User id field can not be empty");
    }

    public static boolean deactivateAccount(int userId) throws BankException {
        if (validate(String.valueOf(userId))) {
            if (Cache.getCache().containsKey(userId)) {
                List<Account> userAccounts = new ArrayList<>(Cache.getCache().get(userId).values());

                if (userAccounts.isEmpty()) {
                    throw new BankException("No accounts available");
                }

                int i = 0;
                System.out.println("Select account");

                for (Account account: userAccounts) {
                    System.out.println(++i + ". " + account.getAccountNumber());
                }
                int option = Input.getInt();

                if (option > 0 && option <= userAccounts.size()) {
                    long accountNumber = userAccounts.get(option-1).getAccountNumber();
                    if (Mediator.deactivateAccount(accountNumber)) {
                        Cache.getCache().get(userId).remove(accountNumber);
                        if (Cache.getCache().get(userId).isEmpty()) {
                            Cache.getCache().put(userId, new HashMap<>());
                        }
                        return true;
                    }

                } else {
                    throw new BankException("Account number does not exist");
                }
            }
            else {
                throw new BankException("User id does not exist");
            }
        }
        else {
            throw new BankException("User id field can not be empty");
        }
        return false;
    }

    public static boolean deactivateUser(int userId) throws BankException {
        if (validate(String.valueOf(userId))) {
            if (Cache.getCache().containsKey(userId)) {
                if (Mediator.deactivateUser(userId)) {
                    Cache.getCache().remove(userId);
                    return true;
                }
            } else{
                throw new BankException("User id does not exist");
            }
        }
        else {
            throw new BankException("User id field can not be empty");
        }
        return false;
    }
}
