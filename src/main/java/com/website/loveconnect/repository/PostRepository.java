package com.website.loveconnect.repository;

import com.website.loveconnect.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
