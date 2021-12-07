package woo;

public class SelectionState extends ClientState{

    public SelectionState(Client client) {
        super(client);
    }

    @Override
    public void delayed(int date, int paymentDeadline) {
        if(date - paymentDeadline > 2) {
            getClient().setPoints(getClient().getPoints() * 0.1);
            getClient().setStatus(new NormalState(getClient()));
        }
    }

    @Override
    public double discount (int _paymentDeadline, int date, double total, int priceLevel) {
        int extraDays = date -_paymentDeadline;

        if (priceLevel == 2) {
            if (_paymentDeadline - date >= 2)
                total = total * 0.95;   //5% discount
        }

        else if (priceLevel == 3) {
            if (extraDays > 1)
                total += extraDays * total * 0.02; //2% daily fine
        }

        else if (priceLevel == 4)
            total += extraDays * total * 0.05; //5% daily fine

        return  total;
    }

    @Override
    public String accept(Visitor v) {
        return v.visitClientState(this);
    }
}
