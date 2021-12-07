package woo.exceptions;

public class UnknownSupplierKeyCoreException extends Exception{

    private String _key;

    public UnknownSupplierKeyCoreException(String _key) {
        this._key = _key;
    }

    public String getKey() {
        return this._key;
    }
}
