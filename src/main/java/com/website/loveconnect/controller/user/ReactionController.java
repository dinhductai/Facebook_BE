package com.website.loveconnect.controller.user;

import com.website.loveconnect.dto.request.ReactionRequest;
import com.website.loveconnect.dto.response.ApiResponse;
import com.website.loveconnect.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReactionController {
    ReactionService reactionService;

    @Operation(summary = "Create reaction",description = "User make a reaction on a post, video, photo")
    @PostMapping(value = "/reaction/create")
    public ResponseEntity<ApiResponse<String>> createReaction(@AuthenticationPrincipal Jwt jwt,
                                                              @RequestBody ReactionRequest reactionRequest) {
        Integer userId = Integer.parseInt(jwt.getSubject());
        reactionService.addReaction(reactionRequest,userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true,"Create reaction successful",null));
    }


    @Operation(summary = "Count reaction",description = "Count reaction on a post")
    @GetMapping(value = "/reaction/cnt")
    public ResponseEntity<ApiResponse<Long>> countReactionOnAPost(@RequestParam Integer postId) {
        return ResponseEntity.ok(new ApiResponse<>(true,"Count reaction successful",
                reactionService.countReactionOnAPost(postId)));
    }
}
