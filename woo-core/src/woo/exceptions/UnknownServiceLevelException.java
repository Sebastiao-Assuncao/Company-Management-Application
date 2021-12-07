package woo.exceptions;

public class UnknownServiceLevelException extends Exception{

    private String _serviceLevel;

    public UnknownServiceLevelException(String _serviceLevel) {
        this._serviceLevel = _serviceLevel;
    }
}
