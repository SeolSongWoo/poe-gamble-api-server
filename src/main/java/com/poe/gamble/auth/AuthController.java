package com.poe.gamble.auth;

import com.poe.gamble.controller.response.CommonCode;
import com.poe.gamble.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/login")
    public ResponseEntity<CommonResponse<JwtUtil.TokenDTO>> createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequest) {
        final JwtUtil.TokenDTO jwtToken = authService.getJwtToken(authenticationRequest);

        return ResponseEntity.ok(CommonResponse.success(jwtToken, CommonCode.LOGIN_OK));
    }
}
