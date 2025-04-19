package shop.itcontest17.stdev2025_13.auth.exception;

import shop.itcontest17.stdev2025_13.global.error.exception.AuthGroupException;

public class InvalidTokenException extends AuthGroupException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException() {
        this("토큰이 유효하지 않습니다.");
    }
}
