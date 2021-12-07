package woo.exceptions;

public class UnknownClientKeyException extends Exception{

    private String _key;

    public UnknownClientKeyException(String _key) {
        this._key = _key;
    }
}
