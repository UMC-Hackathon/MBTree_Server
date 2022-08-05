package com.mbtree.mbtree.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Messages  {
    private int writerId;
    private int treeId;
    private String content;
    private int r; //read
    private LocalDateTime createDate;
    private int xPos;
    private int yPos;
    private int paperStyle;


}
