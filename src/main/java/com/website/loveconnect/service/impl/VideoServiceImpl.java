package com.website.loveconnect.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.website.loveconnect.entity.*;
import com.website.loveconnect.exception.UserNotFoundException;
import com.website.loveconnect.repository.PhotoRepository;
import com.website.loveconnect.repository.PostVideoRepository;
import com.website.loveconnect.repository.UserRepository;
import com.website.loveconnect.repository.VideoRepository;
import com.website.loveconnect.service.VideoService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class VideoServiceImpl implements VideoService {
    Cloudinary cloudinary;
    UserRepository userRepository;
    VideoRepository videoRepository;
    PostVideoRepository postVideoRepository;

    private String saveVideo(MultipartFile file, String userEmail,Post post) throws IOException{
        if (file == null || file.isEmpty()) {
            log.warn("Attempt to upload empty file for user: {}", userEmail);
            throw new IllegalArgumentException("Video cannot be blank");
        }
        if (StringUtils.isEmpty(userEmail)) { //bắt validation email
            throw new IllegalArgumentException("User email cannot be blank");
        }
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap("resource_type", "video"));
        } catch (IOException ioe) {
            log.error("Failed to upload video to Cloudinary", ioe.getMessage());
            throw ioe;
        }
        String videoUrl = (String) uploadResult.get("url");

        User user = userRepository.getUserByEmail(userEmail)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        Video video = new Video();
        video.setVideoUrl(videoUrl);
        video.setUploadDate(new Timestamp(System.currentTimeMillis()));
        video.setIsApproved(true);
        video.setIsStatus(false);
        video.setOwnedVideo(user);
        if(post!= null){
            PostVideo postVideo = PostVideo.builder()
                    .video(video)
                    .post(post)
                    .build();
            postVideoRepository.save(postVideo);
        }
        try {
            videoRepository.save(video);
            log.info("Saved image profile successfully");
        } catch (DataAccessException dae) {
            log.error("Failed to save image profile", dae.getMessage());
            throw new DataAccessException("Failed to save photo to database", dae) {
            };
        }
        return videoUrl;

    }

    @Override
    public String uploadVideo(MultipartFile file, String userEmail) throws IOException {
       return saveVideo(file,userEmail,null);
    }

    @Override
    public List<String> getOwnedVideos(Integer idUser) {
        return List.of();
    }

    @Override
    public void deleteVideo(Integer idUser, String urlImage) {

    }

    @Override
    public String uploadVideoForPost(MultipartFile file, String userEmail, Post post) throws IOException {
        return saveVideo(file, userEmail,post);
    }
}
