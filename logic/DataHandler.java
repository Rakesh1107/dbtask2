package logic;

import io.Input;
import cache.Cache;
import pojo.Account;
import pojo.Customer;
import validator.Validator;
import exception.BankException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public enum DataHandler {
     INSTANCE;

     Mediator mediator = Mediator.INSTANCE;
     Cache cache = Cache.INSTANCE;

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        formatter.setTimeZone(TimeZone.getDefault());
        System.out.println(TimeZone.getDefault().getID());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        System.out.println(formatter.format(calendar.getTime()));
    }

    public long[] createNewUser(String name, long mobileNumber, String address, String branch) throws BankException {

        if (Validator.validate(name, branch, address, String.valueOf(mobileNumber))) {

            if (Validator.validateMobileNumber(mobileNumber)) {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setMobileNumber(mobileNumber);
                customer.setAddress(address);
                customer.setTime(System.currentTimeMillis());
                int userId = mediator.insertCustomer(customer);

                if (userId != -1) {
                    customer.setUserId(userId);
                    Account account = new Account();
                    account.setUserId(userId);
                    account.setBranch(branch);
                    account.setTime(System.currentTimeMillis());
                    long accountNumber = mediator.insertAccount(account);

                    if (accountNumber != -1) {
                        account.setAccountNumber(accountNumber);
                        cache.getUsers().put(userId, customer);
                        cache.getCache().put(userId, new HashMap<>());
                        cache.getCache().get(userId).put(accountNumber, account);
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

    public long createNewAccount(int userId, String branch) throws BankException {

        if (Validator.validate(branch, String.valueOf(userId))) {
            if (!cache.getCache().containsKey(userId)) {
                throw new BankException("User id does not exist");
            }

            Account account;
            account = new Account();
            account.setUserId(userId);
            account.setBranch(branch);
            account.setTime(System.currentTimeMillis());

            long accountNumber = mediator.insertAccount(account);

            if (accountNumber != -1) {
                account.setAccountNumber(accountNumber);
                cache.getCache().get(userId).put(accountNumber, account);
                //Cache.getAccounts().add(accountNumber);
                return accountNumber;
            } else {
                throw new BankException("Account creation failed");
            }
        } else {
            throw new BankException("Branch can not be empty");
        }
    }

    public long checkBalance(int userId) throws BankException {
        if (Validator.validate(String.valueOf(userId))) {
            if (!cache.getCache().containsKey(userId)) {
                throw new BankException("User id not found");
            }
            if (cache.getCache().get(userId).isEmpty()) {
                throw new BankException("No accounts available");
            }
            long balance = 0;
            for (Account account : cache.getCache().get(userId).values()) {
                balance += account.getBalance();
            }
            return balance;
        } else {
            throw new BankException("User id field can not be empty");
        }
    }

    public long withdrawMoney(int userId, long amount) throws BankException {

        if (Validator.validate(String.valueOf(userId), String.valueOf(amount))) {
            if (amount > 0) {
                if (cache.getCache().containsKey(userId)) {

                    if (cache.getCache().get(userId).isEmpty()) {
                        throw new BankException("No accounts available");
                    }

                    if (Validator.validateMoney(amount)) {
                        throw new BankException("Amount should be in multiples of 100");
                    }

                    List<Account> userAccounts = new ArrayList<>(cache.getCache().get(userId).values());
                    int i = 0;
                    System.out.println("Select account");

                    for (Account account : userAccounts) {
                        System.out.println(++i + ". " + account.getAccountNumber());
                    }

                    int option = Input.getInt();

                    if (option > 0 && option <= userAccounts.size()) {
                        long oldBalance = userAccounts.get(option - 1).getBalance();
                        System.out.println(oldBalance);
                        if (oldBalance >= amount) {
                            long accountToUpdate = userAccounts.get(option - 1).getAccountNumber();
                            long newBalance = mediator.updateBalance(1, accountToUpdate, amount);
                            userAccounts.get(option - 1).setBalance(oldBalance - amount);
                            return newBalance;
                        } else {
                            throw new BankException("Insufficient funds");
                        }
                    } else {
                        throw new BankException("Account does not exist");
                    }
                } else {
                    throw new BankException("User id does not exist");
                }
            } else {
                throw new BankException("Amount can not be 0 or negative");
            }
        } else {
            throw new BankException("User id field can not be empty");
        }

    }

    public long depositMoney(int userId, long amount) throws BankException {
        if (Validator.validate(String.valueOf(userId), String.valueOf(amount))) {
            if (cache.getCache().containsKey(userId)) {

                if (cache.getCache().get(userId).isEmpty()) {
                    throw new BankException("No accounts available");
                }

                if (Validator.validateMoney(amount)) {
                    throw new BankException("Amount should be in multiples of 100");
                }

                List<Account> userAccounts = new ArrayList<>(cache.getCache().get(userId).values());
                int i = 0;
                System.out.println("Select account");

                for (Account account : userAccounts) {
                    System.out.println(++i + ". " + account.getAccountNumber());
                }
                int option = Input.getInt();

                if (option > 0 && option <= userAccounts.size()) {
                    long accountToUpdate = userAccounts.get(option - 1).getAccountNumber();
                    long oldBalance = userAccounts.get(option - 1).getBalance();
                    long newBalance = mediator.updateBalance(2, accountToUpdate, amount);
                    userAccounts.get(option - 1).setBalance(oldBalance + amount); // updating in cache
                    return newBalance;
                } else {
                    throw new BankException("Account number does not exist");
                }
            } else {
                throw new BankException("User id not found");
            }
        } else {
            throw new BankException("User id field can not be null");
        }

    }

    public List<Account> showAccounts(int userId) throws BankException {

        if (Validator.validate(String.valueOf(userId))) {
            if (!cache.getCache().containsKey(userId)) {
                throw new BankException("User id does not exist");
            } else {
                if (cache.getCache().get(userId).isEmpty()) {
                    throw new BankException("No accounts available");
                } else {
                    return new ArrayList<>(cache.getCache().get(userId).values());
                }
            }

        } else {
            throw new BankException("User id field can not be empty");
        }

    }

    public boolean deactivateAccount(int userId) throws BankException {
        if (Validator.validate(String.valueOf(userId))) {
            if (cache.getCache().containsKey(userId)) {
                if (cache.getCache().get(userId).isEmpty()) {
                    throw new BankException("No accounts available");
                }

                List<Account> userAccounts = new ArrayList<>(cache.getCache().get(userId).values());

                int i = 0;
                System.out.println("Select account");

                for (Account account : userAccounts) {
                    System.out.println(++i + ". " + account.getAccountNumber());
                }
                int option = Input.getInt();

                if (option > 0 && option <= userAccounts.size()) {
                    long accountNumber = userAccounts.get(option - 1).getAccountNumber();
                    if (mediator.deactivateAccount(accountNumber)) {
                        cache.getCache().get(userId).remove(accountNumber);
                        if (!cache.getCache().containsKey(userId)) {
                            cache.getCache().put(userId, new HashMap<>());
                        }
                        return true;
                    }

                } else {
                    throw new BankException("Account number does not exist");
                }
            } else {
                throw new BankException("User id does not exist");
            }
        } else {
            throw new BankException("User id field can not be empty");
        }
        return false;
    }

    public boolean deactivateUser(int userId) throws BankException {
        if (Validator.validate(String.valueOf(userId))) {
            if (cache.getCache().containsKey(userId)) {
                if (mediator.deactivateUser(userId)) {
                    cache.getCache().remove(userId);
                    return true;
                }
            } else {
                throw new BankException("User id does not exist");
            }
        } else {
            throw new BankException("User id field can not be empty");
        }
        return false;
    }
}
