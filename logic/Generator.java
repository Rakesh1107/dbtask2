package logic;

public class Generator {

    public static long generateAccountNumber() {
        long accountNumber = (long) (Math.random() * 10000000 + 10000000);
        while (DataStorage.getAccounts().contains(accountNumber)) {
            accountNumber = (long) (Math.random() * 10000000 + 10000000);
        }
        return accountNumber;
    }

    public static int generateUserId() {
        int userId = (int) (Math.random() * 999999 + 100000);
        while (DataStorage.getData().containsKey(userId)) {
            userId = (int) (Math.random() * 999999 + 100000);
        }
        return userId;
    }
}
