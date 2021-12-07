package woo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Product implements Serializable, Comparable<Product>{

    private String _key;
    private int _price;
    private int _stockCriticalValue;
    private int _amount;
    private int _N;
    private String _supplierKey;
    private List<Client> _interestedClientList = new ArrayList<>();

    /**
     *  @param key
     * @param price
     * @param criticalValue
     * @param supplierKey
     */
    public Product(String key, int price, int criticalValue, String supplierKey, int amount, int n) {
        _key = key;
        _price = price;
        _stockCriticalValue = criticalValue;
        _supplierKey = supplierKey;
        _amount = amount;
        _N = n;
    }

    public String getKey() {
        return _key;
    }

    public int getPrice() {
        return _price;
    }

    public int getStockCriticalValue() {
        return _stockCriticalValue;
    }

    public int getAmount() {
        return _amount;
    }

    public String getSupplierKey() {
        return _supplierKey;
    }

    public int getN() {
        return _N;
    }
    
    public boolean toggleClientInterest(Client client){
        if (_interestedClientList.contains(client)) {
            _interestedClientList.remove(client);
            return false;
        }
        _interestedClientList.add(client);
        return true;
    }

    public void changePrice (int newPrice) {
        int old_price = _price;
        _price = newPrice;

        if(_price < old_price){
            Notification n = new Notification(_key, "BARGAIN", _price);
            for (Client c : _interestedClientList)
                c.addNotification(n);
        }
    }

    public void changeAmount (int change) {
        int old_amount = _amount;
        _amount += change;

        if(old_amount == 0){
            Notification n = new Notification(_key, "NEW", _price);
            for (Client c : _interestedClientList)
                c.addNotification(n);
        }
    }

    @Override
    public int compareTo(Product p) {
        return _key.compareToIgnoreCase(p.getKey());
    }

    public abstract String accept(Visitor v);
}