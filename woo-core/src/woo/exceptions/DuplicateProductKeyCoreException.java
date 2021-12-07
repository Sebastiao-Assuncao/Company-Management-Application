package woo.exceptions;

public class DuplicateProductKeyCoreException extends Exception {

    private String _key;
    public DuplicateProductKeyCoreException(String key) {
        this._key = key;
    }

    public String getKey() {
        return this._key;
    }
}
