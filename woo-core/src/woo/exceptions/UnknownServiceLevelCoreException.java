package woo.exceptions;

public class UnknownServiceLevelCoreException extends Exception{

    private String _serviceLevel;

    public UnknownServiceLevelCoreException(String _serviceLevel) {
        this._serviceLevel = _serviceLevel;
    }

    public String getServiceLevel() {
        return this._serviceLevel;
    }
}
