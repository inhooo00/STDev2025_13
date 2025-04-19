package shop.itcontest17.stdev2025_13.global.error.exception;

public abstract class AccessDeniedGroupException extends RuntimeException{
    public AccessDeniedGroupException(String message) {
        super(message);
    }
}