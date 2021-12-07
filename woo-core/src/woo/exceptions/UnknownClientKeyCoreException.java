package woo.exceptions;

public class UnknownClientKeyCoreException extends Exception{

    private String _key;

    public UnknownClientKeyCoreException(String _key) {
        this._key = _key;
    }

    public String getKey() {
        return this._key;
    }
}
