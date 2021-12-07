package woo;


public class Box extends Product{
    
    private String _serviceType;

    /**
     *  @param key
     * @param price
     * @param criticalValue
     * @param supplierKey
     * @param serviceType
     */

    public Box(String key, int price, int criticalValue, String supplierKey, String serviceType, int amount, int N) {
        super(key, price, criticalValue, supplierKey, amount, N);
        _serviceType = serviceType;
    }

    public Box(String key, int price, int criticalValue, String supplierKey, String serviceType, int amount) {
        this(key, price, criticalValue, supplierKey, serviceType, amount, 5);
    }

    public String getServiceType() {
        return _serviceType;
    }

    @Override
    public String accept(Visitor v) { return v.visitBox(this);}

}