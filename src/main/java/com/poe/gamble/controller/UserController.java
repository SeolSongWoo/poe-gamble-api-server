package com.poe.gamble.controller;

import com.poe.gamble.controller.response.CommonCode;
import com.poe.gamble.controller.response.CommonResponse;
import com.poe.gamble.dto.UserCardDTO;
import com.poe.gamble.dto.UserDTO;
import com.poe.gamble.repository.UserRepository;
import com.poe.gamble.service.UserCardService;
import com.poe.gamble.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserCardService userCardService;
    @GetMapping("/{email}")
    public ResponseEntity<CommonResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(CommonResponse.success(userService.getUserByEmail(email), CommonCode.FOUND_OK));
    }

    @PostMapping("/create")
    public ResponseEntity<CommonResponse<String>> createUser(@Valid @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.ok(CommonResponse.success("OK", CommonCode.CREATE_OK));
    }

    @GetMapping("/cards")
    public ResponseEntity<CommonResponse<List<UserCardDTO>>> getUserCardsByUUID() {
        final UUID userUUID = UUID.fromString("296dfdaa-9d9d-4250-9d93-f5ba56bf7f0e");
        return ResponseEntity.ok(CommonResponse.success(userCardService.getUserCardsByUUID(userUUID), CommonCode.FOUND_OK));
    }

}
