package exception;

public class BankException extends Throwable {
    public BankException(String message) {
        super(message);
    }
    public BankException(String message, Exception exception) {
        super(message,exception);
    }
}
