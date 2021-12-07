package woo.exceptions;

public class DuplicateSupplierKeyException extends Exception{

    private String _key;

    public DuplicateSupplierKeyException(String _key) {
        this._key = _key;
    }
}
