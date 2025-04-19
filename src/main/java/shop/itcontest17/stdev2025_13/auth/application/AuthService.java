package shop.itcontest17.stdev2025_13.auth.application;

import shop.itcontest17.stdev2025_13.auth.api.dto.response.IdTokenResDto;
import shop.itcontest17.stdev2025_13.auth.api.dto.response.UserInfo;

public interface AuthService {
    UserInfo getUserInfo(String authCode);

    String getProvider();

    IdTokenResDto getIdToken(String code);
}
