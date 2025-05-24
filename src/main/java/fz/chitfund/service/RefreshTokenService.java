package fz.chitfund.service;

import fz.chitfund.entity.RefreshToken;
import fz.chitfund.entity.Users;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public interface RefreshTokenService {
    RefreshToken generateRefreshToken(Users user);
    RefreshToken validateRefreshToken(String token);
    void deleteRefreshToken(String token);
    void deleteRefreshTokenByUserId(Long userId);
}
