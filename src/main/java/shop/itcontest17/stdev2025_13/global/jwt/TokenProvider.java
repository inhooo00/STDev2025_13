package shop.itcontest17.stdev2025_13.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shop.itcontest17.stdev2025_13.auth.api.dto.request.TokenReqDto;
import shop.itcontest17.stdev2025_13.global.jwt.api.dto.TokenDto;

@Slf4j
@Getter
@Component
@NoArgsConstructor
public class TokenProvider {

    @Value("${token.expire.time.access}")
    private String accessTokenExpireTime;

    @Value("${token.expire.time.refresh}")
    private String refreshTokenExpireTime;

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    public TokenProvider(String accessTokenExpireTime, String refreshTokenExpireTime, String secret, Key key) {
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
        this.secret = secret;
        this.key = key;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = hexStringToByteArray(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserEmailFromToken(TokenReqDto tokenReqDto) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(tokenReqDto.authCode())
                .getBody();
        return claims.getSubject(); // 토큰의 subject를 사용자 ID로 간주
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            log.error("JWT is not valid");
        } catch (SignatureException exception) {
            log.error("JWT signature validation fails");
        } catch (ExpiredJwtException exception) {
            log.error("JWT expired");
        } catch (IllegalArgumentException exception) {
            log.error("JWT is null or empty or only whitespace");
        } catch (Exception exception) {
            log.error("JWT validation fails", exception);
        }

        return false;
    }

    public TokenDto generateToken(String email) {
        String accessToken = generateAccessToken(email);
        String refreshToken = generateRefreshToken();

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDto generateAccessTokenByRefreshToken(String email, String refreshToken) {
        String accessToken = generateAccessToken(email);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String generateAccessToken(String email) {
        Date date = new Date();
        Date accessExpiryDate = new Date(date.getTime() + Long.parseLong(accessTokenExpireTime));

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(date)
                .setExpiration(accessExpiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken() {
        Date date = new Date();
        Date refreshExpiryDate = new Date(date.getTime() + Long.parseLong(refreshTokenExpireTime));

        return Jwts.builder()
                .setExpiration(refreshExpiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private byte[] hexStringToByteArray(String secret) {
        int len = secret.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(secret.charAt(i), 16) << 4)
                    + Character.digit(secret.charAt(i + 1), 16));
        }
        return data;
    }
}