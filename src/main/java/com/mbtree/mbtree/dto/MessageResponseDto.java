package com.mbtree.mbtree.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponseDto {
    private int writerId;
    private int treeId;
    private String content;
    private int isRead; //read
    private LocalDateTime createDate;
    private int paperStyle;


}
