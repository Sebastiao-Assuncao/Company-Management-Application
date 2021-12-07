package woo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Supplier implements Serializable, Comparable<Supplier> {

    private String _key;
    private String _name;
    private String _address;
    private boolean _state;
    private List<Order> _orders = new ArrayList<Order>();
    

    public Supplier(String supplierKey, String name, String address){
        _key = supplierKey;
        _name = name;
        _address = address;
        _state = true;
    }

    public String getKey(){
        return _key;
    }

    public String getName(){
        return _name;
    }

    public String getAddress(){
        return _address;
    }

    public boolean getState(){ return _state; }

    public List<Order> getOrders(){
        return _orders;
    }

    public void toggleState(){
        if (_state) _state = false;
        else _state = true;
    }

    public void addOrder(Order order){
        _orders.add(order);
    }

    @Override
    public int compareTo(Supplier s) {
        return _key.compareToIgnoreCase(s.getKey());
    }

    public String accept(Visitor v) {
        return v.visitSupplier(this);
    }
}
