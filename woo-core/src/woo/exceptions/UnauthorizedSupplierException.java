package woo.exceptions;

public class UnauthorizedSupplierException extends Exception {

    private String _supplierKey;

    public UnauthorizedSupplierException(String _supplierKey) {
        this._supplierKey = _supplierKey;
    }
}
