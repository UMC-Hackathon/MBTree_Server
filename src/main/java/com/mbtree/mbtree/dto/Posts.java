package com.mbtree.mbtree.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Posts {
    private String writerId;
    private String userId;
    private String content;
}
