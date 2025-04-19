package shop.itcontest17.stdev2025_13.process.exception;

import shop.itcontest17.stdev2025_13.global.error.exception.NotFoundGroupException;

public class ProcessesNotFoundException extends NotFoundGroupException {
    public ProcessesNotFoundException(String message) {
        super(message);
    }

    public ProcessesNotFoundException() {
        this("존재하지 않는 프로세스입니다");
    }
}