package woo.exceptions;

public class UnknownTransactionKeyCoreException extends Exception {

    private int _key;

    public UnknownTransactionKeyCoreException(int _key) {
        this._key = _key;
    }

    public int getKey() {
        return this._key;
    }
}
