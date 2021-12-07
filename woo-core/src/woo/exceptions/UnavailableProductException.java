package woo.exceptions;

public class UnavailableProductException extends Exception{

    private String _productKey;

    public UnavailableProductException(String _productKey) {
        this._productKey = _productKey;
    }
}
