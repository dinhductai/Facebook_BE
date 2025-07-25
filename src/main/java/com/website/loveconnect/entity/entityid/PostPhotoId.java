package com.website.loveconnect.entity.entityid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPhotoId implements Serializable {
    private Integer post;
    private Integer photo;
}
