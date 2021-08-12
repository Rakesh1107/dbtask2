package exception;

public class BankException extends Exception {
    public BankException(String connecting_to_database_failed) {
        System.out.println(connecting_to_database_failed);
    }
}
