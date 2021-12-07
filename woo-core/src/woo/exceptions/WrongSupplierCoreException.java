package woo.exceptions;

public class WrongSupplierCoreException extends Exception{

    private String _supplierKey;
    private String _productKey;

    public WrongSupplierCoreException(String supplierKey, String productKey) {

        _supplierKey = supplierKey;
        _productKey = productKey;

    }

    public String getSupplierKey() {
        return _supplierKey;
    }

    public String getProductKey() { return _productKey;}
}
