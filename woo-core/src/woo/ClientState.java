package woo;

import java.io.Serializable;

public abstract class ClientState implements Serializable {
    private Client _client;

    public ClientState(Client client) {
        _client = client;
    }

    public Client getClient() {
        return _client;
    }

    public abstract void delayed(int date, int paymentDeadline);

    public abstract double discount (int _paymentDeadline, int date, double total, int priceLevel);

    public abstract String accept(Visitor v);

}
