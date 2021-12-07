package woo.exceptions;

public class WrongSupplierException extends Exception{

    private String _supplierKey;

    public WrongSupplierException(String _supplierKey) {
        this._supplierKey = _supplierKey;
    }
}
