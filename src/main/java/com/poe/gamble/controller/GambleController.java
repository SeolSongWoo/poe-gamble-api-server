package com.poe.gamble.controller;

import com.poe.gamble.aop.CurrentUserEmail;
import com.poe.gamble.controller.response.CommonCode;
import com.poe.gamble.controller.response.CommonResponse;
import com.poe.gamble.dto.GambleDTO;
import com.poe.gamble.service.GambleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gamble")
@Tag(name = "Gamble Controller" ,description = "겜블에 관한 API 엔드포인트")
public class GambleController {

    private final GambleService gambleService;
    @PostMapping("/try")
    public ResponseEntity<CommonResponse<GambleDTO.Response>> tryGambling(@CurrentUserEmail UUID userUUID, @RequestBody GambleDTO.Request gambleDTO) {
        return ResponseEntity.ok(CommonResponse.success(gambleService.tryGambling(userUUID,gambleDTO), CommonCode.FOUND_OK));
    }

}
