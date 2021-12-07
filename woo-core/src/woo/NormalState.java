package woo;

public class NormalState extends ClientState{

    public NormalState(Client client) {
        super(client);
    }

    @Override
    public void delayed(int date, int paymentDeadline) {}

    @Override
    public double discount (int _paymentDeadline, int date, double total, int priceLevel) {
        int extraDays = date -_paymentDeadline;

        if (priceLevel == 3)
            total += extraDays * total * 0.05; //5% daily fine

        else if (priceLevel == 4)
            total += extraDays * total * 0.1; //10% daily fine

        return total;
    }

    @Override
    public String accept (Visitor v) {
        return v.visitClientState(this);
    }
}
