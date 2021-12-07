package woo.exceptions;

public class UnknownSupplierKeyException extends Exception{

    private String _key;

    public UnknownSupplierKeyException(String _key) {
        this._key = _key;
    }
}
