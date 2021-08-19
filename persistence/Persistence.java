package persistence;

import pojo.Account;
import pojo.Customer;
import java.util.List;
import exception.BankException;

public interface Persistence {

    List<Account> getAccounts() throws BankException;

    List<Customer> getCustomers() throws BankException;

    int insertIntoCustomers(Customer customer) throws BankException;

    long insertIntoAccounts(Account account) throws BankException;

    List<Long> insertIntoAccounts(List<Account> accounts) throws BankException;

    List<Integer> insertIntoCustomers(List<Customer> customers) throws BankException;

    long depositMoney(long accountNumber, long money) throws BankException;

    long withdrawMoney(long accountNumber, long money) throws BankException;

    boolean deactivateAccount(long accountNumber) throws BankException;

    boolean deactivateUser(int userId) throws BankException;

    List<Integer> getUsersWithNoActiveAccounts() throws BankException;
}
