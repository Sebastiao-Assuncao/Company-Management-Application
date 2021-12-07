package woo.exceptions;

public class UnknownServiceTypeCoreException extends Exception{

    private String _serviceType;

    public UnknownServiceTypeCoreException(String _serviceType) {
        this._serviceType = _serviceType;
    }

    public String getServiceType() {
        return this._serviceType;
    }
}
