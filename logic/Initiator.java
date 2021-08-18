package logic;

import exception.BankException;

public class Initiator {
    public static void initiate() throws BankException {
        Mediator mediator = new Mediator();
        mediator.load();
    }
}
