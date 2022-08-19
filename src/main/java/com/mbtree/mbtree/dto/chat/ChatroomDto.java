package com.mbtree.mbtree.dto.chat;

import com.mbtree.mbtree.domain.chat.Chat;
import com.mbtree.mbtree.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public  class ChatroomDto {
    int id;
    User user1;
    User user2;
    int quit; // 채팅 그만두기 여부

    public ChatroomDto(int id, User user1, User user2, int quit){
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.quit = quit;
    }
}

