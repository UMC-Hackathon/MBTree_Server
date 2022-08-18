package com.mbtree.mbtree.dto.chat;

public class ChatResponse {

    private ResponseResult responseResult;
    private String chatRoomId;
    private String userId;

    public ChatResponse() {
    }

    public ChatResponse(ResponseResult responseResult, String chatRoomId, String userId) {
        this.responseResult = responseResult;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public ResponseResult getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(ResponseResult responseResult) {
        this.responseResult = responseResult;
    }

    @Override
    public String toString() {
        return "ChatResponse{" + "responseResult=" + responseResult + ", chatRoomId='" + chatRoomId + '\'' + ", userId='" + userId + '\'' + '}';
    }

    public enum ResponseResult {
        SUCCESS, CANCEL, TIMEOUT, QUIT;
    }
}
