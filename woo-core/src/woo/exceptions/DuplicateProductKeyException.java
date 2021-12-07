package woo.exceptions;

public class DuplicateProductKeyException extends Exception {

    private String _key;
    public DuplicateProductKeyException(String key) {
        this._key = key;
    }
}
