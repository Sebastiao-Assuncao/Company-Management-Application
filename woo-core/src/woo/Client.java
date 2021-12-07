package woo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Client implements Serializable, Comparable<Client> {

    private String _key;
    private String _name;
    private String _address;
    private ClientState _status;
    private double _points;
    private List<Sale> _purchases = new ArrayList<>();
    private List<Notification> _notificationsList = new ArrayList<>();

    /**
     *
     * @param key
     * @param name
     * @param address
     */
    public Client (String key, String name, String address) {
        _key = key;
        _name = name;
        _address = address;
        _status = new NormalState(this);
        _points = 0;
    }

    public String getKey() {
        return _key;
    }

    public String getName() {
        return _name;
    }

    public String getAddress() {
        return _address;
    }

    public List<Sale> getPurchases(){
        return _purchases;
    }

    public double getPoints (){
        return _points;
    }

    public void setPoints (double points) {
        _points = points;
    }

    public ClientState getStatus(){
        return _status;
    }

    public List<Notification> getNotificationsList() {
        return _notificationsList;
    }

    public double getPaidSalesValue() {
        double value = 0;
        for (Sale p : _purchases)
            if (p.isPaid())
                value += p.getCurrentCost();
        return value;
    }

    public double getSalesValue() {
        double value = 0;
        for (Sale p : _purchases)
            value += p.getBaseCost();
        return value;
    }

    public void setStatus (ClientState status) {
        _status = status;
    }

    public void paySale (int currentCost, int paymentDeadline, int date, int priceLevel) {
        if(priceLevel <=2){
            _points += currentCost * 10;
            determineStatus();
        }
        else
            _status.delayed(date, paymentDeadline);
    }

    private void determineStatus(){
        if(_points <= 2000)
            setStatus(new NormalState(this));

        else if(_points < 25000)
            setStatus(new SelectionState(this));
            
        else
            setStatus(new EliteState(this));
    }

    public void addPurchase(Sale sale){
        _purchases.add(sale);
    }

    public void addNotification(Notification notification){
        _notificationsList.add(notification);
    }

    public void clearNotifications () {
        _notificationsList.clear();
    }

    @Override
    public int compareTo(Client c) {
        return _key.compareToIgnoreCase(c.getKey());
    }

    public String accept(Visitor v) {
        return v.visitClient(this);
    }
}