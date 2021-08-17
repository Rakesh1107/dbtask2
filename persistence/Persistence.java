package persistence;

import exception.BankException;
import pojo.Account;
import pojo.Customer;

import java.util.List;
import java.lang.reflect.*;

interface Persistence {

//    public static List<Account> getAccounts();
//
//    public static List<Customer> getCustomers();

    public int insertIntoCustomer();

    public long insertIntoAccounts();

    public long depositMoney();

    public long withdrawMoney();

    public boolean deactivateAccount();

    public boolean deactivateUser();

}
