package woo.exceptions;

public class UnknownProductKeyCoreException extends Exception {

    private String _key;

    public UnknownProductKeyCoreException(String _key) {
        this._key = _key;
    }

    public String getKey() {
        return this._key;
    }
}
