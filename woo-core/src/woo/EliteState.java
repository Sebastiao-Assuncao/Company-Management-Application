package woo;

public class EliteState extends ClientState{

    public EliteState(Client client) {
        super(client);
    }

    @Override
    public void delayed(int date, int paymentDeadline) {
        if (date - paymentDeadline > 15) {
            getClient().setPoints(getClient().getPoints() * 0.25);
            getClient().setStatus(new SelectionState(getClient()));
        }
    }

    @Override
    public double discount (int _paymentDeadline, int date, double total, int priceLevel) {
        if (priceLevel == 2)
            return total * 0.9;

        else if (priceLevel == 3)
            return total * 0.95;

        return total;
    }

    @Override
    public String accept (Visitor v) {
        return v.visitClientState(this);
    }
}
