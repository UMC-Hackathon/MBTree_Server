package com.mbtree.mbtree.domain.chat;

import java.security.Timestamp;
import java.time.LocalDateTime;

import com.mbtree.mbtree.config.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    private int quit; // 채팅 그만두기 여부

    private int answer1;
    private int answer2; // 퀴즈 정답 작성

    private int view1;
    private int view2; // 채팅방 목록 공개 여부

    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
