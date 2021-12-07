package woo.exceptions;

public class UnknownServiceTypeException extends Exception{

    private String _serviceType;

    public UnknownServiceTypeException(String _serviceType) {
        this._serviceType = _serviceType;
    }
}
