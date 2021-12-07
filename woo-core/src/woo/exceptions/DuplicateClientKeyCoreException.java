package woo.exceptions;

public class DuplicateClientKeyCoreException extends Exception{

    private String _key;

    public DuplicateClientKeyCoreException(String _key) {
        this._key = _key;
    }

    public String getKey() {
        return this._key;
    }
}
