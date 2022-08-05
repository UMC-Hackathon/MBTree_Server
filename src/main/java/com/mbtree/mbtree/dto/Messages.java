package com.mbtree.mbtree.dto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Messages {
    private String writerId;
    private String treeId;
    private String content;
    private int r; //read
    private String createDate;
    private int xPos;
    private int yPos;
    private int paperStyle;


}
