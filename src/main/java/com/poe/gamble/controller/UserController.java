package com.poe.gamble.controller;

import com.poe.gamble.controller.response.CommonCode;
import com.poe.gamble.controller.response.CommonResponse;
import com.poe.gamble.dto.UserDTO;
import com.poe.gamble.repository.UserRepository;
import com.poe.gamble.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{email}")
    public ResponseEntity<CommonResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(CommonResponse.success(userService.getUserByEmail(email), CommonCode.FOUND_OK));
    }
}
