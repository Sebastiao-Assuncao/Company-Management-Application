package woo;

public class Container extends Box{

    private String _serviceLevel;

    /**
     *  @param key
     * @param price
     * @param criticalValue
     * @param supplierKey
     * @param serviceType
     * @param servicelevel
     */
    public Container(String key, int price, int criticalValue, String supplierKey, String serviceType, String servicelevel, int amount) {
        super(key, price, criticalValue, supplierKey, serviceType, amount, 8);
        _serviceLevel = servicelevel;
    }

    public String getServiceLevel() {
        return _serviceLevel;
    }

    @Override
    public String accept(Visitor v) { return v.visitContainer(this);}
}