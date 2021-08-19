package logic;

import exception.BankException;

public enum Initiator {
    INSTANCE;
    public void initiate() throws BankException {
        Mediator mediator = Mediator.INSTANCE;
        mediator.load();
    }
}
