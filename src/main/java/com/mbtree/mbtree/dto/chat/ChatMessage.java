package com.mbtree.mbtree.dto.chat;

public class ChatMessage {

    private String userId;
    private String message;
    private MessageType messageType;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" + "userId='" + userId + '\'' + ", message='" + message + '\'' + ", messageType=" + messageType + '}';
    }
}