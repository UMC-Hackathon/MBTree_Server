package com.mbtree.mbtree.dto;

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
public class Post implements Serializable {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(name = "id")
    private int id;
    private String content;
    private Float xPos;
    private Float yPos;

    @ManyToOne
    @JoinColumn(name = "writerId")
    private User writerId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;

}
