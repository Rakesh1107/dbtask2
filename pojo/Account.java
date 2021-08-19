package pojo;

public class Account {

    private int userId;
    private long accountNumber;
    private long balance = 0;
    private String branch;
    private long time;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", branch='" + branch + '\'' +
                '}';
    }

}
