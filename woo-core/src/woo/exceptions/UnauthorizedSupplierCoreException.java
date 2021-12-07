package woo.exceptions;

public class UnauthorizedSupplierCoreException extends Exception {

    private String _supplierKey;

    public UnauthorizedSupplierCoreException(String _supplierKey) {
        this._supplierKey = _supplierKey;
    }

    public String getKey() {
        return this._supplierKey;
    }
}
