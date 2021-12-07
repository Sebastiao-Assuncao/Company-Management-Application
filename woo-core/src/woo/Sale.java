package woo;

public class Sale extends Transaction{

    private int _paymentDeadline;
    private double _baseCost;
    private Product _product;
    private int _amount;
    private double _currentCost;
    private boolean _isPaid;
    private Client _client;
    private int _paymentDate;

    /**
     *  @param client
     * @param paymentDeadline
     * @param product
     * @param amount
     * @param id
     */
    public Sale(Client client, int paymentDeadline, Product product, int amount, int id){
        super(id);
        _paymentDeadline = paymentDeadline;
        _product = product;
        _amount = amount;
        _client = client;
        _isPaid = false;
        _baseCost = product.getPrice() * amount;
    }

    public int getPaymentDeadline() {
        return _paymentDeadline;
    }

    public double getBaseCost() {
        return _baseCost;
    }

    public Product getProduct(){
        return _product;
    }

    public int getAmount(){
        return _amount;
    }

    public boolean isPaid(){
        return _isPaid;
    }

    public Client getClient(){
        return _client;
    }

    public double getCurrentCost() {return _currentCost;}

    public int getPaymentDate() {return _paymentDate;}

    public void paySale(int date) {
        int priceLevel = calculatePriceLevel(date);
        applyDiscountsAndFines(date);
        _client.paySale((int)_currentCost, _paymentDeadline, date, priceLevel);
        _paymentDate = date;
        _isPaid = true;
    }

    public void applyDiscountsAndFines(int date){
        double total = _baseCost;
        ClientState clientStatus = _client.getStatus();
        int priceLevel = calculatePriceLevel(date);

        if(priceLevel == 1)
            total = total * 0.9;  //10% discount
        else
            total = clientStatus.discount(_paymentDeadline, date, total, priceLevel);

        _currentCost = total;
    }

    private int calculatePriceLevel(int date){
        int N = _product.getN();
        if(_paymentDeadline - date >= N)
            return 1;
        else if (0 <= (_paymentDeadline - date) && (_paymentDeadline - date) < N)
            return 2;
        else if (0 < date - _paymentDeadline && date - _paymentDeadline <= N)
            return 3;
        else if (date - _paymentDeadline > N)
            return 4;
        else
            return 0;
    }

    @Override
    public String accept(Visitor v) {
        return v.visitTransaction(this);
    }
}