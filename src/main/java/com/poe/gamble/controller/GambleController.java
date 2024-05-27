package com.poe.gamble.controller;

import com.poe.gamble.controller.response.CommonCode;
import com.poe.gamble.controller.response.CommonResponse;
import com.poe.gamble.dto.GambleDTO;
import com.poe.gamble.service.GambleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gamble")
public class GambleController {

    private final GambleService gambleService;
    @PostMapping("/try")
    public ResponseEntity<CommonResponse<GambleDTO.Response>> tryGambling(@RequestBody GambleDTO.Request gambleDTO) {
        final UUID userUUID = UUID.fromString("296dfdaa-9d9d-4250-9d93-f5ba56bf7f0e");
        return ResponseEntity.ok(CommonResponse.success(gambleService.tryGambling(userUUID,gambleDTO), CommonCode.FOUND_OK));
    }

}
