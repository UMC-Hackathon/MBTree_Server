package com.mbtree.mbtree.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserResponseDto {
    private String uuid;
    private String name;
    private String email;
    private String mbti;
    private String location;
    private String token;
    private int point;
   // private LocalDateTime createDate;

}
