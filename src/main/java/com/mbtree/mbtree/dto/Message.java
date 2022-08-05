package com.mbtree.mbtree.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String content;
    private LocalDateTime createDate;
    private int xPos;
    private int yPos;
    private int r; //read
    private int paperStyle;
    @ManyToOne
    @JoinColumn(name = "writerId")
    private User writerId;

    @ManyToOne
    @JoinColumn(name = "treeId")
    private User treeId;

}
