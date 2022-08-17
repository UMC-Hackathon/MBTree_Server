package com.mbtree.mbtree.domain.chat;

import java.time.LocalDateTime;

import com.mbtree.mbtree.config.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Chatroom implements Serializable {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String user1;
    private String user2;

    private String quiz;

    private int quit;

    @CreatedDate
    private LocalDateTime createDate;
}
