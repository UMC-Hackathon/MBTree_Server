package com.mbtree.mbtree.dto.chat;

import java.util.Objects;

public class ChatRequest {

    private String userId;

    public ChatRequest() {
    }

    public ChatRequest(String sessionId) {
        this.userId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setSessionId(String sessionId) {
        this.userId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatRequest)) {
            return false;
        }

        ChatRequest that = (ChatRequest) o;
        return Objects.equals(this.userId, that.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public String toString() {
        return "ChatRequest{" + "userId='" + userId + '\'' + '}';
    }
}