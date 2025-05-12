package com.website.loveconnect.controller.user;

import com.website.loveconnect.dto.request.ProfileDetailRequest;
import com.website.loveconnect.dto.request.UserCreateRequest;
import com.website.loveconnect.dto.response.ApiResponse;
import com.website.loveconnect.dto.response.ProfileDetailResponse;
import com.website.loveconnect.dto.response.UserAndPhotosResponse;
import com.website.loveconnect.dto.response.UserSearchResponse;
import com.website.loveconnect.service.ImageService;
import com.website.loveconnect.service.LikeService;
import com.website.loveconnect.service.UserProfileService;
import com.website.loveconnect.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/api")
public class UserProfileController {
    UserService userService;
    ImageService imageService;
    UserProfileService userProfileService;
    LikeService likeService;
    //tạo tài khoản người dùng
    @PostMapping(value = "/sign-up")
    public ResponseEntity<ApiResponse<String>> signUpAccount(@RequestBody UserCreateRequest userCreateRequest){
        userService.createUser(userCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true,"Create account successful", null));
    }



    //lấy thông tin chi tiết người dùng
    @GetMapping(value = "/user-profile/{userId}")
    public ResponseEntity<ApiResponse<ProfileDetailResponse>> getUserProfile(
            @PathVariable("userId") Integer userId){
        return ResponseEntity.ok(new ApiResponse<>(true,"Get user profile successful",
                userProfileService.getProfileDetail(userId)));
    }

    //cập nhật thông tin chi tiết người dùng
    @PutMapping(value = "/user-profile/{userId}/update")
    public ResponseEntity<ApiResponse<String>> updateUserProfile(
            @PathVariable("userId") Integer userId,
            @RequestBody ProfileDetailRequest profileDetailRequest){
        userProfileService.updateProfileDetail(userId,profileDetailRequest);
        return ResponseEntity.ok(new ApiResponse<>(true,"Update user profile successful",null));
    }

    //xóa người dùng
    @DeleteMapping(value = "/user-profile/{userId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true,"Delete account successful",null));
    }

    @GetMapping(value = "/user-profile/search")
    public ResponseEntity<ApiResponse<Page<UserSearchResponse>>> searchUserByKeyword(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ApiResponse<>(true,"Search users successful",
                userService.getAllUserByKeyword(keyword,page,size)));
    }

    @PostMapping(value = "/user-profile/{senderId}/like/{receivedId}")
    public ResponseEntity<ApiResponse<String>> likeUser(@PathVariable int senderId,
                                                        @PathVariable int receivedId){
        likeService.likeUserById(senderId,receivedId);
        return ResponseEntity.ok(new ApiResponse<>(true,"Like user successful",null));
    }

    @PostMapping(value = "/user-profile/{senderId}/dislike/{receivedId}")
    public ResponseEntity<ApiResponse<String>> dislikeUser(@PathVariable int senderId,
                                                        @PathVariable int receivedId){
        likeService.dislikeUserById(senderId,receivedId);
        return ResponseEntity.ok(new ApiResponse<>(true,"Dislike user successful",null));
    }

    @GetMapping(value = "/random-user-photos/{userId}")
    public ResponseEntity<ApiResponse<Page<UserAndPhotosResponse>>> getUserAndPhotos(@PathVariable("userId") Integer userId,
                                                                                     @RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ApiResponse<>(true,"Get page user and photos successful",
                userService.getAllUsersAndPhotos(page,size,userId)));
    }

}
