package shop.itcontest17.stdev2025_13.global.error.exception;

public abstract class NotFoundGroupException extends RuntimeException{
    public NotFoundGroupException(String message) {
        super(message);
    }
}