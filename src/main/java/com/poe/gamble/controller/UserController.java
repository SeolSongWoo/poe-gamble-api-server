package com.poe.gamble.controller;

import com.poe.gamble.aop.CurrentUserEmail;
import com.poe.gamble.controller.response.CommonCode;
import com.poe.gamble.controller.response.CommonResponse;
import com.poe.gamble.dto.UserCardDTO;
import com.poe.gamble.dto.UserDTO;
import com.poe.gamble.repository.UserRepository;
import com.poe.gamble.service.UserCardService;
import com.poe.gamble.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller" ,description = "유저 정보에 관한 API 엔드포인트")
public class UserController {
    private final UserService userService;
    private final UserCardService userCardService;
    @GetMapping("/{email}")
    public ResponseEntity<CommonResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(CommonResponse.success(userService.getUserByEmail(email), CommonCode.FOUND_OK));
    }

    @PostMapping("/create")
    @Operation(summary = "계정 회원가입", description = "회원가입 API")
    public ResponseEntity<CommonResponse<String>> createUser(@Valid @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.ok(CommonResponse.success("OK", CommonCode.CREATE_OK));
    }

    @GetMapping("/cards")
    @Operation(summary = "특정 유저의 카드정보", description = "특정 유저의 카드 정보 API")
    public ResponseEntity<CommonResponse<List<UserCardDTO>>> getUserCardsByUUID(@CurrentUserEmail UUID userUUID) {
        return ResponseEntity.ok(CommonResponse.success(userCardService.getUserCardsByUUID(userUUID), CommonCode.FOUND_OK));
    }

}
