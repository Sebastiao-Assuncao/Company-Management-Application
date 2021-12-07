package woo;

import java.util.LinkedHashMap;
import java.util.Map;

public class Order extends Transaction{

    private int _totalCost;
    private Map <Product, Integer> _products = new LinkedHashMap<>();
    private String _supplierKey;
    private int _paymentDate;

    /**
     *  @param supplierKey
     * @param date
     * @param id
     */
    public Order(String supplierKey, int date, int id){
        super(id);
        _supplierKey = supplierKey;
        _paymentDate = date;
    }

    public String getSupplierKey(){
        return _supplierKey;
    }

    public int getTotalCost(){
        return _totalCost;
    }

    public int getPaymentDate() {return _paymentDate;}

    public Map<Product, Integer> getProducts(){
        return _products;
    }

    public void calculateOrderCost(){
        for(Map.Entry<Product, Integer> entry : _products.entrySet())
            _totalCost += entry.getKey().getPrice() * entry.getValue();
    }

    @Override
    public String accept(Visitor v) {
        return v.visitTransaction(this);
    }
}