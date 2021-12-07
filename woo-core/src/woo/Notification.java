package woo;

import java.io.Serializable;

public class Notification implements Serializable {

    private String _productKey;
    private DeliveryMethod _delivery = new AppDelivery(this);
    private String _description;
    private int _productPrice;

    /**
     *
     * @param productKey
     * @param description
     * @param productPrice
     */

    public Notification(String productKey, String description, int productPrice){
        _productKey = productKey;
        _description = description;
        _productPrice = productPrice;
    }

    public Notification(String productKey, String description, int productPrice, DeliveryMethod delivery){
        this(productKey, description, productPrice);
        _delivery = delivery;
    }


    public String getProductKey(){
        return _productKey;
    }

    public String getDescription(){
        return _description;
    }

    public int getProductPrice(){
        return _productPrice;
    }

    public String accept(Visitor v) { return v.visitNotification(this);}
}