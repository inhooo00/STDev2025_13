package shop.itcontest17.stdev2025_13.auth.exception;

import shop.itcontest17.stdev2025_13.global.error.exception.NotFoundGroupException;

public class EmailNotFoundException extends NotFoundGroupException {
    public EmailNotFoundException(String message) {
        super(message);
    }

    public EmailNotFoundException() {
        this("존재하지 않는 이메일 입니다.");
    }
}
