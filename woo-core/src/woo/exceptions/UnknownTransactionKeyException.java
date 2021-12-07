package woo.exceptions;

public class UnknownTransactionKeyException extends Exception {

    private int _key;

    public UnknownTransactionKeyException(int _key) {
        this._key = _key;
    }
}
