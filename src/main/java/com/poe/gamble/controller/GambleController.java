package com.poe.gamble.controller;

import com.poe.gamble.controller.response.CommonCode;
import com.poe.gamble.controller.response.CommonResponse;
import com.poe.gamble.dto.GambleDTO;
import com.poe.gamble.service.GambleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gamble")
public class GambleController {

    private final GambleService gambleService;
    @PostMapping("/cards/{cardName}/stock/{stockQuantity}")
    public ResponseEntity<CommonResponse<GambleDTO.Response>> tryGambling(@RequestBody GambleDTO.Request gambleDTO) {
        final String userUUID = "3244324-324234-432432";
        return ResponseEntity.ok(CommonResponse.success(gambleService.tryGambling(userUUID,gambleDTO), CommonCode.FOUND_OK));
    }

}
