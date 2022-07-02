package com.mbtree.mbtree.dto;

import lombok.Builder;

@Builder
public class Post {
    private int writerID;
    private int treeID;
    private String content;


}
