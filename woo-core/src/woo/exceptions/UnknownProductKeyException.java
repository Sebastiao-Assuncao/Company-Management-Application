package woo.exceptions;

public class UnknownProductKeyException extends Exception {

    private String _key;

    public UnknownProductKeyException(String _key) {
        this._key = _key;
    }
}
