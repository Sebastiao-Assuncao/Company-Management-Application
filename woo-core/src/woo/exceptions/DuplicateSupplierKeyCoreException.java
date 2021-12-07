package woo.exceptions;

public class DuplicateSupplierKeyCoreException extends Exception{

    private String _key;

    public DuplicateSupplierKeyCoreException(String _key) {
        this._key = _key;
    }

    public String getKey() {
        return this._key;
    }
}
