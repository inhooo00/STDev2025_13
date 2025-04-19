package shop.itcontest17.stdev2025_13.member.exception;

import shop.itcontest17.stdev2025_13.global.error.exception.InvalidGroupException;

public class ExistsNicknameException extends InvalidGroupException {
    public ExistsNicknameException(String message) {
        super(message);
    }

    public ExistsNicknameException() {
        this("이미 사용중인 닉네임 입니다.");
    }
}