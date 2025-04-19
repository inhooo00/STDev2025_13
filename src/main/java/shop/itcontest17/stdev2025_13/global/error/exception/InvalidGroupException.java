package shop.itcontest17.stdev2025_13.global.error.exception;

public abstract class InvalidGroupException extends RuntimeException{
    public InvalidGroupException(String message) {
        super(message);
    }
}