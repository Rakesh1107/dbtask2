package logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage {

    static Map<Integer, Map<Long, Account>> data = new HashMap<>();
    static List<Long> accounts = new ArrayList<>();

    public static void addData(ResultSet accountData) throws SQLException {
        int userId = accountData.getInt(2);
        long accountNumber = accountData.getLong(3);
        long balance = accountData.getLong(4);
        String branch = accountData.getString(5);

        Account account = new Account();
        account.setUserId(userId);
        account.setAccountNumber(accountNumber);
        account.setBalance(balance);
        account.setBranch(branch);

        accounts.add(accountNumber);

        if(data.containsKey(userId)) {
            Map<Long, Account> newAccount = data.get(userId);
            newAccount.put(accountNumber, account);
        }
        else {
            data.put(userId, new HashMap<>());
            data.get(userId).put(accountNumber, account);
        }
    }

    public static Map<Integer, Map<Long, Account>> getData() {
        return data;
    }

    public static List<Long> getAccounts() {
        return accounts;
    }
}
