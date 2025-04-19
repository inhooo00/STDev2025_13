package shop.itcontest17.stdev2025_13.global.error.exception;

public abstract class AuthGroupException extends RuntimeException{
    public AuthGroupException(String message) {
        super(message);
    }
}