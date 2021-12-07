package woo.exceptions;

public class DuplicateClientKeyException extends Exception{

    private String _key;

    public DuplicateClientKeyException(String _key) {
        this._key = _key;
    }
}
